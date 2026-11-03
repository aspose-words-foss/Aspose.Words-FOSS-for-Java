// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.Debug;

/// <summary>
/// Represents a Markdown Paragraph block.
/// </summary>
class ParagraphBlock extends InlineContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        msArrayList.add(getContentLines(), getContentLine());

        return true;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.SETEXT_HEADING:
            {
                // If content was successfully set to a SetextHeading block, then this paragraph is actually
                // a content of the SetextHeading and thus should be removed. Otherwise, this is not
                // a SetextHeading and we should try another block types.
                ((SetextHeadingBlock)block).setContent(this);
                setAction(BlockAction.REMOVE);

                openParentQuotes();

                // The SetextHeading is still not added to a tree.
                return false;
            }
            case BlockType.TABLE:
            {
                Debug.assertTrue(getContentLines().size() > 0);

                String text = getContentLines().get(getContentLines().size() - 1);
                ((TableBlock)block).add(new RowBlock(text));
                getContentLines().remove(getContentLines().size() - 1);
                if (getContentLines().size() == 0)
                    setAction(BlockAction.REMOVE);

                openParentQuotes();

                return false;
            }
            case BlockType.ORDERED_LIST_ITEM:
            {
                // In order to be attracted to a Principle of Uniformity, the spec at https://spec.commonmark.org/
                // says, that: "In order to solve of unwanted lists in paragraphs with hard-wrapped numerals, we allow
                // only lists starting with 1 to interrupt paragraphs."
                OrderedListItemBlock listItem = (OrderedListItemBlock)block;
                if (listItem.getStartAt() != 1)
                {
                    ListItemBlock parentListItem =
                        (ListItemBlock)getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
                    // The spec clause is actual for the very start of the list.
                    if ((parentListItem == null) ||
                        ((!parentListItem.isInList()) && (getListInReparseState(parentListItem) != null)))
                    {
                        // This will allow list item to be re-parsed as some another
                        // block type to be a lazy continuation of the paragraph block.
                        open();
                        block.setAction(BlockAction.EXCLUDE);
                    }
                }

                return false;
            }
            default:
                return super.tryAppend(block);
        }
    }

    /// <summary>
    /// Gets an indentation part from a left side of a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getLeftIndentation(String txtLine, int start)
    {
        return "";
    }

    /// <summary>
    /// Gets an indentation part from a right side of a text line starting at the end
    /// of text up to a specified start position.
    /// </summary>
    protected /*override*/ String getRightIndentation(String txtLine, int start)
    {
        return "";
    }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getOpening(String txtLine, int start)
    {
        return "";
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        return "";
    }

    /// <summary>
    /// Returns parent list that is in 'Reparse' state.
    /// </summary>
    private static ListItemBlock getListInReparseState(Block startBlock)
    {
        ListItemBlock listItem = (startBlock.hasType(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM))
            ? (ListItemBlock)startBlock
            : (ListItemBlock)startBlock.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);

        while (listItem != null)
        {
            if (listItem.getAction() == BlockAction.REPARSE_LIST_ITEM)
                return listItem;

            listItem = (ListItemBlock)listItem.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
        }

        return null;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.PARAGRAPH; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.LEAF; }

    /// <summary>
    /// Gets block indentation.
    /// </summary>
    /*override*/ int getIndentation() { return getContentLineIndentationLength(); }
}

