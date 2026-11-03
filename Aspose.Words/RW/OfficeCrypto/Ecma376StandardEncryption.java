// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.IDigest;
import com.aspose.CryptoUtilPal;
import com.aspose.DigestAlgorithm;
import com.aspose.BlockCipher;
import com.aspose.BlockCipherFactory;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.Debug;
import com.aspose.HashUtil;
import com.aspose.ArrayUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.IRandomGenerator;


/// <summary>
/// Implements ECMA-376 Standard Encryption algorithm. 
/// </summary>
class Ecma376StandardEncryption implements IEcma376Encryption
{
    /// <summary>
    /// Decrypts stream using ECMA-376 Standard Encryption algorithm.
    /// </summary>
    public void decrypt(Stream encryptionInfoStream, Stream encryptedStream, Stream dstStream, String password) throws Exception
    {
        // Skip EncryptionVersionInfo and copy of the Flags stored in the EncryptionHeader field.
        encryptionInfoStream.setPosition(8);
        BinaryReader reader = new BinaryReader(encryptionInfoStream);

        EncryptionHeader header = new EncryptionHeader(reader);
        EncryptionVerifier verifier = new EncryptionVerifier(reader, true);

        checkEncryptionHeader(header);

        IDigest digest = CryptoUtilPal.createHashAlgorithm(DigestAlgorithm.SHA_1);

        BlockCipher decryptor =
            BlockCipherFactory.createRijndaelDecryptor(generateKey(header.KeySize, digest, password, verifier.Salt));

        checkEncryptionVerifier(decryptor, verifier);

        decryptStream(decryptor, encryptedStream, dstStream);
    }

    /// <summary>
    /// Encrypts stream using ECMA-376 Standard Encryption algorithm. Writes EncryptionInfo stream.
    /// </summary>
    public void encrypt(Stream encryptionInfo, Stream srcStream, Stream encryptedStream, String password) throws Exception
    {
        BinaryWriter writer = new BinaryWriter(encryptionInfo);

        writeEncryptionHeader(writer);

        byte[] salt = getRandomBytes(16);
        BlockCipher encryptor = BlockCipherFactory.createRijndaelEncryptor(generateKey(0x80, CryptoUtilPal.createHashAlgorithm(DigestAlgorithm.SHA_1), password, salt));

        EncryptionVerifier verifier = computeEncryptionVerifier(encryptor, salt);
        verifier.write(writer);

        encryptStream(encryptor, srcStream, encryptedStream);
    }

    /// <summary>
    /// Verifies that encryption specified in header is supported.
    /// </summary>
    private static void checkEncryptionHeader(EncryptionHeader header) throws Exception
    {
        if (header.Flags != (EncryptionHeaderFlags.CRYPTO_API | EncryptionHeaderFlags.AES))
            throw new IllegalStateException("Unexpected encryption flags.");

        if ((header.AlgId != AlgorithmId.AES_128) &&
            (header.AlgId != AlgorithmId.AES_192) &&
            (header.AlgId != AlgorithmId.AES_256))
            throw new IllegalStateException("Unexpected encryption algorithm id.");

        if (header.AlgIdHash != AlgorithmIdHash.SHA_1)
            throw new IllegalStateException("Unexpected encryption algorithm hash id.");
    }

    /// <summary>
    /// Validates encryption password verifier.
    /// </summary>
    private static void checkEncryptionVerifier(BlockCipher cryptoTransform, EncryptionVerifier encryptionVerifier) throws Exception
    {
        Debug.assertTrue(cryptoTransform != null);

        //2. Decrypt the EncryptedVerifier field of the EncryptionVerifier 
        // structure as specified in section 2.3.3, and generated as specified in section 2.3.4.8. 
        // to obtain the Verifier value. 
        byte[] verifier = doFinal(cryptoTransform,
            encryptionVerifier.EncryptedVerifier, 0, encryptionVerifier.EncryptedVerifier.length);

        //3. Decrypt the EncryptedVerifierHash field of the EncryptionVerifier structure to obtain 
        // the hash of the Verifier value. 
        byte[] verifierHash = doFinal(cryptoTransform,
            encryptionVerifier.EncryptedVerifierHash, 0, encryptionVerifier.EncryptedVerifierHash.length);

        //4. Calculate the SHA-1 hash value of the Verifier value calculated in step 2.
        byte[] expectedVerifierHash = HashUtil.computeHash(DigestAlgorithm.SHA_1, verifier);

        //5. Compare the results of step 3 and step 4. If the two hash values do not match, the password is incorrect.            
        //The number of bytes used by the decrypted Verifier hash is given by the VerifierHashSize field.
        if (!ArrayUtil.compareBytes(verifierHash, expectedVerifierHash, encryptionVerifier.VerifierHashSize))
            throw new IncorrectPasswordException("The document password is incorrect.");
    }

    /// <summary>
    /// Decrypts the specified stream.
    /// Must be called after the password was verified.
    /// Does not close streams.
    /// </summary>
    /// <param name="decryptor"></param>
    /// <param name="srcStream">Decrypted from position 0 to the end.</param>
    /// <param name="dstStream">Written from position 0. The position is set to 0 when the method returns.</param>
    private static void decryptStream(BlockCipher decryptor, Stream srcStream, Stream dstStream) throws Exception
    {
        Debug.assertTrue(decryptor != null);
        Debug.assertTrue(srcStream != null);
        Debug.assertTrue(dstStream != null);

        srcStream.setPosition(0);
        dstStream.setPosition(0);

        BinaryReader reader = new BinaryReader(srcStream);
        long actualLength = reader.readInt64();

        // SPEED We know what the decrypted stream size will be, so let's allocate upfront to
        // save multiple allocations. But allocate a bit more to accommodate for decryptor block size.
        dstStream.setLength(MathUtil.roundUp(actualLength, 512));

        decryptor.doTransform(srcStream, dstStream);

        // Set the actual length because it can be smaller due to encryption padding at the end.
        dstStream.setLength(actualLength);

        srcStream.setPosition(0);
        dstStream.setPosition(0);
    }

    private EncryptionVerifier computeEncryptionVerifier(BlockCipher encryptor, byte[] salt) throws Exception 
    {
        byte[] verifier = getRandomBytes(16);

        byte[] temp = HashUtil.computeHash(DigestAlgorithm.SHA_1, verifier);
        byte[] verifierHash = new byte[0x20];
        System.arraycopy((temp), 0, (verifierHash), 0, (temp.length));

        EncryptionVerifier ret = new EncryptionVerifier();
        ret.Salt = salt;
        ret.SaltSize = 0x10;
        ret.VerifierHashSize = 0x20;
        ret.EncryptedVerifier = doFinal(encryptor, verifier, 0, verifier.length);
        ret.EncryptedVerifierHash = doFinal(encryptor, verifierHash, 0, verifierHash.length);
        
        return ret;
    }

    /// <summary>
    /// Writes EncryptionHeader structure.
    /// </summary>
    private static void writeEncryptionHeader(BinaryWriter writer) throws Exception
    {
        writer.writeInt32((int)EncryptionVersionInfo.STANDARD_2010);
        writer.writeInt32((int)(EncryptionHeaderFlags.AES | EncryptionHeaderFlags.CRYPTO_API));
        writer.writeInt32(0x8c);
        writer.writeInt32((int)(EncryptionHeaderFlags.AES | EncryptionHeaderFlags.CRYPTO_API));
        writer.writeInt32(0);
        writer.writeInt32(0x0000660E);
        writer.writeInt32(0x00008004);
        writer.writeInt32(0x00000080);
        writer.writeInt32(0x00000018);
        writer.writeInt32(0);
        writer.writeInt32(0);
        OfficeCryptoUtil.writeWChar("Microsoft Enhanced RSA and AES Cryptographic Provider", writer);
    }

    /// <summary>
    /// Decrypts the specified stream.
    /// Must be called after the password was verified.
    /// Does not close streams.
    /// </summary>
    /// <param name="srcStream">Decrypted from position 0 to the end.</param>
    /// <param name="dstStream">Written from position 0. The position is set to 0 when the method returns.</param>
    /// <param name="encryptor">Encryption algorithm.</param>
    private static void encryptStream(BlockCipher encryptor, Stream srcStream, Stream dstStream) throws Exception
    {
        Debug.assertTrue(encryptor != null);
        Debug.assertTrue(srcStream != null);
        Debug.assertTrue(dstStream != null);

        srcStream.setPosition(0);
        dstStream.setPosition(0);

        long actualLength = srcStream.getLength();
        srcStream.setLength(MathUtil.roundUp(srcStream.getLength(), 16));

        BinaryWriter writer = new BinaryWriter(dstStream);
        writer.writeInt64(actualLength);
        
        // SPEED We know what the decrypted stream size will be, so let's allocate upfront to
        // save multiple allocations. But allocate a bit more to accommodate for decryptor block size 
        // considering initial 8-byte size.
        dstStream.setLength(MathUtil.roundUp(actualLength, 16) + 8);

        encryptor.doTransform(srcStream, dstStream);

        srcStream.setPosition(0);
        dstStream.setPosition(0);
    }

    /// <summary>
    /// Generate an encryption key as specified in section 2.3.4.7.
    /// </summary>
    private static byte[] generateKey(int keySize, IDigest digest, String password, byte[] salt)
    {
        byte[] hash = computeKeyHash(digest, password, salt);

        byte[] x1 = computeX(digest, (byte) 0x36, hash);
        byte[] x2 = computeX(digest, (byte) 0x5c, hash);

        // Concatenate X1 with X2 to form X3, which will yield a value twice the length of cbHash.
        byte[] x3 = new byte[x1.length + x2.length];
        System.arraycopy(x1, 0, x3, 0, x1.length);
        System.arraycopy(x2, 0, x3, x1.length, x2.length);

        // Let keyDerived be equal to the first cbRequiredKeyLength bytes of X3.
        byte[] key = new byte[keySize / 8];
        msArray.copy(x3, 0, key, 0, key.length);
        return key;
    }

    private static byte[] computeKeyHash(IDigest digest, String password, byte[] salt)
    {
        if (password == null)
            password = "";

        // The password MUST be provided as an array of Unicode characters.
        // The initial password hash is generated as: H0 = H(salt + password).
        byte[] passwordBytes = Encoding.getUnicode().getBytes(password);
        byte[] initialBytes = new byte[salt.length + passwordBytes.length];
        System.arraycopy(salt, 0, initialBytes, 0, salt.length);
        System.arraycopy(passwordBytes, 0, initialBytes, salt.length, passwordBytes.length);
        byte[] hash = HashUtil.computeHash(digest, initialBytes);

        // The hash is then iterated using the following approach: Hn = H(iterator + Hn-1)
        byte[] iterationBytes = new byte[4 + hash.length];
        for (int iterator = 0; iterator < 50000; iterator++)
        {
            ArrayUtil.writeUInt32ToByteArray(iterator, iterationBytes, 0);
            System.arraycopy(hash, 0, iterationBytes, 4, hash.length);
            hash = HashUtil.computeHash(digest, iterationBytes);
        }

        // The method used to generate the hash data which is the input into the key 
        // derivation algorithm is: Hfinal = H(Hn + block) and the block number MUST be 0x00000000.
        System.arraycopy(hash, 0, iterationBytes, 0, hash.length);
        ArrayUtil.writeUInt32ToByteArray(0, iterationBytes, hash.length);
        return HashUtil.computeHash(digest, iterationBytes);
    }

    private static byte[] computeX(IDigest digest, byte value, byte[] hash)
    {
        // Form a 64-byte buffer by repeating the constant 0x36 64 times.
        byte[] buf = new byte[64];
        for (int i = 0; i < buf.length; i++)
            buf[i] = value;

        // XOR Hfinal into the first cbHash bytes of this buffer.
        for (int i = 0; i < hash.length; i++)
            buf[i] = (byte)((buf[i] & 0xFF) ^ (hash[i] & 0xFF));

        // Compute a hash of the resulting 64-byte buffer using hashing algorithm H. 
        // This will yield a hash value of length cbHash. Let the resultant value be called X1.
        return HashUtil.computeHash(digest, buf);
    }

    /// <summary>
    /// Gets array of random bytes.
    /// </summary>
    private byte[] getRandomBytes(int count)
    {
        byte[] randomBytes = new byte[count];
        mRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    private static byte[] doFinal(BlockCipher cryptoTransform, byte[] input, int inOff,int inLen)
    {
        if (input == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "input");

        int length = cryptoTransform.getOutputSize(inLen);
        byte[] outBytes = new byte[0];
        if (length > 0)
        {
            outBytes = new byte[length];
            int pos = (inLen > 0) ? cryptoTransform.processBytes(input, inOff, inLen, outBytes, 0) : 0;
            try
            {
                pos += cryptoTransform.doFinal(outBytes, pos);
            }
            catch (Exception e)
            {
                // Java throws DataLengthException, IllegalStateException, InvalidCipherTextException
                // .NET doesn't throw anything.
                // In order to make it autoportable I have to wrap DoFinal method here.
                // @exception DataLengthException if there is insufficient space in out for
                // the output, or the input is not block size aligned and should be.
                // @exception IllegalStateException if the underlying cipher is not initialised.
                // @exception InvalidCipherTextException if padding is expected and not found.
                Debug.writeLine("ERROR: Please check that the underlying cipher is initialised. " +
                                "The input is block size aligned. Padding is correct." +
                                "There is not insufficient space in out for the output. Message:" + e.getMessage());
            }

            if (pos < outBytes.length)
            {
                byte[] tmp = new byte[pos];
                msArray.copy(outBytes, 0, tmp, 0, pos);
                outBytes = tmp;
            }
        }
        else
        {
            cryptoTransform.reset();
        }
        return outBytes;
    }

    private /*final*/ IRandomGenerator mRandom = CryptoUtilPal.createRandomGenerator(DigestAlgorithm.SHA_256);
}

