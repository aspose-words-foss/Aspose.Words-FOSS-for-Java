// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// A collection of <see cref="XmlNamespace"/> objects that represents the entire collection of schemas in the Schema Library.
/// </summary>
/// <remarks>
/// <p>In Microsoft Office Word, the Schema Library represents schemas installed on a user's computer that a user has applied to a Word document or 
/// that a user has explicitly added to the Schema Library by using the Schema Library dialog box.</p>
/// <p>In Aspose.Words this collection represents only schemas that a user has applied to document.</p>
/// </remarks>
class XmlNamespaceCollection implements Iterable<XmlNamespace>
{
    /// <summary>
    /// Adds schema to Schema Library.
    /// </summary>
    void add(XmlNamespace xmlNamespace)
    {
        msArrayList.add(mItems, xmlNamespace);
    }

    /// <summary>
    /// Returns schema at given index.
    /// </summary>
    XmlNamespace get(int index) { return mItems.get(index); }

    public Iterator<XmlNamespace> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Removes schema at given index.
    /// </summary>
    void removeAt(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// Removes all schemas from the collection.
    /// </summary>
    void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Gets the number of schemas in the collection.
    /// </summary>
    int getCount() { return mItems.size(); }

    XmlNamespaceCollection deepClone()
    {
        XmlNamespaceCollection lhs = new XmlNamespaceCollection();

        for (XmlNamespace xmlNamespace : this)
            lhs.add(xmlNamespace.deepClone());

        return lhs;
    }

    private /*final*/ ArrayList<XmlNamespace> mItems = new ArrayList<XmlNamespace>();
}

