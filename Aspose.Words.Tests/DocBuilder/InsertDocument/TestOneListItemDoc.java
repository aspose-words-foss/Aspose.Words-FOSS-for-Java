// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with only single list item paragraph.
/// </summary>
@Test
public class TestOneListItemDoc extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is a list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location);

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
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location);

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
    /// Checks numbering styles of inserted paragraphs with default options.
    /// </summary>
    @Test
    public void testListInsertedParagraphsWithDefaultOptions() throws Exception
    {
        testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM);
        msAssert.isNotNull(getFirstInsertedPara());
        msAssert.areEqual(NumberStyle.ARABIC, getFirstInsertedPara().getListFormat().getListLevel().getNumberStyle());

        testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM);
        msAssert.isNotNull(getFirstInsertedPara());
        msAssert.areEqual(NumberStyle.BULLET, getFirstInsertedPara().getListFormat().getListLevel().getNumberStyle());

        testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM);
        msAssert.isNotNull(getFirstInsertedPara());
        msAssert.areEqual(NumberStyle.BULLET, getFirstInsertedPara().getListFormat().getListLevel().getNumberStyle());
    }

    /// <summary>
    /// Checks numbering styles of inserted paragraphs with disabled 'MergeLists' option.
    /// </summary>
    @Test
    public void testListInsertedParagraphsWithMergeOptionDisabled() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location, new ImportFormatOptions());

            switch (location)
            {
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.isNotNull(getFirstInsertedPara());
                    msAssert.isFalse(getFirstInsertedPara().isListItem());
                    break;
                default:
                    msAssert.isNotNull(getFirstInsertedPara());
                    msAssert.areEqual(NumberStyle.BULLET, getFirstInsertedPara().getListFormat().getListLevel().getNumberStyle());
                    break;
            }
        }
    }
    
    /// <summary>
    /// Checks styles of the inserted paragraphs.
    /// </summary>
    @Test
    public void testStyleInsertedParagraphs() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location);

            msAssert.isNotNull(getFirstInsertedPara());

            msAssert.areEqual((location == TestInsertDstDocLocation.INLINE_SDT)
                    ? StyleIdentifier.NORMAL : StyleIdentifier.USER, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
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
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location);

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
    /// Checks that first inserted node was concatenated with the paragraph before it.
    /// </summary>
    @Test
    public void testCheckFirstParasConcatenated() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_LIST_ITEM_DOC, location);

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


