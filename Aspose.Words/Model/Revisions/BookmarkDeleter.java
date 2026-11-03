// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import java.util.ArrayList;


/// <summary>
/// Allows deleting bookmarks on nodes that are to be deleted. Also moves bookmarks, that should not be deleted, 
/// from deleting nodes. The class is used on accepting and rejecting revisions.
/// </summary>
class BookmarkDeleter
{
    private BookmarkDeleter(DocumentBase doc, RevisionHandlingContext context)
    {
        mDocument = doc;
        mBookmarkCache = new BookmarkCache((Document)doc);
        mContext = context.deepClone();
        completeDeletionLists();
    }

    /// <summary>
    /// Deletes or moves bookmarks if necessary from nodes that are expected to be deleted later.
    /// </summary>
    static void deleteBookmarksFromDeletingNodes(DocumentBase doc, RevisionHandlingContext context) throws Exception
    {
        BookmarkDeleter bmDeleter = new BookmarkDeleter(doc, context);
        bmDeleter.execute();
    }

    /// <summary>
    /// Processes deletion or movement of bookmarks.
    /// </summary>
    private void execute() throws Exception
    {
        deleteBookmarksIfNeed();

        BookmarkMover bmMover = new BookmarkMover(mDocument, mContext);
        bmMover.moveFromDeletingNodes();
    }

    /// <summary>
    /// Deletes bookmarks on text that will be deleted during accepting or rejecting revisions.
    /// </summary>
    private void deleteBookmarksIfNeed() throws Exception
    {
        // MS Word 2013 behavior is the following.
        // Selected-text bookmarks are deleted if the text is deleted.
        // Position bookmarks are deleted:
        // 1. At beginning of a document.
        // 2. If previous and next runs within a paragraph are deleted.
        // 3. If a bookmark is in a table cell and the cell is deleted.

        deleteBookmarksAtBeginOfDoc();
        deleteBookmarksOnDeletingText();
    }

    /// <summary>
    /// Deletes bookmarks till the first node that is not deleted.
    /// </summary>
    private void deleteBookmarksAtBeginOfDoc()
    {
        BookmarksTillPersistNodeFiller bmFiller = new BookmarksTillPersistNodeFiller(mDocument, mContext);
        Map<Node, Node> bmStartToBmEnd = bmFiller.fillBookmarks();

        for (Map.Entry<Node, Node> entry : bmStartToBmEnd.entrySet())
            if (entry.getValue() != null) // is the bookmark end achieved?
            {
                ((BookmarkEnd)entry.getValue()).remove();
                ((BookmarkStart)entry.getKey()).remove();
            }
    }

    /// <summary>
    /// Checks if the node can be bookmark target. 
    /// </summary>
    static boolean isBookmarkTargetNode(Node node)
    {
        // Also the same as in RevisionUtil.HandleNodeRevision.
        return
            node.getNodeType() == NodeType.PARAGRAPH ||
            node instanceof IInline;
    }

    /// <summary>
    /// Deletes bookmarks if need.
    /// </summary>
    private void deleteBookmarksOnDeletingText() throws Exception
    {
        NodeCollection nodes = mDocument.getChildNodes(NodeType.BOOKMARK_START, true);
        ArrayList<Node> bookmarkStarts = nodes.toNodeList();
        for (Node node : bookmarkStarts)
        {
            BookmarkStart bookmarkStart = (BookmarkStart)node;
            if (canDeleteBookmark(bookmarkStart.getName()))
            {
                Bookmark bookmark = mBookmarkCache.get(bookmarkStart.getName());
                if (needDeleteBookmark(bookmark))
                    bookmark.remove();
            }
        }
    }

    /// <summary>
    /// Returns true for bookmarks that can be deleted during accepting or rejecting revisions.
    /// </summary>
    private static boolean canDeleteBookmark(String bookmarkName)
    {
        return !Bookmark.GO_BACK_BOOKMARK_NAME.equals(bookmarkName);
    }

    /// <summary>
    /// Checks if the bookmark should be deleted.
    /// </summary>
    private boolean needDeleteBookmark(Bookmark bookmark) throws Exception
    {
        // Incomplete bookmark encountered. 
        if (bookmark == null)
            return false;

        NodeRange nodeRange = bookmark.getNodeRange();
        boolean coversNodes = false;

        for (Node node : nodeRange)
            if (isBookmarkTargetNode(node))
            {
                if (!isDeletingNode(node))
                    return false;
                coversNodes = true;
            }

        if (!coversNodes)
            return arePrevAndNextNodesDeleted(bookmark) || isInDeletingCell(bookmark.getBookmarkStart());
        else
            return true;
    }

    /// <summary>
    /// Checks if the previous and the next text nodes are in deleted node lists.
    /// </summary>
    private boolean arePrevAndNextNodesDeleted(Bookmark bookmark) throws Exception
    {
        boolean prevDeleted = false;
        Node node = bookmark.getBookmarkStart().getPreviousSibling();
        while (node != null)
        {
            if (isBookmarkTargetNode(node))
            {
                prevDeleted = isDeletingNode(node);
                break;
            }
            node = node.getPreviousSibling();
        }
        if (!prevDeleted)
            return false;

        boolean nextDeleted = false;
        node = bookmark.getBookmarkEnd().getNextSibling();
        while (node != null)
        {
            if (isBookmarkTargetNode(node))
            {
                nextDeleted = isDeletingNode(node);
                break;
            }
            node = node.getNextSibling();
        }

        return nextDeleted;
    }

    /// <summary>
    /// Checks if the node belongs to a deleting table cell.
    /// </summary>
    private boolean isInDeletingCell(Node node)
    {
        CompositeNode parentCell = node.getAncestor(NodeType.CELL);
        return (parentCell != null) && isDeletingNode(parentCell);
    }

    /// <summary>
    /// Checks if the node is in the deleted node lists.
    /// </summary>
    private boolean isDeletingNode(Node node)
    {
        return mContext.isDeletingNode(node);
    }

    /// <summary>
    /// Fills missing cells/rows/tables which contents are to be completely removed but which are not present in
    /// the node deletion lists of <see cref="mContext"/>.
    /// </summary>
    private void completeDeletionLists()
    {
        fillMissingDeletingNodes(mContext.getDelayedParagraphs(), mContext.getDelayedCells(), NodeType.CELL);
        fillMissingDeletingNodes(mContext.getDelayedCells(), mContext.getDelayedRows(), NodeType.ROW);
        fillMissingDeletingNodes(mContext.getDelayedRows(), mContext.getDelayedTables(), NodeType.TABLE);
    }

    /// <summary>
    /// Fills missing parent nodes which children are to be completely removed but which are not present in
    /// the node deletion list.
    /// </summary>
    private static void fillMissingDeletingNodes(ArrayList<Node> deletingNodes, ArrayList<Node> deletingParents, /*NodeType*/int parentType)
    {
        Node previousParent = null;

        for (Node node : deletingNodes)
        {
            CompositeNode parent = node.getFirstNonMarkupParentNode();
            if ((parent != previousParent) && (parent.getNodeType() == parentType) && !deletingParents.contains(parent))
            {
                previousParent = parent;

                // Checks if all node.NodeType children are in the deletingNodes list.
                boolean areAllChildrenDeleting = true;
                for (Node child = parent.getFirstNonMarkupDescendant(); 
                    child != null; 
                    child = child.getNextNonMarkupNodeLimited())
                {
                    if ((child.getNodeType() == node.getNodeType()) && !deletingNodes.contains(child))
                    {
                        areAllChildrenDeleting = false;
                        break;
                    }
                }

                if (areAllChildrenDeleting)
                    deletingParents.add(parent);
            }
        }
    }

    /// <summary>
    /// The document, from which bookmarks are being deleted.
    /// </summary>
    private /*final*/ DocumentBase mDocument;
    /// <summary>
    /// Context of accepting/rejecting revisions that contains node deletion lists.
    /// </summary>
    private /*final*/ RevisionHandlingContext mContext;
    /// <summary>
    /// Bookmarks cache.
    /// </summary>
    private /*final*/ BookmarkCache mBookmarkCache;
}

