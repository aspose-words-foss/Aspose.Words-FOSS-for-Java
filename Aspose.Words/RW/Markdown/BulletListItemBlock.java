// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msChar;

/// <summary>
/// Represents a markdown BulletListItem block.
/// </summary>
class BulletListItemBlock extends ListItemBlock
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

        // The opening sequence MUST be followed with at least one whitespace character,
        // or the block should be entirely empty.
        if ((start + getOpening().length()) < txtLine.length())
        {
            char lastOpeningChar = getOpening().charAt(getOpening().length() - 1);
            if (!msChar.isWhiteSpace(lastOpeningChar))
                return false;
        }

        // Initially, list item block is awaiting another blocks to be parsed as its content.
        setAction(BlockAction.REPARSE_LIST_ITEM);
        setMarker(MarkdownUtil.toListMarker(getOpening().charAt(0)));
        
        return true;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == '-') || (c == '+') || (c == '*'));
    }

    /// <summary>
    /// Gets length of the list label.
    /// </summary>
    /*override*/ int getListLabelLength()
    {
        // The opening length is always greater or equal to 2 characters:
        // the first one is a bullet marker('-', '+', '*') and the second one is space or tab.
        // Even if the list item is completely empty (i.e. has only bullet marker), the spec editor
        // at https://spec.commonmark.org/dingus/ parses it as two characters.
        int length = getOpeningIndentationLength() + Math.max(2, getOpeningLength());

        // We need to consider also indentation of the first content block,
        // if it is not IndentedCode (see rule #2 of the spec).
        if (!isEmpty() && (this.get(0).getType() != BlockType.INDENTED_CODE))
            length += this.get(0).getIndentation();

        return length;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.BULLET_LIST_ITEM; }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return 1; }
}

