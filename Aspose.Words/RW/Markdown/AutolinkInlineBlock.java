// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a markdown AutolinkInline block.
/// </summary>
class AutolinkInlineBlock extends ContainerBlock
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
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        context.open(this);
        context.getBuilder().insertHyperlink(super.getText(), getUrl(), false);
        context.close(this);
    }

    /// <summary>
    /// Returns true, if a text between specified delimiters is valid AutoLink.
    /// </summary>
    static boolean isValid(Delimiter opening, Delimiter closing)
    {
        Debug.assertTrue((opening != null) && (closing != null));
        Debug.assertTrue(opening.isBefore(closing));

        return isValid(opening.getText(), opening.getEnd() + 1, closing.getStart() - 1);
    }

    /// <summary>
    /// Returns true, if a specified text is valid AutoLink.
    /// </summary>
    static boolean isValid(String text, int startIndex, int endIndex)
    {
        return (isValidUri(text, startIndex, endIndex) || isValidEmail(text, startIndex, endIndex));
    }

    /// <summary>
    /// Returns true, if a specified string is valid URI.
    /// </summary>
    private static boolean isValidUri(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue((startIndex >= 0) && (startIndex < text.length()));
        Debug.assertTrue((endIndex >= 0) && (endIndex < text.length()));

        int length = endIndex - startIndex + 1;

        int schemeSeparatorIdx = msString.indexOf(text, COLON, startIndex, length);
        // There must be a scheme separator.
        if (schemeSeparatorIdx == -1)
            return false;

        if (!isValidScheme(text, startIndex, schemeSeparatorIdx - 1))
            return false;

        // Check URI characters.
        for (int i = schemeSeparatorIdx + 1; i <= endIndex; i++)
        {
            char c = text.charAt(i);
            if (MarkdownUtil.isAsciiControlChar(c))
                return false;

            if (c == MarkdownUtil.SPACE_CHAR)
                return false;

            if (c == MarkdownUtil.SOFT_LINE_BREAK_CHAR)
                return false;

            if ((c == '<') || (c == '>'))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns true, if a specified text is valid email address.
    /// </summary>
    private static boolean isValidEmail(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue((startIndex >= 0) && (startIndex < text.length()));
        Debug.assertTrue((endIndex >= 0) && (endIndex < text.length()));

        int length = endIndex - startIndex + 1;

        // There must be at least @.
        if (length < 1)
            return false;

        char lastDomainChar = text.charAt(endIndex);
        // The domain cannot be empty.
        if ((lastDomainChar == COMMERCIAL_AT) || (lastDomainChar == DOT))
            return false;

        // Find end of the local-part of the email address.
        int localPartEndIdx = msString.indexOf(text, COMMERCIAL_AT, startIndex, length);
        if (localPartEndIdx == -1)
            return false;

        // Verify local-part of the email address.
        if (!isValidEmailLocalPart(text, startIndex, localPartEndIdx - 1))
            return false;

        // Verify all domains of the email address.
        int domainStartIdx = localPartEndIdx + 1;
        while (domainStartIdx <= endIndex)
        {
            length = endIndex - domainStartIdx + 1;
            // The end is either position of the next dot or the position just after text length.
            int domainEndIdx = msString.indexOf(text, DOT, domainStartIdx, length);
            if (domainEndIdx == -1)
                domainEndIdx = endIndex;
            else
                domainEndIdx--;

            // Verify email domain.
            if (!isValidEmailDomain(text, domainStartIdx, domainEndIdx))
                return false;

            // Advance to the position just after the Dot separator.
            domainStartIdx = domainEndIdx + 2;
        }

        return true;
    }

    /// <summary>
    /// Returns true, if a specified string is valid scheme of the link.
    /// </summary>
    /// <remarks>
    /// ^[a-zA-Z][a-zA-Z0-9.+-]+
    /// </remarks>
    private static boolean isValidScheme(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue((startIndex >= 0) && (startIndex < text.length()));
        Debug.assertTrue((endIndex >= 0) && (endIndex < text.length()));

        final int MIN_SCHEME_LENGTH = 2;
        final int MAX_SCHEME_LENGTH = 32;

        int length = endIndex - startIndex + 1;

        // Check schema length is valid.
        if ((length < MIN_SCHEME_LENGTH) || (length > MAX_SCHEME_LENGTH))
            return false;

        // The scheme must be started from a letter.
        if (!StringUtil.isLetter(text.charAt(startIndex)))
            return false;

        for (int i = startIndex + 1; i <= endIndex; i++)
        {
            if (!StringUtil.isLetterOrDigit(text.charAt(i)) &&
                !ArrayUtil.findCharInArray(G_ALLOWED_SPECIAL_SCHEME_CHARS, text.charAt(i)))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns true, if a specified text is valid local-part of email address.
    /// </summary>
    private static boolean isValidEmailLocalPart(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue((startIndex >= 0) && (startIndex < text.length()));
        Debug.assertTrue((endIndex >= 0) && (endIndex < text.length()));

        int length = endIndex - startIndex + 1;

        // Email local part can be empty.
        if (length < 1)
            return true;

        // The first character cannot be Dot.
        if (text.charAt(startIndex) == DOT)
            return false;

        for (int i = startIndex; i <= endIndex; i++)
        {
            char c = text.charAt(i);
            // These are only the allowed characters.
            if (!StringUtil.isLetterOrDigit(c) && !ArrayUtil.findCharInArray(G_ALLOWED_SPECIAL_EMAIL_CHARS, c))
                return false;

            // Dots cannot appear consecutively.
            if ((c == DOT) && (text.charAt(i - 1) == DOT))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Returns true, if a specified text is valid email domain.
    /// </summary>
    /// <remarks>
    /// It should be limited to a length of 63 characters and consisting of:
    /// - uppercase and lowercase Latin letters A to Z and a to z;
    /// - digits 0 to 9, provided that top-level domain names are not all-numeric;
    /// - hyphen -, provided that it is not the first or last character.
    /// This is so-called LDH rule (letters, digits, hyphen).
    /// </remarks>
    private static boolean isValidEmailDomain(String text, int startIndex, int endIndex)
    {
        Debug.assertTrue((startIndex >= 0) && (startIndex < text.length()));
        Debug.assertTrue((endIndex >= 0) && (endIndex < text.length()));

        final int MIN_DOMAIN_LENGTH = 1;
        final int MAX_DOMAIN_LENGTH = 63;

        int length = endIndex - startIndex + 1;
        if ((length < MIN_DOMAIN_LENGTH) || (length > MAX_DOMAIN_LENGTH))
            return false;

        // The first character must be letter or digit.
        if (!StringUtil.isLetterOrDigit(text.charAt(startIndex)))
            return false;

        // The very last character cannot be a hyphen.
        if (text.charAt(endIndex) == HYPHEN)
            return false;

        // The inner characters must be letters, digits or hyphen.
        for (int i = startIndex + 1; i <= endIndex; i++)
        {
            if (!StringUtil.isLetterOrDigit(text.charAt(i)) && (text.charAt(i) != HYPHEN))
                return false;
        }

        return true;
    }

    /// <summary>
    /// A type of the block.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.AUTOLINK; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets text of the block.
    /// </summary>
    /*override*/ String getText() { return msString.format("{0}{1}{2}", OPENING_DELIMITER, super.getText(), CLOSING_DELIMITER); }

    /// <summary>
    /// Gets a string value representing URL of the autolink.
    /// </summary>
    private String getUrl() { return isEmail() ? msString.format("mailto:{0}", super.getText()) : super.getText(); }

    /// <summary>
    /// Gets a boolean value indicating this is email autolink.
    /// </summary>
    private boolean isEmail()
    {
        boolean hasCommercialAt = false;
        for (int i = 0; i < super.getText().length(); i++)
        {
        	char c = super.getText().charAt(i);
            switch (c)
            {
                case COMMERCIAL_AT:
                    hasCommercialAt = true;
                    break;
                case COLON:
                    return false;
                default:
                    break;
            }
        }
        return hasCommercialAt;
    }

    /// <summary>
    /// Opening delimiter for AutolinkInlineBlock.
    /// </summary>
    static final char OPENING_DELIMITER = AutoLinkOpeningDelimiter.CHARACTER;

    /// <summary>
    /// Closing delimiter for AutolinkInlineBlock.
    /// </summary>
    static final char CLOSING_DELIMITER = AutoLinkClosingDelimiter.CHARACTER;

    /// <summary>
    /// Allowed email address special characters.
    /// </summary>
    private static /*final*/ char[] G_ALLOWED_SPECIAL_EMAIL_CHARS = {'.', '+', '-', '!', '#', '$', '%', '&', '\'', '*',
        '/', '=', '?', '^', '_', '`', '{' , '|', '}', '~' };

    /// <summary>
    /// Allowed URI scheme special characters.
    /// </summary>
    private static /*final*/ char[] G_ALLOWED_SPECIAL_SCHEME_CHARS = {'.', '+', '-'};

    /// <summary>Commercial "At" character.</summary>
    private static final char COMMERCIAL_AT = '@';
    /// <summary>Colon character.</summary>
    private static final char COLON = ':';
    /// <summary>Dot character.</summary>
    private static final char DOT = '.';
    /// <summary>Hyphen character.</summary>
    private static final char HYPHEN = '-';
}

