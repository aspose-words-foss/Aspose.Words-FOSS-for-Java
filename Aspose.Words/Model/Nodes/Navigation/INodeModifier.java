// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, used by <see cref="NodeCopier"/> and its descendants to modify cloned nodes,
/// for apply different formatting, decode text etc.
/// </summary>
interface INodeModifier
{
    /// <summary>
    /// Modifies the specified node.
    /// </summary>
    /// <remarks>
    /// This method changes nodeToModify.
    /// It does not change referenceNode. This parameter may be used to calculate the changes to make on nodeToModify.
    /// </remarks>
    /// <param name="referenceNode"></param>
    /// <param name="nodeToModify"></param>
    /// <param name="modifyChildren"></param>
    /// <param name="cloningListener"></param>
    public Node modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener) throws Exception;
}

