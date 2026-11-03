// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2014 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class GridState
{
    private GridState(){}
    
    /// <summary>
    /// Original state before calculation is attempted.
    /// </summary>
    public static final int NOT_CALCULATED = 0;

    /// <summary>
    /// Set when an unsupported or unexpected situation during grid calculation is encountered.
    /// </summary>
    public static final int CALCULATION_FAILED = 1;

    /// <summary>
    /// The grid is calculated successfully. 
    /// Table cell and row attributes should be updated accordingly.
    /// </summary>
    public static final int APPLIED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GridState value.
    /// </summary>
    public static String getName(/*GridState*/int gridState)
    {
    	switch (gridState)
    	{
    		case NOT_CALCULATED: return "NOT_CALCULATED";
    		case CALCULATION_FAILED: return "CALCULATION_FAILED";
    		case APPLIED: return "APPLIED";
    		default: return "Unknown GridState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GridState value.
    /// </summary>
    public static String toString(/*GridState*/int gridState)
    {
    	switch (gridState)
    	{
    		case NOT_CALCULATED: return "NotCalculated";
    		case CALCULATION_FAILED: return "CalculationFailed";
    		case APPLIED: return "Applied";
    		default: return "Unknown GridState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GridState name.
    /// </summary>
    public static /*GridState*/int fromName(String gridStateName)
    {
    	if ("NOT_CALCULATED".equals(gridStateName)) return NOT_CALCULATED;
    	if ("CALCULATION_FAILED".equals(gridStateName)) return CALCULATION_FAILED;
    	if ("APPLIED".equals(gridStateName)) return APPLIED;
    	throw new IllegalArgumentException("Unknown GridState name.");
    }
    
    /// <summary>
    /// Returns array of GridState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NOT_CALCULATED,
    		CALCULATION_FAILED,
    		APPLIED,
    	};
    }

    public static final int length = 3;
}

