// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/03/2014 by Alexey Morozov
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestHslColor
{
    @Test
    public void testHslColorOfficeAlgorithm()
    {
        // Values are taken from spec.
        DrColor rgbColor = DrColor.fromArgb(0x4f, 0x81, 0xbd);

        // Verify RGB to HSL conversion.
        HSLColor hslColor = HSLColor.officeFromDrColor(rgbColor);
        msAssert.areEqual(0.5916, 0.001, hslColor.getHue());
        msAssert.areEqual(0.4541, 0.001, hslColor.getSat());
        msAssert.areEqual(0.5250, 0.001, hslColor.getLum());

        // Verify back HSL to RGB conversion.
        rgbColor = hslColor.officeToDrColor();
        msAssert.areEqual(0x4f, rgbColor.getR());
        msAssert.areEqual(0x81, rgbColor.getG());
        msAssert.areEqual(0xbd, rgbColor.getB());
    }
}
