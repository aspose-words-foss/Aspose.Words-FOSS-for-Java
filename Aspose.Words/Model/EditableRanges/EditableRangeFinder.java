// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/06/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Finds an editable range by Id.
/// </summary>
class EditableRangeFinder extends DocumentVisitor
{
    private EditableRangeFinder(int editableRangeId, boolean isLookingForStart)
    {
        mEditableRangeId = editableRangeId;
        mIsLookingForStart = isLookingForStart;
    }

    /// <summary>
    /// Helper function to find an editable range start in the document. Returns the editable range start or null.
    /// </summary>
    static EditableRangeStart findEditableRangeStart(Node node, int editableRangeId) throws Exception
    {
        EditableRangeFinder finder = new EditableRangeFinder(editableRangeId, true);
        node.accept(finder);
        
        return finder.mEditableRangeStart;
    }

    /// <summary>
    /// Helper function to find an editable range end in the document. Returns the editable range end or null.
    /// </summary>
    static EditableRangeEnd findEditableRangeEnd(Node node, int editableRangeId) throws Exception
    {
        if (node == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "node");

        EditableRangeFinder finder = new EditableRangeFinder(editableRangeId, false);
        node.accept(finder);
        
        return finder.mEditableRangeEnd;
    }

    static EditableRangeStart fetchEditableRangeStart(Node node, int editableRangeId) throws Exception
    {
        EditableRangeStart result = findEditableRangeStart(node, editableRangeId);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find an editable range with Id='{0}' in the document.", editableRangeId));
        
        return result;
    }

    static EditableRangeEnd fetchEditableRangeEnd(Node node, int editableRangeId) throws Exception
    {
        EditableRangeEnd result = findEditableRangeEnd(node, editableRangeId);
        if (result == null)
            throw new IllegalStateException(msString.format("Cannot find an editable range with Id='{0}' in the document.", editableRangeId));
        
        return result;
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeStart(EditableRangeStart editableRangeStart)
    {
        if (mIsLookingForStart && (mEditableRangeId == editableRangeStart.getId()))
        {
            mEditableRangeStart = editableRangeStart;
            return VisitorAction.STOP;
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeEnd(EditableRangeEnd editableRangeEnd)
    {
        if (!mIsLookingForStart && (mEditableRangeId == editableRangeEnd.getId()))
        {
            mEditableRangeEnd = editableRangeEnd;
            return VisitorAction.STOP;
        }

        return VisitorAction.CONTINUE;
    }

    private /*final*/ int mEditableRangeId;
    private /*final*/ boolean mIsLookingForStart;
    private EditableRangeStart mEditableRangeStart;
    private EditableRangeEnd mEditableRangeEnd;
}

