// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown AutoLink opening delimiter.
/// </summary>
class AutoLinkOpeningDelimiter extends AutoLinkDelimiter
{
    /// <summary>
    /// Creates an AutoLinkOpeningDelimiter object from a delimiter run.
    /// </summary>
    AutoLinkOpeningDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a delimiter that can be an Opening for this delimiter.
    /// </summary>
    /*override*/ Delimiter getPotentialOpeningBackward()
    {
        return null;
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (isNotIncluded())
            return false;

        if (other.getType() != DelimiterType.AUTO_LINK_CLOSING)
            return false;

        return AutolinkInlineBlock.isValid(this, other);
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.AUTO_LINK_OPENING; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return false; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '<';
}

