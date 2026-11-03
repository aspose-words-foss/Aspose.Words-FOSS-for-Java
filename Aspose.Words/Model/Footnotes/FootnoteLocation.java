// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the footnote or endnote position.
/// </summary>
/*enum*/ final class FootnoteLocation
{
    private FootnoteLocation(){}
    
    /// <summary>
    /// Footnotes are output at the bottom of each page. Valid for footnotes only.
    /// </summary>
    public static final int BOTTOM_OF_PAGE = 1;
    /// <summary>
    /// Footnotes are output beneath text on each page. Valid for footnotes only.
    /// </summary>
    public static final int BENEATH_TEXT = 2;
    /// <summary>
    /// Endnotes are output at the end of the section. Valid for endnotes only.
    /// </summary>
    public static final int END_OF_SECTION = 0;
    /// <summary>
    /// Endnotes are output at the end of the document. Valid for endnotes only.
    /// </summary>
    public static final int END_OF_DOCUMENT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FootnoteLocation value.
    /// </summary>
    public static String getName(/*FootnoteLocation*/int footnoteLocation)
    {
    	switch (footnoteLocation)
    	{
    		case BOTTOM_OF_PAGE: return "BOTTOM_OF_PAGE";
    		case BENEATH_TEXT: return "BENEATH_TEXT";
    		case END_OF_SECTION: return "END_OF_SECTION";
    		case END_OF_DOCUMENT: return "END_OF_DOCUMENT";
    		default: return "Unknown FootnoteLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FootnoteLocation value.
    /// </summary>
    public static String toString(/*FootnoteLocation*/int footnoteLocation)
    {
    	switch (footnoteLocation)
    	{
    		case BOTTOM_OF_PAGE: return "BottomOfPage";
    		case BENEATH_TEXT: return "BeneathText";
    		case END_OF_SECTION: return "EndOfSection";
    		case END_OF_DOCUMENT: return "EndOfDocument";
    		default: return "Unknown FootnoteLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FootnoteLocation name.
    /// </summary>
    public static /*FootnoteLocation*/int fromName(String footnoteLocationName)
    {
    	if ("BOTTOM_OF_PAGE".equals(footnoteLocationName)) return BOTTOM_OF_PAGE;
    	if ("BENEATH_TEXT".equals(footnoteLocationName)) return BENEATH_TEXT;
    	if ("END_OF_SECTION".equals(footnoteLocationName)) return END_OF_SECTION;
    	if ("END_OF_DOCUMENT".equals(footnoteLocationName)) return END_OF_DOCUMENT;
    	throw new IllegalArgumentException("Unknown FootnoteLocation name.");
    }
    
    /// <summary>
    /// Returns array of FootnoteLocation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM_OF_PAGE,
    		BENEATH_TEXT,
    		END_OF_SECTION,
    		END_OF_DOCUMENT,
    	};
    }

    public static final int length = 4;
}

