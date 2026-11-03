// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents a bookmark cache making access to bookmarks contained in a document by their names faster.
/// After the cache instance is attached to a document, it tracks and reflects changes to its bookmark nodes.
/// </summary>
class BookmarkCache implements INodeChangingCallback
{
    BookmarkCache(Document document)
    {
        Debug.assertTrue(document != null);
        mDocument = document;
    }

    /// <summary>
    /// Attaches the cache to the specified document. A single instance of a cache can be attached to a single
    /// document at the same time.
    /// </summary>
    private void ensureAttached() throws Exception
    {
        if (mBookmarkNameToBundleMap != null)
            return;

        mBookmarkNameToBundleMap = new StringToObjDictionary<BookmarkCache.BookmarkBundle>(false);

        // Initialize the state of the cache.
        MapInitializer initializer = new MapInitializer(this);
        mDocument.accept(initializer);

        // Subscribe to bookmark start inserting/removing the cache to be autorefreshable.
        mDocument.addInternalNodeChangingCallback(this);
    }

    /// <summary>
    /// Detaches the cache from a document it was previously attached to.
    /// </summary>
    void clear()
    {
        if (mBookmarkNameToBundleMap == null)
            return;

        mDocument.removeInternalNodeChangingCallback(this);
        mBookmarkNameToBundleMap = null;
    }

    public void /*INodeChangingCallback.*/nodeInserting(NodeChangingArgs args)
    {
        // Do nothing.
    }

    public void /*INodeChangingCallback.*/nodeInserted(NodeChangingArgs args)
    {
        if (args.getNewParent().isRemoved())
            return;

        processNodeChange(args.getNode(), true);
    }

    public void /*INodeChangingCallback.*/nodeRemoving(NodeChangingArgs args)
    {
        // Do nothing.
    }

    public void /*INodeChangingCallback.*/nodeRemoved(NodeChangingArgs args)
    {
        if (args.getOldParent().isRemoved())
            return;

        processNodeChange(args.getNode(), false);
    }

    /// <summary>
    /// Refreshes the cache when a bookmark start or end is inserted or removed.
    /// </summary>
    private void processNodeChange(Node node, boolean isInserted)
    {
        if (node.isComposite())
        {
            // Process possible nested bookmark starts.
            CompositeNode parentNode = (CompositeNode)node;
            for (Node childNode = parentNode.getFirstChild(); childNode != null; childNode = childNode.getNextSibling())
                processNodeChange(childNode, isInserted);
        }
        else if (node.getNodeType() == NodeType.BOOKMARK_START)
        {
            BookmarkStart bookmarkStart = (BookmarkStart)node;
            cacheBookmarkStart(bookmarkStart.getName(), (isInserted ? bookmarkStart : null));
        }
        else if (node.getNodeType() == NodeType.BOOKMARK_END)
        {
            BookmarkEnd bookmarkEnd = (BookmarkEnd)node;
            cacheBookmarkEnd(bookmarkEnd.getName(), (isInserted ? bookmarkEnd : null));
        }
    }

    private void cacheBookmarkStart(String bookmarkName, BookmarkStart bookmarkStart)
    {
        getBookmarkBundle(bookmarkName, true).setStart(bookmarkStart);
    }

    private void cacheBookmarkEnd(String bookmarkName, BookmarkEnd bookmarkEnd)
    {
        getBookmarkBundle(bookmarkName, true).setEnd(bookmarkEnd);
    }

    /// <summary>
    /// Returns a <see cref="BookmarkBundle"/> instance corresponding to the specified bookmark name.
    /// Optionally creates it, if not found.
    /// </summary>
    private BookmarkBundle getBookmarkBundle(String bookmarkName, boolean createIfNull)
    {
        BookmarkBundle bundle = mBookmarkNameToBundleMap.get(bookmarkName);
        if ((bundle == null) && createIfNull)
        {
            bundle = new BookmarkBundle();
            mBookmarkNameToBundleMap.set(bookmarkName, bundle);
        }

        return bundle;
    }

    /// <summary>
    /// Gets a bookmark for the corresponding bookmark start and end from the cache.
    /// </summary>
    Bookmark get(String bookmarkName) throws Exception
    {
        ensureAttached();

        BookmarkBundle bundle = getBookmarkBundle(bookmarkName, false);
        return (bundle != null) ? bundle.getBookmark() : null;
    }

    /// <summary>
    /// Initializes the state of a particular bookmark cache.
    /// </summary>
    private static class MapInitializer extends DocumentVisitor
    {
        MapInitializer(BookmarkCache cache)
        {
            mCache = cache;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
        {
            mCache.cacheBookmarkStart(bookmarkStart.getName(), bookmarkStart);
            return VisitorAction.CONTINUE;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
        {
            mCache.cacheBookmarkEnd(bookmarkEnd.getName(), bookmarkEnd);
            return VisitorAction.CONTINUE;
        }

        private /*final*/ BookmarkCache mCache;
    }

    /// <summary>
    /// Stores information about a bookmark start and end pair being cached.
    /// </summary>
    private static class BookmarkBundle
    {
        void setStart(BookmarkStart start)
        {
            mStart = start;
        }

        void setEnd(BookmarkEnd end)
        {
            mEnd = end;
        }

        Bookmark getBookmark()
        {
            return ((mStart != null) && (mEnd != null)) ? new Bookmark(mStart, mEnd) : null;
        }

        private BookmarkStart mStart;

        private BookmarkEnd mEnd;
    }

    private /*final*/ Document mDocument;
    private StringToObjDictionary<BookmarkCache.BookmarkBundle> mBookmarkNameToBundleMap;
}

