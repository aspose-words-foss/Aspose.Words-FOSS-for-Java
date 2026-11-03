// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// The factory class that helps to create markdown blocks.
/// </summary>
/*static*/ class MarkdownBlockFactory
{
	/* Simulation of static class by using private constructor */
	private MarkdownBlockFactory()
	{}

    /// <summary>
    /// Creates a markdown Block object of a specified type.
    /// </summary>
    static Block create(/*BlockType*/byte type)
    {
        switch (type)
        {
            case BlockType.PARAGRAPH:
                return new ParagraphBlock();
            case BlockType.END_OF_LINE:
                return new EndOfLineBlock();
            case BlockType.ATX_HEADING:
                return new AtxHeadingBlock();
            case BlockType.SETEXT_HEADING:
                return new SetextHeadingBlock();
            case BlockType.BLANK_LINE:
                return new BlankLineBlock();
            case BlockType.QUOTE:
                return new QuoteBlock();
            case BlockType.TABLE:
                return new TableBlock();
            case BlockType.ROW:
                return new RowBlock();
            case BlockType.CELL:
                return new CellBlock();
            case BlockType.HORIZONTAL_RULE:
                return new HorizontalRuleBlock();
            case BlockType.INDENTED_CODE:
                return new IndentedCodeBlock();
            case BlockType.FENCED_CODE:
                return new FencedCodeBlock();
            case BlockType.FOOTNOTE_DEFINITION:
                return new FootnoteDefinitionBlock();
            case BlockType.BULLET_LIST_ITEM:
                return new BulletListItemBlock();
            case BlockType.ORDERED_LIST_ITEM:
                return new OrderedListItemBlock();
            case BlockType.LINK_DEFINITION:
                return new LinkDefinitionBlock();
            default:
                throw new IllegalStateException(msString.format("Invalid block type: {0}", type));
        }
    }
}

