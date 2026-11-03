// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Asterisk delimiter.
/// </summary>
class AsteriskDelimiter extends EmphasesDelimiter
{
    /// <summary>
    /// Creates an AsteriskDelimiter object from a delimiter run.
    /// </summary>
    public AsteriskDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.ASTERISK; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '*';
}

