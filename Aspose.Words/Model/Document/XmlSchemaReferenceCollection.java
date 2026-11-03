// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import java.util.Iterator;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// A collection of XMLSchemaReference objects that represent the unique namespaces that are attached to a document.
/// </summary>
class XmlSchemaReferenceCollection implements Iterable<XmlSchemaReference>
{
    /// <summary>
    /// Attaches schema with given Uri to the document.
    /// </summary>
    /// <remarks>
    /// <p>Aspose.Words exposes in <see cref="XmlNamespaceCollection" /> only schema applied to document. 
    /// This method is indented to attach schema installed on user's computer.</p>
    /// </remarks>
    void add(String uri, String location)
    {
        ArgumentUtil.checkHasChars(uri, "uri");

        if (mItems.containsKey(uri))
            throw new IllegalStateException("Document already has attached schema with such Uri.");

        mItems.add(uri, new XmlSchemaReference(uri, location));
    }

    /// <summary>
    /// Attaches schema to the document.
    /// </summary>
    void add(XmlNamespace xmlNamespace)
    {
        add(xmlNamespace.getUri(), xmlNamespace.getManifestLocation());
    }

    /// <summary>
    /// Gets a document schema at the specified index.
    /// </summary>
    XmlSchemaReference get(int index) { return mItems.getValues().get(index); }

    /// <summary>
    /// Detaches schema reference with given Uri.
    /// </summary>
    void remove(String uri)
    {
        mItems.remove(uri);
    }

    /// <summary>
    /// Detaches schema reference at give index.
    /// </summary>
    void removeAt(int index)
    {
        mItems.removeAt(index);
    }

    /// <summary>
    /// Detaches all schema references from document.
    /// </summary>
    void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Gets the number of schema attached to document.
    /// </summary>
    int getCount() { return mItems.getCount(); }

    public Iterator<XmlSchemaReference> iterator()
    {
        return mItems.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    XmlSchemaReferenceCollection deepClone()
    {
        XmlSchemaReferenceCollection lhs = new XmlSchemaReferenceCollection();

        for (XmlSchemaReference xmlSchemaReference : this)
            lhs.add(xmlSchemaReference.getUri(), xmlSchemaReference.getLocation());

        return lhs;
    }

    private /*final*/ SortedStringListGeneric<XmlSchemaReference> mItems = new SortedStringListGeneric<XmlSchemaReference>();
}

