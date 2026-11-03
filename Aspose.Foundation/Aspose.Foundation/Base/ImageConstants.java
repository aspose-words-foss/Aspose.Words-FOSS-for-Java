// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2016 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*static*/ class ImageConstants
{	/* Simulation of static class by using private constructor */	private ImageConstants()	{}

    public static final double INCHES_PER_METER = 100.0 / 2.54;

    /// <summary>
    /// This is the default resolution for images we create.
    /// We should specify it on all bitmaps we create because new bitmaps
    /// default to the current screen resolution.
    /// </summary>
    public static final float STANDARD_RESOLUTION = 96.0f;

    /// <summary>
    /// Resolution for bitmap images created from metafiles.
    /// </summary>
    public static final int PRINT_RESOLUTION = 300;

    /// <summary>
    /// Size of pict header, when pict is read from file.
    /// </summary>
    public static final int PICT_HEADER_LENGTH = 512;
}

