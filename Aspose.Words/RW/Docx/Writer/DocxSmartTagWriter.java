// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class DocxSmartTagWriter
{
	/* Simulation of static class by using private constructor */
	private DocxSmartTagWriter()
	{}

    static void writeStart(SmartTag smartTag, DocxBuilder builder)
    {
        builder.startElement("w:smartTag");

        builder.writeAttribute("w:uri", smartTag.getUri());
        builder.writeAttribute("w:element", smartTag.getElement());

        writeProperties(smartTag.getProperties(), builder);
    }

    private static void writeProperties(CustomXmlPropertyCollection props, DocxBuilder builder)
    {
        if (props.getCount() == 0)
            return;

        builder.startElement("w:smartTagPr");

        for (CustomXmlProperty prop : props)
        {
            builder.startElement("w:attr");
            builder.writeAttribute("w:uri", prop.getUri());
            builder.writeAttribute("w:name", prop.getName());
            builder.writeAttribute("w:val", prop.getValue());
            builder.endElement();   // w:attr
        }

        builder.endElement();   // w:smartTagPr
    }

    static void writeEnd(DocxBuilder builder)
    {
        builder.endElement();   // w:smartTag
    }
}

