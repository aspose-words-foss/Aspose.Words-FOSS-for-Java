// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2012 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements the PAGE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the number of the current page.
/// </remarks>
public class FieldPage extends Field
{
    /*override*/ Section getPageNumberFormatSection() throws Exception
    {
        // WORDSNET-4764 Parent section from the document object module is not always correct.
        // If the field is in a header/footer and the next section inherits this header/footer,
        // such field will still have the original parent section in the document object model.
        // Use the section set by Layout engine.
        return getUpdater().getDisplayContext().getSection(getStart());
    }
}

