// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.11 ST_BlendMode (Blend Mode)
/// This simple type describes how to render effects one on top of another.
/// </summary>
/*enum*/ final class DmlEffectBlendMode
{
    private DmlEffectBlendMode(){}
    
    public static final int DARKEN = 0;
    public static final int LIGHTEN = 1;
    public static final int MULTIPLY = 2;
    public static final int OVERLAY = 3;
    public static final int SCREEN = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlEffectBlendMode value.
    /// </summary>
    public static String getName(/*DmlEffectBlendMode*/int dmlEffectBlendMode)
    {
    	switch (dmlEffectBlendMode)
    	{
    		case DARKEN: return "DARKEN";
    		case LIGHTEN: return "LIGHTEN";
    		case MULTIPLY: return "MULTIPLY";
    		case OVERLAY: return "OVERLAY";
    		case SCREEN: return "SCREEN";
    		default: return "Unknown DmlEffectBlendMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlEffectBlendMode value.
    /// </summary>
    public static String toString(/*DmlEffectBlendMode*/int dmlEffectBlendMode)
    {
    	switch (dmlEffectBlendMode)
    	{
    		case DARKEN: return "Darken";
    		case LIGHTEN: return "Lighten";
    		case MULTIPLY: return "Multiply";
    		case OVERLAY: return "Overlay";
    		case SCREEN: return "Screen";
    		default: return "Unknown DmlEffectBlendMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlEffectBlendMode name.
    /// </summary>
    public static /*DmlEffectBlendMode*/int fromName(String dmlEffectBlendModeName)
    {
    	if ("DARKEN".equals(dmlEffectBlendModeName)) return DARKEN;
    	if ("LIGHTEN".equals(dmlEffectBlendModeName)) return LIGHTEN;
    	if ("MULTIPLY".equals(dmlEffectBlendModeName)) return MULTIPLY;
    	if ("OVERLAY".equals(dmlEffectBlendModeName)) return OVERLAY;
    	if ("SCREEN".equals(dmlEffectBlendModeName)) return SCREEN;
    	throw new IllegalArgumentException("Unknown DmlEffectBlendMode name.");
    }
    
    /// <summary>
    /// Returns array of DmlEffectBlendMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DARKEN,
    		LIGHTEN,
    		MULTIPLY,
    		OVERLAY,
    		SCREEN,
    	};
    }

    public static final int length = 5;
}

