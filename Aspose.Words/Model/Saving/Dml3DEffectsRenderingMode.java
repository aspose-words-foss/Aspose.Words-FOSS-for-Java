// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2019 by Dmitry Khudobin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how 3D shape effects are rendered.
/// </summary>
public /*enum*/ final class Dml3DEffectsRenderingMode
{
    private Dml3DEffectsRenderingMode(){}
    
    /// <summary>
    /// A lightweight and stable rendering, based on the internal engine, 
    /// but advanced effects such as lighting, materials and other additional effects 
    /// are not displayed when using this mode.
    /// Please see documentation for details.
    /// </summary>
    public static final int BASIC = 0;

    /// <summary>
    /// Rendering of an extended list of special effects including advanced 3D effects 
    /// such as bevels, lighting and materials.
    /// </summary>
    /// <remarks>
    /// The current implementation uses OpenGL.
    /// Please make sure that OpenGL library version 1.1 or higher is installed on your system before use.
    /// This mode is still under development, and some things may not be supported, so it's recommended to use 
    /// the <see cref="Basic"/> mode if the rendering result is not acceptable.
    /// Please see documentation for details.
    /// </remarks>
    public static final int ADVANCED = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Dml3DEffectsRenderingMode value.
    /// </summary>
    public static String getName(/*Dml3DEffectsRenderingMode*/int dml3DEffectsRenderingMode)
    {
    	switch (dml3DEffectsRenderingMode)
    	{
    		case BASIC: return "BASIC";
    		case ADVANCED: return "ADVANCED";
    		default: return "Unknown Dml3DEffectsRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Dml3DEffectsRenderingMode value.
    /// </summary>
    public static String toString(/*Dml3DEffectsRenderingMode*/int dml3DEffectsRenderingMode)
    {
    	switch (dml3DEffectsRenderingMode)
    	{
    		case BASIC: return "Basic";
    		case ADVANCED: return "Advanced";
    		default: return "Unknown Dml3DEffectsRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Dml3DEffectsRenderingMode name.
    /// </summary>
    public static /*Dml3DEffectsRenderingMode*/int fromName(String dml3DEffectsRenderingModeName)
    {
    	if ("BASIC".equals(dml3DEffectsRenderingModeName)) return BASIC;
    	if ("ADVANCED".equals(dml3DEffectsRenderingModeName)) return ADVANCED;
    	throw new IllegalArgumentException("Unknown Dml3DEffectsRenderingMode name.");
    }
    
    /// <summary>
    /// Returns array of Dml3DEffectsRenderingMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BASIC,
    		ADVANCED,
    	};
    }

    public static final int length = 2;
}

