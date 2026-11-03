// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Allows to specify properties of a chart data table.
/// </summary>
public class ChartDataTable implements IChartItemTextProperties, IChartFormatSource, Cloneable
{
    /// <summary>
    /// Non-public ctor to prevent creating instances of this class in customer code.
    /// </summary>
    ChartDataTable(DmlChartPlotArea plotArea)
    {
        mPlotArea = plotArea;
    }

    /// <summary>
    /// Creates a data table and sets the default font and border formatting.
    /// </summary>
    static ChartDataTable createDataTableWithDefaultFormat(DmlChartPlotArea plotArea)
    {
        ChartDataTable dataTable = new ChartDataTable(plotArea);

        // Define border line defaults.

        DmlOutline outline = dataTable.getSpPr().getOutline();
        outline.setWeight(0.75);

        DmlSchemeColor borderColor = new DmlSchemeColor(ThemeColor.TEXT_1);

        DmlLuminanceModulation modulation = new DmlLuminanceModulation();
        modulation.setValue(0.15);
        msArrayList.add(borderColor.getColorModifiers(), modulation);

        DmlLuminanceOffset offset = new DmlLuminanceOffset();
        offset.setValue(0.85);
        msArrayList.add(borderColor.getColorModifiers(), offset);

        outline.setFill(new DmlSolidFill(borderColor));

        // Define font defaults.

        DmlParagraph paragraph = dataTable.getTxPr().addParagraph();
        DmlParagraphProperties properties = paragraph.getProperties();
        properties.hasDefaultRunProperties(true);
        properties.getDefaultRunProperties().setFontSize(new DmlTextPoints(900));

        DmlSchemeColor textColor = new DmlSchemeColor(ThemeColor.TEXT_1);

        modulation = new DmlLuminanceModulation();
        modulation.setValue(0.65);
        msArrayList.add(textColor.getColorModifiers(), modulation);

        offset = new DmlLuminanceOffset();
        offset.setValue(0.35);
        msArrayList.add(textColor.getColorModifiers(), offset);

        properties.getDefaultRunProperties().setFill(new DmlSolidFill(textColor));

        return dataTable;
    }

    /// <summary>
    /// Clones this instance of the data table.
    /// </summary>
    ChartDataTable deepClone()
    {
        ChartDataTable lhs = (ChartDataTable)memberwiseClone();
        if (mSpPr != null)
            lhs.mSpPr = mSpPr.deepClone();

        if (mTxPr != null)
            lhs.mTxPr = mTxPr.deepClone();

        lhs.mFontRunPropertiesSource = null;
        lhs.mFont = null;
        lhs.mFormat = null;

        return lhs;
    }

    /// <summary>
    /// Sets the parent plot area of this data table.
    /// </summary>
    void setPlotArea(DmlChartPlotArea plotArea)
    {
        mPlotArea = plotArea;
    }

    /// <summary>
    /// Gets a flag indicating whether the parent chart supports a data table.
    /// </summary>
    boolean isDataTableSupported()
    {
        for (DmlChart chart : mPlotArea.getCharts())
        {
            if (chart.isScatter() ||
                chart.isPieChart() ||
                chart.isSurfaceChart() ||
                chart.isSurface3DChart() ||
                chart.isRadarChart() ||
                chart.isChartEx())
            {
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Sets a flag indicating whether the data table will be displayed on the chart without checking for data table
    /// support and applying the chart style.
    /// </summary>
    void setShowInternal(boolean value)
    {
        mShow = value;
    }

    /// <summary>
    /// Gets or sets a flag indicating whether the data table will be shown for the chart.
    /// Default value is <c>false</c>.
    /// </summary>
    /// <remarks>
    /// The following chart types do not support data tables: Scatter, Pie, Doughnut, Surface, Radar, Treemap,
    /// Sunburst, Histogram, Pareto, Box and Whisker, Waterfall, Funnel, Combo charts that include series of
    /// these types. Showing a data table for the chart types throws a <see cref="InvalidOperationException"/>
    /// exception.
    /// </remarks>
    public boolean getShow() { return mShow; }
    public void setShow(boolean value)
    {
        if (mShow == value)
            return;

        if (value && !isDataTableSupported())
            throw new IllegalStateException("This chart type does not support a data table.");

        mShow = value;
        if (mShow)
            mPlotArea.getParentChartFormat().getDmlChartSpace().applyChartStyle(this);
    }

    /// <summary>
    /// Gets or sets a flag indicating whether legend keys are displayed in the data table.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean hasLegendKeys() { return mHasLegendKeys; }
    public void hasLegendKeys(boolean value) { mHasLegendKeys = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether a horizontal border of the data table is displayed.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean hasHorizontalBorder() { return mHasHorizontalBorder; }
    public void hasHorizontalBorder(boolean value) { mHasHorizontalBorder = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether a vertical border of the data table is displayed.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean hasVerticalBorder() { return mHasVerticalBorder; }
    public void hasVerticalBorder(boolean value) { mHasVerticalBorder = value; }

    /// <summary>
    /// Gets or sets a flag indicating whether an outline border, that is, a border around series and category names,
    /// is displayed.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean hasOutlineBorder() { return mHasOutlineBorder; }
    public void hasOutlineBorder(boolean value) { mHasOutlineBorder = value; }

    /// <summary>
    /// Provides access to font formatting of the data table.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(getFontRunPropertiesSource(), mPlotArea.getParentChartFormat().getDocument());

        return mFont;
    }

    /// <summary>
    /// Provides access to fill of text background and border formatting of the data table.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Gets the shape properties of the data table.
    /// </summary>
    DmlChartSpPr getSpPr()
    {
        if (mSpPr == null)
            mSpPr = new DmlChartSpPr();

        return mSpPr;
    }

    /// <summary>
    /// Gets the text properties of the data table.
    /// </summary>
    DmlChartTxPr getTxPr()
    {
        if (mTxPr == null)
            mTxPr = new DmlChartTxPr();

        return mTxPr;
    }

    /// <summary>
    /// Gets a <see cref="ChartItemDmlRunPropertiesSource"/> instance that is the source of the data table
    /// font properties.
    /// </summary>
    private ChartItemDmlRunPropertiesSource getFontRunPropertiesSource()
    {
        if (mFontRunPropertiesSource == null)
        {
            mFontRunPropertiesSource =
                new ChartItemDmlRunPropertiesSource(this, mPlotArea.getParentChartFormat().getDmlChartSpace());
        }

        return mFontRunPropertiesSource;
    }

    //>>>>>>>> #region  IChartItemTextProperties members

    @ReservedForInternalUse @Deprecated
    public String /*IChartItemTextProperties.*/generateItemText()
    {
        return null; // Not used for data table.
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IChartItemTextProperties.*/fetchSpecialDefaultRunPropertyValue(int key)
    {
        return null; // Not used for data table.
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
    public DmlChartSpPr /*IChartItemTextProperties.*/getItemSpPr() { return getSpPr(); }

    @ReservedForInternalUse @Deprecated
    public DmlChartTxPr /*IChartItemTextProperties.*/getCollectionTxPr() { return null; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // Do nothing for a data table.
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
        // Not supported by axis.
        return ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        // Not supported by axis.
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return mPlotArea.getParentChartFormat().getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    private boolean mShow;
    private boolean mHasLegendKeys = true;
    private boolean mHasHorizontalBorder = true;
    private boolean mHasVerticalBorder = true;
    private boolean mHasOutlineBorder = true;
    private DmlChartSpPr mSpPr;
    private DmlChartTxPr mTxPr;
    private Font mFont;
    private ChartItemDmlRunPropertiesSource mFontRunPropertiesSource;
    private ChartFormat mFormat;
    private DmlChartPlotArea mPlotArea;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

