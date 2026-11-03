// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/01/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.DrColor;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests members of the <see cref="ChartDataTable"/> class.
/// </summary>
@Test
public class TestChartDataTable
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests the <see cref="ChartDataTable.Font"/> property.
    /// </summary>
    @Test
    public void testDataTableProperties() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestDataTableFormat.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        ChartDataTable dataTable1 = shapes.get(0).getChart().getDataTable();
        ChartDataTable dataTable2 = shapes.get(1).getChart().getDataTable();
        ChartDataTable dataTable3 = shapes.get(2).getChart().getDataTable();
        ChartDataTable dataTable4 = shapes.get(3).getChart().getDataTable();

        checkDataTable(dataTable1, true, true, false, false, true);
        checkDataTable(dataTable2, true, false, true, true, false);
        checkDataTable(dataTable3, true, true, true, true, true);
        // This data table has borders, but Format.Stroke.Visible is 'false'.
        msAssert.isFalse(dataTable4.getFormat().getStroke().getVisible());
        checkDataTable(dataTable4, true, false, true, true, true);

        // Change properties.

        dataTable1.hasLegendKeys(false);
        dataTable1.hasHorizontalBorder(true);
        dataTable1.hasVerticalBorder(true);
        dataTable1.hasOutlineBorder(false);

        dataTable2.hasLegendKeys(true);
        dataTable2.hasOutlineBorder(true);

        dataTable3.hasLegendKeys(false);
        dataTable3.hasHorizontalBorder(false);
        dataTable3.hasVerticalBorder(false);
        dataTable3.hasOutlineBorder(false);

        dataTable4.setShow(false);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestDataTableProperties.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartDataTable.Font"/> property.
    /// </summary>
    @Test
    public void testFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestDataTableFont";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.isTrue(shapes.get(0).getChart().getDataTable().getShow());

        Font font1 = shapes.get(0).getChart().getDataTable().getFont();
        Font font2 = shapes.get(1).getChart().getDataTable().getFont();
        Font font3 = shapes.get(2).getChart().getDataTable().getFont();
        Font font4 = shapes.get(3).getChart().getDataTable().getFont();

        checkFont(font1, "Arial", 11.5, new Color((0x2E), (0x75), (0xB5)), msColor.Empty, true, true,
            Underline.DOTTED_HEAVY, 7, 1.0, false, false, false, true, RunVerticalAlignment.SUBSCRIPT, 1024);
        checkFont(font2, "Calibri", 10.0, new Color((0x59), (0x59), (0x59)), msColor.Empty, false, true,
            Underline.NONE, 0, 0.0, true, false, true, false, RunVerticalAlignment.BASELINE, 1024);
        // Data table of the 3rd chart uses chart space font.
        checkFont(font3, "Times New Roman", 14.0, msColor.Empty, msColor.Empty, false, true,
            Underline.NONE, 0, 0.5, false, true, true, false, RunVerticalAlignment.SUPERSCRIPT, 1024);
        // Data table of the 4th chart uses font defaults.
        checkFont(font4, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        changeFont(font1, "Courier New", 11.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        font1.setHighlightColor(Color.CYAN);

        font2.clearFormatting();

        changeFont(font3, "Verdana", 12.0, msColor.getGreen(), true, false,
            Underline.NONE, 12, 4, false, true, false, true, RunVerticalAlignment.BASELINE, 1049);

        changeFont(font4, "Times New Roman", 14.0, Color.RED, false, true,
            Underline.DOTTED, 3, 3, false, true, true, false, RunVerticalAlignment.SUBSCRIPT, 1049);

        checkFont(font1, "Courier New", 11.0, DrColor.DARK_RED.toNativeColor(), DrColor.CYAN.toNativeColor(),
            true, false, Underline.DOUBLE, 10, 3.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(font2, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(font3, "Verdana", 12.0, DrColor.GREEN.toNativeColor(), msColor.Empty, true, false,
            Underline.NONE, 12, 4.0, false, true, false, true, RunVerticalAlignment.BASELINE, 1049);
        checkFont(font4, "Times New Roman", 14.0, DrColor.RED.toNativeColor(), msColor.Empty,
            false, true, Underline.DOTTED, 3, 3.0, false, true, true, false, RunVerticalAlignment.SUBSCRIPT, 1049);

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        shapes = doc.getFirstSection().getBody().getShapes();
        font1 = shapes.get(0).getChart().getDataTable().getFont();
        font2 = shapes.get(1).getChart().getDataTable().getFont();
        font3 = shapes.get(2).getChart().getDataTable().getFont();
        font4 = shapes.get(3).getChart().getDataTable().getFont();

        checkFont(font1, "Courier New", 11.0, DrColor.DARK_RED.toNativeColor(), DrColor.CYAN.toNativeColor(),
            true, false, Underline.DOUBLE, 10, 3.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(font2, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(font3, "Verdana", 12.0, DrColor.GREEN.toNativeColor(), msColor.Empty, true, false,
            Underline.NONE, 12, 4.0, false, true, false, true, RunVerticalAlignment.BASELINE, 1049);
        checkFont(font4, "Times New Roman", 14.0, DrColor.RED.toNativeColor(), msColor.Empty,
            false, true, Underline.DOTTED, 3, 3.0, false, true, true, false, RunVerticalAlignment.SUBSCRIPT, 1049);
    }

    /// <summary>
    /// Tests the <see cref="ChartDataTable.Font"/> property in a created chart.
    /// </summary>
    @Test
    public void testFontInNewChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        ChartDataTable dataTable = shape.getChart().getDataTable();

        msAssert.isFalse(dataTable.getShow());
        dataTable.setShow(true);

        Font font = dataTable.getFont();

        checkFont(font, "Calibri", 9.0, new Color((0x59), (0x59), (0x59)), msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        // Change font.

        changeFont(font, "Arial", 12.0, msColor.getDarkRed(), false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        // Check.

        checkFont(font, "Arial", 12.0, DrColor.DARK_RED.toNativeColor(), msColor.Empty, false, true,
            Underline.SINGLE, 10, 1.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
    }

    /// <summary>
    /// Tests that an exception is generated when showing a data table for charts of unsupported types.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThis chart type does not support a data table.\\E", dataProvider = "testShowingDataTableInNonSupportedChartDataProvider")
    public void testShowingDataTableInNonSupportedChart(String relativeFileName) throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\" + relativeFileName);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        // This throws an exception.
        chart.getDataTable().setShow(true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShowingDataTableInNonSupportedChartDataProvider")
	public static Object[][] testShowingDataTableInNonSupportedChartDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Word2016Charts\\BoxWhisker.docx"},
			{"TestScatterChart.docx"},
		};
	}

    /// <summary>
    /// Checks properties of the specified <see cref="Font"/> instance.
    /// </summary>
    private static void checkFont(Font font, String expectedFontName, double expectedFontSize, Color expectedColor,
        Color expectedHighlightColor, boolean expectedBold, boolean expectedItalic, /*Underline*/int expectedUnderline,
        int expectedKerning, double expectedSpacing, boolean expectedDoubleStrikeThrough, boolean expectedStrikeThrough,
        boolean expectedAllCaps, boolean expectedSmallCaps, /*RunVerticalAlignment*/int expectedVerticalAlignment,
        int expectedLocaleId)
    {
        TestChartUtil.checkFontProperties(font, expectedFontName, null, null, null, expectedFontSize,
            expectedKerning, expectedSpacing, expectedBold, expectedItalic, expectedStrikeThrough,
            expectedDoubleStrikeThrough, expectedAllCaps, expectedSmallCaps, expectedColor, expectedHighlightColor,
            expectedUnderline, expectedVerticalAlignment, expectedLocaleId);
    }

    /// <summary>
    /// Changes properties of the specified <see cref="Font"/> instance.
    /// </summary>
    private static void changeFont(Font font, String fontName, double fontSize, Color color, boolean bold,
        boolean italic, /*Underline*/int underline, int kerning, int spacing, boolean doubleStrikeThrough,
        boolean strikeThrough, boolean allCaps, boolean smallCaps, /*RunVerticalAlignment*/int verticalAlignment, int localeId)
    {
        TestChartUtil.setFontProperties(font, fontName, fontSize, kerning, spacing, bold, italic, strikeThrough,
            doubleStrikeThrough, allCaps, smallCaps, color, underline, verticalAlignment, localeId);
    }

    /// <summary>
    /// Checks properties of the specified data table.
    /// </summary>
    private void checkDataTable(ChartDataTable dataTable, boolean isShown, boolean hasLegendKeys,
        boolean hasHorizontalBorder, boolean hasVerticalBorder, boolean hasOutlineBorder)
    {
        msAssert.areEqual(isShown, dataTable.getShow());
        msAssert.areEqual(hasLegendKeys, dataTable.hasLegendKeys());
        msAssert.areEqual(hasHorizontalBorder, dataTable.hasHorizontalBorder());
        msAssert.areEqual(hasVerticalBorder, dataTable.hasVerticalBorder());
        msAssert.areEqual(hasOutlineBorder, dataTable.hasOutlineBorder());
    }
}

