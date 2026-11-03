// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;

/// <summary>
/// Represents a markdown FencedCode block.
/// </summary>
class FencedCodeBlock extends CodeBlockBase
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;

        if (getOpening().length() < MIN_OPENING_LENGTH)
            return false;

        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        // All the characters in the fence sequence must be the same.
        if (StringUtil.indexOfNotEqualTo(getOpening(), getOpening().charAt(0), 1) != -1)
            return false;

        // Fences with backtick can contain backticks only in Opening.
        if ((getOpening().charAt(0) == '`') && (getContentLine().indexOf('`') != -1))
            return false;

        mInfo = msString.trimStart(getContentLine());
        int infoEnd = StringUtil.indexOfWhitespace(mInfo);
        if (infoEnd != -1)
            mInfo = mInfo.substring((0), (0) + (infoEnd));

        return true;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.FENCED_CODE:
            {
                // We can append closing code fence of the same type as the code block began with
                // (backticks or tildes), and with at least as many backticks or tildes as the opening code fence.
                if ((block.getOpening().length() < getOpening().length()) || (getOpening().charAt(0) != block.getOpening().charAt(0)))
                {
                    block.setAction(BlockAction.EXCLUDE);
                    return false;
                }

                // A closing code fence can be followed only by spaces.
                if (((FencedCodeBlock)block).mInfo.length() > 0)
                {
                    block.setAction(BlockAction.EXCLUDE);
                    return false;
                }

                // This is a valid Closing fence that closes FencedCode.
                close();
                return true;
            }
            case BlockType.INDENTED_CODE:
            case BlockType.ATX_HEADING:
            case BlockType.SETEXT_HEADING:
            case BlockType.QUOTE:
            // WORDSNET-26214 ListItems can be inside FencedCode as well.
            case BlockType.BULLET_LIST_ITEM:
            case BlockType.ORDERED_LIST_ITEM:
            // WORDSNET-27291 HorizontalRule can be inside FencedCode.
            case BlockType.HORIZONTAL_RULE:
            {
                // All blocks are allowed inside a FencedCode. But they should be treated as a simple text.
                // The easiest way to achieve this, is to skip it now and process the block as a regular paragraph later.
                block.setAction(BlockAction.EXCLUDE);
                return false;
            }
            case BlockType.BLANK_LINE:
            case BlockType.PARAGRAPH:
            {
                msArrayList.add(getContentLines(), StringUtil.trimStart(block.getContentLine(), getOpeningIndentationLength()));
                openParentQuotes();
                return true;
            }
            default:
                return super.tryAppend(block);
        }
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        return "";
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return ((c == '`') || (c == '~'));
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.FENCED_CODE; }

    /// <summary>
    /// The line with the opening code fence may optionally contain some text following the code fence. This is
    /// trimmed of leading and trailing whitespace and called the info string.
    /// </summary>
    String getInfo() { return mInfo; }

    private String mInfo;

    /// <summary>
    /// A FencedCode min opening sequence length.
    /// </summary>
    private static final int MIN_OPENING_LENGTH = 3;
}

