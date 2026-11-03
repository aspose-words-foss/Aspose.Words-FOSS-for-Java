// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2017 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msNumber;


@Test
public class TestFormatDouble
{
    @BeforeMethod (alwaysRun = true)
    public void setStandardCulture()
    {
        SystemPal.saveCulture();
        // Parsing of numbers and dates is performed using the current culture and this is by design.
        // So we have to select the standard culture before we run a test in order to pass on all
        // development machines that can have different cultures selected. 
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void revertToOldCulture()
    {
        SystemPal.restoreCulture();
    }

    @Test
    public void testBasic()
    {
        double doubleValue = 1234567.0;
        // As .Net: formats even doubles as integers.
        msAssert.areEqual("1234567", FormatterPal.doubleToStr(doubleValue));

        doubleValue = 12345678.1;
        // English locale used as default at the moment.
        msAssert.areEqual("12345678.1", FormatterPal.doubleToStr(doubleValue));
    }

    @Test
    public void testEscapesInPatterns() throws Exception
    {
        double doubleValue = -20.0d;
        // escape '-' and space character
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "#,##0;\\-#,##0\\ "), "-20 ");
        // remove the trailing '\'
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "#,##0;-#,##0\\"), "-20");
        // escape '-' and remove the trailing '\'
        doubleValue = 15.0d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "#,##0;\\-#,##0\\"), "15");
        // escape second '¤' and remove the trailing '\'
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "\u00A4#,##0;\\\u00A4-#,##0\\"), "¤15");
        doubleValue = 50.0d;
        // double quotes, don't change the enclosed characters.
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00\" percent\""), "50.00 percent");
        // more than one successive escaped characters
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00\\ \\ \\ \"%\""), "50.00   %");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00\"\\ \\ \\ \""), "50.00\\ \\ \\ ");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00\\ \\ \\ "), "50.00   ");
        // two successive substrings enclosed in double quotes
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00\" %\"\" percent\""), "50.00 % percent");
        // dummy mix
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "\" %\"\"0.00\"\"0.00\\-"), " %0.000.00\\-");
    }

    @Test
    public void testJava2084() throws Exception
    {
        double doubleValue = 9d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "#0x.00"), "9x.00");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "#0x.00"), "9x.00");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0formatAxis"), "9formatAxis");

        doubleValue = 15d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "$ x"), "$ x");

        doubleValue = 0.184d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.00x"), "0.18x");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0.x"), "0x");

        doubleValue = 111492d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "dropx##"), "dropx111492");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0'x'"), "111492x");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0\"x\""), "111492x");

        doubleValue = 24d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "axis"), "axis");

        doubleValue = 3.14d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0formatAxis"), "3formatAxis");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0x"), "3x");

        doubleValue = 600d;
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0formatAxis"), "600formatAxis");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0\\\"formatAxis\\\""), "600\"formatAxis\"");
        msAssert.areEqual(msNumber.formatDouble(doubleValue, "0\"formatAxis\""), "600formatAxis");
    }
}

