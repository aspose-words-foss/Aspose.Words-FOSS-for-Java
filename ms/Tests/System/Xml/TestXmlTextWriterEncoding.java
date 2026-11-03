// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2008 by Konstantin Sidorenko
// 04/02/2016 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Xml.XmlTextWriter;
import com.aspose.ms.System.msArray;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.StreamReader;


@Test
public class TestXmlTextWriterEncoding
{
    @Test
    public void testUtf8() throws Exception
    {
        Encoding encoding = Encoding.getUTF8();
        MemoryStream stream = new MemoryStream(128);
        XmlTextWriter writer = new XmlTextWriter(stream, encoding);

        writer.writeStartDocument();
        writer.writeStartElement("start"); //or else stax throws when trying to write end element on writer.Close()
        writer.writeRaw("123fFЯ");
        writer.flush();

        //check the bom.
        byte[] bytes = stream.toArray();
        byte[] preamble = encoding.getPreamble();
        byte[] bom = new byte[preamble.length];
        msArray.copy(bytes, 0, bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        StreamReader reader = new StreamReader(new MemoryStream(bytes), encoding);
        String declaration = reader.readToEnd();
        msAssert.areEqual(declaration, "<?xml version=\"1.0\" encoding=\"utf-8\"?><start>123fFЯ");

        writer.close();
        reader.close();
    }

    @Test
    public void testEncodingUtf7() throws Exception
    {
        Encoding encoding = Encoding.getUTF7();
        MemoryStream stream = new MemoryStream(128);
        XmlTextWriter writer = new XmlTextWriter(stream, encoding);

        writer.writeStartDocument();
        writer.writeStartElement("start"); //or else stax throws when trying to write end element on writer.Close()
        writer.writeRaw("123fFЯ");
        writer.flush();

        //check the bom - XmlTextWriter (or rather backing StreamWriter) doesn't write it for UTF7.
        byte[] bytes = stream.toArray();
        byte[] preamble = encoding.getPreamble();
        byte[] bom = new byte[preamble.length];
        msArray.copy(bytes, 0, bom, 0, bom.length);
        msAssert.areEqual(preamble, bom);

        StreamReader reader = new StreamReader(new MemoryStream(bytes), encoding);
        String declaration = reader.readToEnd();
        //JAVA: HACK for Sun jdk1.4, jdk1.5 Charset.encode() Flushing bug:
        //for utf-7 encoding ours XmlTextWriter.Flush() writes additional "\r\n" to the end
        //so the encoder can encode last bytes.
        msAssert.areEqual(declaration, "<?xml version=\"1.0\" encoding=\"utf-7\"?><start>123fFЯ\r\n");

        writer.close();
        reader.close();
    }
}

