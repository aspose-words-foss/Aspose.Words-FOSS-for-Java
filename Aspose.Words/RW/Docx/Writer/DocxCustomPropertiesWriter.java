// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/08/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;


/// <summary>
/// Provides static method for building "Custom Properties" package part
/// </summary>
/*static*/ class DocxCustomPropertiesWriter
{
	/* Simulation of static class by using private constructor */
	private DocxCustomPropertiesWriter()
	{}

    /// <summary>
    /// Writes "Custom Properties" document part for the specified document.
    /// </summary>
    static void write(DocxWriter writer) throws Exception
    {
        Document doc = writer.getDocument();

        if (!NrxDocPropertiesWriter.hasWritableProperties(doc.getCustomDocumentProperties()))
            return;

        DocxBuilder builder = writer.createPartAndBuilder("/docProps/custom.xml", DocxContentType.CUSTOM_PROPERTIES, writer.getRelTypes().getCustomProperties());
        builder.startCustomPropertiesDocumentPart();


        // RK It looks like MS Word here generates property ids instead of using existing ones, we do the same.
        int pid = DocumentProperty.MIN_USER_PROP_ID;
        for (DocumentProperty prop : (Iterable<DocumentProperty>) doc.getCustomDocumentProperties())
        {
            if (NrxDocPropertiesWriter.isWritableProperty(prop))
            {
                builder.startElement("property");
                // This is the standard guid for the custom document properties.
                builder.writeAttributeString("fmtid", "{D5CDD505-2E9C-101B-9397-08002B2CF9AE}");
                builder.writeAttributeString("pid", FormatterPal.intToXml(pid));
                builder.writeAttributeString("name", prop.getName());
                builder.writeAttribute("linkTarget", prop.getLinkTarget());
                writePropertyValue(prop, builder);
                builder.endElement();
                pid++;
            }
        }

        builder.endDocument(); //Properties
    }

    private static void writePropertyValue(DocumentProperty prop, NrxXmlBuilder builder)
    {
        switch (prop.getType())
        {
            case PropertyType.BOOLEAN:
                builder.startElement("vt:bool");
                builder.writeString(prop.toBool() ? "true" : "false");
                builder.endElement();
                break;
            case PropertyType.NUMBER:
                builder.startElement("vt:i4");
                builder.writeString(FormatterPal.intToXml(prop.toInt()));
                builder.endElement();
                break;
            case PropertyType.DOUBLE:
                builder.startElement("vt:r8");
                builder.writeString(FormatterPal.doubleToStr(prop.toDouble()));
                builder.endElement();
                break;
            case PropertyType.STRING:
                builder.startElement("vt:lpwstr");
                builder.writeStringConvertSpecialCharacters((String)prop.getValue());
                builder.endElement();
                break;
            case PropertyType.DATE_TIME:
                builder.startElement("vt:filetime");
                builder.writeString(FormatterPal.dateTimeToXmlUtc(prop.toDateTimeInternal()));
                builder.endElement();
                break;
            default:
                // We should not get here because we are not writing properties without a value as
                // it makes an OOXML document that MS Word refuses to open.
                throw new IllegalStateException("Unexpected property type.");
        }
    }
}

