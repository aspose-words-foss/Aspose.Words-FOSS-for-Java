// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Minimal functionality tests for PalZipXXX classes.
/// </summary>
@Test
public class TestPalZip
{
    @Test
    public void testReader() throws Exception
    {
        Stream stream = File.openRead(TestFxUtil.buildTestFileName("Zip\\TestReader.zip"));
        try /*JAVA: was using*/
        {
            checkContainsFile1File2(stream);
        }
        finally { if (stream != null) stream.close(); }
    }

    @Test
    public void testWriter() throws Exception
    {
        String fileName = TestFxUtil.getInTestOutPath("Zip\\TestWriter.zip");
        TestFxUtil.ensureDirectoryForFileExists(fileName);
        Stream stream = File.create(fileName);
        try /*JAVA: was using*/
        {
            ZipWriterPal writer = new ZipWriterPal(stream);
            writer.addEntry("File 1.txt", new MemoryStream(Encoding.getASCII().getBytes("This is file 1.\r\n")));
            writer.addStoredEntry("File 2.txt", Encoding.getASCII().getBytes("Hello World!\r\nThis is file 1.\r\n"));
            writer.finish();

            // Check the zip that we have created using our own reader code.
            stream.setPosition(0);
            checkContainsFile1File2(stream);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// This is a "standard" check for zip file contents using ZipReaderPal that we reuse in several tests.
    /// </summary>
    private static void checkContainsFile1File2(Stream stream) throws Exception
    {
        ZipReaderPal reader = new ZipReaderPal(stream);

        msAssert.isTrue(reader.moveToNextEntry());
        msAssert.areEqual("File 1.txt", reader.getEntryFileName());
        byte[] file1 = reader.loadEntryToByteArray();
        msAssert.areEqual("This is file 1.\r\n", Encoding.getASCII().getString(file1));

        msAssert.isTrue(reader.moveToNextEntry());
        msAssert.areEqual("File 2.txt", reader.getEntryFileName());
        byte[] file2 = reader.loadEntryToByteArray();
        msAssert.areEqual("Hello World!\r\nThis is file 1.\r\n", Encoding.getASCII().getString(file2));

        msAssert.isFalse(reader.moveToNextEntry());
    }

    @Test
    public void testDeflate() throws Exception
    {
        checkCompressionMethod(ZipMethod.DEFLATE, 298);
    }

    @Test
    public void testZLib() throws Exception
    {
        checkCompressionMethod(ZipMethod.ZLIB, 304);
    }

    private static void checkCompressionMethod(/*ZipMethod*/int compressionMethod, int compressedSize) throws Exception
    {
        Stream srcStream = File.openRead(TestFxUtil.buildTestFileName("Zip\\TestCompressionMethod.txt"));
        try /*JAVA: was using*/
        {
            final int UNCOMPRESSED_LENGTH = 536;
            msAssert.areEqual(UNCOMPRESSED_LENGTH, srcStream.getLength());

            // Check compression makes it expected length.
            MemoryStream dstStream = new MemoryStream();
            int length = ZipUtilPal.deflate(srcStream, dstStream, compressionMethod);
            msAssert.areEqual(compressedSize, length);
            msAssert.areEqual(compressedSize, dstStream.getPosition());
            msAssert.areEqual(compressedSize, dstStream.getLength());


            // Verify the decompression results in the original data.
            dstStream.setPosition(0);
            byte[] uncompressedBytes = ZipUtilPal.inflate(dstStream, UNCOMPRESSED_LENGTH, compressionMethod);
            byte[] originalBytes = StreamUtil.copyStreamToByteArray(srcStream);
            msAssert.isTrue(ArrayUtil.isArrayEqual(originalBytes, uncompressedBytes));
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    @Test
    public void testCompressedData() throws Exception
    {
        Stream stream = File.openRead(TestFxUtil.buildTestFileName("Zip\\TestReader.zip"));
        try /*JAVA: was using*/
        {
            ZipReaderPal reader = new ZipReaderPal(stream);

            msAssert.isTrue(reader.moveToNextEntry());
            msAssert.areEqual("File 1.txt", reader.getEntryFileName());
            byte[] compressedFile1 = reader.loadCompressedEntryToByteArray();
            byte[] file1 = CompressedData.getData(compressedFile1);
            msAssert.areEqual("This is file 1.\r\n", Encoding.getASCII().getString(file1));

            msAssert.isTrue(reader.moveToNextEntry());
            msAssert.areEqual("File 2.txt", reader.getEntryFileName());
            byte[] compressedFile2 = reader.loadCompressedEntryToByteArray();
            byte[] file2 = CompressedData.getData(compressedFile2);
            msAssert.areEqual("Hello World!\r\nThis is file 1.\r\n", Encoding.getASCII().getString(file2));

            msAssert.isFalse(reader.moveToNextEntry());
        }
        finally { if (stream != null) stream.close(); }
    }
}

