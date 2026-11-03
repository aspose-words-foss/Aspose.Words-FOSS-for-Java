// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/04/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import java.util.Map;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.msArray;
import com.aspose.StringUtil;
import com.aspose.AnyXmlReader;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Custom XML utilities.
/// </summary>
/// <dev>
/// Several static methods taken from the <see cref="XmlMapping"/> class.
/// </dev>
/*static*/ class CustomXmlUtil
{
	/* Simulation of static class by using private constructor */
	private CustomXmlUtil()
	{}

    /// <summary>
    /// Extracts XML nodes.
    /// </summary>
    /// <remarks>
    /// Look once again for caller duplicate.
    /// </remarks>
    static ArrayList<org.w3c.dom.Node> extractXmlNodes(
        byte[] xmlData, String xPath, String prefixMappings, String storeItemId, XmlMappingContext ctx) throws Exception
    {
        ArrayList<org.w3c.dom.Node> xmlNodes = new ArrayList<org.w3c.dom.Node>();
        try
        {
            org.w3c.dom.Document xml = getXmlDocumentAndPlaceItToCache(xmlData, storeItemId, ctx);

            // WORDSJAVA-925 fixed for java only: CustomXmlNodesCache demands 2Gb heap to save docx to doc.
            // The cache is not used in java now.
            Map<String, String> namespaces = prefixMappingsAsDictionary(prefixMappings);

            xmlNodes = XmlUtilPal.selectNodes(xml.getDocumentElement(), xPath, namespaces);
            // andrnosk: WORDSNET-9940 Word extracts value if ng at first root element.
            if ((xmlNodes.size() == 0) && !msString.startsWith(xPath, "/", StringComparison.ORDINAL))
            {
                xmlNodes =
                    XmlUtilPal.selectNodes(xml.getDocumentElement(), msString.format("/{0}", xPath), namespaces);
            }

            // AM. Word extracts TermInfo/TermName element if Terms element was selected.
            // I don't fully understand this logic, seems to be some predefined XML element.
            if (isPartnerControlsNamespace(xmlNodes))
            {
                String partnerControlsPrefix = "";
                // Look for ParentControl namespace prefix.
                for (Map.Entry<String, String> entry : namespaces.entrySet())
                {
                    if (PARTNER_CONTROLS_NAMESPACE.equals(entry.getValue()))
                    {
                        partnerControlsPrefix = entry.getKey();
                        break;
                    }
                }

                String partnerControlsXPath =
                    msString.format("{0}/{1}:TermInfo/{1}:TermName", xPath, partnerControlsPrefix);
                xmlNodes = XmlUtilPal.selectNodes(xml.getDocumentElement(), partnerControlsXPath, namespaces);
            }
            return xmlNodes;
        }
        catch(Exception e)
        {
            // Do not throw in case invalid XPath.
        }

        return xmlNodes;
    }

    /// <summary>
    /// Collects and concatenates text from xml nodes collection.
    /// </summary>
    /// <remarks>
    /// Mimic MSW behavior, collects text from certain CustomXml nodes.
    /// Please, check @"ImportDocx\Markup\TestSdtGetSetMulti.docx" for the details.
    /// </remarks>
    static String getOverallInnerText(ArrayList<org.w3c.dom.Node> xmlNodes)
    {
        if (xmlNodes.size() == 0)
            return "";

        if (!isPartnerControlsNamespace(xmlNodes))
            return xmlNodes.get(0).getTextContent();

        StringBuilder sb = new StringBuilder();

        int length = xmlNodes.size();
        for (int i = 0; i < length; i++)
        {
            msStringBuilder.append(sb, xmlNodes.get(i).getTextContent());
            if (i != length - 1)
                msStringBuilder.append(sb, "; ");
        }

        return sb.toString();
    }

    /// <summary>
    /// Shows if namespace of these nodes is PartnerControls.
    /// MSW behaves in a special way for this case, we must consider this.
    /// </summary>
    static boolean isPartnerControlsNamespace(ArrayList<org.w3c.dom.Node> xmlNodes)
    {
        return (xmlNodes.size() > 0) && (PARTNER_CONTROLS_NAMESPACE.equals(xmlNodes.get(0).getNamespaceURI()));
    }

    /// <summary>
    /// Creates mapped XML node for given XPath.
    /// </summary>
    /// <dev>
    /// AM. Very draft solution, does not consider iterators and other complex XPath features.
    /// Will improve it on demand.
    /// </dev>
    static ArrayList<org.w3c.dom.Node> createMappedXmlNode(String xpath, String prefixMappings) throws Exception
    {
        MemoryStream tempStream = new MemoryStream();
        AnyXmlBuilder xmlBuilder = new AnyXmlBuilder(tempStream, true);

        Map<String, String> namespaces = prefixMappingsAsDictionary(prefixMappings);
        String[] pathParts = msString.split(xpath, '/');

        xmlBuilder.startDocument();
        for (String pathPart : pathParts)
        {
            if(msString.isNullOrEmpty(pathPart))
                continue;

            xmlBuilder.startElement(parseElementName(pathPart));

            String xmlns = parseNamespace(pathPart, namespaces);

            if(!msString.isNullOrEmpty(xmlns))
                xmlBuilder.writeAttributeString("xmlns", xmlns);
        }

        msArray.reverse(pathParts);
        for (String element : pathParts)
        {
            if(msString.isNullOrEmpty(element))
                continue;

            xmlBuilder.endElement(parseElementName(element));
        }

        xmlBuilder.endDocument();
        tempStream.setPosition(0);

        org.w3c.dom.Document xml = XmlUtilPal.loadXml(tempStream, true);
        ArrayList<org.w3c.dom.Node> xmlNodes = XmlUtilPal.selectNodes(xml.getDocumentElement(), xpath, namespaces);

        return xmlNodes;
    }

    /// <summary>
    /// Returns structured document tag PrefixMappings as Dictionary.
    /// </summary>
    /// <remarks>
    /// AM. I didn't implement caching it will not be called often till public API implemented.
    /// </remarks>
    private static Map<String, String> prefixMappingsAsDictionary(String prefixMappings)
    {
        // Speed.
        if (!StringUtil.hasChars(prefixMappings))
            return null;

        // AM. It's kind of trick here. I don't want to parse mapping manually and
        // construct fake XML document with such mapping. XmlReader does the rest.
        AnyXmlReader xmlMapping = new AnyXmlReader("<?xml version='1.0' encoding='utf-8' standalone='yes'?>" +
                                                   "<map " + prefixMappings + "/>", null);

        HashMap<String, String> result = new HashMap<String, String>();
        xmlMapping.moveToElement();

        // Do not ignore xmlns prefix.
        while (xmlMapping.moveToNextAttribute(false))
            msDictionary.add(result, xmlMapping.getLocalName(), xmlMapping.getValue());

        return result;
    }

    /// <summary>
    /// Gets an XML document and places it in the cache.
    /// </summary>
    private static org.w3c.dom.Document getXmlDocumentAndPlaceItToCache(
        byte[] xmlData, String storeItemId, XmlMappingContext ctx) throws Exception
    {
        // Look for cached XmlDocument.
        if ((ctx != null) && ctx.containsXmlDocument(storeItemId))
            return ctx.get(storeItemId);

        org.w3c.dom.Document xml = XmlUtilPal.loadXml(new MemoryStream(xmlData), true);

        if (ctx != null)
            ctx.add(storeItemId, xml);

        return xml;
    }

    /// <summary>
    /// Parses element name from XPath part.
    /// </summary>
    private static String parseElementName(String pathPart)
    {
        String elementName = "";

        // Extract element name.
        if (pathPart.contains(":"))
            elementName = msString.split(pathPart, ':')[1];

        // Remove possible iterator.
        if(elementName.contains("["))
            elementName = msString.split(elementName, '[')[0];

        return elementName;
    }

    /// <summary>
    /// Parses namespace from XPath part.
    /// </summary>
    private static String parseNamespace(String pathPart, Map<String, String> namespaces)
    {
        String ns = "";

        if (pathPart.contains(":"))
            ns = msString.split(pathPart, ':')[0];

        String value = null;
        Ref<String> referenceToValue = new Ref<String>(value);
        boolean outRefCondition0 = msDictionary.tryGetValue(namespaces, ns, /*out*/ referenceToValue);
        value = referenceToValue.get();
        if (outRefCondition0)
            return value;
        else
            return "";
    }

    /// <summary>
    /// PartnerControls namespace.
    /// </summary>
    private static final String PARTNER_CONTROLS_NAMESPACE =
        "http://schemas.microsoft.com/office/infopath/2007/PartnerControls";
}

