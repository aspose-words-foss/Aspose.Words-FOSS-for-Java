// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the node layout in a diagram. Makes sense for organizational charts only.
/// </summary>
/*enum*/ final class DiagramNodeLayout
{
    private DiagramNodeLayout(){}
    
    /// <summary>
    /// Top-down, centered layout.
    /// </summary>
    public static final int STANDARD = 0;
    /// <summary>
    /// Hanging, both sides of the layout.
    /// </summary>
    public static final int BOTH_HANGING = 1;
    /// <summary>
    /// Hanging, right side of the layout.
    /// </summary>
    public static final int RIGHT_HANGING = 2;
    /// <summary>
    /// Hanging, left side of the layout.
    /// </summary>
    public static final int LEFT_HANGING = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DiagramNodeLayout value.
    /// </summary>
    public static String getName(/*DiagramNodeLayout*/int diagramNodeLayout)
    {
    	switch (diagramNodeLayout)
    	{
    		case STANDARD: return "STANDARD";
    		case BOTH_HANGING: return "BOTH_HANGING";
    		case RIGHT_HANGING: return "RIGHT_HANGING";
    		case LEFT_HANGING: return "LEFT_HANGING";
    		default: return "Unknown DiagramNodeLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DiagramNodeLayout value.
    /// </summary>
    public static String toString(/*DiagramNodeLayout*/int diagramNodeLayout)
    {
    	switch (diagramNodeLayout)
    	{
    		case STANDARD: return "Standard";
    		case BOTH_HANGING: return "BothHanging";
    		case RIGHT_HANGING: return "RightHanging";
    		case LEFT_HANGING: return "LeftHanging";
    		default: return "Unknown DiagramNodeLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DiagramNodeLayout name.
    /// </summary>
    public static /*DiagramNodeLayout*/int fromName(String diagramNodeLayoutName)
    {
    	if ("STANDARD".equals(diagramNodeLayoutName)) return STANDARD;
    	if ("BOTH_HANGING".equals(diagramNodeLayoutName)) return BOTH_HANGING;
    	if ("RIGHT_HANGING".equals(diagramNodeLayoutName)) return RIGHT_HANGING;
    	if ("LEFT_HANGING".equals(diagramNodeLayoutName)) return LEFT_HANGING;
    	throw new IllegalArgumentException("Unknown DiagramNodeLayout name.");
    }
    
    /// <summary>
    /// Returns array of DiagramNodeLayout values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STANDARD,
    		BOTH_HANGING,
    		RIGHT_HANGING,
    		LEFT_HANGING,
    	};
    }

    public static final int length = 4;
}

