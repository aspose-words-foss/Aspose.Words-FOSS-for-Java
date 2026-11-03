// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The type of break at the beginning of the section.
/// </summary>
public /*enum*/ final class SectionStart
{
    private SectionStart(){}
    
    /// <summary>
    /// The new section starts on the same page as the previous section.
    /// </summary>
    public static final int CONTINUOUS = 0;
    /// <summary>
    /// The section starts from a new column.
    /// </summary>
    public static final int NEW_COLUMN = 1;
    /// <summary>
    /// The section starts from a new page.
    /// </summary>
    public static final int NEW_PAGE = 2;
    /// <summary>
    /// The section starts on a new even page.
    /// </summary>
    public static final int EVEN_PAGE = 3;
    /// <summary>
    /// The section starts on a new odd page.
    /// </summary>
    public static final int ODD_PAGE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SectionStart value.
    /// </summary>
    public static String getName(/*SectionStart*/int sectionStart)
    {
    	switch (sectionStart)
    	{
    		case CONTINUOUS: return "CONTINUOUS";
    		case NEW_COLUMN: return "NEW_COLUMN";
    		case NEW_PAGE: return "NEW_PAGE";
    		case EVEN_PAGE: return "EVEN_PAGE";
    		case ODD_PAGE: return "ODD_PAGE";
    		default: return "Unknown SectionStart value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SectionStart value.
    /// </summary>
    public static String toString(/*SectionStart*/int sectionStart)
    {
    	switch (sectionStart)
    	{
    		case CONTINUOUS: return "Continuous";
    		case NEW_COLUMN: return "NewColumn";
    		case NEW_PAGE: return "NewPage";
    		case EVEN_PAGE: return "EvenPage";
    		case ODD_PAGE: return "OddPage";
    		default: return "Unknown SectionStart value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SectionStart name.
    /// </summary>
    public static /*SectionStart*/int fromName(String sectionStartName)
    {
    	if ("CONTINUOUS".equals(sectionStartName)) return CONTINUOUS;
    	if ("NEW_COLUMN".equals(sectionStartName)) return NEW_COLUMN;
    	if ("NEW_PAGE".equals(sectionStartName)) return NEW_PAGE;
    	if ("EVEN_PAGE".equals(sectionStartName)) return EVEN_PAGE;
    	if ("ODD_PAGE".equals(sectionStartName)) return ODD_PAGE;
    	throw new IllegalArgumentException("Unknown SectionStart name.");
    }
    
    /// <summary>
    /// Returns array of SectionStart values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONTINUOUS,
    		NEW_COLUMN,
    		NEW_PAGE,
    		EVEN_PAGE,
    		ODD_PAGE,
    	};
    }

    public static final int length = 5;
}

