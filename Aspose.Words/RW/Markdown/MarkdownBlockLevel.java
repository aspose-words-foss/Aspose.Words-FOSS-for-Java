// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify a level of markdown block.
/// </summary>
/*enum*/ final class MarkdownBlockLevel
{
    private MarkdownBlockLevel(){}
    
    /// <summary>
    /// Specifies level 'None' of the markdown block.
    /// </summary>
    /// <remarks>
    /// The blocks with this level typically are not actually stored in a markdown tree.
    /// </remarks>
    public static final int NONE = 0;

    /// <summary>
    /// Specifies level 'Inline' of the markdown block.
    /// </summary>
    /// <remarks>
    /// The blocks with this level are allowed to be child of Leaf-level or other Inline-level blocks.
    /// </remarks>
    public static final int INLINE = 1;


    /// <summary>
    /// Specifies level 'Leaf' of the markdown block.
    /// </summary>
    /// <remarks>
    /// The Leaf blocks are the blocks that cannot contain Block-level or other Leaf-level blocks.
    /// </remarks>
    public static final int LEAF = 2;

    /// <summary>
    /// Specifies level 'Block' of the markdown block.
    /// </summary>
    /// <remarks>
    /// The Block blocks are the blocks that can contain Leaf-level or other Block-level blocks.
    /// </remarks>
    public static final int BLOCK = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MarkdownBlockLevel value.
    /// </summary>
    public static String getName(/*MarkdownBlockLevel*/int markdownBlockLevel)
    {
    	switch (markdownBlockLevel)
    	{
    		case NONE: return "NONE";
    		case INLINE: return "INLINE";
    		case LEAF: return "LEAF";
    		case BLOCK: return "BLOCK";
    		default: return "Unknown MarkdownBlockLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MarkdownBlockLevel value.
    /// </summary>
    public static String toString(/*MarkdownBlockLevel*/int markdownBlockLevel)
    {
    	switch (markdownBlockLevel)
    	{
    		case NONE: return "None";
    		case INLINE: return "Inline";
    		case LEAF: return "Leaf";
    		case BLOCK: return "Block";
    		default: return "Unknown MarkdownBlockLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MarkdownBlockLevel name.
    /// </summary>
    public static /*MarkdownBlockLevel*/int fromName(String markdownBlockLevelName)
    {
    	if ("NONE".equals(markdownBlockLevelName)) return NONE;
    	if ("INLINE".equals(markdownBlockLevelName)) return INLINE;
    	if ("LEAF".equals(markdownBlockLevelName)) return LEAF;
    	if ("BLOCK".equals(markdownBlockLevelName)) return BLOCK;
    	throw new IllegalArgumentException("Unknown MarkdownBlockLevel name.");
    }
    
    /// <summary>
    /// Returns array of MarkdownBlockLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		INLINE,
    		LEAF,
    		BLOCK,
    	};
    }

    public static final int length = 4;
}

