// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a markdown ItalicInline block.
/// </summary>
class ItalicInlineBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return false;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        Debug.assertTrue(isOpened());

        add(block);
        return true;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.ITALIC_INLINE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*override*/ String getText() { return msString.format("{0}{1}{2}", ASTERISK_DELIMITER, super.getText(), ASTERISK_DELIMITER); }

    static final String ASTERISK_DELIMITER = "*";
    static final String UNDERSCORE_DELIMITER = "_";
}

