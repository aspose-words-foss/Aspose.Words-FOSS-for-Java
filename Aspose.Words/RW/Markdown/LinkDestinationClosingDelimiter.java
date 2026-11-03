// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkDestination closing delimiter.
/// </summary>
class LinkDestinationClosingDelimiter extends LinkDestinationDelimiter
{
    /// <summary>
    /// Creates a LinkDestinationClosingDelimiter object from a delimiter run.
    /// </summary>
    LinkDestinationClosingDelimiter(DelimiterRun delimiterRun, int offset)
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
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.LINK_DESTINATION_CLOSING; }

    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding LinkText.
    /// </summary>
    /*override*/ boolean hasLinkText() { return (isLinked() && ((LinkDestinationDelimiter)getLinkedDelimiter()).hasLinkText()); }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return !isNotIncluded(); }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = ')';
}

