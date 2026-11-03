// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for LowercaseLetterDotTxtNumberingStyle 
/// </summary>
@Test
public class TestLowercaseLetterDotTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyle = new LowercaseLetterDotTxtNumberingStyle();
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
        msAssert.isFalse(mNumberingStyle.isLevelsSupported());
    }

    @Test
    public void testIsStartNumber()
    {
        msAssert.isTrue(mNumberingStyle.isStartNumber("a"));
        msAssert.isFalse(mNumberingStyle.isStartNumber("b"));
    }

    @Test
    public void testGetNextNumber()
    {
        String nextNumber = mNumberingStyle.getNextNumber("a");
        msAssert.areEqual("b", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("z");
        msAssert.areEqual("", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("A");
        msAssert.areEqual("", nextNumber);
    }

    @Test
    public void testGetNumberFormatLevel1()
    {
        String numberFormat = mNumberingStyle.getNumberFormat(0);
        msAssert.areEqual("\u0000.", numberFormat);
    }

    @Test
    public void testDetectNumbering()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("d. Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("d", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("d.", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingWrongText()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("da Some text");
        msAssert.isNull(numberingInfo);
    }

    @Test
    public void testDetectNumberingEmptyText()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("");
        msAssert.isNull(numberingInfo);
    }

    private TxtNumberingStyle mNumberingStyle;
}

