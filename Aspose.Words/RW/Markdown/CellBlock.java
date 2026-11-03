// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a markdown Cell block.
/// </summary>
class CellBlock extends InlineContainerBlock
{
    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        TableBlock parentTable = (TableBlock)getParent(BlockType.TABLE);
        Debug.assertTrue(parentTable != null);

        // The spec says, that if there are a number of cells greater than the number of cells
        // in the header row the excess is ignored.
        if (getIndex() >= parentTable.getColumnsCount())
            return;

        super.write(context);
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        // There should be either opening or closing cell separator (|).
        if ((getOpening().length() == 0) && (getClosing().length() == 0))
            return false;

        // Check if this is just a single trailing separator.
        // WORDSNET-26368 Trim whitespace characters after single trailing separator.
        if ((getOpening().length() != 0) && (getClosing().length() == 0) && (msString.trim(getContentLine()).length() == 0))
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
            case BlockType.INLINE:
            case BlockType.BOLD_INLINE:
            case BlockType.ITALIC_INLINE:
            case BlockType.STRIKETHROUGH:
            case BlockType.UNDERLINE:
            case BlockType.INLINE_CODE:
                return super.tryAppend(block);
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns true, if cell is valid for the delimiter row.
    /// </summary>
    boolean isValidDelimiter()
    {
        boolean hasContent = false;

        String content = msString.trim(getContentLine());
        for (int i = 0; i < content.length(); i++)
        {
            switch (content.charAt(i))
            {
                case ALIGNMENT_CHAR:
                {
                    // The cell alignment character can be only either at the very start or very end.
                    if (i == 0)
                    {
                        mAlignment = ParagraphAlignment.LEFT;
                        break;
                    }

                    if (i == (content.length() - 1))
                    {
                        mAlignment = (mAlignment == ParagraphAlignment.LEFT)
                            ? ParagraphAlignment.CENTER
                            : ParagraphAlignment.RIGHT;
                        break;
                    }

                    return false;
                }
                case CONTENT_CHAR:
                {
                    hasContent = true;
                    break;
                }
                default:
                {
                    // Any other character is not valid in delimiter cell.
                    return false;
                }
            }
        }

        return hasContent;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (c == SEPARATOR_CHAR);
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
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return 1; }

    /// <summary>
    /// Gets a boolean value indicating either closing sequence is allowed without an opening one.
    /// </summary>
    protected /*override*/ boolean getAllowClosingWithoutOpening() { return true; }

    /// <summary>
    /// Gets a closing part from a text line moving from a specified end toward to start position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        int i = start;
        while ((i <= end) && !isOpeningChar(txtLine.charAt(i)))
            i++;

        if (i > end)
            return "";

        return txtLine.substring((i), (i) + (end - i + 1));
    }

    /// <summary>
    /// Gets total length of all block parts.
    /// </summary>
    /*override*/ int getLength()
    {
        return (getOpeningIndentation().length() +
                getOpening().length() +
                getContentLine().length() +
                // Closing in cell starts at the closing pipe | and lasts to the end of whole line.
                // So, need to limit it to just a closing pipe.
                Math.min(getClosing().length(), 1));
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.CELL; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.LEAF; }

    /// <summary>
    /// Gets alignment of the cell content.
    /// </summary>
    /*ParagraphAlignment*/int getAlignment()
    {
        TableBlock parentTable = (TableBlock)getParent(BlockType.TABLE);
        if ((parentTable == null) || (getIndex() >= parentTable.getColumnsCount()))
            return mAlignment;

        return ((CellBlock)parentTable.getDelimiterRow().get(getIndex())).getAlignment();
    }

    static final char ALIGNMENT_CHAR = ':';
    static final char CONTENT_CHAR = '-';
    static final char SEPARATOR_CHAR = '|';

    private /*ParagraphAlignment*/int mAlignment = ParagraphAlignment.CENTER;
}

