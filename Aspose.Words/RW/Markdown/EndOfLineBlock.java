// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;

/// <summary>
/// Represents a EndOfLine block.
/// </summary>
class EndOfLineBlock extends Block
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return (start == txtLine.length());
    }

    /// <summary>
    /// Tries to append a specified block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.END_OF_LINE:
            {
                // When we try to append one EOL to another and there is a closed Quote,
                // it means we have encountered an empty Quote. It has some special cases for
                // blocks that can be `lazy continued`.
                ContainerBlock closedQuote = getParent(BlockType.QUOTE, false);
                if (closedQuote != null)
                {
                    IndentedCodeBlock indentedCodeBlock = ms.as(closedQuote.getLast(), IndentedCodeBlock.class);
                    if (indentedCodeBlock != null)
                    {
                        msArrayList.add(indentedCodeBlock.getContentLines(), block.getContentLine());
                        openParents(BlockType.QUOTE);
                        return true;
                    }

                    ParagraphBlock paraBlock = (ParagraphBlock)closedQuote.getDeepestOpenedChildBlock(BlockType.PARAGRAPH);
                    if (paraBlock != null)
                        paraBlock.close();

                    invertParents(BlockType.QUOTE);
                }

                return false;
            }
            case BlockType.SETEXT_HEADING:
            {
                // A Setext heading can be constituted only from a paragraph block.
                if ((getParent().getType() == BlockType.PARAGRAPH) && SetextHeadingBlock.canSetContent((ParagraphBlock)getParent()))
                    block.setAction(BlockAction.NONE);

                return false;
            }
            case BlockType.TABLE:
            {
                // The row of table can be instantiated only from a paragraph block.
                if (getParent().getType() != BlockType.PARAGRAPH)
                    return false;

                ParagraphBlock paragraph = (ParagraphBlock)getParent();
                if (((TableBlock)block).isValid(paragraph))
                {
                    // The following allows the table to be appended to the parent
                    // paragraph (which will be converted to a row block).
                    block.setAction(BlockAction.NONE);
                    return false;
                }

                // If the table is not valid due to it is inside a Quote and previous paragraph is multiline,
                // then we need to move last added line of the paragraph out of the Quote.
                // Otherwise, the table becomes valid.
                if (paragraph.isInQuote())
                {
                    // Check if the table is at the same Quote level and paragraph is multiline.
                    QuoteBlock parentQuote = (QuoteBlock)paragraph.getParent(BlockType.QUOTE);
                    int lastContentLineIndex = paragraph.getContentLines().size() - 1;
                    if (!parentQuote.isOpened() && (lastContentLineIndex > 0))
                    {
                        parentQuote.closeChildren();
                        // We should move out of the Quote the last content line
                        // to disallow it to constitute a table (if we here, then this is not valid).
                        ContainerBlock containerBlock = paragraph.getTopmostParent(BlockType.QUOTE, false).getParent();

                        // Create new paragraph and move there last content line of previous paragraph.
                        ParagraphBlock newParagraph = new ParagraphBlock();
                        newParagraph.tryParse(paragraph.getContentLines().get(lastContentLineIndex), 0);
                        containerBlock.add(newParagraph).close();
                        paragraph.getContentLines().remove(lastContentLineIndex);

                        // All parent Quotes should be repeated to place a new paragraph (that will be
                        // parsed from this not valid table) into an appropriate nesting level of Quote.
                        ContainerBlock curQuote = parentQuote;
                        while (curQuote != null)
                        {
                            containerBlock = (ContainerBlock)containerBlock.add(new QuoteBlock());
                            curQuote = curQuote.getParent(BlockType.QUOTE, false);
                        }
                    }
                }

                return false;
            }
            case BlockType.QUOTE:
            {
                // Count a nesting level of the quote being appended.
                QuoteBlock quote = (QuoteBlock)getTopmostParent(BlockType.QUOTE, true);
                if (quote != null)
                {
                    quote.close();

                    // To be the same Quote there should not be any other parent blocks (for example, List blocks).
                    Block parent = quote.getParent();
                    if ((parent.getType() == BlockType.QUOTE) && parent.isOpened())
                    {
                        quote.closeChildren();
                        return false;
                    }

                    if (((parent.getType() == BlockType.BULLET_LIST_ITEM) || (parent.getType() == BlockType.ORDERED_LIST_ITEM)) &&
                    (parent.getAction() != BlockAction.REPARSE_LIST_ITEM))
                    {
                        quote.closeChildren();
                        return false;
                    }

                    return true;
                }

                openParents(BlockType.QUOTE);
                return false;
            }
            default:
            {
                // We invert states of parent Quotes to allow append blocks
                // into an appropriate nested Quote level, if any.
                invertParents(BlockType.QUOTE);

                // Also close all child blocks of Quote, except of paragraph as it can be lazy continued.
                ContainerBlock closedQuote = getTopmostParent(BlockType.QUOTE, false);
                if (closedQuote != null)
                    closedQuote.closeChildrenNonInclusive(BlockType.PARAGRAPH);

                // IN: This is a very simplified processing till we are not handling complex blocks nesting,
                // such as Quotes or Lists with Footnotes. Actually, I think we need some processing in MarkdownDocument
                // similar to MarkdownDocument.TryReparseAsListItem().
                if ((block.getType() == BlockType.PARAGRAPH) && (getParent().getType() == BlockType.FOOTNOTE_DEFINITION))
                    getParent().close();

                return false;
            }
        }
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.END_OF_LINE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.NONE; }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of indentation characters.
    /// </summary>
    protected /*override*/ boolean isIndentationChar(char c)
    {
        return false;
    }
}

