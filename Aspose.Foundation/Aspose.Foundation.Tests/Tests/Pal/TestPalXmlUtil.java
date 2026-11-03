// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2014 by Pavel Gorbunov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Xml.XmlNodeType;


// These tests cases already present in com.aspose.ms.TestXmlTextReader in Java
@Test
public class TestPalXmlUtil
{
    @Test
    public void testReadOuterXmlCharacterEntitiesInAttributes() throws Exception
    {
        String input = "<start comment=\"&amp; &lt; &gt; \r\n\t.\"></start>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));

        reader.read();
        msAssert.areEqual("start", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());

        String outerXml = XmlUtilPal.readOuterXml(reader);
        msAssert.areEqual("<start comment=\"&amp; &lt; &gt; &#xD;&#xA;\t.\"></start>", outerXml);
        reader.close();
    }

    @Test
    public void testReadOuterXmlRussianInAttributes() throws Exception
    {
        String input = "<start comment=\"Русский язык\">" +
                       "</start>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.areEqual("start", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());

        String outerXml = XmlUtilPal.readOuterXml(reader);
        msAssert.areEqual(input, outerXml);
        reader.close();
    }

    @Test
    public void testReadOuterXml() throws Exception
    {
        String input = "<book genre='novel' ISBN='1-861003-78' pubdate='1987'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <price usd=\"1.23\" eur=\"1.0\" />\n" +
                       "</book>";

        // readOuterXml() changes single quotes to double ones.
        // readOuterXml() put attributes in alphabetical order
        String output = "<book ISBN=\"1-861003-78\" genre=\"novel\" pubdate=\"1987\">\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <price eur=\"1.0\" usd=\"1.23\" />\n" +
                       "</book>";

        //position on <book> element start.
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        reader.read();
        msAssert.areEqual("book", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        String outerXml = XmlUtilPal.readOuterXml(reader);
        msAssert.areEqual(output, outerXml);
        reader.close();

        //position on whitespaces right after <book> element: outer xml should be empty string.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        reader.read();
        reader.read();
        msAssert.areEqual("", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.SIGNIFICANT_WHITESPACE, reader.getNodeType());
        msAssert.areEqual("", XmlUtilPal.readOuterXml(reader));
        reader.close();

        //position on <title> element.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"title".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual("title", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        msAssert.areEqual("<title>Pride And Prejudice</title>", XmlUtilPal.readOuterXml(reader));
        reader.close();

        //position on <price> element.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"price".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual("price", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        // readOuterXml() put attributes in alphabetical order
        msAssert.areEqual("<price eur=\"1.0\" usd=\"1.23\" />", XmlUtilPal.readOuterXml(reader));
        reader.close();
    }

    @Test
    public void testReadOuterXmlFromString() throws Exception
    {
        String input = "<book ISBN='1-861003-78' genre='novel' pubdate='1987'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <price eur=\"1.0\" usd=\"1.23\" />\n" +
                       "</book>";

        // readOuterXml() changes single quotes to double ones.
        // readOuterXml() put attributes in alphabetical order
        String output = "<book ISBN=\"1-861003-78\" genre=\"novel\" pubdate=\"1987\">\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <price eur=\"1.0\" usd=\"1.23\" />\n" +
                       "</book>";

        //position on <book> element start.
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        reader.read();
        msAssert.areEqual("book", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        String outerXml = XmlUtilPal.readOuterXml(reader);
        msAssert.areEqual(output, outerXml);
        reader.close();

        //position on whitespaces right after <book> element: outer xml should be empty string.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        reader.read();
        reader.read();
        msAssert.areEqual("", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.SIGNIFICANT_WHITESPACE, reader.getNodeType());
        msAssert.areEqual("", XmlUtilPal.readOuterXml(reader));
        reader.close();

        //position on <title> element.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"title".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual("title", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        msAssert.areEqual("<title>Pride And Prejudice</title>", XmlUtilPal.readOuterXml(reader));
        reader.close();

        //position on <price> element.
        reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"price".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual("price", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        // readOuterXml() put attributes in alphabetical order
        msAssert.areEqual("<price eur=\"1.0\" usd=\"1.23\" />", XmlUtilPal.readOuterXml(reader));
        reader.close();
    }

    @Test
    public void testReadOuterXmlWithXmlns() throws Exception
    {
        String bodyNode = "<w:body>\n" +
                          "\t\t<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">\n" +
                          "\t\t\t<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                          "\t\t\t<w10:blip r:embed=\"rId8\" cstate=\"print\" />\n" +
                          "\t\t\t\t<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                          "\t\t\t\t\t<a:nvGrpSpPr>\n" +
                          "\t\t\t\t\t\t<a:cNvPr id=\"0\" name=\"\" /> \n" +
                          "\t\t\t\t\t\t<a:cNvGrpSpPr /> \n" +
                          "\t\t\t\t\t</a:nvGrpSpPr>\n" +
                          "\t\t\t\t</lc:lockedCanvas>\n" +
                          "\t\t\t<w10:blip r:embed=\"rId8\" cstate=\"print\" />\n" +
                          "\t\t\t</a:graphicData>\n" +
                          "\t\t</a:graphic>\n" +
                          "\t</w:body>";
        String documentNode = "<w:document xmlns:w10=\"urn:schemas-microsoft-com:office:word\" " +
                                "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
                                "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" " +
                                "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">\n" +
                              "\t" + bodyNode + "\n" +
                              "</w:document>";
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?> \n" + documentNode;

        // readOuterXml() adds xmlns defined before current xml piece AND used inside the piece.
        // readOuterXml() put attributes in alphabetical order
        // xmlns added to node starting from which defined namespace used, for instance (see below):
        // xmlns:w added to w:body, xmlns:r added to a:blip.
        String bodyNodeOuter = "<w:body xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">\n" +
                               "\t\t<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">\n" +
                               "\t\t\t<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                               "\t\t\t<w10:blip cstate=\"print\" r:embed=\"rId8\" " +
                                    "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" " +
                                    "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" />\n" +
                               "\t\t\t\t<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                               "\t\t\t\t\t<a:nvGrpSpPr>\n" +
                               "\t\t\t\t\t\t<a:cNvPr id=\"0\" name=\"\" /> \n" +
                               "\t\t\t\t\t\t<a:cNvGrpSpPr /> \n" +
                               "\t\t\t\t\t</a:nvGrpSpPr>\n" +
                               "\t\t\t\t</lc:lockedCanvas>\n" +
                               "\t\t\t<w10:blip cstate=\"print\" r:embed=\"rId8\" " +
                                    "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" " +
                                    "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" />\n" +
                               "\t\t\t</a:graphicData>\n" +
                               "\t\t</a:graphic>\n" +
                               "\t</w:body>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"body".equals(reader.getLocalName()))
            reader.read();
        //check prerequisites
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        msAssert.areEqual("w", reader.getPrefix());
        msAssert.areEqual("body", reader.getLocalName());
        //read outer xml
        msAssert.areEqual(bodyNodeOuter, XmlUtilPal.readOuterXml(reader));
        //check cursor properly positioned after outer xml was read.
        msAssert.areEqual(XmlNodeType.WHITESPACE, reader.getNodeType()); // TEXT in Java
        msAssert.areEqual("", reader.getLocalName());
        reader.read();
        msAssert.areEqual(XmlNodeType.END_ELEMENT, reader.getNodeType());
        msAssert.areEqual("document", reader.getLocalName());
    }

    @Test
    public void testReadOuterXmlWithDefaultNamespace() throws Exception
    {
        String documentNode = "<w:document " +
                                "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
                                "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" " +
                                "xmlns=\"urn:schemas-microsoft-com:office:word\">\n" +
                              "\t<w:body>\n" +
                              "\t\t<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">\n" +
                              "\t\t\t<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                              "\t\t\t\t<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                              "\t\t\t\t\t<a:nvGrpSpPr>\n" +
                              "\t\t\t\t\t\t<a:cNvPr id=\"0\" name=\"\" /> \n" +
                              "\t\t\t\t\t\t<a:cNvGrpSpPr /> \n" +
                              "\t\t\t\t\t</a:nvGrpSpPr>\n" +
                              "\t\t\t\t</lc:lockedCanvas>\n" +
                              "\t\t\t</a:graphicData>\n" +
                              "\t\t</a:graphic>\n" +
                              "\t</w:body>\n" +
                              "</w:document>";
        String outerXml = "<w:document " +
                            "xmlns=\"urn:schemas-microsoft-com:office:word\" " +
                            "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
                            "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">\n" +
                          "\t<w:body>\n" +
                          "\t\t<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">\n" +
                          "\t\t\t<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                          "\t\t\t\t<lc:lockedCanvas xmlns:lc=\"http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas\">\n" +
                          "\t\t\t\t\t<a:nvGrpSpPr>\n" +
                          "\t\t\t\t\t\t<a:cNvPr id=\"0\" name=\"\" /> \n" +
                          "\t\t\t\t\t\t<a:cNvGrpSpPr /> \n" +
                          "\t\t\t\t\t</a:nvGrpSpPr>\n" +
                          "\t\t\t\t</lc:lockedCanvas>\n" +
                          "\t\t\t</a:graphicData>\n" +
                          "\t\t</a:graphic>\n" +
                          "\t</w:body>\n" +
                          "</w:document>";
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?> \n" + documentNode;

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));
        while (!"document".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        msAssert.areEqual("w", reader.getPrefix());
        msAssert.areEqual("document", reader.getLocalName());
        msAssert.areEqual(outerXml, XmlUtilPal.readOuterXml(reader));
    }

    /// <summary>
    /// WORDSNET-14030 ArgumentOutOfRangeException occurred if a CDATA section is encountered on reading
    /// outer XML. Now support of CDATA XML sections is implemented in the XmlUtilPal.ReadOuterXml method.
    /// </summary>
    @Test
    public void testReadOuterXmlWithCdata() throws Exception
    {
        String input = "<MyXml><![CDATA[ cdata stuff ]]></MyXml>";
        // JAVA StAX parser with coalescing setting (our default config) reports CDATA as CHARACTERS.
        // As a result - no exception in original 14030 issue.
        String output =
            "<MyXml> cdata stuff </MyXml>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getASCII().getBytes(input)));

        reader.read();
        msAssert.areEqual("MyXml", reader.getLocalName());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());

        String outerXml = XmlUtilPal.readOuterXml(reader);
        msAssert.areEqual(output, outerXml);

        reader.close();
    }
}

