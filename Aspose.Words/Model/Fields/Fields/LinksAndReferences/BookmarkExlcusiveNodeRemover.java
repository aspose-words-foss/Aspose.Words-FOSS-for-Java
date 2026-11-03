// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2019 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// Removes the underlying node range but skips the nodes of the specified bookmark. Bookmark start and end nodes are preserved.
/// </summary>
class BookmarkExclusiveNodeRemover extends NodeRemover
{
    BookmarkExclusiveNodeRemover(NodeRange range, String bookmarkName)
    {
    	super(range, NodeJoinMode.JOIN_TO_NEXT_SIBLING, false);
	
        mBookmarkName = bookmarkName;
    }

    protected /*override*/ void onNonCompositeNode()
    {
        if (isBookmarkStart())
        {
            lockRemove();
            super.onNonCompositeNode();
            hasBookmark(true);
        }
        else if (isBookmarkEnd())
        {
            super.onNonCompositeNode();
            unlockRemove();
        }
        else
        {
            super.onNonCompositeNode();
        }
    }

    private boolean isBookmarkStart()
    {
        return ((getCurrentNode().getNodeType() == NodeType.BOOKMARK_START) && (msString.equals(((BookmarkStart)getCurrentNode()).getName(), mBookmarkName)));
    }

    private boolean isBookmarkEnd()
    {
        return ((getCurrentNode().getNodeType() == NodeType.BOOKMARK_END) && (msString.equals(((BookmarkEnd)getCurrentNode()).getName(), mBookmarkName)));
    }

    boolean hasBookmark() { return mHasBookmark; }; private void hasBookmark(boolean value) { mHasBookmark = value; };

    private boolean mHasBookmark;

    private /*final*/ String mBookmarkName;
}

