// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the shape to follow for a path gradient shade
/// </summary>
/*enum*/ final class DmlPathShadeType
{
    private DmlPathShadeType(){}
    
    /// <summary>
    /// Gradient follows the shape.
    /// </summary>
    public static final int SHAPE = 0;
    /// <summary>
    /// Gradient follows a circular path.
    /// </summary>
    public static final int CIRCLE = 1;
    /// <summary>
    /// Gradient follows a rectangular path.
    /// </summary>
    public static final int RECTANGLE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPathShadeType value.
    /// </summary>
    public static String getName(/*DmlPathShadeType*/int dmlPathShadeType)
    {
    	switch (dmlPathShadeType)
    	{
    		case SHAPE: return "SHAPE";
    		case CIRCLE: return "CIRCLE";
    		case RECTANGLE: return "RECTANGLE";
    		default: return "Unknown DmlPathShadeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPathShadeType value.
    /// </summary>
    public static String toString(/*DmlPathShadeType*/int dmlPathShadeType)
    {
    	switch (dmlPathShadeType)
    	{
    		case SHAPE: return "Shape";
    		case CIRCLE: return "Circle";
    		case RECTANGLE: return "Rectangle";
    		default: return "Unknown DmlPathShadeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPathShadeType name.
    /// </summary>
    public static /*DmlPathShadeType*/int fromName(String dmlPathShadeTypeName)
    {
    	if ("SHAPE".equals(dmlPathShadeTypeName)) return SHAPE;
    	if ("CIRCLE".equals(dmlPathShadeTypeName)) return CIRCLE;
    	if ("RECTANGLE".equals(dmlPathShadeTypeName)) return RECTANGLE;
    	throw new IllegalArgumentException("Unknown DmlPathShadeType name.");
    }
    
    /// <summary>
    /// Returns array of DmlPathShadeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SHAPE,
    		CIRCLE,
    		RECTANGLE,
    	};
    }

    public static final int length = 3;
}
