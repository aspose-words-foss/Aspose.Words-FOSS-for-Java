// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Base class for <see cref="TaskPaneCollection"/>, <see cref="WebExtensionBindingCollection"/>,
/// <see cref="WebExtensionPropertyCollection"/> and <see cref="WebExtensionReferenceCollection"/> collections.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <typeparam name="T">Type of a collection item.</typeparam>
public abstract class BaseWebExtensionCollection<T extends Object> implements Iterable<T>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the item.</param>
    public T get(int index)
    {
        return mItems.get(index);
    }
    public void set(int index, T value)
    {
        mItems.set(index, value);
    }

    /// <summary>
    /// Adds specified item to the collection.
    /// </summary>
    /// <param name="item">Item for adding.</param>
    public void add(T item)
    {
        if (item == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "item");

        msArrayList.add(mItems, item);
    }

    /// <summary>
    /// Removes all elements from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Removes the item at the specified index from the collection.
    /// </summary>
    /// <param name="index">The zero-based index of the collection item.</param>
    public void remove(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// Gets first element from the collection or null when it is not exist.
    /// </summary>
    T getFirst() { return (mItems.size() > 0) ? mItems.get(0) : null; }

    /// <summary>
    /// Gets last element from the collection or null when it is not exist.
    /// </summary>
    T getLast() { return (mItems.size() > 0) ? mItems.get(mItems.size() - 1) : null; }

    //>>>>>>>> #region   IEnumerable<TaskPane>

    /// <summary>
    /// Returns an enumerator that can iterate through a collection.
    /// </summary>
    /// <returns></returns>
    public Iterator<T> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    //<<<<<<<< #endregion   IEnumerable<TaskPane>

    /// <summary>
    /// Container for collection items.
    /// </summary>
    private /*final*/ ArrayList<T> mItems = new ArrayList<T>();
}

