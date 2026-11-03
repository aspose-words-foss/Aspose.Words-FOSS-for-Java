// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;


import com.aspose.XmlUtilPal;
import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.NodeDecorator;
import com.aspose.ms.java.xml.XmlUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@todo 3 sk (?) return "" vs. null vs. Exception() ?

/// <summary>
/// This class is to be MANUALLY ported to Java.
///
/// Allows XPath navigation over a Word document.
///
/// Java: differs from C# source completely because of diffs in Xml libs:
/// C# source extends (and overrides methods of) System.Xml.XPath.XPathNavigator,
/// java - org.jaxen.DefaultNavigator.
///
/// </summary>
class DocumentXPathNavigator
{
    /// <summary>
    /// This method is needed both on .NET and Java.
    /// </summary>
    static NodeList selectNodes(Node node, String xpath)
    {
        if (xpath == null)
            return new NodeList(new ArrayList<Node>());

        try
        {
            xpath = XmlUtilPal.fixXPathForJava(xpath);
            XPath path = XmlUtils.getXPathFactory().newXPath();
            XPathExpression compile = path.compile(xpath);

            NodeDecorator item = new NodeDecoratorImpl(node);
            org.w3c.dom.NodeList nodeList = (org.w3c.dom.NodeList) compile.evaluate(item, XPathConstants.NODESET);

            java.util.List<Node> nodes = new ArrayList<Node>();
            for (int i = 0; i < nodeList.getLength(); i++)
                nodes.add(((NodeDecorator<Node>) nodeList.item(i)).get());

            return new NodeList(nodes);
        }
        catch (XPathExpressionException e)
        {
            Log.warn(e);

            throw new IllegalStateException(e);
        }
    }

    /// <summary>
    /// This method is needed both on .NET and Java.
    /// </summary>
    static Node selectSingleNode(Node node, String xpath)
    {
        if (xpath == null)
            return null;

        try
        {
            xpath = XmlUtilPal.fixXPathForJava(xpath);
            XPath path = XmlUtils.getXPathFactory().newXPath();
            XPathExpression compile = path.compile(xpath);

            NodeDecorator<Node> item = new NodeDecoratorImpl(node);
            NodeDecorator<Node> foundNode = (NodeDecorator) compile.evaluate(item, XPathConstants.NODE);
            return foundNode != null ? foundNode.get() : null;
        }
        catch (XPathExpressionException e)
        {
            Log.warn(e);

            throw new IllegalStateException(e);
        }
    }


    ////////// Implementation ///////////

    // Implementation of org.w3.dom.Node //

    /**
     * This is a pretty straightforward implementation of {@link org.w3c.dom.Node}.
     * NodeDecorator is a wrapper to let built-in XPath engine walk through the Aspose Words model.
     * We MUST use per-element cache to keep all {@link NodeDecorator} instances because XPath engine compares nodes by their references.
     * At the moment methods <code>isSameNode(Node)</code> and <code>isEqualNode(Node)</code> are not used.
     * But according to comments they will be in next releases.
     */
    private static class NodeDecoratorImpl extends NodeDecorator<Node>
    {
        private final String mNodeName;
        private final boolean mIsCompositeNode;
        private final CompositeNode mCompositeNode;
        private final String mBaseUri;
        private NodeListDecorator mChildNodeList;
        private boolean mHasChildNodes;
        private final Map<com.aspose.words.Node, NodeDecorator> mTreeCache;
        private final com.aspose.words.Node mNode;

        public NodeDecoratorImpl(com.aspose.words.Node node)
        {
            this(node, new HashMap<Node, NodeDecorator>());
        }

        public NodeDecoratorImpl(com.aspose.words.Node node, Map<com.aspose.words.Node, NodeDecorator> cache)
        {
            if (node == null)
                throw new IllegalArgumentException("Node");

            if (cache == null)
                throw new IllegalArgumentException("Cache");

            this.mNode = node;
            this.mTreeCache = cache;
            this.mNodeName = mNode.getClass().getSimpleName();

            mIsCompositeNode = node instanceof CompositeNode;
            mCompositeNode = mIsCompositeNode ? (CompositeNode) node : null;
            mBaseUri = mNode instanceof com.aspose.words.Document ? ((com.aspose.words.Document) mNode).getBaseUri() :
                       null;

            if (mIsCompositeNode)
            {
                mHasChildNodes = mCompositeNode.hasChildNodes();
                mChildNodeList = new NodeListDecorator(mCompositeNode.getChildNodes(NodeType.ANY, false), cache);
            }

            mTreeCache.put(node, this);
        }

        @Override
        public Element getDocumentElement()
        {
            return getCached(mNode);
        }

        @Override
        public String getTagName()
        {
            return mNodeName;
        }

        @Override
        public String getNodeName()
        {
            return mNodeName;
        }

        @Override
        public String getNodeValue() throws DOMException
        {
            return mNode.getText();
        }

        @Override
        public short getNodeType()
        {
            switch (mNode.getNodeType())
            {
                case NodeType.DOCUMENT:
                case NodeType.GLOSSARY_DOCUMENT:
                    return org.w3c.dom.Node.DOCUMENT_NODE;

                default:
                    return org.w3c.dom.Node.ELEMENT_NODE;
            }
        }

        @Override
        public org.w3c.dom.Node getParentNode()
        {
            return getCached(mNode.getParentNode());
        }

        @Override
        public org.w3c.dom.NodeList getChildNodes()
        {
            return mChildNodeList;
        }

        @Override
        public org.w3c.dom.Node getFirstChild()
        {
            return mIsCompositeNode ? getCached(mCompositeNode.getFirstChild()) : null;
        }

        @Override
        public org.w3c.dom.Node getLastChild()
        {
            return mIsCompositeNode ? getCached(mCompositeNode.getLastChild()) : null;
        }

        @Override
        public org.w3c.dom.Node getPreviousSibling()
        {
            return getCached(mNode.getPreviousSibling());
        }

        @Override
        public org.w3c.dom.Node getNextSibling()
        {
            return getCached(mNode.getNextSibling());
        }

        @Override
        public org.w3c.dom.Document getOwnerDocument()
        {
            return getCached(mNode.getDocument());
        }

        @Override
        public boolean hasChildNodes()
        {
            return mHasChildNodes;
        }

        @Override
        public org.w3c.dom.Node cloneNode(boolean deep)
        {
            return this; // this class is immutable so it doesn't matter if it is the same instance.
        }

        @Override
        public String getLocalName()
        {
            return mNodeName;
        }

        @Override
        public String getBaseURI()
        {
            return mBaseUri;
        }

        @Override
        public String getTextContent() throws DOMException
        {
            return mNode.getText();
        }

        public com.aspose.words.Node get()
        {
            return mNode;
        }

        @Override
        public boolean isSameNode(org.w3c.dom.Node other)
        {
            if (other instanceof NodeDecorator)
                return get() == ((NodeDecorator) other).get();

            return this == other;
        }

        @Override
        public boolean isEqualNode(org.w3c.dom.Node arg)
        {
            return isSameNode(arg);
        }

        private NodeDecorator getCached(com.aspose.words.Node node)
        {
            return getCachedNode(node, mTreeCache);
        }
    }

    // Implementation of org.w3.dom.NodeList //

    /**
     * Simple implementation of {@link org.w3c.dom.NodeList}.
     */
    private static class NodeListDecorator implements org.w3c.dom.NodeList
    {

        private final NodeCollection mCollection;
        private final Map<Node, NodeDecorator> mCache;


        public NodeListDecorator(NodeCollection collection, Map<Node, NodeDecorator> cache)
        {
            if (collection == null)
                throw new IllegalArgumentException("Collection cannot be null");

            this.mCollection = collection;
            this.mCache = cache;
        }

        @Override
        public org.w3c.dom.Node item(int index)
        {
            return getCachedNode(mCollection.get(index), mCache);
        }

        @Override
        public int getLength()
        {
            return mCollection.getCount();
        }
    }

    /////////// HELPERS ///////////////////

    private static NodeDecorator getCachedNode(com.aspose.words.Node node,
                                              Map<com.aspose.words.Node, NodeDecorator> cache)
    {
        if (node == null)
            return null;

        if (cache.containsKey(node))
            return cache.get(node);

        return new NodeDecoratorImpl(node, cache);
    }
}
