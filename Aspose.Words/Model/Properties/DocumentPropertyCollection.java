// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.DictionaryExtension;
import java.util.Iterator;
import java.util.Map;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// Base class for <see cref="BuiltInDocumentProperties"/> and <see cref="CustomDocumentProperties"/> collections.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-document-properties/">Work with Document Properties</a> documentation article.</para>
/// </summary>
/// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.Common"]/*'/>
/// <seealso cref="BuiltInDocumentProperties"/>
/// <seealso cref="CustomDocumentProperties"/>
public abstract class DocumentPropertyCollection implements Iterable<DocumentProperty>
{
     /// <summary>
    /// Ctor.
    /// </summary>
    DocumentPropertyCollection()
    {
    }

    /// <summary>
    /// Gets number of items in the collection.
    /// </summary>
    public int getCount() { return mItems.getCount(); }

    /// <overloads>Provides access to the collection items.</overloads>
    /// <summary>
    /// Returns a <see cref="DocumentProperty"/> object by the name of the property.
    /// </summary>
    /// <remarks>
    /// <p>Returns <c>null</c> if a property with the specified name is not found.</p>
    /// </remarks>
    /// <param name="name">The case-insensitive name of the property to retrieve.</param>
    public /*virtual*/ DocumentProperty get(String name)
    {
        ArgumentUtil.checkHasChars(name, "name");
        return DictionaryExtension.getValueOrNull(mItems, name);
    }


    /// <summary>
    /// Returns a <see cref="DocumentProperty"/> object by index.
    /// </summary>
    /// <java><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.JavaSlow"]/*'/></java>
    /// <param name="index">Zero-based index of the <see cref="DocumentProperty"/> to retrieve.</param>
    public DocumentProperty get(int index) { return mItems.getByIndex(index); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<DocumentProperty> iterator()
    {
        // RK This was originally implemented as iterator over items, not over dictionary entries
        // and has to stay that way to avoid breaking customers' code.
        return mItems.getValues().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds new property to the collection or returns existing property if one with specified name already exists.
    /// Throws if <paramref name="name"/> is empty [OR] <paramref name="value"/> is <c>null</c>.
    /// </summary>
    /// <param name="name">Name of added property.</param>
    /// <param name="value">Value of added property.</param>
    /// <returns>Added property.</returns>
    /// <remarks>
    /// Compare the method with <see cref="Add"/>.
    /// </remarks>
    DocumentProperty addSafe(String name, Object value)
    {
        ArgumentUtil.checkHasChars(name, "name");
        ArgumentUtil.checkNotNull(value, "value");

        DocumentProperty property = DictionaryExtension.getValueOrNull(mItems, name);
        return (property != null) ? property : add(name, value);
    }

    /// <summary>
    /// Adds new property to the collection. Throws if <paramref name="name"/> is empty [OR]
    /// <paramref name="value"/> is <c>null</c> [OR] property with specified name already exists.
    /// </summary>
    /// <param name="name">Name of added property.</param>
    /// <param name="value">Value of added property.</param>
    /// <returns>Added property.</returns>
    /// <remarks>
    /// Compare the method with <see cref="AddSafe"/>.
    /// </remarks>
    DocumentProperty add(String name, Object value)
    {
        ArgumentUtil.checkHasChars(name, "name");
        ArgumentUtil.checkNotNull(value, "value");

        DocumentProperty property = new DocumentProperty(name, value);
        mItems.add(name, property);

        return property;
    }

    /// <summary>
    /// Returns <c>true</c> if a property with the specified name exists in the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the property.</param>
    /// <returns><c>true</c> if the property exists in the collection; <c>false</c> otherwise.</returns>
    public boolean contains(String name)
    {
        return mItems.containsKey(name);
    }

    /// <summary>
    /// Gets the index of a property by name.
    /// </summary>
    /// <java><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.JavaSlow"]/*'/></java>
    /// <param name="name">The case-insensitive name of the property.</param>
    /// <returns>The zero based index. Negative value if not found.</returns>
    public int indexOf(String name)
    {
        return mItems.indexOfKey(name);
    }

    /// <summary>
    /// Removes a property with the specified name from the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the property.</param>
    public void remove(String name)
    {
        ArgumentUtil.checkHasChars(name, "name");
        mItems.remove(name);
    }

    /// <summary>
    /// Removes a property at the specified index.
    /// </summary>
    /// <java><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentProperties.JavaSlow"]/*'/></java>
    /// <param name="index">The zero based index.</param>
    public void removeAt(int index)
    {
        mItems.removeAt(index);
    }

    /// <summary>
    /// Removes all properties from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Makes a copy of the object.
    /// </summary>
    DocumentPropertyCollection deepClone()
    {
        DocumentPropertyCollection lhs = create();

        for (Map.Entry<String, DocumentProperty> item : (Iterable<Map.Entry<String, DocumentProperty>>) mItems)
        {
            DocumentProperty property = item.getValue();
            lhs.mItems.add(item.getKey(), property.deepClone());
        }

        return lhs;
    }

    /// <summary>
    /// Virtual constructor. Creates empty collection of the same type as object whose method was called.
    /// Used as part of <see cref="Clone"/> operation.
    /// </summary>
    /// <remarks>
    /// This method should be internal AND protected but there is no such modifier in C# and it's more important
    /// to hide it from end user than from other classes in the assembly. So it's marked as internal.
    /// </remarks>
    abstract DocumentPropertyCollection create();

    private /*final*/ SortedStringListGeneric<DocumentProperty> mItems = new SortedStringListGeneric<DocumentProperty>(false);
}

