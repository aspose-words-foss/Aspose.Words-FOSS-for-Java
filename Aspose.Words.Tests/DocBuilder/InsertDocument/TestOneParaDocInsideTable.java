// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with single paragraph inside table.
/// </summary>
@Test
public class TestOneParaDocInsideTable extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is a list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.isTrue(getParaBeforeInsertedContent().isListItem());
                    break;
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.DOCUMENT_START:
                    msAssert.isNull(getParaBeforeInsertedContent());
                    break;
                default:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.isFalse(getParaBeforeInsertedContent().isListItem());
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
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

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
    /// Checks that inserted paragraphs are list items.
    /// </summary>
    @Test
    public void testListInsertedParagraphs() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

            switch (location)
            {
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.isFalse(getFirstInsertedPara().isListItem());
                    break;

                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                {
                    msAssert.isTrue(getFirstInsertedPara().isListItem());
                    Paragraph secondInsertedPara = (Paragraph)getFirstInsertedNode().getNextSibling();
                    msAssert.isFalse(secondInsertedPara.isListItem());
                    break;
                }
                default:
                {
                    msAssert.isFalse(getFirstInsertedPara().isListItem());
                    Paragraph secondInsertedPara = (Paragraph)getFirstInsertedNode().getNextSibling();
                    msAssert.isFalse(secondInsertedPara.isListItem());
                    break;
                }
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
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

            switch (location)
            {
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.areEqual(StyleIdentifier.NORMAL, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
                    break;

                default:
                    msAssert.areEqual(StyleIdentifier.USER, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
                    Paragraph secondInsertedPara = (Paragraph)getFirstInsertedNode().getNextSibling();
                    msAssert.areEqual(StyleIdentifier.NORMAL, secondInsertedPara.getParagraphStyle().getStyleIdentifier());
                    break;
            }
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
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                case TestInsertDstDocLocation.COMMENT:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.COMMENT_TEXT, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                case TestInsertDstDocLocation.FOOTNOTE:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.FOOTNOTE_TEXT, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                case TestInsertDstDocLocation.HEADER:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.HEADER, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.DOCUMENT_START:
                    msAssert.isNull(getParaBeforeInsertedContent());
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
            testInsertDocument(TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE, location);

            int firstInsertedParaTextIdx = getFirstInsertedPara().getText().indexOf("First inserted paragraph");
            switch (location)
            {
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.less(1, firstInsertedParaTextIdx);
                    break;
                default:
                    msAssert.areEqual(0, firstInsertedParaTextIdx);
                    break;
            }
        }
    }
}


