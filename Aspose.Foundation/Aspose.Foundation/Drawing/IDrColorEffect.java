// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2015 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// An interface provided by various color effects E.g. grayscale, black&amp;white etc.
/// </summary>
public interface IDrColorEffect
{
    /// <summary>
    /// Applies the effect to the specified color.
    /// </summary>
    /// <param name="color">Source color.</param>
    /// <returns>The color with effect applied.</returns>
    public DrColor apply(DrColor color);

    /// <summary>
    /// Applies the effect to the specified image.
    /// </summary>
    /// <param name="imageBytes">Source image bytes.</param>
    /// <returns>Images bytes with effect applied.</returns>
    public byte[] apply(byte[] imageBytes) throws Exception;
}

