// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ImageSizeCore;
import com.aspose.ConvertUtilCore;



/// <summary>
/// Contains information about image size and resolution.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-images/">Working with Images</a> documentation article.</para>
/// </summary>
/// <seealso cref="ImageData.ImageSize"/>
public class ImageSize
{
    /// <summary>
    /// Initializes width and height to the given values in pixels. Initializes resolution to 96 dpi.
    /// </summary>
    /// <param name="widthPixels">Width in pixels.</param>
    /// <param name="heightPixels">Height in pixels.</param>
    public ImageSize(int widthPixels, int heightPixels)
    {
    	this(widthPixels, heightPixels, 96.0, 96.0);
	
    }

    /// <summary>
    /// Initializes width, height and resolution to the given values.
    /// </summary>
    /// <param name="widthPixels">Width in pixels.</param>
    /// <param name="heightPixels">Height in pixels.</param>
    /// <param name="horizontalResolution">Horizontal resolution in DPI.</param>
    /// <param name="verticalResolution">Vertical resolution in DPI.</param>
    public ImageSize(int widthPixels, int heightPixels, double horizontalResolution, double verticalResolution)
    {
        mWidthPixels = widthPixels;
        mHeightPixels = heightPixels;
        mHorizontalResolution = horizontalResolution;
        mVerticalResolution = verticalResolution;
    }

    /// <summary>
    /// Creates an object from an internal image size object.
    /// </summary>
    ImageSize(ImageSizeCore core)
    {
        mWidthPixels = core.getWidth();
        mHeightPixels = core.getHeight();
        mHorizontalResolution = core.getHorizontalResolution();
        mVerticalResolution = core.getVerticalResolution();
    }

    /// <summary>
    /// Returns <c>true</c> if width and height are greater than zero.
    /// </summary>
    boolean isValid() { return (getWidthPixels() > 0) && (getHeightPixels() > 0); }


    /// <summary>
    /// Gets the width of the image in pixels.
    /// </summary>
    public int getWidthPixels() { return mWidthPixels; }

    /// <summary>
    /// Gets the height of the image in pixels.
    /// </summary>
    public int getHeightPixels() { return mHeightPixels; }

    /// <summary>
    /// Gets the horizontal resolution in DPI.
    /// </summary>
    public double getHorizontalResolution() { return mHorizontalResolution; }

    /// <summary>
    /// Gets the vertical resolution in DPI.
    /// </summary>
    public double getVerticalResolution() { return mVerticalResolution; }
    
    /// <summary>
    /// Gets the width of the image in points. 1 point is 1/72 inch.
    /// </summary>
    public double getWidthPoints() { return ConvertUtil.pixelToPoint(mWidthPixels, mHorizontalResolution); }

    /// <summary>
    /// Gets the height of the image in points. 1 point is 1/72 inch.
    /// </summary>
    public double getHeightPoints() { return ConvertUtil.pixelToPoint(mHeightPixels, mVerticalResolution); }

    int getWidthTwips() { return ConvertUtilCore.pixelToTwip(mWidthPixels, mHorizontalResolution); }

    int getHeightTwips() { return ConvertUtilCore.pixelToTwip(mHeightPixels, mVerticalResolution); }

    private /*final*/ int mWidthPixels;
    private /*final*/ int mHeightPixels;
    private /*final*/ double mHorizontalResolution;
    private /*final*/ double mVerticalResolution;
}

