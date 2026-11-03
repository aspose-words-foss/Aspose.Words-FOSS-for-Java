// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates possible types of documents for insertion.
/// </summary>
/*enum*/ final class TestInsertSrcDocType
{
    private TestInsertSrcDocType(){}
    
    /// <summary>
    /// Source document contains single paragraph with text.
    /// </summary>
    public static final int ONE_PARA_DOC = 0;

    /// <summary>
    /// Source document contains single paragraph that is a list item.
    /// </summary>
    public static final int ONE_LIST_ITEM_DOC = 1;
    
    /// <summary>
    /// Source document contains list items and starting and ending with list items.
    /// </summary>
    public static final int LIST_ITEMS_DOC = 2;
    
    /// <summary>
    /// Source document contains tables and starts with a paragraph.
    /// </summary>
    public static final int TABLE_DOC_STARTS_WITH_PARA = 3;
    
    /// <summary>
    /// Source document contains a block level SDT and starts with a paragraph.
    /// </summary>
    public static final int BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA = 4;
    
    /// <summary>
    /// Source document contains a block level SDT and ends with a paragraph.
    /// </summary>
    public static final int BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA = 5;
    
    /// <summary>
    /// Source document contains a single table and a paragraph inside it.
    /// </summary>
    public static final int ONE_PARA_DOC_INSIDE_TABLE = 6;
    
    /// <summary>
    /// Source document contains tables and is started with a table.
    /// </summary>
    public static final int TABLE_DOC_STARTS_WITH_TABLE = 7;
    
    /// <summary>
    /// Source document starts with a block level SDT.
    /// </summary>
    public static final int BLOCK_LEVEL_SDT_DOC = 8;
    
    /// <summary>
    /// Source document is multisectional and contains headers/footers.
    /// </summary>
    public static final int MULTI_SECTION_DOC = 9;
    
    /// <summary>
    /// Source document has multiple columns.
    /// </summary>
    public static final int MULTI_COLUMN_DOC = 10;

    /// <summary>
    /// Source document has block-level bookmarks.
    /// </summary>
    public static final int BLOCK_BOOKMARKS_DOC = 11;

    /// <summary>
    /// Source document contains paragraph with 'Heading1' style that has numbering.
    /// The same style exists in destination document, but it is without numbering there.
    /// </summary>
    public static final int PARA_WITH_NUMBERED_STYLE = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TestInsertSrcDocType value.
    /// </summary>
    public static String getName(/*TestInsertSrcDocType*/int testInsertSrcDocType)
    {
    	switch (testInsertSrcDocType)
    	{
    		case ONE_PARA_DOC: return "ONE_PARA_DOC";
    		case ONE_LIST_ITEM_DOC: return "ONE_LIST_ITEM_DOC";
    		case LIST_ITEMS_DOC: return "LIST_ITEMS_DOC";
    		case TABLE_DOC_STARTS_WITH_PARA: return "TABLE_DOC_STARTS_WITH_PARA";
    		case BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA: return "BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA";
    		case BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA: return "BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA";
    		case ONE_PARA_DOC_INSIDE_TABLE: return "ONE_PARA_DOC_INSIDE_TABLE";
    		case TABLE_DOC_STARTS_WITH_TABLE: return "TABLE_DOC_STARTS_WITH_TABLE";
    		case BLOCK_LEVEL_SDT_DOC: return "BLOCK_LEVEL_SDT_DOC";
    		case MULTI_SECTION_DOC: return "MULTI_SECTION_DOC";
    		case MULTI_COLUMN_DOC: return "MULTI_COLUMN_DOC";
    		case BLOCK_BOOKMARKS_DOC: return "BLOCK_BOOKMARKS_DOC";
    		case PARA_WITH_NUMBERED_STYLE: return "PARA_WITH_NUMBERED_STYLE";
    		default: return "Unknown TestInsertSrcDocType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TestInsertSrcDocType value.
    /// </summary>
    public static String toString(/*TestInsertSrcDocType*/int testInsertSrcDocType)
    {
    	switch (testInsertSrcDocType)
    	{
    		case ONE_PARA_DOC: return "OneParaDoc";
    		case ONE_LIST_ITEM_DOC: return "OneListItemDoc";
    		case LIST_ITEMS_DOC: return "ListItemsDoc";
    		case TABLE_DOC_STARTS_WITH_PARA: return "TableDocStartsWithPara";
    		case BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA: return "BlockLevelSdtDocStartsWithPara";
    		case BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA: return "BlockLevelSdtDocEndsWithPara";
    		case ONE_PARA_DOC_INSIDE_TABLE: return "OneParaDocInsideTable";
    		case TABLE_DOC_STARTS_WITH_TABLE: return "TableDocStartsWithTable";
    		case BLOCK_LEVEL_SDT_DOC: return "BlockLevelSdtDoc";
    		case MULTI_SECTION_DOC: return "MultiSectionDoc";
    		case MULTI_COLUMN_DOC: return "MultiColumnDoc";
    		case BLOCK_BOOKMARKS_DOC: return "BlockBookmarksDoc";
    		case PARA_WITH_NUMBERED_STYLE: return "ParaWithNumberedStyle";
    		default: return "Unknown TestInsertSrcDocType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TestInsertSrcDocType name.
    /// </summary>
    public static /*TestInsertSrcDocType*/int fromName(String testInsertSrcDocTypeName)
    {
    	if ("ONE_PARA_DOC".equals(testInsertSrcDocTypeName)) return ONE_PARA_DOC;
    	if ("ONE_LIST_ITEM_DOC".equals(testInsertSrcDocTypeName)) return ONE_LIST_ITEM_DOC;
    	if ("LIST_ITEMS_DOC".equals(testInsertSrcDocTypeName)) return LIST_ITEMS_DOC;
    	if ("TABLE_DOC_STARTS_WITH_PARA".equals(testInsertSrcDocTypeName)) return TABLE_DOC_STARTS_WITH_PARA;
    	if ("BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA".equals(testInsertSrcDocTypeName)) return BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA;
    	if ("BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA".equals(testInsertSrcDocTypeName)) return BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA;
    	if ("ONE_PARA_DOC_INSIDE_TABLE".equals(testInsertSrcDocTypeName)) return ONE_PARA_DOC_INSIDE_TABLE;
    	if ("TABLE_DOC_STARTS_WITH_TABLE".equals(testInsertSrcDocTypeName)) return TABLE_DOC_STARTS_WITH_TABLE;
    	if ("BLOCK_LEVEL_SDT_DOC".equals(testInsertSrcDocTypeName)) return BLOCK_LEVEL_SDT_DOC;
    	if ("MULTI_SECTION_DOC".equals(testInsertSrcDocTypeName)) return MULTI_SECTION_DOC;
    	if ("MULTI_COLUMN_DOC".equals(testInsertSrcDocTypeName)) return MULTI_COLUMN_DOC;
    	if ("BLOCK_BOOKMARKS_DOC".equals(testInsertSrcDocTypeName)) return BLOCK_BOOKMARKS_DOC;
    	if ("PARA_WITH_NUMBERED_STYLE".equals(testInsertSrcDocTypeName)) return PARA_WITH_NUMBERED_STYLE;
    	throw new IllegalArgumentException("Unknown TestInsertSrcDocType name.");
    }
    
    /// <summary>
    /// Returns array of TestInsertSrcDocType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ONE_PARA_DOC,
    		ONE_LIST_ITEM_DOC,
    		LIST_ITEMS_DOC,
    		TABLE_DOC_STARTS_WITH_PARA,
    		BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA,
    		BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA,
    		ONE_PARA_DOC_INSIDE_TABLE,
    		TABLE_DOC_STARTS_WITH_TABLE,
    		BLOCK_LEVEL_SDT_DOC,
    		MULTI_SECTION_DOC,
    		MULTI_COLUMN_DOC,
    		BLOCK_BOOKMARKS_DOC,
    		PARA_WITH_NUMBERED_STYLE,
    	};
    }

    public static final int length = 13;
}

