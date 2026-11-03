// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2015 by Vadim Polienko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;

import java.awt.image.BufferedImage;


/// <summary>
/// Public interface for barcode custom generator. Implementation should be provided by user.
/// </summary>
/// <remarks>
/// Generator instance should be passed through the <see cref="FieldOptions.BarcodeGenerator"/> property.
/// </remarks>
public interface IBarcodeGenerator
{
    /// <summary>
    /// Generate barcode image using the set of parameters (for DisplayBarcode field).
    /// </summary>
    /// <param name="parameters">The set of parameters</param>
    /// <returns>Stream with image data representing generated barcode.</returns>
    /// <remarks>Supported image formats are Bmp, Emf, Gif, Jpeg, Png, Tiff, Wmf, Pict, Ico, WebP, Svg.</remarks>
    public BufferedImage getBarcodeImage(BarcodeParameters parameters) throws Exception;

    /// <summary>
    /// Generate barcode image using the set of parameters (for old-fashioned Barcode field).
    /// </summary>
    /// <param name="parameters">The set of parameters</param>
    /// <returns>Stream with image data representing generated barcode.</returns>
    /// <remarks>Supported image formats are Bmp, Emf, Gif, Jpeg, Png, Tiff, Wmf, Pict, Ico, WebP, Svg.</remarks>
    public BufferedImage getOldBarcodeImage(BarcodeParameters parameters) throws Exception;
}

