// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.Lazy;


/// <summary>
/// Extracts a collection of TOC entries (paragraphs with appropriate styles or TC fields) from a document.
/// </summary>
class TocEntryExtractor extends FieldExtractor
{
    private TocEntryExtractor(ITocEntryExtractorOptions tocEntryExtractorOptions) throws Exception
    {
        mTocEntryExtractorOptions = tocEntryExtractorOptions;

        if (mTocEntryExtractorOptions.isBookmarkRangeSpecified())
        {
            // Remember the bookmark, it is used many times.
            mRangeBookmark = mTocEntryExtractorOptions.getRangeBookmark();
        }
        else
        {
            // Consider the bookmark range started.
            mIsBookmarkRangeStarted = true;
        }
    }

    static ArrayList<ITocEntry> extractTocEntries(Document document, ITocEntryExtractorOptions fieldToc) throws Exception
    {
        TocEntryExtractor extractor = new TocEntryExtractor(fieldToc);
        extractor.extractTocEntries(document);
        return extractor.mTocEntries;
    }

    static ArrayList<ITocEntry> extractTocEntries(Document document, boolean extractFromTables) throws Exception
    {
        TocEntryExtractorOptions options = new TocEntryExtractorOptions();
        options.setSkipTables(!extractFromTables);
        TocEntryExtractor extractor = new TocEntryExtractor(options);
        document.updateListLabels();
        extractor.extractTocEntries(document);
        return extractor.mTocEntries;
    }

    static boolean isParagraphTocEntry(Paragraph paragraph, ITocEntryExtractorOptions options) throws Exception
    {
        TocEntryExtractor extractor = new TocEntryExtractor(options);
        extractor.addParagraphTocEntries(paragraph);
        return extractor.mTocEntries.size() > 0;
    }

    private void extractTocEntries(Document document) throws Exception
    {
        // TOC entries cannot reside in header/footer stories.
        for (Section section : (Iterable<Section>) document.getSections())
        {
            if (!section.getBody().accept(this))
            {
                // Break if bookmark range is finished.
                break;
            }
        }
    }

    protected /*override*/ void onFieldExtracted() throws Exception
    {
        // WORDSNET-7616, 18083: We have to check that TC/SEQ field belongs to range of TOC's bookmark
        if (mTocEntryExtractorOptions.isBookmarkRangeSpecified() && !mIsBookmarkRangeStarted)
            return;

        switch (getCurrentFieldType())
        {
            case FieldType.FIELD_TOC_ENTRY:
                processFieldTC((FieldTC)getCurrentField());
                break;
            case FieldType.FIELD_SEQUENCE:
                processFieldSeq((FieldSeq)getCurrentField());
                break;
            case FieldType.FIELD_REF_DOC:
                processFieldRD((FieldRD)getCurrentField());
                break;
            default:
                break;
        }
    }

    private void processFieldTC(FieldTC field) throws Exception
    {
        if (!mTocEntryExtractorOptions.getIncludeTocEntryFields() && !mTocEntryExtractorOptions.isEntryLevelRangeSpecified())
            return;

        // TC's level is out of the range specified by the TOC?
        if (!mTocEntryExtractorOptions.getTocEntryLevelRange().isInRange(((ITocEntry)field).getLevel()))
            return;

        // Type identifier present but does not match the one specified by the TOC?
        int entryType = FieldIndexAndTablesUtil.getTocEntryType(field.getFieldCodeCache(), FieldTC.TYPE_IDENTIFIER_SWITCH);
        if (!matchEntryTypes(mTocEntryExtractorOptions.getEntryType(), entryType))
            return;

        mTocEntries.add(field);
    }

    private static boolean matchEntryTypes(int x, int y)
    {
        if (x == y)
            return true;

        boolean xIsMissing = x == FieldIndexAndTablesUtil.MISSING_ENTRY_TYPE;
        boolean xIsNull = x == FieldIndexAndTablesUtil.NULL_ENTRY_TYPE;
        boolean yIsMissing = y == FieldIndexAndTablesUtil.MISSING_ENTRY_TYPE;
        boolean yIsNull = y == FieldIndexAndTablesUtil.NULL_ENTRY_TYPE;

        if (xIsMissing && yIsNull)
            return true;

        if (xIsNull && yIsMissing)
            return true;

        final int SPECIAL_ENTRY_TYPE = 'C';

        boolean xIsSpecial = x == SPECIAL_ENTRY_TYPE;
        boolean yIsSpecial = y == SPECIAL_ENTRY_TYPE;

        if (xIsSpecial && (yIsMissing || yIsNull))
            return true;

        if (yIsSpecial && (xIsMissing || xIsNull))
            return true;

        return false;
    }

    private void processFieldSeq(FieldSeq field) throws Exception
    {
        if (!mTocEntryExtractorOptions.isTableOfFigures())
            return;

        if (!isTableOfFiguresEntry(field))
            return;

        Paragraph paragraph = field.getStart().getParentParagraph();

        Node firstNode = msString.isNullOrEmpty(mTocEntryExtractorOptions.getCaptionlessTableOfFiguresLabel())
            ? paragraph.getFirstChild()
            : getCaptionlessFigureEntryFirstNode(field);
        Node lastNode = paragraph.getLastChild();

        ParagraphTocEntryInfo entry = firstNode != null
            ? new ParagraphTocEntryInfo(1, firstNode, lastNode)
            : ParagraphTocEntryInfo.EMPTY_TOC_ENTRY_INFO_INSTANCE;

        addTocEntryForParagraph(paragraph, entry);
    }

    /// <summary>
    /// Trims single leading punctuation mark.
    /// </summary>
    private static Node getCaptionlessFigureEntryFirstNode(Field field)
    {
        Node node = getNextSiblingEmptyRunAware(field);

        Run run = ms.as(node, Run.class);
        if (run == null)
            return node;

        if (!StringUtil.isPunctuationMark(run.getText().charAt(0)))
            return run;

        return run.splitBefore(1) != null
            ? run
            : run.getNextSibling();
    }

    private static Node getNextSiblingEmptyRunAware(Field field)
    {
        for (Node current = field.getEnd().getNextSibling(); current != null; current = current.getNextSibling())
        {
            if (NodeUtil.isCrossStructureAnnotation(current))
                continue;

            if (current.getNodeType() != NodeType.RUN)
                return current;

            if (((Run)current).getText().length() != 0)
                return current;
        }

        return null;
    }

    private void processFieldRD(FieldRD field) throws Exception
    {
        if (!mTocEntryExtractorOptions.getIncludeRefDocFields())
            return;

        Document referenceDocument = FieldIndexAndTablesUtil.openRefDocument(field);
        if (referenceDocument == null)
            return;

        ArrayList<ITocEntry> entries = extractTocEntries(
            referenceDocument,
            new RefDocTocEntryExtractorOptions(mTocEntryExtractorOptions));

        if (entries.size() == 0)
            return;

        referenceDocument.updateListLabels();
        LazyFieldSeqValueEvaluator provider = new LazyFieldSeqValueEvaluator(referenceDocument);
        for (ITocEntry entry : entries)
            mTocEntries.add(new RefDocTocEntry(entry, referenceDocument, provider));
    }

    private boolean isTableOfFiguresEntry(FieldSeq field) throws Exception
    {
        // WORDSNET-18448 Skip hidden and preceding numbers
        if (field.getHideFieldResult() || field.getInsertClosestPrecedingNumber())
            return false;

        if (StringUtil.equalsIgnoreCase(field.getSequenceIdentifier(), getTableOfFiguresLabel()))
            return true;

        if (StringUtil.equalsIgnoreCase(field.getSequenceIdentifier(), getCaptionlessTableOfFiguresLabel()))
            return true;

        return false;
    }

    private String getTableOfFiguresLabel() throws Exception
    {
        return (mTableOfFiguresLabel != null ? mTableOfFiguresLabel : (mTableOfFiguresLabel =
            adjustTableOfFiguresLabel(mTocEntryExtractorOptions.getTableOfFiguresLabel())));
    }

    private String getCaptionlessTableOfFiguresLabel() throws Exception
    {
        return (mCaptionlessTableOfFiguresLabel != null ? mCaptionlessTableOfFiguresLabel : (mCaptionlessTableOfFiguresLabel =
            adjustTableOfFiguresLabel(mTocEntryExtractorOptions.getCaptionlessTableOfFiguresLabel())));
    }

    private static String adjustTableOfFiguresLabel(String label)
    {
        return msString.isNullOrEmpty(label)
            ? ""
            : G_WHITE_SPACES_REGEX.replace(label, "_");
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        mIsBookmarkRangeStarted = mIsBookmarkRangeStarted || isBookmarkRangeStart(bookmarkStart);

        return VisitorAction.CONTINUE;
    }

    private boolean isBookmarkRangeStart(Node node)
    {
        if (mRangeBookmark == null)
            return false;

        return node == mRangeBookmark.getBookmarkStart();
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd) throws Exception
    {
        return isBookmarkRangeEnd(bookmarkEnd)
            ? VisitorAction.STOP
            : VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart) throws Exception
    {
        mFieldCharCounter.visitNode(fieldStart);

        return super.visitFieldStart(fieldStart);
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        mFieldCharCounter.visitNode(fieldSeparator);

        return super.visitFieldSeparator(fieldSeparator);
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd) throws Exception
    {
        mFieldCharCounter.visitNode(fieldEnd);

        return super.visitFieldEnd(fieldEnd);
    }

    private boolean isInsideFieldCode() { return mFieldCharCounter.isInFieldCode(); }

    private boolean isBookmarkRangeEnd(Node node) throws Exception
    {
        if (mRangeBookmark == null)
            return false;

        return node == mRangeBookmark.getBookmarkEnd();
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph) throws Exception
    {
        addParagraphTocEntries(paragraph);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Adds ParagraphTocEntry objects with appropriate level for the specified paragraph to the list.
    /// </summary>
    private void addParagraphTocEntries(Paragraph paragraph) throws Exception
    {
        if (isEmptySectionBreakParagraph(paragraph))
            return;

        // WORDSNET-4929 we should not add items, if there are inside field code
        if (isInsideFieldCode() && !paragraph.isListItemFinal())
            return;

        // WORDSNET-5091 It seems that Word requires outline level specified explicitly if a TOC entry switch is present.
        boolean isExplicitLevelRangeRequired = mTocEntryExtractorOptions.isEntryLevelRangeSpecified() ||
                                            mTocEntryExtractorOptions.getIncludeTocEntryFields();
        if (isExplicitLevelRangeRequired &&
            !mTocEntryExtractorOptions.isHeadingLevelRangeSpecified() &&
            !mTocEntryExtractorOptions.getAreCustomStylesSpecified())
        {
            return;
        }

        if (addTocEntryFromCustomStyle(paragraph))
            return;

        if (addTocEntryFromOutlineLevel(paragraph))
            return;

        if (addTocEntriesFromHeadingStyle(paragraph))
            return;

        addTocEntriesFromLinkedStyle(paragraph);
    }

    private static boolean isEmptySectionBreakParagraph(Paragraph paragraph)
    {
        return paragraph.isSectionBreakParagraph() && (paragraph.getRuns().getCount() == 0);
    }

    /// <summary>
    /// Adds a <see cref="ParagraphTocEntry"/> or <see cref="HiddenParagraphTocEntry"/> made from given paragraph and ParagraphTocEntryInfo.
    /// </summary>
    private void addTocEntryForParagraph(Paragraph paragraph, ParagraphTocEntryInfo entryInfo)
    {
        // WORDSNET-14226 MS Word does not use paragraphs inside tables in PDF bookmarks.
        if (mTocEntryExtractorOptions.getSkipTables() && (paragraph.getAncestor(NodeType.TABLE) != null))
            return;

        if (mLastParagraph == paragraph)
            return;

        mLastParagraph = paragraph;

        if (isInsideFieldCode())
            addHiddenParagraphTocEntry(paragraph, entryInfo.getLevel());
        else
            addParagraphTocEntry(paragraph, entryInfo);
    }

    private void addHiddenParagraphTocEntry(Paragraph paragraph, int level)
    {
        mTocEntries.add(new HiddenParagraphTocEntry(paragraph, level));
    }

    private void addParagraphTocEntry(Paragraph paragraph, ParagraphTocEntryInfo entryInfo)
    {
        Node firstNode = (entryInfo.getFirstChild() != null) || entryInfo.isEmptyTocEntry()
            ? entryInfo.getFirstChild()
            : paragraph.getFirstNonMarkupDescendant();
        Node lastNode = (entryInfo.getLastChild() != null) || entryInfo.isEmptyTocEntry()
            ? entryInfo.getLastChild()
            : paragraph.getLastNonMarkupDescendant();

        boolean isFirstNodeMet = false;
        boolean isTocEndMet = false;
        boolean isParagraphPartAdded = false;

        if ((firstNode != null) && (lastNode != null))
        {
            NodeRange range = new NodeRange(firstNode, lastNode);
            for (Node node : range)
            {
                if (node == firstNode)
                    isFirstNodeMet = true;

                if (node == mTocEntryExtractorOptions.getEnd())
                    isTocEndMet = true;

                if ((node == mTocEntryExtractorOptions.getStart()) && (node != firstNode) && isFirstNodeMet)
                {
                    addParagraphTocEntryCore(paragraph, entryInfo.getLevel(), firstNode, mTocEntryExtractorOptions.getStart().getPreviousSibling());
                    isParagraphPartAdded = true;
                }
                else if ((node == lastNode) && (node != mTocEntryExtractorOptions.getEnd()) && isTocEndMet)
                {
                    addParagraphTocEntryCore(paragraph, entryInfo.getLevel(), mTocEntryExtractorOptions.getEnd().getNextSibling(), lastNode);
                    isParagraphPartAdded = true;
                }
            }
        }

        if (!isParagraphPartAdded)
            addParagraphTocEntryCore(paragraph, entryInfo);
    }

    private void addParagraphTocEntryCore(Paragraph paragraph, ParagraphTocEntryInfo entryInfo)
    {
        mTocEntries.add(new ParagraphTocEntry(paragraph, entryInfo));
    }

    private void addParagraphTocEntryCore(Paragraph paragraph, int entryLevel, Node firstNode, Node lastNode)
    {
        addParagraphTocEntryCore(paragraph, new ParagraphTocEntryInfo(entryLevel, firstNode, lastNode));
    }

    /// <summary>
    /// Adds a ParagraphTocEntry for the given paragraph and outline level if outline level is valid.
    /// </summary>
    /// <returns>
    /// A boolean value indicating whether an entry was actually added.
    /// </returns>
    private boolean addParagraphTocEntryFromLevel(Paragraph paragraph, int level)
    {
        if (!LevelRange.isLevelValid(level))
            return false;

        ParagraphTocEntryInfo tocEntryInfo = getTocEntryInfoBookmarkRangeAware(paragraph, level);
        if (tocEntryInfo == null)
            return false;

        addTocEntryForParagraph(paragraph, tocEntryInfo);

        return true;
    }

    private ParagraphTocEntryInfo getTocEntryInfoBookmarkRangeAware(Paragraph paragraph, int level)
    {
        if (!LevelRange.isLevelValid(level))
            return null;

        if (mIsBookmarkRangeStarted)
            return new ParagraphTocEntryInfo(level);

        // Actually, range can start deeper in a composite node. There is a CR for that: 4930.
        if (mRangeBookmark == null)
            return null;

        if (!isBookmarkStartsInTheBeginningOfParagraph(paragraph, mRangeBookmark.getBookmarkStart()))
            return null;

        return new ParagraphTocEntryInfo(level, mRangeBookmark.getBookmarkStart(), null);
    }

    private static boolean isBookmarkStartsInTheBeginningOfParagraph(Paragraph paragraph, BookmarkStart bookmarkStart)
    {
        if (!bookmarkStart.isAncestorNode(paragraph))
            return false;

        Node node = bookmarkStart;
        while (node != paragraph)
        {
            while (node.getPreviousSibling() != null)
            {
                node = node.getPreviousSibling();
                if (node instanceof IInline)
                {
                    Run run = ms.as(node, Run.class);
                    if ((run != null) && (msString.equals(run.getText(), ControlChar.PAGE_BREAK)))
                        break;

                    return false;
                }
            }

            node = node.getParentNode();
        }

        return true;
    }

    /// <summary>
    /// Adds a ParagraphTocEntry the given paragraph if paragraph custom style is in the TOC field properties.
    /// </summary>
    /// <returns>
    /// A boolean value indicating whether an entry was actually added.
    /// </returns>
    private boolean addTocEntryFromCustomStyle(Paragraph paragraph) throws Exception
    {
        // Check if the paragraph has one of the specified custom styles.
        if (!canUseCustomStyle())
            return false;

        int level = mTocEntryExtractorOptions.getLevelForCustomStyle(
            paragraph,
            paragraph.getParagraphStyle(RevisionsView.FINAL));

        return addParagraphTocEntryFromLevel(paragraph, level);
    }

    private boolean canUseCustomStyle() throws Exception
    {
        return mTocEntryExtractorOptions.getAreCustomStylesSpecified();
    }

    /// <summary>
    /// Adds a ParagraphTocEntry for outline level of the given paragraph if paragraph's outline level should go to TOC.
    /// </summary>
    /// <returns>
    /// A boolean value indicating whether an entry was actually added.
    /// </returns>
    private boolean addTocEntryFromOutlineLevel(Paragraph paragraph) throws Exception
    {
        if (!mTocEntryExtractorOptions.getUseParagraphOutlineLevel())
            return false;

        if (mTocEntryExtractorOptions.getAreCustomStylesSpecified() && !mTocEntryExtractorOptions.isHeadingLevelRangeSpecified())
            return false;

        int level = getParagraphLevelFromOutlineLevel(paragraph);
        if (!mTocEntryExtractorOptions.getHeadingLevelRange().isInRange(level))
            return false;

        return addParagraphTocEntryFromLevel(paragraph, level);
    }

    /// <summary>
    /// Adds ParagraphTocEntries for runs having heading style in the beginning of a paragraph or page/column.
    /// </summary>
    /// <remarks>
    /// There can be multiple TOC entries for a single paragraph.
    /// </remarks>
    private boolean addTocEntriesFromHeadingStyle(Paragraph paragraph) throws Exception
    {
        if (!canUseHeadingStyle())
            return false;

        // WORDSNET-19435 Skip if the \\u switch is specified and paragraph outline level is body text.
        if (mTocEntryExtractorOptions.getUseParagraphOutlineLevel())
        {
            int outlineLevel = getParagraphLevelFromOutlineLevel(paragraph);
            if (outlineLevel == LevelRange.INVALID_LEVEL)
                return false;
        }

        // Get outline level from paragraph.
        int level = getParagraphLevelFromStyle(paragraph);
        if (!mTocEntryExtractorOptions.getHeadingLevelRange().isInRange(level))
            return false;

        return addParagraphTocEntryFromLevel(paragraph, level);
    }

    private boolean canUseHeadingStyle() throws Exception
    {
        // Not included if table of figures.
        if (mTocEntryExtractorOptions.isTableOfFigures() && !mTocEntryExtractorOptions.isHeadingLevelRangeSpecified())
            return false;

        // Bail out if nothing to do.
        if (mTocEntryExtractorOptions.getAreCustomStylesSpecified() && !mTocEntryExtractorOptions.isHeadingLevelRangeSpecified())
            return false;

        return true;
    }

    private void addTocEntriesFromLinkedStyle(Paragraph paragraph) throws Exception
    {
        // WORDSNET-17102 If first runs of a paragraph have HeadingN as a linked style,
        // then they are considered as a TOC entry.

        boolean resolveHeadingStyle = canUseHeadingStyle();
        boolean resolveCustomStyle = canUseCustomStyle();

        if (!resolveHeadingStyle && !resolveCustomStyle)
            return;

        if (paragraph.getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG) != null)
            return;

        boolean isLocked = false;
        boolean isBookmarkRangeStarted = mIsBookmarkRangeStarted;

        NodeRange range = new NodeRange(paragraph, paragraph);
        for (Node currentNode : range)
        {
            switch (currentNode.getNodeType())
            {
                case NodeType.FIELD_START:
                    if (((FieldChar)currentNode).getFieldType() == FieldType.FIELD_TOC)
                        isLocked = true;
                    break;
                case NodeType.FIELD_END:
                    if (((FieldChar)currentNode).getFieldType() == FieldType.FIELD_TOC)
                        isLocked = false;
                    break;
                default:
                    break;
            }

            if (isLocked)
                continue;

            switch (currentNode.getNodeType())
            {
                case NodeType.RUN:
                    if (isBookmarkRangeStarted && isTocEntryStartCandidate((Run)currentNode))
                    {
                        addTocEntryFromLinkedStyle(
                                (Run)currentNode,
                                paragraph,
                                resolveHeadingStyle,
                                resolveCustomStyle);

                        return;
                    }

                    break;
                case NodeType.BOOKMARK_START:
                    if (!isBookmarkRangeStarted)
                    {
                        isBookmarkRangeStarted = isBookmarkRangeStart(currentNode) &&
                                                 isBookmarkStartsInTheBeginningOfParagraph(
                                                     paragraph,
                                                     (BookmarkStart)currentNode);
                    }
                    break;
                case NodeType.BOOKMARK_END:
                    // Stop looking for the new entries after range end.
                    if (isBookmarkRangeEnd(currentNode))
                        return;

                    break;
                default:
                    // Do nothing for the other node types.
                    break;
            }
        }
    }

    /// <summary>
    /// Adds a TOC entry created from run(s) based on a linked style.
    /// </summary>
    private void addTocEntryFromLinkedStyle(
        Run run,
        Paragraph paragraph,
        boolean resolveHeadingStyle,
        boolean resolveCustomStyle) throws Exception
    {
        int level = getParagraphLevelFromLinkedStyle(run, paragraph, resolveHeadingStyle, resolveCustomStyle);
        if (!LevelRange.isLevelValid(level))
            return;

        Node lastRunOfTocEntry = getLastRunOfTocEntry(
            run,
            level,
            paragraph,
            resolveHeadingStyle,
            resolveCustomStyle);

        ParagraphTocEntryInfo tocEntryInfo = new ParagraphTocEntryInfo(level, run, lastRunOfTocEntry, true);
        addTocEntryForParagraph(run.getParentParagraph(), tocEntryInfo);
    }

    /// <summary>
    /// Looks for TOC entry start candidate in the given run.
    /// </summary>
    private static boolean isTocEntryStartCandidate(Run run)
    {
        for (int i = 0; i < run.getText().length(); i++)
        {
        	char c = run.getText().charAt(i);
            // WORDSNET-26724 Skip leading spaces and breaks, with any style.
            if (!isTextChar(c))
                continue;

            // WORDSNET-20677 Skip hidden runs.
            if (run.isHiddenOrDeleted())
                continue;

            return true;
        }

        return false;
    }

    private static boolean isTextChar(char c)
    {
        switch (c)
        {
            case ControlChar.PAGE_BREAK_CHAR:
            case ControlChar.COLUMN_BREAK_CHAR:
                return false;
            default:
                return !msChar.isWhiteSpace(c);
        }
    }

    /// <summary>
    /// Finds the last sibling of the given run having the given level.
    /// </summary>
    /// <remarks>
    /// The run itself is returned if there is no sibling with the given level.
    /// </remarks>
    private Node getLastRunOfTocEntry(
        Run firstRunOfTocEntry,
        int level,
        Paragraph paragraph,
        boolean resolveHeadingStyle,
        boolean resolveCustomStyle) throws Exception
    {
        if (firstRunOfTocEntry == null)
            return null;

        Run lastRunOfTocEntry = firstRunOfTocEntry;

        // Look for the last consecutive run with the given style.
        NodeRange range = new NodeRange(firstRunOfTocEntry, false, paragraph, true);
        for (Node possibleTocEntryNode : range)
        {
            /*NodeType*/int nodeType = possibleTocEntryNode.getNodeType();
            if (nodeType == NodeType.RUN)
            {
                Run possibleTocEntryRun = (Run)possibleTocEntryNode;

                int linkedStyleLevel = getParagraphLevelFromLinkedStyle(
                    possibleTocEntryRun,
                    paragraph,
                    resolveHeadingStyle,
                    resolveCustomStyle);

                if (level == linkedStyleLevel)
                {
                    lastRunOfTocEntry = possibleTocEntryRun;
                }
                else
                {
                    // Bail out, TOC entry end is found.
                    break;
                }
            }
            else if (nodeType == NodeType.COMMENT_RANGE_END)
            {
                // Comment range end breaks the TOC entry.
                break;
            }
            else
            {
                // For other node types, just skip them.
            }
        }

        return lastRunOfTocEntry;
    }

    private static int getParagraphLevelFromStyle(Paragraph paragraph)
    {
        Style style = paragraph.getParagraphStyle(RevisionsView.FINAL);

        return getParagraphLevelFromStyle(style);
    }

    private static int getParagraphLevelFromStyle(Style style)
    {
        if (style.getParagraphFormat() == null)
            return LevelRange.INVALID_LEVEL;

        return getParagraphLevelFromOutlineLevel(style.getParagraphFormat().getOutlineLevel());
    }

    static int getParagraphLevelFromOutlineLevel(Paragraph paragraph)
    {
        // We don't use ParagraphFormat facade because there is no "Final" analog for the ParagraphFormat.OutlineLevel property.
        /*OutlineLevel*/int outlineLevel = (/*OutlineLevel*/Integer)paragraph.fetchParaAttr(ParaAttr.OUTLINE_LEVEL, RevisionsView.FINAL);
        return getParagraphLevelFromOutlineLevel(outlineLevel);
    }

    private static int getParagraphLevelFromOutlineLevel(/*OutlineLevel*/int outlineLevel)
    {
        switch (outlineLevel)
        {
            case OutlineLevel.LEVEL_1:
                return 1;
            case OutlineLevel.LEVEL_2:
                return 2;
            case OutlineLevel.LEVEL_3:
                return 3;
            case OutlineLevel.LEVEL_4:
                return 4;
            case OutlineLevel.LEVEL_5:
                return 5;
            case OutlineLevel.LEVEL_6:
                return 6;
            case OutlineLevel.LEVEL_7:
                return 7;
            case OutlineLevel.LEVEL_8:
                return 8;
            case OutlineLevel.LEVEL_9:
                return 9;
            default:
                return LevelRange.INVALID_LEVEL;
        }
    }

    private int getParagraphLevelFromLinkedStyle(
        IInline inline,
        Paragraph paragraph,
        boolean resolveHeadingStyle,
        boolean resolveCustomStyle) throws Exception
    {
        RunPr runPr = inline.getRunPr_IInline();
        StyleCollection styles = inline.getDocument_IInline().getStyles();
        Style style = styles.fetchByIstd(runPr.getIstd(), StyleIndex.DEFAULT_PARAGRAPH_FONT);
        Style linkedStyle = styles.getByIstd(style.getLinkedIstd(), false);

        if (linkedStyle == null)
            return LevelRange.INVALID_LEVEL;

        if (resolveHeadingStyle)
        {
            int level = getParagraphLevelFromStyle(linkedStyle);
            if (mTocEntryExtractorOptions.getHeadingLevelRange().isInRange(level))
                return level;
        }

        if (resolveCustomStyle)
            return mTocEntryExtractorOptions.getLevelForCustomStyle(paragraph, linkedStyle);

        return LevelRange.INVALID_LEVEL;
    }

    private /*final*/ ITocEntryExtractorOptions mTocEntryExtractorOptions;
    private /*final*/ Bookmark mRangeBookmark;
    private /*final*/ ArrayList<ITocEntry> mTocEntries = new ArrayList<ITocEntry>();
    private /*final*/ FieldCharCounter mFieldCharCounter = new FieldCharCounter(FieldCharCounterOptions.SAFE_MODE);
    private boolean mIsBookmarkRangeStarted;
    private Paragraph mLastParagraph;
    private String mTableOfFiguresLabel;
    private String mCaptionlessTableOfFiguresLabel;

    // The \p{Zs} group must be used to match all Unicode space characters in Java.
    private static final String REGEX_PATTERN = "[\\s\\p{Z}]";

    private static /*final*/ Regex G_WHITE_SPACES_REGEX = new Regex(REGEX_PATTERN, RegexOptions.COMPILED);

    private static class LazyFieldSeqValueEvaluator extends Lazy<FieldSeqValueEvaluator>
    {
        LazyFieldSeqValueEvaluator(Document document)
        {
            mDocument = document;
        }

        protected /*override*/ FieldSeqValueEvaluator initValue()
        {
            return new FieldSeqValueEvaluator(mDocument, new BookmarkCache(mDocument));
        }

        private /*final*/ Document mDocument;
    }
}

