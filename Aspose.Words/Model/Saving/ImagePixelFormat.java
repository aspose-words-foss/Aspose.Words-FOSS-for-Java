// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/07/2010 by Alex Novickov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the pixel format for the generated images of document pages.
/// </summary>
public /*enum*/ final class ImagePixelFormat
{
    private ImagePixelFormat(){}
    
    /// <summary>
    /// 16 bits per pixel, RGB.
    /// </summary>
    public static final int FORMAT_16_BPP_RGB_555 = 0;
    /// <summary>
    /// 16 bits per pixel, RGB.
    /// </summary>
    public static final int FORMAT_16_BPP_RGB_565 = 1;
    /// <summary>
    /// 16 bits per pixel, ARGB.
    /// </summary>
    public static final int FORMAT_16_BPP_ARGB_1555 = 2;
    /// <summary>
    /// 24 bits per pixel, RGB.
    /// </summary>
    public static final int FORMAT_24_BPP_RGB = 3;
    /// <summary>
    /// 32 bits per pixel, RGB.
    /// </summary>
    public static final int FORMAT_32_BPP_RGB = 4;
    /// <summary>
    /// 32 bits per pixel, ARGB.
    /// </summary>
    public static final int FORMAT_32_BPP_ARGB = 5;
    /// <summary>
    /// 32 bits per pixel, ARGB, premultiplied alpha.
    /// </summary>
    public static final int FORMAT_32_BPP_P_ARGB = 6;
    /// <summary>
    /// 48 bits per pixel, RGB.
    /// </summary>
    public static final int FORMAT_48_BPP_RGB = 7;
    /// <summary>
    /// 64 bits per pixel, ARGB.
    /// </summary>
    public static final int FORMAT_64_BPP_ARGB = 8;
    /// <summary>
    /// 64 bits per pixel, ARGB, premultiplied alpha.
    /// </summary>
    public static final int FORMAT_64_BPP_P_ARGB = 9;
    /// <summary>
    /// 1 bit per pixel, Indexed.
    /// </summary>
    public static final int FORMAT_1_BPP_INDEXED = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImagePixelFormat value.
    /// </summary>
    public static String getName(/*ImagePixelFormat*/int imagePixelFormat)
    {
    	switch (imagePixelFormat)
    	{
    		case FORMAT_16_BPP_RGB_555: return "FORMAT_16_BPP_RGB_555";
    		case FORMAT_16_BPP_RGB_565: return "FORMAT_16_BPP_RGB_565";
    		case FORMAT_16_BPP_ARGB_1555: return "FORMAT_16_BPP_ARGB_1555";
    		case FORMAT_24_BPP_RGB: return "FORMAT_24_BPP_RGB";
    		case FORMAT_32_BPP_RGB: return "FORMAT_32_BPP_RGB";
    		case FORMAT_32_BPP_ARGB: return "FORMAT_32_BPP_ARGB";
    		case FORMAT_32_BPP_P_ARGB: return "FORMAT_32_BPP_P_ARGB";
    		case FORMAT_48_BPP_RGB: return "FORMAT_48_BPP_RGB";
    		case FORMAT_64_BPP_ARGB: return "FORMAT_64_BPP_ARGB";
    		case FORMAT_64_BPP_P_ARGB: return "FORMAT_64_BPP_P_ARGB";
    		case FORMAT_1_BPP_INDEXED: return "FORMAT_1_BPP_INDEXED";
    		default: return "Unknown ImagePixelFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImagePixelFormat value.
    /// </summary>
    public static String toString(/*ImagePixelFormat*/int imagePixelFormat)
    {
    	switch (imagePixelFormat)
    	{
    		case FORMAT_16_BPP_RGB_555: return "Format16BppRgb555";
    		case FORMAT_16_BPP_RGB_565: return "Format16BppRgb565";
    		case FORMAT_16_BPP_ARGB_1555: return "Format16BppArgb1555";
    		case FORMAT_24_BPP_RGB: return "Format24BppRgb";
    		case FORMAT_32_BPP_RGB: return "Format32BppRgb";
    		case FORMAT_32_BPP_ARGB: return "Format32BppArgb";
    		case FORMAT_32_BPP_P_ARGB: return "Format32BppPArgb";
    		case FORMAT_48_BPP_RGB: return "Format48BppRgb";
    		case FORMAT_64_BPP_ARGB: return "Format64BppArgb";
    		case FORMAT_64_BPP_P_ARGB: return "Format64BppPArgb";
    		case FORMAT_1_BPP_INDEXED: return "Format1bppIndexed";
    		default: return "Unknown ImagePixelFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImagePixelFormat name.
    /// </summary>
    public static /*ImagePixelFormat*/int fromName(String imagePixelFormatName)
    {
    	if ("FORMAT_16_BPP_RGB_555".equals(imagePixelFormatName)) return FORMAT_16_BPP_RGB_555;
    	if ("FORMAT_16_BPP_RGB_565".equals(imagePixelFormatName)) return FORMAT_16_BPP_RGB_565;
    	if ("FORMAT_16_BPP_ARGB_1555".equals(imagePixelFormatName)) return FORMAT_16_BPP_ARGB_1555;
    	if ("FORMAT_24_BPP_RGB".equals(imagePixelFormatName)) return FORMAT_24_BPP_RGB;
    	if ("FORMAT_32_BPP_RGB".equals(imagePixelFormatName)) return FORMAT_32_BPP_RGB;
    	if ("FORMAT_32_BPP_ARGB".equals(imagePixelFormatName)) return FORMAT_32_BPP_ARGB;
    	if ("FORMAT_32_BPP_P_ARGB".equals(imagePixelFormatName)) return FORMAT_32_BPP_P_ARGB;
    	if ("FORMAT_48_BPP_RGB".equals(imagePixelFormatName)) return FORMAT_48_BPP_RGB;
    	if ("FORMAT_64_BPP_ARGB".equals(imagePixelFormatName)) return FORMAT_64_BPP_ARGB;
    	if ("FORMAT_64_BPP_P_ARGB".equals(imagePixelFormatName)) return FORMAT_64_BPP_P_ARGB;
    	if ("FORMAT_1_BPP_INDEXED".equals(imagePixelFormatName)) return FORMAT_1_BPP_INDEXED;
    	throw new IllegalArgumentException("Unknown ImagePixelFormat name.");
    }
    
    /// <summary>
    /// Returns array of ImagePixelFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FORMAT_16_BPP_RGB_555,
    		FORMAT_16_BPP_RGB_565,
    		FORMAT_16_BPP_ARGB_1555,
    		FORMAT_24_BPP_RGB,
    		FORMAT_32_BPP_RGB,
    		FORMAT_32_BPP_ARGB,
    		FORMAT_32_BPP_P_ARGB,
    		FORMAT_48_BPP_RGB,
    		FORMAT_64_BPP_ARGB,
    		FORMAT_64_BPP_P_ARGB,
    		FORMAT_1_BPP_INDEXED,
    	};
    }

    public static final int length = 11;
}

