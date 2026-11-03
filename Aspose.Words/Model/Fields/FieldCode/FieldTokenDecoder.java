// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Unescapes a field token and optionally trims double quotes if present.
/// Field tokens are stored as is, i.e. with escaping backslashes and optionally double quoted.
/// When accessing the tokens, we should get rid of them by replacing the escaping backslashes and
/// double quotes at the beginning and end.
/// </summary>
class FieldTokenDecoder
{
    /// <summary>
    /// Decodes the specified token represented by a string.
    /// </summary>
    /// <remarks>
    /// The <see cref="DecodeToken(string,NodeRange,bool)"/> and <see cref="DecodeToken(RichString,NodeRange,bool)"/> methods must be identical.
    /// </remarks>
    static String decodeToken(String token, NodeRange tokenRange, boolean trimDoubleQuotes)
    {
        // If an argument range ends with a field end then it is considered to be a field result range,
        // that is its content should not be modified (i.e. escaped, etc.).
        if (FieldUtil.endsWithFieldEnd(tokenRange))
            return token;

        /*FieldTokenDecoderOptions*/int options = FieldTokenDecoderOptions.ESCAPE_CHARS;
        options = FieldTokenDecoderOptionsUtil.withTrimDoubleQuotes(options, trimDoubleQuotes);
        FieldTokenDecoder decoder = new FieldTokenDecoder(options);

        return decoder.decodeTokenPart(token, false, false);
    }

    /// <summary>
    /// Decodes the specified token represented by a string.
    /// </summary>
    /// <remarks>
    /// The <see cref="DecodeToken(string,NodeRange,bool)"/> and <see cref="DecodeToken(RichString,NodeRange,bool)"/> methods must be identical.
    /// </remarks>
    static RichString decodeToken(RichString token, NodeRange tokenRange, boolean trimDoubleQuotes)
    {
        // If an argument range ends with a field end then it is considered to be a field result range,
        // that is its content should not be modified (i.e. escaped, etc.).
        if (FieldUtil.endsWithFieldEnd(tokenRange))
            return token;

        /*FieldTokenDecoderOptions*/int options = FieldTokenDecoderOptions.ESCAPE_CHARS;
        options = FieldTokenDecoderOptionsUtil.withTrimDoubleQuotes(options, trimDoubleQuotes);
        FieldTokenDecoder decoder = new FieldTokenDecoder(options);

        return decoder.decodeTokenPart(token, false, false);
    }

    FieldTokenDecoder(/*FieldTokenDecoderOptions*/int fieldTokenDecoderOptions)
    {
        mFieldTokenDecoderOptions = fieldTokenDecoderOptions;
    }

    /// <summary>
    /// Unescapes the specified string and also trims double quotes if instructed.
    /// </summary>
    /// <remarks>
    /// The <see cref="DecodeTokenPart(string,bool,bool)"/> and <see cref="DecodeTokenPart(RichString,bool,bool)"/> methods must be identical.
    /// </remarks>
    String decodeTokenPart(String tokenPart, boolean isInField, boolean isSymbolicFont)
    {
        StringBuilder builder = null;

        int tokenPartLength = tokenPart.length();

        for (int i = 0; i < tokenPartLength; i++)
        {
            char c = tokenPart.charAt(i);
            if (c == '\\' && !isInField)
            {
                if (isTokenCharEscaped() || (!getEscapeChars() && hasProcessedContent()))
                    builder = processDecodedTokenChar(builder, c, i);

                isTokenCharEscaped(!isTokenCharEscaped());
            }
            else if (!needTrimDoubleQuote(c, i, tokenPartLength, isInField, isSymbolicFont))
            {
                // Unescaped double quotes may only appear at the beginning or end of the token,
                // so if we encounter them, it is a subject to trim.
                builder = processDecodedTokenChar(builder, c, i);

                isTokenCharEscaped(false);
            }
        }

        return builder != null
            ? builder.toString()
            : "";
    }

    /// <summary>
    /// Unescapes the specified string and also trims double quotes if instructed.
    /// </summary>
    /// <remarks>
    /// The <see cref="DecodeTokenPart(string,bool,bool)"/> and <see cref="DecodeTokenPart(RichString,bool,bool)"/> methods must be identical.
    /// </remarks>
    private RichString decodeTokenPart(RichString tokenPart, boolean isInField, boolean isSymbolicFont)
    {
        RichStringBuilder builder = null;

        int tokenPartLength = tokenPart.getLength();

        for (int i = 0; i < tokenPartLength; i++)
        {
            RichChar c = tokenPart.getInternal(i);
            if (c.toSystemChar() == '\\' && !isInField)
            {
                if (isTokenCharEscaped() || (!getEscapeChars() && hasProcessedContent()))
                    builder = processDecodedTokenChar(builder, c, i);

                isTokenCharEscaped(!isTokenCharEscaped());
            }
            else if (!needTrimDoubleQuote(c.toSystemChar(), i, tokenPartLength, isInField, isSymbolicFont))
            {
                // Unescaped double quotes may only appear at the beginning or end of the token,
                // so if we encounter them, it is a subject to trim.
                builder = processDecodedTokenChar(builder, c, i);

                isTokenCharEscaped(false);
            }
        }

        return builder != null
            ? builder.toRichString()
            : RichString.EMPTY;
    }

    private boolean needTrimDoubleQuote(char tokenChar, int tokenCharIndex, int tokenLength, boolean isInField, boolean isSymbolicFont)
    {
        if (isTokenCharEscaped())
            return false;

        if (isInField)
            return false;

        if (isSymbolicFont && getPreserveSymbolicQuotes())
            return false;

        return needTrimDoubleQuote(tokenChar, tokenCharIndex, tokenLength);
    }

    /// <summary>
    /// Processes the specified decoded token char typically by appending it to the current decoded token buffer.
    /// </summary>
    /// <param name="builder">
    /// The current decoded token buffer.
    /// </param>
    /// <param name="c">
    /// The decoded character.
    /// </param>
    /// <param name="positionInTokenPart">
    /// The position of the decoded character in the token part, i.e. the text of the corresponding run.</param>
    /// <returns>
    /// The current decoded token buffer. Typically, if the input value is null, it is initialized within the method
    /// and the output value is a new instance of <see cref="IStringBuilder"/> class.
    /// </returns>
    /// <remarks>
    /// The <see cref="ProcessDecodedTokenChar(StringBuilder,char,int)"/> and <see cref="ProcessDecodedTokenChar(RichStringBuilder,RichChar,int)"/> methods must be identical.
    /// </remarks>
    protected /*virtual*/ StringBuilder processDecodedTokenChar(StringBuilder builder, char c, int positionInTokenPart)
    {
        // Create on the first demand.
        if (builder == null)
            builder = new StringBuilder();

        onContentProcessed();

        return builder.append(c);
    }

    /// <summary>
    /// Processes the specified decoded token char typically by appending it to the current decoded token buffer.
    /// </summary>
    /// <param name="builder">
    /// The current decoded token buffer.
    /// </param>
    /// <param name="c">
    /// The decoded character.
    /// </param>
    /// <param name="positionInTokenPart">
    /// The position of the decoded character in the token part, i.e. the text of the corresponding run.</param>
    /// <returns>
    /// The current decoded token buffer. Typically, if the input value is null, it is initialized within the method
    /// and the output value is a new instance of <see cref="IStringBuilder"/> class.
    /// </returns>
    /// <remarks>
    /// The <see cref="ProcessDecodedTokenChar(StringBuilder,char,int)"/> and <see cref="ProcessDecodedTokenChar(RichStringBuilder,RichChar,int)"/> methods must be identical.
    /// </remarks>
    protected /*virtual*/ RichStringBuilder processDecodedTokenChar(RichStringBuilder builder, RichChar c, int positionInTokenPart)
    {
        // Create on the first demand.
        if (builder == null)
            builder = new RichStringBuilder();

        onContentProcessed();

        return builder.appendInternal(c);
    }

    protected void onContentProcessed()
    {
        if (hasProcessedContent())
            return;

        if (isHasProcessedContentLocked())
            return;

        hasProcessedContent(true);
    }

    protected /*virtual*/ boolean isHasProcessedContentLocked() { return false; }

    private boolean needTrimDoubleQuote(char tokenChar, int tokenCharIndex, int tokenLength)
    {
        if (!getTrimDoubleQuotes())
            return false;

        if (tokenCharIndex != 0 && tokenCharIndex != tokenLength - 1)
            return false;

        return FieldCodeParser.isDoubleQuote(tokenChar);
    }

    /// <summary>
    /// Gets a value indicating whether the current processed character is escaped.
    /// </summary>
    /// <remarks>
    /// The returned value is always <c>false</c> if the token char belongs to a run inside a nested field.
    /// </remarks>
    protected boolean isTokenCharEscaped() { return mIsTokenCharEscaped; }; protected void isTokenCharEscaped(boolean value) { mIsTokenCharEscaped = value; };

    private boolean mIsTokenCharEscaped;

    protected boolean hasProcessedContent() { return mHasProcessedContent; }; protected void hasProcessedContent(boolean value) { mHasProcessedContent = value; };

    private boolean mHasProcessedContent;

    private boolean getPreserveSymbolicQuotes() { return FieldTokenDecoderOptionsUtil.hasPreserveSymbolicQuotes(mFieldTokenDecoderOptions); }

    private boolean getTrimDoubleQuotes() { return FieldTokenDecoderOptionsUtil.hasTrimDoubleQuotes(mFieldTokenDecoderOptions); }

    private boolean getEscapeChars() { return FieldTokenDecoderOptionsUtil.hasEscapeChars(mFieldTokenDecoderOptions); }

    private /*final*/ /*FieldTokenDecoderOptions*/int mFieldTokenDecoderOptions;
}

