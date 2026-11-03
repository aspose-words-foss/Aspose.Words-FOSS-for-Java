// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Compression state with which the picture is stored.
/// </summary>
/*enum*/ final class DmlCompressionState
{
    private DmlCompressionState(){}
    
    public static final int EMAIL = 0;
    public static final int SCREEN = 1;
    public static final int PRINT = 2;
    public static final int HIGH_QUALITY_PRINT = 3;
    public static final int NONE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlCompressionState value.
    /// </summary>
    public static String getName(/*DmlCompressionState*/int dmlCompressionState)
    {
    	switch (dmlCompressionState)
    	{
    		case EMAIL: return "EMAIL";
    		case SCREEN: return "SCREEN";
    		case PRINT: return "PRINT";
    		case HIGH_QUALITY_PRINT: return "HIGH_QUALITY_PRINT";
    		case NONE: return "NONE";
    		default: return "Unknown DmlCompressionState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlCompressionState value.
    /// </summary>
    public static String toString(/*DmlCompressionState*/int dmlCompressionState)
    {
    	switch (dmlCompressionState)
    	{
    		case EMAIL: return "Email";
    		case SCREEN: return "Screen";
    		case PRINT: return "Print";
    		case HIGH_QUALITY_PRINT: return "HighQualityPrint";
    		case NONE: return "None";
    		default: return "Unknown DmlCompressionState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlCompressionState name.
    /// </summary>
    public static /*DmlCompressionState*/int fromName(String dmlCompressionStateName)
    {
    	if ("EMAIL".equals(dmlCompressionStateName)) return EMAIL;
    	if ("SCREEN".equals(dmlCompressionStateName)) return SCREEN;
    	if ("PRINT".equals(dmlCompressionStateName)) return PRINT;
    	if ("HIGH_QUALITY_PRINT".equals(dmlCompressionStateName)) return HIGH_QUALITY_PRINT;
    	if ("NONE".equals(dmlCompressionStateName)) return NONE;
    	throw new IllegalArgumentException("Unknown DmlCompressionState name.");
    }
    
    /// <summary>
    /// Returns array of DmlCompressionState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMAIL,
    		SCREEN,
    		PRINT,
    		HIGH_QUALITY_PRINT,
    		NONE,
    	};
    }

    public static final int length = 5;
}
