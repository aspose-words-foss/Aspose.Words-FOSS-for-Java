// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of vertical overflow.
/// </summary>
/*enum*/ final class DmlTextVerticalOverflowType
{
    private DmlTextVerticalOverflowType(){}
    
    public static final int OVERFLOW = 0;
    public static final int ELLIPSIS = 1;
    public static final int CLIP = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextVerticalOverflowType value.
    /// </summary>
    public static String getName(/*DmlTextVerticalOverflowType*/int dmlTextVerticalOverflowType)
    {
    	switch (dmlTextVerticalOverflowType)
    	{
    		case OVERFLOW: return "OVERFLOW";
    		case ELLIPSIS: return "ELLIPSIS";
    		case CLIP: return "CLIP";
    		default: return "Unknown DmlTextVerticalOverflowType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextVerticalOverflowType value.
    /// </summary>
    public static String toString(/*DmlTextVerticalOverflowType*/int dmlTextVerticalOverflowType)
    {
    	switch (dmlTextVerticalOverflowType)
    	{
    		case OVERFLOW: return "Overflow";
    		case ELLIPSIS: return "Ellipsis";
    		case CLIP: return "Clip";
    		default: return "Unknown DmlTextVerticalOverflowType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextVerticalOverflowType name.
    /// </summary>
    public static /*DmlTextVerticalOverflowType*/int fromName(String dmlTextVerticalOverflowTypeName)
    {
    	if ("OVERFLOW".equals(dmlTextVerticalOverflowTypeName)) return OVERFLOW;
    	if ("ELLIPSIS".equals(dmlTextVerticalOverflowTypeName)) return ELLIPSIS;
    	if ("CLIP".equals(dmlTextVerticalOverflowTypeName)) return CLIP;
    	throw new IllegalArgumentException("Unknown DmlTextVerticalOverflowType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextVerticalOverflowType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OVERFLOW,
    		ELLIPSIS,
    		CLIP,
    	};
    }

    public static final int length = 3;
}
