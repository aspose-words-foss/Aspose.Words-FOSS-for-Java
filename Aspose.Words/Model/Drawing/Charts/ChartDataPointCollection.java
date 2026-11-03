// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.ArgumentUtil;
import com.aspose.IntToObjDictionary;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;


/// <summary>
/// Represents collection of a <see cref="ChartDataPoint"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartDataPointCollection implements Iterable<ChartDataPoint>
{
    ChartDataPointCollection(ChartSeries series)
    {
        mSeries = series;
    }

    /// <summary>
    /// Returns <see cref="ChartDataPoint"/> for the specified index.
    /// </summary>
    public ChartDataPoint get(int index)
    {
        return materializeItem(index);
    }

    ChartDataPointCollection deepClone()
    {
        ChartDataPointCollection lhs = new ChartDataPointCollection(mSeries);

        for (ChartDataPoint point : getMaterializedDataPoints())
        {
            if (!point.hasNonDefaultFormatting())
                continue;

            ChartDataPoint cloned = point.deepClone();
            lhs.addDataPoint(cloned);
        }

        return lhs;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartDataPoint> iterator()
    {
        return new Enumerator(this);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Clears format of all <see cref="ChartDataPoint"/> in this collection.
    /// </summary>
    public void clearFormat()
    {
        // Do not clear the collection: let's keep currently stored data point objects.
        for (ChartDataPoint point : getMaterializedDataPoints())
            point.clearFormat();
    }

    /// <summary>
    /// Gets a flag indicating whether the data point at the specified index has default format.
    /// </summary>
    public boolean hasDefaultFormat(int dataPointIndex)
    {
        ChartDataPoint point = mDataPoints.get(dataPointIndex);
        return (point == null) || !point.hasNonDefaultFormatting();
    }

    /// <summary>
    /// Copies format from the source data point to the destination data point.
    /// </summary>
    public void copyFormat(int sourceIndex, int destinationIndex)
    {
        // The upper bounds of the indexes are currently not checked because we cannot calculate data point count
        // exactly for some chart types of MS Word 2016 (the same behavior as in the 'this' property).
        ArgumentUtil.checkNonNegative(sourceIndex, "sourceIndex");
        ArgumentUtil.checkNonNegative(destinationIndex, "destinationIndex");

        if (sourceIndex == destinationIndex)
            return;

        this.get(destinationIndex).copyFormatFrom(this.get(sourceIndex));
    }

    void addDataPoint(ChartDataPoint dpt)
    {
        mDataPoints.set(dpt.getIndex(), dpt);
        dpt.setParent(mSeries.getDefaultDataPoint());
    }

    /// <summary>
    /// Inserts a data point with default formatting at the specified index. The indexes of the points following
    /// the inserted one are incremented by 1.
    /// </summary>
    void insert(int index)
    {
        if (mDataPoints.getCount() == 0)
            return;

        IntToObjDictionary<ChartDataPoint> dataPoints = new IntToObjDictionary<ChartDataPoint>(mDataPoints.getCount());

        for (ChartDataPoint dataPoint : mDataPoints.getValues())
        {
            if (dataPoint.getIndex() >= index)
                dataPoint.setIndex(dataPoint.getIndex() + 1);

            dataPoints.add(dataPoint.getIndex(), dataPoint);
        }

        mDataPoints = dataPoints;
    }

    /// <summary>
    /// Removes the data point at the specified index. The indexes of the points following the removed one are
    /// decremented by 1.
    /// </summary>
    void remove(int index)
    {
        if (mDataPoints.getCount() == 0)
            return;

        IntToObjDictionary<ChartDataPoint> dataPoints = new IntToObjDictionary<ChartDataPoint>(mDataPoints.getCount());

        for (ChartDataPoint dataPoint : mDataPoints.getValues())
        {
            if (dataPoint.getIndex() == index)
                continue;

            if (dataPoint.getIndex() > index)
                dataPoint.setIndex(dataPoint.getIndex() - 1);

            dataPoints.add(dataPoint.getIndex(), dataPoint);
        }

        mDataPoints = dataPoints;
    }

    /// <summary>
    /// Sets the parent series of this collection.
    /// </summary>
    void setSeries(ChartSeries series)
    {
        mSeries = series;

        for (ChartDataPoint point : getMaterializedDataPoints())
            point.setChart(series.getChart());
    }

    /// <summary>
    /// Gets a flag indicating whether "invertIfNegative" attribute is supported for a data point collection of
    /// the specified chart type.
    /// </summary>
    static boolean supportsInvertIfNegative(/*DmlChartType*/int chartType)
    {
        switch (chartType)
        {
            case DmlChartType.BUBBLE_CHART:
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// If the collection does not contain a data point at the specified index yet, this method creates it and puts
    /// into the collection.
    /// </summary>
    private ChartDataPoint materializeItem(int index)
    {
        // The index is not compared with Count now since we cannot calculate Count exactly for some chart types
        // of MS Word 2016.
        if (index < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        ChartDataPoint point = mDataPoints.get(index);
        if (point == null)
        {
            point = new ChartDataPoint(mSeries.getChart());
            point.setIndex(index);

            addDataPoint(point);
        }

        return point;
    }

    /// <summary>
    /// Gets indexes of data points, which have non-default formatting, starting with the specified index.
    /// </summary>
    ArrayList<Integer> getDataPointIndexesFrom(int labelIndex)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (ChartDataPoint dataPoint : mDataPoints.getValues())
        {
            int index = dataPoint.getIndex();
            if (index < labelIndex)
                continue;

            if (dataPoint.hasNonDefaultFormatting())
                msArrayList.add(list, index);
        }

        Collections.sort(list);

        return list;
    }

    /// <summary>
    /// Returns the number of <see cref="ChartDataPoint"/> in this collection.
    /// </summary>
    public int getCount()
    {
        ChartSeriesHelper helper = new ChartSeriesHelper(mSeries);
        int count = helper.getDataPointCount();

        return
            count +
            // Count points with indexes larger than the last point index possible to display.
            // Calculate only points with non-default formatting.
            getDataPointIndexesFrom(count).size();
    }

    /// <summary>
    /// Returns <c>true</c> if this collection contains at least one explicitly-defined point.
    /// </summary>
    boolean hasItems() { return mDataPoints.getCount() > 0; }

    /// <summary>
    /// Returns true if the collection contains data points with non-default formatting.
    /// </summary>
    boolean hasCustomDataPoints()
    {
        for (ChartDataPoint point : getMaterializedDataPoints())
        {
            if (point.hasNonDefaultFormatting())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets the series to which these bars belongs.
    /// </summary>
    /// <remarks>Property exposed for test purposes.</remarks>
    ChartSeries getSeries() { return mSeries; }

    /// <summary>
    /// Gets an enumerable over all materialized data points.
    /// </summary>
    Iterable<ChartDataPoint> getMaterializedDataPoints() { return mDataPoints.getValues(); }

    private ChartSeries mSeries;
    private IntToObjDictionary<ChartDataPoint> mDataPoints = new IntToObjDictionary<ChartDataPoint>();

    /// <summary>
    /// Enumerator over all data points of the series.
    /// </summary>
    /// <remarks>
    /// The following are included in the enumeration:
    /// 1) points that are displayed by default by MS Word for the current series data and series options even if
    /// they have default formatting defined in <see cref="ChartSeries.DefaultDataPoint"/>;
    /// 2) labels that are not displayed with the current data, but which have non-default formatting.
    /// </remarks>
    private final static class Enumerator implements Iterator<ChartDataPoint>
    {
        Enumerator(ChartDataPointCollection collection)
        {
            ChartSeriesHelper helper = new ChartSeriesHelper(collection.mSeries);
            mCollection = collection;
            mPointCount = helper.getDataPointCount();
        }

        public boolean hasNext()
        {
            if (mCollection.mSeries == null)
                return false;

            if (mCurrentIndex < mPointCount - 1)
            {
                mCurrentIndex++;
                return true;
            }

            if (mExtraDataPointIndexes == null) // Lazy initialization.
                mExtraDataPointIndexes = mCollection.getDataPointIndexesFrom(mPointCount);

            // mExtraDataPointIndexes is almost always empty: just enumerate through the list.
            for (int index : mExtraDataPointIndexes)
            {
                if (mCurrentIndex < index)
                {
                    mCurrentIndex = index;
                    return true;
                }
            }

            return false;
        }

        public void reset()
        {
            mCurrentIndex = -1;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public ChartDataPoint next() { return mCollection.get(mCurrentIndex); }

        private /*final*/ ChartDataPointCollection mCollection;
        private /*final*/ int mPointCount;
        private ArrayList<Integer> mExtraDataPointIndexes;
        private int mCurrentIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

