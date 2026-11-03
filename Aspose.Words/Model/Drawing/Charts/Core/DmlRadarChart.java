// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents radar chart.
/// 5.7.2.154 radarChart (Radar Charts)
/// </summary>
class DmlRadarChart extends DmlCartesianPlaneChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.RADAR_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.RADAR; }

    /*RadarStyle*/int getRadarStyle() { return (/*RadarStyle*/Integer)getChartPr().getProperty(DmlChartAttrs.RADAR_STYLE); }
    void setRadarStyle(/*RadarStyle*/int value) { getChartPr().setProperty(DmlChartAttrs.RADAR_STYLE, value); }
}

