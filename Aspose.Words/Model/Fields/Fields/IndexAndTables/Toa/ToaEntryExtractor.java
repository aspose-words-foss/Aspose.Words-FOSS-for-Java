// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ms.System.msString;
import com.aspose.DictionaryExtension;
import com.aspose.Debug;
import java.util.Map;
import com.aspose.StringToIntDictionary;


class ToaEntryExtractor extends FieldExtractor
{
    private ToaEntryExtractor(Document document, String entryCategory, Bookmark bookmark, String sequenceName, boolean includeRefDocFields, FieldSeqDataProvider seqDataProvider)
    {
        mDocument = document;
        mEntryCategory = entryCategory;
        mBookmark = bookmark;
        mSequenceName = sequenceName;
        mSeqDataProvider = seqDataProvider;
        mIsBookmarkRangeStarted = mBookmark == null;
        mIncludeRefDocFields = includeRefDocFields;
        mBookmarkSequenceNumbersCache = BookmarkSequenceNumbersCache.build(document, mSequenceName, mSeqDataProvider);
    }

    static ArrayList<ToaEntry> extractToaEntries(Document document, String entryCategory, Bookmark bookmark, String sequenceName, FieldSeqDataProvider seqDataProvider)
    {
        return extractToaEntries(
            document,
            entryCategory,
            bookmark,
            sequenceName,
            true,
            seqDataProvider).getValues();
    }

    private static SortedList<String, ToaEntry> extractToaEntries(Document document,
        String entryCategory,
        Bookmark bookmark,
        String sequenceName,
        boolean includeRefDocFields,
        FieldSeqDataProvider seqDataProvider)
    {
        ToaEntryExtractor extractor = new ToaEntryExtractor(document,
            entryCategory,
            bookmark,
            sequenceName,
            includeRefDocFields,
            seqDataProvider);
        extractor.extract(document);
        return extractor.mEntries;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        mIsBookmarkRangeStarted = mIsBookmarkRangeStarted || isBookmarkRangeStart(bookmarkStart);

        return VisitorAction.CONTINUE;
    }

    private boolean isBookmarkRangeStart(Node node)
    {
        return (mBookmark != null) && (node == mBookmark.getBookmarkStart());
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd) throws Exception
    {
        return isBookmarkRangeEnd(bookmarkEnd)
            ? VisitorAction.STOP
            : VisitorAction.CONTINUE;
    }

    private boolean isBookmarkRangeEnd(Node node) throws Exception
    {
        return (mBookmark != null) && (node == mBookmark.getBookmarkEnd());
    }

    protected /*override*/ void onFieldExtracted() throws Exception
    {
        switch (getCurrentFieldType())
        {
            case FieldType.FIELD_TOA_ENTRY:
                processFieldTA((FieldTA)getCurrentField());
                break;
            case FieldType.FIELD_SEQUENCE:
                processFieldSequence((FieldSeq)getCurrentField());
                break;
            case FieldType.FIELD_REF_DOC:
                processFieldRD((FieldRD)getCurrentField());
                break;
            default:
                break;
        }
    }

    private void processFieldTA(FieldTA field) throws Exception
    {
        if (!mIsBookmarkRangeStarted)
            return;

        String entryTitle = field.getLongCitation();
        if (msString.isNullOrEmpty(entryTitle))
            return;

        if (!msString.equals(field.getEntryCategory(), mEntryCategory))
            return;

        ToaEntry entry = getOrCreateToaEntry(entryTitle, field);

        addEntryPage(entry, field);
    }

    private ToaEntry getOrCreateToaEntry(String entryTitle, FieldTA field)
    {
        ToaEntry entry = DictionaryExtension.getValueOrNull(mEntries, entryTitle);

        if (entry == null)
        {
            entry = new ToaEntry(field.getLongCitationRange());
            mEntries.set(entryTitle, entry);
        }

        return entry;
    }

    private void addEntryPage(ToaEntry entry, FieldTA field) throws Exception
    {
        boolean isBold = field.isBold();
        boolean isItalic = field.isItalic();

        if (field.hasPageRangeBookmarkNameSwitch())
        {
            // FOSS
            {
                entry.addPageRangeError(isBold, isItalic, getPageNumber(field), getSequenceNumber());
            }
        }
        else
        {
            entry.addSinglePage(isBold, isItalic, getPageNumber(field), getSequenceNumber());
        }
    }

    private int getSequenceNumber() throws Exception
    {
        return getSequenceNumber(mSequenceName, mLastFieldSeqFound, mSeqDataProvider);
    }

    private static int getSequenceNumber(String sequenceName, FieldSeq lastFieldSeqFound, IFieldUpdateDataProvider seqDataProvider) throws Exception
    {
        Debug.assertTrue((sequenceName == null) == (seqDataProvider == null));

        if (sequenceName == null)
            return ToaEntry.NULL_SEQUENCE_NUMBER;

        if (lastFieldSeqFound == null)
            return 0;

        Constant constant = seqDataProvider.getValue(lastFieldSeqFound);
        if (constant == null)
            return 0;

        return (int)constant.getValueDouble();
    }

    private int getSequenceNumber(String bookmarkName)
    {
        if (mBookmarkSequenceNumbersCache == null)
            return ToaEntry.NULL_SEQUENCE_NUMBER;

        return mBookmarkSequenceNumbersCache.getCachedSequenceNumber(bookmarkName);
    }

    private int getPageNumber(FieldTA field)
    {
        // FOSS
        return 0;
    }

    private void processFieldSequence(FieldSeq field)
    {
        if (mSequenceName == null)
            return;

        if (!msString.equals(field.getSequenceIdentifier(), mSequenceName))
            return;

        mLastFieldSeqFound = field;
    }

    private void processFieldRD(FieldRD field) throws Exception
    {
        if (!mIncludeRefDocFields)
            return;

        Document referenceDocument = FieldIndexAndTablesUtil.openRefDocument(field);
        if (referenceDocument == null)
            return;

        FieldSeqDataProvider seqDataProvider = mSeqDataProvider != null
            ? new FieldSeqDataProvider(referenceDocument)
            : null;
        SortedList<String, ToaEntry> entries = extractToaEntries(referenceDocument, mEntryCategory, null, mSequenceName, false, seqDataProvider);

        if (entries.getCount() == 0)
            return;

        for (Map.Entry<String, ToaEntry> pair : entries)
        {
            ToaEntry entry = DictionaryExtension.getValueOrNull(mEntries, pair.getKey());

            if (entry == null)
                mEntries.set(pair.getKey(), pair.getValue());
            else
                entry.copyFrom(pair.getValue());
        }
    }

    private /*final*/ SortedList<String, ToaEntry> mEntries = new SortedList<String, ToaEntry>();
    private /*final*/ Document mDocument;
    private /*final*/ String mEntryCategory;
    private /*final*/ Bookmark mBookmark;
    private /*final*/ String mSequenceName;
    private /*final*/ FieldSeqDataProvider mSeqDataProvider;
    private /*final*/ BookmarkSequenceNumbersCache mBookmarkSequenceNumbersCache;
    private /*final*/ boolean mIncludeRefDocFields;

    private boolean mIsBookmarkRangeStarted;
    private FieldSeq mLastFieldSeqFound;

    private static class BookmarkSequenceNumbersCache extends FieldExtractor
    {
        private BookmarkSequenceNumbersCache(String sequenceName, FieldSeqDataProvider seqDataProvider)
        {
            mSequenceName = sequenceName;
            mSeqDataProvider = seqDataProvider;
        }

        static BookmarkSequenceNumbersCache build(Document document, String sequenceName, FieldSeqDataProvider seqDataProvider)
        {
            if (sequenceName == null)
                return null;

            BookmarkSequenceNumbersCache cache = new BookmarkSequenceNumbersCache(sequenceName, seqDataProvider);
            cache.extract(document);
            return cache;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart) throws Exception
        {
            int sequenceNumber = getSequenceNumber(mSequenceName, mLastFieldSeqFound, mSeqDataProvider);
            mCache.set(bookmarkStart.getName(), sequenceNumber);
            return super.visitBookmarkStart(bookmarkStart);
        }

        protected /*override*/ void onFieldExtracted()
        {
            if (getCurrentFieldType() != FieldType.FIELD_SEQUENCE)
                return;

            FieldSeq fieldSeq = (FieldSeq)getCurrentField();
            if (!msString.equals(fieldSeq.getSequenceIdentifier(), mSequenceName))
                return;

            mLastFieldSeqFound = fieldSeq;
        }

        int getCachedSequenceNumber(String bookmarkName)
        {
            int sequenceNumber = mCache.get(bookmarkName);
            return StringToIntDictionary.isNullSubstitute(sequenceNumber)
                ? 0
                : sequenceNumber;
        }

        private /*final*/ StringToIntDictionary mCache = new StringToIntDictionary(false);
        private /*final*/ String mSequenceName;
        private /*final*/ FieldSeqDataProvider mSeqDataProvider;

        private FieldSeq mLastFieldSeqFound;
    }
}

