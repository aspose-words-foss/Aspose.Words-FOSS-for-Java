// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.BidiLevelList;
import com.aspose.StringUtil;
import com.aspose.BidiSourceRun;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.BidiLevel;
import com.aspose.BidiRun;
import com.aspose.BidiAlgorithm;
import com.aspose.Language;
import com.aspose.BidiCharacterType;
import java.util.ArrayList;
import com.aspose.UnicodeCharacterDataResolver;


/// <summary>
/// Rearranges text of adjacent inline HTML elements using the Unicode bidirectional algorithm.
/// </summary>
/// <remarks>
/// In HTML, text of inline elements inside a box is a subject to reordering with the bidirectional algorithm.
/// This class is used to accumulate text of adjacent inline elements, reorder it and write runs of reordered text
/// with corresponding font properties into document builder.
/// </remarks>
class HtmlBidiTextArranger
{
    HtmlBidiTextArranger(
        DocumentBuilder builder,
        boolean textIsVisuallyOrdered,
        boolean joinRuns)
    {
        Debug.assertTrue(builder != null);
        mBuilder = builder;
        mTextIsVisuallyOrdered = textIsVisuallyOrdered;
        mJoinRuns = joinRuns;
    }

    /// <summary>
    /// Appends a fragment of text. Each fragment has its own font properties and bidi levels.
    /// </summary>
    void append(
        String text,
        RunPr runPr,
        BidiLevelList activeBidiLevels,
        boolean dontCollapseLastSpace,
        Bookmark bookmark)
    {
        Debug.assertTrue(StringUtil.hasChars(text));

        if ((mParagraph != null) && (mParagraph != mBuilder.getCurrentParagraph()))
        {
            // We changed the paragraph and forgot to write pending text first. It's a severe logical error,
            // and we should never get here. The pending text will be lost.
            Debug.assertTrue(false);
            mRuns.clear();
        }

        // Try to join the new run with the last run in the buffer, if any exists and if formatting of the two runs
        // is equal. Note that the calling code may disable joining of runs.
        boolean joined = false;
        if (mJoinRuns && (mRuns.size() > 0))
        {
            PendingRun lastRun = mRuns.get(mRuns.size() - 1);
            if (!lastRun.isDirectionMark() &&
                lastRun.getRunPr().equals(runPr) &&
                lastRun.getRun().getLevels().equals(activeBidiLevels) &&
                (lastRun.getBookmark() == bookmark))
            {
                mRuns.set(mRuns.size() - 1, new PendingRun(
                    new BidiSourceRun(lastRun.getRun().getText() + text, activeBidiLevels),
                    runPr,
                    bookmark));
                joined = true;
            }
        }

        if (!joined)
        {
            msArrayList.add(mRuns, new PendingRun(new BidiSourceRun(text, activeBidiLevels), runPr, bookmark));
        }

        mDontCollapseWhitespaceAfterText = dontCollapseLastSpace;
        mParagraph = mBuilder.getCurrentParagraph();
    }

    /// <summary>
    /// Appends a proper direction mark (LRM or RLM) according to the bidi levels.
    /// </summary>
    /// <remarks>
    /// Appended marks are used only to calculate bidi embedding levels and aren't written to the rearranged text.
    /// That is why we don't have to specify run properties for marks.
    /// </remarks>
    void appendDirectionMark(BidiLevelList activeBidiLevels)
    {
        Debug.assertTrue(activeBidiLevels.getLength() > 0);
        if (activeBidiLevels.getLength() <= 0)
        {
            return;
        }

        String mark = (activeBidiLevels.get(activeBidiLevels.getLength() - 1) == BidiLevel.EMBED_LTR)
            ? LEFT_TO_RIGHT_MARK
            : RIGHT_TO_LEFT_MARK;
        append(mark, null, activeBidiLevels, false, null);
    }

    /// <summary>
    /// Rearranges text accumulated since the last write and writes it to the document builder.
    /// </summary>
    void rearrangeAndWriteText() throws Exception
    {
        // If there is no pending text, just return.
        if (isEmpty())
        {
            Debug.assertTrue(mParagraph == null);
            return;
        }

        // The pending text must belong to the current paragraph.
        if (mParagraph == mBuilder.getCurrentParagraph())
        {
            // Copy source runs to array.
            BidiSourceRun[] sourceRuns = new BidiSourceRun[mRuns.size()];
            for (int i = 0; i < mRuns.size(); i++)
            {
                PendingRun textRun = mRuns.get(i);
                sourceRuns[i] = textRun.getRun();
            }

            // Reorder source runs using the unicode bidirectional algorithm.
            BidiRun[] bidiRuns = BidiAlgorithm.apply(
                sourceRuns,
                mBuilder.getParagraphFormat().getBidi(),
                mTextIsVisuallyOrdered,
                true);

            // WORDSNET-13311 All text after STRONG tag contained in PRE tag becomes bold in output documents.
            RunPr oldRunPr = mBuilder.getRunPrCopy();
            // Write reordered runs with corresponding font properties.
            for (BidiRun bidiRun : bidiRuns)
            {
                PendingRun sourceTextRun = mRuns.get(bidiRun.getSourceRunIndex());

                // Mark runs have no textual representation in the source document. They are for internal use only
                // and their content shouldn't be written to the result.
                if (sourceTextRun.isDirectionMark())
                {
                    continue;
                }

                mBuilder.setFont(sourceTextRun.getRunPr(), true);
                if (mBuilder.getFont().getBidi() != bidiRun.getRtl())
                {
                    mBuilder.getFont().setBidi(bidiRun.getRtl());
                }

                // WORDSNET-16568 Start a bookmark for inline HTML element with "id" attribute.
                if (sourceTextRun.getBookmark() != null)
                    mBuilder.insertNode(sourceTextRun.getBookmark().getBookmarkStart());

                // WORDSNET-18872 Split bidi text that contains intermixed HebrewIsrael and ArabicSaudiArabia parts. 
                String[] textSplitByLocaleId = mBuilder.getFont().getBidi()
                    ? splitBidiTextByLocaleId(bidiRun.getText())
                    : new String[] { bidiRun.getText() };

                writeSplitText(textSplitByLocaleId, bidiRun);

                // WORDSNET-16568 End a bookmark for inline HTML element with "id" attribute.
                if (sourceTextRun.getBookmark() != null)
                {
                    mBuilder.insertNode(sourceTextRun.getBookmark().getBookmarkEnd());
                }
            }
            mBuilder.setFont(oldRunPr, false);
        }
        else
        {
            // The pending text does not belong to the current paragraph. We changed the paragraph 
            // and forgot to write pending text first. It is a severe logical error, and we should never get here.
            // The pending text will be lost.
            Debug.assertTrue(false);
        }

        mRuns.clear();
        mParagraph = null;
    }

    private void writeSplitText(String[] textSplitByLocaleId, BidiRun bidiRun)
    {
        int originalBidiLocaleId = mBuilder.getFont().getLocaleIdBi();
        for (String textPart : textSplitByLocaleId)
        {
            // WORDSNET-13787 We have to set bidi locale to 'he-IL' on RTL runs containing numbers with any of
            // the following number separator characters: '+', '-', and '/'. Otherwise, these separators will be treated
            // as neutral characters in MS Word and parts of the number will be reordered.
            if (bidiRun.getRequiresHebrewLocaleBi())
            {
                setLocaleIdBi(mBuilder.getFont(), (int)Language.HEBREW_ISRAEL);
            }
            else
            {
                /*Language*/int autoDetectedBidiLocaleId = mBuilder.getFont().getBidi()
                    ? detectBidiLocaleId(textPart)
                    : Language.LANGUAGE_NOT_SET;

                // WORDSNET-18872 Always auto-detect and set bidi locale ID of bidi text.
                if (autoDetectedBidiLocaleId != Language.LANGUAGE_NOT_SET)
                {
                    setLocaleIdBi(mBuilder.getFont(), (int)autoDetectedBidiLocaleId);
                }
                // Don't change bidi locale ID of other text.
                else
                {
                    setLocaleIdBi(mBuilder.getFont(), originalBidiLocaleId);
                }
            }

            Run newRunNode = new Run(mBuilder.getDocument(), textPart, mBuilder.getRunPrCopy());
            mBuilder.insertNode(newRunNode);
        }
    }

    private static void setLocaleIdBi(Font font, int localeId)
    {
        if (font.getLocaleIdBi() != localeId)
            font.setLocaleIdBi(localeId);
    }

    boolean isEmpty() { return mRuns.size() == 0; }

    /// <summary>
    /// Gets a value indicating whether leading whitespace must be trimmed after the text that has been already
    /// written into this buffer.
    /// </summary>
    boolean collapseWhitespaceAfterText()
    {
        if (mDontCollapseWhitespaceAfterText)
        {
            return false;
        }

        for (int runIndex = mRuns.size() - 1; runIndex >= 0; runIndex--)
        {
            PendingRun run = mRuns.get(runIndex);

            // Mark runs are created from HTML markup, not from text, and so they shouldn't affect methods that
            // work with the text. That's why we skip mark runs here.
            if (run.isDirectionMark())
            {
                continue;
            }

            String text = run.getRun().getText();
            if (text.length() == 0)
            {
                continue;
            }

            char lastChar = text.charAt(text.length() - 1);
            return (lastChar == ' ') ||
                (lastChar == ControlChar.LINE_BREAK_CHAR) ||
                (lastChar == ControlChar.PAGE_BREAK_CHAR) ||
                (lastChar == ControlChar.COLUMN_BREAK_CHAR);
        }

        return false;
    }

    /// <summary>
    /// Detects 
    /// </summary>
    private static /*Language*/int detectBidiLocaleId(String bidiText)
    {
        if (bidiText.length() > 0)
        {
            char firstChar = bidiText.charAt(0);
            /*BidiCharacterType*/int bidiType = getBidiCharacterType(firstChar);
            if (bidiType == BidiCharacterType.R)
            {
                return Language.HEBREW_ISRAEL;
            }
            // In some cases Arabic characters may be mixed with Nonspacing Mark.
            // Such strings should have ArabicSaudiArabia language.
            for (int i = 0; i < bidiText.length(); i++)
            {
            	char character = bidiText.charAt(i);
                bidiType = getBidiCharacterType(character);
                if (bidiType == BidiCharacterType.AL)
                {
                    return Language.ARABIC_SAUDI_ARABIA;
                }
            }
        }
        return Language.LANGUAGE_NOT_SET;
    }

    /// <summary>
    /// Splits bidi (RTL) text into parts having same bidi locale ID (HebrewIsrael or ArabicSaudiArabia).
    /// </summary>
    private static String[] splitBidiTextByLocaleId(String bidiText)
    {
        boolean isMixedLocaleBidiText = hasBothArabicAndHebrewCharacters(bidiText);
        if (!isMixedLocaleBidiText)
        {
            return new String[] { bidiText };
        }

        ArrayList<String> splitBidiText = new ArrayList<String>();

        /*BidiCharacterType*/int currentRangeCharType = getBidiCharacterType(bidiText.charAt(0));
        StringBuilder rangeText = new StringBuilder(bidiText.length());

        // It seems Word separates Hebrew to one range and other character types to another range.
        // For example: the following sequence "עבריתالسَّلَامُ" contains 
        // HebrewIsrael, ArabicSaudiArabia and Nonspacing Mark character types.
        for (int i = 0; i < bidiText.length(); i++)
        {
        	char c = bidiText.charAt(i);
            /*BidiCharacterType*/int currentCharType = getBidiCharacterType(c);
            if ((currentRangeCharType != currentCharType) &&
                ((currentRangeCharType == BidiCharacterType.R) || (currentCharType == BidiCharacterType.R)))
            {
                msArrayList.add(splitBidiText, rangeText.toString());
                rangeText.setLength(0);
                currentRangeCharType = currentCharType;
            }
            rangeText.append(c);
        }
        if (rangeText.length() > 0)
        {
            msArrayList.add(splitBidiText, rangeText.toString());
        }

        return msArrayList.toArray(splitBidiText, new String[0]);
    }

    /// <summary>
    /// Determines whether the specified text contains both HebrewIsrael and ArabicSaudiArabia content at the same time.
    /// </summary>
    private static boolean hasBothArabicAndHebrewCharacters(String text)
    {
        boolean hasRtlHebrew = false;
        boolean hasRtlArabic = false;
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            /*BidiCharacterType*/int charType = getBidiCharacterType(c);
            if (charType == BidiCharacterType.R)
            {
                hasRtlHebrew = true;
            }
            else if (charType == BidiCharacterType.AL)
            {
                hasRtlArabic = true;
            }
            if (hasRtlHebrew && hasRtlArabic)
            {
                return true;
            }
        }
        return false;
    }

    private static /*BidiCharacterType*/int getBidiCharacterType(char c)
    {
        return UnicodeCharacterDataResolver.getBidiCharacterType(c);
    }

    /// <summary>
    /// Represents a run of text with corresponding font properties.
    /// </summary>
    private static class PendingRun
    {
        PendingRun(BidiSourceRun run, RunPr runPr, Bookmark bookmark)
        {
            mRun = run;
            mRunPr = runPr;
            mBookmark = bookmark;
        }

        BidiSourceRun getRun() { return mRun; };

        private BidiSourceRun mRun;

        RunPr getRunPr() { return mRunPr; };

        private RunPr mRunPr;

        boolean isDirectionMark() { return (LEFT_TO_RIGHT_MARK.equals(getRun().getText())) || (RIGHT_TO_LEFT_MARK.equals(getRun().getText())); }

        Bookmark getBookmark() { return mBookmark; };

        private Bookmark mBookmark;
    }

    private static final String LEFT_TO_RIGHT_MARK = "\u200e";
    private static final String RIGHT_TO_LEFT_MARK = "\u200f";

    /// <summary>
    /// The document builder the text is to be written to.
    /// </summary>
    private /*final*/ DocumentBuilder mBuilder;

    /// <summary>
    /// Indicates that the text is in visual order (right-to-left text is reversed), 
    /// as apposed to much more common logical order.
    /// </summary>
    private /*final*/ boolean mTextIsVisuallyOrdered;

    /// <summary>
    /// Indicates whether adjacent runs that have equal formatting are allowed to join into one run.
    /// </summary>
    private /*final*/ boolean mJoinRuns;

    /// <summary>
    /// Runs of text to be reordered with the bidirectional algorithm. Element type is <see cref="PendingRun"/>.
    /// </summary>
    private /*final*/ ArrayList<HtmlBidiTextArranger.PendingRun> mRuns = new ArrayList<HtmlBidiTextArranger.PendingRun>();

    /// <summary>
    /// The paragraph the text belongs to.
    /// </summary>
    private Paragraph mParagraph;

    /// <summary>
    /// Indicates that text ends with a space character that was imported from a non-breaking space
    /// and thus cannot be collapsed with spaces after it.
    /// </summary>
    private boolean mDontCollapseWhitespaceAfterText;
}

