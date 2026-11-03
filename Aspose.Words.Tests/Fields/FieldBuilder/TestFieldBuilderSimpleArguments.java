// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;


@Test
public class TestFieldBuilderSimpleArguments extends TestFieldBuilderBase
{
    @Test
    public void testSimpleStringArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_REF)
            .addArgument("StringArgument")
            .addArgument("NextStringArgument");

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_REF)
            .contains().start().text(" REF StringArgument NextStringArgument ").separator().end().andNoMore();
    }

    @Test
    public void testComplexStringArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_STYLE_REF)
            .addArgument(" string argument with whitespaces ")
            .addArgument("string argument \"with\" qoutes")
            .addArgument("string argument \\with\\ slashes")
            .addArgument("\\\\share name\\folder name\\file name.txt");

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_STYLE_REF)
            .contains().start()
            .text(" STYLEREF ")
            .text("\" string argument with whitespaces \" ")
            .text("\"string argument \\\"with\\\" qoutes\" ")
            .text("\"string argument \\\\with\\\\ slashes\" ")
            .text("\"\\\\\\\\share name\\\\folder name\\\\file name.txt\" ")
            .separator().end().andNoMore();
    }

    @Test
    public void testIntArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_INCLUDE_TEXT)
            .addArgument(77)
            .addArgument(0)
            .addArgument(-321);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_INCLUDE_TEXT)
            .contains().start().text(" INCLUDETEXT 77 0 -321 ").separator().end().andNoMore();
    }

    @Test
    public void testDoubleArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_TIME)
            .addArgument(35)
            .addArgument(0.1)
            .addArgument(-17.9);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_TIME)
            .contains().start().text(" TIME 35 0.1 -17.9 ").separator().end().andNoMore();
    }

    @Test
    public void testMultipleArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_FILE_NAME)
            .addArgument("one")
            .addArgument(2)
            .addArgument("one two three")
            .addArgument(4.5);

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_FILE_NAME)
            .contains().start().text(" FILENAME one 2 \"one two three\" 4.5 ").separator().end().andNoMore();
    }
}
