// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/*static*/ class DmlGroupShapeWriter
{
	/* Simulation of static class by using private constructor */
	private DmlGroupShapeWriter()
	{}

    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode dmlNode = dml.getDmlNode();
        /*DmlNodeType*/int grpSpType = dmlNode.getDmlNodeType();

        if (grpSpType != DmlNodeType.GROUP_SHAPE && 
            grpSpType != DmlNodeType.WORDPROCESSING_GROUP_SHAPE)
            throw new IllegalArgumentException("Unexpected group shape type.");

        String prefix = "a";
        String tag = "grpSp";
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        ShapeBase topLevelParent = getTopLevelParent(dml);            
        /*DmlNodeType*/int topLevelParentType = (topLevelParent != null) ? topLevelParent.getDmlNode().getDmlNodeType() : grpSpType;
                        
        if (grpSpType != DmlNodeType.GROUP_SHAPE)                
        {
            tag = "wgp";
            prefix = isIsoStrict ? "wp" : "wpg";
        }

        if ((topLevelParentType == DmlNodeType.WORDPROCESSING_CANVAS) ||
            (topLevelParentType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE))         
            prefix = isIsoStrict ? "wp" : "wpg";
 
        // Use the same writer for writing legacy grpSp and wgp.
        // Determine root tag name by group shape type.
        String rootTagName = msString.format("{0}:{1}", prefix, tag);            
        DmlGroupShape groupShape = (DmlGroupShape)dmlNode;
        NrxXmlBuilder builder = writer.getCurrentBuilder();          

        builder.startElement(rootTagName);

        if (grpSpType == DmlNodeType.WORDPROCESSING_GROUP_SHAPE)
            builder.writeAttribute("xmlns:wpg",
                DmlNamespaceUtil.getNamespace(DmlNodeType.WORDPROCESSING_GROUP_SHAPE, isIsoStrict));

        DmlNonVisualPropertiesWriter.writeNvPr(prefix, groupShape, writer);
        writeGroupShapeProperties(prefix, groupShape, writer);
    }

    static void writeEnd(DocxDocumentWriterBase writer)
    {
        writer.getCurrentBuilder().endElement(); // Closes either "a:grpSp" or "wpg:wgp".
    }

    /// <summary>
    /// Returns top-level parent drawingML.
    /// </summary>
    /// <param name="dml"></param>
    /// <returns></returns>
    private static ShapeBase getTopLevelParent(ShapeBase dml)
    {
        if (dml.isTopLevel())
            return dml;

        if ((dml.getParentNode().getNodeType() != NodeType.SHAPE) && (dml.getParentNode().getNodeType() != NodeType.GROUP_SHAPE))
            return null;

        return getTopLevelParent((ShapeBase)dml.getParentNode());
    }

    static void writeGroupShapeProperties(String prefix, DmlCompositeNode dml, DocxDocumentWriterBase writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getCurrentBuilder();

        String rootTagName = msString.format("{0}:grpSpPr", prefix);

        builder.startElement(rootTagName);

        if ((dml.getDmlNodeType() == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) || 
            (dml.getDmlNodeType() == DmlNodeType.GROUP_SHAPE))
        {
            builder.writeAttributeIfNotDefault("bwMode", DmlEnum.bWModeToDml(((DmlGroupShape)dml).getBWMode()),
                DmlEnum.bWModeToDml(BWMode.DEFAULT));
        }
                         
        DmlXfrmWriter.write(dml.getTransformInternal(), writer);
        DmlFillWriter.write(dml.getFill(), writer, false);
        DmlShapeEffectsWriter.write(dml.getEffects(), writer, false);
        Dml3DPropertiesWriter.writeScene3D(dml.getScene3DProperties(), writer, false);

        DmlExtensionListWriter.write(dml.getExtensions(), writer);

        builder.endElement(rootTagName);
    }
}

