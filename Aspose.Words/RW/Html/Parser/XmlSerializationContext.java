// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import com.aspose.SortedStringListGeneric;
import com.aspose.W3CNamespaces;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// XML serialization context.
/// </summary>
class XmlSerializationContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="htmlNode">HTML node for serialization.</param>
    XmlSerializationContext(HtmlNode htmlNode)
    {
        mNsPrefixMapStack = new msStack<SortedStringListGeneric<String>>();
        mPredefinedNsPrefixMap = new SortedStringListGeneric<String>(false);

        // Collect namespaces declared in parent elements for the given node.
        HtmlElementNode elementNode = htmlNode.getParent();
        while (elementNode != null)
        {
            updateNsPrefixMap(mPredefinedNsPrefixMap, elementNode.getAttributes(), false);
            elementNode = elementNode.getParent();
        }

        final String XML_NAMESPACE_PREFIX = "xml";
        if (!mPredefinedNsPrefixMap.containsKey(XML_NAMESPACE_PREFIX))
            mPredefinedNsPrefixMap.add(XML_NAMESPACE_PREFIX, W3CNamespaces.XML);
    }

    /// <summary>
    /// Call this method before node serialization.
    /// </summary>
    /// <param name="elementNode"></param>
    void startNode(HtmlElementNode elementNode)
    {
        SortedStringListGeneric<String> nsPrefixMap = new SortedStringListGeneric<String>(false);
        nsPrefixMap.addRange((mNsPrefixMapStack.getCount() != 0)
                                 ? getNsPrefixMap()
                                 : mPredefinedNsPrefixMap);
        updateNsPrefixMap(nsPrefixMap, elementNode.getAttributes(), true);
        mNsPrefixMapStack.push(nsPrefixMap);
    }

    /// <summary>
    /// Call this method after node serialization.
    /// </summary>
    void endNode()
    {
        Debug.assertTrue(mNsPrefixMapStack.getCount() != 0);
        mNsPrefixMapStack.pop();
    }

    /// <summary>
    /// Checks whether the given name is well-formed.
    /// </summary>
    boolean isWellFormedName(String name)
    {
        Debug.assertTrue(mNsPrefixMapStack.getCount() != 0);

        int colonCharIndex = name.indexOf(':');
        if (colonCharIndex != -1)
        {
            String prefix = name.substring((0), (0) + (colonCharIndex));
            if (!StringUtil.equalsOrdinalIgnoreCase(prefix, XMLNS_PREFIX) &&
                !getNsPrefixMap().containsKey(prefix))
            {
                return false;
            }
        }

        return true;
    }

    public void addXLinkNamespaceToContext()
    {
        if (!getNsPrefixMap().containsKey("xlink"))
            getNsPrefixMap().add("xlink", W3CNamespaces.X_LINK);
    }

    private static void updateNsPrefixMap(
        SortedStringListGeneric<String> nsPrefixMap,
        HtmlAttributeCollection attributes,
        boolean replaceExisting)
    {
        final String XMLNS_PREFIX_WITH_COLON = XMLNS_PREFIX + ":";
        for (HtmlAttribute attribute : attributes)
        {
            if (!msString.startsWith(attribute.getName(), XMLNS_PREFIX_WITH_COLON, StringComparison.ORDINAL_IGNORE_CASE))
                continue;

            String nsPrefix = attribute.getName().substring(XMLNS_PREFIX_WITH_COLON.length());
            if (msString.equals(nsPrefix, ""))
                continue;

            if (replaceExisting || !nsPrefixMap.containsKey(nsPrefix))
                nsPrefixMap.set(nsPrefix, attribute.getValue());
        }
    }

    private SortedStringListGeneric<String> getNsPrefixMap() { return mNsPrefixMapStack.peek(); }

    private /*final*/ msStack<SortedStringListGeneric<String>> mNsPrefixMapStack;
    private /*final*/ SortedStringListGeneric<String> mPredefinedNsPrefixMap;

    private static final String XMLNS_PREFIX = "xmlns";
}

