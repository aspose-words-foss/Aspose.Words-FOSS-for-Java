// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2023 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Defines an interface for external converter plugin.
/// </summary>
public interface IDocumentConverterPlugin
{
    /// <summary>
    /// Converts document using specified input output streams and save options.
    /// </summary>
    /// <param name="inputStream">The input stream.</param>
    /// <param name="loadOptions">The document load options.</param>
    /// <param name="outputStream">The output stream.</param>
    /// <param name="saveOptions">The save options.</param>
    public void convert(java.io.InputStream inputStream, LoadOptions loadOptions, java.io.OutputStream outputStream, SaveOptions saveOptions);

    /// <summary>
    /// Converts pages from document from input stream to array of images.
    /// </summary>
    /// <param name="inputStream">The input stream.</param>
    /// <param name="loadOptions">The document load options.</param>
    /// <param name="saveOptions">The save options.</param>
    /// <returns>Array of page images streams.</returns>
    public java.io.OutputStream[] convertToImages(java.io.InputStream inputStream, LoadOptions loadOptions, SaveOptions saveOptions);
}

