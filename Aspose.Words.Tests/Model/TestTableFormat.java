// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.DrColor;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import java.awt.Color;


/// <summary>
/// Tests related to WORDSNET-581 Add TableFormat property to Table object.
/// This is about creating a public API to set table properties on the table, not on the rows.
/// </summary>
@Test (groups = "SingleThreadGroup")
public class TestTableFormat
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }


    // FOSS: TestFloatingTablesJira6797 removed — loads a WordML (.wml) input and saves PDF;
    // both the WML reader and the PDF save path are removed.

    /// <summary>
    /// WORDSNET-5709 Layout gets disturbed upon converting DOCX to PDF
    /// Added special handling of floating table properties upon docx import.
    /// </summary>
    /// <remarks>
    /// MS Word shows negative tblpy as tblpYSpec = VerticalAlignment.Bottom in Table Positioning Props dialog,
    /// but this change is not written upon roundtrip. We could have potential layout bug with this.
    /// </remarks>
    @Test
    public void testTestFLoatingTablesJira5709() throws Exception
    {
        // MS Word removes tblpPr when vAnchor = margin and no tblpY or zero tblpY specified only.
        msAssert.isFalse(getTablePrFromTestFile("MsHacksHDefaultVMarginNoTblpY.docx").isFloating());
        msAssert.isFalse(getTablePrFromTestFile("MsHacksHDefaultVMarginZeroTblpY.docx").isFloating());

        validateDefaultBehavior("MsHacksHMarginVMarginOneTblpY.docx", true);
        validateDefaultBehavior("MsHacksHDefaultVMarginOneTblpY.docx", false);
    }



    @Test
    public void testJira17857_Margin() throws Exception
    {
        final String PATH = "Model\\FloatingTables\\TestJira17857_margin";
        // FOSS: Docx2Doc and Docx2Rtf round-trips removed (Doc/Rtf writers gone).
        TestUtil.openSaveOpen(PATH, UnifiedScenario.DOCX_2_DOCX);
    }

    // FOSS: TestJira17857_Wml removed — every case is a WordML (.wml) Wml2Wml round-trip,
    // and both the WML reader and writer are removed. The same floating-table scenarios stay
    // covered by the Docx2Docx cases in TestJira17857.

    private static TablePr getTablePrFromTestFile(String fileName) throws Exception
    {
        Document doc = TestUtil.open(msString.format("Model\\FloatingTables\\{0}", fileName));
        return ((Table)doc.getChild(NodeType.TABLE, 0, true)).getFirstRow().getTablePr();
    }

    /// <summary>
    /// Validate that AW follows default behavior defined in specs/impl. notes: removal of default values and doing +1/-1 trick for FrameLeft.
    /// </summary>
    private static void validateDefaultBehavior(String fileName, boolean isNondefaultHorizontalAnchor) throws Exception
    {
        TablePr tablePr = getTablePrFromTestFile(fileName);
        msAssert.isTrue(tablePr.isFloating());
        msAssert.areEqual(RelativeVerticalPosition.MARGIN, tablePr.getRelativeVerticalPosition());
        msAssert.areEqual(isNondefaultHorizontalAnchor, tablePr.hasNondefaultRelativeHorizontalPosition());
        msAssert.isFalse(tablePr.hasNondefaultRelativeVerticalPosition());
        msAssert.areEqual(0, tablePr.getFrameLeft());
    }

    /// <summary>
    /// Shows how various table formatting can now be specified on the table object.
    /// </summary>
    @Test
    public void testTableFormatGeneral() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);


        Table table = createTestTable(builder, "Default Table");


        table = createTestTable(builder, "Right Aligned, 300pt Width, AutoFit Off");
        msAssert.areEqual(TableAlignment.LEFT, table.getAlignment());
        table.setAlignment(TableAlignment.RIGHT);
        msAssert.areEqual(PreferredWidth.fromPercent(100.0), table.getPreferredWidth());
        table.setPreferredWidth(PreferredWidth.fromPoints(300.0));
        msAssert.areEqual(true, table.getAllowAutoFit());
        table.setAllowAutoFit(false);


        table = createTestTable(builder, "Right-To-Left, Padding, Spacing");
        msAssert.areEqual(false, table.getBidi());
        table.setBidi(true);
        msAssert.areEqual(0, table.getTopPadding());
        table.setTopPadding(5.0);
        msAssert.areEqual(0, table.getBottomPadding());
        table.setBottomPadding(10.0);
        msAssert.areEqual(5.4, table.getLeftPadding());
        table.setLeftPadding(0.0);
        msAssert.areEqual(5.4, table.getRightPadding());
        table.setRightPadding(3.0);
        msAssert.areEqual(0, table.getCellSpacing());
        table.setCellSpacing(1.0);


        table = createTestTable(builder, "Left Indent, Borders, Shading");
        msAssert.areEqual(0, table.getLeftIndent());
        table.setLeftIndent(-50);
        table.setBorder(BorderType.LEFT, LineStyle.DOUBLE, 3.0, DrColor.RED.toNativeColor(), true);
        table.setBorder(BorderType.HORIZONTAL, LineStyle.DOT, 1.0, DrColor.BLUE.toNativeColor(), true);
        table.setShading(TextureIndex.TEXTURE_20_PERCENT, DrColor.LIGHT_GREEN.toNativeColor(), DrColor.VIOLET.toNativeColor());


        table = createTestTable(builder, "No Borders");
        table.clearBorders();


        TestUtil.save(doc, "Model\\Nodes\\TestTableFormatGeneral.docx", null, true);
    }

    /// <summary>
    /// Shows how we can apply table style now to the table.
    /// </summary>
    @Test
    public void testTableFormatStyle() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);


        Table table = createTestTable(builder, "TableNormal");
        table.clearBorders();
        table.setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_NORMAL));


        table = createTestTable(builder, "Set style using identifier.");
        table.clearBorders();
        table.setStyleIdentifier(StyleIdentifier.TABLE_SIMPLE_1);


        table = createTestTable(builder, "Set style using name.");
        table.clearBorders();
        table.setStyleName("Medium Grid 1 Accent 1");


        table = createTestTable(builder, "Set style using object.");
        table.clearBorders();
        table.setStyle(doc.getStyles().getByStyleIdentifier(StyleIdentifier.TABLE_LIST_8));


        TestUtil.save(doc, "Model\\Nodes\\TestTableFormatStyle.docx", null, true);
    }

    @Test
    public void testTableFormatStyleLook() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = createTestTable(builder, "None");
        table.clearBorders();
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1);
        table.setStyleOptions(TableStyleOptions.NONE);

        table = createTestTable(builder, "First and Last Rows");
        table.clearBorders();
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1);
        table.setStyleOptions(TableStyleOptions.FIRST_ROW | TableStyleOptions.LAST_ROW);

        table = createTestTable(builder, "First and Last Cols");
        table.clearBorders();
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1);
        table.setStyleOptions(TableStyleOptions.FIRST_COLUMN | TableStyleOptions.LAST_COLUMN);

        table = createTestTable(builder, "Row Bands");
        table.clearBorders();
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_GRID_3_ACCENT_1);
        table.setStyleOptions(TableStyleOptions.ROW_BANDS);

        TestUtil.save(doc, "Model\\Nodes\\TestTableFormatStyleLook.docx", null, true);
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void testSetNonExistantStyle() throws Exception
    {
        Document doc = new Document();
        Table table = new Table(doc);
        table.setStyleName("AAA");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testSetWrongStyleType() throws Exception
    {
        Document doc = new Document();
        Table table = new Table(doc);
        table.setStyleName("Normal");
    }

    private static Table createTestTable(DocumentBuilder builder, String headingText) throws Exception
    {
        builder.getParagraphFormat().setStyleName("Heading 1");
        builder.writeln(headingText);

        builder.getParagraphFormat().clearFormatting();

        builder.insertCell();
        builder.write("AAA");
        builder.insertCell();
        builder.write("BBBBBBBBB");

        builder.insertCell();
        builder.getCellFormat().getBorders().setLineWidth(3.0);
        builder.write("CCCCCC");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().getBorders().setLineWidth(0.5);
        builder.write("DDD");

        builder.insertCell();
        builder.getCellFormat().getBorders().setLineWidth(3.0);
        builder.write("EEEEEEEEE");

        builder.insertCell();
        builder.getCellFormat().getBorders().setLineWidth(0.5);
        builder.write("FFFFFF");
        builder.endRow();

        builder.insertCell();
        builder.write("GGG");
        builder.insertCell();
        builder.write("HHHHHHHHH");
        builder.insertCell();
        builder.write("IIIIII");
        builder.endRow();

        Table table = builder.endTable();

        builder.writeln();
        return table;
    }

    // FOSS: TestJira7926A removed — it builds the table by loading an HTML string, and the
    // HTML reader is removed ("HTML file format is not supported in the FOSS version"). The
    // HTML import is the scenario (applying a style to an HTML-imported table with non-default
    // cell/row formatting), so it cannot be reproduced without the HTML reader. The sibling
    // TestJira7926B covers the style-application logic on a builder-created table.

    /// <summary>
    /// There is table with cell and row attributes applied,
    /// we have to set new style in the same way like MS Word does.
    /// </summary>
    @Test
    public void testJira7926B() throws Exception
    {
        // This document was created in MSW 2013.
        final String FILE_PATH = "Model\\Nodes\\TestJira7926B.docx";
        Document doc = TestUtil.open(FILE_PATH);

        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);

        // LightListAccent1 style does not exist in AllStyles2013.docx in this case we
        // try to get this style from another BuiltInStyles collections (2007 or 2003) and import to the document.
        table.setStyleIdentifier(StyleIdentifier.LIGHT_LIST_ACCENT_1);

        doc = TestUtil.saveOpen(doc, FILE_PATH);
        table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        Row row = table.getRows().get(1);
        msAssert.areEqual(HeightRule.AT_LEAST, row.getRowFormat().getHeightRule());
        msAssert.areEqual(1.0f, row.getRowFormat().getBorders().getByBorderType(BorderType.TOP).getBorderWidth());
        msAssert.isNull(row.getFirstCell().getCellPr().get(CellAttr.VERTICAL_ALIGNMENT));
        msAssert.isNull(row.getTablePr().get(TableAttr.CELL_SPACING));
    }


    /// <summary>
    /// WORDSNET-14947 Question about values of margins in tables
    /// Added read-only public API for DistanceFromText table properties set.
    /// </summary>
    @Test
    public void testJira14947() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira14947.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(1);

        msAssert.isTrue(table.isFloating());
        msAssert.areEqual(11.35, table.getDistanceTop());
        msAssert.areEqual(26.35, table.getDistanceBottom());
        msAssert.areEqual(9.05, table.getDistanceLeft());
        msAssert.areEqual(22.7, table.getDistanceRight());
    }

    /// <summary>
    /// WORDSNET-15817 Expose Table.HorizontalAlignment property public.
    /// Added read-only public API for relative table alignments.
    /// </summary>
    @Test
    public void testJira15817() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira15817.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(3);

        msAssert.areEqual(TextWrapping.AROUND, table.getTextWrapping());
        msAssert.areEqual(HorizontalAlignment.CENTER, table.getRelativeHorizontalAlignment());
        msAssert.areEqual(VerticalAlignment.DEFAULT, table.getRelativeVerticalAlignment());
    }

    /// <summary>
    /// WORDSNET-15981 Get position of floating table in public API.
    /// Added read-only public API for all positioning attributes of floating table.
    /// </summary>
    @Test
    public void testJira15981() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\TestJira15981.xml");
        TableCollection tables = doc.getFirstSection().getBody().getTables();

        checkTableCore15981(tables.get(0), RelativeHorizontalPosition.PAGE, RelativeVerticalPosition.PAGE, 171.15, 84.15, false);
        checkTableCore15981(tables.get(1), RelativeHorizontalPosition.MARGIN, RelativeVerticalPosition.MARGIN, 183.3, 125.35, true);
        checkTableCore15981(tables.get(2), RelativeHorizontalPosition.COLUMN, RelativeVerticalPosition.PARAGRAPH, 0.0, 0.0, false);
    }

    /// <summary>
    /// The helper method for validating floating table properties in <see cref="TestJira15981"/>.
    /// </summary>
    private static void checkTableCore15981(Table table,
        /*RelativeHorizontalPosition*/int hAnchor, /*RelativeVerticalPosition*/int vAnchor,
        double hDistance, double vDistance,
        boolean allowOverlap)
    {
        msAssert.areEqual(TextWrapping.AROUND, table.getTextWrapping());
        msAssert.areEqual(hAnchor, table.getHorizontalAnchor());
        msAssert.areEqual(vAnchor, table.getVerticalAnchor());
        msAssert.isTrue(MathUtil.areEqual(hDistance, table.getAbsoluteHorizontalDistance()), msString.format("expected: {0}, but was: {1}", hDistance, table.getAbsoluteHorizontalDistance()));
        msAssert.isTrue(MathUtil.areEqual(vDistance, table.getAbsoluteVerticalDistance()), msString.format("expected: {0}, but was: {1}", vDistance, table.getAbsoluteVerticalDistance()));
        msAssert.areEqual(allowOverlap, table.getAllowOverlap());
    }


    /// <summary>
    /// WORDSNET-17330 Added public property <see cref="Table.AllowCellSpacing"/>
    /// </summary>
    @Test
    public void testAllowCellSpacing() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = createTestTable(builder, "None");

        // Verify getter.
        // AllowCellSpacing is false by default.
        msAssert.isFalse(table.getAllowCellSpacing());

        // Specify cell spacing. It changes AllowCellSpacing to true;
        table.setCellSpacing(0.0);
        msAssert.isTrue(table.getAllowCellSpacing());

        // Verify setter.
        // Setting AllowCellSpacing to false removes CellSpacing.
        table.setAllowCellSpacing(false);
        msAssert.isNull(table.getFirstRow().getTablePr().get(TableAttr.CELL_SPACING));

        // Setting AllowCellSpacing to true adds initial values of CellSpacing, if it was not specified.
        table.setAllowCellSpacing(true);
        msAssert.areEqual(ConvertUtilCore.twipToPoint(Table.INITIAL_CELL_SPACING), table.getCellSpacing());

        // Setting AllowCellSpacing to true, when CellSpacing was specified, doesn't change CellSpacing.
        table.setCellSpacing(100.0);
        table.setAllowCellSpacing(true);
        msAssert.areEqual(100, table.getCellSpacing());
    }

    /// <summary>
    /// WORDSNET-19655 Remove invalid nested table indent values during validation.
    /// Nested table indent handling reworked.
    /// </summary>
    @Test
    public void test19655() throws Exception
    {
        final String FILE_NAME = "Model\\Table\\Test19655";

        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        Table table = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getTables().get(1);

        // At this point, it is clear that the negative LeftIndent for the nested table
        // was set to zero during the post-loading stage.

        // Checking that negative values ​​in the LeftIndent public setter for nested tables are ignored.
        table.setLeftIndent(-30);
        msAssert.areEqual(0, table.getLeftIndent());

        // Preparing the negative indentation for the nested table to verify
        // (at the next step) that this indent is updated during the validation stage.
        table.getTablePr().setAttr(TableAttr.LEFT_INDENT, -600);

        for (Row row : (Iterable<Row>) table.getRows())
            row.getTablePr().setAttr(TableAttr.LEFT_INDENT, -600);

        msAssert.areEqual(-30, table.getLeftIndent());

        // Check that negative indentation for nested tables is updated during the validation stage.
        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX);

        table = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getFirstCell().getTables().get(1);
        msAssert.areEqual(0, table.getLeftIndent());
    }

    /// <summary>
    /// WORDSNET-22302 Remove invalid nested table indent values during validation.
    /// SetShading() implementation extended to whole table.
    /// </summary>
    @Test
    public void test22302() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Client code to create the table.
        Table table = builder.startTable();
        builder.insertCell();
        builder.insertCell();
        builder.endRow();
        builder.insertCell();
        builder.insertCell();
        table.setCellSpacing(8.0);

        table.setShading(TextureIndex.TEXTURE_10_PERCENT, Color.RED, Color.RED);

        for (Row row : (Iterable<Row>) table.getRows())
        {
            msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, row.getTablePr().getShading().getTexture());
            msAssert.areEqual(DrColor.RED, row.getTablePr().getShading().getBackgroundPatternColorInternal());

            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, cell.getCellPr().getShading().getTexture());
                msAssert.areEqual(DrColor.RED, cell.getCellPr().getShading().getBackgroundPatternColorInternal());
            }
        }
    }
}

