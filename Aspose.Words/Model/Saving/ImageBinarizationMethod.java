// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2013 by Sergey Merkulov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the method used to binarize image.
/// </summary>
public /*enum*/ final class ImageBinarizationMethod
{
    private ImageBinarizationMethod(){}
    
    /// <summary>
    /// Specifies threshold method.
    /// </summary>
    public static final int THRESHOLD = 0;
    /// <summary>
    /// Specifies dithering using Floyd-Steinberg error diffusion method.
    /// </summary>
    public static final int FLOYD_STEINBERG_DITHERING = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImageBinarizationMethod value.
    /// </summary>
    public static String getName(/*ImageBinarizationMethod*/int imageBinarizationMethod)
    {
    	switch (imageBinarizationMethod)
    	{
    		case THRESHOLD: return "THRESHOLD";
    		case FLOYD_STEINBERG_DITHERING: return "FLOYD_STEINBERG_DITHERING";
    		default: return "Unknown ImageBinarizationMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImageBinarizationMethod value.
    /// </summary>
    public static String toString(/*ImageBinarizationMethod*/int imageBinarizationMethod)
    {
    	switch (imageBinarizationMethod)
    	{
    		case THRESHOLD: return "Threshold";
    		case FLOYD_STEINBERG_DITHERING: return "FloydSteinbergDithering";
    		default: return "Unknown ImageBinarizationMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImageBinarizationMethod name.
    /// </summary>
    public static /*ImageBinarizationMethod*/int fromName(String imageBinarizationMethodName)
    {
    	if ("THRESHOLD".equals(imageBinarizationMethodName)) return THRESHOLD;
    	if ("FLOYD_STEINBERG_DITHERING".equals(imageBinarizationMethodName)) return FLOYD_STEINBERG_DITHERING;
    	throw new IllegalArgumentException("Unknown ImageBinarizationMethod name.");
    }
    
    /// <summary>
    /// Returns array of ImageBinarizationMethod values.
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

