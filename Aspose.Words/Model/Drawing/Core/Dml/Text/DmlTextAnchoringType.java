// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text anchoring types
/// </summary>
/*enum*/ final class DmlTextAnchoringType
{
    private DmlTextAnchoringType(){}
    
    public static final int TOP = 0;
    public static final int CENTER = 1;
    public static final int BOTTOM = 2;
    public static final int JUSTIFIED = 3;
    public static final int DISTRIBUTED = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextAnchoringType value.
    /// </summary>
    public static String getName(/*DmlTextAnchoringType*/int dmlTextAnchoringType)
    {
    	switch (dmlTextAnchoringType)
    	{
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BOTTOM: return "BOTTOM";
    		case JUSTIFIED: return "JUSTIFIED";
    		case DISTRIBUTED: return "DISTRIBUTED";
    		default: return "Unknown DmlTextAnchoringType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextAnchoringType value.
    /// </summary>
    public static String toString(/*DmlTextAnchoringType*/int dmlTextAnchoringType)
    {
    	switch (dmlTextAnchoringType)
    	{
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BOTTOM: return "Bottom";
    		case JUSTIFIED: return "Justified";
    		case DISTRIBUTED: return "Distributed";
    		default: return "Unknown DmlTextAnchoringType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextAnchoringType name.
    /// </summary>
    public static /*DmlTextAnchoringType*/int fromName(String dmlTextAnchoringTypeName)
    {
    	if ("TOP".equals(dmlTextAnchoringTypeName)) return TOP;
    	if ("CENTER".equals(dmlTextAnchoringTypeName)) return CENTER;
    	if ("BOTTOM".equals(dmlTextAnchoringTypeName)) return BOTTOM;
    	if ("JUSTIFIED".equals(dmlTextAnchoringTypeName)) return JUSTIFIED;
    	if ("DISTRIBUTED".equals(dmlTextAnchoringTypeName)) return DISTRIBUTED;
    	throw new IllegalArgumentException("Unknown DmlTextAnchoringType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextAnchoringType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		CENTER,
    		BOTTOM,
    		JUSTIFIED,
    		DISTRIBUTED,
    	};
    }

    public static final int length = 5;
}
