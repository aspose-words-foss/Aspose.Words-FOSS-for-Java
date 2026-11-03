// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/08/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the endnote position.
/// </summary>
/// <seealso cref="EndnoteOptions"/>
public /*enum*/ final class EndnotePosition
{
    private EndnotePosition(){}
    
    /// <summary>
    /// Endnotes are output at the end of the section.
    /// </summary>
    public static final int END_OF_SECTION = 0;
    /// <summary>
    /// Endnotes are output at the end of the document.
    /// </summary>
    public static final int END_OF_DOCUMENT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EndnotePosition value.
    /// </summary>
    public static String getName(/*EndnotePosition*/int endnotePosition)
    {
    	switch (endnotePosition)
    	{
    		case END_OF_SECTION: return "END_OF_SECTION";
    		case END_OF_DOCUMENT: return "END_OF_DOCUMENT";
    		default: return "Unknown EndnotePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EndnotePosition value.
    /// </summary>
    public static String toString(/*EndnotePosition*/int endnotePosition)
    {
    	switch (endnotePosition)
    	{
    		case END_OF_SECTION: return "EndOfSection";
    		case END_OF_DOCUMENT: return "EndOfDocument";
    		default: return "Unknown EndnotePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EndnotePosition name.
    /// </summary>
    public static /*EndnotePosition*/int fromName(String endnotePositionName)
    {
    	if ("END_OF_SECTION".equals(endnotePositionName)) return END_OF_SECTION;
    	if ("END_OF_DOCUMENT".equals(endnotePositionName)) return END_OF_DOCUMENT;
    	throw new IllegalArgumentException("Unknown EndnotePosition name.");
    }
    
    /// <summary>
    /// Returns array of EndnotePosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		END_OF_SECTION,
    		END_OF_DOCUMENT,
    	};
    }

    public static final int length = 2;
}

