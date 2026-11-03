// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import java.util.Comparator;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringOrdinalComparer;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents an item in an index entry/subentry tree.
/// </summary>
/// <remarks>
/// An index entry/subentry tree is represented by a single index entry called a root entry.
/// This is an entry which does not correspond to any XE field entry. Instead, it contains entries of the min
/// level (corresponding to main entry texts of XE fields, for instance to "main" in { XE "main:sub1:sub2" })
/// which in turn contain subentries of the next level (corresponding to the nearest subentry texts of XE fields,
/// for instance to "sub1" in { XE "main:sub1:sub2" }) and so on.
/// </remarks>
class IndexEntry
{
    /// <summary>
    /// A factory method to create a root entry for the whole document.
    /// See <see cref="IndexEntry"/> remarks for any details.
    /// </summary>
    static IndexEntry getRootEntry(FieldCodeIndex fieldCodeIndex, FieldSeqDataProvider fieldSeqDataProvider, DocumentBase document)
    {
        IndexEntry rootEntry = new IndexEntry();
        EntryExtractor extractor = new EntryExtractor(fieldCodeIndex, rootEntry, true, document, fieldSeqDataProvider);
        extractor.extract(document);
        return rootEntry;
    }

    /// <summary>
    /// A factory method to create a root entry for the specified node range of a document.
    /// See <see cref="IndexEntry"/> remarks for any details.
    /// </summary>
    static IndexEntry getRootEntry(FieldCodeIndex fieldCodeIndex, FieldSeqDataProvider fieldSeqDataProvider, NodeRange range) throws Exception
    {
        IndexEntry rootEntry = new IndexEntry();
        EntryExtractor extractor = new EntryExtractor(fieldCodeIndex, rootEntry, true, range, fieldSeqDataProvider);
        extractor.extract(range);
        return rootEntry;
    }

    /// <summary>
    /// Ctor to create a root entry.
    /// </summary>
    private IndexEntry()
    {
    	this(null, null);
	
    }

    private IndexEntry(FieldCodeXE refFieldCode, NodeRange nodeRange)
    {
        mRefFieldCode = refFieldCode;
        mNodeRange = nodeRange;
    }

    /// <summary>
    /// Calculates and adds an <see cref="IndexEntryPageNumberInfo"/> object for the specified XE field.
    /// </summary>
    private void addPageNumberInfo(
        FieldXE field,
        FieldCodeXE fieldCode,
        FieldSeq lastFieldSeq,
        BookmarkSequencesCache bookmarkSequencesCache,
        FieldSeqDataProvider fieldSeqDataProvider)
    {
        // Create on the first demand.
        if (!hasPageNumberInfos())
            mPageNumberInfos = new ArrayList<IndexEntryPageNumberInfo>();

        IndexEntryPageNumberInfo page = buildPageNumberInfo(field, fieldCode, lastFieldSeq, bookmarkSequencesCache, fieldSeqDataProvider);
        msArrayList.add(mPageNumberInfos, page);

        if (fieldCode.hasPageNumberReplacement())
            hasPageNumberReplacements(true);
    }

    private static IndexEntryPageNumberInfo buildPageNumberInfo(
        FieldXE field,
        FieldCodeXE fieldCode,
        FieldSeq lastFieldSeq,
        BookmarkSequencesCache bookmarkSequencesCache,
        FieldSeqDataProvider fieldSeqDataProvider)
    {
        // FOSS: page numbers cannot be calculated without the layout engine. Build the page number info
        // using the neutral page number 0 (see ToaEntryExtractor.GetPageNumber and RefDocTocEntry.GetPageNumber),
        // so that the INDEX field still updates and produces its entries.
        return new IndexEntryPageNumberInfo(
            0,
            IndexEntryPageNumberInfo.INVALID_PAGE_NUMBER,
            lastFieldSeq,
            null,
            fieldCode,
            field,
            fieldSeqDataProvider);
    }

    /// <summary>
    /// Gets a subentry of this entry with the specified text. If not found, creates a new one, stores it
    /// for a further usage and returns it.
    /// </summary>
    private IndexEntry getOrCreateSubentry(String subentryText, FieldCodeXE refFieldCode, NodeRange range)
    {
        IndexEntry subentry = null;
        if (hasSubentries())
        {
            // Get an existing subentry.
            subentry = DictionaryExtension.getValueOrNull(mSubentries, subentryText);
        }
        else
        {
            // Create on the first demand.
            mSubentries = new SortedList<String, IndexEntry>(IndexEntryTextComparer.INSTANCE);
        }

        if (subentry == null)
        {
            // Subentry is not found. Create a new one and remember it.
            subentry = new IndexEntry(refFieldCode, range);
            mSubentries.set(subentryText, subentry);
        }

        return subentry;
    }

    /// <summary>
    /// Gets a subentry text for a subentry with the specified index.
    /// </summary>
    /// <param name="index">A zero-based index. It should be less than <see cref="SubentryCount"/>.</param>
    String getSubentryText(int index)
    {
        return mSubentries.getKeys().get(index);
    }

    /// <summary>
    /// Gets a subentry with the specified index.
    /// </summary>
    /// <param name="index">A zero-based index. It should be less than <see cref="SubentryCount"/>.</param>
    IndexEntry getSubentry(int index)
    {
        return mSubentries.getValues().get(index);
    }

    /// <summary>
    /// Gets the count of subentries contained within this entry.
    /// </summary>
    int getSubentryCount() { return hasSubentries() ? mSubentries.getCount() : 0; }

    /// <summary>
    /// Gets a value indicating whether this entry contains any subentries.
    /// </summary>
    boolean hasSubentries() { return mSubentries != null; }

    /// <summary>
    /// Gets a <see cref="FieldCodeXE"/> object that describes the entry.
    /// </summary>
    /// <remarks>
    /// It corresponds to the first XE field referencing the entry.
    /// </remarks>
    FieldCodeXE getRefFieldCode() { return mRefFieldCode; };

    private FieldCodeXE mRefFieldCode;

    /// <summary>
    /// Gets the entry's node range.
    /// </summary>
    NodeRange getNodeRange() { return mNodeRange; };

    private NodeRange mNodeRange;

    /// <summary>
    /// Gets the collection of <see cref="IndexEntryPageNumberInfo"/> objects describing page numbers
    /// directly referencing the entry.
    /// </summary>
    /// <remarks>
    /// Let's consider { XE "main:sub" } for instance. An entry corresponding to the "sub" part is
    /// directly referenced by this XE field while an entry corresponding to the "main" part is not.
    /// </remarks>
    ArrayList<IndexEntryPageNumberInfo> getPageNumberInfos() { return mPageNumberInfos; }

    /// <summary>
    /// Gets a value indicating whether <see cref="PageNumberInfos"/> collection contains any items.
    /// </summary>
    boolean hasPageNumberInfos() { return mPageNumberInfos != null; }

    /// <summary>
    /// Gets a value indicating whether <see cref="PageNumberInfos"/> collection contains any items
    /// which correspond to page number replacements.
    /// </summary>
    /// <remarks>
    /// See also <see cref="FieldXE.PageNumberReplacement"/>.
    /// </remarks>
    boolean hasPageNumberReplacements() { return mHasPageNumberReplacements; }; private void hasPageNumberReplacements(boolean value) { mHasPageNumberReplacements = value; };

    private boolean mHasPageNumberReplacements;

    private SortedList<String, IndexEntry> mSubentries;
    private ArrayList<IndexEntryPageNumberInfo> mPageNumberInfos;

    /// <summary>
    /// The character separating index subentries of different levels.
    /// </summary>
    static final char SUBENTRY_SEPARATOR = ':';
    /// <summary>
    /// The min subentry level, i.e. the level of a main entry text of XE field.
    /// </summary>
    static final int MIN_SUBENTRY_LEVEL = 1;
    /// <summary>
    /// The max subentry level which is allowed by MS Word.
    /// </summary>
    private static final int MAX_SUBENTRY_LEVEL = 7;

    /// <summary>
    /// Fills a root entry by extracting of XE fields contained within a document or its node range.
    /// </summary>
    private static class EntryExtractor extends FieldExtractor
    {
        EntryExtractor(
            FieldCodeIndex fieldCodeIndex,
            IndexEntry rootEntry,
            boolean includeRefDocFields,
            Node node,
            FieldSeqDataProvider fieldSeqDataProvider)
        {
        	this(
                fieldCodeIndex,
                rootEntry,
                includeRefDocFields,
                BookmarkSequencesCache.build(node, fieldCodeIndex.getSequenceName()),
                fieldSeqDataProvider);
	
        }

        EntryExtractor(
            FieldCodeIndex fieldCodeIndex,
            IndexEntry rootEntry,
            boolean includeRefDocFields,
            NodeRange range,
            FieldSeqDataProvider fieldSeqDataProvider) throws Exception
        {
        	this(
                fieldCodeIndex,
                rootEntry,
                includeRefDocFields,
                BookmarkSequencesCache.build(range, fieldCodeIndex.getSequenceName()),
                fieldSeqDataProvider);
	
        }

        private EntryExtractor(
            FieldCodeIndex fieldCodeIndex,
            IndexEntry rootEntry,
            boolean includeRefDocFields,
            BookmarkSequencesCache bookmarkSequencesCache,
            FieldSeqDataProvider fieldSeqDataProvider)
        {
            mFieldCodeIndex = fieldCodeIndex;
            mRootEntry = rootEntry;
            mBookmarkSequencesCache = bookmarkSequencesCache;
            mIncludeRefDocFields = includeRefDocFields;
            mFieldSeqDataProvider = fieldSeqDataProvider;

            Debug.assertTrue(mFieldCodeIndex.hasSequenceName() == (mFieldSeqDataProvider != null));
        }

        protected /*override*/ void onFieldExtracted() throws Exception
        {
            switch (getCurrentFieldType())
            {
                case FieldType.FIELD_INDEX_ENTRY:
                    processFieldXE((FieldXE)getCurrentField());
                    break;
                case FieldType.FIELD_SEQUENCE:
                    processFieldSequence((FieldSeq)getCurrentField());
                    break;
                case FieldType.FIELD_REF_DOC:
                    processFieldRD((FieldRD)getCurrentField());
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        private void processFieldXE(FieldXE field) throws Exception
        {
            FieldCodeXE fieldCodeXE = new FieldCodeXE(field);
            if (isFieldXEMatch(field, fieldCodeXE))
                processFieldXE(field, fieldCodeXE);
        }

        private void processFieldSequence(FieldSeq field)
        {
            if (!mFieldCodeIndex.hasSequenceName())
                return;

            if (!msString.equals(mFieldCodeIndex.getSequenceName(), field.getSequenceIdentifier()))
                return;

            mLastFieldSeqFound = field;
        }

        /// <summary>
        /// Returns a value indicating whether the specified XE field instance satisfies conditions of the INDEX
        /// field in the context of which the extraction is performed.
        /// </summary>
        private boolean isFieldXEMatch(FieldXE fieldXE, FieldCodeXE fieldCode)
        {
            if (mFieldCodeIndex.getEntryType() != fieldCode.getEntryTypeCore())
                return false;

            if (!fieldXE.shouldBeProcessed(fieldCode))
                return false;

            if (mFieldCodeIndex.hasLetterRange())
            {
                // If the entry is accessed then it has a non-whitespace text.
                int firstLetterIndex = StringUtil.indexOfNonWhitespace(fieldCode.getText());
                char firstLetter = msChar.toUpperInvariant(fieldCode.getText().charAt(firstLetterIndex));

                if (firstLetter < mFieldCodeIndex.getLetterRange().charAt(0))
                    return false;

                if (firstLetter > mFieldCodeIndex.getLetterRange().charAt(1))
                    return false;
            }

            return true;
        }

        /// <summary>
        /// Extracts index entries and subentries from the given XE field entry.
        /// </summary>
        /// <remarks>
        /// It is assumed that the method is called only if the specified XE field entry satisfies conditions of the INDEX
        /// field in the context of which the extraction is performed.
        /// </remarks>
        private void processFieldXE(FieldXE field, FieldCodeXE fieldCode) throws Exception
        {
            mCurrentSubentry = mRootEntry;
            mCurrentSubentryLevel = MIN_SUBENTRY_LEVEL;

            if (fieldCode.getText().indexOf(SUBENTRY_SEPARATOR) == -1)
            {
                // Use a simplified approach when there is no subentry separators for sure.
                moveToSubentry(fieldCode.getText(), fieldCode, fieldCode.getTextRange());
            }
            else
            {
                // Use a full evaluation process when there are suspicious characters
                // which may (or may not) split an entry to subentries.
                SubentryExtractHelper helper = new SubentryExtractHelper(this, fieldCode);
                NodeModifierApplier applier = new NodeModifierApplier(fieldCode.getTextRange(), helper);
                try /*JAVA: was using*/
            	{
                    applier.applyModifier();
            	}
                finally { if (applier != null) applier.dispose(); }
                helper.finalizeExtract();
            }

            // Add a page number info to the directly referenced subentry. There is no sense to do that for a root entry.
            if (mCurrentSubentry != mRootEntry)
                mCurrentSubentry.addPageNumberInfo(field, fieldCode, mLastFieldSeqFound, mBookmarkSequencesCache, mFieldSeqDataProvider);
        }

        /// <summary>
        /// Advances from the current subentry to a subentry of the next level with the specified subentry text.
        /// Creates the subentry if it does not exist.
        /// </summary>
        private void moveToSubentry(String subentryText, FieldCodeXE refFieldCode, NodeRange range)
        {
            // MS Word does not process subentries with level more than MaxSubentryLevel.
            if (mCurrentSubentryLevel > MAX_SUBENTRY_LEVEL)
                return;

            mCurrentSubentry = mCurrentSubentry.getOrCreateSubentry(subentryText, refFieldCode, range);
            mCurrentSubentryLevel++;
        }

        private void processFieldRD(FieldRD field) throws Exception
        {
            if (!mIncludeRefDocFields)
                return;

            Document referenceDocument = FieldIndexAndTablesUtil.openRefDocument(field);
            if (referenceDocument == null)
                return;

            FieldSeqDataProvider fieldSeqDataProvider = mFieldCodeIndex.hasSequenceName()
                ? new FieldSeqDataProvider(referenceDocument)
                : null;
            EntryExtractor extractor = new EntryExtractor(mFieldCodeIndex, mRootEntry, false, referenceDocument, fieldSeqDataProvider);
            extractor.extract(referenceDocument);
        }

        private /*final*/ FieldCodeIndex mFieldCodeIndex;
        private /*final*/ IndexEntry mRootEntry;
        private /*final*/ BookmarkSequencesCache mBookmarkSequencesCache;
        private IndexEntry mCurrentSubentry;
        private int mCurrentSubentryLevel;
        private FieldSeq mLastFieldSeqFound;
        private /*final*/ boolean mIncludeRefDocFields;
        private /*final*/ FieldSeqDataProvider mFieldSeqDataProvider;

        /// <summary>
        /// Helps to extract entries and subentries from a XE field text argument.
        /// </summary>
        private static class SubentryExtractHelper extends FieldXETextArgumentDecoderNodeModifier
        {
            /// <summary>
            /// Ctor.
            /// </summary>
            SubentryExtractHelper(EntryExtractor entryExtractor, FieldCodeXE fieldCode)
            {
            	super(fieldCode.getTextRange());
	
                mEntryExtractor = entryExtractor;
                mFieldCode = fieldCode;
            }

            protected /*override*/ boolean prepareModify(Node referenceNode, Node nodeToModify)
            {
                boolean result = super.prepareModify(referenceNode, nodeToModify);

                if (result && !isInFieldCode() && (referenceNode.getNodeType() == NodeType.RUN))
                {
                    // Remember the reference run to be processed.
                    mCurrentRun = referenceNode;
                }

                return result;
            }

            protected /*override*/ StringBuilder processDecodedTokenChar(StringBuilder builder, char c, int positionInTokenPart)
            {
                // Do not process a nested field's code.
                if (isInFieldCode())
                {
                    // Simply return null not to initialize any extra text buffers.
                    return null;
                }

                if ((c == SUBENTRY_SEPARATOR) && !isTokenCharEscaped())
                {
                    // Process a subentry separator.
                    DocumentPosition subentryStartPosition = getSubentryStartPosition();

                    // Calculate the offset within the current reference run. Note, that positionInToken relates
                    // to the current run to modify which can be cut comparing to the current reference run.
                    int offset = positionInTokenPart;
                    if (mCurrentRun == mFieldCode.getTextRange().getStart().getNode())
                        offset += mFieldCode.getTextRange().getStart().getOffset();

                    // Note, that it is normal for DocumentPosition.Offset to be more than DocumentPosition.Length.
                    mNextSubentryStartPosition = new DocumentPosition(mCurrentRun, offset + 1);

                    // An end position should point to the next character (i.e. subentry separator).
                    DocumentPosition subentryEndPosition = new DocumentPosition(mCurrentRun, offset);

                    extractSubentry(subentryStartPosition, subentryEndPosition);
                }
                else
                {
                    // Append the current character to the current subentry text buffer.
                    mSubentryTextBuilder.append(c);
                }

                // Simply return null not to initialize any extra text buffers.
                return null;
            }

            protected /*override*/ RichStringBuilder processDecodedTokenChar(RichStringBuilder builder, RichChar c, int positionInTokenPart)
            {
                Debug.fail("Rich strings are not supported");

                return super.processDecodedTokenChar(builder, c, positionInTokenPart);
            }

            /// <summary>
            /// Returns a document position at which the current subentry starts.
            /// </summary>
            private DocumentPosition getSubentryStartPosition()
            {
                return (mNextSubentryStartPosition != null)
                    ? mNextSubentryStartPosition
                    : mFieldCode.getTextRange().getStart().deepClone();
            }

            /// <summary>
            /// Extracts the current subentry.
            /// </summary>
            private void extractSubentry(DocumentPosition subentryStartPosition, DocumentPosition subentryEndPosition)
            {
                // Flush the current subentry text.
                String subentryText = mSubentryTextBuilder.toString();
                mSubentryTextBuilder.setLength(0);

                // Create a subentry node range.
                NodeRange subentryRange = new NodeRange(subentryStartPosition, subentryEndPosition);

                mEntryExtractor.moveToSubentry(subentryText, mFieldCode, subentryRange);
            }

            /// <summary>
            /// Extracts the last subentry.
            /// </summary>
            void finalizeExtract()
            {
                DocumentPosition subentryStartPosition = getSubentryStartPosition();
                DocumentPosition subentryEndPosition = mFieldCode.getTextRange().getEnd().deepClone();

                extractSubentry(subentryStartPosition, subentryEndPosition);
            }

            private /*final*/ EntryExtractor mEntryExtractor;
            private /*final*/ FieldCodeXE mFieldCode;
            private /*final*/ StringBuilder mSubentryTextBuilder = new StringBuilder();
            private Node mCurrentRun;
            private DocumentPosition mNextSubentryStartPosition;
        }
    }

    private static class IndexEntryTextComparer implements Comparator<String>
    {
        private IndexEntryTextComparer()
        {
        }

        public int compare(String x, String y)
        {
            if (msObject.referenceEquals(x, y))
                return 0;

            int compareResult = msString.compare(x, y, StringComparison.CURRENT_CULTURE);
            if (compareResult != 0)
                return compareResult;

            return StringOrdinalComparer.DEFAULT.compare(x, y);
        }

        static /*final*/ IndexEntryTextComparer INSTANCE = new IndexEntryTextComparer();
    }

    private static class BookmarkSequencesCache extends FieldExtractor
    {
        private BookmarkSequencesCache(String sequenceName)
        {
            mSequenceName = sequenceName;
        }

        static BookmarkSequencesCache build(Node node, String sequenceName)
        {
            if (sequenceName == null)
                return null;

            BookmarkSequencesCache cache = new BookmarkSequencesCache(sequenceName);
            cache.extract(node);
            return cache;
        }

        static BookmarkSequencesCache build(NodeRange range, String sequenceName) throws Exception
        {
            if (sequenceName == null)
                return null;

            BookmarkSequencesCache cache = new BookmarkSequencesCache(sequenceName);
            cache.extract(range);
            return cache;
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart) throws Exception
        {
            mStartCache.set(bookmarkStart.getName(), mLastFieldSeqFound);
            return super.visitBookmarkStart(bookmarkStart);
        }

        public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd) throws Exception
        {
            mEndCache.set(bookmarkEnd.getName(), mLastFieldSeqFound);
            return super.visitBookmarkEnd(bookmarkEnd);
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

        FieldSeq getCachedStartSequence(String bookmarkName)
        {
            return mStartCache.get(bookmarkName);
        }

        FieldSeq getCachedEndSequence(String bookmarkName)
        {
            return mEndCache.get(bookmarkName);
        }

        private /*final*/ StringToObjDictionary<FieldSeq> mStartCache = new StringToObjDictionary<FieldSeq>(false);
        private /*final*/ StringToObjDictionary<FieldSeq> mEndCache = new StringToObjDictionary<FieldSeq>(false);
        private /*final*/ String mSequenceName;

        private FieldSeq mLastFieldSeqFound;
    }
}

