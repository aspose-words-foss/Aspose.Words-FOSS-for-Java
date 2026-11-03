// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/10/2014 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.HashSetGeneric;


/// <summary>
/// Finds nodes in a range.
/// </summary>
class NodeFinder extends NodeEnumerator
{
    /// <summary>
    /// Finds nodes of specific types in a range.
    /// </summary>
    static ArrayList<Node> findNodes(NodeRange range, /*params*/ /*NodeType*/int... nodeTypes)
    {
        NodeFinder finder = new NodeFinder(range, nodeTypes);
        try /*JAVA: was using*/
    	{
            return finder.find();
    	}
        finally { if (finder != null) finder.dispose(); }
    }

    ArrayList<Node> find()
    {
        getNodes().clear();

        reset();

        while (moveToNextNode())
        {
            if ((mNodeTypes.contains(getCurrentNode().getNodeType()) || mNodeTypes.contains(NodeType.ANY)) && onNodeFinding())
                getNodes().add(getCurrentNode());
        }

        return msArrayList.ctor(getNodes());
    }

    protected NodeFinder(NodeRange range, /*params*/ /*NodeType*/int... nodeTypes)
    {
    	super(range);
	
        mNodeTypes = new HashSetGeneric<Integer>(nodeTypes.length);
        for (/*NodeType*/int nodeType : nodeTypes)
            mNodeTypes.add(nodeType);

        mNodes = new ArrayList<Node>();
    }

    /// <summary>
    /// Called when a node of specific type was encountered. Returns false to skip a node otherwise true.
    /// </summary>
    protected /*virtual*/ boolean onNodeFinding()
    {
        return true;
    }

    protected ArrayList<Node> getNodes() { return mNodes; };

    private ArrayList<Node> mNodes;

    private /*final*/ HashSetGeneric<Integer> mNodeTypes;
}

