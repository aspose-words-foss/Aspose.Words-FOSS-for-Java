// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import java.util.ArrayList;



/// <summary>
/// A collection of strings that represent all the items in a drop-down form field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <seealso cref="FormField"/>
/// <seealso cref="FormField.DropDownItems"/>
public class DropDownItemCollection implements Iterable<String>, IComplexAttr
{
    /// <summary>
    /// Don't need public ctor.
    /// </summary>
    DropDownItemCollection()
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

    /// <summary>
    /// Adds a string to the end of the collection.
    /// </summary>
    /// <param name="value">The string to add to the end of the collection.</param>
    /// <returns>
    /// <ms>The zero-based index at which the new element is inserted.</ms>
    /// <cpp>The zero-based index at which the new element is inserted.</cpp>
    /// <java><tt>true</tt> (as per the general contract of Collection.add).</java>
    /// </returns>
    /// <javaName>boolean add(java.lang.String s)</javaName>
    public int add(String value)
    {
        int index = getCount();
        insert(index, value);
        return index;
    }

    /// <summary>
    /// Determines whether the collection contains the specified value.
    /// </summary>
    /// <param name="value">Case-sensitive value to locate.</param>
    /// <returns><c>true</c> if the item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(String value)
    {
        return mItems.contains(value);
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
    /// Inserts a string into the collection at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index at which value is inserted.</param>
    /// <param name="value">The string to insert.</param>
    public void insert(int index, String value)
    {
        ArgumentUtil.checkNotNull(value, "value");

        if (getCount() >= MAX_ITEMS_COUNT)
            throw new IllegalStateException(msString.format(
                "There can be a maximum of {0} items in a dropdown form field.", MAX_ITEMS_COUNT));

        mItems.add(index, value);
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

    DropDownItemCollection deepClone()
    {
        // We don't have derived classes therefore we simply create a new object, don't memberwise clone.
        DropDownItemCollection lhs = new DropDownItemCollection();

        for (String item : this)
            lhs.add(item);

        return lhs;
    }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public 
    boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    
    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return deepClone();
    }

    /// <summary>
    /// Maximum number of drop down items MS Word allows.
    /// </summary>
    static final int MAX_ITEMS_COUNT = 25;

    private /*final*/ ArrayList<String> mItems = new ArrayList<String>();
}

