// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/05/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies what type of compression to apply when saving page images into a TIFF file.
/// </summary>
public /*enum*/ final class TiffCompression
{
    private TiffCompression(){}
    
    /// <summary>
    /// Specifies no compression.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies the RLE compression scheme.
    /// </summary>
    public static final int RLE = 1;
    /// <summary>
    /// Specifies the LZW compression scheme.
    /// In Java emulated by Deflate (Zip) compression.
    /// </summary>
    public static final int LZW = 2;
    /// <summary>
    /// Specifies the CCITT3 compression scheme.
    /// </summary>
    public static final int CCITT_3 = 3;
    /// <summary>
    /// Specifies the CCITT4 compression scheme.
    /// </summary>
    public static final int CCITT_4 = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TiffCompression value.
    /// </summary>
    public static String getName(/*TiffCompression*/int tiffCompression)
    {
    	switch (tiffCompression)
    	{
    		case NONE: return "NONE";
    		case RLE: return "RLE";
    		case LZW: return "LZW";
    		case CCITT_3: return "CCITT_3";
    		case CCITT_4: return "CCITT_4";
    		default: return "Unknown TiffCompression value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TiffCompression value.
    /// </summary>
    public static String toString(/*TiffCompression*/int tiffCompression)
    {
    	switch (tiffCompression)
    	{
    		case NONE: return "None";
    		case RLE: return "Rle";
    		case LZW: return "Lzw";
    		case CCITT_3: return "Ccitt3";
    		case CCITT_4: return "Ccitt4";
    		default: return "Unknown TiffCompression value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TiffCompression name.
    /// </summary>
    public static /*TiffCompression*/int fromName(String tiffCompressionName)
    {
    	if ("NONE".equals(tiffCompressionName)) return NONE;
    	if ("RLE".equals(tiffCompressionName)) return RLE;
    	if ("LZW".equals(tiffCompressionName)) return LZW;
    	if ("CCITT_3".equals(tiffCompressionName)) return CCITT_3;
    	if ("CCITT_4".equals(tiffCompressionName)) return CCITT_4;
    	throw new IllegalArgumentException("Unknown TiffCompression name.");
    }
    
    /// <summary>
    /// Returns array of TiffCompression values.
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
    	};
    }

    public static final int length = 5;
}


