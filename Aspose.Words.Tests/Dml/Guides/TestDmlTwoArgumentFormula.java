// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlTwoArgumentFormula
{
    @Test
    public void calcualte_ValidFormulaWithValue_Calculated()
    {
        // Arrange
        DmlTwoArgumentFormula formula = new DmlTwoArgumentFormula(G_FORMULA_PARTS, new TwoArgumentFormulaCallbackStub());
        DmlGuideValueProviderStub valueProvider = new DmlGuideValueProviderStub();
        valueProvider.add("fifty", 50.0);
        // Act
        double result = formula.calculate(valueProvider);
        // Assert
        msAssert.areEqual(150.0, result);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructor_InvalidFormula_ExceptionThrown()
    {
        new DmlTwoArgumentFormula(new String[1], new TwoArgumentFormulaCallbackStub());
    }

    @Test
    public void constructor_ValidFormula_PropertyInitialized()
    {
        // Arrange
        // Act
        DmlTwoArgumentFormula formula = new DmlTwoArgumentFormula(G_FORMULA_PARTS, new TwoArgumentFormulaCallbackStub());
        // Assert
        msAssert.areEqual("100", formula.getX());
        msAssert.areEqual("fifty", formula.getY());
    }

    private static /*final*/ String[] G_FORMULA_PARTS = new String[] { "val", "100", "fifty" };
}

