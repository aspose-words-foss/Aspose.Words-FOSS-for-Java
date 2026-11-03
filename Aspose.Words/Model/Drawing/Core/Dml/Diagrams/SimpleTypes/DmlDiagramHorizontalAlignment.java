// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.24 ST_DiagramHorizontalAlignment (Horizontal Alignment)
/// </summary>
/*enum*/ final class DmlDiagramHorizontalAlignment
{
    private DmlDiagramHorizontalAlignment(){}
    
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int NONE = 2;
    public static final int RIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlDiagramHorizontalAlignment value.
    /// </summary>
    public static String getName(/*DmlDiagramHorizontalAlignment*/int dmlDiagramHorizontalAlignment)
    {
    	switch (dmlDiagramHorizontalAlignment)
    	{
    		case CENTER: return "CENTER";
    		case LEFT: return "LEFT";
    		case NONE: return "NONE";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown DmlDiagramHorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlDiagramHorizontalAlignment value.
    /// </summary>
    public static String toString(/*DmlDiagramHorizontalAlignment*/int dmlDiagramHorizontalAlignment)
    {
    	switch (dmlDiagramHorizontalAlignment)
    	{
    		case CENTER: return "Center";
    		case LEFT: return "Left";
    		case NONE: return "None";
    		case RIGHT: return "Right";
    		default: return "Unknown DmlDiagramHorizontalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlDiagramHorizontalAlignment name.
    /// </summary>
    public static /*DmlDiagramHorizontalAlignment*/int fromName(String dmlDiagramHorizontalAlignmentName)
    {
    	if ("CENTER".equals(dmlDiagramHorizontalAlignmentName)) return CENTER;
    	if ("LEFT".equals(dmlDiagramHorizontalAlignmentName)) return LEFT;
    	if ("NONE".equals(dmlDiagramHorizontalAlignmentName)) return NONE;
    	if ("RIGHT".equals(dmlDiagramHorizontalAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown DmlDiagramHorizontalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlDiagramHorizontalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTER,
    		LEFT,
    		NONE,
    		RIGHT,
    	};
    }

    public static final int length = 4;
}

