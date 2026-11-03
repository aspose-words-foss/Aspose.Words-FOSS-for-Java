// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/08/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates status of test comparison of files or package parts.
/// Used in so called 'gold' export/import tests.
/// </summary>
public /*enum*/ final class ComparisonStatus
{
    private ComparisonStatus(){}
    
    public static final int DIFFERENT = 0;
    public static final int MISSING = 1;
    public static final int OK = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ComparisonStatus value.
    /// </summary>
    public static String getName(/*ComparisonStatus*/int comparisonStatus)
    {
    	switch (comparisonStatus)
    	{
    		case DIFFERENT: return "DIFFERENT";
    		case MISSING: return "MISSING";
    		case OK: return "OK";
    		default: return "Unknown ComparisonStatus value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ComparisonStatus value.
    /// </summary>
    public static String toString(/*ComparisonStatus*/int comparisonStatus)
    {
    	switch (comparisonStatus)
    	{
    		case DIFFERENT: return "Different";
    		case MISSING: return "Missing";
    		case OK: return "Ok";
    		default: return "Unknown ComparisonStatus value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ComparisonStatus name.
    /// </summary>
    public static /*ComparisonStatus*/int fromName(String comparisonStatusName)
    {
    	if ("DIFFERENT".equals(comparisonStatusName)) return DIFFERENT;
    	if ("MISSING".equals(comparisonStatusName)) return MISSING;
    	if ("OK".equals(comparisonStatusName)) return OK;
    	throw new IllegalArgumentException("Unknown ComparisonStatus name.");
    }
    
    /// <summary>
    /// Returns array of ComparisonStatus values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DIFFERENT,
    		MISSING,
    		OK,
    	};
    }

    public static final int length = 3;
}

