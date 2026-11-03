// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/05/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents a markdown LinkText block.
/// </summary>
class LinkTextBlock extends ReferenceBlock
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
    /// Returns true, if specified delimiters can constitute a valid visible text of the link.
    /// </summary>
    static boolean isValid(Delimiter opening, Delimiter closing)
    {
        Debug.assertTrue((opening != null) && (closing != null));
        Debug.assertTrue(opening.isBefore(closing));

        return MarkdownUtil.areBalanced(opening, closing, OPENING_DELIMITER, CLOSING_DELIMITER);
    }

    /// <summary>
    /// A type of the block.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.LINK_TEXT; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Opening string delimiter.
    /// </summary>
    protected /*override*/ String getOpeningStringDelimiter() { return "["; }

    /// <summary>
    /// Opening delimiter for LinkTextBlock.
    /// </summary>
    static final char OPENING_DELIMITER = LinkTextOpeningDelimiter.CHARACTER;

    /// <summary>
    /// Closing delimiter for LinkTextBlock.
    /// </summary>
    static final char CLOSING_DELIMITER = LinkTextClosingDelimiter.CHARACTER;
}

