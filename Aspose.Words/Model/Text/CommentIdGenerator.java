// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Class to generate unique ID for a comment.
/// </summary>
class CommentIdGenerator extends DocumentVisitor
{
    /// <summary>
    /// Cannot create instance of this class.
    /// </summary>
    private CommentIdGenerator()
    {
    }

    /// <summary>
    /// Generates unique ID that can be assigned to a comment.
    /// </summary>
    static int generate(DocumentBase document) throws Exception
    {
        CommentIdGenerator generator = new CommentIdGenerator();

        document.accept(generator);

        return generator.mMaxId + 1;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        if (commentRangeStart.getId() > mMaxId)
            mMaxId = commentRangeStart.getId();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        if (comment.getId() > mMaxId)
            mMaxId = comment.getId();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        if (commentRangeEnd.getId() > mMaxId)
            mMaxId = commentRangeEnd.getId();
        return VisitorAction.CONTINUE;
    }

    private int mMaxId = -1;
}

