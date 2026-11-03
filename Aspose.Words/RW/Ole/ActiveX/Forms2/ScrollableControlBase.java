// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/12/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


/// <summary>
/// The base class for scrollable controls such as SpinButton or ScrollBar.
/// </summary>
abstract class ScrollableControlBase extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ScrollableControlBase(String name)
	{
		this(name, null);
	}
    /// <summary>
    /// Ctor.
    /// </summary>
    ScrollableControlBase(String name, Forms2Pr pr)
    {
    	super(name, pr);
	
    }

    /// <summary>
    /// Gets or sets integer that specifies the minimum acceptable value for the <see cref="Position"/> property.
    /// </summary>
    /// <remarks>
    /// Note, Min can be set greater than Max and vice versa.
    /// </remarks>
    int getMin() { return (/*int*/Integer)Pr.fetchAttr(Forms2Attr.MIN); }
    void setMin(int value)
    {
        Pr.setAttr(Forms2Attr.MIN, value);
        // Adjust position accordingly to fit in range of (Min, Max).
        int curPosition = getPosition();
        int max = getMax();
        if (!MathUtil.isInRange(curPosition, value, max))
        {
            setPosition((value < max)
                ? (int)MathUtil.fitToRange(curPosition, value, max)
                : (int)MathUtil.fitToRange(curPosition, max, value));
        }
    }

    /// <summary>
    /// Gets or sets integer that specifies the maximum acceptable value for the <see cref="Position"/> property.
    /// </summary>
    /// <remarks>
    /// Note, Max can be set less than Min and vice versa.
    /// </remarks>
    int getMax() { return (/*int*/Integer)Pr.fetchAttr(Forms2Attr.MAX); }
    void setMax(int value)
    {
        Pr.setAttr(Forms2Attr.MAX, value);
        // Adjust position accordingly to fit in range of (Min, Max).
        int curPosition = getPosition();
        int min = getMin();
        if (!MathUtil.isInRange(curPosition, min, value))
        {
            setPosition((min < value)
                ? (int)MathUtil.fitToRange(curPosition, min, value)
                : (int)MathUtil.fitToRange(curPosition, value, min));
        }
    }

    /// <summary>
    /// Gets or sets integer that specifies the value of a control.
    /// </summary>
    int getPosition() { return (/*int*/Integer)Pr.fetchAttr(Forms2Attr.POSITION); }
    void setPosition(int value)
    {
        if (!MathUtil.isInRange(value, getMin(), getMax()))
        {
            throw new IllegalArgumentException("Could not set the Position property. Invalid property value." + "\r\nParameter name: " + "value");
        }

        Pr.setAttr(Forms2Attr.POSITION, value);
    }
}

