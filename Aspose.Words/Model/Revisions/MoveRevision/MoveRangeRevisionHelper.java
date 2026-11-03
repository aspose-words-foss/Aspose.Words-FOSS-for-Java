// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;


class MoveRangeRevisionHelper
{
    MoveRangeRevisionHelper(MoveRange range)
    {
        mRange = range;
        mStartBound = (AnnotationUtil.getAnnotationRangeBound(range.getStart(), true) != null ? AnnotationUtil.getAnnotationRangeBound(range.getStart(), true) : new RangeBound(range.getStart(), true));
        mEndBound = (AnnotationUtil.getAnnotationRangeBound(range.getEnd(), false) != null ? AnnotationUtil.getAnnotationRangeBound(range.getEnd(), false) : new RangeBound(range.getEnd(), true));
        calcBoundNodes();
    }

    /// <summary>
    /// Returns <c>true</c> if the specified structured document tag is in a continuous chain of nested
    /// structured document tags started at the top node of begin of the move range.
    /// </summary>
    boolean isFirstSdt(StructuredDocumentTag sdt)
    {
        if (getTopStartNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        while ((sdt != null) &&
            (sdt != getTopStartNode()) &&
            ((sdt.getPreviousNonAnnotationSibling() == null) || (getRange().getStart().getNextNonAnnotationSibling() == sdt) ||
                getRange().getStart().isAncestorNode(sdt)) &&
            (sdt.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) &&
            (!sdt.isAbove(getRange().getStart()) || getRange().getStart().isAncestorNode(sdt)))
            sdt = (StructuredDocumentTag)sdt.getParentNode();

        return sdt == getTopStartNode();
    }

    /// <summary>
    /// Returns <c>true</c> if the specified structured document tag is in a continuous chain of nested
    /// structured document tags at the end of the move range.
    /// </summary>
    boolean isLastSdt(StructuredDocumentTag sdt)
    {
        if (getTopEndNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        while ((sdt != null) &&
            (sdt != getTopEndNode()) &&
            (sdt.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) &&
            ((sdt.getNextNonAnnotationSibling() == null) || (getRange().getEnd().getPreviousNonAnnotationSibling() == sdt) ||
                getRange().getEnd().isAncestorNode(sdt)) &&
            sdt.isAbove(getRange().getEnd()))
            sdt = (StructuredDocumentTag)sdt.getParentNode();

        return sdt == getTopEndNode();
    }

    /// <summary>
    /// Returns <c>true</c> if the specified paragraph is the last paragraph in the move range, and top end node of
    /// the move range is this paragraph or its ancestor.
    /// </summary>
    boolean isLastParagraph(Paragraph para)
    {
        if (getTopEndNode() == para)
            return true;
        if (!getTopEndNode().isComposite())
            return false;

        ArrayList<Node> paragraphs = ((CompositeNode)getTopEndNode()).getChildNodes(NodeType.PARAGRAPH, true).toNodeList();
        for (int i = paragraphs.size() - 1; i >= 0; i--)
        {
            Node node = paragraphs.get(i);
            if (node.isAbove(getRange().getEnd()))
                return node == para;
        }

        return false;
    }

    /// <summary>
    /// Calculates real bound nodes of the range.
    /// </summary>
    private void calcBoundNodes()
    {
        setStartNode(getStartNodeInternal());
        setEndNode(getEndNodeInternal());
        Node start = getStartNode();
        Node end = getEndNode();

        // Move start/end up to be in same node level.
        if (start != end)
        {
            Node ancestor = Node.getCommonAncestor(start, end);
            if (ancestor == null)
                throw new IllegalStateException("Wrong move range.");

            if (start != ancestor)
            {
                while (start.getParentNode() != ancestor)
                    start = start.getParentNode();
            }

            if (end != ancestor)
            {
                while (end.getParentNode() != ancestor)
                    end = end.getParentNode();
            }
        }

        Node node;
        // If range start is last child of 'start', move 'start' to the next node.
        if (getStartNode().isAncestorNode(start) && (end != start) && !containsMoveRevision(start))
        {
            node = getStartNode();
            while ((node != start) &&
                   (node.getNextNonAnnotationSibling() == null) &&
                   // Do not exclude a paragraph from the range if it has move revision of the same type.
                   ((node.getParentNode().getNodeType() != NodeType.PARAGRAPH) || !hasMoveRevision(node.getParentNode())))
                node = node.getParentNode();

            if (node == start)
                start = start.getNextSibling();
        }

        // If range end is first child of 'end', move 'end' to the previous node.
        if (getEndNode().isAncestorNode(end) && (end != start) && !containsMoveRevision(end))
        {
            node = getEndNode();
            while ((node != end) && (node.getPreviousNonAnnotationSibling() == null))
                node = node.getParentNode();

            if (node == end)
                end = end.getPreviousSibling();
        }

        // Skip annotations.
        node = start;
        while (NodeUtil.isCrossStructureAnnotation(node) && (node != end))
            node = node.getNextSibling();

        if (node != end)
            start = node;

        // Skip annotations.
        node = end;
        while (NodeUtil.isCrossStructureAnnotation(node) && (node != start))
            node = node.getPreviousSibling();

        if (node != start)
            end = node;

        setTopStartNode(start);
        setTopEndNode(end);
        setRangeParent(start.getParentNode());

        // Fix start/end nodes if outside of top nodes.
        if (getStartNode().isAbove(getTopStartNode()))
        {
            setStartNode(getTopStartNode());
            // Move start to deepest child since start/end nodes are in order from deepest child to parents.
            while (getStartNode().isComposite() && ((CompositeNode)getStartNode()).hasChildNodes())
                setStartNode(((CompositeNode)getStartNode()).getFirstChild());
        }

        if (!NodeUtil.isAncestorOrSelf(getEndNode(), getTopEndNode()) && getTopEndNode().isAbove(getEndNode()))
            setEndNode(getTopEndNode());
    }

    private boolean containsMoveRevision(Node node)
    {
        if (hasMoveRevision(node))
            return true;

        if (!node.isComposite())
            return false;

        for (Node child : (Iterable<Node>) (CompositeNode)node)
        {
            if (containsMoveRevision(child))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node has move revision of the same type as this move range.
    /// </summary>
    private boolean hasMoveRevision(Node node)
    {
        IMoveTrackableNode trackable = ms.as(node, IMoveTrackableNode.class);
        if (trackable == null)
            return false;

        MoveRevision revision = isMoveTo() ? trackable.getMoveToRevision() : trackable.getMoveFromRevision();

        return revision != null;
    }

    /// <summary>
    /// Gets start node calculated for range bound.
    /// </summary>
    private Node getStartNodeInternal()
    {
        if (mStartBound.getDisplacingNode() == null)
        {
            Node node = mStartBound.getNode();
            if (mStartBound.isNodeIncluded() || NodeUtil.isCrossStructureAnnotation(node))
                return node;

            while ((node != null) && (node.getNextSibling() == null))
                node = node.getParentNode();

            return node != null ? node.getNextSibling() : mStartBound.getNode();
        }

        // Do not include displacing SDT, if range start bound is end of the SDT.
        boolean isSdtIncluded = mStartBound.isAnnotationAChildOfSdt()
            ? mStartBound.getDisplacedByType() == DisplacedByType.PREV
            : mStartBound.getDisplacedByType() == DisplacedByType.NEXT;
        return isSdtIncluded ? mStartBound.getDisplacingNode() : mStartBound.getDisplacedAnnotation();
    }

    /// <summary>
    /// Gets end node calculated for range bound.
    /// </summary>
    private Node getEndNodeInternal()
    {
        if (mEndBound.getDisplacingNode() == null)
        {
            Node node = mEndBound.getNode();
            if (mEndBound.isNodeIncluded() || NodeUtil.isCrossStructureAnnotation(node))
                return node;

            while ((node != null) && (node.getPreviousSibling() == null))
                node = node.getParentNode();

            return node != null ? node.getPreviousSibling() : mEndBound.getNode();
        }

        // Do not include displacing SDT, if range end bound is begin of the SDT.
        boolean isSdtIncluded = mEndBound.isAnnotationAChildOfSdt()
            ? mEndBound.getDisplacedByType() == DisplacedByType.NEXT
            : mEndBound.getDisplacedByType() == DisplacedByType.PREV;
        return isSdtIncluded ? mEndBound.getDisplacingNode() : mEndBound.getDisplacedAnnotation();
    }

    /// <summary>
    /// Indicates whether any unrevised node is preserved during accepting/rejecting revisions.
    /// </summary>
    boolean isNonRevisedNodePreserved()
    {
        return isMiddleParagraphPreserved() && isMiddleSdtPreserved();
    }

    /// <summary>
    /// Indicates whether the first SDT if it has no move revision is preserved during accepting/rejecting revisions.
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </summary>
    boolean isFirstSdtPreserved()
    {
        if (isMiddleSdtPreserved())
            return true;
        if (!isRangeStartInFirstSdt())
            return false;

        return !isRangeEndDisplaced() || isRangeEndInLastSdt() || !isInsideSdt();
    }

    /// <summary>
    /// Indicates whether a middle SDT that has no move revision is preserved during accepting/rejecting revisions.
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </summary>
    boolean isMiddleSdtPreserved()
    {
        return isInsideSdt() && isRangeEndDisplaced() && !isRangeEndInLastSdt();
    }

    /// <summary>
    /// Indicates whether the last SDT if it has no move revision is preserved during accepting/rejecting revisions.
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </summary>
    boolean isLastSdtPreserved()
    {
        if (isMiddleSdtPreserved())
            return true;

        if (isRangeEndDisplaced() && isRangeEndInLastSdt())
            return true;

        if (!isRangeEndInLastSdt() && getRange().getEnd().isAncestorNode(getTopEndNode()))
            return true;

        return false;
    }

    /// <summary>
    /// Indicates whether the last SDT when has no children is nested in the first SDT of the move range after
    /// accepting/rejecting revisions.
    /// </summary>
    /// <remarks>
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </remarks>
    boolean isLastSdtNestedInFirst()
    {
        return isRangeStartInFirstSdt() && isRangeEndDisplaced() && isRangeEndInLastSdt();
    }

    /// <summary>
    /// Indicates whether a non-last paragraph that has no move revision is preserved during accepting/rejecting
    /// revisions.
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </summary>
    boolean isMiddleParagraphPreserved()
    {
        return isInsideSdt() && isRangeEndDisplaced() && !isRangeEndInLastSdt();
    }

    /// <summary>
    /// Indicates whether the last paragraph that has no move revision is preserved during accepting/rejecting
    /// revisions.
    /// See WORDSNET-15250 for info of MS Word behaviour.
    /// </summary>
    boolean isLastParagraphPreserved()
    {
        if (isMiddleParagraphPreserved())
            return true;

        // May be preserved only if it is a child of the last SDT.
        return (getTopEndNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) && isRangeEndDisplaced();
    }

    /// <summary>
    /// Returns the processing move range.
    /// </summary>
    MoveRange getRange() { return mRange; };

    private MoveRange mRange;

    /// <summary>
    /// Indicates whether this is a move-to or move-from range.
    /// </summary>
    boolean isMoveTo() { return getRange().isMoveTo(); }

    /// <summary>
    /// Gets topmost-level non-annotation node that is included at least partially into the move range at start.
    /// </summary>
    Node getTopStartNode() { return mTopStartNode; }; private void setTopStartNode(Node value) { mTopStartNode = value; };

    private Node mTopStartNode;

    /// <summary>
    /// Gets topmost-level non-annotation node that is included at least partially into the move range at end.
    /// </summary>
    Node getTopEndNode() { return mTopEndNode; }; private void setTopEndNode(Node value) { mTopEndNode = value; };

    private Node mTopEndNode;

    /// <summary>
    /// Returns node that completely contains the move range.
    /// </summary>
    CompositeNode getRangeParent() { return mRangeParent; }; private void setRangeParent(CompositeNode value) { mRangeParent = value; };

    private CompositeNode mRangeParent;

    /// <summary>
    /// Returns real start node of the range in order from deepest child to parent.
    /// </summary>
    Node getStartNode() { return mStartNode; }; private void setStartNode(Node value) { mStartNode = value; };

    private Node mStartNode;

    /// <summary>
    /// Returns real end node of the range in order from deepest child to parent.
    /// </summary>
    Node getEndNode() { return mEndNode; }; private void setEndNode(Node value) { mEndNode = value; };

    private Node mEndNode;

    /// <summary>
    /// MS Word may nest the last SDT of a move range into the first SDT on accepting revisions. This is
    /// an additional flag for <see cref="IsLastSdtNestedInFirst"/> for a case when the first and the last
    /// SDTs become inline level on accepting revisions.
    /// See the cases 2 (not nested) and 8 (nested) of WORDSNET-15250
    /// </summary>
    boolean canNestInlineSdts() { return isInsideSdt(); }

    /// <summary>
    /// Returns <c>true</c> if parent of the range is an SDT.
    /// </summary>
    private boolean isInsideSdt() { return getRangeParent().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG; }

    /// <summary>
    /// Returns <c>true</c> if top start node of the range is an SDT and range start node is a descendant of it.
    /// </summary>
    private boolean isRangeStartInFirstSdt()
    {
        if (getTopStartNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        if (!getRange().getStart().isAncestorNode(getTopStartNode()))
            return false;

        if (getRange().getStart().getParentNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if the range end node is displaced by a custom XML (SDT) node.
    /// </summary>
    private boolean isRangeEndDisplaced() { return mEndBound.getDisplacingNode() == getTopEndNode(); }

    /// <summary>
    /// Returns <c>true</c> if top end node of the range is an SDT and range end node is a descendant of it.
    /// </summary>
    private boolean isRangeEndInLastSdt()
    {
        if (getTopEndNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        if (!getRange().getEnd().isAncestorNode(getTopEndNode()))
            return false;

        if (getRange().getEnd().getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            return true;

        if (getRange().getEnd().getDisplacedBy() == DisplacedByType.NEXT)
            return true;

        return false;
    }

    private /*final*/ RangeBound mStartBound;
    private /*final*/ RangeBound mEndBound;
}

