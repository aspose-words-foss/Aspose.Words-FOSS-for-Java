// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a typed collection of <see cref="WarningInfo"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You can use this collection object as the simplest form of <see cref="IWarningCallback"/> implementation to gather 
/// all warnings that Aspose.Words generates during a load or save operation. Create an instance of this class and assign it 
/// to the <see cref="LoadOptions.WarningCallback"/> or <see cref="Aspose.Words.DocumentBase.WarningCallback"/> property.</para>
/// 
/// <seealso cref="WarningInfo"/>
/// <seealso cref="IWarningCallback"/>
/// </remarks>
public class WarningInfoCollection implements IWarningCallback, Iterable<WarningInfo>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets an item at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the item.</param>
    public WarningInfo get(int index) { return mItems.get(index); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<WarningInfo> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Removes all elements from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Implements the <see cref="IWarningCallback"/> interface. Adds a warning to this collection.
    /// </summary>
    public void warning(WarningInfo info)
    {
        msArrayList.add(mItems, info);
    }

    private /*final*/ ArrayList<WarningInfo> mItems = new ArrayList<WarningInfo>();
}

