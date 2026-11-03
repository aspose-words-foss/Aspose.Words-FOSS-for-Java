// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies sides of a border.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
public /*enum*/ final class BorderType
{
    private BorderType(){}
    
    /// <summary>
    /// Default value.
    /// </summary>
    public static final int NONE            = -1;
    /// <summary>
    /// Specifies the bottom border of a paragraph or a table cell.
    /// </summary>
    public static final int BOTTOM          = 0;
    /// <summary>
    /// Specifies the left border of a paragraph or a table cell.
    /// </summary>
    public static final int LEFT            = 1;
    /// <summary>
    /// Specifies the right border of a paragraph or a table cell.
    /// </summary>
    public static final int RIGHT           = 2;
    /// <summary>
    /// Specifies the top border of a paragraph or a table cell.
    /// </summary>
    public static final int TOP             = 3;
    /// <summary>
    /// Specifies the horizontal border between cells in a table or between conforming paragraphs.
    /// </summary>
    public static final int HORIZONTAL      = 4;
    /// <summary>
    /// Specifies the vertical border between cells in a table.
    /// </summary>
    public static final int VERTICAL        = 5;
    /// <summary>
    /// Specifies the diagonal border in a table cell.
    /// </summary>
    public static final int DIAGONAL_DOWN    = 6;
    /// <summary>
    /// Specifies the diagonal border in a table cell.
    /// </summary>
    public static final int DIAGONAL_UP      = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BorderType value.
    /// </summary>
    public static String getName(/*BorderType*/int borderType)
    {
    	switch (borderType)
    	{
    		case NONE: return "NONE";
    		case BOTTOM: return "BOTTOM";
    		case LEFT: return "LEFT";
    		case RIGHT: return "RIGHT";
    		case TOP: return "TOP";
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case DIAGONAL_DOWN: return "DIAGONAL_DOWN";
    		case DIAGONAL_UP: return "DIAGONAL_UP";
    		default: return "Unknown BorderType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BorderType value.
    /// </summary>
    public static String toString(/*BorderType*/int borderType)
    {
    	switch (borderType)
    	{
    		case NONE: return "None";
    		case BOTTOM: return "Bottom";
    		case LEFT: return "Left";
    		case RIGHT: return "Right";
    		case TOP: return "Top";
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case DIAGONAL_DOWN: return "DiagonalDown";
    		case DIAGONAL_UP: return "DiagonalUp";
    		default: return "Unknown BorderType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BorderType name.
    /// </summary>
    public static /*BorderType*/int fromName(String borderTypeName)
    {
    	if ("NONE".equals(borderTypeName)) return NONE;
    	if ("BOTTOM".equals(borderTypeName)) return BOTTOM;
    	if ("LEFT".equals(borderTypeName)) return LEFT;
    	if ("RIGHT".equals(borderTypeName)) return RIGHT;
    	if ("TOP".equals(borderTypeName)) return TOP;
    	if ("HORIZONTAL".equals(borderTypeName)) return HORIZONTAL;
    	if ("VERTICAL".equals(borderTypeName)) return VERTICAL;
    	if ("DIAGONAL_DOWN".equals(borderTypeName)) return DIAGONAL_DOWN;
    	if ("DIAGONAL_UP".equals(borderTypeName)) return DIAGONAL_UP;
    	throw new IllegalArgumentException("Unknown BorderType name.");
    }
    
    /// <summary>
    /// Returns array of BorderType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BOTTOM,
    		LEFT,
    		RIGHT,
    		TOP,
    		HORIZONTAL,
    		VERTICAL,
    		DIAGONAL_DOWN,
    		DIAGONAL_UP,
    	};
    }

    public static final int length = 9;
}

