// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 2.8.3.5 CT_MarkerLayout [MS-ODRAWXML]
/// The complex type specifies additional properties for data points that have markers.
/// </summary>
class DmlChartMarkerLayout implements Cloneable
{
    /// <summary>
    /// Clones this <see cref="DmlChartMarkerLayout"/> object.
    /// </summary>
    DmlChartMarkerLayout deepClone()
    {
        return (DmlChartMarkerLayout)memberwiseClone();
    }

    /// <summary>
    /// Represents symbol: an ST_MarkerStyle attribute that specifies a style for markers on a chart.
    /// </summary>
    /// <dev>
    /// The string type is used now. 2.8.4.4 ST_MarkerStyle [MS-ODRAWXML] has all values of
    /// Aspose.Words.Drawing.Charts.MarkerSymbol (21.2.3.27 ST_MarkerStyle [ISO/IEC29500-1:2012]) except
    /// the values 'none' and 'picture'.
    /// </dev>
    String getSymbol() { return mSymbol; }
    void setSymbol(String value) { mSymbol = value; }

    /// <summary>
    /// Represents size: an ST_MarkerSize attribute that specifies the size for markers on a chart.
    /// The simple type specifies that its contents contain an integer between 2 and 72, inclusive,
    /// whose contents are a size in points.
    /// </summary>
    int getSize() { return mSize; }
    void setSize(int value) { mSize = value; }

    /// <summary>
    /// Gets the marker symbol as a <see cref="MarkerSymbol"/> value.
    /// </summary>
    /*MarkerSymbol*/int getMarkerSymbol() { return DmlChartsEnum.dmlToMarkerStyle(getSymbol()); }

    private String mSymbol;
    private int mSize;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

