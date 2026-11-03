// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Removes nodes in a range.
/// </summary>
class NodeRemover extends NodeTraverser
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="range"></param>
    /// <param name="joinMode"></param>
    /// <param name="processBoundBlockAnnotationAsInline">If <c>true</c>, when the range has block level annotation
    /// nodes at bounds, removal gives same result as when the annotation nodes are moved to inline level.</param>
    NodeRemover(NodeRange range, /*NodeJoinMode*/int joinMode, boolean processBoundBlockAnnotationAsInline)
    {
    	super(range);
	
        // The only place where a bookmark range can be removed at the moment is Bookmark.Text setter.
        // But it seems to be an odd scenario to replace a table part with a text. So we do not provide
        // table column bookmark range processing at the moment. Let's implement it on a request.
        if (range.isTableColumnBookmarkRange())
            throw new IllegalArgumentException("Table column bookmark range removal is not supported." + "\r\nParameter name: " + "range");

        mJoinMode = joinMode;
        mProcessBoundBlockAnnotationAsInline = processBoundBlockAnnotationAsInline;
    }

    /// <summary>
    /// Removes nodes from start up to but not including the end node.
    /// Start and end nodes must have the same parent.
    /// </summary>
    /// <remarks>
    /// Moved from <see cref="CompositeNode"/>. This method is used in plenty of places throughout the project,
    /// so it was reasonable leaving it yet it has nothing to do with <see cref="NodeEnumerator"/> etc.
    /// </remarks>
    /// <param name="start">Deletes from this node. If null, then end should be null too and nothing is done.</param>
    /// <param name="end">Deletes up to, but excluding this node. Can be null to indicate delete to end of parent.</param>
    static void removeSameParent(Node start, Node end)
    {
        if ((end != null) && (end.getParentNode() != start.getParentNode()))
            throw new IllegalArgumentException("Start and end nodes must have the same parent.");

        Node curChild = start;
        while (curChild != end)
        {
            Node nextChild = curChild.getNextSibling();
            curChild.remove();
            curChild = nextChild;
        }
    }

    /// <summary>
    /// Removes all nodes between start and end nodes inclusively.
    /// </summary>
    /// <param name="start">The start node of the range.</param>
    /// <param name="end">The end node of the range.</param>
    /// <returns></returns>
    static void remove(Node start, Node end) throws Exception
    {
        remove(start, true, end, true);
    }

    /// <summary>
    /// Removes all nodes between start and end nodes inclusively or exclusively.
    /// </summary>
    /// <param name="start">The start node of the range.</param>
    /// <param name="isRemoveStart"></param>
    /// <param name="end">The end node of the range.</param>
    /// <param name="isRemoveEnd"></param>
    /// <returns></returns>
    static void remove(Node start, boolean isRemoveStart, Node end, boolean isRemoveEnd) throws Exception
    {
        remove(start, isRemoveStart, end, isRemoveEnd, NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING);
    }

    /// <summary>
    /// Removes all nodes between start and end nodes inclusively or exclusively. Controls whether to
    /// join parent nodes if they are different.
    /// </summary>
    /// <param name="start">The start node of the range.</param>
    /// <param name="isRemoveStart"></param>
    /// <param name="end">The end node of the range.</param>
    /// <param name="isRemoveEnd"></param>
    /// <param name="joinMode"></param>
    /// <returns></returns>
    static void remove(Node start, boolean isRemoveStart, Node end, boolean isRemoveEnd, /*NodeJoinMode*/int joinMode) throws Exception
    {
        remove(new NodeRange(start, isRemoveStart, end, isRemoveEnd), joinMode, false);
    }

    /// <summary>
    /// Removes nodes in the specified range.
    /// </summary>
    /// <param name="range">A range whose nodes to remove.</param>
    /// <param name="joinMode"></param>
    /// <param name="processBoundBlockAnnotationAsInline">If <c>true</c>, when the range has block level annotation
    /// nodes at bounds, removal gives same result as when the annotation nodes are moved to inline level.</param>
    /// <returns></returns>
    static void remove(NodeRange range, /*NodeJoinMode*/int joinMode, boolean processBoundBlockAnnotationAsInline) throws Exception
    {
        NodeRemover remover = new NodeRemover(range, joinMode, processBoundBlockAnnotationAsInline);
        try /*JAVA: was using*/
    	{
            remover.removeCore();
    	}
        finally { if (remover != null) remover.dispose(); }
    }

    /// <summary>
    /// Cannot be called "Remove" because will clash with inherited Iterator.Remove in Java.
    /// </summary>
    void removeCore() throws Exception
    {
        traverse();
        removeNodes();
        joinNodes(mJoinMode);
    }

    /// <summary>
    /// Removes nodes pending removal.
    /// </summary>
    protected /*virtual*/ void removeNodes()
    {
        // Remove everything except last parent nodes, if present.
        for (Node node : mNodesToRemove)
            node.remove();
    }

    /// <summary>
    /// Joins ancestor nodes for previously removed nodes using the specified join mode.
    /// </summary>
    void joinNodes(/*NodeJoinMode*/int joinMode)
    {
        switch (joinMode)
        {
            case NodeJoinMode.DONT_JOIN:
                // Do nothing.
                break;
            case NodeJoinMode.JOIN_TO_PREVIOUS_SIBLING:
                joinToPreviousSibling();
                break;
            case NodeJoinMode.JOIN_TO_NEXT_SIBLING:
                joinToNextSibling();
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "joinMode");
        }
    }

    private void joinToPreviousSibling()
    {
        // In mNodesToJoin we have the last paragraph or the last section, then last paragraph.
        // They will be joined with their previous siblings.
        for (CompositeNode sourceNode : mNodesToJoin)
        {
            if (sourceNode.getParentNode() == null)
                continue;

            Node prevSibling = sourceNode.getNodeLevel() != NodeLevel.INLINE
                ? sourceNode.getPreviousNonAnnotationSibling()
                : sourceNode.getPreviousSibling();

            if (prevSibling == null)
                continue;

            // Not sure that we need to join with tables.
            if ((prevSibling.getNodeType() == NodeType.TABLE) || // WORDSNET-13874
                (sourceNode.getNodeType() == NodeType.TABLE))    // WORDSNET-14353
                continue;

            // WORDSNET-15194 Do not join paragraph with StructuredDocumentTag.
            if (prevSibling.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                continue;

            // RESILIENCY WORDSNET-4691 The problem occurred because bookmark end is in SmartTag,
            // in this case previous sibling might be inline (as in the test case). To fix we make sure
            // the previous sibling is composite, if not join content of source node to the parent of previous sibling.

            Node insertAfter;

            if (prevSibling.isComposite())
            {
                insertAfter = ((CompositeNode)prevSibling).getLastChild();
            }
            else
            {
                insertAfter = prevSibling;
                prevSibling = prevSibling.getParentNode();
            }

            if (sourceNode.getNodeType() == NodeType.SECTION)
            {
                // We join section bodies and then remove the source section with all its stories.
                // I can't see another scenario.
                Section destinationSection = (Section)prevSibling;
                Section sourceSection = (Section)sourceNode;
                Body sourceBody = sourceSection.getBody();
                Body destinationBody = destinationSection.getBody();

                destinationBody.insertAfter(sourceBody.getFirstChild(), null, destinationBody.getLastChild());

                if (sourceSection.getNextSibling() != null)
                    joinSectionHeaderFootersToNextSibling(sourceSection, (Section)sourceSection.getNextSibling());
            }
            else
            {
                CompositeNode composite = (CompositeNode)prevSibling;
                composite.insertAfter(sourceNode.getFirstChild(), null, insertAfter);

                // Move annotations that are located between merged nodes.
                if (sourceNode.getNodeLevel() != NodeLevel.INLINE)
                    composite.insertAfter(sourceNode.getPreviousNonAnnotationSibling().getNextSibling(),
                        sourceNode, insertAfter);
            }

            sourceNode.remove();
        }
    }

    private void joinToNextSibling()
    {
        // In mNodesToJoin we have the last paragraph or the last section, then last paragraph.
        // They will be joined with their previous siblings.
        for (CompositeNode joinNode : mNodesToJoin)
        {
            Node previousSibling = joinNode.getNodeLevel() != NodeLevel.INLINE
                ? joinNode.getPreviousNonAnnotationSibling()
                : joinNode.getPreviousSibling();

            if (joinNode.getParentNode() == null || previousSibling == null)
                continue;

            // Not sure that we need to join with tables.
            if ((joinNode.getNodeType() == NodeType.TABLE) || (previousSibling.getNodeType() == NodeType.TABLE))
                continue;

            // Do not join paragraph with StructuredDocumentTag.
            if (joinNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                continue;

            // WORDSNET-25105 Do not join OfficeMath.
            if (joinNode.getNodeType() == NodeType.OFFICE_MATH)
                continue;

            // RESILIENCY WORDSNET-4691 The problem occurred because bookmark end is in SmartTag,
            // in this case previous sibling might be inline (as in the test case). To fix we make sure
            // the previous sibling is composite, if not join content of source node to the parent of previous sibling.

            if (!previousSibling.isComposite())
                previousSibling = previousSibling.getParentNode();

            if (joinNode.getNodeType() == NodeType.SECTION)
                joinSectionToNextSibling((Section)previousSibling, (Section)joinNode);
            else
                joinNodeToNextSibling((CompositeNode)previousSibling, joinNode);
        }
    }

    private static void joinSectionToNextSibling(Section removingSection, Section nextSection)
    {
        // We join section bodies, headers and footers. And then remove the source section with all its stories.
        // I can't see another scenario.

        Body sourceBody = removingSection.getBody();
        Body destinationBody = nextSection.getBody();

        destinationBody.insertBefore(sourceBody.getFirstChild(), null, destinationBody.getFirstChild());
        joinSectionHeaderFootersToNextSibling(removingSection, nextSection);

        removingSection.remove();
    }

    private static void joinNodeToNextSibling(CompositeNode removingNode, CompositeNode nextNode)
    {
        // Move annotations that are located between the merged nodes.
        if (nextNode.getNodeLevel() != NodeLevel.INLINE)
            nextNode.insertBefore(removingNode.getNextSibling(), nextNode, nextNode.getFirstChild());

        // Do not join a paragraph with a SmartTag. (Fix for TestBookmarksRunner.UnifiedTestDefect4691.)
        if ((nextNode.getNodeType() == NodeType.SMART_TAG) && (removingNode == nextNode.getParentNode()))
        {
            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(nextNode.getDocument());
            try /*JAVA: was using*/
            {
                removingNode.insertBefore(nextNode.getFirstChild(), null, nextNode);
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

            nextNode.remove();
        }
        else
        {
            moveChildrenAndRemoveNode(removingNode, nextNode);
        }
    }

    private static void moveChildrenAndRemoveNode(CompositeNode node, CompositeNode childrenDestination)
    {
        Document document = node.fetchDocument();
        boolean useDeleteRevision = document.isTrackRevisionsEnabled() && (node instanceof ITrackableNode);
        if (!useDeleteRevision)
        {
            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(document);
            try /*JAVA: was using*/
            {
                childrenDestination.insertBefore(node.getFirstChild(), null, childrenDestination.getFirstChild());
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

            node.remove();
        }
        else
        {
            RevisionTrackingUtil.addDeleteRevision((ITrackableNode)node, document.getEditSession());
        }
    }

    private static void joinSectionHeaderFootersToNextSibling(Section source, Section destination)
    {
        HeaderFooter[] sourceheaderFooters = source.getHeadersFooters().toArray();
        for (HeaderFooter sourceHeaderFooter : sourceheaderFooters)
        {
            HeaderFooter destinationHeaderFooter = destination.getHeadersFooters().getByHeaderFooterType(sourceHeaderFooter.getHeaderFooterType());
            if (destinationHeaderFooter != null && !destinationHeaderFooter.isLinkedToPrevious())
                continue;

            if (destinationHeaderFooter != null)
                destinationHeaderFooter.remove();

            destination.getHeadersFooters().add(sourceHeaderFooter);
        }
    }

    protected void lockRemove()
    {
        mIsRemoveLocked = true;
    }

    protected void unlockRemove()
    {
        mIsRemoveLocked = false;
    }

    protected /*override*/ void onMiddleNodeAncestor()
    {
        if (mIsRemoveLocked)
            return;

        // Middle parent nodes are removed except if start bound is expected to be a child of it.
        if (isLogicalAncestorOfStartNode(getCurrentNode()))
        {
            CompositeNode deepestComposite = (CompositeNode)getCurrentNode();
            while (true)
            {
                Node node = deepestComposite.getFirstNonAnnotationChild();
                if ((node != null) && node.isComposite() && (node.getNodeLevel() != NodeLevel.INLINE))
                    deepestComposite = (CompositeNode)node;
                else
                    break;

                // Remove non-first children.
                node = node.getNextSibling();
                while (node != null)
                {
                    addNodeToRemove(node);
                    node = node.getNextSibling();
                }
            }

            for (Node node : (Iterable<Node>) deepestComposite)
                addNodeToRemove(node);
        }
        else
        {
            addNodeToRemove(getCurrentNode());
        }
    }

    /// <summary>
    /// Adds node to the collection of nodes pending removal.
    /// </summary>
    protected /*virtual*/ void addNodeToRemove(Node node)
    {
        msArrayList.add(mNodesToRemove, node);
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node is root ancestor of the block range start cross-structure
    /// annotation that is logically expected to be at inline level as descendant of it.
    /// </summary>
    private boolean isLogicalAncestorOfStartNode(Node node)
    {
        Node startNode = getRange().getStart().getNode();
        return mProcessBoundBlockAnnotationAsInline &&
            NodeUtil.isCrossStructureAnnotation(startNode) &&
            (startNode.getNextNonAnnotationSibling() == node) &&
            (startNode.getNodeLevel() != NodeLevel.INLINE);
    }

    protected /*override*/ void onEndNodeAncestor()
    {
        // Last parent node is joined except if start bound is expected to be a child of it.
        // Last section is joined with its previous sibling (more precisely, their bodies are joined),
        // last paragraph is joined with its previous sibling.
        if (!isLogicalAncestorOfStartNode(getCurrentNode()))
            msArrayList.add(mNodesToJoin, (CompositeNode)getCurrentNode());
    }

    protected /*override*/ void onNonCompositeNode()
    {
        if (mIsRemoveLocked)
            return;

        // Child nodes are always removed.
        addNodeToRemove(getCurrentNode());
    }

    /// <summary>
    /// Used to collect nodes to remove in <see cref="OnMiddleNodeAncestor"/>, <see cref="OnEndNodeAncestor"/>
    /// and <see cref="OnNonCompositeNode"/> methods, overridden in inherited classes.
    /// </summary>
    protected ArrayList<Node> getNodesToRemove() { return mNodesToRemove; }

    private /*final*/ /*NodeJoinMode*/int mJoinMode;
    private /*final*/ ArrayList<Node> mNodesToRemove = new ArrayList<Node>();
    private /*final*/ ArrayList<CompositeNode> mNodesToJoin = new ArrayList<CompositeNode>();
    /// <summary>
    /// If <c>true</c>, when the range has block level annotation nodes at bounds, removal gives same result
    /// as when the annotation nodes are moved to inline level.
    /// </summary>
    private /*final*/ boolean mProcessBoundBlockAnnotationAsInline;

    private boolean mIsRemoveLocked;
}

