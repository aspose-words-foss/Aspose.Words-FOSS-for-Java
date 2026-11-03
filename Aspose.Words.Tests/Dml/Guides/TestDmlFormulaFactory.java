// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlFormulaFactory
{
    @Test
    public void absoluteValueFormula()
    {
        checkFormulaResult("abs -1000", 1000.0);
    }

    @Test
    public void addDivideFormula()
    {
        checkFormulaResult("+/ 2 3 4", 1.25);
    }

    @Test
    public void addDivideFormulaWithAdditionalSpaces()
    {
        checkFormulaResult("+/  2  3    4   ", 1.25);
    }

    @Test
    public void addSubtractFormula()
    {
        checkFormulaResult("+- 2 3 4", 1.0);
    }

    @Test
    public void arcTanFormula()
    {
        checkFormulaResult("at2 2 3.5", 3615307.1221834663d);
    }

    @Test
    public void cosineArcTanFormula()
    {
        checkFormulaResult("cat2 2 3 4", 1.2);
    }

    @Test
    public void cosineFormula()
    {
        checkFormulaResult("cos 2 180000", 1.9972590695091477475689841168789);
    }

    @Test
    public void ifElseFormula()
    {
        checkFormulaResult("?: -1000 1 2", 2.0);
        checkFormulaResult("?: 1 2 3", 2.0);
        checkFormulaResult("?: 0 2 3", 3.0);
    }

    @Test
    public void literalValueFormula()
    {
        checkFormulaResult("val 1", 1.0);
    }

    @Test
    public void maximumValueFormula()
    {
        checkFormulaResult("max 5.1 6.2", 6.2);
    }

    @Test
    public void minimumValueFormula()
    {
        checkFormulaResult("min 5.1 6.2", 5.1);
    }

    @Test
    public void moduleFormula()
    {
        checkFormulaResult("mod 1 2 3", 3.7416573867739413855837487323165);
    }

    @Test
    public void multiplyDivideFormula()
    {
        checkFormulaResult("*/ 2 3 4", 1.5);
    }

    @Test
    public void pinToFormula()
    {
        checkFormulaResult("pin 3 4 5", 4.0);
        checkFormulaResult("pin 5 4 3", 5.0);
        checkFormulaResult("pin 3 5 4", 4.0);
    }

    @Test
    public void sineArcTanFormula()
    {
        checkFormulaResult("sat2 2 3 4", 1.6);
    }

    @Test
    public void sineFormula()
    {
        checkFormulaResult("sin 2 900000", 0.5176380902050415246977976752481);
    }

    @Test
    public void squareRootFormula()
    {
        checkFormulaResult("sqrt -4", 2.0);
    }

    @Test
    public void tangentFormula()
    {
        checkFormulaResult("tan 2 180000", 0.10481555856608240807761164894797);
    }

    private static void checkFormulaResult(String formulaString, double expectedResult)
    {
        DmlFormulaFactory factory = new DmlFormulaFactory();

        // Arrange
        DmlFormula formula = factory.create(formulaString);
        // Act
        double result = formula.calculate(new DmlGuideValueProviderStub());
        // Assert
        msAssert.areEqual(expectedResult, 1e-9, result);
    }
}

