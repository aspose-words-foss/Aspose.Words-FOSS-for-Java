// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies rendering mode for 3D shapes.
/// </summary>
/*enum*/ final class ThreeDRenderMode
{
    private ThreeDRenderMode(){}
    
    /// <summary>
    /// Generate a full rendering.
    /// </summary>
    public static final int FULL_RENDER = 0;      
    /// <summary>
    /// Generate a wireframe.
    /// </summary>
    public static final int WIREFRAME = 1;       
    /// <summary>
    /// Generate a bounding cube.
    /// </summary>
    public static final int BOUNDING_CUBE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ThreeDRenderMode value.
    /// </summary>
    public static String getName(/*ThreeDRenderMode*/int threeDRenderMode)
    {
    	switch (threeDRenderMode)
    	{
    		case FULL_RENDER: return "FULL_RENDER";
    		case WIREFRAME: return "WIREFRAME";
    		case BOUNDING_CUBE: return "BOUNDING_CUBE";
    		default: return "Unknown ThreeDRenderMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ThreeDRenderMode value.
    /// </summary>
    public static String toString(/*ThreeDRenderMode*/int threeDRenderMode)
    {
    	switch (threeDRenderMode)
    	{
    		case FULL_RENDER: return "FullRender";
    		case WIREFRAME: return "Wireframe";
    		case BOUNDING_CUBE: return "BoundingCube";
    		default: return "Unknown ThreeDRenderMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ThreeDRenderMode name.
    /// </summary>
    public static /*ThreeDRenderMode*/int fromName(String threeDRenderModeName)
    {
    	if ("FULL_RENDER".equals(threeDRenderModeName)) return FULL_RENDER;
    	if ("WIREFRAME".equals(threeDRenderModeName)) return WIREFRAME;
    	if ("BOUNDING_CUBE".equals(threeDRenderModeName)) return BOUNDING_CUBE;
    	throw new IllegalArgumentException("Unknown ThreeDRenderMode name.");
    }
    
    /// <summary>
    /// Returns array of ThreeDRenderMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FULL_RENDER,
    		WIREFRAME,
    		BOUNDING_CUBE,
    	};
    }

    public static final int length = 3;
}

