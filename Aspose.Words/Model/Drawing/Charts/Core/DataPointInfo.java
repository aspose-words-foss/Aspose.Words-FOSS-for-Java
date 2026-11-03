// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/11/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents information about a data point/label related to a particular chart data value.
/// </summary>
class DataPointInfo
{
    DataPointInfo(int index, int valueIndex, int valueLevel, boolean isPointReferencedByOtherValues)
    {
        mIndex = index;
        setValueIndex(valueIndex);
        mValueLevel = valueLevel;
        mIsPointReferencedByOtherValues = isPointReferencedByOtherValues;
    }

    /// <summary>
    /// Gets the index of the data point/label.
    /// </summary>
    int getIndex() { return mIndex; };

    private int mIndex;

    /// <summary>
    /// Gets the index of the chart data value that corresponds to the data point/label.
    /// </summary>
    int getValueIndex() { return mValueIndex; }; void setValueIndex(int value) { mValueIndex = value; };

    private int mValueIndex;

    /// <summary>
    /// Gets the level of the chart data value that corresponds to the data point/label.
    /// </summary>
    int getValueLevel() { return mValueLevel; };

    private int mValueLevel;

    /// <summary>
    /// Gets a flag indicating whether there are other chart data values that reference to the data point/label and
    /// that the data point and label should be preserved when the value is removed.
    /// </summary>
    boolean isPointReferencedByOtherValues() { return mIsPointReferencedByOtherValues; };

    private boolean mIsPointReferencedByOtherValues;
}

