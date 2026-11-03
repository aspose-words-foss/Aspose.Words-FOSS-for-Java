// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Utility class to store comment information related to commentsIds and commentsExtensible document parts.
/// </summary>
class CommentExtensible
{
    /// <summary>
    /// Ctor to create instance of this class.
    /// </summary>
    CommentExtensible(int paraId, int durableId)
    {
        setParaId(paraId);
        setDurableId(durableId);
    }

    /// <summary>
    /// Paragraph Id of last paragraph of the comment.
    /// </summary>
    int getParaId() { return mParaId; }; void setParaId(int value) { mParaId = value; };

    private int mParaId;

    /// <summary>
    /// Comment durable identifier.
    /// </summary>
    int getDurableId() { return mDurableId; }; void setDurableId(int value) { mDurableId = value; };

    private int mDurableId;

    /// <summary>
    /// Gets or sets the UTC date and time that the comment was made.
    /// </summary>
    DateTime getUtcDateTime() { return mUtcDateTime; }
    void setUtcDateTime(DateTime value) { mUtcDateTime = value; }

    /// <summary>
    /// When <c>true</c> specifies that the comment is a follow-up.
    /// </summary>
    /// <remarks>
    /// The default value is <c>false</c>.
    /// The attribute MUST NOT be present on comments that are replies as specified by the paraIdParent attribute
    /// of an associated commentEx element.
    /// When the property is <c>true</c>, the Office 365 version of Word ignores the content of the comment. Other
    /// versions of Word treat the comment as a regular comment.
    /// </remarks>
    boolean isIntelligentPlaceholder() { return mIsIntelligentPlaceholder; }; void isIntelligentPlaceholder(boolean value) { mIsIntelligentPlaceholder = value; };

    private boolean mIsIntelligentPlaceholder;

    /// <summary>
    /// Gets or sets a collection of reactions to the comment.
    /// </summary>
    CommentReactionCollection getReactions() { return mReactions; }; void setReactions(CommentReactionCollection value) { mReactions = value; };

    private CommentReactionCollection mReactions;

    private DateTime mUtcDateTime = DateTime.MinValue; // Init for Java.
}

