// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.Map;
import java.util.ArrayList;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Evaluates xpath expression for INCLUDETEXT field and formats result in MS Word manner.
/// </summary>
/*static*/ class XPathEvaluator
{
	/* Simulation of static class by using private constructor */
	private XPathEvaluator()
	{}

    static String evaluate(org.w3c.dom.Document document, String xpath, String namespaceMappings) throws Exception
    {

        Map<String, String> mappings = extractNamespaceMappings(namespaceMappings);

        ArrayList<org.w3c.dom.Node> nodes;
        try
        {
            nodes = XmlUtilPal.selectNodes(document.getDocumentElement(), xpath, mappings);
        }
        catch(Exception e)
        {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (org.w3c.dom.Node node : nodes)
            msStringBuilder.append(builder, msString.trim(acceptXmlNode(node)));

        return builder.toString();
    }

    private static String acceptXmlNode(org.w3c.dom.Node node)
    {
        if (XmlUtilPal.isNodeType(node, XmlNodeType.WHITESPACE))
            return "";

        if (XmlUtilPal.isNodeType(node, XmlNodeType.TEXT))
            return acceptXmlNode((org.w3c.dom.Text)node);

        if (XmlUtilPal.isNodeType(node, XmlNodeType.ELEMENT))
            return acceptXmlNode((org.w3c.dom.Element)node);

        if (XmlUtilPal.isNodeType(node, XmlNodeType.ATTRIBUTE))
            return acceptXmlNode((org.w3c.dom.Attr)node);

        else
                Debug.assertTrue(false, msString.format("Unexpected node type: {0}.", node.getNodeType()));

        return "";
    }

    private static String acceptXmlNode(org.w3c.dom.Attr attribute)
    {
        return attribute.getNodeValue();
    }

    private static String acceptXmlNode(org.w3c.dom.Text text)
    {
        return text.getData();
    }

    private static String acceptXmlNode(org.w3c.dom.Element element)
    {
        StringBuilder builder = new StringBuilder();
        org.w3c.dom.Element previousChildAsElement = null;
        for (int i = 0; i < element.getChildNodes().getLength(); i++)
        {
            org.w3c.dom.Node childNode = element.getChildNodes().item(i);
            String childResult = acceptXmlNode(childNode);
            if (msString.isNullOrEmpty(childResult))
                continue;

            if (previousChildAsElement != null)
            {
                if (XmlUtilPal.isNodeType(childNode, XmlNodeType.ELEMENT))
                    builder.append(SEPARATOR);

                if (XmlUtilPal.isNodeType(childNode, XmlNodeType.TEXT) && isLastChildWhitespace(previousChildAsElement))
                    builder.append(SEPARATOR);
            }

            msStringBuilder.append(builder, childResult);

            previousChildAsElement = ms.as(childNode, org.w3c.dom.Element.class);
        }

        return builder.toString();
    }

    private static boolean isLastChildWhitespace(org.w3c.dom.Node element)
    {
        org.w3c.dom.Node lastChild = element.getLastChild();

        if (lastChild == null)
            return false;

        if (XmlUtilPal.isNodeType(lastChild, XmlNodeType.WHITESPACE))
            return true;

        if (XmlUtilPal.isNodeType(lastChild, XmlNodeType.ELEMENT))
            return isLastChildWhitespace(lastChild);

        return false;
    }

    private static Map<String, String> extractNamespaceMappings(String namespaceMappings) throws Exception
    {
        if (msString.isNullOrEmpty(namespaceMappings))
            return null;

        try
        {
            org.w3c.dom.Document document = XmlUtilPal.loadXml(msString.format("<root {0} />", namespaceMappings), false);

            Debug.assertTrue(document.getDocumentElement() != null);

            org.w3c.dom.NamedNodeMap attributes = document.getDocumentElement().getAttributes();

            if (attributes.getLength() == 0)
                return null;

            HashMap<String, String> result = new HashMap<String, String>(attributes.getLength());

            for (int i = 0; i < attributes.getLength(); i++)
                msDictionary.add(result, attributes.item(i).getLocalName(), attributes.item(i).getNodeValue());

            return result;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    private static final char SEPARATOR = ControlChar.SPACE_CHAR;
}

