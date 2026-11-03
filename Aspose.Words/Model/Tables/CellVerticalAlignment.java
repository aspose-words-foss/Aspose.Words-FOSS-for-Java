// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies vertical justification of text inside a table cell.
/// </summary>
public /*enum*/ final class CellVerticalAlignment
{
    private CellVerticalAlignment(){}
    
    /// <summary>
    /// Text is aligned at the top of a cell.
    /// </summary>
    public static final int TOP = 0;
    /// <summary>
    /// Text is aligned in the middle of a cell.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// Text is aligned at the bottom of the cell.
    /// </summary>
    public static final int BOTTOM = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CellVerticalAlignment value.
    /// </summary>
    public static String getName(/*CellVerticalAlignment*/int cellVerticalAlignment)
    {
    	switch (cellVerticalAlignment)
    	{
    		case TOP: return "TOP";
    		case CENTER: return "CENTER";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown CellVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellVerticalAlignment value.
    /// </summary>
    public static String toString(/*CellVerticalAlignment*/int cellVerticalAlignment)
    {
    	switch (cellVerticalAlignment)
    	{
    		case TOP: return "Top";
    		case CENTER: return "Center";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown CellVerticalAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CellVerticalAlignment name.
    /// </summary>
    public static /*CellVerticalAlignment*/int fromName(String cellVerticalAlignmentName)
    {
    	if ("TOP".equals(cellVerticalAlignmentName)) return TOP;
    	if ("CENTER".equals(cellVerticalAlignmentName)) return CENTER;
    	if ("BOTTOM".equals(cellVerticalAlignmentName)) return BOTTOM;
    	throw new IllegalArgumentException("Unknown CellVerticalAlignment name.");
    }
    
    /// <summary>
    /// Returns array of CellVerticalAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		CENTER,
    		BOTTOM,
    	};
    }

    public static final int length = 3;
}

