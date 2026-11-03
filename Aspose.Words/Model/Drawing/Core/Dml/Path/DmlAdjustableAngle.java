// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents adjustable angle
/// </summary>
/// <remarks>
/// 5.1.12.1 ST_AdjAngle (Adjustable Angle Methods)
/// This simple type is an adjustable angle, either an absolute angle or a reference to 
/// a geometry guide. The units for an adjustable angle are 60,000ths of a degree.
/// </remarks>
class DmlAdjustableAngle
{
    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="value">The value of angle in adjustable angle units.</param>
    DmlAdjustableAngle(double value)
    {
        mString = FormatterPal.doubleToStr9Decimals(value);
    }

    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="value">The value can be a number or a guide name.</param>
    DmlAdjustableAngle(String value)
    {
        if (!StringUtil.hasChars(value))
            mString = "0";
        else
            mString = value;
    }

    /// <summary>
    /// Gets the value.
    /// </summary>
    /// <remarks>
    /// An angle in 60,000ths of a degree. Positive angles are clockwise 
    /// (i.e., towards the positive y axis); negative angles are 
    /// counter-clockwise (i.e., towards the negative y axis).
    /// </remarks>
    double getValue(IDmlGuideValueProvider guideValueProvider)
    {
        return DmlFormulaHelper.getValue(mString, guideValueProvider);
    }

    double getValueInRadians(IDmlGuideValueProvider guideValueProvider)
    {
        return ConvertUtilCore.dmlAnglesToRadians(getValue(guideValueProvider));
    }

    /// <summary>
    /// Gets a string representation of the angle
    /// </summary>
    String getString() { return mString; }

    private /*final*/ String mString;
}
