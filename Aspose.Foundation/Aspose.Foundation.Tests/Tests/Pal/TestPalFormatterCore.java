package com.aspose;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import static org.testng.Assert.assertEquals;

@Test
public class TestPalFormatterCore
{
    @Test()
    public void testEmptyString()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "",
                null);
        assertEquals(actualValue, Double.NaN);
    }

    @Test
    public void testSpacesOnly()
    {
        PalFormatterCore.tryParseDouble(
                "     ",
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        assertEquals(text, " ");
                        return null;
                    }
                });
    }

    @Test
    public void testUnprintableWhiteSpaces()
    {
        PalFormatterCore.tryParseDouble(
                "\r\t\n",
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        assertEquals(text, "\n");
                        return null;
                    }
                });
    }

    @DataProvider(name = "spaces")
    public Object[][] spaces()
    {
        return new Object[][]{
                {"   5"},
                {"5   "},
                {"   5    "},
        };
    }

    @Test(dataProvider = "spaces")
    public void testSpaces(String spaces)
    {
        PalFormatterCore.tryParseDouble(
                spaces,
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        assertEquals(text, "5");
                        return null;
                    }
                });
    }

    @Test
    public void testLeadingPlus()
    {
        PalFormatterCore.tryParseDouble("+2", null);
    }

    @DataProvider(name = "exponent")
    public Object[][] exponent()
    {
        return new Object[][]{
                {"2.5e+2"},
                {"2.5e2"},
                {"2.5E2"},
        };
    }

    @Test(dataProvider = "exponent")
    public void testExponent(String exponent)
    {
        PalFormatterCore.tryParseDouble(
                exponent,
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        assertEquals(text, "2.5E2");
                        return null;
                    }
                });
    }

    @Test
    public void testExponentWithCapitalEAndPlus()
    {
        PalFormatterCore.tryParseDouble(
                "2.5E+2",
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        assertEquals(text, "2.5E+2");
                        return null;
                    }
                });
    }

    @Test
    public void testExponentWithSpaceInTheMiddle()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "2.5E+ 2",
                new DecimalFormat());
        assertEquals(actualValue, Double.NaN);
    }

    @Test
    public void testDateAsNumber()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "15/10/2010",
                new DecimalFormat("##/##/####"));
        assertEquals(actualValue, Double.NaN);
    }

    @Test
    public void testIntegerNumber()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "25",
                new DecimalFormat("##"));
        assertEquals(actualValue, 25D);
    }

    @Test
    public void testDoubleNumberWithFixedFormat()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "144,38",
                new DecimalFormat("###,##"));
        assertEquals(actualValue, 14438D);
    }

    @Test
    public void testSilentParseOfDateByDecimalFormat()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "1 2/3",
                new DecimalFormat());
        assertEquals(actualValue, Double.NaN);
    }

    @Test
    public void testSolelyPlus()
    {
        double actualValue = PalFormatterCore.tryParseDouble("+", null);
        assertEquals(actualValue, Double.NaN);
    }

    @Test
    public void testDoubleNumberWithExponent()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "2.3E+2",
                new DecimalFormat());
        assertEquals(actualValue, 230D);
    }

    @Test
    public void testDecimalFormatThrowsException()
    {
        double actualValue = PalFormatterCore.tryParseDouble(
                "42",
                new DecimalFormat()
                {
                    @Override
                    public Number parse(String text, ParsePosition pos)
                    {
                        throw new IllegalStateException("Fail the test case.");
                    }
                });
        assertEquals(actualValue, Double.NaN);
    }
}
