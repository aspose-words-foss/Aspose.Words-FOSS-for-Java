// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


@Test
public class TestSystemStringAdapters
{
    @Test (dataProvider = "testSystemCharAdapterToSystemCharDataProvider")
    public void testSystemCharAdapterToSystemChar(char value)
    {
        SystemCharAdapter _char = SystemCharAdapter.create(value);

        msAssert.areEqual(value, _char.toSystemChar());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemCharAdapterToSystemCharDataProvider")
	public static Object[][] testSystemCharAdapterToSystemCharDataProvider()
	{
		return new Object[][]
		{
			{'a'},
			{'Z'},
		};
	}

    @Test (dataProvider = "testSystemCharAdapterToUppperDataProvider")
    public void testSystemCharAdapterToUppper(char value, char expectedResult)
    {
        SystemCharAdapter _char = SystemCharAdapter.create(value);

        SystemCharAdapter result = _char.toUpperInternal();

        msAssert.areEqual(value, _char.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemCharAdapterToUppperDataProvider")
	public static Object[][] testSystemCharAdapterToUppperDataProvider()
	{
		return new Object[][]
		{
			{'a',  'A'},
			{'Z',  'Z'},
		};
	}

    @Test (dataProvider = "testSystemCharAdapterToLowerDataProvider")
    public void testSystemCharAdapterToLower(char value, char expectedResult)
    {
        SystemCharAdapter _char = SystemCharAdapter.create(value);

        SystemCharAdapter result = _char.toLowerInternal();

        msAssert.areEqual(value, _char.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemCharAdapterToLowerDataProvider")
	public static Object[][] testSystemCharAdapterToLowerDataProvider()
	{
		return new Object[][]
		{
			{'a',  'a'},
			{'Z',  'z'},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterToSystemStringDataProvider")
    public void testSystemStringAdapterToSystemString(String value)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        msAssert.areEqual(value, _string.toSystemString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterToSystemStringDataProvider")
	public static Object[][] testSystemStringAdapterToSystemStringDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterLengthDataProvider")
    public void testSystemStringAdapterLength(String value, int expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        msAssert.areEqual(expectedResult, _string.getLength());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterLengthDataProvider")
	public static Object[][] testSystemStringAdapterLengthDataProvider()
	{
		return new Object[][]
		{
			{"",  0},
			{LOREM_IPSUM,  26},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterIndexerDataProvider")
    public void testSystemStringAdapterIndexer(String value, int index, char expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        msAssert.areEqual(expectedResult, _string.getInternal(index).getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterIndexerDataProvider")
	public static Object[][] testSystemStringAdapterIndexerDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  2,  'r'},
			{LOREM_IPSUM,  22,  'a'},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterIndexOfDataProvider")
    public void testSystemStringAdapterIndexOf(String value, String subString, int expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        msAssert.areEqual(expectedResult, _string.indexOf(subString));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterIndexOfDataProvider")
	public static Object[][] testSystemStringAdapterIndexOfDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  "dol",  12},
			{LOREM_IPSUM,  "blah",  -1},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterReplaceDataProvider")
    public void testSystemStringAdapterReplace(String value, String oldValue, String newValue, String expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        SystemStringAdapter result = _string.replaceInternal(oldValue, newValue);

        msAssert.areEqual(value, _string.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterReplaceDataProvider")
	public static Object[][] testSystemStringAdapterReplaceDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  "or",  "**",  "L**em ipsum dol** sit amet"},
			{LOREM_IPSUM,  "blah",  "**",  LOREM_IPSUM},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterRemoveDataProvider")
    public void testSystemStringAdapterRemove(String value, int startIndex, int count, String expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        SystemStringAdapter result = _string.removeInternal(startIndex, count);

        msAssert.areEqual(value, _string.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterRemoveDataProvider")
	public static Object[][] testSystemStringAdapterRemoveDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  7,  9,  "Lorem ir sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterToUpperDataProvider")
    public void testSystemStringAdapterToUpper(String value, String expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        SystemStringAdapter result = _string.toUpperInternal();

        msAssert.areEqual(value, _string.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterToUpperDataProvider")
	public static Object[][] testSystemStringAdapterToUpperDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  "LOREM IPSUM DOLOR SIT AMET"},
		};
	}

    @Test (dataProvider = "testSystemStringAdapterToLowerDataProvider")
    public void testSystemStringAdapterToLower(String value, String expectedResult)
    {
        SystemStringAdapter _string = SystemStringAdapter.create(value);

        SystemStringAdapter result = _string.toLowerInternal();

        msAssert.areEqual(value, _string.getValue());
        msAssert.areEqual(expectedResult, result.getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringAdapterToLowerDataProvider")
	public static Object[][] testSystemStringAdapterToLowerDataProvider()
	{
		return new Object[][]
		{
			{LOREM_IPSUM,  "lorem ipsum dolor sit amet"},
		};
	}

    @Test
    public void testSystemStringBuilderAdapterGetLength()
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        msAssert.areEqual(26, builder.getLength());
    }

    @Test
    public void testSystemStringBuilderAdapterSetLength1()
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.setLength(10);

        msAssert.areEqual("Lorem ipsu", builder.getValue().toString());
    }

    @Test
    public void testSystemStringBuilderAdapterSetLength2()
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.setLength(30);

        msAssert.areEqual("Lorem ipsum dolor sit amet\u0000\u0000\u0000\u0000", builder.getValue().toString());
    }

    @Test (dataProvider = "testSystemStringBuilderAdapterGetIndexerDataProvider")
    public void testSystemStringBuilderAdapterGetIndexer(int index, char expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        msAssert.areEqual(expectedResult, builder.getChar(index).getValue());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterGetIndexerDataProvider")
	public static Object[][] testSystemStringBuilderAdapterGetIndexerDataProvider()
	{
		return new Object[][]
		{
			{2,  'r'},
			{22,  'a'},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterSetIndexerDataProvider")
    public void testSystemStringBuilderAdapterSetIndexer(int index, char value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.setChar(index, SystemCharAdapter.create(value));

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterSetIndexerDataProvider")
	public static Object[][] testSystemStringBuilderAdapterSetIndexerDataProvider()
	{
		return new Object[][]
		{
			{2,  '*',  "Lo*em ipsum dolor sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterAppendSystemCharAdapterDataProvider")
    public void testSystemStringBuilderAdapterAppendSystemCharAdapter(char value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.append(SystemCharAdapter.create(value));

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterAppendSystemCharAdapterDataProvider")
	public static Object[][] testSystemStringBuilderAdapterAppendSystemCharAdapterDataProvider()
	{
		return new Object[][]
		{
			{'a',  "Lorem ipsum dolor sit ameta"},
			{'Z',  "Lorem ipsum dolor sit ametZ"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterAppendCharDataProvider")
    public void testSystemStringBuilderAdapterAppendChar(char value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.append(value);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterAppendCharDataProvider")
	public static Object[][] testSystemStringBuilderAdapterAppendCharDataProvider()
	{
		return new Object[][]
		{
			{'a',  "Lorem ipsum dolor sit ameta"},
			{'Z',  "Lorem ipsum dolor sit ametZ"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterAppendNumberCharsDataProvider")
    public void testSystemStringBuilderAdapterAppendNumberChars(char value, int count, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.append(value, count);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterAppendNumberCharsDataProvider")
	public static Object[][] testSystemStringBuilderAdapterAppendNumberCharsDataProvider()
	{
		return new Object[][]
		{
			{'a',  1,  "Lorem ipsum dolor sit ameta"},
			{'Z',  5,  "Lorem ipsum dolor sit ametZZZZZ"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterAppendSystemStringAdapterDataProvider")
    public void testSystemStringBuilderAdapterAppendSystemStringAdapter(String value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.append(SystemStringAdapter.create(value));

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterAppendSystemStringAdapterDataProvider")
	public static Object[][] testSystemStringBuilderAdapterAppendSystemStringAdapterDataProvider()
	{
		return new Object[][]
		{
			{"blah",  "Lorem ipsum dolor sit ametblah"},
			{"Wow",  "Lorem ipsum dolor sit ametWow"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterAppendStringDataProvider")
    public void testSystemStringBuilderAdapterAppendString(String value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.append(value);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterAppendStringDataProvider")
	public static Object[][] testSystemStringBuilderAdapterAppendStringDataProvider()
	{
		return new Object[][]
		{
			{"blah",  "Lorem ipsum dolor sit ametblah"},
			{"Wow",  "Lorem ipsum dolor sit ametWow"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterInsertCharDataProvider")
    public void testSystemStringBuilderAdapterInsertChar(int index, char value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.insert(index, value);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterInsertCharDataProvider")
	public static Object[][] testSystemStringBuilderAdapterInsertCharDataProvider()
	{
		return new Object[][]
		{
			{0,  'a',  "aLorem ipsum dolor sit amet"},
			{5,  'Z',  "LoremZ ipsum dolor sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterInsertStringDataProvider")
    public void testSystemStringBuilderAdapterInsertString(int index, String value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.insert(index, value);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterInsertStringDataProvider")
	public static Object[][] testSystemStringBuilderAdapterInsertStringDataProvider()
	{
		return new Object[][]
		{
			{0,  "blah",  "blahLorem ipsum dolor sit amet"},
			{5,  "Wow",  "LoremWow ipsum dolor sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterInsertNumberStringsDataProvider")
    public void testSystemStringBuilderAdapterInsertNumberStrings(int index, String value, int count, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.insert(index, value, count);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterInsertNumberStringsDataProvider")
	public static Object[][] testSystemStringBuilderAdapterInsertNumberStringsDataProvider()
	{
		return new Object[][]
		{
			{0,  "blah",  1,  "blahLorem ipsum dolor sit amet"},
			{5,  "Wow",  3,  "LoremWowWowWow ipsum dolor sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterReplaceByValueDataProvider")
    public void testSystemStringBuilderAdapterReplaceByValue(String oldValue, String newValue, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.replaceInternal(oldValue, newValue);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterReplaceByValueDataProvider")
	public static Object[][] testSystemStringBuilderAdapterReplaceByValueDataProvider()
	{
		return new Object[][]
		{
			{"or",  "**",  "L**em ipsum dol** sit amet"},
			{"blah",  "**",  LOREM_IPSUM},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterReplaceAtIndexDataProvider")
    public void testSystemStringBuilderAdapterReplaceAtIndex(int index, char value, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.replace(index, value);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterReplaceAtIndexDataProvider")
	public static Object[][] testSystemStringBuilderAdapterReplaceAtIndexDataProvider()
	{
		return new Object[][]
		{
			{2,  '*',  "Lo*em ipsum dolor sit amet"},
		};
	}

    @Test (dataProvider = "testSystemStringBuilderAdapterRemoveDataProvider")
    public void testSystemStringBuilderAdapterRemove(int startIndex, int length, String expectedResult)
    {
        SystemStringBuilderAdapter builder = new SystemStringBuilderAdapter(new StringBuilder(LOREM_IPSUM));

        builder.removeInternal(startIndex, length);

        msAssert.areEqual(expectedResult, builder.getValue().toString());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSystemStringBuilderAdapterRemoveDataProvider")
	public static Object[][] testSystemStringBuilderAdapterRemoveDataProvider()
	{
		return new Object[][]
		{
			{6,  6,  "Lorem dolor sit amet"},
		};
	}

    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet";
}

