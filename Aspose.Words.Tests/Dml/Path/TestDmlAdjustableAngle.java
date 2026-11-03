// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlAdjustableAngle
{
    @Test
    public void constructor_Double_ValueSetted()
    {
        //Arrange
        //Act
        DmlAdjustableAngle angle = new DmlAdjustableAngle(123.0);
        //Assert
        msAssert.areEqual(123.0, angle.getValue(new DmlGuideValueProviderStub()));
    }

    @Test
    public void constructor_GuideName_GuideNameSetted()
    {
        DmlGuideValueProviderStub guideValueProvider = new DmlGuideValueProviderStub();
        //Arrange
        String value = "guide";
        guideValueProvider.add(value, 999.0);
        //Act
        DmlAdjustableAngle angle = new DmlAdjustableAngle(value);
        //Assert
        msAssert.areEqual(999.0, angle.getValue(guideValueProvider));
    }

    @Test
    public void constructor_Number_ValueSetted()
    {
        //Arrange
        String value = "570";
        //Act
        DmlAdjustableAngle angle = new DmlAdjustableAngle(value);
        //Assert
        msAssert.areEqual(570.0, angle.getValue(new DmlGuideValueProviderStub()));
    }
    
}
