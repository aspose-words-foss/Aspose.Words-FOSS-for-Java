// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Dml effects types.
/// </summary>
/*enum*/ final class DmlEffectType
{
    private DmlEffectType(){}
    
    public static final int ALPHA_BI_LEVEL = 0;
    public static final int ALPHA_CEILING = 1;
    public static final int ALPHA_FLOOR = 2;
    public static final int ALPHA_INVERSE = 3;
    public static final int ALPHA_MODULATE = 4;
    public static final int ALPHA_MODULATE_FIXED = 5;
    public static final int ALPHA_REPLACE = 6;
    public static final int BI_LEVEL = 7;
    public static final int BLUR = 8;
    public static final int COLOR_CHANGE = 9;
    public static final int COLOR_REPLACE = 10;
    public static final int DUOTONE = 11;
    public static final int FILL_OVERLAY = 12;
    public static final int GRAY_SCALE = 13;
    public static final int HUE_SATURATION_LUMINANCE = 14;
    public static final int LUMINANCE = 15;
    public static final int TINT = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlEffectType value.
    /// </summary>
    public static String getName(/*DmlEffectType*/int dmlEffectType)
    {
    	switch (dmlEffectType)
    	{
    		case ALPHA_BI_LEVEL: return "ALPHA_BI_LEVEL";
    		case ALPHA_CEILING: return "ALPHA_CEILING";
    		case ALPHA_FLOOR: return "ALPHA_FLOOR";
    		case ALPHA_INVERSE: return "ALPHA_INVERSE";
    		case ALPHA_MODULATE: return "ALPHA_MODULATE";
    		case ALPHA_MODULATE_FIXED: return "ALPHA_MODULATE_FIXED";
    		case ALPHA_REPLACE: return "ALPHA_REPLACE";
    		case BI_LEVEL: return "BI_LEVEL";
    		case BLUR: return "BLUR";
    		case COLOR_CHANGE: return "COLOR_CHANGE";
    		case COLOR_REPLACE: return "COLOR_REPLACE";
    		case DUOTONE: return "DUOTONE";
    		case FILL_OVERLAY: return "FILL_OVERLAY";
    		case GRAY_SCALE: return "GRAY_SCALE";
    		case HUE_SATURATION_LUMINANCE: return "HUE_SATURATION_LUMINANCE";
    		case LUMINANCE: return "LUMINANCE";
    		case TINT: return "TINT";
    		default: return "Unknown DmlEffectType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlEffectType value.
    /// </summary>
    public static String toString(/*DmlEffectType*/int dmlEffectType)
    {
    	switch (dmlEffectType)
    	{
    		case ALPHA_BI_LEVEL: return "AlphaBiLevel";
    		case ALPHA_CEILING: return "AlphaCeiling";
    		case ALPHA_FLOOR: return "AlphaFloor";
    		case ALPHA_INVERSE: return "AlphaInverse";
    		case ALPHA_MODULATE: return "AlphaModulate";
    		case ALPHA_MODULATE_FIXED: return "AlphaModulateFixed";
    		case ALPHA_REPLACE: return "AlphaReplace";
    		case BI_LEVEL: return "BiLevel";
    		case BLUR: return "Blur";
    		case COLOR_CHANGE: return "ColorChange";
    		case COLOR_REPLACE: return "ColorReplace";
    		case DUOTONE: return "Duotone";
    		case FILL_OVERLAY: return "FillOverlay";
    		case GRAY_SCALE: return "GrayScale";
    		case HUE_SATURATION_LUMINANCE: return "HueSaturationLuminance";
    		case LUMINANCE: return "Luminance";
    		case TINT: return "Tint";
    		default: return "Unknown DmlEffectType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlEffectType name.
    /// </summary>
    public static /*DmlEffectType*/int fromName(String dmlEffectTypeName)
    {
    	if ("ALPHA_BI_LEVEL".equals(dmlEffectTypeName)) return ALPHA_BI_LEVEL;
    	if ("ALPHA_CEILING".equals(dmlEffectTypeName)) return ALPHA_CEILING;
    	if ("ALPHA_FLOOR".equals(dmlEffectTypeName)) return ALPHA_FLOOR;
    	if ("ALPHA_INVERSE".equals(dmlEffectTypeName)) return ALPHA_INVERSE;
    	if ("ALPHA_MODULATE".equals(dmlEffectTypeName)) return ALPHA_MODULATE;
    	if ("ALPHA_MODULATE_FIXED".equals(dmlEffectTypeName)) return ALPHA_MODULATE_FIXED;
    	if ("ALPHA_REPLACE".equals(dmlEffectTypeName)) return ALPHA_REPLACE;
    	if ("BI_LEVEL".equals(dmlEffectTypeName)) return BI_LEVEL;
    	if ("BLUR".equals(dmlEffectTypeName)) return BLUR;
    	if ("COLOR_CHANGE".equals(dmlEffectTypeName)) return COLOR_CHANGE;
    	if ("COLOR_REPLACE".equals(dmlEffectTypeName)) return COLOR_REPLACE;
    	if ("DUOTONE".equals(dmlEffectTypeName)) return DUOTONE;
    	if ("FILL_OVERLAY".equals(dmlEffectTypeName)) return FILL_OVERLAY;
    	if ("GRAY_SCALE".equals(dmlEffectTypeName)) return GRAY_SCALE;
    	if ("HUE_SATURATION_LUMINANCE".equals(dmlEffectTypeName)) return HUE_SATURATION_LUMINANCE;
    	if ("LUMINANCE".equals(dmlEffectTypeName)) return LUMINANCE;
    	if ("TINT".equals(dmlEffectTypeName)) return TINT;
    	throw new IllegalArgumentException("Unknown DmlEffectType name.");
    }
    
    /// <summary>
    /// Returns array of DmlEffectType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALPHA_BI_LEVEL,
    		ALPHA_CEILING,
    		ALPHA_FLOOR,
    		ALPHA_INVERSE,
    		ALPHA_MODULATE,
    		ALPHA_MODULATE_FIXED,
    		ALPHA_REPLACE,
    		BI_LEVEL,
    		BLUR,
    		COLOR_CHANGE,
    		COLOR_REPLACE,
    		DUOTONE,
    		FILL_OVERLAY,
    		GRAY_SCALE,
    		HUE_SATURATION_LUMINANCE,
    		LUMINANCE,
    		TINT,
    	};
    }

    public static final int length = 17;
}

