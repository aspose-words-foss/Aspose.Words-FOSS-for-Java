// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to determine the length of the error bars
/// Corresponds ST_ErrValType simple type (5.7.3.14).
/// </summary>
/*enum*/ final class ErrorValueType
{
    private ErrorValueType(){}
    
    /// <summary>
    /// Specifies that the length of the error bars shall be determined by the Plus and Minus elements.
    /// </summary>
    public static final int CUSTOM_ERROR_BARS = 0;
    /// <summary>
    /// Specifies that the length of the error bars shall be the fixed value determined by Error Bar Value.
    /// </summary>
    public static final int FIXED_VALUE = 1;
    /// <summary>
    /// Specifies that the length of the error bars shall be Error Bar Value percent of the data.
    /// </summary>
    public static final int PERCENTAGE = 2;
    /// <summary>
    /// Specifies that the length of the error bars shall be Error Bar Value standard deviations of the data.
    /// </summary>
    public static final int STANDARD_DEVIATION = 3;
    /// <summary>
    /// Specifies that the length of the error bars shall be Error Bar Value standard errors of the data.
    /// </summary>
    public static final int STANDARD_ERROR = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ErrorValueType value.
    /// </summary>
    public static String getName(/*ErrorValueType*/int errorValueType)
    {
    	switch (errorValueType)
    	{
    		case CUSTOM_ERROR_BARS: return "CUSTOM_ERROR_BARS";
    		case FIXED_VALUE: return "FIXED_VALUE";
    		case PERCENTAGE: return "PERCENTAGE";
    		case STANDARD_DEVIATION: return "STANDARD_DEVIATION";
    		case STANDARD_ERROR: return "STANDARD_ERROR";
    		default: return "Unknown ErrorValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ErrorValueType value.
    /// </summary>
    public static String toString(/*ErrorValueType*/int errorValueType)
    {
    	switch (errorValueType)
    	{
    		case CUSTOM_ERROR_BARS: return "CustomErrorBars";
    		case FIXED_VALUE: return "FixedValue";
    		case PERCENTAGE: return "Percentage";
    		case STANDARD_DEVIATION: return "StandardDeviation";
    		case STANDARD_ERROR: return "StandardError";
    		default: return "Unknown ErrorValueType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ErrorValueType name.
    /// </summary>
    public static /*ErrorValueType*/int fromName(String errorValueTypeName)
    {
    	if ("CUSTOM_ERROR_BARS".equals(errorValueTypeName)) return CUSTOM_ERROR_BARS;
    	if ("FIXED_VALUE".equals(errorValueTypeName)) return FIXED_VALUE;
    	if ("PERCENTAGE".equals(errorValueTypeName)) return PERCENTAGE;
    	if ("STANDARD_DEVIATION".equals(errorValueTypeName)) return STANDARD_DEVIATION;
    	if ("STANDARD_ERROR".equals(errorValueTypeName)) return STANDARD_ERROR;
    	throw new IllegalArgumentException("Unknown ErrorValueType name.");
    }
    
    /// <summary>
    /// Returns array of ErrorValueType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CUSTOM_ERROR_BARS,
    		FIXED_VALUE,
    		PERCENTAGE,
    		STANDARD_DEVIATION,
    		STANDARD_ERROR,
    	};
    }

    public static final int length = 5;
}

