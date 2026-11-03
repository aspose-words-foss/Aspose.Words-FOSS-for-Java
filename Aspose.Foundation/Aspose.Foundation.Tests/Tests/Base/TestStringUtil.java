// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/10/2017 by Konstantin Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestStringUtil
{
    /// <summary>
    /// Tests <see cref="StringUtil.Ellipsisize"/> method.
    /// </summary>
    @Test
    public void testEllipsisize()
    {
        final String STR = "Very long string with large number of characters";
        String output = StringUtil.ellipsisize(STR, 10);
        msAssert.areEqual("Very lo...", output);

        msAssert.areEqual("1", StringUtil.ellipsisize("1234", 1));
        msAssert.areEqual("12", StringUtil.ellipsisize("1234", 2));
        msAssert.areEqual("123", StringUtil.ellipsisize("1234", 3));
        msAssert.areEqual("1234", StringUtil.ellipsisize("1234", 4));
        msAssert.areEqual("1234", StringUtil.ellipsisize("1234", 5));
        msAssert.areEqual("12...", StringUtil.ellipsisize("1234567", 5));
        msAssert.areEqual("123...", StringUtil.ellipsisize("1234567", 6));
        msAssert.areEqual("1234567", StringUtil.ellipsisize("1234567", 7));
    }
}
