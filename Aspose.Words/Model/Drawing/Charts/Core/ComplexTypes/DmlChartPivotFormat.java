// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.143 pivotFmt (Pivot Format) element.
/// This element contains a set of formatting to be applied to the chart that is based on a pivotTable.
/// </summary>
class DmlChartPivotFormat extends DmlExtensionListSource implements Cloneable
{
    DmlChartPivotFormat(IThemeProvider themeProvider)
    {
        mThemeProvider = themeProvider;
    }

    DmlChartPivotFormat deepClone()
    {
        DmlChartPivotFormat lhs = (DmlChartPivotFormat)memberwiseClone();
        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mDLbl != null)
            lhs.mDLbl = mDLbl.deepClone();

        if (mMarker != null)
            lhs.mMarker = mMarker.deepClone();

        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    ChartDataLabel getDLbl() { return mDLbl; }
    void setDLbl(ChartDataLabel value) { mDLbl = value; }

    int getIndex() { return mIndex; }
    void setIndex(int value) { mIndex = value; }

    ChartMarker getMarker()
    {
        if (mMarker == null)
            mMarker = new ChartMarker(mThemeProvider);

        return mMarker;
    }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }

    private ChartDataLabel mDLbl;
    private int mIndex;
    private ChartMarker mMarker;
    private DmlChartSpPr mSpPr;
    private DmlChartTxPr mTxPr;
    private /*final*/ IThemeProvider mThemeProvider;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

