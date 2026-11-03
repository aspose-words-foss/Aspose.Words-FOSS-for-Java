// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2019 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.4 ST_ArrowheadStyle (Arrowhead Styles)
/// </summary>
/*enum*/ final class DmlArrowheadStyle
{
    private DmlArrowheadStyle(){}
    
    public static final int ARROWHEAD_PRESENT = 0;
    public static final int AUTO = 1;
    public static final int NO_ARROWHEAD = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlArrowheadStyle value.
    /// </summary>
    public static String getName(/*DmlArrowheadStyle*/int dmlArrowheadStyle)
    {
    	switch (dmlArrowheadStyle)
    	{
    		case ARROWHEAD_PRESENT: return "ARROWHEAD_PRESENT";
    		case AUTO: return "AUTO";
    		case NO_ARROWHEAD: return "NO_ARROWHEAD";
    		default: return "Unknown DmlArrowheadStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlArrowheadStyle value.
    /// </summary>
    public static String toString(/*DmlArrowheadStyle*/int dmlArrowheadStyle)
    {
    	switch (dmlArrowheadStyle)
    	{
    		case ARROWHEAD_PRESENT: return "ArrowheadPresent";
    		case AUTO: return "Auto";
    		case NO_ARROWHEAD: return "NoArrowhead";
    		default: return "Unknown DmlArrowheadStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlArrowheadStyle name.
    /// </summary>
    public static /*DmlArrowheadStyle*/int fromName(String dmlArrowheadStyleName)
    {
    	if ("ARROWHEAD_PRESENT".equals(dmlArrowheadStyleName)) return ARROWHEAD_PRESENT;
    	if ("AUTO".equals(dmlArrowheadStyleName)) return AUTO;
    	if ("NO_ARROWHEAD".equals(dmlArrowheadStyleName)) return NO_ARROWHEAD;
    	throw new IllegalArgumentException("Unknown DmlArrowheadStyle name.");
    }
    
    /// <summary>
    /// Returns array of DmlArrowheadStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ARROWHEAD_PRESENT,
    		AUTO,
    		NO_ARROWHEAD,
    	};
    }

    public static final int length = 3;
}

