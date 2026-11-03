// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The base class for markdown heading blocks.
/// </summary>
abstract class HeadingBlock extends InlineContainerBlock
{
    /// <summary>
    /// Tries to append a specified block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        // Only inline blocks can be appended into a heading.
        if (block.getBlockLevel() == MarkdownBlockLevel.INLINE)
            return super.tryAppend(block);

        return false;
    }
    
    /// <summary>
    /// Gets or sets a heading level.
    /// </summary>
    int getLevel() { return mLevel; }
    void setLevel(int value) { mLevel = value; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.LEAF; }

    /// <summary>
    /// A heading level.
    /// </summary>
    private int mLevel;
}

