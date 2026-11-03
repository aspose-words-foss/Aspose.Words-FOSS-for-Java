// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with block level SDT.
/// </summary>
public class TestBlockLevelSdtDoc extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);
            
            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
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
    /// Checks that paragraph after inserted content is list item and
    /// its 'id' is the same as paragraph's list 'id' before inserted content.
    /// </summary>
    @Test
    public void testListParaAfter() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);

            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                    msAssert.areEqual(getParaBeforeInsertedContent().getListFormat().getListId(), getParaAfterInsertedContent().getListFormat().getListId());
                    break;
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                    msAssert.areEqual(((Paragraph)(getParaBeforeInsertedContent().getPreviousSibling())).getListFormat().getListId(), getParaAfterInsertedContent().getListFormat().getListId());
                    break;
                case TestInsertDstDocLocation.INLINE_SDT:
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);
            if (location != TestInsertDstDocLocation.INLINE_SDT)
            {
                Paragraph currPara = getFirstInsertedPara();
                for (int i = 0; i < 14; i++)
                {
                    msAssert.isNotNull(currPara);
                    switch (location)
                    {
                        case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                            // All paragraphs should be the list items, but those which are inside the tables.
                            if ((i == 5) || (i == 6) || (i == 9) || (i == 10))
                                msAssert.isFalse(currPara.isListItem());
                            else
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);

            // MS Word preserves styles of the inserted paragraphs.
            // However at the beginning of the list item, MS Word applies style of
            // destination document paragraph, if it has "Normal" style.
            if (location == TestInsertDstDocLocation.INLINE_SDT)
            {
                msAssert.areEqual(StyleIdentifier.NORMAL, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
            }
            else
            {
                Paragraph currPara = getFirstInsertedPara();
                for (int i = 0; i < 14; i++)
                {
                    msAssert.isNotNull(currPara);
                    /*StyleIdentifier*/int currParaStyle = currPara.getParagraphStyle().getStyleIdentifier();
                    switch (i)
                    {
                        case 1:
                            msAssert.areEqual(StyleIdentifier.NO_SPACING, currParaStyle);
                            break;

                        case 2:
                        case 7:
                            if (location == TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM)
                                msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, currParaStyle);
                            else
                                msAssert.areEqual(StyleIdentifier.NORMAL, currParaStyle);
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);

            switch (location)
            {
                case TestInsertDstDocLocation.INLINE_SDT:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.NORMAL, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                case TestInsertDstDocLocation.DOCUMENT_START:
                case TestInsertDstDocLocation.CELL:
                    msAssert.isNull(getParaBeforeInsertedContent());
                    break;
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
                default:
                    msAssert.isNotNull(getParaBeforeInsertedContent());
                    msAssert.areEqual(StyleIdentifier.USER, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                    break;
            }
        }
    }

    /// <summary>
    /// Checks whether first inserted node was concatenated with paragraph before it.
    /// </summary>
    @Test
    public void testCheckFirstParasConcatenated() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, location);
            // Inserted content begins with this text.
            int firstInsertedParaTextIdx = getFirstInsertedParaText().indexOf("First inserted paragraph");
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

    /// <summary>
    /// Checks that control chars were inserted into inline level SDT exactly the specified number of times.
    /// </summary>
    @Test
    public void testSpecialCharsInsideInlineSdt() throws Exception
    {
        testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC, TestInsertDstDocLocation.INLINE_SDT);

        msAssert.isTrue(getFirstInsertedParaText().contains("Cell2\rThis is content outside sdtCell outside\tSDT outside\rThis"));
        // There are 2 in text and 1 at the paragraph end.
        msAssert.areEqual(3, getCharCount(getFirstInsertedParaText(), '\r'));
        msAssert.areEqual(1, getCharCount(getFirstInsertedParaText(), '\t'));
    }
}


