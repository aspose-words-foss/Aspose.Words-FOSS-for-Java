// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the AUTOTEXT field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts an AutoText entry.
/// </remarks>
public class FieldAutoText extends Field implements IFieldAutoTextCode
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return FieldAutoTextUpdater.update(this);
    }

    /// <summary>
    /// Gets or sets the name of the AutoText entry.
    /// </summary>
    public String getEntryName() { return getFieldCodeCache().getArgumentAsString(ENTRY_NAME_ARGUMENT_INDEX); }
    public void setEntryName(String value) throws Exception { getFieldCodeCache().setArgument(ENTRY_NAME_ARGUMENT_INDEX, value); }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldAutoTextCode.EntryName

    private static final int ENTRY_NAME_ARGUMENT_INDEX = 0;
}

