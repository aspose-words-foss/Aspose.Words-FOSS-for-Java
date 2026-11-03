// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/10/2016 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements writing of the graphic frame element.
/// </summary>
/*static*/ class DmlGraphicFrameWriter
{
	/* Simulation of static class by using private constructor */
	private DmlGraphicFrameWriter()
	{}

    /// <summary>
    /// Starts graphic frame element.
    /// </summary>
    /// <param name="dml">Graphic frame shape.</param>
    /// <param name="writer">Document writer.</param>
    static void writeStart(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        Debug.assertTrue((dml != null) && (writer != null));

        DmlNode dmlNode = dml.getDmlNode();
        String prefix = getPrefix(writer, dmlNode);
        DocxBuilder builder = writer.getCurrentBuilder();

        builder.startElement(msString.format("{0}:graphicFrame", prefix));

        DmlGroupShape groupShape = (DmlGroupShape)dmlNode;
        DmlNonVisualPropertiesWriter.writeNvPr(prefix, groupShape, writer);

        if (!groupShape.isParentLockedCanvas())
            DmlXfrmWriter.write(msString.format("{0}:xfrm", prefix), groupShape.getTransformInternal(), writer);
    }

    /// <summary>
    /// Closes graphic frame tag.
    /// </summary>
    /// <param name="writer">Document writer.</param>
    /// <param name="dml">Graphic frame shape.</param>
    static void writeEnd(DocxDocumentWriterBase writer, ShapeBase dml) throws Exception
    {
        Debug.assertTrue((writer != null) && (dml != null) && (dml.getDmlNode() != null));

        DmlNode dmlNode = dml.getDmlNode();
        DmlGroupShape groupShape = (DmlGroupShape)dmlNode;
        String prefix = getPrefix(writer, dmlNode);

        // See related TestJira17672 for details.
        if (groupShape.isParentLockedCanvas())
            DmlXfrmWriter.write(msString.format("{0}:xfrm", prefix), groupShape.getTransformInternal(), writer);

        // Write extensions for graphic frame.
        DmlExtensionListWriter.write(((DmlGroupShape)dml.getDmlNode()).getExtensions(), writer);
        writer.getCurrentBuilder().endElement(); // Closes either "wp:graphicFrame" or "wpg:graphicFrame" or "a:graphicFrame".
    }

    private static String getPrefix(DocxDocumentWriterBase writer, DmlNode dmlNode)
    {
        Debug.assertTrue(dmlNode.getDmlNodeType() == DmlNodeType.GRAPHIC_FRAME);

        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String prefix = ((DmlGroupShape)dmlNode).isParentLockedCanvas() ? "a" : isIsoStrict ? "wp" : "wpg";
        return prefix;
    }
}

