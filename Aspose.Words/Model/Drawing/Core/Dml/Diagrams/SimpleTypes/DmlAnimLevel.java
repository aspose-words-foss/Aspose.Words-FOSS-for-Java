// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.2 ST_AnimLvlStr (Animation Level String Definition)
/// </summary>
/*enum*/ final class DmlAnimLevel
{
    private DmlAnimLevel(){}
    
    public static final int NONE = 0;
    public static final int LEVEL = 1;
    public static final int CENTER = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAnimLevel value.
    /// </summary>
    public static String getName(/*DmlAnimLevel*/int dmlAnimLevel)
    {
    	switch (dmlAnimLevel)
    	{
    		case NONE: return "NONE";
    		case LEVEL: return "LEVEL";
    		case CENTER: return "CENTER";
    		default: return "Unknown DmlAnimLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAnimLevel value.
    /// </summary>
    public static String toString(/*DmlAnimLevel*/int dmlAnimLevel)
    {
    	switch (dmlAnimLevel)
    	{
    		case NONE: return "None";
    		case LEVEL: return "Level";
    		case CENTER: return "Center";
    		default: return "Unknown DmlAnimLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAnimLevel name.
    /// </summary>
    public static /*DmlAnimLevel*/int fromName(String dmlAnimLevelName)
    {
    	if ("NONE".equals(dmlAnimLevelName)) return NONE;
    	if ("LEVEL".equals(dmlAnimLevelName)) return LEVEL;
    	if ("CENTER".equals(dmlAnimLevelName)) return CENTER;
    	throw new IllegalArgumentException("Unknown DmlAnimLevel name.");
    }
    
    /// <summary>
    /// Returns array of DmlAnimLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		LEVEL,
    		CENTER,
    	};
    }

    public static final int length = 3;
}

