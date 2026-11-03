// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class represents pie chart.
/// 5.7.2.142 pieChart (Pie Charts).
/// </summary>
class DmlPieChart extends DmlChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.PIE_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.PIE; }

    /*override*/ boolean getRenderLegendForDataPoints() { return true; }

    int getFirstSliceAng() { return getUseZeroFirstSliceAng() ? 0 : (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.FIRST_SLICE_ANG); }
    void setFirstSliceAng(int value) { getChartPr().setProperty(DmlChartAttrs.FIRST_SLICE_ANG, value); }

    private boolean getUseZeroFirstSliceAng() { return (getChartType() == DmlChartType.PIE_3_D_CHART) || (getChartType() == DmlChartType.OF_PIE_CHART); }
}

