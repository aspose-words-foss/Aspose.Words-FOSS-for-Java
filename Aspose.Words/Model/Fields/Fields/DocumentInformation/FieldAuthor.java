// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;


/// <summary>
/// Implements the AUTHOR field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves, and optionally sets, the document author's name, as recorded in the <b>Author</b> property of the
/// built-in document properties.
/// </remarks>
public class FieldAuthor extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Document document = fetchDocument();

        String authorName = getResultInternal(document, getFieldCodeCache());

        return new FieldUpdateActionApplyResult(this, authorName);
    }

    private static String getResultInternal(Document document, IFieldCode fieldCode) throws Exception
    {
        String authorName = getAuthorName(document, fieldCode);

        if (authorName != null)
            saveAuthorNameInDocumentProperties(document, authorName);

        return authorName;
    }

    private static void saveAuthorNameInDocumentProperties(Document document, String authorName)
    {
        BuiltInDocumentProperties properties = document.getBuiltInDocumentProperties();
        properties.setAuthor(authorName);
    }

    private static String getAuthorName(Document document, IFieldCode fieldCode) throws Exception
    {
        // We can get author's name from 3 source: fieldcode, built-in document properties and from field options.
        // below illustrates the preparation from these sources in order of priority
        String authorFromFieldCode = fieldCode.getArgumentAsString(AUTHOR_NAME_ARGUMENT_INDEX);
        if (authorFromFieldCode != null)
            return authorFromFieldCode;

        BuiltInDocumentProperties properties = document.getBuiltInDocumentProperties();
        if (StringUtil.hasChars(properties.getAuthor()))
            return properties.getAuthor();

        return document.getFieldOptions().getDefaultDocumentAuthor();
    }

    /// <summary>
    /// Gets or sets the document author's name.
    /// </summary>
    public String getAuthorName() { return getFieldCodeCache().getArgumentAsString(AUTHOR_NAME_ARGUMENT_INDEX); }
    public void setAuthorName(String value) throws Exception { getFieldCodeCache().setArgument(AUTHOR_NAME_ARGUMENT_INDEX, value); }

    private static final int AUTHOR_NAME_ARGUMENT_INDEX = 0;

    static IFieldInfoResultProvider FieldInfoResultProvider = new AuthorInfoResultProvider();

    private static class AuthorInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode) throws Exception
        {
            return new StringConstant(getResultInternal(document, fieldCode));
        }
    }
}

