// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2010 by Konstantin Sidorenko
// 2016/01/20 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestStringFormat
{
    @Test
    public void testHello()
    {
        String hello = msString.format("Hello, {0}!", "World");
        msAssert.areEqual("Hello, World!", hello);
        msAssert.areEqual("1,2,3", msString.format("{0},{1},{2}", 1, 2, 3));
    }

    @Test
    public void testEscapes()
    {
        msAssert.areEqual("Hello, 'World'!", msString.format("Hello, '{0}'!", "World"));
        msAssert.areEqual("{1},{{2,3}}}", msString.format("{{{0}}},{{{{{1},{2}}}}}}}", 1, 2, 3));
    }

    @Test
    public void testNull()
    {
        msAssert.areEqual("Hello, ''!", msString.format("Hello, '{0}'!", null));
        msAssert.areEqual(",", msString.format("{0},{1}", null, null));
        msAssert.areEqual(",,", msString.format("{0},{1},{2}", null, null, null));

        Object o1 = null, o2 = null, o3 = null;
        msAssert.areEqual("Hello, ''!", msString.format("Hello, '{0}'!", o1));
        msAssert.areEqual(",", msString.format("{0},{1}", o1, o2));
        msAssert.areEqual(",,", msString.format("{0},{1},{2}", o1, o2, o3));

    }

    @Test
    public void testHexadecimal()
    {
        msAssert.areEqual("ABC,CDE42", msString.format("{0:X},{1:X}", 0xABC, 0xCDE42));
        msAssert.areEqual("0xABC,0xCDE42", msString.format("0x{0:X},0x{1:X}", 0xABC, 0xCDE42));
    }

    @Test
    public void testDigits()
    {
        msAssert.areEqual("ABC,00000CDE42", msString.format("{0:X2},{1:X10}", 0xABC, 0xCDE42));
        msAssert.areEqual("00ABC,00000CDE42", msString.format("{0:X5},{1:X10}", 0xABC, 0xCDE42));
        msAssert.areEqual("12345,0012345678", msString.format("{0:d3},{1:D10}", 12345, 12345678));
    }

    @Test
    public void testUnsignedDigits()
    {
        byte b = (byte) 0x1B;
        /*ushort*/short ush = (short) 0x32FF;
        /*uint*/int ui = (int) 0xf2374322L;
        msAssert.areEqual("1B,32FF,F2374322", msString.format("{0:X2},{1:X4},{2:X8}", (b & 0xFF), (ush & 0xFFFF), ui));
        msAssert.areEqual("1B,32FF,F2374322", msString.format("{0:X2},{1:X4},{2:X8}", 0x1B, 0x32FF, (int) 0xf2374322L));
    }
}

