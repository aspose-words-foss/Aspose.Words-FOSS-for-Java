// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlAdjustType
{
    private DmlAdjustType(){}
    
    public static final int UNKNOWN = 0;
    public static final int ANGLE = 1;
    public static final int COORDINATE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAdjustType value.
    /// </summary>
    public static String getName(/*DmlAdjustType*/int dmlAdjustType)
    {
    	switch (dmlAdjustType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case ANGLE: return "ANGLE";
    		case COORDINATE: return "COORDINATE";
    		default: return "Unknown DmlAdjustType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAdjustType value.
    /// </summary>
    public static String toString(/*DmlAdjustType*/int dmlAdjustType)
    {
    	switch (dmlAdjustType)
    	{
    		case UNKNOWN: return "Unknown";
    		case ANGLE: return "Angle";
    		case COORDINATE: return "Coordinate";
    		default: return "Unknown DmlAdjustType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAdjustType name.
    /// </summary>
    public static /*DmlAdjustType*/int fromName(String dmlAdjustTypeName)
    {
    	if ("UNKNOWN".equals(dmlAdjustTypeName)) return UNKNOWN;
    	if ("ANGLE".equals(dmlAdjustTypeName)) return ANGLE;
    	if ("COORDINATE".equals(dmlAdjustTypeName)) return COORDINATE;
    	throw new IllegalArgumentException("Unknown DmlAdjustType name.");
    }
    
    /// <summary>
    /// Returns array of DmlAdjustType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		ANGLE,
    		COORDINATE,
    	};
    }

    public static final int length = 3;
}

