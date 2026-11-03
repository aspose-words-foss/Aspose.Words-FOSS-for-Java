// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;


/// <summary>
/// The base class responsible for writing Markdown emphasis.
/// </summary>
abstract class MarkdownEmphasisWriterBase implements Comparable<MarkdownEmphasisWriterBase>
{
    /// <summary>
    /// Initializes a new instance with specified content lines.
    /// </summary>
    protected MarkdownEmphasisWriterBase(TxtContentLines contentLines)
    {
        mContentLines = contentLines;
    }

    //>>>>>>>> #region  IComparable implementation
    /// <summary>
    /// Compares the current instance with another object of the same type and returns an integer that indicates
    /// whether the current instance precedes, follows, or occurs in the same position in the sort order as the
    /// other object.
    /// </summary>
    /// <returns>
    /// A value that indicates the relative order of the objects being compared. The return value has these meanings:
    /// Value Meaning Less than zero This instance precedes <paramref name="other" /> in the sort order.
    /// Zero This instance occurs in the same position in the sort order as <paramref name="other" />.
    /// Greater than zero This instance follows <paramref name="other" /> in the sort order.
    /// </returns>
    public int compareTo(MarkdownEmphasisWriterBase other)
    {
        if (!msObject.referenceEquals(this, other))
        {
            if (mOpeningLineIndex < other.mOpeningLineIndex)
                return -1;

            if (mOpeningLineIndex > other.mOpeningLineIndex)
                return 1;

            if (mOpeningPosition < other.mOpeningPosition)
                return -1;

            if (mOpeningPosition > other.mOpeningPosition)
                return 1;
        }

        return 0;
    }
    //<<<<<<<< #endregion 

    /// <summary>
    /// Opens the emphasis.
    /// </summary>
    void open()
    {
        // Check if the current line starts with whitespace characters,
        // then wrap it into InlineCode to preserve in exported markdown paragraph.
        if ((getCurrentLine().length() > 0) && StringUtil.containsOnlyWhitespaces(getCurrentLine()))
        {
            getCurrentLine().insert(0, InlineCodeDelimiter.CHARACTER);
            getCurrentLine().append(InlineCodeDelimiter.CHARACTER);
        }

        writeDelimiter();
    }

    /// <summary>
    /// Closes the emphasis.
    /// </summary>
    void close()
    {
        int lineIndex = getCurrentLineIndex();
        StringBuilder line = getCurrentLine();

        // There should not be whitespace characters before closing delimiters,
        // so find the position where to insert.
        int position = StringUtil.lastIndexOfNonWhitespace(line);
        while ((position == -1) && (lineIndex > 0))
        {
            line = mContentLines.get(--lineIndex);
            position = StringUtil.lastIndexOfNonWhitespace(line);
        }
        position++;

        line.insert(position, getCloseDelimiter());
        mOpeningLineIndex = -1;
        setState(MarkdownEmphasisWriterState.NONE);
    }

    /// <summary>
    /// Updates state of the writer in accordance with a specified IRunAttrSource.
    /// </summary>
    void updateState(IRunAttrSource src)
    {
        boolean isEmphasized = getIsEmphasized(src);

        if ((isEmphasized == IsEmphasized) && !isPendingForOpening())
            setState(MarkdownEmphasisWriterState.NONE);
        else
            setState((isEmphasized) ? MarkdownEmphasisWriterState.OPENING : MarkdownEmphasisWriterState.CLOSING);

        IsEmphasized = isEmphasized;
    }

    /// <summary>
    /// Swaps this emphasis with a specified one.
    /// </summary>
    boolean swap(MarkdownEmphasisWriterBase emphasis)
    {
        if (emphasis == null)
            return false;

        if (!(isOpened() && emphasis.isOpened()))
            return false;

        // Note, this operation is safe only for sibling emphases.
        // Otherwise, it can invalidate other inner emphases.
        if (!isSibling(emphasis))
            return false;

        MarkdownEmphasisWriterBase left = (compareTo(emphasis) < 0) ? this : emphasis;
        MarkdownEmphasisWriterBase right = (left == this) ? emphasis : this;

        // The emphases are sibling, so they are located at the same content line.
        StringBuilder line = mContentLines.get(right.mOpeningLineIndex);

        // First replace RIGHT emphasis delimiter.
        line.delete((right.mOpeningPosition), (right.mOpeningPosition) + (right.getDelimiter().length()));
        line.insert(right.mOpeningPosition, left.getDelimiter());

        // Then replace LEFT emphasis delimiter.
        line.delete((left.mOpeningPosition), (left.mOpeningPosition) + (left.getDelimiter().length()));
        line.insert(left.mOpeningPosition, right.getDelimiter());

        // Swap positions within lines.
        int tmp = left.mOpeningPosition;
        left.mOpeningPosition = right.mOpeningPosition;
        right.mOpeningPosition = tmp;

        return true;
    }

    /// <summary>
    /// Switches delimiter string to underscores.
    /// </summary>
    /*virtual*/ boolean switchToUnderscore()
    {
        return false;
    }

    /// <summary>
    /// Returns true, if a specified emphasis and this one have the same delimiter characters.
    /// </summary>
    boolean isSameDelimiterChar(MarkdownEmphasisWriterBase emphasis)
    {
        return (getDelimiter().charAt(0) == emphasis.getDelimiter().charAt(0));
    }

    /// <summary>
    /// Gets string representing this emphasis.
    /// </summary>
    String getString()
    {
        return msString.format("{0}, {1}:{2}", getDelimiter(), getCurrentLineIndex(), getCurrentLine().length());
    }

    /// <summary>
    /// Replaces opening delimiter with a specified string.
    /// </summary>
    protected void replaceOpening(String value)
    {
        if (isOpened())
        {
            StringBuilder line = mContentLines.get(mOpeningLineIndex);
            line.delete((mOpeningPosition), (mOpeningPosition) + (value.length()));
            line.insert(mOpeningPosition, value);
        }
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected abstract boolean getIsEmphasized(IRunAttrSource src);

    /// <summary>
    /// Writes delimiter to the end of the current content line.
    /// </summary>
    private void writeDelimiter()
    {
        mOpeningLineIndex = getCurrentLineIndex();
        mOpeningPosition = getCurrentLine().length();
        msStringBuilder.append(getCurrentLine(), getDelimiter());
    }

    /// <summary>
    /// Returns true, if a specified emphasis is sibling to this one.
    /// </summary>
    private boolean isSibling(MarkdownEmphasisWriterBase emphasis)
    {
        if (mOpeningLineIndex != emphasis.mOpeningLineIndex)
            return false;

        return (mOpeningPosition < emphasis.mOpeningPosition)
            ? ((mOpeningPosition + getDelimiter().length()) == emphasis.mOpeningPosition)
            : ((emphasis.mOpeningPosition + emphasis.getDelimiter().length()) == mOpeningPosition);
    }

    /// <summary>
    /// Gets a boolean value indicating the emphasis is opened (has only opening delimiter written).
    /// </summary>
    boolean isOpened() { return (mOpeningLineIndex != -1); }

    /// <summary>
    /// Gets or sets a boolean value indicating the emphasis is pending for opening.
    /// </summary>
    boolean isPendingForOpening()
    {
        return ((getState() == MarkdownEmphasisWriterState.OPENING) && !isOpened());
    }
    void isPendingForOpening(boolean value)
    {
        mOpeningLineIndex = -1;
        setState((value) ? MarkdownEmphasisWriterState.OPENING : MarkdownEmphasisWriterState.NONE);
    }

    /// <summary>
    /// The current state of the emphasis.
    /// </summary>
    /*MarkdownEmphasisWriterState*/int getState() { return mState; }; private void setState(/*MarkdownEmphasisWriterState*/int value) { mState = value; };

    private /*MarkdownEmphasisWriterState*/int mState;

    /// <summary>
    /// Returns true, if the current writer is in Html syntax mode.
    /// </summary>
    boolean UseHtmlSyntax;

    /// <summary>
    /// Gets Flanking type of the opening delimiter for the emphasis.
    /// </summary>
    protected /*FlankingType*/int getOpeningFlanking()
    {
        Debug.assertTrue(isOpened());

        int prevCharIdx = mOpeningPosition - 1;
        int nextCharIdx = mOpeningPosition + getDelimiter().length();

        StringBuilder line = mContentLines.get(mOpeningLineIndex);
        char prevChar = (prevCharIdx < 0) ? InlineContainerBlock.LINE_SEPARATOR : line.charAt(prevCharIdx);
        char nextChar = (nextCharIdx < line.length()) ? line.charAt(nextCharIdx) : InlineContainerBlock.LINE_SEPARATOR;

        return MarkdownUtil.getFlankingType(prevChar, nextChar);
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected abstract String getDelimiter();

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected abstract String getCloseDelimiter();

    /// <summary>
    /// Gets a current content line to write into.
    /// </summary>
    private StringBuilder getCurrentLine() { return mContentLines.getCurrentLine(); }

    /// <summary>
    /// Gets an index of current content line to write into.
    /// </summary>
    private int getCurrentLineIndex() { return mContentLines.getCurrentLineIndex(); }

    /// <summary>
    /// A boolean value indicating either last processed run was emphasized (either the value of a corresponding
    /// FontAttr key property of the run was 'true' or 'false').
    /// </summary>
    protected boolean IsEmphasized;

    /// <summary>
    /// The underlying content lines to write into.
    /// </summary>
    private /*final*/ TxtContentLines mContentLines;

    /// <summary>
    /// The index of line where opening delimiter is written.
    /// </summary>
    private int mOpeningLineIndex = -1;

    /// <summary>
    /// The position within content line where opening delimiter is written.
    /// </summary>
    private int mOpeningPosition;
}

