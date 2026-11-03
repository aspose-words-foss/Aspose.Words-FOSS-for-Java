// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/04/2019 by Denis Panov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;
import com.aspose.CustomTextReader;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing MarkdownFormatDetector.
/// </summary>
@Test
public class TestMarkdownFormatDetector
{
    /// <summary>
    /// Tests markdown detector with empty file.
    /// </summary>
    @Test
    public void testEmptyFile() throws Exception
    {
        verifyFile("TestEmpty.mrdwn", false);
    }

    /// <summary>
    /// Tests that there are no false positives while detecting non-Markdown files.
    /// </summary>
    // FOSS binary1.jpg sample was deleted as unsafe TestData.
    @Test (dataProvider = "testNonMarkdownFilesDataProvider")
    public void testNonMarkdownFiles(String fileName) throws Exception
    {
        verifyFile(fileName, false);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNonMarkdownFilesDataProvider")
	public static Object[][] testNonMarkdownFilesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"binary2.bin"},
			{"BinaryExePart.bin"},
			{"BinaryLibFile.lib"},
			{"IniFile.ini"},
			{"SimpleWord.txt"},
			{"RegFile.reg"},
		};
	}


    /// <summary>
    /// WORDSNET-18865 FileFormatUtil.DetectFileFormat(Stream) recognizes some PNG images as Markdown.
    /// The number of unprintable characters should be checked relative to a number of actually read characters
    /// instead of whole file.
    /// </summary>
    @Test
    public void test18865() throws Exception
    {
        verifyFile("Test18865.png", false);
    }



    /// <summary>
    /// WORDSNET-26236 Without explicitly specifying 'LoadFormat.Markdown',
    /// Aspose.Words cannot determine the MD format in the user's MD file.
    ///
    /// The significance of the FencedCode feature has been increased.
    /// </summary>
    @Test
    public void test26236() throws Exception
    {
        verifyFile("Test26236.mrdwn", true);
    }

    /// <summary>
    /// WORDSNET-28614 Xmpeg file is detected as Markdown by FileFormatUtil.
    /// Introduced a limit on a sentence length in <see cref="MarkdownFormatDetector"/>.
    /// </summary>
    @Test
    public void test28614() throws Exception
    {
        verifyFile("Test28614.xmpeg", false);
    }

    /// <summary>
    /// Verifies whether the input file is markdown or not.
    /// </summary>
    private static void verifyFile(String fileName, boolean isMarkdown) throws Exception
    {
        msAssert.areEqual(isMarkdown, isMarkdownFile(fileName), "file "+ fileName + (isMarkdown ? " isn't" : " is") + " detected as markdown");
    }

    /// <summary>
    /// Returns true, if a specified file has a markdown format.
    /// </summary>
    private static boolean isMarkdownFile(String fileName) throws Exception
    {
        String fullPath = TestUtil.buildTestFileName(msString.format("ImportMarkdown\\FormatDetector\\{0}", fileName));
        // We use some files from a related ImportTxt directory, so try it too.
        if (!File.exists(fullPath))
            fullPath = TestUtil.buildTestFileName(msString.format("ImportTxt\\FormatDetector\\{0}", fileName));

        FileStream fs = new FileStream(fullPath, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            CustomTextReader reader = new CustomTextReader(fs);
            reader.setEncodingByBom();

            FileFormatInfo fileFormatInfo = MarkdownFormatDetector.detect(reader);

            return (fileFormatInfo != null) && (fileFormatInfo.getLoadFormat() == LoadFormat.MARKDOWN);
        }
        finally { if (fs != null) fs.close(); }
    }
}

