// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents area chart element. 
/// 5.7.2.5 areaChart (Area Charts).
/// </summary>
class DmlAreaChart extends DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.AREA_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType()
    {
        switch (getChartPr().getGrouping())
        {
            case Grouping.STACKED:
                return ChartSeriesType.AREA_STACKED;
            case Grouping.PERCENT_STACKED:
                return ChartSeriesType.AREA_PERCENT_STACKED;
            default:
                return ChartSeriesType.AREA;
        }
    }

    DmlChartSpPr getDropLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.DROP_LINES); }

    boolean isDropLinesVisible() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE); }

    /*Grouping*/int getGrouping() { return getChartPr().getGrouping(); }
    void setGrouping(/*Grouping*/int value) { getChartPr().setGrouping(value); }
}

