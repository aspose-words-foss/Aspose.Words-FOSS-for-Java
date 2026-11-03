// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/09/2012 by Ilya Egorov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents charts rendered on a Cartesian plane.
/// </summary>
abstract class DmlCartesianPlaneChart extends DmlChart implements IDmlChart2D
{
    /// <summary>
    /// Clones this DML chart.
    /// </summary>
    /*override*/ DmlChart deepClone()
    {
        DmlCartesianPlaneChart lhs = (DmlCartesianPlaneChart)super.deepClone();
        lhs.mAxX = null;
        lhs.mAxY = null;

        return lhs;
    }

    /// <summary>
    /// Returns id of the X-axis.
    /// </summary>
    public int getAxIdX() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.AX_ID_X); }

    /// <summary>
    /// Returns id of the Y-axis.
    /// </summary>
    public int getAxIdY() { return (/*int*/Integer)getChartPr().getProperty(DmlChartAttrs.AX_ID_Y); }

    public /*virtual*/ ChartAxis getAxX()
    {
        if (mAxX == null)
            mAxX = getPlotArea().getCrossAxis(getAxIdY(), getAxIdX());

        return mAxX;
    }

    public /*virtual*/ void setAxX(ChartAxis value) { mAxX = value; }

    public /*virtual*/ ChartAxis getAxY()
    {
        if (mAxY == null)
            mAxY = getPlotArea().getAxis(getAxIdY());

        return mAxY;
    }

    public /*virtual*/ void setAxY(ChartAxis value) { mAxY = value; }

    private ChartAxis mAxX;
    private ChartAxis mAxY;
}

