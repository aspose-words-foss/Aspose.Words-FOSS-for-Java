// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents CT_Surface complex type that specifies the back wall, side wall or floor of the chart.
/// </summary>
class DmlChartSurface extends DmlExtensionListSource implements Cloneable
{
    DmlChartSurface deepClone()
    {
        DmlChartSurface lhs = (DmlChartSurface)memberwiseClone();
        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mPictureOptions != null)
            lhs.mPictureOptions = mPictureOptions.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    DmlChartPictureOptions getPictureOptions() { return mPictureOptions; }
    void setPictureOptions(DmlChartPictureOptions value) { mPictureOptions = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    int getThickness() { return mThickness; }
    void setThickness(int value) { mThickness = value; }

    private DmlChartPictureOptions mPictureOptions;
    private DmlChartSpPr mSpPr;
    private int mThickness;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

