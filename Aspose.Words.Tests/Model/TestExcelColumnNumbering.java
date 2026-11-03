// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2012 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="NumberConverter.ToExcelColumnName"/> and <see cref="NumberConverter.ParseExcelColumnName"/>.
/// </summary>
@Test
public class TestExcelColumnNumbering
{
    @Test
    public void testLowerCase()
    {
        msAssert.areEqual(0, NumberConverter.parseExcelColumnName("a"));
        msAssert.areEqual(25, NumberConverter.parseExcelColumnName("z"));
        msAssert.areEqual(26, NumberConverter.parseExcelColumnName("Aa"));
        msAssert.areEqual(27, NumberConverter.parseExcelColumnName("aB"));
    }

    @Test
    public void testNextValue()
    {
        checkNextValue("ZZ", "AAA");
        checkNextValue("ZZZ", "AAAA");
        checkNextValue("ZZZZ", "AAAAA");
        checkNextValue("ZZZZZ", "AAAAAA");
    }

    @Test
    public void testPair()
    {
        checkPair(0, "A");
        checkPair(25, "Z");
        checkPair(26, "AA");
        checkPair(27, "AB");
        checkPair(701, "ZZ");
        checkPair(702, "AAA");
    }

    @Test
    public void testRoundTrip()
    {
        checkRoundTrip(123456789);
        checkRoundTrip(987654321);
    }

    @Test
    public void testBigNumbers()
    {
        msAssert.areEqual("FXSHRXW", NumberConverter.toExcelColumnName(Integer.MAX_VALUE - 1));
        msAssert.areEqual("FXSHRXX", NumberConverter.toExcelColumnName(Integer.MAX_VALUE));

        msAssert.areEqual(Integer.MAX_VALUE - 1, NumberConverter.parseExcelColumnName("FXSHRXW"));
        msAssert.areEqual(Integer.MAX_VALUE, NumberConverter.parseExcelColumnName("FXSHRXX"));
    }

    @Test
    public void testBigNumbersError()
    {
         msAssert.areEqual(-1, NumberConverter.parseExcelColumnName("FXSHRXY"));
    }

    @Test
    public void testInvalidColumnIndex()
    {
        msAssert.areEqual(null, NumberConverter.toExcelColumnName(-1));
    }

    private static void checkNextValue(String prevValue, String nextValue)
    {
        msAssert.areEqual(1, NumberConverter.parseExcelColumnName(nextValue) - NumberConverter.parseExcelColumnName(prevValue));
    }

    private static void checkPair(int intValue, String stringValue)
    {
        msAssert.areEqual(intValue, NumberConverter.parseExcelColumnName(stringValue));
        msAssert.areEqual(stringValue, NumberConverter.toExcelColumnName(intValue));
    }

    private static void checkRoundTrip(int intValue)
    {
        String stringValue = NumberConverter.toExcelColumnName(intValue);
        int intValue2 = NumberConverter.parseExcelColumnName(stringValue);

        msAssert.areEqual(intValue, intValue2);
    }
}

