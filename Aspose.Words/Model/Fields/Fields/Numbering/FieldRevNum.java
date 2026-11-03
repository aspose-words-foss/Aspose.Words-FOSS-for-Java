// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the REVNUM field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the document's revision number, as recorded in the <b>Revision</b> property of the
/// built-in document properties.
/// </remarks>
public class FieldRevNum extends Field
{
    /*override*/ FieldUpdateAction updateCore()
    {
        Int32Constant value = getResultInternal(fetchDocument());
        return new FieldUpdateActionApplyResult(this, value);
    }

    private static Int32Constant getResultInternal(Document document)
    {
        return new Int32Constant(document.getBuiltInDocumentProperties().getRevisionNumber());
    }

    static IFieldInfoResultProvider FieldInfoResultProvider = new RevNumInfoResultProvider();

    private static class RevNumInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return getResultInternal(document);
        }
    }
}

