// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.UnicodeUtil;


/// <summary>
/// Provides access to a CSS text with consume/reconsume operations, look-ahead buffer, and character pre-processing,
/// as required by <see cref="CssTokenizer"/>.
/// </summary>
class CssTokenizerStream
{
    /// <summary>
    /// Constructor. Creates a new instance of the stream over the specified CSS text.
    /// </summary>
    CssTokenizerStream(String css)
    {
        mCss = css;

        // Fill up the look-ahead buffer.
        mPositionInBuffer = RECONSUME_BUFFER_DEPTH;
        for (int i = mPositionInBuffer + 1; i < mBuffer.length; i++)
        {
            mBuffer[i] = readNextChar();
        }
    }

    /// <summary>
    /// Consumes another character (steps forward).
    /// </summary>
    boolean consumeChar()
    {
        if (mPositionInBuffer < RECONSUME_BUFFER_DEPTH)
        {
            ++mPositionInBuffer;
        }
        else
        {
            // Shift the look-ahead buffer forward.
            for (int i = 1; i < mBuffer.length; i++)
            {
                mBuffer[i - 1] = mBuffer[i];
            }
            // Read another character.
            mBuffer[mBuffer.length - 1] = readNextChar();
        }
        return mBuffer[mPositionInBuffer] != EOF_CHAR;
    }

    /// <summary>
    /// Reconsumes the current character (steps backward).
    /// </summary>
    /// <remarks>
    /// Only one character can be reconsumed at a time. Repeated calls to this method will throw an exception.
    /// </remarks>
    void reconsumeChar()
    {
        if (mPositionInBuffer > 0)
        {
            --mPositionInBuffer;
            return;
        }
        throw new IllegalStateException("Tried to reconsume too many characters.");
    }

    /// <summary>
    /// Returns a value indicating whether the end of stream has been reached and the next character is
    /// <see cref="EofChar"/>.
    /// </summary>
    boolean isAtEof()
    {
        return getNext() == EOF_CHAR;
    }

    /// <summary>
    /// The last consumed character.
    /// </summary>
    char getCurrent() { return mBuffer[mPositionInBuffer]; }

    /// <summary>
    /// The character to be consumed next.
    /// </summary>
    char getNext() { return mBuffer[mPositionInBuffer + 1]; }

    /// <summary>
    /// The the character to be consumed after <see cref="Next"/>.
    /// </summary>
    char getNext2() { return mBuffer[mPositionInBuffer + 2]; }

    /// <summary>
    /// The the character to be consumed after <see cref="Next2"/>.
    /// </summary>
    char getNext3() { return mBuffer[mPositionInBuffer + 3]; }

    /// <summary>
    /// A special character indicating that the end of the CSS text has been reached and there are no more characters to
    /// consume.
    /// </summary>
    static final char EOF_CHAR = '\u0000';

    /// <summary>
    /// Reads another character from the CSS text.
    /// </summary>
    /// <remarks>
    /// Characters are preprocessed as desrcibed here: https://www.w3.org/TR/css-syntax-3/#input-preprocessing
    /// </remarks>
    private char readNextChar()
    {
        if (mPositionInCss >= mCss.length())
        {
            return EOF_CHAR;
        }

        char nextChar = mCss.charAt(mPositionInCss);
        ++mPositionInCss;

        switch (nextChar)
        {
            case '\r':
            {
                // Replace "\r\n" with a single '\n' character.
                if ((mPositionInCss < mCss.length()) && (mCss.charAt(mPositionInCss) == '\n'))
                {
                    ++mPositionInCss;
                }
                // Normalize line endings.
                return '\n';
            }
            case '\f':
                // Normalize line endings.
                return '\n';
            case '\u0000':
                // Replace zeroes.
                return UnicodeUtil.REPLACEMENT_CHAR;
            default:
                return nextChar;
        }
    }

    /// <summary>
    /// The original CSS text being processed.
    /// </summary>
    private /*final*/ String mCss;

    /// <summary>
    /// Index of the character to read next from the original CSS text.
    /// </summary>
    private int mPositionInCss;

    /// <summary>
    /// Max number of next characters the calling code can examine without consuming.
    /// </summary>
    private static final int LOOK_AHEAD_BUFFER_DEPTH = 3;

    /// <summary>
    /// Max number of characters the calling code can reconsume.
    /// </summary>
    private static final int RECONSUME_BUFFER_DEPTH = 1;

    /// <summary>
    /// Buffer of pre-processed characters.
    /// </summary>
    /// <remarks>
    /// The buffer contains pre-processed characters (see <see cref="ReadNextChar"/>) in the same order as in the original
    /// CSS text. The structure of the buffer from the beginning to the end is as follows: reconsumed characters
    /// (the reconsume buffer), the current character (see <see cref="Current"/>), and next characters that has not been
    /// consumed yet (the look-ahead buffer, see <see cref="Next"/> and similar properties).
    /// </remarks>
    private /*final*/ char[] mBuffer = new char[LOOK_AHEAD_BUFFER_DEPTH + RECONSUME_BUFFER_DEPTH + 1];

    /// <summary>
    /// Index of the current character in <see cref="mBuffer"/>.
    /// </summary>
    /// <remarks>
    /// This index moves backward when the current character is reconsumed and moves forward when a reconsumed character
    /// is later consumed again.
    /// </remarks>
    private int mPositionInBuffer;
}

