// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// A collection of <see cref="Bookmark"/> objects that represent the bookmarks in the specified range.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-bookmarks/">Working with Bookmarks</a> documentation article.</para>
/// </summary>
public class BookmarkCollection implements Iterable<Bookmark>
{
    BookmarkCollection(Node parent)
    {
        if (parent.isComposite())
            mBookmarkStarts = ((CompositeNode)parent).getChildNodes(NodeType.BOOKMARK_START, true);
        else
            mBookmarkStarts = EmptyNodeCollection.createEmpty();
    }

    BookmarkCollection(NodeRange nodeRange)
    {
        mBookmarkStarts = new NodeCollection(nodeRange.getDocument(), new BookmarkMatcher(nodeRange), true);
    }

    /// <summary>
    /// Returns the number of bookmarks in the collection.
    /// </summary>
    public int getCount() { return mBookmarkStarts.getCount(); }

    /// <summary>
    /// Returns a bookmark at the specified index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public Bookmark get(int index) { return ((BookmarkStart)mBookmarkStarts.get(index)).getBookmark(); }

    /// <summary>
    /// Returns a bookmark by name.
    /// </summary>
    /// <remarks>
    /// <p>Returns <c>null</c> if the bookmark with the specified name cannot be found.</p>
    /// </remarks>
    /// <param name="bookmarkName">Case-insensitive name of the bookmark.</param>
    public Bookmark get(String bookmarkName)
    {
        ArgumentUtil.checkNotNull(bookmarkName, "bookmarkName");

        for (BookmarkStart bookmarkStart : (Iterable<BookmarkStart>) mBookmarkStarts)
            if (StringUtil.equalsIgnoreCase(bookmarkName, bookmarkStart.getName()))
                return bookmarkStart.getBookmark();

        return null;
    }


    /// <summary>
    /// Returns index of bookmark with the specified name.
    /// </summary>
    /// <param name="bookmarkName">Name of the bookmark to find the index for.</param>
    /// <returns>Bookmark index in this collection. Returns -1 if the bookmark with the specified name does not exist in the collection.</returns>
    int indexOf(String bookmarkName)
    {
        int i = 0;
        for (BookmarkStart bookmarkStart : (Iterable<BookmarkStart>) mBookmarkStarts)
        {
            if (StringUtil.equalsIgnoreCase(bookmarkName, bookmarkStart.getName()))
                return i;
            i++;
        }
        return -1;
    }

    /// <summary>
    /// Removes the specified bookmark from the document.
    /// </summary>
    /// <param name="bookmark">The bookmark to remove.</param>
    public void remove(Bookmark bookmark) throws Exception
    {
        if (bookmark == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "bookmark");
        bookmark.remove();
    }

    /// <summary>
    /// Removes a bookmark with the specified name.
    /// </summary>
    /// <param name="bookmarkName">The case-insensitive name of the bookmark to remove.</param>
    public void remove(String bookmarkName) throws Exception
    {
        ArgumentUtil.checkNotNull(bookmarkName, "bookmarkName");
        remove(this.get(bookmarkName));
    }

    /// <summary>
    /// Removes a bookmark at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the bookmark to remove.</param>
    public void removeAt(int index) throws Exception
    {
        remove(this.get(index));
    }

    /// <summary>
    /// Removes all bookmarks from this collection and from the document.
    /// </summary>
    public void clear() throws Exception
    {
        // SPEED This is a proper way for removing.
        int remaining = getCount();
        while (remaining > 0)
        {
            removeAt(0);
            remaining--;
        }
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    public Iterator<Bookmark> iterator()
    {
        return new BookmarkIterator(mBookmarkStarts);
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// This is a live collection of bookmark start nodes.
    /// </summary>
    private /*final*/ NodeCollection mBookmarkStarts;

    /// <summary>
    /// Enumerator through items of <see cref="BookmarkCollection"/>.
    /// </summary>
    private final static class BookmarkIterator implements Iterator<Bookmark>
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        BookmarkIterator(NodeCollection bookmarkStarts)
        {
            mBookmarkStartEnumerator = bookmarkStarts.iterator();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        public Bookmark next()
        {
            BookmarkStart bookmarkStart = (BookmarkStart)mBookmarkStartEnumerator.next();
            return bookmarkStart != null ? bookmarkStart.getBookmark() : null;
        }

        /// <summary>
        /// Advances the enumerator to the next element of the collection.
        /// </summary>
        /// <returns></returns>
        public boolean hasNext()
        {
            return mBookmarkStartEnumerator.hasNext();
        }

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        public void reset()
        {
            ((NodeCollectionEnumerator<Node>)mBookmarkStartEnumerator).reset();
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private /*final*/ Iterator<Node> mBookmarkStartEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private static class BookmarkMatcher extends NodeMatcher
    {

        BookmarkMatcher(NodeRange range)
        {
            mNodeRange = range;
            updateBookmarkCache();
        }

        /// <summary>
        /// Returns true if the specified node matches the appropriate Bookmark node.
        /// </summary>
        /// <remarks>
        /// MS Word approach is implemented when the bookmark range, and not just BookmarkStart, is taken into account.
        /// Ex: The document has three sections. BookmarkStart is in the first section, BookmarkEnd is in the third one.
        ///     BookmarkMatcher will indicate that this Bookmark is in all the three sections, and not just in the first one.
        /// </remarks>
        /*override*/ boolean isMatch(Node node) throws Exception
        {
            if (mInitialChangeCount != getDocument().getTreeChangeCount())
                updateBookmarkCache();

            if ((node.getNodeType() != NodeType.BOOKMARK_START) || mRangeIsEmpty)
                return false;

            BookmarkStart bmkStart = (BookmarkStart)node;
            BookmarkEnd bmkEnd = bmkStart.getBookmark().getBookmarkEnd();

            return mBmkStarts.contains(bmkStart) && mBmkEnds.contains(bmkEnd);
        }

        /*override*/ boolean isSkipMarkupNodes() { return true; }

        private void updateBookmarkCache()
        {
            mInitialChangeCount = getDocument().getTreeChangeCount();

            mRangeIsEmpty = mNodeRange.isEmpty() || mNodeRange.isVoid() ||
                            mNodeRange.getStart().getNode().isRemoved() ||
                            mNodeRange.getEnd().getNode().isRemoved() ||
                            (NodeFinder.findNodes(mNodeRange, NodeType.ANY).size() == 0);

            if (mRangeIsEmpty)
                return;

            NodeRange highRange = new NodeRange(getDocument().getFirstSection().getBody().getFirstChild(),
                true, mNodeRange.getEnd().getNode(), mNodeRange.isEndIncluded());
            mBmkStarts = NodeFinder.findNodes(highRange, NodeType.BOOKMARK_START);

            NodeRange tailRange = new NodeRange(mNodeRange.getStart().getNode(), mNodeRange.isStartIncluded(),
                getDocument().getLastChild(), true);
            mBmkEnds = NodeFinder.findNodes(tailRange, NodeType.BOOKMARK_END);
        }

        private Document getDocument() { return mNodeRange.getDocument().fetchDocument(); }

        private boolean mRangeIsEmpty;
        private ArrayList<Node> mBmkStarts;
        private ArrayList<Node> mBmkEnds;
        private int mInitialChangeCount;
        private /*final*/ NodeRange mNodeRange;
    }
}

