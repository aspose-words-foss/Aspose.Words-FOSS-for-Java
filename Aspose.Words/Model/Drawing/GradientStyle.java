// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the style for a gradient fill.
/// </summary>
public /*enum*/ final class GradientStyle
{
    private GradientStyle(){}
    
    /// <summary>
    /// No gradient.
    /// </summary>
    public static final int NONE = -1;

    /// <summary>
    /// Gradient running horizontally across an object.
    /// </summary>
    public static final int HORIZONTAL = 1;

    /// <summary>
    /// Gradient running vertically down an object.
    /// </summary>
    public static final int VERTICAL = 2;

    /// <summary>
    /// Diagonal gradient moving from a bottom corner up to the opposite corner.
    /// </summary>
    public static final int DIAGONAL_UP = 3;

    /// <summary>
    /// Diagonal gradient moving from a top corner down to the opposite corner.
    /// </summary>
    public static final int DIAGONAL_DOWN = 4;

    /// <summary>
    /// Gradient running from a corner to the other three corners.
    /// </summary>
    public static final int FROM_CORNER = 5;

    /// <summary>
    /// Gradient running from the center out to the corners.
    /// </summary>
    public static final int FROM_CENTER = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GradientStyle value.
    /// </summary>
    public static String getName(/*GradientStyle*/int gradientStyle)
    {
    	switch (gradientStyle)
    	{
    		case NONE: return "NONE";
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case DIAGONAL_UP: return "DIAGONAL_UP";
    		case DIAGONAL_DOWN: return "DIAGONAL_DOWN";
    		case FROM_CORNER: return "FROM_CORNER";
    		case FROM_CENTER: return "FROM_CENTER";
    		default: return "Unknown GradientStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GradientStyle value.
    /// </summary>
    public static String toString(/*GradientStyle*/int gradientStyle)
    {
    	switch (gradientStyle)
    	{
    		case NONE: return "None";
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case DIAGONAL_UP: return "DiagonalUp";
    		case DIAGONAL_DOWN: return "DiagonalDown";
    		case FROM_CORNER: return "FromCorner";
    		case FROM_CENTER: return "FromCenter";
    		default: return "Unknown GradientStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GradientStyle name.
    /// </summary>
    public static /*GradientStyle*/int fromName(String gradientStyleName)
    {
    	if ("NONE".equals(gradientStyleName)) return NONE;
    	if ("HORIZONTAL".equals(gradientStyleName)) return HORIZONTAL;
    	if ("VERTICAL".equals(gradientStyleName)) return VERTICAL;
    	if ("DIAGONAL_UP".equals(gradientStyleName)) return DIAGONAL_UP;
    	if ("DIAGONAL_DOWN".equals(gradientStyleName)) return DIAGONAL_DOWN;
    	if ("FROM_CORNER".equals(gradientStyleName)) return FROM_CORNER;
    	if ("FROM_CENTER".equals(gradientStyleName)) return FROM_CENTER;
    	throw new IllegalArgumentException("Unknown GradientStyle name.");
    }
    
    /// <summary>
    /// Returns array of GradientStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		HORIZONTAL,
    		VERTICAL,
    		DIAGONAL_UP,
    		DIAGONAL_DOWN,
    		FROM_CORNER,
    		FROM_CENTER,
    	};
    }

    public static final int length = 7;
}

