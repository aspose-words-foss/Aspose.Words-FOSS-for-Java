// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents line chart.
/// 5.7.2.98 lineChart (Line Charts).
/// </summary>
class DmlLineChart extends DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.LINE_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType()
    {
        switch (getChartPr().getGrouping())
        {
            case Grouping.STACKED:
                return ChartSeriesType.LINE_STACKED;
            case Grouping.PERCENT_STACKED:
                return ChartSeriesType.LINE_PERCENT_STACKED;
            default:
                return ChartSeriesType.LINE;
        }
    }

    DmlChartSpPr getDropLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.DROP_LINES); }

    boolean isDropLinesVisible() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE); }

    /*Grouping*/int getGrouping() { return getChartPr().getGrouping(); }
    void setGrouping(/*Grouping*/int value) { getChartPr().setGrouping(value); }

    DmlChartSpPr getHiLowLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.HI_LOW_LINES); }

    boolean isHiLowLinesVisible() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.IS_HI_LOW_LINES_VISIBLE); }

    boolean getShowMarker() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.SHOW_MARKER); }
    void setShowMarker(boolean value) { getChartPr().setProperty(DmlChartAttrs.SHOW_MARKER, value); }

    boolean getSmooth() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.SMOOTH); }
    void setSmooth(boolean value) { getChartPr().setProperty(DmlChartAttrs.SMOOTH, value); }

    DmlChartUpDownBars getUpDownBars() { return (DmlChartUpDownBars)getChartPr().getProperty(DmlChartAttrs.UP_DOWN_BARS); }
}

