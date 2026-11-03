// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Modifies identifiers of cloned comment nodes.
/// </summary>
/// <seealso cref="CommentIdsRegenerator"/>
class CommentIdsRegeneratorNodeModifier implements INodeModifier
{
    public Node /*INodeModifier.*/modify(
        Node referenceNode,
        Node nodeToModify,
        boolean modifyChildren,
        INodeCloningListener cloningListener)
    {
        mCommentIdsRegenerator.regenerateCommentIds(nodeToModify);
        return nodeToModify;
    }

    private /*final*/ CommentIdsRegenerator mCommentIdsRegenerator = new CommentIdsRegenerator();
}

