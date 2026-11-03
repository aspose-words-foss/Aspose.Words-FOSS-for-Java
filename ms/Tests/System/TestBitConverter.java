// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2017 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.BitConverter;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestBitConverter
{
    @Test
    public void testGetBytesFromInt()
    {
        msAssert.areEqual(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0 }, BitConverter.getBytes(0));
        msAssert.areEqual(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1));
        msAssert.areEqual(new byte[] { (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(100));
        msAssert.areEqual(new byte[] { (byte) 0xE8, (byte) 0x03, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1000));
        msAssert.areEqual(new byte[] { (byte) 0x40, (byte) 0x42, (byte) 0x0F, (byte) 0x00 }, BitConverter.getBytes(1000000));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0xCA, (byte) 0x9A, (byte) 0x3B }, BitConverter.getBytes(1000000000));
        msAssert.areEqual(new byte[] { (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0x7F }, BitConverter.getBytes(Integer.MAX_VALUE - 1));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7F }, BitConverter.getBytes(Integer.MAX_VALUE));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80 }, BitConverter.getBytes(Integer.MIN_VALUE));
        msAssert.areEqual(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x80 }, BitConverter.getBytes(Integer.MIN_VALUE + 1));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x36, (byte) 0x65, (byte) 0xC4 }, BitConverter.getBytes(-1000000000));
        msAssert.areEqual(new byte[] { (byte) 0xC0, (byte) 0xBD, (byte) 0xF0, (byte) 0xFF }, BitConverter.getBytes(-1000000));
        msAssert.areEqual(new byte[] { (byte) 0x18, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1000));
        msAssert.areEqual(new byte[] { (byte) 0x9C, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-100));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1));
    }

    @Test
    public void testGetBytesFromLong()
    {
        msAssert.areEqual(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 }, BitConverter.getBytes(0L));
        msAssert.areEqual(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1L));
        msAssert.areEqual(new byte[] { (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(100L));
        msAssert.areEqual(new byte[] { (byte) 0xE8, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1000L));
        msAssert.areEqual(new byte[] { (byte) 0x40, (byte) 0x42, (byte) 0x0F, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0xCA, (byte) 0x9A, (byte) 0x3B, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x10, (byte) 0xA5, (byte) 0xD4, (byte) 0xE8, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(1000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x80, (byte) 0xC6, (byte) 0xA4, (byte) 0x7E, (byte) 0x8D, (byte) 0x03, (byte) 0x00 }, BitConverter.getBytes(1000000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x64, (byte) 0xA7, (byte) 0xB3, (byte) 0xB6, (byte) 0xE0, (byte) 0x0D }, BitConverter.getBytes(1000000000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7F }, BitConverter.getBytes(Long.MAX_VALUE - 1));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7F }, BitConverter.getBytes(Long.MAX_VALUE));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80 }, BitConverter.getBytes(Long.MIN_VALUE));
        msAssert.areEqual(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80 }, BitConverter.getBytes(Long.MIN_VALUE + 1));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x9C, (byte) 0x58, (byte) 0x4C, (byte) 0x49, (byte) 0x1F, (byte) 0xF2 }, BitConverter.getBytes(-1000000000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x80, (byte) 0x39, (byte) 0x5B, (byte) 0x81, (byte) 0x72, (byte) 0xFC, (byte) 0xFF }, BitConverter.getBytes(-1000000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0xF0, (byte) 0x5A, (byte) 0x2B, (byte) 0x17, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1000000000000L));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x36, (byte) 0x65, (byte) 0xC4, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1000000000L));
        msAssert.areEqual(new byte[] { (byte) 0xC0, (byte) 0xBD, (byte) 0xF0, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1000000L));
        msAssert.areEqual(new byte[] { (byte) 0x18, (byte) 0xFC, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1000L));
        msAssert.areEqual(new byte[] { (byte) 0x9C, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-100L));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, BitConverter.getBytes(-1L));
    }

    @Test
    public void testGetBytesFromFloat()
    {
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(0f));
        msAssert.areEqual(new byte[] { (byte) 0xCD, (byte) 0xCC, (byte) 0xCC, (byte) 0x3D }, BitConverter.getBytes(0.1f));
        msAssert.areEqual(new byte[] { (byte) 0x6F, (byte) 0x12, (byte) 0x83, (byte) 0x3A }, BitConverter.getBytes(0.001f));
        msAssert.areEqual(new byte[] { (byte) 0xBD, (byte) 0x37, (byte) 0x86, (byte) 0x35 }, BitConverter.getBytes(0.000001f));
        msAssert.areEqual(new byte[] { (byte) 0x5F, (byte) 0x70, (byte) 0x89, (byte) 0x30 }, BitConverter.getBytes(0.000000001f));
        msAssert.areEqual(new byte[] { (byte) 0xCC, (byte) 0xBC, (byte) 0x8C, (byte) 0x2B }, BitConverter.getBytes(0.000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x7D, (byte) 0x1D, (byte) 0x90, (byte) 0x26 }, BitConverter.getBytes(0.000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0xEF, (byte) 0x92, (byte) 0x93, (byte) 0x21 }, BitConverter.getBytes(0.000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0xA0, (byte) 0x1D, (byte) 0x97, (byte) 0x1C }, BitConverter.getBytes(0.000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x15, (byte) 0xBE, (byte) 0x9A, (byte) 0x17 }, BitConverter.getBytes(0.000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0xD2, (byte) 0x74, (byte) 0x9E, (byte) 0x12 }, BitConverter.getBytes(0.000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x60, (byte) 0x42, (byte) 0xA2, (byte) 0x0D }, BitConverter.getBytes(0.000000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x4C, (byte) 0x27, (byte) 0xA6, (byte) 0x08 }, BitConverter.getBytes(0.000000000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x25, (byte) 0x24, (byte) 0xAA, (byte) 0x03 }, BitConverter.getBytes(0.000000000000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x98, (byte) 0xE3, (byte) 0x0A, (byte) 0x00 }, BitConverter.getBytes(0.000000000000000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0xCA, (byte) 0x02, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(0.000000000000000000000000000000000000000001f));
        msAssert.areEqual(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, BitConverter.getBytes(0.000000000000000000000000000000000000000000001f));
        // This literal is too small for float. But .Net accepts it:) and BitConverter returns just zeroes instead of overflow.
        // Java doesn't accept the literal - syntax error, so we can't check what BitConverter will return in tis case:).
        // 0.0000000000000000000000000000000000000000000001f
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x3F }, BitConverter.getBytes(1f));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x7A, (byte) 0x44 }, BitConverter.getBytes(1000f));
        msAssert.areEqual(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x7A, (byte) 0xC4 }, BitConverter.getBytes(-1000f));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0x7F, (byte) 0x7F }, BitConverter.getBytes(Float.MAX_VALUE));
        msAssert.areEqual(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0x7F, (byte) 0xFF }, BitConverter.getBytes((-Float.MAX_VALUE)));
    }
}
