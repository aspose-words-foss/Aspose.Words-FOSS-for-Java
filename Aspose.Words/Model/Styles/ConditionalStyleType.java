// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2019 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents possible table areas to which conditional formatting may be defined in a table style.
/// </summary>
public /*enum*/ final class ConditionalStyleType
{
    private ConditionalStyleType(){}
    
    /// <summary>
    /// Specifies formatting of the first row of a table.
    /// </summary>
    public static final int FIRST_ROW = 0;
    /// <summary>
    /// Specifies formatting of the first column of a table.
    /// </summary>
    public static final int FIRST_COLUMN = 1;
    /// <summary>
    /// Specifies formatting of the last row of a table.
    /// </summary>
    public static final int LAST_ROW = 2;
    /// <summary>
    /// Specifies formatting of the last column of a table.
    /// </summary>
    public static final int LAST_COLUMN = 3;
    /// <summary>
    /// Specifies formatting of odd-numbered row stripe.
    /// </summary>
    public static final int ODD_ROW_BANDING = 4;
    /// <summary>
    /// Specifies formatting of odd-numbered column stripe.
    /// </summary>
    public static final int ODD_COLUMN_BANDING = 5;
    /// <summary>
    /// Specifies formatting of even-numbered row stripe.
    /// </summary>
    public static final int EVEN_ROW_BANDING = 6;
    /// <summary>
    /// Specifies formatting of even-numbered column stripe.
    /// </summary>
    public static final int EVEN_COLUMN_BANDING = 7;
    /// <summary>
    /// Specifies formatting of the top left cell of a table.
    /// </summary>
    public static final int TOP_LEFT_CELL = 8;
    /// <summary>
    /// Specifies formatting of the top right cell of a table.
    /// </summary>
    public static final int TOP_RIGHT_CELL = 9;
    /// <summary>
    /// Specifies formatting of the bottom left cell of a table.
    /// </summary>
    public static final int BOTTOM_LEFT_CELL = 10;
    /// <summary>
    /// Specifies formatting of the bottom right cell of a table.
    /// </summary>
    public static final int BOTTOM_RIGHT_CELL = 11;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ConditionalStyleType value.
    /// </summary>
    public static String getName(/*ConditionalStyleType*/int conditionalStyleType)
    {
    	switch (conditionalStyleType)
    	{
    		case FIRST_ROW: return "FIRST_ROW";
    		case FIRST_COLUMN: return "FIRST_COLUMN";
    		case LAST_ROW: return "LAST_ROW";
    		case LAST_COLUMN: return "LAST_COLUMN";
    		case ODD_ROW_BANDING: return "ODD_ROW_BANDING";
    		case ODD_COLUMN_BANDING: return "ODD_COLUMN_BANDING";
    		case EVEN_ROW_BANDING: return "EVEN_ROW_BANDING";
    		case EVEN_COLUMN_BANDING: return "EVEN_COLUMN_BANDING";
    		case TOP_LEFT_CELL: return "TOP_LEFT_CELL";
    		case TOP_RIGHT_CELL: return "TOP_RIGHT_CELL";
    		case BOTTOM_LEFT_CELL: return "BOTTOM_LEFT_CELL";
    		case BOTTOM_RIGHT_CELL: return "BOTTOM_RIGHT_CELL";
    		default: return "Unknown ConditionalStyleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ConditionalStyleType value.
    /// </summary>
    public static String toString(/*ConditionalStyleType*/int conditionalStyleType)
    {
    	switch (conditionalStyleType)
    	{
    		case FIRST_ROW: return "FirstRow";
    		case FIRST_COLUMN: return "FirstColumn";
    		case LAST_ROW: return "LastRow";
    		case LAST_COLUMN: return "LastColumn";
    		case ODD_ROW_BANDING: return "OddRowBanding";
    		case ODD_COLUMN_BANDING: return "OddColumnBanding";
    		case EVEN_ROW_BANDING: return "EvenRowBanding";
    		case EVEN_COLUMN_BANDING: return "EvenColumnBanding";
    		case TOP_LEFT_CELL: return "TopLeftCell";
    		case TOP_RIGHT_CELL: return "TopRightCell";
    		case BOTTOM_LEFT_CELL: return "BottomLeftCell";
    		case BOTTOM_RIGHT_CELL: return "BottomRightCell";
    		default: return "Unknown ConditionalStyleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ConditionalStyleType name.
    /// </summary>
    public static /*ConditionalStyleType*/int fromName(String conditionalStyleTypeName)
    {
    	if ("FIRST_ROW".equals(conditionalStyleTypeName)) return FIRST_ROW;
    	if ("FIRST_COLUMN".equals(conditionalStyleTypeName)) return FIRST_COLUMN;
    	if ("LAST_ROW".equals(conditionalStyleTypeName)) return LAST_ROW;
    	if ("LAST_COLUMN".equals(conditionalStyleTypeName)) return LAST_COLUMN;
    	if ("ODD_ROW_BANDING".equals(conditionalStyleTypeName)) return ODD_ROW_BANDING;
    	if ("ODD_COLUMN_BANDING".equals(conditionalStyleTypeName)) return ODD_COLUMN_BANDING;
    	if ("EVEN_ROW_BANDING".equals(conditionalStyleTypeName)) return EVEN_ROW_BANDING;
    	if ("EVEN_COLUMN_BANDING".equals(conditionalStyleTypeName)) return EVEN_COLUMN_BANDING;
    	if ("TOP_LEFT_CELL".equals(conditionalStyleTypeName)) return TOP_LEFT_CELL;
    	if ("TOP_RIGHT_CELL".equals(conditionalStyleTypeName)) return TOP_RIGHT_CELL;
    	if ("BOTTOM_LEFT_CELL".equals(conditionalStyleTypeName)) return BOTTOM_LEFT_CELL;
    	if ("BOTTOM_RIGHT_CELL".equals(conditionalStyleTypeName)) return BOTTOM_RIGHT_CELL;
    	throw new IllegalArgumentException("Unknown ConditionalStyleType name.");
    }
    
    /// <summary>
    /// Returns array of ConditionalStyleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIRST_ROW,
    		FIRST_COLUMN,
    		LAST_ROW,
    		LAST_COLUMN,
    		ODD_ROW_BANDING,
    		ODD_COLUMN_BANDING,
    		EVEN_ROW_BANDING,
    		EVEN_COLUMN_BANDING,
    		TOP_LEFT_CELL,
    		TOP_RIGHT_CELL,
    		BOTTOM_LEFT_CELL,
    		BOTTOM_RIGHT_CELL,
    	};
    }

    public static final int length = 12;
}

