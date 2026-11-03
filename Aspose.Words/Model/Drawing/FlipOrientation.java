// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Possible values for the orientation of a shape.
/// </summary>
/// <seealso cref="ShapeBase.FlipOrientation"/>
public /*enum*/ final class FlipOrientation
{
    private FlipOrientation(){}
    
    /// <summary>
    /// Coordinates are not flipped.
    /// </summary>
    public static final int NONE = 0x00;
    /// <summary>
    /// Flip along the y-axis, reversing the x-coordinates.
    /// </summary>
    public static final int HORIZONTAL = 0x01;
    /// <summary>
    /// Flip along the x-axis, reversing the y-coordinates.
    /// </summary>
    public static final int VERTICAL = 0x02;
    /// <summary>
    /// Flip along both the y- and x-axis.
    /// </summary>
    public static final int BOTH = 0x03;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FlipOrientation value.
    /// </summary>
    public static String getName(/*FlipOrientation*/int flipOrientation)
    {
    	switch (flipOrientation)
    	{
    		case NONE: return "NONE";
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case BOTH: return "BOTH";
    		default: return "Unknown FlipOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FlipOrientation value.
    /// </summary>
    public static Set<String> getNames(/*FlipOrientation*/int flipOrientation)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & flipOrientation) == NONE) names.add("NONE");
    	if ((HORIZONTAL & flipOrientation) == HORIZONTAL) names.add("HORIZONTAL");
    	if ((VERTICAL & flipOrientation) == VERTICAL) names.add("VERTICAL");
    	if ((BOTH & flipOrientation) == BOTH) names.add("BOTH");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FlipOrientation value.
    /// </summary>
    public static String toString(/*FlipOrientation*/int flipOrientation)
    {
    	switch (flipOrientation)
    	{
    		case NONE: return "None";
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case BOTH: return "Both";
    		default: return toStringSet(flipOrientation);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FlipOrientation value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by FlipOrientation name.
    /// </summary>
    public static /*FlipOrientation*/int fromName(String flipOrientationName)
    {
    	if ("NONE".equals(flipOrientationName)) return NONE;
    	if ("HORIZONTAL".equals(flipOrientationName)) return HORIZONTAL;
    	if ("VERTICAL".equals(flipOrientationName)) return VERTICAL;
    	if ("BOTH".equals(flipOrientationName)) return BOTH;
    	throw new IllegalArgumentException("Unknown FlipOrientation name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FlipOrientation names.
    /// </summary>
    public static /*FlipOrientation*/int fromNames(Set<String> flipOrientationNames)
    {
    	/*FlipOrientation*/int result = 0;
    	for (String name : flipOrientationNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FlipOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		HORIZONTAL,
    		VERTICAL,
    		BOTH,
    	};
    }

    public static final int length = 4;
}

