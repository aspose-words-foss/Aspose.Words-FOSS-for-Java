// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents pt (String Point) (5.7.2.152) element.
/// This element specifies string data for a specific data point.
/// </summary>
class DmlChartStrValue extends DmlChartValue
{
    DmlChartStrValue(int index, String value)
    {
    	super(index, DmlChartValueType.STRING);
	
        mValue = value;
    }

    /*override*/ String getStringValue() { return mValue; }

    /// <summary>
    /// Returns one based index as float value.
    /// </summary>
    /*override*/ double getValue() { return (getIndex() + 1); }

    private /*final*/ String mValue;
}

