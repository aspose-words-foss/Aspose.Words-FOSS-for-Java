// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Emphases delimiter.
/// </summary>
abstract class EmphasesDelimiterBase extends Delimiter
{
    /// <summary>
    /// Creates a EmphasesDelimiter object from a delimiter run.
    /// </summary>
    protected EmphasesDelimiterBase(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset, MAX_LENGTH);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one.
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        if (isNotIncluded())
            return false;

        if (getType() != other.getType())
            return false;

        return ((getFlankingType() == FlankingType.LEFT) || (getFlankingType() == FlankingType.BOTH));
    }

    /// <summary>
    /// Gets a boolean value indicating whether the delimiter is valid.
    /// </summary>
    /*override*/ boolean isValid() { return (getFlankingType() != FlankingType.NONE); }

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterPriority*/int getPriority() { return DelimiterPriority.EMPHASES; }

    /// <summary>
    /// Gets a priority to unlink inside linked delimiters.
    /// </summary>
    /// <remarks>
    /// This is a priority of a linked delimiter below which there can not be other linked delimiters inside.
    /// </remarks>
    /*override*/ /*DelimiterPriority*/int getUnlinkPriority() { return DelimiterPriority.LOWEST; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing()
    {
        if (isNotIncluded())
            return false;

        return ((getFlankingType() == FlankingType.RIGHT) || (getFlankingType() == FlankingType.BOTH));
    }

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter.
    /// </summary>
    private static final int MAX_LENGTH = Integer.MAX_VALUE;
}

