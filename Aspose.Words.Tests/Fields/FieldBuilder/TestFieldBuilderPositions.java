// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;


@Test
public class TestFieldBuilderPositions extends TestFieldBuilderBase
{
    @Test
    public void testInsertIntoParagraph() throws Exception
    {
        Document document = buildTestDocument();
        Paragraph inside = (Paragraph) document.getNodeById("1.0.0");
        Run after = (Run) document.getNodeById("4.1.0.0");

        Field field = new FieldBuilder(FieldType.FIELD_PAGE)
            .buildAndInsert(document.getFirstSection().getBody().getParagraphs().get(1));

        FieldAssert.field(field)
            .inside(inside)
            .between(after, null);
    }

    @Test
    public void testInsertIntoEmptyParagraph() throws Exception
    {
        Document document = buildTestDocument();
        Paragraph inside = (Paragraph)document.getNodeById("2.0.0");

        Field field = new FieldBuilder(FieldType.FIELD_PAGE)
            .buildAndInsert(document.getFirstSection().getBody().getParagraphs().get(2));

        FieldAssert.field(field)
            .inside(inside)
            .between(null, null);
    }

    @Test
    public void testInsertBeforeRun() throws Exception
    {
        Document document = buildTestDocument();
        Paragraph inside = (Paragraph)document.getNodeById("1.0.0");
        Run after = (Run)document.getNodeById("1.1.0.0");
        Run before = (Run)document.getNodeById("2.1.0.0");

        Field field = new FieldBuilder(FieldType.FIELD_PAGE)
            .buildAndInsert(document.getFirstSection().getBody().getParagraphs().get(1).getRuns().get(2));

        FieldAssert.field(field)
            .inside(inside)
            .between(after, before);
    }

    private static Document buildTestDocument() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.write("I. ");
        builder.write("This ");
        builder.write("is ");
        builder.write("first ");
        builder.write("paragraph.");
        builder.writeln();

        builder.write("II. ");
        builder.write("This ");
        builder.write("is ");
        builder.write("second ");
        builder.write("paragraph.");
        builder.writeln();

        return builder.getDocument();
    }
}

