// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a listener to <see cref="NodeCopier"/> events.
/// </summary>
interface INodeCopierListener extends INodeCloningListener
{
    /// <summary>
    /// This method is invoked when the whole node range has been copied.
    /// </summary>
    public void notifyNodeRangeCopied(NodeRange sourceRange, NodeRange insertedRange) throws Exception;
}

