// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/15/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Instance class for writing DrawingML picture.
/// ISO_IEC_29500-1 20.2 (DrawingML - Picture)
/// </summary>
/*static*/ class DmlPictureWriter
{
	/* Simulation of static class by using private constructor */
	private DmlPictureWriter()
	{}

    static void write(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlNode dmlNode = dml.getDmlNode();

        if (dmlNode.getDmlNodeType() != DmlNodeType.PICTURE)
            throw new IllegalArgumentException("Unexpected Dml node type.");

        String prefix = "pic";

        ShapeBase topLevelParentDml = dml.getTopLevelParentShape();
        if ((topLevelParentDml != null) && (topLevelParentDml.getDmlNode().getDmlNodeType() == DmlNodeType.LOCKED_CANVAS))
            prefix = "a";

        String tagName = msString.format("{0}:pic", prefix);
        DmlPicture dmlPicture = (DmlPicture)dmlNode;
        NrxXmlBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(tagName);
        builder.writeAttribute("xmlns:pic", DmlNamespaceUtil.getNamespace(dmlPicture.getDmlNodeType(), isIsoStrict));

        DmlNonVisualPropertiesWriter.writeNvPr(prefix, dmlPicture, writer);
        DmlFillWriter.writeDmlBlipFill(prefix, dmlPicture.getBlipFill(), writer);
        DmlShapePropertiesWriter.write(prefix, dmlPicture, writer);

        // Write extLst (Extension List with Modification Flag) §4.2.4.
        DmlExtensionListWriter.write(dmlPicture.getExtensions(), writer);

        DmlShapeStyleWriter.write(dmlPicture, writer);

        builder.endElement(tagName);
    }
}

