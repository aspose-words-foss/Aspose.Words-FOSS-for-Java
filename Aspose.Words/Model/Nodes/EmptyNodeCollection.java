// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This is an always empty collection of nodes, needed for Range.Bookmarks etc
/// collections to work when Range is obtained from a non composite node.
/// </summary>
/*static*/ class EmptyNodeCollection
{
	/* Simulation of static class by using private constructor */
	private EmptyNodeCollection()
	{}

    /// <summary>
    /// Create new instance of empty collection.
    /// WORDSNET-28048 Don't use a singleton because <see cref="NodeCollection"/> is not thread safe.
    /// </summary>
    static NodeCollection createEmpty()
    {
        return new EmptyCompositeNode().getChildNodes(NodeType.ANY, false);
    }
}

class EmptyCompositeNode extends CompositeNode
{
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SYSTEM; }

    public /*override*/ boolean accept(DocumentVisitor visitor)
    {
        return true;
    }

    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor)
    {
        return VisitorAction.SKIP_THIS_NODE;
    }

    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor)
    {
        return VisitorAction.SKIP_THIS_NODE;
    }

    /*override*/ boolean canInsert(Node node)
    {
        return false;
    }
}

