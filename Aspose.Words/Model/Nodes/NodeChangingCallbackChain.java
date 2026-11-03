// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msObject;


/// <summary>
/// Chains <see cref="INodeChangingCallback"/> instances to invoke them sequentially on a document
/// node changing event.
/// </summary>
class NodeChangingCallbackChain
{
    /// <summary>
    /// Called just before a node belonging to a document is about to be inserted into another node.
    /// </summary>
    void nodeInserting(NodeChangingArgs args) throws Exception
    {
        nodeChanging(NodeState.INSERTING, args);
    }

    /// <summary>
    /// Called when a node belonging to a document has been inserted into another node. 
    /// </summary>
    void nodeInserted(NodeChangingArgs args) throws Exception
    {
        nodeChanging(NodeState.INSERTED, args);
    }

    /// <summary>
    /// Called just before a node belonging to a document is about to be removed from the document.
    /// </summary>
    void nodeRemoving(NodeChangingArgs args) throws Exception
    {
        nodeChanging(NodeState.REMOVING, args);
    }

    /// <summary>
    /// Called when a node belonging to a document has been removed from its parent.
    /// </summary>
    void nodeRemoved(NodeChangingArgs args) throws Exception
    {
        nodeChanging(NodeState.REMOVED, args);
    }

    private void nodeChanging(/*NodeState*/int nodeState, NodeChangingArgs args) throws Exception
    {
        Debug.assertTrue(args != null);

        // 1. Invoke internal callbacks.
        ChainItem nextChainItem = null;
        for (ChainItem chainItem = mRootChainItem; chainItem != null; chainItem = nextChainItem)
        {
            // Remember the next chain item allowing it to be removed in the corresponding callback.
            // The chain should not be broken in this case.
            nextChainItem = chainItem.getNextChainItem();

            nodeChanging(chainItem.getCallback(), nodeState, args);
        }

        // 2. Invoke a user callback if any.
        if (mUserCallback != null)
            nodeChanging(mUserCallback, nodeState, args);
    }

    private static void nodeChanging(INodeChangingCallback callback, /*NodeState*/int nodeState, NodeChangingArgs args) throws Exception
    {
        switch (nodeState)
        {
            case NodeState.INSERTING:
                callback.nodeInserting(args);
                break;
            case NodeState.INSERTED:
                callback.nodeInserted(args);
                break;
            case NodeState.REMOVING:
                callback.nodeRemoving(args);
                break;
            case NodeState.REMOVED:
                callback.nodeRemoved(args);
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "nodeState");
        }
    }

    /// <summary>
    /// Adds the specified callback to the end of the invocation chain.
    /// </summary>
    void addInternalCallback(INodeChangingCallback callback)
    {
        Debug.assertTrue(callback != null);

        ChainItem chainItemToAdd = new ChainItem(callback);
        if (mRootChainItem == null)
        {
            mRootChainItem = chainItemToAdd;
            return;
        }

        ChainItem chainItem = mRootChainItem;
        while (chainItem.getNextChainItem() != null)
            chainItem = chainItem.getNextChainItem();

        chainItem.setNextChainItem(chainItemToAdd);
    }

    /// <summary>
    /// Removes the specified callback from the invocation chain if the callback is contained in it.
    /// Returns <c>true</c> if the callback was removed.
    /// </summary>
    boolean removeInternalCallback(INodeChangingCallback callback)
    {
        Debug.assertTrue(callback != null);

        ChainItem lastChainItem = null;
        for (ChainItem chainItem = mRootChainItem; chainItem != null; chainItem = chainItem.getNextChainItem())
        {
            if (msObject.referenceEquals(chainItem.getCallback(), callback))
            {
                if (chainItem == mRootChainItem)
                {
                    mRootChainItem = chainItem.getNextChainItem();
                }
                else
                {
                    lastChainItem.setNextChainItem(chainItem.getNextChainItem());
                }

                chainItem.setNextChainItem(null);
                return true;
            }

            lastChainItem = chainItem;
        }

        return false;
    }

    /// <summary>
    /// Gets or sets a user-defined callback.
    /// </summary>
    INodeChangingCallback getUserCallback() { return mUserCallback; }
    void setUserCallback(INodeChangingCallback value) { mUserCallback = value; }

    /// <summary>
    /// Returns a value indicating whether the chain does not contain any callbacks.
    /// </summary>
    boolean isEmpty() { return ((mRootChainItem == null) && (mUserCallback == null)); }

    /// <summary>
    /// Represents an item in the invocation chain.
    /// </summary>
    private static class ChainItem
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        ChainItem(INodeChangingCallback callback)
        {
            mCallback = callback;
        }

        /// <summary>
        /// The callback.
        /// </summary>
        INodeChangingCallback getCallback() { return mCallback; }

        /// <summary>
        /// The next item in the invocation chain.
        /// </summary>
        ChainItem getNextChainItem() { return mNextChainItem; }
        void setNextChainItem(ChainItem value) { mNextChainItem = value; }

        private /*final*/ INodeChangingCallback mCallback;
        private ChainItem mNextChainItem;
    }

    /// <summary>
    /// Specifies a state of a node raising an event.
    /// </summary>
    private /*enum*/ final class NodeState
    {
        private NodeState(){}
        
        public static final int INSERTING = 0;
        public static final int INSERTED = 1;
        public static final int REMOVING = 2;
        public static final int REMOVED = 3;

        public static final int length = 4;
    }

    private INodeChangingCallback mUserCallback;
    private ChainItem mRootChainItem;
}

