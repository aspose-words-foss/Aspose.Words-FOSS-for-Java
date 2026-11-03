// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2010 by Roman Korchagin
// 21/10/2015 by Vyacheslav Durin
package com.aspose.ms.System;

import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Copycat of .NET Random class.
 * <p/>
 * .NET version represents a pseudo-random number generator, which is a device that produces a sequence of numbers that meet certain statistical requirements for randomness.
 * <p/>
 * i.e. in case of .NET version if the same seed is used for separate Random objects, they will generate the same series of random numbers.
 * This can be useful for creating a test suite that processes random values, or for replaying games that derive their data from random numbers.
 * <p/>
 * This Java Random version uses SecureRandom that makes it more secure.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/system.random(v=vs.110).aspx">.NET Random Class</a>
 */
public class Random
{

    /**
     * Default constructor which creates self-seeding instance of SecureRandom class.
     * By defaults SecureRandom uses SHA1PRNG on both Windows and Unix.
     * This class is thread-safe.
     */
    public Random()
    {
        try
        {
            // make sure that both on Windows and Unix-like systems the same algorithm used.
            mNative = SecureRandom.getInstance(ALGORITHM);
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.warn(e);

            // in case there is no ALGORITHM then rely on SecureRandom settings.
            // Windows default is SHA1PRNG
            // Unix default algorithm is NativePRNG
            mNative = new SecureRandom();
        }
        mNative.nextBytes(new byte[1]); // make sure that self-seeding happened.
    }

    /**
     * At the moment this constructor is using for test only
     * It creates not self-seeding SecureRandom instance.
     */
    public Random(int seed)
    {
        try
        {
            mNative = SecureRandom.getInstance(ALGORITHM);
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.warn(e);

            mNative = new SecureRandom();
        }
        mNative.setSeed(seed);
    }

    public int next()
    {
        return mNative.nextInt();
    }

    public int next(int minValue, int maxValue)
    {
        return minValue + mNative.nextInt(maxValue - minValue);
    }

    public int next(int maxValue)
    {
        return mNative.nextInt(maxValue);
    }

    public double nextDouble()
    {
        return mNative.nextDouble();
    }

    public void nextBytes(byte[] buffer)
    {
        mNative.nextBytes(buffer);
    }

    private static final String ALGORITHM = "SHA1PRNG";
    private SecureRandom mNative;
}
