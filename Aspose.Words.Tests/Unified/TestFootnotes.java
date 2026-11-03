// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests how for model handles footnotes and endnotes.
/// </summary>
@Test
public class TestFootnotes extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFootnotesBasic(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestFootnotes", lf, sf);

        // One footnote and one endnote
        NodeList notes = doc.selectNodes("//Footnote");
        msAssert.areEqual(4, notes.getCount());

        // Check the model of a footnote.
        Footnote footnote = (Footnote)notes.get(0);
        msAssert.areEqual(FootnoteType.FOOTNOTE, footnote.getFootnoteType());
        msAssert.areEqual(true, footnote.isAuto());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_REFERENCE, footnote.getFont().getStyleIdentifier());
        msAssert.areEqual("\u0002 Footnote 1.\r", footnote.getText());

        // This footnote contains only one paragraph.
        Paragraph footnotePara = (Paragraph)footnote.getFirstChild();
        msAssert.isTrue(footnote.getFirstChild() == footnote.getLastChild());
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_TEXT, footnotePara.getParagraphFormat().getStyleIdentifier());

        // The footnote paragraph contains one SpecialChar that represents the footnote number.
        SpecialChar footnoteNumber = (SpecialChar)footnotePara.getFirstChild();
        msAssert.areEqual(StyleIdentifier.FOOTNOTE_REFERENCE, footnoteNumber.getFont().getStyleIdentifier());
        msAssert.areEqual("\u0002", footnoteNumber.getText());

        // Then the footnote paragraph contains one Run that represents the footnote text.
        Run footnoteRun = (Run)footnoteNumber.getNextSibling();
        msAssert.areEqual(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, footnoteRun.getFont().getStyleIdentifier());
        msAssert.areEqual(" Footnote 1.", footnoteRun.getText());


        // Check the model of an endnote.
        Footnote endnote = (Footnote)notes.get(1);
        msAssert.areEqual(FootnoteType.ENDNOTE, endnote.getFootnoteType());
        msAssert.areEqual(true, endnote.isAuto());
        msAssert.areEqual("\u0002 Endnote 1.\r", endnote.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFootnotesBasicSourceDataProvider")
	public void unifiedTestFootnotesBasicSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFootnotesBasic(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFootnotesBasicSourceDataProvider")
	Object[][] unifiedTestFootnotesBasicSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-4993 Footnotes and endnotes with custom marks are not supported.
    /// Added support for footnotes with custom reference mark.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSuppressFootnoteRef(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestSuppressFootnoteRef", lf, sf);

        // Document contains 2 footnotes: with custom and with autonumbered reference mark.
        msAssert.areEqual(2, doc.getChildNodes(NodeType.FOOTNOTE, true).getCount());


        // Check the fx2ootnote with custom reference mark.
        Paragraph fp = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(NodeType.PARAGRAPH, fp.getNodeType());
        msAssert.areEqual(NodeType.RUN, fp.getFirstChild().getNodeType());
        msAssert.areEqual("Aaa bbb", fp.getFirstChild().getText());

        Footnote footnote = (Footnote)fp.getFirstChild().getNextSibling();
        msAssert.areEqual(NodeType.FOOTNOTE, footnote.getNodeType());
        msAssert.areEqual(false, footnote.isAuto());

        // rtf not fully supported yet, only first char of reference mark obtained.
        // Rest of reference text left just as text run.
        if (sf == SaveFormat.RTF)
        {
            msAssert.areEqual("e", footnote.getReferenceMark());

            Run run = (Run)footnote.getNextSibling();
            msAssert.areEqual(NodeType.RUN, run.getNodeType());
            msAssert.areEqual("ditor", run.getText());

            run = (Run)run.getNextSibling();
            msAssert.areEqual(NodeType.RUN, run.getNodeType());
            msAssert.areEqual(" com", run.getText());
        }
        else
        {
            msAssert.areEqual("editor com", footnote.getReferenceMark());

            Run run = (Run)footnote.getNextSibling();
            msAssert.areEqual(NodeType.RUN, run.getNodeType());
            msAssert.areEqual(" ccc", run.getText());
        }


        // Check the footnote with autonumbered mark.
        {
            Paragraph np = (Paragraph)fp.getNextSibling();
            msAssert.areEqual(NodeType.PARAGRAPH, np.getNodeType());
            msAssert.areEqual(NodeType.RUN, np.getFirstChild().getNodeType());
            msAssert.areEqual("xxx", np.getFirstChild().getText());

            footnote = (Footnote)np.getFirstChild().getNextSibling();
            msAssert.areEqual(NodeType.FOOTNOTE, footnote.getNodeType());
            msAssert.areEqual(true, footnote.isAuto());

            Run run = (Run)footnote.getNextSibling();
            msAssert.areEqual(NodeType.RUN, run.getNodeType());
            msAssert.areEqual(" yyy", run.getText());
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSuppressFootnoteRefSourceDataProvider")
	public void unifiedTestSuppressFootnoteRefSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSuppressFootnoteRef(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSuppressFootnoteRefSourceDataProvider")
	Object[][] unifiedTestSuppressFootnoteRefSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFootnotePerSection(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestFootnotePerSection", lf, sf);

        // Document-wide
        checkFootnoteOptions(
            doc.getFootnoteOptions(),
            FootnotePosition.BENEATH_TEXT,               // non default
            NumberStyle.ARABIC,
            1,
            FootnoteNumberingRule.RESTART_PAGE);         // non default
        checkEndnoteOptions(
            doc.getEndnoteOptions(),
            EndnotePosition.END_OF_SECTION,              // non default
            NumberStyle.LOWERCASE_ROMAN,
            1,
            FootnoteNumberingRule.RESTART_SECTION);      // non default

        // Section 1
        checkFootnoteOptions(
            doc.getFirstSection().getPageSetup().getFootnoteOptions(),
            FootnotePosition.BENEATH_TEXT,               // non default
            NumberStyle.ARABIC,
            1,
            FootnoteNumberingRule.RESTART_PAGE);         // non default
        checkEndnoteOptions(
            doc.getFirstSection().getPageSetup().getEndnoteOptions(),
            EndnotePosition.END_OF_SECTION,              // non default
            NumberStyle.LOWERCASE_ROMAN,
            1,
            FootnoteNumberingRule.RESTART_SECTION);      // non default

        // Section 2
        checkFootnoteOptions(
            doc.getLastSection().getPageSetup().getFootnoteOptions(),
            FootnotePosition.BOTTOM_OF_PAGE,
            NumberStyle.GB_3,                            // non default
            5,                                          // non default
            FootnoteNumberingRule.CONTINUOUS);
        checkEndnoteOptions(
            doc.getLastSection().getPageSetup().getEndnoteOptions(),
            EndnotePosition.END_OF_SECTION,              // non default
            NumberStyle.UPPERCASE_LETTER,                // non default
            10,                                         // non default
            FootnoteNumberingRule.CONTINUOUS);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFootnotePerSectionSourceDataProvider")
	public void unifiedTestFootnotePerSectionSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFootnotePerSection(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFootnotePerSectionSourceDataProvider")
	Object[][] unifiedTestFootnotePerSectionSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void checkFootnoteOptions(
        FootnoteOptions options,
        /*FootnotePosition*/int location,
        /*NumberStyle*/int numberStyle,
        int startNumber,
        /*FootnoteNumberingRule*/int restartRule)
    {
        msAssert.areEqual(location, options.getPosition());
        msAssert.areEqual(numberStyle, options.getNumberStyle());
        msAssert.areEqual(startNumber, options.getStartNumber());
        msAssert.areEqual(restartRule, options.getRestartRule());
    }

    private static void checkEndnoteOptions(
        EndnoteOptions options,
        /*EndnotePosition*/int location,
        /*NumberStyle*/int numberStyle,
        int startNumber,
        /*FootnoteNumberingRule*/int restartRule)
    {
        msAssert.areEqual(location, options.getPosition());
        msAssert.areEqual(numberStyle, options.getNumberStyle());
        msAssert.areEqual(startNumber, options.getStartNumber());
        msAssert.areEqual(restartRule, options.getRestartRule());
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInsertFootnote(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        DocumentBuilder b = new DocumentBuilder();

        FootnoteOptions fo = b.getDocument().getFootnoteOptions();
        fo.setPosition(FootnotePosition.BENEATH_TEXT);
        fo.setRestartRule(FootnoteNumberingRule.RESTART_PAGE);
        fo.setNumberStyle(NumberStyle.UPPERCASE_ROMAN);

        EndnoteOptions eo = b.getDocument().getEndnoteOptions();
        eo.setPosition(EndnotePosition.END_OF_DOCUMENT);
        eo.setNumberStyle(NumberStyle.UPPERCASE_LETTER);
        eo.setStartNumber(5);

        b.insertFootnote(FootnoteType.FOOTNOTE, "test footnote");
        b.insertFootnote(FootnoteType.ENDNOTE, "test endnote");
        b.writeln();

        TestUtil.saveOpen(b.getDocument(), "Model\\Footnote\\TestInsertFootnote", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInsertFootnoteSourceDataProvider")
	public void unifiedTestInsertFootnoteSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInsertFootnote(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInsertFootnoteSourceDataProvider")
	Object[][] unifiedTestInsertFootnoteSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}


    /// <summary>
    /// WORDSNET-5858 NullReferenceException occurred on saving single space ReferenceMark into Docx.
    /// Field.Symbol in ReferenceMark was not supported.
    /// Field.Symbols converts into Runs and fixes this issue.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSymbolicReferenceMark(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestJira5858", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        msAssert.areEqual(2, doc.getChildNodes(NodeType.FOOTNOTE, true).getCount());

        Footnote footnote1 = ((Footnote)doc.getChild(NodeType.FOOTNOTE, 0, true));
        msAssert.areEqual(" ", footnote1.getReferenceMark());

        Footnote footnote2 = ((Footnote)doc.getChild(NodeType.FOOTNOTE, 1, true));

        msAssert.areEqual("\uf02a", footnote2.getReferenceMark());
        msAssert.areEqual("Symbol", footnote2.getRunPr().getNameAscii());
        msAssert.areEqual("Symbol", footnote2.getRunPr().getNameOther());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSymbolicReferenceMarkSourceDataProvider")
	public void unifiedTestSymbolicReferenceMarkSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSymbolicReferenceMark(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSymbolicReferenceMarkSourceDataProvider")
	Object[][] unifiedTestSymbolicReferenceMarkSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-11763 Footnote Continuation Notice was not preserved when saving a document.
    /// Added this FootnoteSeparatorType to <see cref="DocxFootnotesWriter"/> and <see cref="WmlFootnotesWriter"/>.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFootnoteContinuationNotice(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestJira11763", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        FootnoteSeparator separator = doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_CONTINUATION_NOTICE);
        msAssert.isNotNull(separator);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFootnoteContinuationNoticeSourceDataProvider")
	public void unifiedTestFootnoteContinuationNoticeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFootnoteContinuationNotice(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFootnoteContinuationNoticeSourceDataProvider")
	Object[][] unifiedTestFootnoteContinuationNoticeSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-15870 Exception on RTF export, exception on DOCX export.
    /// Invalid footnote inside source RTF document, which contains a ParagraphBreak as a ReferenceMark.
    /// </summary>
    /// <remarks>
    /// RTF reader was imported this document with a null ReferenceMark, which was leading to exceptions on export.
    /// </remarks>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFootnoteParagraphBreakReferenceMark(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        // Verifies no exception.
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\TestJira15870", TestUtil.getUnifiedScenario(lf, sf) | UnifiedScenario.NO_GOLD);

        NodeCollection footnotes = doc.getChildNodes(NodeType.FOOTNOTE, true);
        msAssert.areEqual(1, footnotes.getCount());

        // Verifies footnote's ReferenceMark.
        msAssert.areEqual("\r", ((Footnote)footnotes.get(0)).getReferenceMark());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFootnoteParagraphBreakReferenceMarkSourceDataProvider")
	public void unifiedTestFootnoteParagraphBreakReferenceMarkSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFootnoteParagraphBreakReferenceMark(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFootnoteParagraphBreakReferenceMarkSourceDataProvider")
	Object[][] unifiedTestFootnoteParagraphBreakReferenceMarkSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-17820 Provide API to Remove the Footnote Endnote Separator Line.
    /// Public API usage to remove separator line.
    /// </summary>
    @Test
    public void test17820() throws Exception
    {
        Document doc = TestUtil.open("Model\\Footnote\\Test20641.docx");

        FootnoteSeparator endnoteSeparator = doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        endnoteSeparator.getFirstParagraph().getFirstChild().remove();

        doc = TestUtil.saveOpen(doc, "Model\\Footnote\\Test20641_17820.docx", new OoxmlSaveOptions(), false);
        endnoteSeparator = doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        msAssert.areEqual("\r", endnoteSeparator.getFirstParagraph().getText());
    }

    /// <summary>
    /// WORDSNET-27239 Modifying paragraph style of the footnote separator and footnote continuation separator
    /// Public API usage to format separator line.
    /// </summary>
    @Test
    public void test27239() throws Exception
    {
        Document doc = TestUtil.open("Model\\Footnote\\Test20641.docx");
        FootnoteSeparator endnoteSeparator = doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        endnoteSeparator.getFirstParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        doc = TestUtil.saveOpen(doc, "Model\\Footnote\\Test20641_27239.docx", new OoxmlSaveOptions(), false);
        endnoteSeparator = doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        msAssert.areEqual(ParagraphAlignment.CENTER, endnoteSeparator.getFirstParagraph().getParagraphFormat().getAlignment());
    }
}

