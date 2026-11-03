// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides data for methods of the <see cref="INodeChangingCallback"/> interface.
///
/// <seealso cref="DocumentBase"/>
/// <seealso cref="INodeChangingCallback"/>
/// <seealso cref="NodeChangingAction"/>
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
public class NodeChangingArgs
{
    NodeChangingArgs(Node node, Node oldParent, Node newParent, /*NodeChangingAction*/int action)
    {
        mNode = node;
        mOldParent = oldParent;
        mNewParent = newParent;
        mAction = action;
    }

    /// <summary>
    /// Gets the <see cref="Node"/> that is being added or removed.
    /// </summary>
    public Node getNode() { return mNode; }

    /// <summary>
    /// Gets the node's parent before the operation began.
    /// </summary>
    public Node getOldParent() { return mOldParent; }

    /// <summary>
    /// Gets the node's parent that will be set after the operation completes.
    /// </summary>
    public Node getNewParent() { return mNewParent; }

    /// <summary>
    /// Gets a value indicating what type of node change event is occurring.
    /// </summary>
    public /*NodeChangingAction*/int getAction() { return mAction; }

    private /*final*/ Node mNode;
    private /*final*/ Node mOldParent;
    private /*final*/ Node mNewParent;
    private /*final*/ /*NodeChangingAction*/int mAction;
}

