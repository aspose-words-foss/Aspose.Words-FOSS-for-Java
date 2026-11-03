// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2013 by Ivan Pereshein

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Iterator over list of objects where each object has position relative other objects.
/// List is indexed using bidirectional linked list.
/// </summary>
final class LinkedList
{
    LinkedList()
    {
        mState = new LinkedListState();
    }

    private LinkedList(LinkedList parentList)
    {
        mState = parentList.mState;
        mCurrentNode = parentList.mCurrentNode;
        mPrevItem = parentList.mPrevItem;
    }

    /// <summary>
    /// Moves before the first object in the collection.
    /// Implementation for <see cref="IEnumerator{T}"/>
    /// </summary>
    void reset()
    {
        mPrevItem = null;
        mCurrentNode = null;
    }

    /// <summary>
    /// Returns the current item.
    /// Implementation for <see cref="IEnumerator{T}"/>
    /// </summary>
    ILinkedListNode getCurrent()
    {
        if (mCurrentNode == null)
            return null;

        if (mCurrentNode.isNotIncluded())
        {
            while (mCurrentNode != null && mCurrentNode.isNotIncluded())
                mCurrentNode = mCurrentNode.getNextNode();

            mPrevItem = mCurrentNode == null ? null : mCurrentNode.getPrevNode();
        }

        return mCurrentNode;
    }

    /// <summary>
    /// Gets item which is after the <see cref="Current"/>.
    /// Returns Null if there is no such item, or if item is Null itself.
    /// </summary>
    ILinkedListNode getNext()
    {
        return (mCurrentNode == null) ? null : mCurrentNode.getNextNode();
    }

    /// <summary>
    /// Gets item which is before the <see cref="Current"/>.
    /// Returns Null if there is no such item, or if item is Null itself.
    /// </summary>
    ILinkedListNode getPrevious()
    {
        return (mCurrentNode == null) ? null : mCurrentNode.getPrevNode();
    }

    /// <summary>
    /// Moves to the next object in the linked list, or void if moved from the last item.
    /// Implementation for <see cref="IEnumerator{T}"/>
    /// </summary>
    boolean moveNext()
    {
        if (isEmpty())
            return false;

        if (mCurrentNode == null)
        {
            if (mPrevItem == null)
                return moveFirst();

            return false;
        }

        mPrevItem = mCurrentNode;
        mCurrentNode = mCurrentNode.getNextNode();

        // Skip removed/not-added nodes if any.
        while ((mCurrentNode != null) && mCurrentNode.isNotIncluded())
            mCurrentNode = mCurrentNode.getNextNode();

        if (mCurrentNode != null)
            mPrevItem = mCurrentNode.getPrevNode();

        return mCurrentNode != null;
    }

    /// <summary>
    /// Moves to the previous item in the linked list, or void if moved from the first item.
    /// </summary>
    boolean movePrevious()
    {
        if (isEmpty())
            return false;

        if (mCurrentNode != null && mCurrentNode.getPrevNode() != mPrevItem)
            mPrevItem = mCurrentNode.getPrevNode();

        if (mPrevItem == null)
        {
            if (mCurrentNode == null)
                return false;

            reset();

            return false;
        }
        mCurrentNode = mPrevItem;
        mPrevItem = mPrevItem.getPrevNode();

        return true;
    }

    /// <summary>
    /// Moves to the head of linked list.
    /// </summary>
    boolean moveFirst()
    {
        if (isEmpty())
            return false;

        mCurrentNode = mState.getHead();
        mPrevItem = null;

        return true;
    }

    /// <summary>
    /// Moves to the tail in the linked list.
    /// </summary>
    boolean moveLast()
    {
        if (isEmpty())
            return false;

        mCurrentNode = mState.getTail();
        mPrevItem = mCurrentNode.getPrevNode();

        return true;
    }

    /// <summary>
    /// Moves iterator to the specified item. Throws if movement fails.
    /// </summary>
    void moveObject(ILinkedListNode obj)
    {
        if (!moveObjectSafe(obj))
            throw new IllegalStateException("Object is not in the list.");
    }

    /// <summary>
    /// Moves iterator to the specified item. Returns False if movement fails.
    /// </summary>
    boolean moveObjectSafe(ILinkedListNode obj)
    {
        if (obj.isNotIncluded())
            return false;
        mCurrentNode = obj;
        mPrevItem = mCurrentNode.getPrevNode();
        return true;
    }

    /// <summary>
    /// Returns True if iterator is over the first object in the collection.
    /// </summary>
    boolean isFirst()
    {
        if (mCurrentNode == null)
            throw new IllegalStateException("There is no current object.");

        return mCurrentNode == mState.getHead();
    }

    /// <summary>
    /// Returns True if iterator is over the last object in the collection.
    /// </summary>
    boolean isLast()
    {
        if (mCurrentNode == null)
            throw new IllegalStateException("There is no current object.");

        return mCurrentNode == mState.getTail();
    }

    /// <summary>
    /// Returns True if iterator is not over an object. This happens if iterator
    /// was moved after the last item or before the first item or <see cref="Reset"/>.
    /// </summary>
    boolean isVoid() { return mCurrentNode == null; }

    /// <summary>
    /// Returns true if the linked list does not contain any items.
    /// </summary>
    boolean isEmpty() { return mState.getHead() == null; }

    /// <summary>
    /// Returns True if iterator is beyond the last item in the linked list.
    /// </summary>
    boolean isAfter() { return mCurrentNode == null && mPrevItem != null; }

    /// <summary>
    /// Returns True, if LinkedList contains two or more nodes.
    /// </summary>
    boolean isMoreThanOneNode() { return !isEmpty() && mState.getHead().getNextNode() != null; }

    /// <summary>
    /// Returns True if left object is positioned before the right object in the list.
    /// </summary>
    static boolean isThisOrder(ILinkedListNode left, ILinkedListNode right)
    {
        Debug.assertTrue(left != null);
        Debug.assertTrue(right != null);

        // TODO Normally we shall make sure that left and right are from the same list, which is this one.

        if (left == right)
            return false;

        if (left.getIndex() != right.getIndex())
            return left.getIndex() < right.getIndex();

        if (left.getSecondaryIndex() != right.getSecondaryIndex())
            return left.getSecondaryIndex() < right.getSecondaryIndex();

        // This happens if both indexes match. Internal error.
        throw new IllegalStateException("Linked list is in invalid state.");
    }

    /// <summary>
    /// Insert first item inside the empty linked list.
    /// </summary>
    private void insertFirstItem(ILinkedListNode node)
    {
        node.setIndex(mState.getNextIndex());
        mState.setHead(node);
        mState.setTail(node);
        mPrevItem = node;
    }

    /// <summary>
    /// Inserts an item before the current item (into current item's range). Iterator is not moved.
    /// </summary>
    void insert(ILinkedListNode node)
    {
        if (!node.isNotIncluded())
            throw new IllegalStateException("An object already included into list.");

        if (isEmpty())
        {
            insertFirstItem(node);
        }
        else
        {
            if (mCurrentNode == null)
                throw new IllegalStateException("There is no current object.");

            node.setPrevNode(mCurrentNode.getPrevNode());
            mCurrentNode.setPrevNode(node);
            node.setNextNode(mCurrentNode);

            if (node.getPrevNode() == null)
            {
                Debug.assertTrue(mCurrentNode == mState.getHead());
                node.setIndex(node.getNextNode().getIndex() - 1);
                mState.setHead(node);
            }
            else
            {
                node.getPrevNode().setNextNode(node);

                // If there is a skipped index (if a node was removed).
                if (node.getNextNode().getIndex() > node.getPrevNode().getIndex() + 1)
                {
                    node.setIndex(node.getPrevNode().getIndex() + 1);
                }
                else
                {
                    node.setIndex(node.getPrevNode().getIndex());
                    calculateSecondaryIndex(node);
                }
            }

            mPrevItem = mCurrentNode.getPrevNode();
        }
    }

    /// <summary>
    /// Append an item after the current object. Iterator is not moved.
    /// </summary>
    boolean append(ILinkedListNode node)
    {
        if (!node.isNotIncluded())
            throw new IllegalStateException("An object already included into list.");

        if (mCurrentNode == null)
        {
            Debug.assertTrue(isEmpty());
            insertFirstItem(node);
        }
        else
        {

            ILinkedListNode next = mCurrentNode.getNextNode();
            mCurrentNode.setNextNode(node);
            node.setPrevNode(mCurrentNode);
            node.setNextNode(next);

            if (next == null)
            {
                Debug.assertTrue(mState.getTail() == mCurrentNode);
                mState.setTail(node);
                node.setIndex(mState.getNextIndex());
            }
            else
            {
                next.setPrevNode(node);

                // If there is a skipped index (if a node was removed).
                if (next.getIndex() >  mCurrentNode.getIndex() + 1)
                {
                    node.setIndex(mCurrentNode.getIndex() + 1);
                }
                else
                {
                    node.setIndex(mCurrentNode.getIndex());
                    calculateSecondaryIndex(node);
                }
            }
        }
        return true;
    }

    /// <summary>
    /// Removes current object. Iterator is moved to the next object.
    /// Cannot be called "Remove" because it will clash with Iterator.remove() when auto ported to Java.
    /// </summary>
    void removeCurrentNode()
    {
        if (mCurrentNode == null)
            return;

        ILinkedListNode prev = mCurrentNode.getPrevNode();
        ILinkedListNode next = mCurrentNode.getNextNode();

        if (prev == null)
            mState.setHead(next);
        else
            prev.setNextNode(next);

        if (next == null)
            mState.setTail(prev);
        else
            next.setPrevNode(prev);

        mCurrentNode.markAsRemoved();

        mCurrentNode = next;
        mPrevItem = prev;
    }

    /// <summary>
    /// Returns copy of this instance which shares same collection (even if it's empty).
    /// </summary>
    LinkedList deepClone()
    {
        LinkedList clone = new LinkedList(this);
        return clone;
    }

    //>>>>>>>> #region  Implementation

    private static void calculateSecondaryIndex(ILinkedListNode node)
    {
        // WORDSNET-24888 API performance issue with large documents.
        // Performance optimization: Use dispersed secondary indexes to minimize following nodes
        // secondary index updating time.
        final /*uint*/int BIG_INCREMENT = 1000000000;
        final /*uint*/int SMALL_INCREMENT = 100000;

        if (node.getNextNode().getIndex() != node.getIndex())
        {
            node.setSecondaryIndex(node.getPrevNode().getSecondaryIndex() + (BIG_INCREMENT & 0xFFFFFFFFL));
        }
        else
        {
            long secondaryIndex = node.getPrevNode().getSecondaryIndex() + 1;
            if (secondaryIndex < node.getNextNode().getSecondaryIndex())
            {
                node.setSecondaryIndex(secondaryIndex);
            }
            else
            {
                secondaryIndex = node.getPrevNode().getSecondaryIndex() + (SMALL_INCREMENT & 0xFFFFFFFFL);
                node.setSecondaryIndex(secondaryIndex);
                ILinkedListNode nextNode = node.getNextNode();
                while ((nextNode != null) && (nextNode.getIndex() == node.getIndex()) &&
                    (nextNode.getSecondaryIndex() <= secondaryIndex))
                {
                    secondaryIndex += (SMALL_INCREMENT & 0xFFFFFFFFL);
                    nextNode.setSecondaryIndex(secondaryIndex);
                    nextNode = nextNode.getNextNode();
                }
            }
        }
    }

    private /*final*/ LinkedListState mState;
    private ILinkedListNode mCurrentNode;
    private ILinkedListNode mPrevItem;


    /// <summary>
    /// For unit tests only.
    /// </summary>
    ILinkedListNode getGetHead_Test() { return mState.getHead(); }

    /// <summary>
    /// For unit tests only.
    /// </summary>
    ILinkedListNode getGetTail_Test() { return mState.getTail(); }

    /// <summary>
    /// For unit tests only.
    /// </summary>
    ILinkedListNode getNode_Test(String p)
    {
        ILinkedListNode node = mState.getHead();

        while (node != null)
        {
            if (msString.equals(node.toString(), p))
                return node;
            node = node.getNextNode();
        }

        return node;
    }

    //<<<<<<<< #endregion 

}


