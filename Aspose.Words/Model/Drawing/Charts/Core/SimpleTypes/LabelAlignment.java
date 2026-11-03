// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the possible ways to align the tick labels.
/// Corresponds ST_LblAlgn simple type (5.7.3.22).
/// </summary>
/*enum*/ final class LabelAlignment
{
    private LabelAlignment(){}
    
    /// <summary>
    /// Synthetic value that indicates value is not set explicitly.
    /// </summary>
    public static final int DEFAULT = 0;
    public static final int CENTER = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LabelAlignment value.
    /// </summary>
    public static String getName(/*LabelAlignment*/int labelAlignment)
    {
    	switch (labelAlignment)
    	{
    		case DEFAULT: return "DEFAULT";
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown LabelAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LabelAlignment value.
    /// </summary>
    public static String toString(/*LabelAlignment*/int labelAlignment)
    {
    	switch (labelAlignment)
    	{
    		case DEFAULT: return "Default";
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		default: return "Unknown LabelAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LabelAlignment name.
    /// </summary>
    public static /*LabelAlignment*/int fromName(String labelAlignmentName)
    {
    	if ("DEFAULT".equals(labelAlignmentName)) return DEFAULT;
    	if ("CENTER".equals(labelAlignmentName)) return CENTER;
    	if ("LEFT".equals(labelAlignmentName)) return LEFT;
    	if ("RIGHT".equals(labelAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown LabelAlignment name.");
    }
    
    /// <summary>
    /// Returns array of LabelAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		CENTER,
    		LEFT,
    		RIGHT,
    	};
    }

    public static final int length = 4;
}

