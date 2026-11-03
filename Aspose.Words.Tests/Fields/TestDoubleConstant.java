// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/11/2012 by Alexander Maslov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.FormatterPal;
import com.aspose.SystemPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests DoubleConstant behavior.
/// </summary>
@Test
public class TestDoubleConstant extends TestFieldsBase
{
    @Test
    public void testTryParse()
    {
        msAssert.areEqual(null, DoubleConstant.tryParse(""));
        msAssert.areEqual(null, DoubleConstant.tryParse(null));

        checkDoubleConstantParsing("0", 0.0, false, false);
        checkDoubleConstantParsing("1", 1.0, false, false);
        checkDoubleConstantParsing("-1", -1.0, false, false);
        checkDoubleConstantParsing("1,000.00", 1000.0, true, false);
        checkDoubleConstantParsing("-1,000.00", -1000.0, true, false);

        checkDoubleConstantParsing("123.45e+2", 12345.0, false, false);
        checkDoubleConstantParsing("0.12345e+5", 12345.0, false, false);
        checkDoubleConstantParsing("-0.12345e+5", -12345.0, false, false);
        checkDoubleConstantParsing("123.45e-2", 1.23450, false, false);

        checkParsingCurrencySymbolBefore(
            FormatterPal.getCurrencySymbolCurrent(),
            FormatterPal.getDecimalSeparatorCurrent(),
            FormatterPal.getNumberGroupSeparatorCurrent());

        SystemPal.setCulture("ru-RU");

        checkParsingCurrencySymbolAfter(
            FormatterPal.getCurrencySymbolCurrent(),
            FormatterPal.getDecimalSeparatorCurrent(),
            FormatterPal.getNumberGroupSeparatorCurrent());

        SystemPal.setCulture("fr-CH");

        // DV WORDSNET-17864 Supported by .NET 4.0 and above.

        checkParsingCurrencySymbolAfter(
            "CHF",
            FormatterPal.getDecimalSeparatorCurrent(),
            FormatterPal.getNumberGroupSeparatorCurrent());

        // DV WORDSNET-17864 Unsupported by .NET 4.0 and above natively, see FormatterPal.NormalizeCurrencySymbols.

        checkParsingCurrencySymbolBefore(
            "SFr.",
            FormatterPal.getDecimalSeparatorCurrent(),
            FormatterPal.getNumberGroupSeparatorCurrent());

        checkParsingCurrencySymbolBefore(
            "fr.",
            FormatterPal.getDecimalSeparatorCurrent(),
            FormatterPal.getNumberGroupSeparatorCurrent());
    }

    private static void checkDoubleConstantParsing(String inputString, double expectedValue, boolean isUsesGroupSeparator, boolean isCurrency)
    {
        DoubleConstant actual = DoubleConstant.tryParse(inputString);

        msAssert.areEqual(expectedValue, actual.getValueDouble());
        msAssert.areEqual(isUsesGroupSeparator, actual.isUsesGroupSeparator());
        msAssert.areEqual(isCurrency, actual.isCurrency());
    }

    private static void checkParsingCurrencySymbolBefore(String currensySymbol, char decimalSeparator, char groupSeparator)
    {
        checkDoubleConstantParsing(msString.format("{0}0", currensySymbol), 0.0, false, true);
        checkDoubleConstantParsing(msString.format("{0}1", currensySymbol), 1.0, false, true);
        checkDoubleConstantParsing(msString.format("-{0}1", currensySymbol), -1.0, false, true);
        checkDoubleConstantParsing(msString.format("{0}1000", currensySymbol), 1000.0, false, true);
        checkDoubleConstantParsing(msString.format("{0} 1000{1}00", currensySymbol, decimalSeparator), 1000.0, false, true);
        checkDoubleConstantParsing(msString.format("{0}1{2}000{1}00", currensySymbol, decimalSeparator, groupSeparator), 1000.0, true, true);
        checkDoubleConstantParsing(msString.format("{0} 1{2}000{1}00", currensySymbol, decimalSeparator, groupSeparator), 1000.0, true, true);
        checkDoubleConstantParsing(msString.format("-{0}1{2}000{1}00", currensySymbol, decimalSeparator, groupSeparator), -1000.0, true, true);
        checkDoubleConstantParsing(msString.format("- {0}1{2}000{1}00", currensySymbol, decimalSeparator, groupSeparator), -1000.0, true, true);
    }

    private static void checkParsingCurrencySymbolAfter(String currensySymbol, char decimalSeparator, char groupSeparator)
    {
        checkDoubleConstantParsing(msString.format("0{0}", currensySymbol), 0.0, false, true);
        checkDoubleConstantParsing(msString.format("1{0}", currensySymbol), 1.0, false, true);
        checkDoubleConstantParsing(msString.format("-1{0}", currensySymbol), -1.0, false, true);
        checkDoubleConstantParsing(msString.format("1000{0}", currensySymbol), 1000.0, false, true);
        checkDoubleConstantParsing(msString.format("1000{1}00 {0}", currensySymbol, decimalSeparator), 1000.0, false, true);
        checkDoubleConstantParsing(msString.format("1{2}000{1}00{0}", currensySymbol, decimalSeparator, groupSeparator), 1000.0, true, true);
        checkDoubleConstantParsing(msString.format("1{2}000{1}00 {0}", currensySymbol, decimalSeparator, groupSeparator), 1000.0, true, true);
        checkDoubleConstantParsing(msString.format("-1000{1}00 {0}", currensySymbol, decimalSeparator), -1000.0, false, true);
        checkDoubleConstantParsing(msString.format("- 1{2}000{1}00{0}", currensySymbol, decimalSeparator, groupSeparator), -1000.0, true, true);
    }

    //JAVA: method is internalized
	/*@Test*/
    int testNumberOfDigitsAfterDecimalpoint(double value, String culture)
    {
        SystemPal.setCulture(culture);
        DoubleConstant constant = new DoubleConstant(value);
        return constant.getNumberOfDigitsAfterDecimalPoint();
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testNumberOfDigitsAfterDecimalpointSourceDataProvider")
	public void testNumberOfDigitsAfterDecimalpointSource(double value, String culture, int result)
	{
		Assert.assertEquals(testNumberOfDigitsAfterDecimalpoint(value, culture), result);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testNumberOfDigitsAfterDecimalpointSourceDataProvider")
	Object[][] testNumberOfDigitsAfterDecimalpointSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = getTestNumberOfDigitsAfterDecimalpointCaseSource().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			Object[] data = new Object[arguments.length + 1];
			for (int i=0; i<arguments.length; i++)
				data[i] = arguments[i];
			data[arguments.length] = testCaseData.getResult();
			testCaseSources.add(data);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    public static Iterable<TestCaseData> getTestNumberOfDigitsAfterDecimalpointCaseSource()
    {
        double[] values = { 321.0, 34535.4, 84.67, 0.934, 74.3975 };
        String[] cultures = { "en-US", "en-NZ", "ru-RU", "de-DE" };
        ArrayList<TestCaseData> testCaseSource = new ArrayList<TestCaseData>();
        for (String culture : cultures)
        {
            for (int i = 0; i < values.length; i++)
            {
                msArrayList.add(testCaseSource, new TestCaseData(values[i], culture).returns(i));
            }
        }
        return testCaseSource;
    }

}

