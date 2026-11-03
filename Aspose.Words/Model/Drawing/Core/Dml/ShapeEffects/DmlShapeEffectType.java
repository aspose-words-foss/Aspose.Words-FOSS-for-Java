// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*enum*/ final class DmlShapeEffectType
{
    private DmlShapeEffectType(){}
    
    public static final int FILL_OVERLAY = 0;
    public static final int INNER_SHADOW = 1;
    public static final int PRESET_SHADOW = 2;
    public static final int SOFT_EDGES = 3;
    public static final int BLUR = 4;
    public static final int GLOW = 5;
    public static final int OUTER_SHADOW = 6;
    public static final int REFLECTION = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlShapeEffectType value.
    /// </summary>
    public static String getName(/*DmlShapeEffectType*/int dmlShapeEffectType)
    {
    	switch (dmlShapeEffectType)
    	{
    		case FILL_OVERLAY: return "FILL_OVERLAY";
    		case INNER_SHADOW: return "INNER_SHADOW";
    		case PRESET_SHADOW: return "PRESET_SHADOW";
    		case SOFT_EDGES: return "SOFT_EDGES";
    		case BLUR: return "BLUR";
    		case GLOW: return "GLOW";
    		case OUTER_SHADOW: return "OUTER_SHADOW";
    		case REFLECTION: return "REFLECTION";
    		default: return "Unknown DmlShapeEffectType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlShapeEffectType value.
    /// </summary>
    public static String toString(/*DmlShapeEffectType*/int dmlShapeEffectType)
    {
    	switch (dmlShapeEffectType)
    	{
    		case FILL_OVERLAY: return "FillOverlay";
    		case INNER_SHADOW: return "InnerShadow";
    		case PRESET_SHADOW: return "PresetShadow";
    		case SOFT_EDGES: return "SoftEdges";
    		case BLUR: return "Blur";
    		case GLOW: return "Glow";
    		case OUTER_SHADOW: return "OuterShadow";
    		case REFLECTION: return "Reflection";
    		default: return "Unknown DmlShapeEffectType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlShapeEffectType name.
    /// </summary>
    public static /*DmlShapeEffectType*/int fromName(String dmlShapeEffectTypeName)
    {
    	if ("FILL_OVERLAY".equals(dmlShapeEffectTypeName)) return FILL_OVERLAY;
    	if ("INNER_SHADOW".equals(dmlShapeEffectTypeName)) return INNER_SHADOW;
    	if ("PRESET_SHADOW".equals(dmlShapeEffectTypeName)) return PRESET_SHADOW;
    	if ("SOFT_EDGES".equals(dmlShapeEffectTypeName)) return SOFT_EDGES;
    	if ("BLUR".equals(dmlShapeEffectTypeName)) return BLUR;
    	if ("GLOW".equals(dmlShapeEffectTypeName)) return GLOW;
    	if ("OUTER_SHADOW".equals(dmlShapeEffectTypeName)) return OUTER_SHADOW;
    	if ("REFLECTION".equals(dmlShapeEffectTypeName)) return REFLECTION;
    	throw new IllegalArgumentException("Unknown DmlShapeEffectType name.");
    }
    
    /// <summary>
    /// Returns array of DmlShapeEffectType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FILL_OVERLAY,
    		INNER_SHADOW,
    		PRESET_SHADOW,
    		SOFT_EDGES,
    		BLUR,
    		GLOW,
    		OUTER_SHADOW,
    		REFLECTION,
    	};
    }

    public static final int length = 8;
}

