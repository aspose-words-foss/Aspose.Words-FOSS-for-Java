// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2006 by Konstantin Sidorenko
// 2016/02/02 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Xml.XmlTextWriter;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Note UTF8 BOM (or, rather, utf8 Bom bytes decoded by utf8 encoding:) on the start of the _each first_ test string.
///
/// Quotation from XmlTextWriter ctor:
/// .Net writes the BOM only if sees the Stream begin, but we can't check
/// whether it is begin or not for java's OutputStream - so we always write the BOM
/// during XmlTextWriter initialization.
/// </summary>
@Test
public class TestXmlTextWriter
{
    /// <summary>
    ///  Checks start document declaration options, xml version and encoding.
    /// </summary>
    @Test
    public void testStartDocument() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartDocument();
        writer.flush();
        String declaration = encoding.getString(streams.toArray());
        msAssert.areEqual(declaration, "\uFEFF<?xml version=\"1.0\" encoding=\"utf-8\"?>");

        streams.close();
        streams = new MemoryStream(128);
        encoding = Encoding.getUTF8();
        writer = new XmlTextWriter(streams, encoding);

        writer.writeStartDocument(false);
        writer.flush();
        declaration = encoding.getString(streams.toArray());
        msAssert.areEqual(declaration, "\uFEFF<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>");

        streams.close();
        streams = new MemoryStream(128);
        encoding = Encoding.getUTF8();
        writer = new XmlTextWriter(streams, encoding);

        writer.writeStartDocument(true);
        writer.flush();
        declaration = encoding.getString(streams.toArray());
        msAssert.areEqual(declaration, "\uFEFF<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>");

        //Woodstox throws during closing if no tag started (when auto elements closing enabled) - let's add something.
        writer.writeStartElement("html");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteDocType() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeDocType(
            "html",
            "-//W3C//DTD XHTML 1.0 Transitional//EN",
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd",
            null);

        writer.flush();
        String dtd = encoding.getString(streams.toArray());

        msAssert.areEqual(dtd, "\uFEFF<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");

        //Woodstox throws during closing if no tag started (when auto elements closing enabled) - let's add something.
        writer.writeStartElement("html");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteStartElement() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        writer.writeAttributeString("xmlns", "http://www.w3.org/1999/xhtml");
        writer.writeStartElement("head");

        writer.flush();
        String start = encoding.getString(streams.toArray());
        msAssert.areEqual(start, "\uFEFF<html xmlns=\"http://www.w3.org/1999/xhtml\"><head");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteEndElements() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        writer.writeStartElement("head");
        writer.writeStartElement("meta");
        writer.writeEndElement(); // meta
        writer.writeStartElement("body");

        writer.writeEndElement(); // body
        writer.writeEndElement(); // head
        writer.writeEndElement(); // html

        writer.flush();
        String start = encoding.getString(streams.toArray());
        //JAVA: Woodstox parser can write automatic empty elements
        msAssert.areEqual(start, "\uFEFF<html><head><meta /><body /></head></html>");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteFullEndElements() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        writer.writeStartElement("head");
        writer.writeStartElement("meta");
        writer.writeFullEndElement(); // meta
        writer.writeStartElement("body");

        writer.writeFullEndElement(); // body
        writer.writeFullEndElement(); // head
        writer.writeFullEndElement(); // html

        writer.flush();
        String start = encoding.getString(streams.toArray());
        msAssert.areEqual(start, "\uFEFF<html><head><meta></meta><body></body></head></html>");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteEndDocument() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartDocument();
        writer.writeStartElement("html");
        writer.writeStartElement("head");
        writer.writeStartElement("meta");
        writer.writeEndElement(); // meta
        writer.writeStartElement("body");

        writer.writeEndDocument(); // closes all open tags

        writer.flush();
        String start = encoding.getString(streams.toArray());
        //note: Woodstox closes "body" with full end tag.
        msAssert.areEqual(start, "\uFEFF<?xml version=\"1.0\" encoding=\"utf-8\"?><html><head><meta /><body></body></head></html>");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteString() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("start");
        writer.writeString("Just a plain string.");
        writer.writeString(" A string with entities: ");
        writer.writeString("& < > ");
        writer.writeString("\" ' \r ");
        writer.writeString("\f \u0001 \u0007 .");
        writer.writeEndElement();

        writer.flush();
        String output = encoding.getString(streams.toArray());
        msAssert.areEqual(output, "\uFEFF<start>Just a plain string. A string with entities: &amp; &lt; &gt; \" ' \r &#xC; &#x1; &#x7; .</start>");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteRussianString() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("start");
        writer.writeString("Русский текст.");
        writer.writeEndElement();

        writer.flush();
        String output = encoding.getString(streams.toArray());
        msAssert.areEqual(output, "\uFEFF<start>Русский текст.</start>");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteAttributeString() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        writer.writeAttributeString("xmlns", "http://www.w3.org/1999/xhtml");
        writer.writeStartElement("head");
        writer.writeStartElement("meta");
        writer.writeAttributeString("http-equiv", "Content-Type");
        writer.writeAttributeString("content", "text/html; charset=utf-8");
        writer.writeEndElement(); // meta
        writer.writeStartElement("body");
        writer
            .writeAttributeString("attributeWithEntities", "<attribute>with\"entities\"and\'a'postrofes\'");
        writer.writeAttributeString("att", "<>");

        writer.flush();
        String start = encoding.getString(streams.toArray());

        msAssert.areEqual(start, "\uFEFF<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                                       "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                                       "<body attributeWithEntities=\"&lt;attribute&gt;with&quot;entities&quot;and'a'postrofes'\" att=\"&lt;&gt;\"");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteRussianAttributeString() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        writer.writeAttributeString("xmlns", "http://www.w3.org/1999/xhtml");
        writer.writeStartElement("head");
        writer.writeStartElement("meta");
        writer.writeAttributeString("http-equiv", "Content-Type");
        writer.writeAttributeString("content", "text/html; charset=utf-8");
        writer.writeEndElement(); // meta
        writer.writeStartElement("body");
        writer.writeAttributeString("attributeWithRussian", "Русский текст.");

        writer.flush();
        String start = encoding.getString(streams.toArray());

        msAssert.areEqual(start, "\uFEFF<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                                       "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                                       "<body attributeWithRussian=\"Русский текст.\"");

        writer.close();
        streams.close();
    }

    @Test
    public void testWriteAttributeWithCharacterEntities() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        String entitiesStr = "& < > \" ' \r \n \t \f \u0001 \u0007 .";
        writer.writeAttributeString("entities", entitiesStr);
        writer.writeEndElement();

        writer.flush();
        String output = encoding.getString(streams.toArray());
        String expected =
            "\uFEFF<html entities=\"&amp; &lt; &gt; &quot; ' &#xD; &#xA; &#x9; &#xC; &#x1; &#x7; .\" />";

        writer.close();
        streams.close();
        msAssert.areEqual(output, expected);
    }

    @Test
    public void testWriteRaw() throws Exception
    {
        MemoryStream streams = new MemoryStream(128);
        Encoding encoding = Encoding.getUTF8();
        XmlTextWriter writer = new XmlTextWriter(streams, encoding);

        writer.writeStartElement("html");
        String entitiesStr = "A string with entities: & < > \" ' \r \f \u0001 \u0007 .";
        writer.writeRaw(entitiesStr);

        writer.flush();
        String output = encoding.getString(streams.toArray());
        msAssert.areEqual(output, "\uFEFF<html>" + entitiesStr);

        writer.close();
        streams.close();
    }
}

