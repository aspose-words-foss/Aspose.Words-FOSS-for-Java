// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of the leader line displayed under the tab character.
/// </summary>
public /*enum*/ final class TabLeader
{
    private TabLeader(){}
    
    /// <summary>
    /// No leader line is displayed.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The leader line is made up from dots.
    /// </summary>
    public static final int DOTS = 1;
    /// <summary>
    /// The leader line is made up from dashes.
    /// </summary>
    public static final int DASHES = 2;
    /// <summary>
    /// The leader line is a single line.
    /// </summary>
    public static final int LINE = 3;
    /// <summary>
    /// The leader line is a single thick line.
    /// </summary>
    public static final int HEAVY = 4;
    /// <summary>
    /// The leader line is made up from middle-dots.
    /// </summary>
    public static final int MIDDLE_DOT = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TabLeader value.
    /// </summary>
    public static String getName(/*TabLeader*/int tabLeader)
    {
    	switch (tabLeader)
    	{
    		case NONE: return "NONE";
    		case DOTS: return "DOTS";
    		case DASHES: return "DASHES";
    		case LINE: return "LINE";
    		case HEAVY: return "HEAVY";
    		case MIDDLE_DOT: return "MIDDLE_DOT";
    		default: return "Unknown TabLeader value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabLeader value.
    /// </summary>
    public static String toString(/*TabLeader*/int tabLeader)
    {
    	switch (tabLeader)
    	{
    		case NONE: return "None";
    		case DOTS: return "Dots";
    		case DASHES: return "Dashes";
    		case LINE: return "Line";
    		case HEAVY: return "Heavy";
    		case MIDDLE_DOT: return "MiddleDot";
    		default: return "Unknown TabLeader value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TabLeader name.
    /// </summary>
    public static /*TabLeader*/int fromName(String tabLeaderName)
    {
    	if ("NONE".equals(tabLeaderName)) return NONE;
    	if ("DOTS".equals(tabLeaderName)) return DOTS;
    	if ("DASHES".equals(tabLeaderName)) return DASHES;
    	if ("LINE".equals(tabLeaderName)) return LINE;
    	if ("HEAVY".equals(tabLeaderName)) return HEAVY;
    	if ("MIDDLE_DOT".equals(tabLeaderName)) return MIDDLE_DOT;
    	throw new IllegalArgumentException("Unknown TabLeader name.");
    }
    
    /// <summary>
    /// Returns array of TabLeader values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		DOTS,
    		DASHES,
    		LINE,
    		HEAVY,
    		MIDDLE_DOT,
    	};
    }

    public static final int length = 6;
}

