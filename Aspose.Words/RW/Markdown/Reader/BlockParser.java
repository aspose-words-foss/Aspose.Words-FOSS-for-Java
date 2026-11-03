// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;
import com.aspose.IntList;


/// <summary>
/// The class for parsing a text line into the <see cref="Block"/> object.
/// </summary>
class BlockParser
{
    /// <summary>
    /// Ctor.
    /// </summary>
    BlockParser()
    {
        initTypes();
    }

    /// <summary>
    /// Initializes block collection with all possible block types.
    /// </summary>
    void initTypes()
    {
        // If all possible types are already persisted in a collection, then nothing to do.
        if (mBlockTypes.getCount() == G_ALL_BLOCK_TYPES.length)
            return;

        mBlockTypes.clear();
        for (/*BlockType*/byte type : G_ALL_BLOCK_TYPES)
            mBlockTypes.add((int)type);
    }

    /// <summary>
    /// Excludes specified type from a collection of block types,
    /// so that this type will not participate in a block parsing.
    /// </summary>
    void exclude(/*BlockType*/byte type)
    {
        mBlockTypes.remove((int) type);
    }

    /// <summary>
    /// Parses a text line starting from a specified position to a Block object.
    /// </summary>
    Block parse(String txtLine, int start)
    {
        for (int i = 0; i < mBlockTypes.getCount(); i++)
        {
            Block block = tryParse(txtLine, start, (/*BlockType*/byte)mBlockTypes.get(i));
            if (block != null)
                return block;
        }

        return null;
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position as a specified block type.
    /// </summary>
    /// <returns>
    /// Parsed block of a specified type, or <c>null</c> if cannot parse.
    /// </returns>
    private Block tryParse(String txtLine, int start, /*BlockType*/byte type)
    {
        Block block = mBlocks.get((int)type);
        if (block == null)
            block = MarkdownBlockFactory.create(type);

        if (block.tryParse(txtLine, start))
        {
            mBlocks.set((int)type, null);
            return block;
        }

        mBlocks.set((int)type, block);
        return null;
    }

    // A collection of blocks which we have tried to recognize, but failed.
    // So we can use it for next parsing line.
    private /*final*/ IntToObjDictionary<Block> mBlocks = new IntToObjDictionary<Block>();

    /// <summary>
    /// A collection of block types that will participate in a parsing process.
    /// </summary>
    /// <remarks>
    /// During the parsing process this collection may be altered when we have determined some line of text
    /// as a particular block type, but it turned out later, that this identification was wrong. So we must exclude
    /// that type from the collection to allow parser to try the line of text as some another block type.
    /// </remarks>
    /// <dev>IN. Forced to use IntList instead of generic List due to Java auto-porting problems.</dev>
    private /*final*/ IntList mBlockTypes = new IntList();

    /// <summary>
    /// All possible block types.
    /// </summary>
    /// <remarks>
    /// The order of block types is IMPORTANT:
    /// - Table should be below Quote and above Lists,
    /// - Setext must be above HorizontalRule,
    /// - HorizontalRule must be above BulletListItem,
    /// - BlankLine, EndOfLine and Paragraph should be at the very end, and they should go in this order.
    /// </remarks>
    private static /*final*/ /*BlockType*/byte[] G_ALL_BLOCK_TYPES =
    {
        BlockType.QUOTE,
        BlockType.TABLE,
        BlockType.ATX_HEADING,
        BlockType.SETEXT_HEADING,
        BlockType.HORIZONTAL_RULE,
        BlockType.BULLET_LIST_ITEM,
        BlockType.ORDERED_LIST_ITEM,
        BlockType.INDENTED_CODE,
        BlockType.FENCED_CODE,
        BlockType.FOOTNOTE_DEFINITION,
        BlockType.BLANK_LINE,
        BlockType.END_OF_LINE,
        BlockType.PARAGRAPH
    };
}

