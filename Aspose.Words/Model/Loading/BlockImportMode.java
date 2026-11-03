// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/03/2022 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how properties of block-level elements are imported from HTML-based documents.
/// </summary>
public /*enum*/ final class BlockImportMode
{
    private BlockImportMode(){}
    
    /// <summary>
    /// Properties of parent blocks are merged and stored on child elements (i.e. paragraphs or tables).
    /// </summary>
    /// <remarks>
    /// <para>
    /// Properties of parent blocks are merged as follows: margins are added together; borders of higher-level blocks 
    /// are discarded and only the most inner-level borders are preserved. As a result, when this mode is specified,
    /// some formatting of blocks from the original document will be lost.
    /// </para>
    /// <para>
    /// On the other hand, since all merged block-level properties are stored on document nodes, all formating
    /// in the resulting document will be available for modification.
    /// </para>
    /// </remarks>
    public static final int MERGE = 0;

    /// <summary>
    /// Properties of parent blocks are imported to a special logical structure and are stored separately from
    /// document nodes.
    /// </summary>
    /// <remarks>
    /// <para>
    /// Only margins and borders of 'body', 'div', and 'blockquote' HTML elements are imported. Properties of each HTML
    /// element are stored individually.
    /// </para>
    /// <para>
    /// This mode allows to better preserve borders and margins seen in the HTML document and get better conversion
    /// results. The downside is that the resulting document gets harder to modify, since borders and margins stored
    /// in the logical structure are not available for editing.
    /// </para>
    /// <para>
    /// This mode mimics MS Word's behavior regarding import of block properties.
    /// </para>
    /// </remarks>
    public static final int PRESERVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BlockImportMode value.
    /// </summary>
    public static String getName(/*BlockImportMode*/int blockImportMode)
    {
    	switch (blockImportMode)
    	{
    		case MERGE: return "MERGE";
    		case PRESERVE: return "PRESERVE";
    		default: return "Unknown BlockImportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BlockImportMode value.
    /// </summary>
    public static String toString(/*BlockImportMode*/int blockImportMode)
    {
    	switch (blockImportMode)
    	{
    		case MERGE: return "Merge";
    		case PRESERVE: return "Preserve";
    		default: return "Unknown BlockImportMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BlockImportMode name.
    /// </summary>
    public static /*BlockImportMode*/int fromName(String blockImportModeName)
    {
    	if ("MERGE".equals(blockImportModeName)) return MERGE;
    	if ("PRESERVE".equals(blockImportModeName)) return PRESERVE;
    	throw new IllegalArgumentException("Unknown BlockImportMode name.");
    }
    
    /// <summary>
    /// Returns array of BlockImportMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MERGE,
    		PRESERVE,
    	};
    }

    public static final int length = 2;
}

