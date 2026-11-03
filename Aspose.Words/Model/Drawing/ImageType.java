// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type (format) of an image in a Microsoft Word document.
/// </summary>
/// <seealso cref="ImageData.ImageType"/>
/// <dev>
/// Do not renumber these. The values correspond to MSOBLIPTYPE in the Binary Drawing format.
/// </dev>
public /*enum*/ final class ImageType
{
    private ImageType(){}
    
    /// <summary>
    /// The is no image data.
    /// </summary>
    /// <dev>
    /// The spec says: "An error occurred during loading."
    /// I've seen this in some places, more likely it means "this blip record is unused".
    /// </dev>
    public static final int NO_IMAGE = 0;
    /// <summary>
    /// An unknown image type or image type that cannot be directly stored inside a Microsoft Word document.
    /// </summary>
    public static final int UNKNOWN = 1;
    /// <summary>
    /// Windows Enhanced Metafile.
    /// </summary>
    public static final int EMF = 2;
    /// <summary>
    /// Windows Metafile.
    /// </summary>
    public static final int WMF = 3;
    /// <summary>
    /// Macintosh PICT. An existing image will be preserved in a document, but inserting new
    /// PICT images into a document is not supported.
    /// </summary>
    public static final int PICT = 4;
    /// <summary>
    /// JPEG JFIF.
    /// </summary>
    public static final int JPEG = 5;
    /// <summary>
    /// Portable Network Graphics.
    /// </summary>
    public static final int PNG = 6;
    /// <summary>
    /// Windows Bitmap.
    /// </summary>
    /// <dev>
    /// Note this is BMP in my model, but in a Word document this is stored as a DIB.
    /// DIB is basically a BMP without a header.
    /// </dev>
    public static final int BMP = 7;
    /// <summary>
    /// Encapsulated PostScript.
    /// </summary>
    public static final int EPS = 8;
    /// <summary>
    /// WebP.
    /// </summary>
    public static final int WEB_P = 9;
    /// <summary>
    /// GIF
    /// </summary>
    public static final int GIF = 10;
    // <dev>
    // I've seen image type 18. It was actually JPEG. We support it, but no need to show in the public API.
    // </dev>

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImageType value.
    /// </summary>
    public static String getName(/*ImageType*/int imageType)
    {
    	switch (imageType)
    	{
    		case NO_IMAGE: return "NO_IMAGE";
    		case UNKNOWN: return "UNKNOWN";
    		case EMF: return "EMF";
    		case WMF: return "WMF";
    		case PICT: return "PICT";
    		case JPEG: return "JPEG";
    		case PNG: return "PNG";
    		case BMP: return "BMP";
    		case EPS: return "EPS";
    		case WEB_P: return "WEB_P";
    		case GIF: return "GIF";
    		default: return "Unknown ImageType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImageType value.
    /// </summary>
    public static String toString(/*ImageType*/int imageType)
    {
    	switch (imageType)
    	{
    		case NO_IMAGE: return "NoImage";
    		case UNKNOWN: return "Unknown";
    		case EMF: return "Emf";
    		case WMF: return "Wmf";
    		case PICT: return "Pict";
    		case JPEG: return "Jpeg";
    		case PNG: return "Png";
    		case BMP: return "Bmp";
    		case EPS: return "Eps";
    		case WEB_P: return "WebP";
    		case GIF: return "Gif";
    		default: return "Unknown ImageType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImageType name.
    /// </summary>
    public static /*ImageType*/int fromName(String imageTypeName)
    {
    	if ("NO_IMAGE".equals(imageTypeName)) return NO_IMAGE;
    	if ("UNKNOWN".equals(imageTypeName)) return UNKNOWN;
    	if ("EMF".equals(imageTypeName)) return EMF;
    	if ("WMF".equals(imageTypeName)) return WMF;
    	if ("PICT".equals(imageTypeName)) return PICT;
    	if ("JPEG".equals(imageTypeName)) return JPEG;
    	if ("PNG".equals(imageTypeName)) return PNG;
    	if ("BMP".equals(imageTypeName)) return BMP;
    	if ("EPS".equals(imageTypeName)) return EPS;
    	if ("WEB_P".equals(imageTypeName)) return WEB_P;
    	if ("GIF".equals(imageTypeName)) return GIF;
    	throw new IllegalArgumentException("Unknown ImageType name.");
    }
    
    /// <summary>
    /// Returns array of ImageType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NO_IMAGE,
    		UNKNOWN,
    		EMF,
    		WMF,
    		PICT,
    		JPEG,
    		PNG,
    		BMP,
    		EPS,
    		WEB_P,
    		GIF,
    	};
    }

    public static final int length = 11;
}

