// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;


/// <summary>
/// This class is used on accepting/rejection revisions to store necessary data.
/// </summary>
class RevisionHandlingContext
{
    RevisionHandlingContext(ArrayList<Revision> allRevisions, boolean isAcceptance)
    {
        mAllRevisions = allRevisions;
        mIsSingleRevision = false;
        mIsAcceptance = isAcceptance;
    }

    RevisionHandlingContext(boolean isAcceptance, /*RevisionType*/int revisionType)
    {
        mIsSingleRevision = true;
        mIsAcceptance = isAcceptance;
        mRevisionType = revisionType;
    }

    /// <summary>
    /// Clones this instance of the class.
    /// </summary>
    RevisionHandlingContext deepClone()
    {
        RevisionHandlingContext lhs = new RevisionHandlingContext(getAllRevisions(), isAcceptance());

        msArrayList.addRange(lhs.mDelayedInlines, mDelayedInlines);
        msArrayList.addRange(lhs.mDelayedParagraphs, mDelayedParagraphs);
        msArrayList.addRange(lhs.mDelayedCells, mDelayedCells);
        msArrayList.addRange(lhs.mDelayedRows, mDelayedRows);
        msArrayList.addRange(lhs.mDelayedTables, mDelayedTables);
        msArrayList.addRange(lhs.mDelayedSdts, mDelayedSdts);

        for (Map.Entry<StructuredDocumentTag, RevisionHandlingContext.ParentSdtInfo> pair : mSdtNesting.entrySet())
            msDictionary.add(lhs.mSdtNesting, pair.getKey(), pair.getValue());
        msArrayList.addRange(lhs.mInlinedSdts, mInlinedSdts);

        return lhs;
    }

    /// <summary>
    /// Adds the node to a corresponding delayed deletion list.
    /// </summary>
    void addDelayedNode(Node node)
    {
        // Check whether the node has already been added.
        if (isDeletingNode(node))
            return;

        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
                msArrayList.add(mDelayedParagraphs, node);
                break;
            case NodeType.CELL:
                msArrayList.add(mDelayedCells, node);
                break;
            case NodeType.ROW:
                msArrayList.add(mDelayedRows, node);
                break;
            case NodeType.TABLE:
                msArrayList.add(mDelayedTables, node);
                break;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                msArrayList.add(mDelayedSdts, node);
                break;
            default:
                if (node instanceof IInline)
                    msArrayList.add(mDelayedInlines, node);
                else
                    throw new IllegalStateException("Wrong node type.");
                break;
        }
    }

    /// <summary>
    /// Checks whether the node is in a delayed deletion list.
    /// </summary>
    boolean isDeletingNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
                return mDelayedParagraphs.contains(node);
            case NodeType.CELL:
                return mDelayedCells.contains(node);
            case NodeType.ROW:
                return mDelayedRows.contains(node);
            case NodeType.TABLE:
                return mDelayedTables.contains(node);
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                return mDelayedSdts.contains(node);
            default:
                return (node instanceof IInline) && mDelayedInlines.contains(node);
        }
    }

    /// <summary>
    /// Stores SDT nodes that may become nested if will be empty after accepting/rejecting revisions.
    /// MS Word has strange behaviour of making SDTs nested in some cases.
    /// </summary>
    void addSdtNesting(StructuredDocumentTag parentSdt, StructuredDocumentTag childSdt,
        boolean canNestInlineSdts)
    {
        if (mSdtNesting.containsKey(childSdt))
            return;

        msDictionary.add(mSdtNesting, childSdt, new ParentSdtInfo(parentSdt, canNestInlineSdts));
    }

    /// <summary>
    /// Gets a flag indicating whether the specified parent SDT intended as destination to move nested SDTs
    /// can nest inline level SDTs.
    /// </summary>
    /// <seealso cref="ParentsForNestedSdts"/>
    /// <seealso cref="GetMovingNestedSdts"/>
    boolean canNestInlineSdts(StructuredDocumentTag parentSdt)
    {
        for (ParentSdtInfo info : (Iterable<ParentSdtInfo>) mSdtNesting.values())
        {
            if (info.getParentSdt() == parentSdt)
                return info.canNestInlineSdts();
        }

        return false;
    }

    /// <summary>
    /// Returns list of nodes that are intended to be moved into the specified SDT node during handling revisions.
    /// SDTs may become nested on accepting revisions.
    /// </summary>
    ArrayList<StructuredDocumentTag> getMovingNestedSdts(StructuredDocumentTag parentSdt)
    {
        ArrayList<StructuredDocumentTag> list = new ArrayList<StructuredDocumentTag>();
        for (Map.Entry<StructuredDocumentTag, RevisionHandlingContext.ParentSdtInfo> pair : mSdtNesting.entrySet())
        {
            // Skip nodes that are already nested.
            if ((pair.getValue().getParentSdt() == parentSdt) && !pair.getKey().isAncestorNode(parentSdt))
                msArrayList.add(list, pair.getKey());
        }

        NodeSorter.sort(list);

        return list;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified node has children that are not marked for deletion.
    /// </summary>
    boolean hasPreservedTrackables(Node node, boolean skipMarkups)
    {
        if (!node.isComposite())
            return false;

        Node currentNode = ((CompositeNode)node).getFirstChild();

        while (currentNode != null)
        {
            if (!(NodeUtil.isMarkupNode(currentNode) && skipMarkups) &&
                (currentNode instanceof ITrackableNode) &&
                !isDeletingNode(currentNode))
                return true;

            currentNode = currentNode.nextPreOrder(node);
        }

        return false;
    }

    /// <summary>
    /// Gets/sets currently processing node.
    /// </summary>
    Node getCurrentNode()
    {
        return mCurrentNode;
    }
    void setCurrentNode(Node value)
    {
        mCurrentNode = value;

        // WORDSNET-18519 Comment node can contain move revision.
        IMoveTrackableNode trackable = ms.as(value, IMoveTrackableNode.class);
        setCurrentMoveRange(((trackable != null) && ((trackable.getMoveFromRevision() != null) || (trackable.getMoveToRevision() != null)))
            ? MoveRangeFinder.findMoveRange(value, trackable.getMoveToRevision() != null)
            : null);
    }

    /// <summary>
    /// Returns move range of the current node if it has move-from or move-to revision.
    /// </summary>
    MoveRange getCurrentMoveRange() { return mCurrentMoveRange; }; private void setCurrentMoveRange(MoveRange value) { mCurrentMoveRange = value; };

    private MoveRange mCurrentMoveRange;

    /// <summary>
    /// List that stores deleted inlines.
    /// </summary>
    ArrayList<Node> getDelayedInlines() { return mDelayedInlines; }

    /// <summary>
    /// List that stores deleted paragraphs.
    /// </summary>
    ArrayList<Node> getDelayedParagraphs() { return mDelayedParagraphs; }

    /// <summary>
    /// List that stores deleted rows.
    /// </summary>
    ArrayList<Node> getDelayedRows() { return mDelayedRows; }

    /// <summary>
    /// List that stores deleted cells.
    /// </summary>
    ArrayList<Node> getDelayedCells() { return mDelayedCells; }

    /// <summary>
    /// List that stores deleted tables.
    /// </summary>
    ArrayList<Node> getDelayedTables() { return mDelayedTables; }

    /// <summary>
    /// List that stores deleted structured document tags.
    /// </summary>
    ArrayList<Node> getDelayedSdts() { return mDelayedSdts; }

    /// <summary>
    /// Stores structured document tags that have become inline-level during handling revisions.
    /// </summary>
    ArrayList<StructuredDocumentTag> getInlinedSdts() { return mInlinedSdts; }

    /// <summary>
    /// Returns SDTs that are intended as destination for moving other SDTs during handling revisions.
    /// </summary>
    ArrayList<StructuredDocumentTag> getParentsForNestedSdts()
    {
        ArrayList<StructuredDocumentTag> list = new ArrayList<StructuredDocumentTag>();
        for (ParentSdtInfo info : (Iterable<ParentSdtInfo>) mSdtNesting.values())
        {
            if (!list.contains(info.getParentSdt()))
                msArrayList.add(list, info.getParentSdt());
        }

        return list;
    }

    /// <summary>
    /// Returns all revisions being accepted/rejected. Can be null on handling a single revision.
    /// </summary>
    ArrayList<Revision> getAllRevisions() { return mAllRevisions; };

    private ArrayList<Revision> mAllRevisions;

    /// <summary>
    /// Returns <c>true</c> if acceptance/rejection of a single revision is being performed.
    /// </summary>
    boolean isSingleRevision() { return mIsSingleRevision; };

    private boolean mIsSingleRevision;

    /// <summary>
    /// Indicates whether acceptance (<c>true</c>) or rejection (<c>false</c>) of revisions is being performed.
    /// </summary>
    boolean isAcceptance() { return mIsAcceptance; };

    private boolean mIsAcceptance;

    /// <summary>
    /// Specifies <see cref="Aspose.Words.RevisionType"/> of a single handling revision.
    /// </summary>
    /*RevisionType*/int getRevisionType() { return mRevisionType; };

    private /*RevisionType*/int mRevisionType;

    private /*final*/ ArrayList<Node> mDelayedInlines = new ArrayList<Node>();
    private /*final*/ ArrayList<Node> mDelayedParagraphs = new ArrayList<Node>();
    private /*final*/ ArrayList<Node> mDelayedRows = new ArrayList<Node>();
    private /*final*/ ArrayList<Node> mDelayedCells = new ArrayList<Node>();
    private /*final*/ ArrayList<Node> mDelayedTables = new ArrayList<Node>();
    private /*final*/ ArrayList<Node> mDelayedSdts = new ArrayList<Node>();

    /// <summary>
    /// Stores possible nesting SDTs. Key: child SDT, value: parent SDT info.
    /// </summary>
    private /*final*/ HashMap<StructuredDocumentTag, RevisionHandlingContext.ParentSdtInfo> mSdtNesting =
        new HashMap<StructuredDocumentTag, RevisionHandlingContext.ParentSdtInfo>();
    private /*final*/ ArrayList<StructuredDocumentTag> mInlinedSdts = new ArrayList<StructuredDocumentTag>();

    private Node mCurrentNode;

    /// <summary>
    /// Sort nodes by their location in a document.
    /// </summary>
    private final static class NodeSorter implements Comparator<StructuredDocumentTag>
    {
        static void sort(ArrayList<StructuredDocumentTag> nodeList)
        {
            Collections.sort(nodeList, new NodeSorter());
        }

        /// <summary>
        /// Compares nodes by their location in a document.
        /// </summary>
        public int /*IComparer<StructuredDocumentTag>.*/compare(StructuredDocumentTag node1, StructuredDocumentTag node2)
        {
            if (node1 == node2)
                return 0;

            return node1.isAbove(node2) ? -1 : 1;
        }
    }

    private static class ParentSdtInfo
    {
        ParentSdtInfo(StructuredDocumentTag parentSdt, boolean canNestInlineSdts)
        {
            mParentSdt = parentSdt;
            mCanNestInlineSdts = canNestInlineSdts;
        }

        StructuredDocumentTag getParentSdt() { return mParentSdt; };

        private StructuredDocumentTag mParentSdt;

        boolean canNestInlineSdts() { return mCanNestInlineSdts; };

        private boolean mCanNestInlineSdts;
    }
}

