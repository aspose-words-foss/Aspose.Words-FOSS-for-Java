// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of Dml Fill.
/// </summary>
/*enum*/ final class DmlFillType
{
    private DmlFillType(){}
    
    public static final int BLIP_FILL = 0;
    public static final int GRADIENT_FILL = 1;
    public static final int GROUP_FILL = 2;
    public static final int NO_FILL = 3;
    public static final int PATTERN_FILL = 4;
    public static final int SOLID_FILL = 5;
    public static final int STYLE_FILL = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFillType value.
    /// </summary>
    public static String getName(/*DmlFillType*/int dmlFillType)
    {
    	switch (dmlFillType)
    	{
    		case BLIP_FILL: return "BLIP_FILL";
    		case GRADIENT_FILL: return "GRADIENT_FILL";
    		case GROUP_FILL: return "GROUP_FILL";
    		case NO_FILL: return "NO_FILL";
    		case PATTERN_FILL: return "PATTERN_FILL";
    		case SOLID_FILL: return "SOLID_FILL";
    		case STYLE_FILL: return "STYLE_FILL";
    		default: return "Unknown DmlFillType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFillType value.
    /// </summary>
    public static String toString(/*DmlFillType*/int dmlFillType)
    {
    	switch (dmlFillType)
    	{
    		case BLIP_FILL: return "BlipFill";
    		case GRADIENT_FILL: return "GradientFill";
    		case GROUP_FILL: return "GroupFill";
    		case NO_FILL: return "NoFill";
    		case PATTERN_FILL: return "PatternFill";
    		case SOLID_FILL: return "SolidFill";
    		case STYLE_FILL: return "StyleFill";
    		default: return "Unknown DmlFillType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFillType name.
    /// </summary>
    public static /*DmlFillType*/int fromName(String dmlFillTypeName)
    {
    	if ("BLIP_FILL".equals(dmlFillTypeName)) return BLIP_FILL;
    	if ("GRADIENT_FILL".equals(dmlFillTypeName)) return GRADIENT_FILL;
    	if ("GROUP_FILL".equals(dmlFillTypeName)) return GROUP_FILL;
    	if ("NO_FILL".equals(dmlFillTypeName)) return NO_FILL;
    	if ("PATTERN_FILL".equals(dmlFillTypeName)) return PATTERN_FILL;
    	if ("SOLID_FILL".equals(dmlFillTypeName)) return SOLID_FILL;
    	if ("STYLE_FILL".equals(dmlFillTypeName)) return STYLE_FILL;
    	throw new IllegalArgumentException("Unknown DmlFillType name.");
    }
    
    /// <summary>
    /// Returns array of DmlFillType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BLIP_FILL,
    		GRADIENT_FILL,
    		GROUP_FILL,
    		NO_FILL,
    		PATTERN_FILL,
    		SOLID_FILL,
    		STYLE_FILL,
    	};
    }

    public static final int length = 7;
}

