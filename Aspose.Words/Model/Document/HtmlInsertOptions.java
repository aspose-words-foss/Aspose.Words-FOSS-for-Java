// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/04/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies options for the <see cref="DocumentBuilder.InsertHtml(string, HtmlInsertOptions)"/> method.
/// </summary>
public /*enum*/ final class HtmlInsertOptions
{
    private HtmlInsertOptions(){}
    
    /// <summary>
    /// Use the default options when inserting HTML.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Use font and paragraph formatting specified in <see cref="DocumentBuilder"/> as base formatting for text
    /// inserted from HTML.
    /// </summary>
    /// <remarks>
    /// <para>
    /// If this option is not specified, formatting of <see cref="DocumentBuilder"/> is ignored and text is inserted
    /// with default HTML formatting. As a result, the text looks as it is rendered in browsers.
    /// </para>
    /// <para>
    /// If this option is specified, formatting of inserted text is based on formatting specified in
    /// <see cref="DocumentBuilder"/>, and the text looks as if it were inserted using <see cref="DocumentBuilder.Write(String)"/>.
    /// </para>
    /// </remarks>
    public static final int USE_BUILDER_FORMATTING = 1;

    /// <summary>
    /// Remove the empty paragraph that is normally inserted after HTML that ends with a block-level element.
    /// </summary>
    /// <remarks>
    /// By default, <see cref="DocumentBuilder"/> makes sure that the last block-level element imported from HTML
    /// is closed after import and inserts a paragraph break after the element. This paragraph break separates
    /// content imported from HTML from content of the template document. However, if a HTML fragment is inserted into
    /// an empty paragraph, that paragraph break will create an extra empty paragraph. If this behavior is undesired,
    /// specify this option.
    /// </remarks>
    public static final int REMOVE_LAST_EMPTY_PARAGRAPH = 2;

    /// <summary>
    /// Preserve properties of block-level elements.
    /// </summary>
    /// <remarks>
    /// <para>
    /// By default, properties of parent blocks are merged and stored on their child elements (i.e. paragraphs or tables).
    /// If this option is specified, properties of each block are stored separately in a special logical structure.
    /// As a result, this option allows to better preserve individual borders and margins seen in the HTML document
    /// and get better conversion results. The downside is that the resulting document gets harder to modify, since borders
    /// and margins stored in the logical structure are not available for editing.
    /// </para>
    /// <para>
    /// Only margins and borders of 'body', 'div', and 'blockquote' HTML elements are preserved. Properties of each HTML
    /// element are stored separately.
    /// </para>
    /// <para>
    /// If this option is specified, Aspose.Words mimics MS Word's behavior regarding import of block properties.
    /// </para>
    /// </remarks>
    public static final int PRESERVE_BLOCKS = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlInsertOptions value.
    /// </summary>
    public static String getName(/*HtmlInsertOptions*/int htmlInsertOptions)
    {
    	switch (htmlInsertOptions)
    	{
    		case NONE: return "NONE";
    		case USE_BUILDER_FORMATTING: return "USE_BUILDER_FORMATTING";
    		case REMOVE_LAST_EMPTY_PARAGRAPH: return "REMOVE_LAST_EMPTY_PARAGRAPH";
    		case PRESERVE_BLOCKS: return "PRESERVE_BLOCKS";
    		default: return "Unknown HtmlInsertOptions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer HtmlInsertOptions value.
    /// </summary>
    public static Set<String> getNames(/*HtmlInsertOptions*/int htmlInsertOptions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & htmlInsertOptions) == NONE) names.add("NONE");
    	if ((USE_BUILDER_FORMATTING & htmlInsertOptions) == USE_BUILDER_FORMATTING) names.add("USE_BUILDER_FORMATTING");
    	if ((REMOVE_LAST_EMPTY_PARAGRAPH & htmlInsertOptions) == REMOVE_LAST_EMPTY_PARAGRAPH) names.add("REMOVE_LAST_EMPTY_PARAGRAPH");
    	if ((PRESERVE_BLOCKS & htmlInsertOptions) == PRESERVE_BLOCKS) names.add("PRESERVE_BLOCKS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlInsertOptions value.
    /// </summary>
    public static String toString(/*HtmlInsertOptions*/int htmlInsertOptions)
    {
    	switch (htmlInsertOptions)
    	{
    		case NONE: return "None";
    		case USE_BUILDER_FORMATTING: return "UseBuilderFormatting";
    		case REMOVE_LAST_EMPTY_PARAGRAPH: return "RemoveLastEmptyParagraph";
    		case PRESERVE_BLOCKS: return "PreserveBlocks";
    		default: return toStringSet(htmlInsertOptions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlInsertOptions value.
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
    /// Returns integer representation by HtmlInsertOptions name.
    /// </summary>
    public static /*HtmlInsertOptions*/int fromName(String htmlInsertOptionsName)
    {
    	if ("NONE".equals(htmlInsertOptionsName)) return NONE;
    	if ("USE_BUILDER_FORMATTING".equals(htmlInsertOptionsName)) return USE_BUILDER_FORMATTING;
    	if ("REMOVE_LAST_EMPTY_PARAGRAPH".equals(htmlInsertOptionsName)) return REMOVE_LAST_EMPTY_PARAGRAPH;
    	if ("PRESERVE_BLOCKS".equals(htmlInsertOptionsName)) return PRESERVE_BLOCKS;
    	throw new IllegalArgumentException("Unknown HtmlInsertOptions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by HtmlInsertOptions names.
    /// </summary>
    public static /*HtmlInsertOptions*/int fromNames(Set<String> htmlInsertOptionsNames)
    {
    	/*HtmlInsertOptions*/int result = 0;
    	for (String name : htmlInsertOptionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of HtmlInsertOptions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		USE_BUILDER_FORMATTING,
    		REMOVE_LAST_EMPTY_PARAGRAPH,
    		PRESERVE_BLOCKS,
    	};
    }

    public static final int length = 4;
}

