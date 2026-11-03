// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import com.aspose.DrColor;
import com.aspose.NumberFormatter;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.NumberFormattingOptions;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.IChar;
import com.aspose.SystemStringAdapter;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.SystemStringBehaviour;
import java.util.Iterator;
import org.testng.annotations.DataProvider;


@Test
public class TestRichStringNumberFormatter extends TestFieldsBase
{

    //JAVA: method is internalized
	/*@Test*/
    void testNumberToRichString(TestNumberToRichStringCase testCase)
    {
        RichString actual = (RichString)NumberFormatter.numberToString(
            testCase.getFormat(),
            testCase.getValue(),
            msCultureInfo.getCurrentCulture(),
            NumberFormattingOptions.DEFAULT,
            RichStringBehaviour.INSTANCE);

        TestRichStrings.assertRichString(testCase.getExpectedResult(), actual);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testNumberToRichStringSourceDataProvider")
	public void testNumberToRichStringSource(TestNumberToRichStringCase testCase)
	{
		testNumberToRichString(testCase);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testNumberToRichStringSourceDataProvider")
	Object[][] testNumberToRichStringSourceDataProvider()
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = testNumberToRichStringCases().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static Iterable<TestCaseData> testNumberToRichStringCases()
    {
        ArrayList<TestCaseData> cases = new ArrayList<TestCaseData>();

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            7.0,
            buildRichString(),
            buildRichString("7"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            4.0,
            buildRichString("#"),
            buildRichString("4"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            4.0,
            buildRichString("0"),
            buildRichString("4"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            386.0,
            buildRichString("#", G_RED_FONT, "#", G_GREEN_FONT, "#", G_BLUE_FONT),
            buildRichString("3", G_RED_FONT, "8", G_GREEN_FONT, "6", G_BLUE_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            386.0,
            buildRichString("0", G_RED_FONT, "0", G_GREEN_FONT, "0", G_BLUE_FONT),
            buildRichString("3", G_RED_FONT, "8", G_GREEN_FONT, "6", G_BLUE_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            95.0,
            buildRichString("#", G_RED_FONT),
            buildRichString("95", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            95.0,
            buildRichString("0", G_RED_FONT),
            buildRichString("95", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            3.0,
            buildRichString("###", G_RED_FONT),
            buildRichString("  3", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            3.0,
            buildRichString("000", G_RED_FONT),
            buildRichString("003", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            9475.0,
            buildRichString("#", "#", G_RED_FONT, "#"),
            buildRichString("94", "7", G_RED_FONT, "5"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            9475.0,
            buildRichString("0", "0", G_RED_FONT, "0"),
            buildRichString("94", "7", G_RED_FONT, "5"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            -281,
            buildRichString("#", G_RED_FONT),
            buildRichString("-281", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            -281,
            buildRichString("0", G_RED_FONT),
            buildRichString("-281", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            -305,
            buildRichString("#", "#", G_RED_FONT),
            buildRichString("-30", "5", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            -305,
            buildRichString("0", "0", G_RED_FONT),
            buildRichString("-30", "5", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            8191.0,
            buildRichString("#", ",", G_RED_FONT, "###"),
            buildRichString("8,191"))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            8192.0,
            buildRichString("#", G_GREEN_FONT, ",", G_RED_FONT, "###", G_BLUE_FONT),
            buildRichString("8,", G_GREEN_FONT, "192", G_BLUE_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            8193.0,
            buildRichString("#", G_GREEN_FONT, "##,#", G_BLUE_FONT),
            buildRichString("8,", G_GREEN_FONT, "193", G_BLUE_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            1234567890.0,
            buildRichString("#", G_GREEN_FONT, ",", G_RED_FONT, "###", G_BLUE_FONT, ",", G_RED_FONT, "###", G_YELLOW_FONT),
            buildRichString("1,234,", G_GREEN_FONT, "567,", G_BLUE_FONT, "890", G_YELLOW_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            5.29,
            buildRichString("#.#", G_GREEN_FONT),
            buildRichString("5.3", G_GREEN_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            5.29,
            buildRichString("0.0", G_GREEN_FONT),
            buildRichString("5.3", G_GREEN_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            6.6,
            buildRichString("#.##", G_GREEN_FONT),
            buildRichString("6.6 ", G_GREEN_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            6.6,
            buildRichString("0.00", G_GREEN_FONT),
            buildRichString("6.60", G_GREEN_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            0.35,
            buildRichString("0", G_GREEN_FONT, ".", G_BLUE_FONT, "00", G_YELLOW_FONT),
            buildRichString("0", G_GREEN_FONT, ".", G_BLUE_FONT, "35", G_YELLOW_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            0.35,
            buildRichString("#", G_GREEN_FONT, ".", G_BLUE_FONT, "##", G_YELLOW_FONT),
            buildRichString(" ", G_GREEN_FONT, ".", G_BLUE_FONT, "35", G_YELLOW_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            0.97,
            buildRichString("0", G_RED_FONT, "0", G_YELLOW_FONT, "0", G_GREEN_FONT, ".", G_BLUE_FONT, "00", G_YELLOW_FONT),
            buildRichString("0", G_RED_FONT, "0", G_YELLOW_FONT, "0", G_GREEN_FONT, ".", G_BLUE_FONT, "97", G_YELLOW_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            0.97,
            buildRichString("#", G_RED_FONT, "#", G_YELLOW_FONT, "#", G_GREEN_FONT, ".", G_BLUE_FONT, "##", G_YELLOW_FONT),
            buildRichString(" ", G_RED_FONT, " ", G_YELLOW_FONT, " ", G_GREEN_FONT, ".", G_BLUE_FONT, "97", G_YELLOW_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            1.0,
            buildRichString("'XXXX'", G_BLUE_FONT, "x", G_GREEN_FONT, "#####", G_RED_FONT),
            buildRichString("XXXX", G_BLUE_FONT, " ", G_GREEN_FONT, "    1", G_RED_FONT))));

        msArrayList.add(cases, new TestCaseData(new TestNumberToRichStringCase(
            123456789.0,
            buildRichString("'XXXX'", G_BLUE_FONT, "x", G_GREEN_FONT, "#####", G_RED_FONT),
            buildRichString("XXXX", G_BLUE_FONT, "4", G_GREEN_FONT, "56789", G_RED_FONT))));

        for (TestCaseData testCaseData : cases)
        {
            TestNumberToRichStringCase _case = (TestNumberToRichStringCase)testCaseData.getArguments()[0];
            testCaseData.setName(msString.format(
                "TestNumberToRichString({0}, \"{1}\")",
                _case.getValue(),
                _case.getFormat().toSystemString()));
        }

        return cases;
    }

    private static RichString buildRichString(/*params*/ Object... parts)
    {
        ArrayList<IChar> chars = new ArrayList<IChar>();

        String lastText = null;
        for (Object part : parts)
        {
            String text = ms.as(part, String.class);
            if (text != null)
            {
                if (lastText != null)
                {
                    for (int i = 0; i < lastText.length(); i++)
                    {
                        char c = lastText.charAt(i);
                        msArrayList.add(chars, new RichChar(c, new RunPr()));
                    }
                }

                lastText = text;
                continue;
            }

            RunPr font = ms.as(part, RunPr.class);
            if (font != null)
            {
                if (lastText == null)
                    throw new IllegalArgumentException();

                for (int i = 0; i < lastText.length(); i++)
                {
                    char c = lastText.charAt(i);
                    msArrayList.add(chars, new RichChar(c, font));
                }

                lastText = null;

                continue;
            }

            throw new IllegalArgumentException();
        }

        if (lastText != null)
        {
            for (int i = 0; i < lastText.length(); i++)
            {
                char c = lastText.charAt(i);
                msArrayList.add(chars, new RichChar(c, new RunPr()));
            }
        }

        return new RichString(chars);
    }

    @Test
    public void testNumberFormatterSequentialCalls()
    {
        RichString richFormat = RichString.createFromString("#");
        SystemStringAdapter plainTextFormat = SystemStringAdapter.create("#");
        final double VALUE = 123.0;

        msAssert.isInstanceOf(RichString.class, NumberFormatter.numberToString(richFormat, VALUE, msCultureInfo.getCurrentCulture(), NumberFormattingOptions.DEFAULT, RichStringBehaviour.INSTANCE));

        msAssert.isInstanceOf(SystemStringAdapter.class, NumberFormatter.numberToString(plainTextFormat, VALUE, msCultureInfo.getCurrentCulture(), NumberFormattingOptions.DEFAULT, SystemStringBehaviour.INSTANCE));

        msAssert.isInstanceOf(RichString.class, NumberFormatter.numberToString(richFormat, VALUE, msCultureInfo.getCurrentCulture(), NumberFormattingOptions.DEFAULT, RichStringBehaviour.INSTANCE));

        msAssert.isInstanceOf(SystemStringAdapter.class, NumberFormatter.numberToString(plainTextFormat, VALUE, msCultureInfo.getCurrentCulture(), NumberFormattingOptions.DEFAULT, SystemStringBehaviour.INSTANCE));
    }

    private static /*final*/ RunPr G_RED_FONT;
    private static /*final*/ RunPr G_GREEN_FONT;
    private static /*final*/ RunPr G_BLUE_FONT;
    private static /*final*/ RunPr G_YELLOW_FONT;

    public static class TestNumberToRichStringCase
    {
        TestNumberToRichStringCase(double value, RichString format, RichString expectedResult)
        {
            mValue = value;
            mFormat = format;
            mExpectedResult = expectedResult;
        }

        double getValue() { return mValue; };

        private double mValue;
        RichString getFormat() { return mFormat; };

        private RichString mFormat;
        RichString getExpectedResult() { return mExpectedResult; };

        private RichString mExpectedResult;
    }
    static/* TestRichStringNumberFormatter()*/
    {
        G_RED_FONT = new RunPr();
        G_RED_FONT.setBold(AttrBoolEx.TRUE);
        G_RED_FONT.setColor(DrColor.RED);

        G_GREEN_FONT = new RunPr();
        G_GREEN_FONT.setItalic(AttrBoolEx.TRUE);
        G_GREEN_FONT.setColor(DrColor.GREEN);

        G_BLUE_FONT = new RunPr();
        G_BLUE_FONT.setSize(8);
        G_BLUE_FONT.setColor(DrColor.BLUE);

        G_YELLOW_FONT = new RunPr();
        G_YELLOW_FONT.setUnderline(Underline.SINGLE);
        G_YELLOW_FONT.setColor(DrColor.YELLOW);
    }
}

