// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.65 ST_VerticalAlignment (Vertical Alignment)
/// </summary>
/*enum*/ final class DmlVerticalAlignment
{
    private DmlVerticalAlignment(){}
    
    public static final int BOTTOM = 0;
    public static final int MIDDLE = 1;
    public static final int NONE = 2;
    public static final int TOP = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlVerticalAlignment value.
    /// </summary>
    public static String getName(/*DmlVerticalAlignment*/int dmlVerticalAlignment)
    {
    	switch (dmlVerticalAlignment)
    	{
    		case BOTTOM: return "BOTTOM";
    		case MIDDLE: return "MIDDLE";
    		case NONE: return "NONE";
    		case TOP: return "TOP";
    		default: return "Unknown DmlVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlVerticalAlignment value.
    /// </summary>
    public static String toString(/*DmlVerticalAlignment*/int dmlVerticalAlignment)
    {
    	switch (dmlVerticalAlignment)
    	{
    		case BOTTOM: return "Bottom";
    		case MIDDLE: return "Middle";
    		case NONE: return "None";
    		case TOP: return "Top";
    		default: return "Unknown DmlVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlVerticalAlignment name.
    /// </summary>
    public static /*DmlVerticalAlignment*/int fromName(String dmlVerticalAlignmentName)
    {
    	if ("BOTTOM".equals(dmlVerticalAlignmentName)) return BOTTOM;
    	if ("MIDDLE".equals(dmlVerticalAlignmentName)) return MIDDLE;
    	if ("NONE".equals(dmlVerticalAlignmentName)) return NONE;
    	if ("TOP".equals(dmlVerticalAlignmentName)) return TOP;
    	throw new IllegalArgumentException("Unknown DmlVerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlVerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM,
    		MIDDLE,
    		NONE,
    		TOP,
    	};
    }

    public static final int length = 4;
}

