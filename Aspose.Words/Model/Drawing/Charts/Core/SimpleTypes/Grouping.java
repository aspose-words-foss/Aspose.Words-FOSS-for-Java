// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible groupings for a bar chart.
/// Corresponds ST_BarGrouping simple type (5.7.3.4).
/// </summary>
/*enum*/ final class Grouping
{
    private Grouping(){}
    
    public static final int CLUSTERED = 0;
    public static final int PERCENT_STACKED = 1;
    public static final int STACKED = 2;
    public static final int STANDARD = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Grouping value.
    /// </summary>
    public static String getName(/*Grouping*/int grouping)
    {
    	switch (grouping)
    	{
    		case CLUSTERED: return "CLUSTERED";
    		case PERCENT_STACKED: return "PERCENT_STACKED";
    		case STACKED: return "STACKED";
    		case STANDARD: return "STANDARD";
    		default: return "Unknown Grouping value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Grouping value.
    /// </summary>
    public static String toString(/*Grouping*/int grouping)
    {
    	switch (grouping)
    	{
    		case CLUSTERED: return "Clustered";
    		case PERCENT_STACKED: return "PercentStacked";
    		case STACKED: return "Stacked";
    		case STANDARD: return "Standard";
    		default: return "Unknown Grouping value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Grouping name.
    /// </summary>
    public static /*Grouping*/int fromName(String groupingName)
    {
    	if ("CLUSTERED".equals(groupingName)) return CLUSTERED;
    	if ("PERCENT_STACKED".equals(groupingName)) return PERCENT_STACKED;
    	if ("STACKED".equals(groupingName)) return STACKED;
    	if ("STANDARD".equals(groupingName)) return STANDARD;
    	throw new IllegalArgumentException("Unknown Grouping name.");
    }
    
    /// <summary>
    /// Returns array of Grouping values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CLUSTERED,
    		PERCENT_STACKED,
    		STACKED,
    		STANDARD,
    	};
    }

    public static final int length = 4;
}

