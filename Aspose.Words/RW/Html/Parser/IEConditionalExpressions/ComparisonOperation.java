// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// An operation that is used to compare versions of features.
/// </summary>
/*enum*/ final class ComparisonOperation
{
    private ComparisonOperation(){}
    
    public static final int EQUAL = 0;
    public static final int GREATER = 1;
    public static final int GREATER_OR_EQUAL = 2;
    public static final int LESS = 3;
    public static final int LESS_OR_EQUAL = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ComparisonOperation value.
    /// </summary>
    public static String getName(/*ComparisonOperation*/int comparisonOperation)
    {
    	switch (comparisonOperation)
    	{
    		case EQUAL: return "EQUAL";
    		case GREATER: return "GREATER";
    		case GREATER_OR_EQUAL: return "GREATER_OR_EQUAL";
    		case LESS: return "LESS";
    		case LESS_OR_EQUAL: return "LESS_OR_EQUAL";
    		default: return "Unknown ComparisonOperation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ComparisonOperation value.
    /// </summary>
    public static String toString(/*ComparisonOperation*/int comparisonOperation)
    {
    	switch (comparisonOperation)
    	{
    		case EQUAL: return "Equal";
    		case GREATER: return "Greater";
    		case GREATER_OR_EQUAL: return "GreaterOrEqual";
    		case LESS: return "Less";
    		case LESS_OR_EQUAL: return "LessOrEqual";
    		default: return "Unknown ComparisonOperation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ComparisonOperation name.
    /// </summary>
    public static /*ComparisonOperation*/int fromName(String comparisonOperationName)
    {
    	if ("EQUAL".equals(comparisonOperationName)) return EQUAL;
    	if ("GREATER".equals(comparisonOperationName)) return GREATER;
    	if ("GREATER_OR_EQUAL".equals(comparisonOperationName)) return GREATER_OR_EQUAL;
    	if ("LESS".equals(comparisonOperationName)) return LESS;
    	if ("LESS_OR_EQUAL".equals(comparisonOperationName)) return LESS_OR_EQUAL;
    	throw new IllegalArgumentException("Unknown ComparisonOperation name.");
    }
    
    /// <summary>
    /// Returns array of ComparisonOperation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EQUAL,
    		GREATER,
    		GREATER_OR_EQUAL,
    		LESS,
    		LESS_OR_EQUAL,
    	};
    }

    public static final int length = 5;
}
