// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the NUMCHARS field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the number of characters in the current document, as recorded in the <b>Characters</b> property of the
/// built-in document properties.
/// </remarks>
public class FieldNumChars extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        Int32Constant value = getResultInternal(fetchDocument());
        return new FieldUpdateActionApplyResult(this, value);
    }

    private static Int32Constant getResultInternal(Document document)
    {
        return new Int32Constant(document.getBuiltInDocumentProperties().getCharacters());
    }

    static IFieldInfoResultProvider FieldInfoResultProvider = new NumCharsInfoResultProvider();

    private static class NumCharsInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return getResultInternal(document);
        }
    }
}

