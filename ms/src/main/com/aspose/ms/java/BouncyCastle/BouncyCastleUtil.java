package com.aspose.ms.java.BouncyCastle;

import java.math.BigInteger;
import java.util.Arrays;

public class BouncyCastleUtil
{
    public static byte[] toByteArrayUnsigned(BigInteger value)
    {
        byte[] result = value.toByteArray();
        if (value.compareTo(BigInteger.ZERO) < 0)
            return result;

        int requiredBytes = (int) Math.ceil((double) value.bitLength() / 8);

        if (result.length == requiredBytes)
            return result;

        // Remove unnecessary byte
        return Arrays.copyOfRange(result, 1, result.length);
    }
}
