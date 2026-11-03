// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.Debug;


/// <summary>
/// Represents a markdown InlineCode block (code span).
/// </summary>
class InlineCodeBlock extends ContainerBlock
{

    /// <summary>
    /// Creates the instance with a specified number of backticks.
    /// </summary>
    InlineCodeBlock(int delimiterLength)
    {
        mDelimiter = msString.newString(InlineCodeDelimiter.CHARACTER, delimiterLength);
    }

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
        Debug.assertTrue((block.getType() == BlockType.INLINE) || (block.getType() == BlockType.HTML_TAG));

        String normalizedText = MarkdownUtil.normalize(block.getText());
        add(new InlineBlock(normalizedText));

        return true;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        context.open(this);
        context.getBuilder().write(super.getText());
        context.close(this);
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.INLINE_CODE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets block text.
    /// </summary>
    /// <remarks>
    /// We probably need to UnNormalize text. See <see cref="MarkdownUtil.Normalize"/> for details.
    /// </remarks>
    /*override*/ String getText() { return msString.format("{0}{1}{2}", mDelimiter, super.getText(), mDelimiter); }

    /// <summary>
    /// Gets length of a delimiter.
    /// </summary>
    int getDelimiterLength() { return mDelimiter.length(); }

    /// <summary>
    /// A string value that represents a delimiter for this InlineCode block.
    /// </summary>
    private /*final*/ String mDelimiter;
}

