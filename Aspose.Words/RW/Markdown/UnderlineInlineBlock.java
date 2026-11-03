// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a markdown UnderlineInline block.
/// </summary>
class UnderlineInlineBlock extends ContainerBlock
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
    /*override*/ /*BlockType*/byte getType() { return BlockType.UNDERLINE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /*override*/ String getText() { return msString.format("{0}{1}{2}", DELIMITER, super.getText(), DELIMITER); }

    /// <summary>
    /// Delimiter for UnderlineInlineBlock.
    /// </summary>
    static final String DELIMITER = "++";
}

