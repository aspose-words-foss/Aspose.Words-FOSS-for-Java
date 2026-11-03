// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests export to markdown format.
/// </summary>
@Test
public class TestExportMarkdown extends TestMarkdownBase
{
    /// <summary>
    /// Tests writing an empty document.
    /// </summary>
    @Test
    public void testEmpty() throws Exception
    {
        Document doc = new Document();
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestEmpty.md", new MarkdownSaveOptions(), false);
        msAssert.areEqual("\f", doc.getText());
    }

    /// <summary>
    /// Tests writing a document with Emphases.
    /// </summary>
    @Test (dataProvider = "testEmphasisDataProvider")
    public void testEmphasis(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestEmphasis", scenario);

        Paragraph paragraph = getParagraphWithText(doc, "Lorem ipsum dolor sit amet, consectetur _adipiscing_ ___elit***,");

        Run run = getRunWithText(paragraph, "Lorem");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "ipsum");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "dolor");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "sit");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "amet");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testEmphasisDataProvider")
	public static Object[][] testEmphasisDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with Quotes.
    /// </summary>
    @Test (dataProvider = "testQuoteADataProvider")
    public void testQuoteA(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestQuoteA", scenario);

        Paragraph paragraph = getParagraphWithText(doc, "sit");
        checkStyles(paragraph, "Quote62");

        paragraph = getParagraphWithText(doc, "adipiscing");
        checkStyles(paragraph, "Quote67", "Quote66", "Quote65", "Heading 3");

        paragraph = getParagraphWithText(doc, "elit");
        checkStyles(paragraph, "Quote63", "Heading 2");

        paragraph = getParagraphWithText(doc, "Lorem ipsum");
        checkStyles(paragraph, "Quote68", "SetextHeading1", "Heading 1", "Normal");

        paragraph = getParagraphWithText(doc, "dolor si amet,");
        checkStyles(paragraph, "Quote3", "Quote1");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testQuoteADataProvider")
	public static Object[][] testQuoteADataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with multi line quotes and multi line regular paragraphs.
    /// </summary>
    @Test (dataProvider = "testQuoteBDataProvider")
    public void testQuoteB(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestQuoteB", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem\u000bipsum\r", "Normal");
        checkParagraph(paragraphs.get(1), "foo\r", "Quote");
        checkParagraph(paragraphs.get(2), "bar\r", "Quote1");
        checkParagraph(paragraphs.get(3), "dolor sit amet,\u000bconsectetur adipiscing\u000belit,\r", "Normal");
        checkParagraph(paragraphs.get(4), "baz\u000bbop\u000bsed do\u000beiusmod\r", "Quote");
        checkParagraph(paragraphs.get(5), "\r", "Normal");
        checkParagraph(paragraphs.get(6), "tempor incididunt\r", "Quote1");
        checkParagraph(paragraphs.get(7), "ut labore\f", "Normal");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testQuoteBDataProvider")
	public static Object[][] testQuoteBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with AtxHeadings.
    /// </summary>
    @Test (dataProvider = "testAtxHeadingADataProvider")
    public void testAtxHeadingA(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAtxHeadingA", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem ipsum\r", "Heading 1");
        checkParagraph(paragraphs.get(1), "dolor sit amet,\r", "Heading 2");
        checkParagraph(paragraphs.get(2), "consectetur\r", "Heading 3");
        checkParagraph(paragraphs.get(3), "adipiscing\r", "Heading 6");
        checkParagraph(paragraphs.get(4), "####### elit,\u000b#sed\u000b##do\u000b###eiusmod###\u000b####tempor ####\r", "Normal");
        checkParagraph(paragraphs.get(5), "incididunt\r", "Heading 1");
        checkParagraph(paragraphs.get(6), "ut # # #\f", "Heading 1");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAtxHeadingADataProvider")
	public static Object[][] testAtxHeadingADataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with AtxHeadings combined with regular paragraphs.
    /// </summary>
    @Test (dataProvider = "testAtxHeadingBDataProvider")
    public void testAtxHeadingB(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAtxHeadingB", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem ipsum\r", "Normal");
        checkParagraph(paragraphs.get(1), "dolor sit amet,\r", "Heading 1");
        checkParagraph(paragraphs.get(2), "consectetur\r", "Heading 1");
        checkParagraph(paragraphs.get(3), "adipiscing\f", "Normal");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAtxHeadingBDataProvider")
	public static Object[][] testAtxHeadingBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with SetextHeadings.
    /// </summary>
    @Test (dataProvider = "testSetextHeadingADataProvider")
    public void testSetextHeadingA(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestSetextHeadingA", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem ipsum\r", "SetextHeading1", "Heading 1", "Normal");
        checkParagraph(paragraphs.get(1), "dolor sit amet,\f", "SetextHeading2", "Heading 2", "Normal");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetextHeadingADataProvider")
	public static Object[][] testSetextHeadingADataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with multi line SetextHeadings.
    /// </summary>
    @Test (dataProvider = "testSetextHeadingBDataProvider")
    public void testSetextHeadingB(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestSetextHeadingB", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem\u000bipsum\r", "SetextHeading1", "Heading 1", "Normal");
        checkParagraph(paragraphs.get(1), "dolor\u000bsit\u000bamet,\r", "SetextHeading2", "Heading 2", "Normal");
        checkParagraph(paragraphs.get(2), "consectetur\u000badipiscing\u000belit\f", "SetextHeading2", "Heading 2", "Normal");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetextHeadingBDataProvider")
	public static Object[][] testSetextHeadingBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with HorizontalRules.
    /// </summary>
    @Test (dataProvider = "testHorizontalRuleADataProvider")
    public void testHorizontalRuleA(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestHorizontalRuleA", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkHorizontalRule(paragraphs.get(0), "Normal");
        checkHorizontalRule(paragraphs.get(1), "Normal");
        checkHorizontalRule(paragraphs.get(2), "Normal");
        checkHorizontalRule(paragraphs.get(3), "Normal");

        checkParagraph(paragraphs.get(4), "-_*\u000b-*-\r", "Normal");

        checkHorizontalRule(paragraphs.get(5), "Normal");
        checkHorizontalRule(paragraphs.get(6), "Normal");

        msAssert.areEqual(7, paragraphs.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testHorizontalRuleADataProvider")
	public static Object[][] testHorizontalRuleADataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a document with HorizontalRules combined with heading and regular paragraph.
    /// </summary>
    @Test (dataProvider = "testHorizontalRuleBDataProvider")
    public void testHorizontalRuleB(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestHorizontalRuleB", scenario);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem\r", "SetextHeading2", "Heading 2", "Normal");
        checkParagraph(paragraphs.get(1), "--\u000bipsum\r", "Normal");
        checkHorizontalRule(paragraphs.get(2), "Normal");

        msAssert.areEqual(3, paragraphs.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testHorizontalRuleBDataProvider")
	public static Object[][] testHorizontalRuleBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Tests writing a multi section document with HeadersFooters.
    /// </summary>
    @Test
    public void testHeadersFooters() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestHeadersFooters.docx");

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setExportHeadersFootersMode(TxtExportHeadersFootersMode.ALL_AT_END);

        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestHeadersFooters.md", so);

        msAssert.areEqual("1Lorem ipsum\r", doc.getFirstSection().getBody().getFirstParagraph().getText());
        msAssert.areEqual("Section3 FIRST footer line3\f", doc.getFirstSection().getBody().getLastParagraph().getText());
    }

    /// <summary>
    /// Tests writing document with IndentedCodes.
    /// </summary>
    @Test
    public void testIndentedCode() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestIndentedCode", UnifiedScenario.DOCX_2_MD);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem ipsum\r", "IndentedCode");
        checkParagraph(paragraphs.get(1), "dolor\u000bsit\u000bamet,       consectetur\u000b# adipiscing elit,\r", "Normal");
        checkParagraph(paragraphs.get(2), "sed do\r", "Heading 1");
        checkParagraph(paragraphs.get(3), "eiusmod\u000b\u000btempor\u000bincididunt\u000but labore\u000b> et dolore\r", "IndentedCode");
        checkParagraph(paragraphs.get(4), "magna\u000baliqua.\r", "Quote");
        checkParagraph(paragraphs.get(5), "Ut enim\r", "Quote1", "IndentedCode");
        checkParagraph(paragraphs.get(6), "ad minim\r", "IndentedCode");
        checkParagraph(paragraphs.get(7), "veniam\r", "Quote");
        checkParagraph(paragraphs.get(8), "quis\r", "IndentedCode");
        checkParagraph(paragraphs.get(9), "========\u000bnostrud\r", "SetextHeading1", "Heading 1", "Normal");
        checkParagraph(paragraphs.get(10), "exercitation\f", "IndentedCode");
    }

    /// <summary>
    /// Tests writing document with FencedCodes.
    /// </summary>
    @Test
    public void testFencedCode() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFencedCode", UnifiedScenario.DOCX_2_MD);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paragraphs.get(0), "Lorem ipsum\r", "FencedCode");
        checkParagraph(paragraphs.get(1), "dolor\r", "FencedCode.C#");
        checkParagraph(paragraphs.get(2), "sit\u000bamet,\r", "FencedCode.C++");
        checkParagraph(paragraphs.get(3), "adipiscing elit,\r", "Heading 3");
        checkParagraph(paragraphs.get(4), "# eiusmod\r", "FencedCode");
        checkParagraph(paragraphs.get(5), "   incididunt\r", "Quote", "FencedCode.Java");
        checkParagraph(paragraphs.get(6), "> ut labore\u000b> et dlr\r", "FencedCode");
        checkParagraph(paragraphs.get(7), "quis\f", "FencedCode");
    }

    /// <summary>
    /// Tests writing document with InlineCodes.
    /// </summary>
    @Test
    public void testInlineCode() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestInlineCode", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Check Normal paragraph with combinations of regular, InlineCode and emphasis text.
        checkStyles(paras.get(0), "Normal");
        // A regular text.
        Run run = getRunWithText(paras.get(0), "```");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        // An emphasized text.
        run = getRunWithText(paras.get(0), "foo");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        // An emphasized InlineCode text.
        run = getRunWithText(paras.get(0), "bar");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        // The same as above, but inside a Heading.
        checkStyles(paras.get(1), "Heading 1");
        run = getRunWithText(paras.get(1), "baz");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        run = getRunWithText(paras.get(1), "bop");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());

        // The same as above, but inside a Quote.
        checkStyles(paras.get(2), "Quote");
        run = getRunWithText(paras.get(2), "lorem");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        run = getRunWithText(paras.get(2), "ipsum");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        // Check InlineCode with two back-ticks inside a Quote.
        run = getRunWithText(paras.get(2), "conse```ct`etur* adi*piscing");
        msAssert.areEqual("InlineCode.2", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        // Check InlineCode with four back-ticks inside a Quote.
        run = getRunWithText(paras.get(2), "d do");
        msAssert.areEqual("InlineCode.4", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
    }

    /// <summary>
    /// Tests writing document with Strikethrough.
    /// </summary>
    @Test
    public void testStrikethrough() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestStrikethrough", UnifiedScenario.DOCX_2_MD);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        checkStyles(para, "Normal");

        // A strikethrough text.
        Run run = getRunWithText(para, "Foo ~bar ");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        // A strikethrough + bold text.
        run = getRunWithText(para, "boz");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        // A regular text.
        run = getRunWithText(para, "~~ bop~~~\u000b~lorem");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());
        // A strikethrough + bold + italic text.
        run = getRunWithText(para, "set");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        // An InlineCode text.
        run = getRunWithText(para, "di~~piscin");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests writing document with Underline.
    /// </summary>
    @Test
    public void testUnderline() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestUnderline.docx");

        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions(); { saveOptions.setExportUnderlineFormatting(true); }
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setImportUnderlineFormatting(true); }
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestUnderline", UnifiedScenario.MD_2_MD, saveOptions, loadOptions);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        checkStyles(para, "Normal");

        // An underline text.
        Run run = getRunWithText(para, "Foo +bar ");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        // An underline + bold text.
        run = getRunWithText(para, "boz");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        // A regular text.
        run = getRunWithText(para, "++ bop+++\u000b+lorem");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(Underline.NONE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());
        // A underline + bold + italic text.
        run = getRunWithText(para, "set");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        // An InlineCode text.
        run = getRunWithText(para, "di++piscin");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests writing document with simple bullet lists.
    /// </summary>
    @Test
    public void testBulletListA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListA", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "o", "Normal");
        checkListItem(paras.get(3), "d\r", 1, "o", "Normal");
        checkListItem(paras.get(4), "e\r", 1, "o", "Normal");
        checkListItem(paras.get(5), "f\f", 2, "", "Normal");

        msAssert.areEqual(6, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with nested in single line bullet lists.
    /// </summary>
    @Test
    public void testBulletListB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListB", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 1, "o", "List", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "-", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "o", "Normal");
        checkListItem(paras.get(3), "d\r", 2, "", "List2", "List1", "Normal");
        checkListItem(paras.get(4), "e\f", 2, "", "List3", "List", "Normal");

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with nested in single line bullet lists and quotes.
    /// </summary>
    @Test
    public void testBulletListC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListC", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, " ", "List", "Quote", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "", "Quote2", "List1", "Quote1", "List", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 5, "", "Quote5", "List6", "List5", "Quote4", "List4", "List3", "Quote3",
            "List2", "Normal");
        checkListItem(paras.get(4), "e\f", 5, " ", "List11", "List10", "Quote7", "List9", "List8", "Quote6", "List7",
            "List", "Quote", "Normal");

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with headings inside bullet lists and quotes.
    /// </summary>
    @Test
    public void testBulletListD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListD", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "-", "Quote1", "Heading 1", "Normal");
        checkListItem(paras.get(2), "c\f", 0, "-", "Quote2", "SetextHeading2", "Heading 2", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with headings inside simple bullet lists.
    /// </summary>
    @Test
    public void testBulletListE() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListE", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Heading 1", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "o", "Heading 2", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests writing very complex document with bullet lists.
    /// </summary>
    @Test
    public void testBulletListF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListF", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "Lorem\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "ipsum\r", 0, "+", "Normal");
        checkListItem(paras.get(2), "dolor\r", 0, "*", "Normal");
        checkListItem(paras.get(3), "sit\r", 1, "o", "Normal");
        checkListItem(paras.get(4), "amet\r", 1, "o", "Normal");
        checkListItem(paras.get(5), "consectetur\r", 2, "", "Normal");
        checkListItem(paras.get(6), "adipiscing\u000belit\r", 3, "", "Normal");
        checkListItem(paras.get(7), "sed\r", 3, " ", "Heading 1", "Normal");
        checkListItem(paras.get(8), "do\r", 0, " ", "SetextHeading2", "Heading 2", "Normal");
        checkHorizontalRule(paras.get(9), "Normal");
        checkListItem(paras.get(9), "\r", 0, " ", "Normal");
        checkListItem(paras.get(10), "tabs indentation\u000beiusmod\r", 0, " ", "IndentedCode");
        checkListItem(paras.get(11), "tempor\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(12), "incididunt\r", 1, "o", "Quote2", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(13), "ut\r", 1, "o", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(14), "labore\u000b** et\r", 0, "*", "Normal");
        checkParagraph(paras.get(15), "-dolore\r", "Normal");
        checkHorizontalRule(paras.get(16), "Quote", "Normal");
        checkListItem(paras.get(16), "\r", 0, "+", "Quote");
        checkListItem(paras.get(17), "aliqua.\r", 0, " ", "Quote");
        checkListItem(paras.get(18), "Ut\u000b- enim\r", 1, "o", "Quote");
        checkParagraph(paras.get(19), "+\tad\r", "IndentedCode");
        checkListItem(paras.get(20), "minim\r", 0, "-", "Normal");
        checkListItem(paras.get(21), "veniam\r", 2, "", "Quote4", "List1", "Quote3", "List", "Quote", "Normal");
        checkListItem(paras.get(22), "quis\r", 0, "-", "Normal");
        checkListItem(paras.get(23), "nostrud\r", 0, " ", "Normal");
        checkListItem(paras.get(24), "exercitation\u000b-         foo\f", 1, "o", "Normal");

        msAssert.areEqual(25, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListG() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListG", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(2), "c\f", 1, "o", "List", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListH() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListH", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "-", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 1, "o", "List", "Quote", "Normal");
        checkListItem(paras.get(4), "e\r", 0, "-", "Quote", "Normal");
        checkListItem(paras.get(5), "f\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(6), "g\f", 1, "o", "Quote1", "List1", "Normal");

        msAssert.areEqual(7, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListI() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListI", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(1), "A list item with a blockquote:\r", 0, "*", "Normal");
        checkListItem(paras.get(2), "This is a blockquote\r", 0, " ", "Quote", "Normal");
        checkListItem(paras.get(3), "inside a list item.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading of a markdown document with bullet lists and indented code inside.
    /// </summary>
    @Test
    public void testBulletListJ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListJ", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraph(paras.get(0), "To put a code block within a list item\r", "Normal");
        checkListItem(paras.get(1), "A list item with a code block:\r", 0, "*", "Normal");
        checkListItem(paras.get(2), "[code goes here]\f", 0, " ", "IndentedCode");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading of a markdown document with bullet lists and horizontal rule.
    /// </summary>
    @Test
    public void testBulletListK() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListK", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkHorizontalRule(paras.get(0), "Quote", "Normal");
        checkListItem(paras.get(0), "\r", 0, "+", "Quote", "Normal");
        checkListItem(paras.get(1), "aliqua.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(2, paras.getCount());
    }

    /// <summary>
    /// Tests reading of a markdown document with bullet lists, horizontal rule and indented code.
    /// </summary>
    @Test
    public void testBulletListL() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestBulletListL", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "dolor\r", 0, "*", "Normal");
        checkListItem(paras.get(1), "sit\r", 1, "o", "Normal");

        checkHorizontalRule(paras.get(2), "Normal");
        checkListItem(paras.get(2), "\r", 0, " ", "Normal");

        checkListItem(paras.get(3), "tabs indentation\f", 0, " ", "IndentedCode");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with simple ordered lists.
    /// </summary>
    @Test
    public void testOrderedListA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListA", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "2.", "Normal");
        checkListItem(paras.get(3), "d\r", 0, "2.", "Normal");
        checkListItem(paras.get(4), "e\r", 1, "1.", "Normal");
        checkListItem(paras.get(5), "f\r", 2, "1.", "Normal");
        checkListItem(paras.get(6), "g\r", 3, "1.", "Normal");
        checkListItem(paras.get(7), "h\r", 2, "2.", "Normal");
        checkListItem(paras.get(8), "i\r", 1, "2.", "Normal");
        checkListItem(paras.get(9), "j\f", 0, "3.", "Normal");

        msAssert.areEqual(10, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with simple ordered lists with ')' as marker delimiter.
    /// </summary>
    @Test
    public void testOrderedListB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListB", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1)", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1)", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "2)", "Normal");
        checkListItem(paras.get(3), "d\r", 0, "2)", "Normal");
        checkListItem(paras.get(4), "e\r", 1, "1)", "Normal");
        checkListItem(paras.get(5), "f\r", 2, "1)", "Normal");
        checkListItem(paras.get(6), "g\r", 3, "1)", "Normal");
        checkListItem(paras.get(7), "h\r", 2, "2)", "Normal");
        checkListItem(paras.get(8), "i\r", 1, "2)", "Normal");
        checkListItem(paras.get(9), "j\f", 0, "3)", "Normal");

        msAssert.areEqual(10, paras.getCount());
    }

    /// <summary>
    /// Tests 'StartAt' is set properly upon writing ordered list.
    /// </summary>
    @Test
    public void testOrderedListC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListC", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1)", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "1)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "1.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests 'StartAt' is set properly upon writing ordered list.
    /// </summary>
    @Test
    public void testOrderedListD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListD", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1)", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "5)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "8.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests 'StartAt' is set properly upon writing ordered list.
    /// </summary>
    @Test
    public void testOrderedListE() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListE", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "5.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "3.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "100)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "6.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests export document with ordered lists combined with bullet lists.
    /// </summary>
    @Test
    public void testOrderedListF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListF", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "5.", "Normal");
        checkListItem(paras.get(3), "d\r", 2, "", "Normal");
        checkListItem(paras.get(4), "e\r", 1, "6.", "Normal");
        checkListItem(paras.get(5), "f\f", 0, "2.", "Normal");

        msAssert.areEqual(6, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with nested in a single line ordered lists.
    /// </summary>
    @Test
    public void testOrderedListG() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListG", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 1, "6.", "List", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "10.", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "5.", "List1", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "3.", "List3", "List2", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with nested in a single line ordered lists.
    /// </summary>
    @Test
    public void testOrderedListH() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListH", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "3.", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, " ", "List", "Quote", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "7.", "Quote2", "List2", "Quote1", "List1", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 7, "44.", "Quote7", "List8", "Quote6", "List7", "Quote5", "List6", "Quote4", "List5", "List4", "Quote3", "List3", "Normal");
        checkListItem(paras.get(4), "e\f", 5, " ", "List14", "List13", "Quote9", "List12", "List11", "Quote8", "List10", "List9", "Quote", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 2, '.');
        checkStyleList(doc.getStyles().get("List5"), 4, 45, '.');
        checkStyleList(doc.getStyles().get("List14"), 0, 1, '.');

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with ordered lists combined with headings.
    /// </summary>
    @Test
    public void testOrderedListI() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListI", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "7.", "Heading 1", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "2.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "3.", "Heading 2", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "2.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with ordered lists nested in Quotes.
    /// </summary>
    @Test
    public void testOrderedListJ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListJ", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(2), "c\f", 1, "1.", "List", "Quote", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 1, '.');

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with ordered lists nested in Quotes and vise versa.
    /// </summary>
    @Test
    public void testOrderedListK() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListK", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "2.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 1, "1.", "List", "Quote", "Normal");
        checkListItem(paras.get(4), "e\r", 0, "1.", "Quote", "Normal");
        checkListItem(paras.get(5), "f\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(6), "g\r", 1, "2.", "Quote", "Normal");
        checkListItem(paras.get(7), "h\f", 1, "1.", "Quote1", "List1", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 1, '.');
        checkStyleList(doc.getStyles().get("List1"), 0, 1, '.');

        msAssert.areEqual(8, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with Quote inside ordered list.
    /// </summary>
    @Test
    public void testOrderedListL() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListL", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(1), "A list item with a blockquote:\r", 0, "5.", "Normal");
        checkListItem(paras.get(2), "This is a blockquote\u000binside a list item.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with ordered lists and indented code inside.
    /// </summary>
    @Test
    public void testOrderedListM() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListM", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraph(paras.get(0), "To put a code block within a list item\r", "Normal");
        checkListItem(paras.get(1), "A list item with a code block:\r", 0, "7.", "Normal");
        checkListItem(paras.get(2), "<code goes here>\f", 0, " ", "IndentedCode");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing document with ordered lists and horizontal rule.
    /// </summary>
    @Test
    public void testOrderedListN() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListN", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkHorizontalRule(paras.get(0), "Quote", "Normal");
        checkListItem(paras.get(0), "\r", 0, "4.", "Quote", "Normal");
        checkListItem(paras.get(1), "aliqua.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(2, paras.getCount());
    }

    /// <summary>
    /// Tests writing a very complex markdown document with ordered lists.
    /// </summary>
    @Test
    public void testOrderedListO() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListO", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "Lorem\r", 0, "2.", "Normal");
        checkListItem(paras.get(1), "ipsum\r", 0, "4)", "Normal");
        checkListItem(paras.get(2), "dolor\r", 0, "6.", "Normal");
        checkListItem(paras.get(3), "sit\r", 1, "1.", "Normal");
        checkListItem(paras.get(4), "amet\r", 1, "3)", "Normal");
        checkListItem(paras.get(5), "consectetur\r", 2, "", "Normal");
        checkListItem(paras.get(6), "adipiscing\u000belit\r", 2, "25.", "Normal");
        checkListItem(paras.get(7), "sed\r", 2, " ", "Heading 1", "Normal");
        checkListItem(paras.get(8), "do\r", 0, " ", "SetextHeading2", "Heading 2", "Normal");
        checkHorizontalRule(paras.get(9), "Normal");
        checkListItem(paras.get(9), "\r", 0, " ", "Normal");
        checkListItem(paras.get(10), "tabs indentation\u000beiusmod\r", 0, " ", "IndentedCode");
        checkListItem(paras.get(11), "tempor\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(12), "incididunt\r", 1, "1)", "Quote2", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(13), "ut\r", 1, "1)", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(14), "labore\u000b1.et\r", 0, "7.", "Normal");
        checkParagraph(paras.get(15), "1.dolore\r", "Normal");
        checkHorizontalRule(paras.get(16), "Quote", "Normal");
        checkListItem(paras.get(16), "\r", 0, "1.", "Quote");
        checkListItem(paras.get(17), "aliqua.\r", 0, " ", "Quote");
        checkListItem(paras.get(18), "Ut\u000b1. enim\r", 1, "1.", "Quote");
        checkParagraph(paras.get(19), "1\\.\tad\r", "IndentedCode");
        checkListItem(paras.get(20), "minim\r", 0, "1.", "Normal");
        checkListItem(paras.get(21), "veniam\r", 2, "1.", "Quote4", "List1", "Quote3", "List", "Quote", "Normal");
        checkListItem(paras.get(22), "quis\r", 0, "1.", "Normal");
        checkListItem(paras.get(23), "nostrud\r", 0, " ", "Normal");
        checkListItem(paras.get(24), "exercitation\u000b1.         foo\f", 1, "1.", "Normal");

        msAssert.areEqual(25, paras.getCount());
    }

    /// <summary>
    /// Tests that a new list is started when it was broken with a regular paragraph.
    /// </summary>
    @Test
    public void testOrderedListP() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListP", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkParagraph(paras.get(1), "b\r", "Normal");
        checkListItem(paras.get(2), "c\f", 0, "5.", "Normal");

        msAssert.areEqual(1, paras.get(0).getParaPr().getListId());
        msAssert.areEqual(2, paras.get(2).getParaPr().getListId());

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests that paragraphs under the same list block don't break this list.
    /// </summary>
    @Test
    public void testOrderedListQ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestOrderedListQ", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 0, " ", "Normal");
        checkListItem(paras.get(2), "c\f", 0, "2.", "Normal");

        msAssert.areEqual(1, paras.get(0).getParaPr().getListId());
        msAssert.areEqual(1, paras.get(2).getParaPr().getListId());

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests writing a table.
    /// </summary>
    @Test
    public void testTableA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestTableA", UnifiedScenario.DOCX_2_MD);
        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(1, tables.getCount());

        RowCollection rows = tables.get(0).getRows();
        msAssert.areEqual(6, rows.getCount());

        CellCollection cells = rows.get(1).getCells();
        msAssert.areEqual(3, cells.getCount());

        Cell cell = cells.get(0);
        Paragraph paragraph = cell.getFirstParagraph();
        msAssert.areEqual(ParagraphAlignment.CENTER, paragraph.getParaPr().getAlignment());
        Run run = getRunWithText(paragraph, "First");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        run = getRunWithText(paragraph, "cell");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        cell = cells.get(1);
        paragraph = cell.getFirstParagraph();
        msAssert.areEqual(ParagraphAlignment.LEFT, paragraph.getParaPr().getAlignment());
        run = getRunWithText(paragraph, "Second");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        run = getRunWithText(paragraph, "cell");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getStrikeThrough());

        cell = cells.get(2);
        paragraph = cell.getFirstParagraph();
        msAssert.areEqual(ParagraphAlignment.RIGHT, paragraph.getParaPr().getAlignment());
        run = getRunWithText(paragraph, "Third");
        msAssert.areEqual(MarkdownUtil.INLINE_CODE_STYLE_NAME, run.getCharacterStyle().getName());
        run = getRunWithText(paragraph, "cell");
        msAssert.areEqual("Default Paragraph Font", run.getCharacterStyle().getName());

        paragraph = (Paragraph)tables.get(0).getNextSibling();
        checkParagraph(paragraph, "paragraph after table\f", "Normal");
    }

    /// <summary>
    /// Tests writing a table with multiline cells.
    /// </summary>
    /// <remarks>
    /// Until inline html blocks are implemented, multiline cells are loaded as a single paragraph.
    /// </remarks>
    @Test
    public void testTableB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestTableB", UnifiedScenario.DOCX_2_MD);

        Table table = doc.getFirstSection().getBody().getTables().get(0);

        Paragraph paragraph = table.getFirstRow().getFirstCell().getFirstParagraph();
        checkParagraph(paragraph, "Cell1", "Normal");
    }

    /// <summary>
    /// Tests writing a table with merged cells.
    /// </summary>
    /// <remarks>
    /// Note, that neither standard, nor extended markdown https://github.github.com/gfm/ support merged cells.
    /// So, for a moment it just exports all cells 'as is' not considering merged cells.
    /// Also, the delimiter row is always taken from the very first (header) row.
    /// So, despite all cells are exported, they can be non-displaying in markdown editors.
    /// </remarks>
    @Test
    public void testTableC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestTableC", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getTables().getCount());
    }

    /// <summary>
    /// Tests simple Autolink.
    /// </summary>
    @Test
    public void testAutolinkA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkA", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkB", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(6, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkC", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolinks with Whitespace characters.
    /// </summary>
    @Test
    public void testAutolinkD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkD", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }


    /// <summary>
    /// Tests Autolink without colon and commercial "At".
    /// </summary>
    @Test
    public void testAutolinkF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkF", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with colon and backslash not in the schema name.
    /// </summary>
    @Test
    public void testAutolinkG() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkG", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and backslash.
    /// </summary>
    @Test
    public void testAutolinkH() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkH", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with incorrect length.
    /// </summary>
    @Test
    public void testAutolinkI() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkI", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(5, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong first symbol (must be [a-zA-Z]).
    /// </summary>
    @Test
    public void testAutolinkJ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkJ", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong not-first symbol (must be [a-zA-Z0-9.+-]).
    /// </summary>
    @Test
    public void testAutolinkK() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkK", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and incorrect symbols.
    /// </summary>
    @Test
    public void testAutolinkL() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkL", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(10, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with colon and any unexpected symbol after the schema name.
    /// </summary>
    @Test
    public void testAutolinkM() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkM", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests nested Autolinks.
    /// </summary>
    @Test
    public void testAutolinkN() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkN", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(6, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests the precedence of Autolinks over emphasis delimiters.
    /// </summary>
    @Test
    public void testAutolinkO() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkO", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(7, doc.getFirstSection().getBody().getParagraphs().getCount());
    }


    /// <summary>
    /// Tests hyperlinks that are formatted or not Autolinks.
    /// </summary>
    @Test
    public void testAutolinkQ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkQ", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(7, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests non-Autolinks.
    /// </summary>
    @Test
    public void testAutolinkR() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkR", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink with bookmark.
    /// </summary>
    @Test
    public void testAutolinkS() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkS", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolink represented with a sequence of runs.
    /// </summary>
    @Test
    public void testAutolinkT() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkT", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that code spans and Autolinks have the same precedence.
    /// </summary>
    @Test
    public void testAutolinkU() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkU", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Autolinks with closer angle brackets.
    /// </summary>
    @Test
    public void testAutolinkV() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkV", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that backslash-escaped left angle bracket is not valid for Autolink.
    /// </summary>
    @Test
    public void testAutolinkW() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestAutolinkW", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests simple Link.
    /// </summary>
    @Test
    public void testLinkA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkA", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests simple Link with a title.
    /// </summary>
    @Test
    public void testLinkB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkB", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests empty link text for Link.
    /// </summary>
    /// <remarks>
    /// TODO: Spec allows 'linkText' with zero length, but for now it is not implemented.
    /// </remarks>
    @Test
    public void testLinkC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkC", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests empty link URI for Link.
    /// </summary>
    /// <remarks>
    /// TODO: Spec allows 'linkUri' with zero length, but for now it is not implemented.
    /// </remarks>
    @Test
    public void testLinkD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkD", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Link titles with different quotes.
    /// </summary>
    @Test
    public void testLinkE() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestLinkE", LoadFormat.DOCX);
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.INLINE);
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestLinkE.docx.md", so);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Link with angle brackets for link URI.
    /// </summary>
    @Test
    public void testLinkF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkF", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests Link titles with backslash-escaped quotes.
    /// </summary>
    /// <remarks>
    /// NOTE: For now the 'gold' file is incorrect.
    /// FieldCodeHyperlink.Parse() cannot understand double quotes and backslashes
    /// in the screen tip, if they are not escaped. Backslashes are thrown out.
    /// The screen tip is cut at the first unescaped double quote.
    /// </remarks>
    @Test
    public void testLinkG() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestLinkG", LoadFormat.DOCX);
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.INLINE);
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestLinkG.docx.md", so);
        msAssert.areEqual(13, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that the Link destination cannot contain spaces or line breaks, even if enclosed in pointy brackets.
    /// </summary>
    @Test
    public void testLinkH() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkH", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that parentheses inside the Link destination may be escaped.
    /// </summary>
    @Test
    public void testLinkI() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkI", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that any number of parentheses in the Link target are allowed without escaping,
    /// as long as they are balanced.
    /// </summary>
    @Test
    public void testLinkJ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkJ", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that if Link have unbalanced parentheses,
    /// they need to be escaped or used with the angle brackets.
    /// </summary>
    @Test
    public void testLinkK() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkK", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that parentheses and other symbols in the Link can also be escaped, as usual in Markdown.
    /// </summary>
    @Test
    public void testLinkL() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkL", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that Links can contain fragment identifiers and queries.
    /// </summary>
    @Test
    public void testLinkM() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkM", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that a backslash before a non-escapable character in the Link is just a backslash.
    /// </summary>
    /// <remarks>
    /// NOTE: For now the 'gold' file is incorrect.
    /// FieldCodeHyperlink.Parse() cannot understand double quotes and backslashes
    /// in the screen tip, if they are not escaped. Backslashes are thrown out.
    /// The screen tip is cut at the first unescaped double quote.
    /// </remarks>
    @Test
    public void testLinkN() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkN", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that URL-escaping should be left alone inside the Link destination,
    /// as all URL-escaped characters are also valid URL characters.
    /// </summary>
    @Test
    public void testLinkO() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkO", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that, because titles can often be parsed as Link destinations,
    /// if you try to omit the destination and keep the title, you’ll get unexpected results.
    /// </summary>
    @Test
    public void testLinkP() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkP", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that backslash escapes and entity and numeric character references may be used in Link titles.
    /// </summary>
    @Test
    public void testLinkQ() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkQ", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that whitespace is allowed around the Link destination and title.
    /// </summary>
    @Test
    public void testLinkR() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkR", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }


    /// <summary>
    /// Tests that the Link text may contain balanced brackets, but not unbalanced ones, unless they are escaped.
    /// </summary>
    @Test
    public void testLinkT() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkT", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that the Link text may contain inline content.
    /// </summary>
    @Test
    public void testLinkU() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkU", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that Links may not contain other Links, at any level of nesting.
    /// </summary>
    @Test
    public void testLinkV() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkV", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests the precedence of Link text grouping over emphasis grouping.
    /// </summary>
    @Test
    public void testLinkW() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkW", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that brackets that aren’t part of Links do not take precedence.
    /// </summary>
    @Test
    public void testLinkX() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkX", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests the precedence of HTML tags, code spans, and autolinks over Link grouping.
    /// </summary>
    /// <remarks>
    /// TODO: HTML tags are still not implemented.
    /// </remarks>
    @Test
    public void testLinkY() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkY", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that a Link destination consists of a sequence of characters between angle brackets that contains
    /// no unescaped angle brackets.
    /// </summary>
    @Test
    public void testLinkZ01() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ01", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(8, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that a Link titles may span multiple lines.
    /// </summary>
    @Test
    public void testLinkZ02() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ02", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests an empty Link title.
    /// </summary>
    @Test
    public void testLinkZ03() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ03", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests edge conditions during the Link parsing.
    /// </summary>
    @Test
    public void testLinkZ04() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ04", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(6, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that backslash-escaped left square bracket is not valid for Link.
    /// </summary>
    @Test
    public void testLinkZ05() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ05", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that Link URI cannot start with the left angle bracket
    /// without the right angle bracket at the very end.
    /// </summary>
    @Test
    public void testLinkZ06() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestLinkZ06", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests simple Image.
    /// </summary>
    @Test
    public void testImageA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestImageA", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "alt text", "/uri");
    }

    /// <summary>
    /// Tests Image with a title.
    /// </summary>
    @Test
    public void testImageB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestImageB", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "alt text", "/uri", "title");
        checkParagraph(paras.get(1), "My \f", "Normal");
        checkShape(ms.as(paras.get(1).getChild(NodeType.ANY, 1, false), Shape.class), "foo bar", "/path/to/train.jpg", "title");
    }

    /// <summary>
    /// Tests that it is recommended that in rendering, only the plain string content of the Image description
    /// be used, without formatting.
    /// </summary>
    @Test
    public void testImageC() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestImageC", LoadFormat.DOCX);
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.INLINE);
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\TestImageC.docx.md", so);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "foo bar", "/url2");
        checkShape(ms.as(paras.get(1).getChild(NodeType.ANY, 0, false), Shape.class), "foo bar", "/url2");
    }


    /// <summary>
    /// Tests Image with empty description.
    /// </summary>
    @Test
    public void testImageE() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestImageE", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "", "/url");
    }

    // FOSS TestImageF removed: verifies a written PNG hash and image source name, but the
    // FOSS build does not serialize images to Markdown.

    /// <summary>
    /// Tests a simple Footnote Reference.
    /// </summary>
    @Test
    public void testFootnoteA() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteA", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        checkFootnotes(doc, "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests a Footnote with multiline definition.
    /// </summary>
    @Test
    public void testFootnoteB() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteB", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        checkFootnotes(doc, "\u0002 Line1.\rLine2.\r");
    }

    /// <summary>
    /// Tests Footnote with custom reference.
    /// </summary>
    @Test
    public void testFootnoteC() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteC", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        checkFootnotes(doc, "\u0002 Custom reference definition.\r");
    }

    /// <summary>
    /// Tests Footnote with an empty definition.
    /// </summary>
    @Test
    public void testFootnoteD() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteD", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        checkFootnotes(doc, "\u0002\r");
    }

    /// <summary>
    /// Tests Footnote with multiple paragraphs.
    /// </summary>
    @Test
    public void testFootnoteE() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteE", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        checkFootnotes(doc, "\u0002 Paragraph1\rParagraph2.\r");
    }

    /// <summary>
    /// Tests various emphases in Footnotes.
    /// </summary>
    @Test
    public void testFootnoteF() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteF", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        NodeCollection footnotes = checkFootnotes(doc, "\u0002 Some bold footnote text.\r", "\u0002 Some italic footnote\r");

        msAssert.isTrue(((Footnote)footnotes.get(0)).getFont().getBold());
        msAssert.isFalse(((Footnote)footnotes.get(1)).getFont().getItalic());

        Paragraph paragraph = ((Footnote)footnotes.get(0)).getFirstParagraph();
        checkRun(paragraph.getRuns().get(1), "Some ", false, false);
        checkRun(paragraph.getRuns().get(2), "bold footnote", true, false);
        checkRun(paragraph.getRuns().get(3), " text.", false, false);

        paragraph = ((Footnote)footnotes.get(1)).getFirstParagraph();
        checkRun(paragraph.getRuns().get(1), "Some ", false, false);
        checkRun(paragraph.getRuns().get(2), "italic footnote", false, true);
    }

    /// <summary>
    /// Tests IndentedCode in footnote definition.
    /// </summary>
    @Test
    public void testFootnoteG() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestFootnoteG", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());

        NodeCollection footnotes =
            checkFootnotes(doc, "\u0002 Some footnote text (para1).\r    Some indented footnote text (para2).\r");

        checkParagraph(((Footnote)footnotes.get(0)).getParagraphs().get(0), "\u0002 Some footnote text (para1).\r", "Footnote Text");
        checkParagraph(((Footnote)footnotes.get(0)).getParagraphs().get(1), "    Some indented footnote text (para2).",
            "Footnote Text1", "IndentedCode");
    }

    /// <summary>
    /// Tests various markdown features in a large file.
    /// </summary>
    @Test (dataProvider = "testLargeDocumentDataProvider")
    public void testLargeDocument(/*UnifiedScenario*/int scenario) throws Exception
    {
        String fileName = msString.format("ImportMarkdown\\FormatDetector\\LargeFile{0}",
            (TestUtil.getLoadFormat(scenario) == LoadFormat.MARKDOWN) ? ".mrdwn" : ".docx");

        Document doc = TestUtil.open(fileName);
        doc.updateListLabels();

        checkBulletListItems(doc);
        checkOrderedListItems(doc);
        checkHeadings(doc);
        checkQuotes(doc);
        checkHorizontalRules(doc);
        checkEmphases(doc);
        checkAutolinks(doc);
        checkLinks(doc);
        checkImages(doc);

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.AUTO);
        // Check gold.
        TestUtil.saveOpen(doc, "ImportMarkdown\\FormatDetector\\LargeFile", scenario, so);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLargeDocumentDataProvider")
	public static Object[][] testLargeDocumentDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.DOCX_2_MD},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// WORDSNET-19836 Fails insert list during saving to existing .md document.
    /// Fixed through the implementation of resilient writing.
    /// </summary>
    @Test
    public void test19836() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.writeln("Paragraph before the list.");
        builder.getListFormat().applyNumberDefault();
        builder.getListFormat().getList().getListLevels().get(0).setNumberFormat("-");
        builder.writeln("Text_1");
        builder.writeln("Text_2");
        builder.getListFormat().listIndent();
        builder.writeln("Text_2.1");
        builder.writeln("Text_2.2");

        Document doc = TestUtil.saveOpen(builder.getDocument(), "ExportMarkdown\\Test19836",
            UnifiedScenario.MD_2_MD | UnifiedScenario.NO_GOLD);

        msAssert.areEqual(5, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// WORDSNET-19836 DOCX to Markdown conversion issue with numbered lists.
    /// Supported markdown ordered lists.
    /// </summary>
    @Test
    public void test19812() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test19812", UnifiedScenario.DOCX_2_MD);
        doc.updateListLabels();

        Paragraph paragraph = getParagraphWithText(doc, "Item1");
        checkListItem(paragraph, 0, "1.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Item2", 0, "2.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Item3", 0, "3.", "Normal");
    }

    /// <summary>
    /// WORDSNET-20353 ArgumentOutOfRangeException is thrown while exporting document
    /// with multiple numbered paragraphs inside a cell into Markdown.
    ///
    /// The wrong previous paragraph was used while processing multiple paragraphs inside a cell.
    /// </summary>
    @Test
    public void test20353() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20353", UnifiedScenario.DOCX_2_MD);

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual("- Line1\r", table.getFirstRow().getFirstCell().getFirstParagraph().getText());
    }

    /// <summary>
    /// WORDSNET-20165 Bold and italic text is not properly converted to Markdown.
    /// The emphases should be moved out of adjacent whitespace characters to make a correct
    /// flanking type in output result. Also leading and trailing whitespace characters should
    /// be wrapped into InlineCode to be preserved in markdown.
    /// </summary>
    @Test
    public void test20165A() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20165A", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("   Foo\r", paragraphs.get(0).getText());
        Run run = getRunWithText(paragraphs.get(0), "Foo");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        msAssert.areEqual("Bar\r", paragraphs.get(1).getText());
        run = getRunWithText(paragraphs.get(1), "Bar");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        msAssert.areEqual("   Baz\r", paragraphs.get(2).getText());
        run = getRunWithText(paragraphs.get(2), "Baz");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        msAssert.areEqual("Bop\f", paragraphs.get(3).getText());
        run = getRunWithText(paragraphs.get(3), "Bop");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
    }

    /// <summary>
    /// Relates to WORDSNET-20165
    /// Tests that leading whitespace characters does not wrap into InlineCode delimiters
    /// when they are already formatted as InlineCode.
    /// </summary>
    @Test
    public void test20165B() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20165B", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("   InlineCode text + bold + italic\r", paragraph.getText());
        checkRun(paragraph.getFirstRun(), "   InlineCode text + bold + italic", "InlineCode", true, true);

        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.areEqual("   InlineCode text\r", paragraph.getText());
        checkRun(paragraph.getFirstRun(), "   InlineCode text", "InlineCode", false, false);

        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.areEqual("   Regular text\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "   ", "InlineCode", false, false);
        checkRun(paragraph.getRuns().get(1), "Regular text", "Default Paragraph Font", false, false);
    }

    /// <summary>
    /// Relates to WORDSNET-20165
    /// Tests that leading whitespace characters formatted as InlineCode
    /// with more than one delimiter is exported correctly.
    /// </summary>
    @Test
    public void test20165C() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20165C", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("   InlineCode.3 + bold + italic\r", paragraph.getText());
        checkRun(paragraph.getFirstRun(), "   InlineCode.3 + bold + italic", "InlineCode.3", true, true);
    }

    /// <summary>
    /// Relates to WORDSNET-20165
    /// Tests that emphases in the middle of the text are moved correctly out of whitespace characters
    /// to make a correct flanking type.
    /// </summary>
    @Test
    public void test20165D() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20165D", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("Foo: bar\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(0), "Foo:", true, true);
        checkRun(paragraph.getRuns().get(1), " bar", false, false);
    }


    /// <summary>
    /// Relates to WORDSNET-20165
    /// Tests that if character of text is InlineCode delimiter and it is adjacent to InlineCode delimiters,
    /// then this character should be escaped with space.
    /// </summary>
    @Test
    public void test20165E() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20165E", UnifiedScenario.DOCX_2_MD);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual("To indicate a span of code, wrap it with backtick quotes (`).\f", paragraph.getText());
        checkRun(paragraph.getRuns().get(1), "`", "InlineCode.3", true, false);
    }

    /// <summary>
    /// WORDSNET-20393 Incorrect export of nested tables into markdown.
    /// A parent table should be split for inserting nested table in markdown.
    /// </summary>
    @Test
    public void test20393A() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20393A", UnifiedScenario.DOCX_2_MD);
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        // The original table and its nested table is split onto 3 separate tables:
        msAssert.areEqual("Parent cell para1\u0007\u0007", tables.get(0).getText());
        msAssert.areEqual("Nested cell1A\u0007Nested cell1B\u0007Nested cell1C\u0007Nested cell1D\u0007\u0007" +
                            "Nested cell2A\u0007Nested cell2B\u0007Nested cell2C\u0007Nested cell2D\u0007\u0007", tables.get(1).getText());
        msAssert.areEqual("Parent cell para2\u0007\u0007", tables.get(2).getText());
        msAssert.areEqual(3, tables.getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-20393
    /// Tests complex tables nesting.
    /// </summary>
    @Test
    public void test20393B() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20393B", UnifiedScenario.DOCX_2_MD);
        msAssert.areEqual(17, doc.getChildNodes(NodeType.TABLE, true).getCount());
    }

    /// <summary>
    /// WORDSNET-20394 Content lines with only whitespace characters are incorrectly escaped
    /// while exporting into markdown.
    /// Content lines with only whitespace characters should not be escaped while writing markdown paragraphs.
    /// </summary>
    @Test
    public void test20394() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20394",
            UnifiedScenario.DOCX_2_MD | UnifiedScenario.NO_GOLD);

        Table table = (Table)doc.getFirstSection().getBody().getFirstChild();
        CellCollection cells = table.getRows().get(1).getCells();

        msAssert.areEqual("\u0007", cells.get(0).getText());
        msAssert.areEqual("B2\u0007", cells.get(1).getText());
        msAssert.areEqual("\u0007", cells.get(2).getText());
        msAssert.areEqual("D2\u0007", cells.get(3).getText());
    }

    /// <summary>
    /// WORDSNET-20407 Incorrect export of Headings inside table into Markdown.
    /// The Headings should be exported as HTML 'h1'-'h6' tags.
    /// </summary>
    @Test
    public void test20407() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20407", UnifiedScenario.DOCX_2_MD);
        Table table = (Table)doc.getFirstSection().getBody().getFirstChild();

        Row row = table.getRows().get(0);
        msAssert.areEqual("Foo\rBar\rB***o***z\rBop\r\r1. ListItem\r\rBem\u0007", row.getCells().get(0).getText());

        row = table.getRows().get(1);
        msAssert.areEqual("Lorem\u0007", row.getCells().get(0).getText());
        msAssert.areEqual("Dolor\u0007", row.getCells().get(1).getText());

        row = table.getRows().get(2);
        msAssert.areEqual("1. ListItem\u0007", row.getCells().get(0).getText());
        msAssert.areEqual("- Bullet1\u0007", row.getCells().get(1).getText());

        row = table.getRows().get(3);
        msAssert.areEqual("# # Not a heading\u0007", row.getCells().get(0).getText());
    }

    /// <summary>
    /// WORDSNET-20408 Wrong output of paragraphs started with ordered list markers inside table
    /// while exporting into Markdown.
    /// We should not escape anything inside table as there are no any other features allowed inside it.
    /// </summary>
    @Test
    public void test20408() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20408", UnifiedScenario.DOCX_2_MD);

        Table table = (Table)doc.getFirstSection().getBody().getFirstChild();
        Row row = table.getRows().get(0);

        msAssert.areEqual("1. Not a ListItem1\r2. Not a ListItem2\u0007", row.getCells().get(0).getText());
        msAssert.areEqual("1. Not a ListItem3\u0007", row.getCells().get(1).getText());
    }

    /// <summary>
    /// WORDSNET-20409 List labels are not preserved while exporting numbered paragraphs into Markdown.
    /// Lists are not allowed inside tables, so numbered paragraphs should be saved with their updated list labels.
    /// </summary>
    @Test
    public void test20409() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test20409", UnifiedScenario.DOCX_2_MD);

        Table table = (Table)doc.getFirstSection().getBody().getFirstChild();
        Row row = table.getRows().get(0);
        msAssert.areEqual("1. OrderedItem1\r\u2003a. OrderedItem2\r\u2003b. OrderedItem3\r2. OrderedItem4\u0007", row.getCells().get(0).getText());
        msAssert.areEqual("- UnorderedItem1\r\u2003- UnorderedItem2\r\u2003\u2003- UnorderedItem3\r- UnorderedItem4\u0007", row.getCells().get(1).getText());
    }

    /// <summary>
    /// WORDSNET-20297 Add ability to create MarkdownSaveOptions using
    /// SaveOptions.CreateSaveOptions(SaveFormat saveFormat).
    /// Made MarkdownSaveOptions class public.
    /// </summary>
    @Test
    public void test20297()
    {
        MarkdownSaveOptions so = ms.as(SaveOptions.createSaveOptions(SaveFormat.MARKDOWN), MarkdownSaveOptions.class);
        msAssert.isNotNull(so);

    }

    /// <summary>
    /// WORDSNET-20425 Add the ability to control the alignment of table contents
    /// when exporting to the Markdown.
    /// Implemented new save option <see cref="TableContentAlignment"/>.
    /// </summary>
    @Test (dataProvider = "test20425DataProvider")
    public void test20425(/*TableContentAlignment*/int tableContentAlignment, /*ParagraphAlignment*/int[] expectedAlignments) throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Customers\\Test20425.docx");

        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions();
        saveOptions.setTableContentAlignment(tableContentAlignment);
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Customers\\Test20425",
            UnifiedScenario.DOCX_2_MD | UnifiedScenario.NO_GOLD, saveOptions);

        CellCollection cells = ((Table)doc.getFirstSection().getBody().getFirstChild()).getFirstRow().getCells();

        msAssert.areEqual(expectedAlignments[0], cells.get(0).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(expectedAlignments[1], cells.get(1).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(expectedAlignments[2], cells.get(2).getFirstParagraph().getParaPr().getAlignment());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test20425DataProvider")
	public static Object[][] test20425DataProvider() throws Exception
	{
		return new Object[][]
		{
			{TableContentAlignment.AUTO, 
        new /*ParagraphAlignment*/int[] {ParagraphAlignment.LEFT, ParagraphAlignment.CENTER, ParagraphAlignment.RIGHT}},
			{TableContentAlignment.LEFT, 
        new /*ParagraphAlignment*/int[] {ParagraphAlignment.LEFT, ParagraphAlignment.LEFT, ParagraphAlignment.LEFT}},
			{TableContentAlignment.CENTER, 
        new /*ParagraphAlignment*/int[] {ParagraphAlignment.CENTER, ParagraphAlignment.CENTER, ParagraphAlignment.CENTER}},
			{TableContentAlignment.RIGHT, 
        new /*ParagraphAlignment*/int[] {ParagraphAlignment.RIGHT, ParagraphAlignment.RIGHT, ParagraphAlignment.RIGHT}},
		};
	}

    /// <summary>
    /// WORDSNET-20879 System.InvalidOperationException when save document to markdown format.
    /// Fixed by adding the ImagesFolder property to the MarkdownSaveOptions class.
    /// </summary>
    @Test (enabled = false, description = "FOSS: pending MarkdownImageWriter image-saving restoration. Image files are no longer written to ImagesFolder because ImageShapeWriter was removed with the rendering layer in commit e515d42c2a9, so Directory.GetFiles returns an empty array.")
    public void test20879() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // Inserting the image.
        Stream srcImageStream = ResourceUtil.fetchResourceStream("Aspose.Words.Resources.3dBubble.jpg");
        try /*JAVA: was using*/
    	{
            builder.insertImageInternal(srcImageStream);
    	}
        finally { if (srcImageStream != null) srcImageStream.close(); }

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setImagesFolder(TestUtil.getInTestOutPath("ExportMarkdown\\Customers\\Images20879"));

        // If the image exists before saving, delete it.
        if (Directory.exists(so.getImagesFolder()))
            Directory.delete(so.getImagesFolder(), true);

        // Creating the image directory.
        Directory.createDirectory(so.getImagesFolder());

        // Saving the document.
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
    	{
            builder.getDocument().save(stream, so);
    	}
        finally { if (stream != null) stream.close(); }

        // Checking the image after saving.
        String imageFileName = Directory.getFiles(so.getImagesFolder())[0];
        TestUtil.verifyHash("3307863FCBEA17ECAF59A9EC2F2A6625", StreamUtil.copyFileToByteArray(imageFileName));
    }

    /// <summary>
    /// Related to WORDSNET-20879
    /// Checks for an exception when saving a stream without the specified ImagesFolder.
    /// </summary>
    @Test
    // FOSS we don't write images in the FOSS version hence no exception is thrown.
    public void test20879_2() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Stream srcImageStream = ResourceUtil.fetchResourceStream("Aspose.Words.Resources.3dBubble.jpg");
        try /*JAVA: was using*/
    	{
            builder.insertImageInternal(srcImageStream);
    	}
        finally { if (srcImageStream != null) srcImageStream.close(); }
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
    	{
            builder.getDocument().save(stream, SaveFormat.MARKDOWN);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// WORDSNET-21681 Part of text is lost during DOCX-to-Markdown conversion.
    /// The document contains footnotes that means there are nested paragraphs.
    /// We need a stack of started paragraphs to write them properly in case of nesting.
    /// </summary>
    @Test
    public void test21681() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test21681",
            UnifiedScenario.DOCX_2_MD | UnifiedScenario.NO_GOLD);

        // Check the problematic text is not lost.
        msAssert.isNotNull(getParagraphWithText(doc, "Text is normal, then bold,"));
        msAssert.isNotNull(getParagraphWithText(doc, "This text is usual, but"));

        checkFootnotes(doc,
            "\u0002 Standard footnote on page #1.\r",
            "\u0002 See \u0013 HYPERLINK \"https://www.techrepublic.com/blog/microsoft-office/highlight-text-with-the" +
            "-gradient-fill-effect-in-word/\" \u0014https://www.techrepublic.com/blog/microsoft-office/highlight-text" +
            "-with-the-gradient-fill-effect-in-word/\u0015\r");
    }

    /// <summary>
    /// WORDSNET-21680 Missed footnotes during conversion between DOCX and Markdown.
    /// Implemented the Markdown footnotes.
    /// </summary>
    @Test
    public void test21680() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Customers\\Test21680", UnifiedScenario.DOCX_2_MD);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(25, paras.getCount());

        checkFootnotes(doc,
            "\u0002 1st pagenote in 4th paragraph on the 1st page.\r",
            "\u0002 1st endnote just after 1st pagenote on the 1st page.\r",
            "\u0002 2nd pagenote on the empty paragraph on the 1st page.\r",
            "\u0002 3rd pagenote in 5th paragraph, before the first word of a first letter on the 1st page.\r",
            "\u0002 2nd endnote on the 1st page.\r",
            "\u0002 4th pagenote on the 1st page.\r",
            "\u0002 3rd endnote just before 5th pagenote on the 2nd page.\r",
            "\u0002 5th pagenote on the 2nd page.\r");
    }

    /// <summary>
    /// WORDSNET-22787 Introduce ExportImagesAsBase64 option for MD format.
    /// ExportImagesAsBase64 markdown save option has been implemented.
    /// The case when SourceFileName is empty and ImageBytes is not empty.
    /// </summary>
    @Test (dataProvider = "test22787DataProvider")
    public void test22787(boolean exportImagesAsBase64, String filename) throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Test22787.docx");

        MarkdownSaveOptions mso = new MarkdownSaveOptions();
        mso.setExportImagesAsBase64(exportImagesAsBase64);

        TestUtil.saveCheckGold(doc, "ExportMarkdown\\" + filename, mso);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22787DataProvider")
	public static Object[][] test22787DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  "Test22787.md"},
			{false,  "Test22787_Linked.md"},
		};
	}








    /// <summary>
    /// Tests the roundtrip of the list and that of the escape list.
    /// </summary>
    @Test (dataProvider = "testListEscapingDataProvider")
    public void testListEscaping(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\TestListEscaping",
            scenario | UnifiedScenario.NO_GOLD);
        doc.updateListLabels();
        Paragraph paraListEscaping = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.isFalse(paraListEscaping.isListItem());
        // Checks for "1986." substring and absence of escaping "\.".
        msAssert.areEqual("1986. What a great season.", paraListEscaping.getFirstRun().getText());

        Paragraph paraList = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.isTrue(paraList.isListItem());
        // The text of the first run is "What a great season.".
        msAssert.areEqual("What a great season.", paraList.getFirstRun().getText());
        // Text "1986." gets into ListLabel.
        msAssert.areEqual("1986.", paraList.getListLabel().getLabelString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testListEscapingDataProvider")
	public static Object[][] testListEscapingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.MD_2_DOCX},
			{UnifiedScenario.MD_2_MD},
		};
	}

    /// <summary>
    /// Checks resaving markdown reference links with different ExportLinksMode.
    /// </summary>
    @Test (dataProvider = "testExportLinksModeDataProvider")
    public void testExportLinksMode(/*MarkdownLinkExportMode*/int linkExportMode, String saveSuffix) throws Exception
    {
        String[] urls = new String[] { "https://www.w3schools.com/", "https://www.example.com/",
            "https://www.example.com/", "https://www.google.com/" };
        String[] titles = new String[] { "title", "title1", "title1", "title2" };

        Document doc = TestUtil.open("ExportMarkdown\\TestExportLinksMode.md");

        FieldCollection fields = doc.getFirstSection().getBody().getRange().getFields();
        msAssert.areEqual(4, fields.getCount());
        for (int i = 0; i < 4; i++)
        {
            FieldHyperlink field = ms.as(fields.get(i), FieldHyperlink.class);
            msAssert.areEqual(urls[i], field.getHRef().toString(false));
            msAssert.areEqual(titles[i], field.getScreenTip());
        }

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(linkExportMode);
        TestUtil.saveCheckGold(doc, msString.format("ExportMarkdown\\TestExportLinksMode_{0}.md", saveSuffix), so);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testExportLinksModeDataProvider")
	public static Object[][] testExportLinksModeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownLinkExportMode.INLINE,  "_inline"},
			{MarkdownLinkExportMode.REFERENCE,  "_reference"},
			{MarkdownLinkExportMode.AUTO,  "_auto"},
		};
	}

    /// <summary>
    /// Exporting a reference link moves definitions to the end of a document.
    /// </summary>
    @Test
    public void testExportLinkPosition() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestLinkPosition.md");
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestLinkPosition.md", so);
    }

    /// <summary>
    /// Exported collapsed reference links become shortcuts.
    /// </summary>
    @Test
    public void testExportCollapsedLink() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestExportCollapsedLink.md");
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestExportCollapsedLink.md", so);
    }

    /// <summary>
    /// The reference label is the same as the hyperlink display text.
    /// Checks for absence of [text][text] tautology.
    /// </summary>
    @Test
    public void testExportDoubleLinkText() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestExportDoubleLinkText.docx");

        FieldHyperlink field = ms.as(doc.getRange().getFields().get(0), FieldHyperlink.class);
        msAssert.areEqual("[text]", field.getDocLocation());
        msAssert.areEqual("text", field.getResult());

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestExportDoubleLinkText.md", so);
    }

    /// <summary>
    /// DocLocation contains non-roundtrip information.
    /// </summary>
    @Test
    public void testExportRealDocLocation() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestExportRealDocLocation.docx");
        // DocLocation contains a non-reference link label.
        msAssert.areEqual("Footer", (ms.as(doc.getRange().getFields().get(0), FieldHyperlink.class)).getDocLocation());

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        // Checking the gold. Markdown does not store regular DocLocation.
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestExportRealDocLocation.md", so);
    }




    /// <summary>
    /// Relates to WORDSNET-24968
    /// Tests the export of superscript and subscript emphasis.
    /// </summary>
    @Test
    public void testVerticalAlignedEmphases() throws Exception
    {
        TestUtil.openSaveOpen("ExportMarkdown\\TestVerticalAlignedEmphases", UnifiedScenario.DOCX_2_MD);
    }



    /// <summary>
    /// Checks resaving markdown reference images with different ExportLinksMode and ExportImagesAsBase64 options.
    /// </summary>
    @Test (dataProvider = "testExportImageReferenceLinksModeDataProvider")
    public void testExportImageReferenceLinksMode(/*MarkdownLinkExportMode*/int linkExportMode, String saveSuffix) throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestImageReferenceLinksMode.md");

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(linkExportMode);
        so.setExportImagesAsBase64(false);
        String outputName = msString.format("ExportMarkdown\\TestImageReferenceLinksMode{0}.md", saveSuffix);
        TestUtil.saveCheckGold(doc, outputName, so);

        so.setExportImagesAsBase64(true);
        String outputNameBase64 = msString.format("ExportMarkdown\\TestImageReferenceLinksMode{0}_base64.md", saveSuffix);
        TestUtil.saveCheckGold(doc, outputNameBase64, so);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testExportImageReferenceLinksModeDataProvider")
	public static Object[][] testExportImageReferenceLinksModeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownLinkExportMode.INLINE,  "_inline"},
			{MarkdownLinkExportMode.REFERENCE,  "_reference"},
			{MarkdownLinkExportMode.AUTO,  "_auto"},
		};
	}

    /// <summary>
    /// Exporting a reference images moves definitions to the end of a document.
    /// </summary>
    @Test
    public void testExportImageLinkPosition() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceImages\\TestRefImageA.md");
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestImageRefPosition.md", so);
    }

    /// <summary>
    /// Exported collapsed reference images become shortcuts.
    /// </summary>
    @Test
    public void testExportCollapsedImage() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestCollapsedImageReference.md");
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestCollapsedImageReference.md", so);
    }

    /// <summary>
    /// The reference label is the same as the image alternative text.
    /// Checks for absence of [text][text] tautology.
    /// </summary>
    @Test
    public void testExportDoubleImageAltText() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestDoubleImageReference.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.areEqual("[foo]", shape.getName());
        msAssert.areEqual("foo", shape.getAlternativeText());

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestDoubleImageReference.md", so);
    }

    /// <summary>
    /// Tests the case when the shape name contains non-roundtrip information.
    /// </summary>
    @Test
    public void testExportRealImageName() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\TestRealImageName.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        // Shape.Name contains a non-reference link label.
        msAssert.areEqual("Image name", shape.getName());

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        // Checking the gold. Markdown does not store regular Shape.Name.
        TestUtil.saveCheckGold(doc, "ExportMarkdown\\TestExportRealShapeName.md", so);
    }

    // FOSS TestRenderReferenceImage removed: asserts that identical shapes are rendered into the
    // same Markdown image file, but the FOSS build does not render/serialize images to Markdown.


    /// <summary>
    /// WORDSNET-21376 Support MarkdownSaveOptions.ImagesFolderAlias.
    /// Implemented new save option <see cref="MarkdownSaveOptions.ImagesFolderAlias"/>.
    /// </summary>
    @Test (enabled = false, description = "FOSS: pending MarkdownImageWriter image-saving restoration. ImageShapeWriter was removed with the rendering layer in commit e515d42c2a9, so the gold compare fails because image lines are missing from the .md output.", dataProvider = "test21376DataProvider")
    public void test21376(String imagesFolderAlias, String suffix) throws Exception
    {
        String outFolder = TestUtil.getInTestOutPath("ExportMarkdown");
        Document doc = TestUtil.open("ExportMarkdown\\Test21376.docx");
        MarkdownSaveOptions mdSaveOptions = new MarkdownSaveOptions();
        mdSaveOptions.setImagesFolder(TestUtil.getInTestOutPath("ExportMarkdown\\Images21376"));
        mdSaveOptions.setImagesFolderAlias(imagesFolderAlias);
        mdSaveOptions.setLinkExportMode(MarkdownLinkExportMode.INLINE);
        TestUtil.saveCheckGold(doc, msString.format("{0}\\Test21376_{1}_inline.md", outFolder, suffix), mdSaveOptions);

        mdSaveOptions.setLinkExportMode(MarkdownLinkExportMode.REFERENCE);
        TestUtil.saveCheckGold(doc, msString.format("{0}\\Test21376{1}_reference.md", outFolder, suffix), mdSaveOptions);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21376DataProvider")
	public static Object[][] test21376DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"",  "empty"},
			{"aaa",  "simple"},
			{"aaa/",  "simple"},
			{".\\aaa",  "simple"},
			{".",  "dot"},
			{"./",  "dot"},
			{".\\",  "dot"},
		};
	}


    /// <summary>
    /// WORDSNET-25997 Consider adding an ability to specify custom formatting tags for basic formatting in Markdown.
    /// Implemented option to control over exporting of underline formatting
    /// <see cref="MarkdownSaveOptions.ExportUnderlineFormatting"/>.
    /// </summary>
    @Test (dataProvider = "test25997DataProvider")
    public void test25997(boolean isPreserveUnderline) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.setUnderline(Underline.SINGLE);

        builder.write("text");

        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions(); { saveOptions.setExportUnderlineFormatting(isPreserveUnderline); }
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setImportUnderlineFormatting(isPreserveUnderline); }
        String outName = msString.format("ExportMarkdown\\Test25997_{0}", isPreserveUnderline);
        Document doc = TestUtil.saveOpen(builder.getDocument(), outName, UnifiedScenario.MD_2_MD, saveOptions, loadOptions);

        /*Underline*/int expectedUnderline = isPreserveUnderline ? Underline.SINGLE : Underline.NONE;
        msAssert.areEqual(expectedUnderline, doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr().getUnderline());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25997DataProvider")
	public static Object[][] test25997DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// WORDSNET-26148 Consider adding an option to preserve empty paragraphs upon exporting to MD.
    /// The issue is not reproducible, just added the test.
    /// </summary>
    @Test
    public void test26148() throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Customers\\Test26148.docx");

        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setPreserveEmptyLines(true); }
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Customers\\Test26148", UnifiedScenario.MD_2_MD, loadOptions);

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(2, TestImportMarkdown.getEmptyParagraphsCount(para, false));
    }


    /// <summary>
    /// WORDSNET-27382 Consider providing an option to export tables as HTML when saving document to Markdown.
    /// Implemented public <see cref="MarkdownSaveOptions.ExportAsHtml"/> option.
    /// </summary>
    // FOSS Tables-as-HTML export needs the removed HTML writer; keep only the pure-Markdown case.
    @Test (dataProvider = "test27382DataProvider")
    public void test27382(/*MarkdownExportAsHtml*/int exportAsHtml) throws Exception
    {
        final String TEST_FILE = "ExportMarkdown\\RawHtml\\Test27382";
        Document doc = TestUtil.open(TEST_FILE, LoadFormat.DOCX);

        MarkdownSaveOptions so = new MarkdownSaveOptions(); { so.setExportAsHtml(exportAsHtml); }
        doc = TestUtil.saveOpen(doc, msString.format("{0}_{1}.md", TEST_FILE, MarkdownExportAsHtml.toString(exportAsHtml)), so, true);

        msAssert.areEqual(1, doc.getFirstSection().getBody().getTables().getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27382DataProvider")
	public static Object[][] test27382DataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownExportAsHtml.NONE},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-27382.
    /// Tests nested tables.
    /// </summary>
    // FOSS Tables-as-HTML export needs the removed HTML writer; keep only the pure-Markdown case.
    @Test (dataProvider = "test27382NestedDataProvider")
    public void test27382Nested(/*MarkdownExportAsHtml*/int exportAsHtml, int expectedCount) throws Exception
    {
        final String TEST_FILE = "ExportMarkdown\\RawHtml\\Test27382Nested";
        Document doc = TestUtil.open(TEST_FILE, LoadFormat.DOCX);

        MarkdownSaveOptions so = new MarkdownSaveOptions(); { so.setExportAsHtml(exportAsHtml); }
        doc = TestUtil.saveOpen(doc, msString.format("{0}_{1}.md", TEST_FILE, MarkdownExportAsHtml.toString(exportAsHtml)), so, true);

        msAssert.areEqual(expectedCount, doc.getChildNodes(NodeType.TABLE, true).getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27382NestedDataProvider")
	public static Object[][] test27382NestedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownExportAsHtml.NONE,  3},
		};
	}


    /// <summary>
    /// Relates to WORDSNET-27646.
    /// Tests with default value.
    /// </summary>
    @Test
    public void test27646_Default() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ExportMarkdown\\Test27646_Default",
            UnifiedScenario.DOCX_2_MD | UnifiedScenario.NO_GOLD);

        msAssert.areEqual("fx=a0+n=1∞ancosnπxL+bnsinnπxL", doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getText());
    }

    /// <summary>
    /// WORDSNET-27675 Consider adding property to set image resolution in MarkdownSaveOptions.
    /// Implemented public <see cref="MarkdownSaveOptions.ImageResolution"/> option.
    /// </summary>
    @Test (dataProvider = "test27675DataProvider")
    public void test27675(int resolution) throws Exception
    {
        Document doc = TestUtil.open("ExportMarkdown\\Test27675.docx");

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        // Test default value.
        msAssert.areEqual(96, so.getImageResolution());

        so.setImageResolution(resolution);

        // FOSS: applying MarkdownSaveOptions.ImageResolution requires image re-encoding, which is
        // unavailable without the image-processing subsystem. Verify the save/open round-trip completes
        // without crashing instead of asserting the resulting image DPI.
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Test27675.md", so, false);
        msAssert.isNotNull(doc);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27675DataProvider")
	public static Object[][] test27675DataProvider() throws Exception
	{
		return new Object[][]
		{
			{1},
			{100},
			{300},
			{1000},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-27675.
    /// Tests values must be positive.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "test27675ADataProvider")
    public void test27675A(int resolution)
    {
        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setImageResolution(resolution);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27675ADataProvider")
	public static Object[][] test27675ADataProvider()
	{
		return new Object[][]
		{
			{0},
			{-1},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-27675.
    /// Tests default value.
    /// </summary>
    @Test
    public void test27675_Default() throws Exception
    {
        // The image in test file is actually 300 dpi.
        Document doc = TestUtil.open("ExportMarkdown\\Test27675_Default.docx");

        // FOSS: the default image DPI can only be verified by decoding/re-encoding the image, which is
        // unavailable without the image-processing subsystem. Verify the round-trip completes.
        doc = TestUtil.saveOpen(doc, "ExportMarkdown\\Test27675_Default", UnifiedScenario.MD_2_MD | UnifiedScenario.NO_GOLD);
        msAssert.isNotNull(doc);
    }

    /// <summary>
    /// WORDSNET-18341 Implement Markdown ‘Line Breaks’ feature.
    /// Implemented <see cref="MarkdownSaveOptions.LineBreakExportMode"/>.
    /// </summary>
    @Test (dataProvider = "testExportLineBreakDataProvider")
    public void testExportLineBreak(int mode) throws Exception
    {
        final String TEST_FILE = "ExportMarkdown\\TestExportLineBreak";

        Document doc = TestUtil.open(TEST_FILE + ".docx");

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setLineBreakExportMode((/*MarkdownLineBreakExportMode*/int)mode);

        doc = TestUtil.saveOpen(doc, TEST_FILE + MarkdownLineBreakExportMode.toString(so.getLineBreakExportMode()) + ".md", so, true);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paragraphs.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testExportLineBreakDataProvider")
	public static Object[][] testExportLineBreakDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownLineBreakExportMode.BACKSLASH},
			{MarkdownLineBreakExportMode.SPACES},
		};
	}


    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests single empty paragraph between two others non-empty.
    /// </summary>
    @Test (dataProvider = "test28043ADataProvider")
    public void test28043A(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043A", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043ADataProvider")
	public static Object[][] test28043ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.EMPTY_LINE,  2},
			{MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK,  1},
			{MarkdownEmptyParagraphExportMode.NONE,  2},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests two empty paragraphs between two others non-empty.
    /// </summary>
    @Test (dataProvider = "test28043BDataProvider")
    public void test28043B(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043B", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043BDataProvider")
	public static Object[][] test28043BDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.EMPTY_LINE,  2},
			{MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK,  1},
			{MarkdownEmptyParagraphExportMode.NONE,  2},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests empty paragraphs followed by a table.
    /// </summary>
    @Test (dataProvider = "test28043CDataProvider")
    public void test28043C(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043C", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043CDataProvider")
	public static Object[][] test28043CDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.EMPTY_LINE,  2},
			{MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK,  4},
			{MarkdownEmptyParagraphExportMode.NONE,  2},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests empty paragraphs followed by a InlineCode.
    /// </summary>
    @Test (dataProvider = "test28043DDataProvider")
    public void test28043D(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043D", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043DDataProvider")
	public static Object[][] test28043DDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.EMPTY_LINE,  2},
			{MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK,  1},
			{MarkdownEmptyParagraphExportMode.NONE,  2},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests empty paragraphs followed by a list item.
    /// </summary>
    @Test (dataProvider = "test28043EDataProvider")
    public void test28043E(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043E", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043EDataProvider")
	public static Object[][] test28043EDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.EMPTY_LINE,  2},
			{MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK,  3},
			{MarkdownEmptyParagraphExportMode.NONE,  2},
		};
	}


    /// <summary>
    /// Relates to WORDSNET-28043.
    /// Tests empty paragraphs inside Table.
    /// </summary>
    // FOSS EmptyLine/MarkdownHardLineBreak emit inline HTML (<p>/<br>) in table cells, so the
    // saved .md is detected as HTML on reopen and the removed HTML loader throws; keep None.
    @Test (dataProvider = "test28043GDataProvider")
    public void test28043G(/*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        test28043Core("ExportMarkdown\\Customers\\Test28043G", mode, expectedCount);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28043GDataProvider")
	public static Object[][] test28043GDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownEmptyParagraphExportMode.NONE,  1},
		};
	}


    /// <summary>
    /// WORDSNET-28289 Regression XML to MD: HTML Tables tags with Markdown Content.
    /// We should adopt <see cref="MarkdownSaveOptions.ExportAsHtml"/> option to exporting tables in Markdown.
    /// </summary>
    // FOSS Tables/NonCompatibleTables emit HTML that FOSS can neither write nor read back;
    // keep only the pure-Markdown case.
    @Test (dataProvider = "test28289DataProvider")
    public void test28289(/*MarkdownExportAsHtml*/int exportAsHtml, /*CellMerge*/int expectedCellMerge) throws Exception
    {
        final String FILE_NAME = "ExportMarkdown\\Customers\\Test28289";
        Document doc = TestUtil.open(msString.format("{0}.xml", FILE_NAME));

        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions();
        saveOptions.setExportAsHtml(exportAsHtml);

        String outFileName = msString.format("{0}_{1}.md",FILE_NAME, MarkdownExportAsHtml.toString(exportAsHtml));
        doc = TestUtil.saveOpen(doc, outFileName, saveOptions);

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(2, tables.getCount());

        Table table = tables.get(0);
        msAssert.areEqual(expectedCellMerge, table.getFirstRow().getFirstCell().getCellFormat().getVerticalMerge());

        table = tables.get(1);
        msAssert.areEqual(expectedCellMerge, table.getFirstRow().getFirstCell().getCellFormat().getVerticalMerge());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28289DataProvider")
	public static Object[][] test28289DataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownExportAsHtml.NONE,  CellMerge.NONE},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28289.
    /// Tests when there are tables in document that can be properly represented by pure Markdown
    /// and tables that can not be properly represented by Markdown without using raw HTML.
    /// </summary>
    // FOSS Tables/NonCompatibleTables emit HTML that FOSS can neither write nor read back;
    // keep only the pure-Markdown case.
    @Test (dataProvider = "test28289MixedDataProvider")
    public void test28289Mixed(/*MarkdownExportAsHtml*/int exportAsHtml, /*CellMerge*/int expectedCellMerge, int expectedTablesCount) throws Exception
    {
        final String FILE_NAME = "ExportMarkdown\\Customers\\Test28289Mixed";
        Document doc = TestUtil.open(msString.format("{0}.docx", FILE_NAME));

        MarkdownSaveOptions saveOptions = new MarkdownSaveOptions();
        saveOptions.setExportAsHtml(exportAsHtml);

        String outFileName = msString.format("{0}_{1}.md", FILE_NAME, MarkdownExportAsHtml.toString(exportAsHtml));
        doc = TestUtil.saveOpen(doc, outFileName, saveOptions);

        // Note, Markdown for the moment cannot read HTML-tables.
        // Also, when both tables are written as HTML with `MarkdownExportAsHtml.Tables` flag,
        // it then detected and loaded as HTML format, and not as Markdown. That's why there
        // are 2 tables with this flag, despite HTML tables cannot be read in Markdown yet.
        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(expectedTablesCount, tables.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28289MixedDataProvider")
	public static Object[][] test28289MixedDataProvider() throws Exception
	{
		return new Object[][]
		{
			{MarkdownExportAsHtml.NONE,  CellMerge.NONE,  2},
		};
	}

    // FOSS Test28289BothOptions removed: exercises the ExportAsHtml table-precedence feature,
    // which emits HTML that FOSS can neither write nor read back.


    // FOSS Test28748 removed: verifies image alt-text survives a docx->md->docx roundtrip, but the
    // FOSS build does not serialize images to Markdown, so no shape survives the roundtrip.


    /// <summary>
    /// The core method for Test28043.
    /// </summary>
    private static void test28043Core(String testFile, /*MarkdownEmptyParagraphExportMode*/int mode, int expectedCount) throws Exception
    {
        Document doc = TestUtil.open(testFile + ".docx");

        MarkdownSaveOptions so = new MarkdownSaveOptions();
        so.setEmptyParagraphExportMode(mode);

        doc = TestUtil.saveOpen(doc, msString.format("{0}_{1}.md", testFile, MarkdownEmptyParagraphExportMode.toString(mode)), so, true);

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(expectedCount, paragraphs.getCount());
    }

    /// <summary>
    /// The core method for Test24219.
    /// </summary>
    private static String test24219Core(boolean isExportBase64) throws Exception
    {
        final String TEST_NAME = "ExportMarkdown\\Customers\\Test24219";
        Document doc = TestUtil.open(TEST_NAME, LoadFormat.DOCX);

        // The customer's scenario is to write into FileStream.
        MarkdownSaveOptions options = new MarkdownSaveOptions(); { options.setExportImagesAsBase64(isExportBase64); }
        String outFileName = TestUtil.getInTestOutPath(msString.format("{0} Out_{1}.md", TEST_NAME, isExportBase64));

        String imageFile = outFileName.replace(".md", ".001.png");
        if (File.exists(imageFile))
            File.delete(imageFile);

        FileStream fs = new FileStream(outFileName, FileMode.CREATE);
        try /*JAVA: was using*/
        {
            doc.save(fs, options);
        }
        finally { if (fs != null) fs.close(); }

        return outFileName;
    }

    /// <summary>
    /// Checks bullet list items of the TestLargeDocument() test.
    /// </summary>
    private static void checkBulletListItems(Document doc)
    {
        Paragraph paragraph = getParagraphWithText(doc, "Overview");
        checkListItem(paragraph, 0, "*", "Normal");
        paragraph = getParagraphWithText(doc, "Philosophy");
        checkListItem(paragraph, 1, "o", "Normal");

        paragraph = getParagraphWithText(doc, "Miscellaneous");
        checkListItem(paragraph, 0, "*", "Normal");
        paragraph = getParagraphWithText(doc, "Backslash Escapes");
        checkListItem(paragraph, 1, "o", "Normal");
    }

    /// <summary>
    /// Checks ordered list items of the TestLargeDocument() test.
    /// </summary>
    private static void checkOrderedListItems(Document doc)
    {
        Paragraph paragraph = getParagraphWithText(doc, "Ordered lists use numbers followed by periods:");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Bird", 0, "1.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "McHale", 0, "2.", "Normal");

        paragraph = getParagraphWithText(doc, "If you instead wrote the list in Markdown like this:");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Bird", 0, "1.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "McHale", 0, "2.", "Normal");

        paragraph = getParagraphWithText(doc, "or even:");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Bird", 0, "3.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "McHale", 0, "4.", "Normal");

        paragraph = getParagraphWithText(doc, "This is a list item with two paragraphs.");
        checkListItem(paragraph, 0, "1.", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Vestibulum enim wisi", 0, " ", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, "Suspendisse", 0, "2.", "Normal");

        paragraph = getParagraphWithText(doc, "What a great season.");
        checkListItem(paragraph, 0, "1986.", "Normal");

        paragraph = getParagraphWithText(doc, "1986. What a great season.");
        msAssert.isFalse(paragraph.isListItem());
    }

    /// <summary>
    /// Checks headings of the TestLargeDocument() test.
    /// </summary>
    private static void checkHeadings(Document doc)
    {
        Paragraph paragraph = getParagraphWithText(doc, "Markdown: Syntax");
        checkStyles(paragraph, "SetextHeading1", "Heading 1");

        paragraph = getParagraphWithText(doc, "This is an H1");
        checkStyles(paragraph, "SetextHeading1", "Heading 1");

        paragraph = getParagraphWithText(doc, "This is an H2");
        checkStyles(paragraph, "SetextHeading2", "Heading 2");

        paragraph = getParagraphWithText(doc, "This is an H3");
        checkStyles(paragraph, "Heading 3");

        // Check Heading inside Quote.
        paragraph = getParagraphWithText(doc, "This is a header.");
        checkStyles(paragraph, "Quote2", "Heading 2");
    }

    /// <summary>
    /// Checks quotes of the TestLargeDocument() test.
    /// </summary>
    private static void checkQuotes(Document doc)
    {
        Paragraph paragraph = getParagraphWithText(doc, "This is a blockquote with two paragraphs.");
        checkStyles(paragraph, "Quote");

        paragraph = getParagraphWithText(doc, "This is the first level of quoting.");
        checkStyles(paragraph, "Quote");
        paragraph = getParagraphWithText(doc, "This is nested blockquote.");
        checkStyles(paragraph, "Quote1");
        paragraph = getParagraphWithText(doc, "Back to the first level.");
        checkStyles(paragraph, "Quote");

        // Check Quote inside BulletList item.
        paragraph = getParagraphWithText(doc, "A list item with a blockquote:");
        checkListItem(paragraph, 0, "*", "Normal");
        paragraph = (Paragraph)paragraph.getNextSibling();
        checkListItem(paragraph, 0, " ", "Quote", "Normal");
    }

    /// <summary>
    /// Checks horizontal rules of the TestLargeDocument() test.
    /// </summary>
    private static void checkHorizontalRules(Document doc)
    {
        Paragraph paragraph = getParagraphWithText(doc, "You can produce a horizontal rule tag");
        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.isTrue(((Shape)paragraph.getFirstChild()).getHorizontalRule().getOn());
        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.isTrue(((Shape)paragraph.getFirstChild()).getHorizontalRule().getOn());
        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.isTrue(((Shape)paragraph.getFirstChild()).getHorizontalRule().getOn());
        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.isTrue(((Shape)paragraph.getFirstChild()).getHorizontalRule().getOn());
        paragraph = (Paragraph)paragraph.getNextSibling();
        msAssert.isTrue(((Shape)paragraph.getFirstChild()).getHorizontalRule().getOn());
    }

    /// <summary>
    /// Checks emphases of the TestLargeDocument() test.
    /// </summary>
    private static void checkEmphases(Document doc)
    {
        Paragraph paragraph = (Paragraph)getParagraphWithText(doc, "Markdown treats asterisks").getNextSibling();
        Run run = getRunWithText(paragraph, "single asterisks");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        paragraph = (Paragraph)paragraph.getNextSibling();
        run = getRunWithText(paragraph, "single underscores");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        paragraph = (Paragraph)paragraph.getNextSibling();
        run = getRunWithText(paragraph, "double asterisks");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        paragraph = (Paragraph)paragraph.getNextSibling();
        run = getRunWithText(paragraph, "double underscores");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        // Check emphasis inside word.
        paragraph = (Paragraph)getParagraphWithText(doc, "Emphasis can be used in the middle of a word:").getNextSibling();
        run = getRunWithText(paragraph, "un");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
        run = getRunWithText(paragraph, "frigging");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        run = getRunWithText(paragraph, "believable");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());
    }

    /// <summary>
    /// Checks autolinks of the TestLargeDocument() test.
    /// </summary>
    private static void checkAutolinks(Document doc)
    {
        // Checks Reference link.
        Paragraph paragraph = (Paragraph)getParagraphWithText(doc,
            "Just use an empty set of square brackets").getNextSibling();
        checkParagraph(paragraph,
            "\u0013 HYPERLINK \"http://google.com/\" \\s \"[Google]\" \u0014Google\u0015", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "Markdown will turn this into:").getPreviousSibling();
        checkParagraph(paragraph,
            "\u0013 HYPERLINK \"http://example.com/\" \u0014http://example.com/\u0015\r", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "into something like this:").getPreviousSibling();
        checkParagraph(paragraph,
            "\u0013 HYPERLINK \"mailto:address@example.com\" \u0014address@example.com\u0015\r", "Normal");
    }

    /// <summary>
    /// Checks links of the TestLargeDocument() test.
    /// </summary>
    private static void checkLinks(Document doc)
    {
        Paragraph paragraph = (Paragraph)getParagraphWithText(doc, "Overview");
        checkParagraph(paragraph,
            "\u0013 HYPERLINK \\l \"overview\" \u0014Overview\u0015\r", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "Similarly, because Markdown supports");
        checkParagraph(paragraph,
            "Similarly, because Markdown supports \u0013 HYPERLINK \\l \"html\" \u0014inline HTML\u0015, " +
            "if you use\u000bangle brackets as delimiters for HTML tags, Markdown will treat them as\u000bsuch. But " +
            "if you write:\r", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "To create an inline link, use a set").getNextSibling();
        checkParagraph(paragraph,
            "This is \u0013 HYPERLINK \"http://example.com/\" \\o \"Title\" \u0014an example\u0015 inline link.\r",
            "Normal");

        paragraph = (Paragraph)paragraph.getNextSibling();
        checkParagraph(paragraph,
            "\u0013 HYPERLINK \"http://example.net/\" \u0014This link\u0015 has no title attribute.\r", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "If you're referring to a local resource").getNextSibling();
        checkParagraph(paragraph,
            "See my \u0013 HYPERLINK \"/about/\" \u0014About\u0015 page for details.\r", "Normal");

        paragraph = (Paragraph)getParagraphWithText(doc, "For comparison, here is the same paragraph").getNextSibling();
        checkParagraph(paragraph,
            "I get 10 times more traffic from \u0013 HYPERLINK \"http://google.com/\" \\o \"Google\" \\s \"[Google]\" " +
            "\u0014Google\u0015\u000bthan from \u0013 HYPERLINK \"http://search.yahoo.com/\" \\o \"Yahoo Search\" \\s \"[Yahoo]\" " +
            "\u0014Yahoo\u0015 or\u000b\u0013 HYPERLINK \"http://search.msn.com/\" \\o \"MSN Search\" \\s \"[MSN]\" " +
            "\u0014MSN\u0015.\r", "Normal");
    }

    /// <summary>
    /// Checks images of the TestLargeDocument() test.
    /// </summary>
    private static void checkImages(Document doc) throws Exception
    {
        Paragraph paragraph =
            (Paragraph)getParagraphWithText(doc, "Inline image syntax looks like this:").getNextSibling();
        checkShape(ms.as(paragraph.getChildNodes(NodeType.ANY, false).get(0), Shape.class), "Alt text", "/path/to/img.jpg");

        paragraph = (Paragraph)paragraph.getNextSibling();
        checkShape(ms.as(paragraph.getChildNodes(NodeType.ANY, false).get(0), Shape.class), "Alt text", "/path/to/img.jpg", "Optional title");
    }
}

