// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkDestination opening delimiter.
/// </summary>
class LinkDestinationOpeningDelimiter extends LinkDestinationDelimiter
{
    /// <summary>
    /// Creates a LinkDestinationOpeningDelimiter object from a delimiter run.
    /// </summary>
    LinkDestinationOpeningDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (other.getType() != DelimiterType.LINK_DESTINATION_CLOSING)
            return false;

        if (isLinked())
            return false;

        if (!LinkDestinationBlock.isValid(this, other))
            return false;

        Delimiter linkTextClosing = getLinkTextClosing();
        if (linkTextClosing == null)
            return false;

        if (linkTextClosing.isLinked())
            return true;

        // Try to Link temporary this LinkDestination to raise priority of the
        // corresponding LinkText and check either it can be linked as well.
        link(other);
        Delimiter linkTextOpening = linkTextClosing.getPotentialOpeningBackward();
        unLink();

        return (linkTextOpening != null);
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.LINK_DESTINATION_OPENING; }

    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding linked LinkText.
    /// </summary>
    /*override*/ boolean hasLinkText()
    {
        Delimiter linkTextClosing = getLinkTextClosing();
        return ((linkTextClosing != null) && (linkTextClosing.isLinked()));
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return false; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '(';
}

