// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/26/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.OpcPackagePart;
import com.aspose.words.ref.Ref;


/*static*/ class DmlContentPartWriter
{
	/* Simulation of static class by using private constructor */
	private DmlContentPartWriter()
	{}

    static void write(ShapeBase dml, DocxDocumentWriterBase writer) throws Exception
    {
        DmlContentPart contentPart = (DmlContentPart)dml.getDmlNode();

        DocxBuilder builder = writer.getCurrentBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement("w14:contentPart");
        builder.writeAttribute("bwMode", DmlEnum.bWModeToDml(contentPart.getBWMode()));
        String relId = writeContentPart(contentPart, writer);
        builder.writeAttribute("r:id", relId);
        builder.writeAttribute("xmlns:r", DocxNamespaces.getNamespace(DocxNamespace.RELATIONSHIPS, isIsoStrict));
        builder.writeAttribute("xmlns:w14", DocxNamespaces.getNamespace(DocxNamespace.W1_4_MARKUP, isIsoStrict));

        DmlNonVisualPropertiesWriter.writeNvPr("w14", contentPart, writer);
        DmlXfrmWriter.write("w14:xfrm", contentPart.getTransform(), writer);

        DmlExtensionListWriter.write("w14", contentPart.getExtensions(), writer);

        builder.endElement("w14:contentPart");
    }

    private static String writeContentPart(DmlContentPart contentPart, DocxDocumentWriterBase writer) throws Exception
    {
        String contentType = contentPart.getContentPart().getContentType();
        String relType = DocxRelationshipTypes.getType(DocxRelationshipType.CUSTOM_XML,
            writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
        String partName = msString.format("ink/ink{0}.xml", writer.getNextEmbeddedPartNumber(relType));
        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        OpcPackagePart newContentPart = writer.getPackage().createChildPart(writer.getCurrentBuilder().getPart(), partName, contentType, relType, /*out*/ referenceToRelId);
        relId = referenceToRelId.get();
        newContentPart.setStream(contentPart.getContentPart().getStream());
        DocxDocumentWriterBase.writeUnparsedRels(contentPart.getContentPart(), contentPart.getRelatedParts(), newContentPart, writer);

        return relId;
    }
}

