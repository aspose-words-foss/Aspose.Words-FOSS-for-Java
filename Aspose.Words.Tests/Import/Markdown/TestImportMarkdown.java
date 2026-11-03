// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.StringComparison;
import com.aspose.FormatterPal;
import com.aspose.ThreadPal;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.Stream;
import com.aspose.StreamUtil;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests import from markdown format.
/// </summary>
@Test
public class TestImportMarkdown extends TestMarkdownBase
{
    /// <summary>
    /// Tests reading an empty markdown document.
    /// </summary>
    @Test
    public void testEmptyA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Paragraphs\\TestEmptyA.md");
        msAssert.areEqual("\f", doc.getText());
    }

    /// <summary>
    /// Tests reading a markdown document that contains only whitespace characters.
    /// </summary>
    @Test
    public void testEmptyB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Paragraphs\\TestEmptyB.md");
        msAssert.areEqual("\f", doc.getText());
    }

    /// <summary>
    /// Tests reading a simple markdown document.
    /// </summary>
    @Test
    public void testParagraphA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Paragraphs\\TestParagraphA.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraph(paras.get(0), "Lorem ipsum dolor sit amet, consectetur adipiscing\r", "Normal");
        checkParagraph(paras.get(1), "elit, sed do eiusmod\r", "Normal");
        checkParagraph(paras.get(2), "tempor incididunt\f", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with empty lines at start and end.
    /// </summary>
    @Test
    public void testParagraphB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Paragraphs\\TestParagraphB.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "Lorem ipsum\r", "Normal");
        checkParagraph(paras.get(1), "dolor sit amet,\f", "Normal");

        msAssert.areEqual(2, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with whitespace characters at the start and end of lines.
    /// </summary>
    @Test
    public void testParagraphC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Paragraphs\\TestParagraphC.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "aaa bbb ccc\f", "Normal");
        msAssert.areEqual(1, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with AtxHeadings.
    /// </summary>
    @Test
    public void testAtxHeading() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestAtxHeading.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "Lorem ipsum\r", "Heading 1");
        checkParagraph(paras.get(1), "dolor sit amet,\r", "Normal");
        checkParagraph(paras.get(2), "consectetur\r", "Heading 2");
        checkHorizontalRule(paras.get(3), "Normal");
        checkParagraph(paras.get(4), "----\r", "Heading 3");
        checkParagraph(paras.get(5), "####adipiscing elit,\r", "Normal");
        checkParagraph(paras.get(6), "incididunt ut\r", "Heading 4");
        checkParagraph(paras.get(7), "eiusmod\r", "Heading 5");
        checkParagraph(paras.get(8), "labore et# # # #\r", "Heading 6");
        checkParagraph(paras.get(9), "####### sed do\r", "Normal");
        checkParagraph(paras.get(10), "tempor##\f", "Heading 2");

        msAssert.areEqual(11, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with SetextHeadings.
    /// </summary>
    @Test
    public void testSetextHeadingA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestSetextHeadingA.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "Lorem ipsum\r", "SetextHeading1", "Heading 1");
        checkParagraph(paras.get(1), "dolor sit amet, consectetur\r", "SetextHeading2", "Heading 2");
        checkParagraph(paras.get(2), "\f", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with SetextHeadings.
    /// </summary>
    @Test
    public void testSetextHeadingB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestSetextHeadingB.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "  Lorem ipsum\r", "IndentedCode");
        checkParagraph(paras.get(1), "================ dolor sit amet, consectetur\r", "SetextHeading2", "Heading 2");
        checkHorizontalRule(paras.get(2), "Normal");
        checkParagraph(paras.get(3), "adipiscing elit,\f", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with Quotes.
    /// </summary>
    @Test
    public void testQuoteA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestQuoteA.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "Lorem ipsum\r", "Quote", "SetextHeading1", "Heading 1");
        checkParagraph(paras.get(1), "dolor sit amet,\r", "Quote2", "Quote1", "Normal");
        checkParagraph(paras.get(2), "consectetur\r", "Quote3", "Quote2");
        checkHorizontalRule(paras.get(3), "Normal");
        checkHorizontalRule(paras.get(4), "Quote2");

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with Quotes.
    /// </summary>
    @Test
    public void testQuoteB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestQuoteB.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "Lorem\r", "Quote", "Normal");
        checkParagraph(paras.get(1), "ipsum\r", "Quote1", "Quote", "Normal");
        checkParagraph(paras.get(2), "dolor\r", "Quote2", "Quote1", "Quote", "Normal");
        checkParagraph(paras.get(3), "sit\r", new ExpectedStyleName[] {new ExpectedStyleName("Quote", 58),
            new ExpectedStyleName("Quote2", 1), new ExpectedStyleName("Quote1", 1), new ExpectedStyleName("Quote", 1), new ExpectedStyleName("Normal", 1)});
        checkParagraph(paras.get(4), "amet,\r", "Quote61", "Heading 1");
        checkParagraph(paras.get(5), "consectetur\r", "Quote63", "Quote62", "Heading 2");
        checkParagraph(paras.get(6), "adipiscing\r", "Quote66", "Quote65", "Quote64", "Heading 3");
        checkParagraph(paras.get(7), "\r", "Normal");
        checkParagraph(paras.get(8), "elit\f", "Quote62", "Heading 2");

        msAssert.areEqual(9, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with Quotes separated with blank lines.
    /// </summary>
    @Test
    public void testQuoteC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Quotes\\TestQuoteV.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "baz\r", "Quote", "Normal");
        checkParagraph(paras.get(1), "\r", "Normal");
        checkParagraph(paras.get(2), "tempor incididunt\f", "Quote1", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with Quotes with Headings inside.
    /// </summary>
    @Test
    public void testQuoteD() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Quotes\\TestQuoteW.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "foo\r", "Quote", "Heading 1");
        checkParagraph(paras.get(1), "\r", "Normal");
        checkParagraph(paras.get(2), "bar\r", "Quote1", "Normal");
        checkParagraph(paras.get(3), "\r", "Normal");
        checkParagraph(paras.get(4), "baz\r", "Quote", "Heading 1");
        checkParagraph(paras.get(5), "bop\f", "Quote1", "Normal");

        msAssert.areEqual(6, paras.getCount());
    }


    /// <summary>
    /// Tests reading a markdown document with horizontal rules.
    /// </summary>
    @Test
    public void testHorizontalRuleA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestHorizontalRuleA.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        for (Paragraph para : (Iterable<Paragraph>) paras)
            checkHorizontalRule(para, "Normal");

        msAssert.areEqual(9, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with horizontal rules.
    /// </summary>
    @Test
    public void testHorizontalRuleB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestHorizontalRuleB.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());
        checkParagraph(paras.get(0), "--- Lorem ipsum\r", "SetextHeading2", "Heading 2");
        checkParagraph(paras.get(1), "dolor sit amet, ------\r", "SetextHeading2", "Heading 2");
        checkParagraph(paras.get(2), "consectetur\f", "SetextHeading2", "Heading 2");
    }

    /// <summary>
    /// Tests reading a markdown document with emphases.
    /// </summary>
    @Test
    public void testEmphasis() throws Exception
    {
        // This is a small but very complex file with many emphases. It will be better to check gold here.
        Document doc = TestUtil.openSaveOpen("ImportMarkdown\\TestEmphasis", UnifiedScenario.MD_2_DOCX);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        RunCollection runs = paras.get(0).getRuns();
        msAssert.areEqual(21, runs.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with IndentedCode.
    /// </summary>
    @Test
    public void testIndentedCode() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestIndentedCode.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(11, paras.getCount());
        checkParagraph(paras.get(0), "Lorem ipsum\r", "IndentedCode");
        checkParagraph(paras.get(1), "dolor sit amet,       consectetur # adipiscing elit,\r", "Normal");
        checkParagraph(paras.get(2), "sed do\r", "Heading 1");
        checkParagraph(paras.get(3), "eiusmod\u000b\u000btempor\u000bincididunt\u000but labore\u000b> et dolore\r", "IndentedCode");
        checkParagraph(paras.get(4), "magna aliqua.\r", "Quote", "Normal");
        checkParagraph(paras.get(5), "Ut enim\r", "Quote1", "IndentedCode");
        checkParagraph(paras.get(6), "ad minim\r", "IndentedCode");
        checkParagraph(paras.get(7), "veniam\r", "Quote", "Normal");
        checkParagraph(paras.get(8), "quis\r", "IndentedCode");
        checkParagraph(paras.get(9), "======== nostrud\r", "SetextHeading1", "Heading 1");
        checkParagraph(paras.get(10), "exercitation\f", "IndentedCode");
    }

    /// <summary>
    /// Tests reading a markdown document with FencedCode.
    /// </summary>
    @Test
    public void testFencedCode() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestFencedCode.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(8, paras.getCount());
        checkParagraph(paras.get(0), "Lorem ipsum\r", "FencedCode");
        checkParagraph(paras.get(1), "dolor\r", "FencedCode.C#");
        checkParagraph(paras.get(2), "sit\u000bamet,\r", "FencedCode.C++");
        checkParagraph(paras.get(3), "adipiscing elit,\r", "Heading 3");
        checkParagraph(paras.get(4), "# eiusmod\r", "FencedCode");
        checkParagraph(paras.get(5), "   incididunt\r", "Quote", "FencedCode.Java");
        checkParagraph(paras.get(6), "> ut labore\u000b> et dlr\r", "FencedCode");
        checkParagraph(paras.get(7), "quis\f", "FencedCode");
    }

    /// <summary>
    /// Tests reading a markdown document with InlineCode.
    /// </summary>
    @Test
    public void testInlineCode() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestInlineCode.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        checkParagraph(paras.get(0), "``\u000b``` foo bar\r", "Normal");
        checkParagraph(paras.get(1), "baz bop\r", "Heading 1");
        checkParagraph(paras.get(2), "loremipsum dolor sitamet conse```ct`etur* adi*piscing elit, sed do\r", "Quote");
        checkParagraph(paras.get(3), "eiusmod\f", "SetextHeading1", "Heading 1");
        msAssert.areEqual(4, paras.getCount());

        Paragraph para = paras.get(0);
        checkRun(para.getRuns().get(0), "``", "InlineCode");
        checkRun(para.getRuns().get(1), "\u000b``` ", "Default Paragraph Font");
        checkRun(para.getRuns().get(2), "foo ", "Default Paragraph Font");
        msAssert.areEqual(AttrBoolEx.TRUE, para.getRuns().get(2).getRunPr().getItalic());
        checkRun(para.getRuns().get(3), "bar", "InlineCode");
        msAssert.areEqual(AttrBoolEx.TRUE, para.getRuns().get(3).getRunPr().getItalic());

        para = paras.get(1);
        checkRun(para.getRuns().get(0), "baz", "InlineCode");
        checkRun(para.getRuns().get(1), " bop", "Default Paragraph Font");

        para = paras.get(2);
        checkRun(para.getRuns().get(0), "lorem", "InlineCode");
        checkRun(para.getRuns().get(1), "ipsum dolor ", "Default Paragraph Font");
        checkRun(para.getRuns().get(2), "sit", "InlineCode");
        checkRun(para.getRuns().get(3), "a", "Default Paragraph Font");
        checkRun(para.getRuns().get(4), "met ", "Default Paragraph Font");
        msAssert.areEqual(AttrBoolEx.TRUE, para.getRuns().get(4).getRunPr().getItalic());
        checkRun(para.getRuns().get(5), "conse```ct`etur* adi*piscing", "InlineCode.2");
        checkRun(para.getRuns().get(6), " el", "Default Paragraph Font");
        msAssert.areEqual(AttrBoolEx.TRUE, para.getRuns().get(6).getRunPr().getItalic());
        checkRun(para.getRuns().get(7), "it, se", "Default Paragraph Font");
        msAssert.areEqual(AttrBoolEx.FALSE, para.getRuns().get(7).getRunPr().getItalic());
        checkRun(para.getRuns().get(8), "d do", "InlineCode.4");

        para = paras.get(3);
        checkRun(para.getRuns().get(0), "e", "Default Paragraph Font");
        checkRun(para.getRuns().get(1), "iusmo", "InlineCode");
        checkRun(para.getRuns().get(2), "d", "Default Paragraph Font");
    }

    /// <summary>
    /// Tests reading a markdown document with strikethrough.
    /// </summary>
    @Test
    public void testStrikethrough() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportMarkdown\\TestStrikethrough", UnifiedScenario.MD_2_DOCX);
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        Run run = getRunWithText(paragraph, "Foo ~");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());

        run = getRunWithText(paragraph, "boz");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "~~ bop~~~");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "ipsum");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "set");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "~~a");
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getStrikeThrough());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "di~~piscin");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests reading a markdown document with underline.
    /// </summary>
    @Test
    public void testUnderline() throws Exception
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setImportUnderlineFormatting(true); }
        Document doc = TestUtil.open("ImportMarkdown\\TestUnderline.md", loadOptions);
        doc = TestUtil.saveOpen(doc, "ImportMarkdown\\TestUnderline.docx");
        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

        Run run = getRunWithText(paragraph, "Foo +");
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());

        run = getRunWithText(paragraph, "boz");
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "++ bop+++");
        msAssert.areEqual(Underline.NONE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());

        run = getRunWithText(paragraph, "ipsum");
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "set");
        msAssert.areEqual(Underline.SINGLE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "++a");
        msAssert.areEqual(Underline.NONE, run.getRunPr().getUnderline());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getBold());
        msAssert.areEqual(AttrBoolEx.FALSE, run.getRunPr().getItalic());

        run = getRunWithText(paragraph, "di++piscin");
        msAssert.areEqual("InlineCode", run.getCharacterStyle().getName());
    }

    /// <summary>
    /// Tests reading a markdown document with simple bullet lists.
    /// </summary>
    @Test
    public void testBulletListA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListA.md");
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
    /// Tests reading a markdown document with nested in single line bullet lists.
    /// </summary>
    @Test
    public void testBulletListB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListB.md");
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
    /// Tests reading a markdown document with nested in single line bullet lists and quotes.
    /// </summary>
    @Test
    public void testBulletListC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListC.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, " ", "List", "Quote", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "", "Quote2", "List1", "Quote1", "List", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 5, "", "Quote5", "List6", "List5", "Quote4", "List4", "List3", "Quote3", "List2", "Normal");
        checkListItem(paras.get(4), "e\f", 5, " ", "List11", "List10", "Quote7", "List9", "List8", "Quote6", "List7", "List", "Quote", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 1, '-');
        checkStyleList(doc.getStyles().get("List11"), 0, 1, '-');

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with headings inside bullet lists and quotes.
    /// </summary>
    @Test
    public void testBulletListD() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListD.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "-", "Quote1", "Heading 1", "Normal");
        checkListItem(paras.get(2), "c\f", 0, "-", "Quote2", "SetextHeading2", "Heading 2", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with headings inside simple bullet lists.
    /// </summary>
    @Test
    public void testBulletListE() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListE.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Heading 1", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "o", "Heading 2", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading a very complex markdown document with bullet lists.
    /// </summary>
    @Test
    public void testBulletListF() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListF.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "Lorem\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "ipsum\r", 0, "+", "Normal");
        checkListItem(paras.get(2), "dolor\r", 0, "*", "Normal");
        checkListItem(paras.get(3), "sit\r", 1, "o", "Normal");
        checkListItem(paras.get(4), "amet\r", 1, "o", "Normal");
        checkListItem(paras.get(5), "consectetur\r", 2, "", "Normal");
        checkListItem(paras.get(6), "adipiscing elit\r", 3, "", "Normal");
        checkListItem(paras.get(7), "sed\r", 3, " ", "Heading 1", "Normal");
        checkListItem(paras.get(8), "do\r", 0, " ", "SetextHeading2", "Heading 2", "Normal");
        checkHorizontalRule(paras.get(9), "Normal");
        checkListItem(paras.get(9), "\r", 0, " ", "Normal");
        checkListItem(paras.get(10), "tabs indentation\u000beiusmod\r", 0, " ", "IndentedCode");
        checkListItem(paras.get(11), "tempor\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(12), "incididunt\r", 1, "o", "Quote2", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(13), "ut\r", 1, "o", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(14), "labore ** et\r", 0, "*", "Normal");
        checkParagraph(paras.get(15), "-dolore\r", "Normal");
        checkHorizontalRule(paras.get(16), "Quote", "Normal");
        checkListItem(paras.get(16), "\r", 0, "+", "Quote");
        checkListItem(paras.get(17), "aliqua.\r", 0, " ", "Quote");
        checkListItem(paras.get(18), "Ut - enim\r", 1, "o", "Quote");
        checkParagraph(paras.get(19), "+\tad\r", "IndentedCode");
        checkListItem(paras.get(20), "minim\r", 0, "-", "Normal");
        checkListItem(paras.get(21), "veniam\r", 2, "", "Quote4", "List1", "Quote3", "List", "Quote", "Normal");
        checkListItem(paras.get(22), "quis\r", 0, "-", "Normal");
        checkListItem(paras.get(23), "nostrud\r", 0, " ", "Normal");
        checkListItem(paras.get(24), "exercitation -         foo\f", 1, "o", "Normal");

        msAssert.areEqual(25, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListG() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListG.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "-", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "o", "Quote", "Normal");
        checkListItem(paras.get(2), "c\f", 1, "o", "List", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListH() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListH.md");
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
    /// Tests reading a Markdown document with bullet lists combined with quotes.
    /// </summary>
    @Test
    public void testBulletListI() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListI.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(1), "A list item with a blockquote:\r", 0, "*", "Normal");
        checkListItem(paras.get(2), "This is a blockquote inside a list item.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with bullet lists and indented code inside.
    /// </summary>
    @Test
    public void testBulletListJ() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListJ.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraph(paras.get(0), "To put a code block within a list item\r", "Normal");
        checkListItem(paras.get(1), "A list item with a code block:\r", 0, "*", "Normal");
        checkListItem(paras.get(2), "<code goes here>\f", 0, " ", "IndentedCode");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with bullet lists and horizontal rule.
    /// </summary>
    @Test
    public void testBulletListK() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListK.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkHorizontalRule(paras.get(0), "Quote", "Normal");
        checkListItem(paras.get(0), "\r", 0, "+", "Quote", "Normal");
        checkListItem(paras.get(1), "aliqua.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(2, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with bullet lists, horizontal rule and indented code.
    /// </summary>
    @Test
    public void testBulletListL() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestBulletListL.md");
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
    /// Tests reading markdown document with simple ordered lists with '.' as marker delimiter.
    /// </summary>
    @Test
    public void testOrderedListA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListA.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal" );
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal" );
        checkListItem(paras.get(2), "c\r", 1, "2.", "Normal" );
        checkListItem(paras.get(3), "d\r", 0, "2.", "Normal" );
        checkListItem(paras.get(4), "e\r", 1, "1.", "Normal" );
        checkListItem(paras.get(5), "f\r", 2, "1.", "Normal" );
        checkListItem(paras.get(6), "g\r", 3, "1.", "Normal" );
        checkListItem(paras.get(7), "h\r", 2, "2.", "Normal" );
        checkListItem(paras.get(8), "i\r", 1, "2.", "Normal" );
        checkListItem(paras.get(9), "j\f", 0, "3.", "Normal" );

        msAssert.areEqual(10, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with simple ordered lists with ')' as marker delimiter.
    /// </summary>
    @Test
    public void testOrderedListB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListB.md");
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
    /// Tests 'StartAt' is set properly upon reading ordered list.
    /// </summary>
    @Test
    public void testOrderedListC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListC.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1)", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "1)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "1.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests 'StartAt' is set properly upon reading ordered list.
    /// </summary>
    @Test
    public void testOrderedListD() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListD.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1)", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "5)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "8.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests 'StartAt' is set properly upon reading ordered list.
    /// </summary>
    @Test
    public void testOrderedListE() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListE.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "5.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "3.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "100)", "Normal");
        checkListItem(paras.get(3), "d\f", 0, "6.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with ordered lists combined with bullet lists.
    /// </summary>
    @Test
    public void testOrderedListF() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListF.md");
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
    /// Tests reading markdown document with nested in a single line ordered lists.
    /// </summary>
    @Test
    public void testOrderedListG() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListG.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 1, "6.", "List", "Normal");
        checkListItem(paras.get(1), "b\r", 0, "10.", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "5.", "List1", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "3.", "List3", "List2", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with nested in a single line ordered lists.
    /// </summary>
    @Test
    public void testOrderedListH() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListH.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "3.", "Quote", "Normal");
        checkListItem(paras.get(1), "b\r", 0, " ", "List", "Quote", "Normal");
        checkListItem(paras.get(2), "c\r", 2, "7.", "Quote2", "List2", "Quote1", "List1", "Quote", "Normal");
        checkListItem(paras.get(3), "d\r", 5, "44.", "Quote5", "List7", "List6", "Quote4", "List5", "List4", "Quote3", "List3", "Normal");
        checkListItem(paras.get(4), "e\f", 5, " ", "List13", "List12", "Quote7", "List11", "List10", "Quote6", "List9", "List8", "Quote", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 2, '.');
        checkStyleList(doc.getStyles().get("List5"), 2, 45, '.');
        checkStyleList(doc.getStyles().get("List13"), 0, 1, '.');

        msAssert.areEqual(5, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with ordered lists combined with headings.
    /// </summary>
    @Test
    public void testOrderedListI() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListI.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "7.", "Heading 1", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "2.", "Normal");
        checkListItem(paras.get(2), "c\r", 1, "3.", "Heading 2", "Normal");
        checkListItem(paras.get(3), "d\f", 2, "2.", "Normal");

        msAssert.areEqual(4, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with ordered lists nested in Quotes.
    /// </summary>
    @Test
    public void testOrderedListJ() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListJ.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "a\r", 0, "1.", "Normal");
        checkListItem(paras.get(1), "b\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(2), "c\f", 1, "1.", "List", "Quote", "Normal");

        checkStyleList(doc.getStyles().get("List"), 0, 1, '.');

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with ordered lists nested in Quotes and vise versa.
    /// </summary>
    @Test
    public void testOrderedListK() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListK.md");
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
    /// Tests reading markdown document with Quote inside ordered list.
    /// </summary>
    @Test
    public void testOrderedListL() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListL.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(1), "A list item with a blockquote:\r", 0, "5.", "Normal");
        checkListItem(paras.get(2), "This is a blockquote inside a list item.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with ordered lists and indented code inside.
    /// </summary>
    @Test
    public void testOrderedListM() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListM.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkParagraph(paras.get(0), "To put a code block within a list item\r", "Normal");
        checkListItem(paras.get(1), "A list item with a code block:\r", 0, "7.", "Normal");
        checkListItem(paras.get(2), "<code goes here>\f", 0, " ", "IndentedCode");

        msAssert.areEqual(3, paras.getCount());
    }

    /// <summary>
    /// Tests reading a markdown document with ordered lists and horizontal rule.
    /// </summary>
    @Test
    public void testOrderedListN() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListN.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkHorizontalRule(paras.get(0), "Quote", "Normal");
        checkListItem(paras.get(0), "\r", 0, "4.", "Quote", "Normal");
        checkListItem(paras.get(1), "aliqua.\f", 0, " ", "Quote", "Normal");

        msAssert.areEqual(2, paras.getCount());
    }

    /// <summary>
    /// Tests reading a very complex markdown document with ordered lists.
    /// </summary>
    @Test
    public void testOrderedListO() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListO.md");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        checkListItem(paras.get(0), "Lorem\r", 0, "2.", "Normal");
        checkListItem(paras.get(1), "ipsum\r", 0, "4)", "Normal");
        checkListItem(paras.get(2), "dolor\r", 0, "6.", "Normal");
        checkListItem(paras.get(3), "sit\r", 1, "1.", "Normal");
        checkListItem(paras.get(4), "amet\r", 1, "3)", "Normal");
        checkListItem(paras.get(5), "consectetur\r", 2, "", "Normal");
        checkListItem(paras.get(6), "adipiscing elit\r", 2, "25.", "Normal");
        checkListItem(paras.get(7), "sed\r", 2, " ", "Heading 1", "Normal");
        checkListItem(paras.get(8), "do\r", 0, " ", "SetextHeading2", "Heading 2", "Normal");
        checkHorizontalRule(paras.get(9), "Normal");
        checkListItem(paras.get(9), "\r", 0, " ", "Normal");
        checkListItem(paras.get(10), "tabs indentation\u000beiusmod\r", 0, " ", "IndentedCode");
        checkListItem(paras.get(11), "tempor\r", 1, "1.", "Quote", "Normal");
        checkListItem(paras.get(12), "incididunt\r", 1, "1)", "Quote2", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(13), "ut\r", 1, "1)", "Quote1", "Quote", "Normal");
        checkListItem(paras.get(14), "labore 1.et\r", 0, "7.", "Normal");
        checkParagraph(paras.get(15), "1.dolore\r", "Normal");
        checkHorizontalRule(paras.get(16), "Quote", "Normal");
        checkListItem(paras.get(16), "\r", 0, "1.", "Quote");
        checkListItem(paras.get(17), "aliqua.\r", 0, " ", "Quote");
        checkListItem(paras.get(18), "Ut 1. enim\r", 1, "1.", "Quote");
        checkParagraph(paras.get(19), "1.\tad\r", "IndentedCode");
        checkListItem(paras.get(20), "minim\r", 0, "1.", "Normal");
        checkListItem(paras.get(21), "veniam\r", 2, "1.", "Quote4", "List1", "Quote3", "List", "Quote", "Normal");
        checkListItem(paras.get(22), "quis\r", 0, "1.", "Normal");
        checkListItem(paras.get(23), "nostrud\r", 0, " ", "Normal");
        checkListItem(paras.get(24), "exercitation 1.         foo\f", 1, "1.", "Normal");

        msAssert.areEqual(25, paras.getCount());
    }

    /// <summary>
    /// Tests that new list is started when it was broken with a regular paragraph.
    /// </summary>
    @Test
    public void testOrderedListP() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListP.md");
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
        Document doc = TestUtil.open("ImportMarkdown\\TestOrderedListQ.md");
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
    /// Tests reading markdown document with table.
    /// </summary>
    @Test
    public void testTableA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestTableA.md");
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        RowCollection rows = tables.get(0).getRows();
        msAssert.areEqual(9, rows.getCount());

        Row row = rows.get(0);
        checkRow(row, new String[] {"NoAlign\u0007", "Center\u0007", "Left\u0007", "Right\u0007"});
        msAssert.areEqual(ParagraphAlignment.CENTER, row.getCells().get(0).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, row.getCells().get(1).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.LEFT, row.getCells().get(2).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, row.getCells().get(3).getFirstParagraph().getParaPr().getAlignment());

        row = rows.get(1);
        checkRow(row, new String[] {"First cell\u0007", "Second *cell\u0007", "Third cell*\u0007", "Fourth cell\u0007"});
        Run run = getRunWithText(row.getCells().get(0).getFirstParagraph(), "First");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getBold());
        run = getRunWithText(row.getCells().get(1).getFirstParagraph(), "Second");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getItalic());
        run = getRunWithText(row.getCells().get(2).getFirstParagraph(), "Third");
        msAssert.areEqual(AttrBoolEx.TRUE, run.getRunPr().getStrikeThrough());
        run = getRunWithText(row.getCells().get(3).getFirstParagraph(), "Fourth");
        msAssert.areEqual(MarkdownUtil.INLINE_CODE_STYLE_NAME, run.getCharacterStyle().getName());

        checkRow(rows.get(2), new String[] {"Only first column\u0007", "\u0007", "\u0007", "\u0007"});
        checkRow(rows.get(3), new String[] {"\u0007", "Only second column\u0007", "\u0007", "\u0007"});
        checkRow(rows.get(4), new String[] {"The following are two completely empty rows:\u0007", "\u0007", "\u0007", "\u0007"});
        checkRow(rows.get(5), new String[] {"\u0007", "\u0007", "\u0007", "\u0007"});
        checkRow(rows.get(6), new String[] {"\u0007", "\u0007", "\u0007", "\u0007"});
        checkRow(rows.get(7), new String[] {"1\u0007", "2\u0007", "3\u0007", "4\u0007"});

        row = rows.get(8);
        checkRow(row, new String[] {"last row\u0007", "\u0007", "\u0007", "\u0007"});
        msAssert.areEqual(ParagraphAlignment.CENTER, row.getCells().get(0).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, row.getCells().get(1).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.LEFT, row.getCells().get(2).getFirstParagraph().getParaPr().getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, row.getCells().get(3).getFirstParagraph().getParaPr().getAlignment());

        Paragraph paragraph = (Paragraph)tables.get(0).getNextSibling();
        checkParagraph(paragraph, "paragraph after table", "Normal");

        msAssert.areEqual(1, tables.getCount());
    }

    /// <summary>
    /// Tests reading markdown document with table inside a Quote.
    /// </summary>
    /// <remarks>
    /// For a moment this is not implemented and tables in Quotes are read into model without Quote style.
    /// </remarks>
    @Test
    public void testTableB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestTableB.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        checkParagraph(paragraph, "Foo", "Normal");

        Table table = (Table)paragraph.getNextSibling();

        msAssert.areEqual(1, table.getRows().getCount());
        checkRow(table.getRows().get(0), new String[] {"bar\u0007"});

        paragraph = (Paragraph)table.getNextSibling();
        checkParagraph(paragraph, "baz bop", "Quote", "Normal");
    }

    /// <summary>
    /// Tests sibling tables.
    /// </summary>
    @Test
    public void testTableC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\TestTableC.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        checkParagraph(paragraph, "Foo", "Normal");

        paragraph = doc.getFirstSection().getBody().getLastParagraph();
        checkParagraph(paragraph, "Bar", "Normal");

        TableCollection tables = doc.getFirstSection().getBody().getTables();

        Table table = tables.get(0);
        msAssert.areEqual(2, table.getRows().getCount());
        checkRow(table.getRows().get(0), new String[] {"1\u0007", "2\u0007"});
        checkRow(table.getRows().get(1), new String[] {"3\u0007", "4\u0007"});

        table = tables.get(1);
        msAssert.areEqual(2, table.getRows().getCount());
        checkRow(table.getRows().get(0), new String[] {"1\u0007"});
        checkRow(table.getRows().get(1), new String[] {"2\u0007"});

        table = tables.get(2);
        msAssert.areEqual(3, table.getRows().getCount());
        checkRow(table.getRows().get(0), new String[] {"1\u0007", "2\u0007", "3\u0007"});
        checkRow(table.getRows().get(1), new String[] {"2\u0007", "2\u0007", "\u0007"});
        checkRow(table.getRows().get(2), new String[] {"3\u0007", "\u0007", "3\u0007"});

        msAssert.areEqual(3, tables.getCount());
    }

    /// <summary>
    /// Tests simple Autolink.
    /// </summary>
    @Test
    public void testAutolinkA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkB.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(6, paras.getCount());

        checkParagraph(paras.get(0),
            "text \u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015 text\r", "Normal");
        checkParagraph(paras.get(1),
            "text \u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015\r", "Normal");
        checkParagraph(paras.get(2),
            "\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015 text\r", "Normal");
        checkParagraph(paras.get(3),
            "text\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015text\r", "Normal");
        checkParagraph(paras.get(4),
            "text\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015\r", "Normal");
        checkParagraph(paras.get(5),
            "\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015text\f", "Normal");
    }

    /// <summary>
    /// Tests simple Autolinks.
    /// </summary>
    @Test
    public void testAutolinkC() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkC.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0),
            "text \u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015 text " +
            "\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015 text\r", "Normal");
        checkParagraph(paras.get(1),
            "text \u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015" +
            "\u0013 HYPERLINK \"http://example.com\" \u0014http://example.com\u0015 text\f", "Normal");
    }

    /// <summary>
    /// Tests Autolinks with Whitespace characters.
    /// </summary>
    @Test
    public void testAutolinkD() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkD.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "<http://example .com>\r", "Normal");
        checkParagraph(paras.get(1), "<http://example.com>\r", "Normal");
        checkParagraph(paras.get(2), "<http://example\t\t.com>\f", "Normal");
    }


    /// <summary>
    /// Tests Autolink without colon and commercial "At".
    /// </summary>
    @Test
    public void testAutolinkF() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkF.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "<http//example.com>\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with colon and backslash not in the schema name.
    /// </summary>
    @Test
    public void testAutolinkG() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkG.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://examp\\\\le.com\" \u0014http://examp\\le.com\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and backslash.
    /// </summary>
    @Test
    public void testAutolinkH() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkH.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "<email@examp\\le.com>\r", "Normal");
        checkParagraph(paras.get(1), "<ema\\il@example.com>\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with incorrect length.
    /// </summary>
    @Test
    public void testAutolinkI() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkI.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(5, paras.getCount());

        checkParagraph(paras.get(0), "<://example.com>\r", "Normal");
        checkParagraph(paras.get(1), "<h:example.com>\r", "Normal");
        checkParagraph(paras.get(2),
            "\u0013 HYPERLINK \"ht://example.com\" \u0014ht://example.com\u0015\r", "Normal");
        checkParagraph(paras.get(3),
            "\u0013 HYPERLINK \"http5678901234567890123456789012://example.com\" " +
            "\u0014http5678901234567890123456789012://example.com\u0015\r", "Normal");
        checkParagraph(paras.get(4), "<http56789012345678901234567890123:example.com>\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong first symbol (must be [a-zA-Z]).
    /// </summary>
    @Test
    public void testAutolinkJ() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkJ.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "<1tr://example.com>\r", "Normal");
        checkParagraph(paras.get(1),
            "\u0013 HYPERLINK \"ammdftfsg://example.com\" \u0014ammdftfsg://example.com\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with colon and schema name with wrong not-first symbol (must be [a-zA-Z0-9.+-]).
    /// </summary>
    @Test
    public void testAutolinkK() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkK.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"h.t0.+-89-r://example.com\" \u0014h.t0.+-89-r://example.com\u0015\r", "Normal");
        checkParagraph(paras.get(1), "<a*mfsg:example.com>\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with commercial "At" and incorrect symbols.
    /// </summary>
    /// <remarks>
    /// ^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@
    /// [a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
    /// </remarks>
    @Test
    public void testAutolinkL() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkL.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(10, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"mailto:email@example.com\" \u0014email@example.com\u0015\r", "Normal");
        checkParagraph(paras.get(1), "<e@mail@example.com>\r", "Normal");
        checkParagraph(paras.get(2), "<email@e@xample.com>\r", "Normal");
        checkParagraph(paras.get(3),
            "\u0013 HYPERLINK \"mailto:eM12.!\" \\l \"$%&'*+/=?^_`{|}~-@example.com\" " +
            "\u0014eM12.!#$%&'*+/=?^_`{|}~-@example.com\u0015\r", "Normal");
        checkParagraph(paras.get(4),
            "\u0013 HYPERLINK \"mailto:email@e-xample.com\" \u0014email@e-xample.com\u0015\r", "Normal");
        checkParagraph(paras.get(5), "<email@-example.com>\r", "Normal");
        checkParagraph(paras.get(6), "<email@example-.com>\r", "Normal");
        checkParagraph(paras.get(7),
            "\u0013 HYPERLINK \"mailto:email@example.c-om\" \u0014email@example.c-om\u0015\r", "Normal");
        checkParagraph(paras.get(8), "<email@example.-com>\r", "Normal");
        checkParagraph(paras.get(9), "<email@example.com->\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with colon and any unexpected symbol after the schema name.
    /// </summary>
    @Test
    public void testAutolinkM() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkM.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://examp@$%5^&\" \\l \"@$56&^%1!\" " +
            "\u0014http://examp@$%5^&#@$56&^%1!\u0015\f", "Normal");
    }




    /// <summary>
    /// Tests non-Autolinks.
    /// </summary>
    @Test
    public void testAutolinkR() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkR.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "<>\r", "Normal");
        checkParagraph(paras.get(1), "e-mail@gmail.com\r", "Normal");
        checkParagraph(paras.get(2), "http://example.com\f", "Normal");
    }

    /// <summary>
    /// Tests Autolink with bookmark.
    /// </summary>
    @Test
    public void testAutolinkS() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkS.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"https://spec.commonmark.org/0.28/\" \\l \"autolinks\" " +
            "\u0014https://spec.commonmark.org/0.28/#autolinks\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that code spans and Autolinks have the same precedence.
    /// </summary>
    @Test
    public void testAutolinkU() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkU.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(4, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://foo.bar.`baz\" \u0014http://foo.bar.`baz\u0015`\r", "Normal");
        checkParagraph(paras.get(1), "<http://foo.bar.baz>\r", "Normal");
        checkParagraph(paras.get(2),
            "\u0013 HYPERLINK \"http://foo.`bar.`baz\" \u0014http://foo.`bar.`baz\u0015\r", "Normal");
        checkParagraph(paras.get(3), "<http://foo.bar.baz>\f", "Normal");
    }

    /// <summary>
    /// Tests Autolinks with closer angle brackets.
    /// </summary>
    @Test
    public void testAutolinkV() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkV.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://foo.bar.baz\" \u0014http://foo.bar.baz\u0015>\r", "Normal");
        checkParagraph(paras.get(1),
            "<\u0013 HYPERLINK \"http://foo.bar.baz\" \u0014http://foo.bar.baz\u0015\r", "Normal");
        checkParagraph(paras.get(2),
            "<\u0013 HYPERLINK \"http://foo.bar.baz\" \u0014http://foo.bar.baz\u0015>\f", "Normal");
    }

    /// <summary>
    /// Tests that backslash-escaped left angle bracket is not valid for Autolink.
    /// </summary>
    @Test
    public void testAutolinkW() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Autolink\\TestAutolinkW.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "<http://uri>\f", "Normal");
    }

    /// <summary>
    /// Tests simple Link.
    /// </summary>
    @Test
    public void testLinkA() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"http://example.com\" \u0014text\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests simple Link with a title.
    /// </summary>
    @Test
    public void testLinkB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkB.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \\o \"title\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1),
            "\u0013 HYPERLINK \"http://example.com\" \\o \"title\" \u0014text\u0015\f", "Normal");
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
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkC.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"http://example.com\" \u0014\u0015\f", "Normal");
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
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkD.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \u0014text\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests Link titles with different quotes.
    /// </summary>
    @Test
    public void testLinkE() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkE.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"http://example.com\" \\o \"title\" \u0014text\u0015\r", "Normal");
        checkParagraph(paras.get(1),
            "\u0013 HYPERLINK \"http://example.com\" \\o \"title\" \u0014text\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests Link with angle brackets for link URI.
    /// </summary>
    @Test
    public void testLinkF() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkF.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"http://example.com\" \u0014text\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests Link titles with backslash-escaped quotes.
    /// </summary>
    @Test
    public void testLinkG() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkG.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(13, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti\\\"tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti'tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(2), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti)tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(3), "[text](http://example.com \"ti\"tle\")\r", "Normal");
        checkParagraph(paras.get(4), "[text](http://example.com 'ti'tle')\r", "Normal");
        checkParagraph(paras.get(5), "[text](http://example.com (ti)tle))\r", "Normal");
        checkParagraph(paras.get(6), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti'tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(7), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti\\\"tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(8), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti\\\"tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(9), "\u0013 HYPERLINK \"http://example.com\" \\o \"ti)tle\" \u0014text\u0015\r",
            "Normal");
        checkParagraph(paras.get(10), "[link](/url \"title \"and\" title\")\r", "Normal");
        checkParagraph(paras.get(11), "\u0013 HYPERLINK \"/url\" \\o \"title \\\"and\\\" title\" \u0014link\u0015\r",
            "Normal");
        checkParagraph(paras.get(12), "[text](http://example.com (ti(tle))\f",
            "Normal");
    }

    /// <summary>
    /// Tests that the Link destination cannot contain spaces or line breaks, except spaces enclosed in pointy brackets.
    /// </summary>
    @Test
    public void testLinkH() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkH.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(4, paras.getCount());

        checkParagraph(paras.get(0), "[link](/my uri)\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"/my%20uri\" \u0014link\u0015", "Normal");
        checkParagraph(paras.get(2), "[link](foo bar)\r", "Normal");
        checkParagraph(paras.get(3), "[link](<foo bar>)\f", "Normal");
    }

    /// <summary>
    /// Tests that parentheses inside the Link destination may be escaped.
    /// </summary>
    @Test
    public void testLinkI() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkI.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"(foo)\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that any number of parentheses in the Link target are allowed without escaping,
    /// as long as they are balanced.
    /// </summary>
    @Test
    public void testLinkJ() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkJ.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"foo(and(bar))\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "[link](foo(and(bar))\f", "Normal");
    }

    /// <summary>
    /// Tests that if Link have unbalanced parentheses,
    /// they need to be escaped or used with the angle brackets.
    /// </summary>
    @Test
    public void testLinkK() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkK.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"foo(and(bar)\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"foo(and(bar)\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that parentheses and other symbols in the Link can also be escaped, as usual in Markdown.
    /// </summary>
    @Test
    public void testLinkL() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkL.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"foo):\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1),
            "\u0013 HYPERLINK \"'.+-=*_%60~:!\" \\l \"$%25&@/?%5E%7C;\\\\%22%3C%3E%5B%5D()%7B%7D\" \u0014link\u0015\f",
            "Normal");
    }

    /// <summary>
    /// Tests that Links can contain fragment identifiers and queries.
    /// </summary>
    @Test
    public void testLinkM() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkM.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \\l \"fragment\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"http://example.com\" \\l \"fragment\" \u0014link\u0015\r",
            "Normal");
        checkParagraph(paras.get(2),
            "\u0013 HYPERLINK \"http://example.com?foo=3\" \\l \"frag\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that a backslash before a non-escapable character in the Link is just a backslash.
    /// </summary>
    @Test
    public void testLinkN() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkN.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"foo\\\\bar\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"foobar\" \u0014li\\nk\u0015\r", "Normal");
        checkParagraph(paras.get(2), "\u0013 HYPERLINK \"foo\" \\o \"b\\\\ar\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that URL-escaping should be left alone inside the Link destination,
    /// as all URL-escaped characters are also valid URL characters.
    /// </summary>
    @Test
    public void testLinkO() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkO.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"foo%20b&auml;\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that, because titles can often be parsed as Link destinations,
    /// if you try to omit the destination and keep the title, you’ll get unexpected results.
    /// </summary>
    @Test
    public void testLinkP() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkP.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"%22title%22\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that backslash escapes and entity and numeric character references may be used in Link titles.
    /// </summary>
    @Test
    public void testLinkQ() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkQ.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "\u0013 HYPERLINK \"/url\" \\o \"title \\\"&quot;\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that whitespace is allowed around the Link destination and title.
    /// </summary>
    @Test
    public void testLinkR() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkR.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \\o \"title\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that whitespace is not allowed between the Link text and the following parenthesis.
    /// </summary>
    @Test
    public void testLinkS() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkS.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "[link] (/uri)\f", "Normal");
    }

    /// <summary>
    /// Tests that the Link text may contain balanced brackets, but not unbalanced ones, unless they are escaped.
    /// </summary>
    @Test
    public void testLinkT() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkT.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(4, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \u0014link [foo [bar]]\u0015\r", "Normal");
        checkParagraph(paras.get(1), "[link] bar](/uri)\r", "Normal");
        checkParagraph(paras.get(2), "[link \u0013 HYPERLINK \"/uri\" \u0014bar\u0015\r", "Normal");
        checkParagraph(paras.get(3), "\u0013 HYPERLINK \"/uri\" \u0014link [bar\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that the Link text may contain inline content.
    /// </summary>
    @Test
    public void testLinkU() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkU.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \u0014link foo bar #\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"/uri\" \u0014\u0015\f", "Normal");
        Shape shape = (Shape)paras.get(1).getChild(NodeType.SHAPE, 0, false);
        checkShape(shape, "moon", "moon.jpg");
    }

    /// <summary>
    /// Tests that Links may not contain other Links, at any level of nesting.
    /// </summary>
    @Test
    public void testLinkV() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkV.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "[foo \u0013 HYPERLINK \"/uri\" \u0014bar\u0015](/uri)\r", "Normal");
        checkParagraph(paras.get(1), "[foo [bar \u0013 HYPERLINK \"/uri\" \u0014baz\u0015](/uri)](/uri)\r", "Normal");
        checkShape(ms.as(paras.get(2).getChildNodes(NodeType.ANY, false).get(0), Shape.class), "[foo](uri2)", "uri3");
    }

    /// <summary>
    /// Tests the precedence of Link text grouping over emphasis grouping.
    /// </summary>
    @Test
    public void testLinkW() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkW.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "*\u0013 HYPERLINK \"/uri\" \u0014foo*\u0015\r", "Normal");
        checkParagraph(paras.get(1), "\u0013 HYPERLINK \"baz*\" \u0014foo *bar\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that brackets that aren’t part of Links do not take precedence.
    /// </summary>
    @Test
    public void testLinkX() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkX.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "foo [bar baz]\f", "Normal");
    }


    /// <summary>
    /// Tests that a Link destination consists of a sequence of characters between angle brackets that contains
    /// no unescaped angle brackets.
    /// </summary>
    @Test
    public void testLinkZ01() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkZ01.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(8, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \u0014foo\u0015\r", "Normal");
        checkParagraph(paras.get(1), "[foo](</uri>)\r", "Normal");
        checkParagraph(paras.get(2), "\u0013 HYPERLINK \"%3C/uri%3E\" \u0014foo\u0015\r", "Normal");
        checkParagraph(paras.get(3), "\u0013 HYPERLINK \"%3C/uri%3E\" \u0014foo\u0015\r",  "Normal");
        checkParagraph(paras.get(4), "[foo](ri>)\r", "Normal");
        checkParagraph(paras.get(5), "\u0013 HYPERLINK \"/u%3Eri\" \u0014foo\u0015\r", "Normal");
        checkParagraph(paras.get(6), "[foo](</u<ri>)\r", "Normal");
        checkParagraph(paras.get(7), "\u0013 HYPERLINK \"/u%3Eri\" \u0014foo\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that a Link titles may span multiple lines.
    /// </summary>
    @Test
    public void testLinkZ02() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkZ02.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \\o \"ti\u000btle\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests an empty Link title.
    /// </summary>
    @Test
    public void testLinkZ03() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkZ03.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests edge conditions during the Link parsing.
    /// </summary>
    @Test
    public void testLinkZ04() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkZ04.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(6, paras.getCount());

        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/uri\" \\o \"title\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(1), "[link](/uri \"title\" w)\r", "Normal");
        checkParagraph(paras.get(2), "[link](/uri \"title\" ww)\r", "Normal");
        checkParagraph(paras.get(3), "\u0013 HYPERLINK \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(4), "\u0013 HYPERLINK \"/uri\" \u0014link\u0015\r", "Normal");
        checkParagraph(paras.get(5), "\u0013 HYPERLINK \"/uri\" \u0014link\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests that backslash-escaped left square bracket is not valid for Link.
    /// </summary>
    @Test
    public void testLinkZ05() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Link\\TestLinkZ05.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "[foo](/uri)\f", "Normal");
    }


    /// <summary>
    /// Tests collapsed reference link import.
    /// </summary>
    @Test
    public void testReferenceLink_1() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestCRLinkA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkParagraph(paras.get(0), "This is a link \u0013 HYPERLINK \"http://example.com\" \\o \"title\" " +
                                 "\\s \"[here]\" \u0014here\u0015.\r", "Normal");
    }

    /// <summary>
    /// Tests full reference link import.
    /// </summary>
    @Test
    public void testReferenceLink_2() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestFRLinkB.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkParagraph(paras.get(0), "This is a link \u0013 HYPERLINK \"https://www.w3schools.com/\" \\o \"title\" \\s \"[ref]\" " +
                                 "\u0014text\u0015 and \u0013 HYPERLINK \"http://example.com\" \\o \"title1\" \\s \"[ref1]\" \u0014here\u0015 " +
                                 "and \u0013 HYPERLINK \"http://example.com\" \\o \"title1\" \\s \"[ref1]\" \u0014here1\u0015 and \u0013 " +
                                 "HYPERLINK \"http://example.com\" \\o \"title1\" \\s \"[ref1]\" \u0014here2\u0015.\r", "Normal");
    }

    /// <summary>
    /// Tests shortcut reference link import.
    /// </summary>
    @Test
    public void testReferenceLink_3() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestSRLinkA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkParagraph(paras.get(0), "This is a link \u0013 HYPERLINK \"http://example.com\" \\o \"title\" " +
                                 "\\s \"[here]\" \u0014here\u0015.\r", "Normal");
        }

    /// <summary>
    /// Tests importing the multiline reference link definition.
    /// </summary>
    @Test
    public void testReferenceLink_4() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestRDLinkJ.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());
        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/url\" \\o \"the title\" \\s \"[foo]\" \u0014foo\u0015\f", "Normal");
    }

    /// <summary>
    /// Tests importing the link definition with the URL in angle brackets.
    /// </summary>
    @Test
    public void testReferenceLink_5() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestRDLinkL.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());
        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"my%20url\" \\o \"title\" \\s \"[Foo bar]\" \u0014Foo bar\u0015\f",
            "Normal");
    }

    /// <summary>
    /// Tests importing the reference link definition with the multiline comment.
    /// </summary>
    @Test
    public void testReferenceLink_6() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceLinks\\TestRDLinkM.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());
        checkParagraph(paras.get(0), "\u0013 HYPERLINK \"/url\" \\o \"title\r\nline1\r\nline2\" \\s \"[foo]\" \u0014foo\u0015\f",
            "Normal");
    }


    /// <summary>
    /// Tests Image with a title.
    /// </summary>
    @Test
    public void testImageB() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Image\\TestImageB.md");
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
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Image\\TestImageC.md");
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
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Image\\TestImageE.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(1, paras.getCount());

        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "", "/url");
    }



    /// <summary>
    /// Tests collapsed reference image import.
    /// </summary>
    @Test
    public void testReferenceImage_1() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceImages\\TestCRImageA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 1, false), Shape.class), "foo", "/uri", "title");
    }

    /// <summary>
    /// Tests full reference image import.
    /// </summary>
    @Test
    public void testReferenceImage_2() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceImages\\TestFRImageA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 1, false), Shape.class), "Alt text", "/uri", "title");
    }

    /// <summary>
    /// Tests shortcut reference image import.
    /// </summary>
    @Test
    public void testReferenceImage_3() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\ReferenceImages\\TestSRImageA.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 1, false), Shape.class), "foo", "/uri", "title");

    }

    /// <summary>
    /// Tests importing the reference image in Base64.
    /// </summary>
    @Test
    public void testReferenceImage_4() throws Exception
    {
        final String IMAGE_DATA = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGwAAAA4CAYAAAAYeR0sAAAAAXNSR0IArs4c" +
                                 "6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAGVSURBVHhe7ZXbTQNBDACvI3qhMWq" +
                                 "gUUCO7LBsnHCPfdh3M9L8Zm1PoixZeXv/+D6ifgy0wjty6deyfB7R+8xSHQNqvGOJ3pFH6s0k6tjXoT6Ad6" +
                                 "zI1vPrWuehXtA7QmbL3XTlnJw10CvTxSsH9ha6kuUt9DxxINJrw4Qj1DanhSPUMYdGI1Qb7UuvZ22PPeA9j" +
                                 "vvtEo1QfbUfg577GMQa5+FoxBrv7mjEmufmaMSa7+poxIrjv9GIFc+n0YgVU4Il9CEasWJLsITeoxErhwRL" +
                                 "JsGSSbCE3qIRLI8ESybBEnmLxX9YHu/BBKLFl2DJ/BNMIFpcH2IJBIurG0wgWjyfxhIIFsuXsQyixXBVLIN" +
                                 "o890UTCDaHOXum2MZRBvr7lAlVtx7ANvZJFYJ0frZPJZBtLbKPbvFMuwRbwBc55BQNYTb5/BQNYRbp91Jzz" +
                                 "YfG4h4v5Y30TPF5OrhUkTysMHPHq/cU9T1c1Mv5S2eyXIXXfHclAub3mFm680p6hrXxjuM6B2yh97bOhpsw" +
                                 "TtkD/U5ALgoy/IDBM5W/C1nowQAAAAASUVORK5CYII=";

        Document doc = TestUtil.open("ImportMarkdown\\ReferenceImages\\TestFRImageH.md");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 0, false), Shape.class), "foo", IMAGE_DATA, "title");
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 2, false), Shape.class), "foo1", IMAGE_DATA, "title");
        checkShape(ms.as(paras.get(0).getChild(NodeType.ANY, 4, false), Shape.class), "foo2", IMAGE_DATA, "title");
    }

    /// <summary>
    /// Tests a simple Footnote.
    /// </summary>
    @Test
    public void testFootnote_01() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_01", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote \u0002 Some footnote text.\r and definition after two LineBreaks.", "Normal");
        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests Footnote Definition without Footnote reference is ignored.
    /// </summary>
    @Test
    public void testFootnote_02() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_02", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "\f", "Normal");
    }

    /// <summary>
    /// Tests Footnote Reference without Footnote Definition is not allowed.
    /// </summary>
    @Test
    public void testFootnote_03() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_03", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here's a footnote[^10] without definition (shown as simple text).", "Normal");
        checkFootnotes(paras.get(0));
    }

    /// <summary>
    /// Tests that blank line is not required before Footnote definition.
    /// </summary>
    @Test
    public void testFootnote_04() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_04", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here are footnote \u0002 Some footnote text.\r " +
            "and definition after one LineBreak (accepted).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests that multiple Footnote references with the same reference mark can refer to a single definition.
    /// </summary>
    @Test
    public void testFootnote_05() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_05", LoadFormat.MARKDOWN);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Some footnote text.\r, footnote\u0002 Some footnote text.\r ", "Normal");
        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r", "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests that very first of Footnote definitions with the same reference mark will be taken
    /// and all the rest will be ignored.
    /// </summary>
    @Test
    public void testFootnote_06() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_06", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here are footnote\u0002 Some footnote text (line1).\r " +
            "and two definitions with same label (error, accepted line1).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text (line1).\r");
    }

    /// <summary>
    /// Tests that multiline Footnote Definition is treated as a single line when lines are not separated with blank line.
    /// </summary>
    @Test
    public void testFootnote_07() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_07", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote \u0002 Some footnote text (line1). Some footnote text (line2). " +
            "Some footnote text (line3). Some footnote text (line4).\r " +
            "and multiline definition with one LineBreak (accepted as one line).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text (line1). " +
                                 "Some footnote text (line2). " +
                                 "Some footnote text (line3). " +
                                 "Some footnote text (line4).\r");
    }

    /// <summary>
    /// Tests that in order to have a multiple paragraphs in a Footnote Definition,
    /// each line must be indented with at least four space characters and prepended with a blank line.
    /// </summary>
    @Test
    public void testFootnote_08() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_08", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote " +
            "\u0002 Some footnote text (line1).\r" +
            "Some footnote text (line2).\r" +
            "Some footnote text (line3).\r" +
            " and multiline definition with two LineBreaks and indent (accepted).",
            "Normal");

        checkFootnotes(paras.get(0),
            "\u0002 Some footnote text (line1).\rSome footnote text (line2).\rSome footnote text (line3).\r");
    }

    /// <summary>
    /// Tests that when lines in Footnote definition are not indented with at least four space characters,
    /// they will not treated as a part of this definition.
    /// </summary>
    @Test
    public void testFootnote_09() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_09", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "Here are footnote \u0002 Some footnote text (line1).\r " +
            "and multiline definition with two LineBreaks and without indent (accepted line1).", "Normal");
        checkParagraph(paras.get(1), "Some footnote text (line2).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text (line1).\r");
    }

    /// <summary>
    /// Tests that Footnote Definition cannot have a space character in the reference mark.
    /// </summary>
    @Test
    public void testFootnote_10() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_10", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(6, paras.getCount());

        checkParagraph(paras.get(0), "Here are wrong footnote[^ e] and wrong definition (simple text).", "Normal");
        checkParagraph(paras.get(1), "[^ e]: Some footnote text (simple text).", "Normal");
        checkParagraph(paras.get(2), "Here are wrong footnote[^e f] and wrong definition (simple text).", "Normal");
        checkParagraph(paras.get(3), "[^e f]: Some footnote text (simple text).", "Normal");
        checkParagraph(paras.get(4), "Here are wrong footnote[^e ] and wrong definition (simple text).", "Normal");
        checkParagraph(paras.get(5), "[^e ]: Some footnote text (simple text).", "Normal");
    }

    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition with a valid symbols in the label.
    /// </summary>
    @Test
    public void testFootnote_11() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_11", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Some footnote text.\r and definition (accepted).", "Normal");
        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests that InlineCode delimiters have a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_12() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_12", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are wrong footnote[^t] and wrong definition (simple text and code).", "Normal");
        checkParagraph(paras.get(1), "[^t]: Some footnote text (simple text and code).", "Normal");
    }


    /// <summary>
    /// Tests that InlineLink title has a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_14() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_14", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are \u0013 HYPERLINK \"/uri\" \\o \"titl[^pe\" \u0014link with title\u0015], " +
            "footnote\u0002 Some footnote text.\r and definition (accepted).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// Tests how the simple text is parsed after the multiline definition.
    /// </summary>
    @Test
    public void testFootnote_15() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_15", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Some footnote text (para1).\rSome footnote text (para2).\r" +
            ", multiline definition and simple text.", "Normal");
        checkParagraph(paras.get(1), "Simple text.", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text (para1).\rSome footnote text (para2).\r");
    }

    /// <summary>
    /// Tests two Footnote Definitions in a row.
    /// </summary>
    @Test
    public void testFootnote_16() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_16", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Footnote def1.\r, footnote\u0002 Footnote def2.\r" +
            " and two definitions in a row (accepted).", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Footnote def1.\r", "\u0002 Footnote def2.\r");
    }


    /// <summary>
    /// Tests IndentedCode in Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_18() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_18", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here is footnote" +
                                 "\u0002 Some footnote text (para1).\r" +
                                 "    Some indented footnote text (para2).\r" +
                                 " with IndentedCode in definition.\f", "Normal");

        NodeCollection footnotes = checkFootnotes(paras.get(0),
            "\u0002 Some footnote text (para1).\r    Some indented footnote text (para2).\r");

        checkParagraph(((Footnote)footnotes.get(0)).getParagraphs().get(0), "\u0002 Some footnote text (para1).\r", "Footnote Text");
        checkParagraph(((Footnote)footnotes.get(0)).getParagraphs().get(1), "    Some indented footnote text (para2).\r",
            "Footnote Text1", "IndentedCode");
    }

    /// <summary>
    /// Tests an empty Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_19() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_19", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here are footnote\u0002\r and empty definition.", "Normal");
        checkFootnotes(paras.get(0), "\u0002\r");
    }

    /// <summary>
    /// Tests an empty Footnote Definition and followed simple text.
    /// </summary>
    @Test
    public void testFootnote_20() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_20", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());

        checkParagraph(paras.get(0), "Here are footnote\u0002\r, empty definition and simple text.", "Normal");
        checkParagraph(paras.get(1), "Simple text.", "Normal");

        checkFootnotes(paras.get(0), "\u0002\r");
    }

    /// <summary>
    /// Tests a Footnote Reference in italics.
    /// </summary>
    @Test
    public void testFootnote_21() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_21", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        Paragraph para = paras.get(0);

        checkParagraph(para, "Here are footnote in italics \u0002 Some footnote text.\r and definition.", "Normal");

        checkRunAttr(getRunWithText(para, "Here are footnote in italics"), false, false);
        checkRunAttr(getRunWithText(para, "and definition"), false, false);

        NodeCollection footnotes = checkFootnotes(para, "\u0002 Some footnote text.\r");
        msAssert.isTrue(((Footnote)footnotes.get(0)).getFont().getItalic());
    }

    /// <summary>
    /// Tests a Footnote Reference and a simple text in italics.
    /// </summary>
    @Test
    public void testFootnote_22() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_22", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        Paragraph para = paras.get(0);

        checkParagraph(para, "Here are footnote\u0002 Some footnote text.\r and simple text in italics.", "Normal");

        checkRunAttr(getRunWithText(para, "Here are"), false, false);
        checkRunAttr(getRunWithText(para, "footnote"), false, true);
        checkRunAttr(getRunWithText(para, "and"), false, true);
        checkRunAttr(getRunWithText(para, "simple text"), false, false);

        NodeCollection footnotes = checkFootnotes(para, "\u0002 Some footnote text.\r");
        msAssert.isTrue(((Footnote)footnotes.get(0)).getFont().getItalic());
    }

    /// <summary>
    /// Tests a Footnote Reference and a simple text in italics (special cases).
    /// </summary>
    @Test
    public void testFootnote_23() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_23", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        Paragraph para = paras.get(0);

        checkParagraph(para,
            "Here are footnote \u0002 Some footnote text.\r, footnote\u0002 Some footnote text.\r " +
            "and simple text in italics.", "Normal");

        checkRunAttr(getRunWithText(para, "Here are footnote"), false, false);
        Run run = getRunWithText(para, ",");
        checkRunAttr(run, false, true);
        // This is non-italic run with space character between two footnotes in italics.
        checkRun((Run)run.getNextSibling(), " ", false, false);
        checkRunAttr(getRunWithText(para, " and"), false, false);

        NodeCollection footnotes = checkFootnotes(para, "\u0002 Some footnote text.\r", "\u0002 Some footnote text.\r");
        msAssert.isTrue(((Footnote)footnotes.get(0)).getFont().getItalic());
        msAssert.isTrue(((Footnote)footnotes.get(1)).getFont().getItalic());
    }

    /// <summary>
    /// Tests a Footnote Reference with an indented Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_24() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_24", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Some footnote text.\r and indented definition.", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text.\r");
    }


    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition containing list.
    /// </summary>
    /// <remarks>
    /// TODO: Footnote Definition containing list is not implemented.
    /// </remarks>
    @Test
    public void testFootnote_26() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_26", LoadFormat.MARKDOWN);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0),
            "Here are footnote\u0002 Some footnote text. + list item 1. + list item 2.\r" +
            " and definition containing list.", "Normal");

        checkFootnotes(paras.get(0), "\u0002 Some footnote text. + list item 1. + list item 2.\r");
    }

    /// <summary>
    /// Tests that InlineLink text has a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_27() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_27", LoadFormat.MARKDOWN);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        checkParagraph(paras.get(0), "Here is link \u0013 HYPERLINK \"link_dest\" \u0014^1\u0015.", "Normal");
        checkFootnotes(paras.get(0));
    }

    /// <summary>
    /// Tests emphases in Footnote.
    /// </summary>
    @Test
    public void testFootnote_28() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Footnotes\\TestFootnote_28", LoadFormat.MARKDOWN);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(1, paras.getCount());

        // Check paragraph with footnote references.
        Paragraph paragraph = paras.get(0);
        checkRunAttr(getRunWithText(paragraph, "Here is footnote in "), false, false);
        checkRunAttr(getRunWithText(paragraph, "bold"), true, false);
        checkRunAttr(getRunWithText(paragraph, "and footnote in"), false, false);
        checkRunAttr(getRunWithText(paragraph, "italics"), false, true);

        NodeCollection footnotes = checkFootnotes(paragraph,
            "\u0002 Some bold footnote text.\r",
            "\u0002 Some italic footnote\r");

        // Check first footnote.
        Footnote footnote = (Footnote)footnotes.get(0);
        msAssert.isTrue(footnote.getFont().getBold());
        paragraph = footnote.getParagraphs().get(0);
        checkRunAttr(getRunWithText(paragraph, "Some"), false, false);
        checkRunAttr(getRunWithText(paragraph, "bold footnote"), true, false);
        checkRunAttr(getRunWithText(paragraph, "text."), false, false);

        // Check second footnote.
        footnote = (Footnote)footnotes.get(1);
        msAssert.isFalse(footnote.getFont().getItalic());
        paragraph = footnote.getParagraphs().get(0);
        checkRunAttr(getRunWithText(paragraph, "Some"), false, false);
        checkRunAttr(getRunWithText(paragraph, "italic footnote"), false, true);
    }

    /// <summary>
    /// WORDSNET-21680 Missed footnotes during conversion between DOCX and Markdown.
    /// Fixed via implementing the Markdown footnotes.
    /// </summary>
    @Test
    public void test21680() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test21680", LoadFormat.MARKDOWN);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, paras.getCount());

        checkParagraph(paras.get(0), "header\r", "Heading 1");

        checkParagraph(paras.get(1), "Here's a footnote \u0002 Some footnote text.\r.", "Normal");
        checkFootnotes(paras.get(1), "\u0002 Some footnote text.\r");

        checkParagraph(paras.get(2), "Here's a other footnote \u0002 Some footnote text.\r.", "Normal");
        checkFootnotes(paras.get(2), "\u0002 Some footnote text.\r");
    }

    /// <summary>
    /// WORDSNET-19240 Improve markdown emphases parsing.
    /// There was incorrect calculation of the next character of run delimiter
    /// that caused underscore delimiter to be invalid intraword emphasis.
    /// </summary>
    @Test
    public void test19240() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test19240.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();
        checkParagraph(paragraph, "1234\f", "Normal");

        RunCollection runs = paragraph.getRuns();

        checkRun(runs.get(0), "1", true, false);
        checkRun(runs.get(1), "2", true, true);
        checkRun(runs.get(2), "3", false, true);
        checkRun(runs.get(3), "4", false, false);
    }


    /// <summary>
    /// WORDSNET-20703 XML (or DOCX) Document hangs upon loading.
    /// There are a lot of nested AutoLinks in the file that takes a lot of time to parse them. As there cannot be
    /// nested AutoLinks by spec, we can significantly improve performance if will stop to parse such AutoLinks.
    /// </summary>
    @Test
    public void test20703() throws Exception
    {
        Test20703ThreadWorker worker = new Test20703ThreadWorker();
        TestUtil.isHanging(worker, 15);
    }




    /// <summary>
    /// WORDSNET-22787 Introduce ExportImagesAsBase64 option for MD format.
    /// Tests reading the inline base64 image.
    /// </summary>
    @Test
    public void test22787() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Test22787.md");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        msAssert.isTrue(shape.getImageData().hasImage());
        msAssert.isFalse(shape.getImageData().isLinkOnly());
        msAssert.areEqual(42, 0.1, shape.getImageData().getImageSize().getHeightPoints());
        msAssert.areEqual(56, shape.getImageData().getImageSize().getHeightPixels());
        msAssert.areEqual(81, 0.1, shape.getImageData().getImageSize().getWidthPoints());
        msAssert.areEqual(108, shape.getImageData().getImageSize().getWidthPixels());
    }



    /// <summary>
    /// WORDSNET-23746 Markdown document with a SVG image embedded as a data URL.
    /// FOSS Storing an SVG requires rasterizing a PNG fallback (removed rendering subsystem),
    /// so the Markdown reader skips SVG images instead of throwing. They are not added to the model.
    /// </summary>
    @Test
    public void test23746() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Inlines\\Image\\Test23746.md");

        msAssert.areEqual(0, doc.getFirstSection().getBody().getShapes().getCount());
    }

    /// <summary>
    /// WORDSNET-26214 Code block gets corrupted during loading Markdown.
    /// Improved appending markdown blocks in <see cref="FencedCodeBlock.TryAppend"/>.
    /// </summary>
    @Test
    public void test26214() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test26214.md");
        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.isTrue(para.getText().startsWith("*       *\u000b"));
        msAssert.areEqual("FencedCode", para.getParagraphStyle().getName());
    }

    /// <summary>
    /// WORDSNET-26064 Empty lines are lost after importing MD document.
    /// Implemented new option <see cref="MarkdownLoadOptions.PreserveEmptyLines"/>.
    /// </summary>
    @Test (dataProvider = "test26064DataProvider")
    public void test26064(boolean preserveEmptyLines, int expectedEmptyParagraphsCount) throws Exception
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setPreserveEmptyLines(preserveEmptyLines); }
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test26064.md", loadOptions);

        // Check before save.
        Paragraph para = TestUtil.getParagraphWithText(doc, "1 empty line\r");
        msAssert.areEqual(expectedEmptyParagraphsCount, getEmptyParagraphsCount(para, false));

        // Check after save-open.
        doc = TestUtil.saveOpen(doc, msString.format("ImportMarkdown\\Customers\\Test26064_{0}", preserveEmptyLines),
            UnifiedScenario.MD_2_MD, loadOptions);
        para = TestUtil.getParagraphWithText(doc, "1 empty line\r");
        msAssert.areEqual(expectedEmptyParagraphsCount, getEmptyParagraphsCount(para, false));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26064DataProvider")
	public static Object[][] test26064DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  3},
			{false,  0},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-26064.
    /// Tests complex case with simple paragraph, tables, lists and quotes.
    /// </summary>
    @Test
    public void test26064Complex() throws Exception
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setPreserveEmptyLines(true); }
        Document doc = TestUtil.openSaveOpen("ImportMarkdown\\Customers\\Test26064Complex",
            UnifiedScenario.MD_2_MD, loadOptions);

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual(3, getEmptyParagraphsCount(table, true));
        msAssert.areEqual(3, getEmptyParagraphsCount(table, false));

        Paragraph para = TestUtil.getParagraphWithText(doc, "lorem ipsum\r");
        msAssert.areEqual(3, getEmptyParagraphsCount(para, true));
        msAssert.areEqual(3, getEmptyParagraphsCount(para, false));

        para = TestUtil.getParagraphWithText(doc, "item1\r");
        msAssert.areEqual(3, getEmptyParagraphsCount(para, true));
        msAssert.areEqual(3, getEmptyParagraphsCount(para, false));

        para = TestUtil.getParagraphWithText(doc, "quote3\r");
        msAssert.areEqual(3, getEmptyParagraphsCount(para, true));
    }

    /// <summary>
    /// Relates to WORDSNET-26064.
    /// Checks empty lines at very start and end of input file.
    /// </summary>
    @Test (dataProvider = "test26064EmptyLinesDataProvider")
    public void test26064EmptyLines(boolean preserveEmptyLines, String expectedText) throws Exception
    {
        final String INPUT_TEXT = "\nLorem\n\n\nIpsum\n\n";
        MemoryStream inputStream = new MemoryStream(Encoding.getASCII().getBytes(INPUT_TEXT.toCharArray()));
        try /*JAVA: was using*/
        {
            MarkdownLoadOptions loadOptions = new MarkdownLoadOptions(); { loadOptions.setPreserveEmptyLines(preserveEmptyLines); }
            Document doc = new Document(inputStream, loadOptions);
            msAssert.areEqual(expectedText, doc.getFirstSection().getBody().getText());
        }
        finally { if (inputStream != null) inputStream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test26064EmptyLinesDataProvider")
	public static Object[][] test26064EmptyLinesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  "\rLorem\r\r\rIpsum\r\f"},
			{false,  "Lorem\rIpsum\f"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-26064.
    /// Checks, that <see cref="MarkdownLoadOptions"/> is created with <see cref="LoadFormat.Markdown"/> by default.
    /// </summary>
    @Test
    public void test26064LoadFormat()
    {
        MarkdownLoadOptions markdownLoadOptions = new MarkdownLoadOptions();
        msAssert.areEqual(LoadFormat.MARKDOWN, markdownLoadOptions.getLoadFormat());
    }

    /// <summary>
    /// WORDSNET-26368 Add resilience by ignoring Spaces at start/end of each Row during Markdown Table import.
    /// Spaces before and after the table have been taken into account.
    /// </summary>
    @Test
    public void test26368() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test26368.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(2, tables.getCount());
    }

    /// <summary>
    /// WORDSNET-26451 Paragraph in the table has heading style after importing MD.
    /// Should reset paragraph style to 'Normal' before writing Table block into model
    /// in <see cref="Aspose.Words.RW.Markdown.Reader.MarkdownReaderContext.Open"/>.
    /// </summary>
    @Test
    public void test26451() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test26451.md");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Paragraph para = table.getFirstRow().getFirstCell().getFirstParagraph();

        msAssert.areEqual("Normal", para.getParagraphStyle().getName());
    }

    /// <summary>
    /// Tests loading of HTML elements nested to other inline blocks.
    /// </summary>
    @Test
    public void testHtmlNestedToInline() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlNestedToInline.md");
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(2, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("qw", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("erty", run.getText());
        msAssert.isTrue(run.getFont().getBold());

        paragraph = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(2, paragraph.getRuns().getCount());
        run = paragraph.getRuns().get(0);
        msAssert.areEqual("gh", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("jk", run.getText());
        msAssert.isFalse(run.getFont().getBold());
    }

    /// <summary>
    /// Tests loading of nested HTML elements.
    /// </summary>
    @Test
    public void testHtmlNested() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlNested.md");
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("q", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        msAssert.isFalse(run.getFont().getItalic());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("we", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        msAssert.isFalse(run.getFont().getItalic());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("rty", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        msAssert.isTrue(run.getFont().getItalic());

        paragraph = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        run = paragraph.getRuns().get(0);
        msAssert.areEqual("g", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        msAssert.isTrue(run.getFont().getItalic());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("h", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        msAssert.isTrue(run.getFont().getItalic());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("jk", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        msAssert.isFalse(run.getFont().getItalic());
    }

    /// <summary>
    /// Tests loading of HTML 'br' element.
    /// </summary>
    @Test
    public void testHtmlBr() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlBr.md");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual(2, paras.getCount());
        msAssert.areEqual("qwerty\r", paras.get(0).getText());
        msAssert.areEqual("ghjk\f", paras.get(1).getText());
    }

    /// <summary>
    /// Tests loading of HTML 'b' element.
    /// </summary>
    @Test
    public void testHtmlBold() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlBold.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.isFalse(run.getFont().getBold());
    }

    /// <summary>
    /// Tests loading of HTML 'i' element.
    /// </summary>
    @Test
    public void testHtmlItalic() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlItalic.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getItalic());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.isTrue(run.getFont().getItalic());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.isFalse(run.getFont().getItalic());
    }

    /// <summary>
    /// Tests loading of HTML 'u' element.
    /// </summary>
    @Test
    public void testHtmlUnderline() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlUnderline.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.areEqual(Underline.NONE, run.getFont().getUnderline());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.areEqual(Underline.SINGLE, run.getFont().getUnderline());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.areEqual(Underline.NONE, run.getFont().getUnderline());
    }

    /// <summary>
    /// Tests loading of HTML 'strong' element.
    /// </summary>
    @Test
    public void testHtmlStrong() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlStrong.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getBold());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.isTrue(run.getFont().getBold());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.isFalse(run.getFont().getBold());
    }

    /// <summary>
    /// Tests loading of HTML 'sup' element.
    /// </summary>
    @Test
    public void testHtmlSup() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlSup.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getSuperscript());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.isTrue(run.getFont().getSuperscript());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.isFalse(run.getFont().getSuperscript());
    }

    /// <summary>
    /// Tests loading of HTML 'sub' element.
    /// </summary>
    @Test
    public void testHtmlSub() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlSub.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getSubscript());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.isTrue(run.getFont().getSubscript());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.isFalse(run.getFont().getSubscript());
    }

    /// <summary>
    /// Tests loading of HTML 'code' element.
    /// </summary>
    @Test
    public void testHtmlCode() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlCode.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(3, paragraph.getRuns().getCount());
        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.areEqual("Default Paragraph Font", run.getFont().getStyle().getName());
        run = paragraph.getRuns().get(1);
        msAssert.areEqual("4567", run.getText());
        msAssert.areEqual("InlineCode", run.getFont().getStyle().getName());
        run = paragraph.getRuns().get(2);
        msAssert.areEqual("890", run.getText());
        msAssert.areEqual("Default Paragraph Font", run.getFont().getStyle().getName());
    }

    /// <summary>
    /// Tests loading of HTML 's', 'strike', 'del' elements.
    /// </summary>
    @Test
    public void testHtmlStrike() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlStrike.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(7, paragraph.getRuns().getCount());

        Run run = paragraph.getRuns().get(0);
        msAssert.areEqual("123", run.getText());
        msAssert.isFalse(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(1);
        msAssert.areEqual("abcd", run.getText());
        msAssert.isTrue(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(2);
        msAssert.areEqual("56", run.getText());
        msAssert.isFalse(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(3);
        msAssert.areEqual("zxcvb", run.getText());
        msAssert.isTrue(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(4);
        msAssert.areEqual("8", run.getText());
        msAssert.isFalse(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(5);
        msAssert.areEqual("ghjkl", run.getText());
        msAssert.isTrue(run.getFont().getStrikeThrough());

        run = paragraph.getRuns().get(6);
        msAssert.areEqual("0", run.getText());
        msAssert.isFalse(run.getFont().getStrikeThrough());
    }

    /// <summary>
    /// Tests loading of HTML unknown element 'z'.
    /// </summary>
    @Test
    public void testHtmlUnknown() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestHtmlUnknown.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual(5, paragraph.getRuns().getCount());
        msAssert.areEqual("123", paragraph.getRuns().get(0).getText());
        msAssert.areEqual("<z>", paragraph.getRuns().get(1).getText());
        msAssert.areEqual("4567", paragraph.getRuns().get(2).getText());
        msAssert.areEqual("</z>", paragraph.getRuns().get(3).getText());
        msAssert.areEqual("890", paragraph.getRuns().get(4).getText());
    }

    /// <summary>
    /// Tests loading of HTML inline element inside Quote.
    /// </summary>
    @Test
    public void testHtmlInsideQuote() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestInsideQuote.md");

        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual("aaa\r", paragraph.getText());
        Style style = paragraph.getParagraphStyle();
        msAssert.areEqual("Quote", style.getName());
        msAssert.areEqual("Heading 1", style.getBaseStyleName());

        paragraph = doc.getFirstSection().getBody().getParagraphs().get(1);
        msAssert.areEqual("bbb\f", paragraph.getText());
        style = paragraph.getParagraphStyle();
        msAssert.areEqual("Quote1", style.getName());
        msAssert.areEqual("Heading 6", style.getBaseStyleName());
    }

    /// <summary>
    /// Tests loading of HTML inline element inside List.
    /// </summary>
    @Test
    public void testHtmlInsideList() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestInsideList.md");

        Paragraph paragraph = doc.getFirstSection().getBody().getParagraphs().get(0);
        msAssert.areEqual("aaa\f", paragraph.getText());
        Style style = paragraph.getParagraphStyle();
        msAssert.areEqual("Heading 1", style.getName());
        msAssert.areEqual("Normal", style.getBaseStyleName());

        doc.updateListLabels();
        msAssert.areEqual("1.", paragraph.getListLabel().getLabelString());
    }

    /// <summary>
    /// Tests loading of HTML inline element inside InlineCode.
    /// </summary>
    @Test
    public void testHtmlInsideInlineCode() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\InlineHtml\\TestInsideInlineCode.md");

        RunCollection runs = doc.getFirstSection().getBody().getParagraphs().get(0).getRuns();
        msAssert.areEqual(5, runs.getCount());

        msAssert.areEqual("<b>aaa</b>", runs.get(0).getText());
        msAssert.areEqual("InlineCode", runs.get(0).getFont().getStyle().getName());

        msAssert.areEqual(", ", runs.get(1).getText());
        msAssert.areEqual("Default Paragraph Font", runs.get(1).getFont().getStyle().getName());

        msAssert.areEqual("<br>", runs.get(2).getText());
        msAssert.areEqual("InlineCode", runs.get(2).getFont().getStyle().getName());

        msAssert.areEqual(", ", runs.get(3).getText());
        msAssert.areEqual("Default Paragraph Font", runs.get(3).getFont().getStyle().getName());

        msAssert.areEqual("<i>bbb</i>", runs.get(4).getText());
        msAssert.areEqual("InlineCode", runs.get(4).getFont().getStyle().getName());
    }

    /// <summary>
    /// WORDSNET-27290 HTML is not interpreted while loading Markdown.
    /// Implemented loading of raw HTML into markdown.
    /// </summary>
    @Test
    public void test27290() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test27290.md");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Cell cell = table.getFirstRow().getFirstCell();

        msAssert.areEqual(2, cell.getParagraphs().getCount());
        msAssert.areEqual("你是谁\r", cell.getParagraphs().get(0).getText());
        msAssert.areEqual("我是你\u0007", cell.getParagraphs().get(1).getText());
    }


    /// <summary>
    /// WORDSNET-27318 Investigate whether text between ++ should be underlined.
    /// Implemented a new public option <see cref="MarkdownLoadOptions.ImportUnderlineFormatting"/>.
    /// </summary>
    @Test (dataProvider = "test27318DataProvider")
    public void test27318(boolean isImportUnderlineFormatting) throws Exception
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions();
        loadOptions.setImportUnderlineFormatting(isImportUnderlineFormatting);
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test27318.md", loadOptions);

        Run run = TestUtil.getRunWithText(doc.getFirstSection().getBody().getLastParagraph(), "...12...");
        msAssert.areEqual(isImportUnderlineFormatting ? Underline.SINGLE : Underline.NONE, run.getFont().getUnderline());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27318DataProvider")
	public static Object[][] test27318DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-27318.
    /// Tests <see cref="MarkdownLoadOptions.ImportUnderlineFormatting"/> option default value.
    /// </summary>
    @Test
    public void test27318Default() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test27318Default.md");

        Run run = TestUtil.getRunWithText(doc.getFirstSection().getBody().getLastParagraph(), "...12...");
        msAssert.areEqual(Underline.NONE, run.getFont().getUnderline());
    }

    /// <summary>
    /// WORDSNET-28225 Soft line break is improperly read from markdown document.
    /// Added a new option <see cref="MarkdownLoadOptions.SoftLineBreakCharacter"/> to control the behavior.
    /// </summary>
    @Test (dataProvider = "test28225DataProvider")
    public void test28225(char softLineBreakChar) throws Exception
    {
        MarkdownLoadOptions loadOptions = new MarkdownLoadOptions();
        loadOptions.setSoftLineBreakCharacter(softLineBreakChar);
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test28255.txt", loadOptions);

        // This is last word of the problematic line of text.
        final String LAST_WORD = "#NULL";
        Run run = TestUtil.getRunWithText(doc.getFirstSection().getBody(), msString.format("...{0}...", LAST_WORD));
        if (softLineBreakChar < ' ')
        {
            Assert.assertTrue(run.isLastChild());
        }
        else
        {
            int nextCharIndex = msString.indexOf(run.getText(), LAST_WORD, StringComparison.ORDINAL) + LAST_WORD.length();
            msAssert.areEqual(softLineBreakChar, run.getText().charAt(nextCharIndex));
        }

        TestUtil.saveCheckGold(doc, msString.format("ImportMarkdown\\Customers\\Test28255_{0}.docx",
            FormatterPal.intToStrX(softLineBreakChar)));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28225DataProvider")
	public static Object[][] test28225DataProvider() throws Exception
	{
		return new Object[][]
		{
			{' '},
			{'\u000b'},
			{'\n'},
			{'X'},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-28225.
    /// Tests a new option with default value.
    /// </summary>
    @Test
    public void test28225Default() throws Exception
    {
        Document doc = TestUtil.open("ImportMarkdown\\Customers\\Test28255Default.txt");

        // This is last word of the problematic line of text immediately followed by a 'SPACE' character.
        final String LAST_WORD = "#NULL ";
        Run run = TestUtil.getRunWithText(doc.getFirstSection().getBody(), msString.format("...{0}...", LAST_WORD));
        msAssert.isNotNull(run);

        TestUtil.saveCheckGold(doc, "ImportMarkdown\\Customers\\Test28255Default.docx");
    }


    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Tests import simple HTML table.
    /// </summary>
    @Test
    public void testHtmlTableSimple() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestHtmlTableSimple.md");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Cell cell = table.getFirstRow().getFirstCell();

        msAssert.areEqual("RMP Version number: \u0007", cell.getText());
    }



    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Tests when there are two consequent HTML tables.
    /// </summary>
    @Test
    public void testTwoHtmlTables() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestTwoHtmlTables.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(2, tables.getCount());

        msAssert.areEqual("1st table \u00071 \u0007\u0007", tables.get(0).getText());
        msAssert.areEqual("2nd table\u00072 \u0007\u0007", tables.get(1).getText());
    }



    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Tests HTML table with unclosed tags ('table', 'td').
    /// </summary>
    @Test
    public void testHtmlTableWithUnclosedTagsA() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestHtmlTableWithUnclosedTagsA.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(1, tables.getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Tests HTML table with unclosed tags (two consequent 'td').
    /// </summary>
    @Test
    public void testHtmlTableWithUnclosedTagsB() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestHtmlTableWithUnclosedTagsB.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(1, tables.getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Tests nested HTML tables.
    /// </summary>
    @Test
    public void testNestedHtmlTables() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestNestedHtmlTables.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(1, tables.getCount());

        // Check nested table.
        tables = tables.get(0).getFirstRow().getLastCell().getTables();
        msAssert.areEqual(1, tables.getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-28605.
    /// Test MarkdownList placed in HTML table, but outside 'td'.
    /// </summary>
    @Test
    public void testHtmlTableWithMisplacedMarkdownList() throws Exception
    {
        Document doc = openSaveOpen("ImportMarkdown\\InlineHtml\\TestHtmlTableWithMisplacedMarkdownList.md");

        TableCollection tables = doc.getFirstSection().getBody().getTables();
        msAssert.areEqual(1, tables.getCount());

        Paragraph para = TestUtil.getParagraphWithText(doc, "...item1...");
        Assert.assertTrue(para.isListItem());
    }

    /// <summary>
    /// Returns count of empty paragraphs before or after a specified Node.
    /// </summary>
    /// <param name="node">The node before or after which to start count.</param>
    /// <param name="isBefore">If true, then count before a specified node. Otherwise, count after it.</param>
    static int getEmptyParagraphsCount(Node node, boolean isBefore)
    {
        int count = 0;
        Node siblingNode = isBefore ? node.getPreviousSibling() : node.getNextSibling();
        while ((siblingNode != null) && (siblingNode.getNodeType() == NodeType.PARAGRAPH) &&
               !((Paragraph)siblingNode).hasChildNodes())
        {
            count++;
            siblingNode = isBefore ? siblingNode.getPreviousSibling() : siblingNode.getNextSibling();
        }

        return count;
    }

    /// <summary>
    /// A worker thread used in the Test20703.
    /// </summary>
    private static class Test20703ThreadWorker extends ThreadPal
    {
        protected /*override*/ void doWork() throws Exception
        {
            LoadOptions loadOptions = new LoadOptions(LoadFormat.MARKDOWN, null, null);
            TestUtil.open("ImportMarkdown\\Customers\\Test20703.xml", loadOptions);
        }
    }

    /// <summary>
    /// The resource loading callback used in Test21685A.
    /// </summary>
    private static class Test21685ResourceLoadingCallback implements IResourceLoadingCallback
    {
        /// <summary>
        /// Called when Aspose.Words loads any external resource.
        /// </summary>
        public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args) throws Exception
        {
            setCount(getCount() + 1)/*Property++*/;
            switch (gStringSwitchMap.of(Path.getFileName(args.getOriginalUri())))
            {
                case /*"serilog_180px.png"*/0:
                    return ResourceLoadingAction.SKIP;
                case /*"gfm_640px.png"*/1:
                {
                    Stream srcImageStream = ResourceUtil.fetchResourceStream("Aspose.Words.Resources.3dBubble.jpg");
                    try /*JAVA: was using*/
                    {
                        byte[] newImage = StreamUtil.copyStreamToByteArray(srcImageStream);
                        args.setData(newImage);
                    }
                    finally { if (srcImageStream != null) srcImageStream.close(); }
                    return ResourceLoadingAction.USER_PROVIDED;
                }
                default:
                    return ResourceLoadingAction.DEFAULT;
            }
        }

        /// <summary>
        /// Gets number of times the resource loading callback is invoked.
        /// </summary>
        int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;
    }

    /// <summary>
    /// Opens document specified in the path as Markdown.
    /// Then performs <see cref="TestUtil.SaveOpen(Aspose.Words.Document,string)"/>.
    /// Checks gold.
    /// </summary>
    private static Document openSaveOpen(String path) throws Exception
	{
		return openSaveOpen(path, SaveFormat.DOCX);
	}

    /// <summary>
    /// Opens document specified in the path as Markdown.
    /// Then performs <see cref="TestUtil.SaveOpen(Aspose.Words.Document,string)"/>.
    /// Checks gold.
    /// </summary>
    private static Document openSaveOpen(String path, /*SaveFormat*/int sf) throws Exception
    {
        // Set format explicitly. Otherwise, it can be wrong detected as, for example, HTML in some cases.
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setLoadFormat(LoadFormat.MARKDOWN);
        Document doc = TestUtil.open(path, loadOptions);

        String saveExt = FileFormatUtil.saveFormatToExtension(sf);
        String savePath = Path.changeExtension(path, saveExt);

        return TestUtil.saveOpen(doc, savePath);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"serilog_180px.png",
		"gfm_640px.png"
	);

}

