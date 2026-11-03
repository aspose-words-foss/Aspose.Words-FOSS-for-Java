// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of Dml color representation.
/// </summary>
/*enum*/ final class DmlColorType
{
    private DmlColorType(){}
    
    /// <summary>
    /// Color is represented using the red, green, blue RGB color model. 
    /// Red, green, and blue is expressed as sequence of hex digits, RRGGB
    /// </summary>
    public static final int HEX_RGB_COLOR = 0;
    
    /// <summary>
    /// Color is represented using the HSL color model.
    /// </summary>
    public static final int HSL_COLOR = 1;
    
    /// <summary>
    /// Color is represented using the red, green, blue RGB color model. 
    /// Each component, red, green, and blue is expressed as a percentage from 0% to 100%.
    /// </summary>
    public static final int PERCENTAGE_RGB_COLOR = 2;
    
    /// <summary>
    /// This color can appear in themes. It is represented by scheme color with value "phClr".
    /// </summary>
    public static final int PLACEHOLDER_COLOR = 3;
    
    /// <summary>
    /// Color which is bound to one of a predefined collection of colors.
    /// </summary>
    public static final int PRESET_COLOR = 4;
    
    /// <summary>
    /// Color bound to a user's theme. 
    /// </summary>
    public static final int SCHEME_COLOR = 5;
    
    /// <summary>
    /// Color bound to predefined operating system elements.
    /// </summary>
    public static final int SYSTEM_COLOR = 6;

    /// <summary>
    /// Color which is retrieved from a color style of a chart.
    /// </summary>
    public static final int CHART_STYLE_COLOR = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlColorType value.
    /// </summary>
    public static String getName(/*DmlColorType*/int dmlColorType)
    {
    	switch (dmlColorType)
    	{
    		case HEX_RGB_COLOR: return "HEX_RGB_COLOR";
    		case HSL_COLOR: return "HSL_COLOR";
    		case PERCENTAGE_RGB_COLOR: return "PERCENTAGE_RGB_COLOR";
    		case PLACEHOLDER_COLOR: return "PLACEHOLDER_COLOR";
    		case PRESET_COLOR: return "PRESET_COLOR";
    		case SCHEME_COLOR: return "SCHEME_COLOR";
    		case SYSTEM_COLOR: return "SYSTEM_COLOR";
    		case CHART_STYLE_COLOR: return "CHART_STYLE_COLOR";
    		default: return "Unknown DmlColorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlColorType value.
    /// </summary>
    public static String toString(/*DmlColorType*/int dmlColorType)
    {
    	switch (dmlColorType)
    	{
    		case HEX_RGB_COLOR: return "HexRgbColor";
    		case HSL_COLOR: return "HslColor";
    		case PERCENTAGE_RGB_COLOR: return "PercentageRgbColor";
    		case PLACEHOLDER_COLOR: return "PlaceholderColor";
    		case PRESET_COLOR: return "PresetColor";
    		case SCHEME_COLOR: return "SchemeColor";
    		case SYSTEM_COLOR: return "SystemColor";
    		case CHART_STYLE_COLOR: return "ChartStyleColor";
    		default: return "Unknown DmlColorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlColorType name.
    /// </summary>
    public static /*DmlColorType*/int fromName(String dmlColorTypeName)
    {
    	if ("HEX_RGB_COLOR".equals(dmlColorTypeName)) return HEX_RGB_COLOR;
    	if ("HSL_COLOR".equals(dmlColorTypeName)) return HSL_COLOR;
    	if ("PERCENTAGE_RGB_COLOR".equals(dmlColorTypeName)) return PERCENTAGE_RGB_COLOR;
    	if ("PLACEHOLDER_COLOR".equals(dmlColorTypeName)) return PLACEHOLDER_COLOR;
    	if ("PRESET_COLOR".equals(dmlColorTypeName)) return PRESET_COLOR;
    	if ("SCHEME_COLOR".equals(dmlColorTypeName)) return SCHEME_COLOR;
    	if ("SYSTEM_COLOR".equals(dmlColorTypeName)) return SYSTEM_COLOR;
    	if ("CHART_STYLE_COLOR".equals(dmlColorTypeName)) return CHART_STYLE_COLOR;
    	throw new IllegalArgumentException("Unknown DmlColorType name.");
    }
    
    /// <summary>
    /// Returns array of DmlColorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HEX_RGB_COLOR,
    		HSL_COLOR,
    		PERCENTAGE_RGB_COLOR,
    		PLACEHOLDER_COLOR,
    		PRESET_COLOR,
    		SCHEME_COLOR,
    		SYSTEM_COLOR,
    		CHART_STYLE_COLOR,
    	};
    }

    public static final int length = 8;
}

