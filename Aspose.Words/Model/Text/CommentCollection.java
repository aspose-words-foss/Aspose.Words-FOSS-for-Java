// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2017 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides typed access to a collection of <see cref="Comment"/> nodes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-comments/">Working with Comments</a> documentation article.</para>
/// </summary>
public class CommentCollection extends NodeCollection<Comment>
{
    /// <summary>
    /// Implements matching of comments that are children of given comment.
    /// </summary>
    private static class CommentReplyMatcher extends NodeMatcher
    {
        private /*final*/ Comment mParentComment;
        CommentReplyMatcher(Comment parentComment)
        {
            mParentComment = parentComment;
        }

        /*override*/ boolean isMatch(Node node)
        {
            if (node.getNodeType() != NodeType.COMMENT)
                return false;

            return (((Comment)node).getParentId() == mParentComment.getId());
        }

        /*override*/ boolean isSkipMarkupNodes() { return true; }
    }

    CommentCollection(DocumentBase doc, Comment parentComment)
    {
    	super(doc, new CommentReplyMatcher(parentComment), true);
	
    }

    /// <summary>
    /// Retrieves a <see cref="Comment"/> at the given index.
    /// </summary>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="NodeCollection.IndexerCommon"]/*'/>
    /// <param name="index">An index into the collection.</param>
    public /*new*/ Comment get(int index) { return (Comment)super.get(index); }
}

