// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkText opening delimiter.
/// </summary>
class LinkTextOpeningDelimiter extends LinkTextDelimiter
{
    /// <summary>
    /// Creates a LinkTextOpeningDelimiter object from a delimiter run.
    /// </summary>
    LinkTextOpeningDelimiter(DelimiterRun delimiterRun, int offset)
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

        if (isImageDescription())
            return false;

        // There cannot be nested links by spec.
        if (MarkdownUtil.getLinkedInRange(DelimiterType.LINK_TEXT_OPENING, this, other) != null)
            return false;

        return LinkTextBlock.isValid(this, other);
    }
    
    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.LINK_TEXT_OPENING; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return false; }

    /// <summary>
    /// Gets a boolean value indicating whether this LinkText is actually a part of ImageDescription.
    /// </summary>
    private boolean isImageDescription()
    {
        if ((getPrevNode() == null) || getPrevNode().isNotIncluded())
            return false;

        Delimiter prevDelimiter = (Delimiter)getPrevNode();
        if (prevDelimiter.getType() != DelimiterType.IMAGE_DESCRIPTION_OPENING)
            return false;

        return ((prevDelimiter.getEnd() + 1) == getStart());
    }
    
    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '[';
}

