// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Allows to specify formatting of a single data point on the chart.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// On a series, the <see cref="ChartDataPoint"/> object is a member of the <see cref="ChartDataPointCollection"/>. 
/// The <see cref="ChartDataPointCollection"/> contains a <see cref="ChartDataPoint"/> object for each point. 
/// </remarks>
public class ChartDataPoint implements IChartDataPoint, IChartFormatSource, Cloneable
{
    // Represents 5.7.2.52 dPt (Data Point) element.

    ChartDataPoint(DmlChart chart)
    {
        mChart = chart;
        mPointPr = new DmlChartDataPointPr((chart != null) ? chart.getDocument() : null);
    }

    /// <summary>
    /// Clears format of this data point. The properties are set to the default values defined in the parent series.
    /// </summary>
    public void clearFormat()
    {
        int index = getIndex();

        mPointPr.clear();

        setIndex(index);

        if (mMarker != null)
        {
            mMarker.getMarkerPr().clear();
            mPointPr.setProperty(DmlChartDataPointAttr.MARKER, mMarker);
        }
    }

    /// <summary>
    /// Copies format from the specified source data point.
    /// </summary>
    /// <remarks>
    /// The format of this data point is cleared, and only the changed properties are copied from the source data point.
    /// </remarks>
    /// <dev>
    /// This method is intended to copy format from a data point of the same <see cref="ChartDataPointCollection"/>.
    /// </dev>
    void copyFormatFrom(ChartDataPoint dataPoint)
    {
        if (this == dataPoint)
            return;

        // Preserve the index.
        int index = getIndex();
        mPointPr = dataPoint.getPointPr().deepClone();
        setIndex(index);

        // If SpPr and Marker are present then they are contained in mPointPr and hence are cloned 
        // together with mPointPr, so we should reference the clones from mPointPr.
        mSpPr = (DmlChartSpPr)mPointPr.getDirectProperty(DmlChartDataPointAttr.SP_PR);
        mMarker = (ChartMarker)mPointPr.getDirectProperty(DmlChartDataPointAttr.MARKER);

        mFormat = null;
    }

    /// <summary>
    /// Merges changed properties from the specified source data point.
    /// </summary>
    void mergeFormatFrom(ChartDataPoint dataPoint)
    {
        getPointPr().copyDirectPropertiesFrom(dataPoint.getPointPr());

        // If SpPr and/or Marker are affected, need to get the new values.
        mSpPr = (DmlChartSpPr)mPointPr.getDirectProperty(DmlChartDataPointAttr.SP_PR);
        mMarker = (ChartMarker)mPointPr.getDirectProperty(DmlChartDataPointAttr.MARKER);
    }

    void setParent(ChartDataPoint parent)
    {
        mPointPr.setParent(parent.getPointPr());
    }

    ChartDataPoint deepClone()
    {
        ChartDataPoint lhs = (ChartDataPoint)memberwiseClone();
        if (mPointPr != null)
            lhs.mPointPr = mPointPr.deepClone();

        // If mSpPr and mMarker are present then they are contained in mPointPr and hence are cloned 
        // together with mPointPr, so we should reference the clones from lhs.mPointPr for lhs.
        if (mSpPr != null)
            lhs.mSpPr = (DmlChartSpPr)lhs.mPointPr.getProperty(DmlChartDataPointAttr.SP_PR);

        if (mMarker != null)
            lhs.mMarker = (ChartMarker)lhs.mPointPr.getProperty(DmlChartDataPointAttr.MARKER);

        lhs.mFormat = null;

        return lhs;
    }

    /// <summary>
    /// Sets the parent chart of this data point.
    /// </summary>
    void setChart(DmlChart chart)
    {
        mChart = chart;
    }

    /// <summary>
    /// Sets <see cref="DmlChartSpPr"/> for this data point.
    /// </summary>
    void setSpPr(DmlChartSpPr spPr)
    {
        mSpPr = spPr;
        mPointPr.setProperty(DmlChartDataPointAttr.SP_PR, mSpPr);
    }

    private void setMarker(ChartMarker marker)
    {
        mMarker = marker;
        mPointPr.setProperty(DmlChartDataPointAttr.MARKER, mMarker);
    }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // This un-links data point SpPr from SpPr of series default data point. Do it only on changing properties,
        // not on reading, for the behavior to not depend on a fact whether a customer has read ChartFormat properties
        // or hasn't.

        if (mPointPr.isPropertySpecified(DmlChartDataPointAttr.SP_PR))
            return;
        
        DmlChartSpPr parentSpPr = (DmlChartSpPr)mPointPr.getProperty(DmlChartDataPointAttr.SP_PR);
        DmlChartSpPr spPr = (parentSpPr != null) ? parentSpPr.deepClone() : new DmlChartSpPr();
        setSpPr(spPr);
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
        return ChartShapeType.DEFAULT;
    }
    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/setShapeType(/*ChartShapeType*/int value)
    {
        if (value != ChartShapeType.DEFAULT)
            throw new IllegalStateException("Cannot change the shape type of this chart element.");
    }

    @ReservedForInternalUse @Deprecated
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return mChart.getDocument().getThemeInternal(); }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return mPointPr.isPropertySpecified(DmlChartDataPointAttr.SP_PR) && !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Index of the data point this object applies formatting to.
    /// </summary>
    public int getIndex() { return (/*int*/Integer)mPointPr.getProperty(DmlChartDataPointAttr.INDEX); }
    void setIndex(int value) { mPointPr.setProperty(DmlChartDataPointAttr.INDEX, value); }

    /// <summary>
    /// Specifies the amount the data point shall be moved from the center of the pie.
    /// Can be negative, negative means that property is not set and no explosion should be applied.
    /// Applies only to Pie charts.
    /// </summary>
    public int getExplosion() { return (/*int*/Integer)mPointPr.getProperty(DmlChartDataPointAttr.EXPLOSION); }
    public void setExplosion(int value)
    {
        if (mChart.isPieChart())
            mPointPr.setProperty(DmlChartDataPointAttr.EXPLOSION, value);
        else
            mChart.warn(WarningType.MINOR_FORMATTING_LOSS, "Explosion is not supported by this type of chart, value will not be set.");
    }

    /// <summary>
    /// Specifies whether the parent element shall inverts its colors if the value is negative.
    /// </summary>
    public boolean getInvertIfNegative() { return (/*boolean*/Boolean)mPointPr.getProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE); }
    public void setInvertIfNegative(boolean value) { mPointPr.setProperty(DmlChartDataPointAttr.INVERT_IF_NEGATIVE, value); }

    /// <summary>
    /// Specifies whether the bubbles in Bubble chart should have a 3-D effect applied to them.
    /// </summary>
    public boolean getBubble3D() { return (/*boolean*/Boolean)mPointPr.getProperty(DmlChartDataPointAttr.BUBBLE_3_D); }
    public void setBubble3D(boolean value)
    {
        if (mChart.getChartType() == DmlChartType.BUBBLE_CHART)
            mPointPr.setProperty(DmlChartDataPointAttr.BUBBLE_3_D, value);
        else
            mChart.warn(WarningType.MINOR_FORMATTING_LOSS, "Bubble3D is not supported by this type of chart, value will not be set.");
    }

    /// <summary>
    /// Provides access to fill and line formatting of this data point.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    /// <summary>
    /// Specifies a data marker. Marker is automatically created when requested.
    /// </summary>
    boolean hasMarker() { return (mPointPr.isPropertySpecified(DmlChartDataPointAttr.MARKER)); }

    /// <summary>
    /// Specifies the formatting for the series.
    /// </summary>
    DmlChartSpPr getSpPr()
    {
        // This kind of lazy initialization is used for java.
        // For some reason in multi-threads environment SpPr and Marker from defaults are changed and tests fails.
        // So removed SpPr and Marker from default and init them here on demand.
        if (mSpPr == null)
            mSpPr = (DmlChartSpPr)mPointPr.getProperty(DmlChartDataPointAttr.SP_PR);

        if (mSpPr == null)
            setSpPr(new DmlChartSpPr());

        return mSpPr;
    }

    /// <summary>
    /// Specifies chart data marker.
    /// </summary>
    public ChartMarker getMarker()
    {
        if (mMarker == null)
            mMarker = (ChartMarker)mPointPr.getDirectProperty(DmlChartDataPointAttr.MARKER);

        if (mMarker == null)
            setMarker(new ChartMarker(mChart));

        return mMarker;
    }

    /// <summary>
    /// Specifies the picture to be used on the series.
    /// </summary>
    DmlChartPictureOptions getPictureOptions() { return (DmlChartPictureOptions)mPointPr.getProperty(DmlChartDataPointAttr.PICTURE_OPTIONS); }

    DmlChartDataPointPr getPointPr() { return mPointPr; }

    DmlChart getChart() { return mChart; }

    /// <summary>
    /// Returns a flag indicating whether any properties of this data point have different values than the
    /// corresponding properties of the parent collection.
    /// </summary>
    boolean hasNonDefaultFormatting() { return mPointPr.hasNonDefaultFormatting(); }

    private DmlChartDataPointPr mPointPr;
    private DmlChart mChart;

    private DmlChartSpPr mSpPr;
    private ChartMarker mMarker;
    private ChartFormat mFormat;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

