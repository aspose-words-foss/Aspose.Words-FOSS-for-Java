// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.Debug;
import java.util.ArrayList;


/// <summary>
/// Represents collection of <see cref="ChartSeries"/> related to a <see cref="DmlChart"/> instance.
/// </summary>
class DmlChartSeriesCollection implements Iterable<ChartSeries>
{
    /// <summary>
    /// Creates an instance of this class.
    /// </summary>
    DmlChartSeriesCollection(DmlChart dmlChart)
    {
        mDmlChart = dmlChart;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartSeries> iterator()
    {
        return getSeriesList().iterator();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds the specified series to this collection.
    /// </summary>
    void add(ChartSeries series)
    {
        addForLoad(series);

        if (getLegendEntries() != null)
            getLegendEntries().insertSeriesLegendEntries(series);
    }

    /// <summary>
    /// Adds the specified series to this collection. This method is intended to use when a document is being loaded;
    /// no special updates are performed.
    /// </summary>
    void addForLoad(ChartSeries series)
    {
        getSeriesList().add(series);
        mDmlChart.updateSeriesType();
    }

    /// <summary>
    /// Removes a <see cref="ChartSeries"/> at the specified index.
    /// </summary>
    void removeAt(int index)
    {
        ChartSeries series = getSeriesList().get(index);

        if (getLegendEntries() != null)
            getLegendEntries().removeSeriesLegendEntries(series);

        mDmlChart.removeSeriesData(series.getDataId());

        getSeriesList().remove(index);
    }

    /// <summary>
    /// Removes the specified <see cref="ChartSeries"/> from the collection.
    /// </summary>
    void remove(ChartSeries series)
    {
        int index = getSeriesList().indexOf(series);
        Debug.assertTrue(index >= 0);
        removeAt(index);
    }

    /// <summary>
    /// Removes all <see cref="ChartSeries"/> from this collection.
    /// </summary>
    void clear()
    {
        if (getLegendEntries() != null)
            getLegendEntries().clear();

        getSeriesList().clear();
    }

    /// <summary>
    /// Determines the index of the specified series in the collection.
    /// </summary>
    int indexOf(ChartSeries series)
    {
        return getSeriesList().indexOf(series);
    }

    /// <summary>
    /// Returns a <see cref="ChartSeries"/> at the specified index.
    /// </summary>
    ChartSeries get(int index) { return getSeriesList().get(index); }

    /// <summary>
    /// Returns the number of <see cref="ChartSeries"/> in this collection.
    /// </summary>
    int getCount() { return getSeriesList().size(); }

    ArrayList<ChartSeries> getSeriesList() { return (ArrayList<ChartSeries>)mDmlChart.getChartPr().getProperty(DmlChartAttrs.SERIES); }

    private ChartLegendEntryCollection getLegendEntries()
    {
        DmlChartFormat chartFormat = mDmlChart.getPlotArea().getParentChartFormat();
        if (chartFormat.getLegend() == null)
            return null;

        return chartFormat.getLegend().getLegendEntries();
    }

    private /*final*/ DmlChart mDmlChart;
}

