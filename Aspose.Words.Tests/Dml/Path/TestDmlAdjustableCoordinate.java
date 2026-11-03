// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlAdjustableCoordinate
{
    @Test
    public void constructor_Double_ValueSetted()
    {
        //Arrange
        //Act
        DmlAdjustableCoordinate coordinate = new DmlAdjustableCoordinate(123.0);
        //Assert
        msAssert.areEqual(123.0, coordinate.getValue(new DmlGuideValueProviderStub()));
    }

    @Test
    public void constructor_GuideName_GuideNameSetted()
    {
        DmlGuideValueProviderStub guideValueProvider = new DmlGuideValueProviderStub();
        //Arrange
        String value = "guide";
        guideValueProvider.add(value, 345.0);
        //Act
        DmlAdjustableCoordinate coordinate = new DmlAdjustableCoordinate(value);
        //Assert
        msAssert.areEqual(345.0, coordinate.getValue(guideValueProvider));
    }

    @Test
    public void constructor_Number_ValueSetted()
    {
        //Arrange
        String value = "570";
        //Act
        DmlAdjustableCoordinate coordinate = new DmlAdjustableCoordinate(value);
        //Assert
        msAssert.areEqual(570.0, coordinate.getValue(new DmlGuideValueProviderStub()));
    }

  
}
