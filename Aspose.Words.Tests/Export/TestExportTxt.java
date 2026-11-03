// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2020 by Vasiliy Stepchenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests of issues specific for export to Txt format.
/// </summary>
@Test
public class TestExportTxt extends TestGoldTxtBase
{
    /// <summary>
    /// WORDSNET-21385 System.ArgumentOutOfRangeException was thrown while saving DOCX to TXT.
    /// <see cref="TxtTableBuilder"/> was improved to handle the nested tables properly.
    /// </summary>
    @Test (dataProvider = "test21385DataProvider")
    public void test21385(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        TxtSaveOptions options = new TxtSaveOptions();
        options.setSaveFormat(SaveFormat.TEXT);
        options.setPreserveTableLayout(true);

        // Verify no exception on saving and fixate output by gold file.
        verifyTextGold(doc, doc.getOriginalFileName(), "", GoldLevel.EXPORT_ONLY, options);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21385DataProvider")
	public static Object[][] test21385DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ExportTxt\\Test21385A.docx"},
			{"ExportTxt\\Test21385B.docx"},
			{"ExportTxt\\Test21385C.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-22090 Each Line in TXT File should have a Fix Length of Characters in Line.
    /// Implemented new <see cref="TxtSaveOptions.MaxCharactersPerLine"/> option.
    /// </summary>
    @Test (dataProvider = "test22090DataProvider")
    public void test22090(int lineLength) throws Exception
    {
        Document doc = TestUtil.open("ExportTxt\\Test22090.docx");

        TxtSaveOptions options = new TxtSaveOptions();
        // The value -100 is just chosen to check behavior by default.
        if (lineLength != -100)
            options.setMaxCharactersPerLine(lineLength);

        String suffix = msString.format("({0})", lineLength);
        verifyTextGold(doc, doc.getOriginalFileName(), suffix, GoldLevel.EXPORT_ONLY, options);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22090DataProvider")
	public static Object[][] test22090DataProvider() throws Exception
	{
		return new Object[][]
		{
			{60},
			{120},
			{7},
			{0},
			{-1},
			{1},
			{-100},
		};
	}



    /// <summary>
    /// WORDSNET-25287 Put comment content into a separate line when export to TXT.
    /// To mimic Word we should start all comments from a new line in <see cref="TextWriter"/>.
    /// </summary>
    @Test
    public void test25287() throws Exception
    {
        verifyExport("..\\ExportTxt\\Test25287.docx");
    }

    /// <summary>
    /// WORDSNET-26261 NullReferenceException is thrown when using ToString method on a cell
    /// with nested table and PreserveTableLayout option.
    /// Tests support of PreserveTableLayout option for ToString() method.
    /// </summary>
    @Test
    public void test26261() throws Exception
    {
        final String CELL_TEXT = "Test\r\nTest                 Test\r\nTest                 Test";
        final String ROW_TEXT = "Test                                         c                  " +
          "                          c\r\nTest                 Test\r\nTest                 Test";

        Document doc = TestUtil.open("ExportTxt\\Test26261.docx");

        TxtSaveOptions opt = new TxtSaveOptions();
        opt.setPreserveTableLayout(true);
        msAssert.areEqual(msString.trim(doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().toString(opt)), CELL_TEXT);
        msAssert.areEqual(msString.trim(doc.getFirstSection().getBody().getTables().get(0).getFirstRow().toString(opt)), ROW_TEXT);
    }


    static final String BLOCK_QUOTE_HTML_SNIPPET =
            "<blockquote>\r\n                    Some quoted text_1\r\n                    <br />\r\n                    More quoted text_1\r\n                </blockquote>\r\n                <blockquote>\r\n                    Some quoted text_2\r\n                    <br />\r\n                    More quoted text_2\r\n                    <blockquote>\r\n                        Some nested quoted text\r\n                        <br />\r\n                        ...as blockquotes can be nested\r\n                        <blockquote>\r\n                            Deepest nested quoted textA\r\n                            <br />\r\n                            Deepest nested quoted textB\r\n                            <br />\r\n                            Deepest nested quoted textC\r\n                            <br />\r\n                            Deepest nested quoted textD\r\n                        </blockquote>\r\n                    </blockquote>\r\n                </blockquote>";
}

