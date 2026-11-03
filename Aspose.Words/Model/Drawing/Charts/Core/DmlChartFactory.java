// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Factory for creating DML charts.
/// </summary>
/*static*/ class DmlChartFactory
{
	/* Simulation of static class by using private constructor */
	private DmlChartFactory()
	{}

    static DmlChart createChart(/*DmlChartType*/int chartType)
    {
        switch (chartType)
        {
            case DmlChartType.AREA_CHART:
                return new DmlAreaChart();
            case DmlChartType.AREA_3_D_CHART:
                return new DmlArea3DChart();
            case DmlChartType.LINE_CHART:
                return new DmlLineChart();
            case DmlChartType.LINE_3_D_CHART:
                return new DmlLine3DChart();
            case DmlChartType.STOCK_CHART:
                return new DmlStockChart();
            case DmlChartType.RADAR_CHART:
                return new DmlRadarChart();
            case DmlChartType.SCATTER_CHART:
                return new DmlScatterChart();
            case DmlChartType.PIE_CHART:
                return new DmlPieChart();
            case DmlChartType.PIE_3_D_CHART:
                return new DmlPie3DChart();
            case DmlChartType.DOUGHNUT_CHART:
                return new DmlDoughnutChart();
            case DmlChartType.BAR_CHART:
                return new DmlBarChart();
            case DmlChartType.BAR_3_D_CHART:
                return new DmlBar3DChart();
            case DmlChartType.OF_PIE_CHART:
                return new DmlOfPieChart();
            case DmlChartType.SURFACE_CHART:
                return new DmlSurfaceChart();
            case DmlChartType.SURFACE_3_D_CHART:
                return new DmlSurface3DChart();
            case DmlChartType.BUBBLE_CHART:
                return new DmlBubbleChart();
            default:
                throw new IllegalArgumentException("Unexpected chart type.");
        }
    }
}

