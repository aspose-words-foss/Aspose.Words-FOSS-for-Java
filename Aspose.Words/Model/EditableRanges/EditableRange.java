// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/05/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a single editable range.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="EditableRange"/> is a "facade" object that encapsulates two nodes <see cref="EditableRangeStart"/>
/// and <see cref="EditableRangeEnd"/> in a document tree and allows to work with an editable range as a single object.</p>
/// </remarks>
public class EditableRange
{
    EditableRange(EditableRangeStart editableRangeStart)
    {
        if (editableRangeStart == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "editableRangeStart");
        mEditableRangeStart = editableRangeStart;
    }

    /// <summary>
    /// Removes the editable range from the document. Does not remove content inside the editable range.
    /// </summary>
    public void remove() throws Exception
    {
        getEditableRangeStart().remove();
        getEditableRangeEnd().remove();
    }

    /// <summary>
    /// Gets the editable range identifier.
    /// </summary>
    /// <remarks>
    /// <para>The region must be demarcated using the <see cref="EditableRangeStart"/> and <see cref="EditableRangeEnd"/></para>
    /// 
    /// <para>Editable range identifiers are supposed to be unique across a document and Aspose.Words automatically 
    /// maintains editable range identifiers when loading, saving and combining documents.</para>
    /// </remarks>
    public int getId() { return mEditableRangeStart.getId(); }

    /// <summary>
    /// Returns or sets the single user for editable range.
    /// </summary>
    /// <remarks>
    /// <p>This editor can be stored in one of the following forms:</p>
    /// <p>DOMAIN\Username - for users whose access shall be authenticated using the current user's domain credentials.</p>
    /// <p>user@domain.com - for users whose access shall be authenticated using the user's e-mail address as credentials.</p>
    /// <p>user - for users whose access shall be authenticated using the current user's machine credentials.</p>
    /// <p>Single user and editor group cannot be set simultaneously for the specific editable range, 
    /// if the one is set, the other will be clear.</p>
    /// </remarks>
    public String getSingleUser() { return getEditableRangeStart().getSingleUser(); }
    public void setSingleUser(String value)
    {
        getEditableRangeStart().setSingleUser(value);
        getEditableRangeStart().setEditorGroup(EditorType.UNSPECIFIED);
    }

    /// <summary>
    /// Returns or sets an alias (or editing group) which shall be used to determine if the current user
    /// shall be allowed to edit this editable range.
    /// </summary>
    /// <remarks>
    /// <p>Single user and editor group cannot be set simultaneously for the specific editable range,
    /// if the one is set, the other will be clear.</p>
    /// </remarks>
    public /*EditorType*/int getEditorGroup() { return getEditableRangeStart().getEditorGroup(); }
    public void setEditorGroup(/*EditorType*/int value)
    {
        getEditableRangeStart().setEditorGroup(value);
        getEditableRangeStart().setSingleUser("");
    }

    /// <summary>
    /// Gets the node that represents the start of the editable range.
    /// </summary>
    public EditableRangeStart getEditableRangeStart() { return mEditableRangeStart; }

    /// <summary>
    /// Gets the node that represents the end of the editable range.
    /// </summary>
    public EditableRangeEnd getEditableRangeEnd() throws Exception
    {
        if (mEditableRangeEnd == null)
            mEditableRangeEnd = EditableRangeFinder.findEditableRangeEnd(mEditableRangeStart.getDocument(), getId());
        return mEditableRangeEnd;
    }

    private /*final*/ EditableRangeStart mEditableRangeStart;
    
    /// <summary>
    /// This is not really nice when editable range end can be deleted from the document and this will not get updated.
    /// </summary>
    private EditableRangeEnd mEditableRangeEnd;
}

