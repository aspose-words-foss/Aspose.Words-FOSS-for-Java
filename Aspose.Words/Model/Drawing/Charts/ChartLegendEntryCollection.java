// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collections;
import com.aspose.IntToObjDictionary;
import java.util.Comparator;
import com.aspose.ms.System.msInt;


/// <summary>
/// Represents a collection of chart legend entries.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartLegendEntryCollection implements Iterable<ChartLegendEntry>
{
    /// <summary>
    /// Creates an instance of this class.
    /// </summary>
    ChartLegendEntryCollection(DmlChartFormat chartFormat)
    {
        mChartFormat = chartFormat;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartLegendEntry> iterator()
    {
        return new Enumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Returns a deep clone of this collection.
    /// </summary>
    ChartLegendEntryCollection deepClone(DmlChartFormat chartFormat)
    {
        ChartLegendEntryCollection lhs = new ChartLegendEntryCollection(chartFormat);

        if (mLegendEntries.getCount() == 0)
            return lhs;

        for (ChartLegendEntry value : mLegendEntries.getValues())
            lhs.mLegendEntries.add(value.getIndex(), value.deepClone());

        return lhs;
    }

    /// <summary>
    /// Adds the chart legend entry to this collection. If the collection already contains an entry with that
    /// index, the entry will be replaced.
    /// </summary>
    void addEntry(ChartLegendEntry entry)
    {
        Debug.assertTrue(entry != null);
        mLegendEntries.set(entry.getIndex(), entry);
        entry.setParentDmlChartFormat(mChartFormat);
    }

    /// <summary>
    /// Gets <see cref="ChartLegendEntry"/> for the specified index. Returns <b>null</b> if the entry does not
    /// exist yet.
    /// </summary>
    ChartLegendEntry getEntry(int index)
    {
        return mLegendEntries.get(index);
    }

    /// <summary>
    /// Clears all legend entries of this collection.
    /// </summary>
    void clear()
    {
        mLegendEntries.clear();
    }

    /// <summary>
    /// Inserts legend entries related to the specified added series and its trendlines.
    /// </summary>
    void insertSeriesLegendEntries(ChartSeries series)
    {
        LegendEntryIndexes indexes = getLegendEntryIndexes(series);

        int seriesInsertIndex = indexes.getSeriesIndex();
        // indexes.FirstTrendlineIndex includes the inserted series: decrease.
        int trendlinesInsertIndex = (indexes.getFirstTrendlineIndex() >= 0) ? indexes.getFirstTrendlineIndex() - 1 : -1;
        int seriesTrendLineCount = series.getTrendlines().size();

        ArrayList<ChartLegendEntry> sortedEntries = msArrayList.ctor(mLegendEntries.getValues());
        Collections.sort(sortedEntries, new ChartLegendEntryComparerByIndex(true));

        for (ChartLegendEntry entry : sortedEntries)
        {
            int index = entry.getIndex();
            if (index < seriesInsertIndex)
                break;

            int newIndex = index + 1;
            if ((trendlinesInsertIndex >= 0) && (index >= trendlinesInsertIndex))
                newIndex += seriesTrendLineCount;

            mLegendEntries.remove(index);
            entry.setIndex(newIndex);
            mLegendEntries.add(newIndex, entry);
        }
    }

    /// <summary>
    /// Removes legend entries related to the specified series and its trendlines.
    /// </summary>
    void removeSeriesLegendEntries(ChartSeries series)
    {
        LegendEntryIndexes indexes = getLegendEntryIndexes(series);
        int seriesTrendLineCount = series.getTrendlines().size();

        ArrayList<ChartLegendEntry> sortedEntries = msArrayList.ctor(mLegendEntries.getValues());
        Collections.sort(sortedEntries, new ChartLegendEntryComparerByIndex(false));

        for (ChartLegendEntry entry : sortedEntries)
        {
            int index = entry.getIndex();
            if (index < indexes.getSeriesIndex())
                continue;

            mLegendEntries.remove(index);

            if ((index == indexes.getSeriesIndex()) ||
                ((indexes.getFirstTrendlineIndex() >= 0) &&
                 (index >= indexes.getFirstTrendlineIndex()) &&
                 (index < indexes.getFirstTrendlineIndex() + seriesTrendLineCount)))
            {
                continue;
            }

            int newIndex = index - 1;
            if ((indexes.getFirstTrendlineIndex() >= 0) && (index > indexes.getFirstTrendlineIndex()))
                newIndex -= seriesTrendLineCount;

            entry.setIndex(newIndex);
            mLegendEntries.add(newIndex, entry);
        }
    }

    /// <summary>
    /// Gets a legend entry related to the specified chart series.
    /// </summary>
    ChartLegendEntry getSeriesLegendEntry(ChartSeries series)
    {
        LegendEntryIndexes indexes = getLegendEntryIndexes(series);
        return this.get(indexes.getSeriesIndex());
    }

    private LegendEntryIndexes getLegendEntryIndexes(ChartSeries series)
    {
        // Series legend entries are located before trendline legend entries.

        LegendEntryIndexes indexes = new LegendEntryIndexes();
        int seriesCount = 0;
        int trendlineCount = 0;

        for (DmlChart chart : mChartFormat.getPlotArea().getCharts())
        {
            for (ChartSeries currentSeries : chart.getSeries())
            {
                if (currentSeries == series)
                {
                    indexes.setSeriesIndex(seriesCount);
                    indexes.setFirstTrendlineIndex(trendlineCount);
                }

                seriesCount++;
                trendlineCount += currentSeries.getTrendlines().size();
            }
        }

        indexes.setFirstTrendlineIndex((series.getTrendlines().size() > 0)
            ? indexes.getFirstTrendlineIndex() + seriesCount
            : -1);

        return indexes;
    }

    /// <summary>
    /// Returns the number of <see cref="ChartLegendEntry"/> in this collection.
    /// </summary>
    public int getCount()
    {
        if (mChartFormat == null)
        {
            Debug.assertTrue(false);
            return mLegendEntries.getCount();
        }

        int count = 0;
        for (DmlChart chart : mChartFormat.getPlotArea().getCharts())
        {
            for (ChartSeries series : chart.getSeries())
                count += series.getTrendlines().size() + 1; // Trendlines and the series itself.
        }

        return count;
    }

    /// <summary>
    /// Returns <see cref="ChartLegendEntry"/> for the specified index.
    /// </summary>
    public ChartLegendEntry get(int index)
    {
        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        if (!mLegendEntries.containsKey(index))
        {
            ChartLegendEntry entry = new ChartLegendEntry();
            entry.setIndex(index);

            addEntry(entry);
        }

        return mLegendEntries.get(index);
    }

    /// <summary>
    /// Gets an enumerable over all materialized legend entries.
    /// </summary>
    Iterable<ChartLegendEntry> getMaterializedLegendEntries() { return mLegendEntries.getValues(); }

    private /*final*/ IntToObjDictionary<ChartLegendEntry> mLegendEntries = new IntToObjDictionary<ChartLegendEntry>();

    private /*final*/ DmlChartFormat mChartFormat;

    /// <summary>
    /// Stores information about legend entry indexes related to a chart series and its trendlines.
    /// </summary>
    private static class LegendEntryIndexes
    {
        LegendEntryIndexes()
        {
            setSeriesIndex(-1);
            setFirstTrendlineIndex(-1);
        }

        int getSeriesIndex() { return mSeriesIndex; }; void setSeriesIndex(int value) { mSeriesIndex = value; };

        private int mSeriesIndex;
        int getFirstTrendlineIndex() { return mFirstTrendlineIndex; }; void setFirstTrendlineIndex(int value) { mFirstTrendlineIndex = value; };

        private int mFirstTrendlineIndex;
    }

    /// <summary>
    /// Implements a comparer of <see cref="ChartLegendEntry"/> by index.
    /// </summary>
    private static class ChartLegendEntryComparerByIndex implements Comparator<ChartLegendEntry>
    {
        ChartLegendEntryComparerByIndex(boolean isDescending)
        {
            mIsDescending = isDescending;
        }

        /// <summary>
        /// Performs a comparison of two <see cref="ChartLegendEntry"/> and returns a value indicating whether
        /// the first entry is less than, equal to, or greater than the second one.
        /// </summary>
        public int compare(ChartLegendEntry x, ChartLegendEntry y)
        {
            int sign = mIsDescending ? -1 : 1;

            if (x == null)
                return (y == null) ? 0 : -sign;

            if (y == null)
                return sign;

            return sign * msInt.compareTo(x.getIndex(), y.getIndex());
        }

        private boolean mIsDescending;
    }

    /// <summary>
    /// Represents an enumerator over all legend entries, including those not yet materialized, in order from
    /// the entry at index 0 to the entry at index <see cref="Count"/> - 1.
    /// </summary>
    private final static class Enumerator implements Iterator<ChartLegendEntry>
    {
        Enumerator(ChartLegendEntryCollection collection)
        {
            mCollection = collection;
            mCount = collection.getCount();
        }

        public boolean hasNext()
        {
            mCurrentIndex++;
            return (mCurrentIndex < mCount);
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

        public ChartLegendEntry next() { return mCollection.get(mCurrentIndex); }

        private /*final*/ ChartLegendEntryCollection mCollection;
        private /*final*/ int mCount;
        private int mCurrentIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

