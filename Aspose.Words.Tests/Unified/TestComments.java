// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.RandomUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.Threading.CurrentThread;
import java.util.TimeZone;
import com.aspose.ms.System.TimeSpan;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for how model handles comments (annotations) in a document.
/// </summary>
@Test
public class TestComments extends UnifiedTestsBase
{

    /// <summary>
    /// Creates valid comments and comment ranges via the public API and saves to various formats.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCreateComment(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("A bit of text");

        // Create a comment that has no range.
        Comment comment = new Comment(doc, "author name 1", "an1", new DateTime(2008, 7, 27, 17, 1, 0));
        comment.setText("This is my comment 1.");
        msAssert.areEqual(0, comment.getId());
        builder.insertNode(comment);
        builder.write(" and a bit more text. ");

        // Create a comment that has a range.
        comment = new Comment(doc, "author name 2", "an2", new DateTime(2010, 7, 27, 17, 1, 0));
        comment.setText("This is my comment 2.");
        msAssert.areEqual(1, comment.getId());
        // This is what MS Word usually writes - the comment reference follows the comment range end.
        builder.insertNode(new CommentRangeStart(doc, comment.getId()));
        builder.write("This is a commented range.");
        builder.insertNode(new CommentRangeEnd(doc, comment.getId()));
        builder.insertNode(comment);
        builder.writeln();

        // Create one more comment that has a range. Typically, the comment is just after the
        // comment range end node, but actially it can be anywhere. In this case inside the comment range.
        comment = new Comment(doc);
        comment.setText("This is my comment 3.");
        builder.insertNode(new CommentRangeStart(doc, comment.getId()));
        builder.write("Before comment.");
        builder.insertNode(comment);
        builder.write(" After comment.");
        builder.insertNode(new CommentRangeEnd(doc, comment.getId()));

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCreateComment", lf, sf);

        // Check the comment without a range.
        comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        msAssert.areEqual(0, comment.getId());
        msAssert.areEqual("an1", comment.getInitial());
        msAssert.areEqual("author name 1", comment.getAuthor());
        msAssert.areEqual("\u0005This is my comment 1.\r", comment.getText());

        // Check the comment with a range.
        comment = (Comment)doc.getChild(NodeType.COMMENT, 1, true);
        msAssert.areEqual(1, comment.getId());
        CommentRangeEnd cre = (CommentRangeEnd)comment.getPreviousSibling();
        msAssert.areEqual(comment.getId(), cre.getId());
        Run run = (Run)cre.getPreviousSibling();
        msAssert.areEqual("This is a commented range.", run.getText());
        CommentRangeStart crs = (CommentRangeStart)run.getPreviousSibling();
        msAssert.areEqual(comment.getId(), crs.getId());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCreateCommentSourceDataProvider")
	public void unifiedTestCreateCommentSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCreateComment(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCreateCommentSourceDataProvider")
	Object[][] unifiedTestCreateCommentSourceDataProvider() throws Exception
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
    /// WORDSNET-4346 “System.NullReferenceException” exception occurs during saving document after inserting comment.
    /// RK The problem was caused by null author and initials. Changed defaults to empty strings.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect4346(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("test");

        Comment comment = new Comment(doc);
        builder.getCurrentParagraph().appendChild(comment);
        comment.getParagraphs().add(new Paragraph(doc));
        comment.getFirstParagraph().getRuns().add(new Run(doc, "Comment text."));

        msAssert.areEqual("", comment.getInitial());
        msAssert.areEqual("", comment.getAuthor());

        TestUtil.saveOpen(doc, "Model\\Comment\\TestDefect4346", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect4346SourceDataProvider")
	public void unifiedTestDefect4346Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect4346(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect4346SourceDataProvider")
	Object[][] unifiedTestDefect4346SourceDataProvider() throws Exception
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
    /// Check comment tree read/write.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCommentTree(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Comment\\TestCommentTree", TestUtil.buildScenario(lf, sf, true));

        // Extended comment data is not supported in WordML.
        boolean readParents = (lf != LoadFormat.WORD_ML) && (sf != SaveFormat.WORD_ML);

        // Done marks is only supported in DOCX format.
        boolean readDone = (lf == LoadFormat.DOCX);

        verifyCommentTree(doc, readParents, readDone);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCommentTreeSourceDataProvider")
	public void unifiedTestCommentTreeSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCommentTree(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCommentTreeSourceDataProvider")
	Object[][] unifiedTestCommentTreeSourceDataProvider() throws Exception
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
    /// Our validator removes comments and comment ranges from all stories except main text.
    /// </summary>
    @Test
    public void testCommentInWrongStories() throws Exception
    {
        Document doc = new Document();
        doc.removeAllChildren();

        Section sect = doc.appendChild(new Section(doc));
        Body body = sect.appendChild(new Body(doc));

        // Create a comment in a comment.
        Paragraph para = body.appendChild(new Paragraph(doc));
        Comment comment = para.appendChild(new Comment(doc));
        comment.setText("Comment in main text.");
        para = comment.appendChild(new Paragraph(doc));
        comment = para.appendChild(new Comment(doc));
        comment.setText("Comment in a comment.");

        // Create a comment in a footnote.
        para = body.appendChild(new Paragraph(doc));
        Footnote footnote = para.appendChild(new Footnote(doc, FootnoteType.FOOTNOTE));
        para = footnote.appendChild(new Paragraph(doc));
        para.appendChild(new Run(doc, "Text in a footnote."));
        comment = para.appendChild(new Comment(doc));
        comment.setText("Comment in a footnote.");

        // Create a comment in a shape.
        para = body.appendChild(new Paragraph(doc));
        Shape shape = para.appendChild(new Shape(doc, ShapeType.RECTANGLE));
        shape.setWidth(100.0);
        shape.setHeight(100.0);
        para = shape.appendChild(new Paragraph(doc));
        para.appendChild(new Run(doc, "Text in a shape."));
        comment = para.appendChild(new Comment(doc));
        comment.setText("Comment in a shape.");

        // Create a comment with a range in a header.
        HeaderFooter header = sect.appendChild(new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY));
        para = header.appendChild(new Paragraph(doc));
        para.appendChild(new CommentRangeStart(doc, 123));
        para.appendChild(new Run(doc, "Text in a header."));
        para.appendChild(new CommentRangeEnd(doc, 123));
        para.appendChild(createComment(doc, 123, "Comment in a header."));
        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCommentInWrongStories.docx");
        checkCommentCounts(doc, 1, 0, 0);
    }

    /// <summary>
    /// Our validator removes comment ranges where end comes before start.
    /// </summary>
    @Test
    public void testCommentRangeEndBeforeStart() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.appendChild(new CommentRangeEnd(doc, 123));    // End before start.
        para.appendChild(new Run(doc, "Text in a comment."));
        para.appendChild(new CommentRangeStart(doc, 123));
        para.appendChild(createComment(doc, 123, "Comment 1."));

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCommentRangeEndBeforeStart.docx");
        checkCommentCounts(doc, 1, 0, 0);
    }

    /// <summary>
    /// Our validator removes empty comment ranges because MS Word sometimes fails to display comments with such ranges.
    /// </summary>
    @Test
    public void testCommentRangeEmpty() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.appendChild(new CommentRangeStart(doc, 123));  // Start of the empty range.
        para.appendChild(new CommentRangeStart(doc, 999));  // This is correctly skipped by our validator.
        para.appendChild(new CommentRangeEnd(doc, 123));    // End of the empty range.
        para.appendChild(createComment(doc, 123, "Comment 1."));
        para.appendChild(new Run(doc, "Blah."));

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCommentRangeEmpty.docx");
        checkCommentCounts(doc, 1, 0, 0);
    }

    /// <summary>
    /// Our validator removes comment ranges that have no end/start or no associated cooment.
    /// </summary>
    @Test
    public void testCommentRangeOrphans() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();

        // Comment range without a comment.
        para.appendChild(new CommentRangeStart(doc, 123));
        para.appendChild(new Run(doc, "Blah 1."));
        para.appendChild(new CommentRangeEnd(doc, 123));

        // Comment range start only.
        para.appendChild(new CommentRangeStart(doc, 124));
        para.appendChild(createComment(doc, 124, "Comment 1."));
        para.appendChild(new Run(doc, "Blah 2."));

        // Comment range end only.
        para.appendChild(new CommentRangeEnd(doc, 125));
        para.appendChild(createComment(doc, 125, "Comment 2."));
        para.appendChild(new Run(doc, "Blah 3."));

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCommentRangeOrphans.docx");
        checkCommentCounts(doc, 2, 0, 0);
    }

    /// <summary>
    /// Our validator removes comment ranges if duplicate ids are found,
    /// but preserves comments with duplicate ids by assigning them unique ids.
    /// </summary>
    @Test
    public void testCommentDuplicate() throws Exception
    {
        Document doc = new Document();
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        para.appendChild(new CommentRangeStart(doc, 123));
        para.appendChild(new Run(doc, "Blah 1."));
        para.appendChild(new CommentRangeEnd(doc, 123));
        para.appendChild(createComment(doc, 123, "Comment 1."));

        // Create a duplicate comment.
        para.appendChild(new CommentRangeStart(doc, 123));
        para.appendChild(new Run(doc, "Blah 2."));
        para.appendChild(new CommentRangeEnd(doc, 123));
        para.appendChild(createComment(doc, 123, "Comment 2."));

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestCommentDuplicate.docx");
        checkCommentCounts(doc, 2, 1, 1);
    }

    private static Comment createComment(Document doc, int id, String text)
    {
        Comment comment = new Comment(doc);
        ((INodeWithAnnotationId)comment).setIdInternal(id);
        comment.setText(text);
        return comment;
    }

    private static void checkCommentCounts(Document doc, int commentCount, int commentRangeStartCount, int commentRangeEndCount)
    {
        msAssert.areEqual(commentCount, doc.getChildNodes(NodeType.COMMENT, true).getCount());
        msAssert.areEqual(commentRangeStartCount, doc.getChildNodes(NodeType.COMMENT_RANGE_START, true).getCount());
        msAssert.areEqual(commentRangeEndCount, doc.getChildNodes(NodeType.COMMENT_RANGE_END, true).getCount());
    }

    /// <summary>
    /// Cloning of comments does not automatically generate unique ids.
    /// So if you clone and append a section to the same document and then save, the comments will be "fixed" by the validator,
    /// but the comment ranges in the cloned section will be lost. This is not very nice, but okay for now.
    /// </summary>
    @Test
    public void testCommentClone() throws Exception
    {
        Document doc = new Document();
        Comment comment = new Comment(doc);
        msAssert.areEqual(0, comment.getId());
        comment = (Comment)comment.deepClone(true);
        msAssert.areEqual(0, comment.getId());

        CommentRangeStart crs = new CommentRangeStart(doc, 123);
        msAssert.areEqual(123, crs.getId());
        crs = (CommentRangeStart)crs.deepClone(true);
        msAssert.areEqual(123, crs.getId());
    }

    /// <summary>
    /// Check the comment ids are updated when comments are imported into another document.
    /// </summary>
    @Test
    public void testCommentImport() throws Exception
    {
        Document srcDoc = new Document();
        Paragraph para = srcDoc.getFirstSection().getBody().getFirstParagraph();
        para.appendChild(createComment(srcDoc, 111, "Comment 1."));
        para.appendChild(new CommentRangeStart(srcDoc, 123));
        para.appendChild(new Run(srcDoc, "Blah."));
        para.appendChild(new CommentRangeEnd(srcDoc, 123));
        para.appendChild(createComment(srcDoc, 123, "Comment 2."));

        Document dstDoc = new Document();
        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        Comment comment = (Comment)dstDoc.getChild(NodeType.COMMENT, 0, true);
        msAssert.areEqual(0, comment.getId());

        comment = (Comment)dstDoc.getChild(NodeType.COMMENT, 1, true);
        msAssert.areEqual(1, comment.getId());
        CommentRangeStart start = (CommentRangeStart)dstDoc.getChild(NodeType.COMMENT_RANGE_START, 0, true);
        msAssert.areEqual(1, start.getId());
        CommentRangeEnd end = (CommentRangeEnd)dstDoc.getChild(NodeType.COMMENT_RANGE_END, 0, true);
        msAssert.areEqual(1, end.getId());
    }

    /// <summary>
    /// Our document postloader regenerates ids for all loaded comments so we can be sure
    /// we can generate unique ids for the new comments that the user will create.
    /// </summary>
    @Test
    public void testCommentAdd() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\TestCommentAdd.docx");
        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        // In DOC this is a different number, but our postloader regenerates the numbers.
        msAssert.areEqual(0, comment.getId());

        // So the next comment the user creates gets a unique id.
        comment = new Comment(doc);
        msAssert.areEqual(1, comment.getId());
    }

    @Test
    public void testCommentDate() throws Exception
    {

        Document doc = TestUtil.open("Model\\Comment\\TestCommentDateTimeJ522.docx");
        NodeCollection nodes = doc.getChildNodes(NodeType.COMMENT, true);
        Comment comment1 = (Comment)nodes.get(0);
        Comment comment2 = (Comment)nodes.get(1);

        msAssert.areEqual(new DateTime(2012, 3, 23, 18, 20, 0), comment1.getDateTimeInternal());
        msAssert.areEqual(new DateTime(2012, 9, 27, 10, 57, 0), comment2.getDateTimeInternal());
    }

    /// <summary>
    /// WORDSNET-14471 Detect comment resolved and comment replies and reply to which comment
    /// Added public API to read-only access to comment hierarchy.
    /// </summary>
    @Test
    public void testJira14471() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\TestJira14471.docx");
        NodeCollection comments = doc.getChildNodes(NodeType.COMMENT, true);

        Comment parentComment = (Comment)comments.get(0);
        msAssert.isNull(parentComment.getAncestor());
        msAssert.areEqual(2, parentComment.getReplies().getCount());
        msAssert.isFalse(parentComment.getDone());

        Comment undoneComment = (Comment)comments.get(1);
        msAssert.isNotNull(undoneComment.getAncestor());
        msAssert.areEqual(0, undoneComment.getReplies().getCount());
        msAssert.isFalse(undoneComment.getDone());
        undoneComment.setDone(true);
        msAssert.isTrue(undoneComment.getDone());

        Comment doneComment = (Comment)comments.get(2);
        msAssert.isNotNull(doneComment.getAncestor());
        msAssert.areEqual(0, doneComment.getReplies().getCount());
        msAssert.isTrue(doneComment.getDone());

        // Test that collection are live.
        doneComment.setParentId(Comment.NO_PARENT);
        msAssert.isNull(doneComment.getAncestor());
        msAssert.areEqual(1, parentComment.getReplies().getCount());

        ((INodeWithAnnotationId)parentComment).setIdInternal(1000);
        msAssert.areEqual(0, parentComment.getReplies().getCount());
    }


    /// <summary>
    /// WORDSNET-9959 Tests creation and removal of comment replies.
    /// Added public API to operate comment replies.
    /// </summary>
    @Test
    public void testJira9959() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\TestJira14471.docx");
        Comment mainComment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
        msAssert.areEqual(2, mainComment.getReplies().getCount());

        DateTime replyDate = new DateTime(2017, 9, 19, 13, 0, 0);
        mainComment.addReplyInternal("John Doe", "JD", replyDate, "New reply");
        msAssert.areEqual(3, mainComment.getReplies().getCount());

        Comment reply = (Comment)doc.getChild(NodeType.COMMENT, -1, true);
        msAssert.areEqual("John Doe", reply.getAuthor());
        msAssert.areEqual("JD", reply.getInitial());
        msAssert.areEqual(replyDate, reply.getDateTimeInternal());
        msAssert.areEqual(ControlChar.ANNOTATION_REF + "New reply", msString.trimEnd(reply.getText()));
        msAssert.areEqual(3, reply.getId());
        msAssert.areSame(reply, mainComment.getReplies().get(2));

        CommentRangeStart replyStart = (CommentRangeStart)doc.getChild(NodeType.COMMENT_RANGE_START, -1, true);
        msAssert.areEqual(3, replyStart.getId());
        Node previousNode = replyStart.getPreviousSibling();
        msAssert.areEqual(NodeType.COMMENT_RANGE_START, previousNode.getNodeType());
        msAssert.areEqual(2, ((CommentRangeStart)previousNode).getId());

        CommentRangeEnd replyEnd = (CommentRangeEnd)doc.getChild(NodeType.COMMENT_RANGE_END, -1, true);
        msAssert.areEqual(3, replyEnd.getId());
        previousNode = replyEnd.getPreviousSibling();
        msAssert.areEqual(NodeType.COMMENT, previousNode.getNodeType());
        msAssert.areEqual(2, ((Comment)previousNode).getId());
        msAssert.areSame(mainComment.getReplies().get(1), previousNode);
        msAssert.areSame(reply.getPreviousSibling(), replyEnd);

        mainComment.removeReply(mainComment.getReplies().get(1));
        msAssert.areEqual(2, mainComment.getReplies().getCount());

        for (Comment comment : (Iterable<Comment>) doc.getChildNodes(NodeType.COMMENT, true))
            msAssert.areNotEqual(2, comment.getId());
        for (CommentRangeStart commentStart : (Iterable<CommentRangeStart>) doc.getChildNodes(NodeType.COMMENT_RANGE_START, true))
            msAssert.areNotEqual(2, commentStart.getId());
        for (CommentRangeEnd commentEnd : (Iterable<CommentRangeEnd>) doc.getChildNodes(NodeType.COMMENT_RANGE_END, true))
            msAssert.areNotEqual(2, commentEnd.getId());

        mainComment.removeAllReplies();
        msAssert.areEqual(0, mainComment.getReplies().getCount());

        NodeCollection collection = doc.getChildNodes(NodeType.COMMENT, true);
        msAssert.areEqual(1, collection.getCount());
        msAssert.areSame(mainComment, collection.get(0));

        collection = doc.getChildNodes(NodeType.COMMENT_RANGE_START, true);
        msAssert.areEqual(1, collection.getCount());
        msAssert.areEqual(0, ((CommentRangeStart)collection.get(0)).getId());

        collection = doc.getChildNodes(NodeType.COMMENT_RANGE_END, true);
        msAssert.areEqual(1, collection.getCount());
        msAssert.areEqual(0, ((CommentRangeEnd)collection.get(0)).getId());
    }

    /// <summary>
    /// WORDSNET-9959 Tests that adding a reply to a reply is not allowed like in MS Word.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testAddingReplyToReply() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\TestJira14471.docx");
        Comment reply = (Comment)doc.getChild(NodeType.COMMENT, 1, true);
        reply.addReplyInternal("John Doe", "JD", new DateTime(2017, 9, 23, 12, 30, 0), "New reply");
    }

    /// <summary>
    /// Tests block-level comment range start/end nodes.
    /// </summary>
    @Test
    public void testCommentRanges() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\TestBlockLevelCommentRanges.docx");

        ArrayList<CommentRangeStart> starts =
            doc.getChildNodes(NodeType.COMMENT_RANGE_START, true).<CommentRangeStart>toList();
        ArrayList<CommentRangeEnd> ends =
            doc.getChildNodes(NodeType.COMMENT_RANGE_END, true).<CommentRangeEnd>toList();

        msAssert.areEqual(3, starts.size());
        msAssert.areEqual(3, ends.size());

        msAssert.areEqual(NodeType.BODY, starts.get(0).getParentNode().getNodeType());
        msAssert.areEqual(DisplacedByType.NEXT, starts.get(0).getDisplacedBy());

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, starts.get(1).getParentNode().getNodeType());
        msAssert.areEqual(DisplacedByType.PREV, starts.get(1).getDisplacedBy());

        msAssert.areEqual(NodeType.BODY, ends.get(2).getParentNode().getNodeType());
        msAssert.areEqual(DisplacedByType.PREV, ends.get(2).getDisplacedBy());
    }

    /// <summary>
    /// WORDSNET-16092 Sometimes Comment.AddReply added a separate comment, not a reply.
    /// Wrong paragraph identifier might be generated for a comment (negative number).
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void testJira16092() throws Exception
    {
        Document doc = new Document();

        // Create a main comment.
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        Comment mainComment = createComment(doc, 1, "Comment 1.");
        para.appendChild(new CommentRangeStart(doc, 1));
        para.appendChild(new Run(doc, "Blah."));
        para.appendChild(new CommentRangeEnd(doc, 1));
        para.appendChild(mainComment);

        // Generate replies.
        for (int i = 0; i < 20; i++)
        {
            mainComment.addReplyInternal("John Doe", "JD", new DateTime(2017, 12, 14, 18, 48, 0),
                msString.format("Reply {0}", i + 1));
        }

        // Need to reset RandomUtil test mode to generate real paragraph IDs.
        RandomUtil.resetTestMode();
        try
        {
            // Save/load the document to paragraph IDs be generated.
            doc = TestUtil.saveOpen(doc, "Model\\Comment\\TestJira16092", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        }
        finally
        {
            RandomUtil.setTestMode();
        }

        // Check paragraph IDs.
        for (Comment comment : (Iterable<Comment>) doc.getChildNodes(NodeType.COMMENT, true))
            msAssert.isTrue(comment.getLastParagraph().getParaId() >= 0);
    }

    /// <summary>
    /// WORDSNET-18695 Implementing MS Word behaviour that if a CommetRef node is located above than its
    /// CommentRange in the XML document structure, the CommentRange is moved above the CommetRef and becomes empty.
    /// Now the difference with MS Word is that Aspose.Words removes empty comment ranges like old versions of Word.
    /// </summary>
    @Test
    public void test18695() throws Exception
    {
        TestWarningCallback warnings = new TestWarningCallback();
        LoadOptions options = new LoadOptions();
        options.setWarningCallback(warnings);

        Document doc = TestUtil.open("Model\\Comment\\Test18695.docx", options);

        // The comment range has been removed on annotation validation after loading the document.
        msAssert.isNull(doc.getFirstSection().getBody().getChild(NodeType.COMMENT_RANGE_END, 0, true));
        msAssert.isNotNull(doc.getFirstSection().getBody().getChild(NodeType.COMMENT, 0, true));
        msAssert.isTrue(warnings.contains(WarningSource.UNKNOWN, WarningType.UNEXPECTED_CONTENT,
                WarningStrings.COMMENT_ABOVE_COMMENT_RANGE));
    }



    /// <summary>
    /// WORDSNET-21058 Implemented support of commentsIds and commentsExtensible document parts.
    /// Adding a comment to a new document with setting the new properties is tested.
    /// </summary>
    @Test
    public void test21058NewCommentsInEmptyDocument() throws Exception
    {
        Document doc = new Document();

        // Create a comment.
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        Comment comment = createComment(doc, 999, "Comment 1.");
        para.appendChild(new CommentRangeStart(doc, 999));
        para.appendChild(new Run(doc, "Blah."));
        para.appendChild(new CommentRangeEnd(doc, 999));
        para.appendChild(comment);

        comment.setDateTimeInternal(new DateTime(2020, 9, 21, 23, 0, 0));
        comment.setDateTimeUtc(new DateTime(2020, 9, 21, 18, 0, 0));
        comment.setDurableId(5);

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\Test21058", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);

        verifyComment(comment, "Comment 1.", 5, "2020-09-21T18:00:00Z");
        msAssert.areEqual("2020-09-21T23:00:00Z", FormatterPal.dateTimeToXmlUtc(comment.getDateTimeInternal()));
    }

    /// <summary>
    /// WORDSJAVA-2406 Comment.getDateTime(), DigitalSignature.getSignTime() returns date plus current time zone
    /// </summary>
    @Test
    public void testJava2406() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        DateTime currentDate = new DateTime();

        Comment comment = new Comment(doc, "Amy Lee", "AL", currentDate);
        builder.getCurrentParagraph().appendChild(comment);

        String savedPath = TestUtil.save(doc, "Model\\Comment\\TestJava2406.docx");

        doc = new Document(savedPath);
        comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);

        msAssert.areEqual(currentDate.toString(), comment.getDateTimeInternal().toString());
    }

    /// <summary>
    /// WORDSNET-21481 Comment time became wrong (UTC) after saving/loading a document.
    /// </summary>
    @Test
    public void test21481() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Some text is added.");

        DateTime date = DateTime.getToday();
        Comment comment = new Comment(doc, "Amy Lee", "AL", date);
        comment.setText("Comment text.");
        builder.getCurrentParagraph().appendChild(comment);

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\Test21481.docx", null, false);
        comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);

        msAssert.areEqual(date, comment.getDateTimeInternal());
    }

    /// <summary>
    /// WORDSNET-21325 Comments date do not reflect to changed time zone.
    /// Comment's date now writes to DateTimeUtc property as well.
    /// </summary>
    @Test (dataProvider = "test21325DataProvider")
    public void test21325(/*DateTimeKind*/long kind) throws Exception
    {
        // Looks like this test works only in Russia TZ Standard Time, does not work in Kyiv time zone.
        if (CurrentThread.getTimeZone().getDisplayName().startsWith("Russia TZ "))
        {
            // Customer's code
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.write("Some text is added.");

            DateTime date = new DateTime(2020, 11, 26, 12, 30, 30, kind);

            Paragraph paragraph = doc.getFirstSection().getBody().getFirstParagraph();

            Comment comment = new Comment(
                doc,
                "Author Name",
                "ÄN",
                date
            );
            comment.setText("This is the comment text.");

            paragraph.appendChild(new CommentRangeStart(doc, comment.getId()));
            paragraph.appendChild(comment);

            Run run = new Run(doc);
            run.setText("This is the text the comment is applied to.");
            paragraph.appendChild(run);

            paragraph.appendChild(new CommentRangeEnd(doc, comment.getId()));

            OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
            saveOptions.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
            saveOptions.setSaveFormat(SaveFormat.DOCX);

            // commentExtensible part containing UTC date a Word2019 feature, we need to set it explicitly.
            doc.getCompatibilityOptions().optimizeFor(com.aspose.words.MsWordVersion.WORD_2019);

            String[] partNames = new String[] {
            "word/comments.xml",
            "word/commentsExtensible.xml"};

            org.w3c.dom.Document[] xmlDocs = DocxExportContext.saveAndGetXmlDocuments(doc, saveOptions, partNames);

            // Written local value always the same regardless of source DateTime.Kind.
            final String EXPECTED_LOCAL = "2020-11-26T12:30:30Z";

            // Written UTC value depends on source DateTime.Kind.
            // Cannot make this TestCase parameter as it depends on environment UTC offset and daylightSavingTime.
        TimeSpan daylightSavingTimeDelta = TimeSpan.fromJava(CurrentThread.getTimeZone().getDSTSavings());
            TimeSpan utcOffcet = CurrentThread.getUtcOffsetFromCurrentTimeZone(DateTime.getNow());

            String expectedUtc = (kind != DateTimeKind.UTC)
                ? FormatterPal.dateTimeToXmlUtc(DateTime.add(DateTime.subtract(date, utcOffcet), daylightSavingTimeDelta))
                : EXPECTED_LOCAL;

            msAssert.areEqual(EXPECTED_LOCAL, xmlDocs[0].getElementsByTagName("w:comment").item(0).getAttributes().getNamedItem("w:date").getNodeValue());
            msAssert.areEqual(expectedUtc, xmlDocs[1].getElementsByTagName("w16cex:commentExtensible").item(0).getAttributes().getNamedItem("w16cex:dateUtc").getNodeValue());

            // Verify read values.
            doc = TestUtil.saveOpen(doc, "Model\\Comment\\Test21325", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
            comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);
            msAssert.areEqual(EXPECTED_LOCAL, FormatterPal.dateTimeToXmlUtc(comment.getDateTimeInternal()));
            msAssert.areEqual(expectedUtc, FormatterPal.dateTimeToXmlUtc(comment.getDateTimeUtcInternal()));
        }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21325DataProvider")
	public static Object[][] test21325DataProvider() throws Exception
	{
		return new Object[][]
		{
			{DateTimeKind.UTC},
			{DateTimeKind.LOCAL},
			{DateTimeKind.UNSPECIFIED},
		};
	}

    /// <summary>
    /// WORDSNET-22313 Aspose.Words throws FileCorruptedException when loading a document.
    /// Fixed by resolving duplicate ParaIds used in comments.
    /// </summary>
    @Test
    public void test22313() throws Exception
    {
        RandomUtil.reset();

        final String EXPECTED_AUTHOR = "Chaitra Manjunatha";

        Document doc = TestUtil.open("Model\\Comment\\Test22313", LoadFormat.DOCX);
        NodeCollection comments = doc.getChildNodes(NodeType.COMMENT, true);
        msAssert.areEqual(2, comments.getCount());

        Comment comment = ms.as(comments.get(0), Comment.class);
        msAssert.areEqual(0, comment.getId());
        msAssert.areEqual(-1, comment.getParentId());
        msAssert.areEqual(644582707, comment.getDurableId());
        msAssert.areEqual(EXPECTED_AUTHOR, comment.getAuthor());
        msAssert.areEqual(1834080397, comment.getFirstParagraph().getParaId());
        msAssert.isFalse(comment.isDateTimeUtcDefined());

        comment = ms.as(comments.get(1), Comment.class);
        msAssert.areEqual(1, comment.getId());
        msAssert.areEqual(-1, comment.getParentId());
        msAssert.areEqual(603259337, comment.getDurableId());
        msAssert.areEqual(EXPECTED_AUTHOR, comment.getAuthor());
        msAssert.areEqual(815348239, comment.getFirstParagraph().getParaId());
        msAssert.areEqual(new DateTime(2021, 3, 11, 9, 53, 0), comment.getDateTimeUtcInternal());
    }

    /// <summary>
    /// WORDSNET-22564 Comments for Content controls are messed up after clone.
    /// When cloning nodes, comment/comment range clones had the same ID as the original nodes. When saving a
    /// document, all comment range nodes with duplicate ID are removed. So, in the customer's case, the comment
    /// nodes were preserved, but the comment ranges were lost.
    /// </summary>
    @Test
    public void test22564() throws Exception
    {
        RandomUtil.reset();

        final String FILE_NAME = "Model\\Comment\\Test22564.docx";
        Document doc = TestUtil.open(FILE_NAME);

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (Node node : (Iterable<Node>) sdts)
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)node;
            if (sdt.getSdtType() != SdtType.REPEATING_SECTION)
                continue;

            for (int repeat = 2; repeat > 0; repeat--)
            {
                StructuredDocumentTag clone = (StructuredDocumentTag)sdt.deepClone(true);
                clone.getRange().replace("{{Repeat}}", Integer.toString(repeat));

                sdt.insertNext(clone);
                clone.removeSelfOnly();
            }

            sdt.remove(); // remove section template
        }

        msAssert.areEqual(12, doc.getChildNodes(NodeType.COMMENT_RANGE_START, true).getCount());

        doc = TestUtil.saveOpenDocxExportOnly(doc, FILE_NAME);

        msAssert.areEqual(12, doc.getChildNodes(NodeType.COMMENT_RANGE_START, true).getCount());
    }

    /// <summary>
    /// WORDSNET-23936 Reverse order of replies on the comment in the air
    /// A new reply was added after the last node of the <see cref="Comment.Replies"/> collection. But if the
    /// parent comment is not actually within the document, <see cref="Comment.Replies"/> can return wrong data and
    /// the new reply could be added to an unexpected place. Fixed by refusal of using <see cref="Comment.Replies"/>,
    /// but checking nodes after the parent comment node instead.
    /// </summary>
    @Test
    public void test23936() throws Exception
    {
        RandomUtil.reset();

        Document doc = new Document();

        // Create a paragraph and do not add to the document.
        Paragraph paragraph1 = new Paragraph(doc);

        Comment comment1 = new Comment(doc, "Author", "", new DateTime(2022, 6, 23, 14, 44, 0));
        comment1.setText("Parent Comment #1");
        paragraph1.appendChild(comment1);

        addAndCheckReplies(comment1);

        // Add the parent paragraph to the document.
        doc.getFirstSection().getBody().appendChild(paragraph1);

        // Create another paragraph and do not add to the document.
        Paragraph paragraph2 = new Paragraph(doc);

        Comment comment2 = new Comment(doc, "Author", "", new DateTime(2022, 6, 23, 14, 46, 0));
        comment2.setText("Parent Comment #2");
        paragraph2.appendChild(new CommentRangeStart(doc, comment2.getId()));
        paragraph2.appendChild(new Run(doc, "Text"));
        paragraph2.appendChild(new CommentRangeEnd(doc, comment2.getId()));
        paragraph2.appendChild(comment2);

        addAndCheckReplies(comment2);

        // Add the paragraph to the document.
        doc.getFirstSection().getBody().appendChild(paragraph2);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Comment\\Test23936.docx");
    }

    /// <summary>
    /// WORDSNET-24100 Comment.AddReply adds reply as a separate comment instead of reply.
    /// Ignore negative ParaId on document loading.
    /// </summary>
    @Test
    public void test24100() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\Test24100.docx");
        Comment root = (Comment)doc.getChild(NodeType.COMMENT, 0, true);

        // Add a reply, which will show up under its parent comment.
        root.addReplyInternal("Joe Bloggs", "J.B.", new DateTime(2022, 8, 4), "New reply");

        doc = TestUtil.saveOpen(doc, "Model\\Comment\\Test24100.docx");

        Comment replyComment = (Comment)doc.getChild(NodeType.COMMENT, 1, true);
        msAssert.areEqual(0, replyComment.getParentId());
        msAssert.areEqual("\u0005New reply\r", replyComment.getText());
    }

    /// <summary>
    /// Additional test for WORDSNET-24100
    /// </summary>
    @Test
    public void test24100A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\Test24100A1.xml");
        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 1, true);

        msAssert.areEqual(0, comment.getParentId());
        msAssert.isTrue(comment.getDone());

        doc = TestUtil.open("Model\\Comment\\Test24100A2.xml");
        comment = (Comment)doc.getChild(NodeType.COMMENT, 1, true);

        // Comment extended properties should be lost.
        msAssert.areEqual(Comment.NO_PARENT, comment.getParentId());
        msAssert.isFalse(comment.getDone());
    }

    /// <summary>
    /// Tests setting comment ID properties of the <see cref="Comment"/>, <see cref="CommentRangeStart"/>,
    /// <see cref="CommentRangeEnd"/> classes.
    /// </summary>
    @Test
    public void testSettingCommentIds() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("This test document contains a ");

        final int COMMENT_ID = 10;
        CommentRangeStart commentRangeStart = new CommentRangeStart(doc, COMMENT_ID);
        builder.getCurrentParagraph().appendChild(commentRangeStart);

        final int REPLY_ID = 11;
        CommentRangeStart replyRangeStart = new CommentRangeStart(doc, REPLY_ID);
        builder.getCurrentParagraph().appendChild(replyRangeStart);

        builder.write("comment");

        CommentRangeEnd commentRangeEnd = new CommentRangeEnd(doc, COMMENT_ID);
        builder.getCurrentParagraph().appendChild(commentRangeEnd);

        Comment comment = new Comment(doc, "Test1", "T1", new DateTime(2023, 10, 2, 20, 24, 0));
        comment.setId(COMMENT_ID);
        builder.getCurrentParagraph().appendChild(comment);

        Paragraph commentParagraph = new Paragraph(doc);
        commentParagraph.appendChild(new Run(doc, "This is comment text."));
        comment.appendChild(commentParagraph);

        CommentRangeEnd replyRangeEnd = new CommentRangeEnd(doc, REPLY_ID);
        builder.getCurrentParagraph().appendChild(replyRangeEnd);

        Comment reply = new Comment(doc, "Test2", "T2", new DateTime(2023, 10, 2, 20, 25, 0));
        reply.setId(REPLY_ID);
        reply.setParentId(COMMENT_ID);
        builder.getCurrentParagraph().appendChild(reply);

        Paragraph replyParagraph = new Paragraph(doc);
        replyParagraph.appendChild(new Run(doc, "This is text in the reply."));
        reply.appendChild(replyParagraph);

        builder.writeln(" with reply.");

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Comment\\TestSettingCommentIds.docx");
    }

    /// <summary>
    /// WORDSNET-25975 Comment date is not updated in commentsExtensible.xml
    /// The <see cref="Comment.DateTimeUtc"/> property was not updated when setting comment datetime.
    /// </summary>
    @Test
    public void test25975() throws Exception
    {
        Document doc = TestUtil.open("Model\\Comment\\Test25975.docx");
        Comment comment = (Comment)doc.getChild(NodeType.COMMENT, 0, true);

        msAssert.areEqual(new DateTime(2023, 9, 26, 7, 40, 0), comment.getDateTimeUtcInternal());

        comment.setDateTimeInternal(new DateTime(2020, 1, 1));

        // UtcDateTime differs from DateTime by the offset of the current time zone: just check that the difference
        // is less than one day.
        double difference = DateTime.subtract(comment.getDateTimeInternal(),comment.getDateTimeUtcInternal()).getTotalDays();
        msAssert.isTrue(Math.abs(difference) < 1);

        // Check that the date is written to commentsExtensible.xml part.

        DocxExportContext context = new DocxExportContext(doc, "word/commentsExtensible.xml");
        org.w3c.dom.Node xmlNode = context.getXmlNode("//w16cex:commentsExtensible/w16cex:commentExtensible");

        // Written value depends on current timezone, thus use UtcDateTime that is already checked above.
        String expectedValue = comment.getDateTimeUtcInternal().toString("yyyy-MM-dd\\THH:mm:ss\\Z");
        msAssert.areEqual(expectedValue, xmlNode.getAttributes().getNamedItem("w16cex:dateUtc").getNodeValue());
    }



    /// <summary>
    /// Adds a reply to the specified comment and checks that the first reply is placed after the comment itself
    /// and that the second reply is placed after the first one.
    /// </summary>
    private static void addAndCheckReplies(Comment comment) throws Exception
    {
        Comment previousReply = null;
        for (int i = 1; i <= 2; i++)
        {
            DateTime dateTime = new DateTime(2022, 6, 23, 14, 45, i);
            Comment reply = comment.addReplyInternal("Author", "", dateTime, "#" + i);

            msAssert.areSame((previousReply != null) ? previousReply : comment, reply.getPreviousNonAnnotationSibling());
            previousReply = reply;
        }
    }

    /// <summary>
    /// Check comment tree in details.
    /// </summary>
    private static void verifyCommentTree(Document doc, boolean parentRead, boolean doneRead)
    {
        verifyComment(doc, 0, 0, -1, false, parentRead, doneRead);
        verifyComment(doc, 1, 1, 0, true, parentRead, doneRead);
        verifyComment(doc, 2, 2, 0, false, parentRead, doneRead);
        verifyComment(doc, 3, 3, 0, false, parentRead, doneRead);
        verifyComment(doc, 4, 4, 0, true, parentRead, doneRead);
        verifyComment(doc, 5, 5, -1, false, parentRead, doneRead);
        verifyComment(doc, 6, 6, 5, false, parentRead, doneRead);
    }

    private static void verifyComment(Document doc, int idx, int id, int parentId, boolean done, boolean parentRead, boolean doneRead)
    {
        Comment comment = (Comment)doc.getFirstSection().getBody().getChild(NodeType.COMMENT, idx, true);
        msAssert.areEqual(id, comment.getId());

        // ParentId should be -1 if not read.
        msAssert.areEqual(parentRead ? parentId : -1, comment.getParentId());

        // Done should be false if not read.
        msAssert.areEqual(doneRead ? done : false, comment.getDone());
    }

    /// <summary>
    /// Verifies Word 2016+ properties of a comment.
    /// </summary>
    private static void verifyComment(Comment comment, String text, int durableId, String utcDateTime)
    {
        msAssert.areEqual("\u0005" + text + "\r", comment.getText());
        msAssert.areEqual(durableId, comment.getDurableId());
        if (utcDateTime != null)
            msAssert.areEqual(utcDateTime, FormatterPal.dateTimeToXmlUtc(comment.getDateTimeUtcInternal()));
        else
            msAssert.isFalse(comment.isDateTimeUtcDefined());
        msAssert.isFalse(comment.isIntelligentPlaceholder());
    }
}

