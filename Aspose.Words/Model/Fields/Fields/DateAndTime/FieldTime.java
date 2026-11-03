// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/07/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;


/// <summary>
/// Implements the TIME field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the current date and time.
/// </remarks>
public class FieldTime extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return new FieldUpdateActionApplyResult(this, new DateTimeConstant(fetchDocument().getCurrentDateTimeCache()));
    }

    /*override*/ String getDefaultDateTimeFormat()
    {
        // Can't use .NET's standard specifier here as Word does not have them.
        return FormatterPal.getShortTimePatternCurrent();
    }
}

