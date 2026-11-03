// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*enum*/ final class ImageBinarizationMethodCore
{
    private ImageBinarizationMethodCore(){}
    
    /// <summary>
    /// Specifies threshold method.
    /// </summary>
    public static final int THRESHOLD = 0;
    /// <summary>
    /// Specifies dithering using Floyd-Steinberg error diffusion method.
    /// </summary>
    public static final int FLOYD_STEINBERG_DITHERING = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImageBinarizationMethodCore value.
    /// </summary>
    public static String getName(/*ImageBinarizationMethodCore*/int imageBinarizationMethodCore)
    {
    	switch (imageBinarizationMethodCore)
    	{
    		case THRESHOLD: return "THRESHOLD";
    		case FLOYD_STEINBERG_DITHERING: return "FLOYD_STEINBERG_DITHERING";
    		default: return "Unknown ImageBinarizationMethodCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImageBinarizationMethodCore value.
    /// </summary>
    public static String toString(/*ImageBinarizationMethodCore*/int imageBinarizationMethodCore)
    {
    	switch (imageBinarizationMethodCore)
    	{
    		case THRESHOLD: return "Threshold";
    		case FLOYD_STEINBERG_DITHERING: return "FloydSteinbergDithering";
    		default: return "Unknown ImageBinarizationMethodCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImageBinarizationMethodCore name.
    /// </summary>
    public static /*ImageBinarizationMethodCore*/int fromName(String imageBinarizationMethodCoreName)
    {
    	if ("THRESHOLD".equals(imageBinarizationMethodCoreName)) return THRESHOLD;
    	if ("FLOYD_STEINBERG_DITHERING".equals(imageBinarizationMethodCoreName)) return FLOYD_STEINBERG_DITHERING;
    	throw new IllegalArgumentException("Unknown ImageBinarizationMethodCore name.");
    }
    
    /// <summary>
    /// Returns array of ImageBinarizationMethodCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		THRESHOLD,
    		FLOYD_STEINBERG_DITHERING,
    	};
    }

    public static final int length = 2;
}

