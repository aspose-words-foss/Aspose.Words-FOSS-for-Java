// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class represents doughnut chart.
/// 5.7.2.50 doughnutChart (Doughnut Charts).
/// </summary>
class DmlDoughnutChart extends DmlPieChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.DOUGHNUT_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.DOUGHNUT; }

    int getHoleSize() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.HOLE_SIZE); }
    void setHoleSize(int value) { getChartPr().setProperty(DmlChartAttrs.HOLE_SIZE, value); }
}

