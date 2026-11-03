// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/07/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a markdown IndentedCode block.
/// </summary>
class IndentedCodeBlock extends CodeBlockBase
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (getOpeningLength() <= MAX_INDENTATION_LENGTH)
            return false;

        if (StringUtil.containsOnlyWhitespaces(getContentLine()))
            return false;

        msArrayList.add(getContentLines(), getContentLine());

        return true;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.PARAGRAPH:
            {
                // Paragraph interrupts IndentedCode.
                return false;
            }
            case BlockType.INDENTED_CODE:
            {
                msArrayList.add(getContentLines(), block.getContentLine());
                openParentQuotes();
                return true;
            }
            case BlockType.BLANK_LINE:
            {
                // We can append blank line inside Quote only when both blocks are in Quotes.
                if (isInQuote())
                {
                    // Otherwise interrupt parent Quotes.
                    if (getParent(BlockType.QUOTE, false) == null)
                    {
                        closeParents(BlockType.QUOTE);
                        return false;
                    }

                    openParentQuotes();
                }

                // BlankLines are allowed inside IndentedCode.
                msArrayList.add(getContentLines(), StringUtil.trimStart(block.getContentLine(), MAX_INDENTATION_LENGTH + 1));
                return true;
            }
            default:
                return super.tryAppend(block);
        }
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.INDENTED_CODE; }

    /// <summary>
    /// Gets block indentation.
    /// </summary>
    /*override*/ int getIndentation() { return getOpeningLength() + getContentLineIndentationLength(); }

    /// <summary>
    /// Gets a plain text from the content lines of the inline container.
    /// </summary>
    protected /*override*/ String getInlineText()
    {
        return msString.trimEnd(super.getInlineText(), LINE_SEPARATOR);
    }

    /// <summary>
    /// Gets an indentation part from a left side of a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getLeftIndentation(String txtLine, int start)
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
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        return "";
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == ' ') || (c == '\t'));
    }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return MAX_INDENTATION_LENGTH + 1; }
}

