// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlGuides
{
    @Test (expectedExceptions = NullPointerException.class)
    public void addAdjustableValue_FormulaIsNull_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        guides.addAdjustableValue("aaa", null, false);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void addAdjustableValue_NameIsNull_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        guides.addAdjustableValue(null, "val 100", false);
    }

    @Test
    public void addAdjustableValue_ValidArguments_ValidGuideCreatedAndAdded()
    {
        DmlGuides guides = new DmlGuides();
        // Act
        guides.addAdjustableValue("gd", "val 100", false);
        // Assert
        DmlGuide guide = guides.getAdjustableValues().get(0);
        msAssert.areEqual("gd", guide.getName());
        msAssert.isNotNull(guide.getFormula());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void addGuide_FormulaIsNull_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        guides.addGuide("aaa", null, false);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void addGuide_NameIsNull_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        guides.addGuide(null, "val 100", false);
    }

    @Test
    public void addGuide_ValidArguments_ValidGuideCreatedAndAdded()
    {
        DmlGuides guides = new DmlGuides();
        // Act
        guides.addGuide("gd", "val 100", false);
        // Assert
        DmlGuide guide = guides.getGuides().get(0);
        msAssert.areEqual("gd", guide.getName());
        msAssert.isNotNull(guide.getFormula());
    }

    @Test
    public void calculate_GuideHasRelationOnAdjustableValue_CorrectValuesReturned()
    {
        DmlGuides guides = new DmlGuides();
        // Arrange
        guides.addAdjustableValue("av1", "val 16", false);
        guides.addGuide("g1", "sqrt av1", false);
        // Act
        guides.calculate(0.0, 0.0);
        // Assert
        checkGuideValue("g1", 4.0, guides);
        checkGuideValue("av1", 16.0, guides);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void calculate_GuideHasRelationOnNextGuide_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        // Arrange
        guides.addGuide("g1", "sqrt g2", false);
        guides.addGuide("g2", "sqrt g1", false);
        // Act
        guides.calculate(0.0, 0.0);
        // Assert
        checkGuideValue("g1", 4.0, guides);
    }

    @Test
    public void calculate_GuideHasRelationOnPrevGuide_CalcualtedSuccesefuly()
    {
        DmlGuides guides = new DmlGuides();
        // Arrange
        guides.addGuide("g1", "sqrt 16", false);
        guides.addGuide("g2", "min g1 5", false);
        // Act
        guides.calculate(0.0, 0.0);
        // Assert
        checkGuideValue("g2", 4.0, guides);
    }

    @Test
    public void calculate_WidthAndHeightDefined_StandartGuidesInitialized()
    {
        DmlGuides guides = new DmlGuides();
        // Act
        guides.calculate(22.0, 50.0);
        // Assert
        checkGuideValue("w", 22.0, guides);
        checkGuideValue("h", 50.0, guides);
        checkGuideValue("3cd4", 16200000.0, guides);
        checkGuideValue("3cd8", 8100000.0, guides);
        checkGuideValue("5cd8", 13500000.0, guides);
        checkGuideValue("7cd8", 18900000.0, guides);
        checkGuideValue("b", 50.0, guides);
        checkGuideValue("cd2", 10800000.0, guides);
        checkGuideValue("cd4", 5400000.0, guides);
        checkGuideValue("cd8", 2700000.0, guides);
        checkGuideValue("hc", 22.0/2.0, guides);
        checkGuideValue("hd2", 50.0/2.0, guides);
        checkGuideValue("hd3", 50.0/3.0, guides);
        checkGuideValue("hd4", 50.0/4.0, guides);
        checkGuideValue("hd5", 50.0/5.0, guides);
        checkGuideValue("hd6", 50.0/6.0, guides);
        checkGuideValue("hd8", 50.0/8.0, guides);
        checkGuideValue("l", 0.0, guides);
        checkGuideValue("ls", 50.0, guides);
        checkGuideValue("r", 22.0, guides);
        checkGuideValue("ss", 22.0, guides);
        checkGuideValue("ssd2", 22.0/2.0, guides);
        checkGuideValue("ssd4", 22.0/4.0, guides);
        checkGuideValue("ssd6", 22.0/6.0, guides);
        checkGuideValue("ssd8", 22.0/8.0, guides);
        checkGuideValue("ssd16", 22.0/16.0, guides);
        checkGuideValue("ssd32", 22.0/32.0, guides);
        checkGuideValue("t", 0.0, guides);
        checkGuideValue("vc", 50.0/2.0, guides);
        checkGuideValue("wd2", 22.0/2.0, guides);
        checkGuideValue("wd3", 22.0/3.0, guides);
        checkGuideValue("wd4", 22.0/4.0, guides);
        checkGuideValue("wd5", 22.0/5.0, guides);
        checkGuideValue("wd6", 22.0/6.0, guides);
        checkGuideValue("wd8", 22.0/8.0, guides);
        checkGuideValue("wd10", 22.0/10.0, guides);
        checkGuideValue("wd32", 22.0/32.0, guides);
    }

    private void checkGuideValue(String guideName, double expectedValue, DmlGuides guides)
    {
        msAssert.areEqual(expectedValue, guides.getValue(guideName));
    }

    @Test
    public void getValue_AdjustableValueWithSpecifiedNameExists_ValueReturned()
    {
        DmlGuides guides = new DmlGuides();
        // Arange
        guides.addAdjustableValue("aaa", "val 4", false);
        guides.calculate(0.0, 0.0);
        // Act
        // Assert
        checkGuideValue("aaa", 4.0, guides);
    }

    @Test
    public void getValue_GuideWithSpecifiedNameExists_ValueReturned()
    {
        DmlGuides guides = new DmlGuides();
        // Arange
        guides.addGuide("aaa", "val 4", false);
        guides.calculate(0.0, 0.0);
        // Act
        // Assert
        checkGuideValue("aaa", 4.0, guides);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void getValue_ValueWithSpecifiedNotExists_ExceptionThrown()
    {
        DmlGuides guides = new DmlGuides();
        guides.getValue("aaa");
    }
}

