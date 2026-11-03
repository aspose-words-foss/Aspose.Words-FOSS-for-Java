// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2015 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Display type based on CSS "display" property.
/// </summary>
/*enum*/ final class CssDisplayType
{
    private CssDisplayType(){}
    
    public static final int INLINE = 0;
    public static final int BLOCK = 1;
    public static final int INLINE_BLOCK = 2;
    public static final int LIST_ITEM = 3;
    public static final int RUN_IN = 4;
    public static final int TABLE = 5;
    public static final int INLINE_TABLE = 6;
    public static final int TABLE_CAPTION = 7;
    public static final int TABLE_HEADER_GROUP = 8;
    public static final int TABLE_COLUMN_GROUP = 9;
    public static final int TABLE_COLUMN = 10;
    public static final int TABLE_ROW_GROUP = 11;
    public static final int TABLE_ROW = 12;
    public static final int TABLE_CELL = 13;
    public static final int TABLE_FOOTER_GROUP = 14;
    public static final int NONE = 15;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssDisplayType value.
    /// </summary>
    public static String getName(/*CssDisplayType*/int cssDisplayType)
    {
    	switch (cssDisplayType)
    	{
    		case INLINE: return "INLINE";
    		case BLOCK: return "BLOCK";
    		case INLINE_BLOCK: return "INLINE_BLOCK";
    		case LIST_ITEM: return "LIST_ITEM";
    		case RUN_IN: return "RUN_IN";
    		case TABLE: return "TABLE";
    		case INLINE_TABLE: return "INLINE_TABLE";
    		case TABLE_CAPTION: return "TABLE_CAPTION";
    		case TABLE_HEADER_GROUP: return "TABLE_HEADER_GROUP";
    		case TABLE_COLUMN_GROUP: return "TABLE_COLUMN_GROUP";
    		case TABLE_COLUMN: return "TABLE_COLUMN";
    		case TABLE_ROW_GROUP: return "TABLE_ROW_GROUP";
    		case TABLE_ROW: return "TABLE_ROW";
    		case TABLE_CELL: return "TABLE_CELL";
    		case TABLE_FOOTER_GROUP: return "TABLE_FOOTER_GROUP";
    		case NONE: return "NONE";
    		default: return "Unknown CssDisplayType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssDisplayType value.
    /// </summary>
    public static String toString(/*CssDisplayType*/int cssDisplayType)
    {
    	switch (cssDisplayType)
    	{
    		case INLINE: return "Inline";
    		case BLOCK: return "Block";
    		case INLINE_BLOCK: return "InlineBlock";
    		case LIST_ITEM: return "ListItem";
    		case RUN_IN: return "RunIn";
    		case TABLE: return "Table";
    		case INLINE_TABLE: return "InlineTable";
    		case TABLE_CAPTION: return "TableCaption";
    		case TABLE_HEADER_GROUP: return "TableHeaderGroup";
    		case TABLE_COLUMN_GROUP: return "TableColumnGroup";
    		case TABLE_COLUMN: return "TableColumn";
    		case TABLE_ROW_GROUP: return "TableRowGroup";
    		case TABLE_ROW: return "TableRow";
    		case TABLE_CELL: return "TableCell";
    		case TABLE_FOOTER_GROUP: return "TableFooterGroup";
    		case NONE: return "None";
    		default: return "Unknown CssDisplayType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssDisplayType name.
    /// </summary>
    public static /*CssDisplayType*/int fromName(String cssDisplayTypeName)
    {
    	if ("INLINE".equals(cssDisplayTypeName)) return INLINE;
    	if ("BLOCK".equals(cssDisplayTypeName)) return BLOCK;
    	if ("INLINE_BLOCK".equals(cssDisplayTypeName)) return INLINE_BLOCK;
    	if ("LIST_ITEM".equals(cssDisplayTypeName)) return LIST_ITEM;
    	if ("RUN_IN".equals(cssDisplayTypeName)) return RUN_IN;
    	if ("TABLE".equals(cssDisplayTypeName)) return TABLE;
    	if ("INLINE_TABLE".equals(cssDisplayTypeName)) return INLINE_TABLE;
    	if ("TABLE_CAPTION".equals(cssDisplayTypeName)) return TABLE_CAPTION;
    	if ("TABLE_HEADER_GROUP".equals(cssDisplayTypeName)) return TABLE_HEADER_GROUP;
    	if ("TABLE_COLUMN_GROUP".equals(cssDisplayTypeName)) return TABLE_COLUMN_GROUP;
    	if ("TABLE_COLUMN".equals(cssDisplayTypeName)) return TABLE_COLUMN;
    	if ("TABLE_ROW_GROUP".equals(cssDisplayTypeName)) return TABLE_ROW_GROUP;
    	if ("TABLE_ROW".equals(cssDisplayTypeName)) return TABLE_ROW;
    	if ("TABLE_CELL".equals(cssDisplayTypeName)) return TABLE_CELL;
    	if ("TABLE_FOOTER_GROUP".equals(cssDisplayTypeName)) return TABLE_FOOTER_GROUP;
    	if ("NONE".equals(cssDisplayTypeName)) return NONE;
    	throw new IllegalArgumentException("Unknown CssDisplayType name.");
    }
    
    /// <summary>
    /// Returns array of CssDisplayType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INLINE,
    		BLOCK,
    		INLINE_BLOCK,
    		LIST_ITEM,
    		RUN_IN,
    		TABLE,
    		INLINE_TABLE,
    		TABLE_CAPTION,
    		TABLE_HEADER_GROUP,
    		TABLE_COLUMN_GROUP,
    		TABLE_COLUMN,
    		TABLE_ROW_GROUP,
    		TABLE_ROW,
    		TABLE_CELL,
    		TABLE_FOOTER_GROUP,
    		NONE,
    	};
    }

    public static final int length = 16;
}
