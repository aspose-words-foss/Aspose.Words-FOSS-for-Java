// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTimeKind;
import org.testng.annotations.DataProvider;


@Test
public class TestPalFormatter
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        SystemPal.setTestMode(true);
        SystemPal.saveCulture();
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown()
    {
        SystemPal.restoreCulture();
    }

    @Test (dataProvider = "testDateTimeToXmlUtcDataProvider")
    public void testDateTimeToXmlUtc(String culture)
    {
        SystemPal.setCulture(culture);

        DateTime dateTime = new DateTime(2010, 12, 1, 9, 30, 11);
        msAssert.areEqual("2010-12-01T09:30:11Z", FormatterPal.dateTimeToXmlUtc(dateTime));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDateTimeToXmlUtcDataProvider")
	public static Object[][] testDateTimeToXmlUtcDataProvider()
	{
		return new Object[][]
		{
			{"en-NZ"},
			{"ru-RU"},
		};
	}

    @Test (dataProvider = "testDateTimeToXmlNoTimezoneDataProvider")
    public void testDateTimeToXmlNoTimezone(String culture)
    {
        SystemPal.setCulture(culture);

        DateTime dateTime = new DateTime(2010, 12, 1, 9, 30, 11);
        msAssert.areEqual("2010-12-01T09:30:11", FormatterPal.dateTimeToXmlNoTimezone(dateTime));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDateTimeToXmlNoTimezoneDataProvider")
	public static Object[][] testDateTimeToXmlNoTimezoneDataProvider()
	{
		return new Object[][]
		{
			{"en-NZ"},
			{"ru-RU"},
		};
	}

    @Test (dataProvider = "testDoubleToStrDataProvider")
    // On .NET scientific notation can occur for large numbers too. But it is harder to detect than for small numbers.
    // So I think is an acceptable differences in .NET vs Java formatting for very large numbers.
    public void testDoubleToStr(String result, double value)
    {
        msAssert.areEqual(result, FormatterPal.doubleToStr(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDoubleToStrDataProvider")
	public static Object[][] testDoubleToStrDataProvider()
	{
		return new Object[][]
		{
			{"0",  0.0},
			{"0",  -0.0},
			{"123",  123.0},
			{"0.7",  0.7},
			{"123456",  123456.0},
			{"123.456789012345",  123.456789012345},
			{"1234567890123.45",  1234567890123.45},
			{"123456789012345",  123456789012345.0},
			{"0.123456789012345",  0.123456789012345},
			{"0.000123456789012345",  0.000123456789012345},
			{"0.0001",  0.0001},
			{"-0.0001",  -0.0001},
			{"1E-05",  0.00001},
			{"-1E-05",  -0.00001},
			{"1.23456789012346E-07",  0.0000001234567890123456},
			{"1.23456789012345E-16",  0.000000000000000123456789012345},
			{"123456789012346",  123456789012345.6},
			{"1234567890123.46",  1234567890123.456},
			{"1234567890123460",  1234567890123455.0},
			{"1234567890123450",  1234567890123445.0},
		};
	}

    @Test (dataProvider = "testParseDoubleInvariantDataProvider")
    public void testParseDoubleInvariant(double result, String value)
    {
        msAssert.areEqual(result, FormatterPal.parseDouble(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testParseDoubleInvariantDataProvider")
	public static Object[][] testParseDoubleInvariantDataProvider()
	{
		return new Object[][]
		{
			{0.0,  ""},
			{0.0,  "   "},
			{0.0,  "abc"},
			{0.0,  "1,2"},
			{0.0,  "1;2"},
			{0.0,  "1 2"},
			{0.0,  "1 2/3"},
			{0.0,  "1/02/2005"},
			{5.0,  "5"},
			{1.1,  " 1.1"},
			{1.2,  " \t1.2 \t"},
			{1.3,  " +1.3"},
			{-1.3,  " -1.3"},
			{-0.0000123,  " -1.23E-05  "},
			{-0.0123,  " -1.23e-02  "},
			{0.0000123,  " 1.23E-05"},
			{0.0123,  " 1.23e-02"},
		};
	}

    @Test (dataProvider = "testDoubleToStrNDecimalsDataProvider")
    public void testDoubleToStrNDecimals(String result, int n)
    {
        final double VALUE = 12345.1234567891;
        msAssert.areEqual(result, FormatterPal.doubleToStrNDecimals(VALUE, n));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDoubleToStrNDecimalsDataProvider")
	public static Object[][] testDoubleToStrNDecimalsDataProvider()
	{
		return new Object[][]
		{
			{"12345",  0},
			{"12345.1",  1},
			{"12345.12",  2},
			{"12345.123",  3},
			{"12345.1235",  4},
			{"12345.12346",  5},
			{"12345.123457",  6},
			{"12345.1234568",  7},
			{"12345.12345679",  8},
			{"12345.123456789",  9},
		};
	}

    @Test (dataProvider = "testFloatToStrNDecimalsDataProvider")
    public void testFloatToStrNDecimals(String result, int n)
    {
        final float VALUE = 12345.123456F;
        msAssert.areEqual(result, FormatterPal.floatToStrNDecimals(VALUE, n));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFloatToStrNDecimalsDataProvider")
	public static Object[][] testFloatToStrNDecimalsDataProvider()
	{
		return new Object[][]
		{
			{"12345",  0},
			{"12345.1",  1},
			{"12345.12",  2},
			{"12345.123",  3},
			{"12345.123",  4},
			{"12345.123",  5},
			{"12345.123",  6},
			{"12345.123",  7},
			{"12345.123",  8},
			{"12345.123",  9},
		};
	}

    @Test (dataProvider = "testDoubleToStr2DecimalsDataProvider")
    public void testDoubleToStr2Decimals(String result, double value)
    {
        msAssert.areEqual(result, FormatterPal.doubleToStr2Decimals(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDoubleToStr2DecimalsDataProvider")
	public static Object[][] testDoubleToStr2DecimalsDataProvider()
	{
		return new Object[][]
		{
			{"0",  0.0},
			{"123",  123.0},
			{"123.46",  123.456789012345},
			{"1234567890123.45",  1234567890123.45},
			{"123456789012345",  123456789012345.0},
			{"0.12",  0.123456789012345},
			{"0",  0.000123456789012345},
			{"0",  0.0001},
			{"0",  -0.0001},
			{"0",  0.00001},
			{"0",  -0.00001},
			{"0",  0.0000001234567890123456},
			{"0",  0.000000000000000123456789012345},
			{"123456789012346",  123456789012345.6},
			{"1234567890123.46",  1234567890123.456},
			{"1234567890123460",  1234567890123455.0},
			{"1234567890123450",  1234567890123445.0},
		};
	}

    @Test
    public void testIntToStrX()
    {
        msAssert.areEqual("0", FormatterPal.intToStrX(0));
        msAssert.areEqual("1A0F", FormatterPal.intToStrX(0x1a0f));
        msAssert.areEqual("FFFFFFFF", FormatterPal.intToStrX(/*unchecked*/((int)(int) 0xffffffffL)));

        msAssert.areEqual("ffff00ff", FormatterPal.intToStrXLower(/*unchecked*/((int)(int) 0xffff00ffL)));

        msAssert.areEqual("00", FormatterPal.intToStrX2(0));
        // These do not truncate to the required length. Should they truncate or not?
        msAssert.areEqual("1A0F", FormatterPal.intToStrX2(0x1a0f));
        msAssert.areEqual("FFFFFFFF", FormatterPal.intToStrX2(/*unchecked*/((int)(int) 0xffffffffL)));

        msAssert.areEqual("00", FormatterPal.intToStrX2Lower(0));
        msAssert.areEqual("beef", FormatterPal.intToStrX2Lower(0xbeef));

        msAssert.areEqual("00000000", FormatterPal.intToStrX8(0));
        msAssert.areEqual("0000BEEF", FormatterPal.intToStrX8(0xbeef));
        msAssert.areEqual("FFFFFFFF", FormatterPal.intToStrX8(/*unchecked*/((int)(int) 0xffffffffL)));
    }

    @Test
    public void testInt64ToStrX()
    {
        msAssert.areEqual("0", FormatterPal.int64ToStrX(0));
        msAssert.areEqual("1A0F", FormatterPal.int64ToStrX(0x1a0f));
        msAssert.areEqual("FFFFFFFFFFFFFFFF", FormatterPal.int64ToStrX(/*unchecked*/((long)0xffffffffffffffffL)));

        msAssert.areEqual("00000000", FormatterPal.int64ToStrX8(0));
        msAssert.areEqual("0000BEEF", FormatterPal.int64ToStrX8(0xbeef));
        msAssert.areEqual("FFFFFFFF", FormatterPal.int64ToStrX8(0xffffffffL));
        msAssert.areEqual("FFFFFFFFFFFFFFFF", FormatterPal.int64ToStrX8(/*unchecked*/((long)0xffffffffffffffffL)));
    }

    @Test
    public void testIntToStr()
    {
        // IntToStr
        msAssert.areEqual("0", FormatterPal.intToStr(0));
        msAssert.areEqual("0", FormatterPal.intToStr(-0));
        msAssert.areEqual("123", FormatterPal.intToStr(123));

        // IntToStrD2
        msAssert.areEqual("00", FormatterPal.intToStrD2(0));
        msAssert.areEqual("00", FormatterPal.intToStrD2(-0));
        msAssert.areEqual("01", FormatterPal.intToStrD2(1));
        msAssert.areEqual("123", FormatterPal.intToStrD2(123));

        // IntToStrD4
        msAssert.areEqual("0000", FormatterPal.intToStrD4(0));
        msAssert.areEqual("0000", FormatterPal.intToStrD4(-0));
        msAssert.areEqual("0123", FormatterPal.intToStrD4(123));
        msAssert.areEqual("12345678", FormatterPal.intToStrD4(12345678));

        // IntToStrD10
        msAssert.areEqual("0000000000", FormatterPal.intToStrD10(0));
        msAssert.areEqual("0000000000", FormatterPal.intToStrD10(-0));
        msAssert.areEqual("0123456789", FormatterPal.intToStrD10(123456789));
    }

    @Test (dataProvider = "testGetCurrentThreadFormatsDataProvider")
    public void testGetCurrentThreadFormats(
        String culture,
        char groupSeparator,
        String currency,
        char decimalSeparator,
        String shortDatePattern,
        String shortTimePattern)
    {
        SystemPal.setCulture(culture);

        msAssert.areEqual(groupSeparator, FormatterPal.getNumberGroupSeparatorCurrent());
        msAssert.areEqual(currency, FormatterPal.getCurrencySymbolCurrent());
        msAssert.areEqual(decimalSeparator, FormatterPal.getDecimalSeparatorCurrent());
        msAssert.areEqual(shortDatePattern, FormatterPal.getShortDatePatternCurrent());
        msAssert.areEqual(shortTimePattern, FormatterPal.getShortTimePatternCurrent());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGetCurrentThreadFormatsDataProvider")
	public static Object[][] testGetCurrentThreadFormatsDataProvider()
	{
		return new Object[][]
		{
			{"en-NZ",  ',',  "$",  '.',  "d/MM/yyyy",  "h:mm am/pm"},
			{"uk-UA",  '\u00a0',  GRIVNA,  ',',  "dd.MM.yyyy",  UKRAINE_SHORT_TIME_PATTERN},
		};
	}

    @Test (dataProvider = "testTryParseCurrencyCurrentNZDDataProvider")
    public void testTryParseCurrencyCurrentNZD(double result, String value)
    {
        msAssert.areEqual(result, FormatterPal.tryParseCurrencyCurrent(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTryParseCurrencyCurrentNZDDataProvider")
	public static Object[][] testTryParseCurrencyCurrentNZDDataProvider()
	{
		return new Object[][]
		{
			{Double.NaN,  "abcd"},
			{Double.NaN,  "$$123"},
			{Double.NaN,  "123.45р."},
			{Double.NaN,  "123 45"},
			{123456789.23,  "123,456,,789.23"},
			{-123.45,  "   -$123.45  "},
			{0.123456789,  ".123456789"},
		};
	}

    @Test (dataProvider = "testTryParseCurrencyCurrentUADataProvider")
    // This is what I think an acceptable difference on Java.
    // Java refuses to parse this value because it does not accept a double space group separator.
    // This is what I think an acceptable difference on Java.
    // Java parser fails on spare spaces.
    public void testTryParseCurrencyCurrentUA(double result, String value)
    {
        SystemPal.setCulture("uk-UA");

        msAssert.areEqual(result, FormatterPal.tryParseCurrencyCurrent(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTryParseCurrencyCurrentUADataProvider")
	public static Object[][] testTryParseCurrencyCurrentUADataProvider()
	{
		return new Object[][]
		{
			{Double.NaN,  "abcd"},
			{Double.NaN,  "$$123"},
			{Double.NaN,  "$123"},
			{123456789.23,  "123\u00a0456\u00a0\u00a0789,23"},
			{Double.NaN,  "123 456  789,23"},
			{-123.45,  "   -123,45" + GRIVNA_SUFFIX + "  "},
			{123456.78,  "123456,78 ₴"},
			{0.123456789,  ",123456789" + GRIVNA_SUFFIX},
		};
	}

    @Test
    public void testTryParseDateTimeCurrent()
    {
        DateTime expected = new DateTime(1998, 10, 21);
        //Java doesn't support format "YYYY/MM/DD" out of box.
        DateTime parsed = FormatterPal.tryParseDateTimeCurrent("1998/10/21");
        msAssert.areEqual(expected, parsed);
    }

    @Test
    public void testTryParseDateInvariant()
    {
        DateTime expected = new DateTime(1926, 12, 25);
        DateTime parsed = FormatterPal.tryParseDateTimeInvariant("25 Dec 1926");
        msAssert.areEqual(expected, parsed);
    }

    @Test (dataProvider = "testNumberToStrMSWordWithNoFormatDataProvider")
    public void testNumberToStrMSWordWithNoFormat(String result, double value, boolean isCurrency, boolean isUsesGroupSeparator, int numberOfDigitsAfterDecimalpoint)
    {
        // RK This test used to have "ru-RU" culture, but under Windows 8 it behaves very strangely.
        // This test is supposed to be indifferent to the current culture set on the developer's machine,
        // but having Russian as the current culture was affecting the output e.g. "100 р." vs "100р.".
        // So I changed to use Ukrainian. It has same formatting mostly so I kept the code of the test the same, it just does not have the glitch.
        SystemPal.setCulture("uk-UA");

        String actual = FormatterPal.numberToStrMSWordWithNoFormat(value, isCurrency, isUsesGroupSeparator, numberOfDigitsAfterDecimalpoint, false);

        msAssert.areEqual(result, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberToStrMSWordWithNoFormatDataProvider")
	public static Object[][] testNumberToStrMSWordWithNoFormatDataProvider()
	{
		return new Object[][]
		{
			{"123456,12" + GRIVNA_SUFFIX,  123456.123456,  true,  false,  2},
			{"-123456,12" + GRIVNA_SUFFIX,  -123456.123456,  true,  false,  2},
			{"0,00" + GRIVNA_SUFFIX,  0.0,  true,  false,  2},
			{"123\u00a0456,12" + GRIVNA_SUFFIX,  123456.123456,  true,  true,  2},
			{"-123456,123456",  -123456.123456,  false,  false,  9},
			{"0,0",  0.0,  false,  false,  9},
			{"0,0000001",  0.0000001,  false,  false,  9},
			{"-123\u00a0456,123456",  -123456.123456,  false,  true,  9},
			{"0,0",  0.0,  false,  true,  9},
			{"0,0000001",  0.0000001,  false,  true,  9},
		};
	}

    @Test (dataProvider = "testNumberToStrMSWordWithLocalizedFormatDataProvider")
    public void testNumberToStrMSWordWithLocalizedFormat(
        String cultureName,
        double value,
        String format,
        /*NumberFormattingOptions*/int options,
        String result)
    {
        SystemPal.setCulture(cultureName);

        String actual = FormatterPal.numberToStrMSWord(value, format, options);

        msAssert.areEqual(result, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberToStrMSWordWithLocalizedFormatDataProvider")
	public static Object[][] testNumberToStrMSWordWithLocalizedFormatDataProvider()
	{
		return new Object[][]
		{
			{"ru-RU",  123456.123456,  "#\u00a0##0,##",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12"},
			{"ru-RU",  123456.123456,  "# ##0,##",  NumberFormattingOptions.DEFAULT,  "123 456,12"},
			{"ru-RU",  123456.123456,  "#\u00a0##0,##%",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12%"},
			{"ru-RU",  123456.123456,  "#\u00a0##0,##'%'",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12%"},
			{"ru-RU",  123456.123456,  "#\u00a0##0,##%",  NumberFormattingOptions.IS_MULTIPLY_PERCENT,  "12\u00a0345\u00a0612,35%"},
			{"ru-RU",  123456.123456,  "#\u00a0##0,##'%'",  NumberFormattingOptions.IS_MULTIPLY_PERCENT,  "123\u00a0456,12%"},
			{"ru-RU",  19791105,  "####/##/##",  NumberFormattingOptions.DEFAULT,  "1979/11/05"},
			{"ru-RU",  19791105,  "0000/00/00",  NumberFormattingOptions.DEFAULT,  "1979/11/05"},
			{"ru-RU",  38527720206L,  "8 (###) ###'-'##'-'###",  NumberFormattingOptions.DEFAULT,  "8 (385) 277-20-206"},
			{"ru-RU",  1.01,  "$###0.00",  NumberFormattingOptions.DEFAULT,  "$   0.01"},
			{"ru-RU",  1,  "#.",  NumberFormattingOptions.DEFAULT,  "1."},
			{"de-AT",  123456.123456,  "#\u00a0##0,##",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12"},
			{"de-AT",  123456.123456,  "# ##0,##",  NumberFormattingOptions.DEFAULT,  "123 456,12"},
			{"de-AT",  123456.123456,  "#\u00a0##0,##%",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12%"},
			{"de-AT",  123456.123456,  "#\u00a0##0,##'%'",  NumberFormattingOptions.DEFAULT,  "123\u00a0456,12%"},
			{"de-AT",  123456.123456,  "#\u00a0##0,##%",  NumberFormattingOptions.IS_MULTIPLY_PERCENT,  "12\u00a0345\u00a0612,35%"},
			{"de-AT",  123456.123456,  "#\u00a0##0,##'%'",  NumberFormattingOptions.IS_MULTIPLY_PERCENT,  "123\u00a0456,12%"},
			{"de-AT",  19791105,  "####/##/##",  NumberFormattingOptions.DEFAULT,  "1979/11/05"},
			{"de-AT",  19791105,  "0000/00/00",  NumberFormattingOptions.DEFAULT,  "1979/11/05"},
			{"de-AT",  38527720206L,  "8 (###) ###'-'##'-'###",  NumberFormattingOptions.DEFAULT,  "8 (385) 277-20-206"},
			{"de-AT",  1.01,  "$###0.00",  NumberFormattingOptions.DEFAULT,  "$   0.01"},
			{"de-AT",  1,  "#.",  NumberFormattingOptions.DEFAULT,  "1."},
		};
	}

    @Test (dataProvider = "testNumberToStrMSWordWithLocalizedFormatWithLegacyNumberFormatDataProvider")
    public void testNumberToStrMSWordWithLocalizedFormatWithLegacyNumberFormat(
        String result,
        double value,
        String format)
    {
        msAssert.areEqual(result, FormatterPal.numberToStrMSWord(value, format, NumberFormattingOptions.LEGACY_NUMBER_FORMAT));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberToStrMSWordWithLocalizedFormatWithLegacyNumberFormatDataProvider")
	public static Object[][] testNumberToStrMSWordWithLocalizedFormatWithLegacyNumberFormatDataProvider()
	{
		return new Object[][]
		{
			{"0",  0,  ",0;(,0);0"},
			{"0.0",  0,  ",0;(,0);0.0"},
			{"(0)%",  0,  ",0;(,0);(,0.##)%"},
			{"(1)",  -1,  "#,###;(#,###);0"},
			{"1%",  1,  ",0.##%;(,0.##)%;0"},
		};
	}

    @Test
    public void testJiraJ1010()
    {
        msAssert.areEqual("-", FormatterPal.numberToStrMSWord(0.0, ",0;(,0);'-'", NumberFormattingOptions.DEFAULT));
    }

    @Test (dataProvider = "testNumberToStrMSWordWithLocalizedFormatWithInvariantCultureNumberFormatDataProvider")
    public void testNumberToStrMSWordWithLocalizedFormatWithInvariantCultureNumberFormat(
        String result,
        double value)
    {
        SystemPal.setCulture("nl-NL");

        msAssert.areEqual(result, FormatterPal.numberToStrMSWord(value, "0.00", NumberFormattingOptions.FORMAT_IS_IN_INVARIANT_CULTURE));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberToStrMSWordWithLocalizedFormatWithInvariantCultureNumberFormatDataProvider")
	public static Object[][] testNumberToStrMSWordWithLocalizedFormatWithInvariantCultureNumberFormatDataProvider()
	{
		return new Object[][]
		{
			{"10,00",  10},
			{"3,00",  3},
		};
	}

    @Test (dataProvider = "testJira939DataProvider")
    public void testJira939(String result, double value, String format)
    {
        msAssert.areEqual(result, FormatterPal.numberToStrMSWord(value, format, NumberFormattingOptions.DEFAULT));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira939DataProvider")
	public static Object[][] testJira939DataProvider()
	{
		return new Object[][]
		{
			{"01/02/2014",  1022014.0,  "0#/##/####"},
			{" 1/02/2014",  1022014.0,  "##/##/####"},
			{"01/02/2014",  1022014.0,  "00/00/0000"},
			{"01022014",  1022014.0,  "0#######"},
			{"01022014",  1022014.0,  "00000000"},
		};
	}

    @Test (dataProvider = "testStrToHexDataProvider")
    public void testStrToHex(int result, String value)
    {
        msAssert.areEqual(4871352, FormatterPal.tryParseHex("004A54B8"));
        msAssert.areEqual(Integer.MIN_VALUE, FormatterPal.tryParseHex("00F73CVUM"));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStrToHexDataProvider")
	public static Object[][] testStrToHexDataProvider()
	{
		return new Object[][]
		{
			{4871352,  "004A54B8"},
			{Integer.MIN_VALUE,  "00F73CVUM"},
		};
	}

    @Test (dataProvider = "testTryParseIntPortionDataProvider")
    public void testTryParseIntPortion(int result, String value)
    {
        msAssert.areEqual(658, FormatterPal.tryParseIntPortion("658abe343nc"));
        msAssert.areEqual(-100, FormatterPal.tryParseIntPortion("-100"));
        msAssert.areEqual(Integer.MIN_VALUE, FormatterPal.tryParseIntPortion("a-100"));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTryParseIntPortionDataProvider")
	public static Object[][] testTryParseIntPortionDataProvider()
	{
		return new Object[][]
		{
			{658,  "658abe343nc"},
			{-100,  "-100"},
			{Integer.MIN_VALUE,  "a-100"},
		};
	}

    @Test (dataProvider = "testNumberToStrMSWordWithLocalizedFormatWithInvalidFormatStringDataProvider")
    public void testNumberToStrMSWordWithLocalizedFormatWithInvalidFormatString(String result, String format)
    {
        msAssert.areEqual(result, FormatterPal.numberToStrMSWord(1234567.89, format, NumberFormattingOptions.DEFAULT));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberToStrMSWordWithLocalizedFormatWithInvalidFormatStringDataProvider")
	public static Object[][] testNumberToStrMSWordWithLocalizedFormatWithInvalidFormatStringDataProvider()
	{
		return new Object[][]
		{
			{"1,234,567.89",  ",0.00"},
			{"1,234,567.89",  ",#.00"},
			{"1,234,568",  ",0"},
			{"1234567.89",  ""},
		};
	}

    /// <summary>
    /// Tests that XmlToDateTimeExact always returns UTC time.
    /// </summary>
    @Test (dataProvider = "testXmlToDateTimeExactDataProvider")
    public void testXmlToDateTimeExact(String value)
    {
        msAssert.areEqual(DateTimeKind.UTC, FormatterPal.xmlToDateTimeExact(value).getKind());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testXmlToDateTimeExactDataProvider")
	public static Object[][] testXmlToDateTimeExactDataProvider()
	{
		return new Object[][]
		{
			{"2013-04-02T06:52:20.9977400-06:00"},
			{"2013-04-02T06:52:20.9977400"},
		};
	}

    @Test
    public void testXmlToDateTimeExactParsingInvalid()
    {
        msAssert.areEqual(DateTime.MinValue, FormatterPal.xmlToDateTimeExact("invalid date"));
    }

    @Test (dataProvider = "testXmlToDateTimeExactParsingDataProvider")
    public void testXmlToDateTimeExactParsing(String value)
    {
        msAssert.areNotEqual(DateTime.MinValue, FormatterPal.xmlToDateTimeExact(value));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testXmlToDateTimeExactParsingDataProvider")
	public static Object[][] testXmlToDateTimeExactParsingDataProvider()
	{
		return new Object[][]
		{
			{"2014-07-11T15:05:28.4647142Z"},
			{"2014-07-11T15:05:28.4647142+00:00"},
			{"2013-04-02T06:52:20.9977400-06:00"},
			{"2013-04-02T06:52:20.9977400"},
		};
	}

    /// <summary>
    /// WORDSNET-10143 ArgumentExceptionOutOfRangeException occurs when MailMerge.Execute is called after specifying nl-NL culture
    /// </summary>
    @Test (dataProvider = "testDefect10143DataProvider")
    public void testDefect10143(int numberOfDigits, String expected)
    {
        SystemPal.setCulture("nl-NL");

        final double VALUE = -2.7789130722454869533128359829005491862E01;

        String actual = FormatterPal.numberToStrMSWordWithNoFormat(VALUE, false, false, numberOfDigits, false);
        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDefect10143DataProvider")
	public static Object[][] testDefect10143DataProvider()
	{
		return new Object[][]
		{
			{40,  "-27,7891307224549"},
			{31,  "-27,7891307224549"},
			{24,  "-27,7891307224549"},
			{16,  "-27,7891307224549"},
			{15,  "-27,7891307224549"},
			{14,  "-27,7891307224549"},
			{6,  "-27,789131"},
			{3,  "-27,789"},
			{2,  "-27,79"},
			{0,  "-28"},
		};
	}

    /// <summary>
    /// WORDSNET-10165 Negative values are prefixed with space
    /// </summary>
    @Test (dataProvider = "testDefect10165DataProvider")
    public void testDefect10165(String result, double value, String format)
    {
        msAssert.areEqual(result, FormatterPal.numberToStrMSWord(value, format, NumberFormattingOptions.DEFAULT));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDefect10165DataProvider")
	public static Object[][] testDefect10165DataProvider()
	{
		return new Object[][]
		{
			{"( 1)",  -1,  ",0;(#,0);0"},
			{"1,000.00",  1000,  ",0.00"},
			{"(1)",  -1,  ",0;(,0);0"},
			{"(1.00)",  -1,  ",0;(,0.00);0"},
			{"(1,512)",  -1512,  ",0;(,0);0"},
			{"(1,246)",  1246,  "(,0)"},
		};
	}

    /// <summary>
    /// WORDSJAVA-946 - Charts in Docx xml markup: roundtrip of number with big precision in scientific format.
    /// Check bugfix in NumberFormatter
    /// </summary>
    @Test
    public void testDoubleRoundtripFormat()
    {
        msAssert.areEqual("1.0000000000000005E-08", FormatterPal.doubleToStrRoundtrip(1.0000000000000005E-8));
    }

    @Test
    public void testIsInteger()
    {
        // Java takes care about overflow, whereas .NET considers very big numbers outside the range [int.MinValue,
        // int.MaxValue] as a valid value (because uses Double.TryParse() internally).

        // So, FormatterPal.IsInteger() returns FALSE in Java and TRUE in .NET for all numbers outside of Integer
        // borders [int.MinValue, int.MaxValue]. In all other cases Java strive to copy .NET behaviour.


        long val = (long)Integer.MIN_VALUE;
        msAssert.isTrue(FormatterPal.isInteger(Long.toString(val)));
        val = (long)Integer.MIN_VALUE + 1;
        msAssert.isTrue(FormatterPal.isInteger(Long.toString(val)));
        // Outside Integer borders: false for Java but true for .Net:
        val = (long)Integer.MIN_VALUE - 1;
        msAssert.isFalse(FormatterPal.isInteger(Long.toString(val)));

        val = (long)Integer.MAX_VALUE;
        msAssert.isTrue(FormatterPal.isInteger(Long.toString(val)));
        val = (long)Integer.MAX_VALUE - 1;
        msAssert.isTrue(FormatterPal.isInteger(Long.toString(val)));
        // Outside Integer borders: false for Java but true for .Net:
        val = (long)Integer.MAX_VALUE + 1;
        msAssert.isFalse(FormatterPal.isInteger(Long.toString(val)));

        msAssert.isFalse(FormatterPal.isInteger("++0"));
        msAssert.isFalse(FormatterPal.isInteger("--0"));
        msAssert.isFalse(FormatterPal.isInteger("0a"));
        msAssert.isFalse(FormatterPal.isInteger(" 2 0"));
        msAssert.isFalse(FormatterPal.isInteger(" -2 0"));
        msAssert.isFalse(FormatterPal.isInteger("\t\r\n -2 0 \n\r\t"));
        msAssert.isFalse(FormatterPal.isInteger("-a"));
        msAssert.isFalse(FormatterPal.isInteger("a"));


        msAssert.isTrue(FormatterPal.isInteger(" 2"));
        msAssert.isTrue(FormatterPal.isInteger("\t20"));
        msAssert.isTrue(FormatterPal.isInteger(" 20 "));
        msAssert.isTrue(FormatterPal.isInteger(" -20 "));
        msAssert.isTrue(FormatterPal.isInteger("\t\r\n -20 \n\r\t"));
        msAssert.isTrue(FormatterPal.isInteger("20"));
        msAssert.isTrue(FormatterPal.isInteger("+20"));
        msAssert.isTrue(FormatterPal.isInteger("-20"));

        msAssert.isTrue(FormatterPal.isInteger("123456"));
        msAssert.isTrue(FormatterPal.isInteger("+123456"));
        msAssert.isTrue(FormatterPal.isInteger("-123456"));
    }

    @Test (dataProvider = "testNumberFormatDropOptionDataProvider")
    public void testNumberFormatDropOption(double value, String format, String expected)
    {
        String actual = FormatterPal.numberToStrMSWord(
            value,
            format,
            NumberFormattingOptions.FORMAT_IS_IN_INVARIANT_CULTURE);
        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberFormatDropOptionDataProvider")
	public static Object[][] testNumberFormatDropOptionDataProvider()
	{
		return new Object[][]
		{
			{1,  "x###",  "   1"},
			{12,  "x###",  "  12"},
			{123,  "x###",  " 123"},
			{1234,  "x###",  "1234"},
			{12345,  "x###",  "2345"},
			{123456,  "x###",  "3456"},
			{.123456789,  "x###",  "    "},
			{1.23456789,  "x###",  "   1"},
			{12.3456789,  "x###",  "  12"},
			{123.456789,  "x###",  " 123"},
			{1234.56789,  "x###",  "1235"},
			{12345.6789,  "x###",  "2346"},
			{123456.789,  "x###",  "3457"},
			{1234567.89,  "x###",  "4568"},
			{12345678.9,  "x###",  "5679"},
			{123456789.9,  "x###",  "6790"},
			{.123456789,  "x###.#",  "    .1"},
			{1.23456789,  "x###.#",  "   1.2"},
			{12.3456789,  "x###.#",  "  12.3"},
			{123.456789,  "x###.#",  " 123.5"},
			{1234.56789,  "x###.#",  "1234.6"},
			{12345.6789,  "x###.#",  "2345.7"},
			{123456.789,  "x###.#",  "3456.8"},
			{1234567.89,  "x###.#",  "4567.9"},
			{12345678.9,  "x###.#",  "5678.9"},
			{.123456789,  "###.##x",  "   .123"},
			{1.23456789,  "###.##x",  "  1.235"},
			{12.3456789,  "###.##x",  " 12.346"},
			{123.456789,  "###.##x",  "123.457"},
			{1234.56789,  "###.##x",  "1234.568"},
			{12345.6789,  "###.##x",  "12345.679"},
			{123456.789,  "###.##x",  "123456.789"},
			{1,  "'XXXX'x#####",  "XXXX     1"},
			{123456789,  "'XXXX'x#####",  "XXXX456789"},
		};
	}

    @Test (dataProvider = "testNumberFormatWithBackslashesDataProvider")
    public void testNumberFormatWithBackslashes(String format, String expected)
    {
        String actual = FormatterPal.numberToStrMSWord(
            1234.0,
            format,
            NumberFormattingOptions.FORMAT_IS_IN_INVARIANT_CULTURE);

        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testNumberFormatWithBackslashesDataProvider")
	public static Object[][] testNumberFormatWithBackslashesDataProvider()
	{
		return new Object[][]
		{
			{"\\#",  "\\1234"},
			{"\\\\#",  "\\\\1234"},
			{"\\\\\\#",  "\\\\\\1234"},
			{"#\\",  "1234\\"},
			{"#\\\\",  "1234\\\\"},
			{"#\\\\\\",  "1234\\\\\\"},
			{"\\#\\",  "\\1234\\"},
			{"\\\\#\\\\",  "\\\\1234\\\\"},
			{"\\\\\\#\\\\\\",  "\\\\\\1234\\\\\\"},
			{"\\q\\w\\e\\r#",  "\\q\\w\\e\\r1234"},
			{"#\\q\\w\\e\\r",  "1234\\q\\w\\e\\r"},
			{"\\q#\\w#\\e#\\r#",  "\\q1\\w2\\e3\\r4"},
		};
	}

    @Test
    public void testNumberFormatIgnoreUnmatchedDigitPlaceholder()
    {
        String actual = FormatterPal.numberToStrMSWord(
            478.0,
            "#,###",
            NumberFormattingOptions.IGNORE_UNMATCHED_DIGIT_PLACEHOLDER);

        msAssert.areEqual("478", actual);
    }

    private static final String GRIVNA = "₴";
    // White space between number and grivna international symbol.
    private static final String GRIVNA_SUFFIX = " " + GRIVNA;

    private static final String UKRAINE_SHORT_TIME_PATTERN = "H:mm";
}

