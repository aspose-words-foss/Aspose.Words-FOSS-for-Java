// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Identifies the type of header or footer found in a Word file.
/// </summary>
/// <rev>
/// This is a per section header/footer.
/// Do not renumber as the value of the enum used as an index into plcfhdd.
/// </rev>
public /*enum*/ final class HeaderFooterType
{
    private HeaderFooterType(){}
    
    /// <summary>
    /// Header for even numbered pages.
    /// </summary>
    public static final int HEADER_EVEN = 0;
    /// <summary>
    /// Primary header, also used for odd numbered pages.
    /// </summary>
    public static final int HEADER_PRIMARY = 1;
    /// <summary>
    /// Footer for even numbered pages.
    /// </summary>
    public static final int FOOTER_EVEN = 2;
    /// <summary>
    /// Primary footer, also used for odd numbered pages.
    /// </summary>
    public static final int FOOTER_PRIMARY = 3;
    /// <summary>
    /// Header for the first page of the section.
    /// </summary>
    public static final int HEADER_FIRST = 4;
    /// <summary>
    /// Footer for the first page of the section.
    /// </summary>
    public static final int FOOTER_FIRST = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HeaderFooterType value.
    /// </summary>
    public static String getName(/*HeaderFooterType*/int headerFooterType)
    {
    	switch (headerFooterType)
    	{
    		case HEADER_EVEN: return "HEADER_EVEN";
    		case HEADER_PRIMARY: return "HEADER_PRIMARY";
    		case FOOTER_EVEN: return "FOOTER_EVEN";
    		case FOOTER_PRIMARY: return "FOOTER_PRIMARY";
    		case HEADER_FIRST: return "HEADER_FIRST";
    		case FOOTER_FIRST: return "FOOTER_FIRST";
    		default: return "Unknown HeaderFooterType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HeaderFooterType value.
    /// </summary>
    public static String toString(/*HeaderFooterType*/int headerFooterType)
    {
    	switch (headerFooterType)
    	{
    		case HEADER_EVEN: return "HeaderEven";
    		case HEADER_PRIMARY: return "HeaderPrimary";
    		case FOOTER_EVEN: return "FooterEven";
    		case FOOTER_PRIMARY: return "FooterPrimary";
    		case HEADER_FIRST: return "HeaderFirst";
    		case FOOTER_FIRST: return "FooterFirst";
    		default: return "Unknown HeaderFooterType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HeaderFooterType name.
    /// </summary>
    public static /*HeaderFooterType*/int fromName(String headerFooterTypeName)
    {
    	if ("HEADER_EVEN".equals(headerFooterTypeName)) return HEADER_EVEN;
    	if ("HEADER_PRIMARY".equals(headerFooterTypeName)) return HEADER_PRIMARY;
    	if ("FOOTER_EVEN".equals(headerFooterTypeName)) return FOOTER_EVEN;
    	if ("FOOTER_PRIMARY".equals(headerFooterTypeName)) return FOOTER_PRIMARY;
    	if ("HEADER_FIRST".equals(headerFooterTypeName)) return HEADER_FIRST;
    	if ("FOOTER_FIRST".equals(headerFooterTypeName)) return FOOTER_FIRST;
    	throw new IllegalArgumentException("Unknown HeaderFooterType name.");
    }
    
    /// <summary>
    /// Returns array of HeaderFooterType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HEADER_EVEN,
    		HEADER_PRIMARY,
    		FOOTER_EVEN,
    		FOOTER_PRIMARY,
    		HEADER_FIRST,
    		FOOTER_FIRST,
    	};
    }

    public static final int length = 6;
}

