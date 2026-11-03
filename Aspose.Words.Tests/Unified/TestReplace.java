// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.DrColor;
import org.testng.annotations.DataProvider;


@Test
public class TestReplace
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests replacement on run boundaries.
    /// </summary>
    @Test
    public void testRunBoundaries() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestRunBoundaries.docx";

        Document doc = TestUtil.open(TEST_NAME);
        int count = doc.getRange().replace("oro", "^r^");
        msAssert.areEqual(1, count);

        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();
        msAssert.areEqual("am", runs.get(0).getText());
        msAssert.areEqual("^r^", runs.get(1).getText());
        msAssert.areEqual("zov", runs.get(2).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests only one paragraph break is replaced with simple text.
    /// </summary>
    @Test
    public void testParagraphBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestParagraphBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("t&ps", "new");
        msAssert.areEqual(1, count);

        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();
        msAssert.areEqual("Firs", runs.get(0).getText());
        msAssert.areEqual("new", runs.get(1).getText());
        msAssert.areEqual("econd", runs.get(2).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that textbox content is replaced.
    /// </summary>
    @Test
    public void testTextbox() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestTextbox.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("t", "(t)");
        msAssert.areEqual(2, count);

        msAssert.areEqual("Some (t)ex(t).\r\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }

    @Test
    public void testMultipleOccurenceRun() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestMultipleOccurenceRun.docx";

        // Test both directions.
        for (/*FindReplaceDirection*/int direction : G_DIRECTIONS)
        {
            Document doc = TestUtil.open(TEST_NAME);

            FindReplaceOptions options = new FindReplaceOptions();
            options.setDirection(direction);
            options.getApplyFont().setBold(true);

            int count = doc.getRange().replace("run", "(RUN)", options);
            msAssert.areEqual(5, count);
            msAssert.areEqual("One (RUN) with multiple occurrence of word (RUN), (RUN), (RUN) and one more (RUN).\f", doc.getText());

            testOutput(doc, "Model\\Replace2\\TestMultipleOccurenceRun.docx");
        }
    }

    /// <summary>
    /// Tests that header textbox content is replaced.
    /// </summary>
    @Test
    public void testHeaderTextbox() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestHeaderTextbox.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("Some", "The");
        msAssert.areEqual(2, count);

        Shape headerTextbox = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getShapes().get(0);
        msAssert.areEqual("The text\r", headerTextbox.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replacement on textbox boundaries.
    /// </summary>
    @Test
    public void testTextboxMixed() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestTextboxMixed.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("Normal textbox", "[replaced]");
        msAssert.areEqual(0, count);

        count = doc.getRange().replace("textbox", "~");
        msAssert.areEqual(1, count);

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace with section break.
    /// </summary>
    @Test
    public void testWithSectionBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithSectionBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("o", "&b");
        msAssert.areEqual(3, count);
        msAssert.areEqual(4, doc.getSections().getCount());

        msAssert.areEqual("am\fr\fz\fv\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }
    /// <summary>
    /// Tests replace with paragraph break.
    /// </summary>
    @Test
    public void testWithParagraphBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithParagraphBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("[p]", "&p");
        msAssert.areEqual(2, count);

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual("All ", pc.get(0).getRuns().get(0).getText());
        msAssert.areEqual(" occurrence needs to be replaced with real paragraph break. ", pc.get(1).getRuns().get(0).getText());
        msAssert.areEqual(" only!", pc.get(2).getRuns().get(0).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace simple text with doubled section break.
    /// </summary>
    @Test
    public void testWithDoubleSectionBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithDoubleSectionBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("o", "&b&b");

        msAssert.areEqual(3, count);

        SectionCollection sc = doc.getSections();
        msAssert.areEqual(7, sc.getCount());
        msAssert.areEqual("am\f", sc.get(0).getText());
        msAssert.areEqual("\f", sc.get(1).getText());
        msAssert.areEqual("r\f", sc.get(2).getText());
        msAssert.areEqual("\f", sc.get(3).getText());
        msAssert.areEqual("z\f", sc.get(4).getText());
        msAssert.areEqual("\f", sc.get(5).getText());
        msAssert.areEqual("v\f", sc.get(6).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace with page break.
    /// </summary>
    @Test
    public void testWithPageBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithPageBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);
        int count = doc.getRange().replace("[page]", "&m");
        msAssert.areEqual(2, count);

        msAssert.areEqual("Test page break insertion.\f\rWe should get 3 page \f document eventually.\r\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace paragraph break with section break.
    /// </summary>
    @Test
    public void testParagraphWithSection() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestParagraphWithSection.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("&p", "&b");
        msAssert.areEqual(4, count);

        SectionCollection sc = doc.getSections();

        msAssert.areEqual(5, sc.getCount());
        msAssert.areEqual("1.\f", sc.get(0).getText());
        msAssert.areEqual("2.\f", sc.get(1).getText());
        msAssert.areEqual("3.\f", sc.get(2).getText());
        msAssert.areEqual("4.\f", sc.get(3).getText());
        // Last paragraph break is also replaced.
        msAssert.areEqual("\f", sc.get(4).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace of two sequential paragraph break with one paragraph break.
    /// </summary>
    @Test
    public void testDoubleParagraphBreaks() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestDoubleParagraphBreaks.docx";

        Document doc = TestUtil.open(TEST_NAME);
        int count = doc.getRange().replace("&p&p", "&p");
        msAssert.areEqual(2, count);

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(3, pc.getCount());
        msAssert.areEqual("1\r", pc.get(0).getText());
        msAssert.areEqual("2\r", pc.get(1).getText());
        msAssert.areEqual("3.\f", pc.get(2).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Copy-pasted from TestReplace to ensure that new method works for such case.
    /// </summary>
    @Test
    public void testReplaceOneRunWithEmpty() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");

        //Replace one run completely with empty string and check its completely deleted (removed from the document).
        //Note this also tests how it works when Range.Replace is executed on the run node itself.
        Run run = (Run)doc.selectSingleNode("//Run[2]");
        msAssert.areEqual(1, run.getRange().replace("Two", ""));

        //Check run was removed from the document.
        msAssert.areEqual(6, doc.selectNodes("//Run").getCount());
        msAssert.areEqual(null, run.getParentNode());

        msAssert.areEqual("", run.getText());
    }

    /// <summary>
    /// Tests that text is replaced in headers/footers.
    /// </summary>
    @Test
    public void testHeaderText() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestHeaderText.docx";

        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("amorozov", "Morozov Alexey&p690034.");
        msAssert.areEqual(3, count);
        msAssert.areEqual("Morozov Alexey\r690034.\rMorozov Alexey\r690034.\rMorozov Alexey\r690034.\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that formatting is applied to new runs.
    /// </summary>
    @Test
    public void testApplyHighlight() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestApplyHighlight.docx";

        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setHighlightColor(Color.YELLOW);

        int count = doc.getRange().replace("the", "the", options);

        msAssert.areEqual(3, count);
        for (Run run : (Iterable<Run>) doc.getFirstSection().getBody().getFirstParagraph().getRuns())
        {
            if ("the".equals(run.getText()))
                msAssert.isNotNull(run.getRunPr().get(FontAttr.HIGHLIGHT_COLOR));
            else
                msAssert.isNull(run.getRunPr().get(FontAttr.HIGHLIGHT_COLOR));
        }

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace two sequential paragraph breaks with run.
    /// </summary>
    @Test
    public void testDoubleParagraphBreaksWithRun() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestDoubleParagraphBreaksWithRun.docx";
        Document doc = TestUtil.open(TEST_NAME);
        int count = doc.getRange().replace("&p&p", "[double-break]");

        msAssert.areEqual(4, count);
        msAssert.areEqual("Sample[double-break]text[double-break]with[double-break]few[double-break]whitespaces.\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that text is replace inside of table cells.
    /// </summary>
    @Test
    public void testCellText() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestCellText.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("C", "Cell");
        msAssert.areEqual(4, count);

        for (Row row : (Iterable<Row>) doc.getFirstSection().getBody().getTables().get(0).getRows())
            for (Cell cell : (Iterable<Cell>) row.getCells())
                msAssert.isTrue(cell.getFirstParagraph().getFirstRun().getText().startsWith("Cell"));

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests paragraph formatting is applied to new content.
    /// </summary>
    @Test
    public void testApplyParagraphFormat() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestApplyParagraphFormat.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);

        int count = doc.getRange().replace(".&p", ".&p", options);

        msAssert.areEqual(2, count);
        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(ParagraphAlignment.RIGHT, pc.get(0).getParagraphFormat().getAlignment());
        msAssert.areEqual(ParagraphAlignment.LEFT, pc.get(1).getParagraphFormat().getAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, pc.get(2).getParagraphFormat().getAlignment());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace all breaks with run.
    /// </summary>
    @Test
    public void testAllBreaks() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestAllBreaks.docx";

        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setColor(msColor.getOrange());

        doc.getRange().replace("&b", "[section-break]", options);
        doc.getRange().replace("&m", "[page-break]", options);
        doc.getRange().replace("&p", "[paragraph-break]", options);
        doc.getRange().replace("&l", "[line-break]", options);

        msAssert.areEqual("After this word should be section break [section-break], then follows manual page break [page-break], " +
                "after that is paragraph break [paragraph-break] and finally is manual line break [line-break]. This is end of file." +
                "[paragraph-break]\f", doc.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests replace tags with all supported breaks.
    /// </summary>
    @Test
    public void testWithAllBreaks() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithAllBreaks.docx";

        Document doc = TestUtil.open(TEST_NAME);

        doc.getRange().replace("[section-break]", "&b");
        doc.getRange().replace("[page-break]", "&m");
        doc.getRange().replace("[paragraph-break]", "&p");
        doc.getRange().replace("[line-break]", "&l");

        // Check document structure.
        msAssert.areEqual(2, doc.getSections().getCount());
        msAssert.areEqual("After this word should be section break \f", doc.getFirstSection().getBody().getText());

        Section section = doc.getSections().get(1);
        msAssert.areEqual(2, section.getBody().getParagraphs().getCount());
        // Note page break in first paragraph.
        msAssert.areEqual(", then follows manual page break \f, after that is paragraph break \r", section.getBody().getParagraphs().get(0).getText());
        // Note manual line break in second paragraph.
        msAssert.areEqual(" and finally is manual line break \u000b. This is end of file.\f", section.getBody().getParagraphs().get(1).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that section break cannot be inserted to header.
    /// </summary>
    @Test
    public void testWithSectionBreakInHeader() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithSectionBreakInHeader.docx";
        Document doc = TestUtil.open(TEST_NAME);

        msAssert.areEqual(1, doc.getSections().getCount());
        int count = doc.getRange().replace("amorozov", "a&bv");

        // Two matches found but no replacement actually made.
        msAssert.areEqual(2, count);

        // Still one section.
        msAssert.areEqual(1, doc.getSections().getCount());

        msAssert.areEqual("av\r", doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that section break cannot be inserted into cell.
    /// </summary>
    @Test
    public void testWithSectionBreakInCell() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithSectionBreakInCell.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("section break", "&b");

        // We returned actual match.
        msAssert.areEqual(3, count);

        // Only one section break was inserted.
        msAssert.areEqual(2, doc.getSections().getCount());

        // Text is removed but break is not inserted.
        msAssert.areEqual("Try to insert .\u0007", doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests that formatting is taken from matched run.
    /// </summary>
    @Test
    public void testRunFormattingCopied() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestRunFormattingCopied.docx";

        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("amorozov", "amorozov");
        msAssert.areEqual(1, count);

        Paragraph p = doc.getFirstSection().getBody().getFirstParagraph();

        msAssert.areEqual(2, p.getRuns().getCount());

        Run secondRun = p.getRuns().get(1);
        msAssert.areEqual("amorozov", secondRun.getText());
        msAssert.areEqual(96, p.getRuns().get(1).getRunPr().get(FontAttr.SIZE));

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests section break replacement.
    /// </summary>
    @Test
    public void testSectionBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestSectionBreak.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("&b", "[here-was-section]");

        msAssert.areEqual(1, count);
        msAssert.areEqual(1, doc.getSections().getCount());
        msAssert.areEqual(1, doc.getFirstSection().getBody().getParagraphs().getCount());
        msAssert.areEqual("Section break [here-was-section]deletion\f", doc.getFirstSection().getBody().getFirstParagraph().getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests comment text replacement.
    /// </summary>
    @Test
    public void testCommentText() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestCommentText.docx";
        Document doc = TestUtil.open(TEST_NAME);

        int count = doc.getRange().replace("To be replaced!", "Done.");
        msAssert.areEqual(1, count);

        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        msAssert.areEqual("\u0005Done.\r", comment.getText());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// WORDSNET-1252 Improve Range.Replace to support breaks
    /// </summary>
    @Test
    public void testJira1252() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestJira1252.docx";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("11112222");

        doc.getRange().replace("1111", "EMK3 Sales&p15770 N Dallas Parkway&p");
        doc.getRange().replace("2222", "Dallas, TX 75248&pAttn:  Terry CoulterEMK3 Sales&p");

        msAssert.areEqual(5, doc.getFirstSection().getBody().getParagraphs().getCount());

        testOutput(doc, TEST_NAME);
    }

    /// <summary>
    /// Tests how tag with paragraph break before table is replaced with section break.
    /// </summary>
    @Test
    public void testWithSectionBreakBeforeTable() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWithSectionBreakBeforeTable.docx";
        Document doc = TestUtil.open(TEST_NAME);

        msAssert.areEqual(1, doc.getSections().getCount());
        int count = doc.getRange().replace("[section~break]&p", "&b");

        msAssert.areEqual(1, count);

        // Tag is successfully replaced with section break.
        msAssert.areEqual(2, doc.getSections().getCount());

        testOutput(doc, TEST_NAME);
    }


    /// <summary>
    /// WORDSNET-7456 NullReferenceException occurs when performing a find and replace operation
    /// </summary>
    @Test
    public void testJira7456() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira7456.docx");

        int count = doc.getRange().replace("[~pagebreak~]", "&m");
        msAssert.areEqual(5, count);

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("\f", pc.get(9).getFirstRun().getText());
        msAssert.areEqual("\f", pc.get(13).getFirstRun().getText());
        msAssert.areEqual("\f", pc.get(17).getFirstRun().getText());
        msAssert.areEqual("\f", pc.get(19).getFirstRun().getText());
        msAssert.areEqual("\f", pc.get(22).getFirstRun().getText());
    }

    /// <summary>
    /// Tests replacement of section break if document has only one section.
    /// </summary>
    @Test
    public void testReplaceOnlySection() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello");
        doc.ensureMinimum();

        msAssert.areEqual("Hello\r\f", doc.getText());
        int count = doc.getRange().replace("&b", "");

        msAssert.areEqual(0, count);
        msAssert.areEqual("Hello\r\f", doc.getText());
    }

    /// <summary>
    /// Tests replacement of section break if document has only one section.
    /// </summary>
    @Test
    public void testMergeSections() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Section1");
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);
        builder.writeln("Section2");

        msAssert.areEqual(2, doc.getSections().getCount());

        int count = doc.getRange().replace("&b", "");

        msAssert.areEqual(1, count);
        msAssert.areEqual(1, doc.getSections().getCount());

        testOutput(doc, "Model\\Replace2\\TestMergeSections.docx");
    }


    /// <summary>
    /// Section break is inside structured document tag.
    /// </summary>
    @Test
    public void testJira4826() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestDefect4826.docx");

        int count = doc.getRange().replace("&b", "[section-break]");

        msAssert.areEqual(1, count);

        // Section break inside of SDT is removed.
        msAssert.areEqual(1, doc.getSections().getCount());
    }


    /// <summary>
    /// Tests Wiki demonstration sample.
    /// </summary>
    /// <remarks>
    /// Demonstrates break support.
    /// </remarks>
    @Test
    public void testWiki01() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestWiki01.docx";
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setName("Arial");
        builder.writeln("First section");
        builder.writeln("  1st paragraph");
        builder.writeln("  2nd paragraph");
        builder.writeln("{insert-section}");
        builder.writeln("Second section");
        builder.writeln("  1st paragraph");

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        // Double each paragraph break after word "section", add dot to end of paragraph and make it centered.
        int count = doc.getRange().replace("section&p", "section.&p&p", options);
        msAssert.areEqual(2, count);

        // Insert section break instead of custom text tag.
        count = doc.getRange().replace("{insert-section}", "&b", options);
        msAssert.areEqual(1, count);

        testOutput(doc, TEST_NAME);
    }
    /// <summary>
    /// Tests code sample from public API comment.
    /// </summary>
    @Test
    public void testCodeSample_1() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("Numbers 1, 2, 3");

        // Inserts paragraph break after Numbers.
        doc.getRange().replace("Numbers", "Numbers&p");

        msAssert.areEqual("Numbers\r 1, 2, 3\r\f", doc.getText());
    }


    /// <summary>
    /// WORDSNET-15046 Range.Replace finds and replaces text in the incorrect order
    /// FindReplace method is updated to does replacement in table along with the document body.
    /// </summary>
    @Test
    public void testJira15046() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15046.docx");
        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setHighlightColor(Color.YELLOW);
        options.setReplacingCallback(new TestJira15046Callback());
        doc.getRange().replace("tag", "", options);

        msAssert.areEqual("Tag#0\rTag#1\rtag#2\u0007tag#3\u0007\u0007\rTag#4\r\f", doc.getText());
    }

    static class TestJira15046Callback implements IReplacingCallback
    {
        private int mIndex;
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            args.setReplacement(msString.format("{0}#{1}", args.getMatchInternal().getValue(), mIndex++));
            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// Relates to WORDSNET-14046
    /// Tests that content of adjacent cell in not joined to be replaced.
    /// </summary>
    @Test
    public void testJira15046A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15046A.docx");

        // No replacement should be made.
        int count = doc.getRange().replace("12", "XX");
        msAssert.areEqual(0, count);
    }


    /// <summary>
    /// WORDSNET-15869 RTL text with spaces reversed after replacement
    /// Seems that we need to allow white-spaces when decide to force Bidi for strong RTL replacement.
    /// </summary>
    @Test
    public void testJira15869() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15869.doc");
        doc.getRange().replace("IIFirstNameII", "ראשון שני");

        msAssert.areEqual(AttrBoolEx.TRUE, doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getRunPr().get(FontAttr.BIDI));
    }

    /// <summary>
    /// Tests sorting headers/footers in the node pending list.
    /// </summary>
    @Test
    public void testSortingPendingList() throws Exception
    {
        Document doc = new Document();
        ArrayList<Node> testList = new ArrayList<Node>();

        Comment comment1 = new Comment(doc);
        msArrayList.add(testList, comment1);

        Section section1 = new Section(doc);
        section1.getPageSetup().setDifferentFirstPageHeaderFooter(true);
        HeaderFooter firstHeader1 = new HeaderFooter(doc, HeaderFooterType.HEADER_FIRST);
        HeaderFooter primaryHeader1 = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        HeaderFooter evenHeader1 = new HeaderFooter(doc, HeaderFooterType.HEADER_EVEN);
        HeaderFooter firstFooter1 = new HeaderFooter(doc, HeaderFooterType.FOOTER_FIRST);
        HeaderFooter primaryFooter1 = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        HeaderFooter evenFooter1 = new HeaderFooter(doc, HeaderFooterType.FOOTER_EVEN);
        appendNodes(new Node[] { evenFooter1, primaryHeader1, firstFooter1, evenHeader1, primaryFooter1,
            firstHeader1 }, section1, testList);

        Comment comment2 = new Comment(doc);
        testList.add(2, comment2);
        Comment comment3 = new Comment(doc);
        testList.add(3, comment3);
        Comment comment4 = new Comment(doc);
        msArrayList.add(testList, comment4);

        Section section2 = new Section(doc);
        section2.getPageSetup().setDifferentFirstPageHeaderFooter(false);
        HeaderFooter primaryHeader2 = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        HeaderFooter evenHeader2 = new HeaderFooter(doc, HeaderFooterType.HEADER_EVEN);
        HeaderFooter primaryFooter2 = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        HeaderFooter evenFooter2 = new HeaderFooter(doc, HeaderFooterType.FOOTER_EVEN);
        appendNodes(new Node[] { evenFooter2, primaryFooter2, evenHeader2, primaryHeader2 }, section2, testList);

        Comment comment5 = new Comment(doc);
        msArrayList.add(testList, comment5);

        FindReplace.sortPendingList(testList);

        Object[] expectedOrder = new Object[] { comment1, firstHeader1, firstFooter1, evenHeader1, evenFooter1,
            primaryHeader1, primaryFooter1, comment2, comment3, comment4, primaryHeader2, primaryFooter2,
            evenHeader2, evenFooter2, comment5 };

        msAssert.areEqual(expectedOrder.length, testList.size());
        for (int i = 0; i < testList.size(); i++ )
            msAssert.areSame(expectedOrder[i], testList.get(i), msString.format("Wrong object at index {0}", i));
    }

    /// <summary>
    /// Appends nodes from the specified array as children into the composite node and into the specified list.
    /// </summary>
    private void appendNodes(Node[] nodes, CompositeNode compositeNode, ArrayList<Node> list)
    {
        for (Node node : nodes)
        {
            compositeNode.appendChildForLoad(node);
            list.add(node);
        }
    }



    /// <summary>
    /// WORDSNET-16189 Range.Replace text within GroupShape does not Replace.
    /// Nodes of "GroupShape" type should not be ignored while processing in <see cref="FindReplaceIndexer"/>.
    /// </summary>
    @Test
    public void testJira16189() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira16189.docx");

        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        {
            findReplaceOptions.setFindWholeWordsOnly(false);
            findReplaceOptions.setMatchCase(false);
            findReplaceOptions.setDirection(FindReplaceDirection.BACKWARD);
        }
        doc.getRange().replace("foo", "bar", findReplaceOptions);

        GroupShape shape = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);

        Run run = (Run)shape.getChild(NodeType.RUN, 0, true);
        msAssert.areEqual("bar", run.getText());
    }

    /// <summary>
    /// Related to WORDSNET-16189
    /// Tests FindReplaceIndexer for GroupShape.
    /// </summary>
    @Test
    public void testJira16189FindReplaceIndexerForGroupShapeTest() throws Exception
    {
        GroupShape shape = new GroupShape(new Document());
        FindReplaceIndexer replaceIndexer = new FindReplaceIndexer(shape, new FindReplaceOptions());

        /*IndexerAction*/int result = replaceIndexer.onNode(shape);
        msAssert.areEqual(IndexerAction.NONE, result);
    }

    /// <summary>
    ///  WORDSNET-15840 ReplacingArgs.Replacement returns incorrect value.
    ///  Added replacement option to preserve meta-characters beginning with "&amp;".
    /// </summary>
    @Test
    public void testJira15840() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("text");

        // WORDSNET-20266 Obsolete FindReplaceOptions.PreserveMetaCharacters option removed.
        // & character should be escaped.
        doc.getRange().replace("text", "&&ldquo;");
        msAssert.areEqual("&ldquo;\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-16272 Add a feature to escape ampersand symbol during replacement
    /// Added && meta character to eascape & character.
    /// </summary>
    @Test
    public void testJira16272() throws Exception
    {
        msAssert.areEqual("&ldquo;\f", doReplace("text", "text", "&&ldquo;"));
        msAssert.areEqual("&replaced\f", doReplace("&&ldquo;", "&&ldquo;", "replaced"));
    }

    private static String doReplace(String text, String what, String with) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write(text);

        doc.getRange().replace(what, with);

        return doc.getText();
    }



    /// <summary>
    /// Relates to WORDSNET-16217
    /// Tests Replace without Regex.
    /// </summary>
    @Test
    public void testJira16217Related() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder docBuilder = new DocumentBuilder(doc);

        docBuilder.writeln("$IMPORT_TEXT");
        docBuilder.writeln("$IMPORT_TEXTAREA");

        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        findReplaceOptions.setFindWholeWordsOnly(true);

        doc.getRange().replace("$IMPORT_TEXT", "testing", findReplaceOptions);
        doc.getRange().replace("$IMPORT_TEXTAREA", "textarea", findReplaceOptions);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        msAssert.areEqual("testing", ((Run)runs.get(0)).getText());
        msAssert.areEqual("textarea", ((Run)runs.get(1)).getText());
    }

    private static void doAsposeDocReplaceText(Document doc, String[] templateFields, String[] values) throws Exception
    {
        FindReplaceOptions options = new FindReplaceOptions();
        options.setMatchCase(false);
        options.setFindWholeWordsOnly(false);
        for (int i = 0; i < values.length; i++)
        {
            doc.getRange().replace(templateFields[i], values[i], options);
        }
    }

    /// <summary>
    /// Useful method to tests replace method.
    /// </summary>
    private static void testOutput(Document doc, String fileName)
    {
        //const string tempPath = @"X:\_1252\";
        //ModelDump.Save(doc, tempPath + Path.GetFileName(fileName) + ".model");
        //TestUtil.SaveOpen(doc, Path.Combine(tempPath, Path.GetFileNameWithoutExtension(fileName)) + " Replaced" + Path.GetExtension(fileName));
    }

    /// <summary>
    /// Test case sensitive and insensitive replace within a single run.
    /// </summary>
    @Test
    public void testReplaceInOneRun() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInOneRun.docx");

        //Test case insensitive replace.
        msAssert.areEqual(3, doc.getRange().replace("text", "Replaced text"));
        msAssert.areEqual("Replaced text in header.\rReplaced text in body. Replaced text in body.\f", doc.getText());

        //Test case sensitive replace.
        FindReplaceOptions options = new FindReplaceOptions(); { options.setMatchCase(true); options.setFindWholeWordsOnly(false); }
        msAssert.areEqual(0, doc.getRange().replace("Text", "XXX", options));
    }

    @Test
    public void testReplaceAcrossTwoRuns() throws Exception
    {
        //This document is used for several tests.
        //
        //   Bold      Underline    Italic
        //One[Two]Three[FourFive]Six[SevenEight]NineTen
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");
        msAssert.areEqual(7, doc.selectNodes("//Run").getCount());

        // Replace text that goes across two runs
        msAssert.areEqual(1, doc.getRange().replace("neTw", "xxx"));

        //First run now contains some of the old plus new text.
        Run run = (Run)doc.selectSingleNode("//Run[1]");
        msAssert.areEqual("O", run.getText());
        msAssert.areEqual(false, run.getFont().getBold());

        // Second run contains replacement.
        run = (Run)doc.selectSingleNode("//Run[2]");
        msAssert.areEqual("xxx", run.getText());
        msAssert.areEqual(false, run.getFont().getBold());

        // Third run now contains only part of the old text.
        run = (Run)doc.selectSingleNode("//Run[3]");
        msAssert.areEqual("o", run.getText());
        msAssert.areEqual(true, run.getFont().getBold());
    }

    @Test
    public void testReplaceAcrossManyRuns() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");

        //One[Two]Three[FourFive]Six[SevenEight]NineTen
        msAssert.areEqual(1, doc.getRange().replace("FiveSixSeven", "xxx"));
        msAssert.areEqual(7, doc.selectNodes("//Run").getCount());

        Run run = (Run)doc.selectSingleNode("//Run[5]");
        msAssert.areEqual("xxx", run.getText());
        msAssert.areEqual(Underline.SINGLE, run.getFont().getUnderline());

        run = (Run)doc.selectSingleNode("//Run[6]");
        msAssert.areEqual("Eight", run.getText());
        msAssert.areEqual(true, run.getFont().getItalic());
    }

    @Test
    public void testReplaceOneRunWithText() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");

        //Replace one run completely with new text and check its formatting stays.
        msAssert.areEqual(1, ((Run)doc.selectSingleNode("//Run[2]")).getRange().replace("Two", "AAA"));
        Run run = (Run)doc.selectSingleNode("//Run[2]");

        //Check run stayed in the document.
        msAssert.areEqual(7, doc.selectNodes("//Run").getCount());
        msAssert.isNotNull(run.getParentNode());

        msAssert.areEqual("AAA", run.getText());
        msAssert.areEqual(true, run.getFont().getBold());
    }

    @Test
    public void testReplaceManyRunsWithEmpty() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");

        //Replace three runs completely with empty string, they should be deleted.
        msAssert.areEqual(1, doc.getRange().replace("TwoThreeFourFive", ""));
        msAssert.areEqual(4, doc.selectNodes("//Run").getCount());
    }

    @Test
    public void testReplaceManyRunsWithText() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceInManyRuns.docx");

        //Replace three runs completely with text and check the formatting stays.
        msAssert.areEqual(1, doc.getRange().replace("TwoThreeFourFive", "AAA"));
        Run run = (Run)doc.selectSingleNode("//Run[2]");
        msAssert.areEqual("AAA", run.getText());
        msAssert.areEqual(true, run.getFont().getBold());
    }

    @Test
    public void testReplaceWholeWord() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceWholeWord.docx");

        //Surrounded by spaces, replaced okay.
        Node para = doc.selectSingleNode("//Paragraph[1]");
        FindReplaceOptions options = new FindReplaceOptions(); { options.setMatchCase(false); options.setFindWholeWordsOnly(true); }
        msAssert.areEqual(1, para.getRange().replace("aaa", "X", options));
        msAssert.areEqual("Some X surrounded by spaces.\r", para.getText());

        //Part of word, not found.
        para = doc.selectSingleNode("//Paragraph[2]");
        options = new FindReplaceOptions(); { options.setMatchCase(false); options.setFindWholeWordsOnly(true); }
        msAssert.areEqual(0, para.getRange().replace("aaa", "X", options));
        msAssert.areEqual("Someaaa part of word.\r", para.getText());

        //In brackets, replaced okay.
        para = doc.selectSingleNode("//Paragraph[3]");
        options = new FindReplaceOptions(); { options.setMatchCase(false); options.setFindWholeWordsOnly(true); }
        msAssert.areEqual(1, para.getRange().replace("aaa", "X", options));
        msAssert.areEqual("Some[X] in brackets.\r", para.getText());

        //At end of sentence, replaced okay.
        para = doc.selectSingleNode("//Paragraph[4]");
        options = new FindReplaceOptions(); { options.setMatchCase(false); options.setFindWholeWordsOnly(true); }
        msAssert.areEqual(1, para.getRange().replace("aaa", "X", options));
        msAssert.areEqual("Some X. At end of sentence.\r", para.getText());
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe search string cannot be null or empty.\\E")
    public void testReplaceEmpty() throws Exception
    {
        Document doc = new Document();
        doc.getRange().replace("", "A");
    }

    @Test (enabled = false)
    public void testReplaceAcrossParagraphUnescaped() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceAcrossParent.docx");
        //There is a way to pass unescaped \r, it is also caught.
        int replacementCount = doc.getRange().replaceInternal(new Regex("Para1\rPara2"), "");
        msAssert.areEqual(0, replacementCount);
    }

    @Test
    public void testReplaceAcrossCell() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceAcrossParent.docx");
        int replacementCount = doc.getRange().replace("Cell1\u0007Cell2", "");
        msAssert.areEqual(0, replacementCount);
    }

    @Test
    public void testReplaceAcrossSection() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceAcrossParent.docx");
        int replacementCount = doc.getRange().replace("Section1\fSection2", "");
        msAssert.areEqual(0, replacementCount);
    }

    /// <summary>
    /// The bookmark start or end that is at the start or end of a match, should stay intact.
    /// </summary>
    @Test
    public void testReplaceWithBookmarkAroundMatch() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceWithBookmarks.docx");
        msAssert.areEqual("Test1Word2", doc.getRange().getBookmarks().get("bmk1").getText());

        doc.getRange().replace("Test1Word2", "xxx");
        //Check the bookmark stayed in the doc.
        msAssert.areEqual("xxx", doc.getRange().getBookmarks().get("bmk1").getText());
    }

    @Test
    public void testReplaceWithBookmarksInsideMatch() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceWithBookmarks.docx");
        msAssert.areEqual("Test2Word1 Test2", doc.getRange().getBookmarks().get("bmk2").getText());
        msAssert.areEqual("Word2 Test2Word3", doc.getRange().getBookmarks().get("bmk3").getText());
        msAssert.areEqual("st2Wor", doc.getRange().getBookmarks().get("bmk4").getText());

        doc.getRange().replace("Test2Word2", "xxx");

        //This actually works exactly like in MS Word.
        msAssert.areEqual("Test2Word1 ", doc.getRange().getBookmarks().get("bmk2").getText());

        //This one is slightly different from how MS Word does it. MS Word will keep " Test2Word3" only.
        msAssert.areEqual("xxx Test2Word3", doc.getRange().getBookmarks().get("bmk3").getText());

        //This is different from how MS Word does it. In MS Word if a bookmark is completely inside
        //a match, it is deleted. I don't want to bother deleting the bookmark completely.
        msAssert.areEqual("", doc.getRange().getBookmarks().get("bmk4").getText());
    }

    /// <summary>
    /// WORDSNET-19439 Paragraph alignment is lost when Range.Replace is used with &p.
    /// Word copies props of the para to the new paras that are created during the replacement. Mimic Word.
    /// </summary>
    @Test
    public void test19439() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test19439.docx");

        FindReplaceOptions fr = new FindReplaceOptions();
        fr.getApplyParagraphFormat().setSpaceAfter(20.0);
        fr.getApplyFont().setUnderline(Underline.DASH);

        doc.getRange().replace("<Replace2>", "This text has&pmore than one line&pand some more", fr);

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        for (int i = 2; i < 4; i++)
        {
            msAssert.areEqual(ParagraphAlignment.CENTER, paras.get(i).getParaPr().getAlignment());
            msAssert.isTrue(paras.get(i).getFirstRun().getFont().getBold());

            // Checks that replacement options work.
            msAssert.areEqual(400, paras.get(i).getParaPr().getSpaceAfter());
            msAssert.areEqual(Underline.DASH, paras.get(i).getFirstRun().getFont().getUnderline());
        }
    }


    /// <summary>
    /// WORDSNET-19543 Bullet list is lost after using Range.Replace with &p special meta-character
    /// We need to copy properties of paragraph being replaced.
    /// </summary>
    @Test
    public void test19543() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\Test19543.docx");

        // Customer case.
        for (Node node : doc.getChildNodes(NodeType.PARAGRAPH, true).toArray())
        {
            Paragraph paragraph = ms.as(node, Paragraph.class);
            paragraph.getRange().replace("&p", "~line-break~&p", new FindReplaceOptions());
        }

        // Check that all paragraphs are bullet now.
        doc.updateListLabels();
        for (Paragraph para : (Iterable<Paragraph>) doc.getFirstSection().getBody().getParagraphs())
        {
            msAssert.areEqual("\uf0b7", para.getListLabel().getLabelString());
        }
    }


    /// <summary>
    /// WORDSNET-20212 Range.Replace does not replace the numbers.
    /// We substitute different text breaks with our own substitution characters from the Unicode private area block.
    /// This causes regex engine to not recognize line breaks properly when RegexOptions.Multiline option is enabled.
    /// Fixed by splitting text onto lines manually when RegexOptions.Multiline option is enabled.
    /// </summary>
    @Test
    public void test20212() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test20212.docx");

        doc.getRange().replaceInternal(new Regex("^[1-9]{1}[0-9]{10}[&p]$", RegexOptions.MULTILINE), "REPLACED&p");

        msAssert.areEqual("REPLACED\rREPLACED\rREPLACED\rREPLACED\rREPLACED\r\f", doc.getText());
    }

    /// <summary>
    /// WORDSNET-20186 Document.Range.Replace regex string anchors not working.
    /// Duplicates WORDSNET-20212
    /// </summary>
    @Test
    public void test20186() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test20186.docx");

        doc.getRange().replaceInternal(new Regex("^Here", RegexOptions.MULTILINE), "REPLACED");

        msAssert.areEqual("REPLACED is the second line.\f", doc.getFirstSection().getBody().getParagraphs().get(1).getText());
    }

    /// <summary>
    /// WORDSNET-21018 Return deprecated replace algorithm for compatibility reason.
    /// Added compatibility flag that allows to call old algorithm version.
    /// </summary>
    @Test
    public void test21018() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.writeln("test");
        Run run = builder.getDocument().getFirstSection().getBody().getParagraphs().get(0).getRuns().get(0);
        FindReplaceOptions replaceOptions = new FindReplaceOptions(); { replaceOptions.setLegacyMode(true); }
        run.getRange().replace("test", "TEST", replaceOptions);
        msAssert.areEqual("TEST", run.getText());
        msAssert.isNotNull(run.getParentNode());
    }

    /// <summary>
    /// WORDSNET-20007 Parent node of Run node returns null after replacing text of Run node
    /// Use existing run for replacement.
    /// </summary>
    @Test
    public void test20007() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.writeln("test");
        Run run = builder.getDocument().getFirstSection().getBody().getParagraphs().get(0).getRuns().get(0);
        run.getRange().replace("test", "TEST");
        msAssert.areEqual("TEST", run.getText());
        msAssert.isNotNull(run.getParentNode());
    }

    /// <summary>
    /// WORDSNET-21588 IReplacingCallback is never executed.
    /// We should calculate offset of matches relative to whole text instead of current line
    /// when passing into callback method. Also don't split text onto lines when there are no
    /// any anchors (^, $) specified in regex pattern.
    /// </summary>
    @Test (dataProvider = "test21588DataProvider")
    public void test21588(String testName, String expectedMatchValue) throws Exception
    {
        Document doc = TestUtil.open(msString.format("Model\\Replace\\{0}", testName));

        Test21588ReplacingCallback replacingCallback = new Test21588ReplacingCallback();
        FindReplaceOptions options = new FindReplaceOptions(); {options.setReplacingCallback(replacingCallback);}
        doc.getRange().replaceInternal(new Regex("({{5{).*(}}5})", RegexOptions.MULTILINE), "", options);

        msAssert.areEqual(expectedMatchValue, replacingCallback.getMatchValue());

        // The main checks are performed in replacing callback method.
        msAssert.areEqual(1, replacingCallback.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21588DataProvider")
	public static Object[][] test21588DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test21588.docx",  "{{5{ Don’t Display  }}5}"},
			{"Test21588_WithBreak.docx",  "{{5{ Don’t Display  }}5}"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-21588
    /// Checks various combinations of anchors with multiline regex.
    /// </summary>
    @Test (dataProvider = "test21588AnchorsDataProvider")
    public void test21588Anchors(String pattern, int expectedCount) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.write("ab\ncd\nb\ndc");

        int count = builder.getDocument().getRange().replaceInternal(new Regex(pattern, RegexOptions.MULTILINE), "");
        msAssert.areEqual(expectedCount, count);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21588AnchorsDataProvider")
	public static Object[][] test21588AnchorsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"b",  2},
			{"^b",  1},
			{"d",  2},
			{"^d",  1},
			{"c",  2},
			{"c&p",  1},
			{"ab&pcd",  1},
			{"d&pb",  1},
		};
	}


    /// <summary>
    /// WORDSNET-21639 Range.Replace throws System.IndexOutOfRangeException when LegacyMode = false.
    /// The actual problem is in FindWholeWordsOnly option. We should check that position of next character
    /// of matched text is not greater than the length of whole text
    /// in <see cref="FindReplace.IsWordBoundary(System.Text.RegularExpressions.Match,int)"/>.
    /// </summary>
    @Test
    public void test21639() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test21639.docx");

        FindReplaceOptions options = new FindReplaceOptions(); {options.setFindWholeWordsOnly(true);}
        msAssert.less(0, doc.getRange().replaceInternal(new Regex(".*"), "", options));
    }

    /// <summary>
    /// WORDSNET-21329 Range.Replace does not Remove CR Character in Cell with Nested Table.
    /// The new <see cref="FindReplaceOptions.SmartParagraphBreakReplacement"/> is introduced that allows to move
    /// all child nodes from the paragraph into NextPreOrder paragraph. This allows to remove the paragraph itself.
    /// </summary>
    @Test (dataProvider = "test21329DataProvider")
    public void test21329(boolean forceReplaceParagraphBreak, /*NodeType*/int expectedNodeType) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test21329.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setSmartParagraphBreakReplacement(forceReplaceParagraphBreak);

        int count = doc.getRange().replaceInternal(new Regex("REMOVE_CR&p"), "", options);

        msAssert.areEqual(expectedNodeType, doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getFirstChild().getNodeType());

        // Count of replacement made equal for both cases regardless no replacement occurred if forceReplaceParagraphBreak = false.
        msAssert.areEqual(1, count);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21329DataProvider")
	public static Object[][] test21329DataProvider() throws Exception
	{
		return new Object[][]
		{
			{false,  NodeType.PARAGRAPH},
			{true,  NodeType.TABLE},
		};
	}

    /// <summary>
    /// WORDSNET-22364 Document word find and replace issue
    /// LineBreak character \v should be word boundary character.
    /// </summary>
    @Test
    public void test22364() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test22364.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setDirection(FindReplaceDirection.FORWARD);
        options.setMatchCase(false);
        options.setFindWholeWordsOnly(true);

        doc.getRange().replace("Variable.name", "Srinivas", options);
        doc.getRange().replace("Variable.city", "blo", options); //failed to replace
        doc.getRange().replace("Variable.zip", "1234", options); //failed to replace

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("Srinivas\r", paras.get(3).getText());
        msAssert.areEqual("blo\u000b1234\f", paras.get(5).getText());
    }

    /// <summary>
    /// WORDSNET-22269 Quotes in Word document aren't matched by Replace
    /// Double quote in a pattern is considered equal to any curly/smart quote in text.
    /// </summary>
    @Test
    public void test22269() throws Exception
    {
        Document doc1 = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc1);
        builder.writeln("\"quoted\"");
        builder.writeln("“quoted”");
        builder.writeln("”quoted“");

        Document doc2 = doc1.deepClone();

        doc1.getRange().replace("\"quoted\"", "James Bond", new FindReplaceOptions());
        // Regex pattern should be checked exactly like in MS Word.
        doc2.getRange().replaceInternal(new Regex("\"quoted\""), "James Bond", new FindReplaceOptions());

        msAssert.areEqual("James Bond\rJames Bond\rJames Bond\r\f", doc1.getText());
        msAssert.areEqual("James Bond\r“quoted”\r”quoted“\r\f", doc2.getText());
    }

    /// <summary>
    /// WORDSNET-22353 Header and footer lost after replace with section break
    /// Implemented Word-like section break insertion.
    /// </summary>
    @Test
    public void test22353() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\Test22353.docx");
        doc.getRange().replace("~", "&b");

        // Properties of both sections are equal.
        msAssert.isTrue(doc.getSections().get(0).getSectPr().equals(doc.getSections().get(1).getSectPr()));

        // Header moved into first newly created section.
        msAssert.areEqual(1, doc.getSections().get(0).getHeadersFooters().getCount());
        msAssert.areEqual(0, doc.getSections().get(1).getHeadersFooters().getCount());
    }

    /// <summary>
    /// WORDSNET-22371 Ignore Footnotes in Range.Replace
    /// Added new public option FindReplaceOptions.IgnoreFootnotes.
    /// </summary>
    @Test
    public void test22371() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\Test22371.docx");

        FindReplaceOptions options = new FindReplaceOptions(); { options.setIgnoreFootnotes(true); }
        int count = doc.getRange().replace("Hello World", "other", options);

        // Check that replacement has occurred.
        msAssert.areEqual(1, count);
        msAssert.areEqual("other", doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getText());
    }


    /// <summary>
    /// WORDSNET-23258 IReplacingCallback is called before processing FindWholeWordsOnly option.
    /// Moved check of 'FindWholeWordsOnly' option up to before call to replacing callback method.
    /// </summary>
    @Test (dataProvider = "test23258DataProvider")
    public void test23258(boolean isFindWholeWordsOnly, int expectedReplacementsCount) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test23258.docx");

        Test23258ReplacingCallback replacingCallback = new Test23258ReplacingCallback();
        FindReplaceOptions options =
            new FindReplaceOptions(); { options.setReplacingCallback(replacingCallback); options.setFindWholeWordsOnly(isFindWholeWordsOnly); }
        doc.getRange().replace("test", "test", options);

        msAssert.areEqual(expectedReplacementsCount, replacingCallback.getCount());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23258DataProvider")
	public static Object[][] test23258DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  2},
			{false,  9},
		};
	}

    /// <summary>
    /// WORDSNET-23392 No way to apply Style to ApplyFont in FindReplaceOptions.
    /// Additional check in Font.Style setter added.
    /// </summary>
    @Test
    public void test23392() throws Exception
    {
        int[] runIndexes = new int[] { 1, 3, 5, 7, 9 };
        Document doc = TestUtil.open("Model\\Replace\\Test23392.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        Style style = doc.getStyles().fetchByName("formlink");
        options.getApplyFont().setStyle(style);
        int count = doc.getRange().replace("run", "(RUN)", options);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        for (int runIndex : runIndexes)
        {
            msAssert.areEqual("(RUN)", (ms.as(runs.get(runIndex), Run.class)).getText());
            msAssert.areEqual(style.getIstd(), (ms.as(runs.get(runIndex), Run.class)).getFont().getIstd());
        }
    }

    /// <summary>
    /// WORDSJAVA-2665 Calculation of number fields with only dot separator (German Locale) is incorrect
    /// </summary>
    @Test
    public void testJava2665() throws Exception
    {
        CurrentThread.setCurrentCulture(new msCultureInfo("de-DE"));

        Document doc = TestUtil.open("Model\\Replace\\TestJava2665.docx");

        doc.getRange().replace("<<numbervalue1>>", "100.000");
        doc.getRange().replace("<<numbervalue2>>", "100000");
        doc.getRange().replace("<<numbervalue3>>", "100000,00");
        doc.getRange().replace("<<numbervalue4>>", "100.000,00");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Model\\Replace\\TestJava2665.docx");
    }





    /// <summary>
    /// WORDSNET-24701 Aspose.Words replace behavior differs from MS Word when matched text contains a shape.
    /// We should consider shapes when building text with NodeIndexer.
    /// </summary>
    @Test (dataProvider = "test24701DataProvider")
    public void test24701(String fileName, int expectedCount) throws Exception
    {
        Document doc = TestUtil.open(msString.format("Model\\Replace\\{0}", fileName));

        int count = doc.getRange().replace("5678", "AB");
        msAssert.areEqual(expectedCount, count);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24701DataProvider")
	public static Object[][] test24701DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test24701No.docx",  0},
			{"Test24701Yes.docx",  1},
		};
	}


    /// <summary>
    /// WORDSNET-25418 FindReplaceOptions.ApplyFont.Color is not applied.
    /// We should reset theme color when applying a color.
    /// </summary>
    @Test
    public void test25418() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test25418.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setColor(Color.RED);
        int count = doc.getRange().replace("PLACEHOLDER", "REPLACEMENT", options);

        msAssert.areEqual(1, count);

        Run run = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun();
        msAssert.areEqual(DrColor.fromNativeColor(Color.RED), run.getRunPr().get(FontAttr.COLOR));
        msAssert.isNull(run.getRunPr().get(FontAttr.THEME_COLOR));
    }


    /// <summary>
    /// WORDSNET-26924 Find/replace paces replacement before the matched text instead of after as MS Word does.
    /// Reworked Find/Replace feature so that the replacement order matches Word.
    /// </summary>
    @Test
    public void test26924() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test26924.docx");

        doc.startTrackRevisions("Changes");
        int count = doc.getRange().replace("Soft Costs", "abcd");
        doc.stopTrackRevisions();

        msAssert.areEqual(1, count);

        Run deletedRun = TestUtil.getRunWithText(doc.getFirstSection().getBody().getFirstParagraph(), "Soft Costs");
        msAssert.isNotNull(((ITrackableNode)deletedRun).getDeleteRevision());
        Run insertedRun = (Run)deletedRun.getNextSibling();
        msAssert.isNotNull(((ITrackableNode)insertedRun).getInsertRevision());
        msAssert.areEqual("abcd", insertedRun.getText());
    }

    /// <summary>
    /// Relates to WORDSNET-26924.
    /// This is a slightly more complex customer case with paragraph break and
    /// runs that are needed to be split during replacing process.
    /// </summary>
    @Test
    public void test26924A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test26924A.docx");

        doc.startTrackRevisions("Changes");
        int count = doc.getRange().replace("oft&p Cost", "abcd");
        doc.stopTrackRevisions();

        msAssert.areEqual(1, count);

        Run deletedRun = TestUtil.getRunWithText(doc.getFirstSection().getBody().getFirstParagraph(), "o");
        msAssert.isNotNull(((ITrackableNode)deletedRun).getDeleteRevision());

        deletedRun = (Run)deletedRun.getNextSibling();
        msAssert.isNotNull(((ITrackableNode)deletedRun).getDeleteRevision());
        msAssert.areEqual("ft", deletedRun.getText());

        Paragraph deletedParagraph = deletedRun.getParentParagraph();
        msAssert.isNotNull(((ITrackableNode)deletedParagraph).getDeleteRevision());

        deletedRun = ((Paragraph)deletedParagraph.getNextSibling()).getFirstRun();
        msAssert.isNotNull(((ITrackableNode)deletedRun).getDeleteRevision());
        msAssert.areEqual(" Cost", deletedRun.getText());

        Run insertedRun = (Run)deletedRun.getNextSibling();
        msAssert.isNotNull(((ITrackableNode)insertedRun).getInsertRevision());
        msAssert.areEqual("abcd", insertedRun.getText());

        Paragraph paragraph = insertedRun.getParentParagraph();
        msAssert.isFalse(paragraph.hasRevisions());
    }

    /// <summary>
    /// WORDSNET-23740 Track revisions works not the same as in MS Word when use Replace function.
    /// Duplicates WORDSNET-26924.
    /// </summary>
    @Test
    public void test23740() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test23740.docx");

        doc.startTrackRevisions("aspose");
        int count = doc.getRange().replace("Aspose", "aSPOSE");
        doc.stopTrackRevisions();

        msAssert.areEqual(1, count);

        Run deletedRun = TestUtil.getRunWithText(doc.getFirstSection().getBody().getFirstParagraph(), "Aspose");
        msAssert.isNotNull(((ITrackableNode)deletedRun).getDeleteRevision());

        Run insertedRun = (Run)deletedRun.getNextSibling();
        msAssert.isNotNull(((ITrackableNode)insertedRun).getInsertRevision());
        msAssert.areEqual("aSPOSE", insertedRun.getText());

        Paragraph paragraph = insertedRun.getParentParagraph();
        msAssert.isFalse(paragraph.hasRevisions());
        msAssert.areEqual("AsposeaSPOSE.Words\f", paragraph.getText());
    }

    /// <summary>
    /// WORDSNET-28047 ArgumentOutOfRangeException when using regex replacing inside Run.
    /// There are matches with zero length in customer regex at the very beginning of text.
    /// This means nothing to delete, but something to insert. In this case we cannot find
    /// very first node after which to insert a replacement.
    /// </summary>
    @Test
    public void test28047() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test28047.docx");

        // Customer scenario.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        for (Run run : (Iterable<Run>) runs)
            run.getRange().replaceInternal(new Regex("^\\s*"), "1");

        msAssert.areEqual("\r1Hello World!\r\r1Hello Word!\r\r\r1Hello World!1This is a problem!\f", doc.getText());
    }

    /// <summary>
    /// Relates to WORDSNET-28047.
    /// Checks when there actually was a deletion, and then
    /// we want to replace it again due to a specific regex.
    /// </summary>
    @Test
    public void test28047A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test28047A.docx");

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        for (Run run : (Iterable<Run>) runs)
            // Note, here is missing '^' anchor in regex as it was in original case of the customer.
            run.getRange().replaceInternal(new Regex("\\s*"), "=");

        msAssert.areEqual("=AB==CD\f", doc.getText());
    }

    /// <summary>
    /// Tests replacing SDT content in LegacyMode.
    /// </summary>
    @Test (dataProvider = "testReplaceSdtContentLegacyDataProvider")
    public void testReplaceSdtContentLegacy(String pattern, int expUpdateCount, String expReplaceText) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestSdtContent.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setLegacyMode(true);
        int count = doc.getRange().replace(pattern, "***", options);

        msAssert.areEqual(expUpdateCount, count);

        StructuredDocumentTag sdt = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);
        Run updatedRun = ms.as(sdt.getFirstChild(), Run.class);

        msAssert.areEqual(expReplaceText, updatedRun.getText());
        msAssert.areEqual(expReplaceText, sdt.getXmlMapping().getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testReplaceSdtContentLegacyDataProvider")
	public static Object[][] testReplaceSdtContentLegacyDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ame)",  1,  "%(n***"},
			{"ame) ",  0,  "%(name)"},
		};
	}





    /// <summary>
    /// The replacing callback method for <see cref="TestReplace.Test28070"/>.
    /// </summary>
    private static class Test28070ReplacingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            setStartNodeText(e.getMatchNode().getText());
            setEndNodeText(e.getMatchEndNode().getText());

            return ReplaceAction.REPLACE;
        }

        String getStartNodeText() { return mStartNodeText; }; private void setStartNodeText(String value) { mStartNodeText = value; };

        private String mStartNodeText;
        String getEndNodeText() { return mEndNodeText; }; private void setEndNodeText(String value) { mEndNodeText = value; };

        private String mEndNodeText;
    }

    /// <summary>
    /// The replacing callback method for <see cref="TestReplace.Test23258"/>.
    /// </summary>
    private static class Test23258ReplacingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs args)
        {
            setCount(getCount() + 1)/*Property++*/;
            return ReplaceAction.REPLACE;
        }

        /// <summary>
        /// Gets an integer value, indicating how many times Replacing method was called.
        /// </summary>
        int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;
    }

    /// <summary>
    /// The replacing callback method for WORDSNET-21588
    /// </summary>
    private static class Test21588ReplacingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs args)
        {
            msAssert.areEqual(0, args.getMatchOffset());
            msAssert.areEqual("{{5{", args.getMatchNode().getText());

            setMatchValue(args.getMatchInternal().getValue());
            setCount(getCount() + 1)/*Property++*/;
            return ReplaceAction.SKIP;
        }

        /// <summary>
        /// Gets string representing value of the match.
        /// </summary>
        String getMatchValue() { return mMatchValue; }; private void setMatchValue(String value) { mMatchValue = value; };

        private String mMatchValue;

        /// <summary>
        /// Gets an integer value, indicating how many times Replacing method was called.
        /// </summary>
        int getCount() { return mCount; }; private void setCount(int value) { mCount = value; };

        private int mCount;
    }

    private static /*final*/ /*FindReplaceDirection*/int[] G_DIRECTIONS = { FindReplaceDirection.FORWARD, FindReplaceDirection.BACKWARD };
}

