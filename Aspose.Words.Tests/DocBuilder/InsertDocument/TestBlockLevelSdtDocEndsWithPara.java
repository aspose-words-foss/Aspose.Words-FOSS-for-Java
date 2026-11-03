// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with block level SDT and paragraph at the end.
/// </summary>
@Test
public class TestBlockLevelSdtDocEndsWithPara extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);

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
    /// Checks that paragraph after inserted content is list item and
    /// its 'id' is the same, as paragraph's list 'id' before inserted content.
    /// </summary>
    @Test
    public void testListParaAfter() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);

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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);

            if (location == TestInsertDstDocLocation.INLINE_SDT)
            {
                msAssert.isFalse(getFirstInsertedPara().isListItem());
            }
            else
            {
                Paragraph currPara = getFirstInsertedPara();
                for (int i = 0; i < 8; i++)
                {
                    switch (location)
                    {
                        case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                            msAssert.isTrue(currPara.isListItem());
                            break;
                        default:
                            msAssert.isFalse(currPara.isListItem());
                            break;
                    }
                    currPara = (Paragraph)currPara.nextPreOrderOfType(currPara.getDocument(), NodeType.PARAGRAPH);
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);

            Paragraph currPara = getFirstInsertedPara();
            if (location == TestInsertDstDocLocation.INLINE_SDT)
            {
                msAssert.areEqual(StyleIdentifier.NORMAL, currPara.getParagraphStyle().getStyleIdentifier()); 
            }
            else
            {
                // Check all inserted paragraphs.
                for (int i = 0; i < 8; i++)
                {
                    /*StyleIdentifier*/int currParaStyle = currPara.getParagraphStyle().getStyleIdentifier();
                    switch (i)
                    {
                        case 1:
                            if (location == TestInsertDstDocLocation.DOCUMENT_END)
                            {
                                msAssert.areEqual(StyleIdentifier.FOOTNOTE_TEXT, currParaStyle);
                                currPara = (Paragraph) currPara.nextPreOrderOfType(currPara.getDocument(), NodeType.PARAGRAPH);
                            }
                            else
                                msAssert.areEqual(StyleIdentifier.USER, currParaStyle);
                            break;
                        case 2:
                            if (location == TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM)
                                msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, currParaStyle);
                            else
                                msAssert.areEqual(StyleIdentifier.NORMAL, currParaStyle);
                            break;
                        case 4:
                        case 5:
                            msAssert.areEqual(StyleIdentifier.NO_SPACING, currParaStyle);
                            break;
                        default:
                            msAssert.areEqual(StyleIdentifier.USER, currParaStyle);
                            break;
                    }

                    currPara = (Paragraph) currPara.nextPreOrderOfType(currPara.getDocument(), NodeType.PARAGRAPH);
                }
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);

            switch (location)
            {
                case TestInsertDstDocLocation.DOCUMENT_START:
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.BLOCK_LEVEL_SDT:
                case TestInsertDstDocLocation.COMMENT:
                case TestInsertDstDocLocation.HEADER:
                case TestInsertDstDocLocation.TEXT_BOX:
                case TestInsertDstDocLocation.FOOTNOTE:
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
    /// Checks that first inserted paragraph was concatenated with split paragraph inside destination document.
    /// </summary>
    @Test
    public void testCheckFirstParasConcatenated() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA, location);
            // Inserted content begins with this text.
            int firstInsertedParaTextIdx = getFirstInsertedParaText().indexOf("First inserted paragraph");
            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.CELL:
                case TestInsertDstDocLocation.DOCUMENT_START:
                    msAssert.areEqual(0, firstInsertedParaTextIdx);
                    break;
                default:
                    msAssert.less(1, firstInsertedParaTextIdx);
                    break;
            }
        }
    }
}


