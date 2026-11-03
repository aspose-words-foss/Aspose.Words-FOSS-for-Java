// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class DmlDiagramDrawingWriter
{
	/* Simulation of static class by using private constructor */
	private DmlDiagramDrawingWriter()
	{}

    static void write(ShapeBase drawing, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startDocument("dsp:drawing");
        builder.writeAttribute("xmlns:dgm", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM, isIsoStrict));
        builder.writeAttribute("xmlns:dsp", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM_2008, isIsoStrict));
        builder.writeAttribute("xmlns:a", 
            DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

        writeGroupShape("dsp:spTree", drawing, writer);

        builder.endDocument();
    }

    private static void writeShape(DmlShape shape, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement("dsp:sp");
        builder.writeAttribute("modelId", DmlDiagramEnum.modelIdToDml(shape.getModelId()));

        DmlNonVisualPropertiesWriter.writeNvPr("dsp", shape, writer);
        DmlShapePropertiesWriter.write("dsp", shape, writer);
        DmlShapeStyleWriter.write("dsp", shape.getStyle(), writer);
        if (shape.getTextShape() != null)
        {
            DmlTextShapeWriter.writeDmlShapeTextBody("dsp:txBody", shape.getTextShape().getTextBody(), writer);
            // WORDSNET-13947 Write proper txXfrm.
            DmlXfrmWriter.write("dsp:txXfrm", shape.getTextShape().getTransform(), writer);
        }

        builder.endElement("dsp:sp");
    }

    private static void writeGroupShape(String tagName, ShapeBase group, DocxDocumentWriterBase writer) throws Exception
    {
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(tagName);

        DmlNonVisualPropertiesWriter.writeNvPr("dsp", group.getDmlNode(), writer);
        DmlGroupShapeWriter.writeGroupShapeProperties("dsp", (DmlCompositeNode)group.getDmlNode(), writer);

        for (Shape node : (Iterable<Shape>) group.getChildNodes(NodeType.ANY, false))
        {
            // According to the scheme only Shape and Group Shape can be children of dsp:spTree and dsp:grpSp.
            // Do not process other nodes.
            switch (node.getDmlNode().getDmlNodeType())
            {
                case DmlNodeType.SHAPE:
                    writeShape((DmlShape)node.getDmlNode(), writer);
                    break;
                case DmlNodeType.GROUP_SHAPE:
                    writeGroupShape("dsp:grpSp", group, writer);
                    break;
                default:
                    break;
            }
        }

        builder.endElement(tagName);
    }
}

