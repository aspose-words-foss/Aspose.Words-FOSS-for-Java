// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;


/// <summary>
/// Arranges paragraphs imported from HTML and inserts them into the document model.
/// </summary>
class HtmlParagraphArranger
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlParagraphArranger(
        HtmlReader htmlReader,
        HtmlBidiTextArranger bidiTextArranger,
        DocumentBuilder builder,
        DocumentFormatter documentFormatter,
        HtmlCommonBorderResolver commonBorderResolver,
        ParaPr baseParaPr,
        HtmlListReaderManager listReaderManager,
        HtmlBlockReader htmlBlockReader,
        boolean canReuseListItem)
    {
        mHtmlReader = htmlReader;
        mBidiTextArranger = bidiTextArranger;
        mBuilder = builder;
        mDocumentFormatter = documentFormatter;
        mCommonBorderResolver = commonBorderResolver;
        mBaseParaPr = baseParaPr;
        mListReaderManager = listReaderManager;

        isParaStartNeeded(false);
        mCanReuseListItem = canReuseListItem;

        mIsLastParagraphCreatedImplicitly = true;

        mCurrentParagraphs = new msStack<Paragraph>();

        mEmptyInHtmlParagraphs = new HashSetGeneric<Paragraph>();
        mHtmlBlockReader = htmlBlockReader;
    }

    void startParaIfNeeded() throws Exception
    {
        if (isParaStartNeeded())
        {
            boolean newParagraphStarted = startPara();
            if (newParagraphStarted)
            {
                mCommonBorderResolver.collectParagraphWithoutBorder(mBuilder.getCurrentParagraph());
                mIsLastParagraphCreatedImplicitly = true;
            }

            mHtmlReader.applyParagraphFormatting();

            if (newParagraphStarted)
                mHtmlBlockReader.updateCurrentParagraph(false);
        }
    }

    boolean startPara() throws Exception
    {
        // We explicitly wrote text at the end of the previous paragraph.
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        boolean isNewParaStarted = false;

        boolean reuseCurrentParagraph = canReuseCurrentParagraph();
        if (!reuseCurrentParagraph)
        {
            mBidiTextArranger.rearrangeAndWriteText();

            mBuilder.ensureAtStructuredDocumentTagEnd();

            mBuilder.insertParagraph();
            isNewParaStarted = true;
        }

        isParaStartNeeded(false);
        mIsLastParagraphCreatedImplicitly = false;

        // Let's preserve list id since paragraphs inside lists should be list items or should be indented.
        int oldListId = mBuilder.getParagraphFormat().getListId();
        ListLevel oldListLevel = (oldListId != 0) && (mListReaderManager != null) &&
                                 (mListReaderManager.getCurrentListReader() != null)
            ? mListReaderManager.getCurrentListReader().getCurrentListLevel()
            : null;

        // Preserve the HTML block for the current paragraph.
        // It allows to prevent breaking the HTML block
        // during inserting the HTML document into paragraph enclosed by the HTML block.
        int htmlBlockId = mBuilder.getCurrentParagraph().getParaPr().getHtmlBlockId();

        // This is the beginning of building up new paragraph formatting upon the base formatting.
        mBuilder.getCurrentParagraph().setParaPr(AttrCollectionExtension.deepClone(mBaseParaPr));

        if (htmlBlockId != 0)
            mBuilder.getCurrentParagraph().getParaPr().setHtmlBlockId(htmlBlockId);

        // RK We are preserving list formatting. I am not sure why, I am just adding a comment.
        if ((mListReaderManager != null) && (mListReaderManager.getCurrentListReader() != null) &&
            (mListReaderManager.getCurrentListReader().getCurrentLevelNumber() != -1) && (oldListId != 0) &&
            reuseCurrentParagraph)
        {
            //Do that if some text wasn't already read
            mBuilder.getParagraphFormat().setListId(oldListId);
            // WORDSNET-7191 <p> inside <li> which is nested in a <ol> is imported incorrectly.
            // Restore paragraph list level to avoid this issue.
            if (oldListLevel != null)
                mBuilder.getParagraphFormat().setListLevel(oldListLevel.getLevelNumber());
        }

        // Font formatting of the HTML block element being processed is translated into font formatting of the corresponding
        // paragraph break character. Paragraph break character formatting affects list label formatting, so it is important
        // to keep it correct.
        mHtmlReader.applyFontFormatting(true);
        mBuilder.getCurrentParagraph().setParagraphBreakRunPr(mBuilder.getRunPrCopy());

        return isNewParaStarted;
    }

    void endPara()
    {
        // We explicitly wrote pending text at the end of the block element.
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        // WORDSNET-22574 List items must always be closed at end tags, because the corresponding paragraphs must
        // not be reused. If we don't do this, empty list items will not be imported from HTML in sertain scenarios.
        if (mDocumentFormatter.getCurrentElementHasChildren() || isAtStartOfListItem())
        {
            isParaStartNeeded(true);
        }
    }

    /// <summary>
    /// Sets a value indicating that the current paragraph is empty in the HTML document.
    /// </summary>
    void markCurrentParagraphAsEmptyInHtml()
    {
        mEmptyInHtmlParagraphs.add(mBuilder.getCurrentParagraph());
    }

    /// <summary>
    /// Starts a new paragraph if the current paragraph is empty in the HTML document.
    /// </summary>
    void closeCurrentParagraphIfEmptyInHtml()
    {
        if (isParagraphEmptyInHtml(mBuilder.getCurrentParagraph()))
        {
            mBuilder.insertParagraph();
        }
    }

    /// <summary>
    /// Gets a value indicating whether the specified paragraph is empty in the HTML document.
    /// </summary>
    boolean isParagraphEmptyInHtml(Paragraph paragraph)
    {
        return mEmptyInHtmlParagraphs.contains(paragraph);
    }

    /// <summary>
    /// Remembers the current paragraph of DocumentBuilder and moves its cursor to the specified node.
    /// Used to temporarily move cursor while writing footnotes, endnotes, headers, footers, and SDTs.
    /// </summary>
    void rememberCurrentParagraphAndMoveTo(Node node)
    {
        Debug.assertTrue(node != null);
        Debug.assertTrue(mBidiTextArranger.isEmpty());
        Debug.assertTrue(mBuilder.getCurrentParagraph() != null);

        mCurrentParagraphs.push(mBuilder.getCurrentParagraph());
        mBuilder.moveTo(node);
    }

    /// <summary>
    /// Restores the current paragraph of DocumentBuilder after writing footnotes, endnotes, headers, footers, and SDTs.
    /// </summary>
    void restoreCurrentParagraphIfNeeded()
    {
        Debug.assertTrue(mBidiTextArranger.isEmpty());

        if (mCurrentParagraphs.getCount() > 0)
        {
            Paragraph currentParagraph = mCurrentParagraphs.pop();
            mBuilder.moveTo(currentParagraph);
        }
    }

    void insertEmptyParagraphToListItemIfNeeded()
    {
        if (isAtStartOfListItem() && !isListItemNoneListStyle())
        {
            Debug.assertTrue(mBuilder.getParagraphFormat().isListItem());
            mBuilder.insertParagraph();
        }
    }

    void flushLastParagraphIfNeeded(ParaPr paraPr, RunPr paragraphBreakRunPr)
    {
        if (isParaStartNeeded())
        {
            // WORDSNET-22574 List items must always be closed at end tags, because the corresponding paragraphs must
            // not be reused. If we don't do this, empty list items will not be imported from HTML in sertain scenarios.
            if (!mBuilder.isAtStartOfParagraph() || mBuilder.getCurrentParagraph().isListItem())
            {
                boolean needToMoveLastParagraph =
                    (mBuilder.getCurrentParagraph().getNextSibling() != null) &&
                    (mBuilder.getCurrentParagraph().getNextSibling().getNodeType() == NodeType.BOOKMARK_END);

                mBuilder.ensureAtStructuredDocumentTagEnd();

                Paragraph lastParagraph = mBuilder.insertParagraph();
                // We should move the last paragraph at the end of all nodes if
                // the previous paragraph has the end of bookmarks after itself.
                if (needToMoveLastParagraph)
                {
                    Node lastNode = mBuilder.getCurrentParagraph().getNextSibling();
                    while (lastNode.getNextSibling() != null)
                    {
                        lastNode = lastNode.getNextSibling();
                    }
                    lastNode.insertNext(lastParagraph);
                }

            }
            mBuilder.getCurrentParagraph().setParaPr(paraPr);
            mBuilder.getCurrentParagraph().setParagraphBreakRunPr(paragraphBreakRunPr);
        }
    }

    void insertParagraphImmediately()
    {
        mBuilder.insertParagraph();
    }

    void setIsParaStartNeededIfNodeIsBlockLevel()
    {
        if (!isParaStartNeeded() && mDocumentFormatter.isDisplayedAsBlock())
        {
            isParaStartNeeded(true);
        }
    }

    void debugAssertHasNoRememberedParagraphs()
    {
        Debug.assertTrue(mCurrentParagraphs.getCount() == 0);
    }

    /// <summary>
    /// Checks if the current paragraph can be re-used instead of inserting a new paragraph.
    /// </summary>
    boolean canReuseCurrentParagraph()
    {
        // Empty paragraphs exported by Aspose.Words must remain empty and cannot be re-used.
        if (isParagraphEmptyInHtml(mBuilder.getCurrentParagraph()))
        {
            return false;
        }

        // WORDSNET-18009, WORDSNET-18968 If HTML document is inserted into empty list item, this list item is allowed
        // to be re-used by another list item, so this check is allowed only once for the first paragraph.
        // Other list items can never be re-used by list items.
        boolean canReuseListItem = mCanReuseListItem;
        mCanReuseListItem = false;

        // WORDSNET-11569 Empty list item in multilevel list should contain at least one paragraph.
        if (mBuilder.getCurrentParagraph().isListItem() &&
            (mDocumentFormatter.getElementDisplayType() == CssDisplayType.LIST_ITEM) &&
            !canReuseListItem)
        {
            return false;
        }

        // Since empty blocks collapse in HTML, empty paragraphs can be re-used.
        if (mBuilder.isAtStartOfParagraph())
        {
            return true;
        }

        // WORDSNET-17841 We incorrectly handle cases where a block is nested inside an inline-level anchor
        // element. For example, <a><img style="display:block"></a>
        // We open a hyperlink field when we process the <a> element and this makes the current paragraph non-empty.
        // As a result, when we process the <img> element, we insert a paragraph break, because the image is block-level
        // and it must be placed in a separate paragraph.
        // The code below detects the case where the current paragraph contains nothing but a hyperlink start and allows
        // to re-use the paragraph for block-level elements by marking it empty.
        //
        // Please note that this is a quick fix and it doesn't cover all similar scenarios. However, it seems impossible
        // to implement a proper fix without pre-calculation of CSS styles (multi-stage parsing of HTML).
        if (mBuilder.getCurrentParagraph().getChildNodes(NodeType.ANY, false).getCount() == 3)
        {
            // An opened hyperlink field consists of 3 nodes: field start, field code run, and field separator.
            FieldStart fieldStart = ms.as(mBuilder.getCurrentParagraph().getFirstChild(), FieldStart.class);
            FieldSeparator fieldSeparator = ms.as(mBuilder.getCurrentParagraph().getLastChild(), FieldSeparator.class);
            if ((fieldStart != null) &&
                (fieldSeparator != null) &&
                (fieldStart.getFieldType() == FieldType.FIELD_HYPERLINK) &&
                (fieldSeparator.getFieldType() == FieldType.FIELD_HYPERLINK))
            {
                return true;
            }
        }

        // Paragraphs containing nothing but floating shapes are considered empty and can be re-used.
        if (HtmlUtil.isParagraphHasOnlyFloatingShapes(mBuilder.getCurrentParagraph()))
        {
            return true;
        }

        // Paragraphs containing nothing but page or column breaks are considered empty and can be re-used.
        // WORDSNET-17475 Re-use paragraph with only page or column breaks only if this paragraph was created
        // implicitly.
        if (HtmlUtil.isParagraphHasOnlyPageOrColumnBreaks(mBuilder.getCurrentParagraph()) &&
            mIsLastParagraphCreatedImplicitly)
        {
            return true;
        }

        if (HtmlUtil.isParagraphHasOnlyEmptyRuns(mBuilder.getCurrentParagraph()))
        {
            return true;
        }

        // Otherwise, the current paragraph is not empty and thus cannot be re-used.
        return false;
    }

    /// <summary>
    /// Gets or sets a value indicating that we reached the end of a block-level element in HTML and should start
    /// a new paragraph before writing more text.
    /// </summary>
    boolean isParaStartNeeded() { return mIsParaStartNeeded; }; void isParaStartNeeded(boolean value) { mIsParaStartNeeded = value; };

    private boolean mIsParaStartNeeded;

    private boolean isAtStartOfListItem()
    {
        return (mListReaderManager != null) && (mListReaderManager.getCurrentListReader() != null) &&
               mListReaderManager.getCurrentListReader().isAtStartOfListItem();
    }

    private boolean isListItemNoneListStyle()
    {
        return (mListReaderManager != null) && (mListReaderManager.getCurrentListReader() != null) &&
               mListReaderManager.getCurrentListReader().isListItemNoneListStyle();
    }

    private /*final*/ HtmlReader mHtmlReader;
    private /*final*/ HtmlBidiTextArranger mBidiTextArranger;
    private /*final*/ DocumentBuilder mBuilder;
    private /*final*/ DocumentFormatter mDocumentFormatter;
    // WORDSNET-21193 HtmlParagraphArranger now works with mListReaderManager.CurrentListReader.
    private /*final*/ HtmlListReaderManager mListReaderManager;
    private /*final*/ HtmlCommonBorderResolver mCommonBorderResolver;
    private /*final*/ ParaPr mBaseParaPr;

    private boolean mIsLastParagraphCreatedImplicitly;
    private boolean mCanReuseListItem;

    /// <summary>
    /// Stack of <see cref="Paragraph"/> elements.
    /// Used to store DocumentBuilder's current paragraph in order to restore it
    /// after moving to another node using DocumentBuilder.MoveTo() function.
    /// Used, for example, when reading content of footnotes, endnotes, headers, footers and SDTs.
    /// </summary>
    private /*final*/ msStack<Paragraph> mCurrentParagraphs;

    /// <summary>
    /// A set of <see cref="Paragraph"/> nodes that correspond to empty paragraphs in the HTML document.
    /// </summary>
    /// <remarks>
    /// Empty paragraphs are actually exported as non-empty HTML elements. In order to prevent empty paragraphs
    /// from collapsing in browsers, AW writes a non-breaking space span into each such paragraph. On import we ignore
    /// that special run but have to process such paragraphs differently from paragraphs that contain nothing (and, as a
    /// result, collapse in the HTML document). That's why we keep track of paragraphs imported from such elements.
    /// </remarks>
    private /*final*/ HashSetGeneric<Paragraph> mEmptyInHtmlParagraphs = new HashSetGeneric<Paragraph>();

    private /*final*/ HtmlBlockReader mHtmlBlockReader;
}

