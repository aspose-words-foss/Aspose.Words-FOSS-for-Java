// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the COMMENTS field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves, and optionally sets, the comments relating to the current document, as recorded in the <see cref="BuiltInDocumentProperties.Comments"/> property
/// of the built-in document properties.
/// </remarks>
public class FieldComments extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return new FieldUpdateActionApplyResult(this, getResultInternal(fetchDocument(), getFieldCodeCache()));
    }

    private static String getResultInternal(Document document, IFieldCode fieldCode) throws Exception
    {
        BuiltInDocumentProperties properties = document.getBuiltInDocumentProperties();

        String text = fieldCode.getArgumentAsString(TEXT_ARGUMENT_INDEX);
        if (text != null)
            properties.setComments(text);

        return properties.getComments();
    }

    /// <summary>
    /// Gets or sets the text of the comments.
    /// </summary>
    public String getText() { return getFieldCodeCache().getArgumentAsString(TEXT_ARGUMENT_INDEX); }
    public void setText(String value) throws Exception { getFieldCodeCache().setArgument(TEXT_ARGUMENT_INDEX, value); }

    private static final int TEXT_ARGUMENT_INDEX = 0;

    static IFieldInfoResultProvider FieldInfoResultProvider = new CommentsInfoResultProvider();

    private static class CommentsInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode) throws Exception
        {
            return new StringConstant(getResultInternal(document, fieldCode));
        }
    }
}

