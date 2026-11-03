// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents a markdown Row block.
/// </summary>
class RowBlock extends ContainerBlock
{
    /// <summary>
    /// Ctor.
    /// </summary>
    RowBlock()
    {
    }

    /// <summary>
    /// Creates a Row from a specified text.
    /// </summary>
    RowBlock(String text)
    {
        ArrayList<CellBlock> cells = parse(text); 
        for (CellBlock cell : cells)
            add(cell);
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (getContentLine().length() > 0)
            return false;

        ArrayList<CellBlock> cells = parse(getOpening());
        if (isValidDelimiter(cells))
        {
            Debug.assertTrue(isEmpty());
            for (CellBlock cell : cells)
                add(cell);
            
            return true;
        }

        return false;
    }

    /// <summary>
    /// Parses a specified text onto Cell blocks.
    /// </summary>
    static ArrayList<CellBlock> parse(String text)
    {
        ArrayList<CellBlock> cells = new ArrayList<CellBlock>();

        CellBlock curCell = new CellBlock();
        int start = 0;
        while (curCell.tryParse(text, start))
        {
            msArrayList.add(cells, curCell);
            start += (curCell.getLength() - 1);

            curCell = new CellBlock();
        }

        return cells;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (c == CellBlock.SEPARATOR_CHAR || c == CellBlock.CONTENT_CHAR || c == CellBlock.ALIGNMENT_CHAR || c == ' ');
    }

    /// <summary>
    /// Returns true, if a specified list of cells can constitute the valid row delimiter. 
    /// </summary>
    private static boolean isValidDelimiter(ArrayList<CellBlock> cells)
    {
        for (CellBlock cell : cells)
        {
            if (!cell.isValidDelimiter())
                return false;
        }

        return (cells.size() > 0);
    }


    /// <summary>
    /// Gets block text.
    /// </summary>
    
    /*override*/ String getText()
    {
        StringBuilder sb = new StringBuilder();

        if (getCount() > 0)
            sb.append(CellBlock.SEPARATOR_CHAR);

        for (Block block : (Iterable<Block>) this)
        {
            CellBlock cell = (CellBlock)block;
            msStringBuilder.append(sb, cell.getText());
            sb.append(CellBlock.SEPARATOR_CHAR);
        }

        return sb.toString();
    }


    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.ROW; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }
}

