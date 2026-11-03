// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2026 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides configuration flags for the join runs operation.
/// </summary>
public class JoinRunsOptions
{
    /// <summary>
    /// True indicates that the spacing attributes of all runs will be ignored when joining runs with same formatting.
    /// </summary>
    /// <remarks>
    /// The default value is False.
    /// </remarks>
    public boolean getIgnoreSpacing() { return mIgnoreSpacing; }; public void setIgnoreSpacing(boolean value) { mIgnoreSpacing = value; };

    private boolean mIgnoreSpacing;

    /// <summary>
    /// True indicates that the redundant attributes of all runs will be ignored when joining runs with same formatting.
    /// </summary>
    /// <remarks>
    /// Redundant attributes are those attributes that do not affect the run with the given text content.
    /// The default value is False.
    /// </remarks>
    public boolean getIgnoreRedundant() { return mIgnoreRedundant; }; public void setIgnoreRedundant(boolean value) { mIgnoreRedundant = value; };

    private boolean mIgnoreRedundant;

    /// <summary>
    /// True indicates that the insignificant attributes of all runs will be ignored when joining runs with same formatting.
    /// </summary>
    /// <remarks>
    /// Insignificant attributes are those attributes that do not have a noticeable effect on the formatting of a run with the given text content.
    /// The default value is False.
    /// </remarks>
    public boolean getIgnoreInsignificant() { return mIgnoreInsignificant; }; public void setIgnoreInsignificant(boolean value) { mIgnoreInsignificant = value; };

    private boolean mIgnoreInsignificant;
}

