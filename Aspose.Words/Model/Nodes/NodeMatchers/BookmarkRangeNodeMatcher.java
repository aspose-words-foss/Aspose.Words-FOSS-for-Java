// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;


/// <summary>
/// Matches the node if it is BookmarkStart that has appropriate BookmarkEnd.
/// </summary>
class BookmarkRangeNodeMatcher extends NodeTypeMatcher
{
    /// <summary>
    /// Ctor.
    /// </summary>
    public BookmarkRangeNodeMatcher(CompositeNode parent)
    {
    	super(new /*NodeType*/int[] { NodeType.BOOKMARK_START });
	
        mParent = parent;
        mDocumentChangeCount = parent.getDocument().getTreeChangeCount();
    }

    /// <summary>
    /// Returns true, if specified node is BookmarkStart and has relevant BookmarkEnd.
    /// </summary>
    /*override*/ boolean isMatch(Node node) throws Exception
    {
        if (!super.isMatch(node))
            return false;

        // Clear cache if document has been changed.
        if (mDocumentChangeCount != mParent.getDocument().getTreeChangeCount())
        {
            mCachedStarts.clear();
            mDocumentChangeCount = mParent.getDocument().getTreeChangeCount();
        }

        // FIX 16667. Implemented cache for already checked BookmarkStarts.
        if (mCachedStarts.containsKey(node))
            return (mCachedStarts.get(node) != null);

        BookmarkStart bookmarkStart = (BookmarkStart)node;
        BookmarkEnd bookmarkEnd = BookmarkFinder.findBookmarkEnd(mParent, bookmarkStart.getName(), bookmarkStart);
        mCachedStarts.put(node, bookmarkEnd);

        return (bookmarkEnd != null);
    }

    /// <summary>
    /// Bookmark range parent node.
    /// </summary>
    private /*final*/ CompositeNode mParent;

    /// <summary>
    /// The cache for already checked BookmarkStarts.
    /// </summary>
    /// <remarks>
    /// Key: BookmarkStart
    /// Value: BookmarkEnd
    /// </remarks>
    private /*final*/ HashMap mCachedStarts = new HashMap();

    /// <summary>
    /// The document tree change count at the moment when BookmarkStarts cache was recently initialized.
    /// </summary>
    private int mDocumentChangeCount;
}

