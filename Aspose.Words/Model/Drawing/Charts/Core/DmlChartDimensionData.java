// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a data of a chart dimension (X, Y or Bubble Size).
/// </summary>
class DmlChartDimensionData implements Cloneable
{
    DmlChartDimensionData(DmlChartDataSource dataSource)
    {
        mDataSource = dataSource;
    }

    /// <summary>
    /// Gets a value of the rendering data at the specified index.
    /// </summary>
    DmlChartValue getValue(int index)
    {
        return (getData() != null)
            ? getData().getValue(index) // Gets actual data value.
            : DmlChartRenderingData.getValue(null, index); // Returns a dummy value.
    }

    DmlChartDimensionData deepClone()
    {
        DmlChartDimensionData lhs = (DmlChartDimensionData)memberwiseClone();

        lhs.mDataSource = mDataSource.deepClone();

        if (mData != null)
            lhs.mData = mData.deepClone(lhs.getValues());

        return lhs;
    }

    /// <summary>
    /// Copies data from the specified data source.
    /// </summary>
    void copyDataFrom(DmlChartDimensionData source)
    {
        if (source.isEmpty())
            return;

        mData = source.getData().deepClone();
    }

    /// <summary>
    /// Gets the <see cref="DmlChartDimensionData"/> with "dummy" values.
    /// </summary>
    static DmlChartDimensionData getDummyDimensionData(int valueCount)
    {
        DmlChartDataSource dummySource = new DmlChartDataSource();
        dummySource.setValues(getDummyCollection(valueCount, DmlChartValueType.NUMERIC));
        return new DmlChartDimensionData(dummySource);
    }

    /// <summary>
    /// Gets <see cref="DmlChartValueCollection"/> with "dummy" values.
    /// </summary>
    /// <param name="valueCount">The number of data values.</param>
    /// <param name="valuesType"><see cref="DmlChartValueType"/> that should be used.</param>
    /// <returns><see cref="DmlChartValueCollection"/> with "dummy" values.</returns>
    private static DmlChartValueCollection getDummyCollection(int valueCount, /*DmlChartValueType*/int valuesType)
    {
        DmlChartValueCollection dummyCollection = new DmlChartValueCollection(valuesType);
        dummyCollection.setValueCount(valueCount);
        dummyCollection.fillWithDummyValues();
        return dummyCollection;
    }

    /// <summary>
    /// Gets data for rendering.
    /// </summary>
    /// <remarks>
    /// This property returns series data modified for rendering.
    /// </remarks>
    DmlChartRenderingData getData()
    {
        if (mData == null)
        {
            if (getValues() == null)
                return null;

            mData = new DmlChartRenderingData(getValues());
        }

        return mData;
    }

    /// <summary>
    /// Gets or sets series data.
    /// </summary>
    DmlChartValueCollection getValues() { return (mDataSource != null) ? mDataSource.getValues() : null; }
    void setValues(DmlChartValueCollection value)
    {
        mDataSource.setValues(value);
        mData = null;
    }

    /// <summary>
    /// Returns <c>true</c> if values in the collection are dates.
    /// </summary>
    boolean isDate() { return (getData() != null) && getData().isDate(); }

    /// <summary>
    /// Returns values type in the data source. If the data source has no data <see cref="DmlChartValueType.None"/>
    /// is returned.
    /// </summary>
    /*DmlChartValueType*/int getValueType() { return (getData() != null) ? getData().getValueType() : DmlChartValueType.NONE; }

    boolean isEmpty() { return (getValues() == null); }

    /// <summary>
    /// Gets type of this dimension.
    /// </summary>
    /*DimensionType*/int getDimensionType() { return mDataSource.getValueRef().getDimensionType(); }
    void setDimensionType(/*DimensionType*/int value)
    {
        Debug.assertTrue(mDataSource.getValueRef() != null);
        mDataSource.getValueRef().setDimensionType(value);
    }

    /// <summary>
    /// Gets a data source of this dimension. 
    /// </summary>
    DmlChartDataSource getDataSource() { return mDataSource; }

    private DmlChartDataSource mDataSource;
    private DmlChartRenderingData mData;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

