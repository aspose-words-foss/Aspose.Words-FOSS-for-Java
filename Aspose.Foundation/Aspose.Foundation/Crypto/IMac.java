// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/04/2016 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The base interface for implementations of message authentication codes (MACs).
/// </summary>
public interface IMac
{
    /// <summary>
    /// Initializes underlying MAC with the specified key.
    /// </summary>
    public void init( byte[] key);

    /// <summary>
    /// Computes hash for the specified content.
    /// </summary>
    public byte[] computeHash( byte[] content);

    /// <summary>
    /// Gets MAC size.
    /// </summary>
    public int getSize();
}

