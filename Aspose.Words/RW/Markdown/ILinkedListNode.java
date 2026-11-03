// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/12/2013 by Ivan Pnsk

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a node in <see cref="LinkedList"/>
/// </summary>
interface ILinkedListNode
{
    /// <summary>
    /// Gets or sets the next node in the list, or null if the current node is the last one in the list.
    /// </summary>
    public ILinkedListNode getNextNode(); public void setNextNode(ILinkedListNode value);

    /// <summary>
    /// Gets the previous node in the list, or null if the current node is the first one in the list.
    /// </summary>
    public ILinkedListNode getPrevNode(); public void setPrevNode(ILinkedListNode value);

    /// <summary>
    /// Gets or sets a main order index inside linked list.
    /// </summary>
    public int getIndex(); public void setIndex(int value);

    /// <summary>
    /// Gets or sets a secondary order index inside linked list.
    /// This index is used when a node is inserted in the middle of a list.
    /// The values sequence is ascending, but IS NOT consistent, so sequence can be 1,12,13,1000,1000001,2000000... 
    /// </summary>
    public long getSecondaryIndex(); public void setSecondaryIndex(long value);

    /// <summary>
    /// Gets True if a node is marked as removed from a list or never was added to a list.
    /// </summary>
    public boolean isNotIncluded();

    /// <summary>
    /// Mark node as removed from a list.
    /// </summary>
    public void markAsRemoved();
}

