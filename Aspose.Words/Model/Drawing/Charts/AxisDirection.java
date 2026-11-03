// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2024 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the direction (X, Y or Z) of an axis.
/// </summary>
/*enum*/ final class AxisDirection
{
    private AxisDirection(){}
    
    /// <summary>
    /// An axis direction is not specified.
    /// </summary>
    public static final int UNSPECIFIED = 0;

    /// <summary>
    /// The axis is an X axis.
    /// </summary>
    public static final int X = 1;

    /// <summary>
    /// The axis is an Y axis.
    /// </summary>
    public static final int Y = 2;

    /// <summary>
    /// The axis is a Z axis.
    /// </summary>
    public static final int Z = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisDirection value.
    /// </summary>
    public static String getName(/*AxisDirection*/int axisDirection)
    {
    	switch (axisDirection)
    	{
    		case UNSPECIFIED: return "UNSPECIFIED";
    		case X: return "X";
    		case Y: return "Y";
    		case Z: return "Z";
    		default: return "Unknown AxisDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisDirection value.
    /// </summary>
    public static String toString(/*AxisDirection*/int axisDirection)
    {
    	switch (axisDirection)
    	{
    		case UNSPECIFIED: return "Unspecified";
    		case X: return "X";
    		case Y: return "Y";
    		case Z: return "Z";
    		default: return "Unknown AxisDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisDirection name.
    /// </summary>
    public static /*AxisDirection*/int fromName(String axisDirectionName)
    {
    	if ("UNSPECIFIED".equals(axisDirectionName)) return UNSPECIFIED;
    	if ("X".equals(axisDirectionName)) return X;
    	if ("Y".equals(axisDirectionName)) return Y;
    	if ("Z".equals(axisDirectionName)) return Z;
    	throw new IllegalArgumentException("Unknown AxisDirection name.");
    }
    
    /// <summary>
    /// Returns array of AxisDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNSPECIFIED,
    		X,
    		Y,
    		Z,
    	};
    }

    public static final int length = 4;
}

