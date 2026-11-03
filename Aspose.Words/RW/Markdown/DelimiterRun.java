// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a delimiter run markdown block.
/// </summary>
/// <remarks>
/// This can be a sequence of the delimiters, such as for example: **_***___***,
/// that will be further split onto individual <see cref="Delimiter"/> objects.
/// See 6.4 Emphasis and strong emphasis at https://spec.commonmark.org for details.
/// </remarks>
class DelimiterRun extends Block
{
    /// <summary>
    /// Initializes a new instance of <see cref="DelimiterRun"/> by default.
    /// </summary>
    DelimiterRun()
    {
    }

    /// <summary>
    /// Initializes a new instance of <see cref="DelimiterRun"/> with a specified load options.
    /// </summary>
    DelimiterRun(MarkdownLoadOptions loadOptions)
    {
        mLoadOptions = loadOptions;
    }

    /// <summary>
    /// Returns a string that represents the current object.
    /// </summary>
    public /*override*/ String toString()
    {
        return getOpening();
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        mText = txtLine;

        if (getOpening().length() == 0)
            return false;

        setStart(start + getOpeningIndentation().length());

        // Delimiter run cannot be escaped, except of InlineCode closing.
        return (!isEscaped() || (txtLine.charAt(getStart()) == InlineCodeDelimiter.CHARACTER));
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        return false;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == AsteriskDelimiter.CHARACTER) ||
                (c == UnderscoreDelimiter.CHARACTER) ||
                (c == StrikeThroughDelimiter.CHARACTER) ||
                // WORDSNET-27318 A new option is implemented to control over the Underline formatting.
                ((c == UnderlineDelimiter.CHARACTER) && isImportUnderlineFormatting()) ||
                (c == InlineCodeDelimiter.CHARACTER) ||
                (c == AutoLinkOpeningDelimiter.CHARACTER) ||
                (c == AutoLinkClosingDelimiter.CHARACTER) ||
                (c == LinkTextOpeningDelimiter.CHARACTER) ||
                (c == LinkTextClosingDelimiter.CHARACTER) ||
                (c == LinkDestinationOpeningDelimiter.CHARACTER) ||
                (c == LinkDestinationClosingDelimiter.CHARACTER) ||
                (c == ImageDescriptionOpeningDelimiter.CHARACTER) ||
                (c == FootnoteOpeningDelimiter.CHARACTER));
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of indentation characters.
    /// </summary>
    protected /*override*/ boolean isIndentationChar(char c)
    {
        return !isOpeningChar(c);
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        return "";
    }

    /// <summary>
    /// Gets a content part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getContent(String txtLine, int start, int end)
    {
        return "";
    }

    /// <summary>
    /// Gets an indentation part from a right side of a text line starting at the end
    /// of text up to a specified start position.
    /// </summary>
    protected /*override*/ String getRightIndentation(String txtLine, int start)
    {
        return "";
    }

    /// <summary>
    /// Gets a Delimiter object at a specified offset.
    /// </summary>
    private Delimiter getDelimiter(int offset)
    {
        char delimiterChar = getOpening().charAt(offset);
        switch (delimiterChar)
        {
            case AsteriskDelimiter.CHARACTER:
                return new AsteriskDelimiter(this, offset);

            case UnderscoreDelimiter.CHARACTER:
                return new UnderscoreDelimiter(this, offset);

            case StrikeThroughDelimiter.CHARACTER:
                return new StrikeThroughDelimiter(this, offset);

            case UnderlineDelimiter.CHARACTER:
                return new UnderlineDelimiter(this, offset);

            case InlineCodeDelimiter.CHARACTER:
                return new InlineCodeDelimiter(this, offset);

            case AutoLinkOpeningDelimiter.CHARACTER:
                return new AutoLinkOpeningDelimiter(this, offset);

            case AutoLinkClosingDelimiter.CHARACTER:
                return new AutoLinkClosingDelimiter(this, offset);

            case LinkTextOpeningDelimiter.CHARACTER:
                return new LinkTextOpeningDelimiter(this, offset);

            case LinkTextClosingDelimiter.CHARACTER:
                return new LinkTextClosingDelimiter(this, offset);

            case LinkDestinationOpeningDelimiter.CHARACTER:
                return new LinkDestinationOpeningDelimiter(this, offset);

            case LinkDestinationClosingDelimiter.CHARACTER:
                return new LinkDestinationClosingDelimiter(this, offset);

            case ImageDescriptionOpeningDelimiter.CHARACTER:
                return new ImageDescriptionOpeningDelimiter(this, offset);

            case FootnoteOpeningDelimiter.CHARACTER:
                return new FootnoteOpeningDelimiter(this, offset);

            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + msString.format("Unexpected delimiter character '{0}'.",
                        delimiterChar));
        }
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.DELIMITER_RUN; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.NONE; }

    /// <summary>
    /// Gets a collection of the <see cref="Delimiter"/> objects.
    /// </summary>
    ArrayList<Delimiter> getDelimiters()
    {
        if (mDelimiters == null)
        {
            mDelimiters = new ArrayList<Delimiter>();
            int offset = 0;
            while (offset < getOpening().length())
            {
                Delimiter delimiter = getDelimiter(offset);
                offset += delimiter.getLength();

                msArrayList.add(mDelimiters, delimiter);
            }
        }

        return mDelimiters;
    }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*override*/ String getText() { return mText; }

    /// <summary>
    /// Gets start position of the delimiter run inside an original text.
    /// </summary>
    int getStart() { return mStart; }; private void setStart(int value) { mStart = value; };

    private int mStart;

    /// <summary>
    /// Gets a boolean value indicating the delimiter run is escaped.
    /// </summary>
    boolean isEscaped() { return MarkdownUtil.isEscaped(mText, getStart()); }

    /// <summary>
    /// Gets a boolean value indicating either to recognize <see cref="UnderlineDelimiter"/>
    /// in accordance with <see cref="mLoadOptions"/>.
    /// </summary>
    private boolean isImportUnderlineFormatting() { return (mLoadOptions != null) && mLoadOptions.getImportUnderlineFormatting(); }

    private String mText;
    private ArrayList<Delimiter> mDelimiters;

    private /*final*/ MarkdownLoadOptions mLoadOptions;
}

