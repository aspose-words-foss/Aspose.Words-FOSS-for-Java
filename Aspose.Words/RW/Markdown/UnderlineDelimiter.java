// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Underline delimiter.
/// </summary>
class UnderlineDelimiter extends EmphasesDelimiterBase
{
    /// <summary>
    /// Creates a UnderlineDelimiter object from a delimiter run.
    /// </summary>
    public UnderlineDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new UnderlineInlineBlock();
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one.
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        // The length of Underline delimiter cannot be less than 2.
        if ((getLength() < 2) || (other.getLength() < 2))
            return false;

        return super.canBeOpeningFor(other);
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.UNDERLINE; }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return 2; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '+';
}

