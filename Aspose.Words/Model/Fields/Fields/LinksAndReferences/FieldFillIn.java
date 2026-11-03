// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the FILLIN field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Prompts the user to enter text.
/// </remarks>
public class FieldFillIn extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return FieldUserPromptingUtil.getFieldUpdateAction(this);
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        return FieldUserPromptingUtil.getSwitchType(switchName);
    }

    /// <summary>
    /// Gets or sets the prompt text (the title of the prompt window).
    /// </summary>
    public String getPromptText() { return FieldUserPromptingUtil.getPromptText(this); }
    public void setPromptText(String value) throws Exception { FieldUserPromptingUtil.setPromptText(this, value); }

    /// <summary>
    /// Gets or sets whether the user response should be recieved once per a mail merge operation.
    /// </summary>
    public boolean getPromptOnceOnMailMerge() { return FieldUserPromptingUtil.getPromptOnceOnMailMerge(this); }
    public void setPromptOnceOnMailMerge(boolean value) throws Exception { FieldUserPromptingUtil.setPromptOnceOnMailMerge(this, value); }

    /// <summary>
    /// Gets or sets default user response (initial value contained in the prompt window).
    /// </summary>
    public String getDefaultResponse() { return FieldUserPromptingUtil.getDefaultResponse(this); }
    public void setDefaultResponse(String value) throws Exception { FieldUserPromptingUtil.setDefaultResponse(this, value); }
}

