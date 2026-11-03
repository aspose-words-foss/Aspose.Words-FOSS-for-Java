// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2018 by Konstantin Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.CharsetDetector;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.CodePage;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import org.testng.annotations.DataProvider;


@Test
public class TestCharsetDetector
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-16775 Charset is detected incorrectly if file has less then 10 symbols.
    /// CharsetMultiByteRecognizer didn't consider "good" chars count for short (less then 10 symbols) strings.
    /// </summary>
    @Test
    public void testJira16775() throws Exception
    {
        Encoding encoding = CharsetDetector.detect(File.open(TestUtil.buildTestFileName("ImportTxt\\TestJira16775.txt"), FileMode.OPEN, FileAccess.READ));
        msAssert.areEqual(932, encoding.getCodePage());    // Shift-JIS
    }

    /// <summary>
    /// WORDSJAVA-1754 UnsupportedFileFormatException for non-detectable encoding.
    /// </summary>
    @Test
    public void testJiraJ1754() throws Exception
    {
        Document doc = TestUtil.open("ImportTxt\\TestJiraJ1754.txt");

        String text = ((Run)((Paragraph)((Body)((Section)doc.getFirstChild()).getFirstChild()).getFirstChild()).getFirstChild()).getText();
        msAssert.areEqual(text, "あああ");
    }

    @Test
    public void testJiraJ1754SetWrongEncoding() throws Exception
    {
        // Set wrong encoding. Chinese encoding conflicts with Japanese symbols but doesn't throw.
        LoadOptions options = new LoadOptions();
        options.setEncodingInternal(Encoding.getEncoding("big5"));

        Document doc = TestUtil.open("ImportTxt\\TestJiraJ1754.txt", options);
        String text = ((Run)((Paragraph)((Body)((Section)doc.getFirstChild()).getFirstChild()).getFirstChild()).getFirstChild()).getText();

        // Unsupported symbols substituted by '?':.Net returns "???", Java - "?????".
        msAssert.isTrue("???".equals(text) || "?????".equals(text));
    }

    /// <summary>
    /// WORDSNET-18929 TxtSaveOptions converts single quote ' into â€.
    /// The problem is incorrect encoding detection due to a insufficient number of analyzed
    /// bytes taken from the beginning of the document. To solve the problem,
    /// we increased the number of bytes in detection buffer from 512 bytes to 3kb.
    /// </summary>
    // FOSS: the Test18929_UTF8.html case was dropped — its input was removed as unsafe customer data (commit 56e13acbaa3).
    @Test (dataProvider = "test18929DataProvider")
    public void test18929(String testFileName, int expectedCodePage) throws Exception
    {
        checkCodepage(testFileName, expectedCodePage);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test18929DataProvider")
	public static Object[][] test18929DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test18929_Win1252.html",  CodePage.WINDOWS_LATIN_1_CODE_PAGE},
		};
	}

    /// <summary>
    /// WORDSNET-23607 "Unsupported file format: Unknown" on loading TXT file.
    /// Tweaked CharsetDetector for Shift-JIS encoding and allowed 0x80 control character
    /// for Shift-JIS encoding in TxtFormatDetector.
    /// </summary>
    @Test
    public void test23607() throws Exception
    {
        checkCodepage("Test23607.txt", CodePage.WINDOWS_JAPANESE_SHIFT_JIS);
    }


    /// <summary>
    /// WORDSNET-25453 Hebrew text is imported improperly from HTML.
    /// Improved skip of base64 images in <see cref="CharsetDetector.StripBase64Images"/>.
    /// </summary>
    @Test
    public void test25453() throws Exception
    {
        checkCodepage("Test25453.html", CodePage.CODE_PAGE_UTF_8);

        // FOSS: HTML cannot be loaded into a Document, so verify format detection
        // instead of loading the file and inspecting its content.
        checkHtmlFormat("Test25453.html");
    }



    /// <summary>
    /// Checks expected CodePage for a specified test file.
    /// </summary>
    private static void checkCodepage(String testFileName, int expectedCodePage) throws Exception
    {
        String testName = msString.format("ImportTxt\\EncodingDetector\\{0}", testFileName);
        Stream stream = File.open(TestUtil.buildTestFileName(testName), FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            Encoding encoding = CharsetDetector.detect(stream);
            msAssert.areEqual(expectedCodePage, encoding.getCodePage());
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Checks that the specified test file is detected as HTML format.
    /// The FOSS version cannot load HTML into a <see cref="Document"/>, so we verify
    /// format detection only instead of loading the file and inspecting its content.
    /// </summary>
    private static void checkHtmlFormat(String testFileName) throws Exception
    {
        String testName = msString.format("ImportTxt\\EncodingDetector\\{0}", testFileName);
        FileFormatInfo info = FileFormatUtil.detectFileFormat(TestUtil.buildTestFileName(testName));
        msAssert.areEqual(LoadFormat.HTML, info.getLoadFormat());
    }
}

