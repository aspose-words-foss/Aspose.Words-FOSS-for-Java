// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/24/2014 by Alexey Noskov


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 5.7.2.13 bandFmt (Band Format) This element specifies the formatting band of a surface chart.
/// </summary>
class DmlChartBandFormat implements Cloneable
{
    DmlChartBandFormat(int index, DmlChartSpPr spPr)
    {
        mIndex = index;
        mSpPr = spPr;
    }

    DmlChartBandFormat deepClone()
    {
        DmlChartBandFormat lhs = (DmlChartBandFormat)memberwiseClone();
        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        return lhs;
    }

    int getIndex() { return mIndex; }

    DmlChartSpPr getSpPr() { return mSpPr; }

    private /*final*/ int mIndex;
    private DmlChartSpPr mSpPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

