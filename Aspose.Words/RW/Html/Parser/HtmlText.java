// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.UnicodeUtil;


/// <summary>
/// Processes text tokens during construction of HTML trees.
/// </summary>
/// <remarks>
/// The HTML tree constructor works with text strings, but HTML Specification describes processing of text
/// in terms of characters. This class helps to manipulate characters of text strings.
/// </remarks>
class HtmlText
{
    HtmlText()
    {
        mText = new StringBuilder();
    }

    HtmlText(String text)
    {
        mText = new StringBuilder(text);
    }

    void clear()
    {
        mText.setLength(0);
    }

    void append(HtmlText text)
    {
        msStringBuilder.append(mText, text.mText);
    }

    void removeLeadingLineFeedChar()
    {
        if ((mText.length() > 0) && (mText.charAt(0) == '\n'))
        {
            mText.delete((0), (0) + (1));
        }
    }

    boolean containsAnythingButWhitespace()
    {
        for (int i = 0; i < mText.length(); i++)
        {
            if (!HtmlUtil.isWhitespace(mText.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    boolean containsAnythingButWhitespaceOrNull()
    {
        for (int i = 0; i < mText.length(); i++)
        {
            char c = mText.charAt(i);
            if ((c != '\u0000') && (!HtmlUtil.isWhitespace(c)))
            {
                return true;
            }
        }
        return false;
    }

    void trimLeadingWhitespace()
    {
        int leadingWhitespaceCount = countLeadingWhitespace();
        if (leadingWhitespaceCount > 0)
        {
            mText.delete((0), (0) + (leadingWhitespaceCount));
        }
    }

    void stripNullCharacters()
    {
        msStringBuilder.replace(mText, "\u0000", null);
    }

    void replaceNullCharacters()
    {
        msStringBuilder.replace(mText, '\u0000', UnicodeUtil.REPLACEMENT_CHAR);
    }

    String getLeadingWhitespace()
    {
        int leadingWhitespaceCount = countLeadingWhitespace();
        if (leadingWhitespaceCount > 0)
        {
            char[] whitespace = new char[leadingWhitespaceCount];
            mText.getChars((0), (0) + (leadingWhitespaceCount), (whitespace), (0));
            return new String(whitespace);
        }
        else
        {
            return "";
        }
    }

    String getAllWhitespaces()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mText.length(); i++)
        {
            char c = mText.charAt(i);
            if (HtmlUtil.isWhitespace(c))
            {
                result.append(c);
            }
        }
        return result.toString();
    }

    String getAsString()
    {
        return mText.toString();
    }

    boolean isEmpty() { return mText.length() == 0; }

    private int countLeadingWhitespace()
    {
        int result = 0;
        while ((result < mText.length()) && HtmlUtil.isWhitespace(mText.charAt(result)))
        {
            ++result;
        }
        return result;
    }

    private /*final*/ StringBuilder mText;
}

