// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.SystemPal;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests <see cref="FunctionParameter"/> class.
/// </summary>
@Test
public class TestFunctionParameter extends TestFieldsBase
{
    /// <summary>
    /// Tests parsing parameter value.
    /// </summary>
    @Test
    public void testParsingParameterValue()
    {
        SystemPal.setCulture("en-US");

        /*ParameterPeculiarities*/int expectedProperties = ParameterPeculiarities.NUMBER |
                                                    ParameterPeculiarities.NEGATIVE |
                                                    ParameterPeculiarities.FRACTIONAL |
                                                    ParameterPeculiarities.CURRENCY_AT_START |
                                                    ParameterPeculiarities.SPACE_BEFORE;
        testParsingParameterValue(" -$5000.05", "-$5000.05", expectedProperties, 4);

        expectedProperties = ParameterPeculiarities.CURRENCY_AT_START | ParameterPeculiarities.NUMBER |
                             ParameterPeculiarities.FRACTIONAL | ParameterPeculiarities.SPACE_AFTER;
        testParsingParameterValue("$.05 ", "$.05", expectedProperties, 0);

        expectedProperties = ParameterPeculiarities.NEGATIVE | ParameterPeculiarities.NUMBER |
                             ParameterPeculiarities.FRACTIONAL | ParameterPeculiarities.SPACE_AFTER;
        testParsingParameterValue("-.05 ", "-.05", expectedProperties, 0);

        expectedProperties = ParameterPeculiarities.NONE;
        testParsingParameterValue(".", ".", expectedProperties, 0);

        expectedProperties = ParameterPeculiarities.NUMBER | ParameterPeculiarities.FRACTIONAL;
        testParsingParameterValue("12345.543", "12345.543", expectedProperties, 5);

        expectedProperties = ParameterPeculiarities.NUMBER | ParameterPeculiarities.SPACE_AFTER;
        testParsingParameterValue("12 ", "12", expectedProperties, 2);

        expectedProperties = ParameterPeculiarities.NONE;
        testParsingParameterValue("123.45.67", "123.45.67", expectedProperties, 0);

        expectedProperties = ParameterPeculiarities.NUMBER | ParameterPeculiarities.NEGATIVE;
        testParsingParameterValue("-1", "-1", expectedProperties, 1);
    }

    /// <summary>
    /// Tests parsing parameter value.
    /// </summary>
    /// <param name="value">Parameter value to parse.</param>
    /// <param name="expectedValue">An expected parameter value.</param>
    /// <param name="expectedProperties">An expected parameter properties.</param>
    /// <param name="expectedLength">An expected length of integer part of parameter.</param>
    private static void testParsingParameterValue(String value, String expectedValue, /*ParameterPeculiarities*/int expectedProperties, int expectedLength)
    {
        FunctionParameter target = new FunctionParameter(value);
        msAssert.areEqual(expectedValue, target.getParameterValue());
        msAssert.areEqual(expectedProperties, target.getPeculiarities());
        msAssert.areEqual(expectedLength, target.getLengthOfIntegerPart());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.CURRENCY_AT_END) == ParameterPeculiarities.CURRENCY_AT_END, target.isCurrencyAtEnd());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.CURRENCY_AT_START) == ParameterPeculiarities.CURRENCY_AT_START, target.isCurrencyAtStart());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.FRACTIONAL) == ParameterPeculiarities.FRACTIONAL, target.isFractional());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.NEGATIVE) == ParameterPeculiarities.NEGATIVE, target.isNegative());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.NUMBER) == ParameterPeculiarities.NUMBER, target.isNumber());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.SPACE_AFTER) == ParameterPeculiarities.SPACE_AFTER, target.isSpaceAfter());
        msAssert.areEqual((expectedProperties & ParameterPeculiarities.SPACE_BEFORE) == ParameterPeculiarities.SPACE_BEFORE, target.isSpaceBefore());
    }
}

