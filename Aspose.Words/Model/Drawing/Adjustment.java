// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2024 by Vyacheslav Deryushev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;


/// <summary>
/// Represents adjustment values that are applied to the specified shape.
/// </summary>
public class Adjustment
{
    /// <summary>
    /// Creates instance of the <see cref="Adjustment"/> class.
    /// </summary>
    Adjustment(String name, int value, DmlOneArgumentFormula formula, ShapeBase shape)
    {
        mName = name;
        mValue = value;
        mFormula = formula;
        mShape = shape;
    }

    /// <summary>
    /// Creates instance of the <see cref="Adjustment"/> class.
    /// </summary>
    Adjustment(int index, int value, ShapeBase shape)
    {
        mIndex = index;
        mName = msString.format("adj{0}", mIndex);
        mValue = value;
        mShape = shape;
    }

    /// <summary>
    /// Gets the name of the adjustment.
    /// </summary>
    public String getName() { return mName; }

    /// <summary>
    /// Gets or sets the raw value of the adjustment.
    /// </summary>
    /// <remarks>
    /// An adjust value is simply a guide that has a value based formula specified.
    /// That is, no calculation takes place for an adjust value guide.
    /// Instead, this guide specifies a parameter value that is used for calculations within the shape guides.
    /// </remarks>
    public int getValue()
    {
        return mValue;
    }
    public void setValue(int value)
    {
        if (mValue == value)
            return;

        mValue = value;

        if (mShape.isDmlShape())
        {
            mFormula.setSource(msString.format("val {0}", value));
            mFormula.setX(FormatterPal.intToStr(value));
        }
        else
        {
            int adjustKey = VmlUtil.getAdjustKey(mIndex);
            mShape.getShapePr().set(adjustKey, value);
        }
    }

    private int mValue;
    private /*final*/ int mIndex;
    private /*final*/ String mName;
    private /*final*/ DmlOneArgumentFormula mFormula;
    private /*final*/ ShapeBase mShape;
}

