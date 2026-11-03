// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.37 ST_HierBranchStyle (Hierarchy Branch Style Definition)
/// </summary>
/*enum*/ final class DmlHierBranchStyle
{
    private DmlHierBranchStyle(){}
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int HANGING = 2;
    public static final int STANDARD = 3;
    public static final int INITIAL = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlHierBranchStyle value.
    /// </summary>
    public static String getName(/*DmlHierBranchStyle*/int dmlHierBranchStyle)
    {
    	switch (dmlHierBranchStyle)
    	{
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case HANGING: return "HANGING";
    		case STANDARD: return "STANDARD";
    		case INITIAL: return "INITIAL";
    		default: return "Unknown DmlHierBranchStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlHierBranchStyle value.
    /// </summary>
    public static String toString(/*DmlHierBranchStyle*/int dmlHierBranchStyle)
    {
    	switch (dmlHierBranchStyle)
    	{
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case HANGING: return "Hanging";
    		case STANDARD: return "Standard";
    		case INITIAL: return "Initial";
    		default: return "Unknown DmlHierBranchStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlHierBranchStyle name.
    /// </summary>
    public static /*DmlHierBranchStyle*/int fromName(String dmlHierBranchStyleName)
    {
    	if ("LEFT".equals(dmlHierBranchStyleName)) return LEFT;
    	if ("RIGHT".equals(dmlHierBranchStyleName)) return RIGHT;
    	if ("HANGING".equals(dmlHierBranchStyleName)) return HANGING;
    	if ("STANDARD".equals(dmlHierBranchStyleName)) return STANDARD;
    	if ("INITIAL".equals(dmlHierBranchStyleName)) return INITIAL;
    	throw new IllegalArgumentException("Unknown DmlHierBranchStyle name.");
    }
    
    /// <summary>
    /// Returns array of DmlHierBranchStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LEFT,
    		RIGHT,
    		HANGING,
    		STANDARD,
    		INITIAL,
    	};
    }

    public static final int length = 5;
}

