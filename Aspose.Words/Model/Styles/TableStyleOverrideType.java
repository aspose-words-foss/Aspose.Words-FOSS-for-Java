// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents possible table areas to which conditional formatting may be defined in a table style.
/// </summary>
/// <remarks>
/// This enum is the same as <see cref="ConditionalStyleType"/>, but includes two additional items for
/// internal usage.
/// </remarks>
/*enum*/ final class TableStyleOverrideType
{
    private TableStyleOverrideType(){}
    
    public static final int NONE = 0;
    /// <summary>
    /// Specifies formatting of the first row of a table.
    /// </summary>
    public static final int FIRST_ROW = 1;
    /// <summary>
    /// Specifies formatting of the first column of a table.
    /// </summary>
    public static final int FIRST_COLUMN = 2;
    /// <summary>
    /// Specifies formatting of the last row of a table.
    /// </summary>
    public static final int LAST_ROW = 3;
    /// <summary>
    /// Specifies formatting of the last column of a table.
    /// </summary>
    public static final int LAST_COLUMN = 4;
    /// <summary>
    /// Specifies formatting of odd-numbered row stripe.
    /// </summary>
    public static final int ODD_ROW_BANDING = 5;
    /// <summary>
    /// Specifies formatting of odd-numbered column stripe.
    /// </summary>
    public static final int ODD_COLUMN_BANDING = 6;
    /// <summary>
    /// Specifies formatting of even-numbered row stripe.
    /// </summary>
    public static final int EVEN_ROW_BANDING = 7;
    /// <summary>
    /// Specifies formatting of even-numbered column stripe.
    /// </summary>
    public static final int EVEN_COLUMN_BANDING = 8;
    /// <summary>
    /// Specifies formatting of the top left cell of a table.
    /// </summary>
    public static final int TOP_LEFT_CELL = 9;
    /// <summary>
    /// Specifies formatting of the top right cell of a table.
    /// </summary>
    public static final int TOP_RIGHT_CELL = 10;
    /// <summary>
    /// Specifies formatting of the bottom left cell of a table.
    /// </summary>
    public static final int BOTTOM_LEFT_CELL = 11;
    /// <summary>
    /// Specifies formatting of the bottom right cell of a table.
    /// </summary>
    public static final int BOTTOM_RIGHT_CELL = 12;
    /// <summary>
    /// Specifies formatting of the whole table.
    /// </summary>
    /// <remarks>
    /// The WholeTable value is not present in the public <see cref="ConditionalStyleType"/> enum (and in VBA)
    /// since 'Word does not apply and discards on save any properties within the tblStylePr element when the "type"
    /// attribute has a value of "wholeTable"' [MS-OI29500].
    /// </remarks>
    public static final int WHOLE_TABLE = 13;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TableStyleOverrideType value.
    /// </summary>
    public static String getName(/*TableStyleOverrideType*/int tableStyleOverrideType)
    {
    	switch (tableStyleOverrideType)
    	{
    		case NONE: return "NONE";
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
    		case WHOLE_TABLE: return "WHOLE_TABLE";
    		default: return "Unknown TableStyleOverrideType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TableStyleOverrideType value.
    /// </summary>
    public static String toString(/*TableStyleOverrideType*/int tableStyleOverrideType)
    {
    	switch (tableStyleOverrideType)
    	{
    		case NONE: return "None";
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
    		case WHOLE_TABLE: return "WholeTable";
    		default: return "Unknown TableStyleOverrideType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TableStyleOverrideType name.
    /// </summary>
    public static /*TableStyleOverrideType*/int fromName(String tableStyleOverrideTypeName)
    {
    	if ("NONE".equals(tableStyleOverrideTypeName)) return NONE;
    	if ("FIRST_ROW".equals(tableStyleOverrideTypeName)) return FIRST_ROW;
    	if ("FIRST_COLUMN".equals(tableStyleOverrideTypeName)) return FIRST_COLUMN;
    	if ("LAST_ROW".equals(tableStyleOverrideTypeName)) return LAST_ROW;
    	if ("LAST_COLUMN".equals(tableStyleOverrideTypeName)) return LAST_COLUMN;
    	if ("ODD_ROW_BANDING".equals(tableStyleOverrideTypeName)) return ODD_ROW_BANDING;
    	if ("ODD_COLUMN_BANDING".equals(tableStyleOverrideTypeName)) return ODD_COLUMN_BANDING;
    	if ("EVEN_ROW_BANDING".equals(tableStyleOverrideTypeName)) return EVEN_ROW_BANDING;
    	if ("EVEN_COLUMN_BANDING".equals(tableStyleOverrideTypeName)) return EVEN_COLUMN_BANDING;
    	if ("TOP_LEFT_CELL".equals(tableStyleOverrideTypeName)) return TOP_LEFT_CELL;
    	if ("TOP_RIGHT_CELL".equals(tableStyleOverrideTypeName)) return TOP_RIGHT_CELL;
    	if ("BOTTOM_LEFT_CELL".equals(tableStyleOverrideTypeName)) return BOTTOM_LEFT_CELL;
    	if ("BOTTOM_RIGHT_CELL".equals(tableStyleOverrideTypeName)) return BOTTOM_RIGHT_CELL;
    	if ("WHOLE_TABLE".equals(tableStyleOverrideTypeName)) return WHOLE_TABLE;
    	throw new IllegalArgumentException("Unknown TableStyleOverrideType name.");
    }
    
    /// <summary>
    /// Returns array of TableStyleOverrideType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
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
    		WHOLE_TABLE,
    	};
    }

    public static final int length = 14;
}

