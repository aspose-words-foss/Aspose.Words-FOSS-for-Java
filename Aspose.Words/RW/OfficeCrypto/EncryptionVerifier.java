// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2009 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// 2.3.3 EncryptionVerifier Structure
/// The EncryptionVerifier structure is used by Office Binary Document RC4 CryptoAPI 
/// Encryption and ECMA-376 Document Encryption.
/// </summary>
class EncryptionVerifier
{
    EncryptionVerifier()
    {
    }

    EncryptionVerifier(BinaryReader reader, boolean isAes) throws Exception
    {
        SaltSize = reader.readInt32();
        if (SaltSize != 16)
            throw new IllegalStateException("Unexpected salt size.");

        Salt = reader.readBytes(SaltSize);
        EncryptedVerifier = reader.readBytes(16);
        VerifierHashSize = reader.readInt32();
        EncryptedVerifierHash = reader.readBytes(isAes ? 32 : 20);
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(0x00000010);
        writer.writeBytes(Salt);
        writer.writeBytes(EncryptedVerifier);
        writer.writeInt32(0x00000014);
        writer.writeBytes(EncryptedVerifierHash);
    }

    /// <summary>
    /// An unsigned integer that specifies the size of the Salt field. MUST be 0x00000010.
    /// </summary>
    int SaltSize;

    /// <summary>
    /// An array of bytes that specifies the salt value used during password hash generation. 
    /// MUST NOT be the same data used for the verifier stored encrypted in the EncryptedVerifier field.
    /// </summary>
    byte[] Salt;

    /// <summary>
    /// MUST be the randomly generated Verifier value encrypted using the algorithm 
    /// chosen by the implementation.
    /// </summary>
    byte[] EncryptedVerifier;

    /// <summary>
    /// An unsigned integer that specifies the number of bytes needed to contain the hash 
    /// of the data used to generate the EncryptedVerifier field.
    /// </summary>
    int VerifierHashSize;

    /// <summary>
    /// An array of bytes that contains the encrypted form of the hash of the randomly 
    /// generated Verifier value. The length of the array MUST be the size of the encryption 
    /// block size multiplied by the number of blocks needed to encrypt the hash of the Verifier. 
    /// If the encryption algorithm is RC4, the length MUST be 20 bytes. 
    /// If the encryption algorithm is AES, the length MUST be 32 bytes.
    /// </summary>
    byte[] EncryptedVerifierHash;
}

