// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Test that typed wrappers for nodes work.
/// </summary>
@Test
public class TestTypedNodeCollection
{
    @Test
    public void testAdd() throws Exception
    {
        Document doc = createTestDoc();
        msAssert.areEqual("1\r2\r3\u0007\u00074\f", doc.getText());
    }

    @Test
    public void testInsertAtStart() throws Exception
    {
        Document doc = createTestDoc();
        Body body = doc.getFirstSection().getBody();

        //Insert at the beginning
        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "0"));
        body.getParagraphs().insert(0, para);

        msAssert.areEqual("0\r1\r2\r3\u0007\u00074\f", doc.getText());
    }

    @Test
    public void testInsertInMiddle() throws Exception
    {
        Document doc = createTestDoc();
        Body body = doc.getFirstSection().getBody();

        //Insert in the middle and after table
        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "xxx"));
        body.getParagraphs().insert(2, para);

        msAssert.areEqual("1\r2\r3\u0007\u0007xxx\r4\f", doc.getText());
    }

    @Test
    public void testInsertAtEnd() throws Exception
    {
        Document doc = createTestDoc();
        Body body = doc.getFirstSection().getBody();
        
        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "5"));
        body.getParagraphs().insert(3, para);

        msAssert.areEqual("1\r2\r3\u0007\u00074\r5\f", doc.getText());
    }

    @Test
    public void testInsertOutOfRange() throws Exception
    {
        Document doc = createTestDoc();
        Body body = doc.getFirstSection().getBody();
        
        //Negative indexes are allowed, counted from the end. More than count - means adding to the end.
        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "xxx"));
        body.getParagraphs().insert(-9, para);

        //If index is more than count, add to the end.
        para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "yyy"));
        body.getParagraphs().insert(9, para);

        msAssert.areEqual("1\r2\r3\u0007\u00074\rxxx\ryyy\f", doc.getText());
    }

    @Test
    public void testClear() throws Exception
    {
        Document doc = createTestDoc();
        Body body = doc.getFirstSection().getBody();

        body.getParagraphs().clear();

        //Insert at zero into an empty collection means InsertBefore null and appends to the end.
        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "xxx"));
        body.getParagraphs().insert(0, para);    

        msAssert.areEqual("3\u0007\u0007xxx\f", doc.getText());
    }


    /// <summary>
    /// Creates the following document
    ///        Document
    ///            Section
    ///                Body
    ///                    Paragraph
    ///                        Run("1")
    ///                    Paragraph
    ///                        Run("2")
    ///                    Table
    ///                        Row
    ///                            Cell
    ///                                Paragraph
    ///                                    Run("3")
    ///                    Paragraph
    ///                        Run("4")
    /// </summary>
    private Document createTestDoc() throws Exception
    {
        Document doc = new Document();

        Body body = doc.getFirstSection().getBody();
        msAssert.areEqual(1, body.getParagraphs().getCount());
        body.getFirstParagraph().getRuns().add(new Run(doc, "1"));

        Paragraph para = new Paragraph(doc);
        para.getRuns().add(new Run(doc, "2"));
        body.getParagraphs().add(para);
        msAssert.areEqual(2, body.getParagraphs().getCount());
        msAssert.areEqual(0, body.getTables().getCount());

        Table table = new Table(doc);
        table.ensureMinimum();
        table.getFirstRow().getFirstCell().getFirstParagraph().getRuns().add(new Run(doc, "3"));
        //This is somewhat controversial (adding Table to Paragraphs), but allows to add to the children anyway.
        body.getParagraphs().add(table);
        msAssert.areEqual(2, body.getParagraphs().getCount());
        msAssert.areEqual(1, body.getTables().getCount());

        //TODO 2 Must ensure that section ends with a paragraph manually, make it automatic.
        para = new Paragraph(doc);
        body.getParagraphs().add(para);
        para.getRuns().add(new Run(doc, "4"));
        
        return doc;
    }
}

