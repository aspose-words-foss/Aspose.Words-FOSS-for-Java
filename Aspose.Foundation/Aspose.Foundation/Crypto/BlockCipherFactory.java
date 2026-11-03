package com.aspose;

import com.aspose.words.FipsUnapprovedOperationException;
import org.bouncycastle.crypto.SymmetricSecretKey;
import org.bouncycastle.crypto.fips.FipsAES;
import org.bouncycastle.crypto.general.Blowfish;

public class BlockCipherFactory
{
    public static BlockCipher createRijndaelDecryptor(String chainingMode, int blockSize, byte[] keyBytes, byte[] ivBytes)
    {
        boolean useChaining = "ChainingModeCFB".equalsIgnoreCase(chainingMode);
        FipsAES.Parameters cipherParams = useChaining ? FipsAES.CFB128.withIV(ivBytes) : FipsAES.CBC.withIV(ivBytes);
        SymmetricSecretKey keyParameters = new SymmetricSecretKey(FipsAES.ALGORITHM, keyBytes);
        FipsAES.OperatorFactory operatorFactory = new FipsAES.OperatorFactory();

        return new BlockCipher(operatorFactory.createOutputDecryptor(keyParameters, cipherParams), blockSize);
    }

    /// <summary>
    /// Creates Rijndael cipher based on key's length.
    /// Initialized for decrypting.
    /// Supports lengths 128, 160, 192, 224 or 256.
    /// </summary>
    public static BlockCipher createRijndaelDecryptor(byte[] generateKey)
    {
        //  BufferedBlockCipher cipher = new BufferedBlockCipher(new RijndaelEngine(generateKey.length << 3));
        //  cipher.init(false, new KeyParameter(generateKey));
        //  return cipher;
        SymmetricSecretKey secretKey = new SymmetricSecretKey(FipsAES.ALGORITHM, generateKey);
        return new BlockCipher(new FipsAES.OperatorFactory().createOutputDecryptor(secretKey, FipsAES.ECB), generateKey.length >> 3);
    }

    /// <summary>
    /// Creates Rijndael cipher based on key's length.
    /// Initialized for encrypting.
    /// Supports lengths 128, 160, 192, 224 or 256.
    /// </summary>
    public static BlockCipher createRijndaelEncryptor(byte[] generateKey)
    {
        // BufferedBlockCipher cipher = new BufferedBlockCipher(new RijndaelEngine(generateKey.length << 3));
        // cipher.init(true, new KeyParameter(generateKey));
        // return cipher;
        SymmetricSecretKey secretKey = new SymmetricSecretKey(FipsAES.ALGORITHM, generateKey);
        return new BlockCipher(new FipsAES.OperatorFactory().createOutputEncryptor(secretKey, FipsAES.ECB), generateKey.length >> 3);
    }

    public static BlockCipher createBlowfish(boolean isForEncryption, byte[] key, byte[] iv)
    {
        if (CryptoUtilPal.isInFipsMode())
            throw new FipsUnapprovedOperationException("An attempt to open an ODT file which uses the Blowfish algorithm. This algorithm is not on the FIPS-approved algorithm list.");

        Blowfish.Parameters ivParameters = Blowfish.CFB64.withIV(iv);
        SymmetricSecretKey keyParameters = new SymmetricSecretKey(Blowfish.ALGORITHM, key);
        Blowfish.OperatorFactory f = new Blowfish.OperatorFactory();

        BlockCipher cipher;
        if (isForEncryption)
            cipher = new BlockCipher(f.createOutputEncryptor(keyParameters, ivParameters), 8); // in bytes because CFB64
        else
            cipher = new BlockCipher(f.createOutputDecryptor(keyParameters, ivParameters), 8);

        return cipher;
    }

    /// <summary>
    /// Creates AES cipher in CBC mode with ANSI X9.23 padding.
    /// </summary>
    public static BlockCipher createAesCbcX923Cipher(boolean isForEncryption, byte[] keyBytes, byte[] iv)
    {
        // .NET blocksize=16  AES/CBC
        // blockCipher.init(isForEncryption, keyBytes, FipsAES.CBCwithX923.withIV(iv));

        SymmetricSecretKey keyParameters = new SymmetricSecretKey(FipsAES.ALGORITHM, keyBytes);
        FipsAES.Parameters parameters = FipsAES.CBCwithX923.withIV(iv);
        FipsAES.OperatorFactory operatorFactory = new FipsAES.OperatorFactory();

        BlockCipher blockCipher;
        if (isForEncryption)
            blockCipher = new BlockCipher(operatorFactory.createOutputEncryptor(keyParameters, parameters), 16);
        else
            blockCipher = new BlockCipher(operatorFactory.createOutputDecryptor(keyParameters, parameters), 16);

        return blockCipher;
    }

    /// <summary>
    /// Creates AES cipher in CBC mode with PKCS#7 padding without initialization vector.
    /// </summary>
    public static BlockCipher createAesCbcPkcs7NoIvEncryptor(byte[] keyBytes)
    {
        SymmetricSecretKey keyParameters = new SymmetricSecretKey(FipsAES.ALGORITHM, keyBytes);
        FipsAES.Parameters parameters = FipsAES.CBCwithPKCS7;
        FipsAES.OperatorFactory operatorFactory = new FipsAES.OperatorFactory();

        return new BlockCipher(operatorFactory.createOutputEncryptor(keyParameters, parameters), 16);
    }

    /// <summary>
    /// Creates AES cipher in CBC mode without padding.
    /// </summary>
    public static BlockCipher createAesCbcNoPaddingEncryptor(byte[] keyBytes, byte[] iv)
    {
        SymmetricSecretKey keyParameters = new SymmetricSecretKey(FipsAES.ALGORITHM, keyBytes);
        FipsAES.Parameters parameters = FipsAES.CBC.withIV(iv);
        FipsAES.OperatorFactory operatorFactory = new FipsAES.OperatorFactory();

        return new BlockCipher(operatorFactory.createOutputEncryptor(keyParameters, parameters), 16);
    }
}
