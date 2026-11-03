// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2015 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.60 ST_TextAnchorVertical (Text Anchor Vertical)
/// </summary>
/*enum*/ final class DmlTextAnchorVertical
{
    private DmlTextAnchorVertical(){}
    
    public static final int BOTTOM = 0;
    public static final int MIDDLE = 1;
    public static final int TOP = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextAnchorVertical value.
    /// </summary>
    public static String getName(/*DmlTextAnchorVertical*/int dmlTextAnchorVertical)
    {
    	switch (dmlTextAnchorVertical)
    	{
    		case BOTTOM: return "BOTTOM";
    		case MIDDLE: return "MIDDLE";
    		case TOP: return "TOP";
    		default: return "Unknown DmlTextAnchorVertical value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextAnchorVertical value.
    /// </summary>
    public static String toString(/*DmlTextAnchorVertical*/int dmlTextAnchorVertical)
    {
    	switch (dmlTextAnchorVertical)
    	{
    		case BOTTOM: return "Bottom";
    		case MIDDLE: return "Middle";
    		case TOP: return "Top";
    		default: return "Unknown DmlTextAnchorVertical value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextAnchorVertical name.
    /// </summary>
    public static /*DmlTextAnchorVertical*/int fromName(String dmlTextAnchorVerticalName)
    {
    	if ("BOTTOM".equals(dmlTextAnchorVerticalName)) return BOTTOM;
    	if ("MIDDLE".equals(dmlTextAnchorVerticalName)) return MIDDLE;
    	if ("TOP".equals(dmlTextAnchorVerticalName)) return TOP;
    	throw new IllegalArgumentException("Unknown DmlTextAnchorVertical name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextAnchorVertical values.
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

