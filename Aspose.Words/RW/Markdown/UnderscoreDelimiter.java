// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Underscore delimiter.
/// </summary>
class UnderscoreDelimiter extends EmphasesDelimiter
{
    /// <summary>
    /// Creates an UnderscoreDelimiter object from a delimiter run.
    /// </summary>
    public UnderscoreDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating whether the delimiter is valid.
    /// </summary>
    /*override*/ boolean isValid()
    {
        // Underscore delimiters are not allowed intraword.
        if (isIntraword())
            return false;

        return super.isValid();
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.UNDERSCORE; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '_';
}

