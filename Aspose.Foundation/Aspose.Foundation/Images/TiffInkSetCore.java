// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The set of inks used in a separated (PhotometricInterpretation=5 (Cmyk)) image.
/// </summary>
public /*enum*/ final class TiffInkSetCore
{
    private TiffInkSetCore(){}
    
    /// <summary>
    /// The order of the components is cyan, magenta, yellow, black (default).
    /// </summary>
    public static final int CMYK = 1;
    /// <summary>
    /// Not Cmyk.
    /// </summary>
    public static final int NOT_CMYK = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffInkSetCore value.
    /// </summary>
    public static String getName(/*TiffInkSetCore*/int tiffInkSetCore)
    {
    	switch (tiffInkSetCore)
    	{
    		case CMYK: return "CMYK";
    		case NOT_CMYK: return "NOT_CMYK";
    		default: return "Unknown TiffInkSetCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffInkSetCore value.
    /// </summary>
    public static String toString(/*TiffInkSetCore*/int tiffInkSetCore)
    {
    	switch (tiffInkSetCore)
    	{
    		case CMYK: return "Cmyk";
    		case NOT_CMYK: return "NotCmyk";
    		default: return "Unknown TiffInkSetCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffInkSetCore name.
    /// </summary>
    public static /*TiffInkSetCore*/int fromName(String tiffInkSetCoreName)
    {
    	if ("CMYK".equals(tiffInkSetCoreName)) return CMYK;
    	if ("NOT_CMYK".equals(tiffInkSetCoreName)) return NOT_CMYK;
    	throw new IllegalArgumentException("Unknown TiffInkSetCore name.");
    }
    
    /// <summary>
    /// Returns array of TiffInkSetCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CMYK,
    		NOT_CMYK,
    	};
    }

    public static final int length = 2;
}
