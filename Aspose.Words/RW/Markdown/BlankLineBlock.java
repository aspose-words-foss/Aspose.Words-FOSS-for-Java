// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Represents a markdown blank line.
/// </summary>
class BlankLineBlock extends Block
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        // Check if this is EndOfLine.
        if ((start > 0) && (start == txtLine.length()))
            return false;

        return StringUtil.containsOnlyWhitespaces(getContentLine());
    }

    /// <summary>
    /// Tries to append a specified block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        // The blank line is always closed, so it cannot be appended.
        return false;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of indentation characters.
    /// </summary>
    protected /*override*/ boolean isIndentationChar(char c)
    {
        return false;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.BLANK_LINE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.NONE; }
}

