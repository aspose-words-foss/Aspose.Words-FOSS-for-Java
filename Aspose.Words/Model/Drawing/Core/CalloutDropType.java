// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the drop type of a callout.
/// </summary>
/*enum*/ final class CalloutDropType
{
    private CalloutDropType(){}
    
    public static final int TOP = 0;
    public static final int CENTER = 1;
    public static final int BOTTOM = 2;
    public static final int SPECIFIED = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalloutDropType value.
    /// </summary>
    public static String getName(/*CalloutDropType*/int calloutDropType)
    {
    	switch (calloutDropType)
    	{
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BOTTOM: return "BOTTOM";
    		case SPECIFIED: return "SPECIFIED";
    		default: return "Unknown CalloutDropType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalloutDropType value.
    /// </summary>
    public static String toString(/*CalloutDropType*/int calloutDropType)
    {
    	switch (calloutDropType)
    	{
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BOTTOM: return "Bottom";
    		case SPECIFIED: return "Specified";
    		default: return "Unknown CalloutDropType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalloutDropType name.
    /// </summary>
    public static /*CalloutDropType*/int fromName(String calloutDropTypeName)
    {
    	if ("TOP".equals(calloutDropTypeName)) return TOP;
    	if ("CENTER".equals(calloutDropTypeName)) return CENTER;
    	if ("BOTTOM".equals(calloutDropTypeName)) return BOTTOM;
    	if ("SPECIFIED".equals(calloutDropTypeName)) return SPECIFIED;
    	throw new IllegalArgumentException("Unknown CalloutDropType name.");
    }
    
    /// <summary>
    /// Returns array of CalloutDropType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		CENTER,
    		BOTTOM,
    		SPECIFIED,
    	};
    }

    public static final int length = 4;
}

