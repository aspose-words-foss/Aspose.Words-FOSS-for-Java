// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/*static*/ class DmlDiagramDataWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramDataWriter()
	{}

    static void write(DmlDiagramDataModel data, String drawingId, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("dgm:dataModel");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:a", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

        writePointList(data.getPointList(), writer);
        writeConnectionList(data.getConnectionList(), writer);
        writeBackgroundFormatting(data.getBackground(), writer);
        writeWholeFormatting(data.getWholeFormatting(), writer);

        updateDrawingRelId(data, drawingId);
        DmlExtensionListWriter.write("dgm", data.getExtensions(), writer);

        builder.endDocument();
    }

    static void writeSampleData(String rootTagName, DmlDiagramDataModel data, DocxDocumentWriterBase writer) throws Exception
    {
        // WORDSNET-17927 According to OOXML spec "sampData", "styleData", "clrData" may be absent. So, skip these items when
        // appropriate properties are null.
        if (data != null)
        {
            DocxBuilder builder = writer.getCurrentBuilder();

            builder.startElement(rootTagName);
            builder.writeAttributeIfTrue("useDef", data.getUseDefault());

            builder.startElement("dgm:dataModel");

            writePointList(data.getPointList(), writer);
            writeConnectionList(data.getConnectionList(), writer);
            writeBackgroundFormatting(data.getBackground(), writer);
            writeWholeFormatting(data.getWholeFormatting(), writer);

            builder.endElement("dgm:dataModel");

            builder.endElement(rootTagName);
        }
    }

    private static void writePointList(DmlDiagramPoint[] pointList, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:ptLst");

        for (DmlDiagramPoint point : pointList)
            writePoint(point, writer);

        builder.endElement("dgm:ptLst");
    }

    private static void writePoint(DmlDiagramPoint point, DocxDocumentWriterBase writer) throws Exception
    {
        if (point.getAlternateContentFallbackPoint() == null)
        {
            writePointCore(point, writer);
        }
        else
        {
            DocxBuilder builder = writer.getCurrentBuilder();
            builder.startElement("mc:AlternateContent");
            for (String attr : point.getAlternateContentAttributes().keySet())
                builder.writeAttribute(attr, point.getAlternateContentAttributes().get(attr));

            builder.startElement("mc:Choice");
            for (String attr : point.getAlternateContentChoiceAttributes().keySet())
                builder.writeAttribute(attr, point.getAlternateContentChoiceAttributes().get(attr));

            writePointCore(point, writer);
            builder.endElement("mc:Choice");

            builder.startElement("mc:Fallback");
            writePointCore(point.getAlternateContentFallbackPoint(), writer);
            builder.endElement("mc:Fallback");

            builder.endElement("mc:AlternateContent");
        }
    }

    private static void writePointCore(DmlDiagramPoint point, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:pt");

        builder.writeAttribute("modelId", DmlDiagramEnum.modelIdToDml(point.getModelId()));
        builder.writeAttributeIfNotDefault("type", DmlDiagramEnum.pointTypeToDml(point.getType()), "node");
        builder.writeAttributeIfNotDefault("cxnId", DmlDiagramEnum.modelIdToDml(point.getConnectionId()), "0");

        writePropertySet(point.getPropertySet(), writer);
        DmlShapePropertiesWriter.write("dgm", point.getShapeProperties(), writer);
        DmlTextShapeWriter.writeDmlShapeTextBody("dgm:t", point.getTextBody(), writer);
        DmlExtensionListWriter.write("dgm", point.getExtensions(), writer);

        builder.endElement("dgm:pt");
    }

    private static void writePropertySet(DmlPropertySet prSet, DocxDocumentWriterBase writer)
    {
        if (prSet == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:prSet");

        DmlPropertySetPr dmlPropertySetPr = prSet.getPrSet();

        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PRES_ASSOC_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PRES_NAME, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PRES_STYLE_LBL, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PRES_STYLE_IDX, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PRES_STYLE_CNT, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.LO_TYPE_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.LO_CAT_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.QS_TYPE_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.QS_CAT_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CS_TYPE_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CS_CAT_ID, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.COHERENT_3_D_OFF, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PHLDR_T, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.PHLDR, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_ANG, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_FLIP_VERT, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_FLIP_HOR, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_SZ_X, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_SZ_Y, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_SCALE_X, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_SCALE_Y, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_T, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_LIN_FACT_X, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_LIN_FACT_Y, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_X, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_Y, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_RAD_SCALE_RAD, dmlPropertySetPr, builder);
        writePropertySetPrAttributeIfNotNull(DmlPropertySetAttr.CUST_RAD_SCALE_INC, dmlPropertySetPr, builder);

        DmlDiagramComplexTypesWriter.writeLayoutVariables("dgm:presLayoutVars", prSet.getLayoutVariablePropertySet(), writer);
        DmlShapeStyleWriter.write("dgm", prSet.getStyle(), writer);

        builder.endElement("dgm:prSet");
    }

    private static void writePropertySetPrAttributeIfNotNull(
        /*DmlPropertySetAttr*/int id,
        DmlPropertySetPr props,
        DocxBuilder builder)
    {
        Object val = props.getProperty(id);
        if (val == null)
            return;
        boolean isIsoStrict = builder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT;

        switch (id)
        {
            case DmlPropertySetAttr.PRES_ASSOC_ID:
                builder.writeAttribute("presAssocID", DmlDiagramEnum.modelIdToDml((DmlModelId)val));
                break;
            case DmlPropertySetAttr.PRES_NAME:
                builder.writeAttribute("presName", val);
                break;
            case DmlPropertySetAttr.PRES_STYLE_LBL:
                builder.writeAttribute("presStyleLbl", val);
                break;
            case DmlPropertySetAttr.PRES_STYLE_IDX:
                builder.writeAttribute("presStyleIdx", val);
                break;
            case DmlPropertySetAttr.PRES_STYLE_CNT:
                builder.writeAttribute("presStyleCnt", val);
                break;
            case DmlPropertySetAttr.LO_TYPE_ID:
                builder.writeAttribute("loTypeId", val);
                break;
            case DmlPropertySetAttr.LO_CAT_ID:
                builder.writeAttribute("loCatId", val);
                break;
            case DmlPropertySetAttr.QS_TYPE_ID:
                builder.writeAttribute("qsTypeId", val);
                break;
            case DmlPropertySetAttr.QS_CAT_ID:
                builder.writeAttribute("qsCatId", val);
                break;
            case DmlPropertySetAttr.CS_TYPE_ID:
                builder.writeAttribute("csTypeId", val);
                break;
            case DmlPropertySetAttr.CS_CAT_ID:
                builder.writeAttribute("csCatId", val);
                break;
            case DmlPropertySetAttr.COHERENT_3_D_OFF:
                builder.writeAttribute("coherent3DOff", val);
                break;
            case DmlPropertySetAttr.PHLDR_T:
                builder.writeAttribute("phldrT", val);
                break;
            case DmlPropertySetAttr.PHLDR:
                builder.writeAttribute("phldr", val);
                break;
            case DmlPropertySetAttr.CUST_ANG:
                builder.writeAttribute("custAng", val);
                break;
            case DmlPropertySetAttr.CUST_FLIP_VERT:
                builder.writeAttribute("custFlipVert", val);
                break;
            case DmlPropertySetAttr.CUST_FLIP_HOR:
                builder.writeAttribute("custFlipHor", val);
                break;
            case DmlPropertySetAttr.CUST_SZ_X:
                builder.writeAttribute("custSzX", val);
                break;
            case DmlPropertySetAttr.CUST_SZ_Y:
                builder.writeAttribute("custSzY", val);
                break;
            case DmlPropertySetAttr.CUST_SCALE_X:
                builder.writeAttribute("custScaleX", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_SCALE_Y:
                builder.writeAttribute("custScaleY", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_T:
                builder.writeAttribute("custT", val);
                break;
            case DmlPropertySetAttr.CUST_LIN_FACT_X:
                builder.writeAttribute("custLinFactX", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_LIN_FACT_Y:
                builder.writeAttribute("custLinFactY", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_X:
                builder.writeAttribute("custLinFactNeighborX", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_LIN_FACT_NEIGHBOR_Y:
                builder.writeAttribute("custLinFactNeighborY", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_RAD_SCALE_RAD:
                builder.writeAttribute("custRadScaleRad", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            case DmlPropertySetAttr.CUST_RAD_SCALE_INC:
                builder.writeAttribute("custRadScaleInc", toPercent((/*int*/Integer)val, isIsoStrict));
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Converts value in 1000th of percent to value that is appropriate to document format defined by 
    /// the <paramref name="isIsoStrict"/> parameter.
    /// </summary>
    private static String toPercent(int value, boolean isIsoStrict)
    {
        double asFraction = DmlPercentageUtil.fromDmlPercent(value);
        return DmlPercentageUtil.toPercentOrDmlPercent(asFraction, isIsoStrict);
    }

    private static void writeConnectionList(DmlDiagramConnection[] connectionList, DocxDocumentWriterBase writer)
    {
        if (connectionList == null)
            return;

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:cxnLst");

        for (DmlDiagramConnection connection : connectionList)
            writeConnection(connection, writer);

        builder.endElement("dgm:cxnLst");
    }

    private static void writeConnection(DmlDiagramConnection connection, DocxDocumentWriterBase writer)
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:cxn");

        builder.writeAttribute("modelId", DmlDiagramEnum.modelIdToDml(connection.getModelId()));
        builder.writeAttributeIfNotDefault("type", DmlDiagramEnum.connectionTypeToDml(connection.getType()), "parOf");
        builder.writeAttribute("srcId", DmlDiagramEnum.modelIdToDml(connection.getSourceId()));
        builder.writeAttribute("destId", DmlDiagramEnum.modelIdToDml(connection.getDestinationId()));
        builder.writeAttribute("srcOrd", connection.getSourceOrder());
        builder.writeAttribute("destOrd", connection.getDestinationOrder());
        builder.writeAttributeIfNotDefault("parTransId", DmlDiagramEnum.modelIdToDml(connection.getParentTransitionId()), "0");
        builder.writeAttributeIfNotDefault("sibTransId", DmlDiagramEnum.modelIdToDml(connection.getSiblingTransitionId()), "0");
        builder.writeAttributeIfNotDefault("presId", connection.getPresentationId(), "");

        builder.endElement("dgm:cxn");
    }

    private static void writeBackgroundFormatting(DmlDiagramBackground background, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:bg");

        if (background.getFill().getDmlFillType() != DmlFillType.STYLE_FILL)
            DmlFillWriter.write(background.getFill(), writer, false);

        DmlShapeEffectsWriter.write(background.getEffects(), writer, false);

        builder.endElement("dgm:bg");
    }

    private static void writeWholeFormatting(DmlDiagramWholeFormatting wholeFormatting, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dgm:whole");

        DmlOutlineWriter.write("a:ln", wholeFormatting.getOutline(), writer);

        DmlShapeEffectsWriter.write(wholeFormatting.getEffects(), writer, false);

        builder.endElement("dgm:whole");
    }

    /// <summary>
    /// Updates drawingRelId inside dataModelExt extension.
    /// </summary>
    private static void updateDrawingRelId(DmlDiagramDataModel data, String drawingRelId)
    {
        if (!data.hasExtensions())
            return;

        // Update drawingRelId inside dataModelExt.
        if (StringUtil.hasChars(drawingRelId))
        {
            DmlExtension dataModelExt = data.getExtensions().get(DmlExtensionUri.DATA_MODEL_EXT);

            if (dataModelExt != null)
                dataModelExt.setDrawingRelId(drawingRelId);
        }    
    }
}

