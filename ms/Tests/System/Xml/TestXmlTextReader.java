// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2008 by Konstantin Sidorenko
// 2016/02/01 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Xml.XmlTextReader;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Xml.XmlTextReader.DtdProcessing;


@Test
public class TestXmlTextReader
{
    @Test
    public void testMoveToContent() throws Exception
    {
        String[] inputs =
        {
            "<price>123.4</price>",
            "<!-- some test Comment --><?processing\n  instruction?><price>123.4</price>",
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><price>123.4</price>",
        };

        for (String inputStr : inputs)
        {
            MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(inputStr));
            XmlTextReader reader = new XmlTextReader(stream);

            msAssert.areEqual(XmlNodeType.ELEMENT, reader.moveToContent());
            msAssert.areEqual("price", reader.getLocalName());
            msAssert.areEqual("123.4", reader.readString());

            reader.close();
        }
    }

    @Test
    public void testIsEmptyElement() throws Exception
    {
        String input = "<empty/>";
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));


        msAssert.isTrue(reader.read());
        msAssert.areEqual(XmlNodeType.ELEMENT, reader.getNodeType());
        msAssert.areEqual("empty", reader.getLocalName());

        //JAVA: IsEmptyElement property is not applicable for java's XMLStreareader since StAX parser
        //internally converts empty Elements to ordinary non-empty Elements.

        reader.close();
    }

    @Test
    public void testIsEmptyElementException() throws Exception
    {
        String input = "<empty/>";
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.isTrue(reader.isEmptyElement());
        reader.close();
    }

    @Test
    public void testLocalName() throws Exception
    {
        String input = "<book xmlns:bk='urn:samples'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <bk:genre>novel</bk:genre>\n" +
                       "</book>\n";
        String output = "<book><title><bk:genre> The namespace URI is urn:samples";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();
        while (reader.read())
        {
            if (reader.isStartElement())
            {
                String prefix = reader.getPrefix();
                if (msString.isNullOrEmpty(prefix))
                    msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(sb, "<"), reader.getLocalName()), ">");
                else
                {
                    msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(sb, "<"), reader.getPrefix()), ":"), reader.getLocalName()), ">");
                    msStringBuilder.append(msStringBuilder.append(sb, " The namespace URI is "), reader.getNamespaceURI());
                }
            }
        }

        msAssert.areEqual(output, sb.toString());
        reader.close();
    }

    @Test
    public void testLocalNameAll() throws Exception
    {
        String input = "<book xmlns:bk='urn:samples'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <bk:genre>novel</bk:genre>\n" +
                       "</book>\n";

        //Note: MS hasn't END_DOCUMENT node type.
        String outputMS = "<Element:book><Whitespace:>" +
                          "<Element:title><Text:><EndElement:title><Whitespace:>" +
                          "<Element:genre><Text:><EndElement:genre><Whitespace:>" +
                          "<EndElement:book><Whitespace:>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();
        while (reader.read())
            msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(sb, "<"), XmlNodeType.toString(reader.getNodeType())), ":"), reader.getLocalName()), ">");

        String output = "<START_ELEMENT:book><SPACE:>" +
                        "<START_ELEMENT:title><CHARACTERS:><END_ELEMENT:title><SPACE:>" +
                        "<START_ELEMENT:genre><CHARACTERS:><END_ELEMENT:genre><SPACE:>" +
                        "<END_ELEMENT:book><END_DOCUMENT:>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testNameSpaceURIAndPrefix() throws Exception
    {
        String input = "<book xmlns:bk='urn:samples'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <bk:genre>novel</bk:genre>\n" +
                       "</book>\n";

        //Note: MS hasn't END_DOCUMENT node type.
        String outputMS = "<Element:book::><Whitespace:::>" +
                          "<Element:title::><Text:::><EndElement:title::><Whitespace:::>" +
                          "<Element:genre:bk:urn:samples><Text:::><EndElement:genre:bk:urn:samples><Whitespace:::>" +
                          "<EndElement:book::><Whitespace:::>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        StringBuilder sb = new StringBuilder();
        while (reader.read())
            appendNodeWithPrefixAndNamespace(sb, reader);

        String output = "<START_ELEMENT:book::><SPACE:::>" +
                        "<START_ELEMENT:title::><CHARACTERS:::><END_ELEMENT:title::><SPACE:::>" +
                        "<START_ELEMENT:genre:bk:urn:samples><CHARACTERS:::><END_ELEMENT:genre:bk:urn:samples><SPACE:::>" +
                        "<END_ELEMENT:book::><END_DOCUMENT:::>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testValue() throws Exception
    {
        String input = "<?xml version=\"1.0\"?>\n" +
                       "<!-- This is a sample XML document -->\n" +
                       "<!DOCTYPE Items [<!ENTITY number \"123\">]>\n" +
                       "<Items attr=\"value\">\n" +
                       "  <Item>Test with an entity: &number;</Item>\n" +
                       "  <Item>test with a child Element <more/> stuff</Item>\n" +
                       "  <Item>test with a CDATA section <![CDATA[<456>]]> def</Item>\n" +
                       "  <Item>Test with an char entity: &#65;</Item>\n" +
                       "  <!-- Fourteen chars in this Element.-->\n" +
                       "  <Item>1234567890ABCD</Item>\n" +
                       "</Items>\n";

        //Note: MS hasn't END_DOCUMENT node type.
        //Java skips XmlDeclaration and DOCTYPE Elements.
        String outputMS = "<XmlDeclaration:xml:version=\"1.0\"><Whitespace::\n>" +
                          "<Comment:: This is a sample XML document ><Whitespace::\n>" +
                          "<DocumentType:Items:<!ENTITY number \"123\">><Whitespace::\n>" +
                          "<Element:Items:><Whitespace::\n" +
                          "  ><Element:Item:><Text::Test with an entity: ><EntityReference:number:><EndElement:Item:><Whitespace::\n" +
                          "  ><Element:Item:><Text::test with a child Element ><Element:more:><Text:: stuff><EndElement:Item:><Whitespace::\n" +
                          "  ><Element:Item:><Text::test with a CDATA section ><CDATA::<456>><Text:: def><EndElement:Item:><Whitespace::\n" +
                          "  ><Element:Item:><Text::Test with an char entity: A><EndElement:Item:><Whitespace::\n" +
                          "  ><Comment:: Fourteen chars in this Element.><Whitespace::\n" +
                          "  ><Element:Item:><Text::1234567890ABCD><EndElement:Item:><Whitespace::\n" +
                          "><EndElement:Items:><Whitespace::\n>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();
        while (reader.read())
            appendNode(sb, reader);

        String output = "<COMMENT:: This is a sample XML document ><DTD::<!ENTITY number \"123\">><START_ELEMENT:Items:><SPACE::\n" +
                          "  ><START_ELEMENT:Item:><CHARACTERS::Test with an entity: ><ENTITY_REFERENCE:number:123><END_ELEMENT:Item:><SPACE::\n" +
                          "  ><START_ELEMENT:Item:><CHARACTERS::test with a child Element ><START_ELEMENT:more:><END_ELEMENT:more:><CHARACTERS:: stuff><END_ELEMENT:Item:><SPACE::\n" +
                          "  ><START_ELEMENT:Item:><CHARACTERS::test with a CDATA section <456> def><END_ELEMENT:Item:><SPACE::\n" +
                          "  ><START_ELEMENT:Item:><CHARACTERS::Test with an char entity: A><END_ELEMENT:Item:><SPACE::\n" +
                          "  ><COMMENT:: Fourteen chars in this Element.><SPACE::\n" +
                          "  ><START_ELEMENT:Item:><CHARACTERS::1234567890ABCD><END_ELEMENT:Item:><SPACE::\n" +
                        "><END_ELEMENT:Items:><END_DOCUMENT::>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }


    @Test
    public void testNonLatinNamesAndValues() throws Exception
    {
        String input = "<?xml version=\"1.0\"?>\n" +
                       "<!-- Это пример XML документа с русскими символами -->\n" +
                       "<Items атрибут=\"значение\">\n" +
                       "  <Элемент>просто текст</Элемент>\n" +
                       "</Items>\n";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.COMMENT);
        msAssert.areEqual(reader.getValue(), " Это пример XML документа с русскими символами ");

        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);
        msAssert.areEqual(reader.getLocalName(), "Items");

        reader.moveToFirstAttribute();
        msAssert.areEqual(reader.getLocalName(), "атрибут");
        msAssert.areEqual(reader.getValue(), "значение");

        reader.read();//characters(whitespaces)
        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);
        msAssert.areEqual(reader.getLocalName(), "Элемент");

        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.TEXT);
        msAssert.areEqual(reader.getValue(), "просто текст");

        reader.read();//</Элемент>
        reader.read();//characters(whitespaces)
        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.END_ELEMENT);
        msAssert.areEqual(reader.getLocalName(), "Items");

        reader.close();
    }

    @Test
    public void testXmlReaderMoveToNextAttribute() throws Exception
    {
        String input = "<book genre='novel' ISBN='1-861003-78' pubdate='1987'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <pice usd=\"1.23\" eur=\"1.0\" />\n" +
                       "</book>";
        String outputMS = "<Element:book:><Attribute:genre:novel><Attribute:ISBN:1-861003-78><Attribute:pubdate:1987><Whitespace::\n" +
                          "  ><Element:title:><Text::Pride And Prejudice><EndElement:title:><Whitespace::\n" +
                          "  ><Element:pice:><Attribute:usd:1.23><Attribute:eur:1.0><Whitespace::\n" +
                          "><EndElement:book:>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();

        while (reader.read())
        {
            appendNode(sb, reader);
            while (reader.moveToNextAttribute())
                appendNode(sb, reader);
        }

        String output = "<START_ELEMENT:book:><ATTRIBUTE:genre:novel><ATTRIBUTE:ISBN:1-861003-78><ATTRIBUTE:pubdate:1987><SPACE::\n" +
                        "  ><START_ELEMENT:title:><CHARACTERS::Pride And Prejudice><END_ELEMENT:title:><SPACE::\n" +
                        "  ><START_ELEMENT:pice:><ATTRIBUTE:usd:1.23><ATTRIBUTE:eur:1.0><END_ELEMENT:pice:><SPACE::\n" +
                        "><END_ELEMENT:book:><END_DOCUMENT::>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testXmlReaderNonLatinAttribute() throws Exception
    {
        String input = "<book genre='роман' ISBN='1-861003-78' pubdate='1987'>\n" +
                       "  <title>Преступление и Наказание</title>\n" +
                       "  <pice руб=\"123\" eur=\"1.0\" />\n" +
                       "</book>";
        String outputMS = "<Element:book:><Attribute:genre:роман><Attribute:ISBN:1-861003-78><Attribute:pubdate:1987><Whitespace::\n" +
                          "  ><Element:title:><Text::Преступление и Наказание><EndElement:title:><Whitespace::\n" +
                          "  ><Element:pice:><Attribute:руб:123><Attribute:eur:1.0><Whitespace::\n" +
                          "><EndElement:book:>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();

        while (reader.read())
        {
            appendNode(sb, reader);
            while (reader.moveToNextAttribute())
                appendNode(sb, reader);
        }

        String output = "<START_ELEMENT:book:><ATTRIBUTE:genre:роман><ATTRIBUTE:ISBN:1-861003-78><ATTRIBUTE:pubdate:1987><SPACE::\n" +
                        "  ><START_ELEMENT:title:><CHARACTERS::Преступление и Наказание><END_ELEMENT:title:><SPACE::\n" +
                        "  ><START_ELEMENT:pice:><ATTRIBUTE:руб:123><ATTRIBUTE:eur:1.0><END_ELEMENT:pice:><SPACE::\n" +
                        "><END_ELEMENT:book:><END_DOCUMENT::>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testHasAttributes() throws Exception
    {
        String input = "<book genre='novel' ISBN='1-861003-78' pubdate='1987'>\n" +
                       "  <title>Pride And Prejudice</title>\n" +
                       "  <pice usd=\"1.23\" eur=\"1.0\" />\n" +
                       "</book>";
        String outputMS =
                "<Element:book:>True<Attribute:genre:novel>True<Attribute:ISBN:1-861003-78>True<Attribute:pubdate:1987>True<Whitespace::\n" +
                "  >False<Element:title:>False<Text::Pride And Prejudice>False<EndElement:title:>False<Whitespace::\n" +
                "  >False<Element:pice:>True<Attribute:usd:1.23>True<Attribute:eur:1.0>True<Whitespace::\n" +
                ">False<EndElement:book:>False";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();

        while (reader.read())
        {
            appendNode(sb, reader);
            sb.append(reader.hasAttributes());
            while (reader.moveToNextAttribute())
            {
                appendNode(sb, reader);
                sb.append(reader.hasAttributes());
            }
        }
        String output =
                "<START_ELEMENT:book:>true<ATTRIBUTE:genre:novel>true<ATTRIBUTE:ISBN:1-861003-78>true<ATTRIBUTE:pubdate:1987>true<SPACE::\n" +
                "  >false<START_ELEMENT:title:>false<CHARACTERS::Pride And Prejudice>false<END_ELEMENT:title:>false<SPACE::\n" +
                "  >false<START_ELEMENT:pice:>true<ATTRIBUTE:usd:1.23>true<ATTRIBUTE:eur:1.0>true<END_ELEMENT:pice:>false<SPACE::\n" +
                ">false<END_ELEMENT:book:>false<END_DOCUMENT::>false";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testCharacterEntitiesInText() throws Exception
    {
        String input = "<start Comment=\"Just a plain string with entities:\">" +
                      //note: 0xC, 0x1, 0x7, etc. is illegal character entities in xml 1.0 (and in java) but legal in .Net.
                      //		              "&amp; &lt; &gt; \" ' \r &#xC; &#x1; &#x7; ." +
                      "&amp; &lt; &gt; \" ' \r ." +
                      "</start>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.areEqual(reader.getLocalName(), "start");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);

        reader.read();
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.TEXT);
        String Text = reader.readString();
        msAssert.areEqual(Text, "& < > \" ' \r .");

        reader.close();
    }

    @Test
    public void testCharacterEntitiesInAttributes() throws Exception
    {
        String input = "<start Comment=\"&amp; &lt; &gt; \r.\">" +
                      "</start>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.areEqual(reader.getLocalName(), "start");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);

        reader.moveToFirstAttribute();
        msAssert.areEqual(reader.getLocalName(), "Comment");
        String value = reader.getValue();
        // \r in the attribute value is replaced by a space due the normalization.
        // Normalization is always true in java reader because of Xml standard.
        // WORDSJAVA-1069 Tab inside a list number in TOC are converted to space.
        // Now we disable normalization according to .NET behaviour.
        msAssert.areEqual(value, "& < > \r.");

        reader.close();
    }

    @Test
    public void testReadOuterXmlCharacterEntitiesInText() throws Exception
    {
        String input = "<start Comment=\"Just a plain string with entities:\">" +
                      //note: 0xC, 0x1, 0x7, etc. is illegal character entities in xml 1.0 (and in java) but legal in .Net.
                      //		              "&amp; &lt; &gt; \" ' \r &#xC; &#x1; &#x7; ." +
                      "&amp; &lt; &gt; \" ' \r ." +
                      "</start>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));

        reader.read();
        msAssert.areEqual(reader.getLocalName(), "start");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);

        String outerXml = reader.readOuterXml();
        msAssert.areEqual(outerXml, input);
        reader.close();
    }

    @Test
    public void testWhiteSpaces() throws Exception
    {
        String input = "<pict>\r\n" +
                "\t<scriptText> \r\n" +
                "\r\n" +
                "cnnad_createAd...\r\n" +
                "\r\n" +
                "</scriptText>\r\n" +
                "</pict>";
        String outputMS =
                "<Element:pict:><Whitespace::\r\n" +
                        "\t><Element:scriptText:><Text:: \r\n" +
                        "\r\n" +
                        "cnnad_createAd...\r\n" +
                        "\r\n" +
                        "><EndElement:scriptText:><Whitespace::\r\n" +
                        "><EndElement:pict:>";

        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        StringBuilder sb = new StringBuilder();

        while (reader.read())
            appendNode(sb, reader);

        String output =
                "<START_ELEMENT:pict:><SPACE::\r\n" +
                        "\t><START_ELEMENT:scriptText:><CHARACTERS:: \r\n" +
                        "\r\n" +
                        "cnnad_createAd...\r\n" +
                        "\r\n" +
                        "><END_ELEMENT:scriptText:><SPACE::\r\n" +
                        "><END_ELEMENT:pict:><END_DOCUMENT::>";

        msAssert.areEqual(sb.toString(), output);
        reader.close();
    }

    @Test
    public void testReadInnerXml() throws Exception
    {
        String firstBookTitleInnerText = "The Handmaid's Tale";
        String firstBookInnerText = "\n" +
                                    "    <title>" + firstBookTitleInnerText + "</title>\n" +
                                    "    <price>19.95</price>\n" +
                                    "  ";
        String bookstoreInnerText = "\n" +
                                    "  <book genre=\"novel\" ISBN=\"10-861003-324\">" +
                                    firstBookInnerText +
                                    "</book>\n" +
                                    "  <book genre=\"novel\" ISBN=\"1-861001-57-5\">\n" +
                                    "    <title>Pride And Prejudice</title>\n" +
                                    "    <price>24.95</price>\n" +
                                    "  </book>\n";

        String input = "<!--sample XML fragment-->\n" +
                       "<bookstore>" +
                       bookstoreInnerText +
                       "</bookstore>\n" +
                       "\n";

        //position on Comments right at the begin: inner xml should be empty string.
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        reader.setNamespaces(false);
        reader.setDtdProcessing(DtdProcessing.PROHIBIT);

        reader.read();
        msAssert.areEqual(reader.getLocalName(), "");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.COMMENT);
        msAssert.areEqual(reader.readInnerXml(), "");
        reader.close();

        //position on <bookstore> Element start.
        reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        while (!"bookstore".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual(reader.getLocalName(), "bookstore");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);
        msAssert.areEqual(reader.readInnerXml(), bookstoreInnerText);
        reader.close();

        //position on the first <book> Element start.
        reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        while (!"book".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual(reader.getLocalName(), "book");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);
        msAssert.areEqual(reader.readInnerXml(), firstBookInnerText);
        reader.close();

        //position on the first <book>'s <title> Element start.
        reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(input)));
        while (!"title".equals(reader.getLocalName()))
            reader.read();
        msAssert.areEqual(reader.getLocalName(), "title");
        msAssert.areEqual(reader.getNodeType(), XmlNodeType.ELEMENT);
        msAssert.areEqual(reader.readInnerXml(), firstBookTitleInnerText);
        reader.close();
    }


    private void appendNode(StringBuilder sb, XmlTextReader reader)
    {
        msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(sb, "<"), XmlNodeType.toString(reader.getNodeType())), ":"), reader.getLocalName()), ":"), reader.getValue()), ">");
    }

    private static void appendNodeWithPrefixAndNamespace(StringBuilder sb, XmlTextReader reader)
    {
        msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(sb, "<"), XmlNodeType.toString(reader.getNodeType())), ":"), reader.getLocalName()), ":"), reader.getPrefix()), ":"), reader.getNamespaceURI()), ">");
    }

    @Test
    public void testTabs() throws Exception
    {
        String xml = "<node attr=\"\t0\" />";
        //xml = xml.replace("\t", "&#x9;");
        XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(xml)));
        reader.setNamespaces(false);
        reader.setDtdProcessing(DtdProcessing.PROHIBIT);
        msAssert.isTrue(reader.read());
        msAssert.isTrue(reader.moveToFirstAttribute());
        msAssert.areEqual(reader.getValue(), "\t0");
    }

    @Test
    public void testJiraJ1719() throws Exception
    {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                        "<w:hdr xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" " +
                        "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">" +
                        "<w:p>" +
                        "<w:r>" +
                        "<w:drawing>" +
                        "<wp:inline distT=\"0\" distR=\"114300\" distL=\"114300\" distB=\"0\">" +
                        "<a:graphic>" +
                        "<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                        "</a:graphicData>" +
                        "</a:graphic>" +
                        "</wp:inline>" +
                        "</w:drawing>" +
                        "</w:r>" +
                        "</w:hdr>";

        String message = null;

        try
        {
            XmlTextReader reader = new XmlTextReader(new MemoryStream(Encoding.getUTF8().getBytes(xml)));
            while (reader.read())
            {
            }
        }
        catch (Exception e)
        {
            // .Net: XmlException, Java: XMLStreamException
            message = e.getMessage();
        }

        // Neutral message is "Undeclared namespace prefix \"a\"" - but the message can be localized when system locale is non-english.
        // It is also can be single quotes '' instead of double quotes "" on different system locales.
        msAssert.isTrue(message.contains("\"a\"") || message.contains("'a'"), message);
    }
}

