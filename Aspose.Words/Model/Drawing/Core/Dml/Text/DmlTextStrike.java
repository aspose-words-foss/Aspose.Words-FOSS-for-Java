// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Types of strike.
/// </summary>
/*enum*/ final class DmlTextStrike
{
    private DmlTextStrike(){}
    
    public static final int NO = 0;
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextStrike value.
    /// </summary>
    public static String getName(/*DmlTextStrike*/int dmlTextStrike)
    {
    	switch (dmlTextStrike)
    	{
    		case NO: return "NO";
    		case SINGLE: return "SINGLE";
    		case DOUBLE: return "DOUBLE";
    		default: return "Unknown DmlTextStrike value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextStrike value.
    /// </summary>
    public static String toString(/*DmlTextStrike*/int dmlTextStrike)
    {
    	switch (dmlTextStrike)
    	{
    		case NO: return "No";
    		case SINGLE: return "Single";
    		case DOUBLE: return "Double";
    		default: return "Unknown DmlTextStrike value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextStrike name.
    /// </summary>
    public static /*DmlTextStrike*/int fromName(String dmlTextStrikeName)
    {
    	if ("NO".equals(dmlTextStrikeName)) return NO;
    	if ("SINGLE".equals(dmlTextStrikeName)) return SINGLE;
    	if ("DOUBLE".equals(dmlTextStrikeName)) return DOUBLE;
    	throw new IllegalArgumentException("Unknown DmlTextStrike name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextStrike values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NO,
    		SINGLE,
    		DOUBLE,
    	};
    }

    public static final int length = 3;
}
