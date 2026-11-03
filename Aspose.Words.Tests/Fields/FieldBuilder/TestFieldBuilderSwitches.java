// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.SystemPal;
import org.testng.annotations.DataProvider;


@Test
public class TestFieldBuilderSwitches extends TestFieldBuilderBase
{
    @Test
    public void testSimpleSwitches() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_LIST_NUM)
            .addSwitch("l")
            .addSwitch("s");

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_LIST_NUM)
            .contains().start().text(" LISTNUM \\l \\s ").end().andNoMore();
    }

    @Test
    public void testStringSwitches() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_BARCODE)
            .addSwitch("u", "value")
            .addSwitch("f", "one more value")
            .addSwitch("*", "MERGEFORMAT");

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_BARCODE)
            .contains().start().text(" BARCODE \\u value \\f \"one more value\" \\* MERGEFORMAT ").end().andNoMore();
    }

    @Test
    public void testIntSwitches() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_NOTE_REF)
            .addSwitch("h", -8)
            .addSwitch("p", 44);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_NOTE_REF)
            .contains().start().text(" NOTEREF \\h -8 \\p 44 ").separator().end().andNoMore();
    }

    @Test
    public void testDoubleSwitches() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_SYMBOL)
            .addSwitch("j", 39.1)
            .addSwitch("a", 15.15);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_SYMBOL)
            .contains().start().text(" SYMBOL \\j 39.1 \\a 15.15 ").end().andNoMore();
    }

    @Test
    public void testSwitchesWithBackslash() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_DATE)
            .addSwitch("\\h")
            .addSwitch("\\l", "value")
            .addSwitch("\\s", 42)
            .addSwitch("\\u", 66.66)
            .addSwitch("\\@", "MM/yy");

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_DATE)
            .contains().start().text(" DATE \\h \\l value \\s 42 \\u 66.66 \\@ MM/yy ").separator().end().andNoMore();
    }

    @Test (dataProvider = "testDoubleSwitchCultureDataProvider")
    public void testDoubleSwitchCulture(String culture, String expectedText) throws Exception
    {
        SystemPal.setCulture(culture);

        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_AUTO_NUM)
            .addSwitch("s", 82.37);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_AUTO_NUM)
            .contains().start().text(" AUTONUM \\s ").text(expectedText).text(" ").end().andNoMore();
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDoubleSwitchCultureDataProvider")
	public static Object[][] testDoubleSwitchCultureDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"ru-RU",  "82,37"},
			{"de-DE",  "82,37"},
			{"en-GB",  "82.37"},
		};
	}
}

