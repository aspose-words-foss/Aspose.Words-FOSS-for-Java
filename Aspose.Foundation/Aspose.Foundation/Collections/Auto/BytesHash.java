// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2018 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;


/// <summary>
/// This class generates and holds SHA512 hash of the given array. It overrides equals and hashCode so that its instances could be used in Dictionaries.
/// </summary>
public class BytesHash
{
    /// <summary>
    /// Returns Guid's 16 bytes from the generated SHA512 hash.
    /// </summary>
    public byte[] toGuidByteArray()
    {
        return mGuid.toByteArray();
    }

    /// <returns>SHA512 Hash's hashCode</returns>
    public /*override*/ int hashCode()
    {
        return mHashCode;
    }

    /// <summary>
    /// For performance sake this method checks hashCode from SHA512 hash.
    /// There could be very small collision probability from SHA512 hash function and hashCode() method.
    /// So we neglect this probability.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (!(obj instanceof BytesHash))
            return false;

        return mHashCode == obj.hashCode();
    }

    /// <summary>
    /// Initializes a new instance of BytesHash which generates and holds SHA512 hash of the given byte array.
    /// </summary>
    public BytesHash(byte[] bytes)
    {
    	this(bytes, 0, (bytes != null) ? bytes.length : 0);
	
        // Everything is done by another constructor.
    }

    /// <summary>
    /// Initializes a new instance of BytesHash which generates and holds SHA512 hash of a portion the given byte array.
    /// </summary>
    public BytesHash(byte[] bytes, int offset, int length)
    {
        // SHA512 = 64 length mHash
        IDigest hashAlg = CryptoUtilPal.createHashAlgorithm(DigestAlgorithm.SHA_512);
        mHash = (bytes != null)
            ? HashUtil.computeHash(hashAlg, bytes, offset, length)
            : EMPTY.mHash;
        mHashCode = calculateHashCode(mHash);
        mGuid = createGuid(mHash);
    }

    public static boolean op_Equality(BytesHash left, BytesHash right)
    {
        return equals(left, right);
    }

    public static boolean op_Inequality(BytesHash left, BytesHash right)
    {
        return !equals(left, right);
    }

    /// <summary>
    /// Creates Guid from each 4th byte of SHA512 hash.
    /// </summary>
    private static Guid createGuid(byte[] hash)
    {
        byte[] b = new byte[16];
        for (int i = 0, j = 0; i < hash.length; i += 4, j++)
            b[j] = hash[i];

        return new Guid(b);
    }

    /// <summary>
    /// This method performs equality check.
    /// This method could be used to verify password hashes to make sure there is no collisions from hashCode() method.
    /// </summary>
    /// <returns> true if SHA512 hashes are equal.</returns>
    public static boolean equals(BytesHash k1, BytesHash k2)
    {
        // 1. check hashCode() value first
        boolean eq = k1.equals(k2);
        if (!eq)
            return false;

        // 2. then check the Hashes equality
        if (k1.mHash.length != k2.mHash.length)
            return false;

        for (int i = 0; i < k1.mHash.length; i++)
        {
            if (k1.mHash[i] != k2.mHash[i])
                return false;
        }

        return true;
    }

    /// <summary>
    /// Calculates hash code from the given array. 
    /// This method is taken from JDK10
    /// </summary>
    private static int calculateHashCode(byte[] a)
    {
        if (a == null)
            return 0;

        int result = 1;
        for (byte element : a)
            result = 31 * result + (element & 0xFF);

        return result;
    }

    public static /*final*/ BytesHash EMPTY = new BytesHash(new byte[0]);
    private /*final*/ byte[] mHash;
    private /*final*/ int mHashCode;
    private /*final*/ Guid mGuid;
}

