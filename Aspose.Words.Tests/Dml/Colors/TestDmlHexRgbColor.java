// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlHexRgbColor
{
    @Test
    public void drColor_CorrectValue_CorrectDrColor()
    {
        DmlHexRgbColor color = new DmlHexRgbColor();
        //Arrange
        color.setValue("F1CAB8");
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(new DrColor(0xF1,0xCA,0xB8), result);
    }

    @Test
    public void drColor_Null_EmptyColor()
    {
        DmlHexRgbColor color = new DmlHexRgbColor();
        //Arrange
        color.setValue(null);
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.EMPTY, result);
    }

    @Test
    public void drColor_EmptyString_EmptyColor()
    {
        DmlHexRgbColor color = new DmlHexRgbColor();
        //Arrange
        color.setValue("");
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.EMPTY, result);
    }
}
