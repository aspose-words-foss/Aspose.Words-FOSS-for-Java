// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import com.aspose.StreamUtil;


/// <summary>
/// <see cref="Source"/> xml serializer.
/// </summary>
/*static*/ class BibliographyWriter
{
	/* Simulation of static class by using private constructor */
	private BibliographyWriter()
	{}

    static CustomXmlPart write(Bibliography bibliography, Document document, CustomXmlPart customXmlPart) throws Exception
    {
        customXmlPart = ensureCustomXmlPart(customXmlPart, document);

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            AnyXmlBuilder builder = new AnyXmlBuilder(stream, true);

            write(bibliography, builder);

            customXmlPart.setData(StreamUtil.copyStreamToByteArray(stream));
        }
        finally { if (stream != null) stream.close(); }

        return customXmlPart;
    }

    private static CustomXmlPart ensureCustomXmlPart(CustomXmlPart customXmlPart, Document document)
    {
        if ((customXmlPart != null) && document.getCustomXmlParts().contains(customXmlPart))
            return customXmlPart;

        customXmlPart = new CustomXmlPart();
        document.getCustomXmlParts().add(customXmlPart);

        return customXmlPart;
    }

    private static void write(Bibliography bibliography, AnyXmlBuilder builder)
    {
        builder.startDocument("b:Sources", false);

        builder.writeAttributeString("xmlns:b", Bibliography.XML_NAME_SPACE);
        builder.writeAttributeString("xmlns", Bibliography.XML_NAME_SPACE);
        builder.writeAttributeString("SelectedStyle", bibliography.getBibliographyStyle());
        builder.writeAttributeString("StyleName", bibliography.getStyleName());
        builder.writeAttributeString("Version", bibliography.getVersion());

        for (Source source : bibliography.getSources())
            SourceWriter.write(source, builder);

        builder.endDocument();
    }
}

