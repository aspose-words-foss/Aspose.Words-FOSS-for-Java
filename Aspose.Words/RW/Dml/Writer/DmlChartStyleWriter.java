// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;


/// <summary>
/// This class allows writing a style part of a chart.
/// </summary>
/*static*/ class DmlChartStyleWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartStyleWriter()
	{}

    /// <summary>
    /// Writes a <see cref="DmlChartStyle"/> object into the element 2.8.1.1 chartStyle [MS-ODRAWXML] that is
    /// a root element of the word/charts/styleX.xml part.
    /// </summary>
    static void write(DmlChartStyle style, DocxDocumentWriterBase writer) throws Exception
    {
        if (style == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cs:chartStyle");
        builder.writeAttribute("xmlns:cs", DmlExtensionsNamespace.CHART_STYLE);
        builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT));

        builder.writeAttribute("id", style.getId());

        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.AXIS_TITLE), "axisTitle", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.CATEGORY_AXIS), "categoryAxis", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.CHART_AREA), "chartArea", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL), "dataLabel", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_LABEL_CALLOUT), "dataLabelCallout", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT), "dataPoint", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT_3_D), "dataPoint3D", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT_LINE), "dataPointLine", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT_MARKER), "dataPointMarker", writer);
        writeMarkerLayout(style.getDataPointMarkerLayout(), "dataPointMarkerLayout", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_POINT_WIREFRAME), "dataPointWireframe", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DATA_TABLE), "dataTable", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DOWN_BAR), "downBar", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.DROP_LINE), "dropLine", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.ERROR_BAR), "errorBar", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.FLOOR), "floor", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.GRIDLINE_MAJOR), "gridlineMajor", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.GRIDLINE_MINOR), "gridlineMinor", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.HIGH_LOW_LINE), "hiLoLine", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.LEADER_LINE), "leaderLine", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.LEGEND), "legend", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.PLOT_AREA), "plotArea", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.PLOT_AREA_3_D), "plotArea3D", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.SERIES_AXIS), "seriesAxis", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.SERIES_LINE), "seriesLine", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.TITLE), "title", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.TREND_LINE), "trendline", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.TRENDLINE_LABEL), "trendlineLabel", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.UP_BAR), "upBar", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.VALUE_AXIS), "valueAxis", writer);
        writeStyleEntry(style.getByDmlChartStyleItem(DmlChartStyleItem.WALL), "wall", writer);

        DmlExtensionListWriter.write("cs", style.getExtensions(), writer);

        builder.endElement("cs:chartStyle");
        builder.flush();
    }

    /// <summary>
    /// Writes an element of the 2.8.3.7 CT_StyleEntry complex type.
    /// </summary>
    private static void writeStyleEntry(DmlChartStyleEntry styleEntry, String tagName, 
        DocxDocumentWriterBase writer) throws Exception
    {
        if (styleEntry == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getElementFullName(tagName));

        if ((styleEntry.getModifiers() != null) && (styleEntry.getModifiers().length > 0))
            builder.writeAttribute("mods", msString.join(" ", styleEntry.getModifiers()));

        writeStyleReference(styleEntry.getLineReference(), "lnRef", writer);
        if (!MathUtil.areEqual(styleEntry.getLineWidthScale(), DmlChartStyleEntry.LINE_WIDTH_SCALE_DEFAULT))
            builder.writeElement("cs:lineWidthScale", 
                FormatterPal.doubleToStr9Decimals(styleEntry.getLineWidthScale()));
        writeStyleReference(styleEntry.getFillReference(), "fillRef", writer);
        writeStyleReference(styleEntry.getEffectReference(), "effectRef", writer);
        writeFontReference(styleEntry.getFontReference(), "fontRef", writer);

        if (styleEntry.getShapePr() != null)
            DmlShapePropertiesWriter.write("cs", styleEntry.getShapePr(), writer, true);
        if (styleEntry.getDefaultRunPr() != null)
            DmlTextShapeWriter.writeDmlRunProperties("cs:defRPr", styleEntry.getDefaultRunPr(), writer);
        if (styleEntry.getTextBodyPr() != null)
            DmlTextShapeWriter.writeDmlTextShapeBodyPr("cs:bodyPr", styleEntry.getTextBodyPr(), writer);
        if (styleEntry.hasExtensions())
            DmlExtensionListWriter.write("cs", styleEntry.getExtensions(), writer);

        builder.endElement(getElementFullName(tagName));
    }

    /// <summary>
    /// Gets chart style element name with default prefix.
    /// </summary>
    private static String getElementFullName(String nameWithoutPrefix)
    {
        return msString.format("cs:{0}", nameWithoutPrefix);
    }

    /// <summary>
    /// Writes an element of the 2.8.3.8 CT_StyleReference complex type.
    /// </summary>
    private static void writeStyleReference(DmlChartStyleReference styleReference, String tagName, 
        DocxDocumentWriterBase writer)
    {
        writeReference(FormatterPal.intToXml(styleReference.getStyleMatrixIndex()), styleReference.getColor(),
            styleReference.getModifiers(), tagName, writer);
    }

    /// <summary>
    /// Writes an element of reference type (CT_StyleReference or CT_FontReference [MS-ODRAWXML]).
    /// </summary>
    private static void writeReference(String collectionIndex, DmlColor color, String[] modifiers, String tagName,
        DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getElementFullName(tagName));

        builder.writeAttribute("idx", collectionIndex);
        if ((modifiers != null) && (modifiers.length > 0))
            builder.writeAttribute("mods", msString.join(" ", modifiers));

        if (color != null)
        {
            if (color.getColorType() == DmlColorType.CHART_STYLE_COLOR)
                writeStyleColor((DmlChartStyleColor)color, writer);
            else
                DmlColorWriter.write(color, writer);
        }

        builder.endElement(getElementFullName(tagName));
    }

    /// <summary>
    /// Writes an element of the 2.8.3.6 CT_StyleColor complex type.
    /// </summary>
    private static void writeStyleColor(DmlChartStyleColor styleColor, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cs:styleClr");

        builder.writeAttribute("val", styleColor.getValue());
        DmlColorModifiersWriter.write("a", styleColor.getColorModifiers(), writer);

        builder.endElement("cs:styleClr");
    }

    /// <summary>
    /// Writes an element of the 2.8.3.4 CT_FontReference complex type.
    /// </summary>
    private static void writeFontReference(DmlChartFontReference fontReference, String tagName, 
        DocxDocumentWriterBase writer)
    {
        writeReference(DmlEnum.fontCollectionIndexToDml(fontReference.getFontCollectionIndex()),
            fontReference.getColor(), fontReference.getModifiers(), tagName, writer);
    }

    /// <summary>
    /// Writes an element of the 2.8.3.5 CT_MarkerLayout complex type.
    /// </summary>
    private static void writeMarkerLayout(DmlChartMarkerLayout markerLayout, String tagName, 
        DocxDocumentWriterBase writer)
    {
        if (markerLayout == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getElementFullName(tagName));

        if (StringUtil.hasChars(markerLayout.getSymbol()))
            builder.writeAttribute("symbol", markerLayout.getSymbol());
        if (markerLayout.getSize() > 0)
            builder.writeAttribute("size", markerLayout.getSize());

        builder.endElement(getElementFullName(tagName));
    }

}

