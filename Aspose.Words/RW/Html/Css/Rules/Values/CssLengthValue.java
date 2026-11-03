// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents an identifier CSS length value.
/// </summary>
class CssLengthValue extends CssValue
{
    CssLengthValue(double value, /*CssUnit*/int unit)
    {
    	super(CssValueType.LENGTH, value);
	
        mValue = value;
        mUnit = unit;
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int result = super.hashCode();
            result = (result * 397) ^ (int)getUnit();
            return result;
        }
    }

    /// <summary>
    /// Gets a length value converted to the specified units. Otherwise returns zero.
    /// If we attempt to get a length value as number, we treat it as pixels.
    /// Note: relative values aren't supported.
    /// </summary>
    double getLength(/*CssUnit*/int unit)
    {
        double lenght = 0.0;
        switch (unit)
        {
            case CssUnit.NONE:
            case CssUnit.IN:
            case CssUnit.CM:
            case CssUnit.MM:
            case CssUnit.PT:
            case CssUnit.PC:
            case CssUnit.PX:
                lenght = pointsToLength(unit);
                break;
            case CssUnit.EM:
            case CssUnit.EX:
            case CssUnit.REM:
                Debug.assertTrue(false, "Relative length is not allowed.");
                break;
            default:
                Debug.assertTrue(false);
                break;
        }

        return lenght;
    }

    /*override*/ void toCss(StringBuilder sb)
    {
        msStringBuilder.append(sb, FormatterPal.doubleToStr2Decimals(mValue));
        msStringBuilder.append(sb, cssUnitToString(getUnit()));
    }

    protected /*override*/ boolean doEquals(CssValue other)
    {
        if (!(other instanceof CssLengthValue))
            return false;

        final double TOLERANCE = 0.005; // This accuracy is enough in HTML.
        CssLengthValue length = (CssLengthValue)other;
        return MathUtil.areEqual(mValue, length.mValue, TOLERANCE) && (getUnit() == length.getUnit());
    }

    private static String cssUnitToString(/*CssUnit*/int unit)
    {
        switch (unit)
        {
            case CssUnit.IN:
                return "in";
            case CssUnit.CM:
                return "cm";
            case CssUnit.MM:
                return "mm";
            case CssUnit.PT:
                return "pt";
            case CssUnit.PC:
                return "pc";
            case CssUnit.PX:
                return "px";
            case CssUnit.EM:
                return "em";
            case CssUnit.EX:
                return "ex";
            case CssUnit.REM:
                return "rem";
            default:
                Debug.fail("Unknown css unit type.");
                return "";
        }
    }

    private double getLengthInPoints()
    {
        double lengthInPt;
        switch (getUnit())
        {
            case CssUnit.NONE:
                lengthInPt = mValue;
                break;
            case CssUnit.IN:
                lengthInPt = ConvertUtilCore.inchToPoint(mValue);
                break;
            case CssUnit.CM:
                lengthInPt = ConvertUtilCore.cmToPoint(mValue);
                break;
            case CssUnit.MM:
                lengthInPt = ConvertUtilCore.mmToPoint(mValue);
                break;
            case CssUnit.PT:
                lengthInPt = mValue;
                break;
            case CssUnit.PC:
                lengthInPt = mValue * 12.0; // 1pc = 12pt.
                break;
            case CssUnit.PX:
                lengthInPt = ConvertUtilCore.pixelToPoint(mValue);
                break;
            case CssUnit.EM:
            case CssUnit.EX:
            case CssUnit.REM:
                Debug.assertTrue(false, "Relative length is not allowed.");
                lengthInPt = mValue;
                break;
            default:
                Debug.assertTrue(false);
                lengthInPt = mValue;
                break;
        }

        return lengthInPt;
    }

    private double pointsToLength(/*CssUnit*/int unit)
    {
        double lengthInPoints = getLengthInPoints();
        double length;
        switch (unit)
        {
            case CssUnit.NONE:
                length = lengthInPoints;
                break;
            case CssUnit.IN:
                length = ConvertUtilCore.pointToInch(lengthInPoints);
                break;
            case CssUnit.CM:
                length = lengthInPoints / ConvertUtilCore.POINTS_PER_CM;
                break;
            case CssUnit.MM:
                length = lengthInPoints / ConvertUtilCore.POINTS_PER_MM;
                break;
            case CssUnit.PT:
                length = lengthInPoints;
                break;
            case CssUnit.PC:
                length = lengthInPoints / 12.0; // 1pc = 12pt.
                break;
            case CssUnit.PX:
                length = ConvertUtilCore.pointToPixel(lengthInPoints);
                break;
            case CssUnit.EM:
            case CssUnit.EX:
            case CssUnit.REM:
                Debug.assertTrue(false, "Relative length is not allowed.");
                length = lengthInPoints;
                break;
            default:
                Debug.assertTrue(false);
                length = lengthInPoints;
                break;
        }

        return length;
    }

    /*override*/ double getDoubleValue() { return mValue; }

    /*CssUnit*/int getUnit() { return mUnit; };

    private /*CssUnit*/int mUnit;

    boolean isRelative() { return (getUnit() == CssUnit.EM) || (getUnit() == CssUnit.EX) || (getUnit() == CssUnit.REM); }

    private /*final*/ double mValue;
}

