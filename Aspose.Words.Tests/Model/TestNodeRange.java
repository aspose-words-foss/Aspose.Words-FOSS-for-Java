// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.Debug;
import com.aspose.ms.System.msString;



@Test
public class TestNodeRange
{
    @Test
    public void testEmptyRange()
    {
        Run testRun = getTestRun(0);
        String testText = testRun.getText();

        testEmptyRangeBody(1, 0);
        testEmptyRangeBody(1, 1);
        testEmptyRangeBody(0, 0);
        testEmptyRangeBody(testText.length(), testText.length());
        testEmptyRangeBody(testText.length() - 1, testText.length() - 1);
        testEmptyRangeBody(testText.length(), testText.length() + 1);
    }

    private static void testEmptyRangeBody(int startOffset, int endOffset)
    {
        Run testRun = getTestRun(0);

        NodeRange range = makeRangeFromARun(testRun, startOffset, endOffset);
        String context = makeSingleRunContext(testRun.getText(), startOffset, endOffset);

        msAssert.isTrue(range.isEmpty(), context);
    }

    private static NodeRange makeRangeFromARun(Run run, int startOffset, int endOffset)
    {
        return makeRangeFromRuns(run, startOffset, run, endOffset);
    }

    static NodeRange makeRangeFromRuns(Run run0, int startOffset, Run run1, int endOffset)
    {
        DocumentPosition start = new DocumentPosition(run0, startOffset);
        DocumentPosition end = new DocumentPosition(run1, endOffset);
        return new NodeRange(start, end);
    }

    /// <summary>
    /// Tests splitting of a single run.
    /// </summary>
    @Test
    public void testSingleRunSplit()
    {
        Run testRun = getTestRun(0);
        String testText = testRun.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");

        testSingleRunSplitBody(0, 1, "T");
        testSingleRunSplitBody(0, testText.length() - 1, "Tex");
        testSingleRunSplitBody(0, testText.length(), "Text");
        testSingleRunSplitBody(1, testText.length() - 1, "ex");
        testSingleRunSplitBody(1, testText.length(), "ext");

        // Test out of range condition.
        testSingleRunSplitBody(1, testText.length() + 1, "ext");

        // From the middle
        testSingleRunSplitBody(1, 3, "ex");

        // TODO: ? empty run
    }

    private static void testSingleRunSplitBody(int startOffset, int endOffset, String expectedText)
    {
        Document doc = G_TEST_DOCUMENT.deepClone();
        Run testRun = getTestRun(doc, 0);

        NodeRange range = makeRangeFromARun(testRun, startOffset, endOffset);

        String context = makeSingleRunContext(expectedText, startOffset, endOffset);

        // This line will initialize the cached range.End.Length property.
        // Do not remove it.
        Debug.writeLine("Source range end is end of run: " + range.getEnd().isEnd());

        range.isolate();
        Run extractedRun = (Run)range.getStart().getNode();
        msAssert.areEqual(expectedText, extractedRun.getText(), context);
        msAssert.isTrue(range.getStart().isStart(), "Start is not zero: " + context);
        msAssert.isTrue(range.getEnd().isEnd(), "End is not end: " + context);

        msAssert.areEqual(expectedText.length(), range.getEnd().getOffset(), "End offset is not correct: " + context);
    }

    @Test (enabled = false)
    public static String makeSingleRunContext(String expectedText, int startOffset, int endOffset)
    {
        return msString.format("Expected: '{0}', start: {1}, end: {2}", expectedText, startOffset, endOffset);
    }

    @Test
    public void test2RunsSplit()
    {
        Run run0 = getTestRun(0);
        Run run1 = getTestRun(1);
        String testText = run0.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");
        msAssert.areEqual(run1.getText(), "Text", "Unexpected test run text");

        test2RunsSplitBody(0, 1, "Text", "T");
        test2RunsSplitBody(0, testText.length() - 1, "Text", "Tex");
        test2RunsSplitBody(0, testText.length(), "Text", "Text");
        test2RunsSplitBody(1, testText.length() - 1, "ext", "Tex");
        test2RunsSplitBody(1, testText.length(), "ext", "Text");
        test2RunsSplitBody(1, 1, "ext", "T");
        test2RunsSplitBody(testText.length() - 1, 1, "t", "T");

        // From the middle:
        test2RunsSplitBody(2, 2, "xt", "Te");

        // Nothing from the first:
        test2RunsSplitBody(testText.length(), 1, "", "T");
    }

    @Test
    public void test2RunsSplitTo1Run()
    {
        Run run0 = getTestRun(0);
        Run run1 = getTestRun(1);
        String testText = run0.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");
        msAssert.areEqual(run1.getText(), "Text", "Unexpected test run text");

        test2RunsSplitTo1RunBody(0, 0, "Text", "");
        test2RunsSplitTo1RunBody(testText.length() - 1, 0, "t", "");
        test2RunsSplitTo1RunBody(testText.length(), 1, "", "T");
        test2RunsSplitTo1RunBody(testText.length(), testText.length(), "", "Text");
    }

    @Test
    public void testButtonsFakeResultCorrection() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Field field = getTestField(doc);

        NodeRange resultRange = field.getFakeResult();

        msAssert.areEqual(NodeType.FIELD_START, resultRange.getStart().getNode().getNodeType());
        msAssert.areEqual(NodeType.FIELD_END, resultRange.getEnd().getNode().getNodeType());
        msAssert.areEqual(FieldType.FIELD_HYPERLINK, ((FieldEnd)resultRange.getEnd().getNode()).getFieldType());
    }

    private static Field getTestField(Document doc)
    {
        Section sect = doc.appendChild(new Section(doc));
        Body body = sect.appendChild(new Body(doc));

        Paragraph para = body.appendChild(new Paragraph(doc));

        Run run = new Run(doc, RUN_TEXT);

        FieldStart fielsStart = para.appendChild(new FieldStart(doc, run.getRunPr(), FieldType.FIELD_MACRO_BUTTON));
        para.appendChild(new Run(doc, " MACROBUTTON macro "));

        para.appendChild(new FieldStart(doc, run.getRunPr(), FieldType.FIELD_HYPERLINK));
        para.appendChild(new Run(doc, " HYPERLINK "));

        para.appendChild(new FieldStart(doc, run.getRunPr(), FieldType.FIELD_GO_TO_BUTTON));
        para.appendChild(new Run(doc, "GOTOBUTTON"));
        para.appendChild(new FieldEnd(doc, run.getRunPr(), FieldType.FIELD_GO_TO_BUTTON, false));

        para.appendChild(new FieldStart(doc, run.getRunPr(), FieldType.FIELD_FORMULA));
        para.appendChild(new Run(doc, "=3"));
        para.appendChild(new FieldSeparator(doc, run.getRunPr(), FieldType.FIELD_FORMULA));
        para.appendChild(new FieldEnd(doc, run.getRunPr(), FieldType.FIELD_FORMULA, true));

        para.appendChild(new FieldSeparator(doc, run.getRunPr(), FieldType.FIELD_HYPERLINK));

        para.appendChild(new Run(doc, "Some text"));
        para.appendChild(new BookmarkStart(doc, "_StartBookmark"));
        para.appendChild(new Run(doc, "Link"));
        para.appendChild(new BookmarkEnd(doc, "_EndBookmark"));

        para.appendChild(new FieldEnd(doc, run.getRunPr(), FieldType.FIELD_HYPERLINK, true));
        para.appendChild(new FieldEnd(doc, run.getRunPr(), FieldType.FIELD_MACRO_BUTTON, false));

        return fielsStart.getField();
    }

    private static void test2RunsSplitBody(int startOffset, int endOffset, String expectedText0, String expectedText1)
    {
        Document doc = G_TEST_DOCUMENT.deepClone();
        Run testRun0 = getTestRun(doc, 0);
        Run testRun1 = getTestRun(doc, 1);

        NodeRange range = makeRangeFromRuns(testRun0, startOffset, testRun1, endOffset);

        String context = makeDualRunContext(expectedText0, startOffset, expectedText1, endOffset);

        // This line will initialize the cached range.End.Length property.
        // Do not remove it.
        Debug.writeLine("Source range end is end of run: " + range.getEnd().isEnd());

        range.isolate();
        Run extractedStart = (Run)range.getStart().getNode();
        msAssert.areEqual(expectedText0, extractedStart.getText().substring(range.getStart().getOffset()), context);
        msAssert.isTrue(range.getStart().isStart() || range.getStart().isEnd(), "Start is not zero or end: " + context);

        Run extractedEnd = (Run)range.getEnd().getNode();
        msAssert.areEqual(expectedText1, extractedEnd.getText(), context);
        msAssert.isTrue(range.getEnd().isEnd(), "End is not end: " + context);

        msAssert.areEqual(expectedText1.length(), range.getEnd().getOffset(), "End offset is not correct: " + context);
    }

    private static void test2RunsSplitTo1RunBody(int startOffset, int endOffset, String expectedText0, String expectedText1)
    {
        // TODO remove duplication
        Document doc = G_TEST_DOCUMENT.deepClone();
        Run testRun0 = getTestRun(doc, 0);
        Run testRun1 = getTestRun(doc, 1);

        NodeRange range = makeRangeFromRuns(testRun0, startOffset, testRun1, endOffset);

        String context = makeDualRunContext(expectedText0, startOffset, expectedText1, endOffset);

        range.isolate();
        Run extractedStart = (Run)range.getStart().getNode();
        if (!range.getStart().isEnd())
        {
            msAssert.areEqual(expectedText0, extractedStart.getText(), context);
            msAssert.isTrue(range.getStart().isStart(), "Start is not zero: " + context);
        }

        Run extractedEnd = (Run)range.getEnd().getNode();
        if (!range.getEnd().isStart())
        {
            msAssert.areEqual(expectedText1, extractedEnd.getText(), context);
            msAssert.isTrue(range.getEnd().isEnd(), "End is not end: " + context);
        }
    }

    @Test (enabled = false)
    public static String makeDualRunContext(String expectedText0, int startOffset, String expectedText1, int endOffset)
    {
        return msString.format("Expected: '{0}' and '{3}', start: {1}, end: {2}", expectedText0, startOffset, endOffset, expectedText1);

    }

    /// <summary>
    /// Creates the following test document:
    ///
    /// doc
    ///        section
    ///            body
    ///                para
    ///                    run "Text"
    ///                    run "Text"
    ///                 run "Text"
    /// </summary>
    private static Document createTestDoc() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect = doc.appendChild(new Section(doc));

        Body body = sect.appendChild(new Body(doc));
        Paragraph para1 = body.appendChild(new Paragraph(doc));
        para1.appendChild(new Run(doc, RUN_TEXT));
        para1.appendChild(new Run(doc, RUN_TEXT));
        para1.appendChild(new Run(doc, RUN_TEXT));

        return doc;
    }

    @Test (enabled = false)
    public static Run getTestRun(int runIdx)
    {
        return getTestRun(G_TEST_DOCUMENT, runIdx);
    }

    @Test (enabled = false)
    public static Run getTestRun(Document doc, int runIdx)
    {
        return (Run)doc.getChildNodes(NodeType.RUN, true).get(runIdx);
    }

    private static final String RUN_TEXT = "Text";

    private static /*final*/ Document G_TEST_DOCUMENT;



    /// <summary>
    /// WORDSNET-13363 Document.AcceptAllRevisions throws "The bookmark range is invalid" exception
    ///
    /// If TableColumnBookmark is not included into table, we must ignore FirstColumn and LastColumn.
    /// </summary>
    @Test
    public void testJira13363() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create TableColumnBookmark not in table
        BookmarkStart bookmarkStart = builder.startBookmark("testBookmark");
        bookmarkStart.setFirstColumn(0);
        bookmarkStart.setLastColumn(0);
        BookmarkEnd bookmarkEnd = builder.endBookmark("testBookmark");

        msAssert.isTrue(bookmarkStart.isColumn());
        msAssert.isNull(bookmarkStart.getAncestor(NodeType.TABLE));

        NodeRange nodeRange = new NodeRange(bookmarkStart, false, bookmarkEnd, false, bookmarkStart);

        msAssert.isFalse(nodeRange.isTableColumnBookmarkRange());
        msAssert.areEqual(-1, nodeRange.getFirstTableColumnIndex());
        msAssert.areEqual(-1, nodeRange.getLastTableColumnIndex());
    }

    /// <summary>
    /// Relates to WORDSNET-16667 Checks that Range is updating in real-time.
    /// </summary>
    @Test
    public void testJira16667() throws Exception
    {
        final int BOOKMARKS_COUNT = 5;

        Document doc = BookmarkTestUtil.createDocumentWithBookmarks(BOOKMARKS_COUNT);

        Range range = doc.getRange();
        msAssert.areEqual(BOOKMARKS_COUNT, range.getBookmarks().getCount());

        BookmarkTestUtil.appendBookmark(doc, "anotherOneBookmark");
        msAssert.areEqual(BOOKMARKS_COUNT + 1, range.getBookmarks().getCount());

        range.getBookmarks().remove(range.getBookmarks().get(0).getName());
        msAssert.areEqual(BOOKMARKS_COUNT, range.getBookmarks().getCount());

        doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().remove();
        msAssert.areEqual(BOOKMARKS_COUNT, range.getBookmarks().getCount());
    }

    static/* TestNodeRange()*/
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	// RK This must stay in a static ctor for Java autoporting because it can throw and Java porter generates try/catch here.
        	G_TEST_DOCUMENT = createTestDoc();
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }
}

