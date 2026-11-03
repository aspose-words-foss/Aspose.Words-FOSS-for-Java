// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// This class contains tests for classes and members that are used on text extraction: <see cref="Node.GetText"/>,
/// <see cref="LoadOptions.SkipFormatting"/>, <see cref="PlainTextDocument"/> etc.
/// </summary>
@Test
public class TestTextExtraction
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-13301 The sectPr elements that are located in the pPr element were not loaded when
    /// <see cref="LoadOptions.SkipFormatting"/> is 'true'. Headers/footers might be not extrated in this mode.
    /// </summary>
    @Test
    public void testJira13301() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\InsertDocument\\TestMultiSectionDoc.docx",
            createOptionsForSkipFormatting());
        msAssert.areEqual(3, doc.getSections().getCount());
    }

    /// <summary>
    /// Creates load options with <see cref="LoadOptions.SkipFormatting"/> is set to 'true'.
    /// </summary>
    private static LoadOptions createOptionsForSkipFormatting()
    {
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setLoadMode(LoadMode.SKIP_FORMATTING);
        return loadOptions;
    }

    /// <summary>
    /// WORDSNET-13301 If w:r elements are located in w:rPr elements, their text was not extracted
    /// when <see cref="PlainTextDocument"/> is used.
    /// </summary>
    @Test
    public void testRunInRprElement() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira11751.docx", createOptionsForSkipFormatting());
        Paragraph para = doc.getFirstSection().getHeadersFooters().get(0).getParagraphs().get(0);
        msAssert.areEqual("Bold underlined text in header.\r", para.getText());
    }

    /// <summary>
    /// WORDSNET-13301 The second and later cells in a merged cell range have text that is not displayed
    /// by MS Word, but was extracted by Aspose.Words in the skip-formatting mode. (For fixed table layout only.)
    /// </summary>
    @Test
    public void testMergedCellsInFixedLayout() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Grid\\Fixed\\2003\\TestHMerge.docx",
            createOptionsForSkipFormatting());
        Table table = doc.getFirstSection().getBody().getTables().get(1);
        msAssert.areEqual("C1\u0007\u0007", table.getText());
    }


    /// <summary>
    /// Checks that <see cref="Document.GetText"/> and <see cref="PlainTextDocument.Text"/> return identical text.
    /// </summary>
    private static void checkExtractedText(String docFileName) throws Exception
    {
        Document doc = TestUtil.open(docFileName);
        PlainTextDocument plainTextDoc = new PlainTextDocument(TestUtil.buildTestFileName(docFileName));
        msAssert.areEqual(doc.getText(), plainTextDoc.getText());
    }

}

