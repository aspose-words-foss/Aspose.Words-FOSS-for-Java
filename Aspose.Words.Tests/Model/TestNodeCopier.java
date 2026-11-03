// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestNodeCopier
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testRunsCopying() throws Exception
    {
        mDocument = TestUtil.open("Model\\Nodes\\TestNodeCopierRuns.docx");

        final int SOURCE_RUN_IDX = 1;
        Run sourceRun = getTestRun(SOURCE_RUN_IDX);
        Run nextSourceRun = getTestRun(SOURCE_RUN_IDX + 1);
        Run previousSourceRun = getTestRun(SOURCE_RUN_IDX - 1);

        final int REFERENCE_RUN_INDEX = 3;
        Run referenceRun = getTestRun(REFERENCE_RUN_INDEX);

        // Copy a single run, whole.
        NodeRange copyRange = new NodeRange(sourceRun, true, sourceRun, true);
        NodeCopier.copy(copyRange, referenceRun);

        // Copy a single run, whole, end on the next run
        copyRange = new NodeRange(sourceRun, true, nextSourceRun, false);
        NodeCopier.copy(copyRange, referenceRun);

        // Copy a single run, whole, start on the previous run
        copyRange = new NodeRange(previousSourceRun, false, sourceRun, true);
        NodeCopier.copy(copyRange, referenceRun);

        // Copy a single run, whole, start on the previous run and end on the next run.
        copyRange = new NodeRange(previousSourceRun, false, nextSourceRun, false);
        NodeCopier.copy(copyRange, referenceRun);

        // Copy a single run, whole, start on the previous run and end on the next run.
        copyRange = new NodeRange(previousSourceRun, false, nextSourceRun, false);
        NodeCopier.copy(copyRange, referenceRun);

        // One symbol from the beginning, and one from the end.
        DocumentPosition start = new DocumentPosition(previousSourceRun, previousSourceRun.getText().length() - 1);
        DocumentPosition end = new DocumentPosition(nextSourceRun, 1);
        copyRange = new NodeRange(start, end);
        NodeCopier.copy(copyRange, referenceRun);

        TestUtil.saveOpen(mDocument, "Model\\Nodes\\TestNodeCopierRuns Modified.docx");
    }

    @Test
    public void testParagraphCopying11() throws Exception
    {
        // Copy a single paragraph
        testParagraphCopyingBody(true, true);
    }

    @Test
    public void testParagraphCopying10() throws Exception
    {
        // Copy a single paragraph, do not include the paragraph after
        testParagraphCopyingBody(true, false);
    }

    @Test
    public void testParagraphCopying01() throws Exception
    {
        // Copy a single paragraph, do not include the paragraph before
        testParagraphCopyingBody(false, true);
    }

    @Test
    public void testParagraphCopying00() throws Exception
    {
        // Copy a single paragraph, do not include the paragraphs before and after
        testParagraphCopyingBody(false, false);
    }

    private void testParagraphCopyingBody(boolean startFromSourceParagraph, boolean endOnSourceParagraph) throws Exception
    {
        final int SOURCE_PARAGRAPH_INDEX = 1;
        final int REFERENCE_RUN_INDEX = 8;
        testNodeCopyingBody(SOURCE_PARAGRAPH_INDEX, startFromSourceParagraph, endOnSourceParagraph, REFERENCE_RUN_INDEX, "Paragraphs");
    }

    private void testNodeCopyingBody(int sourceNodeIndex, boolean startFromSourceNode, boolean endOnSourceNode, int referenceNodeIndex, String testFileName) throws Exception
    {
        mDocument = TestUtil.open("Model\\Nodes\\TestNodeCopier" + testFileName + ".docx");

        Debug.writeLine(msString.format("Start from source node: {0}; End on source node: {1}", startFromSourceNode, endOnSourceNode));

        Node sourceNode = mDocument.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).get(sourceNodeIndex);
        Node startNode = startFromSourceNode ? sourceNode : sourceNode.getPreviousSibling();
        Node endNode = endOnSourceNode ? sourceNode : sourceNode.getNextSibling();

        Run referenceRun = getTestRun(referenceNodeIndex);

        // Copy a single paragraph
        NodeRange copyRange = new NodeRange(startNode, startFromSourceNode, endNode, endOnSourceNode);
        NodeCopier.copy(copyRange, referenceRun);

        TestUtil.saveOpen(mDocument, "Model\\Nodes\\TestNodeCopier" + testFileName + " Modified.docx");
    }

    @Test
    public void testParagraphCopying2() throws Exception
    {
        mDocument = TestUtil.open("Model\\Nodes\\TestNodeCopierParagraphs2.docx");

        final int SOURCE_PARAGRAPH_INDEX = 1;
        Paragraph sourceParagraph = getTestParagraph(SOURCE_PARAGRAPH_INDEX);
        Paragraph previousParagraph = getTestParagraph(SOURCE_PARAGRAPH_INDEX - 1);

        Run startRun = (Run)previousParagraph.getChildNodes(NodeType.ANY, false).get(0);
        Run endRun = (Run)sourceParagraph.getChildNodes(NodeType.ANY, false).get(1);

        Run referenceRun = getReferenceRun2();

        DocumentPosition startPosition = new DocumentPosition(startRun, startRun.getText().length() / 2);
        DocumentPosition endPosition = new DocumentPosition(endRun, endRun.getText().length() / 2);
        NodeRange copyRange = new NodeRange(startPosition, endPosition);
        NodeCopier.copy(copyRange, referenceRun);

        TestUtil.saveOpen(mDocument, "Model\\Nodes\\TestNodeCopierParagraphs2 Modified.docx");
    }

    @Test
    public void testParagraphCopying200() throws Exception
    {
        // Empty paragraph
        testParagraphCopyingBody2(false, false);
    }

    @Test
    public void testParagraphCopying201() throws Exception
    {
        // All from the second paragraph
        testParagraphCopyingBody2(false, true);
    }

    @Test
    public void testParagraphCopying210() throws Exception
    {
        // All from the first paragraph
        testParagraphCopyingBody2(true, false);
    }

    @Test
    public void testParagraphCopying211() throws Exception
    {
        // All from the both paragraphs
        testParagraphCopyingBody2(true, true);
    }

    private void testParagraphCopyingBody2(boolean startOnFirstRun, boolean endOnFirstRun) throws Exception
    {
        mDocument = TestUtil.open("Model\\Nodes\\TestNodeCopierParagraphs2.docx");

        Debug.writeLine(msString.format("Start from first run of par 0: {0}; End on first run of par 1: {1}", startOnFirstRun, endOnFirstRun));

        final int SOURCE_PARAGRAPH_INDEX = 0;
        Paragraph startParagraph = getTestParagraph(SOURCE_PARAGRAPH_INDEX);
        DocumentPosition startPosition = getParagraphRun(startParagraph, startOnFirstRun);
        
        Paragraph endParagraph = (Paragraph)startParagraph.getNextSibling();
        DocumentPosition endPosition = getParagraphRun(endParagraph, endOnFirstRun);

        Run referenceRun = getReferenceRun2();

        // Copy a single paragraph
        NodeRange copyRange = new NodeRange(startPosition, endPosition);
        NodeCopier.copy(copyRange, referenceRun);

        String saveSuffix = (startOnFirstRun ? "1" : "0") +
            (endOnFirstRun ? "1" : "0") +
            " Modified.docx";

        TestUtil.saveOpen(mDocument, "Model\\Nodes\\TestNodeCopierParagraphs2 " + saveSuffix);
    }

    @Test
    public void testTableCopying11() throws Exception
    {
        // Copy a single table
        testTableCopyingBody(true, true);
    }

    @Test
    public void testTableCopying10() throws Exception
    {
        // Copy a single table, do not include the paragraph after
        testTableCopyingBody(true, false);
    }

    @Test
    public void testTableCopying01() throws Exception
    {
        // Copy a single table, do not include the paragraph before
        testTableCopyingBody(false, true);
    }

    @Test
    public void testTableCopying00() throws Exception
    {
        // Copy a single table, do not include the paragraphs before and after
        testTableCopyingBody(false, false);
    }

    private void testTableCopyingBody(boolean startFromSourceNode, boolean endOnSourceNode) throws Exception
    {
        final int SOURCE_TABLE_INDEX = 1;
        final int REFERENCE_RUN_INDEX = 12;
        testNodeCopyingBody(SOURCE_TABLE_INDEX, startFromSourceNode, endOnSourceNode, REFERENCE_RUN_INDEX, "Table");
    }

    @Test
    public void testLastParagraph() throws Exception
    {
        final int SOURCE_PARAGRAPH_INDEX = 3;
        final int REFERENCE_RUN_INDEX = 1;
        final boolean START_FROM_SOURCE_NODE = true;
        final boolean END_ON_SOURCE_NODE = true;
        testNodeCopyingBody(SOURCE_PARAGRAPH_INDEX, START_FROM_SOURCE_NODE, END_ON_SOURCE_NODE, REFERENCE_RUN_INDEX, "LastParagraph");
    }

    @Test
    public void testNoSiblingForTheReferenceNode() throws Exception
    {
        final int SOURCE_PARAGRAPH_INDEX = 2;
        final int REFERENCE_RUN_INDEX = 2;
        testNodeCopyingBody(SOURCE_PARAGRAPH_INDEX, true, true, REFERENCE_RUN_INDEX, "NoSibling");
    }

    /// <summary>
    /// Copying paragraphs from one document to another.
    /// </summary>
    @Test
    public void testParagraphCopying3() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestNodeCopierParagraphs.docx");

        // Source document with two paragraphs.
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestNodeCopierParagraphs2.docx");

        nodeCopierCore(srcDoc, dstDoc);

        msAssert.areEqual(8, dstDoc.getFirstSection().getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Copying sections from one document to another.
    /// </summary>
    @Test
    public void testSectionCopying() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Nodes\\TestNodeCopierParagraphs.docx");

        // Source document with three sections.
        Document srcDoc = TestUtil.open("Model\\Nodes\\TestNodeCopierSections.docx");

        nodeCopierCore(srcDoc, dstDoc);

        msAssert.areEqual(1, dstDoc.getSections().get(0).getBody().getParagraphs().getCount());
        msAssert.areEqual(1, dstDoc.getSections().get(1).getBody().getParagraphs().getCount());
        msAssert.areEqual(5, dstDoc.getSections().get(2).getBody().getParagraphs().getCount());
    }

    /// <summary>
    /// Upon UpdateFields AW uses NodeCopier to replace fields with the field results.
    /// Test this when field result contains section break. 
    /// </summary>
    @Test
    public void testFieldResultCopying() throws Exception
    {
        Document doc = TestUtil.open("Model\\Nodes\\TestFieldCopying.docx");
        doc.updateFields();

        // Last section should contain one paragraph after update fields.
        msAssert.areEqual(1, doc.getLastSection().getBody().getParagraphs().getCount());
    }

    private static void nodeCopierCore(Document srcDoc, Document dstDoc) throws Exception
    {
        NodeRange sourceRange = new NodeRange(srcDoc.getFirstSection().getBody().getFirstChild(), srcDoc.getLastSection().getBody().getLastChild());

        ExternalDocumentModifier modifier = new ExternalDocumentModifier(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        NodeCopier.copy(sourceRange, dstDoc.getFirstSection().getBody().getFirstParagraph(), modifier);
    }

    private static DocumentPosition getParagraphRun(Paragraph par, boolean firstRun)
    {
        Run run = (Run)(firstRun ? par.getFirstChild() : par.getLastChild());
        return new DocumentPosition(run, (firstRun ? 0 : run.getText().length()));
    }

    private Run getTestRun(int runIdx)
    {
        return (Run)getTestNode(mDocument, NodeType.RUN, runIdx);
    }

    private Paragraph getTestParagraph(int idx)
    {
        return (Paragraph)getTestNode(mDocument, NodeType.PARAGRAPH, idx);
    }

    private static Node getTestNode(Document doc, /*NodeType*/int nodeType, int nodeIdx)
    {
        return doc.getChildNodes(nodeType, true).get(nodeIdx);
    }

    private Run getReferenceRun2()
    {
        final int REFERENCE_RUN_INDEX = 8;
        return getTestRun(REFERENCE_RUN_INDEX);
    }

    private Document mDocument;
}

