// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents a markdown FootnoteDefinition block.
/// </summary>
class FootnoteDefinitionBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (("".equals(getOpening())) || ("".equals(getClosing())))
            return false;

        if (getReference().length() < 1)
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        if (!FootnoteReferenceBlock.isValid(getReference(), 0, getReference().length() - 1))
            return false;

        return true;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        Debug.assertTrue(block != null);

        switch (block.getType())
        {
            case BlockType.FOOTNOTE_DEFINITION:
                close();
                return false;
            case BlockType.INDENTED_CODE:
            {
                // IN. We mimic GitLab and there cannot be IndentedCode in FootnoteDefinition only at the very first line.
                // Also note, that in order to fall under the Footnote,
                // the paragraphs (and IndentedCode), starting from the second one, must be indented.
                if (isEmpty() || (block.getContentLineIndentationLength() <= MAX_INDENTATION_LENGTH))
                {
                    block.setAction(BlockAction.EXCLUDE);
                    return false;
                }

                add(block);
                return true;
            }
            default:
            {
                // We don't want BlankLines in our tree.
                add((block.getType() == BlockType.BLANK_LINE) ? new EndOfLineBlock() : block);
                return true;
            }
        }
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context)
    {
        // This block is written along with the FootnoteReferenceBlock.
    }

    /// <summary>
    /// Gets string representing a Reference part of this footnote definition.
    /// </summary>
    /// <remarks>The reference is actually a ContentLine between opening [^ and closing ]: delimiters.</remarks>
    String getReference() { return getContentLine(); }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getOpening(String txtLine, int start)
    {
        // Check the Opening fits in txtLine.
        if ((start + OPENING_DELIMITER.length()) > txtLine.length())
            return "";

        // The Opening of Footnote definition can be only at the very first position of the specified offset in txtLine.
        if (msString.indexOf(txtLine, OPENING_DELIMITER, start, OPENING_DELIMITER.length(), StringComparison.INVARIANT_CULTURE) == -1)
            return "";

        mContentIndex = start + OPENING_DELIMITER.length();

        return OPENING_DELIMITER;
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        // The Closing is not allowed without Opening.
        if ("".equals(getOpening()))
            return "";

        // The Closing index was calculated while getting RightIndentation.
        return mClosingIndex == -1 ? "" : CLOSING_DELIMITER;
    }

    /// <summary>
    /// Gets a content part from a text line starting at a specified start position up to the end position.
    /// </summary>
    protected /*override*/ String getContent(String txtLine, int start, int end)
    {
        if ("".equals(getOpening()))
            return "";

        if (mClosingIndex == -1)
            return "";

        return txtLine.substring((mContentIndex), (mContentIndex) + (mClosingIndex - mContentIndex));
    }

    /// <summary>
    /// Gets an indentation part from a right side of a text line starting at the end
    /// of text up to a specified start position.
    /// </summary>
    protected /*override*/ String getRightIndentation(String txtLine, int start)
    {
        if ("".equals(getOpening()))
            return "";

        mClosingIndex = msString.indexOf(txtLine, CLOSING_DELIMITER, mContentIndex, StringComparison.INVARIANT_CULTURE);
        if (mClosingIndex == -1)
            return "";

        // We mimic GitLab, so all whitespaces after Closing sequence ]: are considered as Closing (right) indentation.
        int indentStart = mClosingIndex + CLOSING_DELIMITER.length();
        int indentEnd = MarkdownUtil.indexOfNonWhitespace(txtLine, indentStart, txtLine.length() - 1);
        if (indentEnd <= indentStart)
            return "";

        return txtLine.substring((indentStart), (indentStart) + (indentEnd - indentStart));
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.FOOTNOTE_DEFINITION; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return OPENING_DELIMITER.length(); }

    /// <summary>
    /// The Opening delimiter.
    /// </summary>
    private static final String OPENING_DELIMITER = "[^";

    /// <summary>
    /// The Closing delimiter.
    /// </summary>
    private static final String CLOSING_DELIMITER = "]:";

    /// <summary>
    /// The cached index of Closing delimiter.
    /// </summary>
    private int mClosingIndex;

    /// <summary>
    /// The cached index of ContentLine.
    /// </summary>
    private int mContentIndex;
}

