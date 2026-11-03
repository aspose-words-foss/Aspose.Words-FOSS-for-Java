// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;
import com.aspose.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for bookmarks.
/// </summary>
@Test
public class TestBookmarks extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBookmarks(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarks", lf, sf);

        NodeList starts = doc.selectNodes("//BookmarkStart");
        NodeList ends = doc.selectNodes("//BookmarkEnd");

        // Check bookmarks are present.
        msAssert.areEqual(3, starts.getCount());
        msAssert.areEqual(3, ends.getCount());

        // Check bookmarks have names and start and end where expected.
        BookmarkStart bmkStart = (BookmarkStart)starts.get(0);
        msAssert.areEqual("TestBookmark1", bmkStart.getName());
        msAssert.isTrue(bmkStart.getNextSibling().getText().indexOf("starts in this") != -1);

        BookmarkEnd bmkEnd = (BookmarkEnd)ends.get(0);
        msAssert.areEqual("TestBookmark2", bmkEnd.getName());
        msAssert.isTrue(bmkEnd.getNextSibling().getText().indexOf("and another") != -1);

        // This bookmark is inside bookmark 1.
        bmkStart = (BookmarkStart)starts.get(1);
        msAssert.areEqual("TestBookmark2", bmkStart.getName());
        msAssert.areEqual("paragraph ", bmkStart.getNextSibling().getText());

        // This bookmark is related to the form field.
        bmkStart = (BookmarkStart)starts.get(2);
        msAssert.areEqual("Text1", bmkStart.getName());
        msAssert.areEqual(" FORMTEXT ", bmkStart.getNextSibling().getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarksSourceDataProvider")
	public void unifiedTestBookmarksSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarks(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarksSourceDataProvider")
	Object[][] unifiedTestBookmarksSourceDataProvider() throws Exception
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


    private static void checkBookmarksSamePosition(Document doc, boolean isBookmarkExpectedAtBlockLevel)
    {
        NodeList starts = doc.selectNodes("//BookmarkStart");
        NodeList ends = doc.selectNodes("//BookmarkEnd");

        // Check bookmarks are present.
        msAssert.areEqual(3, starts.getCount());
        msAssert.areEqual(3, ends.getCount());

        Paragraph firstParagraph = doc.getFirstSection().getBody().getParagraphs().get(0);

        BookmarkStart bmkStart1 = (BookmarkStart)starts.get(0);
        msAssert.areEqual("Section1", bmkStart1.getName());
        msAssert.areSame(firstParagraph, bmkStart1.getParentNode());
        BookmarkStart bmkStart2 = (BookmarkStart)starts.get(1);
        msAssert.areEqual("Section1And2", bmkStart2.getName());
        msAssert.areSame(firstParagraph, bmkStart2.getParentNode());

        // Start of Bmk2 is right after Bmk1.
        msAssert.isTrue(bmkStart1.getNextSibling() == bmkStart2);
        msAssert.isTrue(bmkStart2.getNextSibling().getText().indexOf("Section 1.") != -1);

        BookmarkEnd bmkEnd2 = (BookmarkEnd)ends.get(1);
        msAssert.areEqual("Section1And2", bmkEnd2.getName());
        BookmarkEnd bmkEnd3 = (BookmarkEnd)ends.get(2);
        msAssert.areEqual("Section2", bmkEnd3.getName());

        // End of Bmk3 is right after end of Bmk2.
        msAssert.isTrue(bmkEnd2.getNextSibling() == bmkEnd3);
        if (isBookmarkExpectedAtBlockLevel)
        {
            msAssert.areEqual(NodeType.BODY, bmkEnd3.getParentNode().getNodeType());
            msAssert.areSame(doc.getLastSection().getBody().getLastChild(), bmkEnd3.getNextSibling());
        }
        else
        {
            msAssert.isTrue(bmkEnd3.getNextSibling() == null);
            msAssert.areSame(doc.getLastSection().getBody().getLastChild(), bmkEnd3.getParentNode());
        }
    }






    /// <summary>
    /// Test bookmarks in different stories of the document are supported.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBookmarksInStories(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarksInStories", lf, sf);

        msAssert.areEqual(6, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("Header", doc.getRange().getBookmarks().get("BmkHeader").getText());
        // Note that if a bookmark in body encompasses a textbox, then the text of the textbox will
        // be included in the text of the bookmark. This is not really nice, but I cannot change now.
        msAssert.areEqual("Body", doc.getRange().getBookmarks().get("BmkBody").getText());
        msAssert.areEqual("Textbox", doc.getRange().getBookmarks().get("BmkTextbox").getText());
        msAssert.areEqual("Endnote", doc.getRange().getBookmarks().get("BmkEndnote").getText());
        msAssert.areEqual("Footnote", doc.getRange().getBookmarks().get("BmkFootnote").getText());
        msAssert.areEqual("Comment", doc.getRange().getBookmarks().get("BmkComment").getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarksInStoriesSourceDataProvider")
	public void unifiedTestBookmarksInStoriesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarksInStories(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarksInStoriesSourceDataProvider")
	Object[][] unifiedTestBookmarksInStoriesSourceDataProvider() throws Exception
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
    void unifiedTestBookmarkSetText(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarkSetText", lf, sf);

        msAssert.areEqual("This line contains a bookmark.\f", doc.getText());
        doc.getRange().getBookmarks().get("bmk").setText("");
        msAssert.areEqual("This line contains a .\f", doc.getText());
        doc.getRange().getBookmarks().get("bmk").setText("hello");
        msAssert.areEqual("This line contains a hello.\f", doc.getText());
        msAssert.areEqual("hello", doc.getRange().getBookmarks().get("bmk").getText());

        TestUtil.saveOpen(doc, "Model\\TestBookmarkSetText Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkSetTextSourceDataProvider")
	public void unifiedTestBookmarkSetTextSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkSetText(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkSetTextSourceDataProvider")
	Object[][] unifiedTestBookmarkSetTextSourceDataProvider() throws Exception
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
    void unifiedTestBookmarkRename(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarkRename", lf, sf);
        doc.getRange().getBookmarks().get("Test").setName("XXX");
        doc = TestUtil.saveOpen(doc, "Model\\TestBookmarkRename Modified", lf, sf);
        msAssert.isNull(doc.getRange().getBookmarks().get("Test"));
        msAssert.isNotNull(doc.getRange().getBookmarks().get("XXX"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkRenameSourceDataProvider")
	public void unifiedTestBookmarkRenameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkRename(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkRenameSourceDataProvider")
	Object[][] unifiedTestBookmarkRenameSourceDataProvider() throws Exception
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
    void unifiedTestBookmarkRemove(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\TestBookmarks", lf);
        doc.getRange().getBookmarks().remove("testbookmark1");
        doc.getRange().getBookmarks().remove("Text1");
        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestBookmarkRemove", lf, sf);
        msAssert.areEqual(1, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("paragraph ", doc.getRange().getBookmarks().get("TestBookmark2").getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkRemoveSourceDataProvider")
	public void unifiedTestBookmarkRemoveSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkRemove(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkRemoveSourceDataProvider")
	Object[][] unifiedTestBookmarkRemoveSourceDataProvider() throws Exception
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
    void unifiedTestBookmarkRemoveAll(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\TestBookmarks", lf);
        doc.getRange().getBookmarks().clear();
        msAssert.areEqual(0, doc.getRange().getBookmarks().getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkRemoveAllSourceDataProvider")
	public void unifiedTestBookmarkRemoveAllSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkRemoveAll(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkRemoveAllSourceDataProvider")
	Object[][] unifiedTestBookmarkRemoveAllSourceDataProvider() throws Exception
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
    /// Adjacent bookmarks are overlapped in the model, check that setting text does not delete overlapped bookmarks.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBookmarkAdjacent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarkAdjacent", lf, sf);
        doc.getRange().getBookmarks().get("bmk1").setText("XXX");
        doc.getRange().getBookmarks().get("bmk2").setText("YYY");

        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestBookmarkAdjacent Modified", lf, sf);
        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual("XXXYYY\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkAdjacentSourceDataProvider")
	public void unifiedTestBookmarkAdjacentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkAdjacent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkAdjacentSourceDataProvider")
	Object[][] unifiedTestBookmarkAdjacentSourceDataProvider() throws Exception
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
    /// There was a problem when font size was changed after setting bookmark text.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestBookmarkFontSize(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestBookmarkFontSize", lf, sf);
        doc.getRange().getBookmarks().get("bmk1").setText("Hello1");
        doc.getRange().getBookmarks().get("bmk2").setText("Hello2");

        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestBookmarkFontSize Modified", lf, sf);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Existing run of text before the bookmark - it was influencing the change of the font size.
        Run run = (Run)runs.get(0);
        msAssert.areEqual("OldText1", run.getText());
        msAssert.areEqual(18.0, run.getFont().getSize());

        // New runs of text that have correct font sizes now.
        run = (Run)runs.get(1);
        msAssert.areEqual("Hello1", run.getText());
        msAssert.areEqual(12.0, run.getFont().getSize());

        run = (Run)runs.get(2);
        msAssert.areEqual("Hello2", run.getText());
        msAssert.areEqual(12.0, run.getFont().getSize());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestBookmarkFontSizeSourceDataProvider")
	public void unifiedTestBookmarkFontSizeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestBookmarkFontSize(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestBookmarkFontSizeSourceDataProvider")
	Object[][] unifiedTestBookmarkFontSizeSourceDataProvider() throws Exception
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
    /// Start and end of a bookmark are inline.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInline(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestInline", lf, sf);

        BookmarkStart bs = (BookmarkStart)doc.getChild(NodeType.BOOKMARK_START, 0, true);
        msAssert.areEqual("Te", bs.getPreviousSibling().getText());

        Node node = bs.getNextSibling();
        msAssert.areEqual("xt with a boo", node.getText());

        node = node.getNextSibling();
        msAssert.areEqual(NodeType.BOOKMARK_END, node.getNodeType());

        node = node.getNextSibling();
        msAssert.areEqual("kmark.", node.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlineSourceDataProvider")
	public void unifiedTestInlineSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInline(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlineSourceDataProvider")
	Object[][] unifiedTestInlineSourceDataProvider() throws Exception
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
    /// Adjacent bookmarks are overlapped in the model, check that setting text does not delete overlapped bookmarks.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestInlineAdjacent(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestInlineAdjacent", lf, sf);

        BookmarkStart bmk1Start = (BookmarkStart)doc.getChild(NodeType.BOOKMARK_START, 0, true);
        msAssert.areEqual("bmk1", bmk1Start.getName());

        // bmk2 start is before bmk1 at the moment.
        BookmarkStart bmk2Start = (BookmarkStart)bmk1Start.getNextSibling().getNextSibling();
        msAssert.areEqual("bmk2", bmk2Start.getName());

        BookmarkEnd bmk1End = (BookmarkEnd)bmk2Start.getNextSibling();
        msAssert.areEqual("bmk1", bmk1End.getName());

        bmk1Start.getBookmark().setText("Hello!");

        // Check that setting bmk1 text has untangled the bookmarks. bmk2 now starts after bmk1 ends.
        msAssert.areEqual(bmk1End.getNextSibling(), bmk2Start);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestInlineAdjacentSourceDataProvider")
	public void unifiedTestInlineAdjacentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestInlineAdjacent(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestInlineAdjacentSourceDataProvider")
	Object[][] unifiedTestInlineAdjacentSourceDataProvider() throws Exception
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
    /// When bookmark end is next sibling of a paragraph, in inline bookmarks mode,
    /// the bookmark end is moved in the model to the beginning of the next paragraph.
    /// In block bookmarks mode, the position of the bookmark is preserved.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestIncludeEndOfPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        boolean isBookmarkSupportedAtBlockLevel =
            ((lf == LoadFormat.DOCX) || (lf == LoadFormat.WORD_ML)) &&
            ((sf == SaveFormat.DOCX) || (sf == SaveFormat.WORD_ML));

        Document doc = openSaveOpen("Model\\Bookmark\\TestIncludeEndOfPara", lf, sf,
            isBookmarkSupportedAtBlockLevel ? "_BlockBm" : "", 1);
        checkIncludeEndOfPara(doc, isBookmarkSupportedAtBlockLevel);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestIncludeEndOfParaSourceDataProvider")
	public void unifiedTestIncludeEndOfParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestIncludeEndOfPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestIncludeEndOfParaSourceDataProvider")
	Object[][] unifiedTestIncludeEndOfParaSourceDataProvider() throws Exception
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

    private static void checkIncludeEndOfPara(Document doc, boolean isBookmarkExpectedAtBlockLevel)
    {
        BookmarkStart bs = (BookmarkStart)doc.getChild(NodeType.BOOKMARK_START, 0, true);
        msAssert.areEqual("bmk", bs.getName());

        Node node = bs.getNextSibling();
        msAssert.areEqual(NodeType.RUN, node.getNodeType());
        msAssert.areEqual("paragraph.", node.getText());
        msAssert.areEqual(null, node.getNextSibling());    //No more nodes after this run.

        if (isBookmarkExpectedAtBlockLevel)
        {
            //The end of bookmark is next to paragraph of the bookmark start.
            msAssert.areEqual(NodeType.BOOKMARK_END, bs.getParentNode().getNextSibling().getNodeType());
        }
        else
        {
            //The end of bookmark is at the start of the next paragraph.
            Paragraph para = (Paragraph)node.getParentNode().getNextSibling();
            msAssert.areEqual(NodeType.BOOKMARK_END, para.getFirstChild().getNodeType());
        }
    }

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestExcludeEndOfPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestExcludeEndOfPara", lf, sf);

        BookmarkStart bs = (BookmarkStart)doc.getChild(NodeType.BOOKMARK_START, 0, true);
        msAssert.areEqual("bmk", bs.getName());
        msAssert.areEqual(null, bs.getPreviousSibling()); //Bookmark start is the first item in the paragraph.

        Node node = bs.getNextSibling().getNextSibling();    //Skip the run, jump to the bookmark end.
        msAssert.areEqual(NodeType.BOOKMARK_END, node.getNodeType());
        msAssert.areEqual(null, node.getNextSibling());    //Bookmark end is the last child in the paragraph.
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestExcludeEndOfParaSourceDataProvider")
	public void unifiedTestExcludeEndOfParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestExcludeEndOfPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestExcludeEndOfParaSourceDataProvider")
	Object[][] unifiedTestExcludeEndOfParaSourceDataProvider() throws Exception
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
    /// Setting text of a bookmark that starts at the end of a paragraph used to throw.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSetTextAtEndOfPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestSetTextAtEndOfPara", lf, sf);
        doc.getRange().getBookmarks().get("bmk").setText("");
        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestSetTextAtEndOfPara Modified", lf, sf);
        msAssert.areEqual("Para1\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSetTextAtEndOfParaSourceDataProvider")
	public void unifiedTestSetTextAtEndOfParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSetTextAtEndOfPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSetTextAtEndOfParaSourceDataProvider")
	Object[][] unifiedTestSetTextAtEndOfParaSourceDataProvider() throws Exception
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
    /// WORDSNET-760 Setting Bookmark.Text property to string containing CRLF characters causes crash
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestSetTextWithPara(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestSetTextWithPara", lf, sf);
        doc.getRange().getBookmarks().get("bmk").setText("XXX\rYYY");
        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestSetTextWithPara Modified", lf, sf);
        msAssert.areEqual("PaXXX\rYYYra2\f", doc.getText());
        msAssert.areEqual(2, doc.getChildNodes(NodeType.PARAGRAPH, true).getCount());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestSetTextWithParaSourceDataProvider")
	public void unifiedTestSetTextWithParaSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestSetTextWithPara(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestSetTextWithParaSourceDataProvider")
	Object[][] unifiedTestSetTextWithParaSourceDataProvider() throws Exception
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
    void unifiedTestTableColumnBookmark(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Bookmark\\TestTableColumnBookmark", lf, sf);
        BookmarkStart bookmarkStart = doc.getRange().getBookmarks().get("TableColumnBookmark").getBookmarkStart();

        msAssert.isTrue(bookmarkStart.isColumn());
        msAssert.areEqual(1, bookmarkStart.getFirstColumn());
        msAssert.areEqual(3, bookmarkStart.getLastColumn());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestTableColumnBookmarkSourceDataProvider")
	public void unifiedTestTableColumnBookmarkSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestTableColumnBookmark(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestTableColumnBookmarkSourceDataProvider")
	Object[][] unifiedTestTableColumnBookmarkSourceDataProvider() throws Exception
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
    /// Creates new column using as a template the cloned cell with bookmark inside.
    /// </summary>
    Document createSrcDoc() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\TestJira9413.docx");

        DocumentBuilder documentBuilder = new DocumentBuilder(doc);
        documentBuilder.moveToBookmark("IncomeStatement");

        Table table = (Table)documentBuilder.getCurrentNode().getAncestor(NodeType.TABLE);

        Cell hCell = (Cell)table.getFirstRow().getFirstCell().deepClone(true);
        table.getFirstRow().appendChild(hCell);

        documentBuilder.moveTo(hCell.getFirstParagraph());
        documentBuilder.write("NewColumn");

        return documentBuilder.getDocument();
    }

    /// <summary>
    /// WORDSNET-10653 System.ArgumentException occurs after appending one document to another.
    /// The problem occued because there are two bookmarks with the same name in different stories.
    /// Made code recilient.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect10653(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String BOOKMARK_NAME = "testBookmark";
        final String BOOKMARK_NAME_1 = "testBookmark1";

        DocumentBuilder builder = new DocumentBuilder();

        // To replicate the issue we should insert two bookmarks with the same name in different stories.
        // For instance, let's insert one bookmark in the main story and another in the header.
        builder.startBookmark(BOOKMARK_NAME);
        builder.endBookmark(BOOKMARK_NAME);
        builder.writeln();

        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.startBookmark(BOOKMARK_NAME);
        builder.endBookmark(BOOKMARK_NAME);

        // Test story nesting.
        // Insert one bookmark in normal text and another in shape.
        builder.moveToDocumentEnd();
        builder.startBookmark(BOOKMARK_NAME_1);
        builder.writeln();
        builder.writeln();
        builder.writeln();
        builder.endBookmark(BOOKMARK_NAME_1);

        Shape textbox = new Shape(builder.getDocument(), ShapeType.TEXT_BOX);
        textbox.setWidth(100.0);
        textbox.setHeight(100.0);
        textbox.setWrapType(WrapType.INLINE);
        Paragraph paragraph = new Paragraph(builder.getDocument());
        textbox.appendChild(paragraph);

        ((Paragraph)builder.getCurrentParagraph().getPreviousSibling()).appendChild(textbox);
        builder.moveTo(paragraph);
        builder.startBookmark(BOOKMARK_NAME_1);
        builder.write("This is text inside textbox");
        builder.endBookmark(BOOKMARK_NAME_1);

        msAssert.areEqual(4, builder.getDocument().getRange().getBookmarks().getCount());
        msAssert.areEqual(BOOKMARK_NAME, builder.getDocument().getRange().getBookmarks().get(0).getName());
        msAssert.areEqual(BOOKMARK_NAME_1, builder.getDocument().getRange().getBookmarks().get(1).getName());
        msAssert.areEqual(BOOKMARK_NAME_1, builder.getDocument().getRange().getBookmarks().get(2).getName());
        msAssert.areEqual(BOOKMARK_NAME, builder.getDocument().getRange().getBookmarks().get(3).getName());

        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\Bookmark\\TestDefect10653", lf, sf);

        msAssert.areEqual(2, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual(BOOKMARK_NAME, doc.getRange().getBookmarks().get(0).getName());
        msAssert.areEqual(BOOKMARK_NAME_1, doc.getRange().getBookmarks().get(1).getName());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect10653SourceDataProvider")
	public void unifiedTestDefect10653Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect10653(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect10653SourceDataProvider")
	Object[][] unifiedTestDefect10653SourceDataProvider() throws Exception
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


    private static void checkBookmarksAtRowEnd(Document doc, boolean isBookmarkExpectedAtBlockLevel) throws Exception
    {
        // This bookmark is before the row break character in the DOC file.
        Bookmark bmk = doc.getRange().getBookmarks().get("RowETA1End");
        msAssert.areEqual("", bmk.getText());

        // Check that the bookmark is in the appropriate place in the model.
        if (isBookmarkExpectedAtBlockLevel)
        {
            Cell cell = (Cell)bmk.getBookmarkStart().getPreviousSibling();
            msAssert.areEqual("___________________\u0007", cell.getText());
        }
        else
        {
            Cell cell = (Cell)bmk.getBookmarkStart().getParentNode().getParentNode();
            msAssert.areEqual("\u0013 FORMTEXT \u0001\u0014Ten (10) years\u0015\u0007", cell.getText());
        }
    }

    /// <summary>
    /// Opens, saves and then opens document again with the specified parameters and with gold checking.
    /// </summary>
    private static Document openSaveOpen(String fileName, /*LoadFormat*/int lf, /*SaveFormat*/int sf,
        String goldFileNamePostfix, int expectedBookmarkCount) throws Exception
    {
        Document doc = TestUtil.open(fileName, lf);

        doc = TestUtil.saveOpen(doc, fileName + goldFileNamePostfix, lf, sf);

        msAssert.areEqual(expectedBookmarkCount, doc.getRange().getBookmarks().getCount());

        return doc;
    }

    /// <summary>
    /// WORDSNET-4691 InvalidCastException occurs during Bookmark processing.
    /// The problem occurred because bookmark ends in SmartTag,
    /// i.e. start and end of bookmark are on different levels in the documents hierarchy.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect4691(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        final String EXPECTED_CONTENT = "per Test in het";

        Document doc = TestUtil.open("Model\\Bookmark\\TestDefect4691", lf);

        doc.getRange().getBookmarks().get("ArtStartDate").setText("Test");

        msAssert.areEqual(EXPECTED_CONTENT, msString.trim(doc.toString(SaveFormat.TEXT)));

        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestDefect4691", lf, sf);

        msAssert.areEqual(EXPECTED_CONTENT, msString.trim(doc.toString(SaveFormat.TEXT)));

        // Make sure bookmark is still there.
        msAssert.areEqual("Test", doc.getRange().getBookmarks().get("ArtStartDate").getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect4691SourceDataProvider")
	public void unifiedTestDefect4691Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect4691(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect4691SourceDataProvider")
	Object[][] unifiedTestDefect4691SourceDataProvider() throws Exception
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
    /// Tests node level of a bookmark in a textbox.
    /// </summary>
    @Test
    public void testBlockLevelBookmarkInTextbox() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\RevisionGroups\\TestJira15785_DeletedTextBox.docx");
        Bookmark bm = doc.getRange().getBookmarks().get("_GoBack");
        msAssert.areEqual(NodeLevel.BLOCK, bm.getBookmarkEnd().getNodeLevel());
    }


    /// <summary>
    /// Relates to WORDSNET-11024
    /// Slightly complex case when main story has bookmark which is duplicated in DrawingML and AlternateContent.
    /// </summary>
    @Test
    public void testJira11024A() throws Exception
    {
        // Source document has DrawingML which contains two bookmarks having 'Test' and 'Test2' names.
        Document doc = TestUtil.open("Model\\Bookmark\\TestJira11024A.docx");

        // Insert one more bookmark having 'Test2' name into main document body.
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.startBookmark("Test2");
        builder.write("New Test2 Bookmark");
        builder.endBookmark("Test2");

        // Insert one more bookmark having 'Test' name into main document body.
        doc.getFirstSection().getBody().getParagraphs().add(new Paragraph(doc));
        builder.moveToDocumentEnd();
        builder.startBookmark("Test");
        builder.write("New Test Bookmark");
        builder.endBookmark("Test");

        // Let validators work.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        // 'Test2' bookmark should be removed from main document body but 'Test' bookmark should be preserved.
        msAssert.isNull(doc.getFirstSection().getBody().getFirstParagraph().getRange().getBookmarks().get("Test2"));
        msAssert.isNotNull(doc.getFirstSection().getBody().getLastParagraph().getRange().getBookmarks().get("Test"));

        // 'Test' bookmark should be removed from both DrawingML and AlternateContent but 'Test2' bookmark should be preserved.
        Shape dml = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.isNull(dml.getRange().getBookmarks().get("Test"));
        // FOSS no fallback shapes

        msAssert.isNotNull(dml.getRange().getBookmarks().get("Test2"));
        // FOSS no fallback shapes
    }

    /// <summary>
    /// WORDSNET-11558 BookmarkStart.remove() causes an IllegalStateException
    /// Made bookmark fetcher resilient to deletion of bookmark start.
    /// </summary>
    @Test
    public void testJira11558() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\TestJira11558.docx");

        BookmarkCollection bookmarks = doc.getRange().getBookmarks();
        Bookmark bk = bookmarks.get(0);

        bk.getBookmarkStart().remove(); // remove the BookmarkStart

        BookmarkEnd end = bk.getBookmarkEnd();
        msAssert.areEqual("bm", end.getName());
    }

    /// <summary>
    /// WORDSNET-12492 Allow creation of bookmarks over 40 chars long when the target document is PDF.
    /// This would allow to create long meaningful bookmark names for Pdf format,
    /// and perhaps less human-readable ones for another.
    /// </summary>
    @Test
    public void testJira12492() throws Exception
    {
        final String FIELD_TEXT_ORIG = "FieldText";
        final String BOOKMARK_TEXT = "Text inside a bookmark.";
        final String LONG_BOOKMARK_NAME = "MyBookmarkMyBookmarkMyBookmarkMyBookmarkMyBookmark";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create bookmark.
        builder.startBookmark(LONG_BOOKMARK_NAME);
        builder.writeln(BOOKMARK_TEXT);
        builder.endBookmark(LONG_BOOKMARK_NAME);

        // Append "Ref" field.
        Paragraph p = builder.insertParagraph();
        FieldRef fieldRef = (FieldRef)p.appendField(FieldType.FIELD_REF, false);
        fieldRef.setBookmarkName(LONG_BOOKMARK_NAME);
        fieldRef.setResult(FIELD_TEXT_ORIG);

        // Append "Unknown" field.
        p = builder.insertParagraph();
        p.appendChild(new FieldStart(doc, new RunPr(), FieldType.FIELD_REF_NO_KEYWORD));
        p.appendChild(new Run(doc, LONG_BOOKMARK_NAME));
        p.appendChild(new FieldEnd(doc, new RunPr(), FieldType.FIELD_REF_NO_KEYWORD, false));

        // Update fields.
        doc.updateFields();

        // Check bookmarks can be found after document fields were updated.
        checkRefFieldText(doc, BOOKMARK_TEXT);
    }

    /// <summary>
    /// WORDSNET-14408 Deleting items of <see cref="BookmarkCollection"/> in the foreach
    /// cycle is supported now.
    /// </summary>
    @Test
    public void testJira14408() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create bookmarks
        for (int i = 0; i < 5; i++)
        {
            String bookmarkName = "Bookmark" + Integer.toString(i);
            builder.startBookmark(bookmarkName);
            builder.writeln(bookmarkName);
            builder.endBookmark(bookmarkName);
        }

        // Delete bookmarks
        for (Bookmark bookmark : doc.getRange().getBookmarks())
            bookmark.remove();

        msAssert.areEqual(0, doc.getRange().getBookmarks().getCount());
    }


    /// <summary>
    /// WORDSNET-15823 Deletion issue of bookmarks with duplicate name.
    /// Ends of bookmarks with duplicated names point to the same <see cref="BookmarkEnd"/>. It causes the
    /// exception while deleting. Ends of bookmarks selection algorithm is changed to fix the problem.
    /// </summary>
    @Test
    public void testJira15823() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        // Insert duplicated bookmarks.
        builder.startBookmark("test1");
        builder.endBookmark("test1");
        builder.startBookmark("test1");
        builder.endBookmark("test1");

        // Execute "BookmarkFinder" to check updated algorithm.
        BookmarkStart start1 = bookmarks.get(0).getBookmarkStart();
        BookmarkEnd end1 = bookmarks.get(0).getBookmarkEnd();
        BookmarkStart start2 = bookmarks.get(1).getBookmarkStart();
        BookmarkEnd end2 = bookmarks.get(1).getBookmarkEnd();

        // Check that we get different "end" nodes for duplicated bookmarks.
        msAssert.areEqual(end1.getName(), end2.getName());
        msAssert.areEqual(start1.getName(), start2.getName());
        msAssert.isFalse(msObject.referenceEquals(start1, start2));
        msAssert.isFalse(msObject.referenceEquals(end1, end2));

        // Try to remove and check that exception does not occur.
        start1.remove();
        end1.remove();
        start2.remove();
        end2.remove();

        msAssert.areEqual(0, bookmarks.getCount());
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Last occurred <see cref="BookmarkEnd"/> has to be returned when appropriate end of
    /// bookmark is not found.
    /// </summary>
    @Test
    public void testJira15823ManyStarts() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        builder.startBookmark("test1");
        builder.startBookmark("test1");
        builder.endBookmark("test1");
        builder.startBookmark("test1");
        builder.endBookmark("test1");

        msAssert.areEqual(3, bookmarks.getCount());
        BookmarkStart start1 = bookmarks.get(0).getBookmarkStart();
        BookmarkEnd end1 = bookmarks.get(0).getBookmarkEnd();

        BookmarkStart start2 = bookmarks.get(1).getBookmarkStart();
        BookmarkEnd end2 = bookmarks.get(1).getBookmarkEnd();

        BookmarkStart start3 = bookmarks.get(2).getBookmarkStart();
        BookmarkEnd end3 = bookmarks.get(2).getBookmarkEnd();

        msAssert.isNotNull(start1);
        msAssert.isNotNull(start2);
        msAssert.isNotNull(start3);
        msAssert.isNotNull(end1);
        msAssert.isNotNull(end2);
        msAssert.isNotNull(end3);
        msAssert.isFalse(msObject.referenceEquals(start1, start2));
        msAssert.isFalse(msObject.referenceEquals(start2, start3));
        msAssert.isFalse(msObject.referenceEquals(end1, end2));
        msAssert.isTrue(msObject.referenceEquals(end2, end3));
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Checks case when number of <see cref="BookmarkEnd"/> is greater than
    /// number of <see cref="BookmarkStart"/>.
    /// </summary>
    @Test
    public void testJira15823ManyEnds() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        builder.startBookmark("test1");
        builder.endBookmark("test1");
        BookmarkEnd wrongEnd = builder.endBookmark("test1");
        builder.startBookmark("test1");
        BookmarkEnd validEnd = builder.endBookmark("test1");

        msAssert.areEqual(2, bookmarks.getCount());
        BookmarkStart start1 = bookmarks.get(0).getBookmarkStart();
        BookmarkEnd end1 = bookmarks.get(0).getBookmarkEnd();

        BookmarkStart start2 = bookmarks.get(1).getBookmarkStart();
        BookmarkEnd end2 = bookmarks.get(1).getBookmarkEnd();

        msAssert.isFalse(msObject.referenceEquals(start1, start2));
        msAssert.isFalse(msObject.referenceEquals(end1, end2));
        msAssert.isFalse(msObject.referenceEquals(wrongEnd, end2));
        msAssert.isTrue(msObject.referenceEquals(validEnd, end2));
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Checks that AW does not take in attention <see cref="BookmarkStart"/>
    /// followed after referenced <see cref="BookmarkStart"/>.
    /// </summary>
    @Test
    public void testJira15823DifferentNames() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        builder.startBookmark("test1");
        builder.startBookmark("test2");
        BookmarkEnd validEnd1 = builder.endBookmark("test1");
        BookmarkEnd validEnd2 = builder.endBookmark("test2");
        builder.endBookmark("test2");

        msAssert.areEqual(2, bookmarks.getCount());

        BookmarkEnd end1 = bookmarks.get(0).getBookmarkEnd();
        BookmarkEnd end2 = bookmarks.get(1).getBookmarkEnd();

        msAssert.isTrue(msObject.referenceEquals(validEnd1, end1));
        msAssert.isTrue(msObject.referenceEquals(validEnd2, end2));
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Checks that search for <see cref="BookmarkStart"/> does not interrupt in unexpected place.
    /// </summary>
    @Test
    public void testJira15823StartSearch() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        BookmarkEnd end1 = builder.endBookmark("test1");
        builder.startBookmark("test1");

        BookmarkStart start1 = BookmarkFinder.findBookmarkStart(builder.getDocument(), "test1");
        msAssert.isNotNull(start1);
        msAssert.isFalse(msObject.referenceEquals(start1, end1));
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Checks bookmark starts for document without <see cref="BookmarkEnd"/>.
    /// </summary>
    @Test
    public void testJira15823NoEndsA() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        builder.startBookmark("test1");
        builder.startBookmark("test1");

        msAssert.areEqual(2, bookmarks.getCount());
    }

    /// <summary>
    /// This case concern with WORDSNET-15823 issue.
    /// Checks case with attempt to obtain the end of bookmark using <see cref="BookmarkFinder"/>
    /// directly for document where <see cref="BookmarkEnd"/> does not exist.
    /// </summary>
    @Test
    public void testJira15823NoEndsB() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());
        BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
        msAssert.areEqual(0, bookmarks.getCount());

        BookmarkStart start1 = builder.startBookmark("test1");
        BookmarkEnd end1 = BookmarkFinder.findBookmarkEnd(builder.getDocument(), "test1");
        msAssert.isNull(end1);

        end1 = BookmarkFinder.findBookmarkEnd(builder.getDocument(), "test1", start1);
        msAssert.isNull(end1);
    }


    /// <summary>
    /// WORDSNET-15347 Bookmark.Text throws System.InvalidOperationException.
    /// BookmarkEnd is outside of cloned table, so bookmark becomes invalid.
    /// Fixed per WORDSNET-721, which allows bookmarks at the table level.
    /// </summary>
    @Test
    public void testJira15347A() throws Exception
    {
        // There are two tables in the testing document. First of them has BookmarkEnd between last row and table end.
        // The second one has BookmarkEnd outside of the table (inside paragraph next to the table).
        Document doc = TestUtil.open("Model\\Bookmark\\TestJira15347.docx");

        // First table range should return valid bookmark for both: original and cloned instance.
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        msAssert.areEqual(1, table.getRange().getBookmarks().getCount());
        msAssert.isNotNull(table.getRange().getBookmarks().get(0).getText());

        Table clonedTable = (Table)table.deepClone(true);
        msAssert.areEqual(1, clonedTable.getRange().getBookmarks().getCount());
        msAssert.isNotNull(clonedTable.getRange().getBookmarks().get(0).getText());
    }

    /// <summary>
    /// Relates to WORDSNET-15347
    /// Checks that missed BookmarkEnd throws an exception.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QCannot find bookmark 'OI_TABLE_SUB1' in the document.\\E")
    public void testJira15347B() throws Exception
    {
        // The same document as in TestJira15347A, but we check here clone of the second table,
        // where there is no valid BookmarkEnd.
        Document doc = TestUtil.open("Model\\Bookmark\\TestJira15347.docx");

        Table clonedTable = (Table)doc.getFirstSection().getBody().getTables().get(1).deepClone(true);
        msAssert.isNull(clonedTable.getRange().getBookmarks().get(0).getText());
    }



    /// <summary>
    /// Relates to WORDSNET-16667 Checks that BookmarkCollection is updating in real-time.
    /// </summary>
    @Test
    public void testJira16667() throws Exception
    {
        final int BOOKMARKS_COUNT = 5;

        Document doc = BookmarkTestUtil.createDocumentWithBookmarks(BOOKMARKS_COUNT);

        BookmarkCollection bookmarks = doc.getRange().getBookmarks();
        msAssert.areEqual(BOOKMARKS_COUNT, bookmarks.getCount());

        BookmarkTestUtil.appendBookmark(doc, "anotherOneBookmark");
        msAssert.areEqual(BOOKMARKS_COUNT + 1, bookmarks.getCount());

        bookmarks.remove(bookmarks.get(0).getName());
        msAssert.areEqual(BOOKMARKS_COUNT, bookmarks.getCount());
    }


    /// <summary>
    /// WORDSNET-17030 Document.Clone method keeps the reference on original document.
    /// We need to clear cache of Range when cloning nodes.
    /// </summary>
    @Test
    public void testJira17030() throws Exception
    {
        final int BOOKMARKS_COUNT = 10;
        Document doc = BookmarkTestUtil.createDocumentWithBookmarks(BOOKMARKS_COUNT);

        msAssert.areEqual(BOOKMARKS_COUNT, doc.getRange().getBookmarks().getCount());

        Document clonedDoc = doc.deepClone();
        clonedDoc.getRange().getBookmarks().clear();
        msAssert.areEqual(0, clonedDoc.getRange().getBookmarks().getCount());
        msAssert.areEqual(BOOKMARKS_COUNT, doc.getRange().getBookmarks().getCount());

        BookmarkTestUtil.appendBookmark(doc, "anotherOneBookmarkInOriginal");
        msAssert.areEqual(BOOKMARKS_COUNT + 1, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual(0, clonedDoc.getRange().getBookmarks().getCount());

        BookmarkTestUtil.appendBookmark(clonedDoc, "BookmarkInCloned");
        msAssert.areEqual(BOOKMARKS_COUNT + 1, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual(1, clonedDoc.getRange().getBookmarks().getCount());
    }



    /// <summary>
    /// WORDSNET-12678 Implemented API to get information related to a table column bookmark.
    /// </summary>
    @Test
    public void testTableColumnBookmarkApi() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\TestTableColumnBookmark.docx");
        Bookmark bookmark = doc.getRange().getBookmarks().get("TableColumnBookmark");

        msAssert.isTrue(bookmark.isColumn());
        msAssert.areEqual(1, bookmark.getFirstColumn());
        msAssert.areEqual(3, bookmark.getLastColumn());
    }


    /// <summary>
    /// WORDSNET-17309 Because of moving the bookmark to the inline level its position became wrong:
    /// its start node with defined attribute displacedByCustomXml="next" was moved as child of the SDT and became
    /// referring to its end. The issue has been fixed on implementation of WORDSNET-721, in which bookmarks are
    /// allowed to be on the block/cell/row levels.
    /// </summary>
    @Test
    public void testJira17309() throws Exception
    {
        Document document = TestUtil.open("Model\\Bookmark\\TestJira17309.docx");
        Node sdt = document.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);

        Node previousNode = sdt.getPreviousSibling();
        msAssert.areEqual(NodeType.BOOKMARK_START, previousNode.getNodeType());
        msAssert.areEqual("test2", ((BookmarkStart)previousNode).getName());

        Node nextNode = sdt.getNextSibling();
        msAssert.areEqual(NodeType.BOOKMARK_END, nextNode.getNodeType());
        msAssert.areEqual("test2", ((BookmarkEnd)nextNode).getName());
    }

    /// <summary>
    /// Compare REF fields result with expected value.
    /// </summary>
    /// <param name="doc">Document with bookmark.</param>
    /// <param name="bookmarkText">Expected text in field result.</param>
    private static void checkRefFieldText(Document doc, String bookmarkText)
    {
        ParagraphCollection ps = doc.getFirstSection().getBody().getParagraphs();

        Run fieldRun = getFieldResultRun((Paragraph)ps.get(2));
        msAssert.areEqual(bookmarkText, fieldRun.getText());
        fieldRun = getFieldResultRun((Paragraph)ps.get(4));
        msAssert.areEqual(bookmarkText, fieldRun.getText());
    }

    /// <summary>
    /// WORDSNET-18882 Ability to insert column bookmarks has been implemented in the public API.
    /// </summary>
    @Test
    public void testColumnBookmarkInsertion() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();

        builder.insertCell();
        builder.startColumnBookmark("Bookmark0");
        builder.startColumnBookmark("Bookmark1");
        builder.write("Cell 1");
        builder.endColumnBookmark("Bookmark1");

        builder.insertCell();
        builder.write("Cell 2");
        builder.startColumnBookmark("Bookmark2");
        builder.endColumnBookmark("Bookmark2");

        builder.insertCell();
        builder.startColumnBookmark("Bookmark3");
        builder.endColumnBookmark("Bookmark3");
        builder.write("Cell 3");

        builder.endRow();

        builder.insertCell();
        builder.startColumnBookmark("Bookmark4");
        builder.write("Cell 4");
        builder.startColumnBookmark("Bookmark5");

        builder.insertCell();
        builder.endColumnBookmark("Bookmark5");
        builder.endColumnBookmark("Bookmark0");
        builder.write("Cell 5");

        builder.insertCell();
        builder.write("Cell 6");
        builder.endColumnBookmark("Bookmark4");

        builder.endRow();

        builder.insertCell();
        builder.write("Cell 7");

        builder.insertCell();
        builder.write("Cell 8");

        builder.insertCell();
        builder.write("Cell 9");

        builder.endTable();

        builder.moveToCell(0, 2, 0, 0);
        builder.startColumnBookmark("Bookmark6");
        builder.moveToCell(0, 2, 2, -1);
        builder.endColumnBookmark("Bookmark6");

        TestUtil.save(doc, "Model\\Bookmark\\TestColumnBookmarkInsertion.docx", null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-18882 Tests column bookmark behaviour on insertion to a table with merged cells.
    /// </summary>
    @Test
    public void testColumnBookmarkInMergedCells() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();

        for (int i = 1; i <= 2; i++)
        {
            for (int j = 1; j <= 4; j++)
            {
                builder.insertCell();
                builder.write(msString.format("Cell{0}{1}", i, j));
            }

            builder.endRow();
        }

        builder.endTable();

        Row row = table.getRows().get(1);
        row.getCells().get(0).getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        row.getCells().get(1).getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        row.getCells().get(2).getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        row.getCells().get(3).getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);

        // Test bookmark in cells with HorizontalMerge.
        builder.moveToCell(0, 0, 0, 0);
        builder.write("Bookmark1Start");
        builder.startColumnBookmark("Bookmark1");
        builder.moveToCell(0, 1, 2, -1);
        builder.write("Bookmark1End");
        builder.endColumnBookmark("Bookmark1");

        // Resave to get merged cells formatted as GridSpan.
        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\TestColumnBookmarkInMergedCells",
            UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        builder = new DocumentBuilder(doc);

        // Test bookmark in cells with GridSpan.
        builder.moveToCell(0, 0, 0, 0);
        builder.write("Bookmark2Start");
        builder.startColumnBookmark("Bookmark2");
        builder.moveToCell(0, 1, 1, -1);
        builder.write("Bookmark2End");
        builder.endColumnBookmark("Bookmark2");

        Bookmark bm1 = doc.getRange().getBookmarks().get("Bookmark1");
        Bookmark bm2 = doc.getRange().getBookmarks().get("Bookmark2");
        BookmarkStart bm1Start = bm1.getBookmarkStart();
        BookmarkStart bm2Start = bm2.getBookmarkStart();
        table = doc.getFirstSection().getBody().getTables().get(0);

        CompositeNode bm1StartParent = bm1Start.getParentNode();
        msAssert.areEqual(NodeType.PARAGRAPH, bm1StartParent.getNodeType());
        msAssert.areSame(table.getRows().get(0).getCells().get(0), bm1StartParent.getParentNode());
        msAssert.areSame(bm1StartParent, bm2Start.getParentNode());

        msAssert.areEqual(0, bm1Start.getFirstColumn());
        msAssert.areEqual(1, bm1Start.getLastColumn());
        msAssert.areEqual(0, bm2Start.getFirstColumn());
        msAssert.areEqual(1, bm2Start.getLastColumn());

        msAssert.areSame(table, bm1.getBookmarkEnd().getParentNode());
        msAssert.areSame(table, bm2.getBookmarkEnd().getParentNode());
        msAssert.isNull(bm1.getBookmarkEnd().getNextNonAnnotationSibling());
        msAssert.isNull(bm2.getBookmarkEnd().getNextNonAnnotationSibling());
    }

    /// <summary>
    /// WORDSNET-18882 Tests that an exception is generated when a column bookmark is being started outside
    /// a table.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testColumnBookmarkStartOutsideTable() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.startColumnBookmark("Bookmark1");
    }

    /// <summary>
    /// WORDSNET-18882 Tests that an exception is generated when a column bookmark is being ended outside
    /// a table.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testColumnBookmarkEndOutsideTable() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();
        builder.insertCell();
        builder.startColumnBookmark("Bookmark1");
        builder.endTable();

        builder.endColumnBookmark("Bookmark1");
    }

    /// <summary>
    /// WORDSNET-18882 Tests that an exception is generated when a column bookmark is being ended in
    /// a different table than the bookmark start is.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testColumnBookmarkEndInDifferentTable() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();
        builder.insertCell();
        builder.startColumnBookmark("Bookmark1");
        builder.endTable();

        builder.startTable();
        builder.insertCell();
        builder.endColumnBookmark("Bookmark1");
        builder.endTable();
    }

    /// <summary>
    /// WORDSNET-18882 Tests that an exception is generated when a column bookmark is being ended at
    /// a column with an index less than a column index of the bookmark start.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testWrongBookmarkEndColumnIndex() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();

        builder.insertCell();
        builder.insertCell();
        builder.startColumnBookmark("Bookmark1");
        builder.endRow();

        builder.insertCell();
        builder.endColumnBookmark("Bookmark1");
        builder.insertCell();
        builder.endRow();

        builder.endTable();
    }

    /// <summary>
    /// WORDSNET-23756 InvalidCastException is thrown when set text of bookmark, which is inside OfficeMath.
    /// Improved Bookmark.Text handling.
    /// </summary>
    @Test
    public void test23756() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test23756.docx");

        msAssert.areEqual("A=πr2\f", doc.getText());
        doc.getRange().getBookmarks().get("test").setText("test");
        msAssert.areEqual("A=testπr2\f", doc.getText());
    }


    /// <summary>
    /// WORDSNET-24087 Bookmark itself is removed after setting it's text to empty string.
    /// The row-level bookmark parent row should be taken as next sibling row in case when the bookmark
    /// is placed between rows, just inside a table node. This makes a sense in the case of nested tables.
    /// </summary>
    @Test
    public void test24087() throws Exception
    {
        Document doc = TestUtil.open("Model\\Bookmark\\Test24087.docx");

        // The problematic bookmark.
        doc.getRange().getBookmarks().get("Cooling_Capacity_ck").setText("");

        // Check bookmark itself is not removed.
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_Capacity_ck"));

        // These bookmarks intersect with problematic bookmark but should not be removed as well.
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_DehumCapacity_ck"));
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_CapacitySens_ck"));

        // Check also all bookmarks are in place after round-trip.
        doc = TestUtil.saveOpen(doc, "Model\\Bookmark\\Test24087", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_Capacity_ck"));
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_DehumCapacity_ck"));
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Cooling_CapacitySens_ck"));
    }



    /// <summary>
    /// WORDSNET-24897 Do not rename bookmarks when import building blocks.
    /// MS Word allows duplicate bookmark names in different building blocks. The same has been implemented in
    /// Aspose.Words when importing nodes.
    /// </summary>
    @Test
    public void test24897() throws Exception
    {
        final String FILE_NAME = "Model\\Bookmark\\Test24897.dotx";
        Document doc = TestUtil.open(FILE_NAME);
        Document sourceDocument = doc.deepClone();

        CompositeNode sourceBuildingBlock = sourceDocument.getGlossaryDocument().getBuildingBlocks().get(0);
        GlossaryDocument destinationGlossary = doc.getGlossaryDocument();

        BookmarkCollection bookmarks = destinationGlossary.getRange().getBookmarks();
        msAssert.areEqual(1, bookmarks.getCount());
        String originalBookmarkName = bookmarks.get(0).getName();

        // Import sourceBuildingBlock several times.
        final int IMPORT_COUNT = 5;
        for (int i = 0; i < IMPORT_COUNT; i++)
        {
            Node importedNode = destinationGlossary.importNode(sourceBuildingBlock, true);
            destinationGlossary.getBuildingBlocks().add(importedNode);
        }

        // There are the original bookmark and the imported ones. The all bookmark must have the same name because
        // they are in different building blocks.
        checkBookmarkCountAndNames(bookmarks, IMPORT_COUNT + 1, 0, -1, originalBookmarkName);

        // Import building block contents without the building block itself. The bookmark is renamed at this case.
        CompositeNode destinationBuildingBlock = (CompositeNode)destinationGlossary.getBuildingBlocks().get(0).deepClone(false);
        destinationGlossary.getBuildingBlocks().add(destinationBuildingBlock);
        for (Node node = sourceBuildingBlock.getFirstChild(); node != null; node = node.getNextSibling())
        {
            Node importedNode = destinationGlossary.importNode(node, true);
            destinationBuildingBlock.appendChild(importedNode);
        }

        int lastBookmarkIndex = bookmarks.getCount() - 1;
        checkBookmarkCountAndNames(bookmarks, IMPORT_COUNT + 2, 0, lastBookmarkIndex - 1, originalBookmarkName);
        msAssert.areNotEqual(originalBookmarkName, bookmarks.get(lastBookmarkIndex).getName());
        msAssert.isTrue(bookmarks.get(lastBookmarkIndex).getName().startsWith(originalBookmarkName));

        // Test saving.
        doc = TestUtil.saveOpen(doc, FILE_NAME, null, false);
        bookmarks = doc.getGlossaryDocument().getRange().getBookmarks();

        checkBookmarkCountAndNames(bookmarks, IMPORT_COUNT + 2, 0, lastBookmarkIndex - 1, originalBookmarkName);
        msAssert.areNotEqual(originalBookmarkName, bookmarks.get(lastBookmarkIndex).getName());
        msAssert.isTrue(bookmarks.get(lastBookmarkIndex).getName().startsWith(originalBookmarkName));
    }

    /// <summary>
    /// WORDSNET-25490 MS Word keeps the very last bookmark with a non-unique name.
    /// </summary>
    @Test
    public void test25490() throws Exception
    {
        Document document = TestUtil.open("Model\\Bookmark\\Test25490.docx");

        TestUtil.executeValidator(document, SaveFormat.DOCX);

        BookmarkCollection bookmarks = document.getRange().getBookmarks();

        msAssert.areEqual(5, bookmarks.getCount());
        assertBookmark(bookmarks.get(0), "Book0", "Item 3");
        assertBookmark(bookmarks.get(1), "Book1A", "Item 4");
        assertBookmark(bookmarks.get(2), "Book1B", "Item 5");
        assertBookmark(bookmarks.get(3), "Book1C", "Item 6");
        assertBookmark(bookmarks.get(4), "BookD", "Item 9");
    }

    /// <summary>
    /// Additional case for WORDSNET-25491
    /// More detailed case.
    /// </summary>
    @Test
    public void test25491A() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.startBookmark("testbookmark");
        FormField formField = builder.insertCheckBox("", true, 12);

        msAssert.isNotNull(formField);
    }

    /// <summary>
    /// Tests the <see cref="BookmarkFinder.FindBookmarkStart(Node,string,BookmarkEnd)"/> method.
    /// </summary>
    @Test
    public void testFindBookmarkStart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        final String NAME = "test1";
        BookmarkStart start1 = builder.startBookmark(NAME);
        BookmarkStart start2 = builder.startBookmark(NAME);
        BookmarkEnd end2 = builder.endBookmark(NAME);
        BookmarkStart start3 = builder.startBookmark(NAME);
        BookmarkEnd end3 = builder.endBookmark(NAME);
        BookmarkEnd end1 = builder.endBookmark(NAME);

        msAssert.isTrue(msObject.referenceEquals(start1, BookmarkFinder.findBookmarkStart(doc, NAME, end1)));
        msAssert.isFalse(msObject.referenceEquals(start2, BookmarkFinder.findBookmarkStart(doc, NAME, end1)));
        msAssert.isFalse(msObject.referenceEquals(start3, BookmarkFinder.findBookmarkStart(doc, NAME, end1)));
        msAssert.isTrue(msObject.referenceEquals(start2, BookmarkFinder.findBookmarkStart(doc, NAME, end2)));
        msAssert.isTrue(msObject.referenceEquals(start3, BookmarkFinder.findBookmarkStart(doc, NAME, end3)));
    }

    /// <summary>
    /// WORDSNET-19052 When bookmarks are in nearby table cells, bookmark contents are not inserted correctly
    /// Support for column bookmarks has been added to the
    /// <see cref="DocumentBuilder.MoveToBookmark(string,bool,bool)"/> method.
    /// </summary>
    @Test
    public void testMoveToColumnBookmark() throws Exception
    {
        final String FILE_NAME = "Model\\Bookmark\\TestMoveToColumnBookmark.docx";
        Document doc = TestUtil.open(FILE_NAME);
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Emulate cases where a bookmark end is moved to the next paragraph after its original position, as the old
        // versions of AW did.
        BookmarkEnd bookmarkEnd = doc.getRange().getBookmarks().get("BM12-1-2-1row").getBookmarkEnd();
        Node nextNode = bookmarkEnd.getNextNonAnnotationSibling();
        // Ensure that the bookmark end is at the row level, and a row exists after it.
        Debug.assertTrue(nextNode instanceof Row);
        ((CompositeNode)((CompositeNode)nextNode).getChild(NodeType.PARAGRAPH, 0, true)).prependChild(bookmarkEnd);
        bookmarkEnd = doc.getRange().getBookmarks().get("BM11-0-0-2rows").getBookmarkEnd();
        CompositeNode parent = bookmarkEnd.getParentNode();
        // Ensure that the bookmark end is after the last row of the table.
        Debug.assertTrue((parent.getNodeType() == NodeType.TABLE) && (bookmarkEnd.getNextNonAnnotationSibling() == null));
        ((CompositeNode)parent.getNextNonAnnotationSibling()).prependChild(bookmarkEnd);

        for (Bookmark bookmark : doc.getRange().getBookmarks())
        {
            String name = bookmark.getName();
            final String TEXT_TEMPLATE = "<{0} {1}>";

            builder.moveToBookmark(name, true, true);
            builder.write(msString.format(TEXT_TEMPLATE, name, "AfterStart"));

            builder.moveToBookmark(name, false, false);
            builder.write(msString.format(TEXT_TEMPLATE, name, "BeforeEnd"));

            builder.moveToBookmark(name, false, true);
            builder.write(msString.format(TEXT_TEMPLATE, name, "AfterEnd"));

            builder.moveToBookmark(name, true, false);
            builder.write(msString.format(TEXT_TEMPLATE, name, "BeforeStart"));
        }

        TestUtil.saveCheckGoldExportOnly(doc, FILE_NAME);
    }

    private static void assertBookmark(Bookmark bookmark, String name, String text) throws Exception
    {
        msAssert.areEqual(name, bookmark.getName());
        msAssert.areEqual(text, bookmark.getText());
    }

    /// <summary>
    /// Checks the number of bookmarks in the specified collection. And checks that all the bookmarks in the index
    /// range have the specified name.
    /// </summary>
    private static void checkBookmarkCountAndNames(BookmarkCollection bookmarks, int expectedBookmarkCount,
        int startBookmarkIndex, int endBookmarkIndex, String expectedBookmarkName)
    {
        msAssert.areEqual(expectedBookmarkCount, bookmarks.getCount());

        if (endBookmarkIndex < 0)
            endBookmarkIndex = expectedBookmarkCount - 1;

        for (int i = startBookmarkIndex; i <= endBookmarkIndex; i++)
            msAssert.areEqual(expectedBookmarkName, bookmarks.get(i).getName());
    }

    /// <summary>
    /// Get run containing field result.
    /// </summary>
    /// <param name="p">Paragraph containing field data.</param>
    /// <returns>Run with field result.</returns>
    private static Run getFieldResultRun(Paragraph p)
    {
        // Get first run following separator.
        Node separator = p.getChild(NodeType.FIELD_SEPARATOR, 0, false);
        return (Run)p.getChildNodes(NodeType.ANY, false).get((p.indexOf(separator) + 1));
    }
}

