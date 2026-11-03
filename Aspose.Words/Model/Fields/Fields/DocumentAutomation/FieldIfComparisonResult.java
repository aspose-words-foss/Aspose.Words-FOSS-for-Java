// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/09/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the result of the IF field condition evaluation.
/// </summary>
public /*enum*/ final class FieldIfComparisonResult
{
    private FieldIfComparisonResult(){}
    
    /// <summary>
    /// There is an error in the condition.
    /// </summary>
    public static final int ERROR = 0;

    /// <summary>
    /// The condition is <c>true</c>.
    /// </summary>
    public static final int TRUE = 1;

    /// <summary>
    /// The condition is <c>false</c>.
    /// </summary>
    public static final int FALSE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldIfComparisonResult value.
    /// </summary>
    public static String getName(/*FieldIfComparisonResult*/int fieldIfComparisonResult)
    {
    	switch (fieldIfComparisonResult)
    	{
    		case ERROR: return "ERROR";
    		case TRUE: return "TRUE";
    		case FALSE: return "FALSE";
    		default: return "Unknown FieldIfComparisonResult value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldIfComparisonResult value.
    /// </summary>
    public static String toString(/*FieldIfComparisonResult*/int fieldIfComparisonResult)
    {
    	switch (fieldIfComparisonResult)
    	{
    		case ERROR: return "Error";
    		case TRUE: return "True";
    		case FALSE: return "False";
    		default: return "Unknown FieldIfComparisonResult value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldIfComparisonResult name.
    /// </summary>
    public static /*FieldIfComparisonResult*/int fromName(String fieldIfComparisonResultName)
    {
    	if ("ERROR".equals(fieldIfComparisonResultName)) return ERROR;
    	if ("TRUE".equals(fieldIfComparisonResultName)) return TRUE;
    	if ("FALSE".equals(fieldIfComparisonResultName)) return FALSE;
    	throw new IllegalArgumentException("Unknown FieldIfComparisonResult name.");
    }
    
    /// <summary>
    /// Returns array of FieldIfComparisonResult values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ERROR,
    		TRUE,
    		FALSE,
    	};
    }

    public static final int length = 3;
}
