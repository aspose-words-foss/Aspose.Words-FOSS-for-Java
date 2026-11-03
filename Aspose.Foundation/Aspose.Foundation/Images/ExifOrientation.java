// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2021 by Dmitry Sokolov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Orientation defined in the EXIF (exchangeable image file format) meta information.
/// </summary>
/// <remarks>
/// This type of information is formatted according to the TIFF specification, and may be found in JPG, TIFF, PNG, JP2,
/// PGF, MIFF, HDP, PSP and other images.
/// </remarks>
public /*enum*/ final class ExifOrientation
{
    private ExifOrientation(){}
    
    /// <summary>
    /// Normal. Rotation none, flip none,
    /// </summary>
    public static final int HORIZONTAL = 1;

    /// <summary>
    /// Rotation none, flip X.
    /// </summary>
    public static final int MIRROR_HORIZONTAL = 2;

    /// <summary>
    /// Rotation 180, flip none.
    /// </summary>
    public static final int ROTATE = 3;

    /// <summary>
    /// Rotation none, flip Y.
    /// </summary>
    public static final int MIRROR = 4;

    /// <summary>
    /// Rotation 270, flip X.
    /// </summary>
    public static final int MIRROR_HORIZONTAL_AND_ROTATE_270 = 5;

    /// <summary>
    /// Rotation 90, flip none.
    /// </summary>
    public static final int ROTATE_90 = 6;

    /// <summary>
    /// Rotation 90, flip X.
    /// </summary>
    public static final int MIRROR_HORIZONTAL_AND_ROTATE_90 = 7;

    /// <summary>
    /// Rotation 270, flip none.
    /// </summary>
    public static final int ROTATE_270 = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ExifOrientation value.
    /// </summary>
    public static String getName(/*ExifOrientation*/int exifOrientation)
    {
    	switch (exifOrientation)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case MIRROR_HORIZONTAL: return "MIRROR_HORIZONTAL";
    		case ROTATE: return "ROTATE";
    		case MIRROR: return "MIRROR";
    		case MIRROR_HORIZONTAL_AND_ROTATE_270: return "MIRROR_HORIZONTAL_AND_ROTATE_270";
    		case ROTATE_90: return "ROTATE_90";
    		case MIRROR_HORIZONTAL_AND_ROTATE_90: return "MIRROR_HORIZONTAL_AND_ROTATE_90";
    		case ROTATE_270: return "ROTATE_270";
    		default: return "Unknown ExifOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ExifOrientation value.
    /// </summary>
    public static String toString(/*ExifOrientation*/int exifOrientation)
    {
    	switch (exifOrientation)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case MIRROR_HORIZONTAL: return "MirrorHorizontal";
    		case ROTATE: return "Rotate";
    		case MIRROR: return "Mirror";
    		case MIRROR_HORIZONTAL_AND_ROTATE_270: return "MirrorHorizontalAndRotate270";
    		case ROTATE_90: return "Rotate90";
    		case MIRROR_HORIZONTAL_AND_ROTATE_90: return "MirrorHorizontalAndRotate90";
    		case ROTATE_270: return "Rotate270";
    		default: return "Unknown ExifOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ExifOrientation name.
    /// </summary>
    public static /*ExifOrientation*/int fromName(String exifOrientationName)
    {
    	if ("HORIZONTAL".equals(exifOrientationName)) return HORIZONTAL;
    	if ("MIRROR_HORIZONTAL".equals(exifOrientationName)) return MIRROR_HORIZONTAL;
    	if ("ROTATE".equals(exifOrientationName)) return ROTATE;
    	if ("MIRROR".equals(exifOrientationName)) return MIRROR;
    	if ("MIRROR_HORIZONTAL_AND_ROTATE_270".equals(exifOrientationName)) return MIRROR_HORIZONTAL_AND_ROTATE_270;
    	if ("ROTATE_90".equals(exifOrientationName)) return ROTATE_90;
    	if ("MIRROR_HORIZONTAL_AND_ROTATE_90".equals(exifOrientationName)) return MIRROR_HORIZONTAL_AND_ROTATE_90;
    	if ("ROTATE_270".equals(exifOrientationName)) return ROTATE_270;
    	throw new IllegalArgumentException("Unknown ExifOrientation name.");
    }
    
    /// <summary>
    /// Returns array of ExifOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		MIRROR_HORIZONTAL,
    		ROTATE,
    		MIRROR,
    		MIRROR_HORIZONTAL_AND_ROTATE_270,
    		ROTATE_90,
    		MIRROR_HORIZONTAL_AND_ROTATE_90,
    		ROTATE_270,
    	};
    }

    public static final int length = 8;
}

