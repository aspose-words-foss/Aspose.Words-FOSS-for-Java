// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/26/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how DrawingML effects are rendered to fixed page formats.
/// </summary>
public /*enum*/ final class DmlEffectsRenderingMode
{
    private DmlEffectsRenderingMode(){}
    
    /// <summary>
    /// Rendering of DrawingML effects are simplified.
    /// </summary>
    public static final int SIMPLIFIED = 0;
    /// <summary>
    /// No DrawingML effects are rendered.
    /// </summary>
    public static final int NONE = 1;
    /// <summary>
    /// DrawingML effects are rendered in fine mode which involves advanced processing.
    /// In this mode rendering of effects gives better results but at a higher performance cost than <see cref="Simplified"/> mode.
    /// </summary>
    public static final int FINE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlEffectsRenderingMode value.
    /// </summary>
    public static String getName(/*DmlEffectsRenderingMode*/int dmlEffectsRenderingMode)
    {
    	switch (dmlEffectsRenderingMode)
    	{
    		case SIMPLIFIED: return "SIMPLIFIED";
    		case NONE: return "NONE";
    		case FINE: return "FINE";
    		default: return "Unknown DmlEffectsRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlEffectsRenderingMode value.
    /// </summary>
    public static String toString(/*DmlEffectsRenderingMode*/int dmlEffectsRenderingMode)
    {
    	switch (dmlEffectsRenderingMode)
    	{
    		case SIMPLIFIED: return "Simplified";
    		case NONE: return "None";
    		case FINE: return "Fine";
    		default: return "Unknown DmlEffectsRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlEffectsRenderingMode name.
    /// </summary>
    public static /*DmlEffectsRenderingMode*/int fromName(String dmlEffectsRenderingModeName)
    {
    	if ("SIMPLIFIED".equals(dmlEffectsRenderingModeName)) return SIMPLIFIED;
    	if ("NONE".equals(dmlEffectsRenderingModeName)) return NONE;
    	if ("FINE".equals(dmlEffectsRenderingModeName)) return FINE;
    	throw new IllegalArgumentException("Unknown DmlEffectsRenderingMode name.");
    }
    
    /// <summary>
    /// Returns array of DmlEffectsRenderingMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SIMPLIFIED,
    		NONE,
    		FINE,
    	};
    }

    public static final int length = 3;
}

