// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// A typed collection of <see cref="OdsoRecipientData"/>
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/mail-merge-and-reporting/">Mail Merge and Reporting</a> documentation article.</para>
/// </summary>
/// <remarks>
/// 
/// <seealso cref="OdsoRecipientData"/>
/// <seealso cref="Odso.RecipientDatas"/>
/// </remarks>
public class OdsoRecipientDataCollection implements Iterable<OdsoRecipientData>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item in this collection.
    /// </summary>
    public OdsoRecipientData get(int index) { return mItems.get(index); }
    public void set(int index, OdsoRecipientData value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mItems.set(index, value);
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<OdsoRecipientData> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an object to the end of this collection.
    /// </summary>
    /// <param name="value">The object to add. Cannot be <c>null</c>.</param>
    public int add(OdsoRecipientData value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        msArrayList.add(mItems, value);
        return mItems.size() - 1;
    }

    /// <summary>
    /// Removes all elements from this collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Removes the element at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the element.</param>
    public void removeAt(int index)
    {
        mItems.remove(index);
    }

    private /*final*/ ArrayList<OdsoRecipientData> mItems = new ArrayList<OdsoRecipientData>();
}

