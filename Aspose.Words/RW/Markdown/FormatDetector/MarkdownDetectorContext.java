// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.CustomTextReader;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;


/// <summary>
/// The markdown format detector context.
/// </summary>
/// <remarks>
/// Allows to read data from a text reader and keeps various information about it to use in markdown detectors.
/// </remarks>
class MarkdownDetectorContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MarkdownDetectorContext(CustomTextReader reader)
    {
        mReader = reader;
    }

    /// <summary>
    /// Gets a boolean value indicating whether a specified collection contains
    /// markdown features that can break a paragraph.
    /// </summary>
    static boolean canBreakParagraph(HashSetGeneric<Integer> featureTypes)
    {
        Debug.assertTrue(featureTypes != null);

        for (/*MarkdownFeatureType*/int featureType : (Iterable</*MarkdownFeatureType*/Integer>) featureTypes)
        {
            if (canBreakParagraph(featureType))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Reads a new line from a reader.
    /// </summary>
    boolean readLine() throws Exception
    {
        mPrevLine = mLine;
        mPrevLineDetectedFeatures = mCurLineDetectedFeatures;
        mCurLineDetectedFeatures = new HashSetGeneric<Integer>();

        StringBuilder sb = new StringBuilder();

        while (canRead())
        {
            char c = mReader.readChar();

            // WORDSNET-28614 Account sentences length.
            if (isEndOfSentence())
            {
                if (mCurSentenceLength > MAX_SENTENCE_LENGTH)
                    mSentencesWithExceededLengthTotalLength += mCurSentenceLength;
                mCurSentenceLength = 0;
            }
            else
            {
                mCurSentenceLength++;
            }

            if (TxtFormatDetector.isControlChar(c) && !TxtFormatDetector.isAllowedControlChar(c, getEncoding().getCodePage()))
                mNonPrintableCharsCount++;

            switch (c)
            {
                case ControlChar.PARAGRAPH_BREAK_CHAR:
                {
                    // Normalize input - use \n for end of line instead of \r\n.
                    sb.append(ControlChar.LINE_FEED_CHAR);
                    // Skip \n that follows just after \r.
                    read(ControlChar.LINE_FEED_CHAR);

                    mLine = sb.toString();
                    return true;
                }
                case ControlChar.LINE_FEED_CHAR:
                {
                    sb.append(ControlChar.LINE_FEED_CHAR);
                    mLine = sb.toString();

                    return true;
                }
                case ControlChar.TAB_CHAR:
                {
                    // Expand tabs to detect markdown blocks, such as InlineCode.
                    msStringBuilder.append(sb, msString.newString(' ', MarkdownUtil.TAB_SIZE));
                    break;
                }
                default:
                    sb.append(c);
                    break;
            }
        }

        mLine = sb.toString();

        return (mLine.length() > 0);
    }

    /// <summary>
    /// Reads a next character from the reader, if it is equal to a specified one.
    /// </summary>
    private void read(char c) throws Exception
    {
        if (canRead())
        {
            char curChar = mReader.readChar();
            if (c != curChar)
                mReader.stepBack();
        }
    }

    /// <summary>
    /// Gets last read line.
    /// </summary>
    String getLine() { return mLine; }

    /// <summary>
    /// Gets a boolean value indicating whether the previous paragraph was broken.
    /// </summary>
    boolean isPrevParaBroken() { return (isPrevLineBlank() || canBreakParagraph(mPrevLineDetectedFeatures)); }

    /// <summary>
    /// Gets a boolean value indicating whether the reader has available characters to read.
    /// </summary>
    boolean getEndOfFile() throws Exception { return !mReader.hasChars(); }

    /// <summary>
    /// The collection of markdown features detected in a current line of text.
    /// </summary>
    HashSetGeneric<Integer> getCurLineDetectedFeatures() { return mCurLineDetectedFeatures; }

    /// <summary>
    /// The collection of markdown features detected in a previous line of text.
    /// </summary>
    HashSetGeneric<Integer> getPrevLineDetectedFeatures() { return mPrevLineDetectedFeatures; }

    /// <summary>
    /// Gets Encoding object of the reader.
    /// </summary>
    Encoding getEncoding() { return mReader.getEncoding(); }

    /// <summary>
    /// Gets the total number of characters being read by the reader.
    /// </summary>
    long getLength() throws Exception { return Math.min(mReader.getStream().getLength(), MAX_CHARS_TO_READ); }

    /// <summary>
    /// Gets count of the non-printable characters that were read in the context.
    /// </summary>
    int getNonPrintableCharsCount() { return mNonPrintableCharsCount; }

    /// <summary>
    /// Gets integer value representing total length of sentences
    /// which length exceeds maximum allowed <see cref="MaxSentenceLength"/>.
    /// </summary>
    int getLongSentencesTotalLength()
    {
        int sentencesWithExceededLengthTotalLength = mSentencesWithExceededLengthTotalLength;
        if (mCurSentenceLength > MAX_SENTENCE_LENGTH)
            sentencesWithExceededLengthTotalLength += mCurSentenceLength;

        return sentencesWithExceededLengthTotalLength;
    }

    /// <summary>
    /// Gets a boolean value indicating whether a specified markdown feature can break a paragraph.
    /// </summary>
    private static boolean canBreakParagraph(/*MarkdownFeatureType*/int type)
    {
        return ((type == MarkdownFeatureType.HEADING) || (type == MarkdownFeatureType.HORIZONTAL_RULE));
    }

    /// <summary>
    /// Gets a boolean value indicating whether the previous line is blank.
    /// </summary>
    private boolean isPrevLineBlank() { return StringUtil.containsOnlyWhitespaces(mPrevLine); }

    /// <summary>
    /// Returns true, if we currently at the end of a sentence.
    /// </summary>
    private boolean isEndOfSentence() throws Exception
    {
        char curChar = mReader.getCurChar();
        if ((curChar != '.') && (curChar != '!') && (curChar != '?'))
            return false;

        char nextChar = mReader.getNextChar();
        if ((nextChar == '\u0000') && canRead())
        {
            nextChar = mReader.readChar();
            mReader.stepBack();
        }

        return (nextChar == '\u0000') || msChar.isWhiteSpace(nextChar);
    }

    /// <summary>
    /// Gets a boolean value indicating whether a new character can be read from the underlying reader.
    /// </summary>
    private boolean canRead() throws Exception
    {
        if (mReader.getTotalCharsRead() >= MAX_CHARS_TO_READ)
            return false;

        if (!mReader.hasChars())
        {
            // When the reader has no more characters to read, the reason of that can be of two types:
            // 1) The underlying stream has no more bytes.
            // 2) The read limit is reached.
            // As we have not yet exceeded the total allowed number of characters to read, we need to reset
            // read limit to try read a new portion of bytes from the stream to a buffer.
            mReader.resetReadLimit();
            if (!mReader.hasChars())
                return false;
        }

        return true;
    }

    private /*final*/ CustomTextReader mReader;

    private String mPrevLine;
    private String mLine;

    private HashSetGeneric<Integer> mCurLineDetectedFeatures;
    private HashSetGeneric<Integer> mPrevLineDetectedFeatures;

    private int mNonPrintableCharsCount;

    private int mCurSentenceLength;
    private int mSentencesWithExceededLengthTotalLength;

    /// <summary>
    /// Limits the maximum number of characters to read.
    /// </summary>
    /// <remarks>
    /// This value is obtained by experimental means. Feel free to adjust it if needed.
    /// </remarks>
    private static final int MAX_CHARS_TO_READ = 1024;

    /// <summary>
    /// Specifies the maximum length of a sentence, beyond which it will be considered too long.
    /// </summary>
    /// <remarks>
    /// This is an approximate value chosen based on the following considerations.
    /// There can be about 15-20 words in a sentence and each word consists of about 7-8 letters in average.
    /// Actually, this may be too much or too little in some cases. Then, we can reduce the value in the future,
    /// if needed.
    /// </remarks>
    private static final int MAX_SENTENCE_LENGTH = 160;
}

