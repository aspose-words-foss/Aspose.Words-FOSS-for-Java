// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/*static*/ class DmlGraphicWriter
{
	/* Simulation of static class by using private constructor */
	private DmlGraphicWriter()
	{}

    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        if (isGraphicWrappedShape(dml))
        {
            NrxXmlBuilder builder = writer.getCurrentBuilder();
            boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

            builder.startElement("a:graphic");
            builder.writeAttribute("xmlns:a", DocxNamespaces.getNamespace(DocxNamespace.DRAWING_ML_MAIN, isIsoStrict));

            builder.startElement("a:graphicData");
            builder.writeAttribute("uri", DmlNamespaceUtil.getNamespace(dml.getDmlNode().getDmlNodeType(), isIsoStrict));
        }

        writeDmlNodeStart(dml, writer);
    }

    static void writeEnd(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        writeDmlNodeEnd(dml, writer);

        if (isGraphicWrappedShape(dml))
        {
            writer.getCurrentBuilder().endElement("a:graphicData");
            writer.getCurrentBuilder().endElement("a:graphic");
        }
    }

    /// <summary>
    /// Returns "true" when shape has to be wrapped into "graphicData" element.
    /// </summary>
    /// <param name="dml">Shape for check.</param>
    /// <returns>True when shape has to be wrapped.</returns>
    private static boolean isGraphicWrappedShape(ShapeBase dml)
    {
        Debug.assertTrue(dml != null);

        if (dml.isTopLevel())
            return true;

        // Shape has to be wrapped into "graphicData" element when it is a child item of the graphic frame.
        Node parent = dml.getParentNode();
        return (parent.getNodeType() == NodeType.GROUP_SHAPE) &&
               (((ShapeBase)parent).getDmlNode() != null) &&
               (((ShapeBase)parent).getDmlNode().getDmlNodeType() == DmlNodeType.GRAPHIC_FRAME);
    }

    private static void writeDmlNodeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        switch (dml.getDmlNode().getDmlNodeType())
        {
            case DmlNodeType.LOCKED_CANVAS:
            case DmlNodeType.WORDPROCESSING_CANVAS:
                DmlCanvasWriter.writeStart(dml, writer);
                break;
            case DmlNodeType.PICTURE:
                DmlPictureWriter.write(dml, writer);
                break;
            case DmlNodeType.CHART:
            case DmlNodeType.CHART_EX:
                DmlChartWriter.write(dml, writer);
                break;
            case DmlNodeType.DIAGRAM:
                DmlDiagramWriter.write(dml.getDmlNode(), writer);
                break;
            case DmlNodeType.WORDPROCESSING_SHAPE:
            case DmlNodeType.SHAPE:
            case DmlNodeType.CONNECTOR_SHAPE:
                DmlShapeWriter.writeStart(dml, writer);
                break;   
            case DmlNodeType.WORDPROCESSING_GROUP_SHAPE:
            case DmlNodeType.GROUP_SHAPE:
                DmlGroupShapeWriter.writeStart(dml, writer);
                break;
            case DmlNodeType.CONTENT_PART:
                DmlContentPartWriter.write(dml, writer);
                break;
            case DmlNodeType.GRAPHIC_FRAME:
                // According to spec can occur in group shape or canvas.
                DmlGraphicFrameWriter.writeStart(dml, writer);
                break;
            default:
                throw new IllegalArgumentException("Unexpected DrawingML type.");
        }
    }

    private static void writeDmlNodeEnd(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        switch (dml.getDmlNode().getDmlNodeType())
        {
            case DmlNodeType.LOCKED_CANVAS:
            case DmlNodeType.WORDPROCESSING_CANVAS:
                DmlCanvasWriter.writeEnd(writer);
                break;
            case DmlNodeType.PICTURE:
            case DmlNodeType.CHART:
            case DmlNodeType.CHART_EX:
            case DmlNodeType.DIAGRAM:
            case DmlNodeType.CONTENT_PART:
                // Do nothing because whole objects are written using corresponding Write method.
                break;
            case DmlNodeType.WORDPROCESSING_SHAPE:
            case DmlNodeType.SHAPE:
            case DmlNodeType.CONNECTOR_SHAPE:
                DmlShapeWriter.writeEnd(dml, writer);
                break;               
            case DmlNodeType.WORDPROCESSING_GROUP_SHAPE:
            case DmlNodeType.GROUP_SHAPE:
                DmlGroupShapeWriter.writeEnd(writer);
                break;
            case DmlNodeType.GRAPHIC_FRAME:
                DmlGraphicFrameWriter.writeEnd(writer, dml);
                break;
            default:
                throw new IllegalArgumentException("Unexpected DrawingML type.");
        }
    }
}

