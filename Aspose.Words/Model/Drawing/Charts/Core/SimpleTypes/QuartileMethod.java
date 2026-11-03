// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies quartile calculation methods.
/// Corresponds to the 2.24.4.16 ST_QuartileMethod simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class QuartileMethod
{
    private QuartileMethod(){}
    
    /// <summary>
    /// The quartile calculation includes the median when splitting the dataset into quartiles.
    /// </summary>
    public static final int INCLUSIVE_MEDIAN = 0;

    /// <summary>
    /// The quartile calculation excludes the median when splitting the dataset into quartiles.
    /// </summary>
    public static final int EXCLUSIVE_MEDIAN = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer QuartileMethod value.
    /// </summary>
    public static String getName(/*QuartileMethod*/int quartileMethod)
    {
    	switch (quartileMethod)
    	{
    		case INCLUSIVE_MEDIAN: return "INCLUSIVE_MEDIAN";
    		case EXCLUSIVE_MEDIAN: return "EXCLUSIVE_MEDIAN";
    		default: return "Unknown QuartileMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer QuartileMethod value.
    /// </summary>
    public static String toString(/*QuartileMethod*/int quartileMethod)
    {
    	switch (quartileMethod)
    	{
    		case INCLUSIVE_MEDIAN: return "InclusiveMedian";
    		case EXCLUSIVE_MEDIAN: return "ExclusiveMedian";
    		default: return "Unknown QuartileMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by QuartileMethod name.
    /// </summary>
    public static /*QuartileMethod*/int fromName(String quartileMethodName)
    {
    	if ("INCLUSIVE_MEDIAN".equals(quartileMethodName)) return INCLUSIVE_MEDIAN;
    	if ("EXCLUSIVE_MEDIAN".equals(quartileMethodName)) return EXCLUSIVE_MEDIAN;
    	throw new IllegalArgumentException("Unknown QuartileMethod name.");
    }
    
    /// <summary>
    /// Returns array of QuartileMethod values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INCLUSIVE_MEDIAN,
    		EXCLUSIVE_MEDIAN,
    	};
    }

    public static final int length = 2;
}

