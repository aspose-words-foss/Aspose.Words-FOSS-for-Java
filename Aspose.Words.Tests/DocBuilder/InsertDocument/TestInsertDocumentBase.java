// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/12/2014 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.msString;
import com.aspose.Debug;


/// <summary>
/// WORDSNET-5251 Consider providing a one line method in the API for InsertDocument.
/// Base class for all tests for DocumentBuilder.InsertDocument() method.
/// </summary>
public class TestInsertDocumentBase
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Inserts source document of srcType into the destination document at the specified location with Word2002_2010 options by default.
    /// </summary>
    void testInsertDocument(/*TestInsertSrcDocType*/int srcDocType, /*TestInsertDstDocLocation*/int location) throws Exception
    {
        ImportFormatOptions options = new ImportFormatOptions();
        setWord2010Defaults(options);
        testInsertDocument(srcDocType, location, options);
    }

    /// <summary>
    /// Inserts source document of srcType into the destination document at the specified location with the specified insertion options.
    /// </summary>
    void testInsertDocument(/*TestInsertSrcDocType*/int srcDocType, /*TestInsertDstDocLocation*/int location, ImportFormatOptions options) throws Exception
    {
        DocumentBuilder builder = openDstDocument(location);
        Document srcDoc = openSrcDocument(srcDocType);

        // For testing purposes we should know which node is located immediately after inserted content.
        // Current builder's node is always either BookmarkEnd or last run of inline SDT
        // in accordance with this test design.
        Node curNode = (builder.getCurrentNode().getNodeType() == NodeType.BOOKMARK_END)
            ? builder.getCurrentNode().getNextSibling()
            : builder.getCurrentNode();
        
        // If we are at the end of paragraph, then we should know next node to determine node after inserted content.
        if (curNode == null)
            curNode = builder.getCurrentParagraph().getNextSibling();
        
        mFirstInsertedNode = new DocumentInserter(builder).insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);

        // If we was positioned at the end of paragraph, we also might be at the end of its parent. For example, at the end of document.
        // In this case we determine node after inserted content as last child of node where new content was inserted.
        if (curNode == null)
            mNodeAfterInsertedContent = mFirstInsertedNode.getFirstNonMarkupParentNode().getLastChild();
        else
            mNodeAfterInsertedContent = (curNode.getNodeType() == NodeType.PARAGRAPH) ? curNode.getPreviousSibling() : curNode;

        String outFileParams = msString.format("{0}\\{1}_{2}", srcDocType, location, options);
        Debug.writeLine(outFileParams);

        // SonarIgnoreStart
        // Uncomment to generate out files in DocBuilder\InsertDocument directory.
        // string outFileName = String.Format(@"DocBuilder\InsertDocument\{0} out.docx", outFileParams);
        // mDocument.Save(TestUtil.GetInTestOutPath(outFileName));
        // SonarIgnoreEnd
    }

    /// <summary>
    /// Returns true, if source document type can not be inserted at the specified destination document location.
    /// </summary>
    static boolean isBadDstDocTestLocation(/*TestInsertSrcDocType*/int srcDocType, /*TestInsertDstDocLocation*/int location)
    {
        if (srcDocType != TestInsertSrcDocType.MULTI_SECTION_DOC)
            return false;

        switch (location)
        {
            case TestInsertDstDocLocation.CELL:
            case TestInsertDstDocLocation.FOOTNOTE:
            case TestInsertDstDocLocation.HEADER:
            case TestInsertDstDocLocation.TEXT_BOX:
            case TestInsertDstDocLocation.COMMENT:
                return true;

            default:
                return false;
        }
    }

    /// <summary>
    /// Returns how many times specified character occurred in specified string.
    /// </summary>
    static int getCharCount(String text, char ch)
    {
        int res = 0;

        int pos = text.indexOf(ch);
        while (pos > -1)
        {
            res++;
            pos = text.indexOf(ch, pos + 1);
        }

        return res;
    }

    /// <summary>
    /// Opens source document with appropriate content.
    /// </summary>
    static Document openSrcDocument(/*TestInsertSrcDocType*/int srcDocType) throws Exception
    {
        String srcFileName = getSourceName(srcDocType);
        if (srcFileName == null)
            throw new IllegalArgumentException("Unexpected source document.");

        Document doc = TestUtil.open("DocBuilder\\InsertDocument\\Test" + srcFileName + ".docx");
        return doc;
    }

    /// <summary>
    /// Opens destination document and moves <see cref="DocumentBuilder"/> cursor to the location
    /// where we will insert another document.
    /// </summary>
    DocumentBuilder openDstDocument(/*TestInsertDstDocLocation*/int location) throws Exception
    {
        mDocument = TestUtil.open("DocBuilder\\InsertDocument\\TestInsertDocumentDst.docx");

        DocumentBuilder builder = new DocumentBuilder(mDocument);
        String bookmarkName = getLocationName(location);
        if (bookmarkName == null)
            throw new IllegalArgumentException("Unexpected location.");

        builder.moveToBookmark(bookmarkName, true, true);

        if (location == TestInsertDstDocLocation.INLINE_SDT)
        {
            Node node = ((CompositeNode)(builder.getCurrentNode())).getLastChild();

            builder.moveTo(node);
        }

        mCurrentDstDocLocation = location;

        return builder;
    }

    /// <summary>
    /// Sets default options for Word 2002-2010.
    /// </summary>
    private static void setWord2010Defaults(ImportFormatOptions options)
    {
        options.setAdjustSentenceAndWordSpacing(true);
        options.setSmartStyleBehavior(true);
        options.setMergePastedLists(true);
    }

    private static String getLocationName(/*TestInsertDstDocLocation*/int value) 
    {
        switch (value)
    {
            case TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM:
                return "BeginningOfListItem";
            case TestInsertDstDocLocation.BLOCK_LEVEL_SDT:
                return "BlockLevelSdt";
            case TestInsertDstDocLocation.CELL:
                return "Cell";
            case TestInsertDstDocLocation.COMMENT:
                return "Comment";
            case TestInsertDstDocLocation.DOCUMENT_END:
                return "DocumentEnd";
            case TestInsertDstDocLocation.DOCUMENT_START:
                return "DocumentStart";
            case TestInsertDstDocLocation.END_OF_LIST_ITEM:
                return "EndOfListItem";
            case TestInsertDstDocLocation.FOOTNOTE:
                return "Footnote";
            case TestInsertDstDocLocation.HEADER:
                return "Header";
            case TestInsertDstDocLocation.INLINE_SDT:
                return "InlineSdt";
            case TestInsertDstDocLocation.MIDDLE_WITH_SPACE_AFTER:
                return "MiddleWithSpaceAfter";
            case TestInsertDstDocLocation.MIDDLE_WITH_SPACE_BEFORE:
                return "MiddleWithSpaceBefore";
            case TestInsertDstDocLocation.MIDDLE:
                return "Middle";
            case TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM:
                return "MiddleOfListItem";
            case TestInsertDstDocLocation.TEXT_BOX:
                return "TextBox";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "Unknown TestInsertDstDocLocation enum value: " + value);
        }
    }

    private static String getSourceName(/*TestInsertSrcDocType*/int value)
    {
        switch (value)
        {
            case TestInsertSrcDocType.ONE_PARA_DOC:
                return "OneParaDoc";
            case TestInsertSrcDocType.ONE_LIST_ITEM_DOC:
                return "OneListItemDoc";
            case TestInsertSrcDocType.LIST_ITEMS_DOC:
                return "ListItemsDoc";
            case TestInsertSrcDocType.TABLE_DOC_STARTS_WITH_PARA:
                return "TableDocStartsWithPara";
            case TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_STARTS_WITH_PARA:
                return "BlockLevelSdtDocStartsWithPara";
            case TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC_ENDS_WITH_PARA:
                return "BlockLevelSdtDocEndsWithPara";
            case TestInsertSrcDocType.ONE_PARA_DOC_INSIDE_TABLE:
                return "OneParaDocInsideTable";
            case TestInsertSrcDocType.TABLE_DOC_STARTS_WITH_TABLE:
                return "TableDocStartsWithTable";
            case TestInsertSrcDocType.BLOCK_LEVEL_SDT_DOC:
                return "BlockLevelSdtDoc";
            case TestInsertSrcDocType.MULTI_SECTION_DOC:
                return "MultiSectionDoc";
            case TestInsertSrcDocType.MULTI_COLUMN_DOC:
                return "MultiColumnDoc";
            case TestInsertSrcDocType.BLOCK_BOOKMARKS_DOC:
                return "BlockBookmarks";
            case TestInsertSrcDocType.PARA_WITH_NUMBERED_STYLE:
                return "ParaWithNumberedStyle";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "Unknown TestInsertSrcDocType enum value: " + value);
        }
    }

    /// <summary>
    /// Gets all possible locations in the document where we can insert another one.
    /// </summary>
    /*TestInsertDstDocLocation*/int[] getDstDocPossibleLocations() { return G_POSSIBLE_LOCATIONS; }

    /// <summary>
    /// Gets all possible locations inside list item.
    /// </summary>
    static /*TestInsertDstDocLocation*/int[] getListItemLocations() { return G_LIST_ITEM_LOCATIONS; }

    /// <summary>
    /// Gets first node of inserted content.
    /// </summary>
    Node getFirstInsertedNode() { return mFirstInsertedNode; }

    /// <summary>
    /// Gets first paragraph of inserted content.
    /// </summary>
    Paragraph getFirstInsertedPara()
    {
        if (mCurrentDstDocLocation == TestInsertDstDocLocation.INLINE_SDT)
            return (Paragraph) getFirstInsertedNode().getFirstNonMarkupParentNode();
        if ((getFirstInsertedNode().getNodeType() == NodeType.BOOKMARK_START) &&
            ("BlockLevelBookmark1".equals(((BookmarkStart)getFirstInsertedNode()).getName())) &&
            (getFirstInsertedNode().getParentNode().getNodeType() == NodeType.PARAGRAPH))
            return (Paragraph)getFirstInsertedNode().getParentNode();

        return (getFirstInsertedNode().getNodeType() == NodeType.PARAGRAPH)
            ? (Paragraph) getFirstInsertedNode()
            : (Paragraph)getFirstInsertedNode().nextPreOrderOfType(getFirstInsertedNode().getDocument(), NodeType.PARAGRAPH);
    }

    /// <summary>
    /// Gets paragraph before inserted content.
    /// </summary>
    Paragraph getParaBeforeInsertedContent()
    {
        if (mCurrentDstDocLocation == TestInsertDstDocLocation.INLINE_SDT)
            return (Paragraph)getFirstInsertedPara().getPreviousSibling();

        Node prevNode = getFirstInsertedNode().getPreviousSibling();

        if ((getFirstInsertedNode().getNodeType() == NodeType.BOOKMARK_START) &&
            ("BlockLevelBookmark1".equals(((BookmarkStart)getFirstInsertedNode()).getName())) &&
            (getFirstInsertedNode().getParentNode().getNodeType() == NodeType.PARAGRAPH))
            prevNode = getFirstInsertedNode().getParentNode().getPreviousSibling();


        return ((prevNode != null) && (prevNode.getNodeType() == NodeType.PARAGRAPH))
            ? (Paragraph)prevNode
            : null;
    }

    /// <summary>
    /// Gets paragraph after inserted content.
    /// </summary>
    Paragraph getParaAfterInsertedContent()
    {
        if (mCurrentDstDocLocation == TestInsertDstDocLocation.INLINE_SDT)
            return (Paragraph) mNodeAfterInsertedContent.getFirstNonMarkupParentNode().getNextSibling();

        // By design node after inserted content either is a run or a paragraph.
        if (mNodeAfterInsertedContent.getNodeType() == NodeType.PARAGRAPH)
            return (Paragraph) mNodeAfterInsertedContent;

        return (Paragraph) mNodeAfterInsertedContent.getParentNode();
    }

    /// <summary>
    /// Gets first inserted section.
    /// </summary>
    Section getFirstInsertedSection()
    {
        return (Section)getFirstInsertedNode().getAncestor(NodeType.SECTION);
    }

    /// <summary>
    /// Gets second inserted section.
    /// </summary>
    Section getSecondInsertedSection()
    {
        return (Section)getFirstInsertedSection().getNextSibling();
    }

    /// <summary>
    /// Gets third inserted section.
    /// </summary>
    Section getThirdInsertedSection()
    {
        return (getSecondInsertedSection() == null) ? null : (Section)getSecondInsertedSection().getNextSibling();
    }

    /// <summary>
    /// Gets text of first paragraph of inserted content.
    /// </summary>
    String getFirstInsertedParaText()
    {
        return getFirstInsertedPara().getText();
    }

    /// <summary>
    /// Gets first paragraph in second section of inserted content.
    /// </summary>
    Paragraph getFirstInsertedParaInSecondSection()
    {
        if (getSecondInsertedSection() == null)
            return null;

        return (Paragraph)getSecondInsertedSection().getBody().getChild(NodeType.PARAGRAPH, 0, true);
    }

    /// <summary>
    /// Gets first paragraph in third section of inserted content.
    /// </summary>
    Paragraph getFirstInsertedParaInThirdSection()
    {
        if (getThirdInsertedSection() == null)
            return null;

        return (Paragraph)getThirdInsertedSection().getBody().getChild(NodeType.PARAGRAPH, 0, true);
    }

    private Node mFirstInsertedNode;
    private Node mNodeAfterInsertedContent;
    private Document mDocument;

    // Current location in destination document.
    private /*TestInsertDstDocLocation*/int mCurrentDstDocLocation;

    /// <summary>
    /// All possible locations in the document where we can insert another one.
    /// JAVA-changed to not use enum.GetValues() and enum.GetName().
    /// </summary>
    private static /*final*/ /*TestInsertDstDocLocation*/int[] G_POSSIBLE_LOCATIONS = 
    { 
        TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM,
        TestInsertDstDocLocation.BLOCK_LEVEL_SDT,
        TestInsertDstDocLocation.CELL,
        TestInsertDstDocLocation.COMMENT,
        TestInsertDstDocLocation.DOCUMENT_END,
        TestInsertDstDocLocation.DOCUMENT_START,
        TestInsertDstDocLocation.END_OF_LIST_ITEM,
        TestInsertDstDocLocation.FOOTNOTE,
        TestInsertDstDocLocation.HEADER,
        TestInsertDstDocLocation.INLINE_SDT,
        TestInsertDstDocLocation.MIDDLE_WITH_SPACE_AFTER,
        TestInsertDstDocLocation.MIDDLE_WITH_SPACE_BEFORE,
        TestInsertDstDocLocation.MIDDLE,
        TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM,
        TestInsertDstDocLocation.TEXT_BOX
    };

    /// <summary>
    /// All possible locations inside list item.
    /// </summary>
    private static /*final*/ /*TestInsertDstDocLocation*/int[] G_LIST_ITEM_LOCATIONS =
    {
        TestInsertDstDocLocation.BEGINNING_OF_LIST_ITEM,
        TestInsertDstDocLocation.MIDDLE_OF_LIST_ITEM,
        TestInsertDstDocLocation.END_OF_LIST_ITEM
    };
}

