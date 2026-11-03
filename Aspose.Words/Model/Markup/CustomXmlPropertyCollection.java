// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2008 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import java.util.Iterator;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// Represents a collection of custom XML attributes or smart tag properties.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Items are <see cref="CustomXmlProperty"/> objects.</para>
/// </remarks>
public class CustomXmlPropertyCollection implements Iterable<CustomXmlProperty>
{
    /// <summary>
    /// Don't need public ctor.
    /// </summary>
    CustomXmlPropertyCollection()
    {
    }

    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.getCount(); }

    /// <overloads>Provides access to the collection items.</overloads>
    /// <summary>
    /// Gets a property with the specified name.
    /// </summary>
    /// <param name="name">Case-sensitive name of the property to locate.</param>
    public CustomXmlProperty get(String name) { return DictionaryExtension.getValueOrNull(mItems, name); }


    /// <summary>
    /// Gets a property at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the property.</param>
    public CustomXmlProperty get(int index) { return mItems.getByIndex(index); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<CustomXmlProperty> iterator()
    {
        return mItems.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds a property to the collection.
    /// </summary>
    /// <param name="property">The property to add.</param>
    public void add(CustomXmlProperty property)
    {
        mItems.add(property.getName(), property);
    }

    /// <summary>
    /// Adds a property to the collection. Replaces if the property with the given name already exists.
    /// Useful when loading documents.
    /// </summary>
    void addSafe(CustomXmlProperty property)
    {
        mItems.set(property.getName(), property);
    }

    /// <summary>
    /// Determines whether the collection contains a property with the given name.
    /// </summary>
    /// <param name="name">Case-sensitive name of the property to locate.</param>
    /// <returns><c>true</c> if the item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(String name)
    {
        return mItems.containsKey(name);
    }

    /// <summary>
    /// Returns the zero-based index of the specified property in the collection.
    /// </summary>
    /// <param name="name">The case-sensitive name of the property.</param>
    /// <returns>The zero based index. Negative value if not found.</returns>
    public int indexOfKey(String name)
    {
        return mItems.indexOfKey(name);
    }

    /// <summary>
    /// Removes a property with the specified name from the collection.
    /// </summary>
    /// <param name="name">The case-sensitive name of the property.</param>
    public void remove(String name)
    {
        mItems.remove(name);
    }

    /// <summary>
    /// Removes a property at the specified index.
    /// </summary>
    /// <param name="index">The zero based index.</param>
    public void removeAt(int index)
    {
        mItems.removeAt(index);
    }

    /// <summary>
    /// Removes all elements from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Makes a deep copy of the collection.
    /// </summary>
    CustomXmlPropertyCollection deepClone()
    {
        // We don't have derived classes therefore we simply create a new object, don't memberwise clone.
        CustomXmlPropertyCollection lhs = new CustomXmlPropertyCollection();

        for (CustomXmlProperty property : this)
            lhs.add(property.deepClone());

        return lhs;
    }

    private /*final*/ SortedStringListGeneric<CustomXmlProperty> mItems = new SortedStringListGeneric<CustomXmlProperty>();
}

