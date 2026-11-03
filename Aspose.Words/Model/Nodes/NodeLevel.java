// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a node level (inline, block etc).
/// </summary>
/*enum*/ final class NodeLevel
{
    private NodeLevel(){}
    
    /// <summary>
    /// Document level.
    /// </summary>
    public static final int DOCUMENT = 0;
    /// <summary>
    /// Section level.
    /// </summary>
    public static final int SECTION = 1;
    /// <summary>
    /// Section story level.
    /// </summary>
    public static final int SECTION_STORY = 2;
    /// <summary>
    /// Block level.
    /// </summary>
    public static final int BLOCK = 3;
    /// <summary>
    /// Row level.
    /// </summary>
    public static final int ROW = 4;
    /// <summary>
    /// Cell level.
    /// </summary>
    public static final int CELL = 5;
    /// <summary>
    /// Inline level.
    /// </summary>
    public static final int INLINE = 6;
    /// <summary>
    /// Any other level.
    /// </summary>
    public static final int OTHER = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NodeLevel value.
    /// </summary>
    public static String getName(/*NodeLevel*/int nodeLevel)
    {
    	switch (nodeLevel)
    	{
    		case DOCUMENT: return "DOCUMENT";
    		case SECTION: return "SECTION";
    		case SECTION_STORY: return "SECTION_STORY";
    		case BLOCK: return "BLOCK";
    		case ROW: return "ROW";
    		case CELL: return "CELL";
    		case INLINE: return "INLINE";
    		case OTHER: return "OTHER";
    		default: return "Unknown NodeLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NodeLevel value.
    /// </summary>
    public static String toString(/*NodeLevel*/int nodeLevel)
    {
    	switch (nodeLevel)
    	{
    		case DOCUMENT: return "Document";
    		case SECTION: return "Section";
    		case SECTION_STORY: return "SectionStory";
    		case BLOCK: return "Block";
    		case ROW: return "Row";
    		case CELL: return "Cell";
    		case INLINE: return "Inline";
    		case OTHER: return "Other";
    		default: return "Unknown NodeLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NodeLevel name.
    /// </summary>
    public static /*NodeLevel*/int fromName(String nodeLevelName)
    {
    	if ("DOCUMENT".equals(nodeLevelName)) return DOCUMENT;
    	if ("SECTION".equals(nodeLevelName)) return SECTION;
    	if ("SECTION_STORY".equals(nodeLevelName)) return SECTION_STORY;
    	if ("BLOCK".equals(nodeLevelName)) return BLOCK;
    	if ("ROW".equals(nodeLevelName)) return ROW;
    	if ("CELL".equals(nodeLevelName)) return CELL;
    	if ("INLINE".equals(nodeLevelName)) return INLINE;
    	if ("OTHER".equals(nodeLevelName)) return OTHER;
    	throw new IllegalArgumentException("Unknown NodeLevel name.");
    }
    
    /// <summary>
    /// Returns array of NodeLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DOCUMENT,
    		SECTION,
    		SECTION_STORY,
    		BLOCK,
    		ROW,
    		CELL,
    		INLINE,
    		OTHER,
    	};
    }

    public static final int length = 8;
}

