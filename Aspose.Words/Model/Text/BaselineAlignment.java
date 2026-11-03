// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies fonts vertical position on a line.
/// </summary>
public /*enum*/ final class BaselineAlignment
{
    private BaselineAlignment(){}
    
    /// <summary>
    /// Aligns along the top of each font.
    /// </summary>
    public static final int TOP = 0;
    /// <summary>
    /// Aligns the center points of each font.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// Aligns to the baseline of the paragraph.
    /// </summary>
    public static final int BASELINE = 2;
    /// <summary>
    /// Aligns to the bottom of each font.
    /// </summary>
    public static final int BOTTOM = 3;
    /// <summary>
    /// Baseline is adjusted automatically.
    /// </summary>
    public static final int AUTO = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BaselineAlignment value.
    /// </summary>
    public static String getName(/*BaselineAlignment*/int baselineAlignment)
    {
    	switch (baselineAlignment)
    	{
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BASELINE: return "BASELINE";
    		case BOTTOM: return "BOTTOM";
    		case AUTO: return "AUTO";
    		default: return "Unknown BaselineAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BaselineAlignment value.
    /// </summary>
    public static String toString(/*BaselineAlignment*/int baselineAlignment)
    {
    	switch (baselineAlignment)
    	{
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BASELINE: return "Baseline";
    		case BOTTOM: return "Bottom";
    		case AUTO: return "Auto";
    		default: return "Unknown BaselineAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BaselineAlignment name.
    /// </summary>
    public static /*BaselineAlignment*/int fromName(String baselineAlignmentName)
    {
    	if ("TOP".equals(baselineAlignmentName)) return TOP;
    	if ("CENTER".equals(baselineAlignmentName)) return CENTER;
    	if ("BASELINE".equals(baselineAlignmentName)) return BASELINE;
    	if ("BOTTOM".equals(baselineAlignmentName)) return BOTTOM;
    	if ("AUTO".equals(baselineAlignmentName)) return AUTO;
    	throw new IllegalArgumentException("Unknown BaselineAlignment name.");
    }
    
    /// <summary>
    /// Returns array of BaselineAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		CENTER,
    		BASELINE,
    		BOTTOM,
    		AUTO,
    	};
    }

    public static final int length = 5;
}

