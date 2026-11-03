// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;


/// <summary>
/// 2.3.2 EncryptionHeader Structure
/// [ECMA-376] document encryption and Office binary document RC4 CryptoAPI 
/// encryption use the EncryptionHeader structure to specify encryption properties 
/// for an encrypted stream.
/// </summary>
class EncryptionHeader
{
    EncryptionHeader(BinaryReader reader) throws Exception
    {
        int headerSize = reader.readInt32();
        Flags = (/*EncryptionHeaderFlags*/int)reader.readInt32();
        SizeExtra = reader.readInt32();
        AlgId = (/*AlgorithmId*/int)reader.readInt32();
        AlgIdHash = (/*AlgorithmIdHash*/int)reader.readInt32();
        KeySize = reader.readInt32();
        ProviderType = reader.readInt32();
        // Skip unused reserved.
        reader.readInt32();
        // Skip unused reserved.
        reader.readInt32();

        final int FIXED_HEADER_SIZE = 32;
        CspName = OfficeCryptoUtil.readWChar(reader, headerSize - FIXED_HEADER_SIZE);
    }

    /// <summary>
    /// An EncryptionHeaderFlags structure that specifies properties of the encryption 
    /// algorithm used as specified in section 2.3.1.
    /// </summary>
    /*EncryptionHeaderFlags*/int Flags;

    /// <summary>
    /// Reserved, MUST be 0x00000000.
    /// </summary>
    int SizeExtra;

    /// <summary>
    /// A signed integer that specifies the encryption algorithm.
    /// </summary>
    /*AlgorithmId*/int AlgId;

    /// <summary>
    /// A signed integer that specifies the hashing algorithm in concert with the Flags.fExternal bit
    /// </summary>
    /*AlgorithmIdHash*/int AlgIdHash;

    /// <summary>
    /// An unsigned integer that specifies the number of bits in the encryption key. MUST be a multiple of 8.
    /// </summary>
    int KeySize;

    /// <summary>
    /// An implementation specified value which corresponds to constants accepted by the specified CSP.
    /// </summary>
    int ProviderType;

    /// <summary>
    /// A null-terminated Unicode string that specifies the CSP name.
    /// </summary>
    String CspName;
}

