// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestWordUtil
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void paragraphCanBeRemovedTest() throws Exception
    {
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase1());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase2());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase3());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase4());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase5());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase6());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase7());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase8());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase9());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase10());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase11());
        paragraphCanBeRemovedTest(paragraphCanBeRemovedTestCase12());
    }

    private void paragraphCanBeRemovedTest(Paragraph para)
    {
        Node parent = para;
        while (parent != null &&
               parent.getNodeType() != NodeType.BODY &&
               parent.getNodeType() != NodeType.CELL &&
               parent.getNodeType() != NodeType.COMMENT &&
               parent.getNodeType() != NodeType.FOOTNOTE)
            parent = parent.getParentNode();

        parent = ms.as(parent, CompositeNode.class);
        if (parent == null)
            Assert.fail("parent not found");

        msAssert.areEqual(WordUtil.isAddParagraphNeeded((CompositeNode)parent), false);
        boolean canBeRemoved = WordUtil.paragraphCanBeRemoved(para);
        para.remove();
        msAssert.areEqual(!WordUtil.isAddParagraphNeeded((CompositeNode)parent), canBeRemoved);
    }

    private Paragraph paragraphCanBeRemovedTestCase1() throws Exception
    {
        mDoc = new Document();
        mDoc.getFirstSection().getBody().appendChild(new Paragraph(mDoc));
        return mDoc.getFirstSection().getBody().getParagraphs().get(0);
    }


    private Paragraph paragraphCanBeRemovedTestCase2() throws Exception
    {
        mDoc = new Document();
        mDoc.getFirstSection().getBody().appendChild(new Paragraph(mDoc));
        return mDoc.getFirstSection().getBody().getParagraphs().get(1);
    }


    private Paragraph paragraphCanBeRemovedTestCase3() throws Exception
    {
        mDoc = new Document();
        return mDoc.getFirstSection().getBody().getFirstParagraph();
    }


    private Paragraph paragraphCanBeRemovedTestCase4() throws Exception
    {
        mDoc = new Document();
        mDoc.getFirstSection().getBody().getFirstParagraph().remove();
        StructuredDocumentTag tag = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag);
        tag.appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("0.0.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase5() throws Exception
    {
        mDoc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag);
        tag.appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("0.1.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase6() throws Exception
    {
        mDoc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag);
        tag.appendChild(new Paragraph(mDoc));
        mDoc.getFirstSection().getBody().appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("2.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase7() throws Exception
    {
        mDoc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag);
        tag.appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("0.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase8() throws Exception
    {
        mDoc = new Document();
        StructuredDocumentTag tag1 = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag1);
        StructuredDocumentTag tag2 = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        tag1.appendChild(tag2);
        tag2.appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("0.0.1.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase9() throws Exception
    {
        mDoc = new Document();
        StructuredDocumentTag tag1 = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        mDoc.getFirstSection().getBody().appendChild(tag1);
        StructuredDocumentTag tag2 = new StructuredDocumentTag(mDoc, MarkupLevel.BLOCK);
        tag1.appendChild(tag2);
        tag2.appendChild(new Paragraph(mDoc));
        mDoc.getFirstSection().getBody().appendChild(new Paragraph(mDoc));
        return (Paragraph)mDoc.getNodeById("0.0.1.0.0");
    }

    private Paragraph paragraphCanBeRemovedTestCase10() throws Exception
    {
        mDoc = new Document();
        Table table = new Table(mDoc);
        mDoc.getFirstSection().getBody().appendChild(table);
        Row row = new Row(mDoc);
        table.appendChild(row);
        Cell cell = new Cell(mDoc);
        row.appendChild(cell);
        Paragraph para = new Paragraph(mDoc);
        cell.appendChild(para);
        return para;
    }

    private Paragraph paragraphCanBeRemovedTestCase11() throws Exception
    {
        mDoc = new Document();
        Table table = new Table(mDoc);
        mDoc.getFirstSection().getBody().appendChild(table);
        Row row = new Row(mDoc);
        table.appendChild(row);
        Cell cell = new Cell(mDoc);
        row.appendChild(cell);
        Paragraph para = new Paragraph(mDoc);
        cell.appendChild(para);
        cell.appendChild(new Paragraph(mDoc));
        return para;
    }

    private Paragraph paragraphCanBeRemovedTestCase12() throws Exception
    {
        mDoc = new Document();
        Table table = new Table(mDoc);
        mDoc.getFirstSection().getBody().appendChild(table);
        Row row = new Row(mDoc);
        table.appendChild(row);
        Cell cell = new Cell(mDoc);
        row.appendChild(cell);
        cell.appendChild(new Paragraph(mDoc));
        Paragraph para = new Paragraph(mDoc);
        cell.appendChild(para);
        return para;
    }

    // alexnosk: global variable is declared for C++ to make document available upon processing separate node.
    private Document mDoc;
}

