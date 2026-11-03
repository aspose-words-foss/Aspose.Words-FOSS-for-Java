// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2010 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies how the document is split into parts when saving to <see cref="SaveFormat.Html"/>,
/// <see cref="SaveFormat.Epub"/> or <see cref="SaveFormat.Azw3"/> format.
/// </summary>
/// <remarks>
/// <p><see cref="DocumentSplitCriteria" /> is a set of flags which can be combined. For instance you can split the document
/// at page breaks and heading paragraphs in the same export operation.</p>
/// 
/// <p>Different criteria can partially overlap. For instance, <b>Heading 1</b> style is frequently given 
/// <see cref="ParagraphFormat.PageBreakBefore" /> property so it falls under two criteria: <see cref="PageBreak" /> and 
/// <see cref="HeadingParagraph" />. Some section breaks can cause page breaks and so on. 
/// In typical cases specifying only one flag is the most practical option.</p>
/// 
/// <seealso cref="HtmlSaveOptions.DocumentSplitCriteria"/>
/// </remarks>
public /*enum*/ final class DocumentSplitCriteria
{
    private DocumentSplitCriteria(){}
    
    /// <summary>
    /// The document is not split.
    /// </summary>
    public static final int NONE = 0x0000;
    /// <summary>
    /// The document is split into parts at explicit page breaks.
    /// A page break can be specified by a <see cref="ControlChar.PageBreak"/> character, 
    /// a section break specifying start of new section on a new page,
    /// or a paragraph that has its <see cref="ParagraphFormat.PageBreakBefore" /> property set to <c>true</c>.
    /// </summary>
    public static final int PAGE_BREAK = 0x0001;
    /// <summary>
    /// The document is split into parts at column breaks.
    /// A column break can be specified by a <see cref="ControlChar.ColumnBreak"/> character or
    /// a section break specifying start of new section in a new column.
    /// </summary>
    public static final int COLUMN_BREAK = 0x0002;
    /// <summary>
    /// The document is split into parts at a section break of any type.
    /// </summary>
    public static final int SECTION_BREAK = 0x0004;
    /// <summary>
    /// The document is split into parts at a paragraph formatted using a heading style <b>Heading 1</b>, <b>Heading 2</b> etc. 
    /// Use together with <see cref="HtmlSaveOptions.DocumentSplitHeadingLevel"/> to specify the heading levels 
    /// (from 1 to the specified level) at which to split.
    /// </summary>
    public static final int HEADING_PARAGRAPH = 0x0008;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentSplitCriteria value.
    /// </summary>
    public static String getName(/*DocumentSplitCriteria*/int documentSplitCriteria)
    {
    	switch (documentSplitCriteria)
    	{
    		case NONE: return "NONE";
    		case PAGE_BREAK: return "PAGE_BREAK";
    		case COLUMN_BREAK: return "COLUMN_BREAK";
    		case SECTION_BREAK: return "SECTION_BREAK";
    		case HEADING_PARAGRAPH: return "HEADING_PARAGRAPH";
    		default: return "Unknown DocumentSplitCriteria value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer DocumentSplitCriteria value.
    /// </summary>
    public static Set<String> getNames(/*DocumentSplitCriteria*/int documentSplitCriteria)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & documentSplitCriteria) == NONE) names.add("NONE");
    	if ((PAGE_BREAK & documentSplitCriteria) == PAGE_BREAK) names.add("PAGE_BREAK");
    	if ((COLUMN_BREAK & documentSplitCriteria) == COLUMN_BREAK) names.add("COLUMN_BREAK");
    	if ((SECTION_BREAK & documentSplitCriteria) == SECTION_BREAK) names.add("SECTION_BREAK");
    	if ((HEADING_PARAGRAPH & documentSplitCriteria) == HEADING_PARAGRAPH) names.add("HEADING_PARAGRAPH");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentSplitCriteria value.
    /// </summary>
    public static String toString(/*DocumentSplitCriteria*/int documentSplitCriteria)
    {
    	switch (documentSplitCriteria)
    	{
    		case NONE: return "None";
    		case PAGE_BREAK: return "PageBreak";
    		case COLUMN_BREAK: return "ColumnBreak";
    		case SECTION_BREAK: return "SectionBreak";
    		case HEADING_PARAGRAPH: return "HeadingParagraph";
    		default: return toStringSet(documentSplitCriteria);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentSplitCriteria value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by DocumentSplitCriteria name.
    /// </summary>
    public static /*DocumentSplitCriteria*/int fromName(String documentSplitCriteriaName)
    {
    	if ("NONE".equals(documentSplitCriteriaName)) return NONE;
    	if ("PAGE_BREAK".equals(documentSplitCriteriaName)) return PAGE_BREAK;
    	if ("COLUMN_BREAK".equals(documentSplitCriteriaName)) return COLUMN_BREAK;
    	if ("SECTION_BREAK".equals(documentSplitCriteriaName)) return SECTION_BREAK;
    	if ("HEADING_PARAGRAPH".equals(documentSplitCriteriaName)) return HEADING_PARAGRAPH;
    	throw new IllegalArgumentException("Unknown DocumentSplitCriteria name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by DocumentSplitCriteria names.
    /// </summary>
    public static /*DocumentSplitCriteria*/int fromNames(Set<String> documentSplitCriteriaNames)
    {
    	/*DocumentSplitCriteria*/int result = 0;
    	for (String name : documentSplitCriteriaNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of DocumentSplitCriteria values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PAGE_BREAK,
    		COLUMN_BREAK,
    		SECTION_BREAK,
    		HEADING_PARAGRAPH,
    	};
    }

    public static final int length = 5;
}

