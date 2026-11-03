// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the separator character that appears between the chapter and page number.
/// </summary>
/// <seealso cref="PageSetup"/>
/// <seealso cref="PageSetup.ChapterPageSeparator"/>
public /*enum*/ final class ChapterPageSeparator
{
    private ChapterPageSeparator(){}
    
    /// <summary>
    /// A colon.
    /// </summary>
    public static final int HYPHEN = 0;
    /// <summary>
    /// A period.
    /// </summary>
    public static final int PERIOD = 1;
    /// <summary>
    /// A colon.
    /// </summary>
    public static final int COLON = 2;
    /// <summary>
    /// An emphasized dash.
    /// </summary>
    public static final int EM_DASH = 3;
    /// <summary>
    /// A standard dash.
    /// </summary>
    public static final int EN_DASH = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ChapterPageSeparator value.
    /// </summary>
    public static String getName(/*ChapterPageSeparator*/int chapterPageSeparator)
    {
    	switch (chapterPageSeparator)
    	{
    		case HYPHEN: return "HYPHEN";
    		case PERIOD: return "PERIOD";
    		case COLON: return "COLON";
    		case EM_DASH: return "EM_DASH";
    		case EN_DASH: return "EN_DASH";
    		default: return "Unknown ChapterPageSeparator value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ChapterPageSeparator value.
    /// </summary>
    public static String toString(/*ChapterPageSeparator*/int chapterPageSeparator)
    {
    	switch (chapterPageSeparator)
    	{
    		case HYPHEN: return "Hyphen";
    		case PERIOD: return "Period";
    		case COLON: return "Colon";
    		case EM_DASH: return "EmDash";
    		case EN_DASH: return "EnDash";
    		default: return "Unknown ChapterPageSeparator value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ChapterPageSeparator name.
    /// </summary>
    public static /*ChapterPageSeparator*/int fromName(String chapterPageSeparatorName)
    {
    	if ("HYPHEN".equals(chapterPageSeparatorName)) return HYPHEN;
    	if ("PERIOD".equals(chapterPageSeparatorName)) return PERIOD;
    	if ("COLON".equals(chapterPageSeparatorName)) return COLON;
    	if ("EM_DASH".equals(chapterPageSeparatorName)) return EM_DASH;
    	if ("EN_DASH".equals(chapterPageSeparatorName)) return EN_DASH;
    	throw new IllegalArgumentException("Unknown ChapterPageSeparator name.");
    }
    
    /// <summary>
    /// Returns array of ChapterPageSeparator values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HYPHEN,
    		PERIOD,
    		COLON,
    		EM_DASH,
    		EN_DASH,
    	};
    }

    public static final int length = 5;
}

