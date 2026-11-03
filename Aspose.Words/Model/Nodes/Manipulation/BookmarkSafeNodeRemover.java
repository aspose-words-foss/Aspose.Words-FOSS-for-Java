// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.HashSetGeneric;


/// <summary>
/// Node remover with safe bookmark processing.
/// </summary>
/// <remarks>
/// It does not remove incomplete bookmarks inside removing range. 'Incomplete' means bookmark has either only
/// its Start or End (but not both of them) within the range.
/// </remarks>
class BookmarkSafeNodeRemover extends NodeRemover
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="joinMode"></param>
    /// <param name="processBoundBlockAnnotationAsInline">If <c>true</c>, when the range has block level annotation
    /// nodes at bounds, removal gives same result as when the annotation nodes are moved to inline level.</param>
    BookmarkSafeNodeRemover(NodeRange range, /*NodeJoinMode*/int joinMode, boolean processBoundBlockAnnotationAsInline)
    {
    	super(range, joinMode, processBoundBlockAnnotationAsInline);
	
    }

    /// <summary>
    /// Adds node to the collection of nodes pending removal.
    /// </summary>
    /// <remarks>
    /// We mimic Word behavior and do not remove bookmarks that has either only Start or End within range being removed.
    /// </remarks>
    protected /*override*/ void addNodeToRemove(Node node)
    {
        // We need to process possible bookmarks inside composite node being removed.
        if (node.isComposite())
        {
            // We will move bookmarks out of the composite node into its parent.
            if (node.getParentNode() != null)
            {
                NodeCollection bookmarks = ((CompositeNode)node).getChildNodes(G_BOOKMARK_TYPES, true);
                for (int i = bookmarks.getCount() - 1; i >= 0; i--)
                {
                    Node bookmark = bookmarks.get(i);
                    if (node.getParentNode().canInsert(bookmark))
                        node.insertNext(bookmark);
                }
            }
        }
        else
        {
            // Remember incomplete bookmark names.
            IBookmarkNode bookmark = ms.as(getCurrentNode(), IBookmarkNode.class);
            if (bookmark != null)
            {
                if (mIncompleteBookmarkNames.contains(bookmark.getName()))
                    mIncompleteBookmarkNames.remove(bookmark.getName());
                else
                    mIncompleteBookmarkNames.add(bookmark.getName());
            }
        }

        super.addNodeToRemove(node);
    }

    /// <summary>
    /// Removes nodes pending removal.
    /// </summary>
    protected /*override*/ void removeNodes()
    {
        for (Node node : getNodesToRemove())
        {
            IBookmarkNode bookmark = ms.as(node, IBookmarkNode.class);
            if ((bookmark != null) && mIncompleteBookmarkNames.contains(bookmark.getName()))
            {
                // If we here, then we are about to remove bookmark that has only either Start, or End node.
                // Word does not allow to remove such incomplete bookmark range. However, there can be situation
                // when missing Start or End is neighboring to the removing Range. In this case Word 'extends'
                // the range and successfully removes both: End and Start.
                IBookmarkNode bookmarkToRemove = (node.getNodeType() == NodeType.BOOKMARK_START)
                    ? getNeighboringBookmark(getRange().getEnd().getNode(), bookmark.getName(), true)
                    : getNeighboringBookmark(getRange().getStart().getNode(), bookmark.getName(), false);

                if (bookmarkToRemove == null)
                    continue;

                ((Node)bookmarkToRemove).remove();
            }

            node.remove();
        }
    }

    /// <summary>
    /// Returns bookmark node with a specified name neighboring to a specified node,
    /// or <c>null</c>, if there is no such bookmark.
    /// </summary>
    /// <param name="node">The node that is neighboring to the bookmark being searched.</param>
    /// <param name="name">The name of searching bookmark.</param>
    /// <param name="isForward">If <c>true</c>, then iterates forward from a specified node.</param>
    private static IBookmarkNode getNeighboringBookmark(Node node, String name, boolean isForward)
    {
        IBookmarkNode bookmark = (isForward) ? ms.as(node.getNextSibling(), IBookmarkNode.class) : ms.as(node.getPreviousSibling(), IBookmarkNode.class);
        while (bookmark != null)
        {
            if (msString.equals(bookmark.getName(), name))
                return bookmark;

            bookmark = (isForward)
                ? ms.as(((Node)bookmark).getNextSibling(), IBookmarkNode.class)
                : ms.as(((Node)bookmark).getPreviousSibling(), IBookmarkNode.class);
        }

        return null;
    }

    /// <summary>
    /// The collection of bookmark names that were occured only once.
    /// </summary>
    private /*final*/ HashSetGeneric<String> mIncompleteBookmarkNames = new HashSetGeneric<String>();

    private static /*final*/ /*NodeType*/int[] G_BOOKMARK_TYPES = { NodeType.BOOKMARK_START, NodeType.BOOKMARK_END };
}

