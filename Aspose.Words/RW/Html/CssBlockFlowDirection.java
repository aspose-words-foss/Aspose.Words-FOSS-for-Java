// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines block flow direction.
/// </summary>
/// <remarks>
/// For details, see http://dev.w3.org/csswg/css-writing-modes-3/#writing-mode
/// </remarks>
/*enum*/ final class CssBlockFlowDirection
{
    private CssBlockFlowDirection(){}
    
    /// <summary>
    /// Top-to-bottom block flow direction. The writing mode is horizontal.
    /// </summary>
    public static final int HORIZONTAL_TB = 0;
    /// <summary>
    /// Right-to-left block flow direction. The writing mode is vertical.
    /// </summary>
    public static final int VERTICAL_RL = 1;
    /// <summary>
    /// Left-to-right block flow direction. The writing mode is vertical.
    /// </summary>
    public static final int VERTICAL_LR = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssBlockFlowDirection value.
    /// </summary>
    public static String getName(/*CssBlockFlowDirection*/int cssBlockFlowDirection)
    {
    	switch (cssBlockFlowDirection)
    	{
    		case HORIZONTAL_TB: return "HORIZONTAL_TB";
    		case VERTICAL_RL: return "VERTICAL_RL";
    		case VERTICAL_LR: return "VERTICAL_LR";
    		default: return "Unknown CssBlockFlowDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssBlockFlowDirection value.
    /// </summary>
    public static String toString(/*CssBlockFlowDirection*/int cssBlockFlowDirection)
    {
    	switch (cssBlockFlowDirection)
    	{
    		case HORIZONTAL_TB: return "HorizontalTb";
    		case VERTICAL_RL: return "VerticalRl";
    		case VERTICAL_LR: return "VerticalLr";
    		default: return "Unknown CssBlockFlowDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssBlockFlowDirection name.
    /// </summary>
    public static /*CssBlockFlowDirection*/int fromName(String cssBlockFlowDirectionName)
    {
    	if ("HORIZONTAL_TB".equals(cssBlockFlowDirectionName)) return HORIZONTAL_TB;
    	if ("VERTICAL_RL".equals(cssBlockFlowDirectionName)) return VERTICAL_RL;
    	if ("VERTICAL_LR".equals(cssBlockFlowDirectionName)) return VERTICAL_LR;
    	throw new IllegalArgumentException("Unknown CssBlockFlowDirection name.");
    }
    
    /// <summary>
    /// Returns array of CssBlockFlowDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL_TB,
    		VERTICAL_RL,
    		VERTICAL_LR,
    	};
    }

    public static final int length = 3;
}
