// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a text node of an HTML document tree.
/// </summary>
class HtmlTextNode extends HtmlNode
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="text">The text of the node.</param>
    HtmlTextNode(String text)
    {
        Debug.assertTrue(StringUtil.hasChars(text));
        mText = text;
    }

    /// <summary>
    /// Serializes the node to XML text.
    /// </summary>
    /// <param name="result">
    /// The text buffer to which the HTML text of this node will be appended.
    /// </param>
    /// <param name="serializationContext">Serialization context.</param>
    /// <remarks>
    /// This method follows the algorithm described here: http://www.w3.org/TR/html5/syntax.html#serializing-html-fragments
    /// </remarks>
    /*override*/ void serializeToXml(StringBuilder result, XmlSerializationContext serializationContext)
    {
        for (int i = 0; i < mText.length(); i++)
        {
            char c = mText.charAt(i);
            switch (c)
            {
                case '&':
                    msStringBuilder.append(result, "&amp;");
                    break;
                case '\u00A0':
                    // The "&nbsp;" entity is not defined in XML, so a numeric character reference is used instead.
                    msStringBuilder.append(result, HtmlConstants.NO_BREAK_SPACE_HTML_NUMERIC_REF);
                    break;
                case '<':
                    msStringBuilder.append(result, "&lt;");
                    break;
                case '>':
                    msStringBuilder.append(result, "&gt;");
                    break;
                default:
                    result.append(c);
                    break;
            }
        }
    }

    /*override*/ boolean isBoxOrContainsBoxes()
    {
        // Text nodes are not boxes and cannot contain other boxes.
        return false;
    }

    /*override*/ boolean containsText()
    {
        return mTextHasNonIgnoredCharacters;
    }

    /// <summary>
    /// Gets the text of the node.
    /// </summary>
    String getText() { return mText; }

    protected /*override*/ boolean buildNodeBoxLayout(boolean isStart, boolean preformattedWhitespace)
    {
        if (!isStart)
        {
            // This should never happen since text nodes cannot have children.
            return true;
        }

        // Text nodes cannot be empty; they always contain some characters (whitespace or usual).
        // If whitespace is meaningful (preformatted), any text will be meaningful.
        // If whitespace is ignored, only text that contains characters other than whitespace will be meaningful.
        if (preformattedWhitespace)
        {
            Debug.assertTrue(StringUtil.hasChars(mText));
            mTextHasNonIgnoredCharacters = true;
        }
        else
        {
            mTextHasNonIgnoredCharacters = HtmlUtil.containsAnythingButWhitespaces(mText, true);
        }

        // The return value doesn't really matter here because text nodes cannot have children and therefore cannot be
        // "subtree roots". Let's return "true" to indicate that no special processing is needed.
        return true;
    }

    private /*final*/ String mText;

    private boolean mTextHasNonIgnoredCharacters;
}

