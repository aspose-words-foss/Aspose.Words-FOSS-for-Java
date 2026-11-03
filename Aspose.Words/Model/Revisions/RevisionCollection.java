// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.NoSuchElementException;
import com.aspose.ArgumentUtil;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Collection;
import java.util.Iterator;
import com.aspose.EnumeratorWrapperPalGeneric;


/// <summary>
/// A collection of <see cref="Revision"/> objects that represent revisions in the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/track-changes-in-a-document/">Track Changes in a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class directly. Use the <see cref="Document.Revisions"/> property to get revisions present in a document.</para>
/// </remarks>
public class RevisionCollection implements Iterable<Revision>
{
    RevisionCollection(Node node)
    {
        if (node.getNodeType() != NodeType.DOCUMENT)
        {
            mDocument = node.getDocument();
            mParentNode = node;
        }
        else
        {
            mDocument = (DocumentBase)node;
        }

        collectRevisions();
    }

    RevisionCollection(NodeRange nodeRange)
    {
        mDocument = nodeRange.getDocument();
        mNodeRange = nodeRange;
        collectRevisions();
    }

    /// <summary>
    /// Accepts all revisions in this collection.
    /// </summary>
    public void acceptAll() throws Exception
    {
        accept(DefaultRevisionCriteria.INSTANCE);
    }

    /// <summary>
    /// Rejects all revisions in this collection.
    /// </summary>
    public void rejectAll() throws Exception
    {
        reject(DefaultRevisionCriteria.INSTANCE);
    }

    /// <summary>
    /// Accepts revisions that match specified criteria.
    /// </summary>
    /// <param name="criteria">
    /// The <see cref="IRevisionCriteria"/> implementation.
    /// </param>
    /// <returns>
    /// The count of accepted revisions.
    /// </returns>
    public int accept(IRevisionCriteria criteria) throws Exception
    {
        ArgumentUtil.checkNotNull(criteria, "filter");
        return handleRevisions(criteria, true);
    }

    /// <summary>
    /// Rejects revisions that match specified criteria.
    /// </summary>
    /// <param name="criteria">
    /// The <see cref="IRevisionCriteria"/> implementation.
    /// </param>
    /// <returns>
    /// The count of rejected revisions.
    /// </returns>
    public int reject(IRevisionCriteria criteria) throws Exception
    {
        ArgumentUtil.checkNotNull(criteria, "filter");
        return handleRevisions(criteria, false);
    }

    private int handleRevisions(IRevisionCriteria criteria, boolean isAcceptance) throws Exception
    {
        // Nodes should be actually inserted/deleted here so suspend tracking during this operation.
        try
        {
            SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDocument);
            try /*JAVA: was using*/
            {
                // WORDSNET-8209 Use List of revisions to prevent changes of collection in the loop.
                ArrayList<Revision> revisionsToHandle = toList(criteria);

                // Accumulates runs, paragraphs, rows, cells to delete since we cannot delete them
                // while visiting document nodes.
                RevisionHandlingContext context = new RevisionHandlingContext(revisionsToHandle, isAcceptance);

                for (Revision revision : revisionsToHandle)
                    revision.handleRevisions(false, context);

                BookmarkDeleter.deleteBookmarksFromDeletingNodes(mDocument, context);

                SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mDocument);
                try /*JAVA: was using*/
                {
                    RevisionUtil.processDelayedNodes(context, mDocument);
                }
                finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
                invalidateAll();

                return revisionsToHandle.size();
            }
            finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
        }
        finally
        {
            setChangeCount(getChangeCount() + 1)/*Property++*/;
        }
    }

    /// <summary>
    /// Returns the number of revisions in the collection.
    /// </summary>
    public int getCount()
    {
        checkInvalidate();
        return mNodeRevisions.size() + mStyleRevisions.size();
    }

    /// <summary>
    /// Returns the number of empty revisions in the collection.
    /// If the document has changed since last time the collection was accessed then refreshing of the collection
    /// is not implementing.
    /// Used for testing purposes only.
    /// </summary>
    int getEmptyFormatRevisionsCount() { return mEmptyFormatRevisions.size(); }

    /// <summary>
    /// Returns a <see cref="Revision"/> at the specified index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public Revision get(int index)
    {
        checkInvalidate();

        if (index < mNodeRevisions.size())
            return mNodeRevisions.get(index);

        int styleIndex = index - mNodeRevisions.size();
        if (styleIndex < mStyleRevisions.size())
            return mStyleRevisions.get(styleIndex);

        throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "index");
    }

    /// <summary>
    /// Invalidates collection of nodes after accept/reject procedures. Used to keep live state of revision collection.
    /// </summary>
    void invalidate(Revision revision)
    {
        Debug.assertTrue(revision != null);

        if (revision.getRevisionType() != RevisionType.STYLE_DEFINITION_CHANGE)
            mNodeRevisions.remove(revision);
        else
            mStyleRevisions.remove(revision);

        setChangeCount(getChangeCount() + 1)/*Property++*/;
    }

    private void invalidateAll()
    {
        mNodeRevisions.clear();
        mStyleRevisions.clear();
        mEmptyFormatRevisions.clear();

        mRevisionGroups = null;

        setChangeCount(getChangeCount() + 1)/*Property++*/;
    }

    /// <summary>
    /// Tries to find revision with given Istd from input style and removes style this revision from the collection if found.
    /// </summary>
    void remove(Style style)
    {
        Revision foundStyleRevision = null;
        for (Revision styleRevision : mStyleRevisions)
        {
            if (styleRevision.getParentStyle().getIstd() == style.getIstd())
            {
                foundStyleRevision = styleRevision;
                break;
            }
        }

        if (foundStyleRevision != null)
        {
            mStyleRevisions.remove(foundStyleRevision);
            setChangeCount(getChangeCount() + 1)/*Property++*/;
        }
    }

    /// <summary>
    /// Adds all revisions applied to style to the style revision array.
    /// </summary>
    void addStyleRevisions(Style style)
    {
        if (style.getRunPr().hasFormatRevision())
            addStyleRevision(mStyleRevisions, style.getRunPr().getFormatRevision(), style, true);

        if (style.getParaPr().hasFormatRevision())
            addStyleRevision(mStyleRevisions, style.getParaPr().getFormatRevision(), style, true);
    }

    /// <summary>
    /// Loops through all revision groups and finds thd revision group by revision.
    /// </summary>
    /// <returns>
    /// Returns the revision group or null if the revision group is not found.
    /// See also <see cref="Revision.Group"/>.
    /// </returns>
    RevisionGroup getGroupByRevision(Revision revision)
    {
        Node revisionNode = revision.getParentNode();
        // WORDSNET-24018 There are no separate groups for cells/rows in Word, but there is a group of table revisions instead.
        if (revisionNode.getNodeType() == NodeType.ROW)
            revisionNode = ((Row)revisionNode).getParentTable();
        if (revisionNode.getNodeType() == NodeType.CELL)
            revisionNode = ((Cell)revisionNode).getParentTable();

        for (RevisionGroup group : getGroups())
            if ((group.getRevisionType() == revision.getRevisionType()) && group.getNodes().contains(revisionNode))
                return group;

        return null;
    }

    void invalidateGroups()
    {
        mRevisionGroups = null;
    }

    /// <summary>
    /// Incremented each time the collection is changed.
    /// Used as a version to invalidate RevisionIterator objects built for the collections.
    /// </summary>
    int getChangeCount() { return mChangeCount; }; private void setChangeCount(int value) { mChangeCount = value; };

    private int mChangeCount;

    /// <summary>
    /// Collection of revision groups.
    /// </summary>
    public RevisionGroupCollection getGroups() { return (mRevisionGroups != null ? mRevisionGroups : (mRevisionGroups = new RevisionGroupCollection(mDocument))); }

    /// <summary>
    /// Loop through all nodes and styles with revisions and create real collection of revisions.
    /// </summary>
    private void collectRevisions()
    {
        mNodeRevisions.clear();
        mStyleRevisions.clear();
        mEmptyFormatRevisions.clear();

        mDocumentChangeCount = getDocumentChangeCount();
        mRevisionNodeMatcher.setParentNode(mParentNode);

        ArrayList<Node> nodesWithRevisions;
        CompositeNode parent;

        if (mNodeRange != null)
        {
            if (mNodeRange.isEmpty() || mNodeRange.isVoid() ||
                mNodeRange.getStart().getNode().isRemoved() || mNodeRange.getEnd().getNode().isRemoved())
                return;

            nodesWithRevisions = new ArrayList<Node>();
            for (Node node : mNodeRange)
                if (RevisionUtil.hasRevision(node))
                    msArrayList.add(nodesWithRevisions, node);

            parent = null;
        }
        else if (mParentNode == null)
        {
            nodesWithRevisions = new NodeCollection(mDocument, mRevisionNodeMatcher, true).toNodeList();
            parent = mDocument;
        }
        else
        {
            parent = ms.as(mParentNode, CompositeNode.class);
            nodesWithRevisions = (parent != null)
                ? new NodeCollection(parent, mRevisionNodeMatcher, true).toNodeList()
                : new ArrayList<Node>();

            msArrayList.add(nodesWithRevisions, mParentNode);
        }

        if (parent != null)
        {
            // Get revisions from fallback shapes.
            for (ShapeBase shape : (Iterable<ShapeBase>) parent.getChildNodes(NodeType.SHAPE, true))
            {
                if (shape.getFallbackShape() != null)
                {
                    NodeCollection nodes = new NodeCollection(shape.getFallbackShape(), mRevisionNodeMatcher, true);
                    msArrayList.addRange(nodesWithRevisions, nodes.toNodeList());
                }
            }
        }

        // andrnosk: WORDSNET-9611 A node can have more then one revision,
        // that is why we have to go through all nodes with revisions and create real collection of revisions.
        for (Node node : nodesWithRevisions)
        {
            WordAttrCollection formatting = RevisionUtil.getNodeFormatting(node);
            if (formatting != null)
                addNodeRevisions(formatting, node);

            // Paragraph and SDT have extra formatting that is checked for revisions separately.
            Paragraph paragraph = ms.as(node, Paragraph.class);
            if (paragraph != null)
                addNodeRevisions(paragraph.getParagraphBreakRunPr(), paragraph);

            StructuredDocumentTag sdt = ms.as(node, StructuredDocumentTag.class);
            if (sdt != null)
                addNodeRevisions(sdt.getEndCharacterRunPr(), sdt);
        }

        for (Style style : mDocument.getStyles())
        {
            if (style.hasRevisions())
                addStyleRevisions(style);
            if (style.hasEmptyFormatRevision())
                addEmptyStyleFormatRevision(style);
        }

        setChangeCount(getChangeCount() + 1)/*Property++*/;
    }

    private int getDocumentChangeCount()
    {
        // mDocument is null in case when mContainer is EmptyCompositeNode so let's handle this properly.
        return mDocument != null
            ? mDocument.getTreeChangeCount()
            : 0;
    }

    /// <summary>
    /// Invalidates a collection if the document has changed since last time the collection was accessed.
    /// </summary>
    private void checkInvalidate()
    {
        if (mDocumentChangeCount != getDocumentChangeCount())
            collectRevisions();
    }

    /// <summary>
    /// Creates and adds new Revision for style to the empty format revision array.
    /// </summary>
    /// <param name="style">Style with empty revision.</param>
    private void addEmptyStyleFormatRevision(Style style)
    {
        if (style.getRunPr().hasEmptyFormatRevision())
            addStyleRevision(mEmptyFormatRevisions, style.getRunPr().getFormatRevision(), style, false);
        if (style.getParaPr().hasEmptyFormatRevision())
            addStyleRevision(mEmptyFormatRevisions, style.getParaPr().getFormatRevision(), style, false);
    }

    /// <summary>
    /// Adds all revisions applied to node to the node revision array.
    /// </summary>
    private void addNodeRevisions(WordAttrCollection attrCollection, Node node)
    {
        if (attrCollection.hasInsertRevision())
            addNodeRevision(mNodeRevisions, RevisionType.INSERTION, attrCollection.getInsertRevision(), node, true);
        if (attrCollection.hasDeleteRevision())
            addNodeRevision(mNodeRevisions, RevisionType.DELETION, attrCollection.getDeleteRevision(), node, true);
        if (attrCollection.hasFormatRevision())
            addNodeRevision(mNodeRevisions, RevisionType.FORMAT_CHANGE, attrCollection.getFormatRevision(), node, true);
        if (attrCollection.hasMoveFromRevision())
            addNodeRevision(mNodeRevisions, RevisionType.MOVING, attrCollection.getMoveFromRevision(), node, true);
        if (attrCollection.hasMoveToRevision())
            addNodeRevision(mNodeRevisions, RevisionType.MOVING, attrCollection.getMoveToRevision(), node, true);

        if (attrCollection.hasEmptyFormatRevision())
            addNodeRevision(mEmptyFormatRevisions, RevisionType.FORMAT_CHANGE, attrCollection.getFormatRevision(), node, false);

        ParaPr paraPr = ms.as(attrCollection, ParaPr.class);
        if ((paraPr != null) && paraPr.hasNumberRevision())
            addNodeRevision(mNodeRevisions, RevisionType.FORMAT_CHANGE, paraPr.getNumberRevision(), node, true);

        RunPr runPr = ms.as(attrCollection, RunPr.class);
        if ((runPr != null) && runPr.hasNumberRevision())
            addNodeRevision(mNodeRevisions, RevisionType.FORMAT_CHANGE, runPr.getNumberRevision(), node, true);
    }

    /// <summary>
    /// Creates and adds new Revision to the node revision array.
    /// </summary>
    private void addNodeRevision(ArrayList<Revision> revArr, /*RevisionType*/int type, RevisionBase revision, Node nodeWithRevisions, boolean incChangeCount)
    {
        revArr.add(new Revision(type, revision, nodeWithRevisions, this));
        incrementChangeCount(incChangeCount);
    }

    /// <summary>
    /// Creates and adds new Revision to the style revision array.
    /// </summary>
    private void addStyleRevision(ArrayList<Revision> revArr, RevisionBase revision, Style styleWithRevisions, boolean incChangeCount)
    {
        revArr.add(new Revision(RevisionType.STYLE_DEFINITION_CHANGE, revision, styleWithRevisions, this));
        incrementChangeCount(incChangeCount);
    }

    /// <summary>
    /// Check incoming flag and increment changes count if it is set.
    /// </summary>
    /// <param name="incChangeCount">When value is "True" changes count is incremented.</param>
    private void incrementChangeCount(boolean incChangeCount)
    {
        if (incChangeCount)
            setChangeCount(getChangeCount() + 1)/*++Property*/;
    }

    /// <summary>
    /// Creates IList of revisions from this RevisionCollection.
    /// </summary>
    ArrayList<Revision> toList() throws Exception
    {
        return toList(DefaultRevisionCriteria.INSTANCE);
    }

    private ArrayList<Revision> toList(IRevisionCriteria criteria) throws Exception
    {
        ArrayList<Revision> result = new ArrayList<Revision>(getCount() + mEmptyFormatRevisions.size());

        for (Revision revision : this)
            collectRevision(result, revision, criteria);

        for (Revision revision : mEmptyFormatRevisions)
            collectRevision(result, revision, criteria);

        return result;
    }

    private static void collectRevision(Collection<Revision> revisions, Revision revision, IRevisionCriteria criteria) throws Exception
    {
        if (!criteria.isMatch(revision))
            return;

        revisions.add(revision);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<Revision> iterator()
    {
        return new RevisionIterator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private int mDocumentChangeCount;
    private /*final*/ DocumentBase mDocument;
    private /*final*/ Node mParentNode;
    private /*final*/ NodeRange mNodeRange;
    private /*final*/ ArrayList<Revision> mNodeRevisions = new ArrayList<Revision>();
    private /*final*/ ArrayList<Revision> mEmptyFormatRevisions = new ArrayList<Revision>();
    private /*final*/ ArrayList<Revision> mStyleRevisions = new ArrayList<Revision>();
    private /*final*/ RevisionNodeMatcher mRevisionNodeMatcher = new RevisionNodeMatcher();
    private RevisionGroupCollection mRevisionGroups;

    private final static class RevisionIterator implements Iterator<Revision>
    {
        RevisionIterator(RevisionCollection revisions)
        {
            revisions.checkInvalidate();
            mCollection = revisions;
            mCollectionChangeCount = revisions.getChangeCount();
            mNodeRevisions = revisions.mNodeRevisions;
            mStyleRevisions = new EnumeratorWrapperPalGeneric<Revision>(revisions.mStyleRevisions.iterator());
            reset();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public Revision next()
        {
            if (mCollectionChangeCount != mCollection.getChangeCount())
                throw new IllegalStateException(EXCEPTION_MESSAGE);

            return mCurNode;
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public boolean hasNext()
        {
            if (mCollectionChangeCount != mCollection.getChangeCount())
                throw new IllegalStateException(EXCEPTION_MESSAGE);

            boolean result;
            if (mIndex < mNodeRevisions.size())
            {
                mCurNode = mNodeRevisions.get(mIndex);
                mIndex++;
                result = true;
            }
            else if (mStyleRevisions.moveNext())
            {
                mCurNode = mStyleRevisions.getCurrent();
                result = true;
            }
            else
            {
                mCurNode = null;
                result = false;
            }
            return result;
        }

        public void reset()
        {
            mIndex = 0;
            mCurNode = null;
            mCollectionChangeCount = mCollection.getChangeCount();
        }

        private /*final*/ RevisionCollection mCollection;
        private int mCollectionChangeCount;
        private /*final*/ ArrayList<Revision> mNodeRevisions;
        private /*final*/ EnumeratorWrapperPalGeneric<Revision> mStyleRevisions;
        private int mIndex;
        private Revision mCurNode;

        private static final String EXCEPTION_MESSAGE = "RevisionCollection was modified by Accept/Reject operation; enumeration operation may not execute.";

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private static class DefaultRevisionCriteria implements IRevisionCriteria
    {
        private DefaultRevisionCriteria()
        {
        }

        public boolean /*IRevisionCriteria.*/isMatch(Revision revision)
        {
            return true;
        }

        static /*final*/ IRevisionCriteria INSTANCE = new DefaultRevisionCriteria();
    }
}

