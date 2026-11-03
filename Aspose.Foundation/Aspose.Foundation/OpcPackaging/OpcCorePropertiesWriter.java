// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2007 by Vladimir Averkin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Provides static method for building "Core File Properties" package part
/// </summary>
public /*static*/ class OpcCorePropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private OpcCorePropertiesWriter()
	{}

    /// <summary>
    /// Builds "Core File Properties" part for the specified document.
    /// </summary>
    public static void write(
        AnyXmlBuilder builder,
        String title,
        String subject,
        String creator,
        String keywords,
        String description,
        String lastModifiedBy,
        String revision,
        DateTime lastPrinted,
        DateTime created,
        DateTime modified,
        String category,
        String contentStatus,
        String language,
        String docVersion
        )
    {
        builder.startDocument("cp:coreProperties");

        builder.writeAttributeString("xmlns:cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
        builder.writeAttributeString("xmlns:dc", "http://purl.org/dc/elements/1.1/");
        builder.writeAttributeString("xmlns:dcterms", "http://purl.org/dc/terms/");
        builder.writeAttributeString("xmlns:dcmitype", "http://purl.org/dc/dcmitype/");
        builder.writeAttributeString("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

        builder.writeOptionalElement("dc:title", title);
        builder.writeOptionalElement("dc:subject", subject);
        builder.writeOptionalElement("dc:creator", creator);
        builder.writeOptionalElement("cp:keywords", keywords);

        if (StringUtil.hasChars(description))
        {
            builder.startElement("dc:description");
            builder.writeStringConvertSpecialCharacters(description);
            builder.endElement("dc:description");
        }

        builder.writeOptionalElement("cp:lastModifiedBy", lastModifiedBy);
        builder.writeElement("cp:revision", revision);
        builder.writeOptionalElement("cp:lastPrinted", lastPrinted);
        writeOptionalDate(builder, "created", created);
        writeOptionalDate(builder, "modified", modified);
        builder.writeOptionalElement("cp:category", category);
        builder.writeOptionalElement("cp:contentStatus", contentStatus);
        builder.writeOptionalElement("dc:language", language);
        builder.writeOptionalElement("cp:version", docVersion);

        builder.endDocument();
    }

    public static void writeOptionalDate(AnyXmlBuilder builder, String name, DateTime value)
    {
        if (value.getYear() > 1)
        {
            builder.startElement("dcterms:" + name);
            builder.writeAttributeString("xsi:type", "dcterms:W3CDTF");
            builder.writeString(FormatterPal.dateTimeToXmlUtc(value));
            builder.endElement();
        }
    }
}

