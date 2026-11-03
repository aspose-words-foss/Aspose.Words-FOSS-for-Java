// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to receive notifications when nodes are inserted or removed in the document.
/// </summary>
public interface INodeChangingCallback
{
    /// <summary>
    /// Called just before a node belonging to this document is about to be inserted into another node.
    /// </summary>
    public void nodeInserting(NodeChangingArgs args) throws Exception;

    /// <summary>
    /// Called when a node belonging to this document has been inserted into another node. 
    /// </summary>
    public void nodeInserted(NodeChangingArgs args) throws Exception;

    /// <summary>
    /// Called just before a node belonging to this document is about to be removed from the document.
    /// </summary>
    public void nodeRemoving(NodeChangingArgs args) throws Exception;

    /// <summary>
    /// Called when a node belonging to this document has been removed from its parent.
    /// </summary>
    public void nodeRemoved(NodeChangingArgs args) throws Exception;
}

