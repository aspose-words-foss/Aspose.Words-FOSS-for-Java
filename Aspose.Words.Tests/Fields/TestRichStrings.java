// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.IChar;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


@Test
public class TestRichStrings
{

    @Test (dataProvider = "testRichCharToSystemCharDataProvider")
    public void testRichCharToSystemChar(char value)
    {
        RichChar _char = new RichChar(value, G_EMPTY_FONT);

        msAssert.areEqual(value, _char.toSystemChar());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichCharToSystemCharDataProvider")
	public static Object[][] testRichCharToSystemCharDataProvider()
	{
		return new Object[][]
		{
			{'a'},
			{'Z'},
		};
	}

    @Test (dataProvider = "testRichCharToUppperDataProvider")
    public void testRichCharToUppper(char value, char expectedResult)
    {
        RichChar _char = new RichChar(value, G_BLUE_FONT);

        RichChar result = _char.toUpperInternal();

        assertRichChar(new RichChar(value, G_BLUE_FONT), _char);
        assertRichChar(new RichChar(expectedResult, G_BLUE_FONT), result);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichCharToUppperDataProvider")
	public static Object[][] testRichCharToUppperDataProvider()
	{
		return new Object[][]
		{
			{'a',  'A'},
			{'Z',  'Z'},
		};
	}

    @Test (dataProvider = "testRichCharToLowerDataProvider")
    public void testRichCharToLower(char value, char expectedResult)
    {
        RichChar _char = new RichChar(value, G_BLUE_FONT);

        RichChar result = _char.toLowerInternal();

        assertRichChar(new RichChar(value, G_BLUE_FONT), _char);
        assertRichChar(new RichChar(expectedResult, G_BLUE_FONT), result);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichCharToLowerDataProvider")
	public static Object[][] testRichCharToLowerDataProvider()
	{
		return new Object[][]
		{
			{'a',  'a'},
			{'Z',  'z'},
		};
	}

    @Test
    public void testRichStringToSystemString()
    {
        RichString _string = buildLoremIpsumRichString();

        msAssert.areEqual(LOREM_IPSUM, _string.toSystemString());
    }

    @Test
    public void testRichStringLength()
    {
        RichString _string = buildLoremIpsumRichString();

        msAssert.areEqual(26, _string.getLength());
    }

    @Test (dataProvider = "testRichStringIndexerDataProvider")
    public void testRichStringIndexer(int index, char expectedResult)
    {
        RichString _string = buildLoremIpsumRichString();

        msAssert.areEqual(expectedResult, _string.getInternal(index).getCharacter());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringIndexerDataProvider")
	public static Object[][] testRichStringIndexerDataProvider()
	{
		return new Object[][]
		{
			{2,  'r'},
			{22,  'a'},
		};
	}

    @Test (dataProvider = "testRichStringIndexOfDataProvider")
    public void testRichStringIndexOf(String subString, int expectedResult)
    {
        RichString _string = buildLoremIpsumRichString();

        msAssert.areEqual(expectedResult, _string.indexOf(subString));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringIndexOfDataProvider")
	public static Object[][] testRichStringIndexOfDataProvider()
	{
		return new Object[][]
		{
			{"dol",  12},
			{"blah",  -1},
		};
	}

    @Test (dataProvider = "testRichStringIndexOfWithStartIndexDataProvider")
    public void testRichStringIndexOfWithStartIndex(String subString, int startIndex, int expectedResult)
    {
        RichString _string = buildLoremIpsumRichString();

        msAssert.areEqual(expectedResult, _string.indexOf(subString, startIndex));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringIndexOfWithStartIndexDataProvider")
	public static Object[][] testRichStringIndexOfWithStartIndexDataProvider()
	{
		return new Object[][]
		{
			{"or",  0,  1},
			{"or",  10,  15},
			{"or",  20,  -1},
		};
	}

    @Test
    public void testRichStringSubstring()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.substring(7, 9);

        assertRichString(buildLoremIpsumRichString(), _string);
        RunPr[] fonts = populateFonts(
            new RunPr[] {G_YELLOW_FONT, G_EMPTY_FONT},
            new int[] {5 ,4});
        assertRichString(buildRichString("psum dolo", fonts), result);
    }

    @Test
    public void testRichStringReplace()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.replaceInternal("or", "**");

        assertRichString(buildLoremIpsumRichString(), _string);
        assertRichString(buildRichString("L**em ipsum dol** sit amet", G_LOREM_IPSUM_RICH_FONTS), result);
    }

    @Test
    public void testRichStringReplaceRecursive()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.replaceInternal("or", "'or'");

        assertRichString(buildLoremIpsumRichString(), _string);
        RunPr[] fonts = populateFonts(
            new RunPr[] {G_BLUE_FONT, G_YELLOW_FONT, G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT},
            new int[] {8, 6, 7, 4, 5});
        assertRichString(buildRichString("L'or'em ipsum dol'or' sit amet", fonts), result);
    }

    @Test
    public void testRichStringRemove()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.removeInternal(7, 9);

        assertRichString(buildLoremIpsumRichString(), _string);

        RunPr[] fonts = populateFonts(
            new RunPr[] {G_BLUE_FONT, G_YELLOW_FONT, G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT},
            new int[] {6, 1, 1, 4, 5});
        assertRichString(buildRichString("Lorem ir sit amet", fonts), result);
    }

    @Test
    public void testRichStringToUpper()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.toUpperInternal();

        assertRichString(buildLoremIpsumRichString(), _string);
        assertRichString(buildRichString("LOREM IPSUM DOLOR SIT AMET", G_LOREM_IPSUM_RICH_FONTS), result);
    }

    @Test
    public void testRichStringToLower()
    {
        RichString _string = buildLoremIpsumRichString();

        RichString result = _string.toLowerInternal();

        assertRichString(buildLoremIpsumRichString(), _string);
        assertRichString(buildRichString("lorem ipsum dolor sit amet", G_LOREM_IPSUM_RICH_FONTS), result);
    }

    @Test
    public void testRichStringToChunks()
    {
        RichString _string = buildLoremIpsumRichString();

        RichStringChunk[] chunks = _string.toChunks();

        msAssert.areEqual(5, chunks.length);

        msAssert.areEqual("Lorem ", chunks[0].getValue());
        msAssert.areEqual("ipsum ", chunks[1].getValue());
        msAssert.areEqual("dolor", chunks[2].getValue());
        msAssert.areEqual(" sit", chunks[3].getValue());
        msAssert.areEqual(" amet", chunks[4].getValue());

        assertFont(G_BLUE_FONT, chunks[0].getRunPr());
        assertFont(G_YELLOW_FONT, chunks[1].getRunPr());
        assertFont(G_EMPTY_FONT, chunks[2].getRunPr());
        assertFont(G_BLUE_FONT, chunks[3].getRunPr());
        assertFont(G_YELLOW_FONT, chunks[4].getRunPr());
    }

    @Test
    public void testRichStringBuilderGetLength()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        msAssert.areEqual(26, builder.getLength());
    }

    @Test
    public void testRichStringBuilderDecreaseLength()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.setLength(10);

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.removeRange(fonts, 10, fonts.size() - 10);
        assertRichStringBuilder(buildRichString("Lorem ipsu", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderIncreaseLength()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.setLength(30);

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.addRange(fonts, new RunPr[] {G_EMPTY_FONT, G_EMPTY_FONT, G_EMPTY_FONT, G_EMPTY_FONT});
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit amet\u0000\u0000\u0000\u0000", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test (dataProvider = "testRichStringBuilderGetIndexerDataProvider")
    public void testRichStringBuilderGetIndexer(int index, char expectedResult)
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        msAssert.areEqual(expectedResult, builder.getInternal(index).getCharacter());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringBuilderGetIndexerDataProvider")
	public static Object[][] testRichStringBuilderGetIndexerDataProvider()
	{
		return new Object[][]
		{
			{2,  'r'},
			{22,  'a'},
		};
	}

    @Test (dataProvider = "testRichStringBuilderSetIndexerDataProvider")
    public void testRichStringBuilderSetIndexer(int index, char value, String expectedResult)
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.setInternal(index, new RichChar(value, G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        fonts.set(index, G_GREEN_FONT);
        assertRichStringBuilder(buildRichString(expectedResult, msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringBuilderSetIndexerDataProvider")
	public static Object[][] testRichStringBuilderSetIndexerDataProvider()
	{
		return new Object[][]
		{
			{2,  '*',  "Lo*em ipsum dolor sit amet"},
		};
	}

    @Test
    public void testRichStringBuilderAppendRichChar()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.appendInternal(new RichChar('z', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.add(fonts, G_GREEN_FONT);
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit ametz", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderAppendChar()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.appendInternal('z', new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.add(fonts, G_GREEN_FONT);
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit ametz", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderAppendNumberChars()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.appendInternal('z', 4, new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.addRange(fonts, new RunPr[] {G_GREEN_FONT, G_GREEN_FONT, G_GREEN_FONT, G_GREEN_FONT});
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit ametzzzz", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderAppendRichString()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.appendInternal(buildRichString(
            "blah",
            new RunPr[] {G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT, G_GREEN_FONT}));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.addRange(fonts, new RunPr[] {G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT, G_GREEN_FONT});
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit ametblah", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderAppendString()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.appendInternal("blah", new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.addRange(fonts, new RunPr[] {G_GREEN_FONT, G_GREEN_FONT, G_GREEN_FONT, G_GREEN_FONT});
        assertRichStringBuilder(buildRichString("Lorem ipsum dolor sit ametblah", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test (dataProvider = "testRichStringBuilderInsertCharDataProvider")
    public void testRichStringBuilderInsertChar(int index, char value, String expectedResult)
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.insertInternal(5, '*', new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        fonts.add(5, G_GREEN_FONT);
        assertRichStringBuilder(buildRichString("Lorem* ipsum dolor sit amet", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRichStringBuilderInsertCharDataProvider")
	public static Object[][] testRichStringBuilderInsertCharDataProvider()
	{
		return new Object[][]
		{
			{0,  'a',  "aLorem ipsum dolor sit amet"},
			{5,  'Z',  "LoremZ ipsum dolor sit amet"},
		};
	}

    @Test
    public void testRichStringBuilderInsertString()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.insertInternal(5, "Wow", new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        msArrayList.insertRange(fonts, 5, new RunPr[]{G_GREEN_FONT, G_GREEN_FONT, G_GREEN_FONT});
        assertRichStringBuilder(buildRichString("LoremWow ipsum dolor sit amet", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderInsertNumberStrings()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        ArrayList<IChar> charSource = new ArrayList<IChar>(); { charSource.add(new RichChar('\u0000', G_GREEN_FONT)); charSource.add(new RichChar('\u0000', G_BLUE_FONT)); charSource.add(new RichChar('\u0000', G_YELLOW_FONT)); }
        builder.insertInternal(
            5,
            "Wow",
            3,
            charSource);

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        for (int i = 0; i < 3; i++)
            fonts.add(5, G_YELLOW_FONT);
        for (int i = 0; i < 3; i++)
            fonts.add(5, G_BLUE_FONT);
        for (int i = 0; i < 3; i++)
            fonts.add(5, G_GREEN_FONT);

        assertRichStringBuilder(buildRichString("LoremWowWowWow ipsum dolor sit amet", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderReplaceByValue()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.replaceInternal("or", "**");

        assertRichStringBuilder(buildRichString("L**em ipsum dol** sit amet", G_LOREM_IPSUM_RICH_FONTS), builder);
    }

    @Test
    public void testRichStringBuilderReplaceAtIndex()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.replaceInternal(2, '*', new RichChar('\u0000', G_GREEN_FONT));

        ArrayList<RunPr> fonts = msArrayList.ctor(G_LOREM_IPSUM_RICH_FONTS);
        fonts.set(2, G_GREEN_FONT);
        assertRichStringBuilder(buildRichString("Lo*em ipsum dolor sit amet", msArrayList.toArray(fonts, new RunPr[0])), builder);
    }

    @Test
    public void testRichStringBuilderRemove()
    {
        RichStringBuilder builder = buildLoremIpsumRichStringBuilder();

        builder.removeInternal(7, 9);

        RunPr[] fonts = populateFonts(
            new RunPr[] {G_BLUE_FONT, G_YELLOW_FONT, G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT},
            new int[] {6, 1, 1, 4, 5});
        assertRichStringBuilder(buildRichString("Lorem ir sit amet", fonts), builder);
    }

    private static RichString buildLoremIpsumRichString()
    {
        return new RichString(buildRichChars(LOREM_IPSUM, G_LOREM_IPSUM_RICH_FONTS));
    }

    private static RichStringBuilder buildLoremIpsumRichStringBuilder()
    {
        return new RichStringBuilder(buildLoremIpsumRichString());
    }

    private static RichString buildRichString(String value, RunPr[] fonts)
    {
        return new RichString(buildRichChars(value, fonts));
    }

    private static ArrayList<IChar> buildRichChars(String value, /*params*/ RunPr... fonts)
    {
        msAssert.areEqual(value.length(), fonts.length);

        ArrayList<IChar> chars = new ArrayList<IChar>(value.length());
        for (int i = 0; i < value.length(); i++)
            msArrayList.add(chars, new RichChar(value.charAt(i), fonts[i]));

        return chars;
    }

    private static RunPr[] populateFonts(RunPr[] parts, int[] lengths)
    {
        msAssert.areEqual(parts.length, lengths.length);

        ArrayList<RunPr> result = new ArrayList<RunPr>();
        for (int i = 0; i < parts.length; i++)
        {
            for (int j = 0; j < lengths[i]; j++)
                msArrayList.add(result, parts[i]);
        }

        return msArrayList.toArray(result, new RunPr[0]);
    }

    private static void assertFont(RunPr expected, RunPr actual)
    {
        assertFont(expected, actual, null);
    }

    private static void assertFont(RunPr expected, RunPr actual, String context)
    {
        msAssert.isTrue(AttrCollection.equals(expected, actual, null), msString.format("{0}expected: {1}; actual:{2}", context, expected, actual));
    }

    private static void assertRichChar(RichChar expected, RichChar actual)
    {
        assertRichChar(expected, actual, null);
    }

    private static void assertRichChar(RichChar expected, RichChar actual, String context)
    {
        msAssert.areEqual(expected.getCharacter(), actual.getCharacter());
        assertFont(expected.getRunPr(), actual.getRunPr(), msString.format("{0}Char: {1}; ", context, expected.getCharacter()));
    }

    static void assertRichString(RichString expected, RichString actual)
    {
        msAssert.areEqual(expected.toSystemString(), actual.toSystemString());

        for (int i = 0; i < actual.getLength(); i++)
            assertRichChar(expected.getInternal(i), actual.getInternal(i), msString.format("{0}[{1}]; ", expected.toSystemString(), i));
    }

    private static void assertRichStringBuilder(RichString expected, RichStringBuilder actual)
    {
        assertRichString(expected, actual.toRichString());
    }

    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet";
    private static /*final*/ RunPr[] G_LOREM_IPSUM_RICH_FONTS;
    private static /*final*/ RunPr G_BLUE_FONT = new RunPr();
    private static /*final*/ RunPr G_YELLOW_FONT = new RunPr();
    private static /*final*/ RunPr G_GREEN_FONT = new RunPr();
    private static /*final*/ RunPr G_EMPTY_FONT = new RunPr();
    static/* TestRichStrings()*/
    {
        G_BLUE_FONT.setBold(AttrBoolEx.TRUE);
        G_BLUE_FONT.setColor(DrColor.BLUE);

        G_YELLOW_FONT.setItalic(AttrBoolEx.TRUE);
        G_YELLOW_FONT.setColor(DrColor.YELLOW);

        G_GREEN_FONT.setColor(DrColor.GREEN);

        G_LOREM_IPSUM_RICH_FONTS = populateFonts(
            new RunPr[] {G_BLUE_FONT, G_YELLOW_FONT, G_EMPTY_FONT, G_BLUE_FONT, G_YELLOW_FONT},
            new int[] {6, 6, 5, 4, 5});
    }
}

