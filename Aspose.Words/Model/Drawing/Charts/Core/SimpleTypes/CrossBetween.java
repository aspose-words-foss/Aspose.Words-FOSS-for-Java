// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible crossing states of an axis.
/// Corresponds ST_CrossBetween simple type (5.7.3.7)
/// </summary>
/*enum*/ final class CrossBetween
{
    private CrossBetween(){}
    
    public static final int BETWEEN = 0;
    public static final int MIDPOINT_OF_CATEGORY = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CrossBetween value.
    /// </summary>
    public static String getName(/*CrossBetween*/int crossBetween)
    {
    	switch (crossBetween)
    	{
    		case BETWEEN: return "BETWEEN";
    		case MIDPOINT_OF_CATEGORY: return "MIDPOINT_OF_CATEGORY";
    		default: return "Unknown CrossBetween value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CrossBetween value.
    /// </summary>
    public static String toString(/*CrossBetween*/int crossBetween)
    {
    	switch (crossBetween)
    	{
    		case BETWEEN: return "Between";
    		case MIDPOINT_OF_CATEGORY: return "MidpointOfCategory";
    		default: return "Unknown CrossBetween value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CrossBetween name.
    /// </summary>
    public static /*CrossBetween*/int fromName(String crossBetweenName)
    {
    	if ("BETWEEN".equals(crossBetweenName)) return BETWEEN;
    	if ("MIDPOINT_OF_CATEGORY".equals(crossBetweenName)) return MIDPOINT_OF_CATEGORY;
    	throw new IllegalArgumentException("Unknown CrossBetween name.");
    }
    
    /// <summary>
    /// Returns array of CrossBetween values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BETWEEN,
    		MIDPOINT_OF_CATEGORY,
    	};
    }

    public static final int length = 2;
}

