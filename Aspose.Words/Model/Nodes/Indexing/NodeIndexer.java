// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/08/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.SortedIntegerListGeneric;


/// <summary>
/// Builds document or document part text and index each node within text to provide fast access.
/// </summary>
class NodeIndexer
{
    NodeIndexer(Node rootNode)
    {
        setRootNode(rootNode);
    }

    NodeIndexer(ArrayList<Node> nodeList)
    {
        mList = nodeList;
    }

    NodeIndexer(NodeRange nodeRange)
    {
        mNodeRange = nodeRange;
        setRootNode(null);
    }

    NodeIndexer(NodeRange nodeRange, Node rootNode)
    {
        mNodeRange = nodeRange;
        setRootNode(rootNode);
    }

    /// <summary>
    /// Returns node which corresponds to given position.
    /// </summary>
    Node getNodeByPosition(int position)
    {
        return mPositions.getByIndex(getCachedIndexFromPosition(position));
    }

    /// <summary>
    /// Returns start position of node which corresponds to given position.
    /// </summary>
    int getNodeStartByPosition(int position)
    {
        return mPositions.getKey(getCachedIndexFromPosition(position));
    }

    void replace(int key, int newKey, Node value)
    {
        mPositions.replace(key, newKey, value);
        invalidateCache();
    }

    void remove(int key)
    {
        mPositions.remove(key);
        invalidateCache();
    }

    void add(int key, Node value)
    {
        mPositions.add(key, value);
        invalidateCache();
    }

    void update() throws Exception
    {
        mSb.setLength(0);
        mText = null;
        mPositions.clear();
        invalidateCache();

        if (mNodeRange != null)
            updateRangeIndexes();
        else if (mList != null)
        {
            int position = 0;
            for (Node node : mList)
            {
                setRootNode(node);
                int len = buildIndex(node, position);
                position += len;
            }
        }
        else
        {
            buildIndex(getRootNode(), 0);
        }
    }

    /// <summary>
    /// Inserts new node into index and do fast index update.
    /// </summary>
    /// <remarks>
    /// Inserts new key/value pair with low-level keys shift.
    /// </remarks>
    void insertAndUpdate(int key, Node node, String text)
    {
        mPositions.shift(key, text.length());
        mPositions.add(key, node);

        mText.insert(key, text);

        invalidateCache();
    }

    /*virtual*/ /*IndexerAction*/int onNode(Node node) throws Exception
    {
        return IndexerAction.NONE;
    }

    protected /*virtual*/ String getText(Node node)
    {
        return node.getText();
    }

    protected /*virtual*/ String getEndText(CompositeNode node)
    {
        return node.getEndText();
    }

    /// <summary>
    /// Adds index for a specified node.
    /// </summary>
    protected /*virtual*/ void add(int position, Node node, String text)
    {
        msStringBuilder.append(mSb, text);
        mPositions.add(position, node);
    }

    /// <summary>
    /// Updates indexes for range nodes.
    /// </summary>
    private void updateRangeIndexes() throws Exception
    {
        DocumentPosition curPosition = getRootNode() == null
            ? mNodeRange.getStart().deepClone()
            : new DocumentPosition(getRootNode());

        if (!mNodeRange.isStartIncluded() && (getRootNode() == null))
            curPosition.moveNext(true);

        int textPosition = 0;
        Node ignoreParent = null;

        while ((curPosition.getNode() != mNodeRange.getEnd().getNode()) || (mNodeRange.isEndIncluded() && !curPosition.isEnd()))
        {
            curPosition.move(null, true, true, true, false, true);

            Node node = curPosition.getNode();

            if (getRootNode() != null && !node.isAncestorNode(getRootNode()))
                break;

            if (ignoreParent != null)
            {
                if ((node == ignoreParent) || node.isAncestorNode(ignoreParent))
                    continue;

                ignoreParent = null;
            }

            if (!curPosition.isEnd())
            {
                /*IndexerAction*/int action = onNode(node);
                switch (action)
                {
                    case IndexerAction.COLLAPSE:
                        ignoreParent = node;
                        break;
                    case IndexerAction.IGNORE:
                        ignoreParent = node;
                        continue;
                    case IndexerAction.SKIP:
                        continue;
                }

                continue;
            }

            String text = node.isComposite()
                ? getEndText((CompositeNode)node)
                : getText(node);

            int textLength = text.length();
            if (textLength > 0)
            {
                add(textPosition, node, text);
                textPosition += textLength;
            }
        }
    }

    /// <summary>
    /// Cached version of <see cref="GetIndexFromPosition"/> method.
    /// </summary>
    /// <remarks>
    /// Simple trick to avoid repeated search of index when consecutive calls with the same position are made.
    /// Main purpose is to make caller code simpler.
    /// </remarks>
    private int getCachedIndexFromPosition(int position)
    {
        if (position != mCachedPosition)
        {
            mCachedPosition = position;
            mCachedIndex = getIndexFromPosition(position);
        }

        return mCachedIndex;
    }

    /// <summary>
    /// Performs binary search for given position.
    /// </summary>
    private int getIndexFromPosition(int position)
    {
        //This algorithm is adopted from the .NET BinarySearch function.
        int lo = 0;
        int hi = mPositions.getCount();

        while (lo <= hi)
        {
            int i = (lo + hi) >> 1;

            int start = mPositions.getKey(i);
            int end = ((i + 1) < mPositions.getCount()) ? mPositions.getKey(i + 1) : Integer.MAX_VALUE;

            if (position < start)
            {
                // Position is before this node, make next iteration to look closer to the beginning.
                hi = i - 1;
            }
            else if (position >= end)
            {
                // Position is after this node, make next iteration to look close to the end.
                lo = i + 1;
            }
            else
            {
                // Return position.
                return i;
            }
        }

        return 0;
    }

    private int buildIndex(Node node, int position) throws Exception
    {
        /*IndexerAction*/int action = onNode(node);

        // Ignore node completely.
        if (action == IndexerAction.IGNORE)
            return 0;

        // Whole node is collapsed.
        if (action == IndexerAction.COLLAPSE)
        {
            String nodeText = getText(node);
            add(position, node, nodeText);
            return nodeText.length();
        }

        if (node.isComposite())
        {
            int compositeLength = 0;

            CompositeNode compositeNode = (CompositeNode)node;

            for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
            {
                int length = buildIndex(childNode, position);

                position += length;
                compositeLength += length;
            }

            action = onNode(compositeNode);
            if (action != IndexerAction.SKIP)
            {
                String endText = getEndText(compositeNode);
                if (endText.length() > 0)
                {
                    add(position, compositeNode, endText);
                    compositeLength += endText.length();
                }
            }

            return compositeLength;
        }
        else
        {
            if (action != IndexerAction.SKIP)
            {
                String text = getText(node);

                if (text.length() > 0)
                    add(position, node, text);

                return text.length();
            }

            return 0;
        }
    }

    private void invalidateCache()
    {
        mCachedIndex = -1;
        mCachedPosition = -1;
    }

    SortedIntegerListGeneric<Node> getPositions() { return mPositions; }

    /// <summary>
    /// Text being indexed by the indexer.
    /// </summary>
    ChunkedString getText()
    {
        if (mText == null)
            mText = new ChunkedString(mSb.toString());

        return mText;
    }

    Node getRootNode() { return mRootNode; }; private void setRootNode(Node value) { mRootNode = value; };

    private Node mRootNode;

    private int mCachedPosition = -1;
    private int mCachedIndex = -1;

    private /*final*/ NodeIndex mPositions = new NodeIndex();
    private /*final*/ StringBuilder mSb = new StringBuilder();
    private ChunkedString mText;
    private /*final*/ ArrayList<Node> mList;
    private /*final*/ NodeRange mNodeRange;
}

