// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import com.aspose.ms.java.logging.Log;
import com.aspose.words.FipsUnapprovedOperationException;
import org.bouncycastle.crypto.OutputDigestCalculator;
import org.bouncycastle.crypto.Parameters;
import org.bouncycastle.crypto.fips.FipsParameters;
import org.bouncycastle.crypto.fips.FipsSHS;
import org.bouncycastle.crypto.general.GeneralParameters;
import org.bouncycastle.crypto.general.SecureHash;

import java.io.IOException;

/// <summary>
/// This class wraps a BouncyCastle's IDigest object and exposes Aspose.Crypto IDigest interface so we don't pass around a BouncyCastle object in AW code.
/// </summary>
//[JavaManual("Platform abstraction for cryptography library. Manual porting by design.")]
class DigestWrapperPal implements IDigest
{
    DigestWrapperPal(int digestAlgorithm)
    {
        Parameters parameters = getParameters(digestAlgorithm);
        if (parameters instanceof FipsParameters)
            mWrapped = new FipsSHS.OperatorFactory<FipsSHS.Parameters>().createOutputDigestCalculator((FipsSHS.Parameters) parameters);
        else
        {
            if (CryptoUtilPal.isInFipsMode())
                throw new FipsUnapprovedOperationException("An attempt to open a file which uses " + getDigestAlgorithm(digestAlgorithm).getName() + " algorithm. This algorithm is not on the FIPS approved algorithms list.");

            mWrapped = new SecureHash.OperatorFactory().createOutputDigestCalculator((GeneralParameters) parameters);
        }
    }

    public void blockUpdate(byte[] input, int inOff, int length)
    {
        mWrapped.getDigestStream().update(input, inOff, length);
    }

    public int doFinal(byte[] output, int outOff)
    {
        try
        {
            mWrapped.getDigestStream().flush();
            mWrapped.getDigestStream().close();
        }
        catch (IOException e)
        {
            Log.warn(e);
        }

        byte[] digest = mWrapped.getDigest();
        System.arraycopy(digest, 0, output, outOff, digest.length);
        return digest.length;
    }

    public int getDigestSize()
    {
        return mWrapped.getDigestSize();
    }

    public void reset()
    {
        mWrapped.reset();
    }

    public static Parameters getParameters(int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_1:
                return FipsSHS.SHA1;

            case DigestAlgorithm.SHA_224:
                return FipsSHS.SHA224;

            case DigestAlgorithm.SHA_256:
                return FipsSHS.SHA256;

            case DigestAlgorithm.SHA_384:
                return FipsSHS.SHA384;

            case DigestAlgorithm.SHA_512:
                return FipsSHS.SHA512;

            case DigestAlgorithm.MD_5:
                return SecureHash.MD5;

            default:
                throw new IllegalStateException("Unsupported hash algorithm: " + digestAlgorithm);
        }
    }

    public static org.bouncycastle.crypto.DigestAlgorithm getDigestAlgorithm(int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_1:
                return FipsSHS.Algorithm.SHA1;

            case DigestAlgorithm.SHA_224:
                return FipsSHS.Algorithm.SHA224;

            case DigestAlgorithm.SHA_256:
                return FipsSHS.Algorithm.SHA256;

            case DigestAlgorithm.SHA_384:
                return FipsSHS.Algorithm.SHA384;

            case DigestAlgorithm.SHA_512:
                return FipsSHS.Algorithm.SHA512;

            case DigestAlgorithm.MD_5:
                return SecureHash.Algorithm.MD5;

            default:
                throw new IllegalStateException("Unsupported hash algorithm: " + digestAlgorithm);
        }
    }

    private /*final*/ OutputDigestCalculator mWrapped;
}


