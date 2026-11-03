// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.Map;


/// <summary>
/// Provides static method for writing "Content Types" package part.
/// </summary>
public /*static*/ class OpcContentTypesWriter
{
	/* Simulation of static class by using private constructor */
	private OpcContentTypesWriter()
	{}

    /// <summary>
    /// Writes content types for the specified document.
    /// </summary>
    public static void write(OpcPackage opcPackage, boolean isPrettyFormat) throws Exception
    {
        // This iterates over all parts and collects two lists to write: default and override content types.
        SortedStringListGeneric<String> defaultTypes = new SortedStringListGeneric<String>();
        SortedStringListGeneric<String> overrideTypes = new SortedStringListGeneric<String>();

        for (OpcPackagePart part : opcPackage.getParts())
        {
            switch (gStringSwitchMap.of(part.getContentType()))
            {
                case /*OpcContentType.RELATIONSHIPS*/0:
                    // VA Content type for rels is added explicitly in the code below.
                    // RK I'm not exactly sure what is the reason, but lets leave it.
                    break;
                case/*OpcContentType.IMAGE_BMP*/1:
                case/*OpcContentType.IMAGE_EMF*/2:
                case/*OpcContentType.IMAGE_GIF*/3:
                case/*OpcContentType.IMAGE_JPEG*/4:
                case /*OpcContentType.IMAGE_PICT_COMPRESSED*/5:
                case/*OpcContentType.IMAGE_PNG*/6:
                case
            /*OpcContentType.IMAGE_WMF*/7:
                case/*OpcContentType.IMAGE_WEB_P*/8:
                case/*OpcContentType.ODTTF*/9:
                case/*OpcContentType.DIGITAL_SIGNATURE_ORIGIN*/10:
                case/*OpcContentType.DIGITAL_SIGNATURE_CERTIFICATE*/11:
                    defaultTypes.set(part.getExtension(), part.getContentType());
                    break;
                default:
                    // For all other parts, add them as overrides.
                    overrideTypes.add(part.getName(), part.getContentType());
                    break;
            }
        }

        // Create the content types part.
        OpcPackagePart contentTypesPart = new OpcPackagePart("/[Content_Types].xml", "");
        opcPackage.getParts().add(contentTypesPart);

        AnyXmlBuilder builder = new AnyXmlBuilder(contentTypesPart.getStream(), isPrettyFormat);
        builder.startDocument("Types");
        builder.writeAttributeString("xmlns", "http://schemas.openxmlformats.org/package/2006/content-types");

        // Write default content types.
        for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>) defaultTypes)
            writeDefaultElement(entry.getKey(), entry.getValue(), builder);

        writeDefaultElement("rels", OpcContentType.RELATIONSHIPS, builder);
        writeDefaultElement("xml", OpcContentType.XML, builder);

        // Write overrides for content types.
        for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>) overrideTypes)
            writeOverrideElement(entry.getKey(), entry.getValue(), builder);

        builder.endDocument();
    }

    private static void writeDefaultElement(String extension, String contentType, AnyXmlBuilder builder)
    {
        // Example:
        //   <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml" />
        builder.startElement("Default");
        builder.writeAttributeString("Extension", extension);
        builder.writeAttributeString("ContentType", contentType);
        builder.endElement();
    }

    private static void writeOverrideElement(String partName, String contentType, AnyXmlBuilder builder)
    {
        // Example:
        //   <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml" />
        builder.startElement("Override");
        builder.writeAttributeString("PartName", partName);
        builder.writeAttributeString("ContentType", contentType);
        builder.endElement();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"application/vnd.openxmlformats-package.relationships+xml",
		"image/bmp",
		"image/x-emf",
		"image/gif",
		"image/jpeg",
		"image/x-pcz",
		"image/png",
		"image/x-wmf",
		"image/webp",
		"application/vnd.openxmlformats-officedocument.obfuscatedFont",
		"application/vnd.openxmlformats-package.digital-signature-origin",
		"application/vnd.openxmlformats-package.digital-signature-certificate"
	);

}

