// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


@Test
public class TestPercentage
{
  
    @Test
    public void constructor_NonValidString_FractionIs0()
    {
        // Act
        double result = DmlPercentageUtil.fromPercent("100");
        // Assert
        msAssert.areEqual(0.0, result);
    }

    @Test
    public void constructor_ValidString_FractionIsCorret()
    {
        // Act
        double result = DmlPercentageUtil.fromPercent("-101.25%");
        // Assert
        msAssert.areEqual(-1.0125, result);
    }

    /// <summary>
    /// Checks DmlPercentageUtil.FromPercentOrDmlPercent(string, double)
    /// </summary>
    @Test (dataProvider = "testConversionFromPercentOrDmlPercentDataProvider")
    public void testConversionFromPercentOrDmlPercent(String value, double defaultValue, double expectedValue, 
        boolean expectedIsoTransitional)
    {
        OoxmlComplianceInfo complianceInfo = new OoxmlComplianceInfo();
        double result = DmlPercentageUtil.fromPercentOrDmlPercent(value, defaultValue, complianceInfo);
        msAssert.areEqual(expectedValue, result, "The conversion result is wrong.");
        msAssert.areEqual(expectedIsoTransitional, complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_TRANSITIONAL, "Wrong value of the ISO Transitional flag.");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testConversionFromPercentOrDmlPercentDataProvider")
	public static Object[][] testConversionFromPercentOrDmlPercentDataProvider()
	{
		return new Object[][]
		{
			{null,  1.23,  1.23,  false},
			{"",  1.23,  1.23,  false},
			{"0",  1.23,  0,  false},
			{"0%",  1.23,  0,  true},
			{"123400",  0,  1.234,  false},
			{"123.4%",  0,  1.234,  true},
			{"-999900",  0,  -9.999,  false},
			{"-999.9%",  0,  -9.999,  true},
		};
	}

    /// <summary>
    /// Checks DmlPercentageUtil.ToPercentOrDmlPercent
    /// </summary>
    @Test (dataProvider = "testConversionToPercentOrDmlPercentDataProvider")
    public void testConversionToPercentOrDmlPercent(double fraction, boolean isIsoStrict, String expectedValue)
    {
        String result = DmlPercentageUtil.toPercentOrDmlPercent(fraction, isIsoStrict);
        msAssert.areEqual(expectedValue, result, "The conversion result is wrong.");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testConversionToPercentOrDmlPercentDataProvider")
	public static Object[][] testConversionToPercentOrDmlPercentDataProvider()
	{
		return new Object[][]
		{
			{0.99999,  false,  "99999"},
			{0.99999,  true,  "99.999%"},
			{-0.01,  false,  "-1000"},
			{-0.01,  true,  "-1%"},
			{0,  false,  "0"},
			{0,  true,  "0%"},
		};
	}
}
