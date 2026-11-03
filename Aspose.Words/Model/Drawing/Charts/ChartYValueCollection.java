// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of Y values for a chart series.
/// </summary>
/// <remarks>
/// <p>All items of the collection other than <b>null</b> must have the same <see cref="ChartYValue.ValueType"/>.</p>
/// <p>The collection allows only changing Y values. To add or insert new values to a chart series, or remove values,
/// the appropriate methods of the <see cref="ChartSeries"/> class can be used.</p>
/// </remarks>
/// <seealso cref="ChartSeries.Add(ChartXValue)"/>
/// <seealso cref="ChartSeries.Add(ChartXValue, ChartYValue)"/>
/// <seealso cref="ChartSeries.Add(ChartXValue, ChartYValue, double)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue, ChartYValue)"/>
/// <seealso cref="ChartSeries.Insert(int, ChartXValue, ChartYValue, double)"/>
/// <seealso cref="ChartSeries.Remove"/>
public class ChartYValueCollection implements Iterable<ChartYValue>
{
    /// <summary>
    /// Ctor to create an instance of this class.
    /// </summary>
    ChartYValueCollection(ISeriesDataStore seriesDataStore)
    {
        mSeriesDataStore = seriesDataStore;
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<ChartYValue> iterator()
    {
        // Some Y values may not yet be materialized, so use the special Enumerator class to materialize them
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
    void insert(int index, ChartYValue value)
    {
        ensureCorrectDataType(value);

        while (mValues.size() < index)
            msArrayList.add(mValues, null);

        mValues.add(index, value);
    }

    /// <summary>
    /// Sets the value in this collection at the specified index.
    /// </summary>
    void setValue(int index, ChartYValue value)
    {
        ensureCorrectDataType(value);

        while (mValues.size() <= index)
            msArrayList.add(mValues, null);

        mValues.set(index, value);
    }

    /// <summary>
    /// Removes the value at the specified index from the collection.
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
    /// If an Y value that corresponds to the internal <see cref="DmlChartValue"/> at the specified index has not
    /// been created yet, this method creates it and puts into the collection.
    /// </summary>
    private ChartYValue materializeItem(int index)
    {
        Debug.assertTrue(index < getCount());

        while (mValues.size() <= index)
            msArrayList.add(mValues, null);

        if (mValues.get(index) == null)
        {
            mValues.set(index, mSeriesDataStore.getYValue(index, getValueType()));
        }
        else
        {
            // Only this class changes Y data in the series: let's insure that the value has not changed in the
            // corresponding DmlChartValueCollection collection since the last time.
            Debug.assertTrue(msObject.equals(mValues.get(index), mSeriesDataStore.getYValue(index, getValueType())));
        }

        return mValues.get(index);
    }

    /// <summary>
    /// Gets the first non-null value stored in this collection.
    /// </summary>
    private ChartYValue getFirstNotNullValue()
    {
        for (ChartYValue value : mValues)
        {
            if (value != null)
                return value;
        }

        return null;
    }

    /// <summary>
    /// Checks that the data type of the specified value is correct for this collection.
    /// </summary>
    /// <remarks>
    /// An exception is thrown if the collection contains values with a different <see cref="ChartYValueType"/>
    /// than the specified one.
    /// If there are no non-null values in the collection, the method can change the data type of the series
    /// if necessary.
    /// </remarks>
    private void ensureCorrectDataType(ChartYValue value)
    {
        // The value must be of the correct data type, or be null.
        if (value == null)
            return;

        ChartYValue existingValue = getFirstNotNullValue();
        if (existingValue == null)
        {
            mSeriesDataStore.setYValueType(value.getValueType());
            return;
        }

        if (existingValue.getValueType() != value.getValueType())
        {
            throw new IllegalStateException(
                "The value must be of the same type as the existing items in the collection.");
        }
    }

    /// <summary>
    /// Gets the number of items in this collection.
    /// </summary>
    public int getCount() { return mSeriesDataStore.isYValueSupported() ? mSeriesDataStore.getValueCount() : 0; }

    /// <summary>
    /// Gets or sets the Y value at the specified index.
    /// </summary>
    /// <remarks>
    /// Empty values are represented as <b>null</b>.
    /// </remarks>
    public ChartYValue get(int index)
    {
        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        return materializeItem(index);
    }
    public void set(int index, ChartYValue value)
    {
        if (!mSeriesDataStore.isYValueSupported())
            throw new IllegalStateException("This chart series does not support Y values.");

        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");

        setValue(index, value);

        mSeriesDataStore.changeYValue(value, index);
    }

    /// <summary>
    /// Gets or sets the format code applied to the Y values.
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
    public String getFormatCode() { return (mSeriesDataStore.getYFormatCode() != null ? mSeriesDataStore.getYFormatCode() : ""); }
    public void setFormatCode(String value) { mSeriesDataStore.setYFormatCode(value); }

    /// <summary>
    /// Gets the type of values stored in the collection.
    /// </summary>
    private /*ChartYValueType*/int getValueType()
    {
        ChartYValue existingValue = getFirstNotNullValue();
        return (existingValue != null) ? existingValue.getValueType() : mSeriesDataStore.getYValueType();
    }

    private /*final*/ ISeriesDataStore mSeriesDataStore;
    private /*final*/ ArrayList<ChartYValue> mValues = new ArrayList<ChartYValue>();

    /// <summary>
    /// Enumerator over Y values. Y values are materialized when accessed.
    /// </summary>
    private final static class Enumerator implements Iterator<ChartYValue>
    {
        Enumerator(ChartYValueCollection collection)
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

        public ChartYValue next() { return mCollection.get(mCurrentIndex); }

        private /*final*/ ChartYValueCollection mCollection;
        private int mCurrentIndex = -1;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }
}

