// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the LASTSAVEDBY field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the name of the user who last modified and saved the current document, as recorded in the <b>LastModifiedBy</b>
/// property of the built-in document properties.
/// </remarks>
public class FieldLastSavedBy extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return new FieldUpdateActionApplyResult(this, getResultInternal(fetchDocument()));
    }

    private static String getResultInternal(Document document)
    {
        return document.getBuiltInDocumentProperties().getLastSavedBy();
    }

    static IFieldInfoResultProvider FieldInfoResultProvider = new LastSavedByInfoResultProvider();

    private static class LastSavedByInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return new StringConstant(getResultInternal(document));
        }
    }
}

