// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// I perform some operations where the node should have same grand parent,
/// this covers most of situations with fields and bookmarks for me.
/// </summary>
@Test
public class TestGrandParent
{
    /// <summary>
    /// CompositeNode.GetTextFromSequence() was superseded by <see cref="NodeTextCollector.GetText()"/>.
    /// </summary>
    @Test
    public void testGetTextFromSequence() throws Exception
    {
        Document doc = createTestDoc();

        NodeList runs = doc.selectNodes("//Run");

        // Get text inside one parent
        // End node is excluded, therefore nothing.
        msAssert.areEqual("", NodeTextCollector.getText(runs.get(0), true, runs.get(0), false));
        msAssert.areEqual("[0][1]", NodeTextCollector.getText(runs.get(0), true, runs.get(2), false));

        // Get text across several parents
        msAssert.areEqual("[0][1][2]\r", NodeTextCollector.getText(runs.get(0), true, runs.get(3), false));
        msAssert.areEqual("[0][1][2]\r[3]", NodeTextCollector.getText(runs.get(0), true, runs.get(4), false));
        msAssert.areEqual("[0][1][2]\r[3][4][5]\r[6][7]", NodeTextCollector.getText(runs.get(0), true, runs.get(8), false));
    }

    @Test
    public void testRemoveWithCollapseInOneParent() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");

        // Remove one run in the second paragraph, together with the bookmark.
        NodeRemover.remove(runs.get(3), true, runs.get(4), false);            
        msAssert.areEqual("[4][5]\r", doc.selectSingleNode("//Paragraph[2]").getText());
        msAssert.areEqual(1, doc.selectNodes("//BookmarkStart").getCount());
    }

    @Test
    public void testRemoveWithCollapseInTwoParents() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");

        //This delete merge first and second paragraphs.
        NodeRemover.remove(runs.get(0), true, runs.get(4), false);            
        msAssert.areEqual(2, doc.selectNodes("//Paragraph").getCount());
        msAssert.areEqual("[4][5]\r", doc.selectSingleNode("//Paragraph[1]").getText());
        msAssert.areEqual("[6][7][8]\f", doc.selectSingleNode("//Paragraph[2]").getText());
        //The bookmark between the runs was deleted.
        msAssert.areEqual(1, doc.selectNodes("//BookmarkStart").getCount());
    }

    @Test
    public void testRemoveWithCollapseInThreeParents() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");

        //This will delete merge first and second paragraphs.
        NodeRemover.remove(runs.get(1), true, runs.get(8), false);            
        msAssert.areEqual(1, doc.selectNodes("//Paragraph").getCount());
        msAssert.areEqual("[0][8]\f", doc.selectSingleNode("//Paragraph[1]").getText());
        //All bookmarks including the one between paragraphs were deleted.
        msAssert.areEqual(0, doc.selectNodes("//BookmarkStart").getCount());
    }

    @Test
    public void testRemoveToEndOfAnotherParent() throws Exception
    {
        Document doc = createTestDoc();
        NodeList runs = doc.selectNodes("//Run");

        //Delete some from the first paragraph and till the end of the second paragraph.
        NodeRange range = new NodeRange(runs.get(1), true, runs.get(5).getParentNode(), true);
        range.remove();            
        
        msAssert.areEqual("[0]\r", doc.selectSingleNode("//Paragraph[1]").getText());
        msAssert.areEqual("[6][7][8]\f", doc.selectSingleNode("//Paragraph[2]").getText());
    }

    /// <summary>
    /// Creates the following doc
    /// Section
    ///        Body
    ///            Para 0
    ///                Run 0
    ///                Run 1
    ///                Run 2
    ///            Para 1
    ///                Run 3
    ///                BookmarkStart
    ///                Run 4
    ///                Run 5
    ///            Para 2
    ///                BookmarkStart
    ///                Run 6
    ///                Run 7
    ///                Run 8
    /// </summary>
    /// <returns></returns>
    private Document createTestDoc() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        Section sect = doc.appendChild(new Section(doc));
        Body body = sect.appendChild(new Body(doc));

        Paragraph p1 = body.appendChild(new Paragraph(doc));
        p1.appendChild(new Run(doc, "[0]"));
        p1.appendChild(new Run(doc, "[1]"));
        p1.appendChild(new Run(doc, "[2]"));

        Paragraph p2 = body.appendChild(new Paragraph(doc));
        p2.appendChild(new Run(doc, "[3]"));
        p2.appendChild(new BookmarkStart(doc, "bmk1"));
        p2.appendChild(new Run(doc, "[4]"));
        p2.appendChild(new Run(doc, "[5]"));

        Paragraph p3 = body.appendChild(new Paragraph(doc));
        p3.appendChild(new BookmarkStart(doc, "bmk2"));
        p3.appendChild(new Run(doc, "[6]"));
        p3.appendChild(new Run(doc, "[7]"));
        p3.appendChild(new Run(doc, "[8]"));

        return doc;
    }
}

