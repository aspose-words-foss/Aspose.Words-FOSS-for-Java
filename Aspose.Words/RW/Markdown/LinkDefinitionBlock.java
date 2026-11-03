// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents a markdown Link Reference Definition block.
/// </summary>
class LinkDefinitionBlock extends ContainerBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        String text = trimInPlace(txtLine);

        if (!parse(text, start))
            return false;

        if (!"".equals(getOpening()) && "".equals(getClosing()))
            return true;

        if ((!"".equals(getClosing())))
        {
            setReference(msString.toUpper(normalizeString(text.substring((1), (1) + (mClosingIndex - 1))), msCultureInfo.getInvariantCulture()));
            if (MarkdownUtil.hasNonEscapedCharacters(getReference(), 0, getReference().length() - 1, '[', ']'))
                return false;

            int destinationIndex = mClosingIndex + CLOSING_DELIMITER.length();
            setDestination(msString.trimStart(text.substring((destinationIndex), (destinationIndex) + (text.length() - destinationIndex))));

            if (LinkDestinationBlock.isValid(msString.trimStart(getDestination()), 0, getDestination().length() - 1) ||
                LinkDestinationBlock.isValid(msString.trimStart(getDestination()) + "'", 0, getDestination().length()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        // LinkDefinitionBlock can only be appended externally.
        return false;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context)
    {
        // LinkDefinitionBlock is not written into a model.
    }

    /// <summary>
    /// Gets an opening part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getOpening(String txtLine, int start)
    {
        // Check the Opening fits in txtLine.
        if ((start + OPENING_DELIMITER.length()) > txtLine.length())
            return "";

        int openingIndex = msString.indexOf(txtLine, 
            OPENING_DELIMITER,
            start,
            OPENING_DELIMITER.length(),
            StringComparison.INVARIANT_CULTURE);
        if (openingIndex != 0)
            return "";

        mOpeningIndex = openingIndex;
        mContentIndex = start + OPENING_DELIMITER.length();

        return OPENING_DELIMITER;
    }

    /// <summary>
    /// Gets a closing part from a text line starting at a specified position.
    /// </summary>
    protected /*override*/ String getClosing(String txtLine, int start, int end)
    {
        // The Closing is not allowed without Opening.
        if (mOpeningIndex < 0)
            return "";

        mClosingIndex = msString.indexOf(txtLine, CLOSING_DELIMITER, mContentIndex, StringComparison.INVARIANT_CULTURE);
        if (mClosingIndex <= mContentIndex)
            mClosingIndex = -1;

        return mClosingIndex == -1 ? "" : CLOSING_DELIMITER;
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
    /// Resets parsed states.
    /// </summary>
    void reset()
    {
        removeAllParts();
        mOpeningIndex = -1;
        mClosingIndex = -1;
        mContentIndex = -1;
        setReference("");
        setDestination("");
    }

    /// <summary>
    /// Returns True if the given LinkDefinitionBlock is valid.
    /// </summary>
    boolean isValid()
    {
        return (mOpeningIndex != -1) && (mContentIndex > mOpeningIndex) && (mClosingIndex > mContentIndex) &&
               (getReference().length() > 0) && (getDestination().length() > 0) &&
               LinkDestinationBlock.isValid(getDestination(), 0, getDestination().length() - 1);
    }

    /// <summary>
    /// Gets string representing a Reference part of this reference definition.
    /// </summary>
    /// <remarks>
    /// The reference is actually a ContentLine between opening [ and closing ]: delimiters.
    /// </remarks>
    String getReference() { return mReference; }; private void setReference(String value) { mReference = value; };

    private String mReference;

    /// <summary>
    /// Gets string representing a Destination part of this reference definition.
    /// </summary>
    String getDestination() { return mDestination; }; private void setDestination(String value) { mDestination = value; };

    private String mDestination;

    /// <summary>
    /// Gets LinkDestinationBlock corresponding to current Destination string.
    /// </summary>
    LinkDestinationBlock getLinkDestination()
    {
        if (mLinkDestination == null)
        {
            mLinkDestination = new LinkDestinationBlock();
            mLinkDestination.add(new InlineBlock(getDestination()));
        }

        return mLinkDestination;
    }

    /// <summary>
    /// Gets a boolean value indicating either closing sequence is allowed without an opening one.
    /// </summary>
    protected /*override*/ boolean getAllowClosingWithoutOpening() { return true; }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.LINK_DEFINITION; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets integer value that limits a number of opening characters to search.
    /// </summary>
    protected /*override*/ int getOpeningSearchLimit() { return OPENING_DELIMITER.length(); }

    /// <summary>
    /// Returns the string formatted in compliance with the requirements.
    /// </summary>
    /// <remarks>
    /// A line break is replaced by a space.
    /// Several consecutive spaces are replaced by one space.
    /// </remarks>
    private static String normalizeString(String text)
    {
        while (text.contains("\r") || text.contains("\n"))
        {
            text = text.replace("\r", " ");
            text = text.replace("\n", " ");
        }

        return trimInPlace(text);
    }

    /// <summary>
    /// Replaces multiple consecutive spaces by one space.
    /// </summary>
    private static String trimInPlace(String text)
    {
        String result = msString.trim(text);

        while (result.contains("  "))
            result = result.replace("  ", " ");

        return result;
    }

    /// <summary>
    /// The Opening delimiter.
    /// </summary>
    private static final String OPENING_DELIMITER = "[";

    /// <summary>
    /// The Closing delimiter.
    /// </summary>
    private static final String CLOSING_DELIMITER = "]:";

    /// <summary>
    /// The cached index of Closing delimiter.
    /// </summary>
    private int mOpeningIndex = -1;
    private int mClosingIndex = -1;

    /// <summary>
    /// The cached index of ContentLine.
    /// </summary>
    private int mContentIndex;

    private LinkDestinationBlock mLinkDestination;
}

