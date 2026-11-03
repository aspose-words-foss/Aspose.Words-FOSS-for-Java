// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with only single paragraph.
/// </summary>
@Test
public class TestOneParaDoc extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is a list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);

            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                case TestInsertDstDocLocation.DOCUMENT_END:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.isTrue(getParaBeforeInsertedContent().isListItem());
                    break;
                case TestInsertDstDocLocation.MIDDLE:
                case TestInsertDstDocLocation.MIDDLE_WITH_SPACE_AFTER:
                case TestInsertDstDocLocation.MIDDLE_WITH_SPACE_BEFORE:
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.isFalse(getParaBeforeInsertedContent().isListItem());
                    break;
                default:
                    msAssert.isNull(getParaBeforeInsertedContent());
                    break;
            }
        }
    }

    /// <summary>
    /// Checks that paragraph after inserted content is a list item and
    /// its 'id' is the same as paragraph's list 'id' before inserted content.
    /// </summary>
    @Test
    public void testListParaAfter() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);
            
            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                    msAssert.areEqual(getParaBeforeInsertedContent().getListFormat().getListId(), getParaAfterInsertedContent().getListFormat().getListId());
                    break;
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.isNotNull(getParaAfterInsertedContent());
                    msAssert.isTrue(getParaAfterInsertedContent().isListItem());
                    break;
                default:
                    msAssert.isNotNull(getParaAfterInsertedContent());
                    msAssert.isFalse(getParaAfterInsertedContent().isListItem());
                    break;
            }
        }
    }

    /// <summary>
    /// Checks that inserted paragraph is a list item.
    /// </summary>
    @Test
    public void testListFirstInsertedPara() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);

            msAssert.isNotNull(getFirstInsertedPara());

            if (location == TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM)
                msAssert.isTrue(getFirstInsertedPara().isListItem());
            else
                msAssert.isFalse(getFirstInsertedPara().isListItem());
        }
    }

    /// <summary>
    /// Checks style of the inserted paragraph.
    /// </summary>
    @Test
    public void testStyleFirstInsertedPara() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);
            
            msAssert.isNotNull(getFirstInsertedPara());
            
            if (location == TestInsertDstDocLocation.INLINE_SDT)
                msAssert.areEqual(StyleIdentifier.NORMAL, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
            else
                msAssert.areEqual(StyleIdentifier.USER, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
        }
    }

    /// <summary>
    /// Checks style of the paragraph before inserted content.
    /// </summary>
    @Test
    public void testStyleParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);

            switch (location)
            {
                case TestInsertDstDocLocation.DOCUMENT_START:
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.BLOCK_LEVEL_SDT:
                case TestInsertDstDocLocation.COMMENT:
                case TestInsertDstDocLocation.FOOTNOTE:
                case TestInsertDstDocLocation.HEADER:
                case TestInsertDstDocLocation.TEXT_BOX:
                    msAssert.isNull(getParaBeforeInsertedContent());
                    break;
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                case TestInsertDstDocLocation.DOCUMENT_END:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                default:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.NORMAL, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
            }
        }
    }

    /// <summary>
    /// Checks that inserted paragraph was concatenated with the paragraph before it.
    /// </summary>
    @Test
    public void testCheckFirstParasConcatenated() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC, location);

            int firstInsertedParaTextIdx = getFirstInsertedParaText().indexOf("First inserted paragraph");
            switch (location)
            {
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.DOCUMENT_START:
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                    msAssert.areEqual(0, firstInsertedParaTextIdx);
                    break;
                default:
                    msAssert.less(1, firstInsertedParaTextIdx);
                    break;
            }
        }
    }
}


