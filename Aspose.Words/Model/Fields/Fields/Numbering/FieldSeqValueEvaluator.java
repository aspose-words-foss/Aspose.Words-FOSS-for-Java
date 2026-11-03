// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2021 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.NullableInt32;
import com.aspose.StringToIntDictionary;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.DictionaryExtension;
import java.util.ArrayList;
import java.util.HashMap;


/// <summary>
/// Evaluates sequence values.
/// </summary>
/// <dev>
/// Here is how value evaluation works.
///
/// SEQ fields which modify sequence values (i.e. non-cross-reference) are extracted and stored in the
/// extraction order in the sequence items list. Sequence values for each of sequence items are also stored.
///
/// Bookmarks are considered to be sequence markers.
/// A sequence marker points to the last sequence item met to the time of its detecting. When the value for
/// the marker is requested, the evaluator looks for it starting from this sequence item and moving to the first
/// sequence item until a sequence item for the requested sequence (defined by the sequence name) is found.
///
/// These values are evaluated rather than stored because of the possibility of using different sequences
/// in conjunction with the same markers.
///
/// Note, that bookmarks are paired markers i.e. they have two values: corresponding to the starts and
/// corresponding to the ends.
/// </dev>
class FieldSeqValueEvaluator extends FieldExtractor
{
    FieldSeqValueEvaluator(Document document, BookmarkCache bookmarkCache)
    {
    	super(FieldType.FIELD_SEQUENCE);
	
        mBookmarkCache = bookmarkCache;

        // Initializing of a value evaluator consists of two field extracting stages which can not be joined. They are:
        // 1. Collecting of cross-reference bookmarks for SEQ
        // 2. Evaluating of sequence values considering collected bookmark names.

        // Step 1.
        mBookmarkSequenceEnds = BookmarkNamesFinder.findBookmarkNames(document, bookmarkCache);

        // Step 2.
        evaluateSequenceItemValues(document);
    }

    private void evaluateSequenceItemValues(Node document)
    {
        mSequenceStates = new StringToObjDictionary<FieldSeqValueEvaluator.SequenceState>(false);
        extract(document);
        mSequenceStates = null;
    }

    protected /*override*/ void onFieldExtracted() throws Exception
    {
        FieldSeq field = (FieldSeq)getCurrentField();

        if (field.hasBookmarkName() || field.isError(mBookmarkCache))
            return;

        // 1. Retrieve current sequence state. Initialize if not found.
        SequenceState sequenceState = mSequenceStates.get(field.getSequenceIdentifier());
        if (sequenceState == null)
        {
            sequenceState = new SequenceState();
            mSequenceStates.set(field.getSequenceIdentifier(), sequenceState);
        }

        // 2. Change the state according to the field's switches.
        sequenceState.processFieldEntry(field);

        // 3. Save current sequence value for the field.
        mSequenceItemValues.put(field.getStart(), sequenceState.getCurrentValue());

        // 4. Remember the field as a sequence item if needed.
        if (mSequenceItems != null)
            msArrayList.add(mSequenceItems, field);
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
    {
        // Remember bookmark end if needed.
        if (mBookmarkSequenceEnds.containsKey(bookmarkEnd.getName()))
            mBookmarkSequenceEnds.set(bookmarkEnd.getName(), getLastSequenceItemIndex());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph)
    {
        // Notify all of the initialized sequences about the paragraph entry met.
        for (SequenceState sequenceState : (Iterable<SequenceState>) mSequenceStates.getValues())
            sequenceState.processParagraphEntry(paragraph);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Gets a value for the given SEQ field.
    /// </summary>
    Int32Constant getValue(FieldSeq field) throws Exception
    {
        if (field.isError(mBookmarkCache))
            return null;

        return field.isInHeaderFooter()
            ? getHeaderFooterFieldValue(field)
            : getBodyFieldValue(field);
    }

    private Int32Constant getBodyFieldValue(FieldSeq field)
    {
        int value;
        if (field.hasBookmarkName())
        {
            NullableInt32 nullableValue = getBookmarkSequenceValue(field.getBookmarkName(), field.getSequenceIdentifier());
            if (!nullableValue.hasValue())
                return null;

            value = nullableValue.getValue();
        }
        else
        {
            value = getSequenceValue(field.getStart());
        }

        return getInt32Constant(value);
    }

    NullableInt32 getBookmarkSequenceValue(String bookmarkName, String sequenceIdentifier)
    {
        int endIndex = mBookmarkSequenceEnds.get(bookmarkName);
        if (StringToIntDictionary.isNullSubstitute(endIndex))
            return NullableInt32.NULL;

        // SEQ fields use a sequence value corresponding to a bookmark end.
        int value = getInt32Value(endIndex, sequenceIdentifier);
        return new NullableInt32(value);
    }

    private Int32Constant getHeaderFooterFieldValue(FieldSeq field)
    {
        // FOSS: resolving a SEQ field located in a header/footer requires traversing the laid-out pages,
        // which the layout engine provided. Without it, fall back to 0 - the same value the original code
        // returned when no matching SEQ field was found on the page.
        return new Int32Constant(0);
    }

    /// <summary>
    /// Evaluates a sequence value starting from a sequence item with the specified index as per
    /// <see cref="FieldSeqValueEvaluator"/> remarks.
    /// </summary>
    private int getInt32Value(int startIndex, String sequenceName)
    {
        for (int index = startIndex; index >= 0; index--)
        {
            FieldSeq field = mSequenceItems.get(index);
            if (StringUtil.equalsIgnoreCase(field.getSequenceIdentifier(), sequenceName))
            {
                int value = getSequenceValue(field.getStart());
                Debug.assertTrue(value != INVALID_INT_32_VALUE);

                return value;
            }
        }

        // If we are here then a reference to a sequence is met before initialization of this sequence.
        // Return zero in this case as MS Word does.
        return 0;
    }

    /// <summary>
    /// Gets a sequence value of the specified field. Returns <see cref="InvalidInt32Value"/> if it is not found.
    /// </summary>
    private int getSequenceValue(FieldStart fieldStart)
    {
        return DictionaryExtension.getValueOrDefault(mSequenceItemValues, fieldStart, INVALID_INT_32_VALUE);
    }

    /// <summary>
    /// A special factory method for <see cref="Int32Constant"/>.
    /// </summary>
    private static Int32Constant getInt32Constant(int value)
    {
        Debug.assertTrue(value != INVALID_INT_32_VALUE);

        return new Int32Constant(value);
    }

    /// <summary>
    /// Gets an index of the last sequence item.
    /// </summary>
    private int getLastSequenceItemIndex() { return mSequenceItems.size() - 1; }

    void addSequenceBookmark(String bookmarkName) throws Exception
    {
        int endIndex = mBookmarkSequenceEnds.get(bookmarkName);
        if (!StringToIntDictionary.isNullSubstitute(endIndex))
            return;

        Bookmark bookmark = mBookmarkCache.get(bookmarkName);

        endIndex = getClosestSequenceItemIndex(bookmark.getBookmarkEnd(), getLastSequenceItemIndex());

        mBookmarkSequenceEnds.set(bookmarkName, endIndex);
    }

    private int getClosestSequenceItemIndex(Node node, int from)
    {
        for (int i = Math.min(from, getLastSequenceItemIndex()); i >= 0; i--)
            if (Node.nodeAIsAboveNodeB(mSequenceItems.get(i).getEnd(), node))
                return i;

        return -1;
    }

    private /*final*/ BookmarkCache mBookmarkCache;

    private /*final*/ ArrayList<FieldSeq> mSequenceItems = new ArrayList<FieldSeq>();
    private /*final*/ StringToIntDictionary mBookmarkSequenceEnds;
    private /*final*/ HashMap<FieldStart, Integer> mSequenceItemValues = new HashMap<FieldStart, Integer>();
    private StringToObjDictionary<FieldSeqValueEvaluator.SequenceState> mSequenceStates;

    private static final int INVALID_INT_32_VALUE = -1;

    /// <summary>
    /// Specifies paragraphs of which styles are contained between SEQ field entries.
    /// </summary>
    private /*enum*/ final class ParagraphStyles
    {
        private ParagraphStyles(){}
        
        public static final int NONE = 0;
        public static final int HEADING_1 = 1;
        public static final int HEADING_2 = 2;
        public static final int HEADING_3 = 4;
        public static final int HEADING_4 = 8;
        public static final int HEADING_5 = 16;
        public static final int HEADING_6 = 32;
        public static final int HEADING_7 = 64;
        public static final int HEADING_8 = 128;
        public static final int HEADING_9 = 256;

        public static final int length = 10;
    }

    /// <summary>
    /// Represents the state of a single named sequence.
    /// </summary>
    private static class SequenceState
    {
        /// <summary>
        /// Processes a single SEQ field entry.
        /// </summary>
        void processFieldEntry(FieldSeq field)
        {
            setCurrentValue(getNextValue(field));
            mMetParagraphStyles = ParagraphStyles.NONE;
        }

        /// <summary>
        /// Gets the next sequence value to be applied.
        /// </summary>
        private int getNextValue(FieldSeq field)
        {
            // Reset the sequence with the specified number, if switch presents.
            if (field.hasResetNumberSwitch())
            {
                int resetNumber = field.getResetNumberAsInt32().getValueOrDefault();
                return Math.abs(resetNumber);
            }

            // Reset the sequence depending on the specified paragraph heading level, if present.
            // MS Word looks upward from the paragraph where the field is located (inclusively) to the paragraph
            // where the previous field for the same sequence is located (exclusively). If a heading paragraph of
            // a level which is less or equal to the specified one is met, then the sequence is reset.
            if ((mMetParagraphStyles != ParagraphStyles.NONE) && field.hasResetHeadingLevelSwitch())
            {
                int resetHeadingLevel = field.getResetHeadingLevelAsInt32().getValueOrDefault();
                /*ParagraphStyles*/int resetParagraphStyle = getParagraphStyle(resetHeadingLevel);
                if (resetParagraphStyle != ParagraphStyles.NONE)
                {
                    // The following expression evaluates combination of all paragraph styles,
                    // which are less or equal to the specified one.
                    /*ParagraphStyles*/int resetParagraphStyles = (resetParagraphStyle - 1) | resetParagraphStyle;
                    if ((mMetParagraphStyles & resetParagraphStyles) != 0)
                        return 1;
                }
            }

            return field.getInsertClosestPrecedingNumber() ? getCurrentValue() : getCurrentValue() + 1;
        }

        /// <summary>
        /// Processes a single paragraph entry.
        /// </summary>
        void processParagraphEntry(Paragraph paragraph)
        {
            mMetParagraphStyles |= getParagraphStyle(paragraph.getParaPr().getIstd());
        }

        /// <summary>
        /// Gets the current sequence value.
        /// </summary>
        int getCurrentValue() { return mCurrentValue; }; private void setCurrentValue(int value) { mCurrentValue = value; };

        private int mCurrentValue;

        /// <summary>
        /// Returns <see cref="ParagraphStyles"/> value corresponding to the specified style index.
        /// </summary>
        private static /*ParagraphStyles*/int getParagraphStyle(int istd)
        {
            switch (istd)
            {
                case StyleIndex.HEADING_1:
                    return ParagraphStyles.HEADING_1;
                case StyleIndex.HEADING_2:
                    return ParagraphStyles.HEADING_2;
                case StyleIndex.HEADING_3:
                    return ParagraphStyles.HEADING_3;
                case StyleIndex.HEADING_4:
                    return ParagraphStyles.HEADING_4;
                case StyleIndex.HEADING_5:
                    return ParagraphStyles.HEADING_5;
                case StyleIndex.HEADING_6:
                    return ParagraphStyles.HEADING_6;
                case StyleIndex.HEADING_7:
                    return ParagraphStyles.HEADING_7;
                case StyleIndex.HEADING_8:
                    return ParagraphStyles.HEADING_8;
                case StyleIndex.HEADING_9:
                    return ParagraphStyles.HEADING_9;
                default:
                    return ParagraphStyles.NONE;
            }
        }

        private /*ParagraphStyles*/int mMetParagraphStyles;
    }

    /// <summary>
    /// Searches for SEQ cross-reference bookmarks.
    /// </summary>
    private static class BookmarkNamesFinder extends FieldExtractor
    {
        private BookmarkNamesFinder(BookmarkCache bookmarkCache)
        {
        	super(FieldType.FIELD_SEQUENCE);
	
            mBookmarkNames = new StringToIntDictionary(false);
            mBookmarkCache = bookmarkCache;
        }

        static StringToIntDictionary findBookmarkNames(Document document, BookmarkCache bookmarkCache)
        {
            BookmarkNamesFinder finder = new BookmarkNamesFinder(bookmarkCache);
            finder.extract(document);
            return finder.mBookmarkNames;
        }

        protected /*override*/ void onFieldExtracted() throws Exception
        {
            FieldSeq field = (FieldSeq)getCurrentField();

            if (!field.hasBookmarkName())
                return;

            if (field.isError(mBookmarkCache))
                return;

            mBookmarkNames.set(field.getBookmarkName(), 0);
        }

        private /*final*/ StringToIntDictionary mBookmarkNames;
        private /*final*/ BookmarkCache mBookmarkCache;
    }
}

