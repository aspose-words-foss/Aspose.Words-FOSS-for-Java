// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/02/2019 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFormatFloat
{
    @BeforeMethod (alwaysRun = true)
    public void setStandardCulture()
    {
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void revertToOldCulture()
    {
        SystemPal.restoreCulture();
    }

    @Test
    public void testUnexpectedZeros()
    {
        float val = 123.5f;
        String pattern = "0#0,##0.##0";
        msAssert.areEqual("000,123.500", msNumber.formatFloat(val, pattern));
    }

    @Test
    public void testFormatWithMixedZeroPositions()
    {
        String pattern = "[$-409]#,##0.0";
        float[] input =
        {
            1f, 2f, 3f, 4f,
            -1.3f, -9.4f, -8.3f,
            -7.9f, -0.9f, -8.1f, -10.2f,
            -6.6f, -0.1f, -8.8f, -1.6f
        };

        String[] results =
        {
            "[$-409]0,001.0", "[$-409]0,002.0", "[$-409]0,003.0", "[$-409]0,004.0",
            "-[$-409]0,001.3", "-[$-409]0,009.4", "-[$-409]0,008.3", "-[$-409]0,007.9",
            "-[$-409]0,000.9", "-[$-409]0,008.1", "-[$-409]0,010.2", "-[$-409]0,006.6",
            "-[$-409]0,000.1", "-[$-409]0,008.8", "-[$-409]0,001.6",
        };

        for (int i = 0; i < input.length; i++)
            msAssert.areEqual(results[i], msNumber.formatFloat(input[i], pattern));
    }
}

