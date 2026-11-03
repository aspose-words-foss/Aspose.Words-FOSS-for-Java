package com.aspose;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msArray;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests ImageReader, ImageWriter, their builders and ExternalImageLibTestCore.
 */
@Test(groups = SingleThreadTestGroup.SINGLE_THREAD)
public class TestExternalImageLibTestCore
{
    @BeforeClass(alwaysRun = true)
    public void beforeClass()
    {
        ImageReader.setDebug(false);
        ImageWriter.setDebug(false);
        hasPngEncoder = ExternalImageLibTestCore.hasPngEncoder();
    }

    @BeforeMethod
    public void reset()
    {
        enablePngEncoder(hasPngEncoder);
    }

    private static void enablePngEncoder(boolean flag)
    {
        ExternalImageLibTestCore.setHasPngEncoder(flag);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass()
    {
        enablePngEncoder(hasPngEncoder);
    }

    @Test
    public void testDefaultReader()
    {
        ImageReader reader = new ImageReaderBuilder().build();
        msAssert.isTrue(reader instanceof ImageIoReader);
    }

    @Test
    public void testAllReadersUsed()
    {
        ImageReader reader = new ImageReaderBuilder()
                .withTWM()
                .withImageIo()
                .build();

        msAssert.isTrue(reader instanceof TWMReader);
        msAssert.isTrue(reader.getNext() instanceof ImageIoReader);
        msAssert.isNull(reader.getNext().getNext());
    }

    @Test
    public void testDefaultWriter()
    {
        ImageWriter reader = new ImageWriterBuilder().build();
        msAssert.isTrue(reader instanceof ImageIoWriter);
        msAssert.isTrue(reader.getNext() instanceof PngEncoderWriter);
    }

    @Test
    public void testAllWritersUsed()
    {
        ImageWriter reader = new ImageWriterBuilder()
                .withImageIo()
                .withSimplifiedTiff()
                .build();

        msAssert.isTrue(reader instanceof ImageIoWriter);
        msAssert.isTrue(reader.getNext() instanceof SimplifiedJaiTiffWriter);
        msAssert.isNull(reader.getNext().getNext());
    }

    @Test
    public void testHasAvailableExternalLibraries()
    {
        // 1. PngEncoder is available. Empty output
        enablePngEncoder(true);
        checkSystemOutput(null, new String[]{"PngEncoder"});

        // 2. PngEncoder is not available.
        enablePngEncoder(false);
        checkSystemOutput(new String[]{"Java PngEncoder - worlds fastest java png encoder"}, null);
    }

    @Test
    public void testPrintWarningIfCannotReadAndAnyLibMissed() throws Exception
    {
        String userMessage = null;
        PrintStream sysout = System.out;
        try
        {
            // redirect the sysout to the string
            ByteArrayOutputStream awOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(awOutput));

            // PngEncoder is not available.
            enablePngEncoder(false);

            ImageReader reader = new ImageReaderBuilder()
                    .withTWM()
                    .withImageIo()
                    .build();

            msAssert.isTrue(reader instanceof TWMReader);
            msAssert.isTrue(reader.getNext() instanceof ImageIoReader);

            MemoryStream imageStream = new MemoryStream();
            imageStream.write(new byte[]{10, 20, 30}, 0, 3);

            try
            {
                // All the readers fail and the warning about the missing library is printed.
                BufferedImage result = reader.read(imageStream);
            }
            catch (Exception e)
            {
                // it is ok
            }

            // second time no warning
            sysout = System.out;
            awOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(awOutput));

            try
            {
                BufferedImage result = reader.read(imageStream);
            }
            catch (Exception e)
            {
                // it is ok
            }

            userMessage = new String(awOutput.toByteArray());
            msAssert.isTrue(userMessage.isEmpty());
        }
        finally
        {
            // assign sysout back
            System.setOut(sysout);
            System.out.println(userMessage);
        }
    }

    @Test
    public void testReadWriteImages() throws Exception
    {
        String[] fileNames = Directory.getFiles(TestFxUtil.buildTestFileName("Rendering\\Aps\\TestImages"));
        msArray.sort(fileNames, StringOrdinalComparer.DEFAULT);

        for (String in : fileNames)
        {
            byte[] imageBytes = File.readAllBytes(in);

            // read
            BufferedImage image = ExternalImageLibTestCore.read(imageBytes);
            msAssert.assertNotNull(image);
            msAssert.isTrue(image.getWidth() > 10);
            msAssert.isTrue(image.getHeight() > 10);

            // write
            byte[] result = ExternalImageLibTestCore.write(image, FileFormat.GIF);
            msAssert.isTrue(result != null && result.length > 0);

            result = ExternalImageLibTestCore.write(image, FileFormat.BMP);
            msAssert.isTrue(result != null && result.length > 0);

            result = ExternalImageLibTestCore.write(image, FileFormat.JPEG);
            msAssert.isTrue(result != null && result.length > 0);

            result = ExternalImageLibTestCore.write(image, FileFormat.PNG);
            msAssert.isTrue(result != null && result.length > 0);

            result = ExternalImageLibTestCore.write(image, FileFormat.TIFF);
            msAssert.isTrue(result != null && result.length > 0);
        }
    }

    @Test
    public void testPngEncoderNotInClasspath() throws Exception
    {
        // pngEncoder is not available.
        enablePngEncoder(false);

        ImageWriter writer = new ImageWriterBuilder()
                .withPngEncoder()
                .withImageIo()
                .build();

        // pngEncoder is not available ImageIO is used
        msAssert.isTrue(writer instanceof ImageIoWriter);
        msAssert.isNull(writer.getNext());

        BufferedImage bmp = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bmp.createGraphics();
        g.drawString("TEST", 20, 20);
        g.dispose();

        MemoryStream pngStream = new MemoryStream();

        ImageWriterBuilder.Options options = new ImageWriterBuilder.Options();
        writer.writePng(bmp, pngStream, options);

        msAssert.isTrue(pngStream.getLength() > 0);
    }

    private void checkSystemOutput(String[] containsText, String[] notContainsText)
    {
        if (containsText == null && notContainsText == null)
            return;

        String userMessage = null;
        PrintStream sysout = System.out;

        try
        {
            // redirect the sysout to the string
            ByteArrayOutputStream awOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(awOutput));

            ExternalImageLibTestCore.checkLibraries();
            userMessage = new String(awOutput.toByteArray());

            if (containsText != null)
            {
                for (String word : containsText)
                    msAssert.isTrue(userMessage.contains(word));
            }

            if (notContainsText != null)
            {
                for (String word : notContainsText)
                    msAssert.isFalse(userMessage.contains(word));
            }
            awOutput.reset();
        }
        finally
        {
            // assign sysout back
            System.setOut(sysout);
            System.out.println(userMessage);
        }
    }

    private boolean hasPngEncoder;

}
