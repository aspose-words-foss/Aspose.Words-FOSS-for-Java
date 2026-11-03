// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2007 by Konstantin Sidorenko
// 2016/01/18 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.msStringBuilder;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


@Test
public class TestStringBuilder
{
    @Test
    public void testStringBuilderReplace()
    {
        String result;

        result = msStringBuilder.replace(sb, "dog", "elefant").toString();
        msAssert.areEqual(result, "The quick brown elefant jumps over the lazy cat.");

        result = msStringBuilder.replace(sb, "e", "E").toString();
        msAssert.areEqual(result, "ThE quick brown ElEfant jumps ovEr thE lazy cat.");

        result = msStringBuilder.replace(sb, "E", "").toString();
        msAssert.areEqual(result, "Th quick brown lfant jumps ovr th lazy cat.");

        result = msStringBuilder.replace(sb, "o", null).toString();
        msAssert.areEqual(result, "Th quick brwn lfant jumps vr th lazy cat.");
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testStringBuilderReplaceException1()
    {
        msStringBuilder.replace(sb, null, "XXX").toString();
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testStringBuilderReplaceException2()
    {
        msStringBuilder.replace(sb, "", "XXX").toString();
    }

    @Test
    public void testInsertCount()
    {
        String insert = "Insert";
        String original = "original";

        // Empty initial StringBuilder
        StringBuilder builder = new StringBuilder();
        msAssert.areEqual(msStringBuilder.insert(builder, 0, insert, 0).toString(), "");
        msAssert.areEqual(msStringBuilder.insert(builder, 0, insert, 1).toString(), insert);

        builder = new StringBuilder();
        msAssert.areEqual(msStringBuilder.insert(builder, 0, insert, 5).toString(), "InsertInsertInsertInsertInsert");

        // Empty insert string
        builder = new StringBuilder();
        msAssert.areEqual(msStringBuilder.insert(builder, 0, null, 0).toString(), "");
        msAssert.areEqual(msStringBuilder.insert(builder, 0, "", 0).toString(), "");

        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, 0, null, 0).toString(), original);
        msAssert.areEqual(msStringBuilder.insert(builder, 0, "", 0).toString(), original);

        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, 3, null, 0).toString(), original);
        msAssert.areEqual(msStringBuilder.insert(builder, 3, "", 0).toString(), original);

        // Insert in the middle
        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, 3, insert, 1).toString(), "oriInsertginal");
        msAssert.areEqual(msStringBuilder.insert(builder, 3, insert, 3).toString(), "oriInsertInsertInsertInsertginal");

        // Insert into the start
        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, 0, insert, 1).toString(), "Insertoriginal");
        msAssert.areEqual(msStringBuilder.insert(builder, 0, insert, 3).toString(), "InsertInsertInsertInsertoriginal");

        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, 1, insert, 1).toString(), "oInsertriginal");
        msAssert.areEqual(msStringBuilder.insert(builder, 1, insert, 3).toString(), "oInsertInsertInsertInsertriginal");

        // Insert to the end
        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, original.length(), insert, 1).toString(), "originalInsert");
        msAssert.areEqual(msStringBuilder.insert(builder, original.length(), insert, 3).toString(), "originalInsertInsertInsertInsert");

        builder = new StringBuilder(original);
        msAssert.areEqual(msStringBuilder.insert(builder, original.length() - 1, insert, 1).toString(), "originaInsertl");
        msAssert.areEqual(msStringBuilder.insert(builder, original.length() - 1, insert, 3).toString(), "originaInsertInsertInsertInsertl");
    }

    @Test
    public void testUnsignedDigits()
    {
        byte b = (byte) 0x1B;
        /*ushort*/short ush = (short) 0x32FF;
        /*uint*/int ui = (int) 0xf2374322L;

        StringBuilder builder = new StringBuilder();
        msStringBuilder.appendFormat(builder, "{0:X2},{1:X4},{2:X8}", (b & 0xFF), (ush & 0xFFFF), ui);
        msAssert.areEqual("1B,32FF,F2374322", builder.toString());

        builder.setLength(0);
        msStringBuilder.appendFormat(builder, "{0:X2},{1:X4},{2:X8}", 0x1B, 0x32FF, (int) 0xf2374322L);
        msAssert.areEqual("1B,32FF,F2374322", builder.toString());
    }

    @Test (dataProvider = "testStringReplacedWithinRangeDataProvider")
    public void testStringReplacedWithinRange(String expected, String data, String oldVal, String newVal, int startIndex, int count)
    {
        StringBuilder builder = new StringBuilder(data);
        msStringBuilder.replace(builder, oldVal, newVal, startIndex, count);
        msAssert.areEqual(expected, builder.toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStringReplacedWithinRangeDataProvider")
	public static Object[][] testStringReplacedWithinRangeDataProvider()
	{
		return new Object[][]
		{
			{"H ",  "H \u0007",  "\u0007",  "",  2,  1},
			{"tea",  "test",  "st",  "a",  2,  2},
			{"test",  "test",  "te",  "a",  0,  1},
			{"atest",  "testtest",  "test",  "a",  0,  6},
			{"atatat",  "testtesttest",  "tes",  "a",  0,  11},
		};
	}

    private StringBuilder sb = new StringBuilder("The quick brown dog jumps over the lazy cat.");
}

