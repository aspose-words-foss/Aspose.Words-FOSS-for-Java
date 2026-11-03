// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2013 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class BitmapCompression
{
    private BitmapCompression(){}
    
    public static final int RGB = 0x00;
    public static final int RLE_8 = 0x01;
    public static final int RLE_4 = 0x02;
    public static final int BIT_FIELDS = 0x03;
    public static final int JPEG = 0x04;
    public static final int PNG = 0x05;
    public static final int ALPHA_BIT_FIELDS = 0x06;
    public static final int CMYK = 0x0B;
    public static final int CMYK_RLE_8 = 0x0C;
    public static final int CMYK_RLE_4 = 0x0D;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BitmapCompression value.
    /// </summary>
    public static String getName(/*BitmapCompression*/int bitmapCompression)
    {
    	switch (bitmapCompression)
    	{
    		case RGB: return "RGB";
    		case RLE_8: return "RLE_8";
    		case RLE_4: return "RLE_4";
    		case BIT_FIELDS: return "BIT_FIELDS";
    		case JPEG: return "JPEG";
    		case PNG: return "PNG";
    		case ALPHA_BIT_FIELDS: return "ALPHA_BIT_FIELDS";
    		case CMYK: return "CMYK";
    		case CMYK_RLE_8: return "CMYK_RLE_8";
    		case CMYK_RLE_4: return "CMYK_RLE_4";
    		default: return "Unknown BitmapCompression value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BitmapCompression value.
    /// </summary>
    public static String toString(/*BitmapCompression*/int bitmapCompression)
    {
    	switch (bitmapCompression)
    	{
    		case RGB: return "Rgb";
    		case RLE_8: return "Rle8";
    		case RLE_4: return "Rle4";
    		case BIT_FIELDS: return "BitFields";
    		case JPEG: return "Jpeg";
    		case PNG: return "Png";
    		case ALPHA_BIT_FIELDS: return "AlphaBitFields";
    		case CMYK: return "Cmyk";
    		case CMYK_RLE_8: return "CmykRle8";
    		case CMYK_RLE_4: return "CmykRle4";
    		default: return "Unknown BitmapCompression value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BitmapCompression name.
    /// </summary>
    public static /*BitmapCompression*/int fromName(String bitmapCompressionName)
    {
    	if ("RGB".equals(bitmapCompressionName)) return RGB;
    	if ("RLE_8".equals(bitmapCompressionName)) return RLE_8;
    	if ("RLE_4".equals(bitmapCompressionName)) return RLE_4;
    	if ("BIT_FIELDS".equals(bitmapCompressionName)) return BIT_FIELDS;
    	if ("JPEG".equals(bitmapCompressionName)) return JPEG;
    	if ("PNG".equals(bitmapCompressionName)) return PNG;
    	if ("ALPHA_BIT_FIELDS".equals(bitmapCompressionName)) return ALPHA_BIT_FIELDS;
    	if ("CMYK".equals(bitmapCompressionName)) return CMYK;
    	if ("CMYK_RLE_8".equals(bitmapCompressionName)) return CMYK_RLE_8;
    	if ("CMYK_RLE_4".equals(bitmapCompressionName)) return CMYK_RLE_4;
    	throw new IllegalArgumentException("Unknown BitmapCompression name.");
    }
    
    /// <summary>
    /// Returns array of BitmapCompression values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RGB,
    		RLE_8,
    		RLE_4,
    		BIT_FIELDS,
    		JPEG,
    		PNG,
    		ALPHA_BIT_FIELDS,
    		CMYK,
    		CMYK_RLE_8,
    		CMYK_RLE_4,
    	};
    }

    public static final int length = 10;
}

