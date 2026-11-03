// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This class represents a chart of the http://schemas.microsoft.com/office/drawing/2014/chartex schema 
/// [MS-ODRAWXML]. Charts of the schema are introduced in MS Office 2016.
/// </summary>
class DmlChartExChart extends DmlCartesianPlaneChart
{
    /// <summary>
    /// Clones this DML chart.
    /// </summary>
    /*override*/ DmlChart deepClone()
    {
        DmlChartExChart lhs = (DmlChartExChart)super.deepClone();

        return lhs;
    }

    /// <summary>
    /// Returns chart type. Detailed type is defined in chart series.
    /// </summary>
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.CHART_EX_CHART; }

    /// <summary>
    /// Gets the type of series that such DML chart contains by default.
    /// </summary>
    /*override*/ /*ChartSeriesType*/int getDefaultSeriesType()
    {
        // Cannot determine series type because it is defined using ChartSeries.LayoutType.
        return ChartSeriesType.BOX_AND_WHISKER;
    }

    /// <summary>
    /// Gets the X axis.
    /// </summary>
    public /*override*/ ChartAxis getAxX() 
    {
        ChartAxis axis = (getSeriesType() == ChartSeriesType.FUNNEL)
            ? getPlotArea().getAxis(getAxIdX())
            : getPlotArea().getCrossAxis(getAxIdY(), getAxIdX());

        if (axis == null)
        {
            ChartAxis stubXAxis = new ChartAxis(ChartAxisType.CATEGORY, this, getDocument(), true);
            stubXAxis.setAxId(getAxIdX());
            stubXAxis.setPlotArea(getPlotArea());
            getPlotArea().addAxis(stubXAxis);
            mStubXId = stubXAxis.getAxId();
            axis = stubXAxis;
        }

        if (!mIsDefaultXSet)
            setXDefaults(axis);

        return axis;
    }

    private void setXDefaults(ChartAxis axis)
    {
        if (getSeriesType() == ChartSeriesType.FUNNEL)
        {
            axis.setAxPos(com.aspose.words.AxisPosition.LEFT);
            axis.setCrossAx(axis.getAxId() == 1 ? 0 : 1);
            axis.setReverseOrder(true);
        }
        else
        {
            axis.setAxPos(com.aspose.words.AxisPosition.BOTTOM);
        }

        mIsDefaultXSet = true;
    }

    /// <summary>
    /// Gets the Y axis.
    /// </summary>
    public /*override*/ ChartAxis getAxY()
    {
        boolean isFunnel = (getSeriesType() == ChartSeriesType.FUNNEL);
        ChartAxis axis = isFunnel ? getPlotArea().getAxis(getAxIdX() == 0 ? 1 : 0) : getPlotArea().getAxis(getAxIdY());

        if (axis == null)
        {
            ChartAxis stubYAxis = new ChartAxis(ChartAxisType.VALUE, this, getDocument(), true);
            mStubYId = getAxIdX() == 0 ? 1 : 0;
            stubYAxis.setAxId(mStubYId);
            stubYAxis.setPlotArea(getPlotArea());
            stubYAxis.setHidden(true);
            stubYAxis.setAxPos(isFunnel ? com.aspose.words.AxisPosition.BOTTOM : com.aspose.words.AxisPosition.LEFT);
            getPlotArea().addAxis(stubYAxis);
            axis = stubYAxis;
        }

        return axis;
    }
 
    ///// <summary>
    ///// Indicates whether the axis is stub and should not be written.
    ///// </summary>
    boolean isStubAxis(int axId)
    {
        return (mStubXId == axId) || (mStubYId == axId);
    }

    private int mStubXId = Integer.MIN_VALUE;
    private int mStubYId = Integer.MIN_VALUE;
    private boolean mIsDefaultXSet = false;
}

