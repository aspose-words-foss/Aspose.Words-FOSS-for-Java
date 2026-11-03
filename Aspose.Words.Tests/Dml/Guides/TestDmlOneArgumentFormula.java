// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlOneArgumentFormula
{
    @Test
    public void calcualte_ValidFormulaWithGuideName_Calculated()
    {
        // Arrange
        String[] formulaParts = new String[] { "val", "fifty" };
        DmlOneArgumentFormula formula = new DmlOneArgumentFormula(formulaParts, new OneArgumentFormulaCallbackStub());
        DmlGuideValueProviderStub valueProvider = new DmlGuideValueProviderStub();
        valueProvider.add("fifty", 50.0);
        // Act
        double result = formula.calculate(valueProvider);
        // Assert
        msAssert.areEqual(50.0, result);
    }

    @Test
    public void calcualte_ValidFormulaWithValue_Calculated()
    {
        // Arrange
        DmlOneArgumentFormula formula = new DmlOneArgumentFormula(G_FORMULA_PARTS, new OneArgumentFormulaCallbackStub());
        DmlGuideValueProviderStub valueProvider = new DmlGuideValueProviderStub();
        // Act
        double result = formula.calculate(valueProvider);
        // Assert
        msAssert.areEqual(100.0, result);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructor_InvalidFormula_ExceptionThrown()
    {
        new DmlOneArgumentFormula(new String[1], new OneArgumentFormulaCallbackStub());
    }

    @Test
    public void constructor_ValidFormula_PropertyInitialized()
    {
        // Act
        DmlOneArgumentFormula formula = new DmlOneArgumentFormula(G_FORMULA_PARTS, new OneArgumentFormulaCallbackStub());
        // Assert
        msAssert.areEqual("100", formula.getX());
    }

    private static /*final*/ String[] G_FORMULA_PARTS = new String[] {"val", "100"};
}

