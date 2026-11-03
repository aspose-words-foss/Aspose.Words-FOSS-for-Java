// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/13/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides access to the chart shape properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <dev>
/// There are text properties at the chart (chart space) level, but when a chart is created in MS Word, child elements
/// of a chart override text properties. Therefore implementing a Chart.Font property does not provide much benefit,
/// actually it would affect only data labels shown in AW, whose text properties are then empty. It looks like Word
/// VBA has no the Chart.Font property. Moreover, MS Word ignores any text properties defined in a chart space in
/// Word 2016 charts. So it seems there is no need to implement this property in AW too.
/// </dev>
public class Chart implements IChartFormatSource
{
    Chart(DmlChartSpace dmlChartSpace)
    {
        mChartSpace = dmlChartSpace;
    }

    /// <summary>
    /// Provides access to series collection.
    /// </summary>
    public ChartSeriesCollection getSeries()
    {
        if (mSeriesCollection == null)
            mSeriesCollection = new ChartSeriesCollection(getPlotArea());

        return mSeriesCollection;
    }

    /// <summary>
    /// Provides access to a series group collection of this chart.
    /// </summary>
    public ChartSeriesGroupCollection getSeriesGroups()
    {
        if (mSeriesGroups == null)
            mSeriesGroups = new ChartSeriesGroupCollection(mChartSpace);

        return mSeriesGroups;
    }

    /// <summary>
    /// Provides access to the chart title properties.
    /// </summary>
    public ChartTitle getTitle()
    {
        if (mTitle == null)
            mTitle = new ChartTitle(getChartFormat());

        return mTitle;
    }

    /// <summary>
    /// Provides access to the chart legend properties.
    /// </summary>
    public ChartLegend getLegend()
    {
        if (getChartFormat().getLegend() == null)
            getChartFormat().setLegend(new ChartLegend(getChartFormat()));

        return getChartFormat().getLegend();
    }

    /// <summary>
    /// Provides access to properties of a data table of this chart.
    /// The data table can be shown using the <see cref="ChartDataTable.Show"/> property.
    /// </summary>
    public ChartDataTable getDataTable()
    {
        if (getPlotArea().getDataTable() == null)
            getPlotArea().setDataTable(ChartDataTable.createDataTableWithDefaultFormat(getPlotArea()));

        return getPlotArea().getDataTable();
    }

    /// <summary>
    /// Provides access to properties of the primary X axis of the chart.
    /// </summary>
    public ChartAxis getAxisX() { return getPlotArea().getPrimaryXAxis(); }

    /// <summary>
    /// Provides access to properties of the primary Y axis of the chart.
    /// </summary>
    public ChartAxis getAxisY() { return getPlotArea().getPrimaryYAxis(); }

    /// <summary>
    /// Provides access to properties of the Z axis of the chart.
    /// </summary>
    public ChartAxis getAxisZ() { return getPlotArea().getZAxis(); }

    /// <summary>
    /// Gets a collection of all axes of this chart.
    /// </summary>
    public ChartAxisCollection getAxes()
    {
        if (mAxes == null)
            mAxes = new ChartAxisCollection(mChartSpace);

        return mAxes;
    }

    /// <summary>
    /// Gets the path and name of an xls/xlsx file this chart is linked to.
    /// </summary>
    public String getSourceFullName() { return mChartSpace.getLinkedData(); }
    public void setSourceFullName(String value) { mChartSpace.setLinkedData(value); }

    /// <summary>
    /// Provides access to fill and line formatting of the chart.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Gets or sets the style of the chart.
    /// </summary>
    public /*ChartStyle*/int getStyle()
    {
        return (getChartSpace().getDmlChartStyle() != null)
            ? ChartStyleResolver.getChartStyle(getChartSpace().getDmlChartStyle().getIntId())
            : ChartStyle.NORMAL;
    }
    public void setStyle(/*ChartStyle*/int value) throws Exception { getChartSpace().setChartStyle(value); }

    /// <summary>
    /// Returns the document to which this chart belongs.
    /// </summary>
    DocumentBase getDocument() { return mChartSpace.getDml().getDocument(); }

    /// <summary>
    /// Returns a chart space instance that contains all information about the chart.
    /// </summary>
    DmlChartSpace getChartSpace() { return mChartSpace; }

    /// <summary>
    /// Returns a chart format instance that contains chart properties.
    /// </summary>
    private DmlChartFormat getChartFormat() { return mChartSpace.getChartFormat(); }

    /// <summary>
    /// Returns a plot area of this chart.
    /// </summary>
    private DmlChartPlotArea getPlotArea() { return getChartFormat().getPlotArea(); }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // Do nothing for a chart.
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFillSupported() { return true; }

    @ReservedForInternalUse @Deprecated
    public DmlFill /*IChartFormatSource.*/getFill() { return getChartSpace().getSpPr().getFill(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setFill(DmlFill value) { getChartSpace().getSpPr().setFill(value); }

    @ReservedForInternalUse @Deprecated
    public DmlOutline /*IChartFormatSource.*/getOutline() { return getChartSpace().getSpPr().getOutline(); }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setOutline(DmlOutline value) { getChartSpace().getSpPr().setOutline(value); }

    @ReservedForInternalUse @Deprecated
    public /*ChartShapeType*/int /*IChartFormatSource.*/getShapeType()
    {
        // Not supported by a chart.
        return ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        // Not supported by a chart.
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return !getChartSpace().getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    private ChartSeriesCollection mSeriesCollection;
    private /*final*/ DmlChartSpace mChartSpace;
    private ChartAxisCollection mAxes;
    private ChartTitle mTitle;
    private ChartFormat mFormat;
    private ChartSeriesGroupCollection mSeriesGroups;
}

