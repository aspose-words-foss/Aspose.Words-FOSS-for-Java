// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to split a pie of pie or bar of pie chart.
/// Corresponds ST_SplitType simple type (5.7.3.45).
/// </summary>
/*enum*/ final class SplitType
{
    private SplitType(){}
    
    /// <summary>
    /// Specifies the data points shall be split using the default mechanism for this chart type.
    /// </summary>
    public static final int AUTO = 0;
    /// <summary>
    /// Specifies the data points shall be split between the pie 
    /// and the second chart according to the Custom Split values.
    /// </summary>
    public static final int CUSTOM = 1;
    /// <summary>
    /// Specifies the data points shall be split between the pie and the second chart by putting the points 
    /// with percentage less than Split Position percent in the second chart.
    /// </summary>
    public static final int PERCENTAGE = 2;
    /// <summary>
    /// Specifies the data points shall be split between the pie and the second chart by putting 
    /// the last Split Position of the data points in the second chart
    /// </summary>
    public static final int POSITION = 3;
    /// <summary>
    /// Specifies the data points shall be split between the pie and the second chart by putting 
    /// the data points with value less than Split Position in the second chart
    /// </summary>
    public static final int VALUE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SplitType value.
    /// </summary>
    public static String getName(/*SplitType*/int splitType)
    {
    	switch (splitType)
    	{
    		case AUTO: return "AUTO";
    		case CUSTOM: return "CUSTOM";
    		case PERCENTAGE: return "PERCENTAGE";
    		case POSITION: return "POSITION";
    		case VALUE: return "VALUE";
    		default: return "Unknown SplitType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SplitType value.
    /// </summary>
    public static String toString(/*SplitType*/int splitType)
    {
    	switch (splitType)
    	{
    		case AUTO: return "Auto";
    		case CUSTOM: return "Custom";
    		case PERCENTAGE: return "Percentage";
    		case POSITION: return "Position";
    		case VALUE: return "Value";
    		default: return "Unknown SplitType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SplitType name.
    /// </summary>
    public static /*SplitType*/int fromName(String splitTypeName)
    {
    	if ("AUTO".equals(splitTypeName)) return AUTO;
    	if ("CUSTOM".equals(splitTypeName)) return CUSTOM;
    	if ("PERCENTAGE".equals(splitTypeName)) return PERCENTAGE;
    	if ("POSITION".equals(splitTypeName)) return POSITION;
    	if ("VALUE".equals(splitTypeName)) return VALUE;
    	throw new IllegalArgumentException("Unknown SplitType name.");
    }
    
    /// <summary>
    /// Returns array of SplitType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		CUSTOM,
    		PERCENTAGE,
    		POSITION,
    		VALUE,
    	};
    }

    public static final int length = 5;
}

