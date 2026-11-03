// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for ArabicRightBracketTxtNumberingStyle
/// </summary>
@Test
public class TestArabicRightBracketTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyle = new ArabicRightBracketTxtNumberingStyle();
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
    public void testGetNextNumber()
    {
        String nextNumber = mNumberingStyle.getNextNumber("2");
        msAssert.areEqual("3", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("134");
        msAssert.areEqual("135", nextNumber);

        nextNumber = mNumberingStyle.getNextNumber("a");
        msAssert.areEqual("", nextNumber);
    }

    @Test
    public void testGetNumberFormatLevel1()
    {
        String numberFormat = mNumberingStyle.getNumberFormat(0);
        msAssert.areEqual("\u0000)", numberFormat);
    }

    @Test
    public void testDetectNumbering()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("4) Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("4", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("4)", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingWrongText()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("1 Some text");
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

