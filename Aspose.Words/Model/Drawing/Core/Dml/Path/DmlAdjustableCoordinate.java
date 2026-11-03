// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;


/// <summary>
/// Represents adjustable coordinate
/// </summary>
/// <remarks>
/// 5.1.12.2 ST_AdjCoordinate (Adjustable Coordinate Methods)
/// This simple type is an adjustable coordinate is either an 
/// absolute coordinate position or a reference to a geometry guide.
/// </remarks>
class DmlAdjustableCoordinate implements Cloneable
{
    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="value">The value in path coordinate system.</param>
    DmlAdjustableCoordinate(double value)
    {
        mString = FormatterPal.doubleToStr9Decimals(value);
    }

    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="value">The value can be a number or a guide name.</param>
    DmlAdjustableCoordinate(String value)
    {
        if (!StringUtil.hasChars(value))
            mString = "0";
        else
            mString = value;
    }

    /// <summary>
    /// Clones this instance of <see cref="DmlAdjustableCoordinate"/>.
    /// </summary>
    DmlAdjustableCoordinate deepClone()
    {
        return (DmlAdjustableCoordinate)memberwiseClone();
    }

    /// <summary>
    /// Gets the value.
    /// </summary>
    double getValue(IDmlGuideValueProvider guideValueProvider)
    {
        double result = FormatterPal.tryParseDoubleInvariant(mString);
        if (!Double.isNaN(result))
            return result;

        result = NrxXmlReader.tryConvertUniversalMeasureToEmus(mString, null);
        if (!Double.isNaN(result))
            return result;

        return DmlFormulaHelper.getValue(mString, guideValueProvider);
    }

    /// <summary>
    /// Gets a string representation of the coordinate.
    /// </summary>
    String getString() { return mString; }

    private /*final*/ String mString;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
