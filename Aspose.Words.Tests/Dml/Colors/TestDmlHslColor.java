// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlHslColor
{
    @Test
    public void drColor_ColorInitialized_DrColorIsCorrect()
    {
        DmlHslColor color = new DmlHslColor();
        //Arrange
        color.setHue(DmlAngle.fromDegrees(128.0));
        color.setLuminance(0.4);
        color.setSaturation(0.1);
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(new DrColor(91,112,94), result);
    }

    @Test
    public void drColor_ColorNotInitialized_BlackColorReturend()
    {
        DmlHslColor color = new DmlHslColor();
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.BLACK, result);
    }
}
