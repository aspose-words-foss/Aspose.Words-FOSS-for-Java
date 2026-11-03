// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlThreeArgumentFormula
{
    @Test
    public void calcualte_ValidFormulaWithValue_Calculated()
    {
        // Arrange
        DmlThreeArgumentFormula formula = new DmlThreeArgumentFormula(G_FORMULA_PARTS, new ThreeArgumentFormulaCallbackStub());
        DmlGuideValueProviderStub valueProvider = new DmlGuideValueProviderStub();
        valueProvider.add("fifty", 50.0).add("one", 1.0);
        // Act
        double result = formula.calculate(valueProvider);
        // Assert
        msAssert.areEqual(151.0, result);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructor_InvalidFormula_ExceptionThrown()
    {
        new DmlThreeArgumentFormula(new String[1], new ThreeArgumentFormulaCallbackStub());
    }

    @Test
    public void constructor_ValidFormula_PropertyInitialized()
    {
        // Arrange
        // Act
        DmlThreeArgumentFormula formula = new DmlThreeArgumentFormula(G_FORMULA_PARTS, new ThreeArgumentFormulaCallbackStub());
        // Assert
        msAssert.areEqual("100", formula.getX());
        msAssert.areEqual("fifty", formula.getY());
        msAssert.areEqual("one", formula.getZ());
    }

    private static /*final*/ String[] G_FORMULA_PARTS = new String[] {"val", "100", "fifty", "one"};
}

