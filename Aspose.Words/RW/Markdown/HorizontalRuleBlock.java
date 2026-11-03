// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a markdown horizontal rule (thematic break) block.
/// </summary>
class HorizontalRuleBlock extends Block
{
    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        context.open(this);
        context.getBuilder().insertHorizontalRule();
        context.close(this);
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        // A horizontal rule can have only an Opening and optionally OpeningIndentation.
        if (getContentLine().length() > 0)
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        return isOpeningValid();
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        // HorizontalRule is not appendable.
        return false;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == '-') || (c == '_') || (c == '*') || (c == ' '));
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.HORIZONTAL_RULE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.LEAF; }

    /// <summary>
    /// Gets a boolean value indicating whether Opening is valid for a HorizontalRule block.
    /// </summary>
    private boolean isOpeningValid()
    {
        Debug.assertTrue(MIN_OPENING_LENGTH > 0);

        if (getOpening().length() < MIN_OPENING_LENGTH)
            return false;

        int nonWhitespaceCount = 0;
        for (int i = 0; i < getOpening().length(); i++)
        {
        	char c = getOpening().charAt(i);
            if (c == ' ')
                continue;

            // Note, a first opening character is always non-whitespace. Otherwise, it will be opening indentation.
            if (c != getOpening().charAt(0))
                return false;

            nonWhitespaceCount++;
        }

        return (nonWhitespaceCount >= MIN_OPENING_LENGTH);
    }

    /// <summary>
    /// Defines minimum length of characters sequence to consider
    /// it as a valid opening for the HorizontalRule block.
    /// </summary>
    private static final int MIN_OPENING_LENGTH = 3;
}

