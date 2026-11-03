// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2018 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.math.BigInteger;
import com.aspose.ms.java.BouncyCastle.BouncyCastleUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Convert;


@Test
public class TestBigInteger
{
    @Test
    public void testToUnsignedArray()
    {
        // 127 - 7 bits
        BigInteger b = new BigInteger("127");
        byte[] result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 1);
        msAssert.areEqual((byte)result[0], (byte)127);

        // 255 - 8 bits
        b = new BigInteger("255");
        result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 1);
        msAssert.areEqual((byte)result[0], (byte)255);

        // 511 - 9 bits
        b = new BigInteger("511");
        result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 2);
        msAssert.areEqual((byte)result[0], (byte)1);
        msAssert.areEqual((byte)result[1], (byte)255);
    }

    @Test
    public void testToUnsignedArrayNegative()
    {
        // 127 - 8 bits
        BigInteger b = new BigInteger("-127");
        byte[] result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 1);
        msAssert.areEqual((byte)result[0], (byte)129);

        // 255 - 9 bits
        b = new BigInteger("-255");
        result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 2);
        msAssert.areEqual((byte)result[0], (byte)255);
        msAssert.areEqual((byte)result[1], (byte)1);
    }

    @Test
    public void testJiraJ1709()
    {
        BigInteger b = new BigInteger("96981933666541832967299512474322211898636188637190044990075663952721645838174035096765125560945848897035959112600126083370821638355193232683135101141544464166366450262604502324532818487998065203497617865813508716036838981643932262115143013314574725842132061901795478937829910612557843981167517029302449028359");
        byte[] result = BouncyCastleUtil.toByteArrayUnsigned(b);

        msAssert.areEqual(result.length, 128);
        msAssert.areEqual(Convert.toBase64String(result), "ihtbCMhh+ewGvzrzAkK7rAz/+e0rxmDwaSIjdDCVN51bU/qzwA6QtGTM0wQ3eOBybIeKBqI8psjausYssigHWZfgMIivPcoUTjg2HhhFlozFpjYGUGVHV6M8p6cB78l9yQqgR36yxq/9XIGfkw/SP1bUKs3YqIc1LFBzvIdNPQc=");
    }
}


