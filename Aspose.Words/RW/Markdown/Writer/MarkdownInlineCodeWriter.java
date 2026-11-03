// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// The class responsible for writing InlineCode into Markdown.
/// </summary>
class MarkdownInlineCodeWriter
{
    /// <summary>
    /// Initializes a new instance with the specified content lines.
    /// </summary>
    MarkdownInlineCodeWriter(TxtContentLines contentLines, IInline src)
    {
        Debug.assertTrue(contentLines != null);

        mContentLines = contentLines;
        mIsEmphasized = (src != null) &&
                        InlineHelper.getCharacterStyle(src).getName().contains(MarkdownUtil.INLINE_CODE_STYLE_NAME);
    }

    /// <summary>
    /// Opens the InlineCode.
    /// </summary>
    void open()
    {
        // If text ends with the same character as InlineCode delimiter, then we need to escape this character
        // with space to avoid it to be a part of the opening sequence of the InlineCode delimiter.
        writeEscaping();

        // If current content line consists of the only whitespace characters,
        // we need to preserve them in Markdown by moving inside the InlineCode.
        int position = StringUtil.containsOnlyWhitespaces(getCurrentLine())
            ? 0
            : getCurrentLine().length();
        getCurrentLine().insert(position, getDelimiter());
        isOpened(true);
    }

    /// <summary>
    /// Closes the InlineCode.
    /// </summary>
    void close()
    {
        // If text ends with the same character as InlineCode delimiter, then we need to escape this character
        // with space to avoid it to be a part of the closing sequence of the InlineCode delimiter.
        writeEscaping();
        msStringBuilder.append(getCurrentLine(), getDelimiter());

        setState(MarkdownEmphasisWriterState.NONE);
        isOpened(false);
    }

    /// <summary>
    /// Updates state of the writer in accordance with a specified inline.
    /// </summary>
    void updateState(IInline inline)
    {
        String styleName = (inline != null) ? InlineHelper.getCharacterStyle(inline).getName() : "";

        boolean isEmphasized = styleName.contains(MarkdownUtil.INLINE_CODE_STYLE_NAME);
        if (mIsEmphasized == isEmphasized)
            setState(MarkdownEmphasisWriterState.NONE);
        else
            setState((isEmphasized) ? MarkdownEmphasisWriterState.OPENING : MarkdownEmphasisWriterState.CLOSING);

        // If a new InlineCode is starting, then we need to update writer's delimiter
        // with a new number of delimiter characters.
        if (getState() == MarkdownEmphasisWriterState.OPENING)
            updateDelimiter(styleName);

        mIsEmphasized = isEmphasized;
    }

    /// <summary>
    /// Updates delimiter string in accordance with a specified style name.
    /// </summary>
    private void updateDelimiter(String styleName)
    {
        int delimiterLength = (!msString.equals(styleName, ""))
            ? Math.max(MarkdownUtil.getNumberAfterSubstring(styleName, MarkdownUtil.INLINE_CODE_STYLE_NAME), 1)
            : 1;
        mDelimiter = msString.newString(InlineCodeDelimiter.CHARACTER, delimiterLength);
    }

    /// <summary>
    /// Escapes delimiter with the same adjacent character in text.
    /// </summary>
    private void writeEscaping()
    {
        if ((getCurrentLine().length() > 0) && (getCurrentLine().charAt(getCurrentLine().length() - 1) == getDelimiter().charAt(0)))
            msStringBuilder.append(getCurrentLine(), " ");
    }

    /// <summary>
    /// The current state of the writer.
    /// </summary>
    /*MarkdownEmphasisWriterState*/int getState() { return mState; }; private void setState(/*MarkdownEmphasisWriterState*/int value) { mState = value; };

    private /*MarkdownEmphasisWriterState*/int mState;

    /// <summary>
    /// Gets a boolean value indicating either the InlineCode is opened.
    /// </summary>
    boolean isOpened() { return mIsOpened; }; private void isOpened(boolean value) { mIsOpened = value; };

    private boolean mIsOpened;

    /// <summary>
    /// Gets or sets a string representing delimiter of the emphasis.
    /// </summary>
    private String getDelimiter()
    {
        return StringUtil.hasChars(mDelimiter)
            ? mDelimiter
            : Character.toString(InlineCodeDelimiter.CHARACTER);
    }

    /// <summary>
    /// Gets current line to write into.
    /// </summary>
    private StringBuilder getCurrentLine() { return mContentLines.getCurrentLine(); }

    /// <summary>
    /// The content lines to write into.
    /// </summary>
    private /*final*/ TxtContentLines mContentLines;

    /// <summary>
    /// A string value, representing InlineCode delimiter.
    /// </summary>
    private String mDelimiter;

    /// <summary>
    /// A boolean value indicating either last processed run was emphasized (either it has a style with
    /// a corresponding name).
    /// </summary>
    private boolean mIsEmphasized;
}

