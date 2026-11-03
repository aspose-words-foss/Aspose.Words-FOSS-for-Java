// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for CourierCircleTxtNumberingStyle
/// </summary>
public class TestCourierCircleTxtNumberingStyle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mNumberingStyle = new CourierCircleTxtNumberingStyle();
    }

    @Test
    public void testDetectNumberingTrue1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("o Some text");
        msAssert.isNotNull(numberingInfo);
        msAssert.areEqual(1, numberingInfo.getNumbers().length);
        msAssert.areEqual("o", numberingInfo.getNumbers()[0]);
        msAssert.areEqual("o", numberingInfo.getText());
    }

    @Test
    public void testDetectNumberingFalse1()
    {
        TxtNumberingInfo numberingInfo = mNumberingStyle.detectNumbering("overall");
        msAssert.isNull(numberingInfo);
    }

    private TxtNumberingStyle mNumberingStyle;
}

