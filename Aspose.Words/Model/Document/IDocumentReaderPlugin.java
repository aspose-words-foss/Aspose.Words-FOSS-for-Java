// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2020 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Defines an interface for external reader plugins that can read a file into a document.
/// </summary>
public interface IDocumentReaderPlugin
{
    /// <summary>
    /// Reads the data from the specified stream into the <see cref="Document"/> instance.
    /// </summary>
    /// <param name="src">The source stream to read the document from.</param>
    /// <param name="loadOptions">An additional load options to load the document.</param>
    /// <param name="document">The instance of the <see cref="Document"/> class to read the data to. 
    /// If the instance contains some content, it will be overridden by the data from the source stream</param>
    /// <javaName>void read(java.io.InputStream src, com.aspose.words.LoadOptions loadOptions, com.aspose.words.Document document)</javaName>
    public void read(java.io.InputStream src, LoadOptions loadOptions, Document document);
}

