// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/05/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// Finds a bookmark by name.
/// </summary>
class BookmarkFinder extends DocumentVisitor
{
    private BookmarkFinder(String bookmarkName, boolean isLookingForStart)
    {
        mBookmarkName = bookmarkName;
        mIsLookingForStart = isLookingForStart;
        mRefBookmarkStart = null;
    }

    /// <summary>
    /// Helper function to find a bookmark start in the document. Returns the bookmark start or null.
    /// </summary>
    static BookmarkStart findBookmarkStart(Node node, String bookmarkName) throws Exception
    {
        return findBookmarkStart(node, bookmarkName, null);
    }

    /// <summary>
    /// Helper function to find a bookmark start that corresponds to the specified bookmark end in the document.
    /// Returns the bookmark start or <b>null</b>.
    /// </summary>
    static BookmarkStart findBookmarkStart(Node node, String bookmarkName, BookmarkEnd refBookmarkEnd) throws Exception
    {
        BookmarkFinder finder = new BookmarkFinder(bookmarkName, true);
        finder.mRefBookmarkEnd = refBookmarkEnd;
        if (refBookmarkEnd != null)
            finder.mNotClosedStarts = new msStack<BookmarkStart>();

        node.accept(finder);
        return finder.mBookmarkStart;
    }

    static BookmarkEnd findBookmarkEnd(Node node, String bookmarkName) throws Exception
    {
        // AW returns first occurrence for duplicated bookmarks for some operations, like moving cursor of the document
        // builder to the end of bookmark with specified name. However, client can be confused when try to move to
        // the end of duplicated bookmark.
        return findBookmarkEnd(node, bookmarkName, null);
    }

    /// <summary>
    /// Helper function to find a bookmark end in the document. Returns the bookmark end or null.
    /// </summary>
    static BookmarkEnd findBookmarkEnd(Node node, String bookmarkName, BookmarkStart refBookmarkStart) throws Exception
    {
        if (node == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "node");

        BookmarkFinder finder = new BookmarkFinder(bookmarkName, false);
        finder.mRefBookmarkStart = refBookmarkStart;

        node.accept(finder);
        return finder.mBookmarkEnd;
    }

    static BookmarkEnd fetchBookmarkEnd(Node node, String bookmarkName, BookmarkStart refBookmarkStart) throws Exception
    {
        BookmarkEnd result = findBookmarkEnd(node, bookmarkName, refBookmarkStart);

        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find bookmark '{0}' in the document.", bookmarkName));
        return result;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart node)
    {
        if (mIsLookingForStart && StringUtil.equalsIgnoreCase(mBookmarkName, node.getName()))
        {
            // Store bookmark start until referenced "end" node is not found.
            if (mRefBookmarkEnd != null)
            {
                mNotClosedStarts.push(node);
                return VisitorAction.CONTINUE;
            }

            mBookmarkStart = node;
            return VisitorAction.STOP;
        }
        else if (!mIsLookingForStart) // Skip unneeded operations.
        {
            // When referenced "start" node is not found, name of bookmark start node and current node are equal,
            // then increment number of opened bookmarks. This counter will be used to find appropriate "end" node.
            if (StringUtil.equalsIgnoreCase(mBookmarkName, node.getName()) && (mRefBookmarkStart != null))
                ++mNotClosedStartCount;

            // Check that current node is the referenced "start" node for which "end" node is looking for.
            // And reset referenced "start" node to show that appropriate "start" node is found.
            if (msObject.referenceEquals(mRefBookmarkStart, node))
                mRefBookmarkStart = null;
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd node)
    {
        if (!mIsLookingForStart && StringUtil.equalsIgnoreCase(mBookmarkName, node.getName()))
        {
            // Return last bookmark at any case, for resiliency.
            mBookmarkEnd = node;
            // Decrement number of "start" nodes with the same name.
            --mNotClosedStartCount;

            // Skip bookmark end when referenced "start" node is not found yet.
            if (mRefBookmarkStart != null)
                return VisitorAction.CONTINUE;

            // WORDSNET-15823 Return appropriate “BookmarkEnd” followed after referenced “BookmarkStart”.
            // When number of "start" nodes equal to zero, then it is mean that appropriate "end" node is found.
            return (mNotClosedStartCount > 0) ? VisitorAction.CONTINUE : VisitorAction.STOP;
        }
        else if (mIsLookingForStart &&
                 (mRefBookmarkEnd != null) &&
                 StringUtil.equalsIgnoreCase(mBookmarkName, node.getName()))
        {
            if (node == mRefBookmarkEnd)
            {
                // The desired bookmark start is the last non-closed bookmark start with this name.
                mBookmarkStart = (mNotClosedStarts.getCount() > 0) ? mNotClosedStarts.peek() : null;
                return VisitorAction.STOP;
            }

            // Close the last bookmark start with this name.
            if (mNotClosedStarts.getCount() > 0)
                mNotClosedStarts.pop();
        }

        return VisitorAction.CONTINUE;
    }

    private /*final*/ String mBookmarkName;
    private /*final*/ boolean mIsLookingForStart;
    private BookmarkStart mBookmarkStart;
    private BookmarkEnd mBookmarkEnd;

    private BookmarkStart mRefBookmarkStart;
    private int mNotClosedStartCount;
    private BookmarkEnd mRefBookmarkEnd;
    private msStack<BookmarkStart> mNotClosedStarts;
}

