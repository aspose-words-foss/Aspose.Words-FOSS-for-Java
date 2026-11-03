// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class represents line 3d chart.
/// 5.7.2.97 line3DChart (3D Line Charts)
/// </summary>
class DmlLine3DChart extends DmlLineChart implements IDmlChart3D
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.LINE_3_D_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.LINE_3_D; }

    /// <summary>
    /// Returns id of the Z-axis. If the z-axis is not specified, -1 is returned.
    /// </summary>
    /// <remarks>
    /// 5.7.2.9 The possible values for id are defined by the XML Schema unsignedInt datatype. 
    /// The value space of xsd:unsignedInt is the range of integers between 0 and 4294967295
    /// </remarks>
    public int getAxIdZ()
    {
        Object axIdZ = getChartPr().getProperty(DmlChartAttrs.AX_ID_Z);
        return axIdZ != null ? (/*int*/Integer)axIdZ : -1;
    }

    public ChartAxis getAxZ() { return getPlotArea().getAxis(getAxIdZ()); }

    int getGapDepth() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.GAP_DEPTH); }
    void setGapDepth(int value) { getChartPr().setProperty(DmlChartAttrs.GAP_DEPTH, value); }
}

