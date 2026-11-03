// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.ms.System.IO.Directory;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.Debug;
import com.aspose.CustomTextReader;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests how TxtFormatDetector works with various files.
/// </summary>
@Test
public class TestTxtFormatDetector
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
    }

    @Test
    public void testSimpleNumbering1() throws Exception
    {
        verifyTextFile("SimpleNumbering1.txt", true);
    }

    @Test
    public void testSimpleWord() throws Exception
    {
        verifyTextFile("SimpleWord.txt", true);
    }

    @Test
    public void testSimpleNumber() throws Exception
    {
        verifyTextFile("SimpleNumber.txt", true);
    }

    // FOSS TestBinaryJpg removed: its binary1.jpg sample was deleted as unsafe TestData; TestBinaryBin
    // (binary2.bin) still covers "a binary file is not detected as text".

    @Test
    public void testBinaryBin() throws Exception
    {
        verifyTextFile("binary2.bin", false);
    }

    @Test
    public void testBinaryExe() throws Exception
    {
        verifyTextFile("BinaryExePart.bin", false);
    }

    @Test
    public void testBinaryLib() throws Exception
    {
        verifyTextFile("BinaryLibFile.lib", false);
    }

    @Test
    public void testIniFile() throws Exception
    {
        verifyTextFile("IniFile.ini", true);
    }

    @Test
    public void testRegFile() throws Exception
    {
        verifyTextFile("RegFile.reg", true);
    }

    /// <summary>
    /// WORDSNET-7000 UnsupportedFileFormatException occurs when loading a TEXT file into DOM.
    /// The problem occurs because of too strong criteria for text files detecting.
    /// The criteria are weaker now.
    /// </summary>
    @Test
    public void testJira7000() throws Exception
    {
        verifyTextFile("TestJira7000.txt", true);
    }




    /// <summary>
    /// WORDSNET-8196 UnsupportedFileFormatException occurs when loading a TEXT file into DOM
    /// </summary>
    @Test
    public void testJira8196() throws Exception
    {
        verifyTextFile("TestJira8196.txt", true);
    }


    /// <summary>
    /// Test some binary file, detect them as text file.
    /// </summary>
    @Test
    public void testBinaryFiles() throws Exception
    {
        String binaryFilesFolder = "ImportTxt\\FormatDetector\\BinaryFiles\\";
        String[] files = Directory.getFiles(TestUtil.buildTestFileName(binaryFilesFolder));
        for (String file : files)
        {
            verifyTextFile(file, false, true);
        }
    }


    /// <summary>
    /// Tests that the page break (12) and cell end (7) characters are allowed control characters for text.
    /// </summary>
    @Test
    public void testPageBreakAndCellEndChars() throws Exception
    {
        verifyText("text\f\f\f\f cell\u0007 cell\u0007 cell\u0007\f", true);
    }

    /// <summary>
    /// Tests text that contains non-printable characters.
    /// </summary>
    @Test (dataProvider = "testNonPrintableCharsDataProvider")
    public void testNonPrintableChars(String data, boolean expectedAsText) throws Exception
    {
        verifyText(data, expectedAsText);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNonPrintableCharsDataProvider")
	public static Object[][] testNonPrintableCharsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"text\u0000",  true},
			{"Text that has 74 chars. Text that has 74 chars. Text that has 74 chars.\u0001\u0001\u0001",  false},
			{"Text that has 178 chars. Text that has 178 chars. Text that has 178 chars. Text that has 178 " +
        "chars. Text that has 178 chars. Text that has 178 chars. Text that has 178 chars. \u0001\u0001\u0001",  true},
		};
	}

    /// <summary>
    /// WORDSNET-13777 Text document was detected as unknown format.
    /// </summary>
    @Test
    public void testJira13777() throws Exception
    {
        verifyTextFile("TestJira13777.txt", true);
    }

    // FOSS TestJira14361 removed: its TestJira14361.doc sample was deleted as unsafe TestData.


    /// <summary>
    /// WORDSNET-23607 "Unsupported file format: Unknown" on loading TXT file.
    /// Tweaked CharsetDetector for Shift-JIS encoding and allowed 0x80 control character
    /// for Shift-JIS encoding in TxtFormatDetector.
    /// </summary>
    @Test
    public void test23607() throws Exception
    {
        String testPath = TestUtil.getInTestDataPath("ImportTxt\\EncodingDetector\\Test23607.txt");
        verifyTextFile(testPath, true);
    }

    /// <summary>
    /// WORDSNET-26228 Aspose.Words does not throw exception upon loading corrupted document.
    /// Decrease confidence on bad words length.
    /// </summary>
    @Test
    public void test26228() throws Exception
    {
        msAssert.areEqual(LoadFormat.UNKNOWN, FileFormatUtil.detectFileFormat(
                TestUtil.buildTestFileName("ImportTxt\\FormatDetector\\Test26228.docx")).getLoadFormat());
    }

    /// <summary>
    /// Checks whether a file is a text file or not.
    /// </summary>
    /// <param name="fileName">File name to test.</param>
    /// <param name="expected"></param>
    private static void verifyTextFile(String fileName, boolean expected) throws Exception
    {
        verifyTextFile(fileName, expected, false);
    }

    /// <summary>
    /// Checks whether a file is a text file or not.
    /// </summary>
    /// <param name="fileName">File name to test.</param>
    /// <param name="expected"></param>
    private static void verifyTextFile(String fileName, boolean expected, boolean checkAllEncoding) throws Exception
    {
        msAssert.areEqual(expected, isTextFile(fileName, checkAllEncoding), "file " + fileName + " is text");
    }

    /// <summary>
    /// Checks whether the specified string data is text or not.
    /// </summary>
    /// <param name="data">Data to test.</param>
    /// <param name="expected">The data is expected to be detected as text.</param>
    private static void verifyText(String data, boolean expected) throws Exception
    {
        String fileName = TestUtil.getInTestOutPath("ImportTxt\\FormatDetector\\detecting.txt");
        TestUtil.ensureDirectoryForFileExists(fileName);
        File.writeAllText(fileName, data);

        msAssert.areEqual(expected, isTextFile(fileName, false), "Wrong detection of the following data as text:\r\n" + data);
    }

    /// <summary>
    /// Returns true if the file is text file
    /// </summary>
    private static boolean isTextFile(String fileName, boolean checkAllEncoding) throws Exception
    {
        String fullPath = Path.isPathRooted(fileName)
            ? fileName
            : TestUtil.buildTestFileName("ImportTxt\\FormatDetector\\" + fileName);

        FileStream fs = new FileStream(fullPath, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            // Disable Debug.AssertCallingClass assertion called in CustomTextReader constructor
            Debug.showDialogOnAssert(false);
            Debug.throwOnAssert(false);

            CustomTextReader textReader = new CustomTextReader(fs);
            textReader.setEncodingByBom();

            FileFormatInfo fileFormatInfo = TxtFormatDetector.detect(textReader);

            return (fileFormatInfo != null) && (fileFormatInfo.getLoadFormat() == LoadFormat.TEXT);
        }
        finally { if (fs != null) fs.close(); }
    }
}

