// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


/// <summary>
/// Contains options that can be specified when adding a watermark with image.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-watermark/">Working with Watermark</a> documentation article.</para>
/// </summary>
public class ImageWatermarkOptions
{
    /// <summary>
    /// Gets or sets the scale factor expressed as a fraction of the image. The default value is 0 - auto.
    /// </summary>
    /// <remarks>
    /// <p>Valid values range from 0 to 65.5 inclusive.</p>
    /// <p>Auto scale means that the watermark will be scaled to its max width and max height relative to
    /// the page margins.</p>
    /// </remarks>
    /// <exception cref="ArgumentOutOfRangeException">
    /// Throws when argument was out of the range of valid values.
    /// </exception>
    public double getScale() { return mScale; }
    public void setScale(double value) { setScaleInternal(value); }

    /// <summary>
    /// Gets or sets a boolean value which is responsible for washout effect of the watermark.
    /// The default value is <c>true</c>.
    /// </summary>
    public boolean isWashout() { return mIsWashout; }
    public void isWashout(boolean value) { mIsWashout = value; }

    boolean isScaleAuto() { return mScale == AUTO_SCALE; }

    private void setScaleInternal(double value)
    {
        mScale = ArgumentUtil.validateRange(value, 0.0, 0.0, MAX_VALUE, MAX_VALUE, true, "Scale");
    }

    private static final double AUTO_SCALE = 0.0;
    private double mScale = AUTO_SCALE;
    private boolean mIsWashout = true;
    private static final double MAX_VALUE = 65.5;
}

