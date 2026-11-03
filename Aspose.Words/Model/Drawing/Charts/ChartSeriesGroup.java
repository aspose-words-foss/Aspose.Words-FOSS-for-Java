// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;


/// <summary>
/// Represents properties of a chart series group, that is, the properties of chart series of the same type
/// associated with the same axes.
/// </summary>
/// <remarks>
/// <para>Combo charts contains multiple chart series groups, with a separate group for each series type.</para>
/// <para>Also, you can create a chart series group to assign secondary axes to one or more chart series.</para>
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">
/// Working with Charts</a> documentation article.</para>
/// </remarks>
public class ChartSeriesGroup
{
    /// <summary>
    /// Ctor to create instances of this class.
    /// </summary>
    ChartSeriesGroup(DmlChart dmlChart)
    {
        mDmlChart = dmlChart;
    }

    /// <summary>
    /// Gets the necessary type of X axis for series of the specified type.
    /// </summary>
    private static /*ChartAxisType*/int getAxisXType(/*ChartSeriesType*/int seriesType)
    {
        switch (seriesType)
        {
            case ChartSeriesType.AREA:
            case ChartSeriesType.AREA_STACKED:
            case ChartSeriesType.AREA_PERCENT_STACKED:
            case ChartSeriesType.AREA_3_D:
            case ChartSeriesType.AREA_3_D_STACKED:
            case ChartSeriesType.AREA_3_D_PERCENT_STACKED:
            case ChartSeriesType.BAR:
            case ChartSeriesType.BAR_STACKED:
            case ChartSeriesType.BAR_PERCENT_STACKED:
            case ChartSeriesType.BAR_3_D:
            case ChartSeriesType.BAR_3_D_STACKED:
            case ChartSeriesType.BAR_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN:
            case ChartSeriesType.COLUMN_STACKED:
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D:
            case ChartSeriesType.COLUMN_3_D_STACKED:
            case ChartSeriesType.COLUMN_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D_CLUSTERED:
            case ChartSeriesType.LINE:
            case ChartSeriesType.LINE_STACKED:
            case ChartSeriesType.LINE_PERCENT_STACKED:
            case ChartSeriesType.LINE_3_D:
            case ChartSeriesType.RADAR:
            case ChartSeriesType.STOCK:
            case ChartSeriesType.SURFACE:
            case ChartSeriesType.SURFACE_3_D:
            case ChartSeriesType.HISTOGRAM:
            case ChartSeriesType.PARETO:
            case ChartSeriesType.PARETO_LINE:
            case ChartSeriesType.BOX_AND_WHISKER:
            case ChartSeriesType.WATERFALL:
            case ChartSeriesType.FUNNEL:
                return ChartAxisType.CATEGORY;
            case ChartSeriesType.BUBBLE:
            case ChartSeriesType.BUBBLE_3_D:
            case ChartSeriesType.SCATTER:
                return ChartAxisType.VALUE;
            case ChartSeriesType.DOUGHNUT:
            case ChartSeriesType.PIE:
            case ChartSeriesType.PIE_3_D:
            case ChartSeriesType.PIE_OF_BAR:
            case ChartSeriesType.PIE_OF_PIE:
            case ChartSeriesType.TREEMAP:
            case ChartSeriesType.SUNBURST:
            case ChartSeriesType.REGION_MAP:
                // Series of this types have no axes.
                return ChartAxisType.CATEGORY;
            default:
                throw new IllegalArgumentException();
        }
    }

    /// <summary>
    /// Gets the number of series groups that have axes.
    /// </summary>
    private int getNumberOfSeriesGroupsWithAxes()
    {
        int count = 0;

        for (DmlChart chart : getPlotArea().getCharts())
        {
            if (chart.hasAxis())
                count++;
        }

        return count;
    }

    /// <summary>
    /// Gets the type of chart series included in this group.
    /// </summary>
    public /*ChartSeriesType*/int getSeriesType() { return mDmlChart.getSeriesType(); }

    /// <summary>
    /// Gets or sets the axis group to which this series group belongs.
    /// </summary>
    public /*AxisGroup*/int getAxisGroup()
    {
        return (!mDmlChart.hasAxis() || (getAxisX() == getPlotArea().getPrimaryXAxis()) || (getAxisY() == getPlotArea().getPrimaryYAxis()))
            ? AxisGroup.PRIMARY
            : AxisGroup.SECONDARY;
    }
    public void setAxisGroup(/*AxisGroup*/int value)
    {
        if (getAxisGroup() == value)
            return;

        if (!mDmlChart.hasAxis())
            throw new IllegalStateException(UNSUPPORTED_AXES_ERROR);

        switch (value)
        {
            case AxisGroup.PRIMARY:
            {
                Debug.assertTrue(getPlotArea().getPrimaryXAxis() != null);
                Debug.assertTrue(getPlotArea().getPrimaryYAxis() != null);
                mDmlChart.setAxisX(getPlotArea().getPrimaryXAxis());
                mDmlChart.setAxisY(getPlotArea().getPrimaryYAxis());
                break;
            }
            case AxisGroup.SECONDARY:
            {
                if (getPlotArea().getCharts().size() <= 1)
                    throw new IllegalStateException(SECONDARY_AXES_IN_NON_COMBO_CHART_ERROR);

                if (getNumberOfSeriesGroupsWithAxes() <= 1)
                    throw new IllegalStateException(SECONDARY_AXES_FOR_SINGLE_SERIES_GROUP_ERROR);

                mDmlChart.setAxisX(getPlotArea().getOrCreateSecondaryXAxis());
                mDmlChart.getAxisX().setType(getAxisXType(getSeriesType()));
                mDmlChart.setAxisY(getPlotArea().getOrCreateSecondaryYAxis());

                break;
            }
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        }
    }

    /// <summary>
    /// Provides access to properties of the X axis of this series group.
    /// </summary>
    public ChartAxis getAxisX() { return mDmlChart.getAxisX(); }

    /// <summary>
    /// Provides access to properties of the Y axis of this series group.
    /// </summary>
    public ChartAxis getAxisY() { return mDmlChart.getAxisY(); }

    /// <summary>
    /// Gets a collection of series that belong to this series group.
    /// </summary>
    public ChartSeriesCollection getSeries()
    {
        if (mSeriesCollection == null)
            mSeriesCollection = new ChartSeriesCollection(mDmlChart);

        return mSeriesCollection;
    }

    /// <summary>
    /// Gets or sets the percentage of how much the series bars or columns overlap.
    /// </summary>
    /// <remarks>
    /// <para>Applies to series groups of all bar and column types.</para>
    /// <para>The range of acceptable values is from -100 to 100 inclusive. A value of 0 indicates that there is no
    /// space between bars/columns. If the value is -100, the distance between bars/columns is equal to their width.
    /// A value of 100 means that the bars/columns overlap completely.</para>
    /// </remarks>
    public int getOverlap()
    {
        DmlBarChart barChart = ms.as(mDmlChart, DmlBarChart.class);
        return (barChart != null) ? barChart.getOverlap() : 0;
    }
    public void setOverlap(int value)
    {
        DmlBarChart barChart = ms.as(mDmlChart, DmlBarChart.class);
        if (barChart == null)
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, -100, 100, "value");

        barChart.setOverlap(value);
    }

    /// <summary>
    /// Gets or sets the percentage of gap width between chart elements.
    /// </summary>
    /// <remarks>
    /// <para>Applies only to series groups of the bar, column, pie-of-bar, pie-of-pie, histogram, box&amp;whisker,
    /// waterfall and funnel types.</para>
    /// <para>The range of acceptable values is from 0 to 500 inclusive. For bar/column-based series groups, the
    /// property represents the space between bar clusters as a percentage of their width. For pie-of-pie and
    /// bar-of-pie charts, this is the space between the primary and secondary sections of the chart.</para>
    /// </remarks>
    public int getGapWidth()
    {
        if (!isGapWidthSupported())
            return 0;

        DmlBarChart barChart = ms.as(mDmlChart, DmlBarChart.class);
        if (barChart != null)
            return barChart.getGapWidth();

        DmlOfPieChart ofPieChart = ms.as(mDmlChart, DmlOfPieChart.class);
        if (ofPieChart != null)
            return ofPieChart.getGapWidth();

        Debug.assertTrue(mDmlChart.getChartType() == DmlChartType.CHART_EX_CHART);
        AxisScaling axisScaling = mDmlChart.getAxisX().getScaling();
        return !axisScaling.getGapWidth().isNullOrAuto()
            ? MathUtil.doubleToInt(axisScaling.getGapWidth().getValue() * 100.0) // Convert to percents.
            : CHART_EX_AUTO_GAP_WIDTH;
    }
    public void setGapWidth(int value)
    {
        if (!isGapWidthSupported())
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, 0, 500, "value");

        DmlBarChart barChart = ms.as(mDmlChart, DmlBarChart.class);
        if (barChart != null)
        {
            barChart.setGapWidth(value);
            return;
        }

        DmlOfPieChart ofPieChart = ms.as(mDmlChart, DmlOfPieChart.class);
        if (ofPieChart != null)
        {
            ofPieChart.setGapWidth(value);
            return;
        }

        Debug.assertTrue(mDmlChart.getChartType() == DmlChartType.CHART_EX_CHART);
        mDmlChart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(value / 100d)); // Convert from percents.
    }

    /// <summary>
    /// Gets or sets the size of the bubbles as a percentage of their default size.
    /// </summary>
    /// <remarks>
    /// <para>Applies only to series groups of the <see cref="ChartSeriesType.Bubble"/> and
    /// <see cref="ChartSeriesType.Bubble3D"/> types.</para>
    /// <para>The range of acceptable values is from 0 to 300 inclusive. The default value is 100.</para>
    /// </remarks>
    public int getBubbleScale()
    {
        DmlBubbleChart bubbleChart = ms.as(mDmlChart, DmlBubbleChart.class);
        return (bubbleChart != null) ? bubbleChart.getBubbleScale() : 0;
    }
    public void setBubbleScale(int value)
    {
        DmlBubbleChart bubbleChart = ms.as(mDmlChart, DmlBubbleChart.class);
        if (bubbleChart == null)
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, 0, 300, "value");

        bubbleChart.setBubbleScale(value);
    }

    /// <summary>
    /// Gets or sets the angle, in degrees, of the first slice of the parent pie chart.
    /// </summary>
    /// <remarks>
    /// <para>Applies to series groups of the <see cref="ChartSeriesType.Pie"/>, <see cref="ChartSeriesType.Pie3D"/>
    /// and <see cref="ChartSeriesType.Doughnut"/> types.</para>
    /// <para>The range of acceptable values is from 0 to 360 inclusive. The default value is 0.</para>
    /// </remarks>
    public int getFirstSliceAngle()
    {
        DmlPieChart pieChart = ms.as(mDmlChart, DmlPieChart.class);
        if (pieChart == null)
            return 0;

        switch (pieChart.getChartType())
        {
            case DmlChartType.PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
                return pieChart.getFirstSliceAng();
            case DmlChartType.PIE_3_D_CHART:
                // MS Word and Word VBA uses this property as a first slice angle in a Pie 3D chart.
                return pieChart.getChartSpace().getChartFormat().getView3D().getRotY();
            default:
                return 0;
        }
    }
    public void setFirstSliceAngle(int value)
    {
        DmlPieChart pieChart = ms.as(mDmlChart, DmlPieChart.class);
        if (pieChart == null)
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, 0, 360, "value");

        switch (pieChart.getChartType())
        {
            case DmlChartType.PIE_CHART:
            case DmlChartType.DOUGHNUT_CHART:
                pieChart.setFirstSliceAng(value);
                break;
            case DmlChartType.PIE_3_D_CHART:
                // MS Word and Word VBA uses this property as a first slice angle in a Pie 3D chart.
                pieChart.getChartSpace().getChartFormat().getView3D().setRotY(value);
                break;
            default:
                throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);
        }
    }

    /// <summary>
    /// Gets or sets the hole size of the parent doughnut chart as a percentage.
    /// </summary>
    /// <remarks>
    /// <para>Applies only to series groups of the <see cref="ChartSeriesType.Doughnut"/> type.</para>
    /// <para>The range of acceptable values is from 0 to 90 inclusive. The default value is 75.</para>
    /// </remarks>
    public int getDoughnutHoleSize()
    {
        DmlDoughnutChart doughnutChart = ms.as(mDmlChart, DmlDoughnutChart.class);
        return (doughnutChart != null) ? doughnutChart.getHoleSize() : 0;
    }
    public void setDoughnutHoleSize(int value)
    {
        DmlDoughnutChart doughnutChart = ms.as(mDmlChart, DmlDoughnutChart.class);
        if (doughnutChart == null)
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, 0, 90, "value");

        doughnutChart.setHoleSize(value);
    }

    /// <summary>
    /// Gets or sets the size of the pie chart secondary section as a percentage.
    /// </summary>
    /// <remarks>
    /// <para>Applies to series groups of the <see cref="ChartSeriesType.PieOfPie"/> and
    /// <see cref="ChartSeriesType.PieOfBar"/> types.</para>
    /// <para>The range of acceptable values is from 5 to 200 inclusive. The default value is 75.</para>
    /// </remarks>
    public int getSecondSectionSize()
    {
        DmlOfPieChart pieChart = ms.as(mDmlChart, DmlOfPieChart.class);
        return (pieChart != null) ? pieChart.getSecondPieSize() : 0;
    }
    public void setSecondSectionSize(int value)
    {
        DmlOfPieChart pieChart = ms.as(mDmlChart, DmlOfPieChart.class);
        if (pieChart == null)
            throw new IllegalStateException(SETTING_UNSUPPORTED_PROPERTY_ERROR);

        ArgumentUtil.checkRangeInclusive(value, 5, 200, "value");

        pieChart.setSecondPieSize(value);
    }

    /// <summary>
    /// Gets a DML chart corresponding to this series group.
    /// </summary>
    DmlChart getDmlChart() { return mDmlChart; }

    /// <summary>
    /// Gets a plot area of this series group.
    /// </summary>
    private DmlChartPlotArea getPlotArea() { return mDmlChart.getPlotArea(); }

    /// <summary>
    /// Gets a flag indicating whether the <see cref="GapWidth"/> property is supported by this series group.
    /// </summary>
    private boolean isGapWidthSupported()
    {
        switch (getSeriesType())
        {
            case ChartSeriesType.BAR:
            case ChartSeriesType.BAR_STACKED:
            case ChartSeriesType.BAR_PERCENT_STACKED:
            case ChartSeriesType.BAR_3_D:
            case ChartSeriesType.BAR_3_D_STACKED:
            case ChartSeriesType.BAR_3_D_PERCENT_STACKED:
            case ChartSeriesType.COLUMN:
            case ChartSeriesType.COLUMN_STACKED:
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
            case ChartSeriesType.COLUMN_3_D:
            case ChartSeriesType.COLUMN_3_D_CLUSTERED:
            case ChartSeriesType.COLUMN_3_D_STACKED:
            case ChartSeriesType.COLUMN_3_D_PERCENT_STACKED:
            case ChartSeriesType.PIE_OF_PIE:
            case ChartSeriesType.PIE_OF_BAR:
            case ChartSeriesType.HISTOGRAM:
            case ChartSeriesType.BOX_AND_WHISKER:
            case ChartSeriesType.WATERFALL:
            case ChartSeriesType.FUNNEL:
                return true;
            default:
                return false;
        }
    }

    private /*final*/ DmlChart mDmlChart;
    private ChartSeriesCollection mSeriesCollection;

    private static final int CHART_EX_AUTO_GAP_WIDTH = 33;

    private static final String SETTING_UNSUPPORTED_PROPERTY_ERROR =
        "The property cannot be set for a series group of this type.";
    private static final String SECONDARY_AXES_IN_NON_COMBO_CHART_ERROR =
        "Only a combo chart, that is, a chart with multiple series groups can have secondary axes.";
    private static final String SECONDARY_AXES_FOR_SINGLE_SERIES_GROUP_ERROR =
        "You cannot create secondary axes for a combo chart with a single series group that can have axes.";
    private static final String UNSUPPORTED_AXES_ERROR =
        "Axes are not supported by this type of series group.";
}

