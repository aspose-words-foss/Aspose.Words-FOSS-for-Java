// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class represents 3d bar chart.
/// 5.7.2.15 bar3DChart (3D Bar Charts).
/// </summary>
class DmlBar3DChart extends DmlBarChart implements IDmlChart3D
{
    /*override*/ /*DmlChartType*/int getChartType() { return DmlChartType.BAR_3_D_CHART; }

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
                        return ChartSeriesType.BAR_3_D_STACKED;
                    case Grouping.PERCENT_STACKED:
                        return ChartSeriesType.BAR_3_D_PERCENT_STACKED;
                    default:
                        return ChartSeriesType.BAR_3_D;
                }
            }
            case BarDirection.COLUMN:
            {
                switch (getChartPr().getGrouping())
                {
                    case Grouping.STACKED:
                        return ChartSeriesType.COLUMN_3_D_STACKED;
                    case Grouping.PERCENT_STACKED:
                        return ChartSeriesType.COLUMN_3_D_PERCENT_STACKED;
                    case Grouping.CLUSTERED:
                        return ChartSeriesType.COLUMN_3_D_CLUSTERED;
                    default:
                        return ChartSeriesType.COLUMN_3_D;
                }
            }
            default:
                throw new IllegalStateException();
        }
    }

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

    Shape getShape() { return (Shape)getChartPr().getProperty(DmlChartAttrs.SHAPE); }
    void setShape(Shape value) { getChartPr().setProperty(DmlChartAttrs.SHAPE, value); }
}

