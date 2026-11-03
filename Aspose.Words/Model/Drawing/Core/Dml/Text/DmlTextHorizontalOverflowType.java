// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text horizontal overflow Types
/// </summary>
/*enum*/ final class DmlTextHorizontalOverflowType
{
    private DmlTextHorizontalOverflowType(){}
    
    public static final int OVERFLOW = 0;
    public static final int CLIP = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextHorizontalOverflowType value.
    /// </summary>
    public static String getName(/*DmlTextHorizontalOverflowType*/int dmlTextHorizontalOverflowType)
    {
    	switch (dmlTextHorizontalOverflowType)
    	{
    		case OVERFLOW: return "OVERFLOW";
    		case CLIP: return "CLIP";
    		default: return "Unknown DmlTextHorizontalOverflowType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextHorizontalOverflowType value.
    /// </summary>
    public static String toString(/*DmlTextHorizontalOverflowType*/int dmlTextHorizontalOverflowType)
    {
    	switch (dmlTextHorizontalOverflowType)
    	{
    		case OVERFLOW: return "Overflow";
    		case CLIP: return "Clip";
    		default: return "Unknown DmlTextHorizontalOverflowType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextHorizontalOverflowType name.
    /// </summary>
    public static /*DmlTextHorizontalOverflowType*/int fromName(String dmlTextHorizontalOverflowTypeName)
    {
    	if ("OVERFLOW".equals(dmlTextHorizontalOverflowTypeName)) return OVERFLOW;
    	if ("CLIP".equals(dmlTextHorizontalOverflowTypeName)) return CLIP;
    	throw new IllegalArgumentException("Unknown DmlTextHorizontalOverflowType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextHorizontalOverflowType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OVERFLOW,
    		CLIP,
    	};
    }

    public static final int length = 2;
}
