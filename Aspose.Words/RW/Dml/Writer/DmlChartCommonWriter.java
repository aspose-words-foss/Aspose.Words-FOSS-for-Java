// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/21/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.StringToObjDictionary;


/*static*/ class DmlChartCommonWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartCommonWriter()
	{}

    /// <summary>
    /// Writes shape properties.
    /// </summary>
    static void writeShapeProperties(DmlChartSpPr spPr, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        writeShapeProperties(spPr, writer, isChartEx, null);
    }

    /// <summary>
    /// Writes shape properties.
    /// </summary>
    static void writeShapeProperties(DmlChartSpPr spPr, IDmlShapeWriterContext writer, boolean isChartEx,
        String prefix) throws Exception
    {
        if (spPr == null || spPr.isEmpty())
            return;

        if (!StringUtil.hasChars(prefix))
            prefix = getTagPrefix(isChartEx);

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName(prefix, "spPr"));

        // In chart blip fill must have 'a' prefix.
        if ((spPr.getDirectFill() != null) && (spPr.getDirectFill().getDmlFillType() == DmlFillType.BLIP_FILL))
            DmlFillWriter.writeDmlBlipFill("a", (DmlBlipFill)spPr.getDirectFill(), writer);
        else
            DmlFillWriter.write(spPr.getDirectFill(), writer, false);

        DmlOutlineWriter.write("a:ln", spPr.getDirectOutline(), writer);
        DmlShapeEffectsWriter.write(spPr.getEffects(), writer, false);
        Dml3DPropertiesWriter.writeScene3D(spPr.getScene3DProp(), writer, false);
        Dml3DPropertiesWriter.writeShape3D(spPr.getShape3DProp(), writer, false);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), spPr.getExtensions(), writer);

        builder.endElement(getTagName(prefix, "spPr"));
    }

    static void writeTextProperties(DmlChartTxPr txPr, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (txPr == null || txPr.isEmpty())
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("txPr", isChartEx));

        DmlTextShapeWriter.writeDmlTextShapeBodyPr("a:bodyPr", txPr.getBodyPr(), writer);
        // According to the specs lstStyle element is not supported within chart, write it as empty tag.
        builder.writeEmptyElement("a:lstStyle");
        DmlTextShapeWriter.writeParagraph(txPr.getFirstParagraph(), writer);

        builder.endElement(getTagName("txPr", isChartEx));
    }

    static void writeTitle(DmlChartTitle title, IDmlShapeWriterContext writer,
        boolean isAutoTitleDeleted, boolean isChartEx, boolean isAxisTitle) throws Exception
    {
        if ((title == null) || isAutoTitleDeleted)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("title", isChartEx));

        // WORDSNET-21476 For the axis title, these attributes cause an error when MS Word 2019 tries to open the file.
        // It doesn't matter if the title text is set or not.
        if (isChartEx && (!isAxisTitle))
        {
            if (title.getSidePosition() != SidePosition.TOP)
                builder.writeAttribute("pos", DmlChartsEnum.sidePositionToDml(title.getSidePosition()));
            if (title.getPositionAlignment() != PositionAlignment.CENTER)
                builder.writeAttribute("align", DmlChartsEnum.positionAlignmentToDml(title.getPositionAlignment()));

            builder.writeAttributeIfTrue(OVERLAY, title.getOverlay());
        }

        writeTx(title.getTx(), writer, isChartEx);
        if (!isChartEx)
        {
            writeManualLayout(getTagPrefix(isChartEx), title.getLayout(), writer, isChartEx);
            builder.writeElementWithAttributes(getTagName(OVERLAY, isChartEx), "val", title.getOverlay());
        }
        writeShapeProperties(title.getSpPr(), writer, isChartEx);
        writeTextProperties(title.getTxPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), ((IDmlExtensionListSource)title).getExtensions(), writer);

        builder.endElement(getTagName("title", isChartEx));
    }

    static void writeTx(DmlChartTx tx, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (tx == null)
            return;

        if ((tx.getTxType() == DmlChartTxType.CHART_TEXT) && (tx.getRichText().getParagraphs().size() == 0))
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("tx", isChartEx));

        if (tx.getStrRef() == null)
        {
            if (tx.getTxType() == DmlChartTxType.CHART_TEXT)
            {
                writeRich(tx, writer, isChartEx);
            }
            else
            {
                if (isChartEx)
                {
                    writeTxData(tx, writer);
                }
                else
                {
                    // "c:v" is required so write even if SourcePlainText is empty.
                    builder.writeElement(getTagName("v", isChartEx), tx.getSourcePlainText());
                }
            }
        }
        else
        {
            writeValueRef(tx.getStrRef(), writer, false);
        }

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), tx.getExtensions(), writer);

        builder.endElement(getTagName("tx", isChartEx));
    }

    /// <summary>
    /// Writes a rich text element (21.2.2.156 rich).
    /// </summary>
    private static void writeRich(DmlChartTx tx, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("rich", isChartEx));

        DmlTextShapeWriter.writeDmlTextShapeBodyPr("a:bodyPr", tx.getRichText().getProperties(), writer);
        // According to the specs lstStyle element is not supported within chart, write it as empty tag.
        builder.writeEmptyElement("a:lstStyle");
        for (DmlParagraph paragraph : tx.getRichText().getParagraphs())
            DmlTextShapeWriter.writeParagraph(paragraph, writer);

        builder.endElement(getTagName("rich", isChartEx));
    }

    /// <summary>
    /// Writes the txData element of the 2.24.3.79 CT_TextData complex type [MS-ODRAWXML].
    /// </summary>
    private static void writeTxData(DmlChartTx tx, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("txData", true));

        if (tx.getFormula() != null)
            writeFormula(getTagName("f", true), tx.getFormula(), builder);

        if (tx.getPlainText() != null)
        {
            builder.startElement(getTagName("v", true));
            builder.writeString(tx.getPlainText());
            builder.endElement(getTagName("v", true));
        }

        builder.endElement(getTagName("txData", true));
    }

    static void writeDataSource(String tagName, DmlChartDataSource dataSource, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        DmlChartValueCollection values = dataSource.getValues();
        if (values == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        if (!dataSource.getValueRef().isLiteralData())
        {
            // Writes data as xxxRef/xxxCache elements.
            writeValueRef(dataSource.getValueRef(), writer, isChartEx);
        }
        else
        {
            // This code is intended to write xxxLit element, not xxxCache. Otherwise wrong markup is written.

            if (!isChartEx && (values.getValueType() == DmlChartValueType.MULTI_LVL_STRING))
            {
                // Need to write AlternateContent element.

                builder.startElement("mc:AlternateContent");
                builder.writeAttribute("xmlns:mc",
                    DocxNamespaces.getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, false));

                builder.startElement("mc:Choice");
                builder.writeAttribute("Requires", "c16ac");
                builder.writeAttribute("xmlns:c16ac", DocxNamespaces.getNamespace(DocxNamespace.CHART_16_AC, false));
                writeValueCollection(values, writer, false, false);
                builder.endElement("mc:Choice");

                builder.startElement("mc:Fallback");
                DmlChartValueCollection fallbackValues = dataSource.getOrGenerateFallbackValues();
                writeValueCollection(fallbackValues, writer, false, false);
                builder.endElement("mc:Fallback");

                builder.endElement("mc:AlternateContent");
            }
            else
            {
                writeValueCollection(values, writer, false, isChartEx);
            }
        }

        builder.endElement(tagName);
    }

    static void writeManualLayout(String prefix, DmlChartManualLayout layout, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (layout == null)
        {
            builder.writeEmptyElement(msString.format("{0}:layout", prefix));
            return;
        }

        builder.startElement(msString.format("{0}:layout", prefix));
        builder.startElement(getTagName("manualLayout", isChartEx));

        if (layout.getLayoutTarget() == LayoutTarget.INNER)
            builder.writeElementWithAttributes(getTagName("layoutTarget", isChartEx), "val", DmlChartsEnum.layoutTargetToDml(layout.getLayoutTarget()));

        writeLayoutMode(layout.getLeftMode(), builder, getTagName("xMode", isChartEx));
        writeLayoutMode(layout.getTopMode(), builder, getTagName("yMode", isChartEx));
        writeLayoutMode(layout.getWidthMode(), builder, getTagName("wMode", isChartEx));
        writeLayoutMode(layout.getHeightMode(), builder, getTagName("hMode", isChartEx));

        if (layout.isXSet() || layout.isYSet())
        {
            builder.writeElementWithAttributes(getTagName("x", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(layout.getLeft()));
            builder.writeElementWithAttributes(getTagName("y", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(layout.getTop()));
        }

        if (layout.isWidthSet())
            builder.writeElementWithAttributes(getTagName("w", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(layout.getWidth()));

        if (layout.isHeightSet())
            builder.writeElementWithAttributes(getTagName("h", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(layout.getHeight()));

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), layout.getExtensions(), writer);

        builder.endElement(getTagName("manualLayout", isChartEx));
        builder.endElement(msString.format("{0}:layout", prefix));
    }

    /// <summary>
    /// Writes wrapped bool value.
    /// </summary>
    static void writeWrappedBooleanIfTrue(String tagName, boolean value, NrxXmlBuilder builder)
    {
        if (value)
            builder.writeElementWithAttributes(tagName, "val", value);
    }

    /// <summary>
    /// Writes wrapped bool value.
    /// </summary>
    static void writeWrappedDoubleIfNotZero(String tagName, double value, NrxXmlBuilder builder)
    {
        if (!MathUtil.isZero(value))
            builder.writeElementWithAttributes(tagName, "val", FormatterPal.doubleToStr9Decimals(value));
    }

    static void writeSurface(String tagName, DmlChartSurface surface, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        if (surface == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        if (!isChartEx)
            builder.writeElementWithAttributes(getTagName("thickness", isChartEx), "val", surface.getThickness());
        writeShapeProperties(surface.getSpPr(), writer, isChartEx);
        if (!isChartEx)
            writePictureOptions(surface.getPictureOptions(), builder, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), surface.getExtensions(), writer);

        builder.endElement(tagName);
    }

    static void writePictureOptions(DmlChartPictureOptions pictOpt, NrxXmlBuilder builder, boolean isChartEx)
    {
        if (pictOpt == null)
            return;

        builder.startElement(getTagName("pictureOptions", isChartEx));
        writeWrappedBooleanIfTrue(getTagName("applyToEnd", isChartEx), pictOpt.getApplyToEnd(), builder);
        writeWrappedBooleanIfTrue(getTagName("applyToFront", isChartEx), pictOpt.getApplyToFront(), builder);
        writeWrappedBooleanIfTrue(getTagName("applyToSides", isChartEx), pictOpt.getApplyToSides(), builder);
        builder.writeElementWithAttributes(getTagName("pictureFormat", isChartEx), "val",
            DmlChartsEnum.pictureFormatToDml(pictOpt.getPictureFormat()));
        if (!MathUtil.isZero(pictOpt.getPictureStackUnit()))
            builder.writeElementWithAttributes(getTagName("pictureStackUnit", isChartEx), "val",
                pictOpt.getPictureStackUnit());
        builder.endElement(getTagName("pictureOptions", isChartEx));
    }

    static void writeLegend(ChartLegend legend, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if ((legend == null) || !legend.isVisible())
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("legend", isChartEx));

        if (isChartEx)
        {
            if (legend.getSidePosition() != ChartLegend.CHART_EX_DEFAULT_SIDE_POSITION)
                builder.writeAttribute("pos", DmlChartsEnum.sidePositionToDml(legend.getSidePosition()));
            if (legend.getPositionAlignment() != PositionAlignment.CENTER)
                builder.writeAttribute("align", DmlChartsEnum.positionAlignmentToDml(legend.getPositionAlignment()));

            builder.writeAttributeIfTrue(OVERLAY, legend.getOverlay());
        }
        else
        {
            builder.writeElementWithAttributes(getTagName("legendPos", isChartEx), "val",
                DmlChartsEnum.legendPositionToDml(legend.getPosition()));

            for (ChartLegendEntry entry : legend.getLegendEntries().getMaterializedLegendEntries())
                writeLegendEntry(entry, writer, isChartEx);

            writeManualLayout(getTagPrefix(isChartEx), legend.getLayout(), writer, isChartEx);
            builder.writeElementWithAttributes(getTagName(OVERLAY, isChartEx), "val", legend.getOverlay());
        }
        writeShapeProperties(legend.getSpPr(), writer, isChartEx);
        writeTextProperties(legend.getTxPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), ((IDmlExtensionListSource)legend).getExtensions(), writer);

        builder.endElement(getTagName("legend", isChartEx));
    }

    static void writeProtection(DmlChartProtection protection, NrxXmlBuilder builder, boolean isChartEx)
    {
        if (protection == null || !protection.hasProtection())
            return;

        builder.startElement(getTagName("protection", isChartEx));
        writeWrappedBooleanIfTrue(getTagName("chartObject", isChartEx), protection.getChartObject(), builder);
        writeWrappedBooleanIfTrue(getTagName("data", isChartEx), protection.getData(), builder);
        writeWrappedBooleanIfTrue(getTagName("formatting", isChartEx), protection.getFormatting(), builder);
        writeWrappedBooleanIfTrue(getTagName("selection", isChartEx), protection.getSelection(), builder);
        writeWrappedBooleanIfTrue(getTagName("userInterface", isChartEx), protection.getUserInterface(), builder);
        builder.endElement(getTagName("protection", isChartEx));
    }

    static void writeNumberFormat(DmlChartNumFormat format, NrxXmlBuilder builder, boolean isChartEx)
    {
        if (format == null)
            return;

        builder.startElement(getTagName("numFmt", isChartEx));
        builder.writeAttribute("formatCode",
            !msString.isNullOrEmpty(format.getSourceFormatCode())
                ? format.getSourceFormatCode()
                : ChartNumberFormat.GENERAL_FORMAT_CODE);
        builder.writeAttribute("sourceLinked", format.getSourceLinked());
        builder.endElement(getTagName("numFmt", isChartEx));
    }

    static void writeDataLabels(ChartDataLabelCollection dataLabels, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        Debug.assertTrue(dataLabels != null);
        DmlChartDataLabelPr labelPr = dataLabels.getLabelPr();

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName(isChartEx ? "dataLabels" : "dLbls", isChartEx));

        if (isChartEx)
        {
            Object position = labelPr.getDirectProperty(DmlChartDataLabelAttrs.D_LBL_POS);
            if (position != null)
                builder.writeAttribute("pos", DmlChartsEnum.dataLabelPositionToDml((/*ChartDataLabelPosition*/Integer)position));
        }

        ArrayList<ChartDataLabel> labels = dataLabels.getLabelsToWrite();
        for (ChartDataLabel label : labels)
            writeDataLabel(label, writer, isChartEx);

        writeDataLabelProperties(getTagPrefix(isChartEx), dataLabels.getLabelPr(), writer, isChartEx, false);

        if (isChartEx)
        {
            for (ChartDataLabel label : labels)
            {
                if (label.isHidden())
                    builder.writeElementWithAttributes(getTagName("dataLabelHidden", true), "idx", label.getIndex());
            }
        }

        builder.endElement(getTagName(isChartEx ? "dataLabels" : "dLbls", isChartEx));
    }

    /// <summary>
    /// Writes an element of the CT_ChartLines complex type [ISO/IEC 29500] or the 2.24.3.52 CT_Gridlines
    /// complex type [MS-ODRAWXML].
    /// </summary>
    static void writeGridlines(String tagName, DmlChartGridlines gridlines, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        if (gridlines == null)
            return;

        writer.getBuilder().startElement(getTagName(tagName, isChartEx));

        writeShapeProperties(gridlines.getSpPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), gridlines.getExtensions(), writer);

        writer.getBuilder().endElement(getTagName(tagName, isChartEx));
    }

    static void writeWrappedSpPr(String wrapperTagName, DmlChartSpPr spPr, IDmlShapeWriterContext writer,
        boolean allowEmpty, boolean isChartEx) throws Exception
    {
        if (spPr == null || (spPr.isEmpty() && !allowEmpty))
            return;

        writer.getBuilder().startElement(wrapperTagName);
        writeShapeProperties(spPr, writer, isChartEx);
        writer.getBuilder().endElement(wrapperTagName);
    }

    static void writeDataPoints(ChartSeries series, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        boolean writeInvertIfNegative = ChartDataPointCollection.supportsInvertIfNegative(series.getChart().getChartType());

        NrxXmlBuilder builder = writer.getBuilder();
        for (ChartDataPoint dpt : series.getDataPoints().getMaterializedDataPoints())
        {
            if (!dpt.hasNonDefaultFormatting())
                continue;

            builder.startElement(getTagName(isChartEx ? "dataPt" : "dPt", isChartEx));
            DmlChartDataPointPr dmlChartDataPointPr = dpt.getPointPr();
            if (isChartEx)
            {
                builder.writeAttribute("idx", dpt.getIndex());
            }
            else
            {
                writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.INDEX, writer, isChartEx);

                // The attribute 'invertIfNegative' does not get a value from the parent collection (series) in
                // MS Word. Thus if a value is not defined in a data point but is written for the collection
                // (series), it should be written too. And because 'invertIfNegative' is always written for the
                // collection (series) in some chart types (see DmlChartSeriesWriter), let's write it for all
                // materialized data points in that charts too.
                if (writeInvertIfNegative || dpt.getPointPr().isPropertySpecified(DmlChartDataPointAttr.INVERT_IF_NEGATIVE))
                {
                    builder.writeElementWithAttributes(getTagName("invertIfNegative", false),
                        "val", dpt.getInvertIfNegative());
                }

                writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.BUBBLE_3_D, writer, isChartEx);
                writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.EXPLOSION, writer, isChartEx);
                writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.MARKER, writer, isChartEx);
                writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.PICTURE_OPTIONS, writer, isChartEx);
            }
            writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.SP_PR, writer, isChartEx);
            writeDataPointAttr(dmlChartDataPointPr, DmlChartDataPointAttr.EXTENSIONS, writer, isChartEx);
            builder.endElement(getTagName(isChartEx ? "dataPt" : "dPt", isChartEx));
        }
    }

    static void writeMarker(ChartMarker marker, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        DmlChartMarkerPr dmlChartMarkerPr = marker.getMarkerPr();
        if (dmlChartMarkerPr.getCount() == 0)
            return;

        writer.getBuilder().startElement(getTagName("marker", isChartEx));
        writeMarkerAttr(dmlChartMarkerPr, DmlChartMarkerAttr.SYMBOL, writer, isChartEx);
        writeMarkerAttr(dmlChartMarkerPr, DmlChartMarkerAttr.SIZE, writer, isChartEx);
        writeMarkerAttr(dmlChartMarkerPr, DmlChartMarkerAttr.SP_PR, writer, isChartEx);
        writeMarkerAttr(dmlChartMarkerPr, DmlChartMarkerAttr.EXTENSIONS, writer, isChartEx);
        writer.getBuilder().endElement(getTagName("marker", isChartEx));
    }

    static void writeErrorBars(DmlChartErrorBars errorBars, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (errorBars == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(getTagName("errBars", isChartEx));
        if (errorBars.getErrDir() != ErrorBarDirection.Y)
            builder.writeElementWithAttributes(getTagName("errDir", isChartEx), "val", DmlChartsEnum.errorBarDirectionToDml(errorBars.getErrDir()));
        builder.writeElementWithAttributes(getTagName("errBarType", isChartEx), "val", DmlChartsEnum.errorBarTypeToDml(errorBars.getErrBarType()));
        builder.writeElementWithAttributes(getTagName("errValType", isChartEx), "val", DmlChartsEnum.errorValueTypeToDml(errorBars.getErrValType()));
        builder.writeElementWithAttributes(getTagName("noEndCap", isChartEx), "val", errorBars.getNoEndCap());
        writeDataSource(getTagName("plus", isChartEx), errorBars.getPlus().getDataSource(), writer, isChartEx);
        writeDataSource(getTagName("minus", isChartEx), errorBars.getMinus().getDataSource(), writer, isChartEx);
        if (!MathUtil.isZero(errorBars.getVal()))
            builder.writeElementWithAttributes(getTagName("val", isChartEx), "val", errorBars.getVal());
        writeShapeProperties(errorBars.getSpPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), errorBars.getExtensions(), writer);
        builder.endElement(getTagName("errBars", isChartEx));
    }

    static void writeTrendLines(ArrayList<DmlChartTrendline> trendlines, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        for (DmlChartTrendline trendline : trendlines)
        {
            builder.startElement(getTagName("trendline", isChartEx));
            DmlChartTrendlinePr dmlChartTrendlinePr = trendline.getTrendlinePr();
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.NAME, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.SP_PR, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.TRENDLINE_TYPE, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.ORDER, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.PERIOD, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.FORWARD, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.BACKWARD, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.INTERCEPT, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.DISP_R_SQR, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.DISP_EQ, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.TRENDLINE_LBL, writer, isChartEx);
            writeTrendlineArrt(dmlChartTrendlinePr, DmlChartTrendlineAttr.EXTENSIONS, writer, isChartEx);
            builder.endElement(getTagName("trendline", isChartEx));
        }
    }

    static void writeView3D(DmlChartFormat chartFormat, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        DmlChartView3D view3D = chartFormat.getView3D();

        if (view3D == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(getTagName("view3D", isChartEx));

        builder.writeElementWithAttributes(getTagName("rotX", isChartEx), "val", view3D.getRotX());
        writeDoubleIfNotDefault(getTagName("hPercent", isChartEx), view3D.getHPercent(), DmlChartView3D.DEFAULT_H_PERCENT, builder);

        // By specification default RotY is zero, however in MS Word if RotY is omitted,
        // it uses value 20 degrees as default for all charts except Pie3DChart, for Pie3DChart the default value is zero.
        int rotY = view3D.getRotY();
        if ((chartFormat.getPlotArea().getFirstChart().getChartType() == DmlChartType.PIE_3_D_CHART) && !view3D.hasRotY())
            rotY = 0;

        builder.writeElementWithAttributes(getTagName("rotY", isChartEx), "val", rotY);

        writeDoubleIfNotDefault(getTagName("depthPercent", isChartEx), view3D.getDepthPercent(), DmlChartView3D.DEFAULT_DEPTH_PERCENT, builder);
        builder.writeElementWithAttributes(getTagName("rAngAx", isChartEx), "val", view3D.getRAngAx());

        writeDoubleIfNotDefault(getTagName("perspective", isChartEx), view3D.getPerspective(), DmlChartView3D.DEFAULT_PERSPECTIVE, builder);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), view3D.getExtensions(), writer);

        builder.endElement(getTagName("view3D", isChartEx));
    }

    static void writePivotFmts(DmlChartPivotFormats pivotFmts, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (pivotFmts == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("pivotFmts", isChartEx));

        for (DmlChartPivotFormat fmt : pivotFmts.getFmts().getValues())
        {
            builder.startElement(getTagName("pivotFmt", isChartEx));
            builder.writeElementWithAttributes(getTagName("idx", isChartEx), "val", fmt.getIndex());
            writeShapeProperties(fmt.getSpPr(), writer, isChartEx);
            writeTextProperties(fmt.getTxPr(), writer, isChartEx);
            writeMarker(fmt.getMarker(), writer, isChartEx);
            writeDataLabel(fmt.getDLbl(), writer, isChartEx);
            DmlExtensionListWriter.write(getTagPrefix(isChartEx), fmt.getExtensions(), writer);
            builder.endElement(getTagName("pivotFmt", isChartEx));
        }

        builder.endElement(getTagName("pivotFmts", isChartEx));
    }

    static void writePivotSource(DmlChartPivotSource pivotSource, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        if (pivotSource == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("pivotSource", isChartEx));
        builder.writeElement(getTagName("name", isChartEx), pivotSource.getName());
        builder.writeElementWithAttributes(getTagName("fmtId", isChartEx), "val", pivotSource.getFmtId());
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), pivotSource.getExtensions(), writer);
        builder.endElement(getTagName("pivotSource", isChartEx));
    }

    static void writeDisplayUnits(AxisDisplayUnit dispUnits, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if ((dispUnits == null) || !dispUnits.isDefined())
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        String elementName = isChartEx ? "units" : "dispUnits";

        builder.startElement(getTagName(elementName, isChartEx));

        if (dispUnits.getUnit() != AxisBuiltInUnit.NONE)
        {
            if (isChartEx)
            {
                if (dispUnits.getUnit() != AxisBuiltInUnit.CUSTOM)
                    builder.writeAttribute("unit", DmlChartsEnum.builtInUnitToDml(dispUnits.getUnit()));
            }
            else
            {
                if (dispUnits.getUnit() != AxisBuiltInUnit.CUSTOM)
                {
                    builder.writeElementWithAttributes(getTagName("builtInUnit", isChartEx), "val",
                        DmlChartsEnum.builtInUnitToDml(dispUnits.getUnit()));
                }
                else
                {
                    builder.writeElementWithAttributes(getTagName("custUnit", isChartEx), "val",
                        FormatterPal.doubleToStrRoundtrip(dispUnits.getCustomUnit()));
                }
            }
        }

        writeDisplayUnitsLabel(dispUnits.getLabel(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx),
            ((IDmlExtensionListSource)dispUnits).getExtensions(), writer);

        builder.endElement(getTagName(elementName, isChartEx));
    }

    static void writeScaling(AxisScaling scaling, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("scaling", isChartEx));

        if (scaling.getType() == AxisScaleType.LOGARITHMIC)
            builder.writeElementWithAttributes(getTagName("logBase", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(scaling.getLogBase()));

        builder.writeElementWithAttributes(getTagName("orientation", isChartEx), "val",
            DmlChartsEnum.axisOrientationToDml(scaling.getOrientation()));

        if (!scaling.getMaximum().isAuto())
            builder.writeElementWithAttributes(getTagName("max", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(scaling.getMaximum().getValue()));

        if (!scaling.getMinimum().isAuto())
            builder.writeElementWithAttributes(getTagName("min", isChartEx), "val",
                FormatterPal.doubleToStrRoundtrip(scaling.getMinimum().getValue()));

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), ((IDmlExtensionListSource)scaling).getExtensions(), writer);

        builder.endElement(getTagName("scaling", isChartEx));
    }

    static void writeBandFormats(DmlChartBandFormats bandFmts, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (bandFmts == null || bandFmts.getCount() == 0)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(getTagName("bandFmts", isChartEx));

        for (DmlChartBandFormat format : bandFmts.getFormats())
        {
            builder.startElement(getTagName("bandFmt", isChartEx));
            builder.writeElementWithAttributes(getTagName("idx", isChartEx), "val", format.getIndex());
            writeShapeProperties(format.getSpPr(), writer, isChartEx);
            builder.endElement(getTagName("bandFmt", isChartEx));
        }

        builder.endElement(getTagName("bandFmts", isChartEx));
    }

    static void writeCustomSplit(int[] custSplit, NrxXmlBuilder builder, boolean isChartEx)
    {
        builder.startElement(getTagName("custSplit", isChartEx));
        for (int split : custSplit)
            builder.writeElementWithAttributes(getTagName("secondPiePt", isChartEx), "val", split);
        builder.endElement(getTagName("custSplit", isChartEx));
    }

    static void writeUpDownBars(DmlChartUpDownBars upDownBars, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        if (upDownBars == null || upDownBars.isEmpty())
            return;

        writer.getBuilder().startElement(getTagName("upDownBars", isChartEx));
        writer.getBuilder().writeElementWithAttributes(getTagName("gapWidth", isChartEx), "val", upDownBars.getGapWidth());
        writeWrappedSpPr(getTagName("upBars", isChartEx), upDownBars.getUpBars(), writer, upDownBars.hasUpBars(), isChartEx);
        writeWrappedSpPr(getTagName("downBars", isChartEx), upDownBars.getDownBars(), writer, upDownBars.hasDownBars(), isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), upDownBars.getExtensions(), writer);
        writer.getBuilder().endElement(getTagName("upDownBars", isChartEx));
    }

    private static void writeValueRef(DmlChartValueRef valueRef, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        String tagName = getValueRefTagName(valueRef.getValues().getValueType(), isChartEx);

        builder.startElement(tagName);
        if (isChartEx)
        {
            builder.writeAttribute("type", DmlChartsEnum.dimensionTypeToDml(valueRef.getDimensionType()));

            writeFormula(getTagName("f", isChartEx), valueRef.getFormula(), builder);
            writeFormula(getTagName("nf", isChartEx), valueRef.getNameFormula(), builder);

            writeChartValueLevels(valueRef.getValues(), builder, isChartEx);
        }
        else
        {
            builder.writeElement(getTagName("f", isChartEx), valueRef.getFormula().getValue());
            writeValueCollection(valueRef.getValues(), writer, true, isChartEx);
            DmlExtensionListWriter.write(getTagPrefix(isChartEx), valueRef.getExtensions(), writer);
        }

        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes an element of the 2.24.3.28 CT_Formula complex type [MS-ODRAWXML].
    /// </summary>
    private static void writeFormula(String elementName, DmlChartFormula formula, NrxXmlBuilder builder)
    {
        if (formula == null || !StringUtil.hasChars(formula.getValue()))
            return;

        builder.startElement(elementName);

        if (formula.getDirection() != FormulaDirection.COLUMN)
            builder.writeAttribute("dir", DmlChartsEnum.formulaDirectionToDml(formula.getDirection()));
        builder.writeString(formula.getValue());

        builder.endElement();
    }

    private static String getValueRefTagName(/*DmlChartValueType*/int refType, boolean isChartEx)
    {
        switch (refType)
        {
            case DmlChartValueType.STRING:
                return getTagName("strRef", isChartEx);
            case DmlChartValueType.NUMERIC:
                return getTagName("numRef", isChartEx);
            case DmlChartValueType.MULTI_LVL_STRING:
                return getTagName(isChartEx ? "strDim" : "multiLvlStrRef", isChartEx);
            case DmlChartValueType.MULTI_LVL_NUMERIC:
            {
                if (isChartEx)
                    return getTagName("numDim", isChartEx);
                else
                    throw new IllegalArgumentException("Unexpected Dml Chart Value Reference Type");
            }
            default:
                throw new IllegalArgumentException("Unexpected Dml Chart Value Reference Type");
        }
    }

    static void writeValueCollection(String tagName, DmlChartValueCollection values,
        IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if ((values == null) || (values.getValueCount() <= 0) || (values.getValueType() == DmlChartValueType.NONE))
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(tagName);

        if (values.getValueType() == DmlChartValueType.NUMERIC && StringUtil.hasChars(values.getFormatCode()))
            builder.writeElement(getTagName("formatCode", isChartEx), values.getFormatCode());

        builder.writeElementWithAttributes(getTagName("ptCount", isChartEx), "val", values.getValueCount());

        if (values.getValueType() == DmlChartValueType.MULTI_LVL_STRING)
        {
            writeChartValueLevels(values, builder, false);
        }
        else
        {
            // Let's order by value index.
            for (int i = 0; i < values.getValueCount(); i++)
            {
                DmlChartValue value = values.get(i);
                if (value != null)
                    writeChartValuePoint(value, builder, isChartEx);
            }
        }

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), values.getExtensions(), writer);

        builder.endElement(tagName);
    }

    private static void writeValueCollection(DmlChartValueCollection values, IDmlShapeWriterContext writer,
        boolean isCache, boolean isChartEx) throws Exception
    {
        String tagName = getValueCollectionTagName(values, isCache, isChartEx);
        writeValueCollection(tagName, values, writer, isChartEx);
    }

    private static String getValueCollectionTagName(DmlChartValueCollection values, boolean isCache, boolean isChartEx)
    {
        switch (values.getValueType())
        {
            case DmlChartValueType.STRING:
                return getTagName(isCache ? "strCache" : "strLit", isChartEx);
            case DmlChartValueType.NUMERIC:
                return getTagName(isCache ? "numCache" : "numLit", isChartEx);
            case DmlChartValueType.MULTI_LVL_STRING:
            {
                // There is a special namespace for the multiLvlStrLit element in pre-Word 2016 charts.
                if (!isCache && !isChartEx)
                    return "c16ac:multiLvlStrLit";
                else
                    return getTagName("multiLvlStrCache", isChartEx);
            }
            default:
                throw new IllegalArgumentException("Unexpected Dml Chart Value Type");
        }
    }

    private static void writeChartValueLevels(DmlChartValueCollection values, NrxXmlBuilder builder, boolean isChartEx)
    {
        int levels = 0;
        if (isChartEx)
        {
            levels = values.getLevelProperties().size();
        }
        else
        {
            for (DmlChartValue value : values)
                levels = Math.max(((DmlChartMultiLvlStrValue)value).getLevels().size(), levels);
        }

        for (int i = 0; i < levels; i++)
        {
            builder.startElement(getTagName("lvl", isChartEx));
            if (isChartEx)
            {
                DmlChartDataLevelProperties properties = values.getLevelProperties().get(i);
                if (properties != null)
                {
                    builder.writeAttribute("ptCount", properties.getValueCount());
                    builder.writeAttribute("formatCode", properties.getFormatCode());
                    builder.writeAttribute("name", properties.getName());
                }
            }

            // Let's write sorted by index.
            for (int j = 0; j <= values.getLastNonEmptyValueIndex(); j++)
            {
                DmlChartMultiLvlValue value = (DmlChartMultiLvlValue)values.get(j);
                if (value == null)
                    continue;

                // WORDSNET-11644 Levels can have a different number of <c:pt> elements.
                if ((value.getLevels().size() > i) && (value.getLevels().get(i) != null))
                {
                    builder.startElement(getTagName("pt", isChartEx));
                    builder.writeAttribute("idx", value.getIndex());
                    if (isChartEx)
                    {
                        builder.writeString(values.getValueType() == DmlChartValueType.MULTI_LVL_STRING
                            ? (String)value.getLevels().get(i)
                            : FormatterPal.doubleToStr((/*double*/Double)value.getLevels().get(i)));
                    }
                    else
                    {
                        builder.writeElement(getTagName("v", isChartEx), (String)value.getLevels().get(i));
                    }
                    builder.endElement(getTagName("pt", isChartEx));
                }
            }

            builder.endElement(getTagName("lvl", isChartEx));
        }
    }

    private static void writeChartValuePoint(DmlChartValue val, NrxXmlBuilder builder, boolean isChartEx)
    {
        if (val.getValueType() == DmlChartValueType.NONE)
            return;

        builder.startElement(getTagName("pt", isChartEx));
        builder.writeAttribute("idx", val.getIndex());

        if (val.getValueType() == DmlChartValueType.STRING)
            builder.writeElement(getTagName("v", isChartEx), val.getStringValue());

        if (val.getValueType() == DmlChartValueType.NUMERIC)
        {
            DmlChartNumValue numValue = (DmlChartNumValue)val;
            if (StringUtil.hasChars(numValue.getSourceFormatCode()))
                builder.writeAttribute("formatCode", numValue.getSourceFormatCode());
            // NaN value must be written as #N/A. See TestJira8965.
            builder.writeElement(getTagName("v", isChartEx),
                Double.isNaN(numValue.getValue()) ? "#N/A" : FormatterPal.doubleToStrRoundtrip(numValue.getValue()));
        }

        builder.endElement(getTagName("pt", isChartEx));
    }

    private static void writeLayoutMode(/*LayoutMode*/int layoutMode, NrxXmlBuilder builder, String tagName)
    {
        // Factor is default mode so do not write it.
        if (layoutMode == LayoutMode.FACTOR)
            return;

        builder.writeElementWithAttributes(tagName, "val", DmlChartsEnum.layoutModeToDml(layoutMode));
    }

    private static void writeLegendEntry(ChartLegendEntry entry, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (!entry.hasNonDefaultFormatting())
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("legendEntry", isChartEx));
        builder.writeElementWithAttributes(getTagName("idx", isChartEx), "val", entry.getIndex());
        writeWrappedBooleanIfTrue(getTagName("delete", isChartEx), entry.isHidden(), builder);
        writeTextProperties(entry.getTxPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), ((IDmlExtensionListSource)entry).getExtensions(), writer);
        builder.endElement(getTagName("legendEntry", isChartEx));
    }

    private static void writeDataLabel(ChartDataLabel label, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if ((label == null) || (isChartEx && label.isHidden()))
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName(isChartEx ? "dataLabel" : "dLbl", isChartEx));

        if (isChartEx)
        {
            builder.writeAttribute("idx", label.getIndex());
            Object position = label.getLabelPr().getDirectProperty(DmlChartDataLabelAttrs.D_LBL_POS);
            if (position != null)
                builder.writeAttribute("pos", DmlChartsEnum.dataLabelPositionToDml((/*ChartDataLabelPosition*/Integer)position));
        }

        writeDataLabelProperties(getTagPrefix(isChartEx), label.getLabelPr(), writer, isChartEx, label.isHidden());

        builder.endElement(getTagName(isChartEx ? "dataLabel" : "dLbl", isChartEx));
    }

    private static void writeDataLabelProperties(String prefix, DmlChartDataLabelPr labelPr,
        IDmlShapeWriterContext writer, boolean isChartEx, boolean isHidden) throws Exception
    {
        if (!isChartEx)
        {
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.IDX, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.DELETE, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.LAYOUT, writer, isChartEx);
            // WORDSNET-20461 If the data label is hidden we must not write this node,
            // otherwise Word will notify that file is corrupted.
            // I didnt found anything related in the specs, and found the solution comparing Word's and AW's output.
            // Libre Office have no that problem, looks like it's a bug in the Word.
            if (!isHidden)
                writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.TX, writer, isChartEx);
        }
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.NUM_FMT, writer, isChartEx);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SP_PR, writer, isChartEx);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.TX_PR, writer, isChartEx);
        if (isChartEx)
        {
            writeDataLabelVisibility(prefix, labelPr, writer);
        }
        // WORDSNET-20461 The description is above.
        else if (!isHidden)
        {
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.D_LBL_POS, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_LEGEND_KEY, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_VAL, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_CAT_NAME, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_SER_NAME, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_PERCENT, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE, writer, isChartEx);
        }
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SEPARATOR, writer, isChartEx);
        if (!isChartEx)
        {
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_LEADER_LINES, writer, isChartEx);
            writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.LEADER_LINES, writer, isChartEx);
        }
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.EXTENSIONS, writer, isChartEx);
    }

    /// <summary>
    /// Writes properties of the <see cref="DmlExtensionUri.DataLabels"/> extension.
    /// </summary>
    static void writeDataLabelExtensionProperties(String prefix, DmlChartDataLabelPr labelPr,
        IDmlShapeWriterContext writer) throws Exception
    {
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.LAYOUT, writer, false);

        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHAPE_PR, writer, false);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.TX_PR, writer, false);

        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.FIELD_TABLE, writer, false);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE, writer, false);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.SHOW_LEADER_LINES, writer, false);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.LEADER_LINES, writer, false);
        writeDataLabelAttr(prefix, labelPr, DmlChartDataLabelAttrs.X_FOR_SAVE, writer, false);
    }

    /// <summary>
    /// Writes the visibility element of the 2.24.3.20 CT_DataLabelVisibilities complex type [MS-ODRAWXML].
    /// </summary>
    private static void writeDataLabelVisibility(String prefix, DmlChartDataLabelPr labelPr,
        IDmlShapeWriterContext writer)
    {
        Object showSeries = labelPr.getDirectProperty(DmlChartDataLabelAttrs.SHOW_SER_NAME);
        Object showCategory = labelPr.getDirectProperty(DmlChartDataLabelAttrs.SHOW_CAT_NAME);
        Object showValue = labelPr.getDirectProperty(DmlChartDataLabelAttrs.SHOW_VAL);

        if (showSeries != null || showCategory != null || showValue != null)
        {
            writer.getBuilder().writeElementWithAttributes(msString.format("{0}:visibility", prefix),
                "seriesName", showSeries, "categoryName", showCategory, "value", showValue);
        }
    }

    private static void writeDataLabelAttr(String prefix, DmlChartDataLabelPr labelPr, /*DmlChartDataLabelAttrs*/int attr,
        IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        Object value = labelPr.getDirectProperty(attr);
        if (value == null)
            return;

        switch (attr)
        {
            case DmlChartDataLabelAttrs.DELETE:
                builder.writeElementWithAttributes(msString.format("{0}:delete", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.D_LBL_POS:
                builder.writeElementWithAttributes(msString.format("{0}:dLblPos", prefix), "val",
                    DmlChartsEnum.dataLabelPositionToDml((/*ChartDataLabelPosition*/Integer)value));
                break;
            case DmlChartDataLabelAttrs.IDX:
                builder.writeElementWithAttributes(msString.format("{0}:idx", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.LAYOUT:
                writeManualLayout(prefix, (DmlChartManualLayout)value, writer, isChartEx);
                break;
            case DmlChartDataLabelAttrs.NUM_FMT:
                writeNumberFormat((DmlChartNumFormat)value, builder, isChartEx);
                break;
            case DmlChartDataLabelAttrs.SEPARATOR:
                builder.writeElement(msString.format("{0}:separator", prefix), (String)value);
                break;
            case DmlChartDataLabelAttrs.SHOW_BUBBLE_SIZE:
                builder.writeElementWithAttributes(msString.format("{0}:showBubbleSize", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_CAT_NAME:
                builder.writeElementWithAttributes(msString.format("{0}:showCatName", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_LEGEND_KEY:
                builder.writeElementWithAttributes(msString.format("{0}:showLegendKey", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_PERCENT:
                builder.writeElementWithAttributes(msString.format("{0}:showPercent", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_SER_NAME:
                builder.writeElementWithAttributes(msString.format("{0}:showSerName", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_VAL:
                builder.writeElementWithAttributes(msString.format("{0}:showVal", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_LEADER_LINES:
                builder.writeElementWithAttributes(msString.format("{0}:showLeaderLines", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SHOW_DATA_LABELS_RANGE:
                builder.writeElementWithAttributes(msString.format("{0}:showDataLabelsRange", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.SP_PR:
                writeShapeProperties((DmlChartSpPr)value, writer, isChartEx, prefix);
                break;
            case DmlChartDataLabelAttrs.SHAPE_PR:
                DmlShapePropertiesWriter.write(prefix, (DmlChartShapeProperties)value, writer);
                break;
            case DmlChartDataLabelAttrs.TX:
                writeTx((DmlChartTx)value, writer, isChartEx);
                break;
            case DmlChartDataLabelAttrs.TX_PR:
                writeTextProperties((DmlChartTxPr)value, writer, isChartEx);
                break;
            case DmlChartDataLabelAttrs.LEADER_LINES:
                writeWrappedSpPr(msString.format("{0}:leaderLines", prefix), (DmlChartSpPr)value, writer, false,
                    isChartEx);
                break;
            case DmlChartDataLabelAttrs.X_FOR_SAVE:
                builder.writeElementWithAttributes(msString.format("{0}:xForSave", prefix), "val", value);
                break;
            case DmlChartDataLabelAttrs.FIELD_TABLE:
            {
                // The property value is currently represented as list of raw-XML entries.
                ArrayList<String> entries = (ArrayList<String>)value;
                writeDataLabelFieldTable(prefix, entries, writer.getBuilder());
                break;
            }
            case DmlChartDataLabelAttrs.EXTENSIONS:
                DmlExtensionListWriter.write(getTagPrefix(isChartEx), (StringToObjDictionary<DmlExtension>)value, writer);
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Writes the [MS-ODRAWXML] 2.6.1.4 dlblFieldTable element.
    /// </summary>
    private static void writeDataLabelFieldTable(String prefix, ArrayList<String> entries, NrxXmlBuilder builder)
    {
        if ((entries == null) || (entries.size() == 0))
            return;

        String elementName = msString.format("{0}:dlblFieldTable", prefix);
        builder.startElement(elementName);

        // The entries are currently represented as raw-XML strings.
        for (String entry : entries)
            builder.writeRaw(entry);

        builder.endElement(elementName);
    }

    private static void writeDataPointAttr(DmlChartDataPointPr pr, /*DmlChartDataPointAttr*/int attr,
        IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        Object val = pr.getDirectProperty(attr);
        if (val == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        switch (attr)
        {
            case DmlChartDataPointAttr.INDEX:
                builder.writeElementWithAttributes(getTagName("idx", isChartEx), "val", val);
                break;
            case DmlChartDataPointAttr.EXPLOSION:
                builder.writeElementWithAttributes(getTagName("explosion", isChartEx), "val", val);
                break;
            case DmlChartDataPointAttr.INVERT_IF_NEGATIVE:
                builder.writeElementWithAttributes(getTagName("invertIfNegative", isChartEx), "val", val);
                break;
            case DmlChartDataPointAttr.BUBBLE_3_D:
                builder.writeElementWithAttributes(getTagName("bubble3D", isChartEx), "val", val);
                break;
            case DmlChartDataPointAttr.MARKER:
                writeMarker((ChartMarker)val, writer, isChartEx);
                break;
            case DmlChartDataPointAttr.SP_PR:
                writeShapeProperties((DmlChartSpPr)val, writer, isChartEx);
                break;
            case DmlChartDataPointAttr.PICTURE_OPTIONS:
                writePictureOptions((DmlChartPictureOptions)val, builder, isChartEx);
                break;
            case DmlChartDataPointAttr.EXTENSIONS:
                DmlExtensionListWriter.write(getTagPrefix(isChartEx), (StringToObjDictionary<DmlExtension>)val, writer);
                break;
            default:
                break;
        }
    }

    private static void writeMarkerAttr(DmlChartMarkerPr pr, /*DmlChartMarkerAttr*/int attr, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        Object val = pr.getDirectProperty(attr);
        if (val == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        switch (attr)
        {
            case DmlChartMarkerAttr.SYMBOL:
                builder.writeElementWithAttributes(getTagName("symbol", isChartEx), "val",
                    DmlChartsEnum.markerStyleToDml((/*MarkerSymbol*/Integer)val));
                break;
            case DmlChartMarkerAttr.SIZE:
                builder.writeElementWithAttributes(getTagName("size", isChartEx), "val", val);
                break;
            case DmlChartMarkerAttr.SP_PR:
                writeShapeProperties((DmlChartSpPr)val, writer, isChartEx);
                break;
            case DmlChartMarkerAttr.EXTENSIONS:
                DmlExtensionListWriter.write(getTagPrefix(isChartEx), (StringToObjDictionary<DmlExtension>)val, writer);
                break;
            default:
                break;
        }
    }

    private static void writeTrendlineArrt(DmlChartTrendlinePr pr, /*DmlChartTrendlineAttr*/int attr,
        IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        Object val = pr.getDirectProperty(attr);
        if (val == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        switch (attr)
        {
            case DmlChartTrendlineAttr.BACKWARD:
                builder.writeElementWithAttributes(getTagName("backward", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.DISP_EQ:
                builder.writeElementWithAttributes(getTagName("dispEq", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.DISP_R_SQR:
                builder.writeElementWithAttributes(getTagName("dispRSqr", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.FORWARD:
                builder.writeElementWithAttributes(getTagName("forward", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.INTERCEPT:
                builder.writeElementWithAttributes(getTagName("intercept", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.NAME:
                builder.writeElement(getTagName("name", isChartEx), (String)val);
                break;
            case DmlChartTrendlineAttr.ORDER:
                builder.writeElementWithAttributes(getTagName("order", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.PERIOD:
                builder.writeElementWithAttributes(getTagName("period", isChartEx), "val", val);
                break;
            case DmlChartTrendlineAttr.SP_PR:
                writeShapeProperties((DmlChartSpPr)val, writer, isChartEx);
                break;
            case DmlChartTrendlineAttr.TRENDLINE_LBL:
                writeTrendlineLbl((DmlChartTrendlineLabel)val, writer, isChartEx);
                break;
            case DmlChartTrendlineAttr.TRENDLINE_TYPE:
                builder.writeElementWithAttributes(getTagName("trendlineType", isChartEx), "val",
                    DmlChartsEnum.trendlineTypeToDml((/*TrendlineType*/Integer)val));
                break;
            case DmlChartTrendlineAttr.EXTENSIONS:
                DmlExtensionListWriter.write(getTagPrefix(isChartEx), (StringToObjDictionary<DmlExtension>)val, writer);
                break;
            default:
                break;
        }
    }

    private static void writeTrendlineLbl(DmlChartTrendlineLabel lbl, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        if (lbl == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(getTagName("trendlineLbl", isChartEx));
        writeManualLayout(getTagPrefix(isChartEx), lbl.getLayout(), writer, isChartEx);
        writeNumberFormat(lbl.getNumFmt(), builder, isChartEx);
        writeShapeProperties(lbl.getSpPr(), writer, isChartEx);
        writeTx(lbl.getTx(), writer, isChartEx);
        writeTextProperties(lbl.getTxPr(), writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), lbl.getExtensions(), writer);
        builder.endElement(getTagName("trendlineLbl", isChartEx));
    }

    private static void writeDoubleIfNotDefault(String tagName, double value, double defaultValue,
        NrxXmlBuilder builder)
    {
        if (!MathUtil.areEqual(value, defaultValue))
            builder.writeElementWithAttributes(tagName, "val", value);
    }

    private static void writeDisplayUnitsLabel(DmlChartTitle dispUnitsLbl, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        if (dispUnitsLbl == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        String elementName = isChartEx ? "unitsLabel" : "dispUnitsLbl";

        builder.startElement(getTagName(elementName, isChartEx));

        if (!isChartEx)
            writeManualLayout(getTagPrefix(isChartEx), dispUnitsLbl.getLayout(), writer, isChartEx);

        writeShapeProperties(dispUnitsLbl.getSpPr(), writer, isChartEx);
        writeTextProperties(dispUnitsLbl.getTxPr(), writer, isChartEx);
        writeTx(dispUnitsLbl.getTx(), writer, isChartEx);

        DmlExtensionListWriter.write(getTagPrefix(isChartEx),
            ((IDmlExtensionListSource)dispUnitsLbl).getExtensions(), writer);

        builder.endElement(getTagName(elementName, isChartEx));
    }

    /// <summary>
    /// Writes the data element of the 2.24.3.15 CT_Data complex type [MS-ODRAWXML].
    /// The element specifies the number dimension and string dimension for the specified chart data.
    /// </summary>
    static void writeData(DmlChartData data, IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("data", isChartEx));
        builder.writeAttribute("id", data.getId());

        for (DmlChartDataSource dataSource : data.getDataSources())
            writeValueRef(dataSource.getValueRef(), writer, true);

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), data.getExtensions(), writer);

        builder.endElement(getTagName("data", isChartEx));
    }

    /// <summary>
    /// Writes the fmtOvr element of the 2.24.3.26 CT_FormatOverride complex type [MS-ODRAWXML].
    /// </summary>
    static void writeFormatOverride(DmlChartFormatOverride formatOverride, IDmlShapeWriterContext writer,
        boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("fmtOvr", isChartEx));
        builder.writeAttribute("idx", formatOverride.getIndex());

        if (formatOverride.getSpPr() != null)
            writeShapeProperties(formatOverride.getSpPr(), writer, isChartEx);

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), formatOverride.getExtensions(), writer);

        builder.endElement(getTagName("fmtOvr", isChartEx));
    }

    /// <summary>
    /// Writes an element of the CT_TickMark complex type [ISO/IEC 29500] or the 2.24.3.81 CT_TickMarks
    /// complex type [MS-ODRAWXML].
    /// </summary>
    static void writeTickMarks(String tagName, /*AxisTickMark*/int tickMark,
        StringToObjDictionary<DmlExtension> extensions,
        IDmlShapeWriterContext writer, boolean isChartEx) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName(tagName, isChartEx));

        builder.writeAttribute(isChartEx ? "type" : "val", DmlChartsEnum.tickMarkToDml(tickMark));
        if (isChartEx)
            DmlExtensionListWriter.write(getTagPrefix(isChartEx), extensions, writer);

        builder.endElement(getTagName(tagName, isChartEx));
    }

    /// <summary>
    /// Writes the valueColors element of the 2.24.3.86 CT_ValueColors complex type [MS-ODRAWXML].
    /// </summary>
    static void writeValueColors(DmlChartValueColors colors, IDmlShapeWriterContext writer)
    {
        if ((colors == null) || colors.isEmpty())
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("valueColors", true));

        writeValueColor("minColor", colors.getMinimumColor(), writer);
        writeValueColor("midColor", colors.getMiddleColor(), writer);
        writeValueColor("maxColor", colors.getMaximumColor(), writer);

        builder.endElement(getTagName("valueColors", true));
    }

    /// <summary>
    /// Writes a sub-element of the valueColors element of the 2.24.3.86 CT_ValueColors complex type [MS-ODRAWXML].
    /// </summary>
    static void writeValueColor(String tagName, DmlColor color, IDmlShapeWriterContext writer)
    {
        if (color == null)
            return;

        writer.getBuilder().startElement(getTagName(tagName, true));
        DmlColorWriter.write(color, writer);
        writer.getBuilder().endElement(getTagName(tagName, true));
    }

    /// <summary>
    /// Writes the layoutPr element of the 2.24.3.72 CT_SeriesLayoutProperties complex type [MS-ODRAWXML].
    /// </summary>
    static void writeLayoutProperties(DmlChartSeriesLayoutPr layoutPr, IDmlShapeWriterContext writer) throws Exception
    {
        if (layoutPr.getCount() == 0)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("layoutPr", true));

        Object value = layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.PARENT_LABEL_LAYOUT);
        if (value != null)
        {
            builder.writeElementWithAttributes(getTagName("parentLabelLayout", true),
                "val", DmlChartsEnum.parentLabelLayoutToDml((/*ParentLabelLayout*/Integer)value));
        }
        value = layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.REGION_LABEL_LAYOUT);
        if (value != null)
        {
            builder.writeElementWithAttributes(getTagName("regionLabelLayout", true),
                "val", DmlChartsEnum.regionLabelLayoutToDml((/*RegionLabelLayout*/Integer)value));
        }
        builder.writeElementWithAttributes(getTagName("visibility", true),
            "connectorLines", layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_CONNECTOR_LINES_VISIBLE),
            "meanLine", layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_MEAN_LINE_VISIBLE),
            "meanMarker", layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_MEAN_MARKER_VISIBLE),
            "nonoutliers", layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_NON_OUTLIERS_VISIBLE),
            "outliers", layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_OUTLIERS_VISIBLE));
        value = layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.IS_AGGREGATION);
        if (value != null)
            builder.writeEmptyElement(getTagName("aggregation", true));
        writeBinning((DmlChartBinningPr)layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.BINNING), writer);
        value = layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.QUARTILE_METHOD);
        if (value != null)
        {
            builder.writeElementWithAttributes(getTagName("statistics", true),
                "quartileMethod", DmlChartsEnum.quartileMethodToDml((/*QuartileMethod*/Integer)value));
        }
        writeSubtotals((ArrayList<Integer>)layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.SUBTOTALS), builder);
        DmlExtensionListWriter.write(getTagPrefix(true),
            (StringToObjDictionary<DmlExtension>)layoutPr.getDirectProperty(DmlChartSeriesLayoutAttr.EXTENSIONS), writer);

        builder.endElement(getTagName("layoutPr", true));
    }

    /// <summary>
    /// Writes the binning element of the 2.24.3.7 CT_Binning complex type [MS-ODRAWXML].
    /// </summary>
    private static void writeBinning(DmlChartBinningPr binningPr, IDmlShapeWriterContext writer)
    {
        if ((binningPr == null) || (binningPr.getCount() == 0))
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(getTagName("binning", true));

        Object value = binningPr.getDirectProperty(DmlChartBinningAttr.INTERVAL_CLOSED);
        if (value != null)
        {
            builder.writeAttribute("intervalClosed",
                DmlChartsEnum.intervalClosedSideToDml((/*IntervalClosedSide*/Integer)value));
        }
        writeDoubleOrAutomaticAttribute("underflow",
            (DoubleOrAutomatic)binningPr.getDirectProperty(DmlChartBinningAttr.UNDERFLOW), builder);
        writeDoubleOrAutomaticAttribute("overflow",
            (DoubleOrAutomatic)binningPr.getDirectProperty(DmlChartBinningAttr.OVERFLOW), builder);

        value = binningPr.getDirectProperty(DmlChartBinningAttr.BIN_SIZE);
        if (value != null)
        {
            builder.writeElementWithAttributes(getTagName("binSize", true), "val", value);
        }
        else
        {
            value = binningPr.getDirectProperty(DmlChartBinningAttr.BIN_COUNT);
            if (value != null)
                builder.writeElementWithAttributes(getTagName("binCount", true), "val", value);
        }

        builder.endElement(getTagName("binning", true));
    }

    /// <summary>
    /// Writes attribute of the ST_DoubleOrAutomatic, ST_ValueAxisUnit, ST_GapWidthRatio simple types [MS-ODRAWXML].
    /// It may have either numeric value or the enumeration value 'auto'.
    /// </summary>
    static void writeDoubleOrAutomaticAttribute(String name, DoubleOrAutomatic value, NrxXmlBuilder builder)
    {
        if (value != null && !value.isNull())
        {
            if (value.isAuto())
                builder.writeAttribute(name, "auto");
            else
                builder.writeAttribute(name, value.getValue());
        }
    }

    /// <summary>
    /// Writes axis bound value.
    /// </summary>
    static void writeAxisBound(String name, AxisBound value, NrxXmlBuilder builder)
    {
        if (value.isAuto())
            builder.writeAttribute(name, "auto");
        else
            builder.writeAttribute(name, value.getValue());
    }

    /// <summary>
    /// Writes the subtotals element of the 2.24.3.77 CT_Subtotals complex type [MS-ODRAWXML].
    /// </summary>
    private static void writeSubtotals(ArrayList<Integer> subtotals, NrxXmlBuilder builder)
    {
        if ((subtotals == null) || (subtotals.size() == 0))
            return;

        builder.startElement(getTagName("subtotals", true));

        for (int index : subtotals)
            builder.writeElementWithAttributes(getTagName("idx", true), "val", index);

        builder.endElement(getTagName("subtotals", true));
    }

    /// <summary>
    /// Generates a tag name with a prefix depending on chart type.
    /// </summary>
    /// <param name="localName">Name without prefix.</param>
    /// <param name="isChartEx">A flag indicating that the chart is of the chartEx schema.</param>
    /// <returns>Tag name with prefix.</returns>
    static String getTagName(String localName, boolean isChartEx)
    {
        return getTagName(getTagPrefix(isChartEx), localName);
    }

    /// <summary>
    /// Generates a tag name with a specified prefix.
    /// </summary>
    /// <param name="prefix">A prefix that will be used for the tag.</param>
    /// <param name="localName">Name without prefix.</param>
    /// <returns>Tag name with prefix.</returns>
    static String getTagName(String prefix,  String localName)
    {
        return msString.format("{0}:{1}", prefix, localName);
    }

    /// <summary>
    /// Gets a tag prefix depending on chart type.
    /// </summary>
    static String getTagPrefix(boolean isChartEx)
    {
        return isChartEx ? "cx" : "c";
    }

    private static final String OVERLAY = "overlay";
}

