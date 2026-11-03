// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents stock chart.
/// 5.7.2.199 stockChart (Stock Charts)
/// </summary>
class DmlStockChart extends  DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.STOCK_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.STOCK; }

    DmlChartSpPr getDropLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.DROP_LINES); }

    boolean isDropLinesVisible() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE); }

    DmlChartSpPr getHiLowLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.HI_LOW_LINES); }

    DmlChartUpDownBars getUpDownBars() { return (DmlChartUpDownBars)getChartPr().getProperty(DmlChartAttrs.UP_DOWN_BARS); }
}

