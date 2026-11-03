// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2015 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;


/// <summary>
/// Allows moving bookmarks from nodes that will be deleted during accepting or rejecting revisions.
/// </summary>
class BookmarkMover
{
    BookmarkMover(DocumentBase doc, RevisionHandlingContext context)
    {
        mDocument = doc;
        mContext = context;
    }

    /// <summary>
    /// Moves bookmarks from nodes that are in deleting node lists.
    /// </summary>
    void moveFromDeletingNodes()
    {
        moveFromDeletingNodesCore(mDocument, false, new ArrayList<Node>());
    }

    /// <summary>
    /// Processes the composite node to move bookmarks from nodes that will be deleted.
    /// </summary>
    /// <param name="node">a composite node to process.</param>
    /// <param name="isNodeDeleted">the composite node or any of its parents is being deleted.</param>
    /// <param name="bookmarkNodesToMove">bookmark start and bookmark end nodes to move.</param>
    private void moveFromDeletingNodesCore(CompositeNode node, boolean isNodeDeleted,
        ArrayList<Node> bookmarkNodesToMove)
    {
        if (!isNodeDeleted)
            moveNodesIfAllowedTo(node, node.getFirstChild(), bookmarkNodesToMove);

        Node curChild = node.getFirstChild();
        while (curChild != null)
        {
            Node nextChild = curChild.getNextSibling();

            if (curChild instanceof CompositeNode)
            {
                CompositeNode child = (CompositeNode)curChild;
                moveFromDeletingNodesCore(child, isNodeDeleted || mContext.isDeletingNode(child),
                    bookmarkNodesToMove);

                if (!isNodeDeleted)
                    moveNodesIfAllowedTo(node, nextChild, bookmarkNodesToMove);
            }
            else if (isNodeDeleted &&
                     ((curChild.getNodeType() == NodeType.BOOKMARK_START) ||
                      (curChild.getNodeType() == NodeType.BOOKMARK_END)))
            {
                msArrayList.add(bookmarkNodesToMove, curChild);
            }

            curChild = nextChild;
        }
    }

    /// <summary>
    /// Moves the bookmark nodes to the parent node if it is allowed.
    /// </summary>
    private static void moveNodesIfAllowedTo(CompositeNode compositeNode, Node beforeNode, ArrayList<Node> movingNodes)
    {
        // Looks like MS Word moves bookmark start nodes to the nearest paragraph. Bookmark end is inserted
        // at any appropriate place. Let's do the same.
        int i = 0;
        while (i < movingNodes.size())
        {
            Node node = movingNodes.get(i);
            if (compositeNode.canInsert(node) &&
                ((node.getNodeType() != NodeType.BOOKMARK_START) || canInsertBookmarkStart(compositeNode)) &&
                ((node.getNodeType() != NodeType.BOOKMARK_END) || 
                    !containsBookmarkStart(movingNodes, i - 1, ((BookmarkEnd)node).getName())))
            {
                compositeNode.insertBefore(node, beforeNode);
                movingNodes.remove(i);
            }
            else
            {
                i++;
            }
        }
    }

    /// <summary>
    /// Returns <c>true</c> if a bookmark start node can be inserted into the specified destination node.
    /// </summary>
    private static boolean canInsertBookmarkStart(CompositeNode destinationNode)
    {
        return destinationNode.getNodeLevel() == NodeLevel.BLOCK;
    }

    /// <summary>
    /// Returns <c>true</c> if the node list contains a bookmark start node with the specified name.
    /// Search range is limited by the specified end index.
    /// </summary>
    private static boolean containsBookmarkStart(ArrayList<Node> nodes, int endIndex, String bookmarkName)
    {
        for (int i = 0; i <= endIndex; i++)
        {
            if ((nodes.get(i).getNodeType() == NodeType.BOOKMARK_START) &&
                StringUtil.equalsIgnoreCase(((BookmarkStart)nodes.get(i)).getName(), bookmarkName))
                return true;
        }

        return false;
    }

    /// <summary>
    /// A document, from which bookmarks are being filled.
    /// </summary>
    private /*final*/ DocumentBase mDocument;
    /// <summary>
    /// Context of accepting/rejecting revisions that contains node deletion lists.
    /// </summary>
    private /*final*/ RevisionHandlingContext mContext;
}

