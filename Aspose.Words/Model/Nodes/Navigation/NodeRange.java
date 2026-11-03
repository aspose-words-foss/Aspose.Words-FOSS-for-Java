// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;


/// <summary>
/// Represents a range limited by two positions in the document.
/// </summary>
class NodeRange implements Iterable<Node>
{
    /// <summary>
    /// Ctor. Instantiates a node range with inclusive start and end points.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="end"></param>
    NodeRange(Node start, Node end)
    {
    	this(start, true, end, true);
	
    }

    /// <summary>
    /// Ctor. Instantiates a node range with inclusive start and end points.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="includeStart"></param>
    /// <param name="end"></param>
    /// <param name="includeEnd"></param>
    NodeRange(Node start, boolean includeStart, Node end, boolean includeEnd)
    {
    	this(start, includeStart, end, includeEnd, null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="includeStart"></param>
    /// <param name="end"></param>
    /// <param name="includeEnd"></param>
    /// <param name="bookmarkStart"></param>
    NodeRange(
        Node start,
        boolean includeStart,
        Node end,
        boolean includeEnd,
        BookmarkStart bookmarkStart)
    {
    	this(
            includeStart ? DocumentPosition.createPositionBefore(start) : DocumentPosition.createPositionAfter(start),
            includeEnd ? DocumentPosition.createPositionAfter(end) : DocumentPosition.createPositionBefore(end),
            bookmarkStart);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="end"></param>
    NodeRange(DocumentPosition start, DocumentPosition end)
    {
    	this(start, end, null);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="start"></param>
    /// <param name="end"></param>
    /// <param name="bookmarkStart"></param>
    NodeRange(
        DocumentPosition start,
        DocumentPosition end,
        BookmarkStart bookmarkStart)
    {
        mStart = start;
        mEnd = end;

        // Satisfy Java final.
        mBookmarkTable = null;
        mFirstTableColumnIndex = -1;
        mLastTableColumnIndex = -1;

        if ((bookmarkStart != null) && bookmarkStart.isColumn())
        {
            mBookmarkTable = bookmarkStart.getAncestor(NodeType.TABLE);
            if (getBookmarkTable() != null)
            {
                mFirstTableColumnIndex = bookmarkStart.getFirstColumn();
                mLastTableColumnIndex = bookmarkStart.getLastColumn();
            }
        }
    }

    /// <summary>
    /// If the start or end points of the range refer to runs and the position is non-negative,
    /// splits the runs so that any of the points would refer to a complete run.
    /// </summary>
    /// <returns>
    /// The newly inserted run before the <see cref="Start"/> or null if the <see cref="Start"/> was not actually split.
    /// </returns>
    Run isolate()
    {
        final int INVALID_POSITION_OFFSET = -1;

        Run result = null;

        // Split the starting run.
        boolean isStartRun = (getStart().getNode().getNodeType() == NodeType.RUN);
        // No need to split the run if the offset points to the end. Nothing is taken from it.
        if (isStartRun && !getStart().isEnd())
        {
            Run run = (Run)getStart().getNode();

            int startOffset = getStart().getOffset();
            result = run.splitBefore(startOffset);
            // WORDSNET-8573 Reset the length cached in the DocumentPosition instance to prevent NodeRangePosition.IsEof failure.
            getStart().resetLength();
            getStart().moveNodeStart();

            // Update the end position if it is in the same node.
            if (isSameNode() && (getEnd().getOffset() != INVALID_POSITION_OFFSET))
            {
                getEnd().setOffset(getEnd().getOffset() - startOffset);
            }
        }

        // Split the ending run.
        boolean isEndRun = (getEnd().getNode().getNodeType() == NodeType.RUN);
        if (isEndRun && (getEnd().getOffset() > 0))
        {
            Run run = (Run)getEnd().getNode();
            run.splitAfter(getEnd().getOffset());
            // WORDSNET-8573 Reset the length cached in the DocumentPosition instance to prevent NodeRangePosition.IsEof failure.
            getEnd().resetLength();
            getEnd().moveNodeEnd();
        }

        return result;
    }

    /// <summary>
    /// Accepts a <see cref="DocumentVisitor"/> object and calls its methods appropriately.
    /// At the moment only calls VisitXXXStart methods for composite nodes.
    /// </summary>
    void accept(DocumentVisitor visitor, INodeModifier modifier, /*NodeExtractBehavior*/int extractBehavior) throws Exception
    {
        NodeRangeVisitingHelper helper = new NodeRangeVisitingHelper(this, visitor, modifier, extractBehavior);
        try /*JAVA: was using*/
    	{
            helper.sendVisitorToNodeRange();
    	}
        finally { if (helper != null) helper.dispose(); }
    }

    /// <summary>
    /// Removes nodes in the range. Joins parent nodes if needed.
    /// </summary>
    /// <returns>A node that "follows" the range to mark the place where the range used to be.</returns>
    Node remove() throws Exception
    {
        isolate();

        Node referenceNode = getReferenceNode();
        NodeRemover.remove(this, NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING, false);

        return referenceNode;
    }

    private Node getReferenceNode()
    {
        Node endNode = getEnd().getNode();

        if (getEnd().isStart())
            return endNode;

        return endNode.getNextOrParent();
    }

    /// <summary>
    /// Returns true if both start and end nodes of the range belong to the same ancestor.
    /// </summary>
    boolean isInSameAncestor(/*NodeType*/int ancestorType)
    {
        return getSameAncestor(ancestorType) != null;
    }

    CompositeNode getSameAncestor(/*NodeType*/int ancestorType)
    {
        CompositeNode startAncestor = getStart().getNode().getAncestor(ancestorType);
        if (startAncestor == null)
            return null;

        CompositeNode endAncestor = getEnd().getNode().getAncestor(ancestorType);
        if (endAncestor == null)
            return null;

        return startAncestor == endAncestor
            ? startAncestor
            : null;
    }

    /// <summary>
    /// Returns true if both start and end nodes of the range belong to the same ancestor or are the same node.
    /// </summary>
    boolean isInSameAncestorOrSelf(/*NodeType*/int ancestorType)
    {
        return
            (NodeUtil.getAncestorOrSelf(getStart().getNode(), ancestorType) ==
            NodeUtil.getAncestorOrSelf(getEnd().getNode(), ancestorType));
    }

    public Iterator<Node> iterator()
    {
        return new NodeEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets whether the start or end point does not reference a node.
    /// </summary>
    boolean isVoid() { return (getStart().isVoid() || getEnd().isVoid()); }

    /// <summary>
    /// Gets whether the start and end points of the range reference the same node.
    /// </summary>
    boolean isSameNode() { return (getStart().isSameNode(getEnd())); }

    /// <summary>
    /// Gets whether the start and end points of the range have the same parent.
    /// </summary>
    boolean isSameParent() { return (getStart().getNode().getParentNode() == getEnd().getNode().getParentNode()); }

    boolean isStartIncluded() { return !getStart().isEnd(); }

    boolean isEndIncluded() { return !getEnd().isStart(); }

    /// <summary>
    /// Gets the first point of the range.
    /// </summary>
    DocumentPosition getStart() { return mStart; };

    private DocumentPosition mStart;

    /// <summary>
    /// Gets the last point of the range.
    /// </summary>
    DocumentPosition getEnd() { return mEnd; };

    private DocumentPosition mEnd;

    /// <summary>
    /// Gets the <see cref="Document"/> the range belongs to.
    /// </summary>
    DocumentBase getDocument()
    {
        if (isVoid())
            return null;

        return getStart().getNode().getDocument();
    }

    /// <summary>
    /// Gets a value indicating whether the range is a table column bookmark range
    /// i.e. it references columns in a table.
    /// </summary>
    boolean isTableColumnBookmarkRange() { return (getBookmarkTable() != null); }

    /// <summary>
    /// Returns the bookmarked table if the range is a table column bookmark range
    /// or <c>null</c> otherwise.
    /// </summary>
    Node getBookmarkTable() { return mBookmarkTable; };

    private Node mBookmarkTable;

    /// <summary>
    /// Returns the index of the first valid table column if the range is a table
    /// column bookmark range or <b>-1</b> otherwise.
    /// </summary>
    int getFirstTableColumnIndex() { return mFirstTableColumnIndex; };

    private int mFirstTableColumnIndex;

    /// <summary>
    /// Returns the index of the last valid table column if the range is a table
    /// column bookmark range or <b>-1</b> otherwise.
    /// </summary>
    int getLastTableColumnIndex() { return mLastTableColumnIndex; };

    private int mLastTableColumnIndex;

    /// <summary>
    /// Returns the first effective node taking into account inclusion.
    /// </summary>
    Node getFirstRangeNode()
    {
        DocumentPosition p = getStart().deepClone();
        if (!isStartIncluded())
            p.moveNext(true);

        return p.getNode();
    }

    /// <summary>
    /// Gets indication of whether range has nothing inside.
    /// </summary>
    boolean isEmpty()
    {
        // For most nodes, starting and ending on the same position means emptiness.
        return (getEnd().getNode().getNodeType() != NodeType.RUN)
            ? getStart().isEqual(getEnd())
            // Run is an exception:
            : getStart().isSameNode(getEnd()) &&
               (getStart().getOffset() >= getEnd().getOffset() || getStart().isEnd());
    }

    /// <summary>
    /// A node range consisting of null node points.
    /// </summary>
    static /*final*/ NodeRange VOID = new NodeRange(DocumentPosition.VOID, DocumentPosition.VOID);

    /// <summary>
    /// Returns node which is ancestor for all the nodes in the range
    /// </summary>
    Node getCommonAncestor()
    {
        DocumentPosition p = getStart().deepClone();

        Node ancestor = p.getNode();
        if (!isStartIncluded())
        {
            if (!p.moveNext(true))
                return null;

            if (p.getNode() == getEnd().getNode() && !isEndIncluded())
                return null;

            ancestor = p.getNode();
        }

        while (p.moveNext(true))
        {
            boolean isEndNode = (p.getNode() == getEnd().getNode());
            if (isEndNode && !isEndIncluded())
                break;

            // If we moved to parent, its our new ancestor
            if (ancestor.getParentNode() == p.getNode())
                ancestor = p.getNode();

            if (isEndNode)
                break;
        }

        return ancestor;
    }

}

