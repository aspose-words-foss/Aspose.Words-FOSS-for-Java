// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IntToObjDictionary;
import java.util.ArrayList;


/// <summary>
/// Used to validate comments, comment ranges and editable ranges before saving a document.
/// 
/// Marks comments that have no ranges. We need this because when writing to RTF it makes a difference.
/// Removes comment ranges/editable ranges that have end before start.
/// Removes ranges that have only start or end. 
/// Removes comment ranges that have no comment.
/// Removes comment ranges that are empty.
/// Removes comment ranges with duplicate ids.
/// Generates the whole new sequence of comment/editable range ids and new ids for comments/editable range with duplicate ids.
/// 
/// Comment ids in the model can have any values and we have to generate new ids for all comments 
/// to reliably fix comments with duplicate ids.
/// </summary>
class AnnotationValidator
{
    AnnotationValidator(DocumentBase doc, SaveInfo saveInfo, IWarningCallback warningCallback)
    {
        mDoc = doc;
        mSaveInfo = saveInfo;
        mDoc.resetNextAnnotationId();
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Call this at the document end. Performs validation tasks that need to be done at the end.
    /// </summary>
    void visitDocumentEnd()
    {
        validateComments();
        updateParentCommentIds();
        validateEditableRanges();
    }

    /*VisitorAction*/int visitComment(Comment comment)
    {
        comment.ensureMinimum();

        CommentValidatorItem item = getOrCreateCommentValidatorItem(comment.getId());
        if (item.Comment != null)
        {
            // Got a comment with a duplicate id so generate a new id for it.
            // Such comment will lose its range (if it had it), but that's the best we can do.
            ((INodeWithAnnotationId)comment).setIdInternal(mDoc.getNextAnnotationId());
        }
        else
        {
            item.Comment = comment;
        }

        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        CommentValidatorItem item = getOrCreateCommentValidatorItem(commentRangeStart.getId());
        if (item.hasStart())
        {
            // Get a duplicate start, remove.
            commentRangeStart.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }

        item.CommentRangeStart = commentRangeStart;
        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        CommentValidatorItem item = getOrCreateCommentValidatorItem(commentRangeEnd.getId());
        if (item.hasEnd() || !item.hasStart())
        {
            // Got a duplicate end or got and end without a start, remove.
            commentRangeEnd.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }

        item.CommentRangeEnd = commentRangeEnd;
        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitEditableRangeStart(EditableRangeStart editableRangeStart)
    {
        EditableRangeValidatorItem item = getOrCreateEditableRangeValidatorItem(editableRangeStart.getId());
        if (item.hasStart())
        {
            // Get a duplicate start, remove.
            editableRangeStart.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }

        item.EditableRangeStart = editableRangeStart;
        return VisitorAction.CONTINUE;
    }

    /*VisitorAction*/int visitEditableRangeEnd(EditableRangeEnd editableRangeEnd)
    {
        EditableRangeValidatorItem item = getOrCreateEditableRangeValidatorItem(editableRangeEnd.getId());
        if (item.hasEnd() || !item.hasStart())
        {
            // Got a duplicate end or got and end without a start, remove.
            editableRangeEnd.remove();
            return VisitorAction.SKIP_THIS_NODE;
        }

        item.EditableRangeEnd = editableRangeEnd;
        return VisitorAction.CONTINUE;
    }

    private void validateEditableRanges()
    {
        for (EditableRangeValidatorItem item : mEditableRangeValidatorItems)
        {
            if (item.hasStart() && !item.hasEnd())
            {
                warnUnexpected(WarningStrings.EDITABLE_RANGE_VALIDATOR_NO_END);
                item.removeEditableRange();
            }

            if (!item.hasStart() && item.hasEnd())
            {
                warnUnexpected(WarningStrings.EDITABLE_RANGE_VALIDATOR_NO_START);
                item.removeEditableRange();
            }

            // Now ready to apply the newly generated ids.
            item.setNewId();
        }
    }

    private void validateComments()
    {
        for (CommentValidatorItem item : mCommentIdToValidatorItem.getValues())
        {
            if (item.hasStart() && !item.hasEnd())
            {
                warnUnexpected(WarningStrings.COMMENT_VALIDATOR_NO_END);
                item.removeCommentRange();
            }

            if (!item.hasStart() && item.hasEnd())
            {
                warnUnexpected(WarningStrings.COMMENT_VALIDATOR_NO_START);
                item.removeCommentRange();
            }

            if ((item.Comment == null) && item.hasStart() && item.hasEnd())
            {
                warnUnexpected(WarningStrings.COMMENT_VALIDATOR_NO_CONTENT);
                item.removeCommentRange();
            }

            if (item.isEmptyRange())
            {
                warnUnexpected(WarningStrings.COMMENT_VALIDATOR_EMPTY_RANGE);
                item.removeCommentRange();
            }
            else if ((item.Comment != null) && item.hasStart() && item.Comment.isAbove(item.CommentRangeStart))
            {
                warnUnexpected(WarningStrings.COMMENT_ABOVE_COMMENT_RANGE);
                item.removeCommentRange();
            }

            if (item.Comment != null)
            {
                if((mSaveInfo != null) && (item.hasStart() && item.hasEnd()))
                    mSaveInfo.setHasRange(item.Comment);
            }

            // Now ready to apply the newly generated ids.
            item.setNewId();
        }
    }

    private CommentValidatorItem getOrCreateCommentValidatorItem(int id)
    {
        CommentValidatorItem item = mCommentIdToValidatorItem.get(id);
        if (item == null)
        {
            item = new CommentValidatorItem();
            
            // Generate a new id now, this will result in "nice" ids that increase 
            // with the order the comments occur in the document.
            item.NewId = mDoc.getNextAnnotationId();
            mCommentIdToValidatorItem.set(id, item);
        }
        return item;
    }

    private EditableRangeValidatorItem getOrCreateEditableRangeValidatorItem(int id)
    {
        EditableRangeValidatorItem resultItem = null;

        for (EditableRangeValidatorItem item : mEditableRangeValidatorItems)
        {
            if ((item.hasStart()) && (item.EditableRangeStart.getId() == id) && (!item.hasEnd()))
            {
                resultItem = item;
                break;
            }
        }

        if (resultItem == null)
        {
            resultItem = new EditableRangeValidatorItem();

            // Generate a new id now, this will result in "nice" ids that increase 
            // with the order the editable range occur in the document.
            resultItem.NewId = mDoc.getNextAnnotationId();
            msArrayList.add(mEditableRangeValidatorItems, resultItem);
        }

        return resultItem;
    }

    /// <summary>
    /// Updates references to parent comments.
    /// </summary>
    private void updateParentCommentIds()
    {
        IntToObjDictionary<CommentValidatorItem>.Enumerator enumerator = mCommentIdToValidatorItem.iterator();

        while (enumerator.moveNext())
        {
            CommentValidatorItem item = enumerator.getCurrentValue();

            if (item.Comment == null)
                continue;

            int newParentId = Comment.NO_PARENT;

            if (item.Comment.getParentId() != Comment.NO_PARENT)
            {
                int parentId = item.Comment.getParentId();
                CommentValidatorItem parentItem = mCommentIdToValidatorItem.get(parentId);
                // WORDSNET-14055 When the comment does not have parent item, the special (NoParent) value
                // have to be set as parent identifier.
                newParentId = (parentItem != null) ? parentItem.NewId : Comment.NO_PARENT;
            }

            item.Comment.setParentId(newParentId);
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warnUnexpected(String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(WarningType.UNEXPECTED_CONTENT, WarningSource.UNKNOWN, description));
    }

    private /*final*/ DocumentBase mDoc;
    
    /// <summary>
    /// Key is an integer comment id. Value is a <see cref="CommentValidatorItem"/>.
    /// </summary>
    private /*final*/ IntToObjDictionary<CommentValidatorItem> mCommentIdToValidatorItem = 
        new IntToObjDictionary<CommentValidatorItem>();

    /// <summary>
    /// A list of EditableRangeValidatorItems. <see cref="EditableRangeValidatorItem"/>.
    /// </summary>
    private /*final*/ ArrayList<EditableRangeValidatorItem> mEditableRangeValidatorItems = 
        new ArrayList<EditableRangeValidatorItem>();

    private /*final*/ IWarningCallback mWarningCallback;

    private /*final*/ SaveInfo mSaveInfo;
}

