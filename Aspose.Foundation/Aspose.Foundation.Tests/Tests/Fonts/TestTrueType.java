// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.IO.Directory;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test reading of TrueType fonts.
/// </summary>
@Test
public class TestTrueType
{
    @BeforeClass (alwaysRun = true)
    public void setUp() throws Exception
    {
        TestFxUtil.setUpTests();
        Directory.createDirectory(TestFxUtil.getInTestOutPath("TrueType"));
    }

    /// <summary>
    /// WORDSNET-8260, 9334, 9423 "EndOfStreamException" exception occurs during converting document to PDF.
    /// The problem occurred because font is True Type Version 0 (1) and Font Metrics were read improperly.
    /// Changed code to read metrics properly from old versions of True Type.
    /// </summary>
    @Test
    public void testReadMetricsOldVersion() throws Exception
    {
        // Read True Type Version 0
        TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Expo1.TTF"));
        // Read True Type Version 1
        TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\USPS_BAR.TTF"));
    }

    /// <summary>
    /// WORDSNET-9205 "IndexOutOfRangeException" exception occurs during converting document to PDF.
    /// The problem occurred because the latest character (65535) contains invalid mapping.
    /// Made code resilient.
    /// </summary>
    @Test
    public void testDefect9205() throws Exception
    {
        TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\NEWTOW__.TTF"));
    }

    /// <summary>
    /// WORDSNET-15433 “System.ArgumentException: Item has already been added. Key in dictionary: '”
    /// occurs during rendering to PDF and XPS.
    /// The problem occurred because CMAP table of the font contained 4 [0;0] segments that leads to an attempt to
    /// add few glyphs with the same key into a hashtable. Check for the key is added.
    /// </summary>
    @Test
    public void testDefect15433() throws Exception
    {
        TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\CHARLOSC.TTF"));
    }

    /// <summary>
    /// WORDSNET-25211 Code39 font is not embedded into PDF.
    /// Code39 font doesn't contains postscript name and exception is thrown upon loading the font.
    /// </summary>
    @Test
    public void testDefect25211() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\code39.ttf"));
        msAssert.areEqual("Code 39", font.getPostscriptName());
    }

    /// <summary>
    /// WORDSNET-24711 Distance between text lines is too small during rendering.
    /// Font with incorrect hhea table returns wrong LineSpacing value.
    /// </summary>
    @Test
    public void testDefect24711() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\lte50329.ttf"));

        // These values are obtained from GDI+.
        msAssert.areEqual(935, font.getAscent());
        msAssert.areEqual(250, font.getDescent());
        msAssert.areEqual(1185, font.getLineSpacing());
        msAssert.areEqual(1000, font.getEmHeight());
    }

    /// <summary>
    /// There was an error in reading TrueType rev 1.5 font metrics.
    /// </summary>
    @Test
    public void testTrueTypeRev15() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Ira.ttf"));

        // These values are obtained from GDI+.
        msAssert.areEqual(1000, font.getAscent());
        msAssert.areEqual(824, font.getDescent());
        msAssert.areEqual(2024, font.getLineSpacing());
        msAssert.areEqual(1000, font.getEmHeight());
    }

    /// <summary>
    /// Buffet Script font contains glyphs with negative advance widths.
    /// OpenType spec says that advance width should be positive but MS Word allows negative values.
    /// So we should do the same.
    /// </summary>
    @Test
    public void testNegativeAdvanceWidth() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Buffet Script.otf"));

        msAssert.greater(0, font.getGlyphs().getGlyphByCharCode(0xA8).getAdvanceWidth());
        msAssert.greater(0, font.getGlyphs().getGlyphByCharCode(0xB4).getAdvanceWidth());
    }

    /// <summary>
    /// Test that Macintosh TrueType fonts are handled correctly.
    /// </summary>
    @Test
    public void testMacintoshTrueType() throws Exception
    {
        TTFont font = TTFontBuilder.readTtc(TestFxUtil.buildTestFileName("TrueType\\MacTrueType\\Cochin.ttc"), "Cochin");
        msAssert.areEqual("Cochin", font.getFamilyName());
        msAssert.areEqual("Cochin", font.getPostscriptName());

        font = TTFontBuilder.readTtc(TestFxUtil.buildTestFileName("TrueType\\MacTrueType\\Futura.ttc"), "Futura Medium");
        msAssert.areEqual("Futura", font.getFamilyName());
        msAssert.areEqual("Futura-Medium", font.getPostscriptName());

        font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\MacTrueType\\HelveticaNeue.ttf"));
        msAssert.areEqual("Helvetica Neue", font.getFamilyName());
        msAssert.areEqual("HelveticaNeue", font.getPostscriptName());

        font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\MacTrueType\\HelveticaNeueBold.ttf"));
        msAssert.areEqual("Helvetica Neue", font.getFamilyName());
        msAssert.areEqual("HelveticaNeue-Bold", font.getPostscriptName());
    }

    /// <summary>
    /// Custom font defines negative usWinDescent values.
    /// OpenType spec says that it should be positive but MS Word allows negative values.
    /// </summary>
    @Test
    public void testNegativeUsWinDescent() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Shapes1.ttf"));

        msAssert.greater(0, font.getDescent());
    }

    /// <summary>
    /// WORDSNET-22821 CJK metrics adjustments are wrongly used for Barcode font.
    /// </summary>
    @Test
    public void test22821() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test22821\\IDAutomationC128S.ttf"));

        msAssert.isFalse(font.isCjkMetrics());
    }

    /// <summary>
    /// WORDSNET-24139 Customer font has invalid 'vhea' version.
    /// </summary>
    @Test
    public void test24139() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test24139\\CN-CD128.TTF"));

        msAssert.isNotNull(font);
    }

    /// <summary>
    /// WORDSNET-25217 Customer font has invalid 'vhea' version.
    /// </summary>
    @Test
    public void test25217() throws Exception
    {
        TTFont font = TTFontBuilder.readTtc(TestFxUtil.buildTestFileName("TrueType\\Test25217\\kai08mz.ttc"), "AR StdKaiZuinn Md");

        msAssert.isNotNull(font);
    }

    /// <summary>
    /// WORDSNET-25754 Customer font has malformed 'vmtx' table.
    /// </summary>
    @Test
    public void test25754() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test25754\\BMWTypeGlobalPro-Regular.ttf"));

        msAssert.isNotNull(font);
    }

    /// <summary>
    /// WORDSNET-26737 Exception parsing 'post' table.
    /// </summary>
    @Test
    public void test26737() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test26737\\AGLTSYM1.ttf"));

        msAssert.isNotNull(font);
    }

    /// <summary>
    /// WORDSNET-27389 Chinese fonts with Big5 encoding in cmap and name tables.
    /// </summary>
    @Test (dataProvider = "test27389DataProvider")
    public void test27389(String fontFile, String fontName) throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test27389\\" + fontFile));

        msAssert.areEqual(fontName, font.getFamilyName());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27389DataProvider")
	public static Object[][] test27389DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test27389.ttf",  "文鼎中粗隸"},
			{"Test27393.ttf",  "金梅海報書法字形"},
		};
	}

    /// <summary>
    /// WORDSNET-28017 Error in parsing COLR table.
    /// </summary>
    @Test
    public void test28017() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test28017\\SEGUIEMJv151.TTF"));

        msAssert.isNotNull(font);
        msAssert.isTrue(font.isColored());
    }

    /// <summary>
    /// WORDSNET-28625 Error in parsing corrupted cmap table.
    /// </summary>
    @Test
    public void test28625() throws Exception
    {
        TTFont font = TTFontBuilder.readOpenType(TestFxUtil.buildTestFileName("TrueType\\Test28625\\PHTK53SF.TTF"));

        msAssert.areEqual("書法中楷加框（破音三）", font.getFamilyName());
    }
}

