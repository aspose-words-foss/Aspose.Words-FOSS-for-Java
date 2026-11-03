// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/05/2021 by Alexey Maslov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for different RomanRightBracketTxtNumberingStyle (Lowercase, Uppercase)
/// </summary>
@Test
public class TestRomanRightBracketTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyleLower = new LowercaseRomanRightBracketTxtNumberingStyle();
        mNumberingStyleUpper = new UppercaseRomanRightBracketTxtNumberingStyle();
    }

    @Test
    public void testIsBullet()
    {
        msAssert.isFalse(mNumberingStyleLower.isBullet());
        msAssert.isFalse(mNumberingStyleUpper.isBullet());
    }

    @Test
    public void testIsSet()
    {
        msAssert.isTrue(mNumberingStyleLower.isSet());
        msAssert.isTrue(mNumberingStyleUpper.isSet());
    }

    @Test
    public void testIsLevelsSupported()
    {
        msAssert.isFalse(mNumberingStyleLower.isLevelsSupported());
        msAssert.isFalse(mNumberingStyleUpper.isLevelsSupported());
    }

    @Test
    public void testGetNextNumberLower()
    {
        String nextNumber = mNumberingStyleLower.getNextNumber("i");
        msAssert.areEqual("ii", nextNumber);

        nextNumber = mNumberingStyleLower.getNextNumber("mdlxiii");
        msAssert.areEqual("mdlxiv", nextNumber);

        nextNumber = mNumberingStyleLower.getNextNumber("a");
        msAssert.areEqual("", nextNumber);
    }

    @Test
    public void testGetNextNumberUpper()
    {
        String nextNumber = mNumberingStyleUpper.getNextNumber("V");
        msAssert.areEqual("VI", nextNumber);

        nextNumber = mNumberingStyleUpper.getNextNumber("MMDCCLXXXIX");
        msAssert.areEqual("MMDCCXC", nextNumber);

        nextNumber = mNumberingStyleUpper.getNextNumber("5");
        msAssert.areEqual("", nextNumber);
    }

    @Test
    public void testGetNumberFormat()
    {
        String numberFormat = mNumberingStyleLower.getNumberFormat(0);
        msAssert.areEqual("\u0000)", numberFormat);

        numberFormat = mNumberingStyleUpper.getNumberFormat(0);
        msAssert.areEqual("\u0000)", numberFormat);
    }

    @Test
    public void testDetectNumberingLeveLower()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyleLower.detectNumbering("i) Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("i", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("i)", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingLeveUpper()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyleUpper.detectNumbering("XI) Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("XI", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("XI)", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingNoDelimiter()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyleLower.detectNumbering("i Some text");
        msAssert.isNull(numberingInfo);

        numberingInfo = mNumberingStyleUpper.detectNumbering("M Some text");
        msAssert.isNull(numberingInfo);
    }

    @Test
    public void testDetectNumberingEmptyText()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyleLower.detectNumbering("");
        msAssert.isNull(numberingInfo);

        numberingInfo = mNumberingStyleUpper.detectNumbering("");
        msAssert.isNull(numberingInfo);
    }

    private TxtNumberingStyle mNumberingStyleLower;
    private TxtNumberingStyle mNumberingStyleUpper;
}

