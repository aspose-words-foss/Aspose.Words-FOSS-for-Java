// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/22/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Instance class for writing DrawingML chart series.
/// </summary>
/*static*/ class DmlChartSeriesWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartSeriesWriter()
	{}

    static void write(DmlChart chart, DocxDocumentWriterBase writer) throws Exception
    {
        ArrayList<ChartSeries> series = (ArrayList<ChartSeries> )chart.getChartPr().getDirectProperty(DmlChartAttrs.SERIES);
        if (series == null || series.size() == 0)
            return;

        switch (chart.getChartType())
        {
            case DmlChartType.AREA_CHART:
            case DmlChartType.AREA_3_D_CHART:
                writeAreaChartSeries(series, writer);
                break;
            case DmlChartType.LINE_CHART:
            case DmlChartType.LINE_3_D_CHART:
            case DmlChartType.STOCK_CHART:
                writeLineChartSeries(series, writer);
                break;
            case DmlChartType.RADAR_CHART:
                writeRadarChartSeries(series, writer);
                break;
            case DmlChartType.SCATTER_CHART:
                writeScatterChartSeries(series, writer);
                break;
            case DmlChartType.PIE_CHART:
            case DmlChartType.PIE_3_D_CHART:
            case DmlChartType.DOUGHNUT_CHART:
            case DmlChartType.OF_PIE_CHART:
                writePieChartSeries(series, writer);
                break;
            case DmlChartType.BAR_CHART:
            case DmlChartType.BAR_3_D_CHART:
                writeBarChartSeries(series, writer);
                break;
            case DmlChartType.SURFACE_CHART:
            case DmlChartType.SURFACE_3_D_CHART:
                writeSurfaceChartSeries(series, writer);
                break;
            case DmlChartType.BUBBLE_CHART:
                writeBubbleChartSeries(series, writer);
                break;
            case DmlChartType.CHART_EX_CHART:
                writeChartExSeries(series, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected Dml chart type");
        }
    }

    private static void writeBubbleChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            Debug.assertTrue(ChartDataPointCollection.supportsInvertIfNegative(ser.getChart().getChartType()));
            builder.writeElementWithAttributes(getTagName("invertIfNegative", false), "val", ser.getDefaultDataPoint().getInvertIfNegative());
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeTrendLines(ser.getTrendlines(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getXErrorBars(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getYErrorBars(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("xVal", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("yVal", false), ser.getY().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("bubbleSize", false), ser.getSize().getDataSource(),
                writer, false);
            builder.writeElementWithAttributes(getTagName("bubble3D", false), "val", ser.getDefaultDataPoint().getBubble3D());
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeLineChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);

            if (ser.getDefaultDataPoint().hasMarker())
                DmlChartCommonWriter.writeMarker(ser.getDefaultDataPoint().getMarker(), writer, false);

            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeTrendLines(ser.getTrendlines(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getXErrorBars(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getYErrorBars(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            builder.writeElementWithAttributes(getTagName("smooth", false), "val", ser.getSmooth());
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writePieChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            if (ser.getDefaultDataPoint().getExplosion() != -1)
                builder.writeElementWithAttributes(getTagName("explosion", false), "val",
                    ser.getDefaultDataPoint().getExplosion());
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeSurfaceChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeScatterChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            DmlChartCommonWriter.writeMarker(ser.getDefaultDataPoint().getMarker(), writer, false);
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeTrendLines(ser.getTrendlines(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getXErrorBars(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getYErrorBars(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("xVal", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("yVal", false), ser.getY().getDataSource(), writer, false);
            builder.writeElementWithAttributes(getTagName("smooth", false), "val", ser.getSmooth());
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeRadarChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            DmlChartCommonWriter.writeMarker(ser.getDefaultDataPoint().getMarker(), writer, false);
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeAreaChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            DmlChartCommonWriter.writePictureOptions(ser.getDefaultDataPoint().getPictureOptions(), builder, false);
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeTrendLines(ser.getTrendlines(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getXErrorBars(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getYErrorBars(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            writeSeriesEnd(ser, writer, false);
        }
    }

    private static void writeBarChartSeries(ArrayList<ChartSeries> series, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries ser : series)
        {
            builder.startElement(getTagName("ser", false));
            writeSeriesShared(ser, writer, false);
            Debug.assertTrue(ChartDataPointCollection.supportsInvertIfNegative(ser.getChart().getChartType()));
            builder.writeElementWithAttributes(getTagName("invertIfNegative", false), "val", ser.getDefaultDataPoint().getInvertIfNegative());
            DmlChartCommonWriter.writePictureOptions(ser.getDefaultDataPoint().getPictureOptions(), builder, false);
            DmlChartCommonWriter.writeDataPoints(ser, writer, false);
            if (ser.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(ser.getDataLabels(), writer, false);
            DmlChartCommonWriter.writeTrendLines(ser.getTrendlines(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getXErrorBars(), writer, false);
            DmlChartCommonWriter.writeErrorBars(ser.getYErrorBars(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("cat", false), ser.getX().getDataSource(), writer, false);
            DmlChartCommonWriter.writeDataSource(getTagName("val", false), ser.getY().getDataSource(), writer, false);
            // Write Shape of series only if it differs from the parent chart shape.
            if ((ser.getShape() != BarShape.NONE) &&
                (ser.getShape() != (/*BarShape*/Integer)ser.getChart().getChartPr().getProperty(DmlChartAttrs.SHAPE)))
                builder.writeElementWithAttributes(getTagName("shape", false), "val",
                    DmlChartsEnum.shapeToDml(ser.getShape()));
            writeSeriesEnd(ser, writer, false);
        }
    }

    /// <summary>
    /// Writes series of a chart of the http://schemas.microsoft.com/office/drawing/2014/chartex schema
    /// [MS-ODRAWXML].
    /// </summary>
    private static void writeChartExSeries(ArrayList<ChartSeries> seriesCollection, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        for (ChartSeries series : seriesCollection)
        {
            builder.startElement(getTagName("series", true));

            builder.writeAttribute("layoutId", DmlChartsEnum.seriesLayoutToDml(series.getLayoutType()));
            if (series.getHidden())
                builder.writeAttribute("hidden", series.getHidden());
            if (series.getOwner() != null)
            {
                int ownerIndex = seriesCollection.indexOf(series.getOwner());
                if (ownerIndex >= 0)
                    builder.writeAttribute("ownerIdx", ownerIndex);
            }
            builder.writeAttribute("uniqueId", series.getUniqueId());
            if (series.getFormatIndex() >= 0)
                builder.writeAttribute("formatIdx", series.getFormatIndex());

            writeSeriesShared(series, writer, true);
            DmlChartCommonWriter.writeValueColors(series.getValueColors(), writer);
            DmlChartCommonWriter.writeDataPoints(series, writer, true);
            if (series.hasDataLabels())
                DmlChartCommonWriter.writeDataLabels(series.getDataLabels(), writer, true);

            if (series.getDataId() >= 0)
                builder.writeElementWithAttributes(getTagName("dataId", true), "val", series.getDataId());
            DmlChartCommonWriter.writeLayoutProperties(series.getLayoutPr(), writer);
            if (series.getAxisId() >= 0)
                builder.writeElementWithAttributes(getTagName("axisId", true), "val", series.getAxisId());

            writeSeriesEnd(series, writer, true);
        }
    }

    /// <summary>
    /// Writes common properties of all series.
    /// </summary>
    private static void writeSeriesShared(ChartSeries series, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        if (!isChartEx)
        {
            builder.writeElementWithAttributes(getTagName("idx", isChartEx), "val", series.getIndex());
            builder.writeElementWithAttributes(getTagName("order", isChartEx), "val", series.getOrder());
        }
        DmlChartCommonWriter.writeTx(series.getTx(), writer, isChartEx);
        DmlChartCommonWriter.writeShapeProperties(series.getDefaultDataPoint().getSpPr(), writer, isChartEx);
    }

    /// <summary>
    /// Closes series tag and writes extensions if present.
    /// </summary>
    private static void writeSeriesEnd(ChartSeries series, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        StringToObjDictionary<DmlExtension> extensions = ((IDmlExtensionListSource)series).getExtensions();

        if (extensions != null)
            DmlExtensionListWriter.write(getTagPrefix(isChartEx), extensions, writer);

        writer.getCurrentBuilder().endElement(getTagName(isChartEx ? "series" : "ser", isChartEx));
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

