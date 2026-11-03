// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.Debug;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of execution items.
/// </summary>
class ExecutionItemCollection implements Iterable<IExecutionItem>
{
    /// <summary>
    /// Adds an execution item to the collection.
    /// </summary>
    /// <param name="executionItem">The execution item to add.</param>
    void add(IExecutionItem executionItem)
    {
        msArrayList.add(mItems, executionItem);
    }

    public Iterator<IExecutionItem> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets an execution item at the specified index.
    /// </summary>
    IExecutionItem get(int index)
    {
        Debug.assertTrue((index >= 0) && (index < mItems.size()));
        return mItems.get(index);
    }

    /// <summary>
    /// Return the total number of execution items in this collection.
    /// </summary>
    int getCount() { return mItems.size(); }

    private /*final*/ ArrayList<IExecutionItem> mItems = new ArrayList<IExecutionItem>();
}

