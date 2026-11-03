// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/22/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Instance class for writing DrawingML chart objects.
/// </summary>
/*static*/ class DmlChartObjectWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartObjectWriter()
	{}

    static void write(DmlChart chart, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        String tagName = getChartTagName(chart.getChartType(), isChartEx);

        writer.getCurrentBuilder().startElement(tagName);
        writeChartProperties(chart, writer, isChartEx);
        writer.getCurrentBuilder().endElement(tagName);
    }

    private static String getChartTagName(/*DmlChartType*/int type, boolean isChartEx)
    {
        switch (type)
        {
            case DmlChartType.AREA_CHART: return getTagName("areaChart", isChartEx);
            case DmlChartType.AREA_3_D_CHART: return getTagName("area3DChart", isChartEx);
            case DmlChartType.LINE_CHART: return getTagName("lineChart", isChartEx);
            case DmlChartType.LINE_3_D_CHART: return getTagName("line3DChart", isChartEx);
            case DmlChartType.STOCK_CHART: return getTagName("stockChart", isChartEx);
            case DmlChartType.RADAR_CHART: return getTagName("radarChart", isChartEx);
            case DmlChartType.SCATTER_CHART: return getTagName("scatterChart", isChartEx);
            case DmlChartType.PIE_CHART: return getTagName("pieChart", isChartEx);
            case DmlChartType.PIE_3_D_CHART: return getTagName("pie3DChart", isChartEx);
            case DmlChartType.DOUGHNUT_CHART: return getTagName("doughnutChart", isChartEx);
            case DmlChartType.BAR_CHART: return getTagName("barChart", isChartEx);
            case DmlChartType.BAR_3_D_CHART: return getTagName("bar3DChart", isChartEx);
            case DmlChartType.OF_PIE_CHART: return getTagName("ofPieChart", isChartEx);
            case DmlChartType.SURFACE_CHART: return getTagName("surfaceChart", isChartEx);
            case DmlChartType.SURFACE_3_D_CHART: return getTagName("surface3DChart", isChartEx);
            case DmlChartType.BUBBLE_CHART: return getTagName("bubbleChart", isChartEx);
            default:
                throw new IllegalArgumentException("Unexpected Dml chart type");
        }
    }

    private static void writeChartProperties(DmlChart chart, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        DmlChartPr pr = chart.getChartPr();

        // Specifies whether the series form a bar (horizontal) chart or a column (vertical) chart.
        // <see cref="BarDirection"/>.
        Object barDir = pr.getDirectProperty(DmlChartAttrs.BAR_DIR);
        if (barDir != null)
            builder.writeElementWithAttributes(getTagName("barDir", isChartEx), "val",
                DmlChartsEnum.barDirectionToDml((/*BarDirection*/Integer)barDir));

        // Specifies whether this chart is pie of pie or bar of pie.
        // <see cref="OfPieType"/>.
        Object ofPieType = pr.getDirectProperty(DmlChartAttrs.OF_PIE_TYPE);
        if (ofPieType != null)
            builder.writeElementWithAttributes(getTagName("ofPieType", isChartEx), "val",
                DmlChartsEnum.ofPieTypeToDml((/*OfPieType*/Integer)ofPieType));

        // Specifies what type of radar chart shall be drawn.
        // <see cref="RadarStyle"/>.
        Object radarStyle = pr.getDirectProperty(DmlChartAttrs.RADAR_STYLE);
        if (radarStyle != null)
            builder.writeElementWithAttributes(getTagName("radarStyle", isChartEx), "val",
                DmlChartsEnum.radarStyleToDml((/*RadarStyle*/Integer)radarStyle));

        // Specifies the type of lines for the scatter chart.
        // <see cref="ScatterStyle"/>.
        Object scatterStyle = pr.getDirectProperty(DmlChartAttrs.SCATTER_STYLE);
        if (scatterStyle != null)
            builder.writeElementWithAttributes(getTagName("scatterStyle", isChartEx), "val",
                DmlChartsEnum.scatterStyleToDml((/*ScatterStyle*/Integer)scatterStyle));

        // Specifies the surface chart is drawn as a wireframe.
        // Boolean.
        Object wireframe = pr.getDirectProperty(DmlChartAttrs.WIREFRAME);
        if (wireframe != null)
            builder.writeElementWithAttributes(getTagName("wireframe", isChartEx), "val", wireframe);

        // Specifies the type of grouping for a column, line, bar, or area chart.
        // <see cref="Grouping"/>.
        Object grouping = pr.getDirectProperty(DmlChartAttrs.GROUPING);
        if (grouping != null)
            builder.writeElementWithAttributes(getTagName("grouping", isChartEx), "val",
                DmlChartsEnum.groupingToDml((/*Grouping*/Integer)grouping));

        // Specifies that each data marker in the series shall have a different color.
        // Boolean.
        Object varyColors = pr.getDirectProperty(DmlChartAttrs.VARY_COLORS);
        if (varyColors != null)
            builder.writeElementWithAttributes(getTagName("varyColors", isChartEx), "val", varyColors);

        // Specifies list of series of the chart.
        // IList{T} with items of <see cref="ChartSeries"/> type.
        Object series = pr.getDirectProperty(DmlChartAttrs.SERIES);
        if (series != null)
            DmlChartSeriesWriter.write(chart, writer);

        // Specifies the settings for the data labels for the entire chart.
        // <see cref="ChartDataLabelCollection"/>.
        Object dLbls = pr.getDirectProperty(DmlChartAttrs.D_LBLS);
        if (dLbls != null)
            DmlChartCommonWriter.writeDataLabels((ChartDataLabelCollection)dLbls, writer, isChartEx);

        // Specifies drop lines formatting.
        // <see cref="DmlChartSpPr"/>.
        Object dropLines = pr.getDirectProperty(DmlChartAttrs.DROP_LINES);
        boolean isDropLinesVisible = (/*boolean*/Boolean)pr.getProperty(DmlChartAttrs.IS_DROP_LINES_VISIBLE);
        if (dropLines != null)
            DmlChartCommonWriter.writeWrappedSpPr(getTagName("dropLines", isChartEx), (DmlChartSpPr)dropLines,
                writer, isDropLinesVisible, isChartEx);

        // Specifies the high-low lines formatting for the series.
        // <see cref="DmlChartSpPr"/>.
        Object hiLowLines = pr.getDirectProperty(DmlChartAttrs.HI_LOW_LINES);
        boolean isHiLowLinesVisible = (/*boolean*/Boolean)pr.getProperty(DmlChartAttrs.IS_HI_LOW_LINES_VISIBLE);
        if (hiLowLines != null)
            DmlChartCommonWriter.writeWrappedSpPr(getTagName("hiLowLines", isChartEx), (DmlChartSpPr)hiLowLines,
                writer, isHiLowLinesVisible, isChartEx);

        // Specifies the space between bar or column clusters, as a percentage of the bar or column width.
        // Integer [0;500].
        Object gapWidth = pr.getDirectProperty(DmlChartAttrs.GAP_WIDTH);
        if (gapWidth != null)
            builder.writeElementWithAttributes(getTagName("gapWidth", isChartEx), "val", gapWidth);

        // Specifies the space between bar or column clusters, as a percentage of the bar or column width.
        // Integer [0;500].
        Object gapDepth = pr.getDirectProperty(DmlChartAttrs.GAP_DEPTH);
        if (gapDepth != null)
            builder.writeElementWithAttributes(getTagName("gapDepth", isChartEx), "val", gapDepth);

        // Specifies the shape of a 3-D bar chart.
        Object shape = pr.getDirectProperty(DmlChartAttrs.SHAPE);
        if (shape != null)
            builder.writeElementWithAttributes(getTagName("shape", isChartEx), "val",
                DmlChartsEnum.shapeToDml((/*BarShape*/Integer)shape));

        // Specifies how much bars and columns shall overlap on 2-D charts.
        // Integer [-100;100].
        Object overlap = pr.getDirectProperty(DmlChartAttrs.OVERLAP);
        if (overlap != null)
            builder.writeElementWithAttributes(getTagName("overlap", isChartEx), "val", overlap);

        // Specifies how to determine which data points are in the second pie or bar on a pie of pie or bar of pie chart.
        // <see cref="SplitType"/>.
        Object splitType = pr.getDirectProperty(DmlChartAttrs.SPLIT_TYPE);
        if (splitType != null)
            builder.writeElementWithAttributes(getTagName("splitType", isChartEx), "val",
                DmlChartsEnum.splitTypeToDml((/*SplitType*/Integer)splitType));

        // Specifies a value that shall be used to determine which data points
        // are in the second pie or bar on a pie of pie or bar of pie chart.
        // Double.
        Object splitPos = pr.getDirectProperty(DmlChartAttrs.SPLIT_POS);
        if (splitPos != null)
            builder.writeElementWithAttributes(getTagName("splitPos", isChartEx), "val", splitPos);

        // Contains the custom split information for a pie-of-pie or bar-of-pie chart with a custom split type.
        // Array of Integers.
        Object custSplit = pr.getDirectProperty(DmlChartAttrs.CUST_SPLIT);
        if (custSplit != null)
            DmlChartCommonWriter.writeCustomSplit((int[])custSplit, builder, isChartEx);

        // specifies the size of the second pie or bar of a pie of pie chart or a bar of pie chart,
        // as a percentage of the size of the first pie.
        // Integer [5;200].
        Object secondPieSize = pr.getDirectProperty(DmlChartAttrs.SECOND_PIE_SIZE);
        if (secondPieSize != null)
            builder.writeElementWithAttributes(getTagName("secondPieSize", isChartEx), "val", secondPieSize);

        // Specifies series lines formatting for the chart.
        // <see cref="DmlChartSpPr"/>.
        Object serLines = pr.getDirectProperty(DmlChartAttrs.SER_LINES);
        if (serLines != null)
            DmlChartCommonWriter.writeWrappedSpPr(getTagName("serLines", isChartEx), (DmlChartSpPr)serLines, writer,
                (/*boolean*/Boolean)pr.getProperty(DmlChartAttrs.SHOW_SER_LINE), isChartEx);

        // Specifies the scale factor for the bubble chart.
        // Integer [0; 300].
        Object bubbleScale = pr.getDirectProperty(DmlChartAttrs.BUBBLE_SCALE);
        if (bubbleScale != null)
            builder.writeElementWithAttributes(getTagName("bubbleScale", isChartEx), "val", bubbleScale);

        // Specifies negative sized bubbles shall be shown on a bubble chart.
        // Boolean.
        Object showNegBubbles = pr.getDirectProperty(DmlChartAttrs.SHOW_NEG_BUBBLES);
        if (showNegBubbles != null)
            builder.writeElementWithAttributes(getTagName("showNegBubbles", isChartEx), "val", showNegBubbles);

        // Specifies how the bubble size values are represented on the chart.
        // <see cref="SizeRepresents"/>.
        Object sizeRepresents = pr.getDirectProperty(DmlChartAttrs.SIZE_REPRESENTS);
        if (sizeRepresents != null)
            builder.writeElementWithAttributes(getTagName("sizeRepresents", isChartEx), "val",
                DmlChartsEnum.sizeRepresentsToDml((/*SizeRepresents*/Integer)sizeRepresents));

        // Specifies the angle of the first pie or doughnut chart slice, in degrees (clockwise from up).
        // Integer [0;300].
        Object firstSliceAng = pr.getDirectProperty(DmlChartAttrs.FIRST_SLICE_ANG);
        if (firstSliceAng != null)
            builder.writeElementWithAttributes(getTagName("firstSliceAng", isChartEx), "val", firstSliceAng);

        // Specifies the size of the hole in a doughnut chart.
        // Integer [10;90].
        Object holeSize = pr.getDirectProperty(DmlChartAttrs.HOLE_SIZE);
        if (holeSize != null)
            builder.writeElementWithAttributes(getTagName("holeSize", isChartEx), "val", holeSize);

        // Specifies the up and down bars.
        // <see cref="DmlChartUpDownBars"/>.
        Object upDownBars = pr.getDirectProperty(DmlChartAttrs.UP_DOWN_BARS);
        if (upDownBars != null)
            DmlChartCommonWriter.writeUpDownBars((DmlChartUpDownBars)upDownBars, writer, isChartEx);

        // Specifies that the marker shall be shown.
        // Boolean.
        Object showMarker = pr.getDirectProperty(DmlChartAttrs.SHOW_MARKER);
        if (showMarker != null)
            builder.writeElementWithAttributes(getTagName("marker", isChartEx), "val", showMarker);

        // Specifies the line connecting the points on the chart shall be smoothed using Catmull-Rom splines.
        // Boolean.
        Object smooth = pr.getDirectProperty(DmlChartAttrs.SMOOTH);
        if (smooth != null)
            builder.writeElementWithAttributes(getTagName("smooth", isChartEx), "val", smooth);

        // Contains a collection of formatting bands for a surface chart indexed from low to high.
        // <see cref="DmlChartBandFormats"/>.
        Object bandFmts = pr.getDirectProperty(DmlChartAttrs.BAND_FMTS);
        if (bandFmts != null)
            DmlChartCommonWriter.writeBandFormats((DmlChartBandFormats)bandFmts, writer, isChartEx);

        // Id of X axis.
        // Integer.
        Object axIdX = pr.getDirectProperty(DmlChartAttrs.AX_ID_X);
        if (axIdX != null)
            builder.writeElementWithAttributes(getTagName("axId", isChartEx), "val", axIdX);

        // Id of Y axis.
        // Integer.
        Object axIdY = pr.getDirectProperty(DmlChartAttrs.AX_ID_Y);
        if (axIdY != null)
            builder.writeElementWithAttributes(getTagName("axId", isChartEx), "val", axIdY);

        // Id of Z axis (for 3D charts).
        // Integer.
        Object axIdZ = pr.getDirectProperty(DmlChartAttrs.AX_ID_Z);
        if (axIdZ != null)
            builder.writeElementWithAttributes(getTagName("axId", isChartEx), "val", axIdZ);

        Object dmlExtensions = pr.getDirectProperty(DmlChartAttrs.EXTENSIONS);
        if (dmlExtensions != null)
            DmlExtensionListWriter.write(getTagPrefix(isChartEx), (StringToObjDictionary<DmlExtension>)dmlExtensions, writer);
    }

    /// <summary>
    /// Generates a tag name with a prefix depending on chart type.
    /// </summary>
    /// <param name="localName">Name without prefix.</param>
    /// <param name="isChartEx">A flag indicating that the chart is of the chartEx schema.</param>
    /// <returns>Tag name with prefix.</returns>
    private static String getTagName(String localName, boolean isChartEx)
    {
        return DmlChartCommonWriter.getTagName(localName, isChartEx);
    }

    /// <summary>
    /// Gets a tag prefix depending on chart type.
    /// </summary>
    private static String getTagPrefix(boolean isChartEx)
    {
        return DmlChartCommonWriter.getTagPrefix(isChartEx);
    }
}

