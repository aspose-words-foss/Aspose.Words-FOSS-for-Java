// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown StrikeThrough delimiter.
/// </summary>
class StrikeThroughDelimiter extends EmphasesDelimiterBase
{
    /// <summary>
    /// Creates a StrikeThroughDelimiter object from a delimiter run.
    /// </summary>
    public StrikeThroughDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block. 
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new StrikethroughInlineBlock();
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        // The length of Strikethrough delimiter cannot be less than 2.
        if ((getLength() < 2) || (other.getLength() < 2))
            return false;

        return super.canBeOpeningFor(other);
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.STRIKE_THROUGH; }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return 2; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '~';
}

