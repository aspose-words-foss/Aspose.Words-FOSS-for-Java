// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Contains data used to build fake results for LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL fields.
/// </summary>
class FieldNumListLabel
{
    FieldNumListLabel(String text, RunPr runPrOverrides)
    {
        mText = text;
        mRunPrOverrides = runPrOverrides;
    }

    FieldNumListLabel(String text, RunPr runPrOverrides, ListNumberState state)
    {
    	this (text, runPrOverrides);
	
        mState = state.snapshot();
    }

    /// <summary>
    /// A fake result text.
    /// </summary>
    String getText() { return mText; };

    private String mText;

    /// <summary>
    /// Gets a value indicating whether the instance contains a fake result text.
    /// </summary>
    boolean hasText() { return StringUtil.hasChars(getText()); }

    /// <summary>
    /// Font properties' overrides to be applied to a fake result.
    /// </summary>
    RunPr getRunPrOverrides() { return mRunPrOverrides; };

    private RunPr mRunPrOverrides;

    /// <summary>
    /// Gets a value indicating whether the instance contains font properties' overrides.
    /// </summary>
    boolean hasRunPrOverrides() { return (getRunPrOverrides() != null); }

    /// <summary>
    /// Gets an <see cref="ListNumberState"/> object associated with this instance.
    /// </summary>
    ListNumberState getState() { return mState; };

    private ListNumberState mState;
}

