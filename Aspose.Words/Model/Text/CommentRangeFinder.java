// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Finds comment range nodes by ID.
/// </summary>
class CommentRangeFinder extends DocumentVisitor
{
    /// <summary>
    /// Searches for comment range start and range end nodes that have the specified comment ID.
    /// Returns <c>true</c> if the both are found.
    /// </summary>
    boolean find(Node node, int commentId) throws Exception
    {
        mCommentId = commentId;

        node.accept(this);

        if ((mRangeStart != null) != (mRangeEnd != null))
            throw new IllegalStateException(msString.format("Wrong comment range (ID = {0}).", commentId));

        return mRangeStart != null;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        if (commentRangeStart.getId() == mCommentId)
        {
            if (mRangeStart != null)
                throw new IllegalStateException(msString.format("Wrong comment range (ID = {0}).", mCommentId));

            mRangeStart = commentRangeStart;
            if (mRangeEnd != null)
                return VisitorAction.STOP;
        }
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        if (commentRangeEnd.getId() == mCommentId)
        {
            if (mRangeEnd != null)
                throw new IllegalStateException(msString.format("Wrong comment range (ID = {0}).", mCommentId));

            mRangeEnd = commentRangeEnd;
            if (mRangeStart != null)
                return VisitorAction.STOP;
        }
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Gets found comment range start node.
    /// </summary>
    CommentRangeStart getRangeStart() { return mRangeStart; }

    /// <summary>
    /// Gets found comment range end node.
    /// </summary>
    CommentRangeEnd getRangeEnd() { return mRangeEnd; }

    private int mCommentId;
    private CommentRangeStart mRangeStart;
    private CommentRangeEnd mRangeEnd;
}

