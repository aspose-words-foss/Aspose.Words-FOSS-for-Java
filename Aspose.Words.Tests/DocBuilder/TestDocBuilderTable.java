// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.ms.System.msString;
import com.aspose.DrColor;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test how creating tables using DocumentBuilder works.
/// </summary>
@Test
public class TestDocBuilderTable
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Creates a chess like table to test borders and shading.
    /// </summary>
    @Test
    public void testChess() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setSize(8.0);
        builder.getFont().setName("Tahoma");
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        builder.getRowFormat().setHeight(0.75 * 72.0);
        builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);

        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(0.75 * 72.0));
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.getCellFormat().setOrientation(TextOrientation.DOWNWARD);

        for (int y = 0; y < 8; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                builder.insertCell();
                builder.write(msString.format("X{0}, Y{1}", x, y));

                boolean isBlack = (((x + y) & 0x01) != 0);
                builder.getCellFormat().getShading().setBackgroundPatternColorInternal((isBlack) ? DrColor.LIGHT_GREEN : DrColor.LIGHT_YELLOW);
            }
            builder.endRow();
        }

        Table table = builder.endTable();
        table.setAllowAutoFit(false);
        table.setBorders(LineStyle.SINGLE, 2.0, Color.BLUE);


        builder.getParagraphFormat().clearFormatting();
        builder.getParagraphFormat().setKeepWithNext(true);
        builder.getParagraphFormat().setSpaceAfter(12.0);
        builder.getParagraphFormat().setSpaceBefore(11.0);
        builder.getFont().setName("Arial");
        builder.getFont().setBold(true);
        builder.getFont().setUnderline(Underline.THICK);
        builder.getFont().setSize(12.0);
        builder.writeln("blah blah");

        TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestChess.docx");
    }

    /// <summary>
    /// Some checks to the table structure and also checks
    /// that formatting we apply after the end of the table works.
    /// </summary>
    @Test
    public void testEndTable() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.write("InTable");
        builder.endRow();
        //This will be called implicitly if you don't call this, but sometimes it is better to call it.
        builder.endTable();
        builder.getParagraphFormat().setSpaceBefore(12.0);
        builder.write("OutsideTable");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestEndTable.docx", null, false);

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(2, paras.getCount());

        Paragraph p = (Paragraph)paras.get(0);
        msAssert.areEqual("InTable\u0007", p.getText());        //Cell
        msAssert.areEqual(true, p.isInCell());
        msAssert.areEqual(true, p.isEndOfCell());

        p = (Paragraph)paras.get(1);
        msAssert.areEqual("OutsideTable\f", p.getText());    //After table
        msAssert.areEqual(false, p.isInCell());
        msAssert.areEqual(false, p.isEndOfCell());
        msAssert.areEqual(12.0, p.getParagraphFormat().getSpaceBefore());
    }

    /// <summary>
    /// Test a variety of document builder methods and properties by creating a simple table.
    /// </summary>
    @Test
    public void testTable2x2() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Paragraph before table.");

        builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
        builder.getRowFormat().setHeight(24.0);
        Cell cell = builder.insertCell();
        msAssert.isNotNull(cell);
        builder.getCellFormat().setWidth(1 * 72);
        builder.writeln("Row1, Cell1");
        builder.insertCell();
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.getCellFormat().setWidth(1.5 * 72.0);
        builder.write("Row1, Cell2");
        builder.endRow();

        builder.getRowFormat().setHeightRule(HeightRule.AUTO);
        builder.insertCell();
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.TOP);
        builder.getCellFormat().setWidth(1 * 72);
        builder.write("Row2, Cell1");
        builder.insertCell();
        builder.getCellFormat().setWidth(1.5 * 72.0);
        builder.getFont().setColor(Color.RED);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        builder.write("Row2, Cell2");
        builder.endRow();

        Table table = builder.endTable();
        table.setAllowAutoFit(false);
        table.setPreferredWidth(PreferredWidth.AUTO);
        table.setLeftIndent(2 * 72);

        builder.getFont().clearFormatting();
        builder.writeln("Paragraph after table.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestTable2x2.docx");

        msAssert.areEqual("Paragraph before table.\r" +
                "Row1, Cell1\r\u0007Row1, Cell2\u0007\u0007Row2, Cell1\u0007Row2, Cell2\u0007\u0007" +
                "Paragraph after table.\r\f", doc.getSections().get(0).getBody().getText());

        Row row = (Row)doc.selectSingleNode("//Row[1]");
        TablePr tablePr = row.getTablePr();
        msAssert.areEqual(2, row.getCells().getCount());

        //Check cell widths.
        msAssert.areEqual(2 * 1440, tablePr.getLeftIndent());
        msAssert.areEqual(1 * 1440, row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual((int)(1.5 * 1440.0), row.getCells().get(1).getCellPr().getWidth());

        //Check borders are okay.
        msAssert.areEqual(null, row.getCells().get(0).getCellPr().getBorderTop());

        row = (Row)doc.selectSingleNode("//Row[2]");
        //Check left indent for the further rows remained okay, I saw it broken.
        msAssert.areEqual(2 * 1440, row.getTablePr().getLeftIndent());
    }

    /// <summary>
    /// If you try to start a table in the middle of a paragraph, it inserts a paragraph break.
    /// </summary>
    @Test
    public void testMidParagraph() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("Text before table.");
        builder.insertCell();
        builder.write("Cell1");
        builder.endRow();
        builder.insertCell();
        builder.write("Cell2");
        builder.endRow();
        builder.write("Text after table.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestMidParagraph.docx", null, false);

        msAssert.areEqual("Text before table.\r" +
                "Cell1\u0007\u0007Cell2\u0007\u0007" +
                "Text after table.\f", doc.getSections().get(0).getBody().getText());
    }

    @Test
    public void testAtDocumentStart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("Text after table.");
        builder.moveToDocumentStart();
        builder.insertCell();
        builder.write("Cell1");
        builder.endRow();
        builder.endTable();

        builder.moveToDocumentStart();
        builder.write("This gets into the first cell.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestAtDocumentStart.docx", null, false);

        msAssert.areEqual("This gets into the first cell.Cell1\u0007\u0007" +
                "Text after table.\f", doc.getSections().get(0).getBody().getText());
    }

    @Test
    public void testHorizontalMerge() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        builder.write("Text in the merged cells.");

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.NONE);
        builder.write("Text in one cell.");

        builder.insertCell();
        builder.write("Text in another cell.");
        builder.endRow();

        // There was a roundtrip via DOC here. In the FOSS version it is removed because DOCX normalizes horizontal merge out.

        Row row = (Row)doc.selectSingleNode("//Row");

        CellPr cellPr = row.getCells().get(0).getCellPr();
        msAssert.areEqual(CellMerge.FIRST, cellPr.getHorizontalMerge());

        cellPr = row.getCells().get(1).getCellPr();
        msAssert.areEqual(CellMerge.PREVIOUS, cellPr.getHorizontalMerge());
    }

    @Test
    public void testVerticalMerge() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
        builder.write("Text in the merged cells.");

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.write("Text in one cell");
        builder.endRow();

        builder.insertCell();
        //This cell is vertically merged to the cell above and shoudl be empty.
        builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.write("Text in another cell");
        builder.endRow();

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestVerticalMerge.docx", null, false);

        Row row = (Row)doc.selectSingleNode("//Row[1]");
        msAssert.areEqual(CellMerge.FIRST, row.getCells().get(0).getCellPr().getVerticalMerge());

        row = (Row)doc.selectSingleNode("//Row[2]");
        msAssert.areEqual(CellMerge.PREVIOUS, row.getCells().get(0).getCellPr().getVerticalMerge());
    }

    /// <summary>
    /// WORDSNET-8866 Word 2003 eats 100% of CPU and hangs during opening document produced by Aspose.Words.
    /// </summary>
    @Test
    public void testVerticalMergeInLastRow() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (int i = 0; i < 44; ++i)
            builder.writeln("line " + Integer.toString(i));

        builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getCellFormat().getBorders().setLineWidth(1.0);
        builder.startTable();

        builder.insertCell();
        // RK This start of a vertical merge in the last row can cause Word 2003 to fail.
        builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);

        //table in table
        builder.startTable();
        builder.insertCell();
        builder.getCellFormat().clearFormatting();
        builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getCellFormat().getBorders().setColor(Color.BLACK);
        builder.getCellFormat().getBorders().setLineWidth(1.0);
        builder.write("celA");
        builder.endRow();

        builder.insertCell();
        builder.write("celB");
        builder.endRow();

        builder.insertCell();
        builder.write("celC");
        builder.endRow();

        builder.insertCell();
        builder.write("celD");
        builder.endRow();
        builder.endTable();
        //end table in table

        builder.endRow();
        builder.endTable();

        builder.write("End doc");

        //new table
        builder.startTable();
        builder.getCellFormat().clearFormatting();
        builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getCellFormat().getBorders().setLineWidth(1.0);
        builder.insertCell();
        builder.write("celX");
        builder.insertCell();
        builder.write("celY");
        builder.insertCell();
        builder.write("celZ");
        builder.endRow();
        builder.endTable();

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestVerticalMergeInLastRow.docx", null, false);

        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        msAssert.areEqual(CellMerge.NONE, cell.getCellFormat().getVerticalMerge());
    }

    /// <summary>
    /// Cannot insert section breaks etc while building a table.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testFailBreak() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.writeln("Cell1");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
    }

    /// <summary>
    /// Tests inserting a row into existing table. See comments below.
    /// </summary>
    @Test
    public void testAddRow() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create a 2x2 table. Imagine like this is the table you have in the template.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(2.5 * 72.0));
        builder.write("Cell1.1");
        builder.insertCell();
        builder.write("Cell1.2");
        builder.endRow();
        builder.insertCell();
        builder.write("Cell2.1");
        builder.insertCell();
        builder.write("Cell2.2");
        builder.endRow();
        builder.endTable();

        // Insert a new row between the first two rows. See comments below.
        builder.moveToCell(0, 1, 0, 0);
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(1 * 72));
        builder.write("CellX.1");
        builder.insertCell();
        builder.write("CellX.2");
        builder.endRow();

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestAddRow.docx", null, false);

        NodeList rows = doc.selectNodes("//Row");
        msAssert.areEqual(3, rows.getCount());

        //Due to a "defect" in the old code this was working "correctly" inserting a new row
        //into an existing table. But I after some refactorings (after version 2.4.1) this "fixed"
        //the original defect, but broke the ability to insert rows into existing tables.
        //Now it inserts a new nested table into a cell.
        msAssert.areEqual(2, doc.selectNodes("//Table").getCount());

        Row row = (Row)rows.get(0);
        msAssert.areEqual("Cell1.1\u0007Cell1.2\u0007\u0007", row.getText());

        row = (Row)rows.get(1);
        msAssert.areEqual("CellX.1\u0007CellX.2\u0007\u0007Cell2.1\u0007" +    //The nested table got into this cell.
                "Cell2.2\u0007\u0007", row.getText());
    }

    @Test
    public void testCreateNested() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table1 = builder.startTable();
        msAssert.isNotNull(table1);

        builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);
        builder.getRowFormat().setHeight(32.0);
        builder.getRowFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getRowFormat().getBorders().setLineWidth(0.75);
        builder.getRowFormat().getBorders().setColor(DrColor.BLACK.toNativeColor());

        builder.getCellFormat().setWidth(4 * 72);
        builder.insertCell();

            builder.startTable();
            builder.getRowFormat().getBorders().setLineStyle(LineStyle.SINGLE);
            builder.getRowFormat().getBorders().setLineWidth(0.75);
            builder.getRowFormat().getBorders().setColor(DrColor.RED.toNativeColor());

            builder.getCellFormat().setWidth(3 * 72);
            builder.getRowFormat().setHeight(16.0);
            builder.insertCell();
            builder.writeln("Nested");
            builder.endRow();
            builder.endTable();

        Row row = builder.endRow();
        msAssert.isNotNull(row);

        Table table2 = builder.endTable();
        msAssert.areEqual(table1, table2);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestCreateNested.docx", null, false);

        //Nested cells and rows end with \r while 1st level table ends with \x0007.
        msAssert.areEqual("Nested\r\u0007\u0007\u0007\u0007\f", doc.getText());

        NodeList rows = doc.selectNodes("//Row");

        //Check cell and row attributes where properly applied.

        row = (Row)rows.get(0);
        msAssert.areEqual(4 * 72 * 20, row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual(32 * 20, row.getTablePr().getHeight());

        row = (Row)rows.get(1);
        msAssert.areEqual(3 * 72 * 20, row.getCells().get(0).getCellPr().getWidth());
        msAssert.areEqual(16 * 20, row.getTablePr().getHeight());
    }

    /// <summary>
    /// Moves to a cell in existing table and changes cell and row format.
    /// </summary>
    @Test
    public void testChangeExisting() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create the table
        builder.getRowFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getRowFormat().getBorders().setLineWidth(0.75);
        builder.getRowFormat().getBorders().setColor(DrColor.BLACK.toNativeColor());
        builder.insertCell();
        builder.insertCell();
        builder.endRow();
        builder.insertCell();
        builder.insertCell();
        builder.endRow();
        builder.endTable();

        builder.moveToCell(0, 0, 0, 0);
        builder.write("Red");
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.RED);
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(2 * 72));

        builder.moveToCell(0, 0, 1, 0);
        builder.write("Blue");
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.BLUE);
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(3 * 72));

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestChangeExisting.docx", null, false);

        NodeList rows = doc.selectNodes("//Row");

        // Check cell formatting was changed.
        Row row = (Row)rows.get(0);

        CellPr cellPr = row.getCells().get(0).getCellPr();
        msAssert.areEqual(Color.RED.getRGB(), cellPr.getShading().getBackgroundPatternColor().getRGB());
        msAssert.areEqual(2 * 1440, cellPr.getPreferredWidth().getValueTwips());

        cellPr = row.getCells().get(1).getCellPr();
        msAssert.areEqual(Color.BLUE.getRGB(), cellPr.getShading().getBackgroundPatternColor().getRGB());
        msAssert.areEqual(3 * 1440, cellPr.getPreferredWidth().getValueTwips());

        // Check cells in this row were not changed.
        row = (Row)rows.get(1);
        msAssert.areEqual(PreferredWidth.AUTO, row.getCells().get(0).getCellPr().getPreferredWidth());
        msAssert.areEqual(PreferredWidth.AUTO, row.getCells().get(1).getCellPr().getPreferredWidth());
    }

    /// <summary>
    /// Paragraph alignment in cells seems to be screwed on Word 2000.
    /// </summary>
    @Test
    public void testAlign2000() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        builder.write("Right");
        builder.endRow();
        builder.endTable();

        TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestAlign2000.docx", null, false);
    }

    /// <summary>
    /// Test that we can keep rows on the same page.
    /// </summary>
    @Test
    public void testKeepRows() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (int i = 0; i < 45; i++)
            builder.insertParagraph();

        builder.insertCell();
        builder.getParagraphFormat().setKeepWithNext(true);
        builder.write("Row 1");
        builder.endRow();
        builder.insertCell();
        builder.getParagraphFormat().setKeepWithNext(false);
        builder.write("Row 2");
        builder.endRow();
        builder.endTable();
        TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestKeepRows.docx", null, false);
        //There is really no check here.
    }

    @Test
    public void testCellPadding() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();
        builder.insertCell();
        builder.write("Hello");
        builder.endRow();
        builder.endTable();
        TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestCellPadding.docx");

        Row row = (Row)doc.selectSingleNode("//Row");
        TablePr tablePr = row.getTablePr();
        msAssert.areEqual(108, tablePr.getLeftPadding());
        msAssert.areEqual(108, tablePr.getRightPadding());
        msAssert.areEqual(0, tablePr.getTopPadding());
        msAssert.areEqual(0, tablePr.getBottomPadding());
    }

    /// <summary>
    /// After inserting text into a cell with preferred width set, all further created cells had that width.
    /// </summary>
    @Test
    public void testCellWidthAfterDetach() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Table\\TestCellWidthAfterDetach.docx");

        // Skip table width validation in the document.
        doc.getDocPr().CompatibilityOptions.setGrowAutofit(true);

        DocumentBuilder builder = new DocumentBuilder(doc);

        // This position is inside a table, inside a cell that has preferred width 4cm set.
        // DocumentBuilder.CellFormat becomes "bound" to the current cell.
        builder.moveToMergeField("F1");
        builder.write("XXX");

        // Now, when we move the cursor out of that table, the CellFormat is "unbound" from the cell,
        // but it still was containing its original formatting, including the 4cm preferred width.
        // When the code below set only CellFormtat.Width, MS Word or the table layout algorithm would calculate the width
        // according to the preferred width. The new cells would be 4cm instead of 4" and 3". This would be confusing to the user.
        //
        // The I made was in DocumentBuilder not to clone CellPr and RowPr when the cursor is moved, but create new ones.
        builder.moveToMergeField("F2");

        builder.startTable();
        builder.getRowFormat().getBorders().setLineStyle(LineStyle.DOUBLE);
        builder.getRowFormat().getBorders().setLineWidth(0.75);
        builder.getRowFormat().getBorders().setColor(DrColor.BLACK.toNativeColor());
        builder.insertCell();
        builder.getCellFormat().setWidth(4 * 72);
        builder.write("4 inch cell");
        builder.insertCell();
        builder.getCellFormat().setWidth(3 * 72);
        builder.write("3 inch cell");
        builder.endRow();
        builder.endTable();

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestCellWidthAfterDetach.docx", null, false);

        Row row = (Row)doc.getChild(NodeType.ROW, 1, true);
        msAssert.areEqual(2, row.getCells().getCount());

        CellPr cellPr = row.getCells().get(0).getCellPr();
        msAssert.areEqual(4 * 72 * 20, cellPr.getWidth());
        msAssert.isTrue(cellPr.getPreferredWidth().isFixed());
        msAssert.areEqual(4 * 72 * 20, cellPr.getPreferredWidth().getValueTwips());

        cellPr = row.getCells().get(1).getCellPr();
        msAssert.areEqual(3 * 72 * 20, cellPr.getWidth());
        msAssert.isTrue(cellPr.getPreferredWidth().isFixed());
        msAssert.areEqual(3 * 72 * 20, cellPr.getPreferredWidth().getValueTwips());
    }

    /// <summary>
    /// This method was introduced just because one of the customers really needed
    /// to produce tables with zero margins.
    /// </summary>
    @Test
    public void testClearCellMargins() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.write("Cell1");
        builder.insertCell();
        builder.write("Cell2");
        builder.endRow();

        Table table = builder.endTable();
        table.setLeftPadding(0.0);
        table.setRightPadding(0.0);
        table.setTopPadding(0.0);
        table.setBottomPadding(0.0);

        TestUtil.save(doc, "DocBuilder\\Table\\TestCellMargins.docx", null, false);

        Row row = (Row)doc.selectSingleNode("//Row");
        TablePr tablePr = row.getTablePr();
        msAssert.areEqual(0, tablePr.getLeftPadding());
        msAssert.areEqual(0, tablePr.getRightPadding());
        msAssert.areEqual(0, tablePr.getTopPadding());
        msAssert.areEqual(0, tablePr.getBottomPadding());
    }

    /// <summary>
    /// Testing to use default borders.
    /// </summary>
    @Test
    public void testDefaultBorders() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        //Set all borders to 3 pt
        builder.getRowFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getRowFormat().getBorders().setLineWidth(3.0);

        //Set intra cell borders to 1pt
        builder.getRowFormat().getBorders().getByBorderType(BorderType.HORIZONTAL).setLineWidth(1.0);
        builder.getRowFormat().getBorders().getByBorderType(BorderType.VERTICAL).setLineWidth(1.0);

        builder.insertCell();

        builder.write("A");
        builder.insertCell();
        builder.write("B");
        builder.endRow();
        builder.insertCell();
        builder.write("C");
        builder.insertCell();
        builder.write("D");
        builder.endRow();
        builder.endTable();

        TestUtil.save(doc, "DocBuilder\\Table\\TestDefaultBorders.docx", null, false);

        //Check top left cell
        Row row = (Row)doc.selectSingleNode("//Row");
        Cell cell = (Cell)row.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(3.0, cell.getCellFormat().getBorders().getByBorderType(BorderType.TOP).getLineWidth());
        msAssert.areEqual(3.0, cell.getCellFormat().getBorders().getByBorderType(BorderType.LEFT).getLineWidth());
        msAssert.areEqual(1.0, cell.getCellFormat().getBorders().getByBorderType(BorderType.BOTTOM).getLineWidth());
        msAssert.areEqual(1.0, cell.getCellFormat().getBorders().getByBorderType(BorderType.RIGHT).getLineWidth());
    }

    /// <summary>
    /// WORDSNET-905 Generated document hangs up in Word 2000.
    /// The problem was caused by first cell in a row being merged to previous.
    /// </summary>
    @Test
    public void testDefect905() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        builder.write("A");
        builder.insertCell();
        builder.write("B");
        builder.endRow();
        builder.endTable();

        // The first cell is created as merged to previous by the user.
        Cell cell = (Cell)doc.getChild(NodeType.CELL, 0, true);
        msAssert.areEqual(CellMerge.PREVIOUS, cell.getCellFormat().getHorizontalMerge());

        // Roundtrip in the FOSS version is removed because DOCX normalizes horizontal merge out.
    }

    @Test
    public void testDefaultCellWidth() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Cell cell = builder.insertCell();
        msAssert.areEqual(0, cell.getCellFormat().getWidth());
        msAssert.areEqual(PreferredWidth.AUTO, cell.getCellFormat().getPreferredWidth());
    }

    @Test
    public void testMoveToCellStart() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToCell.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToCell(0, 1, 1, 0);
        builder.write("Inserted text.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestMoveToCellStart.docx", null, false);
        msAssert.areEqual("\u0007\u0007\u0007" +
                "Existing text.\u0007Inserted text.\u0007\u0007\f", doc.getSections().get(0).getBody().getText());
    }

    @Test
    public void testMoveToCellEnd() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToCell.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToCell(0, 0, 0, -1);
        builder.write("Inserted text 1.");
        builder.moveToCell(0, 1, 0, -1);
        builder.write(" Inserted text 2.");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestDocBuilderMoveToCellEnd.docx", null, false);
        msAssert.areEqual("Inserted text 1.\u0007\u0007\u0007" +
                "Existing text. Inserted text 2.\u0007\u0007\u0007\f", doc.getSections().get(0).getBody().getText());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testMoveToCellBeyondDoc() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\TestMoveToCell.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToCell(0, 0, 2, 0);
    }

    @Test
    public void testCreateTableAndMoveToCell() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.write("Cell 1");
        builder.insertCell();
        builder.endRow();

        builder.write("Between tables");

        builder.insertCell();
        builder.write("Cell 3");
        builder.endRow();
        builder.endTable();

        builder.moveToCell(0, 0, 1, 0);
        builder.write("Cell 2");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\TestCreateTableAndMoveToCell.docx", null, false);
        msAssert.areEqual("Cell 1\u0007Cell 2\u0007\u0007Between tables\rCell 3\u0007\u0007\f", doc.getSections().get(0).getBody().getText());
    }

    @Test
    public void testDefect26962() throws Exception
    {
        // Create empty document and DocumentBuilder object.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Configure DocumentBuilder
        builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        builder.getCellFormat().getBorders().setColor(Color.BLACK);

        // Build rows with merged cells in various combination
        for (int i = 0; i < G_MERGES.length/6; i++)
            buildTestMergedRow(builder, 9, 40, G_MERGES[i][0], G_MERGES[i][1], G_MERGES[i][2], G_MERGES[i][3], G_MERGES[i][4], G_MERGES[i][5]);
        builder.endTable();

        // Roundtrip via DOC was here and it worked before FOSS.
        // In the FOSS version roundtrip via DOCX normalizes the horizontal merges and therefore
        // produces a different result. Therefore rountrip is removed.

        // Verify all read merged cell ranges.
        Table table = (Table)doc.getChildNodes(NodeType.TABLE, true).get(0);
        for (int i = 0; i < G_MERGES.length/6; i++)
            verifyTestMergedRow(table.getRows().get(i), G_MERGES[i][0], G_MERGES[i][1], G_MERGES[i][2], G_MERGES[i][3], G_MERGES[i][4], G_MERGES[i][5]);
    }

    /// <summary>
    /// Utility procedure, builds row with merged cells. Used in TestDefect26962.
    /// AM. Don't like to implement classes and collection for cell ranges so use simple argument set to define merged cell ranges.
    /// (first1-last1), (first2-last2), (first3-last3) argument define merged ranges.
    /// Think three ranges are enough to test various combinations.
    /// </summary>
    private static void buildTestMergedRow(DocumentBuilder builder, int count, int width, int first1, int last1, int first2, int last2, int first3, int last3)
    {
        for (int i = 0; i < count; i++)
        {
            builder.insertCell();
            builder.getCellFormat().setWidth(width);

            if (i == first1 || i == first2 || i == first3)
            {
                builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
                builder.write(msString.format("Merged cells ({0}:{1})", i, (i == first1) ? last1 : ((i == first2 ? last2 : last3))));
            }
            else if (
                (first1 < i && i <= last1) ||
                (first2 < i && i <= last2) ||
                (first3 < i && i <= last3))
            {
                builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
            }
            else
            {
                builder.getCellFormat().setHorizontalMerge(CellMerge.NONE);
                builder.write(Integer.toString(i));
            }
        }

        builder.endRow();
    }

    /// <summary>
    /// Utility procedure, verifies row with merged cells. Used in TestDefect26962.
    /// AM. Argument meaning is almost the same as for BuildTestMergedRow().
    /// </summary>
    private static void verifyTestMergedRow(Row row, int first1, int last1, int first2, int last2, int first3, int last3)
    {
        for (int i = 0; i < row.getCells().getCount(); i++)
        {
            if (i == first1 || i == first2 || i == first3)
                msAssert.areEqual(CellMerge.FIRST, row.getCells().get(i).getCellFormat().getHorizontalMerge());
            else if (
                (first1 < i && i <= last1) ||
                (first2 < i && i <= last2) ||
                (first3 < i && i <= last3))
                msAssert.areEqual(CellMerge.PREVIOUS, row.getCells().get(i).getCellFormat().getHorizontalMerge());
            else
                msAssert.areEqual(CellMerge.NONE, row.getCells().get(i).getCellFormat().getHorizontalMerge());
        }
    }

    /// <summary>
    /// Defines merged cells combination for TestDefect26962.
    /// </summary>
    private static /*final*/ int[][] G_MERGES = new int[][]
    {
        { -1, -1, -1, -1, -1, -1 },
        {  0,  1, -1, -1, -1, -1 },
        {  0,  7, -1, -1, -1, -1 },
        {  0,  8, -1, -1, -1, -1 },
        {  0,  2,  4,  6, -1, -1 },
        {  1,  2,  4,  5, -1, -1 },
        {  2,  4,  6,  7, -1, -1 },
        {  0,  1,  3,  4,  6,  7 },
        {  4,  7, -1, -1, -1, -1 },
        {  0,  1,  2,  3,  4,  5 },
        {  4,  8, -1, -1, -1, -1 }
    };


    /// <summary>
    /// Checks that ClearFormatting does not clear Width and PreferredWidth and ClearAllFormatting clears all.
    /// </summary>
    @Test
    public void testCellFormatClearFormattig() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        msAssert.areEqual(0, builder.getCellPrCopy().getCount());

        // Set 3 attributes.
        builder.getCellFormat().setLeftPadding(3.0);
        builder.getCellFormat().setWidth(100.0);
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100.0));

        msAssert.areEqual(3, builder.getCellPrCopy().getCount());

        builder.getCellFormat().clearFormatting();
        // Width and PreferredWidth are not cleared.
        msAssert.areEqual(2, builder.getCellPrCopy().getCount());
        msAssert.areEqual(100, builder.getCellFormat().getWidth());
        msAssert.areEqual(PreferredWidth.fromPercent(100.0), builder.getCellFormat().getPreferredWidth());

        builder.getCellFormat().clearAllFormatting();
        msAssert.areEqual(0, builder.getCellPrCopy().getCount());
    }

    /// <summary>
    /// Checks that ClearFormatting actually reverts to the default attributes.
    /// </summary>
    @Test
    public void testRowFormatClearFormatting() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // RowFormat properties are set by default.
        TablePr defaultTablePr = TablePr.createMSWordLooking();
        msAssert.areEqual(defaultTablePr.getCount(), builder.getTablePrCopy().getCount());
        RowFormat  builderRowFormat = builder.getRowFormat();

        msAssert.areEqual(defaultTablePr.getLeftPadding(), getAttrTwips(builder, TableAttr.LEFT_PADDING));
        msAssert.isTrue(getAttrTwips(builder, TableAttr.LEFT_PADDING) > 0d);
        msAssert.areEqual(defaultTablePr.getRightPadding(), getAttrTwips(builder, TableAttr.RIGHT_PADDING));
        msAssert.isTrue(getAttrTwips(builder, TableAttr.RIGHT_PADDING) > 0d);
        msAssert.areEqual(0d, getAttrTwips(builder, TableAttr.LEFT_INDENT));

        // Reset row formatting to the default.
        builderRowFormat.clearFormatting();
        msAssert.areEqual(defaultTablePr.getLeftPadding(), getAttrTwips(builder, TableAttr.LEFT_PADDING));
        msAssert.areEqual(defaultTablePr.getCount(), builder.getTablePrCopy().getCount());
        msAssert.areEqual(0d, getAttrTwips(builder, TableAttr.LEFT_INDENT));

        // Clear all attributes
        ((IRowAttrSource)builder).clearRowAttrs();
        msAssert.areEqual(0, builder.getTablePrCopy().getCount());
    }

    /// <summary>
    /// WORDSNET-9141 RowFormat.HeadingFormat does not repeat table header row on every page.
    /// DocumentBuilder copies first row properties to every row when row is started.
    /// Customer sets RowFormat.HeadingFormat to false right after row is ended but before row is started.
    /// </summary>
    @Test
    public void testJira9141() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.getRowFormat().setHeadingFormat(true);
        builder.insertCell();
        builder.writeln("Heading row");
        builder.endRow();

        builder.getRowFormat().setHeadingFormat(false);

        for (int i = 0; i < 50; i++)
        {
            builder.insertCell();
            builder.write("Column Text");
            builder.endRow();
        }

        msAssert.areEqual(51, table.getRows().getCount());
        msAssert.areEqual(true, table.getRows().get(0).getTablePr().get(TableAttr.HEADING_FORMAT));

        for (int i = 1; i < 51; i++)
            msAssert.areEqual(false, table.getRows().get(i).getTablePr().get(TableAttr.HEADING_FORMAT));
    }

    /// <summary>
    /// WORDSNET-10543 Document.Save method throws System.NullReferenceException.
    /// The table was started, but cells and rows was not added.
    /// So, Table.FirstRow threw an exception in DocumentValidator.IsNextSiblingAjancentTable().
    /// </summary>
    @Test
    public void testJira10543() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder docBuilder = new DocumentBuilder(doc);

        // This a customer's scenario: two adjacent tables and one of them hasn't any rows and cells.

        // First empty table
        docBuilder.startTable();
        docBuilder.endTable();
        // Next adjacent table
        docBuilder.startTable();
        docBuilder.insertCell();
        docBuilder.write("abc");
        docBuilder.endTable();

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Table\\TestJira10543", UnifiedScenario.DOCX_2_DOCX| UnifiedScenario.NO_GOLD);

        // Checks that tables were successfully saved. The empty one treated as non-adjacent and had to be removed upon saving.
        // So checks, that there is only one table and it has only one row.
        NodeCollection tables = doc.getChildNodes(NodeType.TABLE, true);
        msAssert.areEqual(1, tables.getCount());
        msAssert.areEqual(1, ((Table)tables.get(0)).getRows().getCount());
    }

    /// <summary>
    /// WORDSNET-13480 Formatting in CellFormat.Borders changes after read-only access.
    /// Incorrect setting of border's parent caused a problem when working with DocumentBuilder.
    /// </summary>
    @Test
    public void testJira13480() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // We are not inside any cell yet.
        checkLineStyle(builder.getCellFormat(), LineStyle.NONE);

        Cell cell1 = builder.insertCell();

        // We are inside a cell now.
        checkLineStyle(builder.getCellFormat(), LineStyle.SINGLE);
        checkLineStyle(cell1.getCellFormat(), LineStyle.SINGLE);

        // Go out from table.
        builder.moveToDocumentEnd();

        // We are not in any cell again.
        checkLineStyle(builder.getCellFormat(), LineStyle.NONE);
        // Cell's LineStyle must not be changed.
        checkLineStyle(cell1.getCellFormat(), LineStyle.SINGLE);

        // Change global LineStyle to something but not default.
        builder.getCellFormat().getBorders().setLineStyle(LineStyle.DOT);

        // Go to inside cell again.
        builder.moveTo(cell1.getFirstParagraph());

        // We are inside existing cell and we changed builder's LineStyle outside of this cell.
        // So, LineStyle of this cell must stay unchanged.
        checkLineStyle(builder.getCellFormat(), LineStyle.SINGLE);
        checkLineStyle(cell1.getCellFormat(), LineStyle.SINGLE);

        // Inserting new cell when we are inside another cell will rewrite global LineStyle.
        Cell cell2 = builder.insertCell();
        checkLineStyle(cell2.getCellFormat(), LineStyle.SINGLE);

        // Change builder's LineStyle and it affects current cell, but not previously created cell.
        builder.getCellFormat().getBorders().setLineStyle(LineStyle.DOT_DASH);
        checkLineStyle(builder.getCellFormat(), LineStyle.DOT_DASH);
        checkLineStyle(cell2.getCellFormat(), LineStyle.DOT_DASH);

        // First cell must stay unchanged.
        checkLineStyle(cell1.getCellFormat(), LineStyle.SINGLE);

        // Go out from table.
        builder.moveToDocumentEnd();
        // Builder's LineStyle now is global and it was rewritten when new cell was inserted.
        checkLineStyle(builder.getCellFormat(), LineStyle.SINGLE);

        builder.getCellFormat().getBorders().setLineStyle(LineStyle.DOUBLE_WAVE);
        // LineStyle of existing cells must not be changed.
        checkLineStyle(cell1.getCellFormat(), LineStyle.SINGLE);
        checkLineStyle(cell2.getCellFormat(), LineStyle.DOT_DASH);

        // This will create new table. Builder's global LineStyle should affect this table.
        Cell cell3 = builder.insertCell();
        checkLineStyle(cell3.getCellFormat(), LineStyle.DOUBLE_WAVE);
    }

    /// <summary>
    /// WORDSNET-15014 Aspose.Words Objects are very verbose.
    /// SetPaddings method was added.
    /// </summary>
    @Test
    public void testJira15014() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // Set cell paddings.
        builder.getCellFormat().setPaddings(30.0, 40.0, 50.0, 60.0);

        // Check paddings are set.
        msAssert.areEqual(30, builder.getCellFormat().getLeftPadding());
        msAssert.areEqual(40, builder.getCellFormat().getTopPadding());
        msAssert.areEqual(50, builder.getCellFormat().getRightPadding());
        msAssert.areEqual(60, builder.getCellFormat().getBottomPadding());
    }

    /// <summary>
    /// WORDSNET-16216 Issue with vertical merging in table while saving as DOCX when AllowAutoFit is false.
    /// Fix vertical merging of cells if table has AllowAutoFit = false and
    /// before cell with VerticalMerge exist cell with horizontal merge.
    /// </summary>
    @Test
    public void testJira16216() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Table tbl = builder.startTable();
        insertCell(builder, "row 1 cell 1", CellMerge.FIRST, CellMerge.NONE);
        insertCell(builder, "row 1 cell 2", CellMerge.NONE, CellMerge.NONE);
        insertCell(builder, "row 1 cell 3", CellMerge.FIRST, CellMerge.NONE);
        insertCell(builder, "row 1 cell 4", CellMerge.NONE, CellMerge.NONE);
        builder.endRow();

        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE);
        insertCell(builder, "row 2 cell 2", CellMerge.NONE, CellMerge.NONE);
        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE);
        insertCell(builder, "row 2 cell 4", CellMerge.NONE, CellMerge.NONE);
        builder.endRow();

        insertCell(builder, "row 3 cell 1", CellMerge.NONE, CellMerge.FIRST);
        insertCell(builder, "", CellMerge.NONE, CellMerge.PREVIOUS);
        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE);
        insertCell(builder, "row 3 cell 4", CellMerge.NONE, CellMerge.NONE);
        builder.endRow();

        tbl.setBorders(LineStyle.SINGLE, 1.0, Color.BLACK);
        tbl.setPreferredWidth(PreferredWidth.fromPercent(100.0));
        tbl.setAllowAutoFit(false);
        builder.endTable();

        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        // Cells from first row must be merged with next rows
        tbl = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(CellMerge.FIRST, tbl.getRows().get(0).getCells().get(2).getCellPr().getVerticalMerge());
        msAssert.areEqual(CellMerge.PREVIOUS, tbl.getRows().get(1).getFirstCell().getCellPr().getVerticalMerge());
        msAssert.areEqual(3, tbl.getRows().get(2).getCells().getCount());
        msAssert.areEqual(CellMerge.PREVIOUS, tbl.getRows().get(2).getCells().get(1).getCellPr().getVerticalMerge());
    }

    /// <summary>
    /// WORDSNET-19247 Merged cells in table are broken when AllowAutoFit is false.
    /// Decision that cells may be merged is made when cells span the same number of grid columns.
    /// However currently AW compares spans which produces by different algorithms and may have different
    /// results for the same cell. Use similar sources of grid spans to fix the issue.
    /// </summary>
    @Test
    public void test19247() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        PreferredWidth width50 = PreferredWidth.fromPoints(ConvertUtil.millimeterToPoint(50.0));
        PreferredWidth width10 = PreferredWidth.fromPoints(ConvertUtil.millimeterToPoint(10.0));

        Table tbl = builder.startTable();
        insertCell(builder, "Merged col", CellMerge.FIRST, CellMerge.NONE, width50);
        insertCell(builder, "x", CellMerge.NONE, CellMerge.NONE, width10);
        insertCell(builder, "Data 1", CellMerge.NONE, CellMerge.NONE, width50);
        builder.endRow();

        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE, width50);
        insertCell(builder, "x", CellMerge.NONE, CellMerge.NONE, width10);
        insertCell(builder, "Data 2", CellMerge.NONE, CellMerge.NONE, width50);
        builder.endRow();

        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE, width50);
        insertCell(builder, "x", CellMerge.NONE, CellMerge.NONE, width10);
        insertCell(builder, "Data 3", CellMerge.NONE, CellMerge.NONE, width50);
        builder.endRow();

        insertCell(builder, "", CellMerge.PREVIOUS, CellMerge.NONE, width50);
        insertCell(builder, "x", CellMerge.NONE, CellMerge.NONE, width10);
        insertCell(builder, "Data 4", CellMerge.NONE, CellMerge.NONE, width50);
        builder.endRow();

        tbl.setAllowAutoFit(false);
        builder.endTable();

        TestUtil.executeValidator(builder.getDocument(), SaveFormat.DOCX);

        tbl = (Table) builder.getDocument().getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(CellMerge.FIRST, tbl.getRows().get(0).getFirstCell().getCellPr().getVerticalMerge());
        msAssert.areEqual(CellMerge.PREVIOUS, tbl.getRows().get(1).getFirstCell().getCellPr().getVerticalMerge());
        msAssert.areEqual(CellMerge.PREVIOUS, tbl.getRows().get(2).getFirstCell().getCellPr().getVerticalMerge());
        msAssert.areEqual(CellMerge.PREVIOUS, tbl.getRows().get(3).getFirstCell().getCellPr().getVerticalMerge());
        msAssert.areEqual(4, tbl.getRows().getCount());
    }

    /// <summary>
    /// WORDSNET-19483 DocumentBuilder.EndTable sets wrong font size after PushFont/PopFont.
    /// ParagraphBreakRunPr doesn't restore state after popping the font.
    /// PushFont/PopFont should consider ParagraphBreakRunPr.
    /// </summary>
    @Test
    public void test19483() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        checkFont(12, 20, false, builder);

        builder.pushFont();

        builder.getFont().setSize(14.0);
        builder.getFont().setBold(true);

        checkFont(14, 28, true, builder);
        builder.writeln("Bold 14");

        builder.popFont();
        checkFont(12, 20, false, builder);

        builder.startTable();
        builder.insertCell();
        checkFont(12, 20, false, builder);
        builder.writeln("No Bold 12");
        builder.endTable();

        checkFont(12, 20, false, builder);
    }

    /// <summary>
    /// WORDSNET-21602 Table row is lost when HTML and Table is inserted into document using DocumentBuilder.
    /// CellFormat cell merge is now changes to default value on the TableEnd.
    /// </summary>
    @Test
    public void test21602() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder documentBuilder = new DocumentBuilder(doc);

        // AddVerticallyMergedTable
        documentBuilder.writeln("Vertically merged table");

        Table tmpTable = documentBuilder.startTable();

        Cell tmpCell = documentBuilder.insertCell();
        documentBuilder.write("Title");
        tmpCell.getCellFormat().setVerticalMerge(CellMerge.FIRST);
        documentBuilder.endRow();

        tmpCell = documentBuilder.insertCell();
        tmpCell.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
        documentBuilder.endRow();

        documentBuilder.endTable();

        // AddSimpleTable
        documentBuilder.writeln("Simple table");

        tmpTable = documentBuilder.startTable();

        documentBuilder.insertCell();
        documentBuilder.write("Title");
        documentBuilder.endRow();

        documentBuilder.insertCell();
        documentBuilder.write("Text");
        documentBuilder.endRow();

        documentBuilder.endTable();

        Cell cell = doc.getFirstSection().getBody().getTables().get(1).getFirstRow().getFirstCell();
        msAssert.areEqual(CellMerge.NONE, cell.getCellFormat().getVerticalMerge());
    }

    /// <summary>
    /// WORDSNET-23389 Issue when changing Font immediately after DocumentBuilder.StartTable.
    /// Tests the new flatFormatting option to support sequential table formatting.
    /// </summary>
    @Test (dataProvider = "test23389DataProvider")
    public void test23389(boolean сontextTableFormatting) throws Exception
    {
        Document doc = new Document();
        DocumentBuilderOptions opt = new DocumentBuilderOptions();
        opt.setContextTableFormatting(сontextTableFormatting);
        DocumentBuilder builder = new DocumentBuilder(doc, opt);
        builder.writeln("12 size");

        // Special case for ClearFormatting() inside the table.
        Table table1 = builder.startTable();
        builder.getFont().setSize(5.0);
        builder.insertCell();
        builder.write("5 size");
        builder.insertCell();
        builder.getFont().clearFormatting();
        builder.endRow();
        builder.endTable();
        if (сontextTableFormatting)
            builder.writeln("5 size");
        else
            builder.writeln("12 size");

        // Common case for ContextTableFormatting option.
        Table table2 = builder.startTable();
        builder.getFont().setSize(5.0);
        builder.insertCell();
        builder.write("5 size");
        builder.insertCell();
        builder.getFont().setSize(25.0);
        builder.write("25 size");
        builder.endRow();
        builder.endTable();
        if (сontextTableFormatting)
            builder.writeln("5 size");
        else
            builder.writeln("25 size");

        Run run1 = doc.getFirstSection().getBody().getParagraphs().get(1).getFirstRun();
        Run table1Run1 = table1.getFirstRow().getFirstCell().getFirstParagraph().getFirstRun();
        Run table2Run1 = table2.getFirstRow().getFirstCell().getFirstParagraph().getFirstRun();
        Run table2Run2 = table2.getFirstRow().getLastCell().getFirstParagraph().getFirstRun();
        Run run2 = doc.getFirstSection().getBody().getParagraphs().get(2).getFirstRun();

        double run1FontSizeExp = сontextTableFormatting
            ? 5
            : 12;
        double run2FontSizeExp = сontextTableFormatting
            ? 5
            : 25;
        msAssert.areEqual(run1FontSizeExp, run1.getFont().getSize());
        msAssert.areEqual(5, table1Run1.getFont().getSize());
        msAssert.areEqual(5, table2Run1.getFont().getSize());
        msAssert.areEqual(25, table2Run2.getFont().getSize());
        msAssert.areEqual(run2FontSizeExp, run2.getFont().getSize());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23389DataProvider")
	public static Object[][] test23389DataProvider() throws Exception
	{
		return new Object[][]
		{
			{false},
			{true},
		};
	}

    /// <summary>
    /// WORDSNET-15676 Different Font color after builder.EndTable.
    /// WORDSNET-18449 DocumentBuilder.EndTable sets DocumentBuilder.Font.Bold to true.
    /// WORDSNET-18451 DocumentBuilder.EndTable sets DocumentBuilder.Font properties unexpectedly.
    /// Relates to WORDSNET-23389
    /// </summary>
    @Test
    public void test18449() throws Exception
    {
        DocumentBuilderOptions opt = new DocumentBuilderOptions();
        opt.setContextTableFormatting(false);
        DocumentBuilder builder = new DocumentBuilder(opt);

        builder.startTable();
        builder.getFont().setBold(true);
        builder.getFont().setColor(Color.BLUE);
        builder.insertCell();
        builder.write("Row 1, Cell 1 Content.");
        builder.insertCell();
        builder.write("Row 1, Cell 2 Content.");
        builder.endRow();
        builder.getFont().setColor(Color.RED);
        builder.getFont().setBold(false);

        msAssert.isFalse(builder.getFont().getBold());
        msAssert.areEqual(Color.RED.getRGB(), builder.getFont().getColor().getRGB());
        builder.endTable();
        msAssert.isFalse(builder.getFont().getBold());
        msAssert.areEqual(Color.RED.getRGB(), builder.getFont().getColor().getRGB());
        builder.write("This should not be bold and blue.");
        Font lastFont = builder.getDocument().getFirstSection().getBody().getLastParagraph().getLastRun().getFont();
        msAssert.isFalse(lastFont.getBold());
        msAssert.areEqual(Color.RED.getRGB(), lastFont.getColor().getRGB());
    }

    /// <summary>
    /// Insert new cell to table and set appropriate vertical/horizontal merge to it.
    /// </summary>
    private static void insertCell(DocumentBuilder builder, String text, /*CellMerge*/int vertical,
        /*CellMerge*/int horizontal)
	{
		insertCell(builder, text, vertical, horizontal, null);
	}

    /// <summary>
    /// Insert new cell to table and set appropriate vertical/horizontal merge to it.
    /// </summary>
    private static void insertCell(DocumentBuilder builder, String text,/*CellMerge*/int vertical,/*CellMerge*/int horizontal, PreferredWidth preferredWidth)
    {
        builder.insertCell();
        builder.write(text);
        builder.getCellFormat().setVerticalMerge(vertical);
        builder.getCellFormat().setHorizontalMerge(horizontal);
        builder.getCellFormat().setPreferredWidth((preferredWidth == null) ? PreferredWidth.fromPercent(25.0) : preferredWidth);
    }

    /// <summary>
    /// Checks <see cref="LineStyle"/> of right border in the specified <paramref name="cellFormat"/>.
    /// </summary>
    private static void checkLineStyle(CellFormat cellFormat, /*LineStyle*/int expectedLineStyle) throws Exception
    {
        msAssert.areEqual(expectedLineStyle, cellFormat.getBorders().getRight().getLineStyle());
    }

    /// <summary>
    /// Sets border line style and text orientation for row and cell via DocumentBuilder or CurrnetParagraph.
    /// </summary>
    private static void setTableFormatting(/*LineStyle*/int lineStyle, /*TextOrientation*/int orientation, DocumentBuilder builder, boolean setViaDocumentBuilder) throws Exception
    {
        if (setViaDocumentBuilder || builder.getCurrentParagraph().isInCell())
        {
            RowFormat rowFormat = (setViaDocumentBuilder) ? builder.getRowFormat() : builder.getCurrentParagraph().getParentRow().getRowFormat();
            CellFormat cellFormat = (setViaDocumentBuilder) ? builder.getCellFormat() : builder.getCurrentParagraph().getParentCell().getCellFormat();

            rowFormat.getBorders().getLeft().setLineStyle(lineStyle);
            cellFormat.setOrientation(orientation);
        }
    }

    /// <summary>
    /// Checks border line style and text orientation for row and cell in DocumentBuilder and CurrnetParagraph.
    /// </summary>
    private static void checkTableFormatting(/*LineStyle*/int lineStyle, /*TextOrientation*/int orientation, DocumentBuilder builder) throws Exception
    {
        checkTableFormattingInDocumentBuilder(lineStyle, orientation, builder);
        checkTableFormattingInCurrentParagraph(lineStyle, orientation, builder);
    }

    /// <summary>
    /// Checks border line style and text orientation for row and cell in CurrnetParagraph.
    /// </summary>
    private static void checkTableFormattingInCurrentParagraph (/*LineStyle*/int lineStyle, /*TextOrientation*/int orientation, DocumentBuilder builder) throws Exception
    {
        msAssert.isTrue(builder.getCurrentParagraph().isInCell());
        msAssert.areEqual(lineStyle, builder.getCurrentParagraph().getParentRow().getRowFormat().getBorders().getLeft().getLineStyle());
        msAssert.areEqual(orientation, builder.getCurrentParagraph().getParentCell().getCellFormat().getOrientation());
    }

    /// <summary>
    /// Checks border line style and text orientation for row and cell in DocumentBuilder.
    /// </summary>
    private static void checkTableFormattingInDocumentBuilder(/*LineStyle*/int lineStyle, /*TextOrientation*/int orientation, DocumentBuilder builder) throws Exception
    {
        msAssert.areEqual(lineStyle, builder.getRowFormat().getBorders().getLeft().getLineStyle());
        msAssert.areEqual(orientation, builder.getCellFormat().getOrientation());
    }

    private static int getAttrTwips(DocumentBuilder builder, int attrKey)
    {
        TablePr tablePr = builder.getTablePrCopy();
        Object value = tablePr.get(attrKey);
        return (value != null)
            ? (/*int*/Integer)value
            : 0;
    }

    /// <summary>
    /// Helper method, relates to WORDSNET-9040
    /// </summary>
    private static void writeFaultTableTOC(DocumentBuilder docBuilder, Document doc) throws Exception
    {
        docBuilder.startTable();
        docBuilder.getParagraphFormat().clearFormatting();

        Style style = doc.getStyles().get("Normal"); //$NON-NLS-1$
        if (style != null)
        {
            docBuilder.getParagraphFormat().setStyle(style);
        }

        docBuilder.getFont().clearFormatting();

        docBuilder.getRowFormat().setHeight(0.0);

        Cell cell1 = docBuilder.insertCell();
        docBuilder.getParagraphFormat().clearFormatting();

        docBuilder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        docBuilder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);

        Cell rowOneCell = null;
        for (int j = 1; j < 11; ++j)
        {
            rowOneCell = docBuilder.insertCell();

            docBuilder.getCellFormat().setVerticalMerge(CellMerge.NONE);
            docBuilder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        }

        docBuilder.moveTo(cell1.getFirstParagraph());

        docBuilder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        docBuilder.getCellFormat().getBorders().setLineWidth(1.0);

        docBuilder.write("Test string in first row.");

        docBuilder.endRow();

        docBuilder.insertCell();
        docBuilder.getParagraphFormat().clearFormatting();

        Cell cell3 = docBuilder.insertCell();
        docBuilder.getParagraphFormat().clearFormatting();

        docBuilder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        docBuilder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);

        Cell rowTwoCell = null;
        for (int j = 1; j < 10; ++j)
        {
            rowTwoCell = docBuilder.insertCell();

            docBuilder.getCellFormat().setVerticalMerge(CellMerge.NONE);
            docBuilder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        }

        docBuilder.moveTo(cell3.getFirstParagraph());

        docBuilder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
        docBuilder.getCellFormat().getBorders().setLineWidth(1.0);

        docBuilder.write("Test string in second row.");

        docBuilder.endRow();

        Table table = docBuilder.endTable();
        table.setPreferredWidth(PreferredWidth.AUTO);

        rowOneCell.getCellFormat().setWidth(497.25);
        rowOneCell.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(497.25));

        rowTwoCell.getCellFormat().setWidth(497.25);
        rowTwoCell.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(497.25));

        table.setAllowAutoFit(true);
    }

    /// <summary>
    /// Helper method, relates to WORDSNET-19483
    /// </summary>
    private static void checkFont(int fontSize, int paraBreakFontSize, boolean bold, DocumentBuilder builder)
    {
        msAssert.areEqual(fontSize, builder.getFont().getSize());
        msAssert.areEqual(bold, builder.getFont().getBold());
        msAssert.areEqual(paraBreakFontSize, builder.getCurrentParagraph().getParagraphBreakRunPr().getSize());
        msAssert.areEqual(bold, builder.getCurrentParagraph().getParagraphBreakRunPr().getBold().toBool());
    }
}

