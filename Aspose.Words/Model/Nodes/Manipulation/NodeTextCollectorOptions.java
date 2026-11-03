// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class NodeTextCollectorOptions
{
    public NodeTextCollectorOptions()
    {
        isFieldResultMode(false);
        setAllowHiddenText(true);
        setAllowDeletedText(true);
        setAllowInsertedText(true);
        setSkipCommentText(true);
        setSkipFootnoteText(true);
    }

    /// <summary>
    /// True to include field result only.
    /// False to include whole field content: field code, field result and field control characters.
    /// </summary>
    boolean isFieldResultMode() { return mIsFieldResultMode; }; void isFieldResultMode(boolean value) { mIsFieldResultMode = value; };

    private boolean mIsFieldResultMode;

    /// <summary>
    /// True to include hidden inline nodes. False to exclude.
    /// </summary>
    boolean getAllowHiddenText() { return mAllowHiddenText; }; void setAllowHiddenText(boolean value) { mAllowHiddenText = value; };

    private boolean mAllowHiddenText;

    /// <summary>
    /// True to include deleted inline nodes. False to exclude.
    /// </summary>
    boolean getAllowDeletedText() { return mAllowDeletedText; }; void setAllowDeletedText(boolean value) { mAllowDeletedText = value; };

    private boolean mAllowDeletedText;

    /// <summary>
    /// True to include inserted inline nodes. False to exclude.
    /// </summary>
    boolean getAllowInsertedText() { return mAllowInsertedText; }; void setAllowInsertedText(boolean value) { mAllowInsertedText = value; };

    private boolean mAllowInsertedText;

    /// <summary>
    /// Set true to skip comment nodes content. Set false to include it.
    /// </summary>
    boolean getSkipCommentText() { return mSkipCommentText; }; void setSkipCommentText(boolean value) { mSkipCommentText = value; };

    private boolean mSkipCommentText;

    /// <summary>
    /// Set true to skip footnote nodes content. Set false to include it.
    /// </summary>
    boolean getSkipFootnoteText() { return mSkipFootnoteText; }; void setSkipFootnoteText(boolean value) { mSkipFootnoteText = value; };

    private boolean mSkipFootnoteText;
}

