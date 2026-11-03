// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class represents surface 3d chart.
/// 5.7.2.204 surface3DChart (3D Surface Charts)
/// </summary>
class DmlSurface3DChart extends DmlSurfaceChart
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.SURFACE_3_D_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType() { return ChartSeriesType.SURFACE_3_D; }
}

