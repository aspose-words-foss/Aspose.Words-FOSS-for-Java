// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to draw an error bar.
/// Corresponds ST_ErrBarType simple type (5.7.3.12).
/// </summary>
/*enum*/ final class ErrorBarType
{
    private ErrorBarType(){}
    
    /// <summary>
    /// Specifies that error bars shall be shown in the positive and negative directions.
    /// </summary>
    public static final int BOTH = 0;
    /// <summary>
    /// Specifies that error bars shall be shown in the negative direction only.
    /// </summary>
    public static final int MINUS = 1;
    /// <summary>
    /// Specifies that error bars shall be shown in the positive direction only.
    /// </summary>
    public static final int PLUS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ErrorBarType value.
    /// </summary>
    public static String getName(/*ErrorBarType*/int errorBarType)
    {
    	switch (errorBarType)
    	{
    		case BOTH: return "BOTH";
    		case MINUS: return "MINUS";
    		case PLUS: return "PLUS";
    		default: return "Unknown ErrorBarType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ErrorBarType value.
    /// </summary>
    public static String toString(/*ErrorBarType*/int errorBarType)
    {
    	switch (errorBarType)
    	{
    		case BOTH: return "Both";
    		case MINUS: return "Minus";
    		case PLUS: return "Plus";
    		default: return "Unknown ErrorBarType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ErrorBarType name.
    /// </summary>
    public static /*ErrorBarType*/int fromName(String errorBarTypeName)
    {
    	if ("BOTH".equals(errorBarTypeName)) return BOTH;
    	if ("MINUS".equals(errorBarTypeName)) return MINUS;
    	if ("PLUS".equals(errorBarTypeName)) return PLUS;
    	throw new IllegalArgumentException("Unknown ErrorBarType name.");
    }
    
    /// <summary>
    /// Returns array of ErrorBarType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTH,
    		MINUS,
    		PLUS,
    	};
    }

    public static final int length = 3;
}

