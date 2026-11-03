// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class DmlColorModifierType
{
    private DmlColorModifierType(){}
    
    public static final int ALPHA = 0;
    public static final int ALPHA_MODULATION = 1;
    public static final int ALPHA_OFFSET = 2;
    public static final int BLUE = 3;
    public static final int BLUE_MODULATION = 4;
    public static final int BLUE_OFFSET = 5;
    public static final int COMPLEMENT = 6;
    public static final int GAMMA = 7;
    public static final int GRAY = 8;
    public static final int GREEN = 9;
    public static final int GREEN_MODULATION = 10;
    public static final int GREEN_OFFSET = 11;
    public static final int HUE = 12;
    public static final int HUE_MODULATION = 13;
    public static final int HUE_OFFSET = 14;
    public static final int INVERSE = 15;
    public static final int INVERSE_GAMMA = 16;
    public static final int LUMINANCE = 17;
    public static final int LUMINANCE_MODULATION = 18;
    public static final int LUMINANCE_OFFSET = 19;
    public static final int RED = 20;
    public static final int RED_MODULATION = 21;
    public static final int RED_OFFSET = 22;
    public static final int SATURATION = 23;
    public static final int SATURATION_MODULATION = 24;
    public static final int SATURATION_OFFSET = 25;
    public static final int SHADE = 26;
    public static final int TINT = 27;
    /// <summary>
    /// This is special type of color modifier that allows applying multiple modifiers at once.
    /// </summary>
    public static final int COMPLEX_COLOR_MODIFIER = 28;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlColorModifierType value.
    /// </summary>
    public static String getName(/*DmlColorModifierType*/int dmlColorModifierType)
    {
    	switch (dmlColorModifierType)
    	{
    		case ALPHA: return "ALPHA";
    		case ALPHA_MODULATION: return "ALPHA_MODULATION";
    		case ALPHA_OFFSET: return "ALPHA_OFFSET";
    		case BLUE: return "BLUE";
    		case BLUE_MODULATION: return "BLUE_MODULATION";
    		case BLUE_OFFSET: return "BLUE_OFFSET";
    		case COMPLEMENT: return "COMPLEMENT";
    		case GAMMA: return "GAMMA";
    		case GRAY: return "GRAY";
    		case GREEN: return "GREEN";
    		case GREEN_MODULATION: return "GREEN_MODULATION";
    		case GREEN_OFFSET: return "GREEN_OFFSET";
    		case HUE: return "HUE";
    		case HUE_MODULATION: return "HUE_MODULATION";
    		case HUE_OFFSET: return "HUE_OFFSET";
    		case INVERSE: return "INVERSE";
    		case INVERSE_GAMMA: return "INVERSE_GAMMA";
    		case LUMINANCE: return "LUMINANCE";
    		case LUMINANCE_MODULATION: return "LUMINANCE_MODULATION";
    		case LUMINANCE_OFFSET: return "LUMINANCE_OFFSET";
    		case RED: return "RED";
    		case RED_MODULATION: return "RED_MODULATION";
    		case RED_OFFSET: return "RED_OFFSET";
    		case SATURATION: return "SATURATION";
    		case SATURATION_MODULATION: return "SATURATION_MODULATION";
    		case SATURATION_OFFSET: return "SATURATION_OFFSET";
    		case SHADE: return "SHADE";
    		case TINT: return "TINT";
    		case COMPLEX_COLOR_MODIFIER: return "COMPLEX_COLOR_MODIFIER";
    		default: return "Unknown DmlColorModifierType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlColorModifierType value.
    /// </summary>
    public static String toString(/*DmlColorModifierType*/int dmlColorModifierType)
    {
    	switch (dmlColorModifierType)
    	{
    		case ALPHA: return "Alpha";
    		case ALPHA_MODULATION: return "AlphaModulation";
    		case ALPHA_OFFSET: return "AlphaOffset";
    		case BLUE: return "Blue";
    		case BLUE_MODULATION: return "BlueModulation";
    		case BLUE_OFFSET: return "BlueOffset";
    		case COMPLEMENT: return "Complement";
    		case GAMMA: return "Gamma";
    		case GRAY: return "Gray";
    		case GREEN: return "Green";
    		case GREEN_MODULATION: return "GreenModulation";
    		case GREEN_OFFSET: return "GreenOffset";
    		case HUE: return "Hue";
    		case HUE_MODULATION: return "HueModulation";
    		case HUE_OFFSET: return "HueOffset";
    		case INVERSE: return "Inverse";
    		case INVERSE_GAMMA: return "InverseGamma";
    		case LUMINANCE: return "Luminance";
    		case LUMINANCE_MODULATION: return "LuminanceModulation";
    		case LUMINANCE_OFFSET: return "LuminanceOffset";
    		case RED: return "Red";
    		case RED_MODULATION: return "RedModulation";
    		case RED_OFFSET: return "RedOffset";
    		case SATURATION: return "Saturation";
    		case SATURATION_MODULATION: return "SaturationModulation";
    		case SATURATION_OFFSET: return "SaturationOffset";
    		case SHADE: return "Shade";
    		case TINT: return "Tint";
    		case COMPLEX_COLOR_MODIFIER: return "ComplexColorModifier";
    		default: return "Unknown DmlColorModifierType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlColorModifierType name.
    /// </summary>
    public static /*DmlColorModifierType*/int fromName(String dmlColorModifierTypeName)
    {
    	if ("ALPHA".equals(dmlColorModifierTypeName)) return ALPHA;
    	if ("ALPHA_MODULATION".equals(dmlColorModifierTypeName)) return ALPHA_MODULATION;
    	if ("ALPHA_OFFSET".equals(dmlColorModifierTypeName)) return ALPHA_OFFSET;
    	if ("BLUE".equals(dmlColorModifierTypeName)) return BLUE;
    	if ("BLUE_MODULATION".equals(dmlColorModifierTypeName)) return BLUE_MODULATION;
    	if ("BLUE_OFFSET".equals(dmlColorModifierTypeName)) return BLUE_OFFSET;
    	if ("COMPLEMENT".equals(dmlColorModifierTypeName)) return COMPLEMENT;
    	if ("GAMMA".equals(dmlColorModifierTypeName)) return GAMMA;
    	if ("GRAY".equals(dmlColorModifierTypeName)) return GRAY;
    	if ("GREEN".equals(dmlColorModifierTypeName)) return GREEN;
    	if ("GREEN_MODULATION".equals(dmlColorModifierTypeName)) return GREEN_MODULATION;
    	if ("GREEN_OFFSET".equals(dmlColorModifierTypeName)) return GREEN_OFFSET;
    	if ("HUE".equals(dmlColorModifierTypeName)) return HUE;
    	if ("HUE_MODULATION".equals(dmlColorModifierTypeName)) return HUE_MODULATION;
    	if ("HUE_OFFSET".equals(dmlColorModifierTypeName)) return HUE_OFFSET;
    	if ("INVERSE".equals(dmlColorModifierTypeName)) return INVERSE;
    	if ("INVERSE_GAMMA".equals(dmlColorModifierTypeName)) return INVERSE_GAMMA;
    	if ("LUMINANCE".equals(dmlColorModifierTypeName)) return LUMINANCE;
    	if ("LUMINANCE_MODULATION".equals(dmlColorModifierTypeName)) return LUMINANCE_MODULATION;
    	if ("LUMINANCE_OFFSET".equals(dmlColorModifierTypeName)) return LUMINANCE_OFFSET;
    	if ("RED".equals(dmlColorModifierTypeName)) return RED;
    	if ("RED_MODULATION".equals(dmlColorModifierTypeName)) return RED_MODULATION;
    	if ("RED_OFFSET".equals(dmlColorModifierTypeName)) return RED_OFFSET;
    	if ("SATURATION".equals(dmlColorModifierTypeName)) return SATURATION;
    	if ("SATURATION_MODULATION".equals(dmlColorModifierTypeName)) return SATURATION_MODULATION;
    	if ("SATURATION_OFFSET".equals(dmlColorModifierTypeName)) return SATURATION_OFFSET;
    	if ("SHADE".equals(dmlColorModifierTypeName)) return SHADE;
    	if ("TINT".equals(dmlColorModifierTypeName)) return TINT;
    	if ("COMPLEX_COLOR_MODIFIER".equals(dmlColorModifierTypeName)) return COMPLEX_COLOR_MODIFIER;
    	throw new IllegalArgumentException("Unknown DmlColorModifierType name.");
    }
    
    /// <summary>
    /// Returns array of DmlColorModifierType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALPHA,
    		ALPHA_MODULATION,
    		ALPHA_OFFSET,
    		BLUE,
    		BLUE_MODULATION,
    		BLUE_OFFSET,
    		COMPLEMENT,
    		GAMMA,
    		GRAY,
    		GREEN,
    		GREEN_MODULATION,
    		GREEN_OFFSET,
    		HUE,
    		HUE_MODULATION,
    		HUE_OFFSET,
    		INVERSE,
    		INVERSE_GAMMA,
    		LUMINANCE,
    		LUMINANCE_MODULATION,
    		LUMINANCE_OFFSET,
    		RED,
    		RED_MODULATION,
    		RED_OFFSET,
    		SATURATION,
    		SATURATION_MODULATION,
    		SATURATION_OFFSET,
    		SHADE,
    		TINT,
    		COMPLEX_COLOR_MODIFIER,
    	};
    }

    public static final int length = 29;
}

