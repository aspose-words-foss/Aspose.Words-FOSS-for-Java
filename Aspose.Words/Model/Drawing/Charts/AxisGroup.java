// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a type of a chart axis group.
/// </summary>
public /*enum*/ final class AxisGroup
{
    private AxisGroup(){}
    
    /// <summary>
    /// Specifies the primary axis group.
    /// </summary>
    public static final int PRIMARY = 0;

    /// <summary>
    /// Specifies the secondary axis group.
    /// </summary>
    public static final int SECONDARY = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisGroup value.
    /// </summary>
    public static String getName(/*AxisGroup*/int axisGroup)
    {
    	switch (axisGroup)
    	{
    		case PRIMARY: return "PRIMARY";
    		case SECONDARY: return "SECONDARY";
    		default: return "Unknown AxisGroup value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisGroup value.
    /// </summary>
    public static String toString(/*AxisGroup*/int axisGroup)
    {
    	switch (axisGroup)
    	{
    		case PRIMARY: return "Primary";
    		case SECONDARY: return "Secondary";
    		default: return "Unknown AxisGroup value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisGroup name.
    /// </summary>
    public static /*AxisGroup*/int fromName(String axisGroupName)
    {
    	if ("PRIMARY".equals(axisGroupName)) return PRIMARY;
    	if ("SECONDARY".equals(axisGroupName)) return SECONDARY;
    	throw new IllegalArgumentException("Unknown AxisGroup name.");
    }
    
    /// <summary>
    /// Returns array of AxisGroup values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PRIMARY,
    		SECONDARY,
    	};
    }

    public static final int length = 2;
}

