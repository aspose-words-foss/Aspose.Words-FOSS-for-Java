// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Provides common interface for ECMA-376 files encryption.
/// </summary>
interface IEcma376Encryption
{
    /// <summary>
    /// Decrypts structured storage and writes decrypted content into given stream.
    /// </summary>
    public void decrypt(Stream encryptionInfo, Stream encryptedStream, Stream stream, String password) throws Exception;

    /// <summary>
    /// Encrypts stream and writes encrypted content into given structured storage.
    /// </summary>
    public void encrypt(Stream encryptionInfo, Stream stream, Stream encryptedStream, String password) throws Exception;
}

