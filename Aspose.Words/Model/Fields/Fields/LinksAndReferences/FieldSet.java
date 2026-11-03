// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the SET field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Assigns new text to a bookmark.
/// </remarks>
public class FieldSet extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return new FieldUpdateActionSetBookmarkValue(this, getBookmarkName(), getBookmarkTextArgument());
    }

    /// <summary>
    /// Gets or sets the name of the bookmark.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_ARGUMENT_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the new text of the bookmark.
    /// </summary>
    public String getBookmarkText() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_TEXT_ARGUMENT_INDEX); }
    public void setBookmarkText(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_TEXT_ARGUMENT_INDEX, value); }

    private FieldArgument getBookmarkTextArgument() { return getFieldCodeCache().getArgument(BOOKMARK_TEXT_ARGUMENT_INDEX); }

    private static final int BOOKMARK_NAME_ARGUMENT_INDEX = 0;
    private static final int BOOKMARK_TEXT_ARGUMENT_INDEX = 1;
}

