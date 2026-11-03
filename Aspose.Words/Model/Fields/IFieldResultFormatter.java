// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********




/// <summary>
/// Implement this interface if you want to control how the field result is formatted.
/// </summary>
public interface IFieldResultFormatter
{
    /// <summary>
    /// Called when Aspose.Words applies a numeric format switch, i.e. \# "#.##".
    /// </summary>
    /// <remarks>
    /// The implementation should return <c>null</c> to indicate that the default formatting should be applied.
    /// </remarks>
    public String formatNumeric(double value, String format);

    /// <summary>
    /// Called when Aspose.Words applies a date/time format switch, i.e. \@ "dd.MM.yyyy".
    /// </summary>
    /// <remarks>
    /// The implementation should return <c>null</c> to indicate that the default formatting should be applied.
    /// </remarks>
    public String formatDateTime(java.util.Date value, String format, /*CalendarType*/int calendarType);

    /// <summary>
    /// Called when Aspose.Words applies a capitalization format switch, i.e. \* Upper.
    /// </summary>
    /// <remarks>
    /// The implementation should return <c>null</c> to indicate that the default formatting should be applied.
    /// </remarks>
    public String format(String value, /*GeneralFormat*/int format);

    /// <summary>
    /// Called when Aspose.Words applies a number format switch, i.e. \* Ordinal.
    /// </summary>
    /// <remarks>
    /// The implementation should return <c>null</c> to indicate that the default formatting should be applied.
    /// </remarks>
    public String format(double value, /*GeneralFormat*/int format);
}

