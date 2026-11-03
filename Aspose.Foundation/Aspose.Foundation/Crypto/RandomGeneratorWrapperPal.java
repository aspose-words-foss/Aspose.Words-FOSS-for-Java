// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import org.bouncycastle.crypto.EntropySourceProvider;
import org.bouncycastle.crypto.fips.FipsDRBG;
import org.bouncycastle.crypto.fips.FipsSecureRandom;
import org.bouncycastle.crypto.util.BasicEntropySourceProvider;

import java.security.SecureRandom;
/// <summary>
/// This class wraps a BouncyCastle's IRandomGenerator object and exposes Aspose.Crypto interface so we don't pass around a BouncyCastle object in AW code.
/// </summary>
// [JavaManual("Platform abstraction for cryptography library. Manual porting by design.")]
class RandomGeneratorWrapperPal implements IRandomGenerator
{
    RandomGeneratorWrapperPal(int digestAlgorithm)
    {
        SecureRandom entropySource = new SecureRandom();
        EntropySourceProvider entropySourceProvider = new BasicEntropySourceProvider(entropySource, true);
        FipsDRBG.Builder drgbBldr = getAlgorithm(digestAlgorithm)
                .fromEntropySource(entropySourceProvider)
                .setSecurityStrength(256)
                .setEntropyBitsRequired(256);
        mWrapped = drgbBldr.build(entropySource.generateSeed(16), true);
    }

    public void nextBytes(byte[] bytes)
    {
        mWrapped.nextBytes(bytes);
    }

    @Override
    public void addSeedMaterial(byte[] seed)
    {
        mWrapped.setSeed(seed);
    }

    static FipsDRBG.Base getAlgorithm(int digestAlgorithm)
    {
        switch (digestAlgorithm)
        {
            case DigestAlgorithm.SHA_1:
                return FipsDRBG.SHA1;

            case DigestAlgorithm.SHA_224:
                return FipsDRBG.SHA224;

            case DigestAlgorithm.SHA_256:
                return FipsDRBG.SHA256;

            case DigestAlgorithm.SHA_384:
                return FipsDRBG.SHA384;

            case DigestAlgorithm.SHA_512:
                return FipsDRBG.SHA512;

            case DigestAlgorithm.MD_2:
            case DigestAlgorithm.MD_4:
            case DigestAlgorithm.MD_5:
            default:
                throw new IllegalStateException("Unsupported hash algorithm: " + digestAlgorithm);
        }
    }

    private final FipsSecureRandom mWrapped;
}

