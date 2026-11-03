// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/07/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Represents number formatting of the parent element.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
public class ChartNumberFormat
{
    ChartNumberFormat(INumberFormatProvider numFmtProvider, DmlChart chart)
    {
        mNumFmtProvider = numFmtProvider;
        mChart = chart;
    }

    void warn(/*WarningType*/int warningType, String message)
    {
        if (mChart != null)
            mChart.warn(warningType, message);
    }

    /// <summary>
    /// If the underlying number format value refers to inherited property value, i.e. to number format of a parent
    /// collection, the method removes this reference and assigns blank value (source-linked) to the number format.
    /// </summary>
    private void unlinkFromParent()
    {
        if (mNumFmtProvider.isInherited())
            setNumFmt(new DmlChartNumFormat());
    }

    /// <summary>
    /// Gets or sets the format code applied to a data label.
    /// </summary>
    /// <remarks>
    /// Number formatting is used to change the way a value appears in data label and can be used in some very creative ways.
    /// The examples of number formats:
    /// <para>Number - "#,##0.00"</para>
    /// <para>Currency - "\"$\"#,##0.00"</para>
    /// <para>Time - "[$-x-systime]h:mm:ss AM/PM"</para>
    /// <para>Date - "d/mm/yyyy"</para>
    /// <para>Percentage - "0.00%"</para>
    /// <para>Fraction - "# ?/?"</para>
    /// <para>Scientific - "0.00E+00"</para>
    /// <para>Text - "@"</para>
    /// <para>Accounting - "_-\"$\"* #,##0.00_-;-\"$\"* #,##0.00_-;_-\"$\"* \"-\"??_-;_-@_-"</para>
    /// <para>Custom with color - "[Red]-#,##0.0"</para>
    /// </remarks>
    public String getFormatCode()
    {
        return (getNumFmt() != null) ? getNumFmt().getSourceFormatCode() : "";
    }
    public void setFormatCode(String value)
    {
        unlinkFromParent();

        // Mimic MSW behavior and set NumberFormatIsLinkedToSource = false when any custom number format is set.
        isLinkedToSource(false);

        if (StringUtil.hasChars(value))
        {
            getNumFmt().setFormatCode(value);
        }
        else
        {
            getNumFmt().setFormatCode(GENERAL_FORMAT_CODE);
            warn(WarningType.HINT, "An empty string is incorrect NumberFormat, changed to General.");
        }
    }

    /// <summary>
    /// Specifies whether the format code is linked to a source cell.
    /// Default is true.
    /// </summary>
    /// <remarks>The NumberFormat will be reset to general if format code is linked to source.</remarks>
    public boolean isLinkedToSource()
    {
        return linkedToSource();
    }
    public void isLinkedToSource(boolean value)
    {
        if (linkedToSource() == value)
            return;

        unlinkFromParent();

        if (getNumFmt() == null)
            setNumFmt(new DmlChartNumFormat());

        getNumFmt().setSourceLinked(value);
        getNumFmt().setFormatCode(value ? "" : GENERAL_FORMAT_CODE);
    }

    private boolean linkedToSource()
    {
        return (getNumFmt() == null) || getNumFmt().getSourceLinked();
    }

    private DmlChartNumFormat getNumFmt() { return mNumFmtProvider.getNumFmt_INumberFormatProvider(); }
    private void setNumFmt(DmlChartNumFormat value) { mNumFmtProvider.setNumFmt_INumberFormatProvider(value); }

    private /*final*/ INumberFormatProvider mNumFmtProvider;
    private /*final*/ DmlChart mChart;

    /// <summary>
    /// The General format is the default number format that MSW applies when you type a number. 
    /// </summary>
    static final String GENERAL_FORMAT_CODE = "General";

    /// <summary>
    /// Default date format. When this format is specified, MS Word uses current culture date format.
    /// </summary>
    static final String DEFAULT_DATE_FORMAT_CODE = "m/d/yyyy";

    /// <summary>
    /// Default time format.
    /// </summary>
    static final String DEFAULT_TIME_FORMAT_CODE = "h:mm";
}

