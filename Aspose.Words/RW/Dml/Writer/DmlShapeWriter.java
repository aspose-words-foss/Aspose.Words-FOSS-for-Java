// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Represents class writing shape "a:sp" or WordProccessingShape "wps:wsp"
/// </summary>
/*static*/ class DmlShapeWriter
{
	/* Simulation of static class by using private constructor */
	private DmlShapeWriter()
	{}

    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode dmlNode = dml.getDmlNode();

        if (dmlNode.getDmlNodeType() != DmlNodeType.SHAPE &&
            dmlNode.getDmlNodeType() != DmlNodeType.WORDPROCESSING_SHAPE &&
            dmlNode.getDmlNodeType() != DmlNodeType.CONNECTOR_SHAPE)
            throw new IllegalArgumentException("Unexpected Dml node type.");

        DmlShape dmlShape = (DmlShape)dmlNode;
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        String prefix;
        String rootTagName;

        switch (dmlShape.getDmlNodeType())
        {
            case DmlNodeType.SHAPE:
                {
                    prefix = "a";
                    rootTagName = "a:sp";
                    break;
                }
            case DmlNodeType.WORDPROCESSING_SHAPE:
                {
                    prefix = isIsoStrict ? "wp" : "wps";
                    rootTagName = msString.format("{0}:wsp", prefix);
                    break;
                }
            case DmlNodeType.CONNECTOR_SHAPE:
                {
                    prefix = "a";
                    rootTagName = "a:cxnSp";
                    break;
                }
            default:
                throw new IllegalArgumentException("Unexpected Dml node type.");
        }

        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(rootTagName);

        if ((dmlShape.getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE) && !isIsoStrict)
            builder.writeAttribute("xmlns:wps", DmlNamespaceUtil.getNamespace(DmlNodeType.WORDPROCESSING_SHAPE, false));

        DmlNonVisualPropertiesWriter.writeNvPr(prefix, dmlShape, writer);
        DmlShapePropertiesWriter.write(dmlShape, writer);
        if (dml.getDmlNode().getDmlNodeType() == DmlNodeType.SHAPE)
            DmlTextShapeWriter.write((DmlShape)dml.getDmlNode(), writer);

        DmlShapeStyleWriter.write(dmlShape, writer);
        String tagName;

        if (dml.hasChildNodes())
        {
            tagName = isIsoStrict ? "wp:txbx" : "wps:txbx";
            builder.startElement(tagName);
            builder.writeAttributeIfNotZero("id", dml.getTextboxId());

            tagName = isIsoStrict ? "wne:txbxContent" : "w:txbxContent";
            builder.startElement(tagName);
        }
        else if (dml.getLinkedTextboxId() > 0)
        {
            tagName = isIsoStrict ? "wp:linkedTxbx" : "wps:linkedTxbx";
            builder.startElement(tagName);
            builder.writeAttributeIfNotZero("id", dml.getLinkedTextboxId());
            builder.writeAttributeIfNotZero("seq", dml.getLinkedTextboxSeq());
            builder.endElement(tagName);
        }
    }

    static void writeEnd(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder currentBuilder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        if (dml.hasChildNodes())
        {
            currentBuilder.endElement(isIsoStrict ? "wne:txbxContent" : "w:txbxContent");
            currentBuilder.endElement(isIsoStrict ? "wp:txbx" : "wps:txbx");
        }

        if (dml.getDmlNode().getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE)
            DmlTextShapeWriter.write((DmlShape)dml.getDmlNode(), writer);

        currentBuilder.endElement(); // Closes either "a:sp" or "wps:wsp".
    }
}

