// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how the components of each pixel are stored.
/// </summary>
public /*enum*/ final class TiffPlanarConfigurationCore
{
    private TiffPlanarConfigurationCore(){}
    
    /// <summary>
    /// Chunky format. The component values for each pixel are stored contiguously.
    /// The order of the components within the pixel is specified by PhotometricInterpretation.
    /// </summary>
    public static final int CHUNKY = 1;
    /// <summary>
    /// Planar format. The components are stored in separate "component planes."
    /// </summary>
    public static final int PLANAR = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffPlanarConfigurationCore value.
    /// </summary>
    public static String getName(/*TiffPlanarConfigurationCore*/int tiffPlanarConfigurationCore)
    {
    	switch (tiffPlanarConfigurationCore)
    	{
    		case CHUNKY: return "CHUNKY";
    		case PLANAR: return "PLANAR";
    		default: return "Unknown TiffPlanarConfigurationCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffPlanarConfigurationCore value.
    /// </summary>
    public static String toString(/*TiffPlanarConfigurationCore*/int tiffPlanarConfigurationCore)
    {
    	switch (tiffPlanarConfigurationCore)
    	{
    		case CHUNKY: return "Chunky";
    		case PLANAR: return "Planar";
    		default: return "Unknown TiffPlanarConfigurationCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffPlanarConfigurationCore name.
    /// </summary>
    public static /*TiffPlanarConfigurationCore*/int fromName(String tiffPlanarConfigurationCoreName)
    {
    	if ("CHUNKY".equals(tiffPlanarConfigurationCoreName)) return CHUNKY;
    	if ("PLANAR".equals(tiffPlanarConfigurationCoreName)) return PLANAR;
    	throw new IllegalArgumentException("Unknown TiffPlanarConfigurationCore name.");
    }
    
    /// <summary>
    /// Returns array of TiffPlanarConfigurationCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CHUNKY,
    		PLANAR,
    	};
    }

    public static final int length = 2;
}
