// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests Korean numbering.
/// </summary>
@Test
public class TestKoreanNumbering
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests Korean Chosung numbering.
    /// </summary>
    @Test (dataProvider = "testChosungNumberingDataProvider")
    public void testChosungNumbering(long value, String expectedString)
    {
        String s = NumberConverter.numberToLocalizedString(value, NumberStyle.CHOSUNG, false);
        msAssert.areEqual(expectedString, s);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testChosungNumberingDataProvider")
	public static Object[][] testChosungNumberingDataProvider()
	{
		return new Object[][]
		{
			{0,  "0"},
			{1,  "ㄱ"},
			{10,  "ㅊ"},
			{11,  "ㅋ"},
			{14,  "ㅎ"},
			{15,  "ㄱ"},
			{20,  "ㅂ"},
			{28,  "ㅎ"},
			{100,  "ㄴ"},
			{1000,  "ㅂ"},
		};
	}

    /// <summary>
    /// Tests Hangul (koreanLegal) numbering.
    /// </summary>
    @Test (dataProvider = "testHangulNumberingDataProvider")
    public void testHangulNumbering(long value, String expectedString)
    {
        String s = NumberConverter.numberToLocalizedString(value, NumberStyle.HANGUL, false);
        msAssert.areEqual(expectedString, s);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testHangulNumberingDataProvider")
	public static Object[][] testHangulNumberingDataProvider()
	{
		return new Object[][]
		{
			{0,  "0"},
			{1,  "하나"},
			{10,  "열"},
			{11,  "열하나"},
			{20,  "스물"},
			{21,  "스물하나"},
			{100,  "백"},
			{101,  "백일"},
			{110,  "백십"},
			{111,  "백십일"},
			{120,  "백이십"},
			{121,  "백이십일"},
			{200,  "이백"},
			{999,  "구백구십구"},
			{1000,  "천"},
			{1001,  "천일"},
			{2000,  "이천"},
			{10000,  "만"},
			{10001,  "만일"},
			{11111,  "만일천백십일"},
			{32768,  "삼만이천칠백육십팔"},
			{100000,  "십만"},
			{1000000,  "백만"},
			{3400000,  "삼백사십만"},
			{3410000,  "삼백사십만"},
			{1001011,  "백만일천십일"},
			{5050505,  "오백오만오백오"},
			{9123456,  "구백일십이만삼천사백오십육"},
			{10000000,  ""},
		};
	}
}

