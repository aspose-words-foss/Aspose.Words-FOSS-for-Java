// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2009 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the color space of the image.
/// </summary>
public /*enum*/ final class TiffPhotometricInterpretationCore
{
    private TiffPhotometricInterpretationCore(){}
    
    /// <summary>
    /// For bilevel and grayscale images: 0 is imaged as white.
    /// </summary>
    public static final int WHITE_IS_ZERO = 0;
    /// <summary>
    /// For bilevel and grayscale images: 0 is imaged as black.
    /// </summary>
    public static final int BLACK_IS_ZERO = 1;
    /// <summary>
    /// Defines a Red-Green-Blue color map for RGB images.
    /// </summary>
    public static final int RGB = 2;
    /// <summary>
    /// Defines a Red-Green-Blue color map for palette color images.
    /// </summary>
    public static final int RGB_PALETTE = 3;
    /// <summary>
    /// Transparency Mask.
    /// </summary>
    public static final int TRANSPARENCY_MASK = 4;
    /// <summary>
    /// Separated - usually CMYK.
    /// </summary>
    public static final int CMYK = 5;
    /// <summary>
    /// Indicates that the image data is in the YCbCr color space.
    /// </summary>
    public static final int YCBCR = 6;
    /// <summary>
    /// Indicates that the image data is in the Cielab color space.
    /// </summary>
    public static final int CIELAB = 8;
    /// <summary>
    /// This value cannot be use in TIFF, it is used to specify default value while reading. 
    /// This is used to indicate if this field is specified.
    /// </summary>
    public static final int UNSPECIFIED = Short.MAX_VALUE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffPhotometricInterpretationCore value.
    /// </summary>
    public static String getName(/*TiffPhotometricInterpretationCore*/int tiffPhotometricInterpretationCore)
    {
    	switch (tiffPhotometricInterpretationCore)
    	{
    		case WHITE_IS_ZERO: return "WHITE_IS_ZERO";
    		case BLACK_IS_ZERO: return "BLACK_IS_ZERO";
    		case RGB: return "RGB";
    		case RGB_PALETTE: return "RGB_PALETTE";
    		case TRANSPARENCY_MASK: return "TRANSPARENCY_MASK";
    		case CMYK: return "CMYK";
    		case YCBCR: return "YCBCR";
    		case CIELAB: return "CIELAB";
    		case UNSPECIFIED: return "UNSPECIFIED";
    		default: return "Unknown TiffPhotometricInterpretationCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffPhotometricInterpretationCore value.
    /// </summary>
    public static String toString(/*TiffPhotometricInterpretationCore*/int tiffPhotometricInterpretationCore)
    {
    	switch (tiffPhotometricInterpretationCore)
    	{
    		case WHITE_IS_ZERO: return "WhiteIsZero";
    		case BLACK_IS_ZERO: return "BlackIsZero";
    		case RGB: return "Rgb";
    		case RGB_PALETTE: return "RgbPalette";
    		case TRANSPARENCY_MASK: return "TransparencyMask";
    		case CMYK: return "Cmyk";
    		case YCBCR: return "Ycbcr";
    		case CIELAB: return "Cielab";
    		case UNSPECIFIED: return "Unspecified";
    		default: return "Unknown TiffPhotometricInterpretationCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffPhotometricInterpretationCore name.
    /// </summary>
    public static /*TiffPhotometricInterpretationCore*/int fromName(String tiffPhotometricInterpretationCoreName)
    {
    	if ("WHITE_IS_ZERO".equals(tiffPhotometricInterpretationCoreName)) return WHITE_IS_ZERO;
    	if ("BLACK_IS_ZERO".equals(tiffPhotometricInterpretationCoreName)) return BLACK_IS_ZERO;
    	if ("RGB".equals(tiffPhotometricInterpretationCoreName)) return RGB;
    	if ("RGB_PALETTE".equals(tiffPhotometricInterpretationCoreName)) return RGB_PALETTE;
    	if ("TRANSPARENCY_MASK".equals(tiffPhotometricInterpretationCoreName)) return TRANSPARENCY_MASK;
    	if ("CMYK".equals(tiffPhotometricInterpretationCoreName)) return CMYK;
    	if ("YCBCR".equals(tiffPhotometricInterpretationCoreName)) return YCBCR;
    	if ("CIELAB".equals(tiffPhotometricInterpretationCoreName)) return CIELAB;
    	if ("UNSPECIFIED".equals(tiffPhotometricInterpretationCoreName)) return UNSPECIFIED;
    	throw new IllegalArgumentException("Unknown TiffPhotometricInterpretationCore name.");
    }
    
    /// <summary>
    /// Returns array of TiffPhotometricInterpretationCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		WHITE_IS_ZERO,
    		BLACK_IS_ZERO,
    		RGB,
    		RGB_PALETTE,
    		TRANSPARENCY_MASK,
    		CMYK,
    		YCBCR,
    		CIELAB,
    		UNSPECIFIED,
    	};
    }

    public static final int length = 9;
}
