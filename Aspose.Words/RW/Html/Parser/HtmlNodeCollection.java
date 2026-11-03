// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ListUtil;
import java.util.Iterator;


/// <summary>
/// Collection of HTML tree nodes, that represents children of a parent node.
/// </summary>
/// <remarks>
/// Order of items is preserved. Adjacent text nodes are concatenated.
/// </remarks>
class HtmlNodeCollection implements Iterable<HtmlNode>
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="parent">The parent node. <c>null</c> if the node is a root and has no parent.</param>
    HtmlNodeCollection(HtmlElementNode parent)
    {
        mItems = new ArrayList<HtmlNode>();
        mParent = parent;
    }

    /// <summary>
    /// Adds the given node to the end of the collection.
    /// </summary>
    /// <param name="node">The node that will be added.</param>
    /// <remarks>
    /// If the last node of the collection and the given node are both text nodes, then the given node will not be added, 
    /// but the last node of the collection will be replaced by a new node containing the concatenated text
    /// of the last and the given node.
    /// </remarks>
    void add(HtmlNode node)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(node != mParent);

        // Concatenate text.
        if ((node instanceof HtmlTextNode) && (mItems.size() > 0) && (mItems.get(mItems.size() - 1) instanceof HtmlTextNode))
        {
            HtmlTextNode lastNode = (HtmlTextNode)mItems.get(mItems.size() - 1);
            String text = lastNode.getText() + ((HtmlTextNode)node).getText();
            remove(lastNode);
            add(new HtmlTextNode(text));
        }
        // Append node.
        else
        {
            node.setParent(mParent);

            HtmlNode previousSibling;
            if (mItems.size() > 0)
            {
                previousSibling = mItems.get(mItems.size() - 1);
                previousSibling.setNextSibling(node);
            }
            else
            {
                previousSibling = null;
            }
            node.setPreviousSibling(previousSibling);

            node.setNextSibling(null);

            msArrayList.add(mItems, node);
        }

        mVersion++;
    }

    /// <summary>
    /// Effectively moves nodes from the specified range to this collection.
    /// </summary>
    /// <param name="range">A collection nodes to be moved from.</param>
    void moveRange(HtmlNodeCollection range)
    {
        int newCount = mItems.size() + range.mItems.size();
        ListUtil.ensureCapacity(mItems, newCount);

        for (HtmlNode node : range)
            add(node);

        range.clearCore();
    }

    /// <summary>
    /// Inserts the node to the specified position in the collection.
    /// </summary>
    /// <param name="node">The node that will be inserted.</param>
    /// <param name="index">The position to which the node will be inserted.</param>
    /// <remarks>
    /// After the insertion adjacent text nodes are replaced with one node containing concatenated text of these nodes.
    /// </remarks>
    void insert(int index, HtmlNode node)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(node != mParent);
        Debug.assertTrue(mItems.indexOf(node) < 0);
        Debug.assertTrue(index >= 0);
        Debug.assertTrue(index < mItems.size());

        // Concatenate with the previous text node.
        if ((node instanceof HtmlTextNode) && (index > 0) && (mItems.get(index - 1) instanceof HtmlTextNode))
        {
            HtmlTextNode precedingTextNode = (HtmlTextNode)mItems.get(index - 1);
            remove(precedingTextNode);
            String text = precedingTextNode.getText() + ((HtmlTextNode)node).getText();
            insert(index - 1, new HtmlTextNode(text));
        }
        // Concatenate with the previous text node.
        else if ((node instanceof HtmlTextNode) && (mItems.get(index) instanceof HtmlTextNode))
        {
            HtmlTextNode successiveTextNode = (HtmlTextNode)mItems.get(index);
            remove(successiveTextNode);
            String text = ((HtmlTextNode)node).getText() + successiveTextNode.getText();
            insert(index, new HtmlTextNode(text));
        }
        // No concatenation is required, just insert the node.
        else
        {
            node.setParent(mParent);

            HtmlNode previousSibling;
            if (index > 0)
            {
                previousSibling = mItems.get(index - 1);
                previousSibling.setNextSibling(node);
            }
            else
            {
                previousSibling = null;
            }
            node.setPreviousSibling(previousSibling);

            node.setNextSibling(mItems.get(index));
            node.getNextSibling().setPreviousSibling(node);

            mItems.add(index, node);
        }

        mVersion++;
    }

    /// <summary>
    /// Removes the node from the collection.
    /// </summary>
    /// <param name="node">The node that will be removed.</param>
    void remove(HtmlNode node)
    {
        Debug.assertTrue(mItems.indexOf(node) >= 0);

        node.setParent(null);

        if (node.getPreviousSibling() != null)
        {
            node.getPreviousSibling().setNextSibling(node.getNextSibling());
        }

        if (node.getNextSibling() != null)
        {
            node.getNextSibling().setPreviousSibling(node.getPreviousSibling());
        }

        node.setPreviousSibling(null);
        node.setNextSibling(null);

        mItems.remove(node);
        mVersion++;
    }

    /// <summary>
    /// Removes all nodes from the collection.
    /// </summary>
    void clear()
    {
        for (HtmlNode node : mItems)
        {
            node.setParent(null);
            node.setPreviousSibling(null);
            node.setNextSibling(null);
        }
        clearCore();
    }

    private void clearCore()
    {
        mItems.clear();
        mVersion++;
    }

    /// <summary>
    /// Gets the index of the specified node in the collection.
    /// </summary>
    /// <param name="node">The node whose index will be returned.</param>
    /// <returns>
    /// The index of the specified node, or -1 if the node is not in the collection.
    /// </returns>
    int indexOf(HtmlNode node)
    {
        return mItems.indexOf(node);
    }

    public Iterator<HtmlNode> iterator()
    {
        return mItems.iterator();
    }

    /// <summary>
    /// Gets an enumerator that iterates over all instances of <see cref="HtmlNode"/> in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets the number of elements actually contained in the collection.
    /// </summary>
    int getCount() { return mItems.size(); }

    /// <summary>
    /// Collection version. The marker increases every time when the collection is modified.
    /// Used for performance improvement.
    /// </summary>
    long getVersion() { return mVersion; }

    /// <summary>
    /// Gets the node at the specified index.
    /// </summary>
    HtmlNode get(int index) { return mItems.get(index); }

    private /*final*/ ArrayList<HtmlNode> mItems;

    private /*final*/ HtmlElementNode mParent;

    /// <summary>
    /// Collection version. The marker increases every time when the collection is modified.
    /// Used for performance improvement.
    /// </summary>
    /// <remarks>
    /// Version starts from 1 so the default zero value can be considered an 'unknown' version that differs from any
    /// known version of the collection.
    /// </remarks>
    private long mVersion = 1;
}

