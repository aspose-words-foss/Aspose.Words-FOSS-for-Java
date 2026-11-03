// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin

package com.aspose.ms.System.Xml;


import org.w3c.dom.*;

/**
 * Represents the XML declaration node <?xml version='1.0'...?>.
 * see http://msdn.microsoft.com/en-US/library/system.xml.xmldeclaration.aspx
 */
public class XmlDeclaration implements Node
{
    public void writeTo(XmlTextWriter writer)
    {
        writer.writeStartDocument("yes".equalsIgnoreCase(mStandalone));
    }

    public void writeTo(XmlWriter writer)
    {
        throw new UnsupportedOperationException();
    }

    public String getVersion()
    {
        return mVersion;
    }

    private void setVersion(String xmlVersion)
    {
        mVersion = xmlVersion;
    }

    public String getEncoding()
    {
        return mEncoding;
    }

    public void setEncoding(String value)
    {
        mEncoding = value;
    }

    public String getStandalone()
    {
        return mStandalone;
    }

    public void setStandalone(String value)
    {
        mStandalone = value;
    }

    public static XmlDeclaration from(Document xmlDocument)
    {
        XmlDeclaration declaration = new XmlDeclaration();
        declaration.setStandalone(xmlDocument.getXmlStandalone() ? "yes" : "no");
        declaration.setEncoding(xmlDocument.getXmlEncoding());
        declaration.setVersion(xmlDocument.getXmlVersion());
        return declaration;
    }

    public XmlDeclaration() {}

    public XmlDeclaration(String version, String encoding, String standalone)
    {
        mVersion = version;
        mEncoding = encoding;
        mStandalone = standalone;
    }

    private String mVersion;
    private String mStandalone = "yes";
    private String mEncoding;

//////////////////////////////////////////////////

    @Override
    public String getNodeName()
    {
        return null;
    }

    @Override
    public String getNodeValue() throws DOMException
    {
        return null;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException
    {

    }

    @Override
    public short getNodeType()
    {
        return 0;
    }

    @Override
    public Node getParentNode()
    {
        return null;
    }

    @Override
    public NodeList getChildNodes()
    {
        return null;
    }

    @Override
    public Node getFirstChild()
    {
        return null;
    }

    @Override
    public Node getLastChild()
    {
        return null;
    }

    @Override
    public Node getPreviousSibling()
    {
        return null;
    }

    @Override
    public Node getNextSibling()
    {
        return null;
    }

    @Override
    public NamedNodeMap getAttributes()
    {
        return null;
    }

    @Override
    public Document getOwnerDocument()
    {
        return null;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException
    {
        return null;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException
    {
        return null;
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException
    {
        return null;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException
    {
        return null;
    }

    @Override
    public boolean hasChildNodes()
    {
        return false;
    }

    @Override
    public Node cloneNode(boolean deep)
    {
        return null;
    }

    @Override
    public void normalize()
    {

    }

    @Override
    public boolean isSupported(String feature, String version)
    {
        return false;
    }

    @Override
    public String getNamespaceURI()
    {
        return null;
    }

    @Override
    public String getPrefix()
    {
        return null;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException
    {

    }

    @Override
    public String getLocalName()
    {
        return null;
    }

    @Override
    public boolean hasAttributes()
    {
        return false;
    }

    @Override
    public String getBaseURI()
    {
        return null;
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException
    {
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException
    {
        return null;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException
    {

    }

    @Override
    public boolean isSameNode(Node other)
    {
        return false;
    }

    @Override
    public String lookupPrefix(String namespaceURI)
    {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI)
    {
        return false;
    }

    @Override
    public String lookupNamespaceURI(String prefix)
    {
        return null;
    }

    @Override
    public boolean isEqualNode(Node arg)
    {
        return false;
    }

    @Override
    public Object getFeature(String feature, String version)
    {
        return null;
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler)
    {
        return null;
    }

    @Override
    public Object getUserData(String key)
    {
        return null;
    }

}
