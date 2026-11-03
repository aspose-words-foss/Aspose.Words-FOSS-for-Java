// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



public abstract class TestFieldBuilderBase extends TestFieldsBase
{
    protected Field buildAndInsert(FieldBuilder fieldBuilder) throws Exception
    {
        Document document = new Document();

        return buildAndInsert(fieldBuilder, document);
    }

    protected Field buildAndInsert(FieldBuilder fieldBuilder, Document document) throws Exception
    {
         return fieldBuilder.buildAndInsert(document.getFirstSection().getBody().getFirstParagraph());
    }
}

