// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown ImageDescription opening delimiter.
/// </summary>
class ImageDescriptionOpeningDelimiter extends LinkTextDelimiter
{
    /// <summary>
    /// Creates a LinkTextOpeningDelimiter object from a delimiter run.
    /// </summary>
    ImageDescriptionOpeningDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (other.getType() != DelimiterType.LINK_TEXT_CLOSING)
            return false;

        if (isNotIncluded())
            return false;

        return ImageDescriptionBlock.isValid(this, other);
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.IMAGE_DESCRIPTION_OPENING; }

    /// <summary>
    /// Creates a corresponding markdown inline block. 
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new ImageDescriptionBlock();
    }

    /// <summary>
    /// Gets a corresponding LinkText opening delimiter.
    /// </summary>
    LinkTextOpeningDelimiter getLinkTextOpening()
    {
        Delimiter linkTextOpening = (Delimiter)getNextNode();
        if ((linkTextOpening == null) || linkTextOpening.isNotIncluded())
            return null;

        if (linkTextOpening.getType() != DelimiterType.LINK_TEXT_OPENING)
            return null;

        if ((linkTextOpening.getStart() - 1) != getEnd())
            return null;

        return (LinkTextOpeningDelimiter)linkTextOpening;
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return false; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '!';
}

