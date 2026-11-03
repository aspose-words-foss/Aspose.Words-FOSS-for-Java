// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/12/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how to display the picture.
/// </summary>
/// <dev>
/// [MS-OFORMS], 2.5.65 PictureSizeMode
/// </dev>
/*enum*/ final class PictureSizeMode
{
    private PictureSizeMode(){}
    
    public static final int CLIP = 0x00;
    public static final int STRETCH = 0x01;
    public static final int ZOOM = 0x03;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PictureSizeMode value.
    /// </summary>
    public static String getName(/*PictureSizeMode*/int pictureSizeMode)
    {
    	switch (pictureSizeMode)
    	{
    		case CLIP: return "CLIP";
    		case STRETCH: return "STRETCH";
    		case ZOOM: return "ZOOM";
    		default: return "Unknown PictureSizeMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PictureSizeMode value.
    /// </summary>
    public static String toString(/*PictureSizeMode*/int pictureSizeMode)
    {
    	switch (pictureSizeMode)
    	{
    		case CLIP: return "Clip";
    		case STRETCH: return "Stretch";
    		case ZOOM: return "Zoom";
    		default: return "Unknown PictureSizeMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PictureSizeMode name.
    /// </summary>
    public static /*PictureSizeMode*/int fromName(String pictureSizeModeName)
    {
    	if ("CLIP".equals(pictureSizeModeName)) return CLIP;
    	if ("STRETCH".equals(pictureSizeModeName)) return STRETCH;
    	if ("ZOOM".equals(pictureSizeModeName)) return ZOOM;
    	throw new IllegalArgumentException("Unknown PictureSizeMode name.");
    }
    
    /// <summary>
    /// Returns array of PictureSizeMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CLIP,
    		STRETCH,
    		ZOOM,
    	};
    }

    public static final int length = 3;
}

