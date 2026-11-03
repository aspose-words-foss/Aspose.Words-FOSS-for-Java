// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents properties of a dimension data level.
/// (Stores attributes of the CT_NumericLevel or CT_StringLevel complex types [MS-ODRAWXML].)
/// </summary>
class DmlChartDataLevelProperties implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartDataLevelProperties deepClone()
    {
        return (DmlChartDataLevelProperties)memberwiseClone();
    }

    /// <summary>
    /// Gets or sets the number of data values.
    /// </summary>
    int getValueCount() { return mValueCount; }; void setValueCount(int value) { mValueCount = value; };

    private int mValueCount;

    /// <summary>
    /// Gets or sets any custom information of data values formatting.
    /// </summary>
    String getFormatCode() { return mFormatCode; }; void setFormatCode(String value) { mFormatCode = value; };

    private String mFormatCode;

    /// <summary>
    /// Gets or sets name of this level of dimension data.
    /// </summary>
    String getName() { return mName; }; void setName(String value) { mName = value; };

    private String mName;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

