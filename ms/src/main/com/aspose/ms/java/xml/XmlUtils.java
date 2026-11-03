// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
//02/03/12 by Durin Vyacheslav
package com.aspose.ms.java.xml;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.java.IO.NetOutputStream;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.util.ResourceManager;
import com.ctc.wstx.stax.WstxInputFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.Writer;
import java.util.Set;

/**
 * XmlUtils class to work with xml.
 */
public final class XmlUtils
{
    private XmlUtils()
    {
        //
    }

    public static String getAttribute(Node parent, String attrKey)
    {
        if (parent == null)
            return "";

        NamedNodeMap attrs = parent.getAttributes();
        if (attrs == null)
            return null;

        Node attr = attrs.getNamedItem(attrKey);
        return attr == null ? null : attr.getTextContent(); //attr.getNodeValue();
    }

    public static void save(org.w3c.dom.Document outXml, MemoryStream output)
    {
        save((Node) outXml, output);
    }

    public static void save(Document doc, Writer writer)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(doc);
            Result result = new StreamResult(writer);
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
    }

    public static void save(Node outXml, MemoryStream output)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(outXml);
            Result result = new StreamResult(new NetOutputStream(output));
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            Log.warn(e);
        }
    }

    public static void getNodeSetRecursively(final Node rootNode, final Set<Node> result, final Node exclude, final boolean com)
    {
        if (rootNode == exclude)
            return;

        switch (rootNode.getNodeType())
        {
            case Node.ELEMENT_NODE:
                result.add(rootNode);
                Element el = (Element) rootNode;
                if (el.hasAttributes())
                {
                    NamedNodeMap nl = el.getAttributes();
                    for (int i = 0; i < nl.getLength(); i++)
                        result.add(nl.item(i));
                }
                //no return keep working
            case Node.DOCUMENT_NODE:
                for (Node r = rootNode.getFirstChild(); r != null; r = r.getNextSibling())
                {
                    if (r.getNodeType() == Node.TEXT_NODE)
                    {
                        result.add(r);
                        while (r != null && r.getNodeType() == Node.TEXT_NODE)
                            r = r.getNextSibling();

                        if (r == null)
                            return;
                    }
                    getNodeSetRecursively(r, result, exclude, com);
                }
                return;
            case Node.COMMENT_NODE:
                if (com)
                    result.add(rootNode);
                return;
            case Node.DOCUMENT_TYPE_NODE:
                return;
            default:
                result.add(rootNode);
        }
    }

    ///https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Processing
    public static DocumentBuilderFactory newDocumentBuilderFactory() throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String feature = null;
        try
        {
            // 1. doctype
            try
            {
                // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML entity attacks are prevented
                // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
                feature = "http://apache.org/xml/features/disallow-doctype-decl";
                factory.setFeature(feature, true);
            }
            catch (ParserConfigurationException e)
            {
                // This should catch a failed setFeature feature
                if (JavaDebug.ON)
                    System.err.println("ParserConfigurationException was thrown. The feature '" + feature + "' is probably not supported by your XML processor.");
            }

            // 2. externals
            try
            {
                // If you can't completely disable DTDs, then at least do the following:
                // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
                // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
                feature = "http://xml.org/sax/features/external-general-entities";
                factory.setFeature(feature, false);

                // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
                // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
                feature = "http://xml.org/sax/features/external-parameter-entities";
                factory.setFeature(feature, false);
            }
            catch (ParserConfigurationException e)
            {
                // This should catch a failed setFeature feature
                if (JavaDebug.ON)
                    System.err.println("ParserConfigurationException was thrown. The feature '" + feature + "' is probably not supported by your XML processor.");
            }

            // 3. and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
        }
        catch (Throwable e)
        {
            //WORDSJAVA-1222 e.g. setFeature could be abstract in early Xerces version
            if (JavaDebug.ON)
                System.err.println("Exception occurred, XXE may still possible: " + e.getMessage());
        }
        return factory;
    }

    public static WstxInputFactory newWstxInputFactory(boolean isIgnoreNamespaces, boolean isIgnoreDtd)
    {
        return newWstxInputFactory(isIgnoreNamespaces,isIgnoreDtd,null);
    }

    public static WstxInputFactory newWstxInputFactory(boolean isIgnoreNamespaces, boolean isIgnoreDtd, XMLResolver resolver)
    {
        //most default settings are acceptable for as.
        WstxInputFactory factory = new WstxInputFactory();
        factory.configureForSpeed();
        //add some properties so we behave close to .Net XmlTextReader
        factory.getConfig().doNormalizeLFs(false);

        factory.getConfig().doSupportExternalEntities(false);
        factory.getConfig().doReplaceEntityRefs(false);

        factory.getConfig().doSupportNamespaces(!isIgnoreNamespaces);
        // RK This needs to be true for ODT import to work. It looks .NET does this so it should be true.
        factory.getConfig().doCoalesceText(true);

        // WORDSJAVA-1706: DTD is used to parse XML Entities - it is strongly slows things down.
        // Don't set it when not required.
        if (isIgnoreDtd)
            factory.getConfig().setDtdResolver(new EmptyXmlResolver());
        else
            factory.getConfig().doSupportDTDs(true);

        // WORDSJAVA-2731: XHTML Entities do not resolved on Java during loading Epub documents.
        if (resolver != null)
        {
            factory.getConfig().doReplaceEntityRefs(true);
            factory.getConfig().doSupportDTDs(true);
            factory.getConfig().doSupportExternalEntities(false);
            // XhtmlXmlResolver hack is added on .Net to load XHTML Entities from resources.
            factory.getConfig().setDtdResolver(resolver);

            // WORDSJAVA-2731/WORDSJAVA-2736: update Woodstox parser to load XHTML Entities in .Net style.
            // Woodstox's StreamScanner.expandEntity() is changed to use UndeclaredEntityResolver to
            // silently resolve external entities when SupportExternalEntities is prohibited.
            // See third-party/woodstox/README.md of the FOSS repository.
            factory.getConfig().setUndeclaredEntityResolver(new EmptyXmlResolver());
        }

        // FIX WORDSJAVA-WORDSJAVA-1069: Tab inside a list number in TOC are converted to space.
        // Emulate .NET behaviour, read attributes values as is, don't replace whitespaces.
        factory.getConfig().doNormalizeAttrValues(false);
        // FIX WORDSJAVA-1109: default MaxAttributeSize is 65536 * 8 (524K). But DOCX xml can include
        // o:gfxData attribute that contain encoded multi-megabyte image. Let's set the limit to 8M.
        // FIX WORDSJAVA-2620: even bigger embedded image forces as to increase the limit. Let's set it to 32M.
        factory.getConfig().setMaxAttributeSize(65536 * 512);
        // FIX WORDSJAVA-2866: increase ElementDepth by 2 times to work with deeply nested xml (default is 1000).
        factory.getConfig().setMaxElementDepth(2000);
        // WORDSJAVA-2905 let's enable Xml11 chars support to avoid "Illegal character entity" exceptions.
        factory.getConfig().doAllowXml11EscapedCharsInXml10(true);
        return factory;
    }

    public static XPathFactory getXPathFactory()
    {
        return gXpathFactory.get();
    }

    private static void initXpath()
    {
        if (!gInitialized)
        {
            // Optimization from https://issues.apache.org/jira/browse/XALANJ-2540
            // com.sun.* classes are used only if they available.
            final String gDtmManagerName = "com.sun.org.apache.xml.internal.dtm.DTMManager";
            final String gDtmManagerValue = "com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault";
            boolean isDtmManagerDefaultExists = true;

            try
            {
                Class.forName(gDtmManagerValue);
            }
            catch (ClassNotFoundException e)
            {
                Log.warn(e);

                isDtmManagerDefaultExists = false;
            }
            boolean isDtmManagerSet = (System.getProperty(gDtmManagerName) != null);
            if (isDtmManagerDefaultExists && !isDtmManagerSet)
                System.setProperty(gDtmManagerName, gDtmManagerValue);

            gInitialized = true;
        }
    }

    private static volatile boolean gInitialized = false;
    private static final ThreadLocal<XPathFactory> gXpathFactory = new ThreadLocal<XPathFactory>()
    {
        @Override
        protected XPathFactory initialValue()
        {
            return XPathFactory.newInstance();
        }
    };

    static
    {
        initXpath();
    }
}

class EmptyXmlResolver implements XMLResolver
{
    @Override
    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException
    {
        return "";
    }
}
