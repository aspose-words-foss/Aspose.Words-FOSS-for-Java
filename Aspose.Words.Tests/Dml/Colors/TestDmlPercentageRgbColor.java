// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlPercentageRgbColor
{
    @Test
    public void drColor_CorrectValue_CorrectDrColor()
    {
        DmlPercentageRgbColor color = new DmlPercentageRgbColor();
        //Arrange
        color.setR(DmlPercentageUtil.fromPercent("10.0%"));
        color.setG(DmlPercentageUtil.fromPercent("20.0%"));
        color.setB(DmlPercentageUtil.fromPercent("30.0%"));
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(new DrColor(0x1A,0x33,0x4c), result);
    }

    @Test
    public void drColor_NotInitialized_BlackColor()
    {
        DmlPercentageRgbColor color = new DmlPercentageRgbColor();
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.BLACK, result);
    }
}
