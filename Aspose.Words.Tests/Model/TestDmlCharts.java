// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/25/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Simple open/save/compare tests for DOCX document with charts.
/// Used to make sure charts are written properly from the model.
/// Use test documents from charts rendering tests, i.e. tests different types of charts.
/// </summary>
@Test (groups = "SingleThreadGroup")
public class TestDmlCharts
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

    @Test
    public void testAllStylesBarChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAllStylesBarChart.docx");
    }

    @Test
    public void testStylesPieChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestStylesPieChart.docx");
    }

    @Test
    public void testPieChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestPieChart.docx");
    }

    @Test
    public void testScatterChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestScatterChart.docx");
    }

    @Test
    public void testLineChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestLineChart.docx");
    }

    @Test
    public void testAreaChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAreaChart.docx");
    }

    @Test
    public void testBubbleChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestBubbleChart.docx");
    }

    @Test
    public void testStockChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestStockChart.docx");
    }

    @Test
    public void testRadarChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestRadarChart.docx");
    }

    @Test
    public void testAxisDelete() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestAxisDelete.docx");
    }

    @Test
    public void testMarkers() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestMarkers.docx");
    }

    @Test
    public void testSurfaceChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestSurfaceChart.docx");
    }

    @Test
    public void test3DBarCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarCharts.docx");
    }

    @Test
    public void test3DBarChartsCone() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarChartsCone.docx");
    }

    @Test
    public void test3DBarChartsCylinder() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarChartsCylinder.docx");
    }

    @Test
    public void test3DBarChartsPyramid() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DBarChartsPyramid.docx");
    }

    @Test
    public void test3DColumnCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DColumnCharts.docx");
    }

    @Test
    public void test3DColumnChartsCone() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DColumnChartsCone.docx");
    }

    @Test
    public void test3DColumnChartsCylinder() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DColumnChartsCylinder.docx");
    }

    @Test
    public void test3DColumnChartsPyramid() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DColumnChartsPyramid.docx");
    }

    @Test
    public void test3DLineCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DLineCharts.docx");
    }

    @Test
    public void test3DAreaCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DAreaCharts.docx");
    }

    @Test
    public void test3DPieCharts() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DPieCharts.docx");
    }

    @Test
    public void test3DSurfaceChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DSurfaceChart.docx");
    }

    @Test
    public void testSurfaceChartSegments() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestSurfaceChartSegments.docx");
    }

    @Test
    public void test3DSurfaceChartSegments() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Test3DSurfaceChartSegments.docx");
    }

    @Test
    public void testOfPieChart() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOfPieChart.docx");
    }

    @Test
    public void testOfPieChartValue() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOfPieChartValue.docx");
    }

    @Test
    public void testOfPieChartPercentage() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOfPieChartPercentage.docx");
    }

    @Test
    public void testOfPieChartCustom() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\TestOfPieChartCustom.docx");
    }

    /// <summary>
    /// WORDSNET-16069 Incorrect ChartDataPoint Cloning.
    /// </summary>
    /// <remarks>
    /// After cloning a DmlChart object, the clone (more precisely, objects of which DmlChart consists of) had
    /// references to internal objects of the original DmlChart object. For example, after cloning, a ChartDataPoint
    /// object of the clone had their SpPr properties referenced to the same DmlChartSpPr object.
    /// Implemented missing cloning of internal objects.
    /// </remarks>
    @Test
    public void testJira16069() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestJira16069.docx");
        DmlChart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart().getChartSpace().getFirstChart();
        ChartSeries series = chart.getSeries().get(0);
        series.addErrorBars(new DmlChartErrorBars());
        series.getTx().setFormula(new DmlChartFormula());

        DmlChart clonedChart = chart.deepClone();
        ChartSeries clonedSeries = (ChartSeries)clonedChart.getSeries().get(0);

        msAssert.areNotSame(clonedSeries, series);
        msAssert.areSame(clonedSeries.getChart(), clonedChart);
        msAssert.areNotSame(clonedChart.getDataLabels(), chart.getDataLabels());
        msAssert.areSame(clonedChart.getDataLabels().getChart(), clonedChart);
        DmlChartPr chartPr = clonedChart.getChartPr();
        msAssert.areNotSame(chartPr.getProperty(DmlChartAttrs.DROP_LINES), chart.getChartPr().getProperty(DmlChartAttrs.DROP_LINES));
        msAssert.areNotSame(chartPr.getProperty(DmlChartAttrs.BAND_FMTS), chart.getChartPr().getProperty(DmlChartAttrs.BAND_FMTS));

        msAssert.areSame(clonedSeries.getDefaultDataPoint().getChart(), clonedChart);
        msAssert.areSame(clonedSeries.getDataPoints().getSeries(), clonedSeries);
        msAssert.areSame(clonedSeries.getDataPoints().get(0).getChart(), clonedChart);
        msAssert.areNotSame(clonedSeries.getDataPoints().get(0).getSpPr(), series.getDataPoints().get(0).getSpPr());
        msAssert.areSame(clonedSeries.getYErrorBars().getSeries(), clonedSeries);
        msAssert.areNotSame(clonedSeries.getTx().getFormula(), series.getTx().getFormula());
        msAssert.areSame(clonedSeries.getDataLabels().getChart(), clonedChart);

        ChartDataLabel dataLabel = clonedSeries.getDataLabels().get(0);
        msAssert.areSame(dataLabel.getChart(), clonedChart);
        msAssert.areNotSame(dataLabel.getSpPr(), series.getDataLabels().get(0).getSpPr());
        msAssert.areNotSame(dataLabel.getTxPr(), series.getDataLabels().get(0).getTxPr());

        DmlChartTrendline trendLine = (DmlChartTrendline)series.getTrendlines().get(0);
        DmlChartTrendline clonedTrendLine = (DmlChartTrendline)clonedSeries.getTrendlines().get(0);
        msAssert.areNotSame(clonedTrendLine.getTrendlinePr(), trendLine.getTrendlinePr());
        msAssert.areNotSame(clonedTrendLine.getSpPr(), trendLine.getSpPr());
        msAssert.areNotSame(clonedTrendLine.getTrendlineLbl(), trendLine.getTrendlineLbl());
    }






    /// <summary>
    /// Tests default values of <see cref="Font"/> properties of chart elements.
    /// </summary>
    @Test
    public void testChartFontDefaults() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestChartFontDefaults.docx");

        // Pre-Word 2016 chart.
        Chart chart1 = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        checkFont(chart1.getTitle().getFont(), 18, true);
        checkFont(chart1.getAxisX().getTitle().getFont(), 10, true);
        checkFont(chart1.getAxisX().getTickLabels().getFont(), 10, false);
        checkFont(chart1.getAxisY().getDisplayUnit().getLabel().getFont(), 10, true);
        checkFont(chart1.getSeries().get(0).getDataLabels().getFont(), 10, false);
        checkFont(chart1.getLegend().getFont(), 10, false);

        // Word 2016 chart.
        Chart chart2 = doc.getFirstSection().getBody().getShapes().get(1).getChart();
        checkFont(chart2.getTitle().getFont(), 14, false);
        checkFont(chart2.getAxisX().getTitle().getFont(), 9, false);
        checkFont(chart2.getAxisX().getTickLabels().getFont(), 9, false);
        checkFont(chart2.getAxisY().getDisplayUnit().getLabel().getFont(), 9, false);
        checkFont(chart2.getSeries().get(0).getDataLabels().getFont(), 9, false);
        checkFont(chart2.getLegend().getFont(), 9, false);
    }

    /// <summary>
    /// Checks some properties of the specified <see cref="Font"/> instance.
    /// </summary>
    private void checkFont(Font font, int expectedSize, boolean expectedBold)
    {
        msAssert.areEqual(expectedSize, font.getSize());
        msAssert.areEqual(expectedBold, font.getBold());
    }
}

