// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.

package com.aspose.words;


import java.awt.*;

/// <summary>
/// Allows to specify additional <ms><see cref="Graphics"/> quality options</ms><java><see javaref="T:java.awt.RenderingHints"/></java>.
/// </summary>
// [JavaManual("Platform-specific implementation.")]
public class GraphicsQualityOptions
{

    /// <summary>
    /// Gets the preferences for the rendering algorithms.
    /// </summary>
    public RenderingHints getRenderingHints()
    {
        if (mRenderingHints == null)
            mRenderingHints = new RenderingHints(null);

        return mRenderingHints;
    }

    /// <summary>
    /// Sets the value of a single preference for the rendering algorithms.
    /// </summary>
    public void setRenderingHints(RenderingHints renderingHints)
    {
        mRenderingHints = renderingHints;
    }

    private RenderingHints mRenderingHints;

    /// <summary>
    /// Gets or sets WrapMode to TileFlipXY. Default WrapMode results in inaccurate rendered scaled image.
    /// </summary>
    public boolean getUseTileFlipMode() { return mUseTileFlipMode; };
    public void setUseTileFlipMode(boolean value) { mUseTileFlipMode = value; };

    private boolean mUseTileFlipMode;
}

