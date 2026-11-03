// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for AsteriskTxtNumberingStyle
/// </summary>
@Test
public class TestAsteriskTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyle = new AsteriskTxtNumberingStyle();
    }

    @Test
    public void testIsBullet()
    {
        msAssert.isTrue(mNumberingStyle.isBullet());
    }

    @Test
    public void testIsSet()
    {
        msAssert.isFalse(mNumberingStyle.isSet());
    }

    @Test
    public void testIsLevelsSupported()
    {
        msAssert.isFalse(mNumberingStyle.isLevelsSupported());
    }

    @Test
    public void testGetNumberFormatLevel1()
    {
        String numberFormat = mNumberingStyle.getNumberFormat(0);
        msAssert.areEqual("*", numberFormat);
    }
    
    @Test
    public void testDetectNumberingTrue1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("* Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("*", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("*", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingTrue2()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("*ötext");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("*", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("*", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingTrue3()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("*1945");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("*", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("*", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingFalse1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("1 Some text");
        msAssert.isNull(numberingInfo);
    }

    @Test
    public void testDetectNumberingFalse2()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("** Some text");
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

