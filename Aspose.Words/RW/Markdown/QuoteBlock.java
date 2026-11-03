// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a markdown Quote block.
/// </summary>
class QuoteBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (getOpening().length() == 0)
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        return true;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.SETEXT_HEADING:
            {
                // If SetextHeading has no any content, then we still can't say with certainty that this is
                // actually a heading. For example, it can be a HorizontalRule or just a regular Paragraph.
                if (((SetextHeadingBlock)block).getContentLines().size() > 0)
                    add(block);

                return true;
            }
            default:
            {
                // Anything can be added into a Quote. But we don't want BlankLines in our tree.
                Block blockToAppend = (block.getType() == BlockType.BLANK_LINE) ? new EndOfLineBlock() : block;
                add(blockToAppend).openParents(BlockType.QUOTE);
                return true;
            }
        }
    }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getOpening(String txtLine, int start)
    {
        String opening = super.getOpening(txtLine, start);
        if (opening.length() > 0)
        {
            // There can be one whitespace character after main opening sequence.
            int whitespaceIdx = start + opening.length();
            if (whitespaceIdx < txtLine.length())
            {
                char c = txtLine.charAt(whitespaceIdx);
                if (msChar.isWhiteSpace(c))
                    opening = msString.format("{0}{1}", opening, c);
            }
        }

        return opening;
    }

    /// <summary>
    /// Gets a content part from a text line starting at a specified start position up to a end position.
    /// </summary>
    protected /*override*/ String getContent(String txtLine, int start, int end)
    {
        // Quote does not have any own text as a content. Instead, it contains other
        // composite blocks that constitute its content.
        return "";
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
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
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (c == '>');
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.QUOTE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return 1; }
}

