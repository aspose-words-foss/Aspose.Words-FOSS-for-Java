// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/05/2022 by Evgeniy Zaytsev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;


/// <summary>
/// Tests insertion of a document.
/// </summary>
public class TestInsertDoc extends TestInsertDocumentBase
{


    /// <summary>
    /// Checks that exception is thrown when inserting into the bad destination location.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testBadDstLocations() throws Exception
    {
        for (/*TestInsertSrcDocType*/int srcDocType : G_SRC_DOC_TYPES)
            for (/*TestInsertDstDocLocation*/int dstDocLocation : getDstDocPossibleLocations())
            {
                if (isBadDstDocTestLocation(srcDocType, dstDocLocation))
                    testInsertDocument(srcDocType, dstDocLocation);
            }
    }

    /// <summary>
    /// All possible types of source document for insertion.
    /// </summary>
    private static /*final*/ /*TestInsertSrcDocType*/int[] G_SRC_DOC_TYPES =
    {
        TestInsertSrcDocType.ONE_PARA_DOC,
        TestInsertSrcDocType.ONE_LIST_ITEM_DOC,
        TestInsertSrcDocType.LIST_ITEMS_DOC,
        TestInsertSrcDocType.TABLE_DOC_STARTS_WITH_PARA,
        TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA,
        TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA,
        TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE,
        TestInsertSrcDocType.TABLE_DOC_STARTS_WITH_TABLE,
        TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC,
        TestInsertSrcDocType.MULTI_SECTION_DOC,
        TestInsertSrcDocType.MULTI_COLUMN_DOC,
        TestInsertSrcDocType.BLOCK_BOOKMARKS_DOC,
        TestInsertSrcDocType.PARA_WITH_NUMBERED_STYLE
    };

    /// <summary>
    /// All source test documents starts with the following text.
    /// </summary>
    private static final String INSERTED_TEXT = "First inserted paragraph";
}

