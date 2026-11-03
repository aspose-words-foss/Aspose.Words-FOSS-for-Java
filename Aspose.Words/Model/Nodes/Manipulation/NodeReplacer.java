// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msArray;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Collects nodes of specified types in node range during copying
/// and performs replace when node ragne copying is completed.
/// </summary>
abstract class NodeReplacer implements INodeCopierListener
{
    protected NodeReplacer(/*params*/ /*NodeType*/int... nodeTypes)
    {
        Debug.assertTrue(nodeTypes.length > 0);
        mNodeTypes = nodeTypes;
    }

    public void /*INodeCloningListener.*/notifyNodeCloned(Node source, Node clone)
    {
        if (msArray.indexOf((mNodeTypes), (source.getNodeType()), 0, (mNodeTypes).length) == -1)
            return;

        collectClone(source, clone);
    }

    public void /*INodeCopierListener.*/notifyNodeRangeCopied(NodeRange sourceRange, NodeRange insertedRange) throws Exception
    {
        if (!hasClones())
            return;

        for (Node node : mClones)
            replaceCollectedNode(node);

        finalizeReplace();
    }

    protected abstract void replaceCollectedNode(Node node) throws Exception;

    protected /*virtual*/ void collectClone(Node source, Node clone)
    {
        if (!hasClones())
            mClones = new ArrayList<Node>();

        msArrayList.add(mClones, clone);
    }

    protected /*virtual*/ void finalizeReplace() throws Exception
    {
    }

    private boolean hasClones() { return mClones != null; }

    private ArrayList<Node> mClones;

    private /*final*/ /*NodeType*/int[] mNodeTypes;
}

