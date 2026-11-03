// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.Iterator;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// A collection of individual bookmarks outline level.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-bookmarks/">Working with Bookmarks</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Key is a case-insensitive string bookmark name. Value is a int bookmark outline level.</para>
/// <para>Bookmark outline level may be a value from 0 to 9. Specify 0 and Word bookmark will not be displayed in the document outline.
/// Specify 1 and Word bookmark will be displayed in the document outline at level 1; 2 for level 2 and so on.</para>
/// </remarks>
public class BookmarksOutlineLevelCollection implements Iterable<Map.Entry<String, Integer>>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.getCount(); }

    /// <overloads>Provides access to the collection items.</overloads>
    /// <summary>
    /// Gets or a sets a bookmark outline level by the bookmark name.
    /// </summary>
    /// <param name="name">Case-insensitive name of the bookmark.</param>
    /// <returns>The outline level of the bookmark. Valid range is 0 to 9.</returns>
    public int get(String name) { return mItems.get(name); }
    public void set(String name, int value)
    {
        if ((value < 0) || (value > 9))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        mItems.set(name, value);
    }


    /// <summary>
    /// Gets or sets a bookmark outline level at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the bookmark.</param>
    /// <returns>The outline level of the bookmark. Valid range is 0 to 9.</returns>
    public int get(int index) { return mItems.getValues().get(index); }
    public void set(int index, int value)
    {
        if ((value < 0) || (value > 9))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        String key = mItems.getKeys().get(index);
        mItems.set(key, value);
    }

    /// <summary>
    /// Adds a bookmark to the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the bookmark to add.</param>
    /// <param name="outlineLevel">The outline level of the bookmark. Valid range is 0 to 9.</param>
    public void add(String name, int outlineLevel)
    {
        if ((outlineLevel < 0) || (outlineLevel > 9))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "outlineLevel");

        mItems.add(name, outlineLevel);
    }

    /// <summary>
    /// Determines whether the collection contains a bookmark with the given name.
    /// </summary>
    /// <param name="name">Case-insensitive name of the bookmark to locate.</param>
    /// <returns><c>true</c> if item is found in the collection; otherwise, <c>false</c>.</returns>
    public boolean contains(String name)
    {
        return mItems.containsKey(name);
    }

    /// <summary>
    /// Returns the zero-based index of the specified bookmark in the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the bookmark.</param>
    /// <returns>The zero based index. Negative value if not found.</returns>
    public int indexOfKey(String name)
    {
        return mItems.indexOfKey(name);
    }

    /// <summary>
    /// Removes a bookmark with the specified name from the collection.
    /// </summary>
    /// <param name="name">The case-insensitive name of the bookmark.</param>
    public void remove(String name)
    {
        mItems.remove(name);
    }

    /// <summary>
    /// Removes a bookmark at the specified index.
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
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<Map.Entry<String, Integer>> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ SortedStringListGeneric<Integer> mItems = new SortedStringListGeneric<Integer>(false);
}

