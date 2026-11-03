// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class represents the CT_ChartLines complex type [ISO/IEC 29500] and the 2.24.3.52 CT_Gridlines
/// complex type [MS-ODRAWXML].
/// </summary>
class DmlChartGridlines extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    DmlChartGridlines deepClone()
    {
        DmlChartGridlines lhs = (DmlChartGridlines)memberwiseClone();

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Gets the OfficeArt shape properties for the gridlines.
    /// </summary>
    DmlChartSpPr getSpPr() { return mSpPr; }

    private DmlChartSpPr mSpPr = new DmlChartSpPr();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

