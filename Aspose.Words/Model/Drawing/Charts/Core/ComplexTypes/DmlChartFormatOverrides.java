// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class represents the 2.24.3.26 CT_FormatOverride complex type [MS-ODRAWXML].
/// It specifies an override of a chart’s data point color style format.
/// </summary>
class DmlChartFormatOverride extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartFormatOverride deepClone()
    {
        DmlChartFormatOverride lhs = (DmlChartFormatOverride)memberwiseClone();

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Represents spPr: CT_ShapeProperties ([ISO/IEC29500-4:2012] section A.4.1) element that specifies the shape
    /// properties to override the chart’s color style format. A chart’s color style assigns a unique color format
    /// per data point according to an index.
    /// </summary>
    DmlChartSpPr getSpPr() { return mSpPr; }
    void setSpPr(DmlChartSpPr value) { mSpPr = value; }

    /// <summary>
    /// Gets or sets the index of the color format being overridden.
    /// </summary>
    int getIndex() { return mIndex; }
    void setIndex(int value) { mIndex = value; }

    private DmlChartSpPr mSpPr;
    private int mIndex;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

