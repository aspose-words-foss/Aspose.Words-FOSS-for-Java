// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Emphases delimiter.
/// </summary>
abstract class EmphasesDelimiter extends EmphasesDelimiterBase
{
    /// <summary>
    /// Creates a EmphasesDelimiter object from a delimiter run.
    /// </summary>
    protected EmphasesDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (!super.canBeOpeningFor(other))
            return false;

        // If one of the delimiters can both open and close emphasis, then the sum of the lengths of 
        // the delimiter runs containing the opening and closing delimiters must not be a multiple 
        // of 3 (rule #9 of the spec). 
        return (((getFlankingType() != FlankingType.BOTH) && (other.getFlankingType() != FlankingType.BOTH)) ||
                (((getLength() + other.getLength()) % 3) != 0));
    }

    /// <summary>
    /// Creates a corresponding markdown inline block. 
    /// </summary>
    /*override*/ Block toBlock()
    {
        return (getLength() == 1) ? (Block)new ItalicInlineBlock() : new BoldInlineBlock();
    }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return 2; }
}

