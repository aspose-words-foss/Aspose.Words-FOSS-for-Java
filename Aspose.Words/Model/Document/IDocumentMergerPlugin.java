// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2023 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Defines an interface for external merger plugin that can merge Pdf documents.
/// </summary>
public interface IDocumentMergerPlugin
{
    /// <summary>
    /// Merges the given input PDF documents into a single output PDF document using specified input and output streams.
    /// </summary>
    /// <param name="outputStream">The output stream.</param>
    /// <param name="inputStreams">The input streams.</param>
    /// <param name="loadOptions">Load options for the input files.</param>
    public void merge(java.io.OutputStream outputStream, java.io.InputStream[] inputStreams, LoadOptions[] loadOptions);
}

