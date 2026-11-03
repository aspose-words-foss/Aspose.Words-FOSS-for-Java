// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.10.51 ST_PresetShadowVal (Preset Shadow Type)
/// This simple type indicates one of 20 preset shadow types.
/// </summary>
/*enum*/ final class DmlPresetShadow
{
    private DmlPresetShadow(){}
    
    public static final int TOP_LEFT_DROP_SHADOW = 0;
    public static final int TOP_LEFT_LARGE_DROP_SHADOW = 1;
    public static final int BACK_LEFT_LONG_PERSPECTIVE_SHADOW = 2;
    public static final int BACK_RIGHT_LONG_PERSPECTIVE_SHADOW = 3;
    public static final int TOP_LEFT_DOUBLE_DROP_SHADOW = 4;
    public static final int BOTTOM_RIGHT_SMALL_DROP_SHADOW = 5;
    public static final int FRONT_LEFT_LONG_PERSPECTIVE_SHADOW = 6;
    public static final int FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW = 7;
    public static final int OUTER_BOX_SHADOW_3_D = 8;
    public static final int INNER_BOX_SHADOW_3_D = 9;
    public static final int BACK_CENTER_PERSPECTIVE_SHADOW = 10;
    public static final int TOP_RIGHT_DROP_SHADOW = 11;
    public static final int FRONT_BOTTOM_SHADOW = 12;
    public static final int BACK_LEFT_PERSPECTIVE_SHADOW = 13;
    public static final int BACK_RIGHT_PERSPECTIVE_SHADOW = 14;
    public static final int BOTTOM_LEFT_DROP_SHADOW = 15;
    public static final int BOTTOM_RIGHT_DROP_SHADOW = 16;
    public static final int FRONT_LEFT_PERSPECTIVE_SHADOW = 17;
    public static final int FRONT_RIGHT_PERSPECTIVE_SHADOW = 18;
    public static final int TOP_LEFT_SMALL_DROP_SHADOW = 19;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPresetShadow value.
    /// </summary>
    public static String getName(/*DmlPresetShadow*/int dmlPresetShadow)
    {
    	switch (dmlPresetShadow)
    	{
    		case TOP_LEFT_DROP_SHADOW: return "TOP_LEFT_DROP_SHADOW";
    		case TOP_LEFT_LARGE_DROP_SHADOW: return "TOP_LEFT_LARGE_DROP_SHADOW";
    		case BACK_LEFT_LONG_PERSPECTIVE_SHADOW: return "BACK_LEFT_LONG_PERSPECTIVE_SHADOW";
    		case BACK_RIGHT_LONG_PERSPECTIVE_SHADOW: return "BACK_RIGHT_LONG_PERSPECTIVE_SHADOW";
    		case TOP_LEFT_DOUBLE_DROP_SHADOW: return "TOP_LEFT_DOUBLE_DROP_SHADOW";
    		case BOTTOM_RIGHT_SMALL_DROP_SHADOW: return "BOTTOM_RIGHT_SMALL_DROP_SHADOW";
    		case FRONT_LEFT_LONG_PERSPECTIVE_SHADOW: return "FRONT_LEFT_LONG_PERSPECTIVE_SHADOW";
    		case FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW: return "FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW";
    		case OUTER_BOX_SHADOW_3_D: return "OUTER_BOX_SHADOW_3_D";
    		case INNER_BOX_SHADOW_3_D: return "INNER_BOX_SHADOW_3_D";
    		case BACK_CENTER_PERSPECTIVE_SHADOW: return "BACK_CENTER_PERSPECTIVE_SHADOW";
    		case TOP_RIGHT_DROP_SHADOW: return "TOP_RIGHT_DROP_SHADOW";
    		case FRONT_BOTTOM_SHADOW: return "FRONT_BOTTOM_SHADOW";
    		case BACK_LEFT_PERSPECTIVE_SHADOW: return "BACK_LEFT_PERSPECTIVE_SHADOW";
    		case BACK_RIGHT_PERSPECTIVE_SHADOW: return "BACK_RIGHT_PERSPECTIVE_SHADOW";
    		case BOTTOM_LEFT_DROP_SHADOW: return "BOTTOM_LEFT_DROP_SHADOW";
    		case BOTTOM_RIGHT_DROP_SHADOW: return "BOTTOM_RIGHT_DROP_SHADOW";
    		case FRONT_LEFT_PERSPECTIVE_SHADOW: return "FRONT_LEFT_PERSPECTIVE_SHADOW";
    		case FRONT_RIGHT_PERSPECTIVE_SHADOW: return "FRONT_RIGHT_PERSPECTIVE_SHADOW";
    		case TOP_LEFT_SMALL_DROP_SHADOW: return "TOP_LEFT_SMALL_DROP_SHADOW";
    		default: return "Unknown DmlPresetShadow value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPresetShadow value.
    /// </summary>
    public static String toString(/*DmlPresetShadow*/int dmlPresetShadow)
    {
    	switch (dmlPresetShadow)
    	{
    		case TOP_LEFT_DROP_SHADOW: return "TopLeftDropShadow";
    		case TOP_LEFT_LARGE_DROP_SHADOW: return "TopLeftLargeDropShadow";
    		case BACK_LEFT_LONG_PERSPECTIVE_SHADOW: return "BackLeftLongPerspectiveShadow";
    		case BACK_RIGHT_LONG_PERSPECTIVE_SHADOW: return "BackRightLongPerspectiveShadow";
    		case TOP_LEFT_DOUBLE_DROP_SHADOW: return "TopLeftDoubleDropShadow";
    		case BOTTOM_RIGHT_SMALL_DROP_SHADOW: return "BottomRightSmallDropShadow";
    		case FRONT_LEFT_LONG_PERSPECTIVE_SHADOW: return "FrontLeftLongPerspectiveShadow";
    		case FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW: return "FrontRightLongPerspectiveShadow";
    		case OUTER_BOX_SHADOW_3_D: return "OuterBoxShadow3D";
    		case INNER_BOX_SHADOW_3_D: return "InnerBoxShadow3D";
    		case BACK_CENTER_PERSPECTIVE_SHADOW: return "BackCenterPerspectiveShadow";
    		case TOP_RIGHT_DROP_SHADOW: return "TopRightDropShadow";
    		case FRONT_BOTTOM_SHADOW: return "FrontBottomShadow";
    		case BACK_LEFT_PERSPECTIVE_SHADOW: return "BackLeftPerspectiveShadow";
    		case BACK_RIGHT_PERSPECTIVE_SHADOW: return "BackRightPerspectiveShadow";
    		case BOTTOM_LEFT_DROP_SHADOW: return "BottomLeftDropShadow";
    		case BOTTOM_RIGHT_DROP_SHADOW: return "BottomRightDropShadow";
    		case FRONT_LEFT_PERSPECTIVE_SHADOW: return "FrontLeftPerspectiveShadow";
    		case FRONT_RIGHT_PERSPECTIVE_SHADOW: return "FrontRightPerspectiveShadow";
    		case TOP_LEFT_SMALL_DROP_SHADOW: return "TopLeftSmallDropShadow";
    		default: return "Unknown DmlPresetShadow value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPresetShadow name.
    /// </summary>
    public static /*DmlPresetShadow*/int fromName(String dmlPresetShadowName)
    {
    	if ("TOP_LEFT_DROP_SHADOW".equals(dmlPresetShadowName)) return TOP_LEFT_DROP_SHADOW;
    	if ("TOP_LEFT_LARGE_DROP_SHADOW".equals(dmlPresetShadowName)) return TOP_LEFT_LARGE_DROP_SHADOW;
    	if ("BACK_LEFT_LONG_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return BACK_LEFT_LONG_PERSPECTIVE_SHADOW;
    	if ("BACK_RIGHT_LONG_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return BACK_RIGHT_LONG_PERSPECTIVE_SHADOW;
    	if ("TOP_LEFT_DOUBLE_DROP_SHADOW".equals(dmlPresetShadowName)) return TOP_LEFT_DOUBLE_DROP_SHADOW;
    	if ("BOTTOM_RIGHT_SMALL_DROP_SHADOW".equals(dmlPresetShadowName)) return BOTTOM_RIGHT_SMALL_DROP_SHADOW;
    	if ("FRONT_LEFT_LONG_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return FRONT_LEFT_LONG_PERSPECTIVE_SHADOW;
    	if ("FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW;
    	if ("OUTER_BOX_SHADOW_3_D".equals(dmlPresetShadowName)) return OUTER_BOX_SHADOW_3_D;
    	if ("INNER_BOX_SHADOW_3_D".equals(dmlPresetShadowName)) return INNER_BOX_SHADOW_3_D;
    	if ("BACK_CENTER_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return BACK_CENTER_PERSPECTIVE_SHADOW;
    	if ("TOP_RIGHT_DROP_SHADOW".equals(dmlPresetShadowName)) return TOP_RIGHT_DROP_SHADOW;
    	if ("FRONT_BOTTOM_SHADOW".equals(dmlPresetShadowName)) return FRONT_BOTTOM_SHADOW;
    	if ("BACK_LEFT_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return BACK_LEFT_PERSPECTIVE_SHADOW;
    	if ("BACK_RIGHT_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return BACK_RIGHT_PERSPECTIVE_SHADOW;
    	if ("BOTTOM_LEFT_DROP_SHADOW".equals(dmlPresetShadowName)) return BOTTOM_LEFT_DROP_SHADOW;
    	if ("BOTTOM_RIGHT_DROP_SHADOW".equals(dmlPresetShadowName)) return BOTTOM_RIGHT_DROP_SHADOW;
    	if ("FRONT_LEFT_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return FRONT_LEFT_PERSPECTIVE_SHADOW;
    	if ("FRONT_RIGHT_PERSPECTIVE_SHADOW".equals(dmlPresetShadowName)) return FRONT_RIGHT_PERSPECTIVE_SHADOW;
    	if ("TOP_LEFT_SMALL_DROP_SHADOW".equals(dmlPresetShadowName)) return TOP_LEFT_SMALL_DROP_SHADOW;
    	throw new IllegalArgumentException("Unknown DmlPresetShadow name.");
    }
    
    /// <summary>
    /// Returns array of DmlPresetShadow values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP_LEFT_DROP_SHADOW,
    		TOP_LEFT_LARGE_DROP_SHADOW,
    		BACK_LEFT_LONG_PERSPECTIVE_SHADOW,
    		BACK_RIGHT_LONG_PERSPECTIVE_SHADOW,
    		TOP_LEFT_DOUBLE_DROP_SHADOW,
    		BOTTOM_RIGHT_SMALL_DROP_SHADOW,
    		FRONT_LEFT_LONG_PERSPECTIVE_SHADOW,
    		FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW,
    		OUTER_BOX_SHADOW_3_D,
    		INNER_BOX_SHADOW_3_D,
    		BACK_CENTER_PERSPECTIVE_SHADOW,
    		TOP_RIGHT_DROP_SHADOW,
    		FRONT_BOTTOM_SHADOW,
    		BACK_LEFT_PERSPECTIVE_SHADOW,
    		BACK_RIGHT_PERSPECTIVE_SHADOW,
    		BOTTOM_LEFT_DROP_SHADOW,
    		BOTTOM_RIGHT_DROP_SHADOW,
    		FRONT_LEFT_PERSPECTIVE_SHADOW,
    		FRONT_RIGHT_PERSPECTIVE_SHADOW,
    		TOP_LEFT_SMALL_DROP_SHADOW,
    	};
    }

    public static final int length = 20;
}

