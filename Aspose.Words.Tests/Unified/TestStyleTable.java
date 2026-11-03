// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.Debug;
import org.testng.annotations.DataProvider;


/// <summary>
/// Unified tests for table styles.
/// At the moment table styles are supported only in DOCX and WML and expanded to direct formatting when saving to other formats.
/// When table styles are supported in DOC and RTF then most of the tests here should be reworked into unified tests.
///
/// RK Some of these tests now should be reworked to unified.
/// </summary>
@Test
public class TestStyleTable
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Check how built-in table styles are loaded and saved in DOCX.
    /// </summary>
    @Test
    public void testBuiltInTableStylesDOCX() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Style\\Table\\TestBuiltInTableStyles.docx");
    }

    @Test
    public void testBuiltInTableStylesDOC() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Table\\TestBuiltInTableStyles.docx");
        TestUtil.save(doc, "Model\\Style\\Table\\TestBuiltInTableStyles.docx");
    }

    @Test
    public void testWideBandsDOC() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Table\\TestWideBands.docx");
        TestUtil.save(doc, "Model\\Style\\Table\\TestWideBands.docx");
    }




    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table style.
    /// </summary>
    @Test
    public void testTableStyleProperties() throws Exception
    {
        final String FILE_NAME = "Model\\Style\\Table\\TestTableStyleProperties.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("TableStyle1");

        msAssert.isFalse(style.getAllowBreakAcrossPages());
        Border border = style.getBorders().getTop();
        msAssert.areEqual(1.5, border.getLineWidth());
        msAssert.areEqual(new Color((142), (170), (219)), border.getColor());
        msAssert.areEqual(36, style.getTopPadding());
        msAssert.areEqual(TableAlignment.CENTER, style.getAlignment());
        msAssert.areEqual(TableAlignment.CENTER, style.getTablePr().getAlignment());
        msAssert.areEqual(TableAlignment.CENTER, style.getRowPr().getAlignment());
        msAssert.areEqual(3, style.getCellSpacing());
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(60), style.getTablePr().getCellSpacing());
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(60), style.getRowPr().getCellSpacing());
        msAssert.areEqual(18, style.getLeftIndent());
        msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, style.getShading().getTexture());
        msAssert.areEqual(2, style.getRowStripe());
        msAssert.areEqual(3, style.getColumnStripe());

        style.setAllowBreakAcrossPages(true);
        border.setLineWidth(1.0);
        border.setColor(Color.RED);
        style.setTopPadding(12.0);
        style.setAlignment(TableAlignment.RIGHT);
        msAssert.areEqual(TableAlignment.RIGHT, style.getTablePr().getAlignment());
        msAssert.areEqual(TableAlignment.RIGHT, style.getRowPr().getAlignment());
        style.setCellSpacing(1.0);
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(20), style.getTablePr().getCellSpacing());
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(20), style.getRowPr().getCellSpacing());
        style.setLeftIndent(9.0);
        style.getShading().setTexture(TextureIndex.TEXTURE_20_PERCENT);
        style.setRowStripe(1);
        style.setColumnStripe(2);

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        style = (TableStyle)doc.getStyles().get("TableStyle1");

        msAssert.isTrue(style.getAllowBreakAcrossPages());
        border = style.getBorders().getTop();
        msAssert.areEqual(1, border.getLineWidth());
        msAssert.areEqual(new Color((255), (0), (0)), border.getColor());
        msAssert.areEqual(12, style.getTopPadding());
        msAssert.areEqual(TableAlignment.RIGHT, style.getAlignment());
        msAssert.areEqual(TableAlignment.RIGHT, style.getTablePr().getAlignment());
        msAssert.areEqual(TableAlignment.RIGHT, style.getRowPr().getAlignment());
        msAssert.areEqual(1, style.getCellSpacing());
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(20), style.getTablePr().getCellSpacing());
        msAssert.areEqual(PreferredWidth.fromTwipsSafe(20), style.getRowPr().getCellSpacing());
        msAssert.areEqual(9, style.getLeftIndent());
        msAssert.areEqual(TextureIndex.TEXTURE_20_PERCENT, style.getShading().getTexture());
        msAssert.areEqual(1, style.getRowStripe());
        msAssert.areEqual(2, style.getColumnStripe());
    }

    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table style that values are correctly taken from
    /// a base style.
    /// </summary>
    @Test
    public void testTableStyleFormattingFromBaseStyle() throws Exception
    {
        final String FILE_NAME = "Model\\Style\\Table\\TestTableStyleProperties.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("InheritedFromTableStyle1");
        style.getCellPr().clear(); // to remove shading definition that MS Word copies from a base style

        msAssert.isFalse(style.getAllowBreakAcrossPages());
        Border border = style.getBorders().getTop();
        msAssert.areEqual(1.5, border.getLineWidth());
        msAssert.areEqual(new Color((142), (170), (219)), border.getColor());
        msAssert.areEqual(36, style.getTopPadding());
        msAssert.areEqual(TableAlignment.CENTER, style.getAlignment());
        msAssert.areEqual(3, style.getCellSpacing());
        msAssert.areEqual(18, style.getLeftIndent());
        msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, style.getShading().getTexture());
        msAssert.areEqual(2, style.getRowStripe());
        msAssert.areEqual(3, style.getColumnStripe());
    }

    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table style.
    /// </summary>
    @Test
    public void testTableStyleCreation() throws Exception
    {
        Document doc = new Document();
        TableStyle style1 = (TableStyle)doc.getStyles().add(StyleType.TABLE, "TableStyle1");
        TableStyle style2 = (TableStyle)doc.getStyles().addCopy(doc.getStyles().getByStyleIdentifier(StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_3));
        style2.setName("TableStyle2");

        // Check default value of the property.
        msAssert.areEqual(true, style1.getAllowBreakAcrossPages());

        style1.getFont().setColor(new Color((128), (0), (128)));
        style1.getParagraphFormat().setFirstLineIndent(54.0);
        style1.setAllowBreakAcrossPages(false);
        style1.setRightPadding(36.0);
        style2.setAlignment(TableAlignment.RIGHT);
        style2.setLeftIndent(24.0);
        style2.getConditionalStyles().getFirstColumn().getFont().setBold(true);

        doc = TestUtil.saveOpen(doc, "Model\\Style\\Table\\TestTableStyleCreation.docx",
            UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        style1 = (TableStyle)doc.getStyles().get("TableStyle1");
        style2 = (TableStyle)doc.getStyles().get("TableStyle2");

        msAssert.areEqual(new Color((128), (0), (128)), style1.getFont().getColor());
        msAssert.areEqual(54, style1.getParagraphFormat().getFirstLineIndent());
        msAssert.isFalse(style1.getAllowBreakAcrossPages());
        msAssert.areEqual(36, style1.getRightPadding());
        msAssert.areEqual(TableAlignment.RIGHT, style2.getAlignment());
        msAssert.areEqual(24, style2.getLeftIndent());
        msAssert.isTrue(style2.getConditionalStyles().getFirstColumn().getFont().getBold());
    }

    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table conditional style.
    /// </summary>
    @Test
    public void testTableConditionalStyle() throws Exception
    {
        final String FILE_NAME = "Model\\Style\\Table\\TestTableStyleProperties.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("TableStyle2");
        msAssert.areEqual(TextureIndex.TEXTURE_5_PERCENT, style.getShading().getTexture());
        msAssert.areEqual(0.5, style.getBorders().getBottom().getLineWidth());
        msAssert.areEqual("Calibri", style.getFont().getName());

        ConditionalStyleCollection conditionals = style.getConditionalStyles();
        ConditionalStyle conditional = conditionals.getFirstRow();

        msAssert.areEqual(ConditionalStyleType.FIRST_ROW, conditional.getType());
        msAssert.areEqual(ParagraphAlignment.CENTER, conditional.getParagraphFormat().getAlignment());
        msAssert.areEqual("Aharoni", conditional.getFont().getName());
        msAssert.areEqual(24, conditional.getFont().getSize());
        Border border = conditional.getBorders().getTop();
        msAssert.areEqual(1.5, border.getLineWidth()); // from conditional style
        msAssert.areEqual(LineStyle.DOUBLE, border.getLineStyle());
        msAssert.areEqual(TextureIndex.TEXTURE_10_PERCENT, conditional.getShading().getTexture());
        msAssert.areEqual(18, conditional.getTopPadding());
        msAssert.areEqual(4, conditional.getLeftPadding());
        msAssert.areEqual(2, conditional.getRightPadding());
        msAssert.areEqual(5, conditional.getBottomPadding());

        conditional = conditionals.getLastRow();
        msAssert.areEqual(TextureIndex.TEXTURE_5_PERCENT, conditional.getShading().getTexture()); // from table style
        msAssert.areEqual("Calibri", conditional.getFont().getName()); // from table style
        msAssert.areEqual(12, conditional.getFont().getSize());
        msAssert.areEqual(LineStyle.NONE, conditional.getBorders().getBottom().getLineStyle()); // borders are not taken from table style

        msAssert.areEqual(13, conditionals.getFirstColumn().getFont().getSize());
        msAssert.areEqual(14, conditionals.getLastColumn().getFont().getSize());
        msAssert.areEqual(15, conditionals.getOddRowBanding().getFont().getSize());
        msAssert.areEqual(16, conditionals.getEvenRowBanding().getFont().getSize());
        msAssert.areEqual(17, conditionals.getOddColumnBanding().getFont().getSize());
        msAssert.areEqual(18, conditionals.getEvenColumnBanding().getFont().getSize());
        msAssert.areEqual(19, conditionals.getTopLeftCell().getFont().getSize());
        msAssert.areEqual(20, conditionals.getTopRightCell().getFont().getSize());
        msAssert.areEqual(21, conditionals.getBottomLeftCell().getFont().getSize());
        msAssert.areEqual(22, conditionals.getBottomRightCell().getFont().getSize());

        msAssert.areEqual(ConditionalStyleType.FIRST_ROW, conditionals.get(0).getType());

        conditional = conditionals.getFirstRow();
        conditional.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        conditional.getFont().setName("Arial New");
        conditional.getFont().setSize(10.0);
        border = conditional.getBorders().getTop();
        border.setLineWidth(2.0);
        conditional.getShading().setTexture(TextureIndex.TEXTURE_20_PERCENT);
        conditional.setTopPadding(8.0);

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        style = (TableStyle)doc.getStyles().get("TableStyle2");

        conditional = style.getConditionalStyles().getFirstRow();

        msAssert.areEqual(ConditionalStyleType.FIRST_ROW, conditional.getType());
        msAssert.areEqual(ParagraphAlignment.RIGHT, conditional.getParagraphFormat().getAlignment());
        msAssert.areEqual("Arial New", conditional.getFont().getName());
        msAssert.areEqual(10, conditional.getFont().getSize());
        border = conditional.getBorders().getTop();
        msAssert.areEqual(2, border.getLineWidth());
        msAssert.areEqual(TextureIndex.TEXTURE_20_PERCENT, conditional.getShading().getTexture());
        msAssert.areEqual(8, conditional.getTopPadding());
    }

    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table conditional style that values are correctly taken
    /// from a base conditional style.
    /// </summary>
    @Test
    public void testConditionalFormattingFromBaseConditional() throws Exception
    {
        // The test document has generated manually since MS Word copies formatting from base conditional styles to
        // inherited ones.
        final String FILE_NAME = "Model\\Style\\Table\\TestConditionalFormattingFromBaseConditional.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("InheritedStyle1");
        ConditionalStyle conditional = style.getConditionalStyles().getFirstRow();

        msAssert.areEqual("Aharoni", conditional.getFont().getName());
        msAssert.areEqual(14, conditional.getFont().getSize());
        msAssert.isTrue(conditional.getFont().getBold());
        msAssert.isFalse(conditional.getFont().getItalic());

        msAssert.areEqual(ParagraphAlignment.RIGHT, conditional.getParagraphFormat().getAlignment());
        msAssert.areEqual(2, conditional.getParagraphFormat().getLineSpacingInLines());

        msAssert.areEqual(TextureIndex.TEXTURE_30_PERCENT, conditional.getShading().getTexture());
        msAssert.areEqual(new Color((255), (255), (0)), conditional.getShading().getForegroundPatternColor());
        msAssert.areEqual(new Color((128), (128), (255)), conditional.getShading().getBackgroundPatternColor());

        Border border = conditional.getBorders().getTop();
        msAssert.areEqual(0.5, border.getLineWidth());
        msAssert.areEqual(LineStyle.TRIPLE, border.getLineStyle());
        border = conditional.getBorders().getRight();
        msAssert.areEqual(0.5, border.getLineWidth());
        msAssert.areEqual(LineStyle.TRIPLE, border.getLineStyle());
        border = conditional.getBorders().getBottom();
        msAssert.areEqual(0.5, border.getLineWidth());
        msAssert.areEqual(LineStyle.TRIPLE, border.getLineStyle());
        border = conditional.getBorders().getLeft();
        msAssert.areEqual(0.5, border.getLineWidth());
        msAssert.areEqual(LineStyle.TRIPLE, border.getLineStyle());

        // Border is not taken from a parent table style.
        border = style.getConditionalStyles().getLastRow().getBorders().getTop();
        msAssert.areEqual(LineStyle.NONE, border.getLineStyle());

        msAssert.areEqual(4.5, conditional.getTopPadding());
        msAssert.areEqual(1.5, conditional.getLeftPadding());
        msAssert.areEqual(6, conditional.getRightPadding());
        msAssert.areEqual(3, conditional.getBottomPadding());
    }

    /// <summary>
    /// WORDSNET-18400 Tests public properties of a table conditional style that values are correctly taken
    /// from a table style.
    /// </summary>
    @Test
    public void testConditionalFormattingFromTableStyle() throws Exception
    {
        // The test document has generated manually since MS Word copies formatting from base conditional styles to
        // inherited ones.
        final String FILE_NAME = "Model\\Style\\Table\\TestConditionalFormattingFromTableStyle.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("InheritedStyle1");
        ConditionalStyle conditional = style.getConditionalStyles().getFirstRow();

        msAssert.areEqual("Aharoni", conditional.getFont().getName());
        msAssert.areEqual(11, conditional.getFont().getSize());
        msAssert.isTrue(conditional.getFont().getBold());
        msAssert.isFalse(conditional.getFont().getItalic());

        msAssert.areEqual(ParagraphAlignment.RIGHT, conditional.getParagraphFormat().getAlignment());
        msAssert.areEqual(2, conditional.getParagraphFormat().getLineSpacingInLines());

        msAssert.areEqual(TextureIndex.TEXTURE_30_PERCENT, conditional.getShading().getTexture());
        msAssert.areEqual(new Color((255), (255), (0)), conditional.getShading().getForegroundPatternColor());
        msAssert.areEqual(new Color((128), (128), (255)), conditional.getShading().getBackgroundPatternColor());

        // Border is not taken from a parent table style.
        Border border = conditional.getBorders().getTop();
        msAssert.areEqual(LineStyle.NONE, border.getLineStyle());

        msAssert.areEqual(4.5, conditional.getTopPadding());
        msAssert.areEqual(1.5, conditional.getLeftPadding());
        msAssert.areEqual(6, conditional.getRightPadding());
        msAssert.areEqual(3, conditional.getBottomPadding());
    }

    /// <summary>
    /// WORDSNET-18400 Tests the <see cref="ConditionalStyleCollection.ClearFormatting"/> public method.
    /// </summary>
    @Test
    public void testClearingFormattingOfConditionalStyles() throws Exception
    {
        final String FILE_NAME = "Model\\Style\\Table\\TestTableStyleProperties.docx";
        Document doc = TestUtil.open(FILE_NAME);
        TableStyle style = (TableStyle)doc.getStyles().get("TableStyle2");
        ConditionalStyleCollection conditionals = style.getConditionalStyles();

        msAssert.areEqual(12, conditionals.getDefinedStyles().size());

        conditionals.clearFormatting();

        msAssert.areEqual(0, conditionals.getDefinedStyles().size());
    }

    @Test (dataProvider = "testJira17857tblpPrStyleDataProvider")
    public void testJira17857tblpPrStyle(String fileName) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        TableStyle style = (TableStyle)doc.getStyles().get("Table Floating");

        Debug.assertTrue(style.getTablePr().getRelativeVerticalPosition() == RelativeVerticalPosition.TABLE_DEFAULT);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira17857tblpPrStyleDataProvider")
	public static Object[][] testJira17857tblpPrStyleDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Style\\Table\\TestJira17857_tblpPr_style.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-18708 There was a IndexOutOfRangeException exception on conditional style enumeration.
    /// Fixed now.
    /// </summary>
    @Test
    public void testJira18708() throws Exception
    {
        Document doc = new Document();
        TableStyle tableStyle = (TableStyle)doc.getStyles().add(StyleType.TABLE, "MyTableStyle1");

        // There was an error on enumeration.
        for (ConditionalStyle conditional : tableStyle.getConditionalStyles());

        msAssert.areEqual(12, tableStyle.getConditionalStyles().getCount());
    }


    /// <summary>
    /// WORDSNET-21796, case 4 Disabled First Row conditional style was applied to a table, if
    /// <see cref="TablePr.HeadingFormat"/> is <c>true</c> for the first row(s) of it.
    /// </summary>
    @Test
    public void test21796Table4() throws Exception
    {
        Document doc = TestUtil.open("Model\\Table\\Test21796Table4.docx");
        Table table = doc.getFirstSection().getBody().getTables().get(0);
        Cell cell = table.getFirstRow().getFirstCell();
        Border border = cell.getCellFormat().getBorders().getBottom();

        msAssert.isTrue(border.isVisible());
        msAssert.areEqual(0.5, border.getLineWidth());

        // Run validator to generate direct formatting using TableFormattingExpander.
        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);
        border = cell.getCellPr().getBorderBottom();
        // The bolder is null, which indicates that First Row conditional style is not applied.
        msAssert.isNull(border);
    }

    /// <summary>
    /// WORDSNET-21114 Add feature to get or set Cell Vertical Alignment using TableStyle.
    /// Added new public property TableStyle.VerticalAlignment.
    /// </summary>
    @Test
    public void test21114() throws Exception
    {
        final String TABLE_STYLE = "Custom Table 1";
        final String FILE_NAME = "Model\\Table\\Test21114";

        // Check read values.
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        TableStyle style = (TableStyle)doc.getStyles().get(TABLE_STYLE);
        msAssert.areEqual(CellVerticalAlignment.BOTTOM, style.getVerticalAlignment());
        msAssert.areEqual(ParagraphAlignment.RIGHT, style.getParagraphFormat().getAlignment());

        // Check modified values.
        style.setVerticalAlignment(CellVerticalAlignment.CENTER);
        style.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        doc = TestUtil.saveOpen(doc, FILE_NAME, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);
        style = (TableStyle)doc.getStyles().get(TABLE_STYLE);
        msAssert.areEqual(CellVerticalAlignment.CENTER, style.getVerticalAlignment());
        msAssert.areEqual(ParagraphAlignment.CENTER, style.getParagraphFormat().getAlignment());
    }



    /// <summary>
    /// WORDSNET-28402 Remove obsolete TableStyle.Bidi property.
    /// Removed obsolete BiDi property from <see cref="TableStyle"/> class.
    /// </summary>
    @Test
    public void test28402()
    {
    }

    private static void checkTable22728(Table table) throws Exception
    {
        Cell firstRowCell = table.getFirstRow().getFirstCell();
        Cell secondRowCell = table.getRows().get(1).getFirstCell();

        // Test TableStyle methods that get formatting.

        Shading firstRowShading = firstRowCell.getCellFormat().getShading();
        Shading secondRowShading = secondRowCell.getCellFormat().getShading();
        msAssert.areEqual(0, firstRowShading.getBackgroundPatternColor().getRGB());
        msAssert.areEqual(/*unchecked*/((int)(int) 0xffe6faffL), secondRowShading.getBackgroundPatternColor().getRGB());

        // Test TableFormattingExpander.

        // Run validator to generate direct formatting using TableFormattingExpander.
        TestUtil.executeValidator(table.fetchDocument(), SaveFormat.MARKDOWN);
        // Get and check direct attributes.
        firstRowShading = (Shading)firstRowCell.getCellPr().getDirectAttr(CellAttr.SHADING);
        secondRowShading = (Shading)secondRowCell.getCellPr().getDirectAttr(CellAttr.SHADING);
        msAssert.areEqual(0, firstRowShading.getBackgroundPatternColor().getRGB());
        msAssert.areEqual(/*unchecked*/((int)(int) 0xffe6faffL), secondRowShading.getBackgroundPatternColor().getRGB());
    }

    /// <summary>
    /// Checks properties of the specified shading object.
    /// </summary>
    private static void checkShading(Shading shading, int expectedBackgroundColor,
        String expectedThemeFill, String expectedThemeFillTint)
    {
        msAssert.areEqual(expectedBackgroundColor, shading.getBackgroundPatternColor().getRGB());
        msAssert.areEqual(expectedThemeFill, shading.getThemeFill());
        msAssert.areEqual(expectedThemeFillTint, shading.getThemeFillTint());
    }

    /// <summary>
    /// Check text in specified cell is not italic.
    /// </summary>
    private static void checkNotItalic(Table table, int columnIndex, int rowIndex)
    {
        Cell cell = table.getCell(columnIndex, rowIndex);
        RunPr runPr = cell.getFirstParagraph().getFirstRun().getRunPr();

        msAssert.areEqual(AttrBoolEx.FALSE, runPr.get(FontAttr.ITALIC));
    }

    /// <summary>
    /// Check text in specified cell is not bold.
    /// </summary>
    private static void checkNotBold(Table table, int columnIndex, int rowIndex)
    {
        Cell cell = table.getCell(columnIndex, rowIndex);
        RunPr runPr = cell.getFirstParagraph().getFirstRun().getRunPr();

        msAssert.areEqual(AttrBoolEx.FALSE, runPr.get(FontAttr.BOLD));
    }

    private static void checkImportTableStyle(/*ImportFormatMode*/int mode, String fileName) throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Style\\Table\\TestImportTableStyle.docx");
        Document dstDoc = new Document();
        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, mode);
        TestUtil.saveOpen(dstDoc, fileName);
    }

    /// <summary>
    /// Returns total number of table styles.
    /// </summary>
    private static int getTableStylesCount(StyleCollection styles)
    {
        int tableStylesCount = 0;
        for (Style style : styles)
            if (style.getType() == StyleType.TABLE)
                tableStylesCount++;

        return tableStylesCount;
    }

    /// <summary>
    /// WORDSJAVA-2491 The customer wishes to read the table style margins from a DOCX file,
    /// but the values returned for paddings are '0.0'.
    /// </summary>
    @Test
    public void testJava2491() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Table\\TestJava2491.docx");
        double defaultPaddingCm = 1.27;
        double multiplierPointToCm = 0.0352777776;
        for (Style style : doc.getStyles())
        {
            if (style.getName().equals("TableStyle1"))
            {
                msAssert.greaterOrEqual(0.0001, defaultPaddingCm - ((TableStyle)style).getBottomPadding() * multiplierPointToCm);
                msAssert.greaterOrEqual(0.0001, defaultPaddingCm - ((TableStyle)style).getTopPadding() * multiplierPointToCm);
                msAssert.greaterOrEqual(0.0001, defaultPaddingCm - ((TableStyle)style).getRightPadding() * multiplierPointToCm);
                msAssert.greaterOrEqual(0.0001, defaultPaddingCm - ((TableStyle)style).getLeftPadding() * multiplierPointToCm);
            }
        }
    }
}

