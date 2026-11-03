// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/09/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Unlinks single or multimple fields.
/// </summary>
class FieldUnlinker
{
    private FieldUnlinker(Document document)
    {
        mDocument = document;
        mFields = new ArrayList<Field>();
    }

    /// <summary>
    /// Unlinks the given field.
    /// </summary>
    static void unlinkField(Field field) throws Exception
    {
        Document document = field.fetchDocument();
        FieldUnlinker unlinker = new FieldUnlinker(document);
        unlinker.addField(field);
        unlinker.unlinkFields();
    }

    /// <summary>
    /// Unlinks fields contained in the given node.
    /// </summary>
    static void unlinkFields(Node node) throws Exception
    {
        Document document = node.fetchDocument();
        FieldUnlinker unlinker = new FieldUnlinker(document);

        for (Field field : FieldExtractor.extractToCollection(node))
            unlinker.addField(field);

        unlinker.unlinkFields();
    }

    /// <summary>
    /// Unlinks fields contained in the given range.
    /// </summary>
    static void unlinkFields(NodeRange range) throws Exception
    {
        Document document = range.getDocument().fetchDocument();
        FieldUnlinker unlinker = new FieldUnlinker(document);

        for (Field field : FieldExtractor.extractToCollection(range))
            unlinker.addField(field);

        unlinker.unlinkFields();
    }

    private void unlinkFields() throws Exception
    {
        for (Field field : mFields)
        {
            updateListLabelIfNeeded(field);
            field.unlinkCore();
        }
    }

    private void updateListLabelIfNeeded(Field field) throws Exception
    {
        if (mListLabelsUpdated)
            return;

        if (!FieldNumUtil.isFieldNum(field.getType()))
            return;

        mDocument.updateListLabels();
        mListLabelsUpdated = true;
    }

    private void addField(Field field)
    {
        msArrayList.add(mFields, field);
    }

    private /*final*/ Document mDocument;
    private /*final*/ ArrayList<Field> mFields;
    private boolean mListLabelsUpdated;
}

