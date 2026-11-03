// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Ivan Pereshein

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents state of <see cref="LinkedList"/>
/// </summary>
class LinkedListState
{
    /// <summary>
    /// Gets or sets head of linked list.
    /// </summary>
    ILinkedListNode getHead() { return mHead; }
    void setHead(ILinkedListNode value) { mHead = value; }

    /// <summary>
    /// Gets or sets tail of linked list.
    /// </summary>
    ILinkedListNode getTail() { return mTail; }
    void setTail(ILinkedListNode value) { mTail = value; }

    int getNextIndex()
    {
        return mLastIndex++;
    }

    private ILinkedListNode mHead;
    private ILinkedListNode mTail;
    private int mLastIndex;
}

