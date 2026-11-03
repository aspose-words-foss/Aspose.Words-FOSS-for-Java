// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class used to write DrawingML canvas.
/// </summary>
/*static*/ class DmlCanvasWriter
{
	/* Simulation of static class by using private constructor */
	private DmlCanvasWriter()
	{}

    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode dmlNode = dml.getDmlNode();
        /*DmlNodeType*/int canvasType = dmlNode.getDmlNodeType();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        if (canvasType != DmlNodeType.LOCKED_CANVAS && canvasType != DmlNodeType.WORDPROCESSING_CANVAS)
            throw new IllegalArgumentException("Unexpected canvas shape type.");

        // Use the same writer for writing legacy lockedCanvas and wpc.
        // Determine root tag name by group shape type.        
        String prefix = isIsoStrict ? "wp" : "wpc";
        String rootTagName = isIsoStrict ? "wp:wpc" : "wpc:wpc";

        if (canvasType == DmlNodeType.LOCKED_CANVAS)
        {
            prefix = "a";
            rootTagName = "lc:lockedCanvas";
        }
                   
        DmlLockedCanvas canvas = (DmlLockedCanvas)dmlNode;
        NrxXmlBuilder builder = writer.getBuilder();            

        builder.startElement(rootTagName);

        if (canvasType == DmlNodeType.LOCKED_CANVAS)
            builder.writeAttributeString("xmlns:lc", 
                DmlNamespaceUtil.getNamespace(DmlNodeType.LOCKED_CANVAS, isIsoStrict));

        if (canvasType == DmlNodeType.WORDPROCESSING_CANVAS)
        {
            writeCanvasBackground(canvas, writer);
            writeCanvasOutline(canvas, writer);
        }

        DmlNonVisualPropertiesWriter.writeNvPr(prefix, canvas, writer);

        if (canvasType != DmlNodeType.WORDPROCESSING_CANVAS)
            DmlGroupShapeWriter.writeGroupShapeProperties(prefix, canvas, writer);
    }

    private static void writeCanvasBackground(DmlLockedCanvas canvas, DocxDocumentWriterBase writer) throws Exception
    {
        if (canvas.getFill().getDmlFillType() == DmlFillType.STYLE_FILL)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        String tagName = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT) ? "wp:bg" : "wpc:bg";

        builder.startElement(tagName);
        DmlFillWriter.write(canvas.getFill(), writer, false);
        builder.endElement(tagName);
    }

    private static void writeCanvasOutline(DmlLockedCanvas canvas, DocxDocumentWriterBase writer) throws Exception
    {
        if (canvas.getOutline() == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        String tagName = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT) ? "wp:whole" : "wpc:whole";

        builder.startElement(tagName);
        DmlOutlineWriter.write("a:ln", canvas.getOutline(), writer);
        builder.endElement(tagName);
    }

    static void writeEnd(DocxDocumentWriterBase writer)
    {
        writer.getBuilder().endElement(); // End either  "lc:lockedCanvas" or "wpc:wpc".
    }
}

