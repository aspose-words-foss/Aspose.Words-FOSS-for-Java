// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2025 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Applies the assigned chart style to the chart.
/// </summary>
class ChartStyleApplier
{
    /// <summary>
    /// Ctor with a chart space provided.
    /// </summary>
    ChartStyleApplier(DmlChartSpace chartSpace)
    {
        mChartSpace = chartSpace;
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the entire chart.
    /// </summary>
    void applyStyle()
    {
        for (DmlChart chart : mChartSpace.getCharts())
            defineStyleDependentProperties(chart);

        applyChartStyleToChartElements();
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified series.
    /// </summary>
    void applyStyle(ChartSeries series)
    {
        applyStyleToSeries(series, series.getIndex());
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified data labels.
    /// </summary>
    void applyStyle(ChartDataLabelCollection dataLabels)
    {
        fixDataLabelPosition(dataLabels);

        DmlChartStyle style = mChartSpace.getDmlChartStyle();
        DmlChart chart = dataLabels.getChart();
        DmlChartStyleEntry labelsStyleEntry = style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL);

        if (chart.isPieChart() && (chart.getChartType() != DmlChartType.DOUGHNUT_CHART))
        {
            for (ChartDataLabel dataLabel : dataLabels)
                applyStyleEntryToDataLabel(dataLabel, labelsStyleEntry);
        }
        else
        {
            applyStyleEntry(dataLabels.getSpPr(), dataLabels.getTxPr(), labelsStyleEntry);
        }

        //DmlChartStyleItem.DataLabelCallout is not currently supported.

        DmlChartSpPr leaderLinesSpPr =
            (DmlChartSpPr)dataLabels.getLabelPr().getProperty(DmlChartDataLabelAttrs.LEADER_LINES);
        if (leaderLinesSpPr != null)
            applyStyleEntry(leaderLinesSpPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.LEADER_LINE));
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified axis.
    /// </summary>
    void applyStyle(ChartAxis axis)
    {
        if (axis == null)
            return;

        DmlChartStyle style = mChartSpace.getDmlChartStyle();

        applyStyleToAxisTitle(axis);

        /*DmlChartStyleItem*/int styleItem = getAxisStyleItem(axis);
        applyStyleEntry(axis.getSpPr(), axis.getTxPr(), style.getByDmlChartStyleItem(styleItem));

        if (axis.getMajorGridlines() != null)
            applyStyleEntry(axis.getMajorGridlines().getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.GRIDLINE_MAJOR));

        if (axis.getMinorGridlines() != null)
            applyStyleEntry(axis.getMinorGridlines().getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.GRIDLINE_MINOR));
    }

    /// <summary>
    /// Applies the specified chart style item to the text properties.
    /// </summary>
    void applyStyle(DmlChartTxPr txPr, /*DmlChartStyleItem*/int styleItem)
    {
        applyStyleEntryToTxPr(txPr, mChartSpace.getDmlChartStyle().getByDmlChartStyleItem(styleItem), 0);
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified legend.
    /// </summary>
    void applyStyle(ChartLegend legend)
    {
        if (legend == null)
            return;

        DmlChartStyleEntry styleEntry = mChartSpace.getDmlChartStyle().getByDmlChartStyleItem(DmlChartStyleItem.LEGEND);
        applyStyleEntry(legend.getSpPr(), legend.getTxPr(), styleEntry);

        for (ChartLegendEntry entry : legend.getLegendEntries().getMaterializedLegendEntries())
            applyStyleEntry(null, entry.getTxPr(), styleEntry, entry.getIndex());
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified data table.
    /// </summary>
    void applyStyle(ChartDataTable dataTable)
    {
        if (dataTable != null)
            applyStyleEntry(dataTable.getSpPr(), dataTable.getTxPr(), mChartSpace.getDmlChartStyle().getByDmlChartStyleItem(DmlChartStyleItem.DATA_TABLE));
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified marker.
    /// </summary>
    void applyStyle(ChartMarker marker, int chartElementIndex)
    {
        if (marker.getSymbol() == MarkerSymbol.NONE)
            return;

        DmlChartStyle style = mChartSpace.getDmlChartStyle();
        DmlChartMarkerPr markerPr = marker.getMarkerPr();
        DmlChartSpPr spPr = (DmlChartSpPr)markerPr.getDirectProperty(DmlChartMarkerAttr.SP_PR);
        if (spPr == null)
        {
            // Set cloned inherited value.
            spPr = (DmlChartSpPr)markerPr.getProperty(DmlChartMarkerAttr.SP_PR);
            spPr = (spPr != null) ? spPr.deepClone() : new DmlChartSpPr();
            markerPr.setProperty(DmlChartMarkerAttr.SP_PR, spPr);
        }

        applyStyleEntry(spPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT_MARKER), chartElementIndex);

        if (style.getDataPointMarkerLayout().getSize() != 0)
            marker.setSize(style.getDataPointMarkerLayout().getSize());
        else
            marker.getMarkerPr().removeProperty(DmlChartMarkerAttr.SIZE);

        if (!msString.equals(style.getDataPointMarkerLayout().getSymbol(), ""))
        {
            /*MarkerSymbol*/int symbol = (style.getDataPointMarkerLayout().getMarkerSymbol() != MarkerSymbol.DEFAULT)
                ? style.getDataPointMarkerLayout().getMarkerSymbol()
                : G_MARKER_AUTO_SYMBOLS[chartElementIndex % G_MARKER_AUTO_SYMBOLS.length];

            marker.setSymbolInternal(symbol);
        }
        else
        {
            marker.getMarkerPr().removeProperty(DmlChartMarkerAttr.SYMBOL);
        }
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the chart title.
    /// </summary>
    void applyStyleToChartTitle()
    {
        DmlChartTitle title = getChartFormat().getDCTitle();
        if (title != null)
            applyStyleEntry(title.getSpPr(), title.getTxPr(), mChartSpace.getDmlChartStyle().getByDmlChartStyleItem(DmlChartStyleItem.TITLE));
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the title of the specified axis.
    /// </summary>
    void applyStyleToAxisTitle(ChartAxis axis)
    {
        DmlChartTitle title = axis.getTitleInternal();
        if (title != null)
            applyStyleEntry(title.getSpPr(), title.getTxPr(), mChartSpace.getDmlChartStyle().getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE));
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to all elements of the chart.
    /// </summary>
    private void applyChartStyleToChartElements()
    {
        DmlChartStyle style = mChartSpace.getDmlChartStyle();
        DmlChartPlotArea plotArea = getChartFormat().getPlotArea();

        applyStyleEntry(mChartSpace.getSpPr(), mChartSpace.getTxPr(), style.getByDmlChartStyleItem(DmlChartStyleItem.CHART_AREA));

        /*DmlChartStyleItem*/int plotAreaItem = ((plotArea.getFirstChart() != null) && plotArea.getFirstChart().getIs3D())
            ? DmlChartStyleItem.PLOT_AREA_3_D
            : DmlChartStyleItem.PLOT_AREA;
        applyStyleEntry(plotArea.getSpPr(), null, style.getByDmlChartStyleItem(plotAreaItem));

        applyStyleToChartTitle();
        applyStyle(plotArea.getDataTable());

        DmlChartSurface floor = getChartFormat().getFloor();
        if (floor != null)
            applyStyleEntry(floor.getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.FLOOR));

        DmlChartSurface backWall = getChartFormat().getBackWall();
        if (backWall != null)
            applyStyleEntry(backWall.getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.WALL));

        DmlChartSurface sideWall = getChartFormat().getSideWall();
        if (sideWall != null)
            applyStyleEntry(sideWall.getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.WALL));

        applyStyle(getChartFormat().getLegend());

        for (ChartAxis axis : mChartSpace.getChartFormat().getPlotArea().getAxes())
            applyStyle(axis);

        for (DmlChart chart : mChartSpace.getCharts())
        {
            DmlChartPr chartPr = chart.getChartPr();
            DmlChartUpDownBars upDownBars = (DmlChartUpDownBars)chartPr.getDirectProperty(DmlChartAttrs.UP_DOWN_BARS);
            if (upDownBars != null)
            {
                applyStyleEntry(upDownBars.getDownBars(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.DOWN_BAR));
                applyStyleEntry(upDownBars.getUpBars(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.UP_BAR));
            }

            DmlChartSpPr dropLineSpPr =
                getChartSpPr(chartPr, DmlChartAttrs.IS_DROP_LINES_VISIBLE, DmlChartAttrs.DROP_LINES);
            if (dropLineSpPr != null)
                applyStyleEntry(dropLineSpPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.DROP_LINE));

            DmlChartSpPr seriesLinesSpPr =
                getChartSpPr(chartPr, DmlChartAttrs.SHOW_SER_LINE, DmlChartAttrs.SER_LINES);
            if (seriesLinesSpPr != null)
                applyStyleEntry(seriesLinesSpPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.SERIES_LINE));

            DmlChartSpPr hiLowLinesSpPr =
                getChartSpPr(chartPr, DmlChartAttrs.IS_HI_LOW_LINES_VISIBLE, DmlChartAttrs.HI_LOW_LINES);
            if (hiLowLinesSpPr != null)
                applyStyleEntry(hiLowLinesSpPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.HIGH_LOW_LINE));

            for (int i = 0; i < chart.getSeries().getCount(); i++)
                applyStyleToSeries(chart.getSeries().get(i), i);

            DmlSurfaceChart surfaceChart = ms.as(chart, DmlSurfaceChart.class);
            if (surfaceChart != null)
            {
                for (int i = 0; i < surfaceChart.getBandFmts().getCount(); i++)
                {
                    DmlChartSpPr bandSpPr = surfaceChart.getBandFmts().getFmt(i);
                    applyStyleEntry(bandSpPr, null, style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT), i);
                }
            }
        }
    }

    /// <summary>
    /// Applies the chart style assigned to the chart space to the specified series using the specified index to get
    /// style element color.
    /// </summary>
    private void applyStyleToSeries(ChartSeries series, int chartElementIndex)
    {
        markMarkersForShowingIfRequired(series);

        DmlChartStyle style = mChartSpace.getDmlChartStyle();
        DmlChart chart = series.getChart();

        if (series.hasDataLabels())
        {
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            applyStyle(dataLabels);
        }

        if (series.getXErrorBars() != null)
            applyStyleEntry(series.getXErrorBars().getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.ERROR_BAR));

        if (series.getYErrorBars() != null)
            applyStyleEntry(series.getYErrorBars().getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.ERROR_BAR));

        /*DmlChartStyleItem*/int dataPointItem = getDataPointStyleItem(chart);
        if (chart.isPieChart())
        {
            for (int j = 0; j < series.getDataPoints().getCount(); j++)
            {
                ChartDataPoint dataPoint = series.getDataPoints().get(j);
                ((IChartFormatSource)dataPoint).materializeSpPr();
                applyStyleEntry(dataPoint.getSpPr(), null, style.getByDmlChartStyleItem(dataPointItem), j);
            }
        }
        else
        {
            DmlChartSpPr spPr = series.getDefaultDataPoint().getSpPr();
            boolean noLine =
                needPreserveDataPointNoLine(chart.getChartType()) &&
                (spPr != null) &&
                ((spPr.getOutline().getFill() == null) || (spPr.getOutline().getFill().getDmlFillType() == DmlFillType.NO_FILL));

            applyStyleEntry(series.getDefaultDataPoint().getSpPr(), null, style.getByDmlChartStyleItem(dataPointItem), chartElementIndex);

            if (noLine)
                spPr.getOutline().setFill(new DmlNoFill());
        }

        applyStyle(series.getDefaultDataPoint().getMarker(), chartElementIndex);

        for (DmlChartTrendline trendLine : series.getTrendlines())
        {
            applyStyleEntry(trendLine.getSpPr(), null, style.getByDmlChartStyleItem(DmlChartStyleItem.TREND_LINE));

            DmlChartTrendlineLabel trendLineLabel = trendLine.getTrendlineLbl();
            if (trendLineLabel != null)
                applyStyleEntry(trendLineLabel.getSpPr(), trendLineLabel.getTxPr(), style.getByDmlChartStyleItem(DmlChartStyleItem.TRENDLINE_LABEL));
        }
    }

    /// <summary>
    /// Applies the specified chart style entry to the data label.
    /// </summary>
    private void applyStyleEntryToDataLabel(ChartDataLabel dataLabel, DmlChartStyleEntry styleEntry)
    {
        DmlChartDataLabelPr labelPr = dataLabel.getLabelPr();

        DmlChartSpPr spPr = (DmlChartSpPr)labelPr.getDirectProperty(DmlChartDataLabelAttrs.SP_PR);
        if (spPr == null)
        {
            // Set cloned inherited value.
            spPr = (DmlChartSpPr)labelPr.getProperty(DmlChartDataLabelAttrs.SP_PR);
            spPr = (spPr != null) ? spPr.deepClone() : new DmlChartSpPr();
            labelPr.setProperty(DmlChartDataLabelAttrs.SP_PR, spPr);
        }

        DmlChartTxPr txPr = (DmlChartTxPr)labelPr.getDirectProperty(DmlChartDataLabelAttrs.TX_PR);
        if (txPr == null)
        {
            // Set cloned inherited value.
            txPr = (DmlChartTxPr)labelPr.getProperty(DmlChartDataLabelAttrs.TX_PR);
            txPr = (txPr != null) ? txPr.deepClone() : new DmlChartTxPr();
            labelPr.setProperty(DmlChartDataLabelAttrs.TX_PR, txPr);
        }

        applyStyleEntry(spPr, txPr, styleEntry, dataLabel.getIndex());
    }

    /// <summary>
    /// Gets a flag indicating whether if a series of the specified chart type has no line between data points before
    /// applying a style, there should be no line after applying the style.
    /// </summary>
    private static boolean needPreserveDataPointNoLine(/*DmlChartType*/int chartType)
    {
        switch (chartType)
        {
            // Data points on stock and scatter charts should not be connected by lines.
            case DmlChartType.SCATTER_CHART:
            case DmlChartType.STOCK_CHART:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets a style item that should be applied to data points of the specified chart.
    /// </summary>
    private static /*DmlChartStyleItem*/int getDataPointStyleItem(DmlChart chart)
    {
        // For information about which data point style items apply to which chart types, see 2.8.3.1
        // CT_ChartStyle [MS-ODRAWXML].

        if ((chart.getChartType() == DmlChartType.SURFACE_CHART) && ((DmlSurfaceChart)chart).getWireframe())
            return DmlChartStyleItem.DATA_POINT_WIREFRAME;

        if (chart.getIs3D())
            return DmlChartStyleItem.DATA_POINT_3_D;

        if ((chart.getChartType() == DmlChartType.LINE_CHART) ||
            (chart.getChartType() == DmlChartType.RADAR_CHART) ||
            (chart.getChartType() == DmlChartType.SCATTER_CHART))
        {
            return DmlChartStyleItem.DATA_POINT_LINE;
        }

        return DmlChartStyleItem.DATA_POINT;
    }

    /// <summary>
    /// Gets a style item that should be applied to the specified axis.
    /// </summary>
    private static /*DmlChartStyleItem*/int getAxisStyleItem(ChartAxis axis)
    {
        /*DmlChartStyleItem*/int styleItem;
        switch (axis.getType())
        {
            case ChartAxisType.SERIES:
                styleItem = DmlChartStyleItem.SERIES_AXIS;
                break;
            case ChartAxisType.VALUE:
                styleItem = DmlChartStyleItem.VALUE_AXIS;
                break;
            case ChartAxisType.CATEGORY:
            default:
                styleItem = DmlChartStyleItem.CATEGORY_AXIS;
                break;
        }

        return styleItem;
    }

    /// <summary>
    /// Gets a chart attribute of the <see cref="DmlChartSpPr"/> type if the corresponding visibility attribute is
    /// <b>true</b>.
    /// </summary>
    private static DmlChartSpPr getChartSpPr(DmlChartPr chartPr, /*DmlChartAttrs*/int visibilityAttr, /*DmlChartAttrs*/int spPrAttr)
    {
        return (/*boolean*/Boolean)chartPr.getProperty(visibilityAttr)
            ? (DmlChartSpPr)chartPr.getDirectProperty(spPrAttr)
            : null;
    }

    /// <summary>
    /// Applies the chart style entry to the specified shape and text properties.
    /// </summary>
    private void applyStyleEntry(DmlChartSpPr spPr, DmlChartTxPr txPr, DmlChartStyleEntry styleEntry)
    {
        applyStyleEntry(spPr, txPr, styleEntry, 0);
    }

    /// <summary>
    /// Applies the chart style entry to the specified shape and text properties using the specified index to get
    /// element color.
    /// </summary>
    private void applyStyleEntry(DmlChartSpPr spPr, DmlChartTxPr txPr, DmlChartStyleEntry styleEntry,
        int chartElementIndex)
    {
        if (mChartSpace.isChartEx())
        {
            // Word 2016 chart automatically gets style from ChartStyle.
            if (spPr != null)
                spPr.clear();
            if (txPr != null)
                txPr.clear();
            return;
        }

        applyStyleEntryToSpPr(spPr, styleEntry, chartElementIndex);
        applyStyleEntryToTxPr(txPr, styleEntry, chartElementIndex);
    }

    /// <summary>
    /// Applies the chart style entry to the specified shape properties.
    /// </summary>
    private void applyStyleEntryToSpPr(DmlChartSpPr spPr, DmlChartStyleEntry styleEntry, int chartElementIndex)
    {
        if (spPr == null)
            return;

        // styleEntry.Modifiers are currently not supported.

        DefaultShapeProperties shapePr = styleEntry.getShapePr();

        if ((shapePr != null) && (shapePr.getFill() != null))
            spPr.setFill(shapePr.getFill().deepClone());
        else if (styleEntry.getFillReference().getStyleMatrixIndex() > 0)
            spPr.setFill(getTheme().getFillStyle(styleEntry.getFillReference().getStyleMatrixIndex() - 1));
        else
            spPr.setFill(new DmlNoFill());

        applyStyleColor(spPr.getFill(), styleEntry.getFillReference().getColor(), chartElementIndex);

        if ((shapePr != null) && (shapePr.getOutline() != null))
            spPr.setOutline(shapePr.getOutline().deepClone());
        else if (styleEntry.getLineReference().getStyleMatrixIndex() > 0)
            spPr.setOutline(getTheme().getLineStyle(styleEntry.getLineReference().getStyleMatrixIndex() - 1));
        else
            spPr.setOutline(createEmptyDmlOutline());

        if ((spPr.getOutline() != null) && (styleEntry.getLineReference().getColor() != null))
            applyStyleColor(spPr.getOutline().getFill(), styleEntry.getLineReference().getColor(), chartElementIndex);

        EffectStyle themeEffectStyle = (styleEntry.getEffectReference().getStyleMatrixIndex() > 0)
            ? getTheme().getEffectStyle(styleEntry.getEffectReference().getStyleMatrixIndex() - 1)
            : null;
        if ((shapePr != null) && (shapePr.getEffects() != null))
        {
            spPr.setEffects(shapePr.getEffects().deepClone());
        }
        else if (themeEffectStyle != null)
        {
            spPr.setEffects(themeEffectStyle.getEffects().deepClone());
            spPr.getEffects().isTheme(false);
        }
        else
        {
            spPr.setEffects(null);
        }

        if ((spPr.getEffects() != null) && (styleEntry.getEffectReference().getColor() != null))
        {
            DmlColor color = resolveStyleColor(styleEntry.getEffectReference().getColor(), chartElementIndex);
            for (DmlShapeEffect effect : spPr.getEffects())
                effect.setColor(addColorModifiers(color.deepClone(), effect.getColor().getColorModifiers()));
        }

        if ((shapePr != null) && (shapePr.getScene3DProperties() != null))
            spPr.setScene3DProp(shapePr.getScene3DProperties().deepClone());
        else if (themeEffectStyle != null)
            spPr.setScene3DProp(themeEffectStyle.getScene3DProperties());
        else
            spPr.setScene3DProp(null);

        if ((shapePr != null) && (shapePr.getShape3DProperties() != null))
            spPr.setShape3DProp(shapePr.getShape3DProperties().deepClone());
        else if (themeEffectStyle != null)
            spPr.setShape3DProp(themeEffectStyle.getShape3DProperties());
        else
            spPr.setShape3DProp(null);

        if ((shapePr != null) && (shapePr.getSpPrExtensions() != null))
            spPr.setExtensions(DmlExtensionListSource.cloneExtensions(shapePr.getSpPrExtensions()));
    }

    /// <summary>
    /// Applies the specified color to the fill.
    /// </summary>
    private void applyStyleColor(DmlFill fill, DmlColor color, int chartElementIndex)
    {
        if ((fill == null) || (color == null))
            return;

        color = resolveStyleColor(color, chartElementIndex);

        if ((fill.getDmlColorInternal() != null) && (fill.getDmlColorInternal().getColorType() == DmlColorType.PLACEHOLDER_COLOR))
            fill.setDmlColorInternal(addColorModifiers(color.deepClone(), fill.getDmlColorInternal().getColorModifiers()));

        if ((fill.getDmlColor2Internal() != null) && (fill.getDmlColor2Internal().getColorType() == DmlColorType.PLACEHOLDER_COLOR))
            fill.setDmlColor2Internal(addColorModifiers(color.deepClone(), fill.getDmlColor2Internal().getColorModifiers()));

        if (fill.getDmlFillType() == DmlFillType.GRADIENT_FILL)
        {
            DmlGradientFill gradientFill = (DmlGradientFill)fill;
            for (DmlGradientStop gradientStop : gradientFill.getGradientStops())
            {
                if (gradientStop.getColor().getColorType() == DmlColorType.PLACEHOLDER_COLOR)
                    gradientStop.setColor(addColorModifiers(color.deepClone(), gradientStop.getColor().getColorModifiers()));
            }
        }
    }

    /// <summary>
    /// If the passed color is a <see cref="DmlChartStyleColor"/> instance, gets the color from the chart's colors
    /// part using the specified index.
    /// </summary>
    private DmlColor resolveStyleColor(DmlColor color, int chartElementIndex)
    {
        if (color.getColorType() != DmlColorType.CHART_STYLE_COLOR)
            return color;

        DmlChartStyleColor styleColor = (DmlChartStyleColor)color;
        int colorIndex = styleColor.isAuto() ? chartElementIndex : styleColor.getColorIndex();
        return mChartSpace.getColorStyle().getColor(colorIndex);
    }

    /// <summary>
    /// Adds the modifiers to the specified color.
    /// </summary>
    private static DmlColor addColorModifiers(DmlColor color, Iterable<IDmlColorModifier> colorModifiers)
    {
        color = color.deepClone();

        for (IDmlColorModifier colorModifier : colorModifiers)
        {
            // It seems MS Word allows multiple modifiers of the same type and applies them all.
            msArrayList.add(color.getColorModifiers(), colorModifier.deepClone());
        }

        return color;
    }

    /// <summary>
    /// Creates a <see cref="DmlOutline"/> instance with no fill.
    /// </summary>
    private static DmlOutline createEmptyDmlOutline()
    {
        DmlOutline outline = new DmlOutline();
        outline.setFill(new DmlNoFill());
        return outline;
    }

    /// <summary>
    /// Applies the chart style entry to the specified text properties.
    /// </summary>
    private void applyStyleEntryToTxPr(DmlChartTxPr txPr, DmlChartStyleEntry styleEntry, int chartElementIndex)
    {
        if (txPr == null)
            return;

        txPr.setBodyPr((styleEntry.getTextBodyPr() != null) ? styleEntry.getTextBodyPr().deepClone() : null);

        DmlChartFontReference fontReference = styleEntry.getFontReference();
        /*DmlFontCollectionIndex*/int fontIndex = fontReference.getFontCollectionIndex();
        DmlFill fill =  (fontReference.getColor() != null)
            ? new DmlSolidFill(resolveStyleColor(fontReference.getColor(), chartElementIndex))
            : null;

        DmlRunProperties defaultRunPr = styleEntry.getDefaultRunPr();
        if (defaultRunPr != null)
        {
            defaultRunPr = defaultRunPr.deepClone();
            setFonts(defaultRunPr, fontIndex);
            defaultRunPr.setFill(fill);

            // It seems MS Word expands non-defined properties using defaults to not inherit from chart space or
            // from chart element specific defaults. Do the same.
            expandProperties(defaultRunPr);
        }

        setParagraphRunPr(txPr.getFirstParagraph(), defaultRunPr);

        for (DmlParagraph para : txPr.getParagraphs())
            setParagraphRunPr(para, defaultRunPr);
    }

    /// <summary>
    /// Sets a clone of the specified run properties to the DML paragraph.
    /// </summary>
    private static void setParagraphRunPr(DmlParagraph para, DmlRunProperties runPr)
    {
        para.getProperties().setDefaultRunProperties((runPr != null) ? runPr.deepClone() : null);
        if ((runPr != null) && !runPr.isEmpty())
            para.getProperties().hasDefaultRunProperties(true);
    }

    /// <summary>
    /// If some of the <see cref="gExpandedRunProperties"/> properties do not exist in the specified run properties,
    /// gets property values from the parent properties/defaults and adds directly to the run properties.
    /// </summary>
    private static void expandProperties(DmlRunProperties runPr)
    {
        for (/*DmlRunPropertiesIds*/int property : G_EXPANDED_RUN_PROPERTIES)
        {
            if (!runPr.isPropertySpecified(property))
                runPr.setProperty(property, runPr.getProperty(property));
        }
    }

    /// <summary>
    /// Sets the specified theme font to the run properties.
    /// </summary>
    private static void setFonts(DmlRunProperties runPr, /*DmlFontCollectionIndex*/int themeFontIndex)
    {
        switch (themeFontIndex)
        {
            case DmlFontCollectionIndex.MAJOR:
            {
                runPr.setLatinFont(new DmlFont("+mj-lt"));
                runPr.setEastAsianFont(new DmlFont("+mj-ea"));
                runPr.setComplexScriptFont(new DmlFont("+mj-cs"));
                break;
            }
            case DmlFontCollectionIndex.MINOR:
            {
                runPr.setLatinFont(new DmlFont("+mn-lt"));
                runPr.setEastAsianFont(new DmlFont("+mn-ea"));
                runPr.setComplexScriptFont(new DmlFont("+mn-cs"));
                break;
            }
            case DmlFontCollectionIndex.NONE:
            default:
            {
                runPr.setLatinFont(null);
                runPr.setEastAsianFont(null);
                runPr.setComplexScriptFont(null);
                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, mostly the visibility of some chart elements, so
    /// that a Normal style chart after applying the style looks the same as after applying the same style to a Normal
    /// style chart in MS Word.
    /// </summary>
    /// <remarks>
    /// A chart style definition (an instance of the <see cref="DmlChartColorStyle"/> class) contains only definitions
    /// of the text, fill, outline properties of chart elements, as well as symbol and size of data point markers.
    /// This method sets all other properties of chart elements that have different values compared to the Normal
    /// style when the corresponding style is set in MS Word.
    /// </remarks>
    private void defineStyleDependentProperties(DmlChart chart)
    {
        switch (getStyle())
        {
            case ChartStyle.NORMAL:
                // No changes.
                break;
            case ChartStyle.MUTED:
                defineMutedStyleDependentProperties(chart);
                break;
            case ChartStyle.SATURATED:
                defineSaturatedStyleDependentProperties(chart);
                break;
            case ChartStyle.SHADED:
                defineShadedStyleDependentProperties(chart);
                break;
            case ChartStyle.FLAT:
                defineFlatStyleDependentProperties(chart);
                break;
            case ChartStyle.SHADOWED:
                defineShadowedStyleDependentProperties(chart);
                break;
            case ChartStyle.GRADIENT:
                defineGradientStyleDependentProperties(chart);
                break;
            case ChartStyle.ORIGINAL:
                defineOriginalStyleDependentProperties(chart);
                break;
            case ChartStyle.TRANSPARENT_1:
                defineTransparent1StyleDependentProperties(chart);
                break;
            case ChartStyle.TRANSPARENT_2:
                defineTransparent2StyleDependentProperties(chart);
                break;
            case ChartStyle.OUTLINE:
                defineOutlineStyleDependentProperties(chart);
                break;
            case ChartStyle.OUTLINE_BLACK:
                defineOutlineBlackStyleDependentProperties(chart);
                break;
            case ChartStyle.BLACK:
                defineBlackStyleDependentProperties(chart);
                break;
            case ChartStyle.GREY:
                defineGreyStyleDependentProperties(chart);
                break;
            case ChartStyle.BLUE:
                defineBlueStyleDependentProperties(chart);
                break;
            case ChartStyle.SHADED_PLOT:
                defineShadedPlotStyleDependentProperties(chart);
                break;
            default:
                throw new IllegalStateException("Unknown chart style.");
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Muted"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineMutedStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);

                if ((chart.getChartPr().getGrouping() == Grouping.STACKED) ||
                    (chart.getChartPr().getGrouping() == Grouping.PERCENT_STACKED))
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(100);
                    barChart.setOverlap(isBar ? 0 : -24);
                }

                break;
            }
            case DmlChartType.LINE_CHART:
            {
                chart.getChartPr().setProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE, true);
                break;
            }
            case DmlChartType.RADAR_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.BOTTOM);
                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                    {
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);
                        break;
                    }
                    case ChartSeriesType.BOX_AND_WHISKER:
                    {
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);

                        for (ChartSeries series : chart.getSeries())
                            series.getLayoutPr().isMeanLineVisible(true);

                        break;
                    }
                    case ChartSeriesType.WATERFALL:
                    {
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);
                        showDataLabels(chart, ChartDataLabelPosition.INSIDE_END);
                        break;
                    }
                    case ChartSeriesType.FUNNEL:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    }
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Saturated"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineSaturatedStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        // The styles look similar, but with slight differences.
        if (chart.getChartType() != DmlChartType.LINE_CHART)
            defineMutedStyleDependentProperties(chart);
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Shaded"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineShadedStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*ChartSeriesType*/int seriesType = (chart.getSeries().getCount() > 0)
            ? chart.getSeries().get(0).getSeriesType()
            : (/*ChartSeriesType*/int)(-1);

        if ((chart.getChartType() != DmlChartType.BUBBLE_CHART) &&
            (seriesType != ChartSeriesType.HISTOGRAM) &&
            (seriesType != ChartSeriesType.PARETO) &&
            (seriesType != ChartSeriesType.FUNNEL))
        {
            getLegend().setPositionInternal(LegendPosition.TOP);
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);

                if (isStacked)
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(isBar ? 227 : 164);
                    barChart.setOverlap(isBar ? -48 : -22);
                }

                break;
            }
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                barChart.setGapWidth(isStacked ? 150 : 160);

                if (!isStacked)
                {
                    barChart.getChartPr().setProperty(DmlChartAttrs.GAP_DEPTH, 0);
                    getChartFormat().getView3D().setRotX(10);
                    getChartFormat().getView3D().setRotY(0);
                    getChartFormat().getView3D().setRAngAx(false);
                }

                break;
            }
            case DmlChartType.DOUGHNUT_CHART:
            {
                ((DmlDoughnutChart)chart).setHoleSize(70);
                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                switch (seriesType)
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.1));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.2));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Flat"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineFlatStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*ChartSeriesType*/int seriesType = (chart.getSeries().getCount() > 0)
            ? chart.getSeries().get(0).getSeriesType()
            : (/*ChartSeriesType*/int)(-1);

        if (!chart.getIs3D() &&
            (chart.getChartType() != DmlChartType.BUBBLE_CHART) &&
            (seriesType != ChartSeriesType.HISTOGRAM) &&
            (seriesType != ChartSeriesType.PARETO))
        {
            getLegend().setPositionInternal(LegendPosition.TOP);
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        if (chart.hasAxis())
            chart.getAxisY().hasMinorGridlines(true);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;

                if (isStacked)
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth((barChart.getBarDir() == BarDirection.BAR) ? 269 : 199);
                    barChart.setOverlap(0);
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                switch (seriesType)
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.5));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.03));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Shadowed"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineShadowedStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);

                if ((chart.getChartPr().getGrouping() == Grouping.STACKED) ||
                    (chart.getChartPr().getGrouping() == Grouping.PERCENT_STACKED))
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(isBar ? 115 : 100);
                    barChart.setOverlap(isBar ? -20 : -24);
                }

                break;
            }
            case DmlChartType.RADAR_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.TOP);
                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.0));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Gradient"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineGradientStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        boolean isStacked =
            (chart.getChartPr().getGrouping() == Grouping.STACKED) || (chart.getChartPr().getGrouping() == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);

                if (isStacked)
                {
                    barChart.setGapWidth(225);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(isBar ? 326 : 355);
                    barChart.setOverlap(isBar ? -58 : -70);
                }

                break;
            }
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBar3DChart bar3DChart = (DmlBar3DChart)chart;
                if ((bar3DChart.getBarDir() == BarDirection.COLUMN) || isStacked)
                    bar3DChart.setGapWidth(225);

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.75));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.5));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.5));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Original"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineOriginalStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        if (chart.hasAxis())
        {
            chart.getAxisX().hasMajorGridlines(true);
            chart.getAxisY().hasMajorGridlines((chart.getChartType() == DmlChartType.SCATTER_CHART));
            chart.getAxisY().hasMinorGridlines(false);
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.TOP);

                DmlBarChart barChart = (DmlBarChart)chart;
                if (chart.getIs3D())
                {
                    if (isStacked)
                        showDataLabels(chart);
                }
                else
                {
                    showDataLabels(
                        chart,
                        isStacked ? ChartDataLabelPosition.CENTER : ChartDataLabelPosition.OUTSIDE_END);
                }

                if (!chart.getIs3D() || isStacked)
                {
                    for (ChartSeries series : chart.getSeries())
                    {
                        DmlChartSpPr leaderLines = new DmlChartSpPr();
                        series.getDataLabels().getLabelPr().setExtensionProperty(DmlChartDataLabelAttrs.LEADER_LINES, leaderLines);
                        series.getDataLabels().getLabelPr().setExtensionProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, true);
                    }

                    chart.getAxisY().setHiddenInternal(true);
                }

                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);
                if (isStacked)
                {
                    barChart.setGapWidth(79);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(isBar ? 390 : 444);
                        barChart.setOverlap(isBar ? -75 : -90);
                    }
                    else
                    {
                        barChart.setGapWidth(isBar ? 285 : 300);
                    }
                }

                break;
            }
            case DmlChartType.LINE_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.TOP);
                break;
            }
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.NONE);

                if (chart.getChartType() == DmlChartType.DOUGHNUT_CHART)
                {
                    showDataLabels(chart);
                    ((DmlDoughnutChart)chart).setHoleSize(40);
                }
                else
                {
                    showDataLabels(chart, ChartDataLabelPosition.OUTSIDE_END);
                }

                for (ChartSeries series : chart.getSeries())
                {
                    series.getDataLabels().setShowValue(false);
                    series.getDataLabels().setShowCategoryName(true);
                    series.getDataLabels().setShowLeaderLines(true);
                    series.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.LEADER_LINES, new DmlChartSpPr());
                }

                break;
            }
            case DmlChartType.RADAR_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.TOP);
                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                    {
                        getLegend().setPositionInternal(LegendPosition.NONE);
                        showDataLabels(chart);
                        break;
                    }
                    case ChartSeriesType.BOX_AND_WHISKER:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.0));

                        for (ChartSeries series : chart.getSeries())
                            series.getLayoutPr().isMeanLineVisible(true);

                        break;
                    }
                    case ChartSeriesType.FUNNEL:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    }
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Transparent1"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineTransparent1StyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);
                if (isStacked)
                {
                    barChart.setGapWidth(80);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(isBar ? 90 : 80);
                        barChart.setOverlap(isBar ? 10 : 25);
                    }
                    else
                    {
                        barChart.setGapWidth(150);
                    }
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.25));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.25));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.15));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Transparent2"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineTransparent2StyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                if (isStacked)
                {
                    barChart.setGapWidth(100);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(100);
                        barChart.setOverlap(0);
                    }
                    else
                    {
                        barChart.setGapWidth(150);
                    }
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Outline"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineOutlineStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*ChartSeriesType*/int seriesType = (chart.getSeries().getCount() > 0)
            ? chart.getSeries().get(0).getSeriesType()
            : (/*ChartSeriesType*/int)(-1);

        if ((chart.getChartType() != DmlChartType.BUBBLE_CHART) &&
            (seriesType != ChartSeriesType.HISTOGRAM) &&
            (seriesType != ChartSeriesType.PARETO) &&
            (seriesType != ChartSeriesType.FUNNEL))
        {
            getLegend().setPositionInternal(LegendPosition.TOP);
        }

        if (chart.hasAxis())
        {
            chart.getAxisY().hasMajorGridlines(false);
            chart.getAxisY().hasMinorGridlines(false);
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);
                if (isStacked)
                {
                    barChart.setGapWidth(180);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(isBar ? 227 : 164);
                        barChart.setOverlap(isBar ? -48 : -35);
                    }
                    else
                    {
                        barChart.setGapWidth(180);
                    }
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.25));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.25));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.7));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.OutlineBlack"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineOutlineBlackStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*ChartSeriesType*/int seriesType = (chart.getSeries().getCount() > 0)
            ? chart.getSeries().get(0).getSeriesType()
            : (/*ChartSeriesType*/int)(-1);

        if ((chart.getChartType() != DmlChartType.BUBBLE_CHART) &&
            (chart.getChartType() != DmlChartType.SCATTER_CHART) &&
            (seriesType != ChartSeriesType.HISTOGRAM) &&
            (seriesType != ChartSeriesType.PARETO) &&
            (seriesType != ChartSeriesType.FUNNEL))
        {
            getLegend().setPositionInternal(LegendPosition.TOP);
        }

        if (chart.hasAxis())
        {
            chart.getAxisX().hasMajorGridlines(!chart.getIs3D());
            chart.getAxisY().hasMajorGridlines(!chart.getIs3D());
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);
                if (isStacked)
                {
                    barChart.setGapWidth(190);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(isBar ? 182 : 315);
                        barChart.setOverlap(isBar ? -50 : -40);
                    }
                    else
                    {
                        barChart.setGapWidth(190);
                    }
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.BOX_AND_WHISKER:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.0));
                        break;
                    case ChartSeriesType.WATERFALL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.0));
                        break;
                    case ChartSeriesType.FUNNEL:
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.6));
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Black"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineBlackStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;

                if ((chart.getChartPr().getGrouping() == Grouping.STACKED) ||
                    (chart.getChartPr().getGrouping() == Grouping.PERCENT_STACKED))
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (barChart.getBarDir() == BarDirection.BAR)
                    {
                        ((DmlBarChart)chart).setGapWidth(115);
                        ((DmlBarChart)chart).setOverlap(-20);
                    }
                    else
                    {
                        ((DmlBarChart)chart).setGapWidth(100);
                        ((DmlBarChart)chart).setOverlap(-24);
                    }
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                    {
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);
                        break;
                    }
                    case ChartSeriesType.BOX_AND_WHISKER:
                    {
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.5));

                        for (ChartSeries series : chart.getSeries())
                            series.getLayoutPr().isMeanLineVisible(true);

                        break;
                    }
                    case ChartSeriesType.WATERFALL:
                    {
                        showDataLabels(chart, ChartDataLabelPosition.INSIDE_END);
                        break;
                    }
                    case ChartSeriesType.FUNNEL:
                    {
                        // Show legend at top if it is not shown.
                        if (getChartFormat().getLegend() == null)
                            getLegend().setPositionInternal(LegendPosition.TOP);

                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));
                        break;
                    }
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Grey"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineGreyStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        if (chart.hasAxis())
        {
            chart.getAxisX().hasMajorGridlines(false); //(chart.ChartType == DmlChartType.ScatterChart)
            chart.getAxisY().hasMajorGridlines((chart.getChartType() != DmlChartType.BUBBLE_CHART));
            chart.getAxisY().hasMinorGridlines(false);
        }

        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chart.getChartType())
        {
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                if (!chart.getIs3D())
                {
                    showDataLabels(
                        chart,
                        isStacked ? ChartDataLabelPosition.CENTER : ChartDataLabelPosition.INSIDE_END);
                }

                if (!chart.getIs3D() || isStacked)
                {
                    for (ChartSeries series : chart.getSeries())
                    {
                        DmlChartSpPr leaderLines = new DmlChartSpPr();
                        series.getDataLabels().getLabelPr().setExtensionProperty(DmlChartDataLabelAttrs.LEADER_LINES, leaderLines);
                        series.getDataLabels().getLabelPr().setExtensionProperty(DmlChartDataLabelAttrs.SHOW_LEADER_LINES, true);
                    }

                    if ((barChart.getBarDir() == BarDirection.COLUMN) || isStacked)
                        chart.getAxisY().setHiddenInternal(true);
                }
                else
                {
                    getChartFormat().getView3D().setRotX(0);
                    getChartFormat().getView3D().setRotY(0);
                    getChartFormat().getView3D().setRAngAx(false);
                    getChartFormat().getView3D().setDepthPercent(60);
                    getChartFormat().getView3D().setPerspective(100);
                }

                if (isStacked)
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(65);
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                        barChart.setOverlap(0);
                }

                break;
            }
            case DmlChartType.BUBBLE_CHART:
            {
                showDataLabels(chart, ChartDataLabelPosition.CENTER);
                for (ChartSeries series : chart.getSeries())
                    series.getDataLabels().setShowBubbleSize(false);

                break;
            }
            case DmlChartType.LINE_CHART:
            {
                showDataLabels(chart, ChartDataLabelPosition.CENTER);
                chart.getAxisY().setHiddenInternal(true);
                break;
            }
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            {
                if (chart.getChartType() == DmlChartType.DOUGHNUT_CHART)
                {
                    showDataLabels(chart);
                    ((DmlDoughnutChart)chart).setHoleSize(50);
                }
                else
                {
                    showDataLabels(chart, ChartDataLabelPosition.CENTER);
                }

                for (ChartSeries series : chart.getSeries())
                {
                    series.getDataLabels().setShowValue(false);
                    series.getDataLabels().setShowPercentage(true);
                    series.getDataLabels().setShowLeaderLines(true);
                    series.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.LEADER_LINES, new DmlChartSpPr());
                }

                getLegend().setPositionInternal(LegendPosition.RIGHT);

                if (chart.getIs3D())
                {
                    getChartFormat().getView3D().setRotX(50);
                    getChartFormat().getView3D().setDepthPercent(100);
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                    {
                        showDataLabels(chart);
                        getLegend().setPositionInternal(LegendPosition.RIGHT);
                        break;
                    }
                    case ChartSeriesType.HISTOGRAM:
                    case ChartSeriesType.PARETO:
                    case ChartSeriesType.PARETO_LINE:
                    {
                        for (ChartSeries series : chart.getSeries())
                        {
                            if (series.hasDataLabels() || (series.getSeriesType() == ChartSeriesType.PARETO_LINE))
                                continue;

                            series.setHasDataLabels(true);
                            ChartDataLabelCollection dataLabels = series.getDataLabels();
                            dataLabels.setShowValue(true);
                            dataLabels.setPosition(ChartDataLabelPosition.INSIDE_END);
                        }

                        chart.getAxisY().setHiddenInternal(true);

                        break;
                    }
                    case ChartSeriesType.BOX_AND_WHISKER:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(1.5));
                        getLegend().setPositionInternal(LegendPosition.BOTTOM);
                        break;
                    }
                    case ChartSeriesType.FUNNEL:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.15));
                        break;
                    }
                    case ChartSeriesType.WATERFALL:
                    {
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.25));
                        chart.getAxisY().setHiddenInternal(true);
                        break;
                    }
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.Blue"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineBlueStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*DmlChartType*/int chartType = chart.getChartType();
        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chartType)
        {
            case DmlChartType.AREA_3_D_CHART:
            {
                if (!isStacked)
                    getLegend().setPositionInternal(LegendPosition.NONE);

                break;
            }
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                DmlBarChart barChart = (DmlBarChart)chart;
                if (chart.getIs3D() && (grouping == Grouping.STANDARD))
                    getLegend().setPositionInternal(LegendPosition.NONE);

                if (isStacked)
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    barChart.setGapWidth(70);
                    if (chartType == DmlChartType.BAR_CHART)
                        barChart.setOverlap(-20);
                }

                break;
            }
            case DmlChartType.BUBBLE_CHART:
            {
                showDataLabels(chart, ChartDataLabelPosition.CENTER);
                for (ChartSeries series : chart.getSeries())
                    series.getDataLabels().setShowBubbleSize(false);

                break;
            }
            case DmlChartType.LINE_3_D_CHART:
            {
                getLegend().setPositionInternal(LegendPosition.NONE);
                break;
            }
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            {
                if (chart.getChartType() == DmlChartType.DOUGHNUT_CHART)
                {
                    showDataLabels(chart);
                    ((DmlDoughnutChart)chart).setHoleSize(50);
                }
                else
                {
                    showDataLabels(chart, ChartDataLabelPosition.INSIDE_END);
                }

                for (ChartSeries series : chart.getSeries())
                {
                    series.getDataLabels().setShowValue(false);
                    series.getDataLabels().setShowPercentage(true);
                    series.getDataLabels().setShowCategoryName(true);
                    series.getDataLabels().setShowLeaderLines(true);
                    series.getDataLabels().setSeparator("\r");
                    series.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.LEADER_LINES, new DmlChartSpPr());
                }

                getLegend().setPositionInternal(LegendPosition.NONE);
                break;
            }
            case DmlChartType.STOCK_CHART:
            {
                if (chart.hasAxis())
                {
                    chart.getAxisY().hasMajorGridlines(false);
                    chart.getAxisY().hasMinorGridlines(false);
                }

                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                    {
                        getLegend().setPositionInternal(LegendPosition.NONE);
                        break;
                    }
                    case ChartSeriesType.FUNNEL:
                    {
                        getLegend().setPositionInternal(LegendPosition.TOP);
                        chart.getAxisX().getScaling().setGapWidth(DoubleOrAutomatic.fromDouble(0.5));

                        for (ChartSeries series : chart.getSeries())
                            series.setHasDataLabels(false);

                        break;
                    }
                }

                break;
            }
        }
    }

    /// <summary>
    /// Sets the required properties of the chart and its elements, so that a Normal style chart after applying the
    /// <see cref="ChartStyle.ShadedPlot"/> style looks the same as the corresponding style chart in MS Word.
    /// </summary>
    private void defineShadedPlotStyleDependentProperties(DmlChart chart)
    {
        if (chart.getIs3D())
            getChartFormat().getView3D().setDepthPercent(100);

        /*DmlChartType*/int chartType = chart.getChartType();
        /*Grouping*/int grouping = chart.getChartPr().getGrouping();
        boolean isStacked = (grouping == Grouping.STACKED) || (grouping == Grouping.PERCENT_STACKED);

        switch (chartType)
        {
            case DmlChartType.AREA_3_D_CHART:
            {
                if (!isStacked && chart.hasAxis())
                {
                    chart.getAxisZ().hasMajorGridlines(true);
                    chart.getAxisZ().hasMinorGridlines(false);
                }

                break;
            }
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
            {
                if (chart.hasAxis())
                {
                    chart.getAxisX().hasMajorGridlines(true);
                    chart.getAxisX().hasMinorGridlines(false);
                }

                DmlBarChart barChart = (DmlBarChart)chart;
                if (chart.getIs3D() && (grouping == Grouping.STANDARD))
                    getLegend().setPositionInternal(LegendPosition.NONE);

                boolean isBar = (barChart.getBarDir() == BarDirection.BAR);
                if (isStacked)
                {
                    barChart.setGapWidth(150);
                    barChart.setOverlap(100);
                }
                else
                {
                    if (chart.getChartType() == DmlChartType.BAR_CHART)
                    {
                        barChart.setGapWidth(isBar ? 247 : 267);
                        barChart.setOverlap(isBar ? 0 : -43);
                    }
                    else
                    {
                        barChart.setGapWidth(150);
                    }
                }

                break;
            }
            case DmlChartType.BUBBLE_CHART:
            {
                showDataLabels(chart, ChartDataLabelPosition.CENTER);
                for (ChartSeries series : chart.getSeries())
                    series.getDataLabels().setShowBubbleSize(false);

                break;
            }
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.OF_PIE_CHART:
            {
                if (chartType == DmlChartType.DOUGHNUT_CHART)
                    ((DmlDoughnutChart)chart).setHoleSize(70);

                getLegend().setPositionInternal(LegendPosition.RIGHT);
                break;
            }
            case DmlChartType.CHART_EX_CHART:
            {
                if (chart.getSeries().getCount() == 0)
                    break;

                switch (chart.getSeries().get(0).getSeriesType())
                {
                    case ChartSeriesType.TREEMAP:
                    case ChartSeriesType.SUNBURST:
                        getLegend().setPositionInternal(LegendPosition.RIGHT);
                        break;
                }

                break;
            }
        }
    }

    /// <summary>
    /// Changes the position of data labels if the color applied to them when the style is applied blends with
    /// the background at their current position.
    /// </summary>
    private void fixDataLabelPosition(ChartDataLabelCollection dataLabels)
    {
        /*ChartSeriesType*/int seriesType = dataLabels.getSeries().getSeriesType();
        /*ChartDataLabelPosition*/int position = dataLabels.getPosition();

        switch (getStyle())
        {
            case ChartStyle.BLUE:
            {
                if ((seriesType == ChartSeriesType.PIE_OF_BAR) &&
                    ((position == ChartDataLabelPosition.OUTSIDE_END) ||
                     (position == ChartDataLabelPosition.BEST_FIT)))
                {
                    dataLabels.setPosition(ChartDataLabelPosition.INSIDE_END);
                }

                break;
            }
            case ChartStyle.GREY:
            {
                switch (seriesType)
                {
                    case ChartSeriesType.BAR:
                    case ChartSeriesType.COLUMN:
                    {
                        if (position == ChartDataLabelPosition.OUTSIDE_END)
                            dataLabels.setPosition(ChartDataLabelPosition.INSIDE_END);

                        break;
                    }
                    case ChartSeriesType.BUBBLE:
                    case ChartSeriesType.BUBBLE_3_D:
                    case ChartSeriesType.LINE:
                    case ChartSeriesType.LINE_STACKED:
                    case ChartSeriesType.LINE_PERCENT_STACKED:
                    {
                        dataLabels.setPosition(ChartDataLabelPosition.CENTER);
                        break;
                    }
                }

                break;
            }
            case ChartStyle.ORIGINAL:
            {
                if (dataLabels.getSeries().isPieChartSeries() && (seriesType != ChartSeriesType.DOUGHNUT))
                    dataLabels.setPosition(ChartDataLabelPosition.OUTSIDE_END);

                break;
            }
        }
    }

    /// <summary>
    /// Displays  the data labels of all series of the specified chart.
    /// </summary>
    private static void showDataLabels(DmlChart chart)
    {
        for (ChartSeries series : chart.getSeries())
        {
            if (series.hasDataLabels())
                continue;

            series.setHasDataLabels(true);
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            dataLabels.setShowValue(true);
            dataLabels.setShowLegendKey(false);
            dataLabels.setShowCategoryName(false);
            dataLabels.setShowSeriesName(false);
            dataLabels.setShowPercentage(false);
            dataLabels.setShowLeaderLines(false);
        }

        chart.getDataLabels().setShowValue(true);
    }

    /// <summary>
    /// Displays the data labels of all series of the chart at the specified position.
    /// </summary>
    private static void showDataLabels(DmlChart chart, /*ChartDataLabelPosition*/int position)
    {
        showDataLabels(chart);

        for (ChartSeries series : chart.getSeries())
            series.getDataLabels().setPosition(position);
    }

    /// <summary>
    /// If the specified series is a series of a line or radar chart, displays the markers for the appropriate
    /// chart styles.
    /// </summary>
    private void markMarkersForShowingIfRequired(ChartSeries series)
    {
        /*DmlChartType*/int chartType = series.getChart().getChartType();
        if ((chartType != DmlChartType.LINE_CHART) && (chartType != DmlChartType.RADAR_CHART))
            return;

        switch (getStyle())
        {
            case ChartStyle.ORIGINAL:
            case ChartStyle.GREY:
            case ChartStyle.BLUE:
            {
                if ((chartType == DmlChartType.LINE_CHART) ||
                    ((chartType == DmlChartType.RADAR_CHART) && (getStyle() == ChartStyle.BLUE)))
                {
                    series.getDefaultDataPoint().getMarker().setSymbolInternal(MarkerSymbol.DEFAULT);
                }

                break;
            }
        }
    }

    /// <summary>
    /// Gets the document theme, initializing it if it does not exist.
    /// </summary>
    private IThemeProvider getTheme()
    {
        if (mChartSpace.getThemeOverride() != null)
            return mChartSpace.getThemeOverride();

        // This initializes a theme if not defined yet.
        return ((Document)mChartSpace.getDml().getDocument()).getTheme();
    }

    /// <summary>
    /// Gets the chart legend.
    /// </summary>
    private ChartLegend getLegend()
    {
        if (getChartFormat().getLegend() == null)
            getChartFormat().setLegend(new ChartLegend(getChartFormat()));

        return getChartFormat().getLegend();
    }

    /// <summary>
    /// Gets the chart format.
    /// </summary>
    private DmlChartFormat getChartFormat() { return mChartSpace.getChartFormat(); }

    /// <summary>
    /// Gets the applying chart style.
    /// </summary>
    private /*ChartStyle*/int getStyle() { return ChartStyleResolver.getChartStyle(mChartSpace.getDmlChartStyle().getIntId()); }

    private /*final*/ DmlChartSpace mChartSpace;

    private static /*final*/ /*DmlRunPropertiesIds*/int[] G_EXPANDED_RUN_PROPERTIES = new /*DmlRunPropertiesIds*/int[]
    {
        DmlRunPropertiesIds.FONT_SIZE, DmlRunPropertiesIds.BOLD, DmlRunPropertiesIds.ITALICS,
        DmlRunPropertiesIds.UNDERLINE, DmlRunPropertiesIds.STRIKETHROUGH, DmlRunPropertiesIds.KERNING,
        DmlRunPropertiesIds.BASELINE
    };

    private static /*final*/ /*MarkerSymbol*/int[] G_MARKER_AUTO_SYMBOLS = new /*MarkerSymbol*/int[]
    {
        MarkerSymbol.DIAMOND, MarkerSymbol.SQUARE, MarkerSymbol.TRIANGLE, MarkerSymbol.X, MarkerSymbol.STAR,
        MarkerSymbol.CIRCLE, MarkerSymbol.PLUS, MarkerSymbol.DOT, MarkerSymbol.DASH
    };
}

