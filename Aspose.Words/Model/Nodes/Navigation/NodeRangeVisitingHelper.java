// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Helps <see cref="DocumentVisitor"/> visit a <see cref="NodeRange"/>.
/// </summary>
class NodeRangeVisitingHelper extends NodeEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="visitor"></param>
    /// <param name="modifier"></param>
    /// <param name="extractBehavior"></param>
    NodeRangeVisitingHelper(
        NodeRange range,
        DocumentVisitor visitor,
        INodeModifier modifier,
        /*NodeExtractBehavior*/int extractBehavior)
    {
    	super(range);
	
        mVisitor = visitor;
        mModifier = modifier;
        mExtractBehavior = extractBehavior;
    }

    /// <summary>
    /// Sends a document visitor to visit all nodes of node range.
    /// Note some nodes, such as range start or end runs, may be cloned and the clones will be visited
    /// to avoid source nodes modification.
    /// </summary>
    void sendVisitorToNodeRange() throws Exception
    {
        while (moveToNextNode())
        {
            if (!getCurrentNode().isComposite())
            {
                // Clone the node if needed. This might be the case when the node is modified by the modifier
                // or when the node is the start or end node of the run that is being cut.
                Node currentNode = extractCurrentNode(mModifier, mExtractBehavior);
                if (currentNode != null)
                    currentNode.accept(mVisitor);
            }
            else
            {
                ((CompositeNode)getCurrentNode()).acceptStart(mVisitor);
            }
        }
    }

    protected /*override*/ void onMoved(/*DocumentPositionMovement*/int movement)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (movement == DocumentPositionMovement.ABOVE)
            	((CompositeNode)getCurrentNode()).acceptEnd(mVisitor);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private /*final*/ DocumentVisitor mVisitor;
    private /*final*/ INodeModifier mModifier;
    private /*final*/ /*NodeExtractBehavior*/int mExtractBehavior;
}

