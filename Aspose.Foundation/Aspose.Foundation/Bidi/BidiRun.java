// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A piece of text that the Unicode bidirectional algorithm resolved to one embedding level.
/// </summary>
public class BidiRun
{
    public BidiRun(String text, int level)
    {
    	this(text, level, 0, false);
	
        // Empty constructor.
    }

    /// <param name="text">The text of the run.</param>
    /// <param name="level">The embedding level of the run. Odd levels are right-to-left.</param>
    /// <param name="sourceRunIndex">The index of the <see cref="BidiSourceRun"/> the text comes from.</param>
    /// <param name="requiresHebrewLocaleBi">See <see cref="RequiresHebrewLocaleBi"/>.</param>
    public BidiRun(String text, int level, int sourceRunIndex, boolean requiresHebrewLocaleBi)
    {
        mText = text;
        mLevel = level;
        mSourceRunIndex = sourceRunIndex;
        mRequiresHebrewLocaleBi = requiresHebrewLocaleBi;
    }

    /// <summary>
    /// The text of the run.
    /// </summary>
    public String getText() { return mText; }

    /// <summary>
    /// The embedding level of the run.
    /// </summary>
    public int getLevel() { return mLevel; }

    /// <summary>
    /// Indicates whether the run is right-to-left, that is, whether its embedding level is odd.
    /// </summary>
    public boolean getRtl() { return (mLevel % 2) != 0; }

    /// <summary>
    /// The index of the <see cref="BidiSourceRun"/> the text of the run comes from.
    /// </summary>
    public int getSourceRunIndex() { return mSourceRunIndex; }

    /// <summary>
    /// Indicates that the run is a right-to-left number that contains '+', '-' or '/'.
    /// </summary>
    /// <remarks>
    /// MS Word treats these characters as parts of a number only in runs whose bidi language is Hebrew
    /// (ISO/IEC 29500, Annex I, the 'lang' element), so such a run needs that language to keep the number together.
    /// </remarks>
    public boolean getRequiresHebrewLocaleBi() { return mRequiresHebrewLocaleBi; }

    private /*final*/ String mText;
    private /*final*/ int mLevel;
    private /*final*/ int mSourceRunIndex;
    private /*final*/ boolean mRequiresHebrewLocaleBi;
}

