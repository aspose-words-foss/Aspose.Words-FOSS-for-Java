// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2006 by Dmitry Vorobyev

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Drawing.msSize;


/// <summary>
/// Contains information about image size and resolution.
/// </summary>
public class ImageSizeCore
{
    /// <summary>
    /// Creates zero width and height object, but with 96 dpi resolution.
    /// </summary>
    public static ImageSizeCore createEmpty()
    {
        return new ImageSizeCore(0, 0, 0, 0, 0.0, 0.0); // RK The resolution should be zero here, it is "validated" in the ctor.
    }

    /// <summary>
    /// Creates an image size object when you know resolution.
    /// But you still can pass resolution zero, it will default to 96 dpi.
    /// </summary>
    /// <param name="width">Pixels.</param>
    /// <param name="height">Pixels.</param>
    /// <param name="hRes">DPI</param>
    /// <param name="vRes">DPI</param>
    /// <returns></returns>
    public static ImageSizeCore createWithResolution(
        int width,
        int height,
        double hRes,
        double vRes)
    {
        return new ImageSizeCore(0, 0, width, height, hRes, vRes);
    }

    /// <summary>
    /// Creates an image size object when you know resolution.
    /// But you still can pass resolution zero, it will default to 96 dpi.
    /// </summary>
    /// <param name="width">Pixels.</param>
    /// <param name="height">Pixels.</param>
    /// <param name="res">Horizontal and vertical DPI.</param>
    /// <returns></returns>
    public static ImageSizeCore createWithResolution(
        int width,
        int height,
        double res)
    {
        double hRes = res;
        double vRes = res;
        return new ImageSizeCore(0, 0, width, height, hRes, vRes);
    }

    /// <summary>
    /// Creates an image size object when you know physical size (in EMUs).
    /// </summary>
    /// <param name="left">Pixels</param>
    /// <param name="top">Pixels</param>
    /// <param name="right">Pixels</param>
    /// <param name="bottom">Pixels</param>
    /// <param name="widthEmus">EMUs</param>
    /// <param name="heightEmus">EMUs</param>
    /// <returns></returns>
    public static ImageSizeCore createWithDimensions(
        int left,
        int top,
        int right,
        int bottom,
        int widthEmus,
        int heightEmus)
    {
        int width = right - left;
        int height = bottom - top;
        double hRes = (widthEmus != 0) ? (width / ConvertUtilCore.emuToInch(widthEmus)) : 0.0;
        double vRes = (heightEmus != 0) ? (height / ConvertUtilCore.emuToInch(heightEmus)) : 0.0;
        return new ImageSizeCore(left, top, width, height, hRes, vRes);
    }

    /// <summary>
    /// Use static ctors instead.
    /// </summary>
    private ImageSizeCore(int left, int top, int width, int height, double hRes, double vRes)
    {
        mLeft = left;
        mTop = top;
        mWidth = width;
        mHeight = height;
        mHorizontalResolution = hRes;
        mVerticalResolution = vRes;

        if ((hRes == 0) || (vRes == 0))
        {
            mIsOriginalResolutionZero = true;
            mHorizontalResolution = ImageConstants.STANDARD_RESOLUTION;
            mVerticalResolution = ImageConstants.STANDARD_RESOLUTION;
        }
    }

    /// <summary>
    /// Left bound of the image, pixels. Normally used for metafiles only.
    /// </summary>
    public int getLeft() { return mLeft; }

    /// <summary>
    /// Top bound of the image, pixels. Normally used for metafiles only.
    /// </summary>
    public int getTop() { return mTop; }

    /// <summary>
    /// Right bound of the image, pixels. Normally used for metafiles only.
    /// </summary>
    public int getRight() { return getLeft() + getWidth(); }

    /// <summary>
    /// Bottom bound of the image, pixels. Normally used for metafiles only.
    /// </summary>
    public int getBottom() { return getTop() + getHeight(); }

    /// <summary>
    /// Width of the image in pixels.
    /// </summary>
    public int getWidth() { return mWidth; }

    /// <summary>
    /// Height of the image in pixels.
    /// </summary>
    public int getHeight() { return mHeight; }

    /// <summary>
    /// Horizontal resolution in DPI.
    /// </summary>
    public double getHorizontalResolution() { return mHorizontalResolution; }

    /// <summary>
    /// Vertical resolution in DPI.
    /// </summary>
    public double getVerticalResolution() { return mVerticalResolution; }

    /// <summary>
    /// Returns true if the resolution was not specified in the image.
    ///
    /// We never returns zero resolution from <see cref="HorizontalResolution"/> and <see cref="VerticalResolution"/>
    /// to simplify mathematical calculations based on them for the clients, we default to 96 dpi.
    /// But sometimes it is important to know if the resolution was actually specified or not in the image.
    /// </summary>
    public boolean isOriginalResolutionZero() { return mIsOriginalResolutionZero; }

    /// <summary>
    /// Width of the image in points. 1 point is 1/72 inch.
    /// </summary>
    public double getWidthPoints() { return ConvertUtilCore.pixelToPoint(mWidth, mHorizontalResolution); }

    /// <summary>
    /// Height of the image in points. 1 point is 1/72 inch.
    /// </summary>
    public double getHeightPoints() { return ConvertUtilCore.pixelToPoint(mHeight, mVerticalResolution); }

    /// <summary>
    /// Gets width of the metafile in EMUs.
    /// </summary>
    public int getWidthEmus() { return ConvertUtilCore.pointToEmu(getWidthPoints()); }

    /// <summary>
    /// Gets height of the metafile in EMUs.
    /// </summary>
    public int getHeightEmus() { return ConvertUtilCore.pointToEmu(getHeightPoints()); }

    public int getWidthTwips() { return ConvertUtilCore.pixelToTwip(getWidth(), getHorizontalResolution()); }

    public int getHeightTwips() { return ConvertUtilCore.pixelToTwip(getHeight(), getVerticalResolution()); }

    public /*Size*/long getSize() { return msSize.ctor(mWidth, mHeight); }

    private /*final*/ int mLeft;
    private /*final*/ int mTop;
    private /*final*/ int mWidth;
    private /*final*/ int mHeight;
    private double mHorizontalResolution;       // Can't make readonly because of Java.
    private double mVerticalResolution;         // Can't make readonly because of Java.
    private boolean mIsOriginalResolutionZero;     // Can't make readonly because of Java.
}

