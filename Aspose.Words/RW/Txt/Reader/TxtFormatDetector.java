// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.CustomTextReader;
import com.aspose.Debug;
import com.aspose.CodePage;
import com.aspose.ms.System.msChar;
import com.aspose.CharacterClassifier;


/// <summary>
/// <para>The class is helper for <see cref="FileFormatDetector"/></para>
/// <para>The class confirms or rejects an assumption that a provided stream is Plain Text</para>
/// </summary>
class TxtFormatDetector
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private TxtFormatDetector(CustomTextReader textReader)
    {
        Debug.assertTrue(textReader != null);
        mTextReader = textReader;
    }

    /// <summary>
    /// Returns <see cref="FileFormatInfo"/> if Plain Text assumption has been confirmed, or null if rejected.
    /// </summary>
    static FileFormatInfo detect(CustomTextReader streamReader) throws Exception
    {
        return new TxtFormatDetector(streamReader).detect();
    }

    /// <summary>
    /// Checks if the char is allowed in text
    /// </summary>
    static boolean isAllowedControlChar(char c, int codePage)
    {
        switch (c)
        {
            case ZERO_CHAR:
            case ControlChar.TAB_CHAR:
            case ControlChar.LINE_FEED_CHAR:
            case ControlChar.PARAGRAPH_BREAK_CHAR:
            case NEXT_LINE_CHAR:
            case ControlChar.PAGE_BREAK_CHAR:
            case ControlChar.CELL_CHAR:
            case ControlChar.LINE_BREAK_CHAR:
                return true;
            // WORDSNET-23607 Allow 0x80 control char for Shift JIS encoding.
            case (char)0x80:
                return (codePage == CodePage.WINDOWS_JAPANESE_SHIFT_JIS);
            default:
                return false;
        }
    }

    /// <summary>
    /// Checks if the char is Unicode control character ISO 6429
    /// </summary>
    static boolean isControlChar(char c)
    {
        return (c <= 0x1F) || (c == 0x7F) || ((c >= 0x80) && (c <= 0x9F));
    }

    /// <summary>
    /// Checks if the char is zero.
    /// </summary>
    private static boolean isZeroChar(char c)
    {
        return (c == ZERO_CHAR);
    }

    private static boolean isNextLineChar(char c)
    {
        return (c == ControlChar.LINE_FEED_CHAR) || (c == NEXT_LINE_CHAR);
    }

    private static boolean isWordDelimeterChar(char ch)
    {
        return (msChar.isWhiteSpace(ch)) || (isNextLineChar(ch)) || (ch == ',') || (ch == ';') || (ch == '|') ||
            (ch == ControlChar.PAGE_BREAK_CHAR) || (ch == ControlChar.CELL_CHAR);
    }

    private FileFormatInfo detect() throws Exception
    {
        int totalCharsCount = 0;
        int linesCount = 0;
        int wordsCount = 0;
        int wordsLength = 0;
        int letterAndDigitCount = 0;
        int currentWordLength = 0;
        int nonPrintableCharCount = 0;
        int maxSequenceZeroCount = 0;
        int currentSequenceZeroCount = 0;
        boolean hasRtlScript = false;

        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();
            if (CharacterClassifier.isRtlScript(ch))
                hasRtlScript = true;

            if (isControlChar(ch) && !isAllowedControlChar(ch, mTextReader.getEncoding().getCodePage()))
                nonPrintableCharCount++;

            if (isZeroChar(ch))
            {
                currentSequenceZeroCount++;
                if (currentSequenceZeroCount > maxSequenceZeroCount)
                    maxSequenceZeroCount = currentSequenceZeroCount;
            }
            else
                currentSequenceZeroCount = 0;

            totalCharsCount++;
            if (isWordDelimeterChar(ch))
            {
                if (currentWordLength != 0)
                {
                    wordsCount++;
                    wordsLength += currentWordLength;
                    currentWordLength = 0;
                }
                if (isNextLineChar(ch))
                    linesCount++;
            }
            else
            {
                currentWordLength++;
                if (Character.isLetterOrDigit(ch))
                    letterAndDigitCount++;
            }
        }

        if (currentWordLength != 0)
        {
            wordsCount++;
            wordsLength += currentWordLength;
        }

        int txtConfidence = 0;

        // checks letters ratio
        final int LETTERS_RATIO_WEIGHT = 2;
        final double LEGAL_LETTERS_RATIO = 0.3;
        if ((totalCharsCount > 0) && ((double)letterAndDigitCount / totalCharsCount >= LEGAL_LETTERS_RATIO))
        {
            // WORDSNET-7000 UnsupportedFileFormatException occurs when loading a TEXT file into DOM.
            // The problem occurs because of too strong criteria for text files detecting.
            // This criterion is weightier now and all criteria are weaker.
            txtConfidence += LETTERS_RATIO_WEIGHT;
        }

        // checks average line length
        final int LEGAL_LINE_MAX_LENGTH = 200;
        if ((linesCount > 0) && (totalCharsCount / linesCount < LEGAL_LINE_MAX_LENGTH))
        {
            txtConfidence++;
        }

        // the longest English word, that appears in a major dictionary, consist of 45 characters
        // Let's use this criterion and round it to 50.
        final int LEGAL_WORD_MAX_LENGTH = 50;
        // checks average word size
        if (wordsCount > 0)
        {
            int avgWordLength = (wordsLength / wordsCount);
            if (avgWordLength < LEGAL_WORD_MAX_LENGTH)
            {
                txtConfidence++;
            }
            else
            {
                // WORDSNET-26228 Decrease confidence on bad words length.
                // WORDSNET-26255 Introduce weight for long strings.
                txtConfidence -= avgWordLength/LEGAL_WORD_MAX_LENGTH;
            }
        }
        else
        {
            txtConfidence--;
        }

        // Maximum number of sequence zero character.
        final int LEGAL_SEQUENCE_ZERO_COUNT = 2;
        if (maxSequenceZeroCount > LEGAL_SEQUENCE_ZERO_COUNT)
            txtConfidence -= 2;

        // WORDSNET-7574 Incorrect encoding is used while loading TXT file.
        // Allow several non-printable characters in a text. (Let's believe that they are erroneous characters).
        // WORDSNET-13777 Wrong detection of text file that contains non-printable characters.
        final int NON_PRINTABLE_CHAR_MAX_COUNT = 2;
        if (nonPrintableCharCount > NON_PRINTABLE_CHAR_MAX_COUNT)
            txtConfidence -= 100 * nonPrintableCharCount / totalCharsCount;
        else
            txtConfidence++;

        if (txtConfidence >= 2)
        {
            FileFormatInfo fileFormatInfo = new FileFormatInfo();
            fileFormatInfo.setLoadFormat(LoadFormat.TEXT);
            fileFormatInfo.setEncoding(mTextReader.getEncoding());
            fileFormatInfo.hasRtlScript(hasRtlScript);
            return fileFormatInfo;
        }

        return null;
    }

    private /*final*/ CustomTextReader mTextReader;

    private static final char NEXT_LINE_CHAR = (char)0x85;
    private static final char ZERO_CHAR = (char)0x00;
}

