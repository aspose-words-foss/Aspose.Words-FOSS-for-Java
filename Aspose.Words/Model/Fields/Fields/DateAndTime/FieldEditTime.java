// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the EDITTIME field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the total editing time, in minutes, since the document was created.
/// </remarks>
public class FieldEditTime extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        Document document = fetchDocument();
        Int32Constant value = getResultInternal(document);
        return new FieldUpdateActionApplyResult(this, value);
    }

    private static Int32Constant getResultInternal(Document document)
    {
        return new Int32Constant(document.getBuiltInDocumentProperties().getTotalEditingTime());
    }

    static IFieldInfoResultProvider FieldInfoResultProvider = new EditTimeInfoResultProvider();

    private static class EditTimeInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return getResultInternal(document);
        }
    }
}

