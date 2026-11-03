// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/05/2006 by Dmitry Vorobyev

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies what type of compression to apply when saving pages to the TIFF format.
/// </summary>
public /*enum*/ final class TiffCompressionCore
{
    private TiffCompressionCore(){}
    
    /// <summary>
    /// Specifies no compression.
    /// </summary>
    public static final int NONE = 1;
    /// <summary>
    /// Specifies the RLE compression scheme.
    /// </summary>
    public static final int RLE = 2;
    /// <summary>
    /// Specifies the LZW compression scheme.
    /// In Java emulated by Deflate (Zip) compression.
    /// </summary>
    public static final int LZW = 5;
    /// <summary>
    /// Specifies the CCITT3 compression scheme.
    /// </summary>
    public static final int CCITT_3 = 3;
    /// <summary>
    /// Specifies the CCITT4 compression scheme.
    /// </summary>
    public static final int CCITT_4 = 4;

    // AN: The following values are used only during validating TIFF images.

    /// <summary>
    /// Specifies the Jpeg compression scheme.
    /// </summary>
    public static final int JPEG = 6;
    /// <summary>
    /// Specifies the PackBits compression scheme.
    /// </summary>
    public static final int PACK_BITS = 32773;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffCompressionCore value.
    /// </summary>
    public static String getName(/*TiffCompressionCore*/int tiffCompressionCore)
    {
    	switch (tiffCompressionCore)
    	{
    		case NONE: return "NONE";
    		case RLE: return "RLE";
    		case LZW: return "LZW";
    		case CCITT_3: return "CCITT_3";
    		case CCITT_4: return "CCITT_4";
    		case JPEG: return "JPEG";
    		case PACK_BITS: return "PACK_BITS";
    		default: return "Unknown TiffCompressionCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffCompressionCore value.
    /// </summary>
    public static String toString(/*TiffCompressionCore*/int tiffCompressionCore)
    {
    	switch (tiffCompressionCore)
    	{
    		case NONE: return "None";
    		case RLE: return "Rle";
    		case LZW: return "Lzw";
    		case CCITT_3: return "Ccitt3";
    		case CCITT_4: return "Ccitt4";
    		case JPEG: return "Jpeg";
    		case PACK_BITS: return "PackBits";
    		default: return "Unknown TiffCompressionCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffCompressionCore name.
    /// </summary>
    public static /*TiffCompressionCore*/int fromName(String tiffCompressionCoreName)
    {
    	if ("NONE".equals(tiffCompressionCoreName)) return NONE;
    	if ("RLE".equals(tiffCompressionCoreName)) return RLE;
    	if ("LZW".equals(tiffCompressionCoreName)) return LZW;
    	if ("CCITT_3".equals(tiffCompressionCoreName)) return CCITT_3;
    	if ("CCITT_4".equals(tiffCompressionCoreName)) return CCITT_4;
    	if ("JPEG".equals(tiffCompressionCoreName)) return JPEG;
    	if ("PACK_BITS".equals(tiffCompressionCoreName)) return PACK_BITS;
    	throw new IllegalArgumentException("Unknown TiffCompressionCore name.");
    }
    
    /// <summary>
    /// Returns array of TiffCompressionCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		RLE,
    		LZW,
    		CCITT_3,
    		CCITT_4,
    		JPEG,
    		PACK_BITS,
    	};
    }

    public static final int length = 7;
}
