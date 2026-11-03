package com.aspose;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.TiffCompression;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageMetadata;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReaderSpi;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

///
/// Java-specific unit-tests (non-portable)
///
@Test
public class TestImageFormat
{
    @Test
    public void testJiraJ1740() throws Exception
    {
        // Create bitmap
        BitmapPal bitmap = new BitmapPal(10, 10, 200.0f, 200.0f);

        // Save tiff
        String fileOutput = TestFxUtil.buildOutFileName("TestJiraJ1740", "", ".tiff");

        FileOutputStream outputStream = new FileOutputStream(fileOutput);
        try
        {
            TiffWriterPal pal = new TiffWriterPal();
            pal.saveFirstFrame(bitmap, outputStream, TiffCompression.CCITT_3, ImageBinarizationMethodCore.THRESHOLD, false);
        }
        finally
        {
            outputStream.close();
        }

        // Check tiff
        FileImageInputStream f = new FileImageInputStream(new File(fileOutput));
        try
        {
            javax.imageio.ImageReader r = new TIFFImageReaderSpi().createReaderInstance(null);
            r.setInput(f);

            TIFFImageMetadata metadata = (TIFFImageMetadata) r.getImageMetadata(0);
            Entry unit = metadata.getTIFFField(262);    // TIFF_PHOTOMETRIC

            msAssert.areEqual(((Number) unit.getValue()).intValue(), 0); // WhiteIsZero (for compatibility)
        }
        finally
        {
            f.close();
        }
    }

    @Test
    public void testJiraJ1744() throws Exception
    {
        // Create bitmap
        BitmapPal bitmap = new BitmapPal(10, 10, 200.0f, 200.0f);

        // Save tiff
        String fileOutput = TestFxUtil.buildOutFileName("TestJira1744", "", ".tiff");

        FileOutputStream outputStream = new FileOutputStream(fileOutput);
        try
        {
            TiffWriterPal pal = new TiffWriterPal();
            pal.saveFirstFrame(bitmap, outputStream, TiffCompression.CCITT_3, ImageBinarizationMethodCore.THRESHOLD, false);
        }
        finally
        {
            outputStream.close();
        }

        // Check tiff
        FileImageInputStream f = new FileImageInputStream(new File(fileOutput));
        try
        {
            javax.imageio.ImageReader r = new TIFFImageReaderSpi().createReaderInstance(null);
            r.setInput(f);

            TIFFImageMetadata metadata = (TIFFImageMetadata) r.getImageMetadata(0);
            Entry unit = metadata.getTIFFField(296);

            msAssert.areEqual(((Number) unit.getValue()).intValue(), 2); // RES_UNIT_INCH
        }
        finally
        {
            f.close();
        }
    }

}
