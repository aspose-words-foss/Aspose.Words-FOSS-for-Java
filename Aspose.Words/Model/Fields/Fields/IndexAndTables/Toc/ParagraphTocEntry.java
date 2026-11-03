// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.NullableInt32;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msChar;


/// <summary>
/// A TOC entry that is represented by a paragraph with a Heading (or other) style or an outline level.
/// </summary>
class ParagraphTocEntry implements ITocEntry
{
    /// <summary>
    /// Creates a new instance of the class.
    /// </summary>
    ParagraphTocEntry(Paragraph paragraph, ParagraphTocEntryInfo entryInfo)
    {
        mParagraph = paragraph;
        mLevel = entryInfo.getLevel();
        mIsLinkedStyleTocEntry = entryInfo.isLinkedStyleTocEntry();

        mFirstEntryNode = entryInfo.getFirstChild();
        mLastEntryNode = entryInfo.getLastChild();

        if (!entryInfo.isEmptyTocEntry())
        {
            if (mFirstEntryNode == null)
                mFirstEntryNode = paragraph.getFirstNonMarkupDescendant();

            if (mLastEntryNode == null)
                mLastEntryNode = paragraph.getLastNonMarkupDescendant();
        }
    }

    public NodeRange /*ITocEntry.*/insertBookmark(String bookmarkName)
    {
        NodeRange range = getEntryRange();
        if (range == null)
            return null;

        BookmarkStart bookmarkStart = new BookmarkStart(getDocument(), bookmarkName);
        BookmarkEnd bookmarkEnd = new BookmarkEnd(getDocument(), bookmarkName);

        if (range.getStart().getNode().getNodeType() == NodeType.PARAGRAPH)
        {
            ((Paragraph)range.getStart().getNode()).appendChild(bookmarkStart);
            ((Paragraph)range.getStart().getNode()).appendChild(bookmarkEnd);
        }
        else
        {
            // WORDSNET-13419 Insert bookmark nodes to paragraph, like MS Word does.
            insertStartToAncestorParagraph(range.getStart().getNode(), bookmarkStart);
            insertEndToAncestorParagraph(range.getEnd().getNode(), bookmarkEnd);
        }

        return Bookmark.getNodeRange(bookmarkStart, bookmarkEnd);
    }

    private static void insertStartToAncestorParagraph(Node refNode, BookmarkStart start)
    {
        if (refNode.getParentNode().getNodeType() == NodeType.PARAGRAPH)
        {
            refNode.insertPrevious(start);
            return;
        }

        insertStartToAncestorParagraph(refNode.getParentNode(), start);
    }

    private static void insertEndToAncestorParagraph(Node refNode, BookmarkEnd end)
    {
        if (refNode.getParentNode().getNodeType() == NodeType.PARAGRAPH)
        {
            refNode.insertNext(end);
            return;
        }

        insertEndToAncestorParagraph(refNode.getParentNode(), end);
    }

    public int /*ITocEntry.*/getLevel() { return mLevel; }

    public boolean /*ITocEntry.*/getOmitPageNumber() { return false; }

    public Paragraph /*ITocEntry.*/getParagraph() { return mParagraph; }

    public NodeRange /*ITocEntry.*/getLabelRange()
    {
        // WORDSNET-10937 Skip if list label is empty.
        if (!hasLabelRange())
            return null;

        Run labelRun = new Run(getDocument(), msString.trim(mParagraph.getListLabel().getLabelStringFinal()), cloneRunPr());
        Paragraph labelPara = new Paragraph(getDocument());
        labelPara.getParagraphFormat().setStyle(mParagraph.getParagraphStyle(RevisionsView.FINAL));
        labelPara.appendChild(labelRun);

        String trailingCharacter;
        switch (mParagraph.getListFormat().getListLevelFinal().getTrailingCharacter())
        {
            case ListTrailingCharacter.TAB:
                trailingCharacter = ControlChar.TAB;
                break;
            case ListTrailingCharacter.SPACE:
            case ListTrailingCharacter.NOTHING:
                trailingCharacter = ControlChar.SPACE;
                break;
            default:
                throw new IllegalArgumentException();
        }

        Run trailingRun = new Run(getDocument(), trailingCharacter, cloneRunPr());
        labelPara.appendChild(trailingRun);

        return new NodeRange(labelRun, trailingRun);
    }

    private boolean hasLabelRange() { return mParagraph.hasListLabelFinal(); }

    private RunPr cloneRunPr()
    {
        RunPr runPr = AttrCollectionExtension.deepClone(mParagraph.getListLabel()
                .getExpandedRunPr(true, RunPrExpandFlags.NORMAL | RunPrExpandFlags.REVISED));

        // We should not use revision identifiers from source paragraph.
        for (int key : G_IGNORE_ATTRIBUTE_KEYS)
            runPr.remove(key);

        if (mIsLinkedStyleTocEntry)
        {
            NullableInt32 characterStyleIstd = getCharacterStyleIstd();
            Debug.assertTrue(characterStyleIstd.hasValue());
            if (characterStyleIstd.hasValue())
                runPr.setIstd(characterStyleIstd.getValue());
        }

        return runPr;
    }

    private NullableInt32 getCharacterStyleIstd()
    {
        Inline inline = ms.as(mFirstEntryNode, Inline.class);
        if (inline == null)
            return NullableInt32.NULL;

        return inline.getRunPr().containsKey(FontAttr.ISTD)
            ? new NullableInt32((/*int*/Integer)inline.getRunPr().get(FontAttr.ISTD))
            : NullableInt32.NULL;
    }

    private NodeRange getEntryRange()
    {
        ensureEntryRange();
        return mEntryRange;
    }

    public String /*ITocEntry.*/getDocumentOutlineTitle()
    {
        /*OutlineLevel*/int outlineLevel = (/*OutlineLevel*/Integer)mParagraph.fetchParaAttr(ParaAttr.OUTLINE_LEVEL, RevisionsView.FINAL);
        if (outlineLevel == OutlineLevel.BODY_TEXT)
            return null;

        StringBuilder builder = new StringBuilder();

        NodeRange range = (getEntryRange() != null ? getEntryRange() : NodeRange.VOID);

        NodeTextCollectorOptions options = new NodeTextCollectorOptions();
        options.setAllowHiddenText(false);
        options.setAllowDeletedText(false);
        options.isFieldResultMode(true);

        String rangeText = msString.replace(msString.trim(NodeTextCollector.getText(range, options)), '\u000b', ' ');

        if (hasLabelRange())
            msStringBuilder.append(builder, mParagraph.getListLabel().getLabelStringFinal());

        if (hasLabelRange() && !msString.isNullOrEmpty(rangeText))
        {
            switch (mParagraph.getListFormat().getListLevelFinal().getTrailingCharacter())
            {
                case ListTrailingCharacter.TAB:
                    msStringBuilder.append(builder, ControlChar.TAB);
                    break;
                case ListTrailingCharacter.SPACE:
                case ListTrailingCharacter.NOTHING:
                    builder.append(' ');
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        msStringBuilder.append(builder, rangeText);

        return builder.toString();
    }

    public boolean /*ITocEntry.*/isInFieldCode() { return false; }

    public boolean /*ITocEntry.*/hasBookmark() { return true; }

    public boolean /*ITocEntry.*/isLinkedStyleTocEntry() { return mIsLinkedStyleTocEntry; }

    public int /*ITocEntry.*/getSequenceValue(String sequenceIdentifier)
    {
        throw new IllegalStateException();
    }

    public int /*ITocEntry.*/getPageNumber()
    {
        throw new IllegalStateException();
    }

    private void ensureEntryRange()
    {
        if (mIsEntryRangeInitialized)
            return;

        mIsEntryRangeInitialized = true;

        // Remove leading spaces and page breaks.
        Node startNode = trimEntryLeft();

        // Remove trailing spaces and page breaks.
        Node endNode = trimEntryRight();

        if (startNode == null || endNode == null)
        {
            if (!hasLabelRange())
                return;

            if (mParagraph.getParagraphBreakFont().getHidden())
                return;

            if ((mParagraph.getNextNonAnnotationSibling() == null) && mParagraph.getParentSection().isLastChild() && !mParagraph.hasInlineNodes())
                return;

            mEntryRange = new NodeRange(mParagraph, mParagraph);
            return;
        }

        if (!hasLabelRange() && isEmptyEntryRange(startNode, endNode))
            return;

        mEntryRange = new NodeRange(startNode, endNode);
    }

    /// <summary>
    /// Trims leading page/column breaks white space from the TOC entry.
    /// </summary>
    /// <remarks>
    /// Skips any nodes other than runs.
    /// If bookmark a page break, then page numbering will be incorrect in TOC.
    /// </remarks>
    /// <returns>
    /// The first valid run to bookmark, or null.
    /// </returns>
    private Node trimEntryLeft()
    {
        return trimEntry(STEP_FORWARD, mFirstEntryNode, mLastEntryNode, false);
    }

    /// <summary>
    /// Trims trailing page/column breaks and white space from the TOC entry.
    /// </summary>
    /// <remarks>
    /// Skips any nodes other than runs.
    /// </remarks>
    /// <returns>
    /// The last valid run to bookmark, or null.
    /// </returns>
    private Node trimEntryRight()
    {
        // The order of the nodes passed to the method is correct: last, then first.
        return trimEntry(STEP_BACKWARD, mLastEntryNode, mFirstEntryNode, false);
    }

    /// <summary>
    /// Goes from firstEntryNode to lastEntryNode and skips leading/trailing spaces and page/column breaks.
    /// </summary>
    /// <returns>
    /// The first run, starting (or ending) with a valid character.
    /// The run is split if needed.
    /// Null is returned if a valid run is not found.
    /// </returns>
    private static Node trimEntry(int step, Node firstEntryNode, Node lastEntryNode, boolean skipSpecialChars)
    {
        boolean isLastEntryNodeProcessed = false;
        Node currentNode = firstEntryNode;
        int fieldCharCount = 0;

        while ((currentNode != null) && !isLastEntryNodeProcessed)
        {
            // WORDSNET-6150 Field start and end are valid nodes for TOC entry's start or end.
            switch (currentNode.getNodeType())
            {
                case NodeType.FIELD_START:
                {
                    if ((step == STEP_FORWARD) && needStopTrimEntry((FieldStart)currentNode, fieldCharCount))
                        return currentNode;

                    fieldCharCount++;
                    break;
                }
                case NodeType.FIELD_SEPARATOR:
                {
                    fieldCharCount--;
                    break;
                }
                case NodeType.FIELD_END:
                {
                    FieldEnd fieldEnd = (FieldEnd)currentNode;
                    if (!fieldEnd.hasSeparator())
                    {
                        if ((step == STEP_BACKWARD) && needStopTrimEntry(fieldEnd, fieldCharCount))
                            return currentNode;

                        fieldCharCount--;
                    }

                    break;
                }
                case NodeType.RUN:
                {
                    // Skip field code.
                    // Use not-equal check instead of more-than check as we can move backward.
                    if (fieldCharCount != 0)
                        break;

                    Run run = (Run)currentNode;

                    // WORDSNET-20677 Deleted and hidden runs are skipped.
                    if (!run.isHiddenOrDeleted())
                    {
                        int validCharPosition = findValidChar(run.getText(), step);
                        if (validCharPosition != VALID_CHAR_NOT_FOUND)
                        {
                            // Split the run to insert a bookmark, if needed.
                            splitRun(run, step, validCharPosition);

                            // Split or not, a valid run is found.
                            return run;
                        }
                    }

                    break;
                }
                case NodeType.SPECIAL_CHAR:
                {
                    if (skipSpecialChars)
                        break;

                    if (fieldCharCount != 0)
                        break;

                    SpecialChar specialChar = (SpecialChar)currentNode;

                    if (!specialChar.isHiddenOrDeleted() && isValidChar(specialChar.getChar()))
                        return specialChar;

                    break;
                }
                // WORDSNET-7297 TOC entry can include Shape, GroupShape or DrawingML nodes at the beginning or end.
                case NodeType.SHAPE:
                {
                    if (((Shape)currentNode).isInline())
                        return currentNode;
                    break;
                }
                case NodeType.GROUP_SHAPE:
                {
                    if (((GroupShape)currentNode).isInline())
                        return currentNode;
                    break;
                }
                case NodeType.OFFICE_MATH:
                    return currentNode;
                default:
                    break;
            }

            // Check if we are looking at the last TOC entry node.
            isLastEntryNodeProcessed = (currentNode == lastEntryNode);

            currentNode = (step == STEP_FORWARD)
                ? currentNode.getNextNonMarkupNodeLimited()
                : currentNode.getPreviousNonMarkupNodeLimited();
        }

        // If everything is skipped, no TOC entry is created.
        return null;
    }

    private static boolean needStopTrimEntry(FieldChar fieldChar, int fieldCharCount)
    {
        return (fieldCharCount == 0) &&
            FieldNumUtil.isFieldNum(fieldChar.getFieldType()) &&
            !fieldChar.isHiddenOrDeleted();
    }

    /// <summary>
    /// Splits the run to make it start or end on a valid character.
    /// </summary>
    /// <param name="run"></param>
    /// <param name="step"></param>
    /// <param name="validCharPosition"></param>
    private static void splitRun(Run run, int step, int validCharPosition)
    {
        // Split the run to insert a bookmark...
        if (step == STEP_FORWARD)
        {
            // ...after leading spaces.
            run.splitBefore(validCharPosition);
        }
        else
        {
            // .. or before trailing spaces.
            run.splitAfter(validCharPosition + 1);
        }
    }

    /// <summary>
    /// Looks for the first character that can start (or finish) a TOC entry.
    /// </summary>
    /// <remarks>Spaces and page breaks are skipped.</remarks>
    /// <param name="text"></param>
    /// <param name="step">1 to look forward from the beginning, -1 to look backward from the end. Use StepForward and StepBackward.</param>
    /// <returns>The position of the first valid char or ValidCharNotFound.</returns>
    private static int findValidChar(String text, int step)
    {
        int currentPosition;
        int endPosition;

        switch (step)
        {
            case STEP_FORWARD:
                currentPosition = 0;
                endPosition = text.length();
                break;
            case STEP_BACKWARD:
                // start from the end
                currentPosition = text.length() - 1;
                endPosition = -1;
                break;
            default:
                throw new IllegalArgumentException("step");
        }

        boolean isValidCharFound = false;
        while (currentPosition != endPosition)
        {
            isValidCharFound = isValidChar(text.charAt(currentPosition));

            if (isValidCharFound)
            {
                // The found character position is in currentPosition.
                break;
            }
            currentPosition += step;
        }

        return isValidCharFound ? currentPosition : VALID_CHAR_NOT_FOUND;
    }

    /// <summary>
    /// Checks if the given character should be present in the end or beginning of the TOC entry.
    /// </summary>
    /// <param name="c"></param>
    /// <returns></returns>
    private static boolean isValidChar(char c)
    {
        switch (c)
        {
            case ControlChar.PAGE_BREAK_CHAR:
            case ControlChar.COLUMN_BREAK_CHAR:
                return false;
            case ControlChar.DEFAULT_TEXT_INPUT_CHAR:
                return true;
            default:
                return !msChar.isWhiteSpace(c);
        }
    }

    private static boolean isEmptyEntryRange(Node start, Node end)
    {
        if (trimEntry(STEP_FORWARD, start, end, true) == null)
            return true;

        if (trimEntry(STEP_BACKWARD, end, start, true) == null)
            return true;

        return false;
    }

    private Document getDocument() { return mParagraph.fetchDocument(); }

    private NodeRange mEntryRange;
    private boolean mIsEntryRangeInitialized;

    private /*final*/ Paragraph mParagraph;
    private /*final*/ int mLevel;
    private /*final*/ Node mLastEntryNode;
    private /*final*/ Node mFirstEntryNode;
    private /*final*/ boolean mIsLinkedStyleTocEntry;

    private static final int VALID_CHAR_NOT_FOUND = -1;
    private static final int STEP_FORWARD = 1;
    private static final int STEP_BACKWARD = -1;

    private static /*final*/ int[] G_IGNORE_ATTRIBUTE_KEYS = { FontAttr.RSID_R_PR, FontAttr.RSID_R };
}

