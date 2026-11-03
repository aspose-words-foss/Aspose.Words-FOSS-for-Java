// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/22/2020 by Artem Ptitsin.

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides methods for working with a watermark.
/// </summary>
/// <remarks>
/// It is assumed that work with a watermark will be possible with a section or even with a header in the future.
/// </remarks>
interface IWatermarkProvider
{
    /// <summary>
    /// Adds a watermark.
    /// </summary>
    public void add(Shape watermark) throws Exception;

    /// <summary>
    /// Gets the watermark shape or null.
    /// </summary>
    public Shape get();

    /// <summary>
    /// Removes a watermark.
    /// </summary>
    public void remove();
}

