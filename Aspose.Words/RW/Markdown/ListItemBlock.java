// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.Debug;

/// <summary>
/// Represents a markdown Bullet or Ordered ListItem block.
/// </summary>
abstract class ListItemBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.SETEXT_HEADING:
            {
                // If SetextHeading has no any content, then we still can't say with certainty that this is
                // actually a heading. For example, it can be a HorizontalRule or just a regular Paragraph.
                if (((SetextHeadingBlock)block).getContentLines().size() > 0)
                    add(block);

                return true;
            }
            case BlockType.BLANK_LINE:
            {
                // Blank line closes an empty list item.
                if (isEmpty() || (this.get(0).getType() == BlockType.END_OF_LINE))
                    return false;

                // We don't want blank lines in our markdown tree, so just add an EOL.
                add(new EndOfLineBlock()).openParents(BlockType.QUOTE);
                return true;
            }
            case BlockType.END_OF_LINE:
            {
                // This is a special case: an empty bullet list cannot interrupt paragraph of the same level.
                // So this bullet list item should be converted to the paragraph continuation.
                if (isEmpty())
                {
                    ParagraphBlock prevParagraphBlock = ms.as(getPreviousSibling(), ParagraphBlock.class);
                    if (prevParagraphBlock != null)
                    {
                        prevParagraphBlock.open();
                        msArrayList.add(prevParagraphBlock.getContentLines(), getOpening());

                        setAction(BlockAction.REMOVE);
                        return true;
                    }
                }

                add(block);
                return true;
            }
            case BlockType.TABLE:
            {
                return false;
            }
            default:
            {
                // Everything can be added to the list item when it is in an appropriate state.
                if (getAction() == BlockAction.REPARSE_LIST_ITEM)
                {
                    // This list item can be inside a Quote, so it might be closed
                    // to account its nesting level and we should open it now.
                    openParents(BlockType.QUOTE);

                    add(block);
                    return true;
                }

                return false;
            }
        }
    }

    /// <summary>
    /// Gets list item level.
    /// </summary>
    int getLevel()
    {
        int level = 0;
        Block parentListItem = getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
        while (parentListItem != null)
        {
            level++;
            parentListItem = parentListItem.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
        }

        return level;
    }

    /// <summary>
    /// Returns a block that contains a list to which this list item belongs.
    /// </summary>
    ContainerBlock getListContainer()
    {
        ListItemBlock firstBlockOfList;

        ListItemBlock curListItem = this;
        do
        {
            firstBlockOfList = curListItem;

            // If a list item is a first child, then this list item starts a new list.
            if (curListItem.isFirstChild())
                break;

            curListItem = (ListItemBlock)curListItem.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
        }
        while (curListItem != null);

        return firstBlockOfList.getParent();
    }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getOpening(String txtLine, int start)
    {
        String opening = super.getOpening(txtLine, start);
        if (opening.length() > 0)
        {
            // There can be one whitespace character after main opening sequence.
            int whitespaceIdx = start + opening.length();
            if (whitespaceIdx < txtLine.length())
            {
                char c = txtLine.charAt(whitespaceIdx);
                if (msChar.isWhiteSpace(c))
                    opening = msString.format("{0}{1}", opening, c);
            }
        }

        return opening;
    }

    /// <summary>
    /// Gets a content part from a text line starting at a specified start position up to a end position.
    /// </summary>
    protected /*override*/ String getContent(String txtLine, int start, int end)
    {
        // ListItem does not have any own text as the content.
        // Instead, it contains other container blocks that constitute its content.
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
    /// Gets an indentation part from a right side of a text line starting at the end
    /// of text up to a specified start position.
    /// </summary>
    protected /*override*/ String getRightIndentation(String txtLine, int start)
    {
        return "";
    }

    /// <summary>
    /// Sets list marker.
    /// </summary>
    protected void setMarker(/*ListMarker*/int marker)
    {
        mMarker = marker;
    }

    /// <summary>
    /// Sets StartAt.
    /// </summary>
    protected void setStartAt(int startAt)
    {
        mStartAt = startAt;
    }

    /// <summary>
    /// Gets list marker.
    /// </summary>
    /*ListMarker*/int getMarker() { return mMarker; }

    /// <summary>
    /// Gets StartAt index.
    /// </summary>
    int getStartAt() { return mStartAt; }

    /// <summary>
    /// Gets length of the list label.
    /// </summary>
    abstract int getListLabelLength();

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets a boolean value indicating either this list item block starts a level.
    /// </summary>
    boolean isLevelStart()
    {
        Debug.assertTrue(getParent() != null);

        for (Block block : getParent())
        {
            if (block == this)
                break;

            if ((block.getType() == BlockType.ORDERED_LIST_ITEM) || (block.getType() == BlockType.BULLET_LIST_ITEM))
                return false;
        }

        return true;
    }

    private /*ListMarker*/int mMarker;
    private int mStartAt;
}

