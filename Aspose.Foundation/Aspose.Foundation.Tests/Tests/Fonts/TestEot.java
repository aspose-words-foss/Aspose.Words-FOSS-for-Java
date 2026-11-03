// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Directory;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


@Test
public class TestEot
{
    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception
    {
        TestFxUtil.setUpTests();
        Directory.createDirectory(TestFxUtil.getInTestOutPath("TrueType"));
    }

    @Test
    public void testUncompressedEot() throws Exception
    {
        String fontPath = TestFxUtil.buildTestFileName("TrueType\\ROC.EOT");

        byte[] eotData = StreamUtil.copyFileToByteArray(fontPath);
        byte[] openTypeData = EotFontFiler.tryExtractOpenTypeFromEot(eotData);

        TTFont font = TTFontBuilder.readOpenType(openTypeData);

        msAssert.areEqual("Rockwell", font.getFamilyName());
    }

    @Test
    public void testCompressedEot() throws Exception
    {
        String fontPath = TestFxUtil.buildTestFileName("TrueType\\Calibri.eot");

        byte[] eotData = StreamUtil.copyFileToByteArray(fontPath);
        byte[] openTypeData = EotFontFiler.tryExtractOpenTypeFromEot(eotData);

        TTFont font = TTFontBuilder.readOpenType(openTypeData);

        msAssert.areEqual("Calibri", font.getFamilyName());
    }

    /// <summary>
    /// Tests that compression works for simple file.
    /// </summary>
    @Test
    public void testLzCompSimpleFileCompression() throws Exception
    {
        verifyLzCompCompression(
            TestFxUtil.buildTestFileName("TrueType\\SimpleFile.txt"),
            TestFxUtil.buildTestFileName("TrueType\\SimpleFile.lzcomp"));
    }

    /// <summary>
    /// Tests that decompression works for simple file.
    /// File has been compressed using original C code.
    /// </summary>
    @Test
    public void testLzCompSimpleFileDecompression() throws Exception
    {
        verifyLzCompDecompression(
            TestFxUtil.buildTestFileName("TrueType\\SimpleFile.lzcomp"),
            TestFxUtil.buildTestFileName("TrueType\\SimpleFile.txt"));
    }

    /// <summary>
    /// Tests that compression works with Rle enabled.
    /// </summary>
    @Test
    public void testLzCompRleCompression() throws Exception
    {
        verifyLzCompCompression(
            TestFxUtil.buildTestFileName("TrueType\\Rle.txt"),
            TestFxUtil.buildTestFileName("TrueType\\Rle.lzcomp"));
    }

    /// <summary>
    /// Tests that decompression works with Rle enabled.
    /// File has been compressed using original C code.
    /// </summary>
    @Test
    public void testLzCompRleDecompression() throws Exception
    {
        verifyLzCompDecompression(
            TestFxUtil.buildTestFileName("TrueType\\Rle.lzcomp"),
            TestFxUtil.buildTestFileName("TrueType\\Rle.txt"));
    }

    /// <summary>
    /// Tests that decompression works for compressed Calibri font.
    /// File has been compressed using MS Word font embedding function and decompressed
    /// using original C code.
    /// </summary>
    @Test (dataProvider = "testLzCompCalibriDataProvider")
    public void testLzCompCalibri(String compressedFile, String decompressedFile) throws Exception
    {
        verifyLzCompDecompression(TestFxUtil.buildTestFileName(compressedFile), TestFxUtil.buildTestFileName(decompressedFile));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLzCompCalibriDataProvider")
	public static Object[][] testLzCompCalibriDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TrueType\\CalibriBlock1.lzcomp",  "TrueType\\CalibriBlock1.bin"},
			{"TrueType\\CalibriBlock2.lzcomp",  "TrueType\\CalibriBlock2.bin"},
			{"TrueType\\CalibriBlock3.lzcomp",  "TrueType\\CalibriBlock3.bin"},
		};
	}

    @Test
    public void testMagnitudeReader() throws Exception
    {
        // These values are taken from MTX specification.
        byte[] data = new byte[] { (byte) 0x90, (byte) 0x48, (byte) 0x84 };
        short[] expectedResult = new short[] { 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0 };

        MemoryStream stream = new MemoryStream(data);
        try /*JAVA: was using*/
        {
            MagnitudeReader reader = new MagnitudeReader(stream);

            for (int i = 0; i < expectedResult.length; i++)
            {
                msAssert.areEqual(expectedResult[i], reader.readValue());
            }
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Tests <see cref="MagnitudeWriter"/>.
    /// </summary>
    @Test
    public void testMagnitudeWriter() throws Exception
    {
        // These values are taken from MTX specification.
        // As there is just a code snippet, we should extend it with values which are assumed throughout a fragment of codes.
        short[] data = new short[] { 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1 };
        byte[] expectedResult = new byte[] { (byte) 0x90, (byte) 0x48, (byte) 0x84, (byte) 0x00 };

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            MagnitudeWriter writer = new MagnitudeWriter(stream);
            for (int i = 0; i < data.length; i++)
                writer.writeValue(data[i]);
            writer.flush();

            msAssert.areEqual(expectedResult, stream.toArray());
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// This is additional test for <see cref="MagnitudeReader"/> and <see cref="MagnitudeWriter"/>
    /// that verifies round-trip with a various values.
    /// </summary>
    @Test
    public void testMagnitudeRoundtrip() throws Exception
    {
        short[] testValues = { 0, 1, 2, 3, -1, -2, -3, 8, -8, 9, -9, 20, -20, 127, -127, 128, -128, 32767, -32767, 0x90, 0x48, 0x84 };

        MemoryStream writeStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            // Write test values to a stream.
            MagnitudeWriter writer = new MagnitudeWriter(writeStream);
            for (int i = 0; i < testValues.length; i++)
                writer.writeValue(testValues[i]);
            writer.flush();

            MemoryStream readStream = new MemoryStream(writeStream.toArray());
            try /*JAVA: was using*/
            {
                MagnitudeReader reader = new MagnitudeReader(readStream);
                // Read values and compare them with the original values.
                for (int i = 0; i < testValues.length; i++)
                    msAssert.areEqual(testValues[i], reader.readValue());

                msAssert.areEqual(readStream.getPosition(), readStream.getLength());
            }
            finally { if (readStream != null) readStream.close(); }
        }
        finally { if (writeStream != null) writeStream.close(); }
    }

    /// <summary>
    /// Tests CVT table coding round-trip.
    /// </summary>
    @Test
    public void testCvtRoundtrip() throws Exception
    {
        byte[] originalCvt = { (byte) 0x20, (byte) 0xA8, (byte) 0xD9, (byte) 0x10, (byte) 0xAA, (byte) 0xBB, (byte) 0xFE, (byte) 0xEE, (byte) 0xEE, (byte) 0x01, (byte) 0xEE, (byte) 0xFF, (byte) 0xEE, (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFA, (byte) 0xFA };

        byte[] encodedCvt = CtfEncoder.encodeCvtTable(originalCvt);
        byte[] decodedCvt = CtfDecoder.decodeCvtTable(encodedCvt);

        msAssert.areEqual(originalCvt, decodedCvt);
    }

    /// <summary>
    /// Tests CVT table encoding.
    /// </summary>
    @Test (dataProvider = "testCvtEncodingDataProvider")
    public void testCvtEncoding(String fontName) throws Exception
    {
        String ttfFile = buildFontFileName(fontName, "ttf");
        Stream stream = File.openRead(ttfFile);
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(stream);
            fontTablesReader.readHeader();


            byte[] uncompressedCvt = fontTablesReader.readTable(OpenTypeTableTag.CVT);
            byte[] encodedCvt = CtfEncoder.encodeCvtTable(uncompressedCvt);

            checkFontData(fontName, "eot.cvt", encodedCvt);
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCvtEncodingDataProvider")
	public static Object[][] testCvtEncodingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"segoeui"},
			{"arial"},
			{"angsau"},
			{"symbol"},
			{"tahoma"},
			{"wingding"},
			{"MATURASC"},
			{"ActionMan.Regular"},
			{"ActionMan.Bold"},
			{"ActionMan.Italic"},
			{"ActionMan.BoldItalic"},
		};
	}

    /// <summary>
    /// Tests HDMX table encoding.
    /// </summary>
    @Test (dataProvider = "testHdmxEncodingDataProvider")
    public void testHdmxEncoding(String fontName) throws Exception
    {
        String ttfFile = buildFontFileName(fontName, "ttf");
        Stream stream = File.openRead(ttfFile);
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(stream);
            fontTablesReader.readHeader();

            // Read all helper tables that are needed for encoding HDMX table.
            fontTablesReader.seekToTable(OpenTypeTableTag.HEAD);
            FontHeader head = FontHeader.read(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.MAXP);
            MaximumProfile maxp = new MaximumProfile(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HHEA);
            HorizontalHeader hhea = new HorizontalHeader(fontTablesReader.getBinaryReader());

            fontTablesReader.seekToTable(OpenTypeTableTag.HMTX);
            HorizontalMetrics hmtx = HorizontalMetrics.read(fontTablesReader.getBinaryReader(), (hhea.NumberOfHMetrics & 0xFFFF), (maxp.NumGlyphs & 0xFFFF));

            byte[] uncompressedHdmx = fontTablesReader.readTable(OpenTypeTableTag.HDMX);
            byte[] encodedHdmx = CtfEncoder.encodeHdmxTable(uncompressedHdmx, maxp.NumGlyphs, head.UnitsPerEm, hmtx);

            checkFontData(fontName, "eot.hdmx", encodedHdmx);
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testHdmxEncodingDataProvider")
	public static Object[][] testHdmxEncodingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"segoeui"},
			{"LiteEuro"},
			{"ActionMan.Regular"},
			{"ActionMan.Bold"},
			{"ActionMan.Italic"},
			{"ActionMan.BoldItalic"},
		};
	}

    /// <summary>
    /// Tests VDMX table encoding.
    /// </summary>
    @Test (dataProvider = "testVdmxEncodingDataProvider") // This font contains groups that refers to the different offsets in table.
    public void testVdmxEncoding(String fontName) throws Exception
    {
        String ttfFile = buildFontFileName(fontName, "ttf");
        Stream stream = File.openRead(ttfFile);
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(stream);
            fontTablesReader.readHeader();

            byte[] uncompressedVdmx = fontTablesReader.readTable(OpenTypeTableTag.VDMX);
            byte[] encodedVdmx = CtfEncoder.encodeVdmxTable(uncompressedVdmx);

            checkFontData(fontName, "eot.vdmx", encodedVdmx);
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVdmxEncodingDataProvider")
	public static Object[][] testVdmxEncodingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"arial"},
			{"symbol"},
			{"segoeui"},
		};
	}

    /// <summary>
    /// Tests CTF encoding of glyph table.
    /// </summary>
    @Test (dataProvider = "testGlyphTableCtfEncodingDataProvider")
    public void testGlyphTableCtfEncoding(String fontName) throws Exception
    {
        String ttfFile = buildFontFileName(fontName, "ttf");

        Stream stream = File.openRead(TestFxUtil.buildTestFileName(ttfFile));
        try /*JAVA: was using*/
        {
            SfntReader fontTablesReader = new SfntReader(stream);
            fontTablesReader.readHeader();
            fontTablesReader.seekToTable(OpenTypeTableTag.HEAD);
            FontHeader head = FontHeader.read(fontTablesReader.getBinaryReader());

            CtfEncodedData ctfEncodedData = CtfGlyphEncoder.encodeGlyphData(fontTablesReader, head.isLocaShort());

            checkFontData(fontName, "eot.GlyphTable", ctfEncodedData.getFontTable());
            checkFontData(fontName, "eot.PushData", ctfEncodedData.getPushData());
            checkFontData(fontName, "eot.Instructions", ctfEncodedData.getInstructions());
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGlyphTableCtfEncodingDataProvider")
	public static Object[][] testGlyphTableCtfEncodingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"segoeui"},
			{"ActionMan.Regular"},
			{"ActionMan.Bold"},
			{"ActionMan.Italic"},
			{"ActionMan.BoldItalic"},
		};
	}

    /// <summary>
    /// Tests round-trip of push data.
    /// </summary>
    @Test
    public void testPushData() throws Exception
    {
        IntList originalValues = new IntList(new int[]
            {0, 1, 250, 251, 252, 253, 254, 255, 256, 506, 507, 508, 509, 761, 762, 32767,
                -1,-250, -251, -252, -253, 254, -255, -256, -506, -507, -508, -509, -761, -762, -32768,
                5, 6, 5, 7, 5, 8, 2, // Hop3Code
                2, 7, 2, 5, 2, 8, 2, 9, 2, 3, //Hop4Code
                23, 45, 23, 54, 23, 67, 23, 34, 23, 76, 23}); // Hop3Code 2 times

        MemoryStream encodedStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(encodedStream);
            CtfGlyphEncoder.encodePushData(originalValues, writer);

            encodedStream.setPosition(0);
            BigEndianBinaryReader reader = new BigEndianBinaryReader(encodedStream);
            short[] decodedValues = CtfGlyphDecoder.decodePushData(reader, (((/*ushort*/short)originalValues.getCount()) & 0xFFFF));

            msAssert.areEqual(originalValues.getCount(), decodedValues.length);
            for (int i = 0; i < originalValues.getCount(); i++)
                msAssert.areEqual(originalValues.get(i), decodedValues[i]);
        }
        finally { if (encodedStream != null) encodedStream.close(); }
    }

    /// <summary>
    /// Tests 255UShort values round-trip.
    /// </summary>
    @Test
    public void test255UShort() throws Exception
    {
        int[] originalValues = { 0, 1, 253, 250, 254, 255, 256, 506, 507, 508, 509, 761, 762, 65535 };

        MemoryStream encodedStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(encodedStream);
            for (int i = 0; i < originalValues.length; i++)
                UShort255.write(originalValues[i], writer);

            int[] decodedValues = new int[originalValues.length];
            encodedStream.setPosition(0);
            BigEndianBinaryReader reader = new BigEndianBinaryReader(encodedStream);
            for (int i = 0; i < originalValues.length; i++)
                decodedValues[i] = UShort255.read(reader);

            msAssert.areEqual(originalValues, decodedValues);
        }
        finally { if (encodedStream != null) encodedStream.close(); }
    }

    /// <summary>
    /// Tests 255Short values round-trip.
    /// </summary>
    @Test
    public void test255Short() throws Exception
    {
        short[] originalValues =
            {0, 1, 253, 250, 254, 255, 256, 506, 507, 508, 509, 761, 762, 32767,
            -1, -253, -250, -254, -255, -256, -506, -507, -508, -509, -761, -762, -32768};

        MemoryStream encodedStream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BigEndianBinaryWriter writer = new BigEndianBinaryWriter(encodedStream);
            for (int i = 0; i < originalValues.length; i++)
                Short255.write(originalValues[i], writer);

            short[] decodedValues = new short[originalValues.length];
            encodedStream.setPosition(0);
            BigEndianBinaryReader reader = new BigEndianBinaryReader(encodedStream);
            for (int i = 0; i < originalValues.length; i++)
                decodedValues[i] = Short255.read(reader);

            msAssert.areEqual(originalValues, decodedValues);
        }
        finally { if (encodedStream != null) encodedStream.close(); }
    }

    /// <summary>
    /// Checks specified font data equality to the specified expected font data array.
    /// </summary>
    private static void checkFontData(String expectedFontDataName, String expectedFontDataExtension, byte[] fontData) throws Exception
    {
        String expectedFontDataFile = buildFontFileName(expectedFontDataName, expectedFontDataExtension);
        byte[] expectedFontData = StreamUtil.copyFileToByteArray(expectedFontDataFile);
        msAssert.areEqual(expectedFontData, fontData);
    }

    /// <summary>
    /// Verifies decompression with LzComp.
    /// </summary>
    private void verifyLzCompDecompression(String compressedFile, String decompressedFile) throws Exception
    {
        String outFilePath = TestFxUtil.buildOutFileName("TrueType\\LzCompDecompressionReslut", "", "");
        byte[] compressedData = StreamUtil.copyFileToByteArray(compressedFile);
        byte[] uncompressedData = LzCompDecoder.unpackData(compressedData);

        Stream file = File.open(outFilePath, FileMode.CREATE);
        try /*JAVA: was using*/
        {
            file.write(uncompressedData, 0, uncompressedData.length);
        }
        finally { if (file != null) file.close(); }

        msAssert.isTrue(TestFxUtil.compareFiles(decompressedFile, outFilePath));
    }

    /// <summary>
    /// Verifies compression with LzComp.
    /// </summary>
    private void verifyLzCompCompression(String uncompressedFile, String compressedFile) throws Exception
    {
        String outFilePath = TestFxUtil.buildOutFileName("TrueType\\LzCompCompressionReslut", "", "");
        byte[] uncompressedData = StreamUtil.copyFileToByteArray(uncompressedFile);
        byte[] compressedData = LzCompEncoder.packData(uncompressedData);

        Stream file = File.open(outFilePath, FileMode.CREATE);
        try /*JAVA: was using*/
        {
            file.write(compressedData, 0, compressedData.length);
        }
        finally { if (file != null) file.close(); }

        msAssert.isTrue(TestFxUtil.compareFiles(compressedFile, outFilePath));
    }

    /// <summary>
    /// Returns font file name constructed from the specified font file name and extension
    /// relative to the 'TestData\TrueType\EotFonts\' folder.
    /// </summary>
    private static String buildFontFileName(String fontFileNameWithoutExtension, String fontExtension) throws Exception
    {
        return TestFxUtil.buildTestFileName(msString.format("TrueType\\EotFonts\\{0}.{1}", fontFileNameWithoutExtension, fontExtension));
    }
}

