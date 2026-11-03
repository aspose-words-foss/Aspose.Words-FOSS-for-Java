// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how a cell in a table is merged with other cells.
/// </summary>
public /*enum*/ final class CellMerge
{
    private CellMerge(){}
    
    /// <summary>
    /// The cell is not merged.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The cell is the first cell in a range of merged cells.
    /// </summary>
    public static final int FIRST = 1;
    /// <summary>
    /// The cell is merged to the previous cell horizontally or vertically.
    /// </summary>
    public static final int PREVIOUS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CellMerge value.
    /// </summary>
    public static String getName(/*CellMerge*/int cellMerge)
    {
    	switch (cellMerge)
    	{
    		case NONE: return "NONE";
    		case FIRST: return "FIRST";
    		case PREVIOUS: return "PREVIOUS";
    		default: return "Unknown CellMerge value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellMerge value.
    /// </summary>
    public static String toString(/*CellMerge*/int cellMerge)
    {
    	switch (cellMerge)
    	{
    		case NONE: return "None";
    		case FIRST: return "First";
    		case PREVIOUS: return "Previous";
    		default: return "Unknown CellMerge value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CellMerge name.
    /// </summary>
    public static /*CellMerge*/int fromName(String cellMergeName)
    {
    	if ("NONE".equals(cellMergeName)) return NONE;
    	if ("FIRST".equals(cellMergeName)) return FIRST;
    	if ("PREVIOUS".equals(cellMergeName)) return PREVIOUS;
    	throw new IllegalArgumentException("Unknown CellMerge name.");
    }
    
    /// <summary>
    /// Returns array of CellMerge values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FIRST,
    		PREVIOUS,
    	};
    }

    public static final int length = 3;
}

