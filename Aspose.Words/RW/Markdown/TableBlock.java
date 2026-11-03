// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a markdown Table block.
/// </summary>
class TableBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!mDelimiterRow.tryParse(txtLine, start))
            return false;

        // The table must have at least header and delimiter rows.
        // Until they are recognized, lets exclude table block from processing.
        setAction(BlockAction.EXCLUDE);

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
                Debug.assertTrue(((ParagraphBlock)block).getContentLines().size() == 1);
                RowBlock row = new RowBlock(((ParagraphBlock)block).getContentLines().get(0));
                if (row.isEmpty())
                    return false;

                add(row);
                return true;
            }
            case BlockType.ROW:
            {
                add((RowBlock)block);
                return true;
            }
            case BlockType.END_OF_LINE:
            {
                add(block).openParents(BlockType.QUOTE);
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    /// <summary>
    /// Returns true, if a specified paragraph block can constitute the valid header row for this table.
    /// </summary>
    boolean isValid(ParagraphBlock paragraphBlock)
    {
        if (paragraphBlock.getContentLines().size() == 0)
            return false;

        // Table can be inside a Quote, but it has some restrictions.
        if (paragraphBlock.isInQuote())
        {
            // The paragraph can not be multiline.
            if (paragraphBlock.getContentLines().size() > 1)
                return false;

            // All rows must be at the same Quote level.
            if (paragraphBlock.getParent(BlockType.QUOTE, true) != null)
                return false;
        }

        String text = paragraphBlock.getContentLines().get(paragraphBlock.getContentLines().size() - 1);

        // The number of cols must be equal.
        if (mDelimiterRow.getCount() != RowBlock.parse(text).size())
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (c == CellBlock.SEPARATOR_CHAR ||
                c == CellBlock.CONTENT_CHAR ||
                c == CellBlock.ALIGNMENT_CHAR ||
                c == ' ');
    }

    /// <summary>
    /// Gets total length of all block parts.
    /// </summary>
    /*override*/ int getLength() { return mDelimiterRow.getLength(); }

    /// <summary>
    /// Gets block indentation.
    /// </summary>
    /*override*/ int getIndentation() { return mDelimiterRow.getIndentation(); }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.TABLE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets delimiter row.
    /// </summary>
    RowBlock getDelimiterRow() { return mDelimiterRow; }

    /// <summary>
    /// Gets total number of columns in the table.
    /// </summary>
    int getColumnsCount() { return mDelimiterRow.getCount(); }

    private /*final*/ RowBlock mDelimiterRow = new RowBlock();
}

