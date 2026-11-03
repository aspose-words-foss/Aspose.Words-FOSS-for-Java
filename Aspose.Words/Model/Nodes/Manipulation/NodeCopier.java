// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.IntList;


/// <summary>
/// Copies (clones and inserts) nodes from a source range to some point in the document.
/// Nodes being copied may be modified using an <see cref="INodeModifier"/> implementor.
/// </summary>
class NodeCopier extends NodeTraverser
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private NodeCopier(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCloningListener nodeCloningListener,
        /*NodeCopierOptions*/int options)
    {
    	super(sourceRange);
	
        mReferenceNode = referenceNode;
        mModifier = modifier;
        mNodeCloningListener = nodeCloningListener;
        mOptions = options;

        // Whether need to copy from block to inline level.
        Node startNode = sourceRange.getStart().getNode();
        mMoveToInlineLevel =
            getProcessBoundBlockAnnotationAsInline() &&
            (referenceNode.getNodeLevel() == NodeLevel.INLINE) &&
            (startNode.getNodeLevel() == NodeLevel.BLOCK) &&
            NodeUtil.isCrossStructureAnnotation(startNode);

        mCurrentReferenceNode = referenceNode.getPreviousSibling();
        mBuilder = new DocumentBuilder(referenceNode.fetchDocument());

        Paragraph referenceParagraph = (Paragraph)mReferenceNode.getAncestor(NodeType.PARAGRAPH);
        AttrCollection paragraphFrameAttrs = fetchParagraphFrameAttrs(referenceParagraph);
        if (paragraphFrameAttrs.containsAnyKey(ParaPr.FLOATING_ATTRS) || isRangeWithinSingleFrame(sourceRange))
            mReferenceParagraphFrameAttrs = paragraphFrameAttrs;
    }

    /// <summary>
    /// Clones paragraph attributes but preserves frame attributes.
    /// </summary>
    /// <remarks>
    /// EV: Probably, it should be an instance method of the Paragraph class, but I'm not sure it's a common case.
    /// Also, I really don't want to introduce one more XxxUtils class for this method, but I need to reuse it in FieldRefUtil.
    /// </remarks>
    static void cloneParaPrPreserveFrameAttrs(Paragraph paragraph, ParaPr paraPr)
    {
        AttrCollection frameAttrs = fetchParagraphFrameAttrs(paragraph);
        paragraph.setParaPr(AttrCollectionExtension.deepClone(paraPr));
        mirrorFrameAttrs(frameAttrs, paragraph.getParaPr());
    }

    private static boolean isRangeWithinSingleFrame(NodeRange range)
    {
        AttrCollection lastFrameAttrs = null;

        ArrayList<Node> list = NodeFinder.findNodes(range, NodeType.PARAGRAPH);

        Node startParagraph = range.getStart().getNode().getAncestor(NodeType.PARAGRAPH);
        if (startParagraph != null)
            list.add(0, startParagraph);

        for (int i = 0; i < list.size(); i++)
        {
            Node node = list.get(i);
            if (i == list.size() - 1 && range.getEnd().getNode().isAncestorNode(node) && range.getEnd().getNode().isFirstNonZeroLengthChild())
                continue;

            AttrCollection frameAttrs = fetchParagraphFrameAttrs((Paragraph)node);
            if (!frameAttrs.containsAnyKey(ParaPr.FLOATING_ATTRS))
                return false;

            if (lastFrameAttrs != null && !frameAttrs.equals(lastFrameAttrs))
                return false;

            lastFrameAttrs = frameAttrs;
        }

        return true;
    }

    private static AttrCollection fetchParagraphFrameAttrs(Paragraph paragraph)
    {
        AttrCollection attrs = new ParaPr();
        if (paragraph == null)
            return attrs;

        paragraph.getParaPr().mirrorTo(attrs, G_PARA_FRAME_ATTRS);

        for (int key : G_PARA_FRAME_ATTRS)
        {
            if (attrs.containsKey(key))
                continue;

            Object value = ((IParaAttrSource)paragraph).fetchParaAttr(key);
            Object defaultValue = ParaPr.fetchDefaultAttr(key);
            if (value.equals(defaultValue))
                continue;

            attrs.add(key, value);
        }

        return attrs;
    }

    private static void mirrorFrameAttrs(AttrCollection frameAttrs, AttrCollection target)
    {
        if (frameAttrs.containsAnyKey(ParaPr.FLOATING_ATTRS) || target.containsAnyKey(ParaPr.FLOATING_ATTRS))
            frameAttrs.mirrorTo(target, ParaPr.BORDER_ATTRS);
        frameAttrs.mirrorTo(target, ParaPr.FLOATING_ATTRS);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    static void copy(NodeRange sourceRange, Node referenceNode) throws Exception
    {
        copy(sourceRange, referenceNode, null, null);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="modifier">A node modifier object that modifies nodes being copied.</param>
    static void copy(NodeRange sourceRange, Node referenceNode, INodeModifier modifier) throws Exception
    {
        copy(sourceRange, referenceNode, modifier, null);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="nodeCloningListener">A node cloning listener object that listens nodes being copied.</param>
    static void copy(NodeRange sourceRange, Node referenceNode, INodeCloningListener nodeCloningListener) throws Exception
    {
        copy(sourceRange, referenceNode, null, nodeCloningListener);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="modifier">A node modifier object that modifies nodes being copied.</param>
    /// <param name="nodeCloningListener">A node cloning listener object that listens nodes being copied.</param>
    static void copy(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCloningListener nodeCloningListener) throws Exception
    {
        final /*NodeCopierOptions*/int OPTIONS = NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS | NodeCopierOptions.CLONE_NODE;
        copy(sourceRange, referenceNode, modifier, nodeCloningListener, OPTIONS);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="modifier">A node modifier object that modifies nodes being copied.</param>
    /// <param name="nodeCloningListener">A node cloning listener object that listens nodes being copied.</param>
    /// <param name="options">Allows to define copying options.</param>
    static void copy(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCloningListener nodeCloningListener,
        /*NodeCopierOptions*/int options) throws Exception
    {
        if (sourceRange.isVoid())
            return;

        // In most cases, source range itself will work.
        NodeRange copierRange = sourceRange;

        // This is a workaround for copying ranges ending on a composite node when the ending composite node is included.
        boolean isEndingCompositeNodeIncluded = sourceRange.getEnd().getNode().isComposite() && sourceRange.getEnd().isEnd();
        if (isEndingCompositeNodeIncluded)
        {
            // Move to the start of the next node on the same level or higher.
            // This works for the last node as well (the move is OK to fail).
            DocumentPosition newEnd = sourceRange.getEnd().deepClone();

            Node root = null;

            if (sourceRange.getEnd().getNode().getDocument() instanceof GlossaryDocument)
                root = getAncestorBelowBuildingBlock(sourceRange.getEnd().getNode());

            newEnd.move(root, true, false, false, false, false);

            copierRange = new NodeRange(sourceRange.getStart(), newEnd);
        }

        NodeCopier copier = new NodeCopier(
            copierRange,
            referenceNode,
            modifier,
            nodeCloningListener,
            options);
        try /*JAVA: was using*/
        {
            copier.copy();
        }
        finally { if (copier != null) copier.dispose(); }
    }

    private static Node getAncestorBelowBuildingBlock(Node node)
    {
        Node cur = node;
        while (true)
        {
            if (cur.getParentNode() == null)
                return null;

            if (cur.getParentNode().getNodeType() == NodeType.BUILDING_BLOCK)
                return cur;

            cur = cur.getParentNode();
        }
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document, replacing fields with the field results.
    /// WORDSNET-6913 Preserves field codes of FWR-fields if their removal is not forced.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="modifier">A node modifier object that modifies nodes being copied.</param>
    /// <param name="nodeCopierListener">A node copier listener object that listens nodes being copied.</param>
    /// <param name="removeFwrFields">TRUE to force FWR-fields' removal.</param>
    static NodeRange copyWithoutFields(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCopierListener nodeCopierListener,
        boolean removeFwrFields) throws Exception
    {
        return copyWithoutFields(
            sourceRange,
            referenceNode,
            modifier,
            nodeCopierListener,
            removeFwrFields ? FieldRemoverDefaultFilter.INSTANCE : FieldRemoverRetainFwrFieldsFilter.INSTANCE);
    }

    static NodeRange copyWithoutFields(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCopierListener nodeCopierListener,
        boolean removeFwrFields,
        /*NodeCopierOptions*/int options) throws Exception
    {
        return copyWithoutFields(
            sourceRange,
            referenceNode,
            modifier,
            nodeCopierListener,
            removeFwrFields ? FieldRemoverDefaultFilter.INSTANCE : FieldRemoverRetainFwrFieldsFilter.INSTANCE,
            options);
    }

    static NodeRange copyWithoutFields(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCopierListener nodeCopierListener,
        IFieldRemoverFilter fieldRemoverFilter) throws Exception
    {
        return copyWithoutFields(
            sourceRange,
            referenceNode,
            modifier,
            nodeCopierListener,
            fieldRemoverFilter,
            NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS | NodeCopierOptions.CLONE_NODE);
    }

    /// <summary>
    /// Copies (clones and inserts) nodes from a source range to some point in the document, replacing fields with the field results.
    /// WORDSNET-6913 Preserves field codes of FWR-fields if their removal is not forced.
    /// </summary>
    /// <param name="sourceRange">A node range to copy nodes from.</param>
    /// <param name="referenceNode">A node in the document before which the cloned nodes will be inserted.</param>
    /// <param name="modifier">A node modifier object that modifies nodes being copied.</param>
    /// <param name="nodeCopierListener">A node copier listener object that listens nodes being copied.</param>
    /// <param name="fieldRemoverFilter">A field remover filter.</param>
    /// <param name="options">Allows to define copying options.</param>
    static NodeRange copyWithoutFields(
        NodeRange sourceRange,
        Node referenceNode,
        INodeModifier modifier,
        INodeCopierListener nodeCopierListener,
        IFieldRemoverFilter fieldRemoverFilter,
        /*NodeCopierOptions*/int options) throws Exception
    {
        DocumentPosition beforeInsertedRange = DocumentPosition.createPositionBefore(referenceNode);
        beforeInsertedRange.move(null, false, false, false, false, false);

        copy(sourceRange, referenceNode, modifier, nodeCopierListener, options);

        DocumentPosition insertedRangeEnd = DocumentPosition.createPositionBefore(referenceNode);

        NodeRange insertedRange = new NodeRange(beforeInsertedRange, insertedRangeEnd);

        if (nodeCopierListener != null)
            nodeCopierListener.notifyNodeRangeCopied(sourceRange, insertedRange);

        return FieldRemover.replaceFieldsWithResults(insertedRange, fieldRemoverFilter);
    }

    /// <summary>
    /// Performs actual copying.
    /// </summary>
    private void copy() throws Exception
    {
        copyStartNodeAncestors();
        traverse();
    }

    /// <summary>
    /// Copies the start node's ancestors that should wrap the start node it to be inserted in a right place.
    /// For instance, contents of a table should be wrapped.
    /// </summary>
    private void copyStartNodeAncestors() throws Exception
    {
        // SPEED Do not traverse through the range if it is surely unneeded.
        if (!hasPossibleStartNodeAncestorToCopy())
            return;

        Node topAncestor;
        // Find the top start node ancestor encountered while traversal.
        TopStartNodeAncestorFinder finder = new TopStartNodeAncestorFinder(getRange());
        try /*JAVA: was using*/
    	{
            topAncestor = finder.getTopStartNodeAncestor();
    	}
        finally { if (finder != null) finder.dispose(); }

        // If it is not found then simply return as we deal with a plain node range.
        if (topAncestor == null)
            return;

        // Exclude ancestors that are not used as wrappers.
        Node startNode = getRange().getStart().getNode();
        while ((topAncestor != startNode) && !needCopyEdgeNodeAncestor(topAncestor))
            topAncestor = getTopAncestorNodeBelow(startNode, topAncestor);

        // If wrapping is not required, return.
        if (topAncestor == startNode)
            return;

        // Include ancestors to perform additional wrapping if needed.
        while (needWrapEdgeNodeAncestorByParent(topAncestor))
            topAncestor = topAncestor.getParentNode();

        // Insert valid wrapping ancestors from the top to the bottom.
        for (Node ancestor = topAncestor;
             (ancestor != startNode) && isRangeNodeValid(ancestor);
             ancestor = getTopAncestorNodeBelow(startNode, ancestor))
        {
            cloneAndInsertEdgeNodeAncestor(ancestor);
        }

        mTopStartNodeAncestor = topAncestor;
    }

    /// <summary>
    /// Returns a value indicating whether the start of the range being copied has an ancestor which
    /// should be copied if its boundary is crossed while traversing of the range.
    /// </summary>
    private boolean hasPossibleStartNodeAncestorToCopy()
    {
        for (Node ancestor = getRange().getStart().getNode(); ancestor != null; ancestor = ancestor.getParentNode())
        {
            if (needCopyEdgeNodeAncestor(ancestor))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns a value indicating whether the specified edge (i.e. start or end) node ancestor
    /// should be copied to wrap its descendants.
    /// </summary>
    private static boolean needCopyEdgeNodeAncestor(Node ancestor)
    {
        switch (ancestor.getNodeType())
        {
            case NodeType.TABLE:
            case NodeType.SHAPE:
            case NodeType.OFFICE_MATH:
                return true;
            default:
                return (NodeUtil.isRowLevelNode(ancestor) || NodeUtil.isCellLevelNode(ancestor)) &&
                    !NodeUtil.isCrossStructureAnnotation(ancestor);
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified edge (i.e. start or end) node ancestor
    /// requires extra wrapping by the parent node.
    /// </summary>
    private boolean needWrapEdgeNodeAncestorByParent(Node ancestor)
    {
        if ((ancestor.getNodeLevel() == NodeLevel.ROW) && (mReferenceNode.getNodeLevel() != NodeLevel.ROW))
            return true;

        if ((ancestor.getNodeLevel() == NodeLevel.CELL) && (mReferenceNode.getNodeLevel() != NodeLevel.CELL))
            return true;

        if (ancestor.getNodeType() == NodeType.OFFICE_MATH && !((OfficeMath)ancestor).isTopLevel())
            return true;

        return false;
    }

    /// <summary>
    /// Returns the top ancestor node for the specified descendant node just right below the specified ancestor node.
    /// </summary>
    private static Node getTopAncestorNodeBelow(Node descendant, Node topAncestor)
    {
        Node node = descendant;
        while (node.getParentNode() != topAncestor)
            node = node.getParentNode();

        return node;
    }

    /// <summary>
    /// Clones the specified edge (i.e. start or end) node ancestor and inserts it where appropriate.
    /// </summary>
    private void cloneAndInsertEdgeNodeAncestor(Node ancestor) throws Exception
    {
        Node ancestorToInsert = extractRangeNode(ancestor, false);
        // Note, that at the moment there is no INodeModifier implementation which can reject wrapping nodes' copying.
        // If this will be changed then the class should be reworked to reject wrapped nodes' copying also for example.
        Debug.assertTrue(ancestorToInsert != null);

        prepareAncestorNodeInsertion(ancestor);
        insertNode(ancestor, ancestorToInsert);
        mCurrentReferenceParentNode = (CompositeNode)ancestorToInsert;
    }

    /// <summary>
    /// Shortcut overload.
    /// </summary>
    private Node extractRangeNode(Node rangeNode, boolean modifyChildren) throws Exception
    {
        Node node = extractRangeNode(rangeNode, mModifier, mNodeCloningListener, getCloneNode(), modifyChildren);

        if ((mReferenceParagraphFrameAttrs != null) && (node != null) && (node.getNodeType() == NodeType.PARAGRAPH))
            mirrorFrameAttrs(mReferenceParagraphFrameAttrs, ((Paragraph)node).getParaPr());

        return node;
    }

    /// <summary>
    /// Prepares a composite node insertion by separating the reference node with a section or paragraph break if needed.
    /// </summary>
    private void prepareAncestorNodeInsertion(Node sourceAncestor)
    {
        if (hasCurrentReferenceParentNode())
            return;

        switch (sourceAncestor.getNodeType())
        {
            case NodeType.PARAGRAPH:
            case NodeType.TABLE:
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                {
                    if (!getRange().isInSameAncestorOrSelf(NodeType.SECTION))
                    {
                        // andrnosk: We should not insert section break if the current node is last child of source section,
                        // or it is inline node (!IsComposite - it means that reference node is Field or Bookmark)
                        if ((sourceAncestor.getNextNonAnnotationSibling() != null) || !mReferenceNode.isComposite())
                        {
                            // The current node is in the middle, and it seems like the end node of source
                            // range is in another section. Insert a section break before the reference node if needed.
                            separateReferenceNodeWithSectionBreak();
                        }
                    }
                    else
                    {
                        // The current node is in the middle, and it seems like the end node of source
                        // range is in the same section. Insert a paragraph break before the reference node if needed.
                        separateReferenceNodeWithParagraphBreak(sourceAncestor);
                    }

                    break;
                }
            case NodeType.SECTION:
                {
                    // The current section is in the middle, insert a section break before the reference node if needed.
                    separateReferenceNodeWithSectionBreak();
                    break;
                }
            default:
                {
                    // Do nothing.
                    break;
                }
        }
    }

    protected /*override*/ void onStartNodeAncestor()
    {
        // Do not process an invalid node.
        if (!isCurrentNodeValid())
            return;

        // Do not process a start node ancestor if wrapping is not required.
        if (!hasCurrentReferenceParentNode())
        {
            if (getProcessBoundBlockAnnotationAsInline() &&
                NodeUtil.isCrossStructureAnnotation(getRange().getEnd().getNode()) &&
                (getRange().getEnd().getNode().getPreviousNonAnnotationSibling() == getCurrentNode()))
                separateReferenceNode(getCurrentNode());

            return;
        }

        // Set the current reference node for a sibling node if no more wrapping is required.
        mCurrentReferenceNode = mCurrentReferenceParentNode;

        // Set the current parent reference node to wrap a sibling node if needed.
        mCurrentReferenceParentNode = (getCurrentNode() != mTopStartNodeAncestor)
            ? mCurrentReferenceParentNode.getParentNode()
            : null;
    }

    protected /*override*/ void onMiddleNodeAncestor() throws Exception
    {
        // Do not process an invalid node.
        if (!isCurrentNodeValid())
            return;

        if (needToMoveToInlineLevel())
        {
            // Move to compatible node level (to inline) on start of copying only.
            mMoveToInlineLevel = false;

            for (Node node : (Iterable<Node>) ((CompositeNode)getCurrentNode()).getChildNodes(NodeType.ANY, false))
            {
                if (node.isComposite())
                    copyMiddleNode(node);
                else
                    cloneAndInsert(node);
            }

            separateReferenceNode(getCurrentNode());
        }
        else
        {
            copyMiddleNode(getCurrentNode());
        }
    }

    /// <summary>
    /// Copies a node in a middle of copying range into the destination.
    /// </summary>
    private void copyMiddleNode(Node node) throws Exception
    {
        // Clone and insert the encountered ancestor node.
        prepareAncestorNodeInsertion(node);
        cloneAndInsert(node);
    }

    /// <summary>
    /// Returns <c>true</c> if contents of the current node (paragraph) should be copied to the destination
    /// to inline level without adding a new paragraph.
    /// </summary>
    private boolean needToMoveToInlineLevel()
    {
        Node referenceNode = mCurrentReferenceNode != null ? mCurrentReferenceNode : mReferenceNode;
        return mMoveToInlineLevel && (getCurrentNode().getNodeLevel() == NodeLevel.BLOCK) && (referenceNode.getNodeLevel() == NodeLevel.INLINE);
    }

    protected /*override*/ void onEndNodeAncestor() throws Exception
    {
        // Stop traversal if the current node is invalid as all of its descendants are invalid either.
        if (!isCurrentNodeValid())
        {
            stopTraverse();
            return;
        }

        // If the current end node ancestor's descendants should be wrapped by the ancestor,
        // clone and insert it where appropriate.
        if (hasCurrentReferenceParentNode() || needCopyEdgeNodeAncestor(getCurrentNode()))
        {
            cloneAndInsertEdgeNodeAncestor(getCurrentNode());
            return;
        }

        Node startNode = getRange().getStart().getNode();
        if (!(getProcessBoundBlockAnnotationAsInline() &&
                NodeUtil.isCrossStructureAnnotation(startNode) &&
                (startNode.getNodeLevel() == NodeLevel.BLOCK) &&
                (startNode.getNextNonAnnotationSibling() == getCurrentNode())))
            separateReferenceNode(getCurrentNode());
    }

    /// <summary>
    /// Separates the reference node with a section or paragraph break if needed.
    /// </summary>
    private void separateReferenceNode(Node closedSourceNode)
    {
        switch (closedSourceNode.getNodeType())
        {
            case NodeType.PARAGRAPH:
                {
                    separateReferenceNodeWithParagraphBreak(closedSourceNode);
                    mCurrentReferenceNode = mReferenceNode;
                    mCurrentReferenceNode = findValidReferenceNode();
                    mIsInLastParent = true;
                    break;
                }
            case NodeType.SECTION:
                {
                    separateReferenceNodeWithSectionBreak();
                    mCurrentReferenceNode = mReferenceNode;
                    mCurrentReferenceNode = findValidReferenceNode();
                    mIsInLastParent = true;
                    break;
                }
            default:
                {
                    // Do nothing.
                    break;
                }
        }
    }

    protected /*override*/ void onNonCompositeNode() throws Exception
    {
        cloneAndInsert(getCurrentNode());
    }

    /// <summary>
    /// Clones the specified node and inserts it where appropriate.
    /// </summary>
    private void cloneAndInsert(Node node) throws Exception
    {
        // WORDSNET-11803 Do not insert paragraph with section break, because extra paragraph already has been inserted in SeparateReferenceNodeWithPageBreak.
        if (mIsPageBreakInserted && isSectionBreakParagraph(node))
            return;

        Node nodeToInsert = extractRangeNode(node, true);
        insertNode(node, nodeToInsert);
    }

    protected /*override*/ boolean needSkipRangeNodeExtract(Node rangeNode)
    {
        return
            ((mOptions & NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS) != 0) &&
            NodeUtil.isCrossStructureAnnotation(rangeNode);
    }

    /// <summary>
    /// Inserts the specified node.
    /// </summary>
    private void insertNode(Node sourceNode, Node nodeToInsert)
    {
        if (nodeToInsert == null)
            return;

        if (hasCurrentReferenceParentNode())
        {
            insertChildNode(nodeToInsert);
        }
        else if ((mCurrentReferenceNode == null) || mIsInLastParent)
        {
            insertSiblingNodeBefore(sourceNode, nodeToInsert);
        }
        else
        {
            insertSiblingNodeAfter(sourceNode, nodeToInsert);
        }
    }

    /// <summary>
    /// Appends the specified node as a child to the current destination parent node.
    /// </summary>
    private void insertChildNode(Node nodeToInsert)
    {
        mCurrentReferenceParentNode.appendChild(nodeToInsert);
    }

    /// <summary>
    /// Inserts the specified node after the current destination node and then makes it current destination node.
    /// </summary>
    private void insertSiblingNodeAfter(Node sourceNode, Node nodeToInsert)
    {
        mCurrentReferenceNode = findValidReferenceNode(sourceNode);
        mCurrentReferenceNode = mCurrentReferenceNode.insertNext(nodeToInsert);
    }

    /// <summary>
    /// Inserts the specified node before the current destination node and then makes it current destination node.
    /// </summary>
    private void insertSiblingNodeBefore(Node sourceNode, Node nodeToInsert)
    {
        boolean isFirstChild = (mCurrentReferenceNode == null);

        mCurrentReferenceNode = findValidReferenceNode(sourceNode);
        mCurrentReferenceNode.insertPrevious(nodeToInsert);

        if (isFirstChild)
            mCurrentReferenceNode = nodeToInsert;
    }

    protected /*override*/ void onMoved(/*DocumentPositionMovement*/int movement)
    {
        if (hasCurrentReferenceParentNode())
            return;

        switch (movement)
        {
            case DocumentPositionMovement.ABOVE:
            case DocumentPositionMovement.BELOW:
                mCurrentReferenceNode = findValidReferenceNode();
                break;
            default:
                break;
        }
    }

    private void separateReferenceNodeWithPageBreak(boolean insertParagraph)
    {
        if (mIsReferenceNodeSeparated)
            return;

        mBuilder.moveTo(mReferenceNode);

        if (insertParagraph)
            mBuilder.insertParagraph();

        mBuilder.write(ControlChar.PAGE_BREAK);

        mIsReferenceNodeSeparated = true;
        mIsPageBreakInserted = true;
    }

    private void separateReferenceNodeWithParagraphBreak(Node sourceNode)
    {
        // andrnosk: do not insert paragraph, if source node type is the same as reference node type,
        // for example inserting run inside paragraph. And also if reference node is the only one run inside paragraph.
        if (mIsReferenceNodeSeparated || (mReferenceNode.getNodeLevel() == sourceNode.getNodeLevel())
            || ((mReferenceNode.getNodeType() == NodeType.RUN) && mReferenceNode.isFirstChild()))
            return;

        mBuilder.moveTo(mReferenceNode);

        // WORDSNET-16519 Workaround. The DocumentBuilder.InsertParagraph loses ParagraphBreakRunPr.
        RunPr currentParaBreakPr = AttrCollectionExtension.deepClone(mBuilder.getCurrentParagraph().getParagraphBreakRunPr());
        mBuilder.insertParagraph();
        mBuilder.getCurrentParagraph().setParagraphBreakRunPr(currentParaBreakPr);

        setStartParaPr(mBuilder.getCurrentParagraph().getPreviousNonAnnotationSibling());

        mIsReferenceNodeSeparated = true;
    }

    /// <summary>
    /// If the specified start node is paragraph, sets its properties equal to the ones owned by the start paragraph
    /// of the source range to fit MS Word behavior. However, if <see cref="UseSourceStartAncestorPr"/> is
    /// <c>false</c>, does nothing.
    /// </summary>
    private void setStartParaPr(Node startNode)
    {
        if (!getUseSourceStartAncestorPr())
            return;

        if ((startNode == null) || (startNode.getNodeType() != NodeType.PARAGRAPH))
            return;

        Paragraph startParagraph = (Paragraph)startNode;

        Node rangeStartNode = getRange().getStart().getNode();
        Node sourceStartParagraphNode =
            NodeUtil.isCrossStructureAnnotation(rangeStartNode) && (rangeStartNode.getNodeLevel() != NodeLevel.INLINE)
                ? NodeUtil.findNextParagraph(rangeStartNode)
                : NodeUtil.getAncestorOrSelf(rangeStartNode, NodeType.PARAGRAPH);
        if ((sourceStartParagraphNode == null) || (sourceStartParagraphNode.getNodeType() != NodeType.PARAGRAPH))
            return;

        Paragraph sourceStartParagraph = (Paragraph)sourceStartParagraphNode;

        if (mReferenceParagraphFrameAttrs != null)
            cloneParaPrPreserveFrameAttrs(startParagraph, sourceStartParagraph.getParaPr());
        else
            startParagraph.setParaPr(AttrCollectionExtension.deepClone(sourceStartParagraph.getParaPr()));

        startParagraph.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(sourceStartParagraph.getParagraphBreakRunPr()));
    }

    private void separateReferenceNodeWithSectionBreak()
    {
        if (mReferenceNode.getAncestor(NodeType.BODY) == null)
            return;

        if (mReferenceNode.getAncestor(NodeType.FOOTNOTE) != null)
        {
            // WORDSNET-11803 MS Word does not insert section break inside footnotes. Insert page break instead.
            separateReferenceNodeWithPageBreak(true);
            return;
        }

        if (mReferenceNode.getAncestor(NodeType.SHAPE) != null)
        {
            // WORDSNET-21966 MS Word does not insert section break inside shape. Insert page break instead.
            separateReferenceNodeWithPageBreak(false);
            return;
        }

        if (mIsReferenceNodeSeparated)
            return;

        mBuilder.moveTo(mReferenceNode);

        // We have to detect the section start type of the last source section.
        Section section = (Section)NodeUtil.getAncestorOrSelf(getRange().getEnd().getNode(), NodeType.SECTION);
        /*SectionStart*/int sectionStart = section.getPageSetup().getSectionStart();

        mBuilder.insertSection(sectionStart);

        if (getProlongRangeStartSectionHeadersFooters())
        {
            Section startSection = (Section)NodeUtil.getAncestorOrSelf(getRange().getStart().getNode(), NodeType.SECTION);

            if (section != startSection)
            {
                for (HeaderFooter headerFooter : section.getHeadersFooters().toArray())
                    mBuilder.getCurrentSection().getHeadersFooters().add(headerFooter);

                for (HeaderFooter headerFooter : startSection.getHeadersFooters().toArray())
                {
                    if (!isDefaultHeaderFooterContent(headerFooter))
                        section.getHeadersFooters().add(headerFooter.deepClone(true));
                }
            }
        }

        // andrnosk: When we move cursor to paragraph, its placed at the end of paragraph,
        // that is why section break is inserted at the end of paragraph,
        // but we need to insert section break before the paragraph, so move reference paragraph to the next (inserted) section.
        if (mReferenceNode.getNodeType() == NodeType.PARAGRAPH)
            mBuilder.getCurrentSection().getBody().prependChild(mReferenceNode);

        setStartSectPr();

        // andrnosk: Remove empty paragraph which was created upon inserting new section.
        if (!mBuilder.getCurrentParagraph().hasChildNodes())
            mBuilder.getCurrentParagraph().remove();

        mIsReferenceNodeSeparated = true;
    }

    private static boolean isDefaultHeaderFooterContent(HeaderFooter headerFooter)
    {
        if (!headerFooter.hasChildNodes())
            return false;

        if (headerFooter.getCount() != 1)
            return false;

        Paragraph paragraph = headerFooter.getFirstParagraph();
        if (paragraph == null)
            return false;

        return !paragraph.hasChildNodes();
    }

    /// <summary>
    /// Sets properties of the first destination ancestor section equal to the ones owned by the start section
    /// of the source range to fit MS Word behavior. However, if <see cref="UseSourceStartAncestorPr"/> is
    /// <c>false</c>, does nothing.
    /// </summary>
    private void setStartSectPr()
    {
        if (!getUseSourceStartAncestorPr())
            return;

        Section startSection = (Section)mBuilder.getCurrentSection().getPreviousSibling();
        if (startSection == null)
            return;

        Section sourceStartSection = (Section)NodeUtil.getAncestorOrSelf(getRange().getStart().getNode(), NodeType.SECTION);
        startSection.setSectPr(AttrCollectionExtension.deepClone(sourceStartSection.getSectPr()));

        // If the last child of the start section is a paragraph, set its properties either.
        setStartParaPr(startSection.getBody().getLastNonAnnotationChild());
    }

    /// <summary>
    /// Returns a reference node of the same level as the new node to be inserted.
    /// </summary>
    private Node findValidReferenceNode()
    {
        Node sourceNode = (getCurrentNode() != null) ? getCurrentNode() : getRange().getStart().getNode();
        return findValidReferenceNode(sourceNode);
    }

    /// <summary>
    /// Returns a reference node of the same level as the new node to be inserted.
    /// </summary>
    private Node findValidReferenceNode(Node sourceNode)
    {
        if (mCurrentReferenceNode == null)
            mCurrentReferenceNode = mReferenceNode;

        if (sourceNode.getNodeLevel() == mCurrentReferenceNode.getNodeLevel())
            return mCurrentReferenceNode;

        if (isCurrentReferenceValidForCrossStructureAnnotation(sourceNode))
            return mCurrentReferenceNode;

        if (!mIsInLastParent)
        {
            Node referenceNode = findValidReferenceNodeAbove(sourceNode, mCurrentReferenceNode);
            return (referenceNode != null) ? referenceNode : findValidReferenceNodeBelow(sourceNode, mCurrentReferenceNode);
        }
        else
        {
            return findValidReferenceNodeAbove(sourceNode, mReferenceNode);
        }
    }

    private boolean isCurrentReferenceValidForCrossStructureAnnotation(Node sourceNode)
    {
        return mMoveToInlineLevel &&
               sourceNode.getNodeLevel() == NodeLevel.BLOCK &&
               NodeUtil.isCrossStructureAnnotation(sourceNode) &&
               mCurrentReferenceNode.getNodeLevel() == NodeLevel.INLINE;
    }

    /// <summary>
    /// Returns a reference node of the same level as the new node to be inserted.
    /// Looks for it in above levels (node parents).
    /// </summary>
    private static Node findValidReferenceNodeAbove(Node sourceNode, Node referenceNode)
    {
        while ((referenceNode != null) && (referenceNode.getNodeLevel() != sourceNode.getNodeLevel()))
        {
            // WORDSNET-24236 Place SDT ranges to appropriate parent.
            if ((sourceNode.getNodeLevel() == NodeLevel.OTHER) && referenceNode.getParentNode().canInsert(sourceNode))
                return referenceNode;

            referenceNode = getValidParent(referenceNode.getParentNode());
        }

        return referenceNode;
    }

    /// <summary>
    /// Returns a reference node of the same level as the new node to be inserted.
    /// Looks for it in below levels (node children).
    /// </summary>
    private static Node findValidReferenceNodeBelow(Node sourceNode, Node referenceNode)
    {
        while ((referenceNode != null) && (referenceNode.isComposite()) && (referenceNode.getNodeLevel() != sourceNode.getNodeLevel()))
            referenceNode = getValidChild(((CompositeNode)referenceNode).getLastChild());

        return referenceNode;
    }

    private boolean hasCurrentReferenceParentNode() { return (mCurrentReferenceParentNode != null); }

    private static boolean isSectionBreakParagraph(Node node)
    {
        if (node.getNodeType() != NodeType.PARAGRAPH)
            return false;

        return ((Paragraph)node).isSectionBreakParagraph();
    }

    /// <summary>
    /// Finds the top start node ancestor encountered while a node range traversal.
    /// </summary>
    private static class TopStartNodeAncestorFinder extends NodeTraverser
    {
        TopStartNodeAncestorFinder(NodeRange range)
        {
        	super(range);
	
        }

        Node getTopStartNodeAncestor() throws Exception
        {
            traverse();
            return mTopStartNodeAncestor;
        }

        protected /*override*/ void onStartNodeAncestor()
        {
            mTopStartNodeAncestor = getCurrentNode();
        }

        protected /*override*/ void onEndNodeAncestor()
        {
            stopTraverse();
        }

        private Node mTopStartNodeAncestor;
    }

    /// <summary>
    /// Indicates whether to replace every destination start node ancestor's properties with the corresponding
    /// source ones in case when any ancestor of the same type can be met while a source range traversal.
    /// </summary>
    private boolean getUseSourceStartAncestorPr() { return (mOptions & NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR) != 0; }

    private boolean getProlongRangeStartSectionHeadersFooters() { return (mOptions & NodeCopierOptions.PROLONG_RANGE_START_SECTION_HEADERS_FOOTERS) != 0; }

    /// <summary>
    /// When true, then node need to be cloned while extracting it within the enumerated range.
    /// </summary>
    private boolean getCloneNode() { return (mOptions & NodeCopierOptions.CLONE_NODE) != 0; }

    /// <summary>
    /// If <c>true</c>, when the range has block level annotation nodes at bounds, copying gives same result
    /// as when the annotation nodes are moved to inline level.
    /// </summary>
    private boolean getProcessBoundBlockAnnotationAsInline() { return (mOptions & NodeCopierOptions.PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE) != 0; }

    /// <summary>
    /// A node before which source nodes will be copied.
    /// </summary>
    private /*final*/ Node mReferenceNode;
    private /*final*/ AttrCollection mReferenceParagraphFrameAttrs;
    /// <summary>
    /// An object modifying nodes while copying.
    /// </summary>
    private /*final*/ INodeModifier mModifier;
    private /*final*/ INodeCloningListener mNodeCloningListener;
    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ /*NodeCopierOptions*/int mOptions;
    private Node mTopStartNodeAncestor;
    private CompositeNode mCurrentReferenceParentNode;
    /// <summary>
    /// The current reference node.
    /// </summary>
    private Node mCurrentReferenceNode;
    /// <summary>
    /// True if we are in the last parent (that owns the last node).
    /// </summary>
    private boolean mIsInLastParent;
    /// <summary>
    /// True if we have one or more paragraph or section breaks in the sequence and we have already inserted one
    /// to separate the reference node.
    /// </summary>
    private boolean mIsReferenceNodeSeparated;
    /// <summary>
    /// True if paragraph with page break has been inserted instead of section break.
    /// </summary>
    private boolean mIsPageBreakInserted;

    /// <summary>
    /// True means that the range is started with a block level node but the reference node is inline.
    /// The first composite node of the range will not be copied, just its contents. The flag may be set only
    /// if <see cref="ProcessBoundBlockAnnotationAsInline"/> is <c>true</c>.
    /// </summary>
    private boolean mMoveToInlineLevel;

    private static /*final*/ int[] G_PARA_FRAME_ATTRS = buildFrameAttrs();

    private static int[] buildFrameAttrs()
    {
        IntList frameAttrs = new IntList();
        frameAttrs.addRange(ParaPr.FLOATING_ATTRS);
        frameAttrs.addRange(ParaPr.BORDER_ATTRS);
        return frameAttrs.toArray();
    }
}

