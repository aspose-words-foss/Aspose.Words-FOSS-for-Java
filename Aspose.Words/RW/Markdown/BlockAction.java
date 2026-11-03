// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible actions that can be performed over a markdown block.
/// </summary>
/*enum*/ final class BlockAction
{
    private BlockAction(){}
    
    public static final int NONE = 0;
    public static final int REMOVE = 1;
    public static final int EXCLUDE = 2;
    public static final int REPARSE_LIST_ITEM = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BlockAction value.
    /// </summary>
    public static String getName(/*BlockAction*/int blockAction)
    {
    	switch (blockAction)
    	{
    		case NONE: return "NONE";
    		case REMOVE: return "REMOVE";
    		case EXCLUDE: return "EXCLUDE";
    		case REPARSE_LIST_ITEM: return "REPARSE_LIST_ITEM";
    		default: return "Unknown BlockAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BlockAction value.
    /// </summary>
    public static String toString(/*BlockAction*/int blockAction)
    {
    	switch (blockAction)
    	{
    		case NONE: return "None";
    		case REMOVE: return "Remove";
    		case EXCLUDE: return "Exclude";
    		case REPARSE_LIST_ITEM: return "ReparseListItem";
    		default: return "Unknown BlockAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BlockAction name.
    /// </summary>
    public static /*BlockAction*/int fromName(String blockActionName)
    {
    	if ("NONE".equals(blockActionName)) return NONE;
    	if ("REMOVE".equals(blockActionName)) return REMOVE;
    	if ("EXCLUDE".equals(blockActionName)) return EXCLUDE;
    	if ("REPARSE_LIST_ITEM".equals(blockActionName)) return REPARSE_LIST_ITEM;
    	throw new IllegalArgumentException("Unknown BlockAction name.");
    }
    
    /// <summary>
    /// Returns array of BlockAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		REMOVE,
    		EXCLUDE,
    		REPARSE_LIST_ITEM,
    	};
    }

    public static final int length = 4;
}

