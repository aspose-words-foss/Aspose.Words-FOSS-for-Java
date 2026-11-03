// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents scatter chart.
/// 5.7.2.162 scatterChart (Scatter Charts).
/// </summary>
class DmlScatterChart extends DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.SCATTER_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.SCATTER; }

    /*ScatterStyle*/int getScatterStyle() { return (/*ScatterStyle*/Integer)getChartPr().getProperty(DmlChartAttrs.SCATTER_STYLE); }
    void setScatterStyle(/*ScatterStyle*/int value) { getChartPr().setProperty(DmlChartAttrs.SCATTER_STYLE, value); }

    /// <summary>
    /// Returns true if line must be rendered for the chart.
    /// </summary>
    boolean getShowLine()
    {
        switch (getScatterStyle())
        {
            case ScatterStyle.MARKER:
            case ScatterStyle.NONE:
                return false;
            case ScatterStyle.LINE:
            case ScatterStyle.SMOOTH:
            case ScatterStyle.LINE_MARKER:
            case ScatterStyle.SMOOTH_MARKER:
            default:
                return true;
        }
    }

    /// <summary>
    /// Returns true if markers must be rendered for the chart.
    /// </summary>
    boolean getShowMarker()
    {
        switch (getScatterStyle())
        {
            case ScatterStyle.MARKER:
            case ScatterStyle.LINE_MARKER:
            case ScatterStyle.SMOOTH_MARKER:
                return true;
            case ScatterStyle.LINE:
            case ScatterStyle.SMOOTH:
            case ScatterStyle.NONE:
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true if points must be connected with smooth lines for the chart.
    /// </summary>
    boolean getSmooth()
    {
        switch (getScatterStyle())
        {
            case ScatterStyle.SMOOTH_MARKER:
            case ScatterStyle.SMOOTH:
                return true;
            case ScatterStyle.MARKER:
            case ScatterStyle.LINE_MARKER:
            case ScatterStyle.LINE:
            case ScatterStyle.NONE:
            default:
                return false;
        }
    }
}

