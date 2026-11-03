// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ImageSizeCore;
import com.aspose.ImageUtil;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;


/// <summary>
/// Represents a chart data marker.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <dev>
/// Represents 5.7.2.106 marker (Marker) element.
/// </dev>
public class ChartMarker implements IChartFormatSource, Cloneable
{
    ChartMarker(DmlChart chart)
    {
    	this(chart.getDocument().getThemeInternal());
	
        mChart = chart;
    }

    ChartMarker(IThemeProvider themeProvider)
    {
        mThemeProvider = themeProvider;
    }

    ChartMarker deepClone()
    {
        ChartMarker lhs = (ChartMarker)memberwiseClone();
        lhs.mMarkerPr = mMarkerPr.deepClone();
        lhs.mFormat = null;
        return lhs;
    }

    /// <summary>
    /// Sets the marker symbol without applying the chart style.
    /// </summary>
    void setSymbolInternal(/*MarkerSymbol*/int value)
    {
        mMarkerPr.setProperty(DmlChartMarkerAttr.SYMBOL, value);
    }

    //>>>>>>>> #region  IChartFormatSource implementation

    @ReservedForInternalUse @Deprecated
    public void /*IChartFormatSource.*/materializeSpPr()
    {
        // This un-links marker SpPr from SpPr of series. Do it only on changing properties, not on reading, for
        // the behavior to not depend on a fact whether a customer has read ChartFormat properties or hasn't.

        if (mMarkerPr.isPropertySpecified(DmlChartMarkerAttr.SP_PR))
            return;

        DmlChartSpPr parentSpPr = (DmlChartSpPr)mMarkerPr.getProperty(DmlChartMarkerAttr.SP_PR);
        DmlChartSpPr spPr = (parentSpPr != null) ? parentSpPr.deepClone() : new DmlChartSpPr();
        mMarkerPr.setProperty(DmlChartMarkerAttr.SP_PR, spPr);
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
    public IThemeProvider /*IChartFormatSource.*/getThemeProvider() { return mThemeProvider; }

    @ReservedForInternalUse @Deprecated
    public boolean /*IChartFormatSource.*/isFormatDefined() { return mMarkerPr.isPropertySpecified(DmlChartMarkerAttr.SP_PR) && !getSpPr().isEmpty(); }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets chart marker symbol.
    /// </summary>
    public /*MarkerSymbol*/int getSymbol() { return (/*MarkerSymbol*/Integer)mMarkerPr.getProperty(DmlChartMarkerAttr.SYMBOL); }
    public void setSymbol(/*MarkerSymbol*/int value)
    {
        boolean needApplyStyle = (getSymbol() == MarkerSymbol.NONE) && (value != MarkerSymbol.NONE);
        setSymbolInternal(value);

        if (needApplyStyle && (mChart != null))
        {
            int seriesIndex = 0;
            for (int i = 0; i < mChart.getSeries().getCount(); i++)
            {
                if (mChart.getSeries().get(i).getMarker() == this)
                {
                    seriesIndex = i;
                    break;
                }
            }

            mChart.getChartSpace().applyChartStyle(this, seriesIndex);

            if (value != MarkerSymbol.DEFAULT)
                setSymbolInternal(value); // The symbol can be changed during applying style.
        }
    }

    /// <summary>
    /// Gets or sets chart marker size.
    /// Default value is 7.
    /// </summary>
    public int getSize() { return (/*int*/Integer)mMarkerPr.getProperty(DmlChartMarkerAttr.SIZE); }
    public void setSize(int value)
    {
        // Value cannot be less than 2 and more than 72
        if (value >= 2 && value <= 72)
            mMarkerPr.setProperty(DmlChartMarkerAttr.SIZE, value);
        else
            throw new IllegalArgumentException("Marker size has to be in range from 2 to 72.");
    }

    /// <summary>
    /// Provides access to fill and line formatting of this marker.
    /// </summary>
    public ChartFormat getFormat()
    {
        if (mFormat == null)
            mFormat = new ChartFormat(this);

        return mFormat;
    }

    DmlChartSpPr getSpPr() { return (DmlChartSpPr)mMarkerPr.getProperty(DmlChartMarkerAttr.SP_PR); }

    DmlChartMarkerPr getMarkerPr() { return mMarkerPr; }

    boolean isDefaultSize()
    {
        // False means that size is not set and default size must be used.
        return (!mMarkerPr.isPropertySpecified(DmlChartMarkerAttr.SIZE));
    }

    /// <summary>
    /// Gets the bounds of marker.
    /// </summary>
    RectangleF getMarkerBounds(/*PointF*/long center, float specialSize) throws Exception
    {
        RectangleF boundingBox;

        if (getSymbol() == MarkerSymbol.PICTURE && isRendered())
        {
            DmlBlipFill blipFill = ms.as(getSpPr().getFill(), DmlBlipFill.class);
            ImageSizeCore imageSize = ImageUtil.getImageSize(blipFill.getImageBytes());
            boundingBox = new RectangleF((msPointF.getX(center) - imageSize.getWidthEmus() / 2),
            (msPointF.getY(center) - imageSize.getHeightEmus() / 2), imageSize.getWidthEmus(), imageSize.getHeightEmus());
        }
        else
        {
            float size = ConvertUtilCore.pointToEmu(MathUtil.isZero(specialSize) ? getSize() : specialSize);
            boundingBox = new RectangleF((msPointF.getX(center) - size / 2f), (msPointF.getY(center) - size / 2f), size, size);
        }

        return boundingBox;
    }

    /// <summary>
    /// Gets the bounds of marker.
    /// </summary>
    RectangleF getMarkerBounds(/*PointF*/long center) throws Exception
    {
        return getMarkerBounds(center, 0f);
    }

    /// <summary>
    /// Indicates whether to render the marker.
    /// </summary>
    boolean isRendered() throws Exception
    {
        if (getSymbol() == MarkerSymbol.PICTURE)
        {
            DmlBlipFill blipFill = ms.as(getSpPr().getFill(), DmlBlipFill.class);
            // WORDSNET-20219 If "picture" is specified as a marker symbol, but there is no image file, then
            // MS Word does not render markers.
            return (blipFill != null) && (blipFill.getImageBytes() != null) && (blipFill.getImageBytes().length != 0);
        }

        // If the marker symbol is "None" do not render it.
        return (getSymbol() != MarkerSymbol.NONE);
    }

    /// <summary>
    /// Returns a flag indicating whether any properties of this chart marker have different values than the
    /// corresponding properties of the parent data point collection.
    /// </summary>
    boolean hasNonDefaultFormatting() { return mMarkerPr.hasNonDefaultFormatting(); }

    private DmlChartMarkerPr mMarkerPr = new DmlChartMarkerPr();
    private ChartFormat mFormat;
    private /*final*/ IThemeProvider mThemeProvider;
    private /*final*/ DmlChart mChart;

    /// <summary>
    ///  According to the specification, default size is 5, but MS Word uses 7 as default, so do the same.
    /// </summary>
    static final int DEFAULT_MARKER_SIZE = 7;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

