// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2015 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.25 ST_DiagramTextAlignment (Text Alignment)
/// </summary>
/*enum*/ final class DmlDiagramTextAlignment
{
    private DmlDiagramTextAlignment(){}
    
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlDiagramTextAlignment value.
    /// </summary>
    public static String getName(/*DmlDiagramTextAlignment*/int dmlDiagramTextAlignment)
    {
    	switch (dmlDiagramTextAlignment)
    	{
    		case LEFT: return "LEFT";
    		case CENTER: return "CENTER";
    		case RIGHT: return "RIGHT";
    		default: return "Unknown DmlDiagramTextAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlDiagramTextAlignment value.
    /// </summary>
    public static String toString(/*DmlDiagramTextAlignment*/int dmlDiagramTextAlignment)
    {
    	switch (dmlDiagramTextAlignment)
    	{
    		case LEFT: return "Left";
    		case CENTER: return "Center";
    		case RIGHT: return "Right";
    		default: return "Unknown DmlDiagramTextAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlDiagramTextAlignment name.
    /// </summary>
    public static /*DmlDiagramTextAlignment*/int fromName(String dmlDiagramTextAlignmentName)
    {
    	if ("LEFT".equals(dmlDiagramTextAlignmentName)) return LEFT;
    	if ("CENTER".equals(dmlDiagramTextAlignmentName)) return CENTER;
    	if ("RIGHT".equals(dmlDiagramTextAlignmentName)) return RIGHT;
    	throw new IllegalArgumentException("Unknown DmlDiagramTextAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlDiagramTextAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		CENTER,
    		RIGHT,
    	};
    }

    public static final int length = 3;
}

