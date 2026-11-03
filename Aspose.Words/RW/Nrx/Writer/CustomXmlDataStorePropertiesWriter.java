// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.AnyXmlBuilder;


/*static*/ class CustomXmlDataStorePropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private CustomXmlDataStorePropertiesWriter()
	{}

    static void write(CustomXmlPart customXmlPart, AnyXmlBuilder builder, boolean isDocxIsoStrict)
    {
        builder.startDocument("ds:datastoreItem");
        // Write attribute first, followed by the namespace URI, this is the way MS Word writes and makes the gold more similar to the original file.
        builder.writeAttributeString("ds:itemID", customXmlPart.getId());

        builder.writeAttributeString("xmlns:ds", 
            DocxNamespaces.getNamespace(DocxNamespace.CUSTOM_XML, isDocxIsoStrict));

        builder.startElement("ds:schemaRefs");
        for (String namespaceUri : customXmlPart.getSchemas())
        {
            builder.startElement("ds:schemaRef");
            builder.writeAttributeString("ds:uri", namespaceUri);
            builder.endElement("ds:schemaRef");
        }
        builder.endElement("ds:schemaRefs");

        builder.endDocument();
    }
}

