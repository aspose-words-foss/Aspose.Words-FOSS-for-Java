// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents a 'reactionInfo' element of the CT_CommentReactionInfo complex type defined in §2.1.3.2 of
/// [MS-OREACTXML] that specifies the user who added the reaction and the time when the reaction was added.
/// </summary>
class CommentReactionInfo
{
    /// <summary>
    /// Specifies the provider issued ID for the user.
    /// </summary>
    String getUserId() { return mUserId; }; void setUserId(String value) { mUserId = value; };

    private String mUserId;

    /// <summary>
    /// Specifies the display name of the user.
    /// </summary>
    String getUserName() { return mUserName; }; void setUserName(String value) { mUserName = value; };

    private String mUserName;

    /// <summary>
    /// Specifies the provider that produced the <see cref="UserId"/>.
    /// </summary>
    String getUserProvider() { return mUserProvider; }; void setUserProvider(String value) { mUserProvider = value; };

    private String mUserProvider;

    /// <summary>
    /// Specifies date information when the reaction was added, the value is defined to be in the UTC time zone.
    /// </summary>
    DateTime getDateTimeUtc() { return mDateTimeUtc; }; void setDateTimeUtc(DateTime value) { mDateTimeUtc = value; };

    private DateTime mDateTimeUtc;
}

