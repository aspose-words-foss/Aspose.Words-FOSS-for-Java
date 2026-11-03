// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.46 ST_NodeVerticalAlignment (Node Vertical Alignment)
/// </summary>
/*enum*/ final class DmlNodeVerticalAlignment
{
    private DmlNodeVerticalAlignment(){}
    
    public static final int BOTTOM = 0;
    public static final int MIDDLE = 1;
    public static final int TOP = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlNodeVerticalAlignment value.
    /// </summary>
    public static String getName(/*DmlNodeVerticalAlignment*/int dmlNodeVerticalAlignment)
    {
    	switch (dmlNodeVerticalAlignment)
    	{
    		case BOTTOM: return "BOTTOM";
    		case MIDDLE: return "MIDDLE";
    		case TOP: return "TOP";
    		default: return "Unknown DmlNodeVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlNodeVerticalAlignment value.
    /// </summary>
    public static String toString(/*DmlNodeVerticalAlignment*/int dmlNodeVerticalAlignment)
    {
    	switch (dmlNodeVerticalAlignment)
    	{
    		case BOTTOM: return "Bottom";
    		case MIDDLE: return "Middle";
    		case TOP: return "Top";
    		default: return "Unknown DmlNodeVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlNodeVerticalAlignment name.
    /// </summary>
    public static /*DmlNodeVerticalAlignment*/int fromName(String dmlNodeVerticalAlignmentName)
    {
    	if ("BOTTOM".equals(dmlNodeVerticalAlignmentName)) return BOTTOM;
    	if ("MIDDLE".equals(dmlNodeVerticalAlignmentName)) return MIDDLE;
    	if ("TOP".equals(dmlNodeVerticalAlignmentName)) return TOP;
    	throw new IllegalArgumentException("Unknown DmlNodeVerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlNodeVerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM,
    		MIDDLE,
    		TOP,
    	};
    }

    public static final int length = 3;
}

