// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.219 upDownBars (Up/Down Bars) element.
/// This element specifies the up and down bars.
/// </summary>
class DmlChartUpDownBars extends DmlExtensionListSource implements Cloneable
{
    DmlChartUpDownBars deepClone()
    {
        DmlChartUpDownBars lhs = (DmlChartUpDownBars)memberwiseClone();

        if (mUpBars != null)
            lhs.mUpBars = mUpBars.deepClone();

        if (mDownBars != null)
            lhs.mDownBars = mDownBars.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Valid range of values is [0;500].
    /// </summary>
    int getGapWidth() { return mGapWidth; }
    void setGapWidth(int value)
    {
        if (value >= 0 && value <= 500)
            mGapWidth = value;
    }

    DmlChartSpPr getUpBars()
    {
        if (mUpBars == null)
            mUpBars = new DmlChartSpPr();

        return mUpBars;
    }

    DmlChartSpPr getDownBars()
    {
        if (mDownBars == null)
            mDownBars = new DmlChartSpPr();

        return mDownBars;
    }

    /// <summary>
    /// There is no such field in the spec. Added for convenience.
    /// If this flag is true up-down bars should not be rendered in line charts.
    /// Has no effect for stock charts.
    /// </summary>
    boolean isVisible() { return mIsVisible; }
    void isVisible(boolean value) { mIsVisible = value; }

    boolean hasUpBars() { return mHasUpBars; }
    void hasUpBars(boolean value) { mHasUpBars = value; }

    boolean hasDownBars() { return mHasDownBars; }
    void hasDownBars(boolean value) { mHasDownBars = value; }

    boolean isEmpty() { return !hasUpBars() && !hasDownBars(); }

    /// <summary>
    /// Default gap width is 150% of bar width.
    /// </summary>
    private int mGapWidth = 150;
    private DmlChartSpPr mUpBars;
    private boolean mHasUpBars;
    private DmlChartSpPr mDownBars;
    private boolean mHasDownBars;
    private boolean mIsVisible = false;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

