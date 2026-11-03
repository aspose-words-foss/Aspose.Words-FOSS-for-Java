// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with block level SDT and paragraph at the beginning.
/// </summary>
@Test
public class TestBlockLevelSdtDocStartsWithPara extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is a list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);

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
    /// Checks that paragraph after inserted with default options content is a list item and
    /// its 'id' is not the same as paragraph's list 'id' before inserted content
    /// when inserting at the beginning or middle of list item.
    /// </summary>
    @Test
    public void testListParaAfterWithDefaultOptions() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);
            switch (location)
            {
                case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                    msAssert.areNotEqual(getParaBeforeInsertedContent().getListFormat().getListId(), getParaAfterInsertedContent().getListFormat().getListId());
                    break;
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
    /// Checks that paragraph after inserted with disabled 'MergeLists' option content is a list item and
    /// its 'id' is the same as paragraph's list 'id' before inserted content.
    /// </summary>
    @Test
    public void testListParaAfterWithMergeOptionDisabled() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location, new ImportFormatOptions());
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);
            int parasInsertedCount = (location == TestInsertDstDocLocation.INLINE_SDT) ? 1 : 6;
            Paragraph currPara = getFirstInsertedPara();
            for (int i = 0; i < parasInsertedCount; i++)
            {
                msAssert.isNotNull(currPara);
                msAssert.isFalse(currPara.isListItem());
                currPara = (Paragraph)currPara.nextPreOrderOfType(currPara.getDocument(), NodeType.PARAGRAPH);
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);

            Paragraph currPara = getFirstInsertedPara();
            if (location == TestInsertDstDocLocation.INLINE_SDT)
            {
                msAssert.isNotNull(getFirstInsertedPara());
                msAssert.areEqual(StyleIdentifier.NORMAL, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
            }
            else
            {
                // Totally inserted srcDoc has 6 paragraphs. Check them all.
                msAssert.isNotNull(currPara);
                for (int i = 0; i < 6; i++)
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
                                msAssert.areEqual(StyleIdentifier.NO_SPACING, currParaStyle);
                            break;
                        case 2:
                        case 4:
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
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);

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
    /// Checks that first inserted node was concatenated with paragraph before it.
    /// </summary>
    @Test
    public void testCheckFirstParasConcatenated() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, location);
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

    /// <summary>
    /// Checks 'AdjustSpaces' option when inserting document.
    /// </summary>
    @Test
    public void testAjustSpacing() throws Exception
    {
        //Checks when AdjustSpaces is enabled.
        testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, TestInsertDstDocLocation.MIDDLE);
        msAssert.areEqual(25, getFirstInsertedParaText().indexOf("mid First"));
        msAssert.isTrue(getParaAfterInsertedContent().getText().startsWith(" dle of the body"));

        //Checks when AdjustSpaces is disabled.
        testInsertDocument(TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA, TestInsertDstDocLocation.MIDDLE, new ImportFormatOptions());
        msAssert.areEqual(25, getFirstInsertedParaText().indexOf("midFirst"));
        msAssert.isTrue(getParaAfterInsertedContent().getText().startsWith("dle of the body"));
    }
}


