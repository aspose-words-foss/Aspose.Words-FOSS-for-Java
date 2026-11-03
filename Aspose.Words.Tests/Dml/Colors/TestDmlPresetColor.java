// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlPresetColor
{
    @Test
    public void drColor_CorrectValue_CorrectDrColor()
    {
        DmlPresetColor color = new DmlPresetColor();
        //Arrange
        color.setValue("red");
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.RED, result);
    }

    @Test
    public void drColor_NotInitialized_EmptyColor()
    {
        DmlPresetColor color = new DmlPresetColor();
        //Act
        DrColor result = color.createDrColor(null, null);
        //Assert
        msAssert.areEqual(DrColor.EMPTY, result);
    }
}
