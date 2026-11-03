// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlChartDummyValue extends DmlChartValue
{
    DmlChartDummyValue(int index, double value)
    {
    	this(index, value, true);
	
    }

    DmlChartDummyValue(int index, double value, boolean isVisible)
    {
    	super(index, DmlChartValueType.NONE);
	
        mValue = value;
        mIsVisible = isVisible;
    }

    /*override*/ String getStringValue() { return ""; }

    /*override*/ double getValue() { return mValue; }

    /*override*/ boolean isVisible() { return mIsVisible; }

    private /*final*/ double mValue;
    private /*final*/ boolean mIsVisible;
}

