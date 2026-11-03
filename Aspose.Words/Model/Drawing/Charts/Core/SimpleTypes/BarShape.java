// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible shapes for a 3-D data marker.
/// Corresponds ST_Shape simple type (5.7.3.42).
/// </summary>
/*enum*/ final class BarShape
{
    private BarShape(){}
    
    /// <summary>
    /// Specifies the chart shall be drawn with a box shape.
    /// </summary>
    public static final int BOX = 0;
    /// <summary>
    /// Specifies the chart shall be drawn as a cone, with the base of the cone 
    /// on the floor and the point of the cone at the top of the data marker.
    /// </summary>
    public static final int CONE = 1;
    /// <summary>
    /// Specifies the chart shall be drawn with truncated cones such that the point 
    /// of the cone would be the maximum data value.
    /// </summary>
    public static final int CONE_TO_MAX = 2;
    /// <summary>
    /// Specifies the chart shall be drawn as a cylinder.
    /// </summary>
    public static final int CYLINDER = 3;
    /// <summary>
    /// Specifies the chart shall be drawn as a rectangular pyramid, 
    /// with the base of the pyramid on the floor and the point of the pyramid at the top of the data marker.
    /// </summary>
    public static final int PYRAMID = 4;
    /// <summary>
    /// Specifies the chart shall be drawn with truncated cones such that the point 
    /// of the cone would be the maximum data value.
    /// </summary>
    public static final int PYRAMID_TO_MAX = 5;
    /// <summary>
    /// Synthetic value that indicates value is not set.
    /// </summary>
    public static final int NONE = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BarShape value.
    /// </summary>
    public static String getName(/*BarShape*/int barShape)
    {
    	switch (barShape)
    	{
    		case BOX: return "BOX";
    		case CONE: return "CONE";
    		case CONE_TO_MAX: return "CONE_TO_MAX";
    		case CYLINDER: return "CYLINDER";
    		case PYRAMID: return "PYRAMID";
    		case PYRAMID_TO_MAX: return "PYRAMID_TO_MAX";
    		case NONE: return "NONE";
    		default: return "Unknown BarShape value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BarShape value.
    /// </summary>
    public static String toString(/*BarShape*/int barShape)
    {
    	switch (barShape)
    	{
    		case BOX: return "Box";
    		case CONE: return "Cone";
    		case CONE_TO_MAX: return "ConeToMax";
    		case CYLINDER: return "Cylinder";
    		case PYRAMID: return "Pyramid";
    		case PYRAMID_TO_MAX: return "PyramidToMax";
    		case NONE: return "None";
    		default: return "Unknown BarShape value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BarShape name.
    /// </summary>
    public static /*BarShape*/int fromName(String barShapeName)
    {
    	if ("BOX".equals(barShapeName)) return BOX;
    	if ("CONE".equals(barShapeName)) return CONE;
    	if ("CONE_TO_MAX".equals(barShapeName)) return CONE_TO_MAX;
    	if ("CYLINDER".equals(barShapeName)) return CYLINDER;
    	if ("PYRAMID".equals(barShapeName)) return PYRAMID;
    	if ("PYRAMID_TO_MAX".equals(barShapeName)) return PYRAMID_TO_MAX;
    	if ("NONE".equals(barShapeName)) return NONE;
    	throw new IllegalArgumentException("Unknown BarShape name.");
    }
    
    /// <summary>
    /// Returns array of BarShape values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOX,
    		CONE,
    		CONE_TO_MAX,
    		CYLINDER,
    		PYRAMID,
    		PYRAMID_TO_MAX,
    		NONE,
    	};
    }

    public static final int length = 7;
}

