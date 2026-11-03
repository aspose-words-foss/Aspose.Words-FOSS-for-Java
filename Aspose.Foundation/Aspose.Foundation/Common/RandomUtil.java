// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2011 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.BitConverter;


/// <summary>
/// Random numbers generation related methods.
/// 
/// AW uses test system which requires byte level comparisons between output files and template ("Gold") files.
/// This utility class is introduced to make output files independent on random number generation.
/// 
/// Note: Call <see cref="SetTestMode"/> before executing any tests which can be connected to methods of this class.
/// 
/// As this class violates equality between output files in Release and Test modes, keep it as simple as possible.
/// </summary>
public /*static*/ class RandomUtil
{
	/* Simulation of static class by using private constructor */
	private RandomUtil()
	{}

    /// <summary>
    /// <para>Sets Test flag. This flag is used to make behavior of some methods deterministic for test purposes.
    /// Call this method before running any tests which can be connected to methods of this class.</para>
    /// </summary>
    public static void setTestMode()
    {
        gTestMode.set(true);
        reset();
    }

    /// <summary>
    /// Resets the Test flag.
    /// </summary>
    public static void resetTestMode()
    {
        gTestMode.set(false);
    }

    public static boolean isTestMode()
    {
        return gTestMode.get();
    }

    /// <summary>
    /// Reset static initializers, so they don't create inter-test dependencies. 
    /// </summary>
    /// <remarks>Remarks this method is useful in nunit functions marked with [SetUp] attribute.</remarks>
    public static void reset()
    {
        gLastPseudorandomInitializer.set(0);
        gRandomGenerator.set(null);
    }

    /// <summary>
    /// <para>
    /// Returns random <see cref="Guid"/>.
    /// </para>
    /// <para>
    /// If <see cref="SetTestMode"/> has not been called before,
    /// it's the same as <see cref="Guid.NewGuid"/> (Parameters are ignored).
    /// </para>
    /// <para>
    /// In test mode returns Guid built as hash from <paramref name="initializers"/>, 
    /// so it's constant for the same set of initializers. Provide different initializers to get different Guids.
    /// </para>
    /// <para>If no initializers provided, then this methods will use increasing int values from 0 up to generate Guids. 
    /// This is useful when caller does not want to keep track of any initializers but wants to generate new ones all the time.
    /// </para>
    /// </summary>
    /// <remarks>
    /// Each initializer must be of one of following types: string, byte[], int, Enum.
    /// Throws <see cref="NotImplementedException"/> if an initializer has other type.
    /// Implement support of other types if needed.
    /// Note: see description of gTestMode for explanation of using TEST constant.
    /// </remarks>
    public static Guid newGuid(/*params*/ Object... initializers)
    {
        if (!gTestMode.get())
            return Guid.newGuid();

        if (initializers.length == 0)
        {
            // Do not change the order.
            initializers = new Object[]{gLastPseudorandomInitializer.get()};
            gLastPseudorandomInitializer.set(gLastPseudorandomInitializer.get() + 1);
        }

        byte[] guid = new byte[16];
        int offset = 0;

        for (Object obj : initializers)
        {
            int newHash = getTestHashCode(obj) ^ BitConverter.toInt32(guid, offset);
            ArrayUtil.writeUInt32ToByteArray(newHash, guid, offset);
            offset += 4;
            offset %= 16;
        }

        return new Guid(guid);
    }

    // Fix WORDSCPP-564


    public static byte[] getRandomBytesArray(int length)
    {
        
        if (gRandomGenerator.get() == null)
        {
            gRandomGenerator.set(CryptoUtilPal.createRandomGenerator(DigestAlgorithm.SHA_256));
            if (!gTestMode.get())
            {
                // FIX 17450. Add seed to ensure random bytes are different every time it is generated.
                byte[] seed = java.security.SecureRandom.getSeed(length);
                gRandomGenerator.get().addSeedMaterial(seed);
            }
        }
        
        byte[] bytes = new byte[length];
        gRandomGenerator.get().nextBytes(bytes);
        return bytes;
    }

    /// <summary>
    /// <para>Returns 4-byte hash for string, byte[], int and, Enum types.</para>
    /// <para>Throws <see cref="NotImplementedException"/> if <paramref name="value"/> has other type.</para>
    /// <para>Use it for test purposes only, as there was no goal to provide good hash algorithm.</para>
    /// </summary>
    /// <remarks>
    /// Implement support of other types if needed.
    /// </remarks>
    private static int getTestHashCode(Object value)
    {
        byte[] valueBytes = ms.as(value, byte[].class);
        if (valueBytes != null)
            value = ArrayUtil.dumpArray(valueBytes, 0, Math.min(256, valueBytes.length));

        String valueStr = ms.as(value, String.class);
        if (valueStr != null)
            return HashUtil.netGetHashCode(valueStr);

        if (value instanceof /*int*/Integer)
            return (/*int*/Integer)value;
        throw new IllegalStateException("Type of object is not supported.");
    }

    private static ThreadLocal<Boolean> gTestMode = new ThreadLocal<Boolean>()
    {
        @Override
        protected Boolean initialValue() { return false; }
    };

	private static ThreadLocal<Integer> gLastPseudorandomInitializer = new ThreadLocal<Integer>()
    {
        @Override
        protected Integer initialValue() { return 0; }
    };

	private static ThreadLocal<IRandomGenerator> gRandomGenerator = new ThreadLocal<IRandomGenerator>()
    {
        @Override
        protected IRandomGenerator initialValue() { return null; }
    };
}

