// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/07/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, provides necessary internal DmlChartNumFormat object to work 
/// with public ChartDataLabel/ChartAxis NumberFormat.
/// </summary>
interface INumberFormatProvider
{
    /// <summary>
    /// Specifies number formatting for the parent element.
    /// <see cref="DmlChartNumFormat"/>.
    /// </summary>
    public DmlChartNumFormat getNumFmt_INumberFormatProvider(); public void setNumFmt_INumberFormatProvider(DmlChartNumFormat value);

    /// <summary>
    /// Returns a flag indicating whether the provider uses number format that is inherited from the parent number
    /// format provider.
    /// </summary>
    public boolean isInherited();
}

