// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import java.util.Iterator;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// A collection of document variables.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-document-properties/">Work with Document Properties</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Variable names and values are strings.</p>
/// <p>Variable names are case-insensitive.</p>
/// </remarks>
public class VariableCollection implements Iterable<Map.Entry<String, String>>
{
    VariableCollection()
    {
    }

    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.getCount(); }

    /// <overloads>Provides access to the collection items.</overloads>
    /// <summary>
    /// Gets or a sets a document variable by the case-insensitive name.
    /// <c>null</c> values are not allowed as a right hand side of the assignment and will be replaced by empty string.
    /// </summary>
    public String get(String name)
    {
        ArgumentUtil.checkHasChars(name, "name");
        return mItems.getSafe(name,"");
    }
    public void set(String name, String value)
    {
        ArgumentUtil.checkHasChars(name, "name");
        // andrnosk: WORDSNET-5100 If value is not null use value otherwise use empty string.
        mItems.set(name, StringUtil.hasChars(value) ? value : "");
    }


    /// <summary>
    /// Gets or sets a document variable at the specified index.
    /// <c>null</c> values are not allowed as a right hand side of the assignment and will be replaced by empty string.
    /// </summary>
    /// <param name="index">Zero-based index of the document variable.</param>
    public String get(int index)
    {
        return mItems.getValues().get(index);
    }
    // andrnosk: WORDSNET-5100 If value is not null use value otherwise use empty string.
    public void set(int index, String value)
    {
        String key = mItems.getKeys().get(index);
        mItems.set(key, (StringUtil.hasChars(value))
            ? value
            : "");
    }

    /// <summary>
    /// Returns an enumerator object that can be used to iterate over all variable in the collection.
    /// </summary>
    public Iterator<Map.Entry<String, String>> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds a document variable to the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the variable to add.</param>
    /// <param name="value">The value of the variable. The value cannot be <c>null</c>, if value is null empty string will be used instead.</param>
    public void add(String name, String value)
    {
        // RESILIENCY 16524 - The problem occurred because there is few variables with the same name in the document. 
        // andrnosk: WORDSNET-5100 If value is not null use value otherwise use empty string.
        mItems.set(name, StringUtil.hasChars(value) ? value : "");
    }

    /// <summary>
    /// Determines whether the collection contains a document variable with the given name.
    /// </summary>
    /// <param name="name">Case-insensitive name of the document variable to locate.</param>
    /// <returns><c>true</c> if item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(String name)
    {
        return mItems.containsKey(name);
    }

    /// <summary>
    /// Returns the zero-based index of the specified document variable in the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the variable.</param>
    /// <returns>The zero based index. Negative value if not found.</returns>
    public int indexOfKey(String name)
    {
        return mItems.indexOfKey(name);
    }

    /// <summary>
    /// Removes a document variable with the specified name from the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the variable.</param>
    public void remove(String name)
    {
        mItems.remove(name);
    }

    /// <summary>
    /// Removes a document variable at the specified index.
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
    VariableCollection deepClone()
    {
        // We don't have derived classes therefore we simply create a new object, don't memberwise clone.
        VariableCollection lhs = new VariableCollection();

        for (Map.Entry<String, String> entry : this)
            lhs.add(entry.getKey(), entry.getValue());

        return lhs;
    }

    private /*final*/ SortedStringListGeneric<String> mItems = new SortedStringListGeneric<String>(false);
}

