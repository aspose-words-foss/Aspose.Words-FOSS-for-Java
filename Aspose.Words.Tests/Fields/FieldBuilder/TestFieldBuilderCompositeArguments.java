// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;


@Test
public class TestFieldBuilderCompositeArguments extends TestFieldBuilderBase
{
    @Test
    public void testFieldBuilderArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_FORMULA)
            .addArgument(new FieldBuilder(FieldType.FIELD_STYLE_REF).addArgument("Heading 1"))
            .addArgument("+")
            .addArgument(new FieldBuilder(FieldType.FIELD_FORMULA).addArgument(3));

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_FORMULA)
            .contains()
                .start().text(" = ")
                .start().text(" STYLEREF \"Heading 1\" ").separator().end()
                .text(" + ")
                .start().text(" = 3 ").separator().end()
                .text(" ").separator().end().andNoMore();
    }

    @Test
    public void testFieldArgumentBuilderWithRunArguments() throws Exception
    {
        Document document = new Document();

        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_IF)
            .addArgument("left expression")
            .addArgument("=")
            .addArgument("right expression")
            .addArgument(new FieldArgumentBuilder().addText("true part ").addNode(new Run(document, "expressions are equal")))
            .addArgument(new FieldArgumentBuilder().addText("false part ").addNode(new Run(document, "expressions are not equal")));

        Field field = buildAndInsert(fieldBuilder, document);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_IF)
            .contains()
                .start()
                .text(" IF \"left expression\" = \"right expression\" \"true part expressions are equal\" \"false part expressions are not equal\" ")
                .separator()
                .end()
                .andNoMore();
    }

    @Test
    public void testFieldArgumentBuilderWithParagraphBreakArguments() throws Exception
    {
        DocumentBuilder documentBuilder = new DocumentBuilder();
        documentBuilder.write("Run 1");
        documentBuilder.write("Run 2");
        documentBuilder.write("Run 3");

        Document document = documentBuilder.getDocument();
        Run after = (Run)document.getNodeById("1.0.0.0");
        Run before = (Run)document.getNodeById("2.0.0.0");

        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_IF)
            .addArgument("left expression")
            .addArgument("=")
            .addArgument("right expression")
            .addArgument(new FieldArgumentBuilder().addText("\rexpressions are equal"))
            .addArgument(new FieldArgumentBuilder().addText("\rexpressions are not equal"));

        Field field = fieldBuilder.buildAndInsert(document.getFirstSection().getBody().getFirstParagraph().getRuns().get(2));

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_IF)
            .inside((Paragraph)document.getNodeById("0.0.0"), (Paragraph)document.getNodeById("2.0.0"))
            .between(after, before)
            .contains()
                .start().text(" IF \"left expression\" = \"right expression\" \"").paragraphBreak()
                .text("expressions are equal\" \"").paragraphBreak()
                .text("expressions are not equal\" ").separator().end().andNoMore();
    }

    @Test
    public void testFieldArgumentBuilderWithFieldBuilderArguments() throws Exception
    {
        FieldBuilder fieldBuilder = new FieldBuilder(FieldType.FIELD_IF)
            .addArgument("left expression")
            .addArgument("=")
            .addArgument("right expression")
            .addArgument(
                new FieldArgumentBuilder()
                    .addText("Firstname: ")
                    .addField(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("firstname")))
            .addArgument(
                new FieldArgumentBuilder()
                    .addText("Secondname: ")
                    .addField(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("secondname")));

        Field field = buildAndInsert(fieldBuilder);

        FieldAssert.field(field)
            .ofType(FieldType.FIELD_IF)
            .contains()
                .start().text(" IF \"left expression\" = \"right expression\" \"Firstname: ")
                .start().text(" MERGEFIELD firstname ").separator().end()
                .text("\" \"Secondname: ")
                .start().text(" MERGEFIELD secondname ").separator().end()
                .text("\" ").separator().end().andNoMore();
    }
}

