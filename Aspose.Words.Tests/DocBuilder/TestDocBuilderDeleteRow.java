// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDocBuilderDeleteRow
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testDeleteRow() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        createTwoRowTable(builder);

        Row row = builder.deleteRow(0, 0);
        msAssert.isNotNull(row);
        //This text will be appended to the end of the document because its where the cursor is now.
        builder.writeln("Text2");

        msAssert.areEqual(1, doc.selectNodes("//Row").getCount());
        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderDeleteRow.docx", null, false);
        msAssert.areEqual("Cell 2.1\u0007\u0007Text1\rText2\r\f", doc.getText());
    }

    /// <summary>
    /// Deleting a row when it contains the cursor should move the cursor to the next row.
    /// </summary>
    @Test
    public void testDeleteRowWithCursor() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        createTwoRowTable(builder);

        builder.moveToCell(0, 0, 0, 0);
        builder.deleteRow(0, 0);
        //The text should be inserted into the first cell of the second row as it is the
        //row after the deleted row.
        builder.writeln("Text2");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderDeleteRowWithCursor.docx", null, false);
        msAssert.areEqual("Text2\rCell 2.1\u0007\u0007Text1\r\f", doc.getText());
    }

    /// <summary>
    /// Deleting a last row in a table when it contains the cursor should move the cursor to beyond the table.
    /// </summary>
    @Test
    public void testDeleteLastRowWithCursor() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        createTwoRowTable(builder);

        builder.moveToCell(0, 1, 0, 0);
        builder.deleteRow(0, 1);
        //The text should be inserted after the table because the cursor should have moved to beyond the table.
        builder.writeln("Text2");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderDeleteLastRowWithCursor.docx", null, false);
        msAssert.areEqual("Cell 1.1\u0007\u0007Text2\rText1\r\f", doc.getText());
    }

    /// <summary>
    /// Deleting a row that has only one table should delete the whole table.
    /// </summary>
    @Test
    public void testDeleteSingleRow() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        createTwoRowTable(builder);

        builder.moveToCell(0, 0, 0, 0);
        builder.deleteRow(0, 0);
        builder.deleteRow(0, 0);
        builder.writeln("Text2");

        msAssert.areEqual(0, doc.selectNodes("//Table").getCount());
        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderDeleteLastRowWithCursor.docx", null, false);
        msAssert.areEqual("Text2\rText1\r\f", doc.getText());
    }

    private static void createTwoRowTable(DocumentBuilder builder)
    {
        builder.insertCell();
        builder.write("Cell 1.1");
        builder.endRow();
        builder.insertCell();
        builder.write("Cell 2.1");
        builder.endRow();
        builder.endTable();
        builder.writeln("Text1");
    }
}

