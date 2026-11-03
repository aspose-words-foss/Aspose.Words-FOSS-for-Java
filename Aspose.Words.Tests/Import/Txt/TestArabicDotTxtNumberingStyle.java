// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for ArabicDotTxtNumberingStyle 
/// </summary>
@Test
public class TestArabicDotTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyle = new ArabicDotTxtNumberingStyle();
    }

    @Test
    public void testIsBullet()
    {
        msAssert.isFalse(mNumberingStyle.isBullet());
    }

    @Test
    public void testIsSet()
    {
        msAssert.isTrue(mNumberingStyle.isSet());
    }

    @Test
    public void testIsLevelsSupported()
    {
        msAssert.isTrue(mNumberingStyle.isLevelsSupported());
    }

    @Test
    public void testGetNextNumber()
    {
        String nextNumber = mNumberingStyle.getNextNumber("1");
        msAssert.areEqual("2", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("134");
        msAssert.areEqual("135", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("a");
        msAssert.areEqual("", nextNumber);
    }

    @Test
    public void testGetNumberFormat()
    {
        String numberFormat = mNumberingStyle.getNumberFormat(0);
        msAssert.areEqual("\u0000.", numberFormat);

        numberFormat = mNumberingStyle.getNumberFormat(1);
        msAssert.areEqual("\u0000.\u0001.", numberFormat);
    }

    @Test
    public void testDetectNumberingLevel1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("1. Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("1", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("1.", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingLevel2()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("4.12.9. Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(3, numberingInfo.getNumbers().length);
        msAssert.areEqual("4", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("12", numberingInfo.getNumbers()[1]);
        msAssert.areEqual("9", numberingInfo.getNumbers()[2]);
        msAssert.areEqual("4.12.9.", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingLevel2WoTrailingDotLevel1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("4.12 Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(2, numberingInfo.getNumbers().length);
        msAssert.areEqual("4", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("12", numberingInfo.getNumbers()[1]);
        msAssert.areEqual("4.12", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingLevel2WoTrailingDotLevel0()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("1 Some text");
        msAssert.isNotNull(numberingInfo);
    }

    @Test
    public void testDetectNumberingEmptyText()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("");
        msAssert.isNull(numberingInfo);
    }

    private TxtNumberingStyle mNumberingStyle;
}

