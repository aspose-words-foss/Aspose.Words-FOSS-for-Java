// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/09/2018 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.Convert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests RandomUtil class.
/// </summary>
@Test
public class TestRandomUtil
{
    /// <summary>
    /// WORDSNET-17450 RandomUtil.GetRandomBytesArray() always generates the same result.
    /// We should add a seed material to a random generator.
    /// </summary>
    /// <remarks>
    /// Tests random byte arrays generated in test mode.
    /// In this mode the sequence of random byte arrays must be always the same.
    /// </remarks>
    @Test
    public void testJira17450TestMode()
    {
        // Set test mode.
        RandomUtil.setTestMode();

        // Check random bytes in test mode are always generated in determined sequence.
        checkArraysEqual(G_FIRST_TEST_RANDOM_VALUE, RandomUtil.getRandomBytesArray(16));
        checkArraysEqual(G_SECOND_TEST_RANDOM_VALUE, RandomUtil.getRandomBytesArray(16));

        // Reset and check values are generated from the beginning again.
        RandomUtil.reset();
        checkArraysEqual(G_FIRST_TEST_RANDOM_VALUE, RandomUtil.getRandomBytesArray(16));
        checkArraysEqual(G_SECOND_TEST_RANDOM_VALUE, RandomUtil.getRandomBytesArray(16));
    }

    /// <summary>
    /// Relates to WORDSNET-17450
    /// </summary>
    /// <remarks>
    /// Tests random byte arrays generated in production mode.
    /// Checks that random generator produces different sequence of random bytes.
    /// </remarks>
    @Test (groups = "SingleThreadGroup")
    public void testJira17450ProductionModeA()
    {
        // Set production mode.
        RandomUtil.resetTestMode();

        // Generate random bytes array of some arbitrary lengths.
        checkArraysNotEqual(RandomUtil.getRandomBytesArray(16), RandomUtil.getRandomBytesArray(16));
        checkArraysNotEqual(RandomUtil.getRandomBytesArray(1), RandomUtil.getRandomBytesArray(1));
        checkArraysNotEqual(RandomUtil.getRandomBytesArray(3), RandomUtil.getRandomBytesArray(3));
        checkArraysNotEqual(RandomUtil.getRandomBytesArray(4), RandomUtil.getRandomBytesArray(4));
    }

    /// <summary>
    /// Relates to WORDSNET-17450
    /// </summary>
    /// <remarks>
    /// Tests random byte arrays generated in production mode.
    /// Checks that even after generator is reset it produces different sequence of random bytes.
    /// </remarks>
    @Test (groups = "SingleThreadGroup")
    public void testJira17450ProductionModeB()
    {
        // Set production mode.
        RandomUtil.resetTestMode();
        byte[][] randomBytesArrayA = getArrayOfRandomByteArrays(16, 1, 2, 3, 4, 5, 8, 14, 17, 32);

        // Reset and generate another one array of random byte arrays.
        RandomUtil.reset();
        byte[][] randomBytesArrayB = getArrayOfRandomByteArrays(16, 1, 2, 3, 4, 5, 8, 14, 17, 32);

        // Check arrays different after random generator was reset.
        for (int i = 0; i < randomBytesArrayA.length; i++)
            checkArraysNotEqual(randomBytesArrayA[i], randomBytesArrayB[i]);
    }

    /// <summary>
    /// WORDSJAVA-1917 In order to verify encrypted data we have to have predictable vectors.
    /// So the PredictableRandomGeneratorPal class was introduced to achieve this goals.
    /// When testMode is on then the same sequence of byte arrays comes up.
    /// </summary>
    @Test
    public void testPredictableRandomInTestMode()
    {
        RandomUtil.setTestMode();
        String[] sequence = { "N0frUk0DaLES69wyD38C+A==", "b1EvVLRfboPl8h7m/GqfeQ==", "9g8f2o7Vx7tWhDxLIiVzRg==", };
        for (String bytesArray : sequence)
        {
            byte[] bytes = RandomUtil.getRandomBytesArray(16);
            msAssert.areEqual(bytesArray, Convert.toBase64String(bytes));
        }
    }

    /// <summary>
    /// WORDSJAVA-1917 When testMode is off then the random sequence of byte arrays comes up.
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void testProductionRandom()
    {
        RandomUtil.resetTestMode();
        String[] sequence = { "N0frUk0DaLES69wyD38C+A==", "b1EvVLRfboPl8h7m/GqfeQ==", "9g8f2o7Vx7tWhDxLIiVzRg==", };
        for (String bytesArray : sequence)
        {
            byte[] bytes = RandomUtil.getRandomBytesArray(16);
            msAssert.areNotEqual(bytesArray, Convert.toBase64String(bytes));
        }
    }

    /// <summary>
    /// Gets array of random byte arrays.
    /// </summary>
    private static byte[][] getArrayOfRandomByteArrays(/*params*/ int... arraysLengths)
    {
        byte[][] randomBytesArray = new byte[arraysLengths.length][];
        for (int i = 0; i < arraysLengths.length; i++)
            randomBytesArray[i] = RandomUtil.getRandomBytesArray(arraysLengths[i]);

        return randomBytesArray;
    }

    /// <summary>
    /// Checks arrays are equal.
    /// </summary>
    private static void checkArraysEqual(byte[] arrayA, byte[] arrayB)
    {
        msAssert.isTrue(ArrayUtil.isArrayEqual(arrayA, arrayB), msString.format("\nArrayA: {0}\nArrayB: {1}", ArrayUtil.dumpArray(arrayA), ArrayUtil.dumpArray(arrayB)));
    }

    /// <summary>
    /// Checks arrays are not equal.
    /// </summary>
    private static void checkArraysNotEqual(byte[] arrayA, byte[] arrayB)
    {
        msAssert.isFalse(ArrayUtil.isArrayEqual(arrayA, arrayB), msString.format("Both arrays equal to: {0}", ArrayUtil.dumpArray(arrayA)));
    }

    /// <summary>
    /// Random array in test mode is generated using the same deterministic seed,
    /// so the sequence of random bytes in test mode always the same.
    /// </summary>
    private static /*final*/ byte[] G_FIRST_TEST_RANDOM_VALUE = new byte[] { (byte) 0x37, (byte) 0x47, (byte) 0xEB, (byte) 0x52, (byte) 0x4D, (byte) 0x03, (byte) 0x68, (byte) 0xB1, (byte) 0x12, (byte) 0xEB, (byte) 0xDC, (byte) 0x32, (byte) 0x0F, (byte) 0x7F, (byte) 0x02, (byte) 0xF8 };
    private static /*final*/ byte[] G_SECOND_TEST_RANDOM_VALUE = new byte[] { (byte) 0x6F, (byte) 0x51, (byte) 0x2F, (byte) 0x54, (byte) 0xB4, (byte) 0x5F, (byte) 0x6E, (byte) 0x83, (byte) 0xE5, (byte) 0xF2, (byte) 0x1E, (byte) 0xE6, (byte) 0xFC, (byte) 0x6A, (byte) 0x9F, (byte) 0x79 };
}

