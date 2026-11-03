// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestSfnt
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestFxUtil.setUpTests();
    }

    /// <summary>
    /// Check my checksum calculation works for odd-sized tables.
    /// </summary>
    @Test
    public void testChecksum()
    {
        byte[] b1 = new byte[]
        {
            (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,        // This is big endian 0x01020304
            (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08        // This is big endian 0x05060708
        };

        // This is the sum of the above values.
        msAssert.areEqual(0x06080a0c, SfntTableRecordEntry.calculateChecksum(b1, 0, 8));

        // Also check how checksum works for tables whose length does not divide neatly by 4.
        msAssert.areEqual(0x06080a04, SfntTableRecordEntry.calculateChecksum(b1, 0, 7));
        msAssert.areEqual(0x06080304, SfntTableRecordEntry.calculateChecksum(b1, 0, 6));
        msAssert.areEqual(0x06020304, SfntTableRecordEntry.calculateChecksum(b1, 0, 5));
        msAssert.areEqual(0x01020304, SfntTableRecordEntry.calculateChecksum(b1, 0, 4));
        msAssert.areEqual(0x01020300, SfntTableRecordEntry.calculateChecksum(b1, 0, 3));
    }

}

