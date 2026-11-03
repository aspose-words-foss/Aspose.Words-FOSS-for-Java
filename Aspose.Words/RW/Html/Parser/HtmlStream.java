// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;


/// <summary>
/// Preprocesses an HTML text according to HTML 5 rules and provides means to read its contents.
/// </summary>
class HtmlStream
{
    /// <summary>
    /// Constructor.
    /// </summary>
    /// <param name="html">An HTML text read from file.</param>
    HtmlStream(String html)
    {
        Debug.assertTrue(html != null);

        // Preprocess the HTML as described here: http://www.w3.org/TR/html5/syntax.html#preprocessing-the-input-stream
        // The leading BOM character is not removed, because it was removed when the HTML text was loaded.
        StringBuilder preprocessedHtml = new StringBuilder(html);
        // Convert CRLF to LF, single CR to LF. The order of the line ending replacements is important.
        msStringBuilder.replace(preprocessedHtml, "\r\n", "\n");
        msStringBuilder.replace(preprocessedHtml, '\r', '\n');

        // It is unlikely that we meet an end-of-file character in an HTML document, but let's secure ourselves against 
        // that situation by replacing the end-of-file character with the null character, which will be processed
        // by the HTML tokenizer.
        msStringBuilder.replace(preprocessedHtml, EOF_CHAR, '\u0000');

        mHtml = preprocessedHtml.toString();

        // Set the current position just before the first character, so the first character will be consumed next.
        seek(-1);
    }

    /// <summary>
    /// Moves the stream pointer forward one character if possible.
    /// </summary>
    void consumeChar()
    {
        consumeChar(1);
    }

    /// <summary>
    /// Moves the stream pointer forward the specified number of characters.
    /// </summary>
    /// <param name="count">The number of characters the stream pointer will be moved.</param>
    /// <remarks>
    /// This method either moves the stream pointer exactly the specified number of characters forward, or fails.
    /// </remarks>
    void consumeChar(int count)
    {
        int newPosition = mPosition + count;
        Debug.assertTrue(newPosition <= mHtml.length());
        seek(newPosition);
    }

    /// <summary>
    /// Moves the stream pointer back one character if possible.
    /// </summary>
    void unconsumeChar()
    {
        int newPosition = mPosition - 1;
        Debug.assertTrue(newPosition >= -1);
        seek(newPosition);
    }

    /// <summary>
    /// Compares next characters of the stream with the specified string.
    /// </summary>
    /// <param name="str">The string to compare with.</param>
    /// <param name="asciiCaseInsensitive">
    /// Whether to use ASCII case-insensitive comparison. Otherwise, ordinal case-sensitive comparison is used.
    /// </param>
    /// <remarks>This method does not move the stream pointer.</remarks>
    boolean nextStringEquals(String str, boolean asciiCaseInsensitive)
    {
        Debug.assertTrue(StringUtil.hasChars(str));

        int position = mPosition;
        for (int i = 0; i < str.length(); i++)
        {
            ++position;
            if (!isInsideHtml(position))
            {
                return false;
            }
            char c1 = mHtml.charAt(position);
            char c2 = str.charAt(i);
            if (asciiCaseInsensitive)
            {
                c1 = StringUtil.asciiLowerCase(c1);
                c2 = StringUtil.asciiLowerCase(c2);
            }
            if (c1 != c2)
            {
                return false;
            }
        }
        return true;
    }

    /// <summary>
    /// Stores the current stream pointer in a bookmark.
    /// </summary>
    /// <returns>A bookmark instance storing the current stream pointer value.</returns>
    HtmlStreamBookmark setBookmark()
    {
        return new HtmlStreamBookmark(this, mPosition);
    }

    /// <summary>
    /// Moves the stream pointer to the position of the specified bookmark.
    /// </summary>
    /// <param name="bookmark">The bookmark storing the position to which the stream pointer will be moved.</param>
    void unconsumeToBookmark(HtmlStreamBookmark bookmark)
    {
        Debug.assertTrue(bookmark != null);
        Debug.assertTrue(bookmark.getStream() == this);

        seek(bookmark.getPosition());
    }

    /// <summary>
    /// Gets text from the stream starting from the specified bookmark and up to the current position.
    /// The current character is not included.
    /// </summary>
    String getBookmarkedText(HtmlStreamBookmark bookmark)
    {
        Debug.assertTrue(bookmark != null);
        Debug.assertTrue(bookmark.getStream() == this);

        int start = Math.max(0, bookmark.getPosition());
        int length = Math.max(0, mPosition - start);

        return mHtml.substring((start), (start) + (length));
    }

    /// <summary>
    /// Gets the character that has been consumed last. Does not move the stream pointer.
    /// </summary>
    char getCurrentChar() { return mCurrentChar; }

    /// <summary>
    /// Gets the character that will be consumed (read) next. Does not move the stream pointer.
    /// </summary>
    char getNextChar() { return mNextChar; }

    /// <summary>
    /// The special character that indicates the absence of any character (the end-of-file character).
    /// </summary>
    static final char EOF_CHAR = '\uFFFF';

    /// <summary>
    /// Moves the stream pointer to the specified position and reads current and next characters.
    /// </summary>
    /// <param name="position">The position to which the stream pointer will be moved.</param>
    private void seek(int position)
    {
        mPosition = position;

        mCurrentChar = (isInsideHtml(position))
            ? mHtml.charAt(position)
            : EOF_CHAR;

        int nextCharPosition = position + 1;
        mNextChar = (isInsideHtml(nextCharPosition))
            ? mHtml.charAt(nextCharPosition)
            : EOF_CHAR;
    }

    /// <summary>
    /// Checks whether the specified position is a valid index of the preprocessed HTML text.
    /// </summary>
    /// <param name="position">The position that will be checked.</param>
    /// <returns><c>true</c> if the position is a valid index; <c>false</c> otherwise.</returns>
    private boolean isInsideHtml(int position)
    {
        return (position >= 0) && (position < mHtml.length());
    }

    /// <summary>
    /// The preprocessed HTML text.
    /// </summary>
    private /*final*/ String mHtml;

    /// <summary>
    /// The stream pointer. Index of the character that has been consumed last.
    /// </summary>
    private int mPosition;

    /// <summary>
    /// The character that has been consumed last.
    /// </summary>
    private char mCurrentChar;

    /// <summary>
    /// The character that will be consumed next.
    /// </summary>
    private char mNextChar;
}

