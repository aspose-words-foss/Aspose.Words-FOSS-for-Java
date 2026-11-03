// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// A collection of strings that represent XML schemas that are associated with a custom XML part.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class. You access the collection of XML schemas of a custom XML part
/// via the <see cref="CustomXmlPart.Schemas"/> property.</para>
/// 
/// <seealso cref="CustomXmlPart"/>
/// <seealso cref="CustomXmlPart.Schemas"/>
/// </remarks>
public class CustomXmlSchemaCollection implements Iterable<String>
{
    /// <summary>
    /// Public ctor not needed.
    /// </summary>
    CustomXmlSchemaCollection()
    {
    }

    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets the element at the specified index.
    /// </summary>
    public String get(int index) { return mItems.get(index); }
    public void set(int index, String value) { mItems.set(index, value); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<String> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<String> mItems = new ArrayList<String>();

    /// <summary>
    /// Adds an item to the collection.
    /// </summary>
    /// <param name="value">The item to add.</param>
    public void add(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        msArrayList.add(mItems, value);
    }

    /// <summary>
    /// Returns the zero-based index of the specified value in the collection.
    /// </summary>
    /// <param name="value">The case-sensitive value to locate.</param>
    /// <returns>The zero based index. Negative value if not found.</returns>
    public int indexOf(String value)
    {
        return mItems.indexOf(value);
    }

    /// <summary>
    /// Removes the specified value from the collection.
    /// </summary>
    /// <param name="name">The case-sensitive value to remove.</param>
    public void remove(String name)
    {
        mItems.remove(name);
    }

    /// <summary>
    /// Removes a value at the specified index.
    /// </summary>
    /// <param name="index">The zero based index.</param>
    public void removeAt(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// Removes all elements from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Makes a deep clone of this object.
    /// </summary>
    public CustomXmlSchemaCollection deepClone()
    {
        // We don't have derived classes therefore we simply create a new object, don't memberwise clone.
        CustomXmlSchemaCollection lhs = new CustomXmlSchemaCollection();

        for (String item : this)
            lhs.add(item);

        return lhs;
    }
}

