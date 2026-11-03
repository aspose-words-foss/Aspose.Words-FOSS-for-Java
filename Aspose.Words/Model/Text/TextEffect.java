// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Animation effect for text runs.
/// </summary>
public /*enum*/ final class TextEffect
{
    private TextEffect(){}
    
    /// <summary>
    ///
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    ///
    /// </summary>
    public static final int LAS_VEGAS_LIGHTS = 1;
    /// <summary>
    ///
    /// </summary>
    public static final int BLINKING_BACKGROUND = 2;
    /// <summary>
    ///
    /// </summary>
    public static final int SPARKLE_TEXT = 3;
    /// <summary>
    ///
    /// </summary>
    public static final int MARCHING_BLACK_ANTS = 4;
    /// <summary>
    ///
    /// </summary>
    public static final int MARCHING_RED_ANTS = 5;
    /// <summary>
    ///
    /// </summary>
    public static final int SHIMMER = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextEffect value.
    /// </summary>
    public static String getName(/*TextEffect*/int textEffect)
    {
    	switch (textEffect)
    	{
    		case NONE: return "NONE";
    		case LAS_VEGAS_LIGHTS: return "LAS_VEGAS_LIGHTS";
    		case BLINKING_BACKGROUND: return "BLINKING_BACKGROUND";
    		case SPARKLE_TEXT: return "SPARKLE_TEXT";
    		case MARCHING_BLACK_ANTS: return "MARCHING_BLACK_ANTS";
    		case MARCHING_RED_ANTS: return "MARCHING_RED_ANTS";
    		case SHIMMER: return "SHIMMER";
    		default: return "Unknown TextEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextEffect value.
    /// </summary>
    public static String toString(/*TextEffect*/int textEffect)
    {
    	switch (textEffect)
    	{
    		case NONE: return "None";
    		case LAS_VEGAS_LIGHTS: return "LasVegasLights";
    		case BLINKING_BACKGROUND: return "BlinkingBackground";
    		case SPARKLE_TEXT: return "SparkleText";
    		case MARCHING_BLACK_ANTS: return "MarchingBlackAnts";
    		case MARCHING_RED_ANTS: return "MarchingRedAnts";
    		case SHIMMER: return "Shimmer";
    		default: return "Unknown TextEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextEffect name.
    /// </summary>
    public static /*TextEffect*/int fromName(String textEffectName)
    {
    	if ("NONE".equals(textEffectName)) return NONE;
    	if ("LAS_VEGAS_LIGHTS".equals(textEffectName)) return LAS_VEGAS_LIGHTS;
    	if ("BLINKING_BACKGROUND".equals(textEffectName)) return BLINKING_BACKGROUND;
    	if ("SPARKLE_TEXT".equals(textEffectName)) return SPARKLE_TEXT;
    	if ("MARCHING_BLACK_ANTS".equals(textEffectName)) return MARCHING_BLACK_ANTS;
    	if ("MARCHING_RED_ANTS".equals(textEffectName)) return MARCHING_RED_ANTS;
    	if ("SHIMMER".equals(textEffectName)) return SHIMMER;
    	throw new IllegalArgumentException("Unknown TextEffect name.");
    }
    
    /// <summary>
    /// Returns array of TextEffect values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		LAS_VEGAS_LIGHTS,
    		BLINKING_BACKGROUND,
    		SPARKLE_TEXT,
    		MARCHING_BLACK_ANTS,
    		MARCHING_RED_ANTS,
    		SHIMMER,
    	};
    }

    public static final int length = 7;
}

