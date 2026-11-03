// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The type of a (length) measurement for fill dimensions type.
/// </summary>
/*enum*/ final class FillDimensionType
{
    private FillDimensionType(){}
    
    /// <summary>
    /// Default size, ignore the values.
    /// </summary>
    public static final int DEFAULT = 0;  
    /// <summary>
    /// Values are in EMUs.
    /// </summary>
    public static final int EMU = 1;  
    /// <summary>
    /// Values are in pixels.
    /// </summary>
    public static final int PIXEL = 2;  
    /// <summary>
    /// Values are 16.16 fractions of shape size.
    /// </summary>
    public static final int SHAPE_FRACTION = 3;  
    /// <summary>
    /// Aspect ratio is fixed.
    /// </summary>
    public static final int FIXED_ASPECT  = 4;  
    /// <summary>
    /// EMUs, fixed aspect ratio.
    /// </summary>
    public static final int EMU_FIXED_RATIO= 5;  
    /// <summary>
    /// Pixels, fixed aspect ratio.
    /// </summary>
    public static final int PIXEL_FIXED_RATIO = 6;  
    /// <summary>
    /// Proportion of shape, fixed aspect ratio.
    /// </summary>
    public static final int SHAPE_FRACTION_FIXED_RATIO = 7;  
    /// <summary>
    /// Aspect ratio is fixed, favor larger size. The values are Fixed.
    /// </summary>
    public static final int FIXED_RATIO_ENLARGE = 8;  
    /// <summary>
    /// EMUs, fixed aspect ratio.
    /// </summary>
    public static final int EMU_FIXED_RATIO_BIG = 9;  
    /// <summary>
    /// Pixels, fixed aspect ratio.
    /// </summary>
    public static final int PIXEL_FIXED_RATIO_BIG = 10; 
    /// <summary>
    /// Proportion of shape, fixed aspect ratio.
    /// </summary>
    public static final int SHAPE_FRACTION_FIXED_RATIO_BIG = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FillDimensionType value.
    /// </summary>
    public static String getName(/*FillDimensionType*/int fillDimensionType)
    {
    	switch (fillDimensionType)
    	{
    		case DEFAULT: return "DEFAULT";
    		case EMU: return "EMU";
    		case PIXEL: return "PIXEL";
    		case SHAPE_FRACTION: return "SHAPE_FRACTION";
    		case FIXED_ASPECT: return "FIXED_ASPECT";
    		case EMU_FIXED_RATIO: return "EMU_FIXED_RATIO";
    		case PIXEL_FIXED_RATIO: return "PIXEL_FIXED_RATIO";
    		case SHAPE_FRACTION_FIXED_RATIO: return "SHAPE_FRACTION_FIXED_RATIO";
    		case FIXED_RATIO_ENLARGE: return "FIXED_RATIO_ENLARGE";
    		case EMU_FIXED_RATIO_BIG: return "EMU_FIXED_RATIO_BIG";
    		case PIXEL_FIXED_RATIO_BIG: return "PIXEL_FIXED_RATIO_BIG";
    		case SHAPE_FRACTION_FIXED_RATIO_BIG: return "SHAPE_FRACTION_FIXED_RATIO_BIG";
    		default: return "Unknown FillDimensionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FillDimensionType value.
    /// </summary>
    public static String toString(/*FillDimensionType*/int fillDimensionType)
    {
    	switch (fillDimensionType)
    	{
    		case DEFAULT: return "Default";
    		case EMU: return "Emu";
    		case PIXEL: return "Pixel";
    		case SHAPE_FRACTION: return "ShapeFraction";
    		case FIXED_ASPECT: return "FixedAspect";
    		case EMU_FIXED_RATIO: return "EmuFixedRatio";
    		case PIXEL_FIXED_RATIO: return "PixelFixedRatio";
    		case SHAPE_FRACTION_FIXED_RATIO: return "ShapeFractionFixedRatio";
    		case FIXED_RATIO_ENLARGE: return "FixedRatioEnlarge";
    		case EMU_FIXED_RATIO_BIG: return "EmuFixedRatioBig";
    		case PIXEL_FIXED_RATIO_BIG: return "PixelFixedRatioBig";
    		case SHAPE_FRACTION_FIXED_RATIO_BIG: return "ShapeFractionFixedRatioBig";
    		default: return "Unknown FillDimensionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FillDimensionType name.
    /// </summary>
    public static /*FillDimensionType*/int fromName(String fillDimensionTypeName)
    {
    	if ("DEFAULT".equals(fillDimensionTypeName)) return DEFAULT;
    	if ("EMU".equals(fillDimensionTypeName)) return EMU;
    	if ("PIXEL".equals(fillDimensionTypeName)) return PIXEL;
    	if ("SHAPE_FRACTION".equals(fillDimensionTypeName)) return SHAPE_FRACTION;
    	if ("FIXED_ASPECT".equals(fillDimensionTypeName)) return FIXED_ASPECT;
    	if ("EMU_FIXED_RATIO".equals(fillDimensionTypeName)) return EMU_FIXED_RATIO;
    	if ("PIXEL_FIXED_RATIO".equals(fillDimensionTypeName)) return PIXEL_FIXED_RATIO;
    	if ("SHAPE_FRACTION_FIXED_RATIO".equals(fillDimensionTypeName)) return SHAPE_FRACTION_FIXED_RATIO;
    	if ("FIXED_RATIO_ENLARGE".equals(fillDimensionTypeName)) return FIXED_RATIO_ENLARGE;
    	if ("EMU_FIXED_RATIO_BIG".equals(fillDimensionTypeName)) return EMU_FIXED_RATIO_BIG;
    	if ("PIXEL_FIXED_RATIO_BIG".equals(fillDimensionTypeName)) return PIXEL_FIXED_RATIO_BIG;
    	if ("SHAPE_FRACTION_FIXED_RATIO_BIG".equals(fillDimensionTypeName)) return SHAPE_FRACTION_FIXED_RATIO_BIG;
    	throw new IllegalArgumentException("Unknown FillDimensionType name.");
    }
    
    /// <summary>
    /// Returns array of FillDimensionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		EMU,
    		PIXEL,
    		SHAPE_FRACTION,
    		FIXED_ASPECT,
    		EMU_FIXED_RATIO,
    		PIXEL_FIXED_RATIO,
    		SHAPE_FRACTION_FIXED_RATIO,
    		FIXED_RATIO_ENLARGE,
    		EMU_FIXED_RATIO_BIG,
    		PIXEL_FIXED_RATIO_BIG,
    		SHAPE_FRACTION_FIXED_RATIO_BIG,
    	};
    }

    public static final int length = 12;
}

