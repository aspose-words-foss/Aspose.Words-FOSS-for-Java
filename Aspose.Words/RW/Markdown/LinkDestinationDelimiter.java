// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkDestination delimiter.
/// </summary>
abstract class LinkDestinationDelimiter extends Delimiter
{
    /// <summary>
    /// Creates a LinkDestinationDelimiter object from a delimiter run.
    /// </summary>
    protected LinkDestinationDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset, MAX_LENGTH);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new LinkDestinationBlock();
    }

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterPriority*/int getPriority() { return DelimiterPriority.LINK_DESTINATION; }

    /// <summary>
    /// Gets a priority to unlink inside linked delimiters.
    /// </summary>
    /// <remarks>
    /// This is a priority of a linked delimiter below which there can be no other linked delimiters inside.
    /// </remarks>
    /*override*/ /*DelimiterPriority*/int getUnlinkPriority() { return DelimiterPriority.HIGHEST; }

    /// <summary>
    /// Gets a boolean value indicating whether it has a corresponding LinkText.
    /// </summary>
    abstract boolean hasLinkText();

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return 1; }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter.
    /// </summary>
    private static final int MAX_LENGTH = 1;
}

