// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import org.bouncycastle.crypto.OutputMACCalculator;
import org.bouncycastle.crypto.Parameters;
import org.bouncycastle.crypto.SymmetricKey;
import org.bouncycastle.crypto.SymmetricSecretKey;
import org.bouncycastle.crypto.fips.FipsSHS;

import java.io.IOException;


/// <summary>
/// This class wraps a BouncyCastle's IMac object and exposes Aspose.Crypto IMac interface so we don't pass around a BouncyCastle object in AW code.
/// </summary>
//[JavaManual("Platform abstraction for cryptography library. Manual porting by design.")]
public class MacWrapperPal implements IMac
{
    public MacWrapperPal(int digestAlgorithm)
    {
        mDigestAlgorithm = digestAlgorithm;
    }

    /// <summary>
    /// Initializes underlying MAC with the specified key.
    /// </summary>
    public void init(byte[] key)
    {
        Parameters authParameters = getParameters(mDigestAlgorithm);
        SymmetricKey aesKey = new SymmetricSecretKey(authParameters, key);
        mMac = new FipsSHS.MACOperatorFactory().createOutputMACCalculator(aesKey, (FipsSHS.AuthParameters) authParameters);
    }

    /// <summary>
    /// Computes hash for the specified content.
    /// </summary>
    public byte[] computeHash(byte[] content)
    {
        try
        {
            mMac.getMACStream().update(content, 0, content.length);
            mMac.getMACStream().flush();
            mMac.getMACStream().close();
        }
        catch (IOException e)
        {
            Log.warn(e);
        }

        return mMac.getMAC();
    }

    /// <summary>
    /// Gets MAC size.
    /// </summary>
    public int getSize() { return mMac.getMACSize(); }

    private static FipsSHS.AuthParameters getParameters(int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_1:
                return FipsSHS.SHA1_HMAC;

            case DigestAlgorithm.SHA_224:
                return FipsSHS.SHA224_HMAC;

            case DigestAlgorithm.SHA_256:
                return FipsSHS.SHA256_HMAC;

            case DigestAlgorithm.SHA_384:
                return FipsSHS.SHA384_HMAC;

            case DigestAlgorithm.SHA_512:
                return FipsSHS.SHA512_HMAC;

            default:
                throw new IllegalStateException("Unsupported hash algorithm: " + digestAlgorithm);
        }

    }

    private final int mDigestAlgorithm;
    private /*final*/ OutputMACCalculator mMac;
}

