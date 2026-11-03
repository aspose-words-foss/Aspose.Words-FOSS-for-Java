// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/07/2012 by Alexey Noskov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.File;


/// <summary>
/// Tests for <see cref="DmlChartReader"/>.
/// </summary>
@Test
public class TestDmlChartReader
{
    @Test
    public void testAreaChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestAreaChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());
        
        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.AREA_CHART, chart.getChartType());
        msAssert.isNotNull(chart.getSeries());
        msAssert.areEqual(2, chart.getSeries().getCount());

        DmlAreaChart areaChart = (DmlAreaChart)chart;
        msAssert.isNotNull(areaChart.getAxX());
        msAssert.areEqual(ChartAxisType.CATEGORY, areaChart.getAxX().getType());
        msAssert.areEqual(AxisCategoryType.AUTOMATIC, areaChart.getAxX().getCategoryType());
        msAssert.isTrue(areaChart.getAxX().isDateCategoryAxis());
        msAssert.isNotNull(areaChart.getAxY());
        msAssert.areEqual(ChartAxisType.VALUE, areaChart.getAxY().getType());
    }

    @Test
    public void testBarChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestBarChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.BAR_CHART, chart.getChartType());
        msAssert.isNotNull(chart.getSeries());
        msAssert.areEqual(3, chart.getSeries().getCount());

        DmlBarChart areaChart = (DmlBarChart)chart;
        msAssert.isNotNull(areaChart.getAxX());
        msAssert.areEqual(ChartAxisType.VALUE, areaChart.getAxX().getType());
        msAssert.isNotNull(areaChart.getAxY());
        msAssert.areEqual(ChartAxisType.CATEGORY, areaChart.getAxY().getType());
        msAssert.areEqual(AxisCategoryType.AUTOMATIC, areaChart.getAxY().getCategoryType());
        msAssert.isFalse(areaChart.getAxX().isDateCategoryAxis());
    }


    @Test
    public void testBubbleChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestBubbleChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.BUBBLE_CHART, chart.getChartType());
    }

    @Test
    public void testDoughnutChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestDoughnutChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.DOUGHNUT_CHART, chart.getChartType());
    }

    @Test
    public void testLineChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestLineChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.LINE_CHART, chart.getChartType());
    }

    @Test
    public void testPieChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestPieChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.PIE_CHART, chart.getChartType());
    }

    @Test
    public void testRadar() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestRadar.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();
        
        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.RADAR_CHART, chart.getChartType());
    }

    @Test
    public void testScatterChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestScatterChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.SCATTER_CHART, chart.getChartType());
    }

    @Test
    public void testStockChart() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestStockChart.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.STOCK_CHART, chart.getChartType());
    }

    @Test
    public void testSurface3D() throws Exception
    {
        DmlChartSpace chartSpace = openChart("Model\\Charts\\RawXml\\TestSurface3d.xml");
        msAssert.isNotNull(chartSpace);
        msAssert.isNotNull(chartSpace.getChartFormat());
        msAssert.isNotNull(chartSpace.getChartFormat().getPlotArea());
        msAssert.areEqual(1, chartSpace.getChartFormat().getPlotArea().getCharts().size());

        DmlChart chart = chartSpace.getFirstChart();

        msAssert.isNotNull(chart);
        msAssert.areEqual(DmlChartType.SURFACE_3_D_CHART, chart.getChartType());
    }

    private DmlChartSpace openChart(String fileName) throws Exception
    {
        fileName = TestUtil.buildTestFileName(fileName);

        DmlChartSpace chartSpace = new DmlChartSpace();

        FileStream fs = File.openRead(fileName);
        try /*JAVA: was using*/
        {
            DocxDocumentReaderStub reader = new DocxDocumentReaderStub(fs);
            DmlChartReader.read(reader, chartSpace);
        }
        finally { if (fs != null) fs.close(); }

        return chartSpace;
    }
}

