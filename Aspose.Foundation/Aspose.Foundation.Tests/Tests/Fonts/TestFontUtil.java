// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/02/2016 by Alexander Zhiltsov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Class for testing methods of <see cref="FontUtil"/>.
/// </summary>
@Test
public class TestFontUtil
{
    /// <summary>
    /// Tests the <see cref="FontUtil.ResolveCharset"/> method.
    /// </summary>
    @Test (dataProvider = "testCharsetByIanaNameDataProvider")
    public void testCharsetByIanaName(String ianaName, String fontName, int expectedResult)
    {
        msAssert.areEqual(expectedResult, FontUtil.resolveCharset(ianaName, fontName));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCharsetByIanaNameDataProvider")
	public static Object[][] testCharsetByIanaNameDataProvider()
	{
		return new Object[][]
		{
			{null,  "",  -1},
			{"",  "",  -1},
			{"unknown charset",  "",  -1},
			{"iso-8859-1",  "",  0},
			{"macintosh",  "",  0x4D},
			{"shift_jis",  "",  0x80},
			{"ks_c-5601-1987",  "",  0x81},
			{"KS C-5601-1992",  "",  0x82},
			{"GBK",  "",  0x86},
			{"Big5",  "",  0x88},
			{"windows-1253",  "",  0xA1},
			{"iso-8859-9",  "",  0xA2},
			{"windows-1258",  "",  0xA3},
			{"windows-1255",  "",  0xB1},
			{"windows-1256",  "",  0xB2},
			{"windows-1257",  "",  0xBA},
			{"windows-1251",  "",  0xCC},
			{"windows-874",  "",  0xDE},
			{"windows-1250",  "",  0xEE},
		};
	}

    /// <summary>
    /// Tests the <see cref="FontUtil.IanaNameByCharset"/> method.
    /// </summary>
    @Test (dataProvider = "testIanaNameByCharsetDataProvider")
    public void testIanaNameByCharset(int charset, String expectedIanaName)
    {
        msAssert.areEqual(expectedIanaName, FontUtil.ianaNameByCharset(charset));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIanaNameByCharsetDataProvider")
	public static Object[][] testIanaNameByCharsetDataProvider()
	{
		return new Object[][]
		{
			{-1,  ""},
			{0,  "iso-8859-1"},
			{1,  ""},
			{2,  ""},
			{0x4D,  "macintosh"},
			{0x80,  "shift_jis"},
			{0x81,  "ks_c-5601-1987"},
			{0x82,  "KS C-5601-1992"},
			{0x86,  "GBK"},
			{0x88,  "Big5"},
			{0xA1,  "windows-1253"},
			{0xA2,  "iso-8859-9"},
			{0xA3,  "windows-1258"},
			{0xB1,  "windows-1255"},
			{0xB2,  "windows-1256"},
			{0xBA,  "windows-1257"},
			{0xCC,  "windows-1251"},
			{0xDE,  "windows-874"},
			{0xEE,  "windows-1250"},
			{0xFF,  ""},
		};
	}

}

