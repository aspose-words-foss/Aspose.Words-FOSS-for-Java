// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown LinkText delimiter.
/// </summary>
abstract class LinkTextDelimiter extends Delimiter
{
    /// <summary>
    /// Creates a LinkTextDelimiter object from a delimiter run.
    /// </summary>
    protected LinkTextDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset, 1);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new LinkTextBlock();
    }

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterPriority*/int getPriority()
    {
        return DelimiterPriority.LINK_TEXT;
    }

    /// <summary>
    /// Gets a priority to unlink inside linked delimiters.
    /// </summary>
    /// <remarks>
    /// This is a priority of a linked delimiter below which there can be no other linked delimiters inside.
    /// </remarks>
    /*override*/ /*DelimiterPriority*/int getUnlinkPriority() { return DelimiterPriority.HIGHEST; }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return 1; }
}

