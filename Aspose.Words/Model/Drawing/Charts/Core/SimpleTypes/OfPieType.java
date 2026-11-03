// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible types of Pie or Pie or Bar of Pie charts.
/// Corresponds ST_OfPieType simple type (5.7.3.28).
/// </summary>
/*enum*/ final class OfPieType
{
    private OfPieType(){}
    
    /// <summary>
    /// Specifies that the chart is a bar of pie chart, not a pie of pie chart.
    /// </summary>
    public static final int BAR = 0;
    /// <summary>
    /// Specifies that the chart is pie of pie chart, not a bar of pie chart.
    /// </summary>
    public static final int PIE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OfPieType value.
    /// </summary>
    public static String getName(/*OfPieType*/int ofPieType)
    {
    	switch (ofPieType)
    	{
    		case BAR: return "BAR";
    		case PIE: return "PIE";
    		default: return "Unknown OfPieType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OfPieType value.
    /// </summary>
    public static String toString(/*OfPieType*/int ofPieType)
    {
    	switch (ofPieType)
    	{
    		case BAR: return "Bar";
    		case PIE: return "Pie";
    		default: return "Unknown OfPieType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OfPieType name.
    /// </summary>
    public static /*OfPieType*/int fromName(String ofPieTypeName)
    {
    	if ("BAR".equals(ofPieTypeName)) return BAR;
    	if ("PIE".equals(ofPieTypeName)) return PIE;
    	throw new IllegalArgumentException("Unknown OfPieType name.");
    }
    
    /// <summary>
    /// Returns array of OfPieType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BAR,
    		PIE,
    	};
    }

    public static final int length = 2;
}

