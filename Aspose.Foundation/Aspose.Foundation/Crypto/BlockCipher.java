package com.aspose;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.JavaOnlyStreamUtil;
import com.aspose.ms.java.IO.NetOutputStream;
import com.aspose.ms.java.logging.Log;
import org.bouncycastle.crypto.CipherOutputStream;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputDecryptor;
import org.bouncycastle.crypto.OutputEncryptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/// <summary>
/// This class is a Facade which provides an simplified interface to BouncyCastle's IBufferedCipher and IBlockCipher.
/// This class can be instantiated via BlockCipherFactory.
/// Not all the methods from IBufferedCipher and IBlockCipher are implemented.
/// Extend this class with new methods if needed.
/// </summary>
public class BlockCipher
{
    // Common methods

    public int getBlockSize()
    {
        return mBlockSize;
    }

    public void reset() { }

    // IBlockCipher methods, add new if needed

    public void processBlock(byte[] inBytes, int inOff, byte[] outBytes, int outOff)
    {
        ByteArrayOutputStream resultBytes = new ByteArrayOutputStream();
        CipherOutputStream cipherStream = getCipherOutputStream(resultBytes);
        cipherStream.update(inBytes, inOff, getBlockSize());
        try
        {
            cipherStream.flush();
        }
        catch (IOException e)
        {
            Log.error(e);
        }

        byte[] result = resultBytes.toByteArray();
        System.arraycopy(result, 0, outBytes, outOff, result.length);
    }

    public void processData(byte[] inBytes, byte[] outBytes)
    {
        if (inBytes.length % mBlockSize != 0)
            throw new IllegalArgumentException("Data size is not rounded to the cipher block size.");
        if (outBytes.length < inBytes.length)
            throw new IllegalArgumentException("Not enough bytes in output array");

        int blockSize = mBlockSize;
        for (int i = 0; i < inBytes.length; i += blockSize)
            processBlock(inBytes, i, outBytes, i);
    }

    // IBufferedCipher methods, add new if needed

    /// <summary>
    /// Encrypts the srcStream and copy it into the dstStream.
    /// </summary>
    public void doTransform(Stream srcStream, Stream dstStream) throws Exception
    {
        OutputStream outStream = new NetOutputStream(dstStream);
        CipherOutputStream cipherOutputStream = getCipherOutputStream(outStream);
        JavaOnlyStreamUtil.copyStream(srcStream, cipherOutputStream, 128);
    }

    public int getOutputSize(int inLen)
    {
        if (mEncryptor != null)
            return mEncryptor.getMaxOutputSize(inLen);

        else if (mDecryptor != null)
            return mDecryptor.getMaxOutputSize(inLen);


        throw new IllegalStateException("Either Encryptor or Decryptor must exist");
    }

    public byte[] processBytes(byte[] input, int inOff, int length)
    {
        if(mResultBytes == null)
        {
            mResultBytes = new ByteArrayOutputStream();
        }

        mCipherStream = getCipherOutputStream(mResultBytes);
        mCipherStream.update(input, inOff, length);

        return null;
    }

    public int processBytes(byte[] inputBytes, byte[] outputBytes, int v) throws InvalidCipherTextException
    {
        return processBytes(inputBytes, 0, inputBytes.length, outputBytes, v);
    }

    public int processBytes(byte[] input, int inOff, int inLen, byte[] outBytes, int i)
    {
        ByteArrayOutputStream resultBytes = new ByteArrayOutputStream();
        CipherOutputStream cipherStream = getCipherOutputStream(resultBytes);
        cipherStream.update(input, inOff, inLen);
        try
        {
            cipherStream.close();
        }
        catch (IOException e)
        {
            Log.error(e);
        }

        byte[] result = resultBytes.toByteArray();
        System.arraycopy(result, 0, outBytes, i, result.length);

        return result.length;
    }

    public int processBytesViaCipherStream(byte[] sourceBytes, int i, int length, byte[] decryptedBytes, int i1)
    {
        return processBytes(sourceBytes, i, length, decryptedBytes, i1);
    }

    public byte[] doFinal()
    {
        try
        {
            mCipherStream.close();
        }
        catch (IOException e)
        {
            Log.error(e);
        }

        return mResultBytes.toByteArray();
    }

    public int doFinal(byte[] outBytes, int pos)
    {
        // new API does doFinal automatically from cipherStream.close();
        return 0;
    }

    private CipherOutputStream getCipherOutputStream(OutputStream stream)
    {
        if (stream == null)
            throw new IllegalArgumentException("Stream cannot be null");

        if (mEncryptor != null)
            return mEncryptor.getEncryptingStream(stream);

        else if (mDecryptor != null)
            return mDecryptor.getDecryptingStream(stream);

        throw new IllegalStateException("Either Encryptor or Decryptor must exist");
    }

    BlockCipher(OutputDecryptor decryptor, int blockSize)
    {
        if (decryptor == null)
            throw new IllegalArgumentException("decryptor cannot be null");

        mDecryptor = decryptor;
        mBlockSize = blockSize;
    }

    BlockCipher(OutputEncryptor encryptor, int blockSize)
    {
        if (encryptor == null)
            throw new IllegalArgumentException("decryptor cannot be null");

        mEncryptor = encryptor;
        mBlockSize = blockSize;
    }


    private OutputEncryptor mEncryptor;
    private OutputDecryptor mDecryptor;
    private CipherOutputStream mCipherStream;
    private ByteArrayOutputStream mResultBytes;
    private final int mBlockSize;
}
