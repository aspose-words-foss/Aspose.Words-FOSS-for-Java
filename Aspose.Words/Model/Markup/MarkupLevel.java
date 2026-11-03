// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the level in the document tree where a particular <see cref="StructuredDocumentTag"/> can occur.
/// </summary>
public /*enum*/ final class MarkupLevel
{
    private MarkupLevel(){}
    
    /// <summary>
    /// Specifies the unknown or invalid value.
    /// </summary>
    public static final int UNKNOWN = 0;

    /// <summary>
    /// The element occurs at the inline level (e.g. among as runs of text).
    /// </summary>
    public static final int INLINE = 1;

    /// <summary>
    /// The element occurs at the block level (e.g. among tables and paragraphs).
    /// </summary>
    public static final int BLOCK = 2;

    /// <summary>
    /// The element occurs among rows in a table.
    /// </summary>
    public static final int ROW = 3;

    /// <summary>
    /// The element occurs among cells in a row.
    /// </summary>
    public static final int CELL = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkupLevel value.
    /// </summary>
    public static String getName(/*MarkupLevel*/int markupLevel)
    {
    	switch (markupLevel)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case INLINE: return "INLINE";
    		case BLOCK: return "BLOCK";
    		case ROW: return "ROW";
    		case CELL: return "CELL";
    		default: return "Unknown MarkupLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkupLevel value.
    /// </summary>
    public static String toString(/*MarkupLevel*/int markupLevel)
    {
    	switch (markupLevel)
    	{
    		case UNKNOWN: return "Unknown";
    		case INLINE: return "Inline";
    		case BLOCK: return "Block";
    		case ROW: return "Row";
    		case CELL: return "Cell";
    		default: return "Unknown MarkupLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkupLevel name.
    /// </summary>
    public static /*MarkupLevel*/int fromName(String markupLevelName)
    {
    	if ("UNKNOWN".equals(markupLevelName)) return UNKNOWN;
    	if ("INLINE".equals(markupLevelName)) return INLINE;
    	if ("BLOCK".equals(markupLevelName)) return BLOCK;
    	if ("ROW".equals(markupLevelName)) return ROW;
    	if ("CELL".equals(markupLevelName)) return CELL;
    	throw new IllegalArgumentException("Unknown MarkupLevel name.");
    }
    
    /// <summary>
    /// Returns array of MarkupLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		INLINE,
    		BLOCK,
    		ROW,
    		CELL,
    	};
    }

    public static final int length = 5;
}

