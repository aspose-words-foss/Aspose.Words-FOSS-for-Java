// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2015 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.5 ST_AutoTextRotation (Auto Text Rotation)
/// </summary>
/*enum*/ final class DmlAutoTextRotation
{
    private DmlAutoTextRotation(){}
    
    public static final int NONE = 0;
    public static final int GRAVITY = 1;
    public static final int UPRIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAutoTextRotation value.
    /// </summary>
    public static String getName(/*DmlAutoTextRotation*/int dmlAutoTextRotation)
    {
    	switch (dmlAutoTextRotation)
    	{
    		case NONE: return "NONE";
    		case GRAVITY: return "GRAVITY";
    		case UPRIGHT: return "UPRIGHT";
    		default: return "Unknown DmlAutoTextRotation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAutoTextRotation value.
    /// </summary>
    public static String toString(/*DmlAutoTextRotation*/int dmlAutoTextRotation)
    {
    	switch (dmlAutoTextRotation)
    	{
    		case NONE: return "None";
    		case GRAVITY: return "Gravity";
    		case UPRIGHT: return "Upright";
    		default: return "Unknown DmlAutoTextRotation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAutoTextRotation name.
    /// </summary>
    public static /*DmlAutoTextRotation*/int fromName(String dmlAutoTextRotationName)
    {
    	if ("NONE".equals(dmlAutoTextRotationName)) return NONE;
    	if ("GRAVITY".equals(dmlAutoTextRotationName)) return GRAVITY;
    	if ("UPRIGHT".equals(dmlAutoTextRotationName)) return UPRIGHT;
    	throw new IllegalArgumentException("Unknown DmlAutoTextRotation name.");
    }
    
    /// <summary>
    /// Returns array of DmlAutoTextRotation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		GRAVITY,
    		UPRIGHT,
    	};
    }

    public static final int length = 3;
}

