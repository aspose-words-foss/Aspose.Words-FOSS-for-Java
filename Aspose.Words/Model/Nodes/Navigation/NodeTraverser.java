// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Traverses over a range of nodes in a special manner. Calls appropriate methods while traversing.
/// See summary for the <see cref="Traverse"/> method.
/// </summary>
class NodeTraverser extends NodeEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    protected NodeTraverser(NodeRange range)
    {
    	super(range, true);
	
    }

    /// <summary>
    /// Moves from the start node to the end node of the range in a special manner. It tends to move upwards
    /// and does not move down unless the current composite node is an ancestor of the end node. It allows efficiently
    /// manipulating nodes in a range, e.g. copying or removing. Additionally, calls different protected virtual
    /// methods that signalize about some events during traversal.
    /// </summary>
    protected void traverse() throws Exception
    {
        mNeedStopTraverse = false;
        boolean isDeep = true;
        Node oldNode = null;

        // isByNode should be set to false we could stop at ends of composite nodes.
        while (!mNeedStopTraverse && moveToNextNode(isDeep, false))
        {
            // Do not stop at the same node twice.
            if (oldNode == getCurrentNode())
                continue;

            if (getCurrentPosition().isEnd())
            {
                if (getCurrentNode().isComposite() && NodeUtil.isAncestorOrSelf(getRange().getStart().getNode(), getCurrentNode()))
                    onStartNodeAncestor();
            }
            else if (getCurrentNode().isComposite())
            {
                if (!NodeUtil.isAncestorOrSelf(getRange().getEnd().getNode(), getCurrentNode()))
                {
                    onMiddleNodeAncestor();
                    isDeep = false;
                }
                else
                {
                    onEndNodeAncestor();
                    isDeep = true;
                }
            }
            else
            {
                onNonCompositeNode();
                isDeep = false;
            }

            oldNode = getCurrentNode();
        }
    }

    /// <summary>
    /// Called when a composite node that owns the start node in the range is encountered.
    /// </summary>
    protected /*virtual*/ void onStartNodeAncestor() throws Exception
    {
        //  Implementation is not required.
    }

    /// <summary>
    /// Called when a composite node that does not own the start or end node in the range is encountered.
    /// </summary>
    protected /*virtual*/ void onMiddleNodeAncestor() throws Exception
    {
        //  Implementation is not required.
    }

    /// <summary>
    /// Called when a composite node that owns the end node in the range is encountered.
    /// </summary>
    protected /*virtual*/ void onEndNodeAncestor() throws Exception
    {
        //  Implementation is not required.
    }

    /// <summary>
    /// Called when a non-composite node is encountered.
    /// </summary>
    protected /*virtual*/ void onNonCompositeNode() throws Exception
    {
        //  Implementation is not required.
    }

    /// <summary>
    /// Stops traversal.
    /// </summary>
    protected void stopTraverse()
    {
        mNeedStopTraverse = true;
    }

    private boolean mNeedStopTraverse;
}

