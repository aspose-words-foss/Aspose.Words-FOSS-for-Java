// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// When implemented, represents a tokenizer that enumerates over field code tokens.
/// </summary>
interface IFieldCodeTokenizer
{
    /// <summary>
    /// Proceeds to the next field code token, such as a text character, child field's start or end, etc.
    /// </summary>
    /// <returns>True if successful, false if passed the last field code token.</returns>
    public boolean moveToNextToken();

    /// <summary>
    /// Returns a document position that corresponds to the current position of the tokenizer.
    /// </summary>
    /// <returns>A document position that corresponds to the current position of the tokenizer.</returns>
    public DocumentPosition getCurrentPosition();

    /// <summary>
    /// Gets the current field code token.
    /// </summary>
    public /*FieldCodeToken*/int getCurrentToken();

    /// <summary>
    /// Gets whether the current position is at the end of the current token.
    /// </summary>
    public boolean isEndOfToken();

    /// <summary>
    /// Gets the current field char.
    /// </summary>
    /// <remarks>
    /// Only makes sense if <see cref="CurrentToken"/> is <see cref="FieldCodeToken.TextChar"/>.
    /// </remarks>
    public char getCurrentChar();

    /// <summary>
    /// Gets the current field node.
    /// </summary>
    /// <remarks>
    /// It should not be used if <see cref="CurrentToken"/> is <see cref="FieldCodeToken.TextChar"/>.
    /// </remarks>
    public Node getCurrentNode();

    /// <summary>
    /// A boolean value indicating if the tokenizer reached the end of data;
    /// </summary>
    public boolean isEof();

    /// <summary>
    /// Locale ID specified via formatting properties of the current Inline node.
    /// </summary>
    /// <remarks>
    /// If the current node is not defined or is not Inline, <see cref="RunPr.ProcessOrUserDefaultLanguageId"/> is returned.
    /// </remarks>
    public int getCurrentLocaleId();

    /// <summary>
    /// Locale ID of the East Asian language specified via formatting properties of the current Inline node.
    /// </summary>
    /// <remarks>
    /// If the current node is not defined or is not Inline, <see cref="RunPr.ProcessOrUserDefaultLanguageId"/> is returned.
    /// </remarks>
    public int getCurrentLocaleIdFarEast();

    /// <summary>
    /// Locale ID of the RTL specified via formatting properties of the current Inline node.
    /// </summary>
    /// <remarks>
    /// If the current node is not defined or is not Inline, <see cref="RunPr.ProcessOrUserDefaultLanguageId"/> is returned.
    /// </remarks>
    public int getCurrentLocaleIdBi();

    /// <summary>
    /// A boolean value indicating if RTL is specified via formatting properties of the current Inline node.
    /// </summary>
    public boolean getCurrentBidi();

    /// <summary>
    /// A font name specified via formatting properties of the current Inline node.
    /// </summary>
    /// <remarks>
    /// If the current node is not defined or is not Inline, <c>null</c> is returned.
    /// </remarks>
    public String getCurrentFontName();
}

