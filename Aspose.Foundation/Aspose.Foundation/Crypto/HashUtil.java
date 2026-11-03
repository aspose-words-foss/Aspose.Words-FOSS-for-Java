// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


public /*static*/ class HashUtil
{
	/* Simulation of static class by using private constructor */
	private HashUtil()
	{}

    /// <summary>
    /// Calculates hash using the specified digest algorithm.
    /// </summary>
    public static byte[] computeHash(/*DigestAlgorithm*/int digestAlgorithm, byte[] input, int offset, int length)
    {
        return computeHash(CryptoUtilPal.createHashAlgorithm(digestAlgorithm), input, offset, length);
    }

    /// <summary>
    /// Calculates hash using the specified digest algorithm.
    /// </summary>
    public static byte[] computeHash(/*DigestAlgorithm*/int digestAlgorithm, byte[] input)
    {
        return computeHash(digestAlgorithm, input, 0, input.length);
    }

    /// <summary>
    /// Calculates hash using given IDigest object.
    /// Resets the digest state before calculating.
    /// </summary>
    public static byte[] computeHash(IDigest digest, byte[] input, int offset, int length)
    {
        if (input == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "input");

        digest.reset();
        digest.blockUpdate(input, offset, length);

        byte[] digestBytes = new byte[digest.getDigestSize()];
        digest.doFinal(digestBytes, 0);
        return digestBytes;
    }

    /// <summary>
    /// Calculates hash using given IDigest object.
    /// Resets the digest state before calculating.
    /// </summary>
    public static byte[] computeHash(IDigest digest, byte[] input)
    {
        return computeHash(digest, input, 0, input.length);
    }

    /// <summary>
    /// Calculates hash of stream using the specified digest algorithm.
    /// </summary>
    public static byte[] computeHash(/*DigestAlgorithm*/int digestAlgorithm, Stream inputStream) throws Exception
    {
        IDigest digest = CryptoUtilPal.createHashAlgorithm(digestAlgorithm);
        digest.reset();

        byte[] buffer = new byte[4096];
        int bytesRead;
        do
        {
            bytesRead = inputStream.read(buffer, 0, 4096);
            if (bytesRead > 0)
                digest.blockUpdate(buffer, 0, bytesRead);
        }
        while (bytesRead > 0);

        byte[] digestBytes = new byte[digest.getDigestSize()];
        digest.doFinal(digestBytes, 0);
        return digestBytes;
    }

    /// <summary>
    /// Calculates SHA512 hash of a byte array and returns it as a BytesHash object.
    /// </summary>
    public static BytesHash getSHA512Hash(byte[] bytes)
    {
        return new BytesHash(bytes);
    }

    /// <summary>
    /// Implements .Net String.GetHashCode method. MS implementation of the method was unsafe, 
    /// here is "ugly port" to safe code. We want the same string hashcode value in .NET, Mono 
    /// and Java to generate GUID font resource names for XPS so the golds will pass.
    /// </summary>
    public static int netGetHashCode(String str)
    {
        int num = 352654597;
        int num2 = num;

        int numPtr = 0;
        for (int i = str.length(); i > 0; i -= 4)
        {
            int int1 = str.charAt(numPtr);
            int int2 = (i > 1) ? str.charAt(numPtr + 1) : 0;
            int int3 = (i > 2) ? str.charAt(numPtr + 2) : 0;
            int int4 = (i > 3) ? str.charAt(numPtr + 3) : 0;

            int intch1 = int2;
            intch1 <<= 16;
            intch1 += int1;

            int intch2 = int4;
            intch2 <<= 16;
            intch2 += int3;

            num = (((num << 5) + num) + (num >> 27)) ^ intch1;
            if (i <= 2)
            {
                break;
            }
            num2 = (((num2 << 5) + num2) + (num2 >> 27)) ^ intch2;
            numPtr += 4;
        }
        return (num + (num2 * 1566083941));
    }
}

