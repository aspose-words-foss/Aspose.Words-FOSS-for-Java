// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests the <see cref="Bookmark.Text"/> property.
/// </summary>
@Test
public class TestBookmarkText
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests setting text. Contents of a paragraph is removed and the paragraph is merged with the next one.
    /// </summary>
    @Test (dataProvider = "testSettingTextWithParagraphMergeDataProvider")
    public void testSettingTextWithParagraphMerge(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        checkSettingTextWithParagraphMerge(doc);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSettingTextWithParagraphMergeDataProvider")
	public static Object[][] testSettingTextWithParagraphMergeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Bookmark\\TestSettingTextWithParagraphMerge1.docx"},
			{"Model\\Bookmark\\TestSettingTextWithParagraphMerge2.docx"},
			{"Model\\Bookmark\\TestSettingTextWithParagraphMerge3.docx"},
			{"Model\\Bookmark\\TestSettingTextWithParagraphMerge4.docx"},
			{"Model\\Bookmark\\TestSettingTextWithParagraphMerge5.docx"},
		};
	}

    /// <summary>
    /// Tests that returned text is the same as set.
    /// </summary>
    @Test (dataProvider = "testSettingTextDataProvider")
    public void testSettingText(String fileName, String bookmarkName) throws Exception
    {
        Document doc = TestUtil.open(fileName);
        Bookmark bookmark = doc.getRange().getBookmarks().get(bookmarkName);

        final String NEW_TEXT = "New";
        bookmark.setText(NEW_TEXT);
        msAssert.areEqual(NEW_TEXT, bookmark.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSettingTextDataProvider")
	public static Object[][] testSettingTextDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Fields\\EquationsAndFormulas\\TestJira5671.docx",  "a"},
			{"ImportDocx\\TestJira5758.docx",  "_last_block"},
		};
	}


    /// <summary>
    /// Tests getting/setting text on bookmarks displaced by SDT.
    /// </summary>
    @Test (enabled = false)
    public void testDisplacedBySdt(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        Bookmark bookmark = doc.getRange().getBookmarks().get("PubDate");
        // Start node is either before the SDT or the first child of it.
        /*NodeType*/int parentNodeType = bookmark.getBookmarkStart().getParentNode().getNodeType();
        /*NodeType*/int nextNodeType = bookmark.getBookmarkStart().getNextSibling().getNodeType();
        msAssert.areEqual("14 February 2018", bookmark.getText());

        final String NEW_TEXT = "New";
        bookmark.setText(NEW_TEXT);
        msAssert.areEqual(NEW_TEXT, bookmark.getText());
        msAssert.areEqual(parentNodeType, bookmark.getBookmarkStart().getParentNode().getNodeType());
        msAssert.areEqual(nextNodeType, bookmark.getBookmarkStart().getNextSibling().getNodeType());

        CompositeNode sdt = (CompositeNode)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(NodeType.CELL, sdt.getFirstNonAnnotationChild().getNodeType());
        CompositeNode cell = (CompositeNode)sdt.getFirstNonAnnotationChild();
        msAssert.areEqual(NodeType.PARAGRAPH, cell.getFirstChild().getNodeType());
        msAssert.areSame(sdt, bookmark.getBookmarkEnd().getParentNode());
    }






    /// <summary>
    /// Tests value of the <see cref="Bookmark.Text"/> property if bookmark end is at the end of story.
    /// </summary>
    @Test (dataProvider = "testBookmarkTextAtEndOfStoryDataProvider")
    public void testBookmarkTextAtEndOfStory(String fileName, String bookmarkName,
        int expectedTextLength, String startsWith, String endsWith) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        Bookmark bm = doc.getRange().getBookmarks().get(bookmarkName);
        String text = bm.getText();

        // Test Explorer does not show test cases if string parameter contains non-readable chars e.g. '\u0005', so
        // it may be not possible to specify entire bookmark text and it may be too long: several parameters are used.
        msAssert.areEqual(expectedTextLength, text.length());
        msAssert.isTrue(text.startsWith(startsWith));
        msAssert.isTrue(text.endsWith(endsWith));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBookmarkTextAtEndOfStoryDataProvider")
	public static Object[][] testBookmarkTextAtEndOfStoryDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Revision\\RevisionGroups\\TestJira15785_DeletedTextBox.docx",  "_GoBack", 
        170,  "[Grab your reader’s",  "just drag it.]"},
			{"Model\\Bookmark\\TestBookmarkTextAtEndOfStory.docx",  "header", 
        23,  "",  "Header line\rHeader line"},
			{"Model\\Bookmark\\TestBookmarkTextAtEndOfStory.docx",  "footnote", 
        32,  "",  " Bookmark in footnote\rNext line"},
		};
	}








    /// <summary>
    /// Relates to WORDSNET-19518.
    /// Checks the insertion text and the retrieving text for the bookmark, which is in the second row.
    /// </summary>
    @Test
    public void test19518StartWithSecondRow() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test19518StartWithSecondRow.docx");
        Bookmark bookmark = doc.getRange().getBookmarks().get("SIGNERSTAMP1");

        msAssert.areEqual("Row1_Cell0_Text0\u0007Row1_Cell1_Text0\u0007", bookmark.getText());

        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getLastRow().getFirstCell();
        checkSetTextInColumnBookmark(bookmark, cell);
    }

    /// <summary>
    /// Relates to WORDSNET-19518.
    /// Checks that the range text is empty if the first column and last column are greater
    /// than the count of row cells.
    /// </summary>
    @Test
    public void test19518FirstColumnAndLastColumnMoreThanColumnsCount() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test19518ColumnsOutOfRange.docx");
        Bookmark bookmark = doc.getRange().getBookmarks().get("SIGNERSTAMP1");

        msAssert.areEqual("", bookmark.getText());
        bookmark.setText("test");
        msAssert.areEqual("", bookmark.getText());
    }

    /// <summary>
    /// Relates to WORDSNET-19518.
    /// Checks that the last column equals row last column if last column greater than the count of row cells.
    /// </summary>
    @Test
    public void test19518LastColumnMoreThanColumnsCount() throws Exception
    {
        final String BOOKMARK_TEXT =
            "Row0_Cell1_Text0Row0_Cell1_Text1\u0007Row0_Cell2_Text0Row0_Cell2_Text1Row0_Cell2_Text2\u0007\u0007" +
            "Row1_Cell0_Text0\u0007Row1_Cell1_Text0\u0007Row1_Cell2_Text0\u0007";

        Document doc = TestUtil.open("Model\\Bookmark\\Test19518LastColumnOutOfRange.docx");
        Bookmark bookmark = doc.getRange().getBookmarks().get("SIGNERSTAMP1");

        msAssert.areEqual(BOOKMARK_TEXT, bookmark.getText());

        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells().get(1);
        checkSetTextInColumnBookmark(bookmark, cell);
    }

    /// <summary>
    /// Relates to WORDSNET-19518.
    /// Checks that the first column equals the last column if the last column is less than the first column.
    /// </summary>
    @Test (dataProvider = "test19518LastColumnLessThanFirstColumnDataProvider")
    public void test19518LastColumnLessThanFirstColumn(String path, String bookmarkText) throws Exception
    {
        Document doc = TestUtil.open(path);
        Bookmark bookmark = doc.getRange().getBookmarks().get("SIGNERSTAMP1");

        msAssert.areEqual(bookmarkText, bookmark.getText());

        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells().get(bookmark.getLastColumn());
        checkSetTextInColumnBookmark(bookmark, cell);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19518LastColumnLessThanFirstColumnDataProvider")
	public static Object[][] test19518LastColumnLessThanFirstColumnDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Bookmark\\Test19518LastColumnLessThanFirstColumnA.docx",  "Row0_Cell1_Text0Row0_Cell1_Text1\u0007"},
			{"Model\\Bookmark\\Test19518LastColumnLessThanFirstColumnB.docx", 
        "Row0_Cell0\u0007Row0_Cell1\u0007Row0_Cell2\u0007\u0007Row1_Cell0\u0007"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-19518. Checks that BookmarkEnd may be not in a row.
    /// </summary>
    @Test (dataProvider = "test19518BookmarkNotInRowDataProvider")
    public void test19518BookmarkNotInRow(String path, String bookmarkText) throws Exception
    {
        Document doc = TestUtil.open(path);
        Bookmark bookmark = doc.getRange().getBookmarks().get("SIGNERSTAMP1");

        msAssert.areEqual(bookmarkText, bookmark.getText());

        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getFirstCell();
        checkSetTextInColumnBookmark(bookmark, cell);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19518BookmarkNotInRowDataProvider")
	public static Object[][] test19518BookmarkNotInRowDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Bookmark\\Test19518BookmarkEndNotInRowA.docx", 
        "Row1_Cell0\u0007Row1_Cell1\u0007Row1_Cell2\u0007\u0007Row2_Cell0\u0007Row2_Cell1\u0007"},
			{"Model\\Bookmark\\Test19518BookmarkEndNotInRowB.docx", 
        "Row1_Cell0\u0007Row1_Cell1\u0007Row1_Cell2\u0007\u0007Row2_Cell0\u0007Row2_Cell1\u0007"},
			{"Model\\Bookmark\\Test19518BookmarkEndNotInRowC.docx",  "Row1_Cell0\u0007Row1_Cell1\u0007"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-19518. Checks that BookmarkEnd may be not in next row.
    /// </summary>
    @Test (enabled = false)
    public void test19518BookmarkEndInNextRow(String path, String bookmarkText) throws Exception
    {
        Document doc = TestUtil.open(path);

        Bookmark bm1 = doc.getRange().getBookmarks().get("BM1");
        msAssert.areEqual(bookmarkText, bm1.getText());

        Bookmark bm2 = doc.getRange().getBookmarks().get("BM2");
        msAssert.areEqual(bookmarkText, bm2.getText());

        checkSetTextInColumnBookmark(bm1, doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getCells().get(1));
        checkSetTextInColumnBookmark(bm2, doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getCells().get(1));
    }

    /// <summary>
    /// Relates to WORDSNET-19518. Checks that BookmarkStarts move to
    /// the first paragraph from other paragraphs within the cell when inserting text.
    /// </summary>
    @Test
    public void test19518BookmarkStartsMovingInFirstPara() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test19518BookmarkStartsMovingInFirstPara.docx");

        Bookmark bm1 = doc.getRange().getBookmarks().get("BM1");
        checkSetTextInColumnBookmark(bm1, doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getCells().get(0));

        Paragraph para = doc.getFirstSection().getBody().getTables().get(0).getRows().get(0).getCells().get(0).getFirstParagraph();
        msAssert.areEqual(para, doc.getRange().getBookmarks().get("BM1").getBookmarkStart().getParentNode());
        msAssert.areEqual(para, doc.getRange().getBookmarks().get("BM2").getBookmarkStart().getParentNode());
    }





    /// <summary>
    /// WORDSNET-21830 Replacing overlapping Bookmarks behaves unexpected or causes an error with Java.
    /// Fixed by restoring deleted overlapping bookmarks if they were deleted.
    /// </summary>
    @Test
    public void test21830() throws Exception
    {
        final String TEST_FILE_NAME_WITHOUT_EXTENSION = "Model\\Bookmark\\Test21830";

        Document doc = TestUtil.open(TEST_FILE_NAME_WITHOUT_EXTENSION, LoadFormat.DOCM);
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("Aaaaaaa\rXxx", doc.getRange().getBookmarks().get("mark1").getText());
        msAssert.areEqual("Xxx\rbbbbbbb", doc.getRange().getBookmarks().get("mark2").getText());

        // The first customer's experiment.
        doc.getRange().getBookmarks().get("mark1").setText("1111111\r");
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("1111111\r", doc.getRange().getBookmarks().get("mark1").getText());
        msAssert.areEqual("1111111\r\rbbbbbbb", doc.getRange().getBookmarks().get("mark2").getText());

        // WORDSNET-22642 Word removes `mark1` as its both End and Start are located inside 'mark2'.
        doc.getRange().getBookmarks().get("mark2").setText("2222222\r");
        msAssert.areEqual(1, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("2222222\r", doc.getRange().getBookmarks().get("mark2").getText());

        // Re-saving confirms the result.
        doc = TestUtil.saveOpen(doc, TEST_FILE_NAME_WITHOUT_EXTENSION, UnifiedScenario.DOCM_2_DOCM_NO_GOLD);
        msAssert.areEqual(1, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("2222222\r", doc.getRange().getBookmarks().get("mark2").getText());

        // The second customer's experiment (different order of replacements).
        doc = TestUtil.open(TEST_FILE_NAME_WITHOUT_EXTENSION, LoadFormat.DOCM);
        doc.getRange().getBookmarks().get("mark2").setText("2222222\r");
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("Aaaaaaa\r", doc.getRange().getBookmarks().get("mark1").getText());
        msAssert.areEqual("2222222\r", doc.getRange().getBookmarks().get("mark2").getText());

        doc.getRange().getBookmarks().get("mark1").setText("1111111\r");
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("1111111\r", doc.getRange().getBookmarks().get("mark1").getText());
        // This is wrong. Actually Word in this scenario displays "1111111\r2222222\r" for this bookmark.
        // The issue seems in UntagleStart/End methods.
        msAssert.areEqual("2222222\r", doc.getRange().getBookmarks().get("mark2").getText());

        // Re-saving confirms the result.
        doc = TestUtil.saveOpen(doc, TEST_FILE_NAME_WITHOUT_EXTENSION, UnifiedScenario.DOCM_2_DOCM_NO_GOLD);
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("1111111\r", doc.getRange().getBookmarks().get("mark1").getText());
        msAssert.areEqual("2222222\r", doc.getRange().getBookmarks().get("mark2").getText());
    }

    /// <summary>
    /// WORDSNET-22642 Wrong Replacement of Text Content if Bookmarks in Word DOCM Document are enclosing each other.
    /// The enclosed bookmarks, that have its both Start and End inside the range being removed, should be removed.
    /// This is follow up of incorrect WORDSNET-21830, where ALL removed bookmarks were restored.
    /// </summary>
    @Test
    public void test22642() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test22642.docm");

        doc.getRange().getBookmarks().get("mark2").setText("22222");

        // Check document structure: the first node is BookmarkStart, then Run with specified text "22222"
        // and at the last BookmarkEnd. There should not be any another nodes (i.e. bookmarks).
        BookmarkCollection bookmarks = doc.getRange().getBookmarks();
        msAssert.areEqual(1, bookmarks.getCount());
        msAssert.areEqual("mark2", bookmarks.get(0).getName());

        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(bookmarks.get(0).getBookmarkStart(), para.getFirstChild());

        Run run = (Run)bookmarks.get(0).getBookmarkStart().getNextSibling();
        msAssert.areEqual("22222", run.getText());

        msAssert.areEqual(bookmarks.get(0).getBookmarkEnd(), run.getNextSibling());

        // It will be also convenient to check gold here.
        TestUtil.saveCheckGold(doc, "Model\\Bookmark\\Test22642.docm");
    }






    /// <summary>
    /// WORDSNET-24140 Nested bookmark is removed after setting text of outer bookmark.
    /// Checks whether nested bookmarks are not deleted when a text is inserted.
    /// </summary>
    @Test
    public void test24140() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test24140.dotx");
        msAssert.isNotNull(doc.getRange().getBookmarks().get("True"));
        Bookmark bookmark = doc.getRange().getBookmarks().get("False2");
        bookmark.setText("");
        doc = TestUtil.saveOpen(doc, "Test24140", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.isNotNull(doc.getRange().getBookmarks().get("True"));
    }

    private static void checkBookmarkOfTest19767(Bookmark bookmark, String bookmarkNewText) throws Exception
    {
        BookmarkStart bookmarkStart = bookmark.getBookmarkStart();
        BookmarkEnd bookmarkEnd = bookmark.getBookmarkEnd();
        Paragraph paragraph1 = (Paragraph)bookmarkStart.getParentNode();
        Paragraph paragraph2 = (Paragraph)paragraph1.getNextNonAnnotationSibling();

        int beforeParagraph1 = paragraph1.getParaPr().getSpaceBefore();
        int afterParagraph1 = paragraph1.getParaPr().getSpaceAfter();
        int beforeParagraph2 = paragraph2.getParaPr().getSpaceBefore();
        int afterParagraph2 = paragraph2.getParaPr().getSpaceAfter();
        int paragraph1FontSize = paragraph1.getParagraphBreakRunPr().getSize();
        int paragraph2FontSize = paragraph2.getParagraphBreakRunPr().getSize();

        boolean isEndedInFirstParagraph = (bookmarkEnd.getParentNode() == paragraph1);

        bookmark.setText(bookmarkNewText);

        boolean isParagraphInserted = bookmarkNewText.contains("\r");
        Paragraph bookmarkStartParagraph = (Paragraph)bookmarkStart.getParentNode();
        Paragraph bookmarkEndParagraph = (Paragraph)bookmarkEnd.getParentNode();
        if (!isParagraphInserted)
            msAssert.areSame(bookmarkStartParagraph, bookmarkEndParagraph);

        ParaPr resultParaPr = bookmarkStartParagraph.getParaPr();
        Run nextRun = (Run)bookmarkEnd.getNextNonAnnotationSibling();

        if (!"".equals(bookmarkNewText))
        {
            Run bookmarkRun = (Run)bookmarkStart.getNextNonAnnotationSibling();
            msAssert.areEqual(paragraph1FontSize, bookmarkRun.getRunPr().getSize());
        }

        if (isEndedInFirstParagraph)
        {
            msAssert.areSame(paragraph1, bookmarkEndParagraph);
            msAssert.areEqual(beforeParagraph1, resultParaPr.getSpaceBefore());
            msAssert.areEqual(afterParagraph1, resultParaPr.getSpaceAfter());
            msAssert.areEqual(paragraph1FontSize, nextRun.getRunPr().getSize());
        }
        else
        {
            msAssert.areSame(paragraph2, bookmarkEndParagraph);
            msAssert.areEqual(beforeParagraph2, resultParaPr.getSpaceBefore());
            msAssert.areEqual(afterParagraph2, resultParaPr.getSpaceAfter());
            msAssert.areEqual(paragraph2FontSize, nextRun.getRunPr().getSize());
        }

        msAssert.areEqual((isEndedInFirstParagraph || isParagraphInserted) ? paragraph1FontSize : paragraph2FontSize, bookmarkStartParagraph.getParagraphBreakRunPr().getSize());
        msAssert.areEqual(isEndedInFirstParagraph ? paragraph1FontSize : paragraph2FontSize, bookmarkEndParagraph.getParagraphBreakRunPr().getSize());
    }

    private static void checkTableStructure(Table table)
    {
        BookmarkCollection bookmarks = table.getRange().getBookmarks();
        RowCollection rows = table.getRows();

        msAssert.areEqual(3, rows.getCount());
        msAssert.areEqual(3, rows.get(0).getCells().getCount());
        msAssert.areEqual(4, rows.get(1).getCells().getCount());
        msAssert.areEqual(5, rows.get(2).getCells().getCount());

        Bookmark bmTitel = bookmarks.get("titel");
        msAssert.isNotNull(bmTitel);
        msAssert.isNotNull(bookmarks.get("dok"));
        msAssert.isNotNull(bookmarks.get("dat"));
        msAssert.isNotNull(bookmarks.get("rev"));
        msAssert.isNotNull(bookmarks.get("utf"));
        msAssert.isNotNull(bookmarks.get("app1"));

        msAssert.areEqual(table.getFirstRow().getFirstCell().getFirstParagraph(), bmTitel.getBookmarkStart().getParentNode());
        for (int i = 1; i < bookmarks.getCount(); i++)
            msAssert.areEqual(table.getLastRow().getFirstCell().getFirstParagraph(), bookmarks.get(i).getBookmarkStart().getParentNode());
    }

    private static void checkSetTextInColumnBookmark(Bookmark bookmark, Cell cell) throws Exception
    {
        bookmark.setText("test");
        msAssert.areEqual("test\u0007", cell.getRange().getText());
    }

    /// <summary>
    /// Checks result of setting text for simple case with merging paragraphs.
    /// </summary>
    private static void checkSettingTextWithParagraphMerge(Document doc) throws Exception
    {
        Bookmark bookmark = doc.getRange().getBookmarks().get("Test");
        msAssert.areEqual("Paragraph 2\r", bookmark.getText());
        /*NodeLevel*/int startLevel = bookmark.getBookmarkStart().getNodeLevel();

        Node childOf3Para = doc.getFirstSection().getBody().getParagraphs().get(2).getFirstChild();
        /*NodeType*/int nodeTypeAfterBookmark = childOf3Para != null ? childOf3Para.getNodeType() : NodeType.NULL;
        String textAfterBookmark = childOf3Para != null ? childOf3Para.getText() : null;

        final String NEW_TEXT = "New";
        bookmark.setText(NEW_TEXT);
        bookmark = doc.getRange().getBookmarks().get("Test");
        msAssert.areEqual(NEW_TEXT, bookmark.getText());

        BookmarkEnd end = bookmark.getBookmarkEnd();
        if (nodeTypeAfterBookmark != NodeType.NULL)
        {
            msAssert.areEqual(NodeType.RUN, end.getNextSibling().getNodeType());
            msAssert.areEqual("Paragraph 3", ((Run)end.getNextSibling()).getText());
        }
        else
        {
            msAssert.isNull(end.getNextSibling());
        }
        msAssert.areEqual(1701, ((Paragraph)end.getParentNode()).getParaPr().getLeftIndent());

        BookmarkStart start = bookmark.getBookmarkStart();
        msAssert.areEqual(startLevel, start.getNodeLevel());
        if (startLevel == NodeLevel.BLOCK)
        {
            msAssert.areSame(end.getParentNode(), start.getNextSibling());
        }
        else
        {
            msAssert.areEqual(NodeType.RUN, start.getNextSibling().getNodeType());
            msAssert.areEqual(NEW_TEXT, ((Run)start.getNextSibling()).getText());
        }
    }
}

