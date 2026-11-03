// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/08/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Common interface for the <see cref="FootnoteOptions"/> and <see cref="EndnoteOptions"/> classes.
/// </summary>
interface IFootnoteOptions
{
    /// <summary>
    /// Specifies the number format for automatically numbered footnotes or endnotes.
    /// </summary>
    public /*NumberStyle*/int getNumberStyle(); public void setNumberStyle(/*NumberStyle*/int value);

    /// <summary>
    /// Determines when automatic numbering restarts.
    /// </summary>
    public /*FootnoteNumberingRule*/int getRestartRule(); public void setRestartRule(/*FootnoteNumberingRule*/int value);

    /// <summary>
    /// Specifies the starting number or character for the first automatically numbered footnotes or endnotes.
    /// </summary>
    public int getStartNumber(); public void setStartNumber(int value);

    /// <summary>
    /// Specifies the footnotes or endnotes position.
    /// </summary>
    public /*FootnoteLocation*/int getLocation(); public void setLocation(/*FootnoteLocation*/int value);
}

