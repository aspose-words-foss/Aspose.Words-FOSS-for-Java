// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Defines an interface for readers that can read a file into a document.
/// Helps to break dependency Aspose.Words from RW.*.
/// </summary>
interface IDocumentReader
{
    /// <summary>
    /// Reads the file into the document.
    /// </summary>
    public void read() throws Exception;

    public boolean isEncrypted();

    public Stream decrypt() throws Exception;
}

