// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests how TxtNumberingDetector detects numbering in various text.
/// </summary>
@Test
public class TestTxtNumberingDetector
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
    }

    @Test
    public void testDetectForEmptySource()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("", false);
        msAssert.isNull(numbering);
    }

    @Test
    public void testDetectLowerAlphaRightBracket()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("b) some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("b)", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("b", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectLowerAlphaDot()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("c. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("c.", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("c", numbering.getNumbers()[0]);
    }


    @Test
    public void testDetectUpperAlphaRightBracket()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("D) some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("D)", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("D", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectUpperAlphaDot()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("E. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("E.", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("E", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectFalse()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("AB. some text", false);
        msAssert.isNull(numbering);
    }

    @Test
    public void testDetectAsterisk()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("* some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("*", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("*", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectDash()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("- some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("-", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("-", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectArabicDotLevel1()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("3. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("3.", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("3", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectArabicDotZerro()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("0. some text", false);
        msAssert.isNull(numbering);
    }

    @Test
    public void testDetectArabicNumberRightBracket()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("1) some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("1)", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("1", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectArabicDotLevel3()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("2.1.31. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("2.1.31.", numbering.getText());
        msAssert.areEqual(3, numbering.getNumbers().length);
        msAssert.areEqual("2", numbering.getNumbers()[0]);
        msAssert.areEqual("1", numbering.getNumbers()[1]);
        msAssert.areEqual("31", numbering.getNumbers()[2]);
    }

    @Test
    public void testDetectRomanLowercaseDot()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("mmdc. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("mmdc.", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("mmdc", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectRomanLowercaseRightBracket()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("xxiv) some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("xxiv)", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("xxiv", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectRomanUppercaseDot()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("XXIII. some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("XXIII.", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("XXIII", numbering.getNumbers()[0]);
    }

    @Test
    public void testDetectRomanUppercaseRightBracket()
    {
        TxtNumbering numbering = TxtNumberingDetector.detect("MXVII) some text", false);
        msAssert.isNotNull(numbering);
        msAssert.areEqual("MXVII)", numbering.getText());
        msAssert.areEqual(1, numbering.getNumbers().length);
        msAssert.areEqual("MXVII", numbering.getNumbers()[0]);
    }
}

