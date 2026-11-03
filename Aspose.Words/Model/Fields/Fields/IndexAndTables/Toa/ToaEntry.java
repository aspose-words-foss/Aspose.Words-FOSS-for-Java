// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;


class ToaEntry
{
    ToaEntry(NodeRange range)
    {
        mRange = range;
        mRangeDocument = range.getDocument();
    }

    NodeRange getRange() { return mRange; };

    private NodeRange mRange;

    ArrayList<Run> buildPagesRuns(DocumentBase document, boolean usePassim, String pageNumberListSeparator, String sequenceSeparator, String pageRangeSeparator)
    {
        if (usePassim && isPassim())
            return buildPassim(document);

        PagesRunsBuilder builder = new PagesRunsBuilder(document);
        buildPagesRuns(builder, mPages, pageNumberListSeparator, pageRangeSeparator, sequenceSeparator);
        return builder.getRuns();
    }

    private boolean isPassim()
    {
        final int LIMIT = 5;
        if (mPages.size() < LIMIT)
            return false;

        ToaEntryPage last = mPages.get(0).getPage();
        int duplicates = 0;
        for (int i = 1; i < mPages.size(); i++)
        {
            ToaEntryPage current = mPages.get(i).getPage();

            if (current.equals(last) || areDuplicates(current, last) || areDuplicates(last, current))
                duplicates++;

            last = current;
        }

        return mPages.size() - duplicates >= LIMIT;
    }

    private static boolean areDuplicates(ToaEntryPage x, ToaEntryPage y)
    {
        ToaEntrySinglePage singlePage = ms.as(x, ToaEntrySinglePage.class);
        ToaEntryPageRangeError pageRangeError = ms.as(y, ToaEntryPageRangeError.class);

        if (singlePage == null || pageRangeError == null)
            return false;

        return singlePage.getNumber() == pageRangeError.getNumber();
    }

    private static ArrayList<Run> buildPassim(DocumentBase document)
    {
        ArrayList<Run> result = new ArrayList<Run>();
        msArrayList.add(result, new Run(document, "passim"));
        return result;
    }

    private static void buildPagesRuns(
        PagesRunsBuilder builder,
        ArrayList<ToaEntry.IPageContainer> pages,
        String pageNumberListSeparator,
        String pageRangeSeparator,
        String sequenceSeparator)
    {
        for (int i = 0; i < pages.size(); i++)
        {
            IPageContainer container = pages.get(i);
            SequencedPage sequencedPage = ms.as(container, SequencedPage.class);
            ToaEntryPage page = container.getPage();

            if (i != 0)
                builder.add(pageNumberListSeparator);

            ToaEntrySinglePage singlePage = ms.as(page, ToaEntrySinglePage.class);
            if (singlePage != null)
            {
                addPageNumber(builder, sequencedPage, sequenceSeparator, singlePage.getNumber(), singlePage.isBold(), singlePage.isItalic());
                continue;
            }

            ToaEntryPageRange pageRange = ms.as(page, ToaEntryPageRange.class);
            if (pageRange != null)
            {
                addPageNumber(builder, sequencedPage, sequenceSeparator, pageRange.getStart(), pageRange.isStartBold(), pageRange.isStartItalic());
                builder.add(pageRangeSeparator);
                addPageNumber(builder, sequencedPage, sequenceSeparator, pageRange.getEnd(), pageRange.isEndBold(), pageRange.isEndItalic());
                continue;
            }

            ToaEntryPageRangeError pageRangeError = ms.as(page, ToaEntryPageRangeError.class);
            if (pageRangeError != null)
            {
                builder.add("Error! Not a valid bookmark in entry on page ", G_BOLD);
                addPageNumber(builder, sequencedPage, sequenceSeparator, pageRangeError.getNumber(), pageRangeError.isBold(), pageRangeError.isItalic());
                continue;
            }

            Debug.fail("Unexpected ToaEntryPageNumber implementation");
        }
    }

    private static void addPageNumber(
        PagesRunsBuilder builder,
        SequencedPage sequencedPage,
        String sequenceSeparator,
        int page,
        boolean isBold,
        boolean isItalic)
    {
        String text = buildSequencedPageNumberString(sequencedPage, sequenceSeparator, page);
        builder.add(text, isBold, isItalic);
    }

    private static String buildSequencedPageNumberString(SequencedPage sequencedPage, String sequenceSeparator, int page)
    {
        if (sequencedPage == null)
            return Integer.toString(page);

        return sequencedPage.getSequenceNumber() + sequenceSeparator + page;
    }

    void addSinglePage(boolean isBold, boolean isItalic, int pageNumber, int sequenceNumber)
    {
        addPage(new ToaEntrySinglePage(isBold, isItalic, pageNumber), sequenceNumber);
    }

    void addPageRange(boolean isBold, boolean isItalic, int start, int end, int sequenceNumber)
    {
        addPage(new ToaEntryPageRange(isBold, isItalic, start, end), sequenceNumber);
    }

    void addPageRangeError(boolean isBold, boolean isItalic, int pageNumber, int sequenceNumber)
    {
        addPage(new ToaEntryPageRangeError(isBold, isItalic, pageNumber), sequenceNumber);
    }

    private void addPage(ToaEntryPage page, int sequenceNumber)
    {
        if (sequenceNumber == NULL_SEQUENCE_NUMBER)
            append(page);
        else
            append(new SequencedPage(sequenceNumber, page));
    }

    void copyFrom(ToaEntry entry)
    {
        for (IPageContainer page : entry.mPages)
            append(page);
    }

    private void append(IPageContainer item)
    {
        IPageContainer last = mPages.size() == 0
            ? null
            : mPages.get(mPages.size() - 1);

        if (last != null)
        {
            if (last.equals(item))
                return;

            if (replaceSinglePageWithPageRange(last, item))
                return;
        }

        msArrayList.add(mPages, item);
    }

    private boolean replaceSinglePageWithPageRange(IPageContainer last, IPageContainer item)
    {
        ToaEntrySinglePage lastPage = ms.as(last.getPage(), ToaEntrySinglePage.class);
        if (lastPage == null)
            return false;

        ToaEntryPageRange itemPage = ms.as(item.getPage(), ToaEntryPageRange.class);
        if (itemPage == null)
            return false;

        if (lastPage.getNumber() != itemPage.getStart())
            return false;

        SequencedPage lastSeq = ms.as(last, SequencedPage.class);
        SequencedPage itemSeq = ms.as(item, SequencedPage.class);

        Debug.assertTrue((lastSeq == null) == (itemSeq == null));

        if ((lastSeq != null) && (itemSeq != null) && (lastSeq.getSequenceNumber() != itemSeq.getSequenceNumber()))
            return false;

        itemPage.isStartBold(lastPage.isBold());
        itemPage.isStartItalic(lastPage.isItalic());
        mPages.set(mPages.size() - 1, item);

        return true;
    }

    protected static RunPr getFormatting(boolean isBold, boolean isItalic)
    {
        if (isBold && isItalic)
            return G_BOLD_ITALIC;
        if (isBold)
            return G_BOLD;
        if (isItalic)
            return G_ITALIC;
        return null;
    }

    private /*final*/ ArrayList<ToaEntry.IPageContainer> mPages = new ArrayList<ToaEntry.IPageContainer>();
    // ReSharper disable once NotAccessedField.Local - Avoid C++ leaks
    private /*final*/ DocumentBase mRangeDocument;

    static final int NULL_SEQUENCE_NUMBER = Integer.MIN_VALUE;

    private static /*final*/ RunPr G_BOLD = G_BOLDInitializer();
    /*JAVA-added private static Initializer*/
    private static RunPr G_BOLDInitializer()
    {
        RunPr G_BOLD = new RunPr(); { G_BOLD.setBold(AttrBoolEx.TRUE); }
        return G_BOLD;
    }
    private static /*final*/ RunPr G_ITALIC = G_ITALICInitializer();
    /*JAVA-added private static Initializer*/
    private static RunPr G_ITALICInitializer()
    {
        RunPr G_ITALIC = new RunPr(); { G_ITALIC.setItalic(AttrBoolEx.TRUE); }
        return G_ITALIC;
    }
    private static /*final*/ RunPr G_BOLD_ITALIC = G_BOLD_ITALICInitializer();
    /*JAVA-added private static Initializer*/
    private static RunPr G_BOLD_ITALICInitializer()
    {
        RunPr G_BOLD_ITALIC = new RunPr(); { G_BOLD_ITALIC.setBold(AttrBoolEx.TRUE); G_BOLD_ITALIC.setItalic(AttrBoolEx.TRUE); }
        return G_BOLD_ITALIC;
    }

    private interface IPageContainer
    {
        public ToaEntryPage getPage();
    }

    private abstract static class ToaEntryPage implements IPageContainer
    {
        public ToaEntryPage /*IPageContainer.*/getPage() { return this; }
    }

    private static class ToaEntrySinglePage extends ToaEntryPage
    {
        ToaEntrySinglePage(boolean isBold, boolean isItalic, int number)
        {
            mIsBold = isBold;
            mIsItalic = isItalic;
            mNumber = number;
        }

        int getNumber() { return mNumber; };

        private int mNumber;

        boolean isBold() { return mIsBold; };

        private boolean mIsBold;

        boolean isItalic() { return mIsItalic; };

        private boolean mIsItalic;

        public /*override*/ int hashCode()
        {
            return getNumber();
        }

        public /*override*/ boolean equals(Object obj)
        {
            if (msObject.referenceEquals(null, obj))
                return false;
            if (msObject.referenceEquals(this, obj))
                return true;
            if (obj.getClass() != getClass())
                return false;
            return equals((ToaEntrySinglePage)obj);
        }

        private boolean equals(ToaEntrySinglePage other)
        {
            return getNumber() == other.getNumber();
        }

    }

    private static class ToaEntryPageRange extends ToaEntryPage
    {
        ToaEntryPageRange(boolean isBold, boolean isItalic, int start, int end)
        {
            isStartBold(isBold);
            isStartItalic(isItalic);
            mIsEndBold = isBold;
            mIsEndItalic = isItalic;
            mStart = start;
            mEnd = end;
        }

        int getStart() { return mStart; };

        private int mStart;

        int getEnd() { return mEnd; };

        private int mEnd;

        boolean isStartBold() { return mIsStartBold; }; void isStartBold(boolean value) { mIsStartBold = value; };

        private boolean mIsStartBold;

        boolean isStartItalic() { return mIsStartItalic; }; void isStartItalic(boolean value) { mIsStartItalic = value; };

        private boolean mIsStartItalic;

        boolean isEndBold() { return mIsEndBold; };

        private boolean mIsEndBold;

        boolean isEndItalic() { return mIsEndItalic; };

        private boolean mIsEndItalic;

    }

    private static class ToaEntryPageRangeError extends ToaEntryPage
    {
        ToaEntryPageRangeError(boolean isBold, boolean isItalic, int number)
        {
            mIsBold = isBold;
            mIsItalic = isItalic;
            mNumber = number;
        }

        int getNumber() { return mNumber; };

        private int mNumber;

        boolean isBold() { return mIsBold; };

        private boolean mIsBold;

        boolean isItalic() { return mIsItalic; };

        private boolean mIsItalic;

        public /*override*/ int hashCode()
        {
            return getNumber();
        }

        public /*override*/ boolean equals(Object obj)
        {
            if (msObject.referenceEquals(null, obj))
                return false;
            if (msObject.referenceEquals(this, obj))
                return true;
            if (obj.getClass() != getClass())
                return false;
            return equals((ToaEntryPageRangeError)obj);
        }

        private boolean equals(ToaEntryPageRangeError other)
        {
            return getNumber() == other.getNumber();
        }

    }

    private static class SequencedPage implements IPageContainer
    {
        SequencedPage(int sequenceNumber, ToaEntryPage number)
        {
            mSequenceNumber = sequenceNumber;
            mNumber = number;
        }

        int getSequenceNumber() { return mSequenceNumber; };

        private int mSequenceNumber;

        public ToaEntryPage /*IPageContainer.*/getPage() { return mNumber; }

        public /*override*/ int hashCode()
        {
            /*unchecked*/
            {
                return (getSequenceNumber() * 397) ^ mNumber.hashCode();
            }
        }

        public /*override*/ boolean equals(Object obj)
        {
            if (msObject.referenceEquals(null, obj))
                return false;
            if (msObject.referenceEquals(this, obj))
                return true;
            if (obj.getClass() != SequencedPage.class)
                return false;
            return equals((SequencedPage)obj);
        }

        private boolean equals(SequencedPage other)
        {
            return getSequenceNumber() == other.getSequenceNumber() && msObject.equals(mNumber, other.mNumber);
        }


        private /*final*/ ToaEntryPage mNumber;
    }

    private static class PagesRunsBuilder
    {
        PagesRunsBuilder(DocumentBase document)
        {
            mDocument = document;
        }

        void add(String text)
        {
            add(text, null);
        }

        void add(String text, boolean isBold, boolean isItalic)
        {
            add(text, getFormatting(isBold, isItalic));
        }

        void add(String text, RunPr runPr)
        {
            if (runPr == null)
                runPr = new RunPr();

            if (mRuns.size() > 0)
            {
                Run lastRun = mRuns.get(mRuns.size() - 1);
                // ReSharper disable once RedundantCast - Casting for C++ to avoid C2668 error.
                if (lastRun.getRunPr().equals((AttrCollection)runPr))
                {
                    lastRun.setText(lastRun.getText() + text);
                    return;
                }
            }

            msArrayList.add(mRuns, new Run(mDocument, text, runPr));
        }

        ArrayList<Run> getRuns() { return mRuns; }

        private /*final*/ DocumentBase mDocument;
        private /*final*/ ArrayList<Run> mRuns = new ArrayList<Run>();
    }

}

