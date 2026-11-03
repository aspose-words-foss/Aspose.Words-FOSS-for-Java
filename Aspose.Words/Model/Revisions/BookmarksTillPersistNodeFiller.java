// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2015 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StringUtil;


/// <summary>
/// Allows filling bookmarks that are on deleting text at the begin of a document. 
/// </summary>
class BookmarksTillPersistNodeFiller
{
    BookmarksTillPersistNodeFiller(DocumentBase doc, RevisionHandlingContext context)
    {
        mDocument = doc;
        mContext = context;
    }

    /// <summary>
    /// Fills bookmarks that are on deleting text at the begin of a document.
    /// </summary>
    Map<Node, Node> fillBookmarks()
    {
        mBookmarks = new HashMap<Node, Node>();
        fillBookmarksForNode(mDocument, false);
        return mBookmarks;
    }

    /// <summary>
    /// Recursive method to fill bookmarks from the passed composite node and all its children. 
    /// </summary>
    /// <param name="node">a composite node to process.</param>
    /// <param name="isNodeDeleted">the composite node or any of its parents is being deleted.</param>
    /// <returns>false if a node that will not be deleted is achived and 
    /// the process should be stopped.</returns>
    private boolean fillBookmarksForNode(CompositeNode node, boolean isNodeDeleted)
    {
        boolean first = true;
        Node curChild = node.getFirstChild();
        while (curChild != null)
        {
            if (!isNodeDeleted && BookmarkDeleter.isBookmarkTargetNode(curChild))
            {
                if (!mContext.isDeletingNode(curChild))
                {
                    if (first)
                    {
                        // First node of document is not deleted: should not delete bookmarks
                        mBookmarks.clear();
                    }
                    return false;
                }
                first = false;
            }

            if (curChild instanceof CompositeNode)
            {
                if (!fillBookmarksForNode((CompositeNode)curChild, 
                        isNodeDeleted || mContext.isDeletingNode(curChild)))
                    return false;
            }
            else if (curChild.getNodeType() == NodeType.BOOKMARK_START)
            {
                msDictionary.add(mBookmarks, curChild, null);
            }
            else if (curChild.getNodeType() == NodeType.BOOKMARK_END)
            {
                Node start = findBookmarkStart(mBookmarks, (BookmarkEnd)curChild);
                if (start != null)
                    mBookmarks.put(start, curChild);
            }

            curChild = curChild.getNextSibling();
        }
        return true;
    }

    /// <summary>
    /// Searches bookmark start by bookmark end in a bookmark start-to-end dictionary.
    /// </summary>
    private static Node findBookmarkStart(Map<Node, Node> bookmarkStartToBookmarkEnd, BookmarkEnd bookmarkEnd)
    {
        for (Node node : bookmarkStartToBookmarkEnd.keySet())
        {
            BookmarkStart bookmarkStart = (BookmarkStart)node;
            if (StringUtil.equalsIgnoreCase(bookmarkStart.getName(), bookmarkEnd.getName()))
                return bookmarkStart;
        }
        return null;
    }

    /// <summary>
    /// A document, from which bookmarks are being filled.
    /// </summary>
    private /*final*/ DocumentBase mDocument;
    /// <summary>
    /// Context of accepting/rejecting revisions that contains node deletion lists.
    /// </summary>
    private /*final*/ RevisionHandlingContext mContext;
    /// <summary>
    /// Filling hash table. Has bookmark start as keys and bookmark end as values.
    /// </summary>
    private Map<Node, Node> mBookmarks;
}

