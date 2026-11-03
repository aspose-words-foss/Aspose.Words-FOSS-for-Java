// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/03/2011 by Andrey Kamimura

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how headers and footers are exported to HTML, MHTML or EPUB.
/// </summary>
/// <seealso cref="HtmlSaveOptions.ExportHeadersFootersMode"/>
public /*enum*/ final class ExportHeadersFootersMode
{
    private ExportHeadersFootersMode(){}
    
    /// <summary>
    /// Headers and footers are not exported.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Primary headers and footers are exported at the beginning and the end of each section.
    /// </summary>
    public static final int PER_SECTION = 1;
    /// <summary>
    /// Primary header of the first section is exported at the beginning of the document and primary footer is at the end.
    /// </summary>
    public static final int FIRST_SECTION_HEADER_LAST_SECTION_FOOTER = 2;
    /// <summary>
    /// First page header and footer are exported at the beginning and the end of each section.
    /// </summary>
    public static final int FIRST_PAGE_HEADER_FOOTER_PER_SECTION = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ExportHeadersFootersMode value.
    /// </summary>
    public static String getName(/*ExportHeadersFootersMode*/int exportHeadersFootersMode)
    {
    	switch (exportHeadersFootersMode)
    	{
    		case NONE: return "NONE";
    		case PER_SECTION: return "PER_SECTION";
    		case FIRST_SECTION_HEADER_LAST_SECTION_FOOTER: return "FIRST_SECTION_HEADER_LAST_SECTION_FOOTER";
    		case FIRST_PAGE_HEADER_FOOTER_PER_SECTION: return "FIRST_PAGE_HEADER_FOOTER_PER_SECTION";
    		default: return "Unknown ExportHeadersFootersMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ExportHeadersFootersMode value.
    /// </summary>
    public static String toString(/*ExportHeadersFootersMode*/int exportHeadersFootersMode)
    {
    	switch (exportHeadersFootersMode)
    	{
    		case NONE: return "None";
    		case PER_SECTION: return "PerSection";
    		case FIRST_SECTION_HEADER_LAST_SECTION_FOOTER: return "FirstSectionHeaderLastSectionFooter";
    		case FIRST_PAGE_HEADER_FOOTER_PER_SECTION: return "FirstPageHeaderFooterPerSection";
    		default: return "Unknown ExportHeadersFootersMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ExportHeadersFootersMode name.
    /// </summary>
    public static /*ExportHeadersFootersMode*/int fromName(String exportHeadersFootersModeName)
    {
    	if ("NONE".equals(exportHeadersFootersModeName)) return NONE;
    	if ("PER_SECTION".equals(exportHeadersFootersModeName)) return PER_SECTION;
    	if ("FIRST_SECTION_HEADER_LAST_SECTION_FOOTER".equals(exportHeadersFootersModeName)) return FIRST_SECTION_HEADER_LAST_SECTION_FOOTER;
    	if ("FIRST_PAGE_HEADER_FOOTER_PER_SECTION".equals(exportHeadersFootersModeName)) return FIRST_PAGE_HEADER_FOOTER_PER_SECTION;
    	throw new IllegalArgumentException("Unknown ExportHeadersFootersMode name.");
    }
    
    /// <summary>
    /// Returns array of ExportHeadersFootersMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PER_SECTION,
    		FIRST_SECTION_HEADER_LAST_SECTION_FOOTER,
    		FIRST_PAGE_HEADER_FOOTER_PER_SECTION,
    	};
    }

    public static final int length = 4;
}

