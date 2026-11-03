// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Tests insertion of document with multiple sections.
/// </summary>
@Test
public class TestMultiSectionDoc extends TestInsertDocumentBase
{
    /// <summary>
    /// Checks that paragraph before inserted content is a list item.
    /// </summary>
    @Test
    public void testListParaBefore() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
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
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
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
    }

    /// <summary>
    /// Checks that inserted paragraphs are list items.
    /// </summary>
    @Test
    public void testListInsertedParagraphs() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
                switch (location)
                {
                    case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                        msAssert.isTrue(getFirstInsertedPara().isListItem());
                        msAssert.isTrue((getFirstInsertedParaInSecondSection() != null) && (getFirstInsertedParaInSecondSection().isListItem()));
                        msAssert.isTrue((getFirstInsertedParaInThirdSection() != null) && (getFirstInsertedParaInThirdSection().isListItem()));
                        break;
                    default:
                        msAssert.isFalse(getFirstInsertedPara().isListItem());
                        msAssert.isFalse((getFirstInsertedParaInSecondSection() != null) && (getFirstInsertedParaInSecondSection().isListItem()));
                        msAssert.isFalse((getFirstInsertedParaInThirdSection() != null) && (getFirstInsertedParaInThirdSection().isListItem()));
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
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
                if (location != TestInsertDstDocLocation.INLINE_SDT)
                {
                    msAssert.isNotNull(getFirstInsertedParaInSecondSection());
                    msAssert.isNotNull(getFirstInsertedParaInThirdSection());
                    msAssert.areEqual(StyleIdentifier.USER, getFirstInsertedPara().getParagraphStyle().getStyleIdentifier());
                    msAssert.areEqual(StyleIdentifier.USER, getFirstInsertedParaInSecondSection().getParagraphStyle().getStyleIdentifier());
                    msAssert.areEqual(StyleIdentifier.USER, getFirstInsertedParaInThirdSection().getParagraphStyle().getStyleIdentifier());
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
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
                switch (location)
                {
                    case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                    case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                    case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                    case TestInsertDstDocLocation.DOCUMENT_END:
                        msAssert.isNotNull(getParaBeforeInsertedContent());
                        msAssert.areEqual(StyleIdentifier.LIST_PARAGRAPH, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                        break;
                    case TestInsertDstDocLocation.DOCUMENT_START:
                    case TestInsertDstDocLocation.BLOCK_LEVEL_SDT:
                        msAssert.isNull(getParaBeforeInsertedContent());
                        break;
                    default:
                        msAssert.isNotNull(getParaBeforeInsertedContent());
                        msAssert.areEqual(StyleIdentifier.NORMAL, getParaBeforeInsertedContent().getParagraphStyle().getStyleIdentifier());
                        break;
                }
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
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);

                int firstInsertedParaTextIdx = getFirstInsertedParaText().indexOf("First inserted paragraph");
                switch (location)
                {
                    case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
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

    /// <summary>
    /// Checks insertion into inline SDT with default options.
    /// </summary>
    @Test
    public void testInsertionIntoInlineSdtWithDefaultOptions() throws Exception
    {
        testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, TestInsertDstDocLocation.INLINE_SDT);
        final String INSERTED_TEXT =
            "inline level First inserted paragraph\t\t This is content of the second section Last inserted paragraphSDT";

        msAssert.isTrue(getFirstInsertedParaText().contains(INSERTED_TEXT));
    }

    /// <summary>
    /// Checks insertion into inline SDT with disabled 'AdjustSpacing' option.
    /// </summary>
    @Test
    public void testInsertionIntoInlineSdtWithAdjustSpacingOptionDisabled() throws Exception
    {
        testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, TestInsertDstDocLocation.INLINE_SDT, new ImportFormatOptions());
        final String INSERTED_TEXT =
            "inline levelFirst inserted paragraph\t\t This is content of the second section Last inserted paragraphSDT";

        msAssert.isTrue(getFirstInsertedParaText().contains(INSERTED_TEXT));
    }


    /// <summary>
    /// Checks that document has needed count of the sections and appropriate headers-footers.
    /// </summary>
    @Test
    public void testSectionsNumber() throws Exception
    {
        for (/*TestInsertDstDocLocation*/int location : getDstDocPossibleLocations())
        {
            if (!isBadLocation(location))
            {
                testInsertDocument(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
                switch (location)
                {
                    case TestInsertDstDocLocation.INLINE_SDT:
                        msAssert.isTrue("This is header\r".equals(getFirstInsertedSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText()));
                        msAssert.areEqual(1, getFirstInsertedNode().fetchDocument().getSections().getCount());
                        break;
                    default:
                        msAssert.areEqual(3, getFirstInsertedNode().fetchDocument().getSections().getCount());
                        msAssert.isNotNull(getSecondInsertedSection());
                        msAssert.isNotNull(getThirdInsertedSection());
                        msAssert.isTrue("Header section1\r".equals(getFirstInsertedSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_FIRST).getText()));
                        msAssert.isTrue("Source document header second section\r".equals(getSecondInsertedSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText()));
                        msAssert.isTrue("This is header\r".equals(getThirdInsertedSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getText()));
                        break;
                }
            }
        }
    }

    /// <summary>
    /// Returns true, if document can not be inserted at the specified location.
    /// </summary>
    private static boolean isBadLocation(/*TestInsertDstDocLocation*/int location)
    {
        return isBadDstDocTestLocation(TestInsertSrcDocType.MULTI_SECTION_DOC, location);
    }
}


