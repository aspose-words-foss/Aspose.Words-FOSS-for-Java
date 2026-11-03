// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates possible locations where we are going to insert test documents.
/// There are bookmarks in the destination document corresponded to these locations.
/// </summary>
/*enum*/ final class TestInsertDstDocLocation
{
    private TestInsertDstDocLocation(){}
    
    public static final int BEGINNING_OF_LIST_ITEM = 0;
    public static final int BLOCK_LEVEL_SDT = 1;
    public static final int CELL = 2;
    public static final int COMMENT = 3;
    public static final int DOCUMENT_END = 4;
    public static final int DOCUMENT_START = 5;
    public static final int END_OF_LIST_ITEM = 6;
    public static final int FOOTNOTE = 7;
    public static final int HEADER = 8;
    public static final int INLINE_SDT = 9;
    public static final int MIDDLE_WITH_SPACE_AFTER = 10;
    public static final int MIDDLE_WITH_SPACE_BEFORE = 11;
    public static final int MIDDLE = 12;
    public static final int MIDDLE_OF_LIST_ITEM = 13;
    public static final int TEXT_BOX = 14;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TestInsertDstDocLocation value.
    /// </summary>
    public static String getName(/*TestInsertDstDocLocation*/int testInsertDstDocLocation)
    {
    	switch (testInsertDstDocLocation)
    	{
    		case BEGINNING_OF_LIST_ITEM: return "BEGINNING_OF_LIST_ITEM";
    		case BLOCK_LEVEL_SDT: return "BLOCK_LEVEL_SDT";
    		case CELL: return "CELL";
    		case COMMENT: return "COMMENT";
    		case DOCUMENT_END: return "DOCUMENT_END";
    		case DOCUMENT_START: return "DOCUMENT_START";
    		case END_OF_LIST_ITEM: return "END_OF_LIST_ITEM";
    		case FOOTNOTE: return "FOOTNOTE";
    		case HEADER: return "HEADER";
    		case INLINE_SDT: return "INLINE_SDT";
    		case MIDDLE_WITH_SPACE_AFTER: return "MIDDLE_WITH_SPACE_AFTER";
    		case MIDDLE_WITH_SPACE_BEFORE: return "MIDDLE_WITH_SPACE_BEFORE";
    		case MIDDLE: return "MIDDLE";
    		case MIDDLE_OF_LIST_ITEM: return "MIDDLE_OF_LIST_ITEM";
    		case TEXT_BOX: return "TEXT_BOX";
    		default: return "Unknown TestInsertDstDocLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TestInsertDstDocLocation value.
    /// </summary>
    public static String toString(/*TestInsertDstDocLocation*/int testInsertDstDocLocation)
    {
    	switch (testInsertDstDocLocation)
    	{
    		case BEGINNING_OF_LIST_ITEM: return "BeginningOfListItem";
    		case BLOCK_LEVEL_SDT: return "BlockLevelSdt";
    		case CELL: return "Cell";
    		case COMMENT: return "Comment";
    		case DOCUMENT_END: return "DocumentEnd";
    		case DOCUMENT_START: return "DocumentStart";
    		case END_OF_LIST_ITEM: return "EndOfListItem";
    		case FOOTNOTE: return "Footnote";
    		case HEADER: return "Header";
    		case INLINE_SDT: return "InlineSdt";
    		case MIDDLE_WITH_SPACE_AFTER: return "MiddleWithSpaceAfter";
    		case MIDDLE_WITH_SPACE_BEFORE: return "MiddleWithSpaceBefore";
    		case MIDDLE: return "Middle";
    		case MIDDLE_OF_LIST_ITEM: return "MiddleOfListItem";
    		case TEXT_BOX: return "TextBox";
    		default: return "Unknown TestInsertDstDocLocation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TestInsertDstDocLocation name.
    /// </summary>
    public static /*TestInsertDstDocLocation*/int fromName(String testInsertDstDocLocationName)
    {
    	if ("BEGINNING_OF_LIST_ITEM".equals(testInsertDstDocLocationName)) return BEGINNING_OF_LIST_ITEM;
    	if ("BLOCK_LEVEL_SDT".equals(testInsertDstDocLocationName)) return BLOCK_LEVEL_SDT;
    	if ("CELL".equals(testInsertDstDocLocationName)) return CELL;
    	if ("COMMENT".equals(testInsertDstDocLocationName)) return COMMENT;
    	if ("DOCUMENT_END".equals(testInsertDstDocLocationName)) return DOCUMENT_END;
    	if ("DOCUMENT_START".equals(testInsertDstDocLocationName)) return DOCUMENT_START;
    	if ("END_OF_LIST_ITEM".equals(testInsertDstDocLocationName)) return END_OF_LIST_ITEM;
    	if ("FOOTNOTE".equals(testInsertDstDocLocationName)) return FOOTNOTE;
    	if ("HEADER".equals(testInsertDstDocLocationName)) return HEADER;
    	if ("INLINE_SDT".equals(testInsertDstDocLocationName)) return INLINE_SDT;
    	if ("MIDDLE_WITH_SPACE_AFTER".equals(testInsertDstDocLocationName)) return MIDDLE_WITH_SPACE_AFTER;
    	if ("MIDDLE_WITH_SPACE_BEFORE".equals(testInsertDstDocLocationName)) return MIDDLE_WITH_SPACE_BEFORE;
    	if ("MIDDLE".equals(testInsertDstDocLocationName)) return MIDDLE;
    	if ("MIDDLE_OF_LIST_ITEM".equals(testInsertDstDocLocationName)) return MIDDLE_OF_LIST_ITEM;
    	if ("TEXT_BOX".equals(testInsertDstDocLocationName)) return TEXT_BOX;
    	throw new IllegalArgumentException("Unknown TestInsertDstDocLocation name.");
    }
    
    /// <summary>
    /// Returns array of TestInsertDstDocLocation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BEGINNING_OF_LIST_ITEM,
    		BLOCK_LEVEL_SDT,
    		CELL,
    		COMMENT,
    		DOCUMENT_END,
    		DOCUMENT_START,
    		END_OF_LIST_ITEM,
    		FOOTNOTE,
    		HEADER,
    		INLINE_SDT,
    		MIDDLE_WITH_SPACE_AFTER,
    		MIDDLE_WITH_SPACE_BEFORE,
    		MIDDLE,
    		MIDDLE_OF_LIST_ITEM,
    		TEXT_BOX,
    	};
    }

    public static final int length = 15;
}

