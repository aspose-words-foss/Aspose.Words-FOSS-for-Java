// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/05/2010 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.ms.System.msString;
import com.aspose.OpcContentType;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.words.ref.Ref;


/// <summary>
/// Writes the Custom XML Data Storage and Custom XML Data Storage Properties Parts.
/// </summary>
/*static*/ class DocxCustomXmlPartWriter
{
	/* Simulation of static class by using private constructor */
	private DocxCustomXmlPartWriter()
	{}

    static void write(DocxDocumentWriter writer) throws Exception
    {
        int customXmlPartId = 1;
        int customXmlPropsPartId = 1;
        for (CustomXmlPart customXmlPart : writer.getDocument().getCustomXmlParts())
        {
            // Add Custom XML Data Storage Part as a child of the Main Document Part.
            OpcPackagePart opcDataPart = writer.getPackage().createChildPart(
                writer.getDocumentPart(),
                msString.format("/customXml/item{0}.xml", customXmlPartId++),
                OpcContentType.XML,
                writer.getRelTypes().getCustomXml());

            opcDataPart.setStream(new MemoryStream(customXmlPart.getData()));

            // Add Custom XML Data Storage Properties Parts as children of the Custom XML Data Storage Part.
            String dummyRelId = null;
            Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
            DocxBuilder builder = writer.createChildPartAndBuilder(
                opcDataPart, 
                msString.format("/customXml/itemProps{0}.xml", customXmlPropsPartId++),
                DocxContentType.CUSTOM_XML_PROPERTIES,
                writer.getRelTypes().getCustomXmlProperties(),
                /*out*/ referenceToDummyRelId);
            dummyRelId = referenceToDummyRelId.get();

            CustomXmlDataStorePropertiesWriter.write(customXmlPart, builder, 
                builder.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT);
        }
    }

}

