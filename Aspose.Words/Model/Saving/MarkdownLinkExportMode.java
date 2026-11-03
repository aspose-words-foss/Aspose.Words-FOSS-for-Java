// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/12/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how links are exported into Markdown.
/// </summary>
public /*enum*/ final class MarkdownLinkExportMode
{
    private MarkdownLinkExportMode(){}
    
    /// <summary>
    /// Automatically detect export mode for each link.
    /// </summary>
    /// <dev>
    /// Export link as a reference block if it has roundtrip information or is mentioned more than once
    /// in a document. Otherwise, export link as an inline block.
    /// </dev>
    public static final int AUTO = 0;

    /// <summary>
    /// Export all links as inline blocks.
    /// </summary>
    public static final int INLINE = 1;

    /// <summary>
    /// Export all links as reference blocks.
    /// </summary>
    public static final int REFERENCE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownLinkExportMode value.
    /// </summary>
    public static String getName(/*MarkdownLinkExportMode*/int markdownLinkExportMode)
    {
    	switch (markdownLinkExportMode)
    	{
    		case AUTO: return "AUTO";
    		case INLINE: return "INLINE";
    		case REFERENCE: return "REFERENCE";
    		default: return "Unknown MarkdownLinkExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownLinkExportMode value.
    /// </summary>
    public static String toString(/*MarkdownLinkExportMode*/int markdownLinkExportMode)
    {
    	switch (markdownLinkExportMode)
    	{
    		case AUTO: return "Auto";
    		case INLINE: return "Inline";
    		case REFERENCE: return "Reference";
    		default: return "Unknown MarkdownLinkExportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownLinkExportMode name.
    /// </summary>
    public static /*MarkdownLinkExportMode*/int fromName(String markdownLinkExportModeName)
    {
    	if ("AUTO".equals(markdownLinkExportModeName)) return AUTO;
    	if ("INLINE".equals(markdownLinkExportModeName)) return INLINE;
    	if ("REFERENCE".equals(markdownLinkExportModeName)) return REFERENCE;
    	throw new IllegalArgumentException("Unknown MarkdownLinkExportMode name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownLinkExportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO,
    		INLINE,
    		REFERENCE,
    	};
    }

    public static final int length = 3;
}

