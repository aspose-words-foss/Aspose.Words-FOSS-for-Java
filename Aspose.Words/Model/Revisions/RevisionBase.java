// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Common base for all revisions.
/// </summary>
class RevisionBase
{
    RevisionBase(String author, DateTime dateTime)
    {
        setAuthor(author);
        setDateTime(dateTime);
    }
    /// <summary>
    /// Cannot be null.
    /// </summary>
    String getAuthor() { return mAuthor; }; void setAuthor(String value) { mAuthor = value; };

    private String mAuthor;

    DateTime getDateTime() { return mDateTime; }; void setDateTime(DateTime value) { mDateTime = value; };

    private DateTime mDateTime;
}

