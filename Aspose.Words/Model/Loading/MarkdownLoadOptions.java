// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify additional options when loading <see cref="LoadFormat.Markdown"/> document into a <see cref="Document"/> object.
/// </summary>
/// <dev>
/// We do not inherit this class from <see cref="TxtLoadOptions"/>, as for the moment there is a number of properties,
/// such as <see cref="TxtLoadOptions.DetectNumberingWithWhitespaces"/>, <see cref="TxtLoadOptions.DetectHyperlinks"/>
/// and some others that we do not want to have in <see cref="MarkdownLoadOptions"/> class.
/// </dev>
public class MarkdownLoadOptions extends LoadOptions
{
    /// <summary>
    /// Initializes a new instance of <see cref="MarkdownLoadOptions"/> class.
    /// </summary>
    /// <remarks>
    /// Automatically sets <see cref="LoadFormat"/> to <see cref="LoadFormat.Markdown"/>.
    /// </remarks>
    public MarkdownLoadOptions()
    {
        setLoadFormat(LoadFormat.MARKDOWN);
    }

    /// <summary>
    /// Initializes a new instance of <see cref="MarkdownLoadOptions"/> class.
    /// </summary>
    /// <remarks>
    /// Automatically sets <see cref="LoadFormat"/> to <see cref="LoadFormat.Markdown"/>.
    /// </remarks>
    MarkdownLoadOptions(LoadOptions loadOptions)
    {
    	super(loadOptions);
	
        setLoadFormat(LoadFormat.MARKDOWN);
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether to preserve empty lines while load a <see cref="LoadFormat.Markdown"/> document.
    /// The default value is <c>false</c>.
    /// <para>
    /// Normally, empty lines between block-level elements in Markdown are ignored. Empty lines at the beginning and
    /// end of the document are also ignored. This option allows to import such empty lines.
    /// </para>
    /// </summary>
    public boolean getPreserveEmptyLines() { return mPreserveEmptyLines; }; public void setPreserveEmptyLines(boolean value) { mPreserveEmptyLines = value; };

    private boolean mPreserveEmptyLines;

    /// <summary>
    /// Gets or sets a boolean value indicating either to recognize a sequence
    /// of two plus characters "++" as underline text formatting.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getImportUnderlineFormatting() { return mImportUnderlineFormatting; }; public void setImportUnderlineFormatting(boolean value) { mImportUnderlineFormatting = value; };

    private boolean mImportUnderlineFormatting;

    /// <summary>
    /// Gets or sets a character value representing `soft line break`.
    /// The default value is <c>SPACE (U+0020)</c>.
    /// </summary>
    /// <remarks>
    /// Note, setting this option to <see cref="ControlChar.LineBreakChar"/> allows you
    /// to load soft line breaks as hard line breaks.
    /// </remarks>
    public char getSoftLineBreakCharacter() { return mSoftLineBreakCharacter; }
    public void setSoftLineBreakCharacter(char value) { mSoftLineBreakCharacter = value; }

    /// <summary>
    /// Represents a default <see cref="SoftLineBreakCharacter"/>.
    /// </summary>
    static final char DEFAULT_SOFT_LINE_BREAK_CHARACTER = ControlChar.SPACE_CHAR;

    private char mSoftLineBreakCharacter = DEFAULT_SOFT_LINE_BREAK_CHARACTER;
}

