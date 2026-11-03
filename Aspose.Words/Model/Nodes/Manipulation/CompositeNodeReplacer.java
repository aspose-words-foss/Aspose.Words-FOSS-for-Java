// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Collects composite nodes of specified types in node range during copying
/// and replaces them with children when node ragne copying is completed.
/// </summary>
class CompositeNodeReplacer extends NodeReplacer
{
    CompositeNodeReplacer(/*params*/ /*NodeType*/int... nodeTypes)
    {
    	super(nodeTypes);
	
    }

    protected /*override*/ void replaceCollectedNode(Node node)
    {
        CompositeNode compositeNode = (CompositeNode)node;

        while (compositeNode.getFirstChild() != null)
            compositeNode.insertPrevious(compositeNode.getFirstChild());

        compositeNode.remove();
    }
}

