// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/09/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.MathUtil;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of bubble sizes for a chart series.
/// </summary>
/// <remarks>
/// <p>The collection allows only changing bubble sizes. To add or insert new values to a chart series, or remove
/// values, the appropriate methods of the <see cref="ChartSeries"/> class can be used.</p>
/// <p>Empty bubble size values are represented as <see cref="double.NaN"/>.</p>
/// </remarks>
/// <seealso cref="ChartSeries.Add(ChartXValue)"/>
/// <seealso cref="ChartSeries.Add(ChartXValue, ChartYValue)"/>
/// <seealso cref="ChartSeries.Add(ChartXValue, ChartYValue, double)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue, ChartYValue)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue, ChartYValue, double)"/>
/// <seealso cref="ChartSeries.Remove"/>
public class BubbleSizeCollection implements Iterable<Double>
{
    /// <summary>
    /// Ctor to create an instance of this class.
    /// </summary>
    BubbleSizeCollection(ISeriesDataStore seriesDataStore)
    {
        mSeriesDataStore = seriesDataStore;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<Double> iterator()
    {
        // Some bubble sizes may not yet be materialized, so use the special Enumerator class to materialize them
        // on access.
        return new Enumerator(this);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Inserts the value into this collection at the specified index.
    /// </summary>
    void insert(int index, double value)
    {
        while (mValues.size() < index)
            msArrayList.add(mValues, NON_LOADED_BUBBLE_SIZE);

        mValues.add(index, value);
    }

    /// <summary>
    /// Removes the bubble size at the specified index from the collection.
    /// </summary>
    void remove(int index)
    {
        if (mValues.size() > index)
            mValues.remove(index);
    }

    /// <summary>
    /// Removes all values from the collection.
    /// </summary>
    void clear()
    {
        mValues.clear();
    }

    /// <summary>
    /// If a bubble size value that corresponds to the item of the bubble size <see cref="DmlChartValueCollection"/>
    /// at the specified index has not been put to the collection yet, this method retrieves and puts it.
    /// </summary>
    /// <dev>
    /// Although bubble size is <see cref="double"/> and stored by value, but let's use the same implementation
    /// as in <see cref="ChartXValueCollection"/> and <see cref="ChartYValueCollection"/>, in which elements are
    /// loaded as they are accessed.
    /// </dev>
    private double materializeItem(int index)
    {
        Debug.assertTrue(index < getCount());

        while (mValues.size() <= index)
            msArrayList.add(mValues, NON_LOADED_BUBBLE_SIZE);

        if (isNonLoadedBubbleSize(mValues.get(index)))
        {
            mValues.set(index, mSeriesDataStore.getBubbleSize(index));
        }
        else
        {
            // Only this class changes bubble size data in the series: let's insure that the value has not changed
            // in the corresponding DmlChartValueCollection since the last time.
            Debug.assertTrue(MathUtil.areEqual(mValues.get(index), mSeriesDataStore.getBubbleSize(index)));
        }

        return mValues.get(index);
    }

    /// <summary>
    /// Returns <b>true</b> if the specified value is used as an indication that the bubble size value is not
    /// loaded from the corresponding <see cref="DmlChartValueCollection"/>.
    /// </summary>
    private static boolean isNonLoadedBubbleSize(double value)
    {
        return Double.isNaN(value);
    }

    /// <summary>
    /// Gets the number of items in this collection.
    /// </summary>
    public int getCount()
    {
        return mSeriesDataStore.isBubbleSizeSupported() ? mSeriesDataStore.getValueCount() : 0;
    }

    /// <summary>
    /// Gets or sets the bubble size value at the specified index.
    /// </summary>
    public double get(int index)
    {
        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        return materializeItem(index);
    }
    public void set(int index, double value)
    {
        if (!mSeriesDataStore.isBubbleSizeSupported())
            throw new IllegalStateException("This chart does not support bubble size.");

        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        while (mValues.size() <= index)
            msArrayList.add(mValues, NON_LOADED_BUBBLE_SIZE);

        mValues.set(index, value);

        mSeriesDataStore.changeBubbleSize(value, index);
    }

    /// <summary>
    /// Gets or sets the format code applied to the bubble sizes.
    /// </summary>
    /// <remarks>
    /// Number formatting is used to change the way values appears in the chart.
    /// The examples of number formats:
    /// <para>Number - "#,##0.00"</para>
    /// <para>Currency - "\"$\"#,##0.00"</para>
    /// <para>Time - "[$-x-systime]h:mm:ss AM/PM"</para>
    /// <para>Date - "d/mm/yyyy"</para>
    /// <para>Percentage - "0.00%"</para>
    /// <para>Fraction - "# ?/?"</para>
    /// <para>Scientific - "0.00E+00"</para>
    /// <para>Accounting - "_-\"$\"* #,##0.00_-;-\"$\"* #,##0.00_-;_-\"$\"* \"-\"??_-;_-@_-"</para>
    /// <para>Custom with color - "[Red]-#,##0.0"</para>
    /// </remarks>
    public String getFormatCode() { return (mSeriesDataStore.getBubbleSizeFormatCode() != null ? mSeriesDataStore.getBubbleSizeFormatCode() : ""); }
    public void setFormatCode(String value) { mSeriesDataStore.setBubbleSizeFormatCode(value); }

    private /*final*/ ISeriesDataStore mSeriesDataStore;
    private /*final*/ ArrayList<Double> mValues = new ArrayList<Double>();

    /// <summary>
    /// Enumerator over bubble size values. Bubble sizes are materialized when accessed.
    /// </summary>
    private final static class Enumerator implements Iterator<Double>
    {
        Enumerator(BubbleSizeCollection collection)
        {
            mCollection = collection;
        }

        public boolean hasNext()
        {
            mCurrentIndex++;
            return mCurrentIndex < mCollection.getCount();
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

        public Double next() { return mCollection.get(mCurrentIndex); }

        private /*final*/ BubbleSizeCollection mCollection;
        private int mCurrentIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    /// <summary>
    /// Value that is used as an indication that a bubble size value is not loaded from the corresponding
    /// <see cref="DmlChartValueCollection"/>.
    /// </summary>
    private static final double NON_LOADED_BUBBLE_SIZE = Double.NaN;
}

