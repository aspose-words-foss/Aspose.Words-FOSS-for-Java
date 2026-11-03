// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents chart legend properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <dev>
/// 5.7.2.94 legend (Legend) element.
/// </dev>
public class ChartLegend implements IDmlExtensionListSource, IChartFormatSource, Cloneable
{
    /// <summary>
    /// Initializes a new instance of the <see cref="ChartLegend"/> class.
    /// </summary>
    /// <dev>
    /// A customer should not be able to create an instance of this class.
    /// </dev>
    ChartLegend()
    {
    }

    /// <summary>
    /// Initializes a new instance of the this class with defining a parent <see cref="DmlChartFormat"/> object.
    /// </summary>
    ChartLegend(DmlChartFormat chartFormat)
    {
        mChartFormat = chartFormat;
        mLegendEntries = new ChartLegendEntryCollection(chartFormat);
    }

    void addEntry(ChartLegendEntry entry)
    {
        mLegendEntries.addEntry(entry);
    }

    ChartLegendEntry getEntry(int index)
    {
        return mLegendEntries.getEntry(index);
    }

    ChartLegend deepClone(DmlChartFormat parentDmlChartFormat)
    {
        ChartLegend lhs = (ChartLegend)memberwiseClone();
        lhs.mChartFormat = parentDmlChartFormat;

        if (mLayout != null)
            lhs.mLayout = mLayout.deepClone();

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        lhs.mLegendEntries = mLegendEntries.deepClone(parentDmlChartFormat);

        if (mExtensions != null)
            lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        lhs.mFont = null;

        return lhs;
    }

    /// <summary>
    /// Sets the legend position without applying the chart style.
    /// </summary>
    void setPositionInternal(/*LegendPosition*/int value)
    {
        mIsVisible = (value != LegendPosition.NONE);

        mPositionAlignment = (value == LegendPosition.TOP_RIGHT)
            ? PositionAlignment.MAXIMUM
            : PositionAlignment.CENTER;

        switch (value)
        {
            case LegendPosition.BOTTOM:
                mSidePosition = SidePosition.BOTTOM;
                break;
            case LegendPosition.LEFT:
                mSidePosition = SidePosition.LEFT;
                break;
            case LegendPosition.RIGHT:
                mSidePosition = SidePosition.RIGHT;
                break;
            case LegendPosition.TOP:
            case LegendPosition.TOP_RIGHT:
                mSidePosition = SidePosition.TOP;
                break;
            case LegendPosition.NONE:
            default:
                break;
        }
    }

    /// <summary>
    /// Returns a collection of legend entries for all series and trendlines of the parent chart.
    /// </summary>
    public ChartLegendEntryCollection getLegendEntries() { return mLegendEntries; }

    /// <summary>
    /// Specifies the position of the legend on a chart.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="LegendPosition.Right"/> for pre-Word 2016 charts and
    /// <see cref="LegendPosition.Top"/> for Word 2016 charts.
    /// </remarks>
    public /*LegendPosition*/int getPosition()
    {
        if (!mIsVisible)
            return LegendPosition.NONE;

        switch (mSidePosition)
        {
            case SidePosition.LEFT:
                return LegendPosition.LEFT;
            case SidePosition.TOP:
                return (mPositionAlignment == PositionAlignment.MAXIMUM)
                    ? LegendPosition.TOP_RIGHT
                    : LegendPosition.TOP;
            case SidePosition.RIGHT:
                return LegendPosition.RIGHT;
            case SidePosition.BOTTOM:
                return LegendPosition.BOTTOM;
            default:
                return DEFAULT_POSITION;
        }
    }
    public void setPosition(/*LegendPosition*/int value)
    {
        if (getPosition() == value)
            return;

        boolean oldIsVisible = mIsVisible;

        setPositionInternal(value);

        if (!oldIsVisible && mIsVisible)
            mChartFormat.getDmlChartSpace().applyChartStyle(this);
    }

    /// <summary>
    /// Provides access to the default font formatting of legend entries. To override the font formatting for
    /// a specific legend entry, use the<see cref="ChartLegendEntry.Font"/> property.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
        {
            IRunAttrSource runAttrSource =
                new ChartCollectionDmlRunPropertiesSource(getTxPr(), mChartFormat.getDmlChartSpace());
            mFont = Font.makeFont(runAttrSource, mChartFormat.getDocument());
        }

        return mFont;
    }

    /// <summary>
    /// Provides access to fill and line formatting of the legend.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Gets or sets the legend side position.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="Core.SimpleTypes.SidePosition.Right"/> for pre-Word 2016 charts and
    /// <see cref="Core.SimpleTypes.SidePosition.Top"/> for Word 2016 charts.
    /// </remarks>
    /*SidePosition*/int getSidePosition() { return mSidePosition; }
    void setSidePosition(/*SidePosition*/int value) { mSidePosition = value; }

    /// <summary>
    /// Gets or sets the alignment along the side position of the legend.
    /// </summary>
    /// <remarks>
    /// The default position alignment is <see cref="Core.SimpleTypes.PositionAlignment.Center"/>.
    /// The property has limited use for charts prior to Word 2016: only the Center and Maximum values can be used,
    /// and the Maximum value can only be used with the Top side position.
    /// </remarks>
    /*PositionAlignment*/int getPositionAlignment() { return mPositionAlignment; }
    void setPositionAlignment(/*PositionAlignment*/int value) { mPositionAlignment = value; }

    /// <summary>
    /// Determines whether other chart elements shall be allowed to overlap legend.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getOverlay() { return mOverlay; }
    public void setOverlay(boolean value) { mOverlay = value; }

    DmlChartManualLayout getLayout() { return mLayout; }
    void setLayout(DmlChartManualLayout value) { mLayout = value; }

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

    /// <summary>
    /// Gets or sets a flag indicating whether the legend is visible on the chart.
    /// </summary>
    boolean isVisible() { return mIsVisible; }
    void isVisible(boolean value) { mIsVisible = value; }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList ([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    ///  </summary>
    /// <remarks>
    /// Explicit implementation hides this from public.
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // Do nothing for a legend.
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFillSupported() { return true; }

    @ReservedForInternalUse @Deprecated
    public DmlFill /*IChartFormatSource.*/getFill() { return getSpPr().getFill(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setFill(DmlFill value) { getSpPr().setFill(value); }

    @ReservedForInternalUse @Deprecated
    public DmlOutline /*IChartFormatSource.*/getOutline() { return getSpPr().getOutline(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setOutline(DmlOutline value) { getSpPr().setOutline(value); }

    @ReservedForInternalUse @Deprecated
    public /*ChartShapeType*/int /*IChartFormatSource.*/getShapeType()
    {
        // Not supported by a legend.
        return ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        // Not supported by a legend.
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return mChartFormat.getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    private StringToObjDictionary<DmlExtension> mExtensions;
    private DmlChartManualLayout mLayout;
    private boolean mOverlay;
    private DmlChartSpPr mSpPr;
    private DmlChartTxPr mTxPr;
    private ChartLegendEntryCollection mLegendEntries;
    private boolean mIsVisible;
    private /*SidePosition*/int mSidePosition = SidePosition.RIGHT;
    private /*PositionAlignment*/int mPositionAlignment = PositionAlignment.CENTER;
    private Font mFont;
    private ChartFormat mFormat;
    private DmlChartFormat mChartFormat;

    static final /*LegendPosition*/int DEFAULT_POSITION = LegendPosition.RIGHT;
    static final /*SidePosition*/int CHART_EX_DEFAULT_SIDE_POSITION = SidePosition.TOP;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

