// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Basic node functionality tests.
/// </summary>
@Test
public class TestNonMarkupTraverse
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testFirstNonMarkupDescendant() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Paragraph par = builder.insertParagraph();
        msAssert.isNull(par.getFirstNonMarkupDescendant(), "First descendant of an empty composite should be null.");

        StructuredDocumentTag tagFirst = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        StructuredDocumentTag tagNext = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(tagFirst);
        builder.insertNode(tagNext);
        msAssert.areSame(tagNext, tagFirst.getNextSibling());
        msAssert.isNull(par.getFirstNonMarkupDescendant(), "First non-markup descendant of a composite having only markups inside should be null.");

        Run textRun = new Run(doc, "text");
        builder.insertNode(textRun);
        msAssert.areSame(textRun, tagNext.getNextSibling());
        msAssert.areSame(textRun, par.getFirstNonMarkupDescendant(), "Immediate child which is not a first child should be found.");

        Run runInDepth = new Run(doc, "depth");
        tagNext.insertBefore(runInDepth, null);
        msAssert.areSame(runInDepth, tagNext.getFirstChild());
        msAssert.areSame(runInDepth, par.getFirstNonMarkupDescendant(), "Depth-first traverse order should find deeper descendants of preceding children.");
    }

    @Test
    public void testNextNonMarkupNode() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Paragraph par = builder.insertParagraph();
        Run firstRun = new Run(doc, "first Run");
        par.insertBefore(firstRun, null);
        msAssert.areSame(firstRun, par.getFirstChild());
        msAssert.isNull(firstRun.getNextNonMarkupNodeLimited(), "No siblings: should be null.");

        StructuredDocumentTag tagFirst = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        StructuredDocumentTag tagNext = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(tagFirst);
        msAssert.areSame(tagFirst, firstRun.getNextSibling());
        builder.insertNode(tagNext);
        msAssert.areSame(tagNext, tagFirst.getNextSibling());
        msAssert.isNull(firstRun.getNextNonMarkupNodeLimited(), "No non-markup siblings: should be null.");

        Run siblingRun = new Run(doc, "sibling run");
        par.insertAfter(siblingRun, tagNext);
        msAssert.areSame(siblingRun, tagNext.getNextSibling());
        msAssert.areSame(siblingRun, firstRun.getNextNonMarkupNodeLimited(), "Non-markup sibling not found.");

        Run grandChildRun = new Run(doc, "grandchild Run");
        tagNext.insertBefore(grandChildRun, null);
        msAssert.areSame(grandChildRun, tagNext.getFirstChild());
        msAssert.areSame(grandChildRun, firstRun.getNextNonMarkupNodeLimited(), "Depth-first traverse order should find deeper descendants of preceding children.");

        msAssert.areSame(siblingRun, tagNext.getNextNonMarkupNodeLimited(), "Search should not go inside the current node.");
        msAssert.areSame(siblingRun, grandChildRun.getNextNonMarkupNodeLimited(), "Search should go up markup nodes.");
        
        Run grandGrandChildRun = new Run(doc, "grand grand child");
        StructuredDocumentTag grandChildTag = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        tagFirst.insertBefore(grandChildTag, null);
        msAssert.areSame(grandChildTag, tagFirst.getFirstChild());
        grandChildTag.insertBefore(grandGrandChildRun, null);
        msAssert.areSame(grandGrandChildRun, grandChildTag.getFirstChild());

        msAssert.areSame(grandChildRun, grandGrandChildRun.getNextNonMarkupNodeLimited(), "Search should go deep after going up.");

        Paragraph nextPar = (Paragraph) par.getParentNode().insertAfter(new Paragraph(doc), par);
        nextPar.insertBefore(new Run(doc, "test"), null);
        msAssert.isNull(siblingRun.getNextNonMarkupNodeLimited(), "Search should go up markup nodes only");
    }

    @Test
    public void testPreviousNonMarkupNode() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Paragraph par = builder.insertParagraph();

        StructuredDocumentTag tagFirst = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(tagFirst);
        msAssert.areSame(tagFirst, par.getFirstChild());
        msAssert.isNull(tagFirst.getPreviousNonMarkupNodeLimited(), "No siblings: should be null.");

        StructuredDocumentTag tagNext = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(tagNext);
        msAssert.areSame(tagNext, tagFirst.getNextSibling());
        msAssert.isNull(tagNext.getPreviousNonMarkupNodeLimited(), "No non-markup siblings: should be null.");

        Run firstRun = new Run(doc, "first Run");
        par.insertBefore(firstRun, tagFirst);
        msAssert.areSame(firstRun, par.getFirstChild());
        msAssert.areSame(firstRun, tagNext.getPreviousNonMarkupNodeLimited(), "Non-markup sibling not found.");

        Run lastRun = new Run(doc, "last run");
        par.insertAfter(lastRun, tagNext);
        msAssert.areSame(lastRun, tagNext.getNextSibling());

        Run grandChildRun = new Run(doc, "grandchild Run");
        tagNext.insertBefore(grandChildRun, null);
        msAssert.areSame(grandChildRun, tagNext.getFirstChild());
        msAssert.areSame(grandChildRun, lastRun.getPreviousNonMarkupNodeLimited(), "Depth-first traverse order should find deeper descendants of preceding children.");

        msAssert.areSame(firstRun, tagNext.getPreviousNonMarkupNodeLimited(), "Search should not go inside the current node.");
        msAssert.areSame(firstRun, grandChildRun.getPreviousNonMarkupNodeLimited(), "Search should go up markup nodes.");

        Run grandGrandChildRun = new Run(doc, "grand grand child");
        StructuredDocumentTag grandChildTag = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        tagFirst.insertBefore(grandChildTag, null);
        msAssert.areSame(grandChildTag, tagFirst.getFirstChild());
        grandChildTag.insertBefore(grandGrandChildRun, null);
        msAssert.areSame(grandGrandChildRun, grandChildTag.getFirstChild());

        msAssert.areSame(grandGrandChildRun, grandChildRun.getPreviousNonMarkupNodeLimited(), "Search should go deep after going up.");

        Paragraph nextPar = (Paragraph) par.getParentNode().insertAfter(new Paragraph(doc), par);
        Run nextParRun = new Run(doc, "next par run");
        nextPar.insertBefore(nextParRun, null);
        msAssert.areSame(nextParRun, nextPar.getFirstChild());
        msAssert.isNull(nextParRun.getPreviousNonMarkupNodeLimited(), "Search should go up markup nodes only");
    }

    @Test
    public void testLastNonMarkupDescendant() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Paragraph par = builder.insertParagraph();
        msAssert.isNull(par.getLastNonMarkupDescendant(), "Last descendant of an empty composite should be null.");

        StructuredDocumentTag tagFirst = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        StructuredDocumentTag tagNext = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(tagFirst);
        builder.insertNode(tagNext);
        msAssert.areSame(tagNext, tagFirst.getNextSibling());
        msAssert.isNull(par.getLastNonMarkupDescendant(), "Last non-markup descendant of a composite having only markups inside should be null.");

        Run textRun = new Run(doc, "text");
        par.insertBefore(textRun, tagFirst);
        msAssert.areSame(tagFirst, textRun.getNextSibling());
        msAssert.areSame(textRun, par.getLastNonMarkupDescendant(), "Immediate child which is not the last child should be found.");

        Run runInDepth = new Run(doc, "depth");
        tagFirst.insertBefore(runInDepth, null);
        msAssert.areSame(runInDepth, tagFirst.getFirstChild());
        msAssert.areSame(runInDepth, par.getLastNonMarkupDescendant(), "Depth-first traverse order should find deeper descendants of succeeding children.");

        Run lastRun = new Run(doc, "last run");
        par.insertAfter(lastRun, tagNext);
        msAssert.areSame(lastRun, tagNext.getNextSibling());
        msAssert.areSame(lastRun, par.getLastNonMarkupDescendant(), "Last non-markup should be found on upper levels despite deeper preceding nodes.");
    }

    /// <summary>
    /// WORDSNET-15251 GetChildNodes was returning extra nodes.
    /// When a root node is a markup, <see cref="NodeCollection.TraverseFlatSkipMarkupNodes"/> was returning
    /// root-sibling node on a last iteration.
    /// </summary>
    @Test
    public void testJira15251() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertNode(new Run(doc, "First run"));

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        builder.insertNode(sdt);
        sdt.appendChild(new Run(doc, "Inner run"));

        builder.insertNode(new Run(doc, "Last run"));

        NodeCollection runsInsideSdtBlock = sdt.getChildNodes(NodeType.RUN, false);
        Node firstChild = runsInsideSdtBlock.get(0); 

        msAssert.areEqual(1, runsInsideSdtBlock.getCount(), "It must be only one NodeType.Run descendant.");

        // Verify forward sibling traverse.
        msAssert.isNull(runsInsideSdtBlock.getNthMatchingNode(firstChild, 1));

        // Verify backward sibling traverse.
        msAssert.isNull(runsInsideSdtBlock.getNthMatchingNode(firstChild, -1));
    }

    /// <summary>
    /// Tests the <see cref="Node.NextNonMarkupCompositeLimited"/>,
    /// <see cref="Node.PreviousNonMarkupCompositeLimited"/> properties.
    /// </summary>
    @Test
    public void testNextNonMarkupCompositeLimited() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();

        Paragraph paragraph1 = new Paragraph(doc);
        body.appendChild(paragraph1);
        BookmarkStart bookmarkStart1 = new BookmarkStart(doc, "BM1");
        paragraph1.appendChild(bookmarkStart1);
        Run run1 = new Run(doc);
        paragraph1.appendChild(run1);

        BookmarkEnd bookmarkEnd1 = new BookmarkEnd(doc, "BM1");
        body.appendChild(bookmarkEnd1);

        StructuredDocumentTag sdt1 = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        body.appendChild(sdt1);
        Paragraph sdt1Paragraph1 = new Paragraph(doc);
        sdt1.appendChild(sdt1Paragraph1);
        Run run2 = new Run(doc);
        sdt1Paragraph1.appendChild(run2);
        BookmarkStart bookmarkStart2 = new BookmarkStart(doc, "BM2");
        sdt1.appendChild(bookmarkStart2);

        Paragraph paragraph2 = new Paragraph(doc);
        body.appendChild(paragraph2);
        Run run3 = new Run(doc);
        paragraph2.appendChild(run3);
        BookmarkEnd bookmarkEnd2 = new BookmarkEnd(doc, "BM2");
        paragraph2.appendChild(bookmarkEnd2);

        // Check with using NextNonMarkupNodeLimited.
        ArrayList<Node> nodeOrder = new ArrayList<Node>();
        Node node = paragraph1;
        while (node != null)
        {
            msArrayList.add(nodeOrder, node);
            node = node.getNextNonMarkupNodeLimited();
        }

        Node[] expectedOrder = new Node[] { paragraph1, bookmarkEnd1, sdt1Paragraph1, bookmarkStart2, paragraph2 };
        checkForSame(expectedOrder, nodeOrder, false);

        // Check with using PreviousNonMarkupNodeLimited.
        nodeOrder.clear();
        node = expectedOrder[expectedOrder.length - 1];
        while (nodeOrder.size() != expectedOrder.length)
        {
            msArrayList.add(nodeOrder, node);
            node = node.getPreviousNonMarkupNodeLimited();
        }

        checkForSame(expectedOrder, nodeOrder, true);

        // Test NextNonMarkupCompositeLimited
        nodeOrder.clear();
        node = paragraph1;
        while (node != null)
        {
            msArrayList.add(nodeOrder, node);
            node = node.getNextNonMarkupCompositeLimited();
        }

        expectedOrder = new Node[] { paragraph1, sdt1Paragraph1, paragraph2 };
        checkForSame(expectedOrder, nodeOrder, false);

        // Test PreviousNonMarkupCompositeLimited
        nodeOrder.clear();
        node = expectedOrder[expectedOrder.length - 1];
        while (nodeOrder.size() != expectedOrder.length)
        {
            msArrayList.add(nodeOrder, node);
            node = node.getPreviousNonMarkupCompositeLimited();
        }

        checkForSame(expectedOrder, nodeOrder, true);
    }

    /// <summary>
    /// Checks that same nodes are at same positions in the specified array and list.
    /// </summary>
    private static void checkForSame(Node[] expectedObjects, ArrayList<Node> actualObjects, boolean reverseOrder)
    {
        msAssert.areEqual(expectedObjects.length, actualObjects.size());
        for (int i = 0, j = expectedObjects.length - 1; i < expectedObjects.length; i++, j--)
            msAssert.areSame(expectedObjects[i], actualObjects.get(reverseOrder ? j : i));
    }

    /// <summary>
    /// Tests the <see cref="CompositeNode.FirstNonMarkupCompositeLimited"/>,
    /// <see cref="CompositeNode.LastNonMarkupCompositeLimited"/> properties.
    /// </summary>
    @Test
    public void testFirstNonMarkupCompositeLimited() throws Exception
    {
        Document doc = new Document();
        Body body = doc.getFirstSection().getBody();
        body.removeAllChildren();

        StructuredDocumentTag sdt1 = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        body.appendChild(sdt1);
        BookmarkStart bookmarkStart1 = new BookmarkStart(doc, "BM1");
        sdt1.appendChild(bookmarkStart1);
        Paragraph sdt1Paragraph1 = new Paragraph(doc);
        sdt1.appendChild(sdt1Paragraph1);
        Run run2 = new Run(doc);
        sdt1Paragraph1.appendChild(run2);
        BookmarkEnd bookmarkEnd1 = new BookmarkEnd(doc, "BM1");
        sdt1.appendChild(bookmarkEnd1);

        StructuredDocumentTag sdt2 = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        body.appendChild(sdt2);
        BookmarkStart bookmarkStart2 = new BookmarkStart(doc, "BM2");
        sdt2.appendChild(bookmarkStart2);

        BookmarkEnd bookmarkEnd2 = new BookmarkEnd(doc, "BM2");
        body.appendChild(bookmarkEnd2);

        msAssert.areSame(sdt1Paragraph1, sdt1.getFirstNonMarkupCompositeDescendant());
        msAssert.areSame(sdt1Paragraph1, sdt1.getLastNonMarkupCompositeDescendant());

        msAssert.isNull(sdt2.getFirstNonMarkupCompositeDescendant());
        msAssert.isNull(sdt2.getLastNonMarkupCompositeDescendant());

        msAssert.areSame(sdt1Paragraph1, body.getFirstNonMarkupCompositeDescendant());
        msAssert.areSame(sdt1Paragraph1, body.getLastNonMarkupCompositeDescendant());
    }
}

