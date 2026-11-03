// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.CryptoUtilPal;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.StreamUtil;
import com.aspose.ms.System.BitConverter;
import com.aspose.ArrayUtil;
import com.aspose.IMac;
import com.aspose.BlockCipherFactory;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.IDigest;
import com.aspose.BlockCipher;


/// <summary>
/// Decrypt document encrypted using agile encryption.
/// Has few limitation - only one PasswordKeyEncryptor, only AES is supported as cipher algorithm and only SHA1 supported as hashing algorithm. 
/// Seems that is enough to decrypt documents encrypted by Word2010.
/// 
/// Original source code can be found at http://s3downloads.lyquidity.com/OoXmlCrypto/OoXmlAgileCrypto3.cs - many thanks to author.
/// </summary>
class Ecma376AgileEncryption implements IEcma376Encryption
{
    public void decrypt(Stream encryptionInfoStream, Stream encryptedStream, Stream stream, String password) throws Exception
    {
        // Skip EncryptionVersionInfo and copy of the Flags stored in the EncryptionHeader field.
        encryptionInfoStream.setPosition(8);
        BinaryReader reader = new BinaryReader(encryptionInfoStream);

        mEncryptionInfo = new EncryptionInfo(reader);

        if (!"AES".equals(getPkeData().getCipherAlgorithm()))
            throw new IllegalStateException("Unsupported cipher algorithm.");

        initAlgorithms(password, getPkeData().getHashAlgorithm(), getKeyData().getHashAlgorithm());

        if (!verifyPassword())
            throw new IncorrectPasswordException("The document password is incorrect.");

        if (!verifyIntegrity(encryptedStream))
            throw new FileCorruptedException("The document integrity is failed.");

        byte[] decryptedKeyValue = decryptWithBlockKey(getPkeData().getSaltValue(), getPkeData().getEncryptedKeyValue(), G_KEY_VALUE_BLOCK_KEY);

        decryptStream(encryptedStream, stream, decryptedKeyValue);
    }

    public void encrypt(Stream encryptionInfo, Stream stream, Stream encryptedStream, String password)
    {
        throw new UnsupportedOperationException();
    }

    private void initAlgorithms(String password, String pkeHashName, String hashName)
    {
        mPkeHashAlg = CryptoUtilPal.createHashAlgorithm(pkeHashName);
        mHashAlg = CryptoUtilPal.createHashAlgorithm(hashName);
        mAgileKey = createAgileKey(password, getPkeData().getSaltValue(), getPkeData().getSpinCount());
    }

    /// <summary>
    /// Decrypts encrypted stream.
    /// </summary>
    private void decryptStream(Stream srcStream, Stream dstStream, byte[] decryptedKeyValue) throws Exception
    {
        Debug.assertTrue(srcStream != null);
        Debug.assertTrue(dstStream != null);

        srcStream.setPosition(0);
        dstStream.setPosition(0);

        BinaryReader reader = new BinaryReader(srcStream);
        long actualLength = reader.readInt64();

        // SPEED We know what the decrypted stream size will be, so let's allocate upfront to
        // save multiple allocations. But allocate a bit more to accommodate for decryptor block size.
        dstStream.setLength(MathUtil.roundUp(actualLength, 512));

        byte[] segment = new byte[4096];

        int segmentIndex = 0;
        while (srcStream.getPosition() < srcStream.getLength())
        {
            int segmentSize = Math.min((int)(srcStream.getLength() - srcStream.getPosition()), 4096);
            StreamUtil.read(srcStream, segment, 0, segmentSize);

            byte[] iv = computeHash(mHashAlg, getKeyData().getSaltValue(), BitConverter.getBytes(segmentIndex), getKeyData().getBlockSize(), (byte) 0x36);
            byte[] decryptedData = decrypt(decryptedKeyValue, iv, segment);

            dstStream.write(decryptedData, 0, segmentSize);
            segmentIndex++;
        }

        // Set the actual length because it can be smaller due to encryption padding at the end.
        dstStream.setLength(actualLength);
        dstStream.setPosition(0);
    }

    /// <summary>
    /// Verifies password.
    /// </summary>
    private boolean verifyPassword()
    {
        byte[] salt = getPkeData().getSaltValue();
        int hashSize = getPkeData().getHashSize();

        byte[] decryptedHash = pad(decryptWithBlockKey(salt, getPkeData().getEncryptedVerifierHashValue(), G_VERIFIER_HASH_VALUE_BLOCK_KEY), hashSize, (byte) 0x00);
        byte[] inputHash = computeHash(mPkeHashAlg, decryptWithBlockKey(salt, getPkeData().getEncryptedVerifierHashInput(), G_VERIFIER_HASH_INPUT_BLOCK_KEY));
        return ArrayUtil.isArrayEqual(decryptedHash, inputHash);
    }

    /// <summary>
    /// Verifies data integrity.
    /// </summary>
    private boolean verifyIntegrity(Stream srcStream)
    {
        byte[] salt = getPkeData().getSaltValue();
        byte[] keySalt = getKeyData().getSaltValue();
        int keyHashSize = getKeyData().getHashSize();
        int keyBlockSize = getKeyData().getBlockSize();

        byte[] decryptedKey = decryptWithBlockKey(salt, getPkeData().getEncryptedKeyValue(), G_KEY_VALUE_BLOCK_KEY);
        byte[] ivDataIntegritySalt = computeHash(mHashAlg, keySalt, G_DATA_INTEGRITY_SALT_BLOCK_KEY, keyBlockSize, (byte) 0x00);

        IMac keyDataHashAlg = CryptoUtilPal.createHMac(getKeyData().getHashAlgorithm());
        keyDataHashAlg.init(decryptAndPad(decryptedKey, ivDataIntegritySalt, getKeyData().getEncryptedHmacKey(), keyHashSize, (byte) 0x00));

        //TODO 0 RK new KeyParameter(DecryptAndPad(decryptedKey, ivDataIntegritySalt, KeyData.EncryptedHmacKey, keyHashSize, 0x00))
        byte[] dataIntegrityHash = computeHash(keyDataHashAlg, srcStream);

        // Decrypt the hash generated by the encryptor.
        byte[] ivDataIntegrityHash = computeHash(mHashAlg, keySalt, G_DATA_INTEGRITY_HMAC_VALUE_BLOCK_KEY, keyBlockSize, (byte) 0x00);
        byte[] decryptedDataIntegrityHmac = decryptAndPad(decryptedKey, ivDataIntegrityHash, getKeyData().getEncryptedHmacValue(), keyHashSize, (byte) 0x00);

        return ArrayUtil.isArrayEqual(dataIntegrityHash, decryptedDataIntegrityHmac);
    }

    private byte[] decryptWithBlockKey(byte[] ivBytes, byte[] sourceBytes, byte[] block)
    {
        return decrypt(computeBlockKey(block), ivBytes, sourceBytes);
    }

    private byte[] decryptAndPad(byte[] keyBytes, byte[] ivBytes, byte[] sourceBytes, int size, byte padding)
    {
        return pad(decrypt(keyBytes, ivBytes, sourceBytes), size, padding);
    }

    /// <summary>
    /// Decrypts bytes using block key and iv.
    /// </summary>
    private byte[] decrypt(byte[] keyBytes, byte[] ivBytes, byte[] sourceBytes)
    {
        // Generate encryptor from the existing key bytes and initialization 
        // vector. Key size will be defined based on the number of the key bytes.
        mAlgorithm = BlockCipherFactory.createRijndaelDecryptor(getPkeData().getCipherChaining(), getPkeData().getBlockSize(), keyBytes, ivBytes);
        //  Since at this point we don't know what the size of decrypted data
        //  will be, allocate the buffer long enough to hold ciphertext;
        //  plaintext is never longer than ciphertext.
        byte[] decryptedBytes = new byte[sourceBytes.length];
        int offset = mAlgorithm.processBytesViaCipherStream(sourceBytes, 0, sourceBytes.length, decryptedBytes, 0);
        mAlgorithm.doFinal(decryptedBytes, offset);
    
        return decryptedBytes;
    }

    /// <summary>
    /// Creates base key.
    /// </summary>
    /// <remarks>Consider refactoring with Ecma376Decryptor.</remarks>
    private byte[] createAgileKey(String password, byte[] salt, int spinCount)
    {
        byte[] passwordBytes = Encoding.getUnicode().getBytes(password != null ? password : "");

        // H(0) = H(salt, password);
        byte[] hash = computeHash(mPkeHashAlg, salt, passwordBytes);

        byte[] iterationBytes = new byte[hash.length + 4];
        for (int i = 0; i < spinCount; i++)
        {
            // Generate each hash in turn
            // H(n) = H(i, H(n-1))
            ArrayUtil.writeUInt32ToByteArray(i, iterationBytes, 0);
            System.arraycopy(hash, 0, iterationBytes, 4, hash.length);

            hash = computeHash(mPkeHashAlg, iterationBytes);
        }

        return (hash);
    }

    private byte[] computeBlockKey(byte[] blockKey)
    {
        return computeHash(mPkeHashAlg, mAgileKey, blockKey, getPkeData().getKeyBits() >> 3, (byte) 0x36);
    }

    /// <summary>
    /// Hashes data + block bytes. Resize and pad.
    /// </summary>
    private static byte[] computeHash(IDigest hashAlg, byte[] data, byte[] block, int size, byte padding)
    {
        return pad(computeHash(hashAlg, data, block), size, padding);
    }

    /// <summary>
    /// Hashes data + block bytes.
    /// </summary>
    private static byte[] computeHash(IDigest hashAlg, byte[] data, byte[] block)
    {
        byte[] array = new byte[data.length + block.length];

        System.arraycopy(data, 0, array, 0, data.length);
        System.arraycopy(block, 0, array, data.length, block.length);

        return computeHash(hashAlg, array);
    }

    /// <summary>
    /// Resize array to given size and pads new array elements.
    /// </summary>
    private static byte[] pad(byte[] data, int size, byte padding)
    {
        byte[] newData = new byte[size];

        for (int i = 0; i < size; i++)
            newData[i] = (i < Math.min(size, data.length)) ? data[i] : padding;

        return newData;
    }

    /// <summary>
    /// Compute hash with the given implementation of IDigest (SHA1, SHAXXX).
    /// </summary>
    private static byte[] computeHash(IDigest alg, byte[] data)
    {
        alg.blockUpdate(data, 0, data.length);
        byte[] digest = new byte[alg.getDigestSize()];
        alg.doFinal(digest, 0);
        return digest;
    }

    /// <summary>
    /// Compute hash with the given implementation of HMac.
    /// </summary>
    private static byte[] computeHash(IMac keyDataHashAlg, Stream encryptedStream)
    {
        byte[] source = StreamUtil.copyStreamToByteArray(encryptedStream);
        return keyDataHashAlg.computeHash(source);
    }

    /// <summary>
    /// Provides access to base part of encryption info.
    /// </summary>
    private EncryptionInfo getKeyData() { return mEncryptionInfo; }

    /// <summary>
    /// Provides access to PasswordKeyEncryptor part of encryption info.
    /// </summary>
    private PasswordKeyEncryptor getPkeData() { return mEncryptionInfo.getPasswordKeyEncryptor(); }

    private EncryptionInfo mEncryptionInfo;
    private BlockCipher mAlgorithm;
    private IDigest mPkeHashAlg;
    private IDigest mHashAlg;
    private byte[] mAgileKey;

    // These block keys are defined in the specification document
    private static /*final*/ byte[] G_VERIFIER_HASH_INPUT_BLOCK_KEY = new byte[] { (byte) 0xfe, (byte) 0xa7, (byte) 0xd2, (byte) 0x76, (byte) 0x3b, (byte) 0x4b, (byte) 0x9e, (byte) 0x79 };
    private static /*final*/ byte[] G_VERIFIER_HASH_VALUE_BLOCK_KEY = new byte[] { (byte) 0xd7, (byte) 0xaa, (byte) 0x0f, (byte) 0x6d, (byte) 0x30, (byte) 0x61, (byte) 0x34, (byte) 0x4e };
    private static /*final*/ byte[] G_KEY_VALUE_BLOCK_KEY = new byte[] { (byte) 0x14, (byte) 0x6e, (byte) 0x0b, (byte) 0xe7, (byte) 0xab, (byte) 0xac, (byte) 0xd0, (byte) 0xd6 };
    private static /*final*/ byte[] G_DATA_INTEGRITY_SALT_BLOCK_KEY = new byte[] { (byte) 0x5f, (byte) 0xb2, (byte) 0xad, (byte) 0x01, (byte) 0x0c, (byte) 0xb9, (byte) 0xe1, (byte) 0xf6 };
    private static /*final*/ byte[] G_DATA_INTEGRITY_HMAC_VALUE_BLOCK_KEY = new byte[] { (byte) 0xa0, (byte) 0x67, (byte) 0x7f, (byte) 0x02, (byte) 0xb2, (byte) 0x2c, (byte) 0x84, (byte) 0x33 };
}

