// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ImageConstants;


/// <summary>
/// Options used when exporting images.
/// Used internally in HTML, SVG and XAML export modules.
/// </summary>
class SaveImageOptions implements Cloneable
{
    /// <summary>
    /// Creates a clone of the current <see cref="SaveImageOptions"/> class.
    /// </summary>
    SaveImageOptions deepClone()
    {
        return (SaveImageOptions)memberwiseClone();
    }

    /// <summary>
    /// Indicates the format in which metafiles are to be saved.
    /// </summary>
    /*HtmlMetafileFormat*/int MetafileFormat;

    /// <summary>
    /// Causes all images to be scaled to the shape size.
    /// </summary>
    boolean ScaleImageToShapeSize;

    /// <summary>
    /// If metafiles are converted to raster or images are scaled to shape size,
    /// this is the output resolution for the images.
    /// </summary>
    int Resolution = DEFAULT_RESOLUTION;

    /// <summary>
    /// Causes all images to be saved in Base64 format.
    /// </summary>
    boolean ToBase64;

    /// <summary>
    /// When saving allows override standard behavior.
    /// See <see cref="ImageSavingArgs"/>.
    /// </summary>
    IImageSavingCallback ImageSavingCallback;

    /// <summary>
    /// Image's zoom factor.
    /// </summary>
    float Scale = 1.0f;

    /// <summary>
    /// Gets or sets a value determining whether or not to use anti-aliasing for rendering.
    /// </summary>
    boolean UseAntiAliasing;

    /// <summary>
    /// Causes all shapes to be saved as SVG markup.
    /// </summary>
    boolean ShapesAsSvg;

    /// <summary>
    /// Default output resolution for the images.
    /// </summary>
    static final int DEFAULT_RESOLUTION = (int)ImageConstants.STANDARD_RESOLUTION;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

