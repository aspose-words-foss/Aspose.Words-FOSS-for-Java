// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.UnicodeUtil;


/// <summary>
/// CSS tokenizer. Splits CSS text into CSS tokens.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-syntax-3/ and the latest editor's draft at https://drafts.csswg.org/css-syntax/
/// </remarks>
class CssTokenizer
{
    /// <summary>
    /// Returns all CSS tokens of the provided CSS text.
    /// </summary>
    static ArrayList<CssToken> getTokens(String css)
    {
        ArrayList<CssToken> result = new ArrayList<CssToken>();
        CssTokenizer tokenizer = new CssTokenizer(css);
        while (tokenizer.moveNext())
        {
            msArrayList.add(result, tokenizer.getCurrentToken());
        }
        return result;
    }

    /// <summary>
    /// Constructor. Initializes a new instance of the tokenizer to process the provided CSS text.
    /// </summary>
    /// <param name="css">CSS text to split into tokens.</param>
    CssTokenizer(String css)
    {
        mStream = new CssTokenizerStream(css);
    }

    /// <summary>
    /// Advances the tokenizer by one token.
    /// </summary>
    /// <returns>
    /// A value indicating whether another token has been parsed to <see cref="CurrentToken"/>.
    /// <c>true</c> - parsed another token. <c>false</c> - no more tokens, reached the end of the CSS text.
    /// </returns>
    boolean moveNext()
    {
        setCurrentToken(CssToken.EOF);

        if (consumeWhitespaceWithOptionalComments())
        {
            setCurrentToken(G_WHITESPACE);
            return true;
        }

        if (mStream.isAtEof())
        {
            return false;
        }

        mStream.consumeChar();

        if (isNameStartCodePoint(mStream.getCurrent()))
        {
            mStream.reconsumeChar();
            setCurrentToken(consumeIdentLikeToken());
            return true;
        }

        if (StringUtil.isDigit(mStream.getCurrent()))
        {
            mStream.reconsumeChar();
            setCurrentToken(consumeNumericToken());
            return true;
        }

        switch (mStream.getCurrent())
        {
            case '"':
            case '\'':
            {
                setCurrentToken(consumeStringToken());
                break;
            }
            case '#':
            {
                setCurrentToken(consumeHashToken());
                break;
            }
            case '(':
            {
                setCurrentToken(G_ROUND_BRACKET_LEFT);
                break;
            }
            case ')':
            {
                setCurrentToken(G_ROUND_BRACKET_RIGHT);
                break;
            }
            case '+':
            {
                if (isNumber(mStream.getCurrent(), mStream.getNext(), mStream.getNext2()))
                {
                    mStream.reconsumeChar();
                    setCurrentToken(consumeNumericToken());
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case ',':
            {
                setCurrentToken(G_COMMA);
                break;
            }
            case '-':
            {
                if (isNumber(mStream.getCurrent(), mStream.getNext(), mStream.getNext2()))
                {
                    mStream.reconsumeChar();
                    setCurrentToken(consumeNumericToken());
                }
                else if (isIdentifier(mStream.getCurrent(), mStream.getNext(), mStream.getNext2()))
                {
                    mStream.reconsumeChar();
                    setCurrentToken(consumeIdentLikeToken());
                }
                else if ((mStream.getNext() == '-') && (mStream.getNext2() == '>'))
                {
                    mStream.consumeChar(); // '-'
                    mStream.consumeChar(); // '>'
                    setCurrentToken(G_CDC);
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case '.':
            {
                if (isNumber(mStream.getCurrent(), mStream.getNext(), mStream.getNext2()))
                {
                    mStream.reconsumeChar();
                    setCurrentToken(consumeNumericToken());
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case ':':
            {
                setCurrentToken(G_COLON);
                break;
            }
            case ';':
            {
                setCurrentToken(G_SEMICOLON);
                break;
            }
            case '<':
            {
                if ((mStream.getNext() == '!') && (mStream.getNext2() == '-') && (mStream.getNext3() == '-'))
                {
                    mStream.consumeChar(); // '!'
                    mStream.consumeChar(); // '-'
                    mStream.consumeChar(); // '-'
                    setCurrentToken(G_CDO);
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case '@':
            {
                if (isIdentifier(mStream.getNext(), mStream.getNext2(), mStream.getNext3()))
                {
                    String name = consumeName();
                    setCurrentToken(new CssTextToken(CssTokenType.AT_KEYWORD, name));
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case '[':
            {
                setCurrentToken(G_SQUARE_BRACKET_LEFT);
                break;
            }
            case ']':
            {
                setCurrentToken(G_SQUARE_BRACKET_RIGHT);
                break;
            }
            case '\\':
            {
                if (isValidEscape(mStream.getCurrent(), mStream.getNext()))
                {
                    mStream.reconsumeChar();
                    setCurrentToken(consumeIdentLikeToken());
                }
                else
                {
                    setCurrentToken(createDelimToken(mStream.getCurrent()));
                }
                break;
            }
            case '{':
            {
                setCurrentToken(G_BLOCK_BRACKET_LEFT);
                break;
            }
            case '}':
            {
                setCurrentToken(G_BLOCK_BRACKET_RIGHT);
                break;
            }
            default:
            {
                setCurrentToken(createDelimToken(mStream.getCurrent()));
                break;
            }
        }

        return true;
    }

    /// <summary>
    /// The last parsed CSS token or <see cref="CssToken.Eof"/> if nothing has been parsed.
    /// </summary>
    CssToken getCurrentToken() { return mCurrentToken; }; private void setCurrentToken(CssToken value) { mCurrentToken = value; };

    private CssToken mCurrentToken;

    private CssToken consumeIdentLikeToken()
    {
        String name = consumeName();

        if (mStream.getNext() == '(')
        {
            if ("url".equals(StringUtil.asciiLowerCase(name)))
            {
                mStream.consumeChar();
                return consumeUrl();
            }
            else
            {
                mStream.consumeChar();
                return new CssTextToken(CssTokenType.FUNCTION, name);
            }
        }
        return new CssTextToken(CssTokenType.IDENT, name);
    }

    private CssToken consumeUrl()
    {
        consumeWhitespace();

        if (mStream.isAtEof())
        {
            return new CssTextToken(CssTokenType.URL, "");
        }

        if ((mStream.getNext() == '"') || (mStream.getNext() == '\''))
        {
            mStream.consumeChar();
            CssToken urlStringToken = consumeStringToken();
            if (urlStringToken.getType() == CssTokenType.BAD_STRING)
            {
                return consumeBadUrl();
            }

            consumeWhitespace();

            boolean endedNormally = mStream.getNext() == ')';
            if (endedNormally || mStream.isAtEof())
            {
                if (endedNormally)
                {
                    mStream.consumeChar();
                }
                return new CssTextToken(CssTokenType.URL, ((CssTextToken)urlStringToken).getText());
            }

            return consumeBadUrl();
        }

        mTextBuffer.setLength(0);
        while (true)
        {
            mStream.consumeChar();

            if (isWhitespace(mStream.getCurrent()))
            {
                consumeWhitespace();

                boolean endedNormally = mStream.getNext() == ')';
                if (endedNormally || mStream.isAtEof())
                {
                    if (endedNormally)
                    {
                        mStream.consumeChar();
                    }
                    return new CssTextToken(CssTokenType.URL, mTextBuffer.toString());
                }

                return consumeBadUrl();
            }

            if (isNonPrintable(mStream.getCurrent()))
            {
                return consumeBadUrl();
            }

            switch (mStream.getCurrent())
            {
                case ')':
                case CssTokenizerStream.EOF_CHAR:
                {
                    return new CssTextToken(CssTokenType.URL, mTextBuffer.toString());
                }
                case '"':
                case '\'':
                case '(':
                {
                    return consumeBadUrl();
                }
                case '\\':
                {
                    if (isValidEscape(mStream.getCurrent(), mStream.getNext()))
                    {
                        consumeEscapedCodePoint(mTextBuffer);
                    }
                    else
                    {
                        return consumeBadUrl();
                    }
                    break;
                }
                default:
                {
                    mTextBuffer.append(mStream.getCurrent());
                    break;
                }
            }
        }
    }

    private CssToken consumeBadUrl()
    {
        mTextBuffer.setLength(0);
        while (true)
        {
            mStream.consumeChar();
            if ((mStream.getCurrent() == ')') || (mStream.getCurrent() == CssTokenizerStream.EOF_CHAR))
            {
                return G_BAD_URL;
            }
            if (isValidEscape(mStream.getCurrent(), mStream.getNext()))
            {
                consumeEscapedCodePoint(mTextBuffer);
            }
        }
    }

    private CssToken consumeHashToken()
    {
        if (isNameCodePoint(mStream.getNext()) || isValidEscape(mStream.getNext(), mStream.getNext2()))
        {
            boolean isIdentifier = isIdentifier(mStream.getNext(), mStream.getNext2(), mStream.getNext3());
            return new CssHashToken(consumeName(), isIdentifier);
        }
        return createDelimToken(mStream.getCurrent());
    }

    private CssToken consumeNumericToken()
    {
        boolean isInteger = true;
        boolean isSigned = false;
        boolean isNegative = false;

        mTextBuffer.setLength(0);

        // Optional sign.
        if (mStream.getNext() == '-')
        {
            // This is used to resolve parsing errors and clamp too large and too small values to the valid range.
            isNegative = true;
        }
        if ((mStream.getNext() == '+') || (mStream.getNext() == '-'))
        {
            isSigned = true;
            mStream.consumeChar();
            mTextBuffer.append(mStream.getCurrent());
        }

        // Whole part.
        while (StringUtil.isDigit(mStream.getNext()))
        {
            mStream.consumeChar();
            mTextBuffer.append(mStream.getCurrent());
        }

        // Optional fractional part.
        if ((mStream.getNext() == '.') && StringUtil.isDigit(mStream.getNext2()))
        {
            // It's not an integer. It's a floating-point number.
            isInteger = false;

            mStream.consumeChar();
            mTextBuffer.append(mStream.getCurrent());
            mStream.consumeChar();
            mTextBuffer.append(mStream.getCurrent());

            while (StringUtil.isDigit(mStream.getNext()))
            {
                mStream.consumeChar();
                mTextBuffer.append(mStream.getCurrent());
            }
        }

        // Optional exponent part. For example, "e123" or "E+456".
        boolean hasNegativeExponent = false;
        char a = mStream.getNext();
        char b = mStream.getNext2();
        char c = mStream.getNext3();
        if ((a == 'e') || (a == 'E'))
        {
            if (b == '-')
            {
                // This is used to resolve parsing errors and clamp too large and too small values to the valid range.
                hasNegativeExponent = true;
            }

            int charactersToConsume = 0;
            if (StringUtil.isDigit(b))
            {
                charactersToConsume = 2;
            }
            else if (((b == '+') || (b == '-')) && StringUtil.isDigit(c))
            {
                charactersToConsume = 3;
            }

            // If a valid exponent prefix found.
            if (charactersToConsume > 0)
            {
                // It's not an integer. It's a number in the scientific notation.
                isInteger = false;

                for (int i = 0; i < charactersToConsume; i++)
                {
                    mStream.consumeChar();
                    mTextBuffer.append(mStream.getCurrent());
                }

                while (StringUtil.isDigit(mStream.getNext()))
                {
                    mStream.consumeChar();
                    mTextBuffer.append(mStream.getCurrent());
                }
            }
        }

        // Convert string to number. Take the exponent into consideration.
        double value = FormatterPal.tryParseDoubleInvariant(mTextBuffer.toString());

        //Java parse double differently
        if (Double.isInfinite(value))
        {
            value = isNegative
                    ? -Double.MAX_VALUE
                    : Double.MAX_VALUE;
        }

        if (Double.isNaN(value))
        {
            // We get here if the exponent is too large and the number is either too large or too small.
            if (hasNegativeExponent)
            {
                // The number is too small.
                value = 0.0;
            }
            else
            {
                // The number is too large.
                value = isNegative
                    ? -Double.MAX_VALUE
                    : Double.MAX_VALUE;
            }
        }

        if (isIdentifier(mStream.getNext(), mStream.getNext2(), mStream.getNext3()))
        {
            String unit = consumeName();
            return new CssDimensionToken(value, unit, isInteger, isSigned);
        }

        /*CssTokenType*/int numberTokenType = CssTokenType.NUMBER;
        if (mStream.getNext() == '%')
        {
            mStream.consumeChar();
            numberTokenType = CssTokenType.PERCENTAGE;
        }

        return new CssNumberToken(numberTokenType, value, isInteger, isSigned);
    }

    private static boolean isNumber(char a, char b, char c)
    {
        if (StringUtil.isDigit(a))
        {
            return true;
        }

        switch (a)
        {
            case '+':
            case '-':
                return StringUtil.isDigit(b) || ((b == '.') && StringUtil.isDigit(c));
            case '.':
                return StringUtil.isDigit(b);
            default:
                return false;
        }
    }

    private static boolean isIdentifier(char a, char b, char c)
    {
        if (isNameStartCodePoint(a))
        {
            return true;
        }

        if (a == '-')
        {
            return isNameStartCodePoint(b) || isValidEscape(b, c);
        }

        if (a == '\\')
        {
            return isValidEscape(a, b);
        }

        return false;
    }

    private String consumeName()
    {
        mTextBuffer.setLength(0);
        while (mStream.consumeChar())
        {
            if (isNameCodePoint(mStream.getCurrent()))
            {
                mTextBuffer.append(mStream.getCurrent());
            }
            else if (isValidEscape(mStream.getCurrent(), mStream.getNext()))
            {
                consumeEscapedCodePoint(mTextBuffer);
            }
            else
            {
                mStream.reconsumeChar();
                break;
            }
        }
        return mTextBuffer.toString();
    }

    private static boolean isValidEscape(char a, char b)
    {
        return (a == '\\') && (b != CssTokenizerStream.EOF_CHAR) && (b != '\n');
    }

    private CssToken consumeStringToken()
    {
        mTextBuffer.setLength(0);
        char endingCharacter = mStream.getCurrent();
        while (mStream.consumeChar() && (mStream.getCurrent() != endingCharacter))
        {
            switch (mStream.getCurrent())
            {
                case '\n':
                {
                    mStream.reconsumeChar();
                    return G_BAD_STRING;
                }
                case '\\':
                {
                    if (!mStream.isAtEof())
                    {
                        if (mStream.getNext() == '\n')
                        {
                            mStream.consumeChar();
                        }
                        else
                        {
                            consumeEscapedCodePoint(mTextBuffer);
                        }
                    }
                    break;
                }
                default:
                {
                    mTextBuffer.append(mStream.getCurrent());
                    break;
                }
            }
        }

        return new CssTextToken(CssTokenType.STRING, mTextBuffer.toString());
    }

    private void consumeEscapedCodePoint(StringBuilder stringBuilder)
    {
        if (mStream.isAtEof())
        {
            stringBuilder.append(UnicodeUtil.REPLACEMENT_CHAR);
            return;
        }

        mStream.consumeChar();

        if (StringUtil.isHexDigit(mStream.getCurrent()))
        {
            mHexNumberBuffer.setLength(0);
            mHexNumberBuffer.append(mStream.getCurrent());
            // Consume as many hex digits as possible but no more than max length.
            while ((mHexNumberBuffer.length() < MAX_ESCAPED_CODE_POINT_LENGTH) &&
                StringUtil.isHexDigit(mStream.getNext()))
            {
                mStream.consumeChar();
                mHexNumberBuffer.append(mStream.getCurrent());
            }
            if (isWhitespace(mStream.getNext()))
            {
                mStream.consumeChar();
            }
            parseHexEscapeCode(stringBuilder, mHexNumberBuffer.toString());
        }
        else
        {
            stringBuilder.append(mStream.getCurrent());
        }
    }

    /// <summary>
    /// Consumes optional sequence of whitespace characters.
    /// </summary>
    /// <returns>
    /// A value indicating whether any whitespace has been consumed.
    /// </returns>
    private boolean consumeWhitespace()
    {
        boolean anyWhitespaceConsumed = false;
        while (isWhitespace(mStream.getNext()))
        {
            mStream.consumeChar();
            anyWhitespaceConsumed = true;
        }
        return anyWhitespaceConsumed;
    }

    /// <summary>
    /// Consumes optional whitespace sequences interleaved with optional comments.
    /// </summary>
    /// <returns>
    /// A value indicating whether any whitespace has been consumed.
    /// </returns>
    private boolean consumeWhitespaceWithOptionalComments()
    {
        boolean comsumedMoreWhitespace = true;
        boolean anyWhitespaceConsumed = false;
        while (comsumedMoreWhitespace)
        {
            consumeComments();
            comsumedMoreWhitespace = consumeWhitespace();
            if (comsumedMoreWhitespace)
            {
                anyWhitespaceConsumed = true;
            }
        }
        return anyWhitespaceConsumed;
    }

    /// <summary>
    /// Consumes zero or more consecutive comments: /* ... */
    /// </summary>
    private void consumeComments()
    {
        while ((mStream.getNext() == '/') && (mStream.getNext2() == '*'))
        {
            mStream.consumeChar(); // '/'
            mStream.consumeChar(); // '*'

            // In case the comment is not closed properly we might end up skipping everything up to the EOF.
            while (!mStream.isAtEof())
            {
                mStream.consumeChar(); // This might be the '*' character of the closing sequence "*/".

                if ((mStream.getCurrent() == '*') && (mStream.getNext() == '/'))
                {
                    mStream.consumeChar(); // '/'

                    // Reached the closing sequence "*/" of the comment.
                    break;
                }
            }
        }
    }

    private static boolean isWhitespace(char c)
    {
        return (c == ' ') || (c == '\n') || (c == '\t');
    }

    private static boolean isNameStartCodePoint(char c)
    {
        return StringUtil.isLetter(c) || (c == '_') || (c >= '\u0080');
    }

    private static boolean isNameCodePoint(char c)
    {
        return isNameStartCodePoint(c) || StringUtil.isDigit(c) || (c == '-');
    }

    private static boolean isNonPrintable(char c)
    {
        // Note that zero code points (U+0000 NULL) are not on this list, because they are removed from the source CSS
        // during preprocessing and our tokenizer emits U+0000 NULL at end-of-file.
        return ((c >= '\u0001') && (c <= '\u0008')) ||
            (c == '\u000B') ||
            ((c >= '\u000E') && (c <= '\u001F')) ||
            (c == '\u007F');
    }

    /// <summary>
    /// Parses an escaped character and appends it to the provided string builder.
    /// </summary>
    /// <param name="stringBuilder">A string builder where the parsed character will be appended to.</param>
    /// <param name="escapedCode">The hexadecimal character code without the leading backslash character.</param>
    /// <returns>A character or a group of characters that corresponds to the hexadecimal code. When the escaped character
    /// is invalid in CSS (is zero, surrogate, or out of Unicode range), the replacement character is returned.</returns>
    /// <remarks>
    /// See https://www.w3.org/TR/css-syntax-3/#consume-an-escaped-code-point
    /// </remarks>
    private static void parseHexEscapeCode(
        StringBuilder stringBuilder,
        String escapedCode)
    {
        int codePoint = FormatterPal.parseHex(escapedCode);
        if ((codePoint == 0) || !UnicodeUtil.isValidUtf32(codePoint) || UnicodeUtil.isSurrogate(codePoint))
        {
            // Replace an invalid character.
            stringBuilder.append(UnicodeUtil.REPLACEMENT_CHAR);
        }
        else
        {
            UnicodeUtil.appendUtf32(stringBuilder, codePoint);
        }
    }

    private static CssToken createDelimToken(char c)
    {
        switch (c)
        {
            case '!':
                return G_EXCLAMATION_MARK;
            case '~':
                return G_TILDE;
            case '=':
                return G_EQUALS_SIGN;
            case '>':
                return G_GREATER_THAN_SIGN;
            case '|':
                return G_DASH;
            case '+':
                return G_PLUS;
            case '-':
                return G_MINUS;
            case '*':
                return G_ASTERISK;
            case '/':
                return G_SLASH;
            default:
                return new CssDelimToken(c);
        }
    }

    private /*final*/ CssTokenizerStream mStream;
    private /*final*/ StringBuilder mTextBuffer = new StringBuilder();

    /// <summary>
    /// The max length in hex digits of an escaped code point.
    /// </summary>
    private static final int MAX_ESCAPED_CODE_POINT_LENGTH = 6;

    /// <summary>
    /// A temporary buffer used to parse escaped code points.
    /// </summary>
    private /*final*/ StringBuilder mHexNumberBuffer = new StringBuilder(MAX_ESCAPED_CODE_POINT_LENGTH);

    // We pre-create commonly used tokens to re-use them and reduce memory consumption.
    private static /*final*/ CssToken G_WHITESPACE = new CssToken(CssTokenType.WHITESPACE);
    private static /*final*/ CssToken G_COMMA = new CssToken(CssTokenType.COMMA);
    private static /*final*/ CssToken G_COLON = new CssToken(CssTokenType.COLON);
    private static /*final*/ CssToken G_SEMICOLON = new CssToken(CssTokenType.SEMICOLON);
    private static /*final*/ CssToken G_CDO = new CssToken(CssTokenType.CDO);
    private static /*final*/ CssToken G_CDC = new CssToken(CssTokenType.CDC);
    private static /*final*/ CssToken G_ROUND_BRACKET_LEFT = new CssToken(CssTokenType.ROUND_BRACKET_LEFT);
    private static /*final*/ CssToken G_ROUND_BRACKET_RIGHT = new CssToken(CssTokenType.ROUND_BRACKET_RIGHT);
    private static /*final*/ CssToken G_SQUARE_BRACKET_LEFT = new CssToken(CssTokenType.SQUARE_BRACKET_LEFT);
    private static /*final*/ CssToken G_SQUARE_BRACKET_RIGHT = new CssToken(CssTokenType.SQUARE_BRACKET_RIGHT);
    private static /*final*/ CssToken G_BLOCK_BRACKET_LEFT = new CssToken(CssTokenType.BLOCK_BRACKET_LEFT);
    private static /*final*/ CssToken G_BLOCK_BRACKET_RIGHT = new CssToken(CssTokenType.BLOCK_BRACKET_RIGHT);
    private static /*final*/ CssToken G_BAD_URL = new CssToken(CssTokenType.BAD_URL);
    private static /*final*/ CssToken G_BAD_STRING = new CssToken(CssTokenType.BAD_STRING);
    private static /*final*/ CssToken G_EXCLAMATION_MARK = new CssDelimToken('!');
    private static /*final*/ CssToken G_TILDE = new CssDelimToken('~');
    private static /*final*/ CssToken G_EQUALS_SIGN = new CssDelimToken('=');
    private static /*final*/ CssToken G_GREATER_THAN_SIGN = new CssDelimToken('>');
    private static /*final*/ CssToken G_DASH = new CssDelimToken('|');
    private static /*final*/ CssToken G_PLUS = new CssDelimToken('+');
    private static /*final*/ CssToken G_MINUS = new CssDelimToken('-');
    private static /*final*/ CssToken G_ASTERISK = new CssDelimToken('*');
    private static /*final*/ CssToken G_SLASH = new CssDelimToken('/');
}

