package com.aspose.ms.java.xml;

import org.w3c.dom.*;

public abstract class NodeDecorator<T> implements Element, Document
{
    public abstract T get();

    ////////// NOT IMPLEMENTED ////////////

    @Override
    public String getAttribute(String name)
    {
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String name)
    {
        return null;
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException
    {
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
    {
        return null;
    }

    @Override
    public boolean hasAttribute(String name)
    {
        return false;
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException
    {
        return false;
    }

    @Override
    public TypeInfo getSchemaTypeInfo()
    {
        return null;
    }

    @Override
    public Attr getAttributeNode(String name)
    {
        return null;
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException
    {
        return null;
    }

    @Override
    public NamedNodeMap getAttributes()
    {
        return null;
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
    public Object getFeature(String feature, String version)
    {
        return null;
    }

    @Override
    public boolean hasAttributes()
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
    public Object getUserData(String key)
    {
        return null;
    }

    @Override
    public Element getElementById(String elementId)
    {
        return null;
    }

    @Override
    public String getInputEncoding()
    {
        return null;
    }

    @Override
    public String getXmlEncoding()
    {
        return null;
    }

    @Override
    public boolean getXmlStandalone()
    {
        return false;
    }

    @Override
    public DocumentType getDoctype()
    {
        return null;
    }

    @Override
    public DOMImplementation getImplementation()
    {
        return null;
    }


    @Override
    public String getXmlVersion()
    {
        return null;
    }

    @Override
    public boolean getStrictErrorChecking()
    {
        return false;
    }

    @Override
    public String getDocumentURI()
    {
        return null;
    }

    @Override
    public DOMConfiguration getDomConfig()
    {
        return null;
    }


    ////////////////////////////////////// UNSUPPORTED //////////////////////

    @Override
    public Element createElement(String tagName) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public DocumentFragment createDocumentFragment()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Text createTextNode(String data)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comment createComment(String data)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public CDATASection createCDATASection(String data) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr createAttribute(String name) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityReference createEntityReference(String name) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node importNode(Node importedNode, boolean deep) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setXmlStandalone(boolean xmlStandalone) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setXmlVersion(String xmlVersion) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStrictErrorChecking(boolean strictErrorChecking)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDocumentURI(String documentURI)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node adoptNode(Node source) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void normalizeDocument()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttribute(String name) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void normalize()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPrefix(String prefix) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTextContent(String textContent) throws DOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler)
    {
        throw new UnsupportedOperationException();
    }
}
