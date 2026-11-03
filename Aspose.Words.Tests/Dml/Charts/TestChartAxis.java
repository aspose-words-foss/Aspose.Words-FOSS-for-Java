// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import com.aspose.SystemPal;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests members of the <see cref="ChartAxis"/> class.
/// </summary>
@Test
public class TestChartAxis
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// WORDSNET-16119 A category axis is marked as date/time on adding date/time category values.
    /// This promote that correct axis properties are written into a document file (on DOCX depending on 
    /// the <see cref="DmlChartAxisAttrs.IsDateCategoryAxis"/> attribute either catAx or dateAx element is generated).
    /// </summary>
    @Test
    public void testTimeCategoryAxis() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartAxis xAxis = chart.getAxisX();

        // Expected automatic-text category type.
        msAssert.areEqual(false, xAxis.isDateCategoryAxis());

        // Add series with DateTime data.
        chart.getSeries().addInternal("Series 1",
            new DateTime[] { new DateTime(2017, 11, 6), new DateTime(2017, 11, 9), new DateTime(2017, 11, 15),
                new DateTime(2017, 11, 21), new DateTime(2017, 11, 25), new DateTime(2017, 11, 29) },
            new double[] { 1.2, 0.3, 2.1, 2.9, 4.2, 5.3 });

        // Expected automatic-date/time category type.
        msAssert.areEqual(true, xAxis.isDateCategoryAxis());

        chart.getSeries().clear();

        // Expected automatic-text category type.
        msAssert.areEqual(false, xAxis.isDateCategoryAxis());
    }

    /// <summary>
    /// Tests the <see cref="ChartAxis.HasMajorGridlines"/> and <see cref="ChartAxis.HasMinorGridlines"/> properties.
    /// </summary>
    @Test
    public void testGridLines() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertChart(ChartType.BAR, 432.0, 252.0);
        checkGridlines(doc, "Model\\Charts\\Test{0}.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartAxis.HasMajorGridlines"/> and <see cref="ChartAxis.HasMinorGridlines"/> properties
    /// of a Word 2016 chart.
    /// </summary>
    @Test
    public void testGridLinesInWord2016Chart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Pareto.docx");
        checkGridlines(doc, "Model\\Charts\\Word2016Charts\\Pareto{0}.docx");
    }

    /// <summary>
    /// WORDSNET-24673 Provide public API to manipulate chart gridlines.
    /// Tests the customer's case as is.
    /// </summary>
    @Test
    public void test24673() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.BAR, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getLegend().setPosition(LegendPosition.NONE);

        ChartSeriesCollection seriesColl = chart.getSeries();
        chart.getAxisY().setHidden(true);
        chart.getAxisY().hasMajorGridlines(false);
        chart.getAxisX().setHidden(true);
        chart.getTitle().setShow(false);

        seriesColl.clear();
        chart.getAxisY().getScaling().setMaximum(new AxisBound(100.0));

        String[] categories = new String[] { "AW Category 1" };
        seriesColl.add("AW Series 1", categories, new double[] { 30.0 });

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\Test24673.docx");
    }


    /// <summary>
    /// Tests the <see cref="ChartAxis.Title"/> property of a new chart.
    /// </summary>
    @Test
    public void testNewChartAxisTitle() throws Exception
    {
        // Axis title text depends on current culture.
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
        try
        {
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);

            Shape shape = builder.insertChart(ChartType.BAR, 432.0, 252.0);
            Chart chart = shape.getChart();

            ChartAxisTitle xTitle = chart.getAxisX().getTitle();
            checkTitle(xTitle, false, false, "Axis Title");
            ChartAxisTitle yTitle = chart.getAxisY().getTitle();
            checkTitle(yTitle, false, false, "Axis Title");

            xTitle.setText("Category");
            xTitle.setOverlay(true);
            xTitle.setShow(true);

            yTitle.setShow(true);
            yTitle.setOverlay(true);
            yTitle.setText("Value");

            checkTitle(xTitle, true, true, "Category");
            checkTitle(yTitle, true, true, "Value");

            doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestNewChartAxisTitle.docx", null, false);
            shape = doc.getFirstSection().getBody().getShapes().get(0);
            chart = shape.getChart();

            checkTitle(chart.getAxisX().getTitle(), true, true, "Category");
            checkTitle(chart.getAxisY().getTitle(), true, true, "Value");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Tests the <see cref="ChartAxis.Title"/> property of an existing chart.
    /// </summary>
    @Test
    public void testExistingAxisTitle() throws Exception
    {
        // Axis title text depends on current culture.
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
        try
        {
            Document doc = TestUtil.open("Model\\Charts\\TestAxisTitle.docx");
            Chart chart1 = doc.getFirstSection().getBody().getShapes().get(0).getChart();

            ChartAxisTitle xTitle1 = chart1.getAxisX().getTitle();
            checkTitle(xTitle1, true, false, "Horizontal Axis");
            ChartAxisTitle yTitle1 = chart1.getAxisY().getTitle();
            checkTitle(yTitle1, true, false, "Vertical Axis ");

            xTitle1.setShow(false);

            yTitle1.setOverlay(true);
            yTitle1.setText("Value");

            checkTitle(xTitle1, false, false, "Horizontal Axis");
            checkTitle(yTitle1, true, true, "Value");

            Chart chart4 = doc.getFirstSection().getBody().getShapes().get(3).getChart();

            ChartAxisTitle xTitle4 = chart4.getAxisX().getTitle();
            checkTitle(xTitle4, true, false, "Primary horizontal");
            ChartAxisTitle yTitle4 = chart4.getAxisY().getTitle();
            checkTitle(yTitle4, true, false, "Primary vertical");

            xTitle4.setText(null);
            yTitle4.setText("");

            checkTitle(xTitle4, true, false, "Axis Title");
            checkTitle(yTitle4, true, false, "Axis Title");

            TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestExistingAxisTitle.docx");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Tests a found issue: if an axis has no title and the <see cref="ChartAxisTitle.Show"/> property is set to
    /// <b>true</b> to display the title before setting title text, the title remains invisible.
    /// </summary>
    @Test
    public void testShowingAxisTitle() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        Chart chart = shape.getChart();
        ChartAxisTitle xTitle = chart.getAxisX().getTitle();
        ChartAxisTitle yTitle = chart.getAxisY().getTitle();

        xTitle.setShow(true);
        xTitle.setText("Categories");
        yTitle.setShow(true);
        yTitle.setText("Values");

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestShowingAxisTitle.docx");
    }

    /// <summary>
    /// Tests the <see cref="AxisTickLabels.Font"/> property.
    /// </summary>
    @Test
    public void testTickLabelsFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestTickLabelsFont";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        // Clone the first and second charts, and a paragraph between them.
        Body body = doc.getFirstSection().getBody();
        Paragraph para1 = body.getParagraphs().get(0);
        body.appendChild(para1.deepClone(true));
        Paragraph para2 = body.getParagraphs().get(1);
        body.appendChild(para2.deepClone(true));
        Paragraph para3 = body.getParagraphs().get(2);
        body.appendChild(para3.deepClone(true));

        // Pre-Word 2016 chart.

        Chart chart1 = body.getShapes().get(0).getChart();

        // X axis font is changed.
        Font xFont1 = chart1.getAxisX().getTickLabels().getFont();
        // Y axis font is taken from chart space.
        Font yFont1 = chart1.getAxisY().getTickLabels().getFont();

        checkFont(xFont1, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 12, 0.2, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(yFont1, "Times New Roman", 14.0, msColor.Empty, msColor.Empty, false, true,
            Underline.SINGLE, 9, 0.0, false, false, false, false, RunVerticalAlignment.SUPERSCRIPT, 1024);

        changeFont(xFont1, "Courier New", 11.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        xFont1.setHighlightColor(Color.CYAN);

        changeFont(yFont1, "Verdana", 12.0, msColor.getGreen(), true, false,
            Underline.NONE, 12, 4, false, true, false, true, RunVerticalAlignment.BASELINE, 1049);

        // Word 2016 chart.

        Chart chart2 = body.getShapes().get(1).getChart();

        // X axis font is changed.
        Font xFont2 = chart2.getAxisX().getTickLabels().getFont();
        // Default format is used for Y axis font.
        Font yFont2 = chart2.getAxisY().getTickLabels().getFont();

        checkFont(xFont2, "Calibri", 8.0, new Color((68), (114), (196)), msColor.Empty, false, false,
            Underline.DOTTED, 0, 0.2, false, false, true, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(yFont2, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        changeFont(xFont2, "Courier New", 10.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        xFont2.setHighlightColor(Color.CYAN);

        changeFont(yFont2, "Times New Roman", 10.0, msColor.getGreen(), false, true,
            Underline.SINGLE, 12, 2, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);

        // Pre-Word 2016 chart.

        Chart chart3 = body.getShapes().get(2).getChart();

        // Default font format is used for X axis.
        Font xFont3 = chart3.getAxisX().getTickLabels().getFont();

        checkFont(xFont3, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        // Pre-Word 2016 chart.

        Chart chart4 = body.getShapes().get(3).getChart();

        Font xFont4 = chart4.getAxisX().getTickLabels().getFont();
        checkFont(xFont4, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 12, 0.2, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        xFont4.clearFormatting();

        // Word 2016 chart.

        Chart chart5 = body.getShapes().get(4).getChart();

        Font xFont5 = chart5.getAxisX().getTickLabels().getFont();
        checkFont(xFont5, "Calibri", 8.0, new Color((68), (114), (196)), msColor.Empty, false, false,
            Underline.DOTTED, 0, 0.2, false, false, true, false, RunVerticalAlignment.BASELINE, 1024);

        xFont5.clearFormatting();

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        body = doc.getFirstSection().getBody();
        chart1 = body.getShapes().get(0).getChart();
        chart2 = body.getShapes().get(1).getChart();
        chart3 = body.getShapes().get(2).getChart();
        chart4 = body.getShapes().get(3).getChart();
        chart5 = body.getShapes().get(4).getChart();

        // Check results.

        checkFont(chart1.getAxisX().getTickLabels().getFont(), "Courier New", 11.0, new Color((139), (0), (0)),
            new Color((0), (255), (255)),
            true, false, Underline.DOUBLE, 10, 3.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(chart1.getAxisY().getTickLabels().getFont(), "Verdana", 12.0, new Color((0), (128), (0)), msColor.Empty,
            true, false, Underline.NONE, 12, 4.0, false, true, false, true, RunVerticalAlignment.BASELINE, 1049);
        checkFont(chart2.getAxisX().getTickLabels().getFont(), "Courier New", 10.0, new Color((139), (0), (0)),
            new Color((0), (255), (255)),
            true, false, Underline.DOUBLE, 10, 1.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(chart2.getAxisY().getTickLabels().getFont(), "Times New Roman", 10.0, new Color((0), (128), (0)), msColor.Empty,
            false, true, Underline.SINGLE, 12, 2.0, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);
        checkFont(chart3.getAxisX().getTickLabels().getFont(), "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(chart4.getAxisX().getTickLabels().getFont(), "Times New Roman", 14.0, msColor.Empty, msColor.Empty, false, true,
            Underline.SINGLE, 9, 0.0, false, false, false, false, RunVerticalAlignment.SUPERSCRIPT, 1024);
        checkFont(chart5.getAxisX().getTickLabels().getFont(), "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
    }

    /// <summary>
    /// Tests the <see cref="AxisTickLabels.Font"/> property when creating a new chart.
    /// </summary>
    @Test (dataProvider = "testTickLabelsFontInNewChartDataProvider") // pre-Word 2016 chart
    public void testTickLabelsFontInNewChart(/*ChartType*/int chartType) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 432.0, 252.0);
        Chart chart = shape.getChart();

        Font xFont = chart.getAxisX().getTickLabels().getFont();
        Font yFont = chart.getAxisY().getTickLabels().getFont();

        int defaultKerning = chart.getChartSpace().isChartEx() ? 0 : 12;

        checkFont(xFont, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false, Underline.NONE,
            defaultKerning, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        checkFont(yFont, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false, Underline.NONE,
            defaultKerning, 0.0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        // Change font.

        changeFont(xFont, "Arial", 11.0, msColor.getDarkRed(), false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        changeFont(yFont, "Times New Roman", 14.0, Color.RED, true, false,
            Underline.DOUBLE, 0, 0, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);

        // Check.

        checkFont(xFont, "Arial", 11.0, new Color((139), (0), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 10, 1.0, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        checkFont(yFont, "Times New Roman", 14.0, new Color((255), (0), (0)), msColor.Empty, true, false,
            Underline.DOUBLE, 0, 0.0, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTickLabelsFontInNewChartDataProvider")
	public static Object[][] testTickLabelsFontInNewChartDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartType.LINE},
			{ChartType.WATERFALL},
		};
	}


    /// <summary>
    /// Tests tick labels orientation and rotation in a new chart.
    /// </summary>
    @Test
    public void testTickLabelsOrientationInNewChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape1 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        AxisTickLabels axisX1TickLabels = shape1.getChart().getAxisX().getTickLabels();
        AxisTickLabels axisY1TickLabels = shape1.getChart().getAxisY().getTickLabels();

        axisX1TickLabels.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);
        axisX1TickLabels.setRotation(-10);
        axisY1TickLabels.setOrientation(ShapeTextOrientation.HORIZONTAL);
        axisY1TickLabels.setRotation(45);

        Shape shape2 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        AxisTickLabels axisX2TickLabels = shape2.getChart().getAxisX().getTickLabels();
        AxisTickLabels axisY2TickLabels = shape2.getChart().getAxisY().getTickLabels();

        axisX2TickLabels.setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL);
        axisY2TickLabels.setRotation(5);

        checkOrientation(axisX1TickLabels, ShapeTextOrientation.VERTICAL_FAR_EAST, -10);
        checkOrientation(axisY1TickLabels, ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(axisX2TickLabels, ShapeTextOrientation.WORD_ART_VERTICAL, 0);
        checkOrientation(axisY2TickLabels, ShapeTextOrientation.HORIZONTAL, 5);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTickLabelsOrientationInNewChart.docx", null, true);

        shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        axisX1TickLabels = shape1.getChart().getAxisX().getTickLabels();
        axisY1TickLabels = shape1.getChart().getAxisY().getTickLabels();
        shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        axisX2TickLabels = shape2.getChart().getAxisX().getTickLabels();
        axisY2TickLabels = shape2.getChart().getAxisY().getTickLabels();

        checkOrientation(axisX1TickLabels, ShapeTextOrientation.VERTICAL_FAR_EAST, -10);
        checkOrientation(axisY1TickLabels, ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(axisX2TickLabels, ShapeTextOrientation.WORD_ART_VERTICAL, 0);
        checkOrientation(axisY2TickLabels, ShapeTextOrientation.HORIZONTAL, 5);
    }

    /// <summary>
    /// Tests tick labels orientation and rotation in an existing chart.
    /// </summary>
    @Test
    public void testTickLabelsOrientationInExistingChart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestTickLabelsOrientation.docx");
        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        AxisTickLabels axisX1TickLabels = shape1.getChart().getAxisX().getTickLabels();
        AxisTickLabels axisY1TickLabels = shape1.getChart().getAxisY().getTickLabels();
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        AxisTickLabels axisX2TickLabels = shape2.getChart().getAxisX().getTickLabels();
        AxisTickLabels axisY2TickLabels = shape2.getChart().getAxisY().getTickLabels();

        checkOrientation(axisX1TickLabels, ShapeTextOrientation.HORIZONTAL, 85);
        checkOrientation(axisY1TickLabels, ShapeTextOrientation.HORIZONTAL, -90);
        checkOrientation(axisX2TickLabels, ShapeTextOrientation.WORD_ART_VERTICAL, 20);
        checkOrientation(axisY2TickLabels, ShapeTextOrientation.HORIZONTAL, 0);

        axisX1TickLabels.setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT);
        axisX1TickLabels.setRotation(0);

        axisY1TickLabels.setRotation(-10);

        axisX2TickLabels.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);

        axisY2TickLabels.setRotation(10);

        checkOrientation(axisX1TickLabels, ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 0);
        checkOrientation(axisY1TickLabels, ShapeTextOrientation.HORIZONTAL, -10);
        checkOrientation(axisX2TickLabels, ShapeTextOrientation.VERTICAL_FAR_EAST, 20);
        checkOrientation(axisY2TickLabels, ShapeTextOrientation.HORIZONTAL, 10);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTickLabelsOrientation.docx", null, true);

        shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        axisX1TickLabels = shape1.getChart().getAxisX().getTickLabels();
        axisY1TickLabels = shape1.getChart().getAxisY().getTickLabels();
        shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        axisX2TickLabels = shape2.getChart().getAxisX().getTickLabels();
        axisY2TickLabels = shape2.getChart().getAxisY().getTickLabels();

        checkOrientation(axisX1TickLabels, ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 0);
        checkOrientation(axisY1TickLabels, ShapeTextOrientation.HORIZONTAL, -10);
        checkOrientation(axisX2TickLabels, ShapeTextOrientation.VERTICAL_FAR_EAST, 20);
        checkOrientation(axisY2TickLabels, ShapeTextOrientation.HORIZONTAL, 10);
    }

    /// <summary>
    /// Checks the <see cref="AxisTickLabels.Orientation"/> and <see cref="AxisTickLabels.Rotation"/>
    /// properties of axis tick labels.
    /// </summary>
    private static void checkOrientation(AxisTickLabels tickLabels, /*ShapeTextOrientation*/int expectedOrientation,
        int expectedRotation)
    {
        msAssert.areEqual(expectedOrientation, tickLabels.getOrientation());
        msAssert.areEqual(expectedRotation, tickLabels.getRotation());
    }

    /// <summary>
    /// Checks properties of a <see cref="Font"/> instance.
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
    /// Changes properties of a <see cref="Font"/> instance.
    /// </summary>
    private static void changeFont(Font font, String fontName, double fontSize, Color color, boolean bold,
        boolean italic, /*Underline*/int underline, int kerning, int spacing, boolean doubleStrikeThrough,
        boolean strikeThrough, boolean allCaps, boolean smallCaps, /*RunVerticalAlignment*/int verticalAlignment, int localeId)
    {
        TestChartUtil.setFontProperties(font, fontName, fontSize, kerning, spacing, bold, italic, strikeThrough,
            doubleStrikeThrough, allCaps, smallCaps, color, underline, verticalAlignment, localeId);
    }

    /// <summary>
    /// Sets the <see cref="ChartAxis.HasMajorGridlines"/> and <see cref="ChartAxis.HasMinorGridlines"/> properties
    /// of the first chart in the document and checks golds when gridlines are shown and when they are hidden.
    /// </summary>
    private static void checkGridlines(Document doc, String outputFileNameTemplate) throws Exception
    {
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        Chart chart = shape.getChart();
        ChartAxis xAxis = chart.getAxisX();
        ChartAxis yAxis = chart.getAxisY();

        xAxis.hasMajorGridlines(true);
        xAxis.hasMinorGridlines(true);
        yAxis.hasMajorGridlines(true);
        yAxis.hasMinorGridlines(true);

        TestUtil.saveCheckGoldExportOnly(doc, msString.format(outputFileNameTemplate, "ShownGridLines"));

        xAxis.hasMajorGridlines(false);
        xAxis.hasMinorGridlines(false);
        yAxis.hasMajorGridlines(false);
        yAxis.hasMinorGridlines(false);

        TestUtil.saveCheckGoldExportOnly(doc, msString.format(outputFileNameTemplate, "HiddenGridLines"));
    }

    /// <summary>
    /// Checks the properties of the passed <see cref="ChartAxisTitle"/> instance.
    /// </summary>
    private static void checkTitle(ChartAxisTitle title, boolean expectedShow, boolean expectedOverlay, String expectedText)
    {
        msAssert.areEqual(expectedShow, title.getShow());
        msAssert.areEqual(expectedOverlay, title.getOverlay());
        msAssert.areEqual(expectedText, title.getText());
    }
}

