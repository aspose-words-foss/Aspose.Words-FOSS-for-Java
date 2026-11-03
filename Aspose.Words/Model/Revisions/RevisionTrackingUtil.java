// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msNumber;
import com.aspose.RandomUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents methods to mark nodes with revisions when document revision tracking
/// (<see cref="Document.StartTrackRevisions(string,System.DateTime)"/>) is ON.
/// </summary>
/*static*/ class RevisionTrackingUtil
{
	/* Simulation of static class by using private constructor */
	private RevisionTrackingUtil()
	{}

    /// <summary>
    /// Process deletion tracking.
    /// </summary>
    /// <returns>Returns false if deletion was NOT tracked so real deletion should be performed.</returns>
    static boolean trackDeletion(Node node)
    {
        // Changes are not tracked in comments.
        if (node.getAncestor(NodeType.COMMENT) != null)
            return false;

        if (node.getNodeType() == NodeType.CELL)
            return false;

        HashSetGeneric<Node> delayedDeletion = new HashSetGeneric<Node>();

        trackDeletionCore(node, delayedDeletion);

        // If start node is in delayed node list we just return false and all subtree will be deleted.
        if (delayedDeletion.contains(node))
            return false;

        // Process delayed deletion.
        // Nodes should be actually deleted so suspend tracking during this operation.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(node.getDocument());
        try /*JAVA: was using*/
        {
            for (Node nodeToDelete : delayedDeletion)
                nodeToDelete.remove();
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        return true;
    }

    /// <summary>
    /// Adds insert revision to given ITrackRevision node.
    /// </summary>
    static void addInsertRevision(ITrackableNode node, EditSession editSession)
    {
        EditRevision editRevision =
            new EditRevision(EditRevisionType.INSERTION, editSession.getAuthor(), editSession.getDateTime());
        node.setInsertRevision(editRevision);
    }

    /// <summary>
    /// Adds delete revision to given ITrackRevision node.
    /// </summary>
    static void addDeleteRevision(ITrackableNode node, EditSession editSession)
    {
        EditRevision editRevision =
            new EditRevision(EditRevisionType.DELETION, editSession.getAuthor(), editSession.getDateTime());
        node.setDeleteRevision(editRevision);
    }

    /// <summary>
    /// Tracks revisions for the specified node, which has been inserted into the document.
    /// </summary>
    /// <remarks>
    /// If the node is moved within the same document, move revisions may be generated.
    /// </remarks>
    static void trackInsertion(Node node, CompositeNode oldParent, Node oldNextSibling)
    {
        if ((oldParent == null) || (oldParent.getDocument() != node.getDocument()))
        {
            // The old position of the node is not in the same document: just mark as inserted.
            markInserted(node);
            return;
        }

        // On moving cells with tracking changes in MS Word, it merges cells with generating deletion/insertion
        // revisions for cell contents.
        // Now we generate move ranges on moving cell nodes. On accepting/rejecting revisions in MS Word, it
        // displays a warning that the table is corrupted (not always reproduced), but everything looks like good.
        // When generating such movement as deletion/insertion revisions, I got that accepting in MS Word works
        // well, but rejecting causes generation of wrong table grid.
        // Thus let's generate move revisions for now. It seems cell movement tracking has no practical profit
        // and this case will not be used often by customers.

        if (node.getDocument().isMoveRevisionsTracked() && trackMoving(node, oldParent, oldNextSibling))
            return;

        // This node insertion cannot be tracked as a move revision, so generate deletion + insertion revisions.

        if ((node instanceof ITrackableNode) || node.isComposite()) // If trackable or may contain trackable children.
        {
            // Create deletion revision node. Any move revision should be kept in the node.
            Node deletionRevisionNode = node.deepClone(true);
            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(node.getDocument());
            try /*JAVA: was using*/
            {
                oldParent.insertBefore(deletionRevisionNode, oldNextSibling);
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

            markDeleted(deletionRevisionNode);
        }

        markInserted(node);
    }

    /// <summary>
    /// Creates move-from and move-to ranges and places them around the specified nodes.
    /// </summary>
    static void createMoveRanges(NodeRange moveToNodeRange, NodeRange moveFromNodeRange,
        String author, DateTime dateTime)
    {
        Document doc = (Document)moveToNodeRange.getDocument();
        // It is needed to generate unique move range name. When generating name included entire GUID number,
        // MS Word causes errors on accepting/rejecting changes. So, let's generate name using 16-digit hex.
        String name = "move" +
                      msNumber.format(RandomUtil.newGuid().hashCode(), "X8") +
                      msNumber.format(RandomUtil.newGuid().hashCode(), "X8");

        int moveFromRangeId = doc.getNextAnnotationId();
        MoveFromRangeStart moveFromRangeStart =
            new MoveFromRangeStart(doc, moveFromRangeId, author, name, dateTime, DisplacedByType.UNSPECIFIED);
        insertMoveRangeNode(moveFromRangeStart, moveFromNodeRange.getStart().getNode(), false);

        MoveFromRangeEnd moveFromRangeEnd = new MoveFromRangeEnd(doc, moveFromRangeId, DisplacedByType.UNSPECIFIED);
        insertMoveRangeNode(moveFromRangeEnd, moveFromNodeRange.getEnd().getNode(), true);

        int moveToRangeId = doc.getNextAnnotationId();
        MoveToRangeStart moveToRangeStart =
            new MoveToRangeStart(doc, moveToRangeId, author, name, dateTime, DisplacedByType.UNSPECIFIED);
        insertMoveRangeNode(moveToRangeStart, moveToNodeRange.getStart().getNode(), false);

        MoveToRangeEnd moveToRangeEnd = new MoveToRangeEnd(doc, moveToRangeId, DisplacedByType.UNSPECIFIED);
        insertMoveRangeNode(moveToRangeEnd, moveToNodeRange.getEnd().getNode(), true);
    }

    /// <summary>
    /// Marks node and all its child nodes as inserted.
    /// </summary>
    private static void markInserted(Node node)
    {
        if (node.isComposite())
        {
            CompositeNode compositeNode = (CompositeNode)node;
            for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
                markInserted(childNode);
        }

        ITrackableNode trackable = ms.as(node, ITrackableNode.class);
        if (trackable == null)
            return;

        // Remove any move revisions if present. The 'node' may be moved to a place outside of a move range.
        trackable.removeMoveRevisions();

        addInsertRevision(trackable, ((Document)node.getDocument()).getEditSession());
    }

    /// <summary>
    /// Marks node and all its child nodes as deleted.
    /// </summary>
    private static void markDeleted(Node node)
    {
        if (node.isComposite())
        {
            CompositeNode compositeNode = (CompositeNode)node;
            for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
                markDeleted(childNode);
        }

        ITrackableNode trackable = ms.as(node, ITrackableNode.class);
        if (trackable != null)
            addDeleteRevision(trackable, ((Document)node.getDocument()).getEditSession());
    }

    /// <summary>
    /// Marks node and all its child nodes as moved.
    /// </summary>
    private static void markMoved(Node node, /*MoveRevisionType*/int revisionType)
    {
        // MS Word does not generate revisions in comments on moving: do the same. At this case a comment is visible
        // as in move-from range as in move-to when opening in MS Word.
        // And MS Word does not generate revisions in contents of text box shapes.
        if (node.isComposite() && !(node instanceof ShapeBase) && (node.getNodeType() != NodeType.COMMENT))
        {
            CompositeNode compositeNode = (CompositeNode)node;

            for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
            {
                if (compositeNode instanceof InlineStory)
                {
                    // This child node is located in another story than the move range: mark with
                    // deletion/insertion instead.
                    if (revisionType == MoveRevisionType.MOVE_TO)
                        markInserted(childNode);
                    else
                        markDeleted(childNode);
                }
                else
                {
                    markMoved(childNode, revisionType);
                }
            }
        }

        ITrackableNode trackable = ms.as(node, ITrackableNode.class);
        if (trackable != null)
            addMoveRevision(trackable, ((Document)node.getDocument()).getEditSession(), revisionType);
    }

    /// <summary>
    /// Checks whether the specified node can be marked as a move revision, and if it can, performs it with generating
    /// the necessary move ranges, and clones the node to represent a move-from revision.
    /// </summary>
    private static boolean trackMoving(Node node, CompositeNode oldParent, Node oldNextSibling)
    {
        // The following cases are checked:
        // 1. Whether the node is inserted before a move-to range or to start of a move-to range, and whether
        // its old position was before a move-from range or at start of a move-from range. If it is so, and
        // the move-to range have the same name as the move-from range, and the range is created at the current
        // revision tracking session, the node is added to the existing move range.
        // (The both cases: when the node is inserted before a move-to range, and when to start of a move-to
        // range, are supported to not restrict a customer in a way how to move multiple nodes.)
        // 2. The same as #1, but at the end of move-to and move-from ranges.
        // 3. If neither #1 nor #2 happens, and as the insertion position as the old position are outside of
        // any move range, a new move range is created.
        // Otherwise, Insert and Delete revisions are generated.

        // Get previous and next nodes (trackable or move range start/end). They are not nodes of the same level but
        // logically next/previous with child traversal at first, i.e. where a paragraph is after all its children.
        // This nodes allow to determine whether the node is located in a move range.
        Node prevNode = getPreviousTrackableOrMoveRange(node, false);
        Node nextNode = getNextTrackableOrMoveRange(node, false);
        Node oldNextSiblingOrParent = (oldNextSibling != null) ? oldNextSibling : oldParent;
        Node oldPrevNode = ((oldNextSibling == null) && (oldParent.getLastChild() != null))
            ? getPreviousTrackableOrMoveRange(oldParent.getLastChild(), true)
            : getPreviousTrackableOrMoveRange(oldNextSiblingOrParent, false);
        Node oldNextNode = getNextTrackableOrMoveRange(oldNextSiblingOrParent, true);

        MoveRangeStart moveToRangeStart = ms.as(prevNode, MoveToRangeStart.class);
        moveToRangeStart = (moveToRangeStart == null) ? ms.as(nextNode, MoveToRangeStart.class) : moveToRangeStart;

        MoveRangeStart moveFromRangeStart = ms.as(oldPrevNode, MoveFromRangeStart.class);
        moveFromRangeStart = (moveFromRangeStart == null) ? ms.as(oldNextNode, MoveFromRangeStart.class) : moveFromRangeStart;

        MoveToRangeEnd moveToRangeEnd = ms.as(prevNode, MoveToRangeEnd.class);
        moveToRangeEnd = (moveToRangeEnd == null) ? ms.as(nextNode, MoveToRangeEnd.class) : moveToRangeEnd;

        MoveFromRangeEnd moveFromRangeEnd = ms.as(oldPrevNode, MoveFromRangeEnd.class);
        moveFromRangeEnd = (moveFromRangeEnd == null) ? ms.as(oldNextNode, MoveFromRangeEnd.class) : moveFromRangeEnd;

        boolean createNewMoveRange = false;
        boolean needMoveMoveToRangeStart = false;
        boolean needMoveMoveToRangeEnd = false;
        boolean needMoveMoveFromRangeStart = false;
        boolean needMoveMoveFromRangeEnd = false;

        // 1. Check whether the node is moved to the start of a move-to range.
        if (areCorrespondingRangesOfCurrentTracking(moveToRangeStart, moveFromRangeStart))
        {
            // If the node is inserted before the move-to range start node, move the range start to include
            // the node.
            needMoveMoveToRangeStart = moveToRangeStart == nextNode;

            // If the node was located before the move-from range start node, move the range start to include
            // the move-from revision node.
            needMoveMoveFromRangeStart = moveFromRangeStart == oldNextNode;
        }
        // 2. Check whether the node is moved to the end of a move-to range.
        else if (areCorrespondingRangesOfCurrentTracking(moveToRangeEnd, moveFromRangeEnd))
        {
            // If the node is inserted after the move-to range end node, move the range end to include
            // the node.
            needMoveMoveToRangeEnd = moveToRangeEnd == prevNode;

            // If the node was located after the move-from range end node, move the range end to include
            // the move-from revision node.
            needMoveMoveFromRangeEnd = moveFromRangeEnd == oldPrevNode;
        }
        // 3. Check whether the node is not inserted into an existing move range.
        else if (!isInMoveRange(prevNode, nextNode) && !isInMoveRange(oldPrevNode, oldNextNode))
        {
            // Let's do not create a move range if the node is not trackable and cannot contain trackable children.
            if (!node.isComposite() && !(node instanceof IMoveTrackableNode))
                return false;

            // If the node is not in a move range, a new move range will be created.
            createNewMoveRange = true;
        }
        else
        {
            return false; // Insertion/Deletion revisions will be generated.
        }

        // Let's skip generating move-from revision node for annotations at least for now. They need regenerating
        // IDs and keeping the same ID for start and end nodes. Also bookmarks need unique name.
        boolean skipMoveFromRevision = (node instanceof INodeWithAnnotationId) || (node instanceof IBookmarkNode);

        Document doc = (Document)node.getDocument();
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(doc);
        try /*JAVA: was using*/
        {
            Node moveFromRevisionNode = null;
            if (!skipMoveFromRevision)
            {
                // Create move-from revision node.
                moveFromRevisionNode = node.deepClone(true);
                oldParent.insertBefore(moveFromRevisionNode, oldNextSibling);
                markMoved(moveFromRevisionNode, MoveRevisionType.MOVE_FROM);
            }

            markMoved(node, MoveRevisionType.MOVE_TO);

            // All movements are performed after cloning the 'node' since a move range node may be needed to place
            // into it. Without this the move range node might be cloned too.
            if (needMoveMoveToRangeStart)
                insertMoveRangeNode(moveToRangeStart, node, false);
            if (needMoveMoveToRangeEnd)
                insertMoveRangeNode(moveToRangeEnd, node, true);
            if (needMoveMoveFromRangeStart)
                insertMoveRangeNode(moveFromRangeStart, moveFromRevisionNode, false);
            if (needMoveMoveFromRangeEnd)
                insertMoveRangeNode(moveFromRangeEnd, moveFromRevisionNode, true);

            if (createNewMoveRange)
            {
                Debug.assertTrue(moveFromRevisionNode != null);
                createMoveRanges(new NodeRange(node, node),
                    new NodeRange(moveFromRevisionNode, moveFromRevisionNode),
                    doc.getEditSession().getAuthor(),
                    doc.getEditSession().getDateTime());
            }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        return true;
    }

    /// <summary>
    /// Inserts the move range node before or after the specified node. If the move range node cannot be inserted at
    /// the level of the node, it is placed to the block level.
    /// </summary>
    private static void insertMoveRangeNode(Node moveRangeNode, Node revisionNode, boolean insertAfter)
    {
        if (revisionNode == null)
            return;

        CompositeNode parent = revisionNode.getParentNode();
        if (!parent.canInsert(moveRangeNode) && revisionNode.isComposite())
        {
            // E.g. for a section, we place move range node before/after the first/last paragraph of the section.
            parent = (CompositeNode)revisionNode;
            while (!parent.canInsert(moveRangeNode) && parent.getFirstChild().isComposite())
            {
                // Position of the move range node is expected before/after revisionNode. It would be incorrect if
                // we go through a trackable node end.
                Debug.assertTrue(!(parent instanceof ITrackableNode));
                parent = (CompositeNode)(insertAfter ? parent.getLastChild() : parent.getFirstChild());
            }

            // Position of the move range node is expected before/after revisionNode. It would be incorrect if
            // we go through a trackable node end.
            Debug.assertTrue(!(parent instanceof ITrackableNode));
            parent.insert(moveRangeNode, null, !insertAfter);
        }
        else
        {
            parent.insert(moveRangeNode, revisionNode, insertAfter);
        }
    }

    /// <summary>
    /// Returns a flag indicating whether a node located between the specified nodes is within a move range.
    /// </summary>
    private static boolean isInMoveRange(Node prevNode, Node nextNode)
    {
        return
            (prevNode instanceof MoveToRangeStart) ||
            (prevNode instanceof MoveFromRangeStart) ||
            (nextNode instanceof MoveToRangeEnd) ||
            (nextNode instanceof MoveFromRangeEnd) ||
            hasAnyMoveRevision(prevNode) ||
            hasAnyMoveRevision(nextNode);
    }

    /// <summary>
    /// Returns a flag indicating whether the specified nodes are start (or end) nodes of the corresponding move
    /// ranges (i.e. ranges of the same movement), and which have been created during the current session of
    /// revision tracking.
    /// </summary>
    private static boolean areCorrespondingRangesOfCurrentTracking(Node moveToRangeBound, Node moveFromRangeBound)
    {
        if ((moveToRangeBound == null) || (moveFromRangeBound == null))
            return false;

        MoveRangeStart moveToRangeStart = (moveToRangeBound.getNodeType() == NodeType.MOVE_TO_RANGE_END)
            ? MoveRangeFinder.findMoveRangeStart(moveToRangeBound, true)
            : (MoveToRangeStart)moveToRangeBound;

        MoveRangeStart moveFromRangeStart = (moveFromRangeBound.getNodeType() == NodeType.MOVE_FROM_RANGE_END)
            ? MoveRangeFinder.findMoveRangeStart(moveFromRangeBound, false)
            : (MoveFromRangeStart)moveFromRangeBound;

        Document doc = (Document)moveToRangeBound.getDocument();

        return
            (msString.equals(moveToRangeStart.getName(), moveFromRangeStart.getName())) &&
            (msString.equals(moveToRangeStart.getAuthor(), doc.getEditSession().getAuthor())) &&
            (DateTime.equals(moveToRangeStart.getDate(), doc.getEditSession().getDateTime()));
    }

    /// <summary>
    /// Finds the nearest trackable node or start/end node of a move range in the backward direction.
    /// </summary>
    private static Node getPreviousTrackableOrMoveRange(Node node, boolean includeNode)
    {
        Node prevNode = includeNode ? node : getPreviousNode(getFirstDescendantOrSelf(node));
        while (prevNode != null)
        {
            if ((prevNode.getNodeType() == NodeType.MOVE_FROM_RANGE_START) ||
                (prevNode.getNodeType() == NodeType.MOVE_FROM_RANGE_END) ||
                (prevNode.getNodeType() == NodeType.MOVE_TO_RANGE_START) ||
                (prevNode.getNodeType() == NodeType.MOVE_TO_RANGE_END) ||
                (prevNode instanceof ITrackableNode))
            {
                return prevNode;
            }

            prevNode = getPreviousNode(prevNode);
        }

        return null;
    }

    /// <summary>
    /// Gets a node before the specified node. It is expected that a parent node is located after all its child nodes.
    /// </summary>
    private static Node getPreviousNode(Node node)
    {
        if (node.isComposite() && ((CompositeNode)node).hasChildNodes())
            return ((CompositeNode)node).getLastChild();

        while ((node.getPreviousSibling() == null) && (node.getParentNode() != null))
            node = node.getParentNode();

        return node.getPreviousSibling();
    }

    /// <summary>
    /// Finds the nearest trackable node or start/end node of a move range in the forward direction.
    /// </summary>
    private static Node getNextTrackableOrMoveRange(Node node, boolean includeNode)
    {
        Node nextNode = includeNode ? getFirstDescendantOrSelf(node) : getNextNode(node);

        while (nextNode != null)
        {
            if ((nextNode.getNodeType() == NodeType.MOVE_FROM_RANGE_START) ||
                (nextNode.getNodeType() == NodeType.MOVE_FROM_RANGE_END) ||
                (nextNode.getNodeType() == NodeType.MOVE_TO_RANGE_START) ||
                (nextNode.getNodeType() == NodeType.MOVE_TO_RANGE_END) ||
                (nextNode instanceof ITrackableNode))
            {
                return nextNode;
            }

            nextNode = getNextNode(nextNode);
        }

        return null;
    }

    /// <summary>
    /// Gets a node after the specified node. It is expected that a parent node is located after all its child nodes.
    /// </summary>
    private static Node getNextNode(Node node)
    {
        Node nextNode = node.getNextSibling();
        return (nextNode != null) ? getFirstDescendantOrSelf(nextNode) : node.getParentNode();
    }

    /// <summary>
    /// Gets the deepest first descendant of the specified node or the node itself, if it has no children.
    /// </summary>
    private static Node getFirstDescendantOrSelf(Node node)
    {
        while (node.isComposite() && ((CompositeNode)node).hasChildNodes())
            node = ((CompositeNode)node).getFirstChild();

        return node;
    }

    /// <summary>
    /// Returns a flag indicating whether the specified node has a move-from or move-to revision.
    /// </summary>
    private static boolean hasAnyMoveRevision(Node node)
    {
        IMoveTrackableNode trackable = ms.as(node, IMoveTrackableNode.class);
        if (trackable == null)
            return false;

        return (trackable.getMoveToRevision() != null) || (trackable.getMoveFromRevision() != null);
    }

    /// <summary>
    /// Adds a move revision to the given ITrackRevision node.
    /// </summary>
    private static void addMoveRevision(IMoveTrackableNode node, EditSession editSession, /*MoveRevisionType*/int revisionType)
    {
        MoveRevision revision = new MoveRevision(revisionType, editSession.getAuthor(), editSession.getDateTime());

        if (revisionType == MoveRevisionType.MOVE_TO)
            node.setMoveToRevision(revision);
        else
            node.setMoveFromRevision(revision);
    }

    private static void trackDeletionCore(Node node, HashSetGeneric<Node> delayedDeletion)
    {
        // Special case. Do not mark nodes in comment.
        if (node.getAncestor(NodeType.COMMENT) != null)
            return;

        // Special case. Do not mark nodes in textbox.
        if (node.getParentNode().getNodeType() == NodeType.SHAPE)
            return;

        ITrackableNode trackableNode = ms.as(node, ITrackableNode.class);
        if (trackableNode != null)
        {
            EditSession editSession = ((Document)node.getDocument()).getEditSession();

            if (isInsertedBySameAuhor(trackableNode, editSession))
            {
                // Nodes were inserted by the same author should be deleted.
                delayedDeletion.add(node);
            }
            else
            {
                // Otherwise mark node as deleted
                addDeleteRevision(trackableNode, editSession);

                // This node is preserved so all its parents should be preserved as well.
                removeAncestorsFromDelayedDeletion(node, delayedDeletion);
            }
        }

        if (node.isComposite())
        {
            CompositeNode compositeNode = (CompositeNode)node;
            for (Node childNode : (Iterable<Node>) compositeNode.getChildNodes(NodeType.ANY, false))
                trackDeletionCore(childNode, delayedDeletion);
        }
    }

    /// <summary>
    /// Removes all ancestors of node from delayed deletion list.
    /// </summary>
    private static void removeAncestorsFromDelayedDeletion(Node node, HashSetGeneric<Node> delayedDeletion)
    {
        while (node.getParentNode() != null)
        {
            node = node.getParentNode();
            delayedDeletion.remove(node);
        }
    }

    /// <summary>
    /// Returns true if given node was inserted by the same author.
    /// </summary>
    private static boolean isInsertedBySameAuhor(ITrackableNode node, EditSession editSession)
    {
        EditRevision editRevision = node.getInsertRevision();

        if (editRevision == null)
            return false;

        // Word just compares author names rather than Rsid as I thought before.
        return (msString.equals(editSession.getAuthor(), editRevision.getAuthor()));
    }
}

