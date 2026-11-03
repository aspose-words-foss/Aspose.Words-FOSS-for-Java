// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to represent data as bubble chart sizes.
/// Corresponds ST_SizeRepresents simple type (5.7.3.43).
/// </summary>
/*enum*/ final class SizeRepresents
{
    private SizeRepresents(){}
    
    /// <summary>
    /// Specifies the area of the bubbles shall be proportional to the bubble size value.
    /// </summary>
    public static final int AREA = 0;
    /// <summary>
    /// Specifies the radius of the bubbles shall be proportional to the bubble size value.
    /// </summary>
    public static final int WIDTH = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SizeRepresents value.
    /// </summary>
    public static String getName(/*SizeRepresents*/int sizeRepresents)
    {
    	switch (sizeRepresents)
    	{
    		case AREA: return "AREA";
    		case WIDTH: return "WIDTH";
    		default: return "Unknown SizeRepresents value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SizeRepresents value.
    /// </summary>
    public static String toString(/*SizeRepresents*/int sizeRepresents)
    {
    	switch (sizeRepresents)
    	{
    		case AREA: return "Area";
    		case WIDTH: return "Width";
    		default: return "Unknown SizeRepresents value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SizeRepresents name.
    /// </summary>
    public static /*SizeRepresents*/int fromName(String sizeRepresentsName)
    {
    	if ("AREA".equals(sizeRepresentsName)) return AREA;
    	if ("WIDTH".equals(sizeRepresentsName)) return WIDTH;
    	throw new IllegalArgumentException("Unknown SizeRepresents name.");
    }
    
    /// <summary>
    /// Returns array of SizeRepresents values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AREA,
    		WIDTH,
    	};
    }

    public static final int length = 2;
}

