// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/05/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents an edit session i.e Author and DateTime stored with changes made while <see cref="Document.TrackRevisions"/> is set.
/// </summary>
class EditSession
{
    /// <summary>
    /// Creates edit session using given author and time.
    /// </summary>
    EditSession(String author, DateTime dateTime)
    {
        mAuthor = author;
        mDateTime = dateTime;
    }

    /// <summary>
    /// Revision tracking author.
    /// </summary>
    String getAuthor() { return mAuthor; }
    void setAuthor(String value) { mAuthor = value; }

    /// <summary>
    /// Revision tracking date/time.
    /// </summary>
    DateTime getDateTime() { return mDateTime; }

    private String mAuthor;
    private /*final*/ DateTime mDateTime;
}

