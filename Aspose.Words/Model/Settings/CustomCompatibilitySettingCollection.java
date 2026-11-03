// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2010 by Denis Darkin (copy/pasted and adopted from OdsoRecipientDataCollection)

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// A typed collection of <see cref="CustomCompatibilitySetting"/>
/// </summary>
/// <seealso cref="CustomCompatibilitySetting"/>
class CustomCompatibilitySettingCollection implements Iterable<CustomCompatibilitySetting>
{
            
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item in this collection.
    /// </summary>
    CustomCompatibilitySetting get(int index) { return mItems.get(index); }
    void set(int index, CustomCompatibilitySetting value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mItems.set(index, value);
    }

    /// <summary>
    /// Returns item by name or null if item with given name is not presented in collection.
    /// </summary>
    /// <remarks>
    /// Seems there should be not too much items so hash table is not necessary.
    /// </remarks>
    CustomCompatibilitySetting get(String name)
    {
        for(CustomCompatibilitySetting item : mItems)
            if (msString.equals(item.getName(), name))
                return item;

        return null;
    }

    public Iterator<CustomCompatibilitySetting> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an object to the end of this collection.
    /// </summary>
    /// <param name="value">The object to add. Cannot be null.</param>
    int add(CustomCompatibilitySetting value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        msArrayList.add(mItems, value);
        return mItems.size() - 1;
    }

    /// <summary>
    /// Removes all elements from this collection.
    /// </summary>
    void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Removes the element at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the element.</param>
    void removeAt(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// Makes a copy of the object.
    /// </summary>
    CustomCompatibilitySettingCollection deepClone()
    {
        CustomCompatibilitySettingCollection lhs = new CustomCompatibilitySettingCollection();

        for (CustomCompatibilitySetting item : mItems)
            msArrayList.add(lhs.mItems, item.deepClone());

        return lhs;
    }

    private /*final*/ ArrayList<CustomCompatibilitySetting> mItems = new ArrayList<CustomCompatibilitySetting>();
}

