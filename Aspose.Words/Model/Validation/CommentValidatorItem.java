// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A data holder. Keeps info about comment and comment range start/end during document validation.
/// </summary>
class CommentValidatorItem
{
    /// <summary>
    /// Used to renumber comment and comment range nodes during validation.
    /// </summary>
    void setNewId()
    {
        if (Comment != null)
            ((INodeWithAnnotationId)Comment).setIdInternal(NewId);
        if (CommentRangeStart != null)
            CommentRangeStart.setId(NewId);
        if (CommentRangeEnd != null)
            CommentRangeEnd.setId(NewId);
    }

    void removeCommentRange()
    {
        // TODO 2 See Aspose.Words.TestData\Model\Para\TestTextFrameDetection.docx
        // The code crashes because CommentRangeStart.Parent == null.
        // I don't know how it comes to be null. This is probably an issue with text frame to text box conversion.

        if (CommentRangeStart != null)
        {
            if (CommentRangeStart.getParentNode() != null)
                CommentRangeStart.remove();
            CommentRangeStart = null;
        }
        
        if (CommentRangeEnd != null)
        {
            if (CommentRangeEnd.getParentNode() != null)
                CommentRangeEnd.remove();
            CommentRangeEnd = null;
        }
    }

    boolean hasStart() { return (CommentRangeStart != null); }

    boolean hasEnd() { return (CommentRangeEnd != null); }

    boolean isSameParent() { return hasStart() && hasEnd() && (CommentRangeStart.getParentNode() == CommentRangeEnd.getParentNode()); }

    boolean isEmptyRange()
    {
        // WORDSNET-4661 Code assumes that comment range start/end are children of the same parent but it's not true for
        // defect file. Fix avoids to remove such range nodes.
        if(!isSameParent())
            return false;

        Node node = CommentRangeStart;
        while ((node != null) && (node != CommentRangeEnd))
        {
            if (node.getTextLength() > 0)
                return false;

            node = node.getNextSibling();
        }
        return true;                
    }

    Comment Comment;
    CommentRangeStart CommentRangeStart;
    CommentRangeEnd CommentRangeEnd;
    int NewId;
}

