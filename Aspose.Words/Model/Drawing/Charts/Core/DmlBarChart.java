// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents 2d bar chart.
/// 5.7.2.16 barChart (Bar Charts).
/// </summary>
class DmlBarChart extends DmlCartesianPlaneChart 
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.BAR_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType()
    {
        switch (getChartPr().getBarDirection())
        {
            case BarDirection.BAR:
            {
                switch (getChartPr().getGrouping())
                {
                    case Grouping.STACKED:
                        return ChartSeriesType.BAR_STACKED;
                    case Grouping.PERCENT_STACKED:
                        return ChartSeriesType.BAR_PERCENT_STACKED;
                    default:
                        return ChartSeriesType.BAR;
                }
            }
            case BarDirection.COLUMN:
            {
                switch (getChartPr().getGrouping())
                {
                    case Grouping.STACKED:
                        return ChartSeriesType.COLUMN_STACKED;
                    case Grouping.PERCENT_STACKED:
                        return ChartSeriesType.COLUMN_PERCENT_STACKED;
                    default:
                        return ChartSeriesType.COLUMN;
                }
            }
            default:
                throw new IllegalStateException("Unknown bar or column chart.");
        }
    }

    /*BarDirection*/int getBarDir() { return (/*BarDirection*/Integer)getChartPr().getProperty(DmlChartAttrs.BAR_DIR); }
    void setBarDir(/*BarDirection*/int value) { getChartPr().setProperty(DmlChartAttrs.BAR_DIR, value); }

    int getGapWidth() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.GAP_WIDTH); }
    void setGapWidth(int value) { getChartPr().setProperty(DmlChartAttrs.GAP_WIDTH, value); }

    /*Grouping*/int getGrouping() { return getChartPr().getGrouping(); }
    void setGrouping(/*Grouping*/int value) { getChartPr().setGrouping(value); }

    int getOverlap() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.OVERLAP); }
    void setOverlap(int value) { getChartPr().setProperty(DmlChartAttrs.OVERLAP, value); }

    DmlChartSpPr getSerLines() { return (DmlChartSpPr)getChartPr().getProperty(DmlChartAttrs.SER_LINES); }
}

