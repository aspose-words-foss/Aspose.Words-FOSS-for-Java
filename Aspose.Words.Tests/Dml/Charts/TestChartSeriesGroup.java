// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import java.awt.Color;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests functionality related to chart series groups and combo charts.
/// </summary>
@Test
public class TestChartSeriesGroup
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// Tests creating a combo chart with secondary Y axis.
    /// </summary>
    @Test
    public void testCreatingComboChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.LINE, 450.0, 250.0);
        Chart chart = shape.getChart();

        msAssert.areEqual(1, chart.getSeriesGroups().getCount());
        ChartSeriesGroup seriesGroup = chart.getSeriesGroups().get(0);
        msAssert.areEqual(ChartSeriesType.LINE, seriesGroup.getSeriesType());
        msAssert.areEqual(AxisGroup.PRIMARY, seriesGroup.getAxisGroup());

        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        series.add("Primary Group Series 1", categories, new double[] { 2.0, 3.0, 4.0 });
        series.add("Primary Group Series 2", categories, new double[] { 5.0, 2.0, 3.0 });

        ChartSeriesGroup newSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.LINE);
        newSeriesGroup.setAxisGroup(AxisGroup.SECONDARY);
        newSeriesGroup.getAxisX().setHidden(true);
        newSeriesGroup.getAxisY().getTitle().setShow(true);
        newSeriesGroup.getAxisY().getTitle().setText("Secondary Y axis");

        newSeriesGroup.getSeries().add("Secondary Group Series", categories, new double[] { 13.0, 11.0, 16.0 });

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestCreatingComboChart.docx");
    }

    /// <summary>
    /// Tests creating a combo chart with secondary X and Y axes.
    /// </summary>
    @Test
    public void testComboChartWithSecondaryAxes() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        series.add("Series 1", categories, new double[] { 2.0, 3.0, 4.0 });
        series.add("Series 2", categories, new double[] { 5.0, 2.0, 3.0 });

        ChartSeriesGroup seriesGroup = chart.getSeriesGroups().add(ChartSeriesType.SCATTER);
        seriesGroup.setAxisGroup(AxisGroup.SECONDARY);
        ChartAxisTitle axisTitle = seriesGroup.getAxisX().getTitle();
        axisTitle.setShow(true);
        axisTitle.setOverlay(false);
        axisTitle.setText("Secondary X axis");
        seriesGroup.getSeries().add("Series 3", new double[] { 10.0, 15.0, 24.0 }, new double[] { 3.0, 1.0, 6.0 });

        chart.getTitle().setOverlay(false);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestComboChartWithSecondaryXAxis.docx");
    }

    /// <summary>
    /// Tests creating a Volume-Open-High-Low-Close stock chart similar to the one that can be created in MS Word.
    /// </summary>
    @Test
    public void testStockComboChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        DateTime[] categories = new DateTime[] { new DateTime(2002, 5, 1), new DateTime(2002, 6, 1),
            new DateTime(2002, 7, 1), new DateTime(2002, 8, 1), new DateTime(2002, 9, 1) };
        series.addInternal("Volume", categories, new double[] { 70.0, 120.0, 150.0, 135.0, 148.0 });

        ChartSeriesGroup stockSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.STOCK);
        stockSeriesGroup.setAxisGroup(AxisGroup.SECONDARY);
        stockSeriesGroup.getAxisX().setHidden(true);

        ChartSeriesCollection stockSeries = stockSeriesGroup.getSeries();
        ChartSeries open = stockSeries.addInternal("Open", categories, new double[] { 44.0, 25.0, 38.0, 50.0, 34.0 });
        stockSeries.addInternal("High", categories, new double[] { 55.0, 57.0, 57.0, 58.0, 58.0 });
        stockSeries.addInternal("Low", categories, new double[] { 12.0, 12.0, 13.0, 11.0, 25.0 });
        ChartSeries close = stockSeries.addInternal("Close", categories, new double[] { 25.0, 38.0, 50.0, 35.0, 43.0 });

        // AW doesn't allow showing Up-Down bars, so use markers to display open and close values.
        open.getMarker().setSymbol(MarkerSymbol.DASH);
        open.getMarker().setSize(20);
        open.getMarker().getFormat().getFill().solid(Color.YELLOW);
        open.getMarker().getFormat().getStroke().setVisible(false);
        close.getMarker().setSymbol(MarkerSymbol.DASH);
        close.getMarker().setSize(20);
        close.getMarker().getFormat().getFill().solid(Color.RED);
        close.getMarker().getFormat().getStroke().setVisible(false);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestStockComboChart.docx");
    }

    /// <summary>
    /// Tests saving a chart with series groups that have no series, and then using it as a template for
    /// a combo chart.
    /// </summary>
    @Test
    public void testSavingSeriesGroupWithoutSeries() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        series.add("Series 1", categories, new double[] { 2.0, 3.0, 4.0 });
        series.add("Series 2", categories, new double[] { 5.0, 2.0, 3.0 });

        ChartSeriesGroup lineSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.LINE);
        msAssert.areEqual(AxisGroup.PRIMARY, lineSeriesGroup.getAxisGroup());
        ChartSeriesGroup scatterSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.SCATTER);
        // Because column and line series have category X values, but a scatter series has numeric X values, the
        // added scatter series group is automatically linked to the secondary axis group like MS Word does.
        msAssert.areEqual(AxisGroup.SECONDARY, scatterSeriesGroup.getAxisGroup());

        final String FILE_NAME = "Model\\Charts\\TestSavingSeriesGroupWithoutSeries.docx";
        doc = TestUtil.saveOpen(doc, FILE_NAME, null, false);

        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        msAssert.areEqual(ChartSeriesType.LINE, chart.getSeriesGroups().get(1).getSeriesType());
        chart.getSeriesGroups().get(1).getSeries().add("Line Group Series", categories, new double[] { 7.0, 5.0, 8.0 });
        msAssert.areEqual(ChartSeriesType.SCATTER, chart.getSeriesGroups().get(2).getSeriesType());
        chart.getSeriesGroups().get(2).getSeries().add("Scatter Group Series",
            new double[] { 10.0, 50.0, 100.0 }, new double[] { 17.0, 25.0, 8.0 });

        TestUtil.saveCheckGoldExportOnly(doc, FILE_NAME);
    }





    /// <summary>
    /// Tests creating a combo chart that contains a series group which has no axes (pie series group).
    /// </summary>
    @Test
    public void testComboChartWithNoAxesSeriesGroup() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape1 = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart1 = shape1.getChart();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        double[] values = new double[] { 7.0, 3.0, 4.0 };

        chart1.getSeries().clear();
        ChartSeries columnSeries1 = chart1.getSeries().add("Series 1", categories, values);
        columnSeries1.getFormat().getStroke().setColor(Color.WHITE);
        columnSeries1.getFormat().getFill().setColor(new Color((0xffc000), true));

        ChartSeriesGroup pieSeriesGroup = chart1.getSeriesGroups().add(ChartSeriesType.PIE);
        pieSeriesGroup.getSeries().add("Series 2", categories, values);
        msAssert.areEqual(AxisGroup.PRIMARY, pieSeriesGroup.getAxisGroup());

        // And do the same with adding series groups in reverse order.

        Shape shape2 = builder.insertChart(ChartType.PIE, 450.0, 250.0);
        Chart chart2 = shape2.getChart();

        chart2.getSeries().clear();
        chart2.getSeries().add("Series 1", categories, values);

        ChartSeriesGroup columnSeriesGroup = chart2.getSeriesGroups().add(ChartSeriesType.COLUMN);
        ChartSeries columnSeries2 = columnSeriesGroup.getSeries().add("Series 2", categories, values);
        columnSeries2.getFormat().getStroke().setColor(Color.WHITE);
        columnSeries2.getFormat().getFill().setColor(new Color((0xffc000), true));
        msAssert.areEqual(AxisGroup.PRIMARY, columnSeriesGroup.getAxisGroup());

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestComboChartWithPieSeriesGroup.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartSeriesGroup.GapWidth"/> and <see cref="ChartSeriesGroup.Overlap"/> properties
    /// in created pre-Word 2016 charts.
    /// </summary>
    @Test
    public void testSeriesGroupGapWidthAndOverlapInNewChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Column chart.
        Shape columnShape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        ChartSeriesGroup columnSeriesGroup = columnShape.getChart().getSeriesGroups().get(0);

        msAssert.areEqual(219, columnSeriesGroup.getGapWidth());
        msAssert.areEqual(-27, columnSeriesGroup.getOverlap());

        columnSeriesGroup.setGapWidth(100);
        columnSeriesGroup.setOverlap(10);

        // Bar chart.
        Shape barShape = builder.insertChart(ChartType.BAR, 450.0, 250.0);
        ChartSeriesGroup barSeriesGroup = barShape.getChart().getSeriesGroups().get(0);

        msAssert.areEqual(182, barSeriesGroup.getGapWidth());
        msAssert.areEqual(0, barSeriesGroup.getOverlap());

        barSeriesGroup.setGapWidth(100);
        barSeriesGroup.setOverlap(-10);

        // Pie-of-pie chart.
        Shape ofPieShape = builder.insertChart(ChartType.PIE_OF_PIE, 450.0, 250.0);
        ChartSeriesGroup ofPieSeriesGroup = ofPieShape.getChart().getSeriesGroups().get(0);

        msAssert.areEqual(100, ofPieSeriesGroup.getGapWidth());

        ofPieSeriesGroup.setGapWidth(50);

        // Pie-of-bar chart.
        Shape ofBarShape = builder.insertChart(ChartType.PIE_OF_BAR, 450.0, 250.0);
        ChartSeriesGroup ofBarSeriesGroup = ofBarShape.getChart().getSeriesGroups().get(0);

        msAssert.areEqual(100, ofBarSeriesGroup.getGapWidth());

        ofBarSeriesGroup.setGapWidth(200);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestSeriesGroupGapWidthAndOverlap.docx");
    }


    /// <summary>
    /// Tests the <see cref="ChartSeriesGroup.BubbleScale"/> property.
    /// </summary>
    @Test
    public void testSeriesGroupBubbleScale() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.BUBBLE_3_D, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);

        msAssert.areEqual(100, seriesGroup.getBubbleScale());

        seriesGroup.setBubbleScale(10);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestSeriesGroupBubbleScale.docx");
    }

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.GapWidth"/> property is set in a series
    /// group with an unsupported series type.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe property cannot be set for a series group of this type.\\E", dataProvider = "testGapWidthInUnsupportedSeriesGroupDataProvider")
    public void testGapWidthInUnsupportedSeriesGroup(String fileName) throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\" + fileName);
        ChartSeriesGroup seriesGroup = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeriesGroups().get(0);
        seriesGroup.setGapWidth(100);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGapWidthInUnsupportedSeriesGroupDataProvider")
	public static Object[][] testGapWidthInUnsupportedSeriesGroupDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Word2016Charts\\Treemap.docx"},
			{"TestLineChart.docx"},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.Overlap"/> property is set in a series
    /// group with an unsupported series type.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe property cannot be set for a series group of this type.\\E", dataProvider = "testOverlapInUnsupportedSeriesGroupDataProvider")
    public void testOverlapInUnsupportedSeriesGroup(String fileName) throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\" + fileName);
        ChartSeriesGroup seriesGroup = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeriesGroups().get(0);
        seriesGroup.setOverlap(15);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testOverlapInUnsupportedSeriesGroupDataProvider")
	public static Object[][] testOverlapInUnsupportedSeriesGroupDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Word2016Charts\\Treemap.docx"},
			{"TestLineChart.docx"},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.BubbleScale"/> property is set in a series
    /// group with an unsupported series type.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe property cannot be set for a series group of this type.\\E", dataProvider = "testBubbleScaleInUnsupportedSeriesGroupDataProvider")
    public void testBubbleScaleInUnsupportedSeriesGroup(String fileName) throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\" + fileName);
        ChartSeriesGroup seriesGroup = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeriesGroups().get(0);
        seriesGroup.setBubbleScale(15);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBubbleScaleInUnsupportedSeriesGroupDataProvider")
	public static Object[][] testBubbleScaleInUnsupportedSeriesGroupDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Word2016Charts\\Treemap.docx"},
			{"TestLineChart.docx"},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.GapWidth"/> property is set to a value
    /// outside the allowable range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testGapWidthValueRangeDataProvider")
    public void testGapWidthValueRange(int gapWidth) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);
        seriesGroup.setGapWidth(gapWidth);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGapWidthValueRangeDataProvider")
	public static Object[][] testGapWidthValueRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{-1},
			{501},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.Overlap"/> property is set to a value
    /// outside the allowable range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testOverlapValueRangeDataProvider")
    public void testOverlapValueRange(int overlap) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);
        seriesGroup.setOverlap(overlap);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testOverlapValueRangeDataProvider")
	public static Object[][] testOverlapValueRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{-101},
			{101},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when the <see cref="ChartSeriesGroup.BubbleScale"/> property is set to a value
    /// outside the allowable range.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "testBubbleScaleValueRangeDataProvider")
    public void testBubbleScaleValueRange(int bubbleScale) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.BUBBLE_3_D, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);
        seriesGroup.setBubbleScale(bubbleScale);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBubbleScaleValueRangeDataProvider")
	public static Object[][] testBubbleScaleValueRangeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{-1},
			{301},
		};
	}

    /// <summary>
    /// Tests that an exception occurs when adding a new series group to a Word 2016 chart. Word 2016 charts do not
    /// support combo charts and multiple series groups.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QYou cannot add series groups to charts of this type.\\E")
    public void testAddingChartGroupToWord2016Chart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\BoxWhisker.docx");
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        chart.getSeriesGroups().add(ChartSeriesType.COLUMN);
    }

    /// <summary>
    /// Tests that an exception occurs when adding a new series group to a 3D chart. 3D charts do not support combo
    /// charts and multiple series groups.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QYou cannot add series groups to charts of this type.\\E")
    public void testAddingSeriesGroupTo3dChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN_3_D, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().add(ChartSeriesType.COLUMN);
    }

    /// <summary>
    /// Tests that an exception occurs when adding a new series group with a series type that is not supported by
    /// combo charts.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QYou cannot add series groups of this type.\\E")
    public void testAdding3dSeriesGroup() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().add(ChartSeriesType.COLUMN_3_D);
    }

    /// <summary>
    /// Tests that an exception occurs when removing all series groups from a chart.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThere must be at least one series group in a chart.\\E")
    public void testRemovingAllChartGroups() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().removeAt(0);
    }

    /// <summary>
    /// Tests that an exception occurs when changing the <see cref="ChartSeriesGroup.AxisGroup"/> property in a
    /// non-combo chart.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QOnly a combo chart, that is, a chart with multiple series groups can have secondary axes.\\E")
    public void testChangingAxisGroupInNonComboChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().get(0).setAxisGroup(AxisGroup.SECONDARY);
    }

    /// <summary>
    /// Tests that an exception occurs when changing the <see cref="ChartSeriesGroup.AxisGroup"/> property in a
    /// combo chart, in which only one series group has axes.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QYou cannot create secondary axes for a combo chart with a single series group that can have axes.\\E")
    public void testChangingAxisGroupWithSingleSeriesGroupWithAxes() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().add(ChartSeriesType.PIE);
        chart.getSeriesGroups().get(0).setAxisGroup(AxisGroup.SECONDARY);
    }

    /// <summary>
    /// Tests that an exception occurs when changing the <see cref="ChartSeriesGroup.AxisGroup"/> property in a
    /// series group that has no axes.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QAxes are not supported by this type of series group.\\E")
    public void testChangingAxisGroupInSeriesGroupWithoutAxes() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();
        chart.getSeriesGroups().add(ChartSeriesType.LINE);
        ChartSeriesGroup pieSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.PIE);
        pieSeriesGroup.setAxisGroup(AxisGroup.SECONDARY);
    }

    /// <summary>
    /// WORDSNET-17134 Manipulation of Doughnut Chart Style
    /// New properties have been added to the <see cref="ChartSeriesGroup"/> class to define pie chart style.
    /// </summary>
    @Test
    public void testPieChartStyle() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestPieChartStyle";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        ChartSeriesGroup seriesGroup1 = shapes.get(0).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup1, 20, 120, -1, -1);

        ChartSeriesGroup seriesGroup2 = shapes.get(1).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup2, 4, 3, -1, -1);

        ChartSeriesGroup seriesGroup3 = shapes.get(2).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup3, -1, 10, 90, -1);

        ChartSeriesGroup seriesGroup4 = shapes.get(3).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup4, 5, -1, -1, 125);

        ChartSeriesGroup seriesGroup5 = shapes.get(4).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup5, -1, -1, -1, 95);

        // Update the charts.
        changePieChartProperties(seriesGroup1, 0, 270, -1, -1);
        changePieChartProperties(seriesGroup2, 20, 90, -1, -1);
        changePieChartProperties(seriesGroup3, 5, 0, 50, -1);
        changePieChartProperties(seriesGroup4, 80, -1, -1, 20);
        changePieChartProperties(seriesGroup5, 40, -1, -1, 50);

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        shapes = doc.getFirstSection().getBody().getShapes();

        seriesGroup1 = shapes.get(0).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup1, 0, 270, -1, -1);

        seriesGroup2 = shapes.get(1).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup2, 20, 90, -1, -1);

        seriesGroup3 = shapes.get(2).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup3, 5, 0, 50, -1);

        seriesGroup4 = shapes.get(3).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup4, 80, -1, -1, 20);

        seriesGroup5 = shapes.get(4).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup5, 40, -1, -1, 50);
    }

    /// <summary>
    /// Tests defining style of new pie charts.
    /// </summary>
    @Test
    public void testStyleOfNewPieChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.PIE, 450.0, 250.0);
        ChartSeriesGroup seriesGroup1 = shape.getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup1, -1, 0, -1, -1);

        shape = builder.insertChart(ChartType.PIE_3_D, 450.0, 250.0);
        ChartSeriesGroup seriesGroup2 = shape.getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup2, -1, 0, -1, -1);

        shape = builder.insertChart(ChartType.DOUGHNUT, 450.0, 250.0);
        ChartSeriesGroup seriesGroup3 = shape.getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup3, -1, 0, 75, -1);

        shape = builder.insertChart(ChartType.PIE_OF_PIE, 450.0, 250.0);
        ChartSeriesGroup seriesGroup4 = shape.getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup4, -1, -1, -1, 75);

        shape = builder.insertChart(ChartType.PIE_OF_BAR, 450.0, 250.0);
        ChartSeriesGroup seriesGroup5 = shape.getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup5, -1, -1, -1, 75);

        // Update the charts.
        changePieChartProperties(seriesGroup1, 1, 90, -1, -1);
        changePieChartProperties(seriesGroup2, 10, 20, -1, -1);
        changePieChartProperties(seriesGroup3, 0, 180, 10, -1);
        changePieChartProperties(seriesGroup4, 0, -1, -1, 150);
        changePieChartProperties(seriesGroup5, 4, -1, -1, 50);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestStyleOfNewPieChart", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        seriesGroup1 = shapes.get(0).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup1, 1, 90, -1, -1);

        seriesGroup2 = shapes.get(1).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup2, 10, 20, -1, -1);

        seriesGroup3 = shapes.get(2).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup3, 0, 180, 10, -1);

        seriesGroup4 = shapes.get(3).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup4, 0, -1, -1, 150);

        seriesGroup5 = shapes.get(4).getChart().getSeriesGroups().get(0);
        checkPieChartProperties(seriesGroup5, 4, -1, -1, 50);
    }

    /// <summary>
    /// Checks pie chart specific properties.
    /// </summary>
    private static void checkPieChartProperties(ChartSeriesGroup seriesGroup, int expectedExplosion,
        int expectedFirstSliceAngle, int expectedDoughnutHoleSize, int expectedSecondSectionSize)
    {
        msAssert.areEqual(expectedExplosion, seriesGroup.getSeries().get(0).getExplosion());

        if (expectedFirstSliceAngle >= 0)
            msAssert.areEqual(expectedFirstSliceAngle, seriesGroup.getFirstSliceAngle());

        if (expectedDoughnutHoleSize >= 0)
            msAssert.areEqual(expectedDoughnutHoleSize, seriesGroup.getDoughnutHoleSize());

        if (expectedSecondSectionSize >= 0)
            msAssert.areEqual(expectedSecondSectionSize, seriesGroup.getSecondSectionSize());
    }

    /// <summary>
    /// Changes pie chart specific properties.
    /// </summary>
    private static void changePieChartProperties(ChartSeriesGroup seriesGroup, int explosion,
        int firstSliceAngle, int doughnutHoleSize, int secondSectionSize)
    {
        seriesGroup.getSeries().get(0).setExplosion(explosion);

        if (firstSliceAngle >= 0)
            seriesGroup.setFirstSliceAngle(firstSliceAngle);

        if (doughnutHoleSize >= 0)
            seriesGroup.setDoughnutHoleSize(doughnutHoleSize);

        if (secondSectionSize >= 0)
            seriesGroup.setSecondSectionSize(secondSectionSize);
    }
}

