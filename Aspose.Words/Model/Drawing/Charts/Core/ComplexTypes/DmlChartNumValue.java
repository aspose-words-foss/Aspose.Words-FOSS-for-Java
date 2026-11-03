// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.NullableBool;
import com.aspose.StringUtil;


/// <summary>
/// Represents pt (Numeric Point) (5.7.2.151) element.
/// This element specifies data for a particular data point.
/// </summary>
class DmlChartNumValue extends DmlChartValue
{
    DmlChartNumValue(int index, double value)
    {
    	this(index, value, null);
	
    }

    DmlChartNumValue(int index, double value, String formatCode)
    {
    	this(index, value, formatCode, 1.0);
	
    }

    DmlChartNumValue(int index, double value, String formatCode, double displayUnit)
    {
    	super(index, DmlChartValueType.NUMERIC);
	
        mValue = value;
        setSourceFormatCode(formatCode);
        mDisplayUnit = displayUnit;
    }

    void setDisplayUnit(double displayUnit)
    {
        mDisplayUnit = displayUnit;
    }

    static String valueToString(double value, double displayUnit, String formatCode)
    {
        if (Double.isNaN(value))
            return NAN_STRING_VALUE;

        double displayValue = value / displayUnit;

        // FormatterPal needed for java since ordinal double format has too big precision in java.
        // Special processing for "general" format is required.
        if (DmlChartFormatCodeValidator.isGeneralFormatCode(formatCode))
            return DmlChartRenderingUtil.getGeneralFormatStringValue(displayValue);
        else
            return DmlChartFormatCodeValidator.isDateFormat(formatCode)
                ? toDateString(value, formatCode)
                : DmlChartRenderingUtil.doubleToString(displayValue, formatCode);
    }
  
    /// <summary>
    /// Converts date value to string.
    /// </summary>
    private static String toDateString(double value, String formatCode)
    {
        // If the DmlChartNumValue is a date and less than 0, MS Word do not display the string value.
        if (value < 0)
            return "";

        DateTime date = DmlChartUtil.getDateFromDouble(value);

        // If format code is not specified lets use standard one.
        if (DmlChartFormatCodeValidator.isGeneralFormatCode(formatCode))
            formatCode = FormatterPal.getShortDatePatternCurrent();

        return DmlChartRenderingUtil.getStringDate(date, formatCode);
    }

    /*override*/ String getStringValue()
    {
        if (msString.isNullOrEmpty(mStringValue))
            mStringValue = valueToString(mValue, mDisplayUnit, mFormatCode);

        return mStringValue;
    }

    /// <summary>
    /// Returns true if format string is date format.
    /// </summary>
    /*override*/ boolean isDate()
    {
        if (mIsDate == NullableBool.NOT_DEFINED)
        {
            String formatCode = getFormatCode();
            if (!StringUtil.hasChars(formatCode) && (getCollection() != null))
                formatCode = getCollection().getFormatCode();

            mIsDate = DmlChartFormatCodeValidator.isDateFormat(formatCode)
                ? NullableBool.TRUE
                : NullableBool.FALSE;
        }

        return (mIsDate == NullableBool.TRUE);
    }

    /*override*/ double getValue() { return mValue; }

    String getFormatCode() { return mFormatCode; }
    void setFormatCode(String value)
    {
        mFormatCode = DmlChartFormatCodeValidator.validateFormatCode(value);
        mIsDate = NullableBool.NOT_DEFINED;
    }

    /// <summary>
    /// Returns format code set in the document.
    /// Used for roundtrip.
    /// </summary>
    String getSourceFormatCode() { return mSourceFormatCode; }
    private void setSourceFormatCode(String value)
    {
        mSourceFormatCode = value;
        setFormatCode(mSourceFormatCode);
    }

    /// <summary>
    /// Returns the double value based on the specified display unit value (default "1").
    /// </summary>
    double getDisplayUnitValue() { return mValue / mDisplayUnit ; }

    private /*final*/ double mValue;
    private String mFormatCode;
    private String mSourceFormatCode;
    private double mDisplayUnit;
    private String mStringValue = "";
    private /*NullableBool*/byte mIsDate = NullableBool.NOT_DEFINED;
}

