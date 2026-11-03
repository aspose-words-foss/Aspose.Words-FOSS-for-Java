// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import java.util.Iterator;


/// <summary>
/// Enumerates a range of nodes.
/// </summary>
class NodeEnumerator implements Iterator<Node>, IDocumentPositionListener
{
    /// <summary>
    /// Ctor.
    /// </summary>
    NodeEnumerator(NodeRange range)
    {
    	this(range, false);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    protected NodeEnumerator(NodeRange range, boolean stopAtInvalidRangeNodes)
    {
        mRange = range;
        mStopAtInvalidRangeNodes = stopAtInvalidRangeNodes;
    }

    public void dispose()
    {
        // Nothing to dispose.
    }

    boolean moveToNextNode()
    {
        return moveToNextNode(true);
    }

    boolean moveToNextNode(boolean isDeep)
    {
        return moveToNextNode(isDeep, true);
    }

    boolean moveToNextNode(boolean isDeep, boolean isByNode)
    {
        if (getRange().isVoid() || getRange().isEmpty())
            return false;

        // First move?
        if (getCurrentPosition() == null)
        {
            setCurrentPosition(getRange().getStart().deepClone());
            if (getRange().isStartIncluded() && canStopAtCurrentNode())
                return true;
        }

        if (isEof(true))
            return false;

        do
        {
            Node oldNode = getCurrentNode();
            do
            {
                if (!getCurrentPosition().move(null, true, isDeep, true, !isByNode, true, this))
                    return false;

                if (isEof(isByNode))
                    return false;
            }
            while (isByNode && ((getCurrentPosition().isEnd()) || (getCurrentNode() == oldNode)));
        }
        while (!canStopAtCurrentNode());

        return true;
    }

    private boolean isEof(boolean isByNode)
    {
        // If this is not the last node, it is certainly not the end.
        if (!isEndNode())
            return false;

        // If this is the last node and it is not the same as the first node and the end is included, it's EOF.
        if (!isStartNode() && !getRange().isEndIncluded())
            return true;

        // If this is the last node and we move by node, exit.
        if (getCurrentPosition().isEnd() && isByNode)
            return true;

        // We may need to stop inside a run.
        if (!isByNode && getCurrentPosition().isEqual(getRange().getEnd()))
            return true;

        return false;
    }

    /// <summary>
    /// Returns a value indicating whether the enumeration can stop at the current node i.e. it is valid or
    /// stops at invalid nodes are requested.
    /// </summary>
    private boolean canStopAtCurrentNode()
    {
        return (mStopAtInvalidRangeNodes || isCurrentNodeValid());
    }

    /// <summary>
    /// Returns a value indicating whether the current node and all of its descendants are valid for enumeration
    /// i.e. belong to a valid column in case of a table column bookmark range being enumerated.
    /// </summary>
    protected boolean isCurrentNodeValid()
    {
        return isRangeNodeValid(getCurrentNode());
    }

    /// <summary>
    /// Returns a value indicating whether the specified node within the enumerated range and all of its descendants
    /// are valid for enumeration i.e. belong to a valid column in case of a table column bookmark range being enumerated.
    /// </summary>
    /// <remarks>
    /// Always returns <c>true</c> in case of a non-table-column-bookmark range being enumerated even if the node
    /// does not fall within the range.
    /// </remarks>
    protected boolean isRangeNodeValid(Node rangeNode)
    {
        // If a non-table-column-bookmark range is enumerated then the node is always valid.
        // If the node is the table of a table column bookmark range being enumerated then it is valid.
        if (!getRange().isTableColumnBookmarkRange() || (rangeNode == getRange().getBookmarkTable()))
            return true;

        // We may deal with nested tables. Let's consider the top nested table in this case as
        // all of its descendants are valid or invalid together with it.
        Node node = rangeNode;
        while (true) // break inside.
        {
            Node parentTable = node.getAncestor(NodeType.TABLE);
            if (parentTable == null)
                return false;

            if (parentTable == getRange().getBookmarkTable())
                break;

            node = parentTable;
        }

        // If the node is a row of the table of a table column bookmark range being enumerated then it is valid.
        if (node.getNodeLevel() == NodeLevel.ROW)
            return !NodeUtil.isCrossStructureAnnotation(node);

        // Get a cell of the table of a table column bookmark range being enumerated which is an ancestor of the node and
        // then determine whether it is valid or invalid together with all of its descendants depending on a column index.
        Node cellLevelNode = (node.getNodeLevel() == NodeLevel.CELL) ? node : node.getAncestor(NodeType.CELL);
        if (cellLevelNode == null)
            return false;
        // Use parent SDT if the cell inside it.
        while (cellLevelNode.getParentNode().getNodeLevel() == NodeLevel.CELL)
            cellLevelNode = cellLevelNode.getParentNode();

        // Get nearest left cell if node is cross structure annotation.
        boolean isAnnotation = NodeUtil.isCrossStructureAnnotation(cellLevelNode);
        if (isAnnotation)
        {
            cellLevelNode = cellLevelNode.getPreviousNonAnnotationSibling();
            if (cellLevelNode == null) // the node is before first column
                return false;
        }
        // If the node is annotation, we have moved to cell at left of it. So, end bound column should not be
        // included in check at this case: correct end bound index for it.
        int lastValidColumnIndex = getRange().getLastTableColumnIndex() - (isAnnotation ? 1 : 0);

        int columnIndex = cellLevelNode.getParentNode().indexOfChildByDescendant(cellLevelNode, true);
        return ((columnIndex >= getRange().getFirstTableColumnIndex()) && (columnIndex <= lastValidColumnIndex));
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is partially valid i.e. some of its descendants are valid
    /// but some of them are not.
    /// </summary>
    /// <remarks>
    /// Always returns <c>false</c> in case of a non-table-column-bookmark range being enumerated.
    /// </remarks>
    private boolean isRangeNodePartiallyValid(Node rangeNode)
    {
        // If a non-table-column-bookmark range is enumerated then the node is always fully valid.
        if (!getRange().isTableColumnBookmarkRange())
            return false;

        // Only the table of a table column bookmark range being enumerated and its rows are partially valid
        // other nodes are fully valid or fully invalid.
        Node bookmarkTable = getRange().getBookmarkTable();
        return ((rangeNode == bookmarkTable) ||
                (NodeUtil.isRowLevelNode(rangeNode) && !NodeUtil.isCrossStructureAnnotation(rangeNode) &&
                    (rangeNode.getFirstNonMarkupParentNode() == bookmarkTable)));
    }

    /// <summary>
    /// Extracts the current node. Correctly cuts the node text accordingly to the source range if needed.
    /// </summary>
    Node extractCurrentNode(INodeModifier modifier, /*NodeExtractBehavior*/int behavior) throws Exception
    {
        boolean cloneNode = needCloneNodeOnExtract(behavior);
        boolean modifyChildren = needModifyChildrenOnExtract(behavior);
        return extractRangeNode(getCurrentNode(), modifier, null, cloneNode, modifyChildren);
    }

    /// <summary>
    /// Extracts the specified node within the enumerated range. Correctly cuts the node text accordingly to
    /// the source range if needed.
    /// </summary>
    /// <remarks>
    /// Does not check whether the node falls within the range being enumerated.
    /// </remarks>
    protected Node extractRangeNode(
        Node rangeNode,
        INodeModifier modifier,
        INodeCloningListener cloningListener,
        boolean cloneNode,
        boolean modifyChildren) throws Exception
    {
        Node node = extractRangeNode(rangeNode, cloningListener, cloneNode, modifyChildren, true);
        if (node == null)
            return null;

        if (rangeNode.getNodeType() == NodeType.RUN)
        {
            DocumentPosition startPosition = getRange().getStart();
            DocumentPosition endPosition = getRange().getEnd();
            Run run = (Run)node;

            int startOffset = isStartNode() ? startPosition.getOffset() : 0;
            int endOffset = isEndNode()
                // WORDSNET-26019 ArgumentOutOfRangeException when updating TOC
                ? endPosition.isEnd() ? run.getText().length() : endPosition.getOffset()
                : run.getText().length();
            int cutLength = endOffset - startOffset;

            // Cut if needed only.
            if (cutLength < run.getText().length())
                node = run.cutText(startOffset, cutLength, !cloneNode);
        }

        if (modifier != null)
            node = modifier.modify(rangeNode, node, modifyChildren, cloningListener);

        return node;
    }

    /// <summary>
    /// Extracts the specified node within the enumerated range.
    /// </summary>
    /// <remarks>
    /// Does not check whether the node falls within the range being enumerated.
    /// </remarks>
    private Node extractRangeNode(
        Node rangeNode,
        INodeCloningListener cloningListener,
        boolean cloneNode,
        boolean cloneChildren,
        boolean checkValidity)
    {
        if (checkValidity && !isRangeNodeValid(rangeNode))
            return null;

        if (needSkipRangeNodeExtract(rangeNode))
            return null;

        // If the node should not be cloned, simply return it. Its descendants are not filtered in this case.
        if (!cloneNode)
            return rangeNode;

        // Otherwise, clone the node with its descendants if needed, performing all of the required checks.
        Node clone = rangeNode.deepClone(false, cloningListener);

        if (cloneChildren && rangeNode.isComposite())
        {
            CompositeNode parent = (CompositeNode)rangeNode;
            CompositeNode parentClone = (CompositeNode)clone;

            // Do not check validity for descendants, if the parent node is fully valid.
            checkValidity = isRangeNodePartiallyValid(rangeNode);

            for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling())
            {
                Node childClone = extractRangeNode(child, cloningListener, true, true, checkValidity);
                if (childClone != null)
                    parentClone.appendChildForLoad(childClone);
            }
        }

        return clone;
    }

    /// <summary>
    /// Returns a value indicating whether the specified range node should be skipped on extract.
    /// </summary>
    protected /*virtual*/ boolean needSkipRangeNodeExtract(Node rangeNode)
    {
        return false;
    }

    /// <summary>
    /// Returns a value indicating if the current node should be cloned on extract
    /// due to the specified extracting behavior.
    /// </summary>
    private boolean needCloneNodeOnExtract(/*NodeExtractBehavior*/int behavior)
    {
        switch (behavior)
        {
            case NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL:
                return true;
            case NodeExtractBehavior.DONT_MODIFY_CHILDREN_CLONE_RUNS:
                return (getCurrentNode().getNodeType() == NodeType.RUN);
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "behavior");
        }
    }

    /// <summary>
    /// Returns a value indicating if child nodes of the current node should be modified on extract
    /// due to the specified extracting behavior.
    /// </summary>
    private static boolean needModifyChildrenOnExtract(/*NodeExtractBehavior*/int behavior)
    {
        switch (behavior)
        {
            case NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL:
                return true;
            case NodeExtractBehavior.DONT_MODIFY_CHILDREN_CLONE_RUNS:
                return false;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "behavior");
        }
    }

    protected /*virtual*/ void onMoved(/*DocumentPositionMovement*/int movement)
    {
        //  Implementation is not required.
    }

    protected static Node getValidParent(Node node)
    {
        if (node == null)
            return null;

        return (node.getNodeType() != NodeType.BODY ? node : node.getParentNode());
    }

    protected static Node getValidChild(Node node)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (node == null)
            	return null;

        	// If we move down from the section level, we should enter body because earlier we have exited body, too.
        	// Section breaks are only allowed in a body.
        	switch (node.getNodeType())
        	{
            	case NodeType.HEADER_FOOTER:
            	{
                	Body body = ((Section)node.getParentNode()).getBody();
                	return (body != null)
                    	? body.getFirstChild()
                    	: null;
            	}
            	case NodeType.BODY:
            	{
                	Body body = (Body)node;
                	// WORDSNET-27816 if Body is empty, we go to the next node in the hierarchy.
                	return body.hasChildNodes()
                    	? body.getFirstChild()
                    	: body.nextPreOrder(body.getDocument());
            	}
            	default:
                	return node;
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    public boolean /*IEnumerator.*/hasNext()
    {
        return moveToNextNode();
    }

    public void reset()
    {
        setCurrentPosition(null);
    }

    public 
    Node /*IEnumerator<Node>.*/next() { return getCurrentNode(); }

    public void /*IDocumentPositionListener.*/notifyMoved(/*DocumentPositionMovement*/int movement)
    {
        switch (movement)
        {
            case DocumentPositionMovement.ABOVE:
            {
                Node newNode = getValidParent(getCurrentNode());
                if (newNode != getCurrentNode())
                    setCurrentPosition(DocumentPosition.createPositionAfter(newNode));
                break;
            }
            case DocumentPositionMovement.BELOW:
            {
                Node newNode = getValidChild(getCurrentNode());
                // WORDSNET-27816 here we jump over an empty Body.
                if ((getCurrentNode().getNodeType() == NodeType.BODY) && (newNode == null))
                    getCurrentPosition().moveNodeEnd();
                else if (newNode != getCurrentNode())
                    setCurrentPosition(DocumentPosition.createPositionBefore(newNode));
                break;
            }
            default:
                break;
        }

        onMoved(movement);
    }

    /// <summary>
    /// Gets whether the current node is the start node of the range.
    /// </summary>
    private boolean isStartNode() { return getRange().getStart().isSameNode(getCurrentPosition()); }

    /// <summary>
    /// Gets whether the current node is the end node of the range.
    /// </summary>
    protected boolean isEndNode() { return getRange().getEnd().isSameNode(getCurrentPosition()); }

    /// <summary>
    /// Gets the node range being enumerated.
    /// </summary>
    NodeRange getRange() { return mRange; };

    private NodeRange mRange;

    Node getCurrentNode() { return (getCurrentPosition() != null) ? getCurrentPosition().getNode() : null; }

    DocumentPosition getCurrentPosition() { return mCurrentPosition; }; void setCurrentPosition(DocumentPosition value) { mCurrentPosition = value; };

    private DocumentPosition mCurrentPosition;

    private /*final*/ boolean mStopAtInvalidRangeNodes;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
}

