// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/09/2016 by Ivan Lyagin
// 
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Provides document streams to perform a GOLD comparison test.
/// </summary>
public interface IComparisonStreamProvider
{
    /// <summary>
    /// Returns an out document stream.
    /// </summary>
    public Stream getStreamOut() throws Exception;
    /// <summary>
    /// Returns a GOLD document stream.
    /// </summary>
    public Stream getStreamGold() throws Exception;
    /// <summary>
    /// Returns an original GOLD document stream. When Java GOLD document exists, .Net GOLD document 
    /// is referred as original GOLD.
    /// </summary>
    public Stream getStreamOriginalGold() throws Exception;
    /// <summary>
    /// Returns an MS Word document stream.
    /// </summary>
    public Stream getStreamMS() throws Exception;
}

