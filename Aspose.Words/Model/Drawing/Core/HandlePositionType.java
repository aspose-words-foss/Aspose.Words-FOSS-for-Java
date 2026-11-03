// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2010 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of handle positioning.
/// Affects the position coordinates calculation.
/// </summary>
/*enum*/ final class HandlePositionType
{
    private HandlePositionType(){}
    
    public static final int UNKNOWN = 0; 

    // Position the x(y) coordinate of adjust handle on the left(top) perimeter of it's shape.
    public static final int LEFT_TOP = 1;

    // Position the x(y) coordinate of adjust handle on the right(bottom) perimeter of it's shape.
    public static final int RIGHT_BOTTOM = 2;

    // Position the x(y) coordinate of adjust handle on the horizontal(vertical) center of it's shape.
    public static final int CENTER = 3;

    // Position of x(y) coordinate of adjust handle is the result value of formula specified by index.
    public static final int FORMULA = 4;

    // Position of x(y) coordinate of adjust handle is the adjust value specified by index.
    public static final int ADJUST = 5;

    // Position of x(y) coordinate of adjust handle is the constant value
    public static final int CONSTANT = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HandlePositionType value.
    /// </summary>
    public static String getName(/*HandlePositionType*/int handlePositionType)
    {
    	switch (handlePositionType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case LEFT_TOP: return "LEFT_TOP";
    		case RIGHT_BOTTOM: return "RIGHT_BOTTOM";
    		case CENTER: return "CENTER";
    		case FORMULA: return "FORMULA";
    		case ADJUST: return "ADJUST";
    		case CONSTANT: return "CONSTANT";
    		default: return "Unknown HandlePositionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HandlePositionType value.
    /// </summary>
    public static String toString(/*HandlePositionType*/int handlePositionType)
    {
    	switch (handlePositionType)
    	{
    		case UNKNOWN: return "Unknown";
    		case LEFT_TOP: return "LeftTop";
    		case RIGHT_BOTTOM: return "RightBottom";
    		case CENTER: return "Center";
    		case FORMULA: return "Formula";
    		case ADJUST: return "Adjust";
    		case CONSTANT: return "Constant";
    		default: return "Unknown HandlePositionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HandlePositionType name.
    /// </summary>
    public static /*HandlePositionType*/int fromName(String handlePositionTypeName)
    {
    	if ("UNKNOWN".equals(handlePositionTypeName)) return UNKNOWN;
    	if ("LEFT_TOP".equals(handlePositionTypeName)) return LEFT_TOP;
    	if ("RIGHT_BOTTOM".equals(handlePositionTypeName)) return RIGHT_BOTTOM;
    	if ("CENTER".equals(handlePositionTypeName)) return CENTER;
    	if ("FORMULA".equals(handlePositionTypeName)) return FORMULA;
    	if ("ADJUST".equals(handlePositionTypeName)) return ADJUST;
    	if ("CONSTANT".equals(handlePositionTypeName)) return CONSTANT;
    	throw new IllegalArgumentException("Unknown HandlePositionType name.");
    }
    
    /// <summary>
    /// Returns array of HandlePositionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		LEFT_TOP,
    		RIGHT_BOTTOM,
    		CENTER,
    		FORMULA,
    		ADJUST,
    		CONSTANT,
    	};
    }

    public static final int length = 7;
}

