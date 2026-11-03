// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the GOTOBUTTON field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts a jump command, such that when it is activated, the insertion point of the document is
/// moved to the specified location.
/// </remarks>
public class FieldGoToButton extends Field
{
    /*override*/ NodeRange getFakeResult() throws Exception
    {
        FieldArgument text = getFieldCodeCache().getArgument(DISPLAY_TEXT_ARGUMENT_INDEX);

        return FieldButtonFakeResultBuilder.buildFieldButtonFakeResult(text);
    }

    /// <summary>
    /// Gets or sets the name of a bookmark, a page number, or some other item to jump to.
    /// </summary>
    public String getLocation() { return getFieldCodeCache().getArgumentAsString(LOCATION_ARGUMENT_INDEX); }
    public void setLocation(String value) throws Exception { getFieldCodeCache().setArgument(LOCATION_ARGUMENT_INDEX, value);}

    /// <summary>
    /// Gets or sets the text of the "button" that appears in the document, such that it can be selected to activate the jump.
    /// </summary>
    public String getDisplayText() { return getFieldCodeCache().getArgumentAsString(DISPLAY_TEXT_ARGUMENT_INDEX); }
    public void setDisplayText(String value) throws Exception { getFieldCodeCache().setArgument(DISPLAY_TEXT_ARGUMENT_INDEX, value); }

    private static final int LOCATION_ARGUMENT_INDEX = 0;
    private static final int DISPLAY_TEXT_ARGUMENT_INDEX = 1;
}

