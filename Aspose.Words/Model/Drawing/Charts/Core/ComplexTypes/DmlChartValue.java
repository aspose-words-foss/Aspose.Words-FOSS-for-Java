// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msDouble;
import com.aspose.ms.System.msInt;


/// <summary>
/// Represents a data value of a chart.
/// </summary>
/// <dev>
/// If state of a <see cref="DmlChartValue"/> is changed, the parent collection should be notified using
/// <see cref="MarkCollectionAsChanged"/>.
/// </dev>
abstract class DmlChartValue implements Comparable<DmlChartValue>, Cloneable
{
    protected DmlChartValue(int index, /*DmlChartValueType*/int valueType)
    {
        mIndex = index;
        mValueType = valueType;
    }

    public int compareTo(DmlChartValue other)
    {
        // alexnosk: After changing IComparable to IComparable<DmlChartValue> it is required to handle null values.
        // Emulate old behavior and place null values at the very beginning of the sorted array.
        int test = msDouble.compareTo(getValue(), (other == null) ? -Double.MAX_VALUE : other.getValue());
        if (test != 0)
            return test;

        // If values are the same place them in index order.
        return msInt.compareTo(other.getIndex(), mIndex);
    }

    /// <summary>
    /// Checks whether the specified <see cref="DmlChartValue"/> is null or is NaN.
    /// </summary>
    /// <param name="value">The specified  <see cref="DmlChartValue"/></param>
    /// <returns>"True" if specified <see cref="DmlChartValue"/> is null or is NaN, otherwise "False"</returns>
    static boolean isNullOrNaN(DmlChartValue value)
    {
        return (value == null) || value.isNaN();
    }

    /// <summary>
    /// Checks whether the specified value is <b>null</b> or of the <see cref="DmlChartValueType.None"/> type.
    /// </summary>
    static boolean isNullOrNone(DmlChartValue value)
    {
        return (value == null) || (value.getValueType() == DmlChartValueType.NONE);
    }

    /// <summary>
    /// Creates a copy of this chart data value.
    /// </summary>
    /*virtual*/ DmlChartValue deepClone()
    {
        DmlChartValue lhs = (DmlChartValue)memberwiseClone();
        lhs.mCollection = null;
        return lhs;
    }

    /// <summary>
    /// Returns value for the specified level index, if index is negative or grater than level count in this value
    /// returns dummy value. Note: never returns null.
    /// </summary>
    /*virtual*/ DmlChartValue getLevelValue(int levelIndex)
    {
        if (levelIndex < 0 || levelIndex >= getLevelsCount())
            return new DmlChartDummyValue(getIndex(), getValue());

        return this;
    }

    /// <summary>
    /// Marks the parent collection as changed.
    /// </summary>
    protected void markCollectionAsChanged()
    {
        if (mCollection != null)
            mCollection.markAsChanged();
    }

    int getIndex() { return mIndex; }
    void setIndex(int value)
    {
        if (mIndex == value)
            return;

        mIndex = value;
        markCollectionAsChanged();
    }

    /// <summary>
    /// Indicates whether value is NaN.
    /// </summary>
    boolean isNaN() { return Double.isNaN(getValue()); }

    /*DmlChartValueType*/int getValueType() { return mValueType; }

    /*virtual*/ int getLevelsCount() { return 1; }

    /// <summary>
    /// Flag indicates whether value is visible on Axis. 
    /// By default returns true.
    /// </summary>
    /*virtual*/ boolean isVisible()
    {
        return true; 
    }

    abstract String getStringValue();

    float getFloatValue() { return (float)getValue(); }

    abstract double getValue();

    /*virtual*/ boolean isDate() { return false; }

    /// <summary>
    /// Gets or sets the parent collection.
    /// </summary>
    DmlChartValueCollection getCollection() { return mCollection; }
    void setCollection(DmlChartValueCollection value) { mCollection = value; }

    private int mIndex;
    private /*final*/ /*DmlChartValueType*/int mValueType;
    private DmlChartValueCollection mCollection;

    /// <summary>
    /// String representation of NaN values.
    /// </summary>
    static final String NAN_STRING_VALUE = "#N/A";

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

