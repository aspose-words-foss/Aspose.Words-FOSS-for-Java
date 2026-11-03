// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown AutoLink delimiter.
/// </summary>
abstract class AutoLinkDelimiter extends Delimiter
{
    /// <summary>
    /// Creates an AutoLinkDelimiter object from a delimiter run.
    /// </summary>
    protected AutoLinkDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset, MAX_LENGTH);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new AutolinkInlineBlock();
    }

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterPriority*/int getPriority() { return DelimiterPriority.AUTO_LINK; }

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

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter.
    /// </summary>
    private static final int MAX_LENGTH = 1;
}

