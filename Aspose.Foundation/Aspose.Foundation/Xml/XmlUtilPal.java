// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2010 by Roman Korchagin

package com.aspose;

import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.Xml.*;
import com.aspose.ms.System.msString;
import com.aspose.ms.java.IO.NetOutputStream;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.XmlUtils;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.java.util.JavaUtil;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.xml.DOMConverter;
import com.aspose.ms.java.xml.DotNetEscapingWriter;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLResolver;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;


/// <summary>
/// This class is to be ported manually to Java.
/// </summary>
public /*static*/ class XmlUtilPal
{
    /* Simulation of static class by using private constructor */
    private XmlUtilPal()
    {}

    /// <summary>
    /// Creates and returns a <see cref="XmlTextWriter"/>.
    /// This has to be abstracted because we need some special code for creating on Mono,
    /// but we don't have XmlTextWriter(StreamWriter) ctor on Java.
    /// </summary>
    public static XmlTextWriter createXmlTextWriter(Stream stream, Encoding encoding) throws Exception
    {
        //JAVA-deleted: We can't emulate .Net formatting in java, so let's do not format java out at all.
        // AM: Create XmlTextWriter such way in order to explicitly set newline characters for Mono.
        // StreamWriter sw = new StreamWriter(stream, encoding);
        // sw.setNewLine("\r\n");
        return new XmlTextWriter(stream, encoding);
    }

    public static XmlTextReader createXmlTextReader(Stream stream)
    {
        // RK This disables resolving DTD references during load. I think it improves speed and I don't see why we could possibly want DTDs resolved.
        return new XmlTextReader(stream, false, true);
    }

    /// <summary>
    /// Creates and returns a <see cref="XmlTextReader"/> for reading XML from a string.
    /// The string can be a complete document or a fragment.
    /// </summary>
    public static XmlTextReader createXmlTextReader(Stream stream, XMLResolver resolver)
    {
        // TextReader with custom XMLResolver
        return new XmlTextReader(stream, resolver);
    }

    /// <summary>
    /// Creates and returns a <see cref="XmlTextReader"/> for reading XML from a string.
    /// The string can be a complete document or a fragment.
    /// If the string is a fragment, you can specify optional hashtable of prefixes to namespaces mapping.
    /// </summary>
    public static XmlTextReader createXmlTextReader(String xml, Map<String, String> namespaces)
    {
        // FIX WORDSJAVA-21848 - Reading VML shape library and DML shapes reads with ignoring namespaces.
        // RK This disables resolving DTD references during load. I think it improves speed and I don't see why we could possibly want DTDs resolved.
        return new XmlTextReader(xml, namespaces, true);
    }

    /// <summary>
    /// Loads an XML string into a <see cref="XmlDocument"/>.
    /// </summary>
    public static Document loadXml(String xml, boolean preserveWhitespace) throws Exception
    {
        // WORDSJAVA-1218, WORDSJAVA-1229
        return new DOMConverter().parse(removeUnsupportedAttributes(xml), getCachedDocumentBuilderFactory(preserveWhitespace).newDocumentBuilder());
    }

    /// <summary>
    /// Loads a stream into a <see cref="XmlDocument"/>.
    /// </summary>
    public static Document loadXml(Stream stream, boolean preserveWhitespace) throws Exception
    {
        // WORDSJAVA-1218, WORDSJAVA-1229
        Document xml = new DOMConverter().parse(stream, getCachedDocumentBuilderFactory(preserveWhitespace).newDocumentBuilder());

        // builder closes input stream, but we don't need this.
        if (stream instanceof MemoryStream)
            ((MemoryStream) stream).reopen();
        return xml;
    }

    public static void write(Node domXml, Stream stream)
    {
        try
        {
            DOMSource source = new DOMSource(domXml);
            StreamResult result = new StreamResult(new NetOutputStream(stream));
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);

            stream.flush();
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }
    }

    public static String getOuterXml(Node domXml) throws Exception
    {
        MemoryStream writer = new MemoryStream();
        XmlUtils.save(domXml, writer);
        return Encoding.getUTF8().getString(writer.getBuffer(), 0, (int) writer.getLength());
    }

    public static String getOuterXmlOld(Document doc) throws IOException
    {
        MemoryStream stream = new MemoryStream();
        saveXml(doc, stream);
        // RK I am hoping it is UTF-8. What else could it be?
        return Encoding.getUTF8().getString(stream.getBuffer(), 0, (int) stream.getLength());
    }

    public static String getOuterXmlOld(Node node) throws IOException
    {
        MemoryStream stream = new MemoryStream();
        saveXml(node, stream);
        // RK I am hoping it is UTF-8. What else could it be?
        return Encoding.getUTF8().getString(stream.getBuffer(), 0, (int) stream.getLength());
    }

    public static void saveXml(Document doc, Stream stream)
    {
        DOMConverter.saveXml(doc, stream);
    }

    public static void saveXml(Node node, Stream stream)
    {
        DOMConverter.saveXml(node, stream);
    }

    /// <summary>
    /// Reads the contents, including markup, representing the current node and all its children.
    /// Skips ignorable whitespace after the end till the next node.
    /// </summary>
    public static String readOuterXml(XmlTextReader reader)
    {
        return readOuterXml(reader, null, null);
    }

    /// <summary>
    /// Reads the contents, including markup, representing the current node and all its children.
    /// Skips ignorable whitespace after the end till the next node. 
    /// Allows providing the <see cref="IXmlUpdater"/> interface to update XML on reading.
    /// Also, allows providing the <see cref="XmlTextReaderNamespaceStorage"/> which populates with
    /// used XML namespaces while reading.
    /// </summary>
    public static String readOuterXml(XmlTextReader reader, IXmlUpdater xmlUpdater,
        XmlTextReaderNamespaceStorage namespaceStorage)
    {
        if (reader.getNodeType() != XmlNodeType.ELEMENT)
            return "";

        if (namespaceStorage == null)
            namespaceStorage = new XmlTextReaderNamespaceStorage(null);

        StringBuilder builder = new StringBuilder();
        readOuterXml(reader, builder, xmlUpdater, namespaceStorage);
        // JAVA HACK: We assume that all whitespaces after end element are ignorable and can be skipped.
        reader.read2();
        return builder.toString();
    }

    private static void readOuterXml(XmlTextReader reader, StringBuilder builder,
        IXmlUpdater xmlUpdater, XmlTextReaderNamespaceStorage namespaceStorage)
    {
        readStartElement(reader, builder, namespaceStorage, xmlUpdater);

        if (reader.isEmptyElement())
        {
            // Go to the fictive END_ELEMENT.
            // This element exists only in Woodstox model but doesn't exist in a real document.
            reader.read();
            return;
        }

        do
        {
            reader.read();

            switch (reader.getNodeType())
            {
                case XmlNodeType.ELEMENT:
                {
                    // calls himself recursively
                        readOuterXml(reader, builder, 
                            xmlUpdater, new XmlTextReaderNamespaceStorage(namespaceStorage));
                    break;
                }
                case XmlNodeType.END_ELEMENT:
                {
                    builder.append("</").append(reader.getName()).append(">");
                    return;
                }
                case XmlNodeType.SIGNIFICANT_WHITESPACE:
                case XmlNodeType.WHITESPACE:
                {
                    builder.append(reader.getValue());
                    break;
                }
                case XmlNodeType.TEXT:
                {
                    builder.append(escapeText(reader.getValue()));
                    break;
                }
                case XmlNodeType.COMMENT:
                {
                    // do nothing
                    break;
                }
                case XmlNodeType.CDATA:
                {
                    // JAVA never gets here. StAX parser with coalescing setting (our default config)
                    // reports CDATA as CHARACTERS.
                    builder.append("<![CDATA[").append(reader.getValue()).append("]]>");
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected XML node type: " + XmlNodeType.toString(reader.getNodeType()));
            }
        }
        while (true);
    }

    private static void readStartElement(XmlTextReader reader, StringBuilder builder,
                                         XmlTextReaderNamespaceStorage storage, IXmlUpdater xmlUpdater)
    {
        builder.append("<");
        builder.append(reader.getName());

        // storage.getNamespaces() contains namespace declarations
        // ordinary attributes
        SortedList<String, String> attributes = new SortedList<String, String>();

        // StAX counts namespace declarations ('xmlns' attributes) separately from ordinary attributes.
        // Local defined namespaces. Overridden prefixes are not supported at the moment.

        // WORDSJAVA-1072. It is possible to get xmlns=".." as an attribute only if XmlTextReader was created with
        // isIgnoreNamespaces=true otherwise we have to use XmlTextReader.getLocalNamespaces() in order to retrieve
        // namespaces. So if we are able to get the list of local namespaces it means XmlTextReader is taking into
        // account namespaces. Lets collect them into storage.getNamespaces() which represents the current start element.
        for (Map.Entry<String, String> e : reader.getLocalNamespaces())
        {
            // WORDSJAVA-1072. isPrefixDefined checks if there are the given namespace defined on any level (including
            // parent tags). But if there was the same namespace at the parent level a new one won't be added to the
            // current element. In case XmlTextReader was created with "isIgnoreNamespaces=false" we could read
            // namespaces via getLocalNamespaces()

            // Here we should use "contains()" to make sure there is no duplicate at the same level. Don't use
            // isPrefixDefined here because parent tag could have the same namespace and the current tag won't get it.
            if (!storage.getNamespaces().contains(e.getKey()))
                storage.getNamespaces().add(e.getKey(), e.getValue());
        }

        // check prefix of element itself, if it is defined
        String prefix = reader.getPrefix();
        if (JavaUtil.hasChars(prefix) && JavaUtil.hasChars(reader.getNamespaceURI()) && !storage.isPrefixDefined(prefix))
            storage.getNamespaces().add(prefix, reader.getNamespaceURI());

        if (reader.moveToFirstAttribute())
        {
            do
            {
                if ("xmlns".equals(reader.getPrefix()))
                {
                    // define a local namespace
                    if (reader.getLocalName() != null && !storage.isPrefixDefined(reader.getLocalName()))
                        storage.getNamespaces().add(reader.getLocalName(), reader.getValue());
                    continue;
                }

                if ("xmlns".equals(reader.getName()) && !storage.isPrefixDefined(""))
                {
                    // default namespace
                    storage.getNamespaces().add("", reader.getValue());
                    continue;
                }

                attributes.add(reader.getName(), reader.getValue());

                if (JavaUtil.hasChars(reader.getPrefix()) && JavaUtil.hasChars(reader.getNamespaceURI()) && !storage.isPrefixDefined(reader.getPrefix()))
                    storage.getNamespaces().add(reader.getPrefix(), reader.getNamespaceURI());
            }
            while (reader.moveToNextAttribute());
        }

        if (attributes.getCount() > 0)
        {
            for (Map.Entry<String, String> e : attributes)
            {
                builder.append(" ");
                builder.append(e.getKey());
                builder.append("=\"");
                builder.append(escapeAttributeValue(e.getValue()));
                builder.append("\"");
            }
        }
        if (storage.getNamespaces().getCount() > 0)
        {
            for (Map.Entry<String, String> e : storage.getNamespaces())
            {
                String namespaceUrl = xmlUpdater != null
                                      ? xmlUpdater.replaceNamespace(e.getValue())
                                      : e.getValue();

                if (JavaUtil.hasChars(e.getKey()))
                {
                    builder.append(" xmlns:");
                    builder.append(e.getKey());
                    builder.append("=\"");
                    builder.append(escapeAttributeValue(namespaceUrl));
                    builder.append("\"");
                }
                else
                {
                    // default namespace
                    builder.append(" xmlns=\"");
                    builder.append(escapeAttributeValue(namespaceUrl));
                    builder.append("\"");
                }
            }
        }

        reader.moveToElement();
        if (reader.isEmptyElement())
            builder.append(" />");
        else
            builder.append(">");
    }

    private static String escapeText(String s)
    {
        try
        {
            CharArrayWriter charWriter = new CharArrayWriter();
            DotNetEscapingWriter escapingWriter = new DotNetEscapingWriter(charWriter, false);
            escapingWriter.write(s);
            escapingWriter.flush();
            return charWriter.toString();
        }
        catch (IOException e)
        {
            Log.warn(e);
        }
        return s;
    }

    /// <summary>
    /// Replaces invalid XML characters in a string with their valid XML equivalent as SecurityElement.Escape() does.
    /// Carriage returns and line feeds do not need to be escaped in an attribute value in order for the XML to be well-formed.
    /// But we escape \r and \n too, because .NET version of XmlTextReader.ReadOuterXml() escapes them (if reader
    /// is created via ctor, rather than XmlReader.Create() factory method).
    /// </summary>
    private static String escapeAttributeValue(String value)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            switch (c)
            {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '\r':
                    sb.append("&#xD;");
                    break;
                case '\n':
                    sb.append("&#xA;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }

    /// <summary>
    /// Selects nodes using an XPath query.
    /// Namespaces is optional (can be null) and contains prefixes mapped to namespaces.
    /// </summary>
    public static ArrayList<Node> selectNodes(Element element, String xPath, Map<String, String> namespaces)
    {
        try
        {
            XPath path = XmlUtils.getXPathFactory().newXPath();
            if (namespaces == null)
                namespaces = collectNamespaces(element, xPath);

            String realXPath = replaceNamespacePrefix(namespaces, xPath);
            xPath = fixIllegalNamespace(namespaces, xPath);

            path.setNamespaceContext(new XmlNamespaceManager(namespaces));

            XPathExpression compile = path.compile(fixXPathForJava(xPath));
            NodeList evaluatedItems = (NodeList) compile.evaluate(element, XPathConstants.NODESET);
            ArrayList<Node>  list = new ArrayList<Node>();
            for (int i = 0; i < evaluatedItems.getLength(); i++)
                if (namespaces.isEmpty() || checkNamespaceUri(evaluatedItems.item(i), realXPath))
                    list.add(evaluatedItems.item(i));

            if (list != null && list.size() == 1 && !(list.get(0) instanceof Node))
                throw new IllegalStateException("Jaxen XPath evaluated non-node-set. Throw to emulate .Net behaviour.");

            return list;
        }
        catch (XPathExpressionException e)
        {
            // Should not throw an exception, but return an empty array to emulate .Net behaviour.
            return new ArrayList<>();
        }
    }
    /// <summary>
    /// Selects nodes using an XPath query.
    /// </summary>
    public static ArrayList<Node> selectNodes(Node node, String xPath)
    {
        try
        {
            XPath path = XmlUtils.getXPathFactory().newXPath();

            XPathExpression compile = path.compile(fixXPathForJava(xPath));
            NodeList evaluatedItems = (NodeList) compile.evaluate(node, XPathConstants.NODESET);
            ArrayList<Node>  list = new ArrayList<Node>();
            for (int i = 0; i < evaluatedItems.getLength(); i++)
                list.add(evaluatedItems.item(i));

            if (list != null && list.size() == 1 && !(list.get(0) instanceof Node))
                throw new IllegalStateException("Jaxen XPath evaluated non-node-set. Throw to emulate .Net behaviour.");

            return list;
        }
        catch (XPathExpressionException e)
        {
            // Should not throw an exception, but return an empty array to emulate .Net behaviour.
            return new ArrayList<>();
        }
    }

    // After fixing illegal namespaces some evaluation results contains node with wrong namespaces
    private static boolean checkNamespaceUri(Node item, String realXPath)
    {
        realXPath = gRegexIndexer.replace(realXPath,"");
        while (item != null && realXPath != null)
        {
            // remove local name from xPath
            String ln = item.getLocalName();

            // Since the exact attribute value is used, no further checking is required regardless of the namespace.
            if(realXPath.endsWith(XPATH_ALL_ATTRIBUTE_CONSTANT) ||
                    (ln != null && realXPath.endsWith(XPATH_ATTRIBUTE_CONSTANT + ln)))
                return true;

            if (ln != null && realXPath.endsWith(ln))
                realXPath = realXPath.substring(0, realXPath.length() - ln.length());

            // remove namespace from xPath, it also works then xPath didn't contain any namespace in this node
            String ns = item.getNamespaceURI() != null ? "/" + item.getNamespaceURI() + ":" : "/";
            if (realXPath.endsWith(ns))
                realXPath = realXPath.substring(0, realXPath.length() - ns.length());

            item = item.getParentNode();
        }

        return "".equals(realXPath) || checkXPathRarelyAxis(realXPath);
    }

    private static boolean checkXPathRarelyAxis(String xPath)
    {
        return xPath != null &&
                (xPath.contains("ancestor") ||
                xPath.contains("descendant") ||
                xPath.contains("self"));
    }

    /// <summary>
    /// This function solves problem with illegal namespaces.
    /// Sometimes we have node with different illegal namespaces that not exist in current xPath.
    /// So we can put full namespace to xPath for checking node later.
    /// </summary>
    private static String replaceNamespacePrefix(Map namespaces, String xPath)
    {
        if (xPath == null || namespaces == null || namespaces.isEmpty())
            return xPath;
        String originXPath = xPath;

        Iterator it = namespaces.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String nsPrefixRegex = "/" + pair.getKey() + ":";
            String nsValue = "/" + pair.getValue() + ":";
            xPath = xPath.replaceAll(nsPrefixRegex, nsValue);
        }

        return xPath;
    }

    /// <summary>
    /// This function solves problem with illegal namespaces - namespaces without prefix like 'http://'
    /// </summary>
    private static String fixIllegalNamespace(Map namespaces, String xPath)
    {
        if (xPath == null || namespaces == null || namespaces.isEmpty())
            return xPath;

        Iterator it = namespaces.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String value = (String) pair.getValue();
            if (value != null && !value.contains("://"))
            {
                // Change illegal namespace prefix to global namespace, like:
                // "/ns3:m538[1]" -> "/*[local-name()='m538'][1]"
                String nsPrefixRegex = "/" + pair.getKey() + ":([^|/\\[]+)";
                xPath = xPath.replaceAll(nsPrefixRegex, "/*[local-name()='$1']");
            }
        }

        return xPath;
    }

    /// <summary>
    /// Select single node using an XPath query.
    /// Namespaces is optional (can be null) and contains prefixes mapped to namespaces.
    /// </summary>
    public static Node selectSingleNode(Element element, String xPath, Map<String, String> namespaces)
    {
        /*assert element != null*/
        /*assert StringUtil.hasChars(xPath)*/

        try
        {
            XPath path = XmlUtils.getXPathFactory().newXPath();
            if (namespaces == null)
                namespaces = collectNamespaces(element, xPath);

            path.setNamespaceContext(new XmlNamespaceManager(namespaces));

            XPathExpression compile = path.compile(fixXPathForJava(xPath));
            return (Node) compile.evaluate(element, XPathConstants.NODE);
        }
        catch (XPathExpressionException e)
        {
            throw new IllegalStateException(e);
        }
    }

    public static void save(Document outXml, MemoryStream output)
    {
        XmlUtils.save(outXml, output);
    }

    public static boolean isNodeType(Node node, int xmlNodeType)
    {
        if (node == null)
            return false;

        short nodeType = node.getNodeType();
        switch (xmlNodeType)
        {
            case XmlNodeType.ATTRIBUTE:
                return nodeType == Node.ATTRIBUTE_NODE;

            case XmlNodeType.ELEMENT:
                return nodeType == Node.ELEMENT_NODE;

            case XmlNodeType.TEXT:
                return ((nodeType == Node.TEXT_NODE) && !isWhitespaceNode(node));

            case XmlNodeType.WHITESPACE:
                return isWhitespaceNode(node);

            case XmlNodeType.CDATA:
                return nodeType == Node.CDATA_SECTION_NODE;

            case XmlNodeType.COMMENT:
                return nodeType == Node.COMMENT_NODE;

            case XmlNodeType.DOCUMENT:
                return nodeType == Node.DOCUMENT_NODE;

            case XmlNodeType.DOCUMENT_TYPE:
                return nodeType == Node.DOCUMENT_TYPE_NODE;

            case XmlNodeType.ENTITY:
                return nodeType == Node.ENTITY_NODE;

            case XmlNodeType.ENTITY_REFERENCE:
                return nodeType == Node.ENTITY_REFERENCE_NODE;

            case XmlNodeType.NOTATION:
                return nodeType == Node.NOTATION_NODE;

            case XmlNodeType.PROCESSING_INSTRUCTION:
                return nodeType == Node.PROCESSING_INSTRUCTION_NODE;

            case XmlNodeType.END_ELEMENT:
            case XmlNodeType.SIGNIFICANT_WHITESPACE:
            case XmlNodeType.NONE:
                if (JavaDebug.ON)
                    System.out.println("There is no match in org.w3c.dom.Node class for the type '" + xmlNodeType + "'");
                return false;
        }
        return false;
    }

    /*
     * Java's StAX parser reports SPACE (i.e. Whitespace for .Net) only for "truly ignorable white spaces".
     * Most (.Net) Whitespace Nodes are reported as TEXT (Text Nodes for .Net) by this parser.
     *
     * So, let's manually separate .Net-style Whitespace Nodes.
     * See also comments to XmlNodeType.
     */
    public static boolean isWhitespaceNode(Node node)
    {
        if (node instanceof Text)
            return isWhitespace(((Text) node).getData());

        return false;
    }

    // separate method for tests
    static boolean isWhitespace(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) > ' ')
                return false;
        }

        return true;
    }

    public static String fixXPathForJava(String xpath)
    {
        return xpath != null ? xpath.replaceAll("\\[\\]", "") : null; // doesn't support []
    }

    private static DocumentBuilderFactory getCachedDocumentBuilderFactory(boolean preserveWhitespace)
    {
        if (preserveWhitespace)
            return gBuilderFactoryPreserveWhitespace.get();
        else
            return gBuilderFactory.get();
    }

    private static DocumentBuilderFactory getDocumentBuilderFactory(boolean preserveWhitespace) throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = newDocumentBuilderFactory();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(!preserveWhitespace);
        return factory;
    }

    public static DocumentBuilderFactory newDocumentBuilderFactory() throws ParserConfigurationException
    {
        return XmlUtils.newDocumentBuilderFactory();
    }

    private static ThreadLocal<DocumentBuilderFactory> gBuilderFactory = new ThreadLocal<DocumentBuilderFactory>()
    {
        @Override
        public DocumentBuilderFactory initialValue()
        {
            try
            {
                return getDocumentBuilderFactory(false);
            }
            catch (ParserConfigurationException e)
            {
                Log.warn(e);

                return null;
            }
        }
    };

    private static ThreadLocal<DocumentBuilderFactory> gBuilderFactoryPreserveWhitespace = new ThreadLocal<DocumentBuilderFactory>()
    {
        @Override
        public DocumentBuilderFactory initialValue()
        {
            try
            {
                return getDocumentBuilderFactory(true);
            }
            catch (ParserConfigurationException e)
            {
                Log.warn(e);

                return null;
            }
        }
    };

    /// <summary>
    /// Collects nodes from <paramref name="xPath"/> inside the <paramref name="element"/>
    /// and adds their name spaces to the <paramref name="nsMgr"/>.
    /// </summary>
    //JAVA-changed: returns HashMap of collected namespaces instead of adding them to XmlNamespaceManager.
    private static Map<String, String> collectNamespaces(/*XmlNamespaceManager nsMgr, */Element element, String xPath)
    {
        // Index of next name space.
        int nsIdx = 0;

        //JAVA-added
        Map<String, String> namespaces = new HashMap<String, String>();

        // Add name space of the root element.
        addNamespace(namespaces, element, nsIdx++);

        // Remove indexers from the xPath and split it by nodes.
        String[] pathNodes = msString
                .split(gRegexIndexer.replace(xPath, ""), new char[] { '/' }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);

        for (String pathNode : pathNodes)
        {
            String pathNodeWithoutNamespace = gRegexNamespace.replace(pathNode, "");

            // Speed. Avoid to collect namespaces for xpath part without namespace specification.
            if (msString.equals(pathNode, pathNodeWithoutNamespace))
                continue;

            NodeList xmlNodes = element.getElementsByTagName(pathNodeWithoutNamespace);
            if (xmlNodes.getLength() != 0)
                addNamespace(namespaces, xmlNodes.item(0), nsIdx++);
        }

        //JAVA-added
        return namespaces;
    }

    /// <summary>
    /// Adds name space of <paramref name="xmlNode"/> into <paramref name="nsMgr"/> with 'ns' prefix.
    /// </summary>
    private static void addNamespace(/*XmlNamespaceManager nsMgr*/ Map<String, String> namespaces, Node xmlNode, int nsIdx)
    {
        String nsUri = getNamespaceUri(xmlNode);
        
        if (StringUtil.hasChars(nsUri))
        {
            String nsPrefix = msString.format("ns{0}", nsIdx);
            namespaces.put(nsPrefix, nsUri);
        }
    }

    /// <summary>
    /// Returns name space Uri with empty prefix for the specified <paramref name="xmlNode"/>.
    /// </summary>
    private static String getNamespaceUri(Node xmlNode)
    {
        if (xmlNode.getAttributes() != null)
        {
            Attr xmlns = (Attr) xmlNode.getAttributes().getNamedItem("xmlns");
            if (xmlns != null)
                return xmlns.getNodeValue();
        }

        return null;
    }

    /**
     * JAVA-added
     * WORDSJAVA-2240: in case if encoding exists we must create a new Document and set the encoding while parsing.
     * standalone and version values can be set via setters via XmlUtilPal.appendChild()
     */
    public static Document getNewDocumentWithEncodingIfAny(XmlDeclaration declaration, Document document)
    {
        if (declaration == null || msString.isNullOrEmpty(declaration.getEncoding()))
            return document;

        try
        {
            StringBuilder declarationXmlBuilder = new StringBuilder("<?xml version=\"{0}\"");
            if (!msString.isNullOrEmpty(declaration.getEncoding()))
                declarationXmlBuilder.append(" encoding=\"{1}\"");

            if (!msString.isNullOrEmpty(declaration.getStandalone()))
                   declarationXmlBuilder.append(" standalone=\"{2}\"");

            declarationXmlBuilder.append("?><test></test>"); // to make parsing valid

            Encoding encoding = msString.isNullOrEmpty(declaration.getEncoding()) ? Encoding.getUTF8() : Encoding.getEncoding(declaration.getEncoding());
            byte[] declarationXml = encoding.getBytes(msString.format(declarationXmlBuilder.toString(), declaration.getVersion(), declaration.getEncoding(), declaration.getStandalone()));

            DocumentBuilder builder = getCachedDocumentBuilderFactory(false).newDocumentBuilder();

            InputSource is = new InputSource(new ByteArrayInputStream(declarationXml));
            is.setEncoding(declaration.getEncoding());
            Document documentWithDeclaration = builder.parse(is);

            // remove the stub <test/> node
            documentWithDeclaration.removeChild(documentWithDeclaration.getFirstChild());

            // copy the root node from the original doc.
            // NOTE: Only node tree gets copied not comments and instructions.
            if (document != null && document.getDocumentElement() != null)
            {
                Node root = document.getDocumentElement();
                documentWithDeclaration.importNode(root, true);
                documentWithDeclaration.appendChild(root);
            }

            return documentWithDeclaration;
        }
        catch (Throwable any)
        {
            Log.warn(any);
        }

        return document;
    }

    public static void appendChild(Node parent, Node child)
    {
        if (parent == null || child == null)
            return;

        if (child instanceof XmlDeclaration)
        {
            XmlDeclaration declarationNode = (XmlDeclaration) child;
            if (parent instanceof Document)
            {
                Document node = (Document) parent;
                node.setXmlStandalone("yes".equalsIgnoreCase(declarationNode.getStandalone()));
                node.setXmlVersion(declarationNode.getVersion());
                // Java doesn't allow to set encoding here it must be set in Transformer or OutputFormat
            }
            else
                throw new IllegalArgumentException("XmlDeclaration cannot be appended to node type " + parent.getNodeType());
        }
        else
            parent.appendChild(child);
    }

    // Used for XPath
    private static class XmlNamespaceManager implements NamespaceContext
    {
        public XmlNamespaceManager(Map<String, String> namespaces)
        {
            mNamespaces = namespaces;
        }

        public String getNamespaceURI(String prefix)
        {
            return (String) mNamespaces.get(prefix);
        }

        public String getPrefix(String namespaceURI)
        {
            for (Map.Entry<String,String> entry : mNamespaces.entrySet())
            {
                if (entry.getValue().equals(namespaceURI))
                    return entry.getKey();
            }
            return null;
        }

        public Iterator getPrefixes(String namespaceURI)
        {
            return mNamespaces.keySet().iterator();
        }

        private Map<String, String> mNamespaces;
    }

    /// <summary>
    /// Reads the whole content of the current node, including xml child nodes.
    /// </summary>
    /// <param name="reader"></param>
    /// <returns></returns>
    public static String readInnerXml(XmlTextReader reader) throws Exception
    {
        XmlReader tmpXmlReader = reader.readSubtree();
        tmpXmlReader.read();
        return tmpXmlReader.readInnerXml();
    }

    private static String removeUnsupportedAttributes(String xml)
    {
        // The Woodstox we're using doesn't support some attributes, causing exceptions.
        // Until we update Woodstox, we'll have to remove these attributes from the document.
        // See WORDSJAVA-3169
        for(String attr : UNSUPPORTED_ATTRIBUTES)
        {
            if (xml.contains(attr)) {
                xml = xml.replaceAll("\\s+" + attr + ":[^=]+=\"[^\"]*\"", "");
                xml = xml.replaceAll("</?" + attr + ":[^>]*>", "");
            }
        }

        return xml;
    }

    private final static String[] UNSUPPORTED_ATTRIBUTES = {"loext"};

    private static final String REGEX_PATTERN_INDEXER = "(\\[\\d+\\])|(\\[[^\\/]+)|(\\/\\*)";
    private static Regex gRegexIndexer = new Regex(REGEX_PATTERN_INDEXER,
            RegexOptions.COMPILED | RegexOptions.CULTURE_INVARIANT);

    private static final String REGEX_PATTERN_NAMESPACE = "(ns\\d+:)";
    private static Regex gRegexNamespace = new Regex(REGEX_PATTERN_NAMESPACE,
            RegexOptions.COMPILED | RegexOptions.CULTURE_INVARIANT);
    private static final String XPATH_ATTRIBUTE_CONSTANT = "@";
    private static final String XPATH_ALL_ATTRIBUTE_CONSTANT = "@*";
}
