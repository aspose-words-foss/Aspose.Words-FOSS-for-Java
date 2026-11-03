// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2020 by Konstantin Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Convert;
import com.aspose.DrColor;
import com.aspose.ms.System.Environment;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ThreadPal;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.TimeSpan;
import java.lang.Class;
import org.testng.annotations.DataProvider;


@Test
public class TestReplaceRegex
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that meta character works with Regex.
    /// </summary>
    @Test
    public void testRegexMetaCharacters() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestRegexMetaCharacters.docx";
        Document doc = TestUtil.open(TEST_NAME);
        int count = docRangeReplace(doc, "[&p]{2}", "[two~breaks]");
        msAssert.areEqual(1, count);
        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests all nodes replacement with single character.
    /// </summary>
    @Test
    public void testAllNodes() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestAllNodes.docx";
        Document doc = TestUtil.open(TEST_NAME);

        docRangeReplace(doc, ".", "A");
    }

    /// <summary>
    /// Tests case when we cannot remove paragraph break.
    /// </summary>
    @Test
    public void testIrreplaceableParagraphBreak() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestIrreplaceableParagraphBreak.docx";

        Document doc = TestUtil.open(TEST_NAME);

        int count = docRangeReplace(doc, "&p", "");

        // We returns two replacement while actually did only one. Word does the same.
        msAssert.areEqual(3, count);

        // First paragraph remains untouched. It cannot be removed.
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests number regex replacement.
    /// </summary>
    @Test
    public void testNumberRegex() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestNumberRegex.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setColor(msColor.getDarkBlue());
        options.getApplyFont().setBold(true);
        options.getApplyFont().setSize(14.0);

        int count = docRangeReplace(doc, "\\d+", "*", options);

        msAssert.areEqual(7, count);
    }

    @Test
    public void testNumberHighlightCallback() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestNumberHighlightCallback.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setBold(true);
        options.getApplyFont().setColor(msColor.getDarkOrange());
        options.setReplacingCallback(new NumberHighlighter());

        int count = docRangeReplace(doc, "[0-9]+", "Whatever", options);

        msAssert.areEqual(5, count);
        msAssert.areEqual("There are three numbers: 35478.0, 999283.0 and 000.0. \rAll four numbers should be conditionally formatted.\f", doc.getText());
    }

    private static class NumberHighlighter implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            if (args.getMatchInternal().getLength() > 1)
            {
                args.setReplacement(msString.format("{0}.0", args.getMatchInternal().getValue()));
            }
            else
            {
                args.setReplacement(("3".equals(args.getMatchInternal().getValue())) ? "three" : "four");
            }

            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// Tests that callback is able to insert paragraph breaks.
    /// </summary>
    @Test
    public void testSentenceCounterCallback() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestSentenceCounterCallback.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.setReplacingCallback(new SentenceCounter());

        int count = docRangeReplace(doc, "[\\w\\s]+\\.", "Whatever", options);

        msAssert.areEqual(3, count);

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(4, pc.getCount());
        msAssert.isTrue(pc.get(0).getText().startsWith("1)"));
        msAssert.isTrue(pc.get(1).getText().startsWith("2)"));
        msAssert.isTrue(pc.get(2).getText().startsWith("3)"));
    }

    /// <summary>
    /// The same as above but tests backward direction.
    /// </summary>
    @Test
    public void testSentenceCounterCallbackBackward() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestSentenceCounterCallback.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.setDirection(FindReplaceDirection.BACKWARD);
        options.setReplacingCallback(new SentenceCounter());

        int count = docRangeReplace(doc, "[\\w\\s]+\\.", "Whatever", options);

        msAssert.areEqual(3, count);

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(4, pc.getCount());
        msAssert.isTrue(pc.get(0).getText().startsWith("3)"));
        msAssert.isTrue(pc.get(1).getText().startsWith("2)"));
        msAssert.isTrue(pc.get(2).getText().startsWith("1)"));
    }

    private static class SentenceCounter implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            args.setReplacement(msString.format("{0}) {1}&p", mCount++, msString.trim(args.getMatchInternal().getValue())));

            return ReplaceAction.REPLACE;
        }

        private int mCount = 1;
    }

    /// <summary>
    /// Tests that DocumentBuilder can write safely during replace operation.
    /// </summary>
    @Test
    public void testBuilderIsolation() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestBuilderIsolation.docx";
        Document doc = TestUtil.open(TEST_NAME);

        FindReplaceOptions options = new FindReplaceOptions();
        options.setReplacingCallback(new BuilderWriter());

        int count = docRangeReplace(doc, "[0-9]+", "[was-number]", options);

        // Only 2 numbers are replaced. Written by DocumentBuilder is isolated.
        msAssert.areEqual(2, count);
    }

    private static class BuilderWriter implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            DocumentBuilder builder = new DocumentBuilder(args.getMatchNode().fetchDocument());
            builder.moveToDocumentStart();
            builder.getFont().setColor(Color.RED);
            builder.writeln(msString.format("DocumentBuilder writes: {0}...", mCount++));


            builder.moveToDocumentEnd();
            builder.getFont().setColor(msColor.getGreen());
            builder.writeln(msString.format("DocumentBuilder writes: {0}...", mCount++));

            return ReplaceAction.REPLACE;
        }

        private int mCount = 1;
    }

    /// <summary>
    /// WORDSNET-13138 Document.Range.Replace throws exception when isForward is set to true
    /// </summary>
    @Test
    public void testJira13138() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestJira13138.docx";

        // Test both directions.
        for (/*FindReplaceDirection*/int direction : new /*FindReplaceDirection*/int[] { FindReplaceDirection.FORWARD, FindReplaceDirection.BACKWARD })
        {
            Document doc = TestUtil.open(TEST_NAME);

            int count = docRangeReplace(doc, "Test", "", new FindReplaceOptions(direction, new Callback13138()));
            msAssert.areEqual(1, count);
            msAssert.areEqual("\r\r\r\r\r\rTest value\f", doc.getText());
        }
    }

    private static class Callback13138 implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            DocumentBuilder builder = new DocumentBuilder(ms.as(e.getMatchNode().getDocument(), Document.class));
            builder.moveTo((Run)e.getMatchNode());
            builder.write("Test value");
            e.setReplacement("");
            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// WORDSNET-12954 Document.Range.Replace is throwing exception when isForward is set to true
    /// </summary>
    @Test
    public void testJira12954() throws Exception
    {
        final String TEST_NAME = "Model\\Replace2\\TestJira12954.docx";

        // Test both directions.
        for (/*FindReplaceDirection*/int direction : G_DIRECTIONS)
        {
            Document doc = TestUtil.open(TEST_NAME);

            FindReplaceOptions options = new FindReplaceOptions(direction, new Callback12954());

            int count = docRangeReplace(doc, "%([A-Z0-9_]{3,30})%", "", options);
            msAssert.areEqual(1, count);

            msAssert.areEqual(1, doc.getRange().getBookmarks().getCount());
            msAssert.areEqual("%MODEL%", doc.getRange().getBookmarks().get(0).getName());
        }
    }

    private static class Callback12954 implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            DocumentBuilder builder = new DocumentBuilder(ms.as(e.getMatchNode().getDocument(), Document.class));
            builder.moveTo((Run)e.getMatchNode());
            String bookmarkName = e.getMatchInternal().getValue();
            builder.startBookmark(bookmarkName);
            builder.endBookmark(bookmarkName);
            e.setReplacement("");
            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// WORDSNET-13046 Range.Replace throws System.NotSupportedException
    /// </summary>
    @Test
    public void testJira13046() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira13046.docx");

        msAssert.areEqual(6, doc.getFirstSection().getBody().getParagraphs().getCount());

        int count = docRangeReplace(doc, "[a-z][^\\.;]*(Bente)[^\\.;]*", "HELLO"); // Fails

        msAssert.areEqual(1, count);
        msAssert.areEqual(4, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// WORDSNET-12078 Range.Replace method throws System.NotSupportedException
    /// </summary>
    @Test
    public void testJira12078() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello This a test String. Guidance \n dnvnhjlmnmvhctd  ajjdkb ;.nnsh EndGuidance.;;';lp Hello Tis a test String. Guidance dnvnhjlmnmvhctd  ajjdkb ;.nnsh EndGuidance");

        msAssert.areEqual(3, doc.getFirstSection().getBody().getParagraphs().getCount());

        final String STR_PATTERN = "(?s)Guidance.*?EndGuidance";

        int count = docRangeReplace(doc, STR_PATTERN, "");

        msAssert.areEqual(2, count);
        msAssert.areEqual(2, doc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Tests that callback returned action is correctly processed.
    /// </summary>
    @Test
    public void testSkipAllCallback() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello world.");

        int count = docRangeReplace(doc, ".", "*", new FindReplaceOptions(new SkipAllCallback()));

        msAssert.areEqual(0, count);
        msAssert.areEqual("Hello world.\r\f", doc.getText());
    }

    private static class SkipAllCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            return ReplaceAction.SKIP;
        }
    }

    /// <summary>
    /// Tests that callback returned action is correctly processed.
    /// </summary>
    @Test
    public void testEmptyCallback() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello world.");

        int count = docRangeReplace(doc, ".", "*", new FindReplaceOptions(new EmptyCallback()));

        msAssert.areEqual(14, count);
        msAssert.areEqual("**************\f", doc.getText());
    }

    private static class EmptyCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// Tests arguments passed to callback.
    /// </summary>
    @Test
    public void testTestingCallback() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("123 or 456.");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setReplacingCallback(new TestingCallback());

        int count = docRangeReplace(doc, "[0-9]+", "*", options);

        msAssert.areEqual(2, count);
        msAssert.areEqual("* or *.\r\f", doc.getText());
    }

    private static class TestingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            msAssert.areEqual("*", e.getReplacement());
            msAssert.isTrue(("123".equals(e.getMatchInternal().getValue())) || ("456".equals(e.getMatchInternal().getValue())));
            msAssert.isTrue((e.getMatchOffset() == 0) || (e.getMatchOffset() == 4));

            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// Tests Wiki demonstration sample.
    /// </summary>
    /// <remarks>
    /// Demonstrates callback usage.
    /// Numbers matched by regular expression are converted to HEX presentation and highlighted.
    /// </remarks>
    @Test
    public void testWiki02() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setName("Arial");
        builder.write("There are few numbers that should be converted to HEX and highlighted: 123, 456, 789 and 17379.");

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setHighlightColor(msColor.getDarkOrange());
        options.setReplacingCallback(new NumberHexer());

        int count = docRangeReplace(doc, "[0-9]+", "", options);
        msAssert.areEqual(4, count);
    }

    private static class NumberHexer implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            int number = Convert.toInt32(args.getMatchInternal().getValue());
            args.setReplacement(msString.format("0x{0:X}", number));

            return ReplaceAction.REPLACE;
        }
    }

    /// <summary>
    /// Tests code sample from public API comment.
    /// </summary>
    @Test
    public void testCodeSample_2() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("a1, b2, c3");

        // Replaces each number with paragraph break.
        docRangeReplace(doc, "\\d+", "&p");

        msAssert.areEqual("a\r, b\r, c\r\r\f", doc.getText());
    }

    /// <summary>
    /// Relates to WORDSNET-14046
    /// Tests that content cell break character replacement is correctly handled.
    /// </summary>
    @Test
    public void testJira15046B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15046A.docx");

        // No replacement should be made.
        int count = docRangeReplace(doc, ".", "XX");
        msAssert.areEqual(5, count);
    }

    /// <summary>
    /// WORDSNET-15177 Range.Replace method does not replace text in footnote
    /// Footnotes were excluded from find/replace processing.
    /// </summary>
    @Test
    public void testJira15177() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15177.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.getApplyFont().setHighlightColor(Color.YELLOW);

        options.setReplacingCallback(new ReplaceEvaluatorFindAndHighlight());
        options.setDirection(FindReplaceDirection.BACKWARD);
        // We want the "your document" phrase to be highlighted.
        docRangeReplace(doc, "(?i)[a-z]+", "", options);

        Footnote footnote = (Footnote)doc.getFirstSection().getBody().getChild(NodeType.FOOTNOTE, 0, true);

        Run run = footnote.getFirstParagraph().getRuns().get(1);
        msAssert.areEqual("Lorem", run.getText());
        msAssert.areEqual(DrColor.YELLOW.toArgb(), ((DrColor)run.getRunPr().get(FontAttr.HIGHLIGHT_COLOR)).toArgb());
    }

    private static class ReplaceEvaluatorFindAndHighlight implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            if (args.getMatchInternal().getValue().contains("o"))
            {
                args.setReplacement(args.getMatchInternal().getValue());
                return ReplaceAction.REPLACE;
            }
            else
            {
                return ReplaceAction.SKIP;
            }
        }
    }

    /// <summary>
    /// WORDSNET-15624 Tests that headers/footers are processed in order of their visual
    /// representation in a document.
    /// </summary>
    @Test
    public void testHeaderFooterOrder() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestJira15624.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        MatchLogger logger = new MatchLogger();
        options.setReplacingCallback(logger);

        docRangeReplace(doc, "(header|footer)",  "", options);

        msAssert.areEqual("First header" + Environment.getNewLine() +
                            "First footer" + Environment.getNewLine() +
                            "Second header" + Environment.getNewLine() +
                            "Second footer" + Environment.getNewLine() +
                            "Third header" + Environment.getNewLine() +
                            "Third footer" + Environment.getNewLine(), logger.getText());
    }


    /// <summary>
    /// WORDSNET-16217 FindReplaceOptions.FindWholeWordsOnly does not replace standalone word.
    /// Added a word's boundaries determination.
    /// </summary>
    @Test
    public void testJira16217() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder docBuilder = new DocumentBuilder(doc);

        docBuilder.writeln("$IMPORT_TEXT");
        docBuilder.writeln("$IMPORT_TEXTAREA");

        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        findReplaceOptions.setFindWholeWordsOnly(true);

        docRangeReplace(doc, "\\$IMPORT_TEXT", "testing", findReplaceOptions);
        docRangeReplace(doc, "\\$IMPORT_TEXTAREA", "textarea", findReplaceOptions);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        msAssert.areEqual("testing", ((Run)runs.get(0)).getText());
        msAssert.areEqual("textarea", ((Run)runs.get(1)).getText());
    }

    private static class MatchLogger implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs e)
        {
            msStringBuilder.appendLine(mText, e.getMatchNode().getText());
            return ReplaceAction.SKIP;
        }

        String getText() { return mText.toString(); }

        private /*final*/ StringBuilder mText = new StringBuilder();
    }

    @Test
    public void testReplaceDirection() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceDirection.docx");

        mCounter = 0;
        //Backward
        Node para = doc.selectSingleNode("//Paragraph[1]");
        para.getRange().replaceInternal(new Regex("A"), "", new FindReplaceOptions(FindReplaceDirection.BACKWARD, new ReplaceEvaluator1(this)));
        msAssert.areEqual("A4 A3 A2 A1 A0\r", para.getText());

        mCounter = 0;
        //Forward
        para = doc.selectSingleNode("//Paragraph[2]");
        para.getRange().replaceInternal(new Regex("A"), "", new FindReplaceOptions(FindReplaceDirection.FORWARD, new ReplaceEvaluator1(this)));
        msAssert.areEqual("A0 A1 A2 A3 A4\r", para.getText());
    }

    private static class ReplaceEvaluator1 implements IReplacingCallback
    {
        public ReplaceEvaluator1(TestReplaceRegex context)
        {
            mContext = context;
        }

        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            //Also shows how to add something to the text, not just replace.
            e.setReplacement(e.getMatchInternal().getValue() + Integer.toString(mContext.mCounter));
            mContext.mCounter++;
            return ReplaceAction.REPLACE;
        }

        private /*final*/ TestReplaceRegex mContext;
    }

    @Test
    public void testReplaceAcrossParagraph() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceAcrossParent.docx");
        //Note the way of capturing a paragraph break without specifying it explicitly.
        int replacementCount = docRangeReplace(doc, "Para1.Para2", "");
        msAssert.areEqual(1, replacementCount);
    }

    @Test (enabled = false)
    public void testReplaceAcrossParagraphUnescaped() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceAcrossParent.docx");
        //There is a way to pass unescaped \r, it is also caught.
        int replacementCount = docRangeReplace(doc, "Para1\rPara2", "");
        msAssert.areEqual(0, replacementCount);
    }

    /// <summary>
    /// WORDSNET-614 This used to throw object reference not set.
    /// </summary>
    @Test
    public void testDefect614() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestDefect614.docx");
        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        cell.getRange().replaceInternal(new Regex(".*[^\u0007]"), "");
        msAssert.areEqual("\u0007", cell.getRange().getText());
    }

    /// <summary>
    /// Replacing a particular string with a table.
    /// </summary>
    @Test
    public void testReplaceWithTable() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestReplaceWithTable.docx");

        //Note I have to pass "isForward = false", otherwise such a complex replace will not work.
        docRangeReplace(doc, "Tag", "", new FindReplaceOptions(FindReplaceDirection.BACKWARD, new HandleReplaceWithTable()));

        TestUtil.save(doc, "Model\\Replace\\TestReplaceWithTable.docx");

        msAssert.areEqual("Hello\u0007\u0007" +
                "Other text\r" +
                "Hello\u0007\u0007" +
                "\f", doc.getRange().getText());
    }

    private static class HandleReplaceWithTable implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            //The node that contains text should be a Run.
            Run run = (Run)e.getMatchNode();

            //We can only insert a table at the "block" level, that is among paragraphs and tables.
            //Step up to the paragraph node that contains the run with the match text.
            Paragraph para = run.getParentParagraph();

            //This is the table we are going to insert.
            //Note you can use DocumentBuilder instead of building node by node,
            //it could be easier to build a table with DocumentBuilder.
            Table newTable = createTestTable(para.getDocument());

            //Ask the parent of the paragraph to insert the new table after this paragraph.
            para.insertNext(newTable);

            //In fact I want the old paragraph to be removed.
            para.remove();

            //Let the replace continue.
            e.setReplacement("");
            return ReplaceAction.REPLACE;
        }
    }

    private static Table createTestTable(DocumentBase doc)
    {
        //Create a table with one cell.
        Table table = new Table(doc);
        table.ensureMinimum();

        Cell cell = table.getFirstRow().getFirstCell();
        cell.getCellFormat().setWidth(144.0);

        //Add some text.
        Run run = new Run(doc, "Hello");
        cell.getFirstParagraph().appendChild(run);

        return table;
    }

    /// <summary>
    /// Checks several replacements in one run when forward direction is used.
    /// </summary>
    @Test
    public void testTwoReplacementInOneRun() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();
        builder.write("ol");
        builder.writeln("d old old");

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        Debug.assertTrue(runs.getCount() == 2);
        Debug.assertTrue("ol".equals(((Run)runs.get(0)).getText()));

        FindReplaceOptions options = new FindReplaceOptions();
        {
            options.setDirection(FindReplaceDirection.FORWARD);
            options.setReplacingCallback(null);
        }
        FindReplace findReplace = new FindReplace(doc, new Regex("old"), "new text", options);
        findReplace.replace();

        String expectedText = "new text new text new text";
        msAssert.areEqual(expectedText, doc.getText().substring((0), (0) + (expectedText.length())));
    }

    /// <summary>
    /// WORDSNET-12530 ArgumentOutOfRangeException may occur during replacement when forward
    /// direction is used and length of replcement string is different than length of searching string.
    /// </summary>
    @Test
    public void testJira12530() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestJira12530.docx");

        docRangeReplace(doc, "(?i)<\\*.+?(Counter)\\*>", "", new FindReplaceOptions(FindReplaceDirection.FORWARD, new TestJira12530ReplacingCallback()));

        String expectedText = "1     \r2     \r3     \r4     \r5     \r6     \r7     \r8     \f";
        msAssert.areEqual(expectedText, doc.getText());
    }

    private static class TestJira12530ReplacingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            switch (gStringSwitchMap.of(e.getMatchInternal().toString()))
            {
                case /*"<*NumberCounter*>"*/0:
                {
                    e.setReplacement(Integer.toString(mSectionNumberCounter));
                    mSectionNumberCounter++;
                    return ReplaceAction.REPLACE;
                }
                default:
                {
                    return ReplaceAction.SKIP;
                }
            }
        }

        private int mSectionNumberCounter = 1;
    }



    /// <summary>
    /// Relates to WORDSNET-18986 Checks for replacement in SmartTag with para.
    /// </summary>
    @Test
    public void test18986ReplaceSmartTagWithPara() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test18986ReplaceSmartTagWithPara.docx");

        int replaceCount = docRangeReplace(doc, "\\[(.*?)\\]", "Before&pAfter", new FindReplaceOptions());
        msAssert.areEqual(4, replaceCount);
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(6, paras.getCount());

        msAssert.areEqual(1, paras.get(0).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Before", paras.get(0).getFirstRun().getText());

        msAssert.areEqual(1, paras.get(0).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("After", paras.get(1).getFirstRun().getText());

        msAssert.areEqual(1, paras.get(2).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Before", paras.get(2).getFirstRun().getText());

        msAssert.areEqual(4, paras.get(3).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("After", paras.get(3).getRuns().get(0).getText());
        msAssert.areEqual(NodeType.SMART_TAG, paras.get(3).getRuns().get(1).getParentNode().getNodeType());
        msAssert.areEqual("MustBeInSmartTag", paras.get(3).getRuns().get(1).getText());
        msAssert.areEqual("WP_SMT_SMT_WR2", paras.get(3).getRuns().get(2).getText());
        msAssert.areEqual("Before", paras.get(3).getRuns().get(3).getText());

        msAssert.areEqual(2, paras.get(4).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("After", paras.get(4).getRuns().get(0).getText());
        msAssert.areEqual("Before", paras.get(4).getRuns().get(1).getText());

        msAssert.areEqual(1, paras.get(5).getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("After", paras.get(5).getFirstRun().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-18986 Checks for replacement in SmartTag.
    /// </summary>
    @Test
    public void test18986ReplaceSmartTagA() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test18986ReplaceSmartTag.docx");

        int replaceCount = docRangeReplace(doc, "\\[(.*?)\\]", "Test", new FindReplaceOptions());
        msAssert.areEqual(8, replaceCount);

        NodeCollection smartTags = doc.getFirstSection().getBody().getChildNodes(NodeType.SMART_TAG, true);
        msAssert.areEqual(2, smartTags.getCount());
    }

    /// <summary>
    /// WORDSNET-19357 Difference in Order between the now and old deprecated Replace methods.
    /// The new Replace method searches for text in text boxes after the first search-pass in a document -
    /// mimics Word. Added option.
    /// </summary>
    @Test (dataProvider = "test19357DataProvider")
    public void test19357(boolean useLegacyOrder) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test19357.docx");

        Test19357ReplacingCallback callback = new Test19357ReplacingCallback();
        FindReplaceOptions options = new FindReplaceOptions();
        options.setReplacingCallback(callback);

        // Checks the default value.
        msAssert.isFalse(options.getUseLegacyOrder());
        options.setUseLegacyOrder(useLegacyOrder);

        docRangeReplace(doc, "\\[(.*?)\\]", "", options);

        if (useLegacyOrder)
        {
            msAssert.areEqual("[tag 1]", callback.getMatches().get(0));
            msAssert.areEqual("[tag 2]", callback.getMatches().get(1));
            msAssert.areEqual("[tag 3]", callback.getMatches().get(2));
        }
        else
        {
            msAssert.areEqual("[tag 1]", callback.getMatches().get(0));
            msAssert.areEqual("[tag 3]", callback.getMatches().get(1));
            msAssert.areEqual("[tag 2]", callback.getMatches().get(2));
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19357DataProvider")
	public static Object[][] test19357DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    private static class Test19357ReplacingCallback implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs e)
        {
            msArrayList.add(mMatches, e.getMatchInternal().getValue());
            return ReplaceAction.REPLACE;
        }

        public ArrayList<String> getMatches() { return mMatches; }

        private /*final*/ ArrayList<String> mMatches = new ArrayList<String>();
    }

    /// <summary>
    /// WORDSNET-19913 Range.Replace with substitution.
    /// The new <see cref="FindReplaceOptions.UseSubstitutions"/> is introduced.
    /// We should calculate substituted result from the replacing string before passing it
    /// to the replacing routine for every match when the option is enabled.
    /// </summary>
    @Test (dataProvider = "test19913DataProvider")
    public void test19913(boolean isRecognizeSubstitutions, String expectedContent) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test19913.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setUseSubstitutions(isRecognizeSubstitutions);
        docRangeReplace(doc, "([A-z]+) give money to ([A-z]+)", "$2 take money from $1", options);

        msAssert.areEqual(expectedContent, doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19913DataProvider")
	public static Object[][] test19913DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  "Paul take money from Jason\f"},
			{false,  "$2 take money from $1\f"},
		};
	}

    /// <summary>
    /// WORDSNET-19912 FindReplaceOptions new properties.
    /// Implemented new option <see cref="FindReplaceOptions.IgnoreDeleted"/>.
    /// </summary>
    @Test (dataProvider = "test19912DeleteRevisionDataProvider")
    public void test19912DeleteRevision(boolean isIgnoreDeleted, String expectedDeletedText) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test19912Revisions.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setIgnoreDeleted(isIgnoreDeleted);

        docRangeReplace(doc, "e", "*", options);

        String expectedText = msString.format("t*xt {0} ins*rt*d t*xt\f", expectedDeletedText);
        msAssert.areEqual(expectedText, doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19912DeleteRevisionDataProvider")
	public static Object[][] test19912DeleteRevisionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{false,  "d*l*t*d"},
			{true,  "deleted"},
		};
	}

    /// <summary>
    /// WORDSNET-19912 FindReplaceOptions new properties.
    /// Implemented new option <see cref="FindReplaceOptions.IgnoreInserted"/>.
    /// </summary>
    @Test (dataProvider = "test19912InsertRevisionDataProvider")
    public void test19912InsertRevision(boolean isIgnoreInserted, String expectedInsertedText) throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test19912Revisions.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setIgnoreInserted(isIgnoreInserted);

        docRangeReplace(doc, "e", "*", options);

        String expectedText = msString.format("t*xt d*l*t*d {0}\f", expectedInsertedText);
        msAssert.areEqual(expectedText, doc.getFirstSection().getBody().getFirstParagraph().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19912InsertRevisionDataProvider")
	public static Object[][] test19912InsertRevisionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{false,  "ins*rt*d t*xt"},
			{true,  "inserted text"},
		};
	}


    /// <summary>
    /// WORDSJAVA-2546 com.aspose.ms.System.Text.RegularExpressions.Match.result() returns
    /// correct value plus some extra symbols when regular expressions with substitutions are used.
    /// </summary>
    @Test
    public void testJava2546() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("world");
        builder.writeln("world");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setUseSubstitutions(true);

        doc.getRange().replaceInternal(new Regex("(world)"), "Hello $1!", options);

        msAssert.areEqual("Hello world!\rHello world!", msString.trim(doc.getText()));
    }

    /// <summary>
    /// WORDSJAVA-2455 "useSubstitutions" test doesn't replace text
    /// </summary>
    @Test
    public void testJava2455() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("John sold a car to Paul.");
        builder.writeln("Jane sold a house to Joe.");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setUseSubstitutions(true);

        docRangeReplace(doc, "([A-z]+) sold a ([A-z]+) to ([A-z]+)", "$3 bought a $2 from $1", options);

        msAssert.areEqual("Paul bought a car from John.\rJoe bought a house from Jane.", msString.trim(doc.getText()));
    }

    /// <summary>
    /// WORDSNET-22179 Range.Replace hangs after upgrading to 21.4.
    /// The issue is caused by WORDSJAVA-2455, where we have started to get matches from both escaped
    /// and unescaped patterns. Solved by reverting WORDSJAVA-2455, as string patterns are not regexes
    /// and therefore should be always escaped.
    /// </summary>
    @Test
    public void test22179() throws Exception
    {
        Test22179ThreadWorker worker = new Test22179ThreadWorker();
        TestUtil.isHanging(worker, 5);
    }

    /// <summary>
    /// WORDSNET-22200 Regression: Range.Replace is causing memory leak.
    /// The issue duplicates WORDSNET-22179
    /// </summary>
    @Test
    public void test22200() throws Exception
    {
        Test22200ThreadWorker worker = new Test22200ThreadWorker();
        TestUtil.isHanging(worker, 5);
    }

    /// <summary>
    /// WORDSNET-22632 Find and Replace does not work with delete revisions.
    /// Before the fix we ignored whole deleted/inserted nodes, so that if the node is composite,
    /// then all its child nodes were also ignored, even they have no any revisions.
    /// The behavior is changed to not skip child nodes in such cases for now.
    /// </summary>
    @Test
    public void test22632DeleteRevision() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test22632Del.docx");
        msAssert.isTrue(doc.getFirstSection().getBody().getFirstParagraph().isDeleteRevision());
        FindReplaceOptions options = new FindReplaceOptions(); { options.setIgnoreDeleted(true); }
        test22632Core(doc, options);
    }

    /// <summary>
    /// Relates to WORDSNET-22632 Checks insertion revision.
    /// </summary>
    @Test
    public void test22632InsertRevision() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\Test22632Ins.docx");
        msAssert.isTrue(doc.getFirstSection().getBody().getFirstParagraph().isInsertRevision());

        FindReplaceOptions options = new FindReplaceOptions(); { options.setIgnoreInserted(true); }
        test22632Core(doc, options);
    }



    /// <summary>
    /// WORDSJAVA-2910 - Incorrect behavior of regular expression while searching a sentence ending by a dot.
    /// </summary>
    @Test
    public void testJava2910() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace\\TestJava2910.docx");

        FindReplaceOptions boldFontOptions = new FindReplaceOptions();
        boldFontOptions.getApplyFont().setBold(true);
        boldFontOptions.setUseSubstitutions(true);

        final String PATTERN = "^[^\\.]+?\\.";
        final String REPLACEMENT = "$0";

        docRangeReplace(doc, PATTERN, REPLACEMENT, boldFontOptions);

        TestUtil.saveCheckGold(doc, "Model\\Replace\\TestJava2910.docx");
    }

    /// <summary>
    /// Tests SpecialChar update when replacing in LegacyMode.
    /// </summary>
    @Test
    public void testReplaceCommentLegacy() throws Exception
    {
        Document doc = TestUtil.open("Model\\Replace2\\TestCommentText.docx");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setLegacyMode(true);

        // Checks System.InvalidCastException here.
        int updateCount = doc.getRange().replaceInternal(new Regex("[a-zA-Z\u0005]"), "*", options);
        msAssert.areEqual(16, updateCount);
    }

    /// <summary>
    /// The core test method for WORDSNET-22632
    /// </summary>
    private static void test22632Core(Document doc, FindReplaceOptions options) throws Exception
    {
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        Run run = para.getFirstRun();
        msAssert.areEqual("First line has a ", run.getText());
        msAssert.isFalse(run.isDeleteRevision());
        msAssert.isFalse(run.isInsertRevision());

        docRangeReplace(doc, "First", "REPLACED", options);

        msAssert.areEqual("REPLACED line has a insertion\r", para.getText());
    }

    /// <summary>
    /// A worker thread for Test22200.
    /// </summary>
    private static class Test22200ThreadWorker extends ThreadPal
    {
        protected /*override*/ void doWork() throws Exception
        {
            Document doc = TestUtil.open("Model\\Replace\\Test22200.docx");

            // This is scenario of the customer. I did not optimize anything in this code intentionally.
            doc.getRange().replace("[projectname]", "sdfsdf");
            doc.getRange().replace("[chargeCodes]", "sdfsdf");
            doc.getRange().replace("[resort]", "sdfsdf");
            doc.getRange().replace("[service]", "sdfsdf");
            doc.getRange().replace("[businesssponsor]", "sdfsdf");
            doc.getRange().replace("[tpManager]", "sdfsdf");
            doc.getRange().replace("[projectManager]", "sdfsdf");
            doc.getRange().replace("[changeIDs]", "sdfsdf / asdasd / asdasd /a dasd a/Asda s/Asda / asda sda/ asdasd");
            doc.getRange().replace("[proposalFolderLink]", "https://news.google.com/topstories?hl=de&gl=DE&ceid=DE:de");
            doc.getRange().replace("[serviceRequestLink]", "https://news.google.com/topstories?hl=de&gl=DE&ceid=DE:de");
            doc.getRange().replace("[projectSpaceLink]", "https://news.google.com/topstories?hl=de&gl=DE&ceid=DE:de");

            doc = TestUtil.saveOpen(doc, "Model\\Replace\\Test22200.docx", new OoxmlSaveOptions(), false);

            // Check all values were successfully replaced.
            String docText = doc.getText();
            msAssert.isFalse(docText.contains("[projectname]"));
            msAssert.isFalse(docText.contains("[resort]"));
            msAssert.isFalse(docText.contains("[service]"));
            msAssert.isFalse(docText.contains("[businesssponsor]"));
            msAssert.isFalse(docText.contains("[tpManager]"));
            msAssert.isFalse(docText.contains("[projectManager]"));
            msAssert.isFalse(docText.contains("[changeIDs]"));
            msAssert.isFalse(docText.contains("[proposalFolderLink]"));
            msAssert.isFalse(docText.contains("[serviceRequestLink]"));
            msAssert.isFalse(docText.contains("[projectSpaceLink]"));
        }
    }

    /// <summary>
    /// A worker thread for Test22179.
    /// </summary>
    private static class Test22179ThreadWorker extends ThreadPal
    {
        protected /*override*/ void doWork() throws Exception
        {
            String[] patterns = new String[]
            {
                "[Patient Name]", "[Date of birth]", "[Email]", "[X Number]", "[Telephone]", "[Enquiry Timestamp]",
                "[Financial Class]", "[Status]", "[Comment]", "[Source]"
            };
            String[] replacement = new String[]
            {
                "Mrs TestDB  TestAttachments ", " ", "658018@testtest.com", "3g3g", "75983410", "28/01/2021 19:47",
                "Insured", "Open", "She says she isn't feeling well and vwould like to see a Dr.", "Self Referral"
            };

            msAssert.areEqual(patterns.length, replacement.length);

            Document doc = TestUtil.open("Model\\Replace\\Test22179.docx");

            int count = 0;
            for (int i = 0; i < patterns.length; i++)
            {
                count += doc.getRange().replace(patterns[i], replacement[i]);
            }

            msAssert.areEqual(0, count);
        }
    }

    private static int docRangeReplace(Document doc, String regexStr, String replacement) throws Exception
    {
        return docRangeReplace(doc, regexStr, replacement, new FindReplaceOptions());
    }

    private static int docRangeReplace(Document doc, String regexStr, String replacement, FindReplaceOptions options) throws Exception
    {
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(regexStr);
        return doc.getRange().replace(regex, replacement, options);
    }

    private int mCounter;
    private static /*final*/ /*FindReplaceDirection*/int[] G_DIRECTIONS = { FindReplaceDirection.FORWARD, FindReplaceDirection.BACKWARD };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"<*NumberCounter*>"
	);

}

