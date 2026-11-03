// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2014 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a run of text with corresponding bidi levels.
/// This information is needed for the Unicode bidirectional algorithm.
/// </summary>
public class BidiSourceRun
{
    public BidiSourceRun(String text)
    {
    	this(text, new BidiLevelList());
	
        // Empty constructor.
    }

    public BidiSourceRun(String text, /*BidiLevel*/int level)
    {
    	this(text, new BidiLevelList(level));
	
        // Empty constructor.
    }

    public BidiSourceRun(String text, BidiLevelList levels)
    {
    	this(text, levels, true, false);
	
        // Empty constructor.
    }

    public BidiSourceRun(String text, boolean hebrewScript, boolean arabicScript)
    {
    	this(text, new BidiLevelList(), hebrewScript, arabicScript);
	
        // Empty constructor.
    }

    public BidiSourceRun(String text, BidiLevelList levels, boolean useHebrewNumberSeparators, boolean ignoreBidiTypeET)
    {
        Debug.assertTrue(levels != null);

        mText = text;
        mLevels = levels;
        mUseHebrewNumberSeparators = useHebrewNumberSeparators;
        mIgnoreBidiTypeET = ignoreBidiTypeET;
    }

    public String getText() { return mText; }

    /// <summary>
    /// Nested bidi levels opened for this run. From outermost to innermost.
    /// </summary>
    public BidiLevelList getLevels() { return mLevels; }

    /// <summary>
    /// Indicates whether characters '+', '-' and '/' should be treated as number separators in RTL paragraphs.
    /// </summary>
    public boolean getUseHebrewNumberSeparators() { return mUseHebrewNumberSeparators; }

    /// <summary>
    /// Indicates whether characters of the <see cref="BidiCharacterType.ET"/> type
    /// should be treated as <see cref="BidiCharacterType.ON"/> characters in RTL paragraphs.
    /// </summary>
    public boolean getIgnoreBidiTypeET() { return mIgnoreBidiTypeET; }

    private /*final*/ String mText;
    private /*final*/ BidiLevelList mLevels;
    private /*final*/ boolean mUseHebrewNumberSeparators;
    private /*final*/ boolean mIgnoreBidiTypeET;
}
