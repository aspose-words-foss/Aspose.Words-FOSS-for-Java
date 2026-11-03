// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/09/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.TestFxUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Unit tests for the <see cref="RangeDocumentBuilder"/> class.
/// </summary>
@Test
public class TestRangeDocumentBuilder
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testDocumentNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath("Document\\TestSimple.docx"));
        Document newDoc = RangeDocumentBuilder.build(doc.getRange());

        msAssert.areEqual(doc.getSections().getCount(), newDoc.getSections().getCount());
        msAssert.areEqual(doc.getFirstSection().getBody().getCount(), newDoc.getFirstSection().getBody().getCount());
    }

    @Test
    public void testSectionNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Section\\TestSections.docx")));
        Node testNode = getTestNode(doc, NodeType.SECTION, 1);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.areEqual(1, newDoc.getSections().getCount());
        msAssert.areEqual(getNodeText(testNode), getNodeText(newDoc.getFirstSection()));
    }

    @Test
    public void testBodyNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Stories\\TestBody.docx")));
        Node testNode = getTestNode(doc, NodeType.BODY, 0);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.areEqual(1, newDoc.getSections().getCount());
        msAssert.isNotNull(newDoc.getFirstSection().getBody());
        msAssert.areEqual(getNodeText(testNode), getNodeText(newDoc.getFirstSection().getBody()));
    }

    @Test
    public void testFootnoteNodeToDocument() throws Exception
    {
        verifyInlineNodeToDocument("Stories\\TestBodyFootnoteEndnote.docx", NodeType.FOOTNOTE, 0);
    }

    @Test
    public void testHeaderNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Stories\\TestBodyHeaderFooter.docx")));
        Node testNode = getTestNode(doc, NodeType.HEADER_FOOTER, 0);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.areEqual(1, newDoc.getSections().getCount());
        msAssert.areEqual(1, newDoc.getFirstSection().getHeadersFooters().getCount());
        msAssert.areEqual(getNodeText(testNode), getNodeText(newDoc.getFirstSection().getHeadersFooters().get(0)));
    }

    @Test
    public void testFooterNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Stories\\TestBodyHeaderFooter.docx")));
        Node testNode = getTestNode(doc, NodeType.HEADER_FOOTER, 1);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.areEqual(1, newDoc.getSections().getCount());
        msAssert.areEqual(1, newDoc.getFirstSection().getHeadersFooters().getCount());
        msAssert.areEqual(getNodeText(testNode), getNodeText(newDoc.getFirstSection().getHeadersFooters().get(0)));
    }

    @Test
    public void testParagraphNodeToDocument() throws Exception
    {
        verifySectionChildNodeToDocument("Document\\TestSimple.docx", NodeType.PARAGRAPH, 2);
    }

    @Test
    public void testRunNodeToDocument() throws Exception
    {
        verifyInlineNodeToDocument("Document\\TestSimple.docx", NodeType.RUN, 7);
    }

    @Test
    public void testBookmarkNodeToDocument() throws Exception
    {
        verifyInlineNodeToDocument("Bookmark\\TestBookmarks.docx", NodeType.BOOKMARK_START, 0);
    }


    @Test
    public void testImageNodeToDocument() throws Exception
    {
        verifyInlineNodeToDocument("Shape\\Image\\TestImageInline.docx", NodeType.SHAPE, 0);
    }

    @Test
    public void testTableNodeToDocument() throws Exception
    {
        verifySectionChildNodeToDocument("Table\\TestSimple.docx", NodeType.TABLE, 0);
    }

    @Test
    public void testRowNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Table\\TestSimple.docx")));
        Node testNode = getTestNode(doc, NodeType.ROW, 1);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.isInstanceOf(Table.class, newDoc.getFirstSection().getBody().getFirstChild());
        Table table = (Table) newDoc.getFirstSection().getBody().getFirstChild();
        msAssert.areEqual(1, table.getRows().getCount());
        msAssert.areEqual(getNodeText(testNode), getNodeText(table.getFirstRow()));
    }

    @Test
    public void testCellNodeToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath("Table\\TestSimple.docx")));
        Node testNode = getTestNode(doc, NodeType.CELL, 1);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.isInstanceOf(Table.class, newDoc.getFirstSection().getBody().getFirstChild());
        Table table = (Table)newDoc.getFirstSection().getBody().getFirstChild();
        msAssert.areEqual(1, table.getRows().getCount());
        msAssert.areEqual(1, table.getRows().get(0).getCells().getCount());
        msAssert.areEqual(getNodeText(testNode), getNodeText(table.getRows().get(0).getCells().get(0)));
    }

    @Test
    public void testNestedTableNodeToDocument() throws Exception
    {
        verifySectionChildNodeToDocument("Table\\TestNested.docx", NodeType.TABLE, 1);
    }

    @Test
    public void testGlossaryDocumentToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath("BuildingBlocks\\TestBuildingBlocks.dotx"));
        Document newDoc = RangeDocumentBuilder.build(doc.getGlossaryDocument().getRange());

        msAssert.isNotNull(newDoc.getGlossaryDocument());
    }

    // FOSS: TestCustomXmlMarkupToDocument removed — its input is a .doc (Doc reader removed)
    // with no .docx sibling, and it verifies the legacy custom-XML-markup import warning,
    // which a Word .doc->.docx conversion would strip.

    @Test
    public void testStdToDocument() throws Exception
    {
        Node testNode = getTestNode("Markup\\TestSdtPlaceholders.docx", "0.0.0.0");
        Debug.assertTrue(testNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        Node node1 = newDoc.getFirstSection().getBody().getFirstChild();
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, node1.getNodeType());
        msAssert.areEqual(NodeType.PARAGRAPH, ((CompositeNode) node1).getFirstChild().getNodeType());
    }

    @Test
    public void testBuildingBlockToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath("BuildingBlocks\\TestBuildingBlocks.dotx"));
        Document newDoc = RangeDocumentBuilder.build(doc.getGlossaryDocument().getFirstBuildingBlock().getRange());

        msAssert.isNotNull(newDoc.getGlossaryDocument());
        msAssert.areEqual(1, newDoc.getGlossaryDocument().getBuildingBlocks().getCount());
    }

    @Test
    public void testGlossaryDocumentRunToDocument() throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath("BuildingBlocks\\TestBuildingBlocks.dotx"));
        Node node = doc.getGlossaryDocument().getChild(NodeType.RUN, 0, true);
        Debug.assertTrue(node != null);
        Document newDoc = RangeDocumentBuilder.build(node.getRange());

        msAssert.isNotNull(newDoc.getGlossaryDocument());
        msAssert.areEqual(1, newDoc.getGlossaryDocument().getBuildingBlocks().getCount());
        msAssert.isNotNull(newDoc.getGlossaryDocument().getFirstBuildingBlock().getFirstSection());

        Node runNode = newDoc.getGlossaryDocument().getChild(NodeType.RUN, 0, true);
        msAssert.isNotNull(runNode);
        msAssert.areEqual(node.getText(), runNode.getText());
    }

    /// <summary>
    /// Gets a test node of a source document.
    /// </summary>
    /// <param name="doc">Original document.</param>
    /// <param name="nodeType">Specifies type of a node to select.</param>
    /// <param name="index">Zero based index of a node to select.
    /// Negative indexes are also allowed and indicate access from the end,
    /// that is -1 means the last node.</param>
    private static Node getTestNode(Document doc, /*NodeType*/int nodeType, int index)
    {
        Node node = doc.getChild(nodeType, index, true);
        Debug.assertTrue(node != null);
        return node;
    }

    /// <summary>
    /// Gets a test node of a source document.
    /// </summary>
    /// <param name="srcDocFileName">Name of an original file, relative to the TestData\Model folder.</param>
    /// <param name="id">Node string id.</param>
    private static Node getTestNode(String srcDocFileName, String id) throws Exception
    {
        srcDocFileName = com.aspose.TestFxUtil.correctPath(srcDocFileName);
        Document doc = TestUtil.open(TestUtil.getInModelPath(srcDocFileName));
        Node node = doc.getNodeById(id);
        Debug.assertTrue(node != null);
        return node;
    }

    /// <summary>
    /// Tests a document created from a section child node.
    /// </summary>
    /// <param name="srcDocFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="nodeType">Specifies type of a node to test.</param>
    /// <param name="index">Zero based index of a node to test.
    /// Negative indexes are also allowed and indicate access from the end,
    /// that is -1 means the last node.</param>
    private static void verifySectionChildNodeToDocument(String srcDocFileName, /*NodeType*/int nodeType, int index) throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath(srcDocFileName)));
        Node testNode = getTestNode(doc, nodeType, index);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());

        msAssert.areEqual(1, newDoc.getSections().getCount());
        msAssert.areEqual(1, newDoc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).getCount());
        msAssert.isInstanceOf(testNode.getClass(), newDoc.getFirstSection().getBody().getFirstChild());

        String srcText = getNodeText(testNode);
        String destText = getNodeText(newDoc.getFirstSection().getBody().getFirstChild());
        msAssert.areEqual(srcText, destText);
    }

    /// <summary>
    /// Tests a document created from a inline-level node.
    /// </summary>
    /// <param name="srcDocFileName">Name of a original file, relative to the TestData\Model folder.</param>
    /// <param name="nodeType">Specifies type of a node to test.</param>
    /// <param name="index">Zero based index of a node to test.
    /// Negative indexes are also allowed and indicate access from the end,
    /// that is -1 means the last node.</param>
    private static void verifyInlineNodeToDocument(String srcDocFileName, /*NodeType*/int nodeType, int index) throws Exception
    {
        Document doc = TestUtil.open(TestUtil.getInModelPath(TestFxUtil.correctPath(srcDocFileName)));
        Node testNode = getTestNode(doc, nodeType, index);
        Document newDoc = RangeDocumentBuilder.build(testNode.getRange());
        msAssert.areEqual(1, newDoc.getSections().getCount());

        Body newBody = newDoc.getFirstSection().getBody();
        msAssert.isNotNull(newBody);
        msAssert.isInstanceOf(Paragraph.class, newBody.getFirstChild());
        msAssert.areEqual(getNodeText(testNode), getNodeText(((Paragraph) newBody.getFirstChild()).getFirstChild()));
    }

    /// <summary>
    /// Gets text of a node.
    /// </summary>
    private static String getNodeText(Node node)
    {
        char[] breakChars = new char[] { '\f', '\r' };

        String text = node.getText();
        if (node instanceof Paragraph)
            text = msString.trimEnd(text, breakChars); // Remove end of paragraph characters.
        return text;
    }
}

