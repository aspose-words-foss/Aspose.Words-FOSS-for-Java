// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/09/2021 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Constructs an HTML document tree according to the XHTML specification.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/xhtml1/
/// </remarks>
class XhtmlTreeConstructor
{
    /// <summary>
    /// Parses an XHTML document and returns the HTML tree.
    /// </summary>
    /// <param name="stream">The stream with data of the XHTML document.</param>
    /// <returns>The root element of the document's HTML tree.</returns>
    static HtmlDocument construct(Stream stream)
    {
        XhtmlTreeConstructor constructor = new XhtmlTreeConstructor();
        constructor.run(stream);
        return new HtmlDocument(constructor.mRootElement, HtmlDocumentMode.STANDARDS);
    }

    /// <summary>
    /// Parses XHTML from the stream into an HTML tree.
    /// </summary>
    private void run(Stream stream)
    {

        // Let the parser expand entities for us. Note that support for external entities is turned off,
        // like in Adobe Digital Editions. This is done by specifying a null XML resolver when creating the reader.
        XhtmlXmlResolver xmlResolver = new XhtmlXmlResolver();
        XmlTextReader reader = XmlUtilPal.createXmlTextReader(stream, xmlResolver);

        while (reader.read())
        {
            xmlResolver.setCurrentNodeType(reader.getNodeType());
            switch (reader.getNodeType())
            {
                case XmlNodeType.ELEMENT:
                {
                    HtmlAttributeCollection attributes = new HtmlAttributeCollection();
                    if (reader.hasAttributes())
                    {
                        while (reader.moveToNextAttribute())
                        {
                            attributes.add(new HtmlAttribute(reader.getName(), reader.getValue()));
                        }
                    }
                    reader.moveToElement();
                    processStartTag(reader.getName(), reader.getNamespaceURI(), reader.isEmptyElement(), attributes);
                    break;
                }
                case XmlNodeType.END_ELEMENT:
                {
                    //Java XmlTextReader doesn't understand self-closing tags by default. Let's help him a little.
                    if (isSelfClosingTag(reader.getName()))
                        break;

                    processEndTag(reader.getName(), reader.getNamespaceURI());
                    break;
                }
                case XmlNodeType.TEXT:
                case XmlNodeType.WHITESPACE:
                case XmlNodeType.CDATA:
                case XmlNodeType.SIGNIFICANT_WHITESPACE:
                {
                    // Currently, we had to replace all CR control characters because HTML parser
                    // doesn't handle this character in accordance with parsing HTML documents.
                    String text = reader.getValue().replace("\r\n", "\n").replace("\r", "\n");
                    processText(text);
                    break;
                }
                default:
                {
                    // Discard other types of nodes.
                    break;
                }
            }
        }
    }

    private void processStartTag(
        String name,
        String ns,
        boolean isSelfClosing,
        HtmlAttributeCollection attributes)
    {
        // A XHTML document must have only one root. An XML exception will be thrown by the XML parser otherwise.
        if (mRootElement == null)
        {
            // According to the specification, the first tag in a XHTML document must be 'html' but
            // we don't check this here. HTML reader can resolve it better.
            HtmlElementNode htmlElement = createElement(name, ns, attributes);
            mRootElement = htmlElement;
            mOpenedElements.add(htmlElement);
        }
        else if (!mOpenedElements.isEmpty())
        {
            insertHtmlElement(name, ns, attributes);
        }

        if (isSelfClosing)
        {
            processEndTag(name, ns);
        }
    }

    private void processEndTag(String name, String ns)
    {
        Debug.assertTrue(!mOpenedElements.isEmpty());

        // In Java, some tags are handled differently than in .NET.
        // Therefore, situations with empty mOpenedElements can occur
        if (mOpenedElements.isEmpty())
            return;

        HtmlElementNode poppedElement = mOpenedElements.getLast();
        Debug.assertTrue(msString.equals(poppedElement.getName(), name));
        Debug.assertTrue(msString.equals(poppedElement.getNamespace(), ns));
        mOpenedElements.removeLast();
    }

    private void insertHtmlElement(
        String name,
        String ns,
        HtmlAttributeCollection attributes)
    {
        insertElement(name, ns, attributes);
    }

    private void insertElement(
        String name,
        String ns,
        HtmlAttributeCollection attributes)
    {
        HtmlElementNode element = createElement(name, ns, attributes);

        insertIntoCurrentNode(element);
        mOpenedElements.add(element);
    }

    private void processText(String text)
    {
        // Discard content outside or directly inside HTML node.
        if (!mOpenedElements.isEmpty() && (!"html".equals(mOpenedElements.getLast().getName())))
        {
            HtmlTextNode textNode = new HtmlTextNode(text);
            insertIntoCurrentNode(textNode);
        }
    }

    private void insertIntoCurrentNode(HtmlNode node)
    {
        Debug.assertTrue(node != null);

        HtmlElementNode currentNode = mOpenedElements.getLast();
        currentNode.getChildren().add(node);
    }

    private static HtmlElementNode createElement(
        String name,
        String ns,
        HtmlAttributeCollection attributes)
    {
        return new HtmlElementNode(name, ns, attributes);
    }

    /// <summary>
    /// The root element of the document's HTML tree.
    /// </summary>
    private HtmlElementNode mRootElement;

    /// <summary>
    /// The list of HTML elements that are still opened. The most recently opened element comes last.
    /// </summary>
    private /*final*/ HtmlOpenedElementList mOpenedElements = new HtmlOpenedElementList();

    private boolean isSelfClosingTag(String inputTag)
    {
        for (int i = 0; i < mSelfClosingTag.length; i++)
        {
            if (msString.equals(inputTag, mSelfClosingTag[i], com.aspose.ms.System.StringComparison.ORDINAL_IGNORE_CASE))
                return true;
        }
        return false;
    }

    private String[]  mSelfClosingTag =  new String[] {"area", "base", "br", "col", "embed", "hr", "img",
                                                       "input", "link", "meta", "param", "source", "track",
                                                       "wbr", "script", "command", "keygen", "menuitem"};
}

