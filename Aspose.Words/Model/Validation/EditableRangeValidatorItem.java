// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A data holder. Keeps info about editable range start/end during document validation.
/// </summary>
class EditableRangeValidatorItem
{
    /// <summary>
    /// Used to renumber editable range start/end nodes during validation.
    /// </summary>
    void setNewId()
    {
        if (EditableRangeStart != null)
            EditableRangeStart.setId(NewId);
        if (EditableRangeEnd != null)
            EditableRangeEnd.setId(NewId);
    }

    void removeEditableRange()
    {
        if (EditableRangeStart != null)
        {
            if (EditableRangeStart.getParentNode() != null)
                EditableRangeStart.remove();
            EditableRangeStart = null;
        }

        if (EditableRangeEnd != null)
        {
            if (EditableRangeEnd.getParentNode() != null)
                EditableRangeEnd.remove();
            EditableRangeEnd = null;
        }
    }

    boolean hasStart() { return (EditableRangeStart != null); }

    boolean hasEnd() { return (EditableRangeEnd != null); }

    EditableRangeStart EditableRangeStart;
    EditableRangeEnd EditableRangeEnd;
    int NewId;
}
