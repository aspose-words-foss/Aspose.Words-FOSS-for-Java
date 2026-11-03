// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines the footnote position.
/// </summary>
/// <seealso cref="FootnoteOptions"/>
public /*enum*/ final class FootnotePosition
{
    private FootnotePosition(){}
    
    /// <summary>
    /// Footnotes are output at the bottom of each page.
    /// </summary>
    public static final int BOTTOM_OF_PAGE = 1;
    /// <summary>
    /// Footnotes are output beneath text on each page.
    /// </summary>
    public static final int BENEATH_TEXT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FootnotePosition value.
    /// </summary>
    public static String getName(/*FootnotePosition*/int footnotePosition)
    {
    	switch (footnotePosition)
    	{
    		case BOTTOM_OF_PAGE: return "BOTTOM_OF_PAGE";
    		case BENEATH_TEXT: return "BENEATH_TEXT";
    		default: return "Unknown FootnotePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FootnotePosition value.
    /// </summary>
    public static String toString(/*FootnotePosition*/int footnotePosition)
    {
    	switch (footnotePosition)
    	{
    		case BOTTOM_OF_PAGE: return "BottomOfPage";
    		case BENEATH_TEXT: return "BeneathText";
    		default: return "Unknown FootnotePosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FootnotePosition name.
    /// </summary>
    public static /*FootnotePosition*/int fromName(String footnotePositionName)
    {
    	if ("BOTTOM_OF_PAGE".equals(footnotePositionName)) return BOTTOM_OF_PAGE;
    	if ("BENEATH_TEXT".equals(footnotePositionName)) return BENEATH_TEXT;
    	throw new IllegalArgumentException("Unknown FootnotePosition name.");
    }
    
    /// <summary>
    /// Returns array of FootnotePosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM_OF_PAGE,
    		BENEATH_TEXT,
    	};
    }

    public static final int length = 2;
}

