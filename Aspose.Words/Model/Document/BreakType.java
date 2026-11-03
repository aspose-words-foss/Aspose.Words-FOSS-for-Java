// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type of a break inside a document.
/// </summary>
public /*enum*/ final class BreakType
{
    private BreakType(){}
    
    /// <summary>
    /// Break between paragraphs.
    /// </summary>
    public static final int PARAGRAPH_BREAK = 0;
    /// <summary>
    /// Explicit page break.
    /// </summary>
    public static final int PAGE_BREAK = 1;
    /// <summary>
    /// Explicit column break.
    /// </summary>
    public static final int COLUMN_BREAK = 2;
    /// <summary>
    /// Specifies start of new section on the same page as the previous section.
    /// </summary>
    public static final int SECTION_BREAK_CONTINUOUS = 3;
    /// <summary>
    /// Specifies start of new section in the new column.
    /// </summary>
    public static final int SECTION_BREAK_NEW_COLUMN = 4;
    /// <summary>
    /// Specifies start of new section on a new page.
    /// </summary>
    public static final int SECTION_BREAK_NEW_PAGE = 5;
    /// <summary>
    /// Specifies start of new section on a new even page.
    /// </summary>
    public static final int SECTION_BREAK_EVEN_PAGE = 6;
    /// <summary>
    /// Specifies start of new section on a odd page.
    /// </summary>
    public static final int SECTION_BREAK_ODD_PAGE = 7;
    /// <summary>
    /// Explicit line break.
    /// </summary>
    public static final int LINE_BREAK = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BreakType value.
    /// </summary>
    public static String getName(/*BreakType*/int breakType)
    {
    	switch (breakType)
    	{
    		case PARAGRAPH_BREAK: return "PARAGRAPH_BREAK";
    		case PAGE_BREAK: return "PAGE_BREAK";
    		case COLUMN_BREAK: return "COLUMN_BREAK";
    		case SECTION_BREAK_CONTINUOUS: return "SECTION_BREAK_CONTINUOUS";
    		case SECTION_BREAK_NEW_COLUMN: return "SECTION_BREAK_NEW_COLUMN";
    		case SECTION_BREAK_NEW_PAGE: return "SECTION_BREAK_NEW_PAGE";
    		case SECTION_BREAK_EVEN_PAGE: return "SECTION_BREAK_EVEN_PAGE";
    		case SECTION_BREAK_ODD_PAGE: return "SECTION_BREAK_ODD_PAGE";
    		case LINE_BREAK: return "LINE_BREAK";
    		default: return "Unknown BreakType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BreakType value.
    /// </summary>
    public static String toString(/*BreakType*/int breakType)
    {
    	switch (breakType)
    	{
    		case PARAGRAPH_BREAK: return "ParagraphBreak";
    		case PAGE_BREAK: return "PageBreak";
    		case COLUMN_BREAK: return "ColumnBreak";
    		case SECTION_BREAK_CONTINUOUS: return "SectionBreakContinuous";
    		case SECTION_BREAK_NEW_COLUMN: return "SectionBreakNewColumn";
    		case SECTION_BREAK_NEW_PAGE: return "SectionBreakNewPage";
    		case SECTION_BREAK_EVEN_PAGE: return "SectionBreakEvenPage";
    		case SECTION_BREAK_ODD_PAGE: return "SectionBreakOddPage";
    		case LINE_BREAK: return "LineBreak";
    		default: return "Unknown BreakType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BreakType name.
    /// </summary>
    public static /*BreakType*/int fromName(String breakTypeName)
    {
    	if ("PARAGRAPH_BREAK".equals(breakTypeName)) return PARAGRAPH_BREAK;
    	if ("PAGE_BREAK".equals(breakTypeName)) return PAGE_BREAK;
    	if ("COLUMN_BREAK".equals(breakTypeName)) return COLUMN_BREAK;
    	if ("SECTION_BREAK_CONTINUOUS".equals(breakTypeName)) return SECTION_BREAK_CONTINUOUS;
    	if ("SECTION_BREAK_NEW_COLUMN".equals(breakTypeName)) return SECTION_BREAK_NEW_COLUMN;
    	if ("SECTION_BREAK_NEW_PAGE".equals(breakTypeName)) return SECTION_BREAK_NEW_PAGE;
    	if ("SECTION_BREAK_EVEN_PAGE".equals(breakTypeName)) return SECTION_BREAK_EVEN_PAGE;
    	if ("SECTION_BREAK_ODD_PAGE".equals(breakTypeName)) return SECTION_BREAK_ODD_PAGE;
    	if ("LINE_BREAK".equals(breakTypeName)) return LINE_BREAK;
    	throw new IllegalArgumentException("Unknown BreakType name.");
    }
    
    /// <summary>
    /// Returns array of BreakType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PARAGRAPH_BREAK,
    		PAGE_BREAK,
    		COLUMN_BREAK,
    		SECTION_BREAK_CONTINUOUS,
    		SECTION_BREAK_NEW_COLUMN,
    		SECTION_BREAK_NEW_PAGE,
    		SECTION_BREAK_EVEN_PAGE,
    		SECTION_BREAK_ODD_PAGE,
    		LINE_BREAK,
    	};
    }

    public static final int length = 9;
}

