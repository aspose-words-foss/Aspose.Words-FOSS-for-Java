// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2005 by Konstantin Sidorenko

package com.aspose.words;

import com.aspose.words.ref.Ref;

import java.util.Iterator;


/// <summary>
/// MANUALLY ported because this iterator is passed to the Jaxen XPath engine which
/// calls hasNext() multiple times per loop and that does not work with our pattern for
/// automated porting of iterators.
///
/// Provides for-each enumeration over a <see cref="NodeCollection"/> and <see cref="CompositeNode"/>.
/// </summary>
final class NodeCollectionEnumerator<T extends Node> implements Iterator<T>
{
    NodeCollectionEnumerator(INodeCollection nodes)
    {
        mNodes = nodes;
        reset();
    }

    public T next()
    {
	    //JAVA-added move cursor code (.Net just returns cursor (already moved by MoveNext()) here.
	    hasNext();//get next node if any.
	    mCurNode = mNextNode;//move cursor to the next.

	    if ((mCurNode == mNodes.getContainer()) || (mCurNode == null))
            throw new IllegalStateException("Invalid position of the enumerator.");

        return mCurNode;
    }

 	//JAVA-changed .Net MoveNext() to implement java contract of hasNext():
	//just checks next node (and saves it in mNextNode) but doesn't moves the cursor.
	public boolean hasNext()
    {
        if (mCurNode == null)
            return false;

	    mNextNode = mCurNode;
        // MoveNext used to throw if document structure is changed, but it's convenient in many cases
        // to ignore the changes if they don't break iterating. Further, we use a trick to make possible
        // to remove the current node during iterating through NodeCollection. If the current node has
        // been deleted, we use "the next after the previous visited" node to step forward.

        if ((mCurNode != mNodes.getContainer()) && (mCurNode.getParentNode() == null))
        {
            // mCurNode here may become a node outside the node collection.
	        mNextNode = mPrevNode;
        }

        // Throw only if iterating cannot be continued.
        if ((mNextNode != mNodes.getContainer()) && (mNextNode.getParentNode() == null))
            throw new IllegalStateException("Document structure was changed.");

        // mCurNode here always becomes a node of the node collection (or null after the end).
        try
        {
            mPrevNode = (T) mNodes.getNextMatchingNode(mNextNode);
            mNextNode = (T) mNodes.getCurrentNode();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return (mNextNode != null);
    }

    public void reset()
    {
        mCurNode = (T) mNodes.getContainer();
    }

	// Code moved here from NodeCollection for portability:
	// MoveNext() is hasNext() in java and doesn't move cursor. So it is better to implement
	// this functionality inside this manually ported class (the body is slightly changed in java).
	public static <T extends Node> int getCount(INodeCollection collection)
	{
		int count = 0;
		NodeCollectionEnumerator<T> enumerator = new NodeCollectionEnumerator<T>(collection);
		while (enumerator.hasNext())
		{
			enumerator.mCurNode = enumerator.mNextNode;//move the cursor
			count++;
		}

		return count;
	}

    /// <summary>
    /// A collection to iterate trough.
    /// </summary>
    private /*final*/ INodeCollection mNodes;

    /// <summary>
    /// The iterating goes through all nodes of an underlying container of the collection and stops on matching nodes.
    /// This field is the previous visited node of the container. It may be outside of the collection.
    /// </summary>
    private T mPrevNode;

    /// <summary>
    /// The current node of the collection.
    /// Has two special values: <c>mNodes.Container</c> means "before the first element" and
    /// <c>null</c> means "after the last element"
    /// </summary>
    private T mCurNode;

	//JAVA-added to implement java contract of hasNext() - check next, but doesn't move the cursor (saved in mNextNode)
    private T mNextNode;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

