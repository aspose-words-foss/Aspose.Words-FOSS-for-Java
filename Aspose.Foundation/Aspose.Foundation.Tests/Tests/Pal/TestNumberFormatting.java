// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/05/2015 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Globalization.msCultureInfo;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests number formatting to make sure Java's output is the same as .NET's.
/// http://msdn.microsoft.com/en-us/library/dwhawy9k(v=vs.110).aspx
/// http://msdn.microsoft.com/en-us/library/0c899ak8(v=vs.110).aspx
/// </summary>
@Test
public class TestNumberFormatting
{
    @Test
    public void testStandardNumericFormat()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

            double value = 123.456;
            msCultureInfo[] cultures =
                {
                    new msCultureInfo("ru-RU", false),
                    new msCultureInfo("en-US", false),
                    new msCultureInfo("de-DE", false),
                    new msCultureInfo("sv-SE", false),
                    new msCultureInfo("ja-JP", false)
                };

            // C
            String[] specifier = new String[] { "C", "C2", "C3", "C10" };
            String[] precisionResults = new String[] { "$123.46", "$123.46", "$123.456", "$123.4560000000" };
            //JAVA specific results. No ruble currency symbol yet.
            String[] cultureSpecificResults = new String[] { "123,46 руб.", "$123.46", "123,46 €", "123,46 kr", "￥123" }; 

            verifyFormat(specifier, value, precisionResults, cultures, cultureSpecificResults);

            // D
            int valueInt = -123; //Supported by: Integral types only.
            specifier = new String[] { "D", "D5" };
            precisionResults = new String[] { "-123", "-00123" };
            cultureSpecificResults = new String[] { "-123", "-123", "-123", "-123", "-123" };
            verifyFormat(specifier, valueInt, precisionResults, cultures, cultureSpecificResults);

            // E
            specifier = new String[] { "e", "e2" };
            precisionResults = new String[] { "1.234560e+002", "1.23e+002" };
            cultureSpecificResults = new String[]
                {"1,234560e+002", "1.234560e+002", "1,234560e+002", "1,234560e+002", "1.234560e+002"};
            verifyFormat(specifier, value, precisionResults, cultures, cultureSpecificResults);

            // F
            specifier = new String[] { "F1", "F4" };
            precisionResults = new String[] { "123.5", "123.4560" };
            cultureSpecificResults = new String[] { "123,5", "123.5", "123,5", "123,5", "123.5" };
            verifyFormat(specifier, value, precisionResults, cultures, cultureSpecificResults);

            // G
            specifier = new String[] { "G", "G4" };
            //JAVA specific rounding. 
            precisionResults =       new String[] { "123.456", "123.456" }; 
            cultureSpecificResults = new String[] { "123,456", "123.456", "123,456", "123,456", "123.456" };
            verifyFormat(specifier, value, precisionResults, cultures, cultureSpecificResults);

            // N
            double valueDouble = 123456.789;
            specifier = new String[] { "N", "N3" };
            precisionResults = new String[] { "123,456.79", "123,456.789" };
            cultureSpecificResults = new String[] { "123 456,79", "123,456.79", "123.456,79", "123 456,79", "123,456.79" };
            verifyFormat(specifier, valueDouble, precisionResults, cultures, cultureSpecificResults);

            // P
            double persentValue = 1.789;
            specifier = new String[] { "P", "P2" };
            // JAVA-changed
            precisionResults =       new String[] { "178.90%", "178.90%" }; 
            cultureSpecificResults = new String[] { "178,90%", "178.90%", "178,90%", "178,90 %", "178.90%" };
            verifyFormat(specifier, persentValue, precisionResults, cultures, cultureSpecificResults);

            // R
            double roundValue = 3.14159265358979323846;
            //Supported by: Single, Double, and BigInteger.Precision specifier: Ignored.
            specifier = new String[] { "R" };

            //JAVA specific rounding.
            precisionResults =       new String[] { "3.1415926535897930" }; 
            cultureSpecificResults = new String[] { "3,1415926535897930", "3.1415926535897930", "3,1415926535897930", "3,1415926535897930", "3.1415926535897930" };
            verifyFormat(specifier, roundValue, precisionResults, cultures, cultureSpecificResults);

            // X
            int hexValue = 1463; //Supported by: Integral types only.
            specifier = new String[] { "X", "X6" };
            precisionResults = new String[] { "5B7", "0005B7" };
            cultureSpecificResults = new String[] { "5B7", "5B7", "5B7", "5B7", "5B7" };
            verifyFormat(specifier, hexValue, precisionResults, cultures, cultureSpecificResults);
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    @Test (enabled = false, description = "This functionality is very different in Java. Will be implemented when we need it")
    public void testCustomNumericFormat()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();
            double value = 123.456;

            // 0
            String format = "00000";
            verifyCustomFormat(value, format, "00123");

            // #
            format = "#############";
            verifyCustomFormat(value, format, "123");

            // .
            format = "0.0";
            verifyCustomFormat(value, format, "123.5");

            // ,
            double value2 = 23456.129;
            format = "#,#.##";
            verifyCustomFormat(value2, format, "23,456.13");

            // %
            format = "%#0.00";
            verifyCustomFormat(value, format, "%12345.60");

            // ‰
            format = "#0.00‰";
            verifyCustomFormat(value, format, "123456.00‰");

            // E0
            format = "#0.0e0";
            verifyCustomFormat(value, format, "12.3e1");

            // E+0
            format = "0.0##e+00";
            verifyCustomFormat(value, format, "1.235e+02");

            // 'string'
            format = "# ' degrees'";
            verifyCustomFormat(value, format, "123  degrees");

            // \
            format = "#0.0#;(#0.0#);-\u0000-";
            verifyCustomFormat(value, format, "123.46");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    @Test
    public void testExp()
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();
            msAssert.areEqual("1.234560e+000", format("e", 1.23456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234560e+001", format("e", 12.3456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234560e+002", format("e", 123.456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234560e+003", format("e", 1234.56, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234560e-001", format("e", .123456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234560e-001", format("e", .123456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.235e-001", format("e3", .123456, msCultureInfo.getCurrentCulture()));
            msAssert.areEqual("1.234123e+019", format("e", 12341234123412341234D, msCultureInfo.getCurrentCulture()));
        }
        finally
        {
            SystemPal.restoreCulture();
        }

    }

    private static <T> void verifyFormat(String[] specifier, T value, String[] precisionResults,
            msCultureInfo[] cultures, String[] cultureSpecificResults)
    {
        for (int i = 0; i < specifier.length; i++)
            msAssert.areEqual(precisionResults[i], format(specifier[i], value, msCultureInfo.getCurrentCulture()));

        for (int i = 0; i < cultures.length; i++)
            msAssert.areEqual(cultureSpecificResults[i], format(specifier[0], value, cultures[i]));
    }

    private static <T> void verifyCustomFormat(T value, String format, String result)
    {
        msAssert.areEqual(result, format(format, value, msCultureInfo.getCurrentCulture()));
    }

    private static <T> String format(String fmt, T val, msCultureInfo culture)
    {
        return com.aspose.ms.System.msNumber.toString(val, fmt, culture);
    }
}

