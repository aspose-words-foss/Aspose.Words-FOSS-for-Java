// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;

/// <summary>
/// Represents a markdown SetextHeading block.
/// </summary>
class SetextHeadingBlock extends HeadingBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        if (getOpening().length() == 0)
            return false;

        // It is not allowed to have different characters in opening sequence.
        char openingChar = getOpening().charAt(0);
        for (int i = 1; i < getOpening().length(); i++)
        {
            if (getOpening().charAt(i) != openingChar)
                return false;
        }

        // SetextHeading MUST not have anything after opening sequence.
        if (!StringUtil.containsOnlyWhitespaces(getContentLine()) || !StringUtil.containsOnlyWhitespaces(getClosing()))
            return false;

        // SetextHeading MUST have a content. So, set an initial state to exclude this block
        // from a markdown document tree until it is empty.
        setAction(BlockAction.EXCLUDE);

        setLevel((openingChar == HEADING_1_CHAR) ? 1 : 2);

        return true;
    }

    /// <summary>
    /// Sets a content of a specified InlineContainer to the SetextHeading.
    /// </summary>
    void setContent(InlineContainerBlock inlineContainer)
    {
        msArrayList.addRange(getContentLines(), inlineContainer.getContentLines());
        
        applyOpeningIndentation(inlineContainer);
    }

    /// <summary>
    /// Returns true, if a content of a specified inline container can be set to this block.
    /// </summary>
    static boolean canSetContent(InlineContainerBlock inlineContainer)
    {
        // A SetextHeading opening sequence MUST be at the same nesting level as its content if it is inside a Quote.
        if (inlineContainer.isInQuote() && (inlineContainer.getParent(BlockType.QUOTE, true) != null))
            return false;

        // A SetextHeading opening sequence can constitute a SetextHeading
        // with a list item only when it falls into this list item too.     
        ListItemBlock listItemBlock =
            (ListItemBlock)inlineContainer.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);
        if ((listItemBlock != null) && (listItemBlock.getAction() != BlockAction.REPARSE_LIST_ITEM))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == HEADING_1_CHAR) || (c == HEADING_2_CHAR));
    }

    /// <summary>
    /// Applies an opening indentation from a specified block.
    /// </summary>
    private void applyOpeningIndentation(Block block)
    {
        mOriginalOpeningIndentation = getOpeningIndentation();
        setOpeningIndentation(block.getOpeningIndentation());
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.SETEXT_HEADING; }

    /// <summary>
    /// Gets total length of all block parts.
    /// </summary>
    /*override*/ int getLength() { return (getOriginalOpeningIndentation().length() + getOpening().length()); }

    /// <summary>
    /// Gets block indentation.
    /// </summary>
    /*override*/ int getIndentation()
    {
        int indentation = getOpeningIndentationLength();
        if (getContentLines().size() > 0)
        {
            String contentLine = getContentLines().get(0);
            int nonWhitespaceIndex = StringUtil.indexOfNonWhitespace(contentLine);
            if (nonWhitespaceIndex != -1)
                indentation += MarkdownUtil.getLength(contentLine.substring((0), (0) + (nonWhitespaceIndex)));
        }

        return indentation;
    }

    /// <summary>
    /// Gets original opening indentation of the block it has before the content was applied.
    /// </summary>
    /// <remarks>
    /// When SetextHeading gets its content with <see cref="SetContent"/> method, an opening
    /// indentation is also changed to an indentation of a block being set as a new content.
    /// </remarks>
    private String getOriginalOpeningIndentation() { return (mOriginalOpeningIndentation == null) ? getOpeningIndentation() : mOriginalOpeningIndentation; }

    static final char HEADING_1_CHAR = '=';
    static final char HEADING_2_CHAR = '-';

    private String mOriginalOpeningIndentation;
}

