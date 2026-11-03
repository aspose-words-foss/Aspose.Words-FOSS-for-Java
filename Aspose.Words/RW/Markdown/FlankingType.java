// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible flanking types of the <see cref="Delimiter"/> object.
/// </summary>
/// <remarks>
/// See an algorithm for parsing nested emphasis and links at https://spec.commonmark.org
/// </remarks>
/*enum*/ final class FlankingType
{
    private FlankingType(){}
    
    public static final int NONE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTH = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FlankingType value.
    /// </summary>
    public static String getName(/*FlankingType*/int flankingType)
    {
    	switch (flankingType)
    	{
    		case NONE: return "NONE";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case BOTH: return "BOTH";
    		default: return "Unknown FlankingType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FlankingType value.
    /// </summary>
    public static String toString(/*FlankingType*/int flankingType)
    {
    	switch (flankingType)
    	{
    		case NONE: return "None";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case BOTH: return "Both";
    		default: return "Unknown FlankingType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FlankingType name.
    /// </summary>
    public static /*FlankingType*/int fromName(String flankingTypeName)
    {
    	if ("NONE".equals(flankingTypeName)) return NONE;
    	if ("LEFT".equals(flankingTypeName)) return LEFT;
    	if ("RIGHT".equals(flankingTypeName)) return RIGHT;
    	if ("BOTH".equals(flankingTypeName)) return BOTH;
    	throw new IllegalArgumentException("Unknown FlankingType name.");
    }
    
    /// <summary>
    /// Returns array of FlankingType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		LEFT,
    		RIGHT,
    		BOTH,
    	};
    }

    public static final int length = 4;
}

