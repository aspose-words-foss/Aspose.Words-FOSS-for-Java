// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.LinkedList;


/// <summary>
/// Represents a queue of execution items.
/// </summary>
class ExecutionQueue
{
    /// <summary>
    /// Enqueues an execution item.
    /// </summary>
    /// <param name="item">The item to enqueue.</param>
    void enqueue(IExecutionItem item)
    {
        Debug.assertTrue(item != null);
        mQueue.addLast(item);
    }

    /// <summary>
    /// Dequeues an execution item.
    /// </summary>
    /// <returns>The dequeued item.</returns>
    IExecutionItem dequeue()
    {
        return mQueue.removeFirst();
    }

    /// <summary>
    /// Gets the number of execution items contained in this queue.
    /// </summary>
    int getCount() { return mQueue.size(); }

    private /*final*/ LinkedList<IExecutionItem> mQueue = new LinkedList<IExecutionItem>();
}

