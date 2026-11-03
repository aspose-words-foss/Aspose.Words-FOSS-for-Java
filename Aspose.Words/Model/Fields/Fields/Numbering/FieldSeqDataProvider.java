// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides values for the SEQ fields.
/// </summary>
class FieldSeqDataProvider implements IFieldUpdateDataProvider
{
    FieldSeqDataProvider(FieldUpdater fieldUpdater)
    {
        mBookmarkCache = fieldUpdater.getBookmarkCache();
        mDocument = fieldUpdater.getDocument();
    }

    FieldSeqDataProvider(Document document)
    {
        mBookmarkCache = new BookmarkCache(document);
        mDocument = document;
    }

    public Object /*IFieldUpdateDataProvider.*/getData(Field field)
    {
        return null;
    }

    public Constant /*IFieldUpdateDataProvider.*/getValue(Field field) throws Exception
    {
        switch (field.getType())
        {
            case FieldType.FIELD_SEQUENCE:
                ensureEvaluator();
                return mValueEvaluator.getValue((FieldSeq)field);
            default:
                return null;
        }
    }

    void addSequenceBookmark(String bookmarkName) throws Exception
    {
        if (mValueEvaluator == null)
            return;

        mValueEvaluator.addSequenceBookmark(bookmarkName);
    }

    void invalidate()
    {
        mValueEvaluator = null;
    }

    /// <summary>
    /// Initializes <see cref="mValueEvaluator"/> member variable if it has not been initialized yet.
    /// </summary>
    private void ensureEvaluator()
    {
        // Calculate SEQ fields' values on the first demand as it is too expensive to do it in ctor
        // since it is called for every SEQ field entry's update.
        if (mValueEvaluator != null)
            return;

        mValueEvaluator = new FieldSeqValueEvaluator(mDocument, mBookmarkCache);
    }

    private /*final*/ BookmarkCache mBookmarkCache;
    private /*final*/ Document mDocument;
    private FieldSeqValueEvaluator mValueEvaluator;
}

