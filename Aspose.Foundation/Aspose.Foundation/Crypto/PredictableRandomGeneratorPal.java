// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
package com.aspose;

import org.bouncycastle.crypto.EntropySource;
import org.bouncycastle.crypto.EntropySourceProvider;
import org.bouncycastle.crypto.fips.FipsDRBG;
import org.bouncycastle.crypto.fips.FipsSecureRandom;

import java.security.SecureRandom;

/// <summary>
/// This class wraps a BouncyCastle's IRandomGenerator object and exposes Aspose.Crypto interface so we don't pass around a BouncyCastle object in AW code.
/// </summary>
// [JavaManual("Platform abstraction for cryptography library. Manual porting by design.")]
class PredictableRandomGeneratorPal implements IRandomGenerator
{
    public void nextBytes(byte[] bytes)
    {
        mWrapped.nextBytes(bytes);
    }

    @Override
    public void addSeedMaterial(byte[] seed)
    {
        // intentionally left empty, to prevent changing the sequence.
    }

    PredictableRandomGeneratorPal(int digestAlgorithm)
    {
        final SecureRandom mEntropySource = new SecureRandom();
        mEntropySource.setSeed(1); // make it return the same sequence of byte arrays each JVM starts

        EntropySourceProvider entropySourceProvider = new PredictableEntropySourceProvider(mEntropySource, 256);

        FipsDRBG.Builder drgbBldr = RandomGeneratorWrapperPal.getAlgorithm(digestAlgorithm)
                .fromEntropySource(entropySourceProvider)
                .setSecurityStrength(256)
                .setEntropyBitsRequired(256);

        byte[] nonce = new byte[16];
        mEntropySource.nextBytes(nonce); // the same nonce everytime JVM starts
        mWrapped = drgbBldr.build(nonce, false);
    }

    private final FipsSecureRandom mWrapped;

    private static class PredictableEntropySourceProvider implements EntropySourceProvider
    {
        @Override
        public EntropySource get(int bitsRequired)
        {
            return new EntropySource()
            {
                @Override
                public boolean isPredictionResistant()
                {
                    return false;
                }

                @Override
                public byte[] getEntropy()
                {
                    byte[] bytes = new byte[mEntropySize];
                    mEntropySource.nextBytes(bytes);
                    return bytes;
                }

                @Override
                public int entropySize()
                {
                    return mEntropySize;
                }
            };
        }

        PredictableEntropySourceProvider(SecureRandom source, int entropySize)
        {
            this.mEntropySource = source;
            this.mEntropySize = entropySize;
        }

        private final SecureRandom mEntropySource;
        private final int mEntropySize;
    }

}

