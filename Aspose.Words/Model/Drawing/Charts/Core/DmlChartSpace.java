// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents 5.7.2.29 chartSpace (Chart Space) element.
/// This element specifies overall settings for a single chart, and is the root node for the chart part.
/// Root element of DrawingML Chart part.
/// </summary>
class DmlChartSpace extends DmlNode implements IDmlCommonShapePrSource
{
    DmlChartSpace()
    {
    	this(false);
	
    }

    DmlChartSpace(boolean isChartEx)
    {
        mIsChartEx = isChartEx;
    }

    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        // Probably we should set mEmbeddedData data to null here. It seems we do the same for embedded ole objects in shapes.

        DmlChartSpace lhs = (DmlChartSpace) super.deepClone(isCloneChildren, cloningListener);

        if (mChartFormat != null)
        {
            lhs.setChartFormat(mChartFormat.deepClone());
            lhs.getChartFormat().setChartSpace(lhs);
        }

        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mPivotSource != null)
            lhs.mPivotSource = mPivotSource.deepClone();

        if (mProtection != null)
            lhs.mProtection = mProtection.deepClone();

        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        if (mThemeOverride != null)
            lhs.mThemeOverride = mThemeOverride.deepClone();

        if (mColorMapOverride != null)
            lhs.mColorMapOverride = mColorMapOverride.deepClone();

        if (mColorStyle != null)
            lhs.mColorStyle = mColorStyle.deepClone();

        if (mDmlChartStyle != null)
            lhs.mDmlChartStyle = mDmlChartStyle.deepClone();

        if (mData != null)
            lhs.mData = mData.deepClone();

        if (mFormatOverrides != null)
        {
            lhs.mFormatOverrides = new ArrayList<DmlChartFormatOverride>();
            for (DmlChartFormatOverride item : mFormatOverrides)
                msArrayList.add(lhs.mFormatOverrides, item.deepClone());
        }

        if (mExtLst != null)
        {
            lhs.mExtLst = new StringToObjDictionary<DmlExtension>();
            for (DmlExtension ext : mExtLst.getValues())
            {
                DmlExtension cloned = ext.deepClone();
                lhs.mExtLst.set(cloned.getUri(), cloned);
            }
        }

        return lhs;
    }

    void removeExternalDataLinkage()
    {
        // Just remove embedded document or link to a file, but keep reference formulas like '<c:f>Sheet1!$B$1</c:f>'
        // to preserve structure of XLSX file if possible.
        setEmbeddedData(null);
        mLinkedData = null;
    }

    /// <summary>
    /// Sets the specified chart style to the chart space.
    /// </summary>
    void setChartStyle(/*ChartStyle*/int chartStyle) throws Exception
    {
        final /*ChartSeriesType*/int UNKNOWN_SERIES_TYPE = (/*ChartSeriesType*/int)(-1);
        /*ChartSeriesType*/int seriesType = UNKNOWN_SERIES_TYPE;
        if (getFirstChart() != null)
        {
            seriesType = (getFirstChart().getSeries().getCount() > 0)
                ? getFirstChart().getSeries().get(0).getSeriesType()
                : getFirstChart().getSeriesType();
        }

        setChartStyle(chartStyle, DmlChartUtil.seriesTypeToChartType(seriesType), true);
    }

    /// <summary>
    /// Sets the specified chart style to the chart space.
    /// </summary>
    void setChartStyle(/*ChartStyle*/int chartStyle, /*ChartType*/int chartType, boolean apply) throws Exception
    {
        int styleId = ChartStyleResolver.getStyleId(chartType, chartStyle);
        mDmlChartStyle = PresetChartReader.readChartStyle(styleId, (Document)getDml().getDocument());

        if (apply)
        {
            ChartStyleApplier styleApplier = new ChartStyleApplier(this);
            styleApplier.applyStyle();
        }
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified series.
    /// </summary>
    void applyChartStyle(ChartSeries series)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(series);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified data labels.
    /// </summary>
    void applyChartStyle(ChartDataLabelCollection dataLabels)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(dataLabels);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified axis.
    /// </summary>
    void applyChartStyle(ChartAxis axis)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(axis);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified legend.
    /// </summary>
    void applyChartStyle(ChartLegend legend)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(legend);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified data table.
    /// </summary>
    void applyChartStyle(ChartDataTable dataTable)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(dataTable);
    }

    /// <summary>
    /// Applies the specified chart style item to the text properties.
    /// </summary>
    void applyChartStyle(DmlChartTxPr txPr, /*DmlChartStyleItem*/int styleItem)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(txPr, styleItem);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified marker.
    /// </summary>
    void applyChartStyle(ChartMarker marker, int seriesIndex)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyle(marker, seriesIndex);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the title of the specified axis.
    /// </summary>
    void applyChartStyleToAxisTitle(ChartAxis axis)
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyleToAxisTitle(axis);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the chart title.
    /// </summary>
    void applyChartStyleToChartTitle()
    {
        if (mDmlChartStyle == null)
            return;

        ChartStyleApplier styleApplier = new ChartStyleApplier(this);
        styleApplier.applyStyleToChartTitle();
    }

    /*override*/ /*DmlNodeType*/int getDmlNodeType() { return mIsChartEx ? DmlNodeType.CHART_EX : DmlNodeType.CHART; }

    //>>>>>>>> #region  IDmlCommonShapePrSource implementation

    public DmlFill getFill() { return getSpPr().getFill(); }
    public void setFill(DmlFill value) { getSpPr().setFill(value); }

    public DmlOutline getOutline() { return getSpPr().getOutline(); }
    public void setOutline(DmlOutline value) { getSpPr().setOutline(value); }

    public DmlShapeStyle /*IDmlCommonShapePrSource.*/getStyle()
    {
        // Currently only index of the ShapeStyle is stored in the model, to preserve it during
        // open/save, please see StyleIndex property.
        // That is why null is always returned.
        return null;
    }
    public void /*IDmlCommonShapePrSource.*/setStyle(DmlShapeStyle value)
    {
        // Do nothing.
    }

    //<<<<<<<< #endregion 

    DmlChartFormat getChartFormat() { return mChartFormat; }
    void setChartFormat(DmlChartFormat value)
    {
        value.setChartSpace(this);
        mChartFormat = value;
    }

    /// <summary>
    /// Gets a list of DML charts of this chart space.
    /// </summary>
    ArrayList<DmlChart> getCharts() { return mChartFormat.getPlotArea().getCharts(); }

    /// <summary>
    /// Gets the first DML chart of this chart space.
    /// </summary>
    DmlChart getFirstChart() { return mChartFormat.getPlotArea().getFirstChart(); }

    boolean getDate1904() { return mDate1904; }
    void setDate1904(boolean value) { mDate1904 = value; }

    IEmbeddedObject getEmbeddedData() { return mEmbeddedData; }; void setEmbeddedData(IEmbeddedObject value) { mEmbeddedData = value; };

    private IEmbeddedObject mEmbeddedData;

    String getLinkedData() { return mLinkedData; }
    void setLinkedData(String value) { mLinkedData = value; }

    boolean hasExternalData() { return ((getEmbeddedData() != null) || (getLinkedData() != null)); }

    String getLang() { return mLang; }
    void setLang(String value) { mLang = value; }

    DmlChartPivotSource getPivotSource() { return mPivotSource; }
    void setPivotSource(DmlChartPivotSource value) { mPivotSource = value; }

    DmlChartProtection getProtection() { return mProtection; }
    void setProtection(DmlChartProtection value) { mProtection = value; }

    boolean getRoundedCorners() { return mRoundedCorners; }
    void setRoundedCorners(boolean value) { mRoundedCorners = value; }

    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    int getStyleIndex() { return mStyleIndex; }
    void setStyleIndex(int value)
    {
        if (value >= 1 && value <= 48)
            mStyleIndex = value;
    }

    // Negative value means property is not set.
    int getWord2010Style() { return mWord2010Style; }
    void setWord2010Style(int value) { mWord2010Style = value; }

    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }
    void setTxPr(DmlChartTxPr value) { mTxPr = value; }

    /// <summary>
    /// This is external data of the chart.
    /// Temporary solution for writing charts.
    /// </summary>
    byte[] getChartExternalData() { return mChartExternalData; }
    void setChartExternalData(byte[] value) { mChartExternalData = value; }

    /// <summary>
    /// Theme override for chart.
    /// </summary>
    Theme getThemeOverride() { return mThemeOverride; }
    void setThemeOverride(Theme value) { mThemeOverride = value; }

    /// <summary>
    /// Flag indicates whether chart must be auto updated from external data.
    /// Default is true.
    /// </summary>
    boolean getAutoUpdate() { return mAutoUpdate; }
    void setAutoUpdate(boolean value) { mAutoUpdate = value; }

    DmlChartColorMapOverride getColorMapOverride() { return mColorMapOverride; }
    void setColorMapOverride(DmlChartColorMapOverride value) { mColorMapOverride = value; }

    /// <summary>
    /// Gets or sets <see cref="DmlChartColorStyle"/> object that specifies color style for the chart.
    /// </summary>
    DmlChartColorStyle getColorStyle() { return mColorStyle; }
    void setColorStyle(DmlChartColorStyle value) { mColorStyle = value; }

    /// <summary>
    /// Gets or sets <see cref="ComplexTypes.DmlChartStyle"/> object that specifies style for the chart.
    /// </summary>
    DmlChartStyle getDmlChartStyle() { return mDmlChartStyle; }
    void setDmlChartStyle(DmlChartStyle value) { mDmlChartStyle = value; }

    /// <summary>
    /// Flag indicates whether Word2010+ styles are used for this chart space.
    /// </summary>
    boolean getUseWord2010Style() { return getWord2010Style() > 0 || isChartEx() ;}

    /// <summary>
    /// Indicates that current document is created in MS Word 2007 or in lower version.
    /// </summary>
    boolean isWord2007OrLower()
    {
        Document doc = (Document) getDml().getDocument();
        return (doc != null) && doc.getBuiltInDocumentProperties().isWord2007OrLower();
    }



    public /*override*/ String getAlternativeText()
    {
        return getDml().isTopLevel() ? super.getAlternativeText() : "";
    }
    public /*override*/ void setAlternativeText(String value)
    {
        if (getDml().isTopLevel())
            super.setAlternativeText(value);
    }

    public /*override*/ String getTitle()
    {
        return getDml().isTopLevel() ? super.getTitle() : "";
    }
    public /*override*/ void setTitle(String value)
    {
        if (getDml().isTopLevel())
            super.setTitle(value);
    }

    public /*override*/ String getName()
    {
        return getDml().isTopLevel() ? super.getName() : "";
    }
    public /*override*/ void setName(String value)
    {
        if (getDml().isTopLevel())
            super.setName(value);
    }

    /*override*/ boolean getHidden() { return getDml().isTopLevel() && super.getHidden(); }
    /*override*/ void setHidden(boolean value)
    {
        // Probably chart always placed into the "graphic frame" i.e. needs a container in which to be displayed.
        if (getDml().isTopLevel())
            super.setHidden(value);
    }

    /*override*/ boolean getAspectRatioLocked()
    {
        return getDml().isTopLevel() && super.getAspectRatioLocked();
    }
    /*override*/ void setAspectRatioLocked(boolean value)
    {
        if (getDml().isTopLevel())
            super.setAspectRatioLocked(value);
    }

    /// <summary>
    /// Indicates that this chart is an extension of the ISO/IEC 29000 format that is defined by
    /// the http://schemas.microsoft.com/office/drawing/2015/9/8/chartex schema of [MS-ODRAWXML].
    /// The extension is introduced in MS Word 2016.
    /// </summary>
    boolean isChartEx() { return mIsChartEx; }

    /// <summary>
    /// Stores data of the 'data' element of the 2.24.3.10 CT_ChartData complex type [MS-ODRAWXML].
    /// </summary>
    DmlChartDataCollection getData()
    {
        if (mData == null)
            mData = new DmlChartDataCollection();
        return mData;
    }

    /// <summary>
    /// Stores data of the 'fmtOvrs' element of the 2.24.3.27 CT_FormatOverrides complex type [MS-ODRAWXML].
    /// Each item is of the <see cref="DmlChartFormatOverride"/> data type.
    /// </summary>
    ArrayList<DmlChartFormatOverride> getFormatOverrides()
    {
        if (mFormatOverrides == null)
            mFormatOverrides = new ArrayList<DmlChartFormatOverride>();
        return mFormatOverrides;
    }

    private StringToObjDictionary<DmlExtension> mExtLst;
    private byte[] mChartExternalData;
    private boolean mAutoUpdate = true;
    private DmlChartColorMapOverride mColorMapOverride;
    private DmlChartFormat mChartFormat;
    private boolean mDate1904;
    private String mLang;
    private DmlChartPivotSource mPivotSource;
    private DmlChartProtection mProtection;
    private boolean mRoundedCorners;
    private DmlChartSpPr mSpPr;
    private int mStyleIndex = 2;
    // Negative value means property is not set.
    private int mWord2010Style = Integer.MIN_VALUE;
    private DmlChartTxPr mTxPr;

    private /*final*/ boolean mIsChartEx;
    private String mLinkedData;
    private Theme mThemeOverride;
    private DmlChartColorStyle mColorStyle;
    private DmlChartStyle mDmlChartStyle;
    private DmlChartDataCollection mData;
    private ArrayList<DmlChartFormatOverride> mFormatOverrides;
}

