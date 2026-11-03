// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.213 trendlineLbl (Trendline Label) element.
/// This element specifies the label for the trendline.
/// </summary>
class DmlChartTrendlineLabel extends DmlExtensionListSource implements Cloneable
{
    DmlChartTrendlineLabel deepClone()
    {
        DmlChartTrendlineLabel lhs = (DmlChartTrendlineLabel)memberwiseClone();
        if (mLayout != null)
            lhs.mLayout = mLayout.deepClone();

        if (mNumFmt != null)
            lhs.mNumFmt = mNumFmt.deepClone();

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mTx != null)
            lhs.mTx = mTx.deepClone();

        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    DmlChartManualLayout getLayout() { return mLayout; }
    void setLayout(DmlChartManualLayout value) { mLayout = value; }

    DmlChartNumFormat getNumFmt() { return mNumFmt; }
    void setNumFmt(DmlChartNumFormat value) { mNumFmt = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    /// <summary>
    /// Indicates whether the data label position should be changed. 
    /// </summary>
    boolean isPositionChanged() { return hasLayout() && (getLayout().isXSet() || getLayout().isYSet()); }

    /// <summary>
    /// Indicates whether size of label should be changed. 
    /// </summary>
    boolean isSizeChanged() { return hasLayout() && (getLayout().isWidthSet() || getLayout().isHeightSet()); }

    /// <summary>
    /// Indicates whether the data label has layout. 
    /// </summary>
    boolean hasLayout() { return (getLayout() != null); }

    DmlChartTx getTx() { return mTx; }
    void setTx(DmlChartTx value) { mTx = value; }

    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }

    private DmlChartManualLayout mLayout;
    private DmlChartNumFormat mNumFmt;
    private DmlChartSpPr mSpPr;
    private DmlChartTx mTx;
    private DmlChartTxPr mTxPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

