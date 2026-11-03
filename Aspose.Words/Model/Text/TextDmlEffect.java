// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2019 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Dml text effect for text runs.
/// </summary>
public /*enum*/ final class TextDmlEffect
{
    private TextDmlEffect(){}
    
    /// <summary>
    /// Glow effect, in which a color blurred outline is added outside the edges of the object.
    /// </summary>
    public static final int GLOW = 0;

    /// <summary>
    /// Fill overlay effect.
    /// </summary>
    public static final int FILL = 1;

    /// <summary>
    /// Shadow effect.
    /// </summary>
    public static final int SHADOW = 2;

    /// <summary>
    /// Outline effect.
    /// </summary>
    public static final int OUTLINE = 3;

    /// <summary>
    /// 3D effect.
    /// </summary>
    public static final int EFFECT_3_D = 4;

    /// <summary>
    /// Reflection effect.
    /// </summary>
    public static final int REFLECTION = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextDmlEffect value.
    /// </summary>
    public static String getName(/*TextDmlEffect*/int textDmlEffect)
    {
    	switch (textDmlEffect)
    	{
    		case GLOW: return "GLOW";
    		case FILL: return "FILL";
    		case SHADOW: return "SHADOW";
    		case OUTLINE: return "OUTLINE";
    		case EFFECT_3_D: return "EFFECT_3_D";
    		case REFLECTION: return "REFLECTION";
    		default: return "Unknown TextDmlEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextDmlEffect value.
    /// </summary>
    public static String toString(/*TextDmlEffect*/int textDmlEffect)
    {
    	switch (textDmlEffect)
    	{
    		case GLOW: return "Glow";
    		case FILL: return "Fill";
    		case SHADOW: return "Shadow";
    		case OUTLINE: return "Outline";
    		case EFFECT_3_D: return "Effect3D";
    		case REFLECTION: return "Reflection";
    		default: return "Unknown TextDmlEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextDmlEffect name.
    /// </summary>
    public static /*TextDmlEffect*/int fromName(String textDmlEffectName)
    {
    	if ("GLOW".equals(textDmlEffectName)) return GLOW;
    	if ("FILL".equals(textDmlEffectName)) return FILL;
    	if ("SHADOW".equals(textDmlEffectName)) return SHADOW;
    	if ("OUTLINE".equals(textDmlEffectName)) return OUTLINE;
    	if ("EFFECT_3_D".equals(textDmlEffectName)) return EFFECT_3_D;
    	if ("REFLECTION".equals(textDmlEffectName)) return REFLECTION;
    	throw new IllegalArgumentException("Unknown TextDmlEffect name.");
    }
    
    /// <summary>
    /// Returns array of TextDmlEffect values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		GLOW,
    		FILL,
    		SHADOW,
    		OUTLINE,
    		EFFECT_3_D,
    		REFLECTION,
    	};
    }

    public static final int length = 6;
}

