// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents a chart legend entry.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A legend entry corresponds to a specific chart series or trendline.</p>
/// <p>The text of the entry is the name of the series or trendline. The text cannot be changed.</p>
///
/// <seealso cref="ChartSeries.LegendEntry"/>
/// </remarks>
/// <dev>
/// Represents 5.7.2.95 legendEntry (Legend Entry) element.
/// </dev>
public class ChartLegendEntry implements IDmlExtensionListSource, IChartItemTextProperties, Cloneable
{
    /// <summary>
    /// Ctor for internal use only.
    /// </summary>
    ChartLegendEntry()
    {
    }

    ChartLegendEntry deepClone()
    {
        ChartLegendEntry lhs = (ChartLegendEntry)memberwiseClone();
        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        if (mExtensions != null)
            lhs.mExtensions = DmlExtensionListSource.cloneExtensions(mExtensions);

        lhs.mFont = null;

        return lhs;
    }

    /// <summary>
    /// Sets a <see cref="DmlChartFormat"/> instance that is a parent of this legend entry.
    /// </summary>
    void setParentDmlChartFormat(DmlChartFormat chartFormat)
    {
        mChartFormat = chartFormat;
    }

    /// <summary>
    /// Gets or sets a value indicating whether this entry is hidden in the chart legend.
    /// The default value is <b>false</b>.
    /// </summary>
    /// <remarks>
    /// When a chart legend entry is hidden, it does not affect the corresponding chart series or trendline that
    /// is still displayed on the chart.
    /// </remarks>
    public boolean isHidden() { return mIsHidden; }
    public void isHidden(boolean value) { mIsHidden = value; }

    /// <summary>
    /// Provides access to the font formatting of this legend entry.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
        {
            IRunAttrSource runAttrSource = new ChartItemDmlRunPropertiesSource(this, mChartFormat.getDmlChartSpace());
            mFont = Font.makeFont(runAttrSource, mChartFormat.getDocument());
        }

        return mFont;
    }

    int getIndex() { return mIndex; }
    void setIndex(int value) { mIndex = value; }

    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }

    /// <summary>
    /// Returns a flag indicating whether any properties of this legend entry have values ​​that differ from
    /// the corresponding properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting()
    {
        return
            isHidden() ||
            ((mTxPr != null) && !mTxPr.isEmpty()) ||
            ((mExtensions != null) && (mExtensions.getCount() > 0));
    }

    /// <summary>
    /// Represents extLst: a CT_OfficeArtExtensionList ([ISO/IEC29500-1:2012] section A.4.1) element that specifies
    /// the extension list in which all future extensions of element type ext is defined.
    ///  </summary>
    /// <dev>
    /// Explicit implementation hides this from public.
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public StringToObjDictionary<DmlExtension> /*IDmlExtensionListSource.*/getExtensions() { return mExtensions; }
    @ReservedForInternalUse @Deprecated
    public void /*IDmlExtensionListSource.*/setExtensions(StringToObjDictionary<DmlExtension> value) { mExtensions = value; }

    //>>>>>>>> #region  IChartItemTextProperties members

    @ReservedForInternalUse @Deprecated
    public String /*IChartItemTextProperties.*/generateItemText()
    {
        return null; // Not used for a legend entry.
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/fetchSpecialDefaultRunPropertyValue(int key)
    {
        return null; // No special default values.
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/getRelativePropertyValue(int key, Object value)
    {
        return value;
    }

    @ReservedForInternalUse @Deprecated
    public DmlChartTx /*IChartItemTextProperties.*/getItemTx() { return null; }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getItemTxPr() { return getTxPr(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartItemTextProperties.*/setItemTxPr(DmlChartTxPr value) { mTxPr = value; }

    @ReservedForInternalUse @Deprecated
    public DmlChartSpPr /*IChartItemTextProperties.*/getItemSpPr() { return null; }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getCollectionTxPr() { return mChartFormat.getLegend().getTxPr(); }

    //<<<<<<<< #endregion 

    private boolean mIsHidden;
    private int mIndex;
    private DmlChartTxPr mTxPr;
    private Font mFont;
    private StringToObjDictionary<DmlExtension> mExtensions;
    private DmlChartFormat mChartFormat;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

