// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.Iterator;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of <see cref="ChartSeriesGroup"/> objects.
/// </summary>
/// <remarks>
/// To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a>
/// documentation article.
/// </remarks>
/// <dev>
/// Only Pareto charts can have a <see cref="ChartSeriesGroup"/> instance that contains series of different types:
/// <see cref="ChartSeriesType.Pareto"/> and <see cref="ChartSeriesType.ParetoLine"/>. When a Pareto series is added
/// to a <see cref="ChartSeriesCollection"/>, a Pareto Line series is automatically added as well.
/// Pareto line series have their own secondary Y axis.
/// </dev>
public class ChartSeriesGroupCollection implements Iterable<ChartSeriesGroup>
{
    /// <summary>
    /// Ctor to create instances of this class.
    /// </summary>
    ChartSeriesGroupCollection(DmlChartSpace chartSpace)
    {
        mChartSpace = chartSpace;
        fillSeriesGroups();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartSeriesGroup> iterator()
    {
        return mSeriesGroups.iterator();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Removes a series group at the specified index. All child series will be removed from the chart.
    /// </summary>
    public void removeAt(int index)
    {
        ArgumentUtil.checkRangeInclusive(index, 0, getCount() - 1, "index");

        // Let's do not remove all series groups so that adding series using the Chart.Series property will work
        // without errors. (When using the property, new series are added to the first DmlChart/SeriesGroup.)
        if (getCount() == 1)
            throw new IllegalStateException(REQUIRED_AT_LEAST_ONE_SERIES_GROUP_ERROR);

        Debug.assertTrue(this.get(index).getDmlChart() == mChartSpace.getCharts().get(index));
        mChartSpace.getCharts().remove(index);

        mSeriesGroups.remove(index);
    }

    /// <summary>
    /// Adds a new series group of the specified series type to this collection.
    /// </summary>
    /// <remarks>
    /// Combo charts can contain series groups only of the following types: area, bar, column, line, pie, scatter,
    /// radar and stock types (except the corresponding 3D series types).
    /// </remarks>
    public ChartSeriesGroup add(/*ChartSeriesType*/int seriesType) throws Exception
    {
        // It is expected that a chart contains at least one series group.
        Debug.assertTrue(getCount() > 0);

        if (mChartSpace.isChartEx() ||
            // Throw only if the current chart is not already a combo chart.
            ((getCount() == 1) && !isAllowedInComboCharts(this.get(0).getSeriesType())))
        {
            throw new IllegalStateException(CANNOT_ADD_SERIES_GROUPS_ERROR);
        }

        if (!isAllowedInComboCharts(seriesType))
            throw new IllegalStateException(CANNOT_ADD_SERIES_GROUPS_OF_THIS_TYPE_ERROR);

        DmlChart dmlChart = createDmlChart(seriesType);
        mChartSpace.getCharts().add(dmlChart);

        return add(dmlChart);
    }

    /// <summary>
    /// Creates and adds a new series group to this collection.
    /// </summary>
    private ChartSeriesGroup add(DmlChart dmlChart)
    {
        ChartSeriesGroup seriesGroup = new ChartSeriesGroup(dmlChart);
        msArrayList.add(mSeriesGroups, seriesGroup);
        return seriesGroup;
    }

    /// <summary>
    /// Creates an instance of <see cref="DmlChart"/> for the specified series type.
    /// </summary>
    private DmlChart createDmlChart(/*ChartSeriesType*/int seriesType) throws Exception
    {
        /*ChartType*/int chartType = DmlChartUtil.seriesTypeToChartType(seriesType);
        Document doc = (Document)mChartSpace.getChartFormat().getDocument();
        IChartInserter chartInserter = ReaderFactory.createChartInserter();

        DmlChartSpace tmpChartSpace = chartInserter.createChartSpace(chartType, doc);
        DmlChart newDmlChart = tmpChartSpace.getFirstChart();
        newDmlChart.getSeries().clear();

        if (newDmlChart.hasAxis())
            defineAxesForNewDmlChart(newDmlChart);

        newDmlChart.setPlotArea(mChartSpace.getChartFormat().getPlotArea());

        return newDmlChart;
    }

    /// <summary>
    /// Sets the exiting axes to use in the specified newly created DML chart, or gets axes from the DML chart and
    /// adds them to the axes collection of the chart.
    /// </summary>
    private void defineAxesForNewDmlChart(DmlChart dmlChart)
    {
        DmlChartPlotArea plotArea = mChartSpace.getChartFormat().getPlotArea();

        IDmlChart2D chart2D = ms.as(dmlChart, IDmlChart2D.class);
        if ((chart2D != null) && (chart2D.getAxX() != null))
        {
            Debug.assertTrue((chart2D.getAxX() != null) == (chart2D.getAxY() != null));

            ChartAxis primaryXAxis = plotArea.getPrimaryXAxis();
            ChartAxis primaryYAxis = plotArea.getPrimaryYAxis();
            Debug.assertTrue((primaryXAxis != null) == (primaryYAxis != null));

            /*AxisGroup*/int axisGroup = getNewDmlChartAxisGroup(dmlChart);
            ChartAxis axisX = (axisGroup == AxisGroup.PRIMARY) ? primaryXAxis : plotArea.getSecondaryXAxis();
            ChartAxis axisY = (axisGroup == AxisGroup.PRIMARY) ? primaryYAxis : plotArea.getSecondaryYAxis();
            Debug.assertTrue((axisX != null) == (axisY != null));

            if (axisX == null)
            {
                plotArea.getAxes().add(chart2D.getAxX());
                plotArea.getAxes().add(chart2D.getAxY());

                if (primaryXAxis != null)
                {
                    DmlChartUtil.setXAxisOppositePosition(chart2D.getAxX(), primaryXAxis.getActualAxisPosition());
                    DmlChartUtil.setYAxisOppositePosition(chart2D.getAxY(), primaryYAxis.getActualAxisPosition());
                }
            }
            else
            {
                dmlChart.setAxisX(axisX);
                chart2D.setAxX(axisX);
                dmlChart.setAxisY(axisY);
                chart2D.setAxY(axisY);
            }
        }

        // Combo charts do not support 3D chart series groups.
        Debug.assertTrue(!(dmlChart instanceof IDmlChart3D));
    }

    /// <summary>
    /// Gets an axis group to set to the specified newly created DML chart (series group).
    /// </summary>
    private /*AxisGroup*/int getNewDmlChartAxisGroup(DmlChart dmlChart)
    {
        if (getCount() == 0)
            return AxisGroup.PRIMARY;

        // Assign numeric X chart series to a separate axis group than category X chart series like MS Word does.
        // Otherwise category values would refer to 1, 2, 3... numbers.
        if (isNumericX(this.get(0).getSeriesType()) == isNumericX(dmlChart.getSeriesType()))
            return this.get(0).getAxisGroup();
        else
            return (this.get(0).getAxisGroup() == AxisGroup.PRIMARY) ? AxisGroup.SECONDARY : AxisGroup.PRIMARY;
    }

    /// <summary>
    /// Gets a flag indicating whether the specified series type has numeric X values.
    /// </summary>
    private static boolean isNumericX(/*ChartSeriesType*/int seriesType)
    {
        switch (seriesType)
        {
            case ChartSeriesType.SCATTER:
            case ChartSeriesType.BUBBLE:
            case ChartSeriesType.BUBBLE_3_D:
            case ChartSeriesType.HISTOGRAM:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Creates series groups for each DML chart of the parent chart and adds them to this collection.
    /// </summary>
    private void fillSeriesGroups()
    {
        for (DmlChart dmlChart : mChartSpace.getCharts())
            add(dmlChart);
    }

    /// <summary>
    /// Gets a flag indicating whether series groups of the specified type are allowed in combo charts.
    /// </summary>
    private static boolean isAllowedInComboCharts(/*ChartSeriesType*/int seriesType)
    {
        switch (seriesType)
        {
            case ChartSeriesType.AREA:
            case ChartSeriesType.AREA_STACKED:
            case ChartSeriesType.AREA_PERCENT_STACKED:
            case ChartSeriesType.BAR:
            case ChartSeriesType.BAR_STACKED:
            case ChartSeriesType.BAR_PERCENT_STACKED:
            case ChartSeriesType.COLUMN:
            case ChartSeriesType.COLUMN_STACKED:
            case ChartSeriesType.COLUMN_PERCENT_STACKED:
            case ChartSeriesType.DOUGHNUT:
            case ChartSeriesType.LINE:
            case ChartSeriesType.LINE_STACKED:
            case ChartSeriesType.LINE_PERCENT_STACKED:
            case ChartSeriesType.PIE:
            case ChartSeriesType.PIE_OF_BAR:
            case ChartSeriesType.PIE_OF_PIE:
            case ChartSeriesType.RADAR:
            case ChartSeriesType.SCATTER:
            case ChartSeriesType.STOCK:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a <see cref="ChartSeriesGroup"/> at the specified index.
    /// </summary>
    public ChartSeriesGroup get(int index) { return mSeriesGroups.get(index); }

    /// <summary>
    /// Returns the number of series groups in this collection.
    /// </summary>
    public int getCount() { return mSeriesGroups.size(); }

    private /*final*/ DmlChartSpace mChartSpace;
    private /*final*/ ArrayList<ChartSeriesGroup> mSeriesGroups = new ArrayList<ChartSeriesGroup>();

    private static final String REQUIRED_AT_LEAST_ONE_SERIES_GROUP_ERROR = "There must be at least one series group in a chart.";
    private static final String CANNOT_ADD_SERIES_GROUPS_ERROR = "You cannot add series groups to charts of this type.";
    private static final String CANNOT_ADD_SERIES_GROUPS_OF_THIS_TYPE_ERROR = "You cannot add series groups of this type.";
}

