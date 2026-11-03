// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown InlineCode delimiter.
/// </summary>
class InlineCodeDelimiter extends Delimiter
{
    /// <summary>
    /// Creates an InlineCodeDelimiter object from a delimiter run.
    /// </summary>
    public InlineCodeDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset, MAX_LENGTH);
	
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new InlineCodeBlock(getLength());
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one.
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (getType() != other.getType())
            return false;

        if (isNotIncluded())
            return false;

        // The lengths of opening and closing sequences of InlineCode delimiters must be equal.
        if (getLength() != other.getLength())
            return false;

        // The opening of the InlineCode block must be unescaped.
        return (!isEscaped());
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.INLINE_CODE; }

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterPriority*/int getPriority() { return DelimiterPriority.INLINE_CODE; }

    /// <summary>
    /// Gets a priority to unlink inside linked delimiters.
    /// </summary>
    /// <remarks>
    /// This is a priority of a linked delimiter below which there can be no other linked delimiters inside.
    /// </remarks>
    /*override*/ /*DelimiterPriority*/int getUnlinkPriority() { return DelimiterPriority.HIGHEST; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return !isNotIncluded(); }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected /*override*/ int getMaxLinkedLength() { return Integer.MAX_VALUE; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '`';

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter.
    /// </summary>
    private static final int MAX_LENGTH = Integer.MAX_VALUE;
}

