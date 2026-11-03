// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Basic node functionality tests.
/// </summary>
@Test
public class TestNode
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testDocumentCtor() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        //General features
        msAssert.areEqual(NodeType.DOCUMENT, doc.getNodeType());
        msAssert.areEqual(true, doc.isComposite());
        msAssert.areEqual(null, doc.getParentNode());
        msAssert.areEqual(doc, doc.getDocument());
        msAssert.isNotNull(doc.getNullNode());

        //Sibling features
        msAssert.areEqual(null, doc.getPreviousSibling());
        msAssert.areEqual(null, doc.getNextSibling());

        //Child features
        msAssert.areEqual(false, doc.hasChildNodes());
        msAssert.areEqual(null, doc.getFirstChild());
        msAssert.areEqual(null, doc.getLastChild());
    }

    @Test
    public void testSectionCtor() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        Section sect = new Section(doc);

        //General features
        msAssert.areEqual(NodeType.SECTION, sect.getNodeType());
        msAssert.areEqual(true, sect.isComposite());
        msAssert.areEqual(null, sect.getParentNode());        //No parent at this stage.
        msAssert.areEqual(doc, sect.getDocument());    //But owner document is still accessible

        //Sibling features
        msAssert.areEqual(null, sect.getPreviousSibling());
        msAssert.areEqual(null, sect.getNextSibling());

        //Child features
        msAssert.areEqual(false, sect.hasChildNodes());
        msAssert.areEqual(null, sect.getFirstChild());
        msAssert.areEqual(null, sect.getLastChild());
    }

    @Test
    public void testCloneNode() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);
        Section sect1 = doc.appendChild(new Section(doc));
        sect1.appendChild(new Body(doc));

        //Clone without children
        Section sect2 = (Section)sect1.deepClone(false);
        msAssert.areEqual(null, sect2.getParentNode());
        msAssert.areEqual(doc, sect2.getDocument());
        msAssert.areEqual(null, sect2.getFirstChild());

        //Clone with children, check the child was cloned
        Section sect3 = (Section)sect1.deepClone(true);
        msAssert.areEqual(NodeType.BODY, sect3.getFirstChild().getNodeType());
    }



    /// <summary>
    /// Tests Node.IsAbove() method.
    /// </summary>
    @Test
    public void testIsAbove() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Paragraph par0 = builder.insertParagraph();
        Run run00 = new Run(doc, "00 ");
        builder.insertNode(run00);
        Run run01 = new Run(doc, "01 ");
        builder.insertNode(run01);
        Run run02 = new Run(doc, "01 ");
        builder.insertNode(run02);


        Paragraph par1 = builder.insertParagraph();
        Run run10 = new Run(doc, "10 ");
        builder.insertNode(run10);
        Run run11 = new Run(doc, "11 ");
        builder.insertNode(run11);
        Run run12 = new Run(doc, "12 ");
        builder.insertNode(run12);

        // Null cases:
        msAssert.isFalse(Node.nodeAIsAboveNodeB(null, null));
        msAssert.isFalse(Node.nodeAIsAboveNodeB(null, run00));
        msAssert.isFalse(run00.isAbove(null));

        // Same node cases.
        msAssert.isFalse(run00.isAbove(run00));
        msAssert.isFalse(par0.isAbove(par0));
        msAssert.isFalse(doc.isAbove(doc));

        // Leaf cases.
        msAssert.isTrue(run00.isAbove(run01));
        msAssert.isFalse(run01.isAbove(run00));
        msAssert.isTrue(run00.isAbove(par1));

        // Ancestor cases.
        msAssert.isTrue(par1.isAbove(run12));
        msAssert.isTrue(doc.isAbove(run00));
        msAssert.isFalse(par1.isAbove(doc));
        msAssert.isTrue(doc.getFirstSection().getBody().isAbove(run12));

        // Composite node cases.
        msAssert.isTrue(par0.isAbove(par1));

        // Different tree cases.
        Paragraph parentlessClone = (Paragraph)par0.deepClone(true);
        msAssert.isNull(parentlessClone.getParentNode());
        msAssert.isFalse(parentlessClone.isAbove(run00));
        msAssert.isFalse(doc.isAbove(parentlessClone));
        Document doc2 = new Document();
        msAssert.isFalse(doc.isAbove(doc2.getFirstSection().getBody()));
    }


    @Test
    public void testJira5551GetChildGoesToSibling() throws Exception
    {
        final String FILE_NAME = "Model\\Nodes\\TestJira5551GetChild.docx";
        Document doc = TestUtil.open(FILE_NAME);

        Paragraph par = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);

        // The second child is Run:
        // WORDSNET-10749 There is now CustomXml in document now. Hence, FirstChild is Run itself.
        Node firstChild = par.getFirstChild();
        msAssert.areEqual(NodeType.RUN, firstChild.getNodeType());

        // Get the first run. *Dont'* go deep.
        Node firstRun = par.getChild(NodeType.RUN, 0, false);
        msAssert.isNotNull(firstRun);
    }

    @Test
    public void testJira455() throws Exception
    {
        Document doc = new Document();

        // The run is NOT added to the document. This was causing an exception.
        Run run = new Run(doc, "test");
        msAssert.areEqual("test", run.toString(SaveFormat.TEXT));
    }

    /// <summary>
    /// Tests the <see cref="Node.NodeLevel"/> property.
    /// </summary>
    @Test
    public void testNodeLevel() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        msAssert.areEqual(NodeLevel.SECTION_STORY, body.getNodeLevel());

        BookmarkStart bookmarkStart1 = new BookmarkStart(doc, "BM1");
        msAssert.areEqual(NodeLevel.INLINE, bookmarkStart1.getNodeLevel());
        body.appendChild(bookmarkStart1);
        msAssert.areEqual(NodeLevel.BLOCK, bookmarkStart1.getNodeLevel());

        Paragraph paragraph1 = new Paragraph(doc);
        body.appendChild(paragraph1);
        msAssert.areEqual(NodeLevel.BLOCK, paragraph1.getNodeLevel());

        StructuredDocumentTag sdt1 = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        BookmarkEnd bookmarkEnd1 = new BookmarkEnd(doc, "BM1");
        sdt1.appendChild(bookmarkEnd1);
        msAssert.areEqual(NodeLevel.BLOCK, bookmarkEnd1.getNodeLevel());
        body.appendChild(sdt1);
        msAssert.areEqual(NodeLevel.BLOCK, sdt1.getNodeLevel());

        Paragraph paragraph2 = new Paragraph(doc);
        sdt1.appendChild(paragraph2);
        Run run1 = new Run(doc);
        paragraph2.appendChild(run1);
        msAssert.areEqual(NodeLevel.INLINE, run1.getNodeLevel());

        StructuredDocumentTag sdt2 = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        paragraph2.appendChild(sdt2);
        msAssert.areEqual(NodeLevel.INLINE, sdt2.getNodeLevel());

        BookmarkStart bookmarkStart2 = new BookmarkStart(doc, "BM2");
        // Incorrect node place, but check for NodeLevel.Inline.
        sdt2.appendChild(bookmarkStart2);
        msAssert.areEqual(NodeLevel.INLINE, bookmarkStart2.getNodeLevel());

        Table table1 = new Table(doc);
        body.appendChild(table1);
        msAssert.areEqual(NodeLevel.BLOCK, table1.getNodeLevel());

        BookmarkEnd bookmarkEnd2 = new BookmarkEnd(doc, "BM2");
        table1.appendChild(bookmarkEnd2);
        msAssert.areEqual(NodeLevel.ROW, bookmarkEnd2.getNodeLevel());

        StructuredDocumentTag sdt3 = new StructuredDocumentTag(doc, MarkupLevel.ROW);
        table1.appendChild(sdt3);
        msAssert.areEqual(NodeLevel.ROW, sdt3.getNodeLevel());

        Row row1 = new Row(doc);
        table1.appendChild(row1);
        msAssert.areEqual(NodeLevel.ROW, row1.getNodeLevel());

        BookmarkStart bookmarkStart3 = new BookmarkStart(doc, "BM3");
        row1.appendChild(bookmarkStart3);
        msAssert.areEqual(NodeLevel.CELL, bookmarkStart3.getNodeLevel());

        StructuredDocumentTag sdt4 = new StructuredDocumentTag(doc, MarkupLevel.CELL);
        row1.appendChild(sdt4);
        msAssert.areEqual(NodeLevel.CELL, sdt4.getNodeLevel());

        BookmarkEnd bookmarkEnd3 = new BookmarkEnd(doc, "BM3");
        sdt4.appendChild(bookmarkEnd3);
        msAssert.areEqual(NodeLevel.CELL, bookmarkEnd3.getNodeLevel());

        Cell cell1 = new Cell(doc);
        sdt4.appendChild(cell1);
        msAssert.areEqual(NodeLevel.CELL, cell1.getNodeLevel());

        BookmarkStart bookmarkStart4 = new BookmarkStart(doc, "BM4");
        cell1.appendChild(bookmarkStart4);
        msAssert.areEqual(NodeLevel.BLOCK, bookmarkStart4.getNodeLevel());

        Paragraph paragraph3 = new Paragraph(doc);
        cell1.appendChild(paragraph3);
        BookmarkEnd bookmarkEnd4 = new BookmarkEnd(doc, "BM4");
        paragraph3.appendChild(bookmarkEnd4);
        msAssert.areEqual(NodeLevel.INLINE, bookmarkEnd4.getNodeLevel());
    }

    /// <summary>
    /// Relates to WORDSNET-17082 Checks links for inserted node without tracking revisions and with it.
    /// </summary>
    @Test
    public void testJira17082() throws Exception
    {
        // Tests when track revisions is disabled.
        testJira17082Core(false);

        // Tests when track revisions is enabled.
        testJira17082Core(true);
    }

    /// <summary>
    /// Tests the <see cref="CompositeNode.AcceptStart"/> and <see cref="CompositeNode.AcceptEnd"/> methods.
    /// </summary>
    @Test
    public void testAcceptStartAcceptEnd() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().appendChild(new TestCompositeNode(doc));

        TestDocumentVisitor visitor = new TestDocumentVisitor();

        doc.accept(visitor);

        msAssert.isTrue(visitor.isTestCompositeNodeStartVisited());
        msAssert.isTrue(visitor.isTestCompositeNodeEndVisited());
    }

    /// <summary>
    /// Core method for TestJira17082.
    /// Creates new test document, then inserts run from the second paragraph into the first paragraph
    /// and checks correctness of all necessary node links.
    /// </summary>
    private static void testJira17082Core(boolean trackRevisions) throws Exception
    {
        Document doc = createTestJira17082Document();

        if (trackRevisions)
            doc.startTrackRevisions("Author");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        paras.get(0).insertAfter(paras.get(1).getRuns().get(1), paras.get(0).getFirstRun());

        Paragraph firstPara = doc.getFirstSection().getBody().getFirstParagraph();
        Paragraph secondPara = (Paragraph)firstPara.getNextSibling();

        msAssert.areEqual(3, firstPara.getRuns().getCount());
        // The run movement is represented as move ranges: a copy of the run exists in the source paragraph.
        msAssert.areEqual(trackRevisions ? 3 : 2, secondPara.getRuns().getCount());

        // The paragraphs contains move range start/end nodes: have to use XXXNonAnnotationSibling methods.
        msAssert.areEqual(firstPara.getRuns().get(0), firstPara.getRuns().get(1).getPreviousNonAnnotationSibling());
        msAssert.areEqual(firstPara.getRuns().get(1), firstPara.getRuns().get(0).getNextNonAnnotationSibling());
        msAssert.areEqual(firstPara.getRuns().get(1), firstPara.getRuns().get(2).getPreviousNonAnnotationSibling());
        msAssert.areEqual(firstPara.getRuns().get(2), firstPara.getRuns().get(1).getNextNonAnnotationSibling());

        msAssert.areEqual(secondPara.getRuns().get(0), secondPara.getRuns().get(1).getPreviousNonAnnotationSibling());
        msAssert.areEqual(secondPara.getRuns().get(1), secondPara.getRuns().get(0).getNextNonAnnotationSibling());
    }

    /// <summary>
    /// Creates WORDSNET-17082 test document.
    /// </summary>
    private static Document createTestJira17082Document() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(new Document());

        builder.write("First para. First run.");
        builder.writeln("First para. Second run.");

        builder.write("Second para. First run.");
        builder.write("Second para. Second run.");
        builder.write("Second para. Third run.");

        return builder.getDocument();
    }

    /// <summary>
    /// Used to test the <see cref="CompositeNode.AcceptStart"/> and <see cref="CompositeNode.AcceptEnd"/> methods
    /// and ability to inherit from <see cref="CompositeNode"/>.
    /// </summary>
    private static class TestCompositeNode extends CompositeNode
    {
        TestCompositeNode(Document doc)
        {
        	super(doc);
	
        }

        public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
        {
            return acceptCore(visitor);
        }

        public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor)
        {
            TestDocumentVisitor testVisitor = ms.as(visitor, TestDocumentVisitor.class);
            return (testVisitor != null) ? testVisitor.visitTestCompositeNodeStart(this) : VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor)
        {
            TestDocumentVisitor testVisitor = ms.as(visitor, TestDocumentVisitor.class);
            return (testVisitor != null) ? testVisitor.visitTestCompositeNodeEnd(this) : VisitorAction.CONTINUE;
        }

        public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SYSTEM; }
    }

    /// <summary>
    /// Represents a document visitor with support of <see cref="TestCompositeNode"/>.
    /// </summary>
    private static class TestDocumentVisitor extends DocumentVisitor
    {
        /*VisitorAction*/int visitTestCompositeNodeStart(TestCompositeNode node)
        {
            isTestCompositeNodeStartVisited(true);
            return VisitorAction.CONTINUE;
        }

        /*VisitorAction*/int visitTestCompositeNodeEnd(TestCompositeNode node)
        {
            isTestCompositeNodeEndVisited(true);
            return VisitorAction.CONTINUE;
        }

        boolean isTestCompositeNodeStartVisited() { return mIsTestCompositeNodeStartVisited; }; private void isTestCompositeNodeStartVisited(boolean value) { mIsTestCompositeNodeStartVisited = value; };

        private boolean mIsTestCompositeNodeStartVisited;
        boolean isTestCompositeNodeEndVisited() { return mIsTestCompositeNodeEndVisited; }; private void isTestCompositeNodeEndVisited(boolean value) { mIsTestCompositeNodeEndVisited = value; };

        private boolean mIsTestCompositeNodeEndVisited;
    }
}

