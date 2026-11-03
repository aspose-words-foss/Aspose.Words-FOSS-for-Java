// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ImageUtil;
import com.aspose.URI;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents a markdown LinkDestination block (with title).
/// </summary>
class LinkDestinationBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return false;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        Debug.assertTrue(isOpened());

        add(block);
        return true;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context)
    {
        // A LinkDestination should be never written independently, but only along with a LinkText or ImageDescription,
        // which are always go before it. So, nothing to do here.
    }

    /// <summary>
    /// Returns true, if text between startIndex and endIndex can be a valid Link destination.
    /// </summary>
    static boolean isValid(String text, int startIndex, int endIndex)
    {
        int openAngleBracket = MarkdownUtil.indexOfUnescaped(text, '<', startIndex, endIndex);
        int closeAngleBracket = (openAngleBracket == -1)
            ? -1
            : MarkdownUtil.indexOfUnescaped(text, '>', openAngleBracket, endIndex);

        int startNonWhitespace = MarkdownUtil.indexOfNonWhitespace(text, startIndex, endIndex);

        // Check URI.
        int uriStart = ((openAngleBracket > -1) && (closeAngleBracket > -1) && (openAngleBracket <= startNonWhitespace))
            ? openAngleBracket
            : startNonWhitespace;
        if (uriStart == -1)
            // The empty URI is allowed.
            return true;

        int uriEnd = ((openAngleBracket > -1) && (closeAngleBracket > -1) && (openAngleBracket <= startNonWhitespace))
            ? closeAngleBracket
            : MarkdownUtil.indexOfWhitespace(text, uriStart, endIndex);

        if (uriEnd == -1)
            uriEnd = endIndex;

        if (!isValidUri(text, uriStart, uriEnd))
            return false;

        // Check Title.
        int titleStart = MarkdownUtil.indexOfNonWhitespace(text, uriEnd + 1, endIndex);
        // Uri and title must have at least one space between each other.
        if ((titleStart == uriEnd + 1) && (MarkdownUtil.indexOfWhitespace(text, startIndex, endIndex) < 0))
            return false;
        if (titleStart == -1)
            // The empty title is allowed.
            return true;

        if (!isValidTitle(text, titleStart, endIndex))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if text between specified delimiters can be a valid Link destination.
    /// </summary>
    static boolean isValid(Delimiter opening, Delimiter closing)
    {
        Debug.assertTrue((opening != null) && (closing != null));
        Debug.assertTrue(opening.isBefore(closing));

        return isValidSvgDataUrl(opening.getText().substring((opening.getEnd() + 1), (opening.getEnd() + 1) + ((closing.getStart() - 1) - opening.getEnd()))) ||
               isValid(opening.getText(), opening.getEnd() + 1, closing.getStart() - 1);
    }

    /// <summary>
    /// Returns true, if a specified string is valid Link URI.
    /// </summary>
    private static boolean isValidUri(String text, int uriStart, int uriEnd)
    {
        // There are two rules for the URI. First one is when URI is enclosed in '<' and '>'
        // and the second one when it is not.
        if (isInAngleBrackets(text, uriStart, uriEnd))
        {
            if (MarkdownUtil.hasNonEscapedCharacters(text, uriStart + 1, uriEnd - 1, '<', '>'))
                return false;

            return msString.indexOf(text, MarkdownUtil.SOFT_LINE_BREAK_CHAR, uriStart, uriEnd-uriStart ) == -1;
        }
        else
        {
            if (text.length() == 0)
                return false;

            if (text.charAt(uriStart) == '<')
                return false;

            for (int i = uriStart; i < uriEnd; i++)
            {
                char ch = text.charAt(i);
                if (MarkdownUtil.isAsciiControlChar(ch))
                    return false;

                if (ch == MarkdownUtil.SPACE_CHAR)
                    return false;

                if (ch == MarkdownUtil.SOFT_LINE_BREAK_CHAR)
                    return false;

                if ((ch == MarkdownUtil.HARD_LINE_BREAK_SLASH_CHAR) || (ch == MarkdownUtil.HARD_LINE_BREAK_SPACES_CHAR))
                    return false;
            }

            return MarkdownUtil.areBalanced(text, uriStart, uriEnd, '(', ')');
        }
    }

    /// <summary>
    /// Returns true, if a specified text started and ended with the specified positions can be a valid Link title.
    /// </summary>
    private static boolean isValidTitle(String text, int titleStart, int titleEnd)
    {
        Debug.assertTrue(text != null);

        // Trim whitespace characters from the end.
        while ((titleEnd >= 0) && MarkdownUtil.isWhitespaceCharacter(text.charAt(titleEnd)))
        {
            titleEnd--;
        }

        int length = (titleEnd - titleStart) + 1;

        // A link title can be empty.
        if (length < 1)
            return true;

        // If link title is not empty, then it must be wrapped into opening and closing delimiters.
        if (length < 2)
            return false;

        // Check link title has opening delimiter.
        char openingDelimiter = text.charAt(titleStart);
        if (!isTitleOpening(openingDelimiter))
            return false;

        // Check closing delimiter is not escaped.
        if (text.charAt(titleEnd - 1) == ControlChar.BACKSLASH_CHAR)
            return false;

        // Check link title has closing delimiter.
        char closingDelimiter = text.charAt(titleEnd);
        if (!isTitleClosing(closingDelimiter))
            return false;

        // Check that closing delimiter corresponds to an opening one.
        char expectedClosingDelimiter = (openingDelimiter == '(') ? ')' : openingDelimiter;
        if (closingDelimiter != expectedClosingDelimiter)
            return false;

        // At last, check there is no unescaped opening or closing delimiters.
        if (MarkdownUtil.hasNonEscapedCharacters(text, titleStart + 1, titleEnd - 1, openingDelimiter, closingDelimiter))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if the text is in the angle brackets.
    /// </summary>
    private static boolean isInAngleBrackets(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue(text != null);

        int length = (endIndex - startIndex) + 1;
        // There should be at least `<` and `>`.
        if (length < 2)
            return false;

        // Check closing `>` is not escaped.
        if (text.charAt(endIndex - 1) == ControlChar.BACKSLASH_CHAR)
            return false;

        return ((text.charAt(startIndex) == '<') && (text.charAt(endIndex) == '>'));
    }

    /// <summary>
    /// Returns true, if a specified character is valid link title opening delimiter.
    /// </summary>
    private static boolean isTitleOpening(char c)
    {
        return (c == '\"') || (c == '\'') || (c == '(');
    }

    /// <summary>
    /// Returns true, if a specified character is valid link title closing delimiter.
    /// </summary>
    private static boolean isTitleClosing(char c)
    {
        return (c == '\"') || (c == '\'') || (c == ')');
    }

    /// <summary>
    /// Returns true, if this link destination is an Svg image embedded as a data Url.
    /// </summary>
    boolean isSvgDataUrl() { return isValidSvgDataUrl(super.getText()); }

    /// <summary>
    /// Returns true, if a specified text is valid Svg data Url.
    /// </summary>
    private static boolean isValidSvgDataUrl(String text)
    {
        DataUrl dataUrl = DataUrl.parse(text);
        if (dataUrl != null)
        {
            byte[] dataBytes = dataUrl.getDataInUtf8();
            if (dataBytes.length > 0)
                return ImageUtil.isSvg(dataBytes);
        }

        return false;
    }

    /// <summary>
    /// Returns unescaped URI.
    /// </summary>
    private String getUnescapedUri()
    {
        String text = super.getText();
        int endIndex = text.length() - 1;

        if (isValidSvgDataUrl(text))
            return text.replace("\u000b", "%20");

        int openAngleBracket = MarkdownUtil.indexOfUnescaped(text, '<', 0, endIndex);
        int closeAngleBracket = (openAngleBracket == -1)
            ? -1
            : MarkdownUtil.indexOfUnescaped(text, '>', openAngleBracket, endIndex);
        int startNonWhitespace = MarkdownUtil.indexOfNonWhitespace(text, 0, endIndex);

        if ((openAngleBracket > -1) && (closeAngleBracket > -1) && (openAngleBracket <= startNonWhitespace))
        {
            text = text.substring((openAngleBracket + 1), (openAngleBracket + 1) + ((closeAngleBracket - openAngleBracket) - 1));
            return text;
        }

        // Skip leading whitespace characters in Uri.
        int uriStart = startNonWhitespace;
        // Uri can be empty.
        if (uriStart == -1)
            return "";

        int uriEnd = MarkdownUtil.indexOfWhitespace(text, uriStart, endIndex) - 1;
        if (uriEnd < 0)
            uriEnd = endIndex;

        // Valid URI can be surrounded with '<' and '>'.
        // Remove them to get `pure` text of URI.
        if (isInAngleBrackets(text, uriStart, uriEnd))
        {
            uriStart++;
            uriEnd--;
        }

        return text.substring((uriStart), (uriStart) + ((uriEnd - uriStart) + 1));
    }

    /// <summary>
    /// A type of the block.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.LINK_DESTINATION; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*override*/ String getText()
    {
        // The spec editor as well as GitLab are both ignore a link destination text inside ImageDescription.
        return (getParent(BlockType.IMAGE_DESCRIPTION) != null) ? "" : super.getText();
    }

    /// <summary>
    /// Gets a string value representing an URI of the link destination.
    /// </summary>
    String getUri()
    {
        URI uri = new URI(MarkdownUtil.unescapeMarkupSymbols(getUnescapedUri()));
        return uri.toString(true);
    }

    /// <summary>
    /// Gets a string value representing a Title of the link destination.
    /// </summary>
    String getTitle()
    {
        // Remove Uri string from the destination text.
        String text = super.getText();
        if (isValidSvgDataUrl(text))
            return "";
        String uri = getUnescapedUri();
        if (uri.length() == 0)
            return "";
        int uriPos = msString.indexOf(text, uri, StringComparison.ORDINAL);
        if (uriPos < 0)
            return "";
        text = text.substring(uriPos + uri.length() - 1);

        int endIndex = text.length() - 1;
        // Skip leading whitespace characters in Uri.
        int uriStart = MarkdownUtil.indexOfNonWhitespace(text, 0, endIndex);
        // In case of Uri is empty, the title is empty too.
        if (uriStart == -1)
            return "";

        int uriEnd = MarkdownUtil.indexOfWhitespace(text, uriStart, endIndex) - 1;
        // There can be only Uri without Title in this destination.
        if (uriEnd < 0)
            return "";

        int titleStart = MarkdownUtil.indexOfNonWhitespace(text, uriEnd + 1, endIndex);
        // The title can be empty.
        if (titleStart == -1)
            return "";

        // Skip whitespace characters from the end.
        while (MarkdownUtil.isWhitespaceCharacter(text.charAt(endIndex)))
            endIndex--;

        // Valid title is always inside surrounding opening and closing delimiters `"`, `'`, `(`.
        // Remove them to get `pure` text of title.
        String title = text.substring((titleStart + 1), (titleStart + 1) + (endIndex - titleStart - 1));

        // WORDSNET-18341 Line breaks are allowed in link text. Preserve it by
        // replacing with LineBreak, otherwise it will be removed in Block.Write().
        title = msString.replace(title, MarkdownUtil.SOFT_LINE_BREAK_CHAR, ControlChar.LINE_BREAK_CHAR);

        return msString.trim(MarkdownUtil.unescapeQuotesAndParentheses(title));
    }

    /// <summary>
    /// Opening delimiter for LinkTextBlock.
    /// </summary>
    static final char OPENING_DELIMITER = LinkDestinationOpeningDelimiter.CHARACTER;

    /// <summary>
    /// Closing delimiter for LinkTextBlock.
    /// </summary>
    static final char CLOSING_DELIMITER = LinkDestinationClosingDelimiter.CHARACTER;
}

