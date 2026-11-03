// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkText closing delimiter.
/// </summary>
class LinkTextClosingDelimiter extends LinkTextDelimiter
{
    /// <summary>
    /// Creates a LinkTextClosingDelimiter object from a delimiter run.
    /// </summary>
    LinkTextClosingDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one.
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        return false;
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.LINK_TEXT_CLOSING; }


    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding LinkDestination.
    /// </summary>
    boolean hasLinkDestination()
    {
        if ((getNextNode() == null) || getNextNode().isNotIncluded())
            return false;

        Delimiter nextDelimiter = (Delimiter)getNextNode();
        if ((nextDelimiter.getType() != DelimiterType.LINK_DESTINATION_OPENING) || !nextDelimiter.isLinked())
            return false;

        return ((nextDelimiter.getStart() - 1) == getEnd());
    }

    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding reference LinkText.
    /// </summary>
    boolean hasReferenceLinkText()
    {
        if ((getNextNode() == null) || getNextNode().isNotIncluded())
            return false;

        Delimiter nextDelimiter = (Delimiter)getNextNode();
        if ((nextDelimiter.getType() != DelimiterType.LINK_TEXT_OPENING) || !nextDelimiter.isLinked())
            return false;

        return ((nextDelimiter.getStart() - 1) == getEnd());
    }

    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding collapsed LinkText.
    /// </summary>
    boolean hasCollapsedLinkText()
    {
        if ((getPrevNode() == null) || getPrevNode().isNotIncluded() || (getPrevNode().getPrevNode() == null))
            return false;

        Delimiter prevDelimiter = (Delimiter)getPrevNode().getPrevNode();
        if ((prevDelimiter.getType() != DelimiterType.LINK_TEXT_CLOSING) || !prevDelimiter.isLinked())
            return false;

        return ((prevDelimiter.getEnd() + 2) == getEnd());
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return !isNotIncluded(); }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = ']';
}

