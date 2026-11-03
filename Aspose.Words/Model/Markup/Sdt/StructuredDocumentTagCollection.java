// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/03/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.NoSuchElementException;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// A collection of <see cref="IStructuredDocumentTag"/> instances that represent the structured document tags in the specified range.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
public class StructuredDocumentTagCollection implements Iterable<IStructuredDocumentTag>
{
    StructuredDocumentTagCollection(Node parent)
    {
        if (parent.isComposite())
            mStructuredDocumentTags = ((CompositeNode)parent).getChildNodes(new /*NodeType*/int[]
                { NodeType.STRUCTURED_DOCUMENT_TAG, NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START }, true);
        else
            mStructuredDocumentTags = EmptyNodeCollection.createEmpty();
    }

    StructuredDocumentTagCollection(NodeRange nodeRange)
    {
        mStructuredDocumentTags = new NodeCollection(nodeRange.getDocument(), new StructuredDocumentTagMatcher(nodeRange), true);
    }

    /// <summary>
    /// Returns the structured document tag by identifier.
    /// </summary>
    /// <remarks>
    /// <p>Returns null if the structured document tag with the specified identifier cannot be found.</p>
    /// </remarks>
    /// <param name="id">The structured document tag identifier.</param>
    public IStructuredDocumentTag getById(int id)
    {
        for (Node node : (Iterable<Node>) mStructuredDocumentTags)
        {
            IStructuredDocumentTag sdt = ms.as(node, IStructuredDocumentTag.class);
            if ((sdt != null) && (sdt.getId() == id))
                return sdt;
        }
        return null;
    }

    /// <summary>
    /// Returns the first structured document tag encountered in the collection with the specified title.
    /// </summary>
    /// <remarks>
    /// <p>Returns null if the structured document tag with the specified title cannot be found.</p>
    /// </remarks>
    /// <param name="title">The title of structured document tag.</param>
    public IStructuredDocumentTag getByTitle(String title)
    {
        ArgumentUtil.checkNotNull(title, "title");

        for (Node node : (Iterable<Node>) mStructuredDocumentTags)
        {
            IStructuredDocumentTag sdt = ms.as(node, IStructuredDocumentTag.class);
            if ((sdt != null) && (msString.equals(sdt.getTitle(), title, StringComparison.ORDINAL)))
                return sdt;
        }
        return null;
    }

    /// <summary>
    /// Returns the first structured document tag encountered in the collection with the specified tag.
    /// </summary>
    /// <remarks>
    /// <p>Returns null if the structured document tag with the specified tag cannot be found.</p>
    /// </remarks>
    /// <param name="tag">The tag of the structured document tag.</param>
    public IStructuredDocumentTag getByTag(String tag)
    {
        ArgumentUtil.checkNotNull(tag, "tag");

        for (Node node : (Iterable<Node>) mStructuredDocumentTags)
        {
            IStructuredDocumentTag sdt = ms.as(node, IStructuredDocumentTag.class);
            if ((sdt != null) && (msString.equals(sdt.getTag(), tag, StringComparison.ORDINAL)))
                return sdt;
        }
        return null;
    }


    /// <summary>
    /// Removes the structured document tag with the specified identifier.
    /// </summary>
    /// <param name="id">The structured document tag identifier.</param>
    public void remove(int id)
    {
        IStructuredDocumentTag sdt = this.getById(id);
        if (sdt == null)
            throw new IllegalArgumentException("The specified Id is not found.");

        sdt.getNode().remove();
    }

    /// <summary>
    /// Removes a structured document tag at the specified index.
    /// </summary>
    /// <param name="index">An index into the collection.</param>
    public void removeAt(int index)
    {
        mStructuredDocumentTags.removeAt(index);
    }

    /// <summary>
    /// Returns index of structured document tag with the specified identifier.
    /// </summary>
    /// <param name="id">The structured document tag identifier.</param>
    /// <returns>Structured document tag index in this collection. Returns -1 if the structured document tag with the specified Id does not exist in the collection.</returns>
    int indexOf(int id)
    {
        for (int i = 0; i < mStructuredDocumentTags.getCount(); i++)
        {
            if (((IStructuredDocumentTag)mStructuredDocumentTags.get(i)).getId() == id)
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Returns the number of structured document tags in the collection.
    /// </summary>
    public int getCount() { return mStructuredDocumentTags.getCount(); }

    /// <summary>
    /// Returns the structured document tag at the specified index.
    /// </summary>
    /// <param name="index">An index into the collection.</param>
    public IStructuredDocumentTag get(int index)
    {
        return (IStructuredDocumentTag)mStructuredDocumentTags.get(index);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<IStructuredDocumentTag> iterator()
    {
        return new StructuredDocumentTagIterator(mStructuredDocumentTags);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Enumerator through items of <see cref="StructuredDocumentTagCollection"/>.
    /// </summary>
    private final static class StructuredDocumentTagIterator implements Iterator<IStructuredDocumentTag>
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        StructuredDocumentTagIterator(NodeCollection structuredDocumentTags)
        {
            mSdtCollEnumerator = structuredDocumentTags.iterator();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        public IStructuredDocumentTag next()
        {
            return (IStructuredDocumentTag)mSdtCollEnumerator.next();
        }

        /// <summary>
        /// Advances the enumerator to the next element of the collection.
        /// </summary>
        /// <returns></returns>
        public boolean hasNext()
        {
            return mSdtCollEnumerator.hasNext();
        }

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        public void reset()
        {
            ((NodeCollectionEnumerator<Node>)mSdtCollEnumerator).reset();
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ Iterator<Node> mSdtCollEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    /// <summary>
    /// This is a live collection of structured document tag nodes.
    /// </summary>
    private /*final*/ NodeCollection mStructuredDocumentTags;

    private static class StructuredDocumentTagMatcher extends NodeMatcher
    {

        StructuredDocumentTagMatcher(NodeRange range)
        {
            mNodeRange = range;
            updateStructuredDocumentTagCache();
        }

        /// <summary>
        /// Returns true if the specified node matches the appropriate structured document tag node.
        /// </summary>
        /// <remarks>
        /// Supports both StructuredDocumentTag and the ranged SDT. Both types of nodes are treated as the ranges
        /// with the start and the end, see the comment BookmarkMatcher.IsMatch() for more details.
        /// </remarks>
        /*override*/ boolean isMatch(Node node)
        {
            if (mInitialChangeCount != getDocument().getTreeChangeCount())
                updateStructuredDocumentTagCache();

            if (((node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG) && (node.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START)) ||
                mRangeIsEmpty)
                return false;

            StructuredDocumentTagRangeStart sdtStart = ms.as(node, StructuredDocumentTagRangeStart.class);
            if (sdtStart != null)
            {
                StructuredDocumentTagRangeEnd sdtEnd = sdtStart.getRangeEnd();
                return mRangesStarts.contains(sdtStart) && mRangesEnds.contains(sdtEnd);
            }

            return mSdtNodes.contains(node);
        }

        /*override*/ boolean isSkipMarkupNodes() { return true; }

        private void updateStructuredDocumentTagCache()
        {
            mInitialChangeCount = getDocument().getTreeChangeCount();

            mRangeIsEmpty = mNodeRange.isEmpty() || mNodeRange.isVoid() ||
                            mNodeRange.getStart().getNode().isRemoved() ||
                            mNodeRange.getEnd().getNode().isRemoved() ||
                            (NodeFinder.findNodes(mNodeRange, NodeType.ANY).size() == 0);

            if (mRangeIsEmpty)
                return;

            mSdtNodes = NodeFinder.findNodes(mNodeRange, NodeType.STRUCTURED_DOCUMENT_TAG);

            // We add here StructuredDocumentTag which is the parent for the part of the range.
            Node parent = mNodeRange.getFirstRangeNode().getParentNode();
            do
            {
                if (parent.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
                    mSdtNodes.add(parent);
                parent = parent.getParentNode();
            }
            while ((parent != null) && (parent.getNodeLevel() != NodeLevel.SECTION_STORY));

            NodeRange highRange = new NodeRange(getDocument().getFirstSection().getBody().getFirstChild(),
                true, mNodeRange.getEnd().getNode(), mNodeRange.isEndIncluded());
            mRangesStarts = NodeFinder.findNodes(highRange, NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START);

            NodeRange tailRange = new NodeRange(mNodeRange.getStart().getNode(), mNodeRange.isStartIncluded(),
                getDocument().getLastChild(), true);
            mRangesEnds = NodeFinder.findNodes(tailRange, NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END);
        }

        private Document getDocument() { return mNodeRange.getDocument().fetchDocument(); }

        private boolean mRangeIsEmpty;
        private ArrayList<Node> mSdtNodes;
        private ArrayList<Node> mRangesStarts;
        private ArrayList<Node> mRangesEnds;
        private int mInitialChangeCount;
        private /*final*/ NodeRange mNodeRange;
    }
}

