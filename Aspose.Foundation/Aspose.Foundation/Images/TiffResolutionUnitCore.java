// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies what unit of measurement is used for XResolution and YResolution.
/// </summary>
public /*enum*/ final class TiffResolutionUnitCore
{
    private TiffResolutionUnitCore(){}
    
    /// <summary>
    /// No absolute unit of measurement. Used for images that may have a non-square aspect ratio, but no meaningful absolute dimensions.
    /// </summary>
    public static final int NONE = 1;
    /// <summary>
    /// Inch.
    /// </summary>
    public static final int INCH = 2;
    /// <summary>
    /// Centimeter.
    /// </summary>
    public static final int CENTIMETER = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffResolutionUnitCore value.
    /// </summary>
    public static String getName(/*TiffResolutionUnitCore*/int tiffResolutionUnitCore)
    {
    	switch (tiffResolutionUnitCore)
    	{
    		case NONE: return "NONE";
    		case INCH: return "INCH";
    		case CENTIMETER: return "CENTIMETER";
    		default: return "Unknown TiffResolutionUnitCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffResolutionUnitCore value.
    /// </summary>
    public static String toString(/*TiffResolutionUnitCore*/int tiffResolutionUnitCore)
    {
    	switch (tiffResolutionUnitCore)
    	{
    		case NONE: return "None";
    		case INCH: return "Inch";
    		case CENTIMETER: return "Centimeter";
    		default: return "Unknown TiffResolutionUnitCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffResolutionUnitCore name.
    /// </summary>
    public static /*TiffResolutionUnitCore*/int fromName(String tiffResolutionUnitCoreName)
    {
    	if ("NONE".equals(tiffResolutionUnitCoreName)) return NONE;
    	if ("INCH".equals(tiffResolutionUnitCoreName)) return INCH;
    	if ("CENTIMETER".equals(tiffResolutionUnitCoreName)) return CENTIMETER;
    	throw new IllegalArgumentException("Unknown TiffResolutionUnitCore name.");
    }
    
    /// <summary>
    /// Returns array of TiffResolutionUnitCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		INCH,
    		CENTIMETER,
    	};
    }

    public static final int length = 3;
}
