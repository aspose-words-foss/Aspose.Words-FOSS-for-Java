// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msMath;


/// <summary>
/// Represents 5.7.2.27 chart (Chart) element.
/// This element specifies the chart.
/// </summary>
class DmlChartFormat extends DmlExtensionListSource implements IDmlChartTitleHolder, Cloneable
{
    /// <summary>
    /// If font size is not specified for a chart title, MS Word uses 1.2 * [font size of chart space]. This method
    /// is used to get such calculated font size.
    /// </summary>
    public int getRelativeFontSize(int chartFontSize)
    {
        return (int)msMath.round(chartFontSize * 1.2);
    }

    public void warn(/*WarningType*/int warningType, String message)
    {
        if (mChartSpace == null)
            return;

        IWarningCallback warningCallback = mChartSpace.getDml().getDocument().getWarningCallback();
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(warningType, WarningSource.DRAWING_ML, message));
    }

    void setChartSpace(DmlChartSpace chartSpace)
    {
        mChartSpace = chartSpace;
    }

    DmlChartFormat deepClone()
    {
        DmlChartFormat lhs = (DmlChartFormat)memberwiseClone();
        if (mTitle != null)
        {
            lhs.mTitle = mTitle.deepClone();
            lhs.mTitle.setTitleHolder(lhs);
        }

        if (mBackWall != null)
            lhs.mBackWall = mBackWall.deepClone();

        if (mFloor != null)
            lhs.mFloor = mFloor.deepClone();

        if (mSideWall != null)
            lhs.mSideWall = mSideWall.deepClone();

        if (mLegend != null)
            lhs.mLegend = mLegend.deepClone(lhs);

        if (mPivotFmts != null)
            lhs.mPivotFmts = mPivotFmts.deepClone();

        if (mPlotArea != null)
        {
            DmlChartPlotArea cloned = mPlotArea.deepClone();
            cloned.setChartFormat(lhs);
            lhs.mPlotArea = cloned;
        }

        if (mView3D != null)
            lhs.mView3D = mView3D.deepClone();

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    DmlChartSpace getDmlChartSpace() { return mChartSpace; }

    boolean getAutoTitleDeleted() { return mAutoTitleDeleted; }
    void setAutoTitleDeleted(boolean value) { mAutoTitleDeleted = value; }

    boolean getPlotVisOnly() { return mPlotVisOnly; }
    void setPlotVisOnly(boolean value) { mPlotVisOnly = value; }

    boolean getShowDLblsOverMax() { return mShowDLblsOverMax; }
    void setShowDLblsOverMax(boolean value) { mShowDLblsOverMax = value; }

    DmlChartSurface getBackWall() { return mBackWall; }
    void setBackWall(DmlChartSurface value) { mBackWall = value; }

    DmlChartSurface getFloor() { return mFloor; }
    void setFloor(DmlChartSurface value) { mFloor = value; }

    DmlChartSurface getSideWall() { return mSideWall; }
    void setSideWall(DmlChartSurface value) { mSideWall = value; }

    /*DisplayBlanksAs*/int getDispBlanksAs() { return mDispBlanksAs; }
    void setDispBlanksAs(/*DisplayBlanksAs*/int value) { mDispBlanksAs = value; }

    ChartLegend getLegend() { return mLegend; }
    void setLegend(ChartLegend value) { mLegend = value; }

    DmlChartPivotFormats getPivotFmts() { return mPivotFmts; }
    void setPivotFmts(DmlChartPivotFormats value) { mPivotFmts = value; }

    DmlChartPlotArea getPlotArea() { return mPlotArea; }
    void setPlotArea(DmlChartPlotArea value) { mPlotArea = value; }

    DmlChartView3D getView3D() { return mView3D; }
    void setView3D(DmlChartView3D value) { mView3D = value; }

    /// <summary>
    /// Gets or sets title.
    /// </summary>
    public DmlChartTitle getDCTitle() { return mTitle; }
    public void setDCTitle(DmlChartTitle value) { mTitle = value; }

    /// <summary>
    /// Returns the position of the title.
    /// </summary>
    public /*TitlePosition*/int getTitlePosition() { return TitlePosition.TOP; }

    /// <summary>
    /// Indicates whether the chart title is visible.
    /// </summary>
    public boolean /*IDmlChartTitleHolder.*/isVisible() { return !getAutoTitleDeleted() && (getDCTitle() != null); }

    /// <summary>
    /// Gets the default title text.
    /// </summary>
    public String /*IDmlChartTitleHolder.*/getDefaultTitleText() { return DefaultChartTitles.getTitle(); }

    /// <summary>
    /// Gets the title default font size in points.
    /// </summary>
    public double /*IDmlChartTitleHolder.*/getDefaultFontSize() { return mChartSpace.isChartEx() ? 14 : 18; }

    /// <summary>
    /// Gets the title font size in points that MS Word sets for created charts.
    /// </summary>
    public double /*IDmlChartTitleHolder.*/getDefaultDisplayedFontSize() { return 14.0; }

    public /*DmlChartStyleItem*/int /*IDmlChartTitleHolder.*/getStyleItem() { return DmlChartStyleItem.TITLE; }

    /// <summary>
    /// Gets the parent chart space.
    /// </summary>
    public DmlChartSpace /*IDmlChartTitleHolder.*/getChartSpace() { return mChartSpace; }

    /// <summary>
    /// Returns the document containing the parent chart.
    /// </summary>
    DocumentBase getDocument() { return mChartSpace.getDml().getDocument(); }

    /// <summary>
    /// Returns true if title should not be displayed even if it exists.
    /// </summary>
    public boolean getTitleDeleted() { return getAutoTitleDeleted(); }
    public void setTitleDeleted(boolean value)
    {
        if (getAutoTitleDeleted() == value)
            return;

        setAutoTitleDeleted(value);
        if (!value)
        {
            if (mTitle == null)
                mTitle = new DmlChartTitle(this);

            mChartSpace.applyChartStyleToChartTitle();
        }
    }

    private boolean mAutoTitleDeleted;
    private DmlChartSurface mBackWall;
    private /*DisplayBlanksAs*/int mDispBlanksAs = DisplayBlanksAs.ZERO;
    private DmlChartSurface mFloor;
    private ChartLegend mLegend;
    private DmlChartPivotFormats mPivotFmts;
    private DmlChartPlotArea mPlotArea;
    private boolean mPlotVisOnly;
    private boolean mShowDLblsOverMax;
    private DmlChartSurface mSideWall;
    private DmlChartTitle mTitle;
    private DmlChartView3D mView3D;
    private DmlChartSpace mChartSpace;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

