// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the MACROBUTTON field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Allows a macro or command to be run.</p>
/// <p>In Aspose.Words this field can also act as a merge field.</p>
/// </remarks>
public class FieldMacroButton extends Field implements IMergeFieldSurrogate
{
    /*override*/ NodeRange getFakeResult() throws Exception
    {
        FieldArgument text = getFieldCodeCache().getArgument(DISPLAY_TEXT_ARGUMENT_INDEX);

        return FieldButtonFakeResultBuilder.buildFieldButtonFakeResult(text);
    }

    @ReservedForInternalUse @Deprecated
    public String /*IMergeFieldSurrogate.*/getMergeFieldName()
    {
        return getDisplayText();
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/canWorkAsMergeField()
    {
        return false;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/isMergeValueRequired()
    {
        return false;
    }

    /// <summary>
    /// Gets or sets the name of the macro or command to run.
    /// </summary>
    public String getMacroName() { return getFieldCodeCache().getArgumentAsString(MACRO_NAME_ARGUMENT_INDEX); }
    public void setMacroName(String value) throws Exception { getFieldCodeCache().setArgument(MACRO_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the text to appear as the "button" that is selected to run the macro or command.
    /// </summary>
    public String getDisplayText() { return getFieldCodeCache().getArgumentAsString(DISPLAY_TEXT_ARGUMENT_INDEX); }
    public void setDisplayText(String value) throws Exception { getFieldCodeCache().setArgument(DISPLAY_TEXT_ARGUMENT_INDEX, value);}

    private static final int MACRO_NAME_ARGUMENT_INDEX = 0;

    private static final int DISPLAY_TEXT_ARGUMENT_INDEX = 1;
}

