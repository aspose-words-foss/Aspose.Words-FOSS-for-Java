// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents surface chart.
/// 5.7.2.205 surfaceChart (Surface Charts)
/// </summary>
class DmlSurfaceChart extends DmlCartesianPlaneChart implements IDmlChart3D
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.SURFACE_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.SURFACE; }

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

    DmlChartBandFormats getBandFmts() { return (DmlChartBandFormats)getChartPr().getProperty(DmlChartAttrs.BAND_FMTS); }

    boolean getWireframe() { return (/*boolean*/Boolean)getChartPr().getProperty(DmlChartAttrs.WIREFRAME); }
    void setWireframe(boolean value) { getChartPr().setProperty(DmlChartAttrs.WIREFRAME, value); }
}

