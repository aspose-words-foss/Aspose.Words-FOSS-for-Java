// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Determines where vertically on a line of text the actual words are positioned.
/// </summary>
/*enum*/ final class DmlFontAlignment
{
    private DmlFontAlignment(){}
    
    public static final int AUTOMATIC = 0;
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int BASELINE = 3;
    public static final int BOTTOM = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFontAlignment value.
    /// </summary>
    public static String getName(/*DmlFontAlignment*/int dmlFontAlignment)
    {
    	switch (dmlFontAlignment)
    	{
    		case AUTOMATIC: return "AUTOMATIC";
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BASELINE: return "BASELINE";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown DmlFontAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFontAlignment value.
    /// </summary>
    public static String toString(/*DmlFontAlignment*/int dmlFontAlignment)
    {
    	switch (dmlFontAlignment)
    	{
    		case AUTOMATIC: return "Automatic";
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BASELINE: return "Baseline";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown DmlFontAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFontAlignment name.
    /// </summary>
    public static /*DmlFontAlignment*/int fromName(String dmlFontAlignmentName)
    {
    	if ("AUTOMATIC".equals(dmlFontAlignmentName)) return AUTOMATIC;
    	if ("TOP".equals(dmlFontAlignmentName)) return TOP;
    	if ("CENTER".equals(dmlFontAlignmentName)) return CENTER;
    	if ("BASELINE".equals(dmlFontAlignmentName)) return BASELINE;
    	if ("BOTTOM".equals(dmlFontAlignmentName)) return BOTTOM;
    	throw new IllegalArgumentException("Unknown DmlFontAlignment name.");
    }
    
    /// <summary>
    /// Returns array of DmlFontAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTOMATIC,
    		TOP,
    		CENTER,
    		BASELINE,
    		BOTTOM,
    	};
    }

    public static final int length = 5;
}
