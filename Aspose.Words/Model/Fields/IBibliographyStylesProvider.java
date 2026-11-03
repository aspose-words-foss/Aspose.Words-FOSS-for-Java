// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Implement this interface to provide bibliography style for
/// the  <see cref="FieldBibliography"/> and <see cref="FieldCitation"/> fields when they're updated.
/// </summary>
public interface IBibliographyStylesProvider
{
    /// <summary>
    /// Returns bibliography style.
    /// </summary>
    /// <param name="styleFileName">The bibliography style file name.</param>
    /// <returns>The <see cref="Stream"/> with bibliography style XSLT stylesheet.</returns>
    /// <remarks>
    /// The implementation should return <c>null</c> to indicate that
    /// the MS Word version of specified style should be used.
    /// </remarks>
    public java.io.InputStream getStyle(String styleFileName) throws Exception;
}

