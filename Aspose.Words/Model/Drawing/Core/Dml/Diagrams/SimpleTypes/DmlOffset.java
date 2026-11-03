// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2014 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.47 ST_Offset (Offset)
/// </summary>
/*enum*/ final class DmlOffset
{
    private DmlOffset(){}
    
    public static final int CENTER = 0;
    public static final int OFFSET = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlOffset value.
    /// </summary>
    public static String getName(/*DmlOffset*/int dmlOffset)
    {
    	switch (dmlOffset)
    	{
    		case CENTER: return "CENTER";
    		case OFFSET: return "OFFSET";
    		default: return "Unknown DmlOffset value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlOffset value.
    /// </summary>
    public static String toString(/*DmlOffset*/int dmlOffset)
    {
    	switch (dmlOffset)
    	{
    		case CENTER: return "Center";
    		case OFFSET: return "Offset";
    		default: return "Unknown DmlOffset value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlOffset name.
    /// </summary>
    public static /*DmlOffset*/int fromName(String dmlOffsetName)
    {
    	if ("CENTER".equals(dmlOffsetName)) return CENTER;
    	if ("OFFSET".equals(dmlOffsetName)) return OFFSET;
    	throw new IllegalArgumentException("Unknown DmlOffset name.");
    }
    
    /// <summary>
    /// Returns array of DmlOffset values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		OFFSET,
    	};
    }

    public static final int length = 2;
}

