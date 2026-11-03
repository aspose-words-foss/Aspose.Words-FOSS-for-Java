// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents a node of an HTML document tree.
/// </summary>
abstract class HtmlNode
{
    /// <summary>
    /// Serializes the node to XML text.
    /// </summary>
    /// <returns>
    /// The string containing XML text of this node.
    /// </returns>
    /// <remarks>
    /// At the moment this method is used only to serialize SVG fragments back to text.
    /// </remarks>
    String serializeToXml()
    {
        XmlSerializationContext context = new XmlSerializationContext(this);
        StringBuilder result = new StringBuilder();
        serializeToXml(result, context);
        return result.toString();
    }

    /// <summary>
    /// Recalculates properties of all HTML elements in this HTML tree for the CSS box model.
    /// </summary>
    void buildBoxLayout()
    {
        HtmlElementNode root = ms.as(this, HtmlElementNode.class);
        if (root == null)
            return;

        // Subtrees with preformatted whitespace require special processing. We rember the root of such a subtree while
        // traversing its children.
        HtmlNode preformattedWhitespaceSubtreeRoot = null;

        HtmlTreeEnumerator enumerator = new HtmlTreeEnumerator(root);
        while (enumerator.moveNext())
        {
            if (enumerator.isStart() &&
                (preformattedWhitespaceSubtreeRoot == null) &&
                enumerator.getCurrent().isPreformattedWhitespaceNode())
            {
                // We're entering a preformatted whitespace subtree.
                preformattedWhitespaceSubtreeRoot = enumerator.getCurrent();
            }

            boolean preformattedWhitespace = preformattedWhitespaceSubtreeRoot != null;
            boolean processSubtree = enumerator.getCurrent().buildNodeBoxLayout(enumerator.isStart(), preformattedWhitespace);
            if (!processSubtree)
            {
                // Note that after this call the current element won't be visited again while normally each element is
                // visited twice.
                enumerator.dontEnumerateCurrentSubTree();
            }

            if ((!enumerator.isStart() || !processSubtree) &&
                msObject.referenceEquals(enumerator.getCurrent(), preformattedWhitespaceSubtreeRoot))
            {
                // We're leaving a preformatted whitespace subtree.
                preformattedWhitespaceSubtreeRoot = null;
            }
        }
    }

    /// <summary>
    /// Serializes the node to XML text.
    /// </summary>
    /// <param name="result">
    /// The text buffer to which the XML text of this node will be appended.
    /// </param>
    /// <param name="serializationContext">Serialization context.</param>
    abstract void serializeToXml(StringBuilder result, XmlSerializationContext serializationContext);

    /// <summary>
    /// Gets the parent node of this node. May be <c>null</c>. Do not set this property directly.
    /// </summary>
    /// <remarks>
    /// This property should not be changed directly. Use parent's property <see cref="HtmlElementNode.Children"/>
    /// to change position of the node in an HTML tree.
    /// </remarks>
    HtmlElementNode getParent() { return mParent; }
    void setParent(HtmlElementNode value) { mParent = value; }

    /// <summary>
    /// Gets the sibling node that follows this node. May be <c>null</c>. Do not set this property directly.
    /// </summary>
    /// <remarks>
    /// This property should not be changed directly. Use parent's property <see cref="HtmlElementNode.Children"/>
    /// to change position of the node in an HTML tree.
    /// </remarks>
    HtmlNode getNextSibling() { return mNextSibling; }
    void setNextSibling(HtmlNode value) { mNextSibling = value; }

    /// <summary>
    /// Gets the sibling node that precedes this node. May be <c>null</c>. Do not set this property directly.
    /// </summary>
    /// <remarks>
    /// This property should not be changed directly. Use parent's property <see cref="HtmlElementNode.Children"/>
    /// to change position of the node in an HTML tree.
    /// </remarks>
    HtmlNode getPreviousSibling() { return mPreviousSibling; }
    void setPreviousSibling(HtmlNode value) { mPreviousSibling = value; }

    /// <summary>
    /// Indicates whether this node or any of its child nodes is a box.
    /// </summary>
    abstract boolean isBoxOrContainsBoxes();

    /// <summary>
    /// Indicates whether this node or any of its child nodes contains meaningful text (text that contains non-ignored
    /// characters). In most cases, any character except whitespace is meaningful, but in preformatted text whitespace
    /// characters are meaningful too.
    /// </summary>
    abstract boolean containsText();

    /// <summary>
    /// Builds box layout of the node.
    /// </summary>
    /// <returns>
    /// A value indicating whether the subtree of the node must also be processed.
    /// </returns>
    protected abstract boolean buildNodeBoxLayout(boolean isStart, boolean preformattedWhitespace);

    /// <summary>
    /// Gets a value indicating whether whitespace of this node and of all its children is preformatted.
    /// </summary>
    protected /*virtual*/ boolean isPreformattedWhitespaceNode()
    {
        // Whitespace of most nodes is not preformatted by default.
        return false;
    }

    private HtmlElementNode mParent;
    private HtmlNode mPreviousSibling;
    private HtmlNode mNextSibling;
}

