// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;


/// <summary>
/// Represents a markdown OrderedListItem block.
/// </summary>
class OrderedListItemBlock extends ListItemBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        // There must be at least one digit and either '.' or ')'.
        if (getOpening().length() < 2)
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        // The opening sequence MUST start from a digit. 
        if (!StringUtil.isDigit(getOpening().charAt(0)))
            return false;

        int markerDelimiterIndex = getOpening().length() - 1;
        if (msChar.isWhiteSpace(getOpening().charAt(markerDelimiterIndex)))
            markerDelimiterIndex--;
        
        // The number of digits must be less than 10.
        if (markerDelimiterIndex > 9)
            return false;
        
        // The marker delimiter must be at the very end or just before ending whitespace.
        if (msString.indexOfAny(getOpening(), new char[] {'.', ')'}) != markerDelimiterIndex)
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
        setMarker(MarkdownUtil.toListMarker(getOpening().charAt(markerDelimiterIndex)));
        setStartAt(FormatterPal.parseInt(getOpening().substring((0), (0) + (markerDelimiterIndex))));
        
        return true;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (StringUtil.isDigit(c) || (c == '.') || (c == ')'));
    }

    /// <summary>
    /// Gets length of the list label.
    /// </summary>
    /*override*/ int getListLabelLength()
    {
        // The opening length is always greater or equal to 3 characters:
        // the first is label number, the second is a marker('.', ')') and the third one is space or tab.
        // Even if the list item is completely empty (i.e. has only digits and marker), the spec editor
        // at https://spec.commonmark.org/dingus/ parses it as three characters.
        int length = getOpeningIndentationLength() + Math.max(3, getOpeningLength());
        
        // We need to consider also indentation of the first content block,
        // if it is not IndentedCode (see rule #2 of the spec).
        if (!isEmpty() && (this.get(0).getType() != BlockType.INDENTED_CODE))
            length += this.get(0).getIndentation();

        return length;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.ORDERED_LIST_ITEM; }
}

