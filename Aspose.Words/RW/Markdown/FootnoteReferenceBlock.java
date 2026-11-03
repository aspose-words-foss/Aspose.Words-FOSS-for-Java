// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msChar;
import com.aspose.ArrayUtil;
import java.util.ArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a markdown FootnoteReference block.
/// </summary>
class FootnoteReferenceBlock extends ContainerBlock
{
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
        Debug.assertTrue(isOpened() && (block != null));

        add(block);
        return true;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        Debug.assertTrue(context != null);

        // If there is no an appropriate FootnoteDefinition, then write it as just a simple inline text.
        if (getDefinitionBlock() == null)
        {
            context.getBuilder().write(getText());
        }
        else
        {
            context.open(this);
            for (Block definitionBlockChild : (Iterable<Block>) getDefinitionBlock())
                definitionBlockChild.write(context);
            context.close(this);
        }
    }

    /// <summary>
    /// Returns true if the specified delimiters can constitute a valid Footnote reference.
    /// </summary>
    static boolean isValid(FootnoteOpeningDelimiter opening, Delimiter closing)
    {
        Debug.assertTrue((opening != null) && (closing != null) && opening.isBefore(closing));

        return ((opening.getLinkTextOpening() != null) && isValid(opening.getText(), opening.getEnd() + 1, closing.getStart() - 1));
    }

    /// <summary>
    /// Returns true if the specified text within the specified bounds can contain a valid Footnote reference.
    /// </summary>
    static boolean isValid(String text, int startIndex, int endIndex)
    {
        final int MIN_REFERENCE_LENGTH = 1;

        Debug.assertTrue((text != null) && (startIndex >= 0) && (startIndex < text.length()) &&
            (endIndex >= 0) && (endIndex < text.length()));

        if ((endIndex - startIndex + 1) < MIN_REFERENCE_LENGTH)
            return false;

        for (int i = startIndex; i <= endIndex; i++)
        {
            if (msChar.isWhiteSpace(text.charAt(i)) || ArrayUtil.findCharInArray(G_UNALLOWABLE_SPECIAL_CHARS, text.charAt(i)))
                return false;
        }
        return true;
    }

    /// <summary>
    /// Resolves this FootnoteReference by binding it with the first occurred in a specified collection
    /// of FootnoteDefinitions with the same Reference.
    /// </summary>
    void resolve(ArrayList<FootnoteDefinitionBlock> footnoteDefinitionBlocks)
    {
        Debug.assertTrue(footnoteDefinitionBlocks != null);

        // The different markdown editors process definitions with duplicated references in a different ways.
        // As we are attracting preferably to GitLab, we take the first Definition we encounter.
        for (FootnoteDefinitionBlock definitionBlock : footnoteDefinitionBlocks)
        {
            if (setDefinition(definitionBlock))
                break;
        }
    }

    /// <summary>
    /// Sets FootnoteDefinitionBlock to this object if the References are equal.
    /// </summary>
    private boolean setDefinition(FootnoteDefinitionBlock definitionBlock)
    {
        Debug.assertTrue(definitionBlock != null);

        if (!msString.equals(definitionBlock.getReference(), getReference()))
            return false;

        setDefinitionBlock(definitionBlock);
        return true;
    }

    /// <summary>
    /// Gets text of the block.
    /// </summary>
    /*override*/ String getText() { return msString.format("{0}{1}{2}", OPENING_DELIMITER, getReference(), CLOSING_DELIMITER); }

    /// <summary>
    /// A type of the block.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.FOOTNOTE_REFERENCE; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets string representing a Reference part of this FootnoteReferenceBlock.
    /// </summary>
    /// <remarks>This is a text between opening [^ and closing ]: delimiters.</remarks>
    private String getReference() { return super.getText(); }

    /// <summary>
    /// Gets a corresponding FootnoteDefinition block.
    /// </summary>
    private FootnoteDefinitionBlock getDefinitionBlock() { return mDefinitionBlock; }; private void setDefinitionBlock(FootnoteDefinitionBlock value) { mDefinitionBlock = value; };

    private FootnoteDefinitionBlock mDefinitionBlock;

    /// <summary>
    /// Unallowable special characters.
    /// </summary>
    private static /*final*/ char[] G_UNALLOWABLE_SPECIAL_CHARS = { '~', '`', '!', ':', '&', '*', '_', '<', '\\', '[',
        ']', '{', '}' };

    /// <summary>
    /// The Opening delimiter.
    /// </summary>
    static final String OPENING_DELIMITER = "[^";

    /// <summary>
    /// The Closing delimiter.
    /// </summary>
    static final String CLOSING_DELIMITER = "]";
}

