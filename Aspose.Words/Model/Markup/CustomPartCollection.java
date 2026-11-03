// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of <see cref="CustomPart"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not normally need to create instances of this class. You access custom parts 
/// related to the OOXML package via the <see cref="Document.PackageCustomParts"/> property.</para>
/// 
/// <seealso cref="CustomPart"/>
/// <seealso cref="Document.PackageCustomParts"/>
/// </remarks>
public class CustomPartCollection implements Iterable<CustomPart>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the item.</param>
    public CustomPart get(int index) { return mItems.get(index); }
    public void set(int index, CustomPart value) { mItems.set(index, value); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<CustomPart> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an item to the collection.
    /// </summary>
    /// <param name="part">The item to add.</param>
    public void add(CustomPart part)
    {
        msArrayList.add(mItems, part);
    }

    /// <summary>
    /// Removes an item at the specified index.
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
    /// Makes a deep copy of this collection and its items.
    /// </summary>
    public CustomPartCollection deepClone()
    {
        // We don't have derived classes therefore we simply create a new object, don't memberwise clone.
        CustomPartCollection lhs = new CustomPartCollection();

        for (CustomPart item : mItems)
            lhs.add(item.deepClone());

        return lhs;
    }

    private /*final*/ ArrayList<CustomPart> mItems = new ArrayList<CustomPart>();
}

