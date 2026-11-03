// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/21/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.OpcPackagePart;
import com.aspose.StreamUtil;
import com.aspose.FileSystem;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Drawing.msPointF;
import com.aspose.words.ref.Ref;


/// <summary>
/// Instance class for writing DrawingML chart spaces.
/// </summary>
/*static*/ class DmlChartSpaceWriter
{
	/* Simulation of static class by using private constructor */
	private DmlChartSpaceWriter()
	{}

    static void write(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlChartSpace chartSpace = ms.as(dml.getDmlNode(), DmlChartSpace.class);
        if (chartSpace == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        boolean isChartEx = chartSpace.isChartEx();

        builder.startDocument(getTagName("chartSpace", isChartEx));
        builder.writeAttribute("xmlns:" + getTagPrefix(isChartEx), DocxNamespaces.getNamespace(
            chartSpace.isChartEx() ? DocxNamespace.DRAWING_ML_CHART_EX : DocxNamespace.DRAWING_ML_CHART, isIsoStrict));
        builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));
        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));

        if (isChartEx)
        {
            writeChartData(chartSpace, writer);
        }
        else
        {
            builder.writeElementWithAttributes(getTagName("date1904", isChartEx), "val", chartSpace.getDate1904());
            builder.writeElementWithAttributes(getTagName("lang", isChartEx), "val", chartSpace.getLang());
            builder.writeElementWithAttributes(getTagName("roundedCorners", isChartEx), "val", chartSpace.getRoundedCorners());
            writeStyle(chartSpace, builder);
            writeColormapOverride(chartSpace.getColorMapOverride(), writer, isChartEx);
            DmlChartCommonWriter.writePivotSource(chartSpace.getPivotSource(), writer, isChartEx);
            DmlChartCommonWriter.writeProtection(chartSpace.getProtection(), builder, isChartEx);
        }
        
        writeChart(chartSpace.getChartFormat(), writer, isChartEx);
        DmlChartCommonWriter.writeShapeProperties(chartSpace.getSpPr(), writer, isChartEx);
        DmlChartCommonWriter.writeTextProperties(chartSpace.getTxPr(), writer, isChartEx);

        if (isChartEx)
        {
            writeColormapOverride(chartSpace.getColorMapOverride(), writer, isChartEx);
            writeFormatOverrides(chartSpace, writer);
        }
        else
        {
            writeExternalData(chartSpace, writer);
        }

        writeUserShapes(dml, writer, isChartEx);
        writeThemeOverride(chartSpace.getThemeOverride(), writer);
        writeColorStyle(chartSpace.getColorStyle(), writer);
        writeStyle(chartSpace.getDmlChartStyle(), writer);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), chartSpace.getExtensions(), writer);

        builder.endDocument();
    }

    private static void writeStyle(DmlChartStyle style, DocxDocumentWriterBase writer) throws Exception
    {
        if (style == null)
            return;

        String contentType = DocxContentType.CHART_STYLE;
        String relType = DocxRelationshipTypes.getType(DocxRelationshipType.CHART_STYLE, 
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String partName = msString.format("chart/style{0}.xml", writer.getNextEmbeddedPartNumber(relType));
        String dummyRelId = null;
        Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
        DocxBuilder builder = writer.createChildPartAndBuilder(writer.getCurrentBuilder().getPart(), partName, contentType,
            relType, /*out*/ referenceToDummyRelId);
        dummyRelId = referenceToDummyRelId.get();

        writer.pushBuilder(builder);
        DmlChartStyleWriter.write(style, writer);
        writer.popBuilder();
    }

    private static void writeColorStyle(DmlChartColorStyle colorStyle, DocxDocumentWriterBase writer) throws Exception
    {
        if (colorStyle == null)
            return;

        String contentType = DocxContentType.CHART_COLOR_STYLE;
        String relType = DocxRelationshipTypes.getType(DocxRelationshipType.CHART_COLOR_STYLE,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String partName = msString.format("chart/colors{0}.xml", writer.getNextEmbeddedPartNumber(relType));
        String dummyRelId = null;
        Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
        DocxBuilder builder = writer.createChildPartAndBuilder(writer.getCurrentBuilder().getPart(), partName, contentType,
            relType, /*out*/ referenceToDummyRelId);
        dummyRelId = referenceToDummyRelId.get();

        writer.pushBuilder(builder);
        DmlChartColorStyleWriter.write(colorStyle, writer);
        writer.popBuilder();
    }

    private static void writeStyle(DmlChartSpace chartSpace, DocxBuilder builder)
    {
        boolean isChartEx = chartSpace.isChartEx(); 
        if (chartSpace.getWord2010Style() < 0)
        {
            // 2 is default style, MS Word does not write it.
            if (chartSpace.getStyleIndex() != 2)
                builder.writeElementWithAttributes(getTagName("style", isChartEx), "val", chartSpace.getStyleIndex());
        }
        else
        {
            boolean isIsoStrict = builder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT;

            // In MS Word 2013 AlternateContent is written for style.
            builder.startElement("mc:AlternateContent");
            builder.writeAttribute("xmlns:mc", 
                DocxNamespaces.getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, isIsoStrict));

            builder.startElement("mc:Choice");
            builder.writeAttribute("Requires", "c14");
            builder.writeAttribute("xmlns:c14", 
                DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_2007, isIsoStrict));
            builder.writeElementWithAttributes("c14:style", "val", chartSpace.getWord2010Style());
            builder.endElement("mc:Choice");

            builder.startElement("mc:Fallback");
            builder.writeElementWithAttributes(getTagName("style", isChartEx), "val", chartSpace.getStyleIndex());
            builder.endElement("mc:Fallback");

            builder.endElement("mc:AlternateContent");
        }
    }

    private static void writeColormapOverride(DmlChartColorMapOverride colorMapOverride, 
        DocxDocumentWriterBase writer, boolean isChartEx)
    {
        if(colorMapOverride == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        builder.writeElementWithAttributes(getTagName("clrMapOvr", isChartEx),
            "bg1", colorMapOverride.Bg1,
            "tx1", colorMapOverride.Tx1,
            "bg2", colorMapOverride.Bg2,
            "tx2", colorMapOverride.Tx2,
            "accent1", colorMapOverride.Accent1,
            "accent2", colorMapOverride.Accent2,
            "accent3", colorMapOverride.Accent3,
            "accent4", colorMapOverride.Accent4,
            "accent5", colorMapOverride.Accent5,
            "accent6", colorMapOverride.Accent6,
            "hlink", colorMapOverride.Hlink,
            "folHlink", colorMapOverride.FolHlink);
    }

    private static void writeThemeOverride(Theme themeOverride, DocxDocumentWriterBase writer) throws Exception
    {
        if (themeOverride == null)
            return;

        DocxDocumentWriter.writeTheme(themeOverride, writer, true);
    }

    private static void writeChart(DmlChartFormat chartFormat, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("chart", isChartEx));
        DmlChartCommonWriter.writeTitle(chartFormat.getDCTitle(), writer, chartFormat.getAutoTitleDeleted(), isChartEx, false);
        if (!isChartEx)
        {
            builder.writeElementWithAttributes(getTagName("autoTitleDeleted", isChartEx), "val", chartFormat.getAutoTitleDeleted());
            DmlChartCommonWriter.writePivotFmts(chartFormat.getPivotFmts(), writer, isChartEx);
            DmlChartCommonWriter.writeView3D(chartFormat, writer, isChartEx);
            DmlChartCommonWriter.writeSurface(getTagName("floor", isChartEx), chartFormat.getFloor(), writer, isChartEx);
            DmlChartCommonWriter.writeSurface(getTagName("sideWall", isChartEx), chartFormat.getSideWall(), writer, isChartEx);
            DmlChartCommonWriter.writeSurface(getTagName("backWall", isChartEx), chartFormat.getBackWall(), writer, isChartEx);
        }

        writePlotArea(chartFormat.getPlotArea(), writer, isChartEx);
        DmlChartCommonWriter.writeLegend(chartFormat.getLegend(), writer, isChartEx);

        if (!isChartEx)
        {
            DmlChartCommonWriter.writeWrappedBooleanIfTrue(getTagName("plotVisOnly", isChartEx), chartFormat.getPlotVisOnly(), builder);
            builder.writeElementWithAttributes(getTagName("dispBlanksAs", isChartEx), "val", DmlChartsEnum.displayBlanksAsToDml(chartFormat.getDispBlanksAs()));
            builder.writeElementWithAttributes(getTagName("showDLblsOverMax", isChartEx), "val", chartFormat.getShowDLblsOverMax());
        }

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), chartFormat.getExtensions(), writer);
        
        builder.endElement(getTagName("chart", isChartEx));
    }

    private static void writePlotArea(DmlChartPlotArea plotArea, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("plotArea", isChartEx));
        if (!isChartEx)
        {
            DmlChartCommonWriter.writeManualLayout(getTagPrefix(isChartEx), plotArea.getLayout(), writer, isChartEx);

            for (DmlChart chart : plotArea.getCharts())
                DmlChartObjectWriter.write(chart, writer, isChartEx);
        }
        else
        {
            writePlotAreaRegion(plotArea, writer);
        }

        // It looks like MS Word cannot open a document with a non-combo pre-Word 2016 chart that has secondary axes:
        // do not write them.
        boolean removeUnusedAxes = !isChartEx && (plotArea.getCharts().size() == 1);

        for (ChartAxis axis : plotArea.getAxes())
        {
            if (removeUnusedAxes && !isAxisUsed(axis, plotArea.getCharts()))
                continue;

            if (isChartEx)
                DmlChartAxisWriter.writeChartExAxis(axis, writer);
            else
                DmlChartAxisWriter.write(axis, writer);
        }

        if (!isChartEx)
            writeDataTable(plotArea.getDataTable(), writer, isChartEx);

        DmlChartCommonWriter.writeShapeProperties(plotArea.getSpPr(), writer, isChartEx);

        DmlExtensionListWriter.write(getTagPrefix(isChartEx), plotArea.getExtensions(), writer);

        builder.endElement(getTagName("plotArea", isChartEx));
    }

    /// <summary>
    /// Returns a flag indicating whether the specified axis is used by DML charts of the chart being written.
    /// </summary>
    private static boolean isAxisUsed(ChartAxis axis, ArrayList<DmlChart> charts)
    {
        for (DmlChart chart : charts)
        {
            IDmlChart2D chart2D = ms.as(chart, IDmlChart2D.class);
            if ((chart2D != null) && ((chart2D.getAxIdX() == axis.getAxId()) || (chart2D.getAxIdY() == axis.getAxId())))
                return true;

            IDmlChart3D chart3D = ms.as(chart, IDmlChart3D.class);
            if ((chart3D != null) && (chart3D.getAxIdZ() == axis.getAxId()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Writes the plotAreaRegion of 2.24.3.65 CT_PlotAreaRegion complex type [MS-ODRAWXML].
    /// </summary>
    private static void writePlotAreaRegion(DmlChartPlotArea plotArea, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("plotAreaRegion", true));

        DmlChartCommonWriter.writeSurface(getTagName("plotSurface", true), plotArea.getSurface(), writer, true);

        // plotArea.FirstChart: plot area of a chartEx chart may contain only one chart.
        DmlChartSeriesWriter.write(plotArea.getFirstChart(), writer); 

        DmlExtensionListWriter.write(getTagPrefix(true), plotArea.getRegionExtensions(), writer);

        builder.endElement(getTagName("plotAreaRegion", true));
    }

    private static void writeDataTable(ChartDataTable dt, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        if ((dt == null) || !dt.getShow())
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(getTagName("dTable", isChartEx));
        builder.writeElementWithAttributes(getTagName("showHorzBorder", isChartEx), "val", dt.hasHorizontalBorder());
        builder.writeElementWithAttributes(getTagName("showVertBorder", isChartEx), "val", dt.hasVerticalBorder());
        builder.writeElementWithAttributes(getTagName("showOutline", isChartEx), "val", dt.hasOutlineBorder());
        builder.writeElementWithAttributes(getTagName("showKeys", isChartEx), "val", dt.hasLegendKeys());
        DmlChartCommonWriter.writeShapeProperties(dt.getSpPr(), writer, isChartEx);
        DmlChartCommonWriter.writeTextProperties(dt.getTxPr(), writer, isChartEx);

        builder.endElement(getTagName("dTable", isChartEx));
    }

    private static void writeExternalData(DmlChartSpace chartSpace, DocxDocumentWriterBase writer) throws Exception
    {
        // For now write only Ooxml embedded objects here. I never saw other types of data for charts.
        // So I think this is acceptable.
        String relId = null;
        boolean isChartEx = chartSpace.isChartEx();
        DocxBuilder builder = writer.getCurrentBuilder();

        if (chartSpace.getEmbeddedData() != null)
        {
            OoxmlObject ooxmlObject = ms.as(chartSpace.getEmbeddedData(), OoxmlObject.class);
            boolean isOoxmlObject = (ooxmlObject != null);

            String contentType = isOoxmlObject ? ooxmlObject.getContentType() : DocxContentType.OLE_OBJECT;
            String relType = isOoxmlObject ? writer.getRelTypes().getPackage() : writer.getRelTypes().getOleObject();              
 
            String partNameFormat = isOoxmlObject 
                ? "/word/embeddings/Microsoft_Excel_Worksheet{0}{1}" 
                : "/word/embeddings/OleObject{0}{1}";

            // WORDSNET-14967 Preserve extension of the embedded object while re-saving the document.
            String extension = DocxEnum.contentTypeToExtension(contentType);

            // Sometimes content type for embedded data is octet stream, but MS Word still writes xlsx extension.
            if (DocxContentType.OCTET_STREAM.equals(contentType))
                extension = DocxEnum.contentTypeToExtension(DocxContentType.XLSX);

            String partName = msString.format(partNameFormat, writer.getNextEmbeddedPartNumber(relType), extension);                
            Ref<String> referenceToRelId = new Ref<String>(relId);
            OpcPackagePart part = writer.getPackage().createChildPart(builder.getPart(), partName, contentType, relType, /*out*/ referenceToRelId);
            relId = referenceToRelId.get();

            if (isOoxmlObject)
            {
                ooxmlObject.getStream().setPosition(0);
                StreamUtil.copyStream(ooxmlObject.getStream(), part.getStream());
            }
            else
            {
                FileSystem fs = new FileSystem(((OleObject)chartSpace.getEmbeddedData()).getData());
                fs.save(part.getStream());
            }
        }
        else if (StringUtil.hasChars(chartSpace.getLinkedData()))
        {
            relId = writer.addExternalOleObjectRelationship(chartSpace.getLinkedData());
        }

        if (StringUtil.hasChars(relId))
        {
            builder.startElement(getTagName("externalData", isChartEx));
            // MS Word writes the id attribute with the Transitional OOXML relationships namespace on saving
            // a chartEx chart to Strict OOXML: let's do the same.
            if ((writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT) && isChartEx)
            {
                builder.writeAttribute("xmlns:p5", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, false));
                builder.writeAttribute("p5:id", relId);
            }
            else
            {
                builder.writeAttribute("r:id", relId);
            }
            if (!chartSpace.getAutoUpdate())
            {
                if (isChartEx)
                    builder.writeAttribute(getTagName("autoUpdate", true), chartSpace.getAutoUpdate());
                else
                    builder.writeElementWithAttributes(getTagName("autoUpdate", false), "val", chartSpace.getAutoUpdate());
            }
            builder.endElement(getTagName("externalData", isChartEx));
        }
    }

    private static void writeUserShapes(ShapeBase dml, DocxDocumentWriterBase writer, boolean isChartEx) throws Exception
    {
        if (dml.getChildNodes(NodeType.ANY, false).getCount() == 0)
            return;

        String chartDrawingRelType = DocxRelationshipTypes.getType(DocxRelationshipType.CHART_DRAWING,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        int chartDrawingPartNumber = writer.getNextEmbeddedPartNumber(chartDrawingRelType);
        String chartDrawingPartName = msString.format("/word/drawings/drawing{0}.xml", chartDrawingPartNumber);

        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        DocxBuilder userShapesBuilder = writer.createChildPartAndBuilder(
            writer.getCurrentBuilder().getPart(),
            chartDrawingPartName, 
            DocxContentType.CHART_DRAWING, 
            chartDrawingRelType, 
            /*out*/ referenceToRelId);
        relId = referenceToRelId.get();

        writer.getCurrentBuilder().writeElementWithAttributes(getTagName("userShapes", isChartEx), "r:id", relId);
        
        writer.pushBuilder(userShapesBuilder);
        writeUserShapesCore(dml, writer);
        writer.popBuilder();
    }

    private static void writeUserShapesCore(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("c:userShapes");
        builder.writeAttribute("xmlns:c", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART, isIsoStrict));
        builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));
        for (ShapeBase shape : (Iterable<ShapeBase>) dml.getChildNodes(NodeType.ANY, false))
            writeUserShapeRelative(shape, writer);

        // Here we must actually write user shapes.
        builder.endDocument();
    }

    private static void writeUserShapeRelative(ShapeBase shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        String tagName = (shape.getAnchorType() == DmlChartUserShapeAnchorType.ABSOLUTE)
            ? "cdr:absSizeAnchor"
            : "cdr:relSizeAnchor";

        builder.startElement(tagName);
        builder.writeAttribute("xmlns:cdr", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_CHART_DRAWING, isIsoStrict));

        writePoint("cdr:from", shape.getFrom(), builder);
        writePoint("cdr:to", shape.getTo(), builder);

        writeShapeCore(shape, writer);

        builder.endElement(tagName);
    }

    private static void writeShapeCore(ShapeBase shape, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode dmlNode = shape.getDmlNode();
        switch (dmlNode.getDmlNodeType())
        {
            case DmlNodeType.CONNECTOR_SHAPE:
                writeConnectorShape((DmlShape)dmlNode, writer);
                break;
            case DmlNodeType.SHAPE:
                writeShape((DmlShape)dmlNode, writer);
                break;
            case DmlNodeType.PICTURE:
                writePictureShape((DmlPicture)dmlNode, writer);
                break;
            case DmlNodeType.GROUP_SHAPE:
                writeGroupShape(shape, writer);
                break;
            default:
                break;
        }
    }

    private static void writeConnectorShape(DmlShape cxnShape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cdr:cxnSp");
        DmlNonVisualPropertiesWriter.writeNvPr("cdr", cxnShape, writer);
        DmlShapePropertiesWriter.write("cdr", cxnShape, writer);
        DmlShapeStyleWriter.write("cdr", cxnShape.getStyle(), writer);
        builder.endElement("cdr:cxnSp");
    }

    private static void writeShape(DmlShape shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cdr:sp");
        DmlNonVisualPropertiesWriter.writeNvPr("cdr", shape, writer);
        DmlShapePropertiesWriter.write("cdr", shape, writer);
        DmlShapeStyleWriter.write("cdr", shape.getStyle(), writer);

        if (shape.getTextShape() != null)
            DmlTextShapeWriter.writeDmlShapeTextBody("cdr:txBody", shape.getTextShape().getTextBody(), writer);
        
        builder.endElement("cdr:sp");
    }

    private static void writePictureShape(DmlPicture pic, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cdr:pic");
        DmlNonVisualPropertiesWriter.writeNvPr("cdr", pic, writer);
        DmlFillWriter.writeDmlBlipFill("cdr", pic.getBlipFill(), writer);
        DmlShapePropertiesWriter.write("cdr", pic, writer);
        DmlShapeStyleWriter.write("cdr", pic.getStyle(), writer);
        builder.endElement("cdr:pic");
    }

    private static void writeGroupShape(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode grpSp = dml.getDmlNode();
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("cdr:grpSp");
        DmlNonVisualPropertiesWriter.writeNvPr("cdr", grpSp, writer);
        DmlGroupShapeWriter.writeGroupShapeProperties("cdr", (DmlCompositeNode)grpSp, writer);

        for (ShapeBase node : (Iterable<ShapeBase>) dml.getChildNodes(NodeType.ANY, false))
            writeShapeCore(node, writer);
        
        builder.endElement("cdr:grpSp");
    }

    private static void writePoint(String tagName, /*PointF*/long point, DocxBuilder builder)
    {
        builder.startElement(tagName);
        builder.writeElement("cdr:x", FormatterPal.doubleToStrNDecimals(msPointF.getX(point), 5));
        builder.writeElement("cdr:y", FormatterPal.doubleToStrNDecimals(msPointF.getY(point), 5));
        builder.endElement(tagName);
    }

    /// <summary>
    /// Writes the chartData element of the 2.24.3.10 CT_ChartData complex type [MS-ODRAWXML].
    /// The element specifies the data for the chart.
    /// </summary>
    static void writeChartData(DmlChartSpace chartSpace, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isChartEx = chartSpace.isChartEx();

        builder.startElement(getTagName("chartData", isChartEx));

        writeExternalData(chartSpace, writer);
        for (DmlChartData dataItem : chartSpace.getData())
            DmlChartCommonWriter.writeData(dataItem, writer, isChartEx);
        DmlExtensionListWriter.write(getTagPrefix(isChartEx), chartSpace.getData().getExtensions(), writer);

        builder.endElement(getTagName("chartData", isChartEx));
    }

    /// <summary>
    /// Writes the fmtOvrs element of the 2.24.3.27 CT_FormatOverrides complex type [MS-ODRAWXML].
    /// </summary>
    static void writeFormatOverrides(DmlChartSpace chartSpace, DocxDocumentWriterBase writer) throws Exception
    {
        if (chartSpace.getFormatOverrides().size() == 0)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isChartEx = chartSpace.isChartEx();

        builder.startElement(getTagName("fmtOvrs", isChartEx));

        for (DmlChartFormatOverride item : chartSpace.getFormatOverrides())
            DmlChartCommonWriter.writeFormatOverride(item, writer, isChartEx);

        builder.endElement(getTagName("fmtOvrs", isChartEx));
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

