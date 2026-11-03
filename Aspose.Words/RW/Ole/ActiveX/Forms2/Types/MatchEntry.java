// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how a ListBox or ComboBox searches its list as the user types.
/// </summary>
/*enum*/ final class MatchEntry
{
    private MatchEntry(){}
    
    public static final int FIRST_LETTER = 0;
    public static final int COMPLETE = 1;
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MatchEntry value.
    /// </summary>
    public static String getName(/*MatchEntry*/int matchEntry)
    {
    	switch (matchEntry)
    	{
    		case FIRST_LETTER: return "FIRST_LETTER";
    		case COMPLETE: return "COMPLETE";
    		case NONE: return "NONE";
    		default: return "Unknown MatchEntry value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MatchEntry value.
    /// </summary>
    public static String toString(/*MatchEntry*/int matchEntry)
    {
    	switch (matchEntry)
    	{
    		case FIRST_LETTER: return "FirstLetter";
    		case COMPLETE: return "Complete";
    		case NONE: return "None";
    		default: return "Unknown MatchEntry value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MatchEntry name.
    /// </summary>
    public static /*MatchEntry*/int fromName(String matchEntryName)
    {
    	if ("FIRST_LETTER".equals(matchEntryName)) return FIRST_LETTER;
    	if ("COMPLETE".equals(matchEntryName)) return COMPLETE;
    	if ("NONE".equals(matchEntryName)) return NONE;
    	throw new IllegalArgumentException("Unknown MatchEntry name.");
    }
    
    /// <summary>
    /// Returns array of MatchEntry values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIRST_LETTER,
    		COMPLETE,
    		NONE,
    	};
    }

    public static final int length = 3;
}

