// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This object holds a lot of memory and should not be held in memory for too long.
///
/// This is a very dumb, but very memory expensive object that contains all image pixels values.
/// At the moment it contains pixels in a format suitable for saving to PDF.
/// But I am keeping this class here because I want to put platform-specific image extraction into
/// <see cref="BitmapPal"/> and I want it to return the parsed pixels in a platform-neutral way.
/// </summary>
public class DrPixels
{
    public DrPixels(
        byte[] colorValues,
        byte[] alphaValues,
        boolean hasTransparentPixels,
        /*ColorModel*/int colorModel,
        int bitsPerComponent,
        DrColor[] colorTable)
    {
        mColorValues = colorValues;
        mAlphaValues = alphaValues;
        mHasTransparentPixels = hasTransparentPixels;
        mColorModel = colorModel;

        if (colorModel == ColorModel.INDEXED && bitsPerComponent != 1 && bitsPerComponent != 4 && bitsPerComponent != 8)
            throw new IllegalArgumentException("Wrong bitsPerComponent value with indexed color model");

        mBitsPerComponent = bitsPerComponent;
        mColorTable = colorTable;
    }

    /// <summary>
    /// Returns an array containing either RGB values for RGB bitmaps or color indices for indexed bitmaps.
    /// </summary>
    public byte[] getColorValues() { return mColorValues; }

    private /*final*/ byte[] mColorValues;
    private byte[] mAlphaValues;
    private boolean mHasTransparentPixels;
    private /*final*/ /*ColorModel*/int mColorModel;
    private /*final*/ int mBitsPerComponent;
    private /*final*/ DrColor[] mColorTable;
}

