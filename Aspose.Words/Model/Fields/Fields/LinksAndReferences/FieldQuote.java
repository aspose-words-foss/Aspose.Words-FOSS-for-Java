// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/02/2012 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the QUOTE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the specified text.
/// </remarks>
public class FieldQuote extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        FieldQuoteUpdater updater = new FieldQuoteUpdater(this);
        return updater.update();
    }

    /// <summary>
    /// Gets or sets the text to retrieve.
    /// </summary>
    public String getText() { return getFieldCodeCache().getArgumentAsString(TEXT_ARGUMENT_INDEX); }
    public void setText(String value) throws Exception { getFieldCodeCache().setArgument(TEXT_ARGUMENT_INDEX, value); }

    private static final int TEXT_ARGUMENT_INDEX = 0;
}

