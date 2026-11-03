// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ISetGeneric;
import com.aspose.CaseInsensitiveHashSet;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msArray;
import com.aspose.StringUtil;
import com.aspose.ArrayUtil;
import com.aspose.StringToIntDictionary;
import com.aspose.FormatterPal;
import com.aspose.ms.System.StringSplitOptions;


/// <summary>
/// Implements the TOC field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Builds a table of contents (which can also be a table of figures) using the entries specified by TC fields,
/// their heading levels, and specified styles, and inserts that table at this place in the document.
/// </remarks>
public class FieldToc extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Updates the page numbers for items in this table of contents.
    /// </summary>
    /// <returns>
    /// <c>true</c> if the operation is successful. If any of the related TOC bookmarks was removed, <c>false</c> will be returned.
    /// </returns>
    public boolean updatePageNumbers() throws Exception
    {
        NodeRange resultRange = getFieldResultRange();

        ArrayList<Field> pageRefFields = FieldExtractor.extractToCollection(resultRange, true, FieldType.FIELD_PAGE_REF);

        if (pageRefFields.size() == 0)
            return false;

        for (Field field : pageRefFields)
        {
            if (((FieldPageRef)field).getBookmarkNode() == null)
                return false;
        }

        FieldUpdater.updateFields(pageRefFields);

        return true;
    }

    /*override*/ /*FieldUpdateStage*/int getUpdateStage()
    {
        return FieldUpdateStage.DEFERRED_UPDATE_REF;
    }

    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        removeOldResultBookmarks();

        // The TOC field result is way too complex, so build it right away.
        getUpdateContext().removeOldResultSafe();

        FieldUpdateAction result = updateParsed();
        if (result != null)
            return result;

        getUpdateContext().joinOldResultNodes();

        // WORDSNET-7485 If temporal paragraph was inserted it need to remove it.
        if (mIsExtraParaWasAdded)
            removeExtraParagraph();

        // Nothing else to do with the field.
        return new FieldUpdateActionDoNothing(this);
    }

    /// <summary>
    /// For tests purpose only.
    /// </summary>
    ITocEntryExtractorOptions parseOptions()
    {
        return ParsedFieldToc.tryParse(this);
    }

    private FieldUpdateAction updateParsed() throws Exception
    {
        ParsedFieldToc options = ParsedFieldToc.tryParse(this);
        if (options == null)
            return new FieldUpdateActionInsertErrorMessage(this, LevelRange.PARSING_ERROR_MESSAGE);

        DocumentBuilder builder = FieldIndexAndTablesUtil.getDocumentBuilder(this);
        boolean isExtraParaAlreadyAdded = !FieldIndexAndTablesUtil.isFirstNonZeroLengthChild(this);

        // WORDSNET-7485 Parent paragraph of the End node might be simultaneously a TOC entry.
        // In this case ParagraphTocEntry instance which will be built with such paragraph
        // will contain invalid mParagraph reference because of inserting of new paragraphs.
        // We insert temporal paragraph to separate End node and TOC entry.
        if (!isExtraParaAlreadyAdded &&
            getStart().getParentParagraph() != getEnd().getParentParagraph() &&
            TocEntryExtractor.isParagraphTocEntry(getEnd().getParentParagraph(), options))
        {
            insertExtraParagraph(builder);
        }

        ArrayList<ITocEntry> entries = TocEntryExtractor.extractTocEntries(fetchDocument(), options);

        if (entries.size() == 0)
            return new FieldUpdateActionInsertErrorMessage(this, getNoEntriesErrorMessage(options));

        // List labels should be actual before field value is constructed.
        getUpdater().requestExternalAction(new ExternalActionUpdateListLabels(fetchDocument()));

        // FOSS

        if (!buildTocEntries(builder, entries, options))
            return new FieldUpdateActionInsertErrorMessage(this, getNoEntriesErrorMessage(options));

        return null;
    }

    /*override*/ void beforeUnlink()
    {
        NodeRange range = getFieldResultRange();
        for (Node node : range)
        {
            Inline inline = ms.as(node, Inline.class);
            if ((inline != null) && (inline.getFont().getStyleIdentifier() == StyleIdentifier.HYPERLINK))
                inline.getRunPr().remove(FontAttr.ISTD);
        }
    }

    private void removeOldResultBookmarks() throws Exception
    {
        ArrayList<Field> fields = FieldExtractor.extractToCollection(
            getFieldResultRange(),
            true,
            FieldType.FIELD_PAGE_REF,
            FieldType.FIELD_HYPERLINK,
            FieldType.FIELD_SEQUENCE);
        if (fields.size() == 0)
            return;

        ISetGeneric<String> removedBookmarks = new CaseInsensitiveHashSet(fields.size());
        for (Field field : fields)
        {
            String bookmarkName = getFieldBookmarkName(field);
            if (msString.isNullOrEmpty(bookmarkName))
                continue;

            if(!removedBookmarks.add(bookmarkName))
                continue;

            Bookmark bookmark = getUpdater().getCachedBookmark(bookmarkName);
            if (bookmark == null)
                continue;

            bookmark.remove();
        }
    }

    private static String getFieldBookmarkName(Field field)
    {
        switch (field.getType())
        {
            case FieldType.FIELD_PAGE_REF:
                return ((FieldPageRef)field).getBookmarkName();
            case FieldType.FIELD_HYPERLINK:
                return ((FieldHyperlink)field).getSubAddress();
            case FieldType.FIELD_SEQUENCE:
                return ((FieldSeq)field).getBookmarkName();
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean buildTocEntries(
        DocumentBuilder builder,
        ArrayList<ITocEntry> entries,
        ParsedFieldToc options) throws Exception
    {
        // SPEED 17460 Do not use BookmarkCollection in a loop! Collect bookmark names first.
        String[] bookmarkNames = getDocumentBookmarkNames();
        msArray.sort(bookmarkNames);

        boolean result = false;
        for (ITocEntry entry : entries)
            result = buildTocEntry(builder, bookmarkNames, entry, options) || result;

        return result;
    }

    private String[] getDocumentBookmarkNames()
    {
        NodeCollection bookmarkNodes = getDocument().getChildNodes(new /*NodeType*/int[] {NodeType.BOOKMARK_START, NodeType.BOOKMARK_END}, true);
        String[] bookmarkNames = new String[bookmarkNodes.getCount()];

        for (int i = 0; i < bookmarkNodes.getCount(); i++)
        {
            IBookmarkNode bookmarkNode = (IBookmarkNode)bookmarkNodes.get(i);
            bookmarkNames[i] = bookmarkNode.getName();
        }

        return bookmarkNames;
    }

    private boolean buildTocEntry(DocumentBuilder builder, String[] bookmarkNames, ITocEntry entry, ParsedFieldToc options) throws Exception
    {
        // Mark entry contents with a hidden bookmark to make PAGEREF fields that are a part of the TOC's result
        // reference them properly.
        String bookmarkName = getUniqueTocBookmarkName(bookmarkNames);

        NodeRange bookmarkRange = entry.insertBookmark(bookmarkName);

        // Failed to insert bookmark (for example, no valid child nodes in the paragraph)?
        if (bookmarkRange == null)
            return false;

        FieldIndexAndTablesUtil.convertOuterInlineSdtToBlock(this);

        /*StyleIdentifier*/int styleIdentifier = getStyleIdentifierForLevel(entry.getLevel(), options.isTableOfFigures());

        // WORDSNET-18533 Remember and restore field end paragraph break font
        RunPr breakRunPr = AttrCollectionExtension.deepClone(getEnd().getParentParagraph().getParagraphBreakRunPr());

        // Set the current paragraph's properties.
        FieldIndexAndTablesUtil.setUpEntryParagraph(this, builder, styleIdentifier);
        FieldIndexAndTablesUtil.setFontForTabStopOrParagraphBreak(fetchDocument(), builder.getCurrentParagraph());

        getEnd().getParentParagraph().setParagraphBreakRunPr(breakRunPr);

        builder.clearFont();

        Node dummyRefNode = FieldIndexAndTablesUtil.createAndInsertDummyRefNode(builder);
        Node refNode = insertHyperlink(entry, dummyRefNode, builder, bookmarkName);

        buildTocEntry(entry, bookmarkRange, refNode, options);

        builder.moveTo(refNode);
        builder.clearFont();

        buildEntryPageNumber(builder, entry, bookmarkName, options);

        dummyRefNode.remove();

        return true;
    }

    // WORDSNET-7485 Insert extra paragraph which will be temporal parent for End node.
    private void insertExtraParagraph(DocumentBuilder builder)
    {
        builder.moveTo((getEnd().getNextSibling() != null ? getEnd().getNextSibling() : getEnd().getParentNode()));

        // WORDSNET-17217 Workaround. The DocumentBuilder.InsertParagraph loses ParagraphBreakRunPr.
        RunPr currentParaBreakPr = AttrCollectionExtension.deepClone(builder.getCurrentParagraph().getParagraphBreakRunPr());

        builder.ensureAtStructuredDocumentTagEnd();

        builder.writeln();
        builder.getCurrentParagraph().setParagraphBreakRunPr(currentParaBreakPr);

        Paragraph para = builder.getCurrentParagraph();
        builder.moveTo(builder.getCurrentParagraph().getPreviousNonAnnotationSibling());
        builder.getParagraphFormat().clearFormatting();
        builder.getCurrentParagraph().getParagraphBreakFont().clearFormatting();
        para.cloneListLabelStringAndValueIfNeeded(builder.getCurrentParagraph());

        mIsExtraParaWasAdded = true;
    }

    // WORDSNET-7485 Remove temporal parent paragraph of End node.
    private void removeExtraParagraph() throws Exception
    {
        Paragraph endParagraph = getEnd().getParentParagraph();
        if (endParagraph.getNextSibling() == null)
            return;

        Paragraph toRemove = (Paragraph)endParagraph.getNextNonAnnotationSibling();

        if (toRemove.hasChildNodes())
        {
            NodeRemover.remove(toRemove, true, toRemove.getFirstChild(), false, NodeJoinMode.JOIN_TO_NEXT_SIBLING);
        }
        else
        {
            endParagraph.setParaPr(toRemove.getParaPr());
            endParagraph.setParagraphBreakRunPr(toRemove.getParagraphBreakRunPr());
            toRemove.remove();
        }
    }

    private Node insertHyperlink(ITocEntry entry, Node refNode, DocumentBuilder builder, String sourceEntryBookmarkName)
    {
        if (getInsertHyperlinks() && entry.hasBookmark())
        {
            builder.moveTo(refNode);
            Field field = builder.insertField(msString.format(" HYPERLINK \\l \"{0}\" ", sourceEntryBookmarkName), "");
            return field.getEnd();
        }

        return refNode;
    }

    /// <summary>
    /// Copies TOC entry nodes to the TOC.
    /// </summary>
    private void buildTocEntry(ITocEntry entry, NodeRange bookmarkRange, Node refNode, ITocEntryExtractorOptions options) throws Exception
    {
        CompositeModifier compositeModifier = new CompositeModifier();

        FieldTC fieldTc = null;
        RefDocTocEntry refDocTocEntry = ms.as(entry, RefDocTocEntry.class);
        if (refDocTocEntry != null)
        {
            compositeModifier.addModifier(new ExternalDocumentModifier(refDocTocEntry.getDocument(), getDocument(), ImportFormatMode.USE_DESTINATION_STYLES));
            fieldTc = ms.as(refDocTocEntry.getRefTocEntry(), FieldTC.class);
        }

        // WORDSNET-23793 Only un-escape TC argument.
        fieldTc = (fieldTc != null ? fieldTc : (ms.as(entry, FieldTC.class)));
        if (fieldTc != null)
            compositeModifier.addModifier(new FieldTokenDecoderNodeModifier(fieldTc.getTextRange(), FieldTokenDecoderOptions.ALL));

        TocEntryNodeModifier tocEntryNodeModifier = new TocEntryNodeModifier(
            getPreserveTabs(),
            getPreserveLineBreaks(),
            entry.getLevel(),
            options.isTableOfFigures());
        compositeModifier.addModifier(tocEntryNodeModifier);

        INodeModifier attributeModifier = getAttributeModifier(entry);
        compositeModifier.addModifier(attributeModifier);

        NodeRange labelRange = entry.getLabelRange();
        if (labelRange != null)
            NodeCopier.copy(labelRange, refNode, compositeModifier);

        CompositeNodeCopierListener compositeListener = new CompositeNodeCopierListener();
        compositeListener.add(new FieldFakeResultAppender(this, new FieldFakeResultNodeModifierAdapter(attributeModifier)));
        compositeListener.add(new CompositeNodeReplacer(NodeType.STRUCTURED_DOCUMENT_TAG, NodeType.SMART_TAG));
        compositeListener.add(new OfficeMathReplacer());

        IFieldRemoverFilter retainAdvanceFieldsFilter = new FieldRemoverRetainCertainFieldsFilter(FieldType.FIELD_ADVANCE);

        NodeCopier.copyWithoutFields(bookmarkRange, refNode, compositeModifier, compositeListener, retainAdvanceFieldsFilter);

        tocEntryNodeModifier.finalizeEntry();
    }

    private void buildEntryPageNumber(
        DocumentBuilder builder,
        ITocEntry entry,
        String sourceEntryBookmarkName,
        ParsedFieldToc options) throws Exception
    {
        // WORDSNET-20691 MS Word applies tabstop regardless of presence of page number.
        FieldIndexAndTablesUtil.ensureEntryParagraphTabStop(builder, getStart().getParentParagraph());

        boolean omitPageNumber =
            (isPageNumberOmittingLevelRangeSpecified() &&
            options.getPageNumberOmittingLevelRange().isInRange(entry.getLevel())) ||
            entry.getOmitPageNumber();

        // Build this unless we are instructed to omit page number.
        if (omitPageNumber)
            return;

        String separator = StringUtil.hasChars(getEntrySeparator())
                               ? Character.toString(getEntrySeparator().charAt(0)) // Word only takes the first character of the custom separator.
                               : ControlChar.TAB;
        builder.write(separator);

        if (!msString.isNullOrEmpty(getPrefixedSequenceIdentifier()))
        {
            buildEntryPageNumberPrefix(builder, entry, sourceEntryBookmarkName);

            String sequenceSeparator = StringUtil.hasChars(getSequenceSeparator())
                ? Character.toString(getSequenceSeparator().charAt(0))
                : "-";
            builder.write(sequenceSeparator);
        }

        buildEntryPageNumberPostfix(builder, entry, sourceEntryBookmarkName);

        getUpdater().freezeTocEntryBookmark(sourceEntryBookmarkName);
    }

    private void buildEntryPageNumberPrefix(DocumentBuilder builder, ITocEntry entry, String sourceEntryBookmarkName) throws Exception
    {
        if (entry.hasBookmark())
        {
            builder.insertField(
                msString.format(" SEQ {0} {1} \\* ARABIC ", getPrefixedSequenceIdentifier(), sourceEntryBookmarkName),
                PAGE_NUMBER_PLACEHOLDER);

            FieldSeqDataProvider provider = (FieldSeqDataProvider)getUpdater().getDataProviders().getOfType(FieldSeqDataProvider.class);
            if (provider != null)
                provider.addSequenceBookmark(sourceEntryBookmarkName);
        }
        else
        {
            builder.write(Integer.toString(entry.getSequenceValue(getPrefixedSequenceIdentifier())));
        }
    }

    private static void buildEntryPageNumberPostfix(DocumentBuilder builder, ITocEntry entry, String sourceEntryBookmarkName) throws Exception
    {
        if (entry.hasBookmark())
        {
            // Insert a PAGEREF field whose result is just a placeholder ("?") for the page number.
            builder.insertField(
                msString.format(" PAGEREF {0} \\h ", sourceEntryBookmarkName),
                PAGE_NUMBER_PLACEHOLDER);
        }
        else
        {
            builder.write(Integer.toString(entry.getPageNumber()));
        }
    }

    /// <summary>
    /// Makes an error message to display in TOC for the case when no TOC entries were found.
    /// </summary>
    private String getNoEntriesErrorMessage(ITocEntryExtractorOptions options) throws Exception
    {
        if (options.isBookmarkRangeSpecified() && (options.getRangeBookmark() == null))
        {
            FieldArgument bookmarkArg = getFieldCodeCache().getSwitchArgument(BOOKMARK_NAME_SWITCH);
            boolean isBookmarkNameReallySpecified = (bookmarkArg != null) && !bookmarkArg.getRange().isVoid();
            return isBookmarkNameReallySpecified
                ? Bookmark.ERROR_BOOKMARK_NOT_DEFINED
                : "Error! No bookmark name given.";
        }

        return options.isTableOfFigures()
            ? "No table of figures entries found."
            : "No table of contents entries found.";
    }

    private INodeModifier getAttributeModifier(ITocEntry entry)
    {
        // WORDSNET-23594 Strip duplicate direct formatting from TOC entries
        // WORDSNET-26975 Wrong colors in TOC.
        /*StyleIdentifier*/int tocStyleId = getStyleIdentifierForLevel(entry.getLevel(), false);

        return getInsertHyperlinks() && entry.hasBookmark()
                   ? (INodeModifier) new TocHyperlinkEntryAttributeModifier(tocStyleId, getDocument().getStyles())
                   : new TocNormalEntryAttributeModifier(tocStyleId, getDocument().getStyles(), !entry.isLinkedStyleTocEntry());
    }

    /// <summary>
    /// Returns a unique name for a TOC bookmark.
    /// </summary>
    /// <param name="bookmarkNames">A list of existing bookmark names (those that existed before update).</param>
    private String getUniqueTocBookmarkName(String[] bookmarkNames)
    {
        String bookmarkName;
        boolean contains;
        do
        {
            bookmarkName = msString.format("_Toc{0}", fetchDocument().getNextTocEntryBookmarkIndex());
            contains = ArrayUtil.binarySearch(bookmarkNames, bookmarkName) >= 0;
        }
        while (contains);

        return bookmarkName;
    }

    static /*StyleIdentifier*/int getStyleIdentifierForLevel(int entryLevel, boolean isTableOfFigures)
    {
        if (isTableOfFigures)
            return StyleIdentifier.TABLE_OF_FIGURES;

        switch (entryLevel)
        {
            case 1:
                return StyleIdentifier.TOC_1;
            case 2:
                return StyleIdentifier.TOC_2;
            case 3:
                return StyleIdentifier.TOC_3;
            case 4:
                return StyleIdentifier.TOC_4;
            case 5:
                return StyleIdentifier.TOC_5;
            case 6:
                return StyleIdentifier.TOC_6;
            case 7:
                return StyleIdentifier.TOC_7;
            case 8:
                return StyleIdentifier.TOC_8;
            case 9:
                return StyleIdentifier.TOC_9;
            default:
                // Word works this way.
                return StyleIdentifier.TOC_1;
        }
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INSERT_HYPERLINKS_SWITCH*/0:
            case /*USE_PARAGRAPH_OUTLINE_LEVEL_SWITCH*/1:
            case /*PRESERVE_TABS_SWITCH*/2:
            case /*PRESERVE_LINE_BREAKS_SWITCH*/3:
            case /*HIDE_IN_WEB_LAYOUT_SWITCH*/4:
                return FieldSwitchType.FLAG;
            case /*CAPTIONLESS_TABLE_OF_FIGURES_LABEL_SWITCH*/5:
            case /*BOOKMARK_NAME_SWITCH*/6:
            case /*TABLE_OF_FIGURES_LABEL_SWITCH*/7:
            case /*SEQUENCE_SEPARATOR_SWITCH*/8:
            case /*ENTRY_IDENTIFIER_SWITCH*/9:
            case /*ENTRY_LEVEL_RANGE_SWITCH*/10:
            case /*PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH*/11:
            case /*HEADING_LEVEL_RANGE_SWITCH*/12:
            case /*ENTRY_SEPARATOR_SWITCH*/13:
            case /*PREFIXED_SEQUENCE_IDENTIFIER_SWITCH*/14:
            case /*CUSTOM_STYLES_SWITCH*/15:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private boolean isPageNumberOmittingLevelRangeSpecified() { return getFieldCodeCache().hasSwitch(PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH); }

    /// <summary>
    /// Gets or sets the name of the bookmark that marks the portion of the document used to build the table.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getSwitchArgumentAsString(BOOKMARK_NAME_SWITCH); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setSwitch(BOOKMARK_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the sequence identifier used when building a table of figures.
    /// </summary>
    public String getTableOfFiguresLabel() { return getFieldCodeCache().getSwitchArgumentAsString(TABLE_OF_FIGURES_LABEL_SWITCH); }
    public void setTableOfFiguresLabel(String value) throws Exception { getFieldCodeCache().setSwitch(TABLE_OF_FIGURES_LABEL_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the sequence identifier used when building a table of figures that does not include caption's
    /// label and number.
    /// </summary>
    public String getCaptionlessTableOfFiguresLabel() { return getFieldCodeCache().getSwitchArgumentAsString(CAPTIONLESS_TABLE_OF_FIGURES_LABEL_SWITCH); }
    public void setCaptionlessTableOfFiguresLabel(String value) throws Exception { getFieldCodeCache().setSwitch(CAPTIONLESS_TABLE_OF_FIGURES_LABEL_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate sequence numbers and page numbers.
    /// </summary>
    public String getSequenceSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(SEQUENCE_SEPARATOR_SWITCH); }
    public void setSequenceSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(SEQUENCE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets a string that should match type identifiers of TC fields being included.
    /// </summary>
    public String getEntryIdentifier() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_IDENTIFIER_SWITCH); }
    public void setEntryIdentifier(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_IDENTIFIER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to make the table of contents entries hyperlinks.
    /// </summary>
    public boolean getInsertHyperlinks() { return getFieldCodeCache().hasSwitch(INSERT_HYPERLINKS_SWITCH); }
    public void setInsertHyperlinks(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_HYPERLINKS_SWITCH, value); }

    /// <summary>
    /// Gets or sets a range of levels of the table of contents entries to be included.
    /// </summary>
    public String getEntryLevelRange() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_LEVEL_RANGE_SWITCH); }
    public void setEntryLevelRange(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_LEVEL_RANGE_SWITCH, value); }

    /// <summary>
    /// Gets or sets a range of levels of the table of contents entries from which to omits page numbers.
    /// </summary>
    public String getPageNumberOmittingLevelRange() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH); }
    public void setPageNumberOmittingLevelRange(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH, value); }

    /// <summary>
    /// Gets or sets a range of heading levels to include.
    /// </summary>
    public String getHeadingLevelRange() { return getFieldCodeCache().getSwitchArgumentAsString(HEADING_LEVEL_RANGE_SWITCH); }
    public void setHeadingLevelRange(String value) throws Exception { getFieldCodeCache().setSwitch(HEADING_LEVEL_RANGE_SWITCH, value); }

    /// <summary>
    /// Gets or sets a sequence of characters that separate an entry and its page number.
    /// </summary>
    public String getEntrySeparator() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_SEPARATOR_SWITCH); }
    public void setEntrySeparator(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the identifier of a sequence for which a prefix should be added to the entry's page number.
    /// </summary>
    public String getPrefixedSequenceIdentifier() { return getFieldCodeCache().getSwitchArgumentAsString(PREFIXED_SEQUENCE_IDENTIFIER_SWITCH); }
    public void setPrefixedSequenceIdentifier(String value) throws Exception { getFieldCodeCache().setSwitch(PREFIXED_SEQUENCE_IDENTIFIER_SWITCH, value); }

    /// <summary>
    /// Gets or sets a list of styles other than the built-in heading styles to include in the table of contents.
    /// </summary>
    public String getCustomStyles() { return getFieldCodeCache().getSwitchArgumentAsString(CUSTOM_STYLES_SWITCH); }
    public void setCustomStyles(String value) throws Exception { getFieldCodeCache().setSwitch(CUSTOM_STYLES_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to use the applied paragraph outline level.
    /// </summary>
    public boolean getUseParagraphOutlineLevel() { return getFieldCodeCache().hasSwitch(USE_PARAGRAPH_OUTLINE_LEVEL_SWITCH); }
    public void setUseParagraphOutlineLevel(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_PARAGRAPH_OUTLINE_LEVEL_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to preserve tab entries within table entries.
    /// </summary>
    public boolean getPreserveTabs() { return getFieldCodeCache().hasSwitch(PRESERVE_TABS_SWITCH); }
    public void setPreserveTabs(boolean value) throws Exception { getFieldCodeCache().setSwitch(PRESERVE_TABS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to preserve newline characters within table entries.
    /// </summary>
    public boolean getPreserveLineBreaks() { return getFieldCodeCache().hasSwitch(PRESERVE_LINE_BREAKS_SWITCH); }
    public void setPreserveLineBreaks(boolean value) throws Exception { getFieldCodeCache().setSwitch(PRESERVE_LINE_BREAKS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to hide tab leader and page numbers in Web layout view.
    /// </summary>
    public boolean getHideInWebLayout() { return getFieldCodeCache().hasSwitch(HIDE_IN_WEB_LAYOUT_SWITCH); }
    public void setHideInWebLayout(boolean value) throws Exception { getFieldCodeCache().setSwitch(HIDE_IN_WEB_LAYOUT_SWITCH, value); }

    // WORDSNET-7485
    private boolean mIsExtraParaWasAdded;

    private static final String CAPTIONLESS_TABLE_OF_FIGURES_LABEL_SWITCH = "\\a";
    private static final String BOOKMARK_NAME_SWITCH = "\\b";
    private static final String TABLE_OF_FIGURES_LABEL_SWITCH = "\\c";
    private static final String SEQUENCE_SEPARATOR_SWITCH = "\\d";
    private static final String ENTRY_IDENTIFIER_SWITCH = "\\f";
    private static final String INSERT_HYPERLINKS_SWITCH = "\\h";
    private static final String ENTRY_LEVEL_RANGE_SWITCH = "\\l";
    private static final String PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH = "\\n";
    private static final String HEADING_LEVEL_RANGE_SWITCH = "\\o";
    private static final String ENTRY_SEPARATOR_SWITCH = "\\p";
    private static final String PREFIXED_SEQUENCE_IDENTIFIER_SWITCH = "\\s";
    private static final String CUSTOM_STYLES_SWITCH = "\\t";
    private static final String USE_PARAGRAPH_OUTLINE_LEVEL_SWITCH = "\\u";
    private static final String PRESERVE_TABS_SWITCH = "\\w";
    private static final String PRESERVE_LINE_BREAKS_SWITCH = "\\x";
    private static final String HIDE_IN_WEB_LAYOUT_SWITCH = "\\z";

    // It used to be a "??" placeholder, but it was changed to "?" per JIRA - 10635.
    // Lines with "??" placeholders wrapped differently from lines with single-digit page numbers.
    // It caused contents to shift down and incorrect page numbers in TOC as a result.
    private static final String PAGE_NUMBER_PLACEHOLDER = "?";

    private static class FieldFakeResultNodeModifierAdapter implements IFieldFakeResultNodeModifier
    {
        public FieldFakeResultNodeModifierAdapter(INodeModifier attributeModifier)
        {
            mAttributeModifier = attributeModifier;
        }

        public void /*IFieldFakeResultNodeModifier.*/modify(Node node) throws Exception
        {
            mAttributeModifier.modify(node, node, false, null);
        }

        private /*final*/ INodeModifier mAttributeModifier;
    }

    private static class OfficeMathReplacer extends NodeReplacer
    {
        OfficeMathReplacer()
        {
        	super(NodeType.OFFICE_MATH);
	
        }

        protected /*override*/ void collectClone(Node source, Node clone)
        {
            OfficeMath officeMath = (OfficeMath)source;
            if (!officeMath.isTopLevel())
                return;

            super.collectClone(source, clone);
        }

        protected /*override*/ void replaceCollectedNode(Node node)
        {
            OfficeMath officeMath = (OfficeMath)node;
            linearizeOfficeMath(officeMath);
        }

        private static void linearizeOfficeMath(OfficeMath officeMath)
        {
            if (officeMath.canInsert(new Run(officeMath.getDocument())))
            {
                Node[] runs = officeMath.getChildNodes(NodeType.RUN, true).toArray();
                officeMath.removeAllChildren();
                for (Node run : runs)
                    officeMath.appendChild(run);
            }
            else
            {
                for (OfficeMath childOfficeMath : (Iterable<OfficeMath>) officeMath.getChildNodes(NodeType.OFFICE_MATH, false))
                    linearizeOfficeMath(childOfficeMath);
            }
        }
    }

    private final static class ParsedFieldToc implements ITocEntryExtractorOptions
    {
        private ParsedFieldToc(
            FieldToc field,
            LevelRange entryLevelRange,
            LevelRange headingLevelRange,
            LevelRange pageNumberOmittingLevelRange)
        {
            mField = field;

            mCustomStylesToLevelsMap = parseCustomStyles(field);
            mTocEntryLevelRange = entryLevelRange;
            mHeadingLevelRange = headingLevelRange;
            mPageNumberOmittingLevelRange = pageNumberOmittingLevelRange;

            mEntryType = FieldIndexAndTablesUtil.getTocEntryType(field.getFieldCodeCache(), ENTRY_IDENTIFIER_SWITCH);

            mIsBookmarkRangeSpecified = hasSwitch(BOOKMARK_NAME_SWITCH);
            mIncludeTocEntryFields = hasSwitch(ENTRY_IDENTIFIER_SWITCH);
            mIsEntryLevelRangeSpecified = hasSwitch(ENTRY_LEVEL_RANGE_SWITCH);
            mIsTableOfFigures = hasSwitch(CAPTIONLESS_TABLE_OF_FIGURES_LABEL_SWITCH) ||
                               hasSwitch(TABLE_OF_FIGURES_LABEL_SWITCH);
            mIsHeadingLevelRangeSpecified = hasSwitch(HEADING_LEVEL_RANGE_SWITCH);
            mAreCustomStylesSpecified = hasSwitch(CUSTOM_STYLES_SWITCH);
            mUseParagraphOutlineLevel = mField.getUseParagraphOutlineLevel();
            mTableOfFiguresLabel = mField.getTableOfFiguresLabel();
            mCaptionlessTableOfFiguresLabel = mField.getCaptionlessTableOfFiguresLabel();
            mEnd = mField.getEnd();
            mStart = mField.getStart();

            // \l switch without \f switch works, but only if \l switch argument is specified.
            if (!getIncludeTocEntryFields() && (field.getFieldCodeCache().getSwitchArgument(ENTRY_LEVEL_RANGE_SWITCH) == null))
                mTocEntryLevelRange = LevelRange.EMPTY_RANGE;
        }

        public boolean isBookmarkRangeSpecified() { return mIsBookmarkRangeSpecified; };

        private  boolean mIsBookmarkRangeSpecified;
        public boolean getIncludeTocEntryFields() { return mIncludeTocEntryFields; };

        private  boolean mIncludeTocEntryFields;
        public boolean isEntryLevelRangeSpecified() { return mIsEntryLevelRangeSpecified; };

        private  boolean mIsEntryLevelRangeSpecified;
        public boolean isTableOfFigures() { return mIsTableOfFigures; };

        private  boolean mIsTableOfFigures;
        public boolean isHeadingLevelRangeSpecified() { return mIsHeadingLevelRangeSpecified; };

        private  boolean mIsHeadingLevelRangeSpecified;
        public boolean getAreCustomStylesSpecified() { return mAreCustomStylesSpecified; };

        private  boolean mAreCustomStylesSpecified;
        public boolean getUseParagraphOutlineLevel() { return mUseParagraphOutlineLevel; };

        private  boolean mUseParagraphOutlineLevel;
        public String getTableOfFiguresLabel() { return mTableOfFiguresLabel; };

        private  String mTableOfFiguresLabel;
        public LevelRange getTocEntryLevelRange() { return mTocEntryLevelRange; };

        private LevelRange mTocEntryLevelRange;
        public LevelRange getHeadingLevelRange() { return mHeadingLevelRange; };

        private LevelRange mHeadingLevelRange;
        LevelRange getPageNumberOmittingLevelRange() { return mPageNumberOmittingLevelRange; };

        private LevelRange mPageNumberOmittingLevelRange;
        public String getCaptionlessTableOfFiguresLabel() { return mCaptionlessTableOfFiguresLabel; };

        private  String mCaptionlessTableOfFiguresLabel;
        public int getEntryType() { return mEntryType; };

        private  int mEntryType;
        public FieldEnd getEnd() { return mEnd; };

        private FieldEnd mEnd;
        public FieldStart getStart() { return mStart; };

        private FieldStart mStart;

        public boolean getSkipTables() { return false; }

        public boolean getIncludeRefDocFields() { return true; }

        public Bookmark getRangeBookmark() throws Exception
        {

            if (mField.getBookmarkName() == null)
                return null;

            Bookmark bookmark = mField.getDocument().getRange().getBookmarks().get(mField.getBookmarkName());
            if (bookmark == null)
                return null;

            // Check if it is in the section body.
            boolean bookmarkIsInSectionBody = NodeUtil.hasAncestor(bookmark.getBookmarkStart(), NodeType.BODY) &&
                                           NodeUtil.hasAncestor(bookmark.getBookmarkEnd(), NodeType.BODY);

            return bookmarkIsInSectionBody
                ? bookmark
                : null;
        }

        public int getLevelForCustomStyle(Paragraph paragraph, Style style)
        {
            if (getUseParagraphOutlineLevel())
            {
                int outlineLevel = TocEntryExtractor.getParagraphLevelFromOutlineLevel(paragraph);
                if (!LevelRange.isLevelValid(outlineLevel))
                    return LevelRange.INVALID_LEVEL;
            }

            int level = mCustomStylesToLevelsMap.get(style.getName());
            if (!StringToIntDictionary.isNullSubstitute(level))
                return level;

            // WORDSNET-12288 Compare custom styles with style aliases.
            String[] styleAliases = style.getAliasesInternal();
            for (String styleAlias : styleAliases)
            {
                level = mCustomStylesToLevelsMap.get(styleAlias);
                if (!StringToIntDictionary.isNullSubstitute(level))
                    return level;
            }

            return LevelRange.INVALID_LEVEL;
        }

        private boolean hasSwitch(String switchName)
        {
            return mField.getFieldCodeCache().hasSwitch(switchName);
        }

        static ParsedFieldToc tryParse(FieldToc field)
        {
            LevelRange entryLevelRange = parseSwitchLevelRange(field, ENTRY_LEVEL_RANGE_SWITCH);
            if (entryLevelRange == null)
                return null;

            LevelRange headingLevelRange = parseSwitchLevelRange(field, HEADING_LEVEL_RANGE_SWITCH);
            if (headingLevelRange == null)
                return null;

            LevelRange pageNumberOmittingLevelRange = parseSwitchLevelRange(field, PAGE_NUMBER_OMITTING_LEVEL_RANGE_SWITCH);
            if (pageNumberOmittingLevelRange == null)
                return null;

            return new ParsedFieldToc(field, entryLevelRange, headingLevelRange, pageNumberOmittingLevelRange);
        }

        private static LevelRange parseSwitchLevelRange(Field field, String switchName)
        {
            if (!field.getFieldCodeCache().hasSwitch(switchName))
                return LevelRange.MAX_RANGE;

            FieldArgument argument = field.getFieldCodeCache().getSwitchArgument(switchName);
            if (argument == null)
                return LevelRange.MAX_RANGE;

            return LevelRange.tryParse(argument.getNormalizedText());
        }

        private static StringToIntDictionary parseCustomStyles(FieldToc field)
        {
            StringToIntDictionary result = new StringToIntDictionary(false);

            String separator = field.fetchDocument().getFieldOptions().getCustomTocStyleSeparator();
            if (msString.isNullOrEmpty(separator))
            {
                // WORDSNET-16998 Use list separators from the thread culture rather than hardcoded ones.
                separator = Character.toString(FormatterPal.getListSeparatorCurrent());
            }

            for (String customStyles : field.getFieldCodeCache().getSwitchArgumentsAsStrings(CUSTOM_STYLES_SWITCH))
            {
                if (msString.isNullOrEmpty(customStyles))
                    continue;

                String[] customStylesParts = msString.split(customStyles, 
                    new String[] { separator },
                    StringSplitOptions.REMOVE_EMPTY_ENTRIES);

                parseCustomStyles(result, field, customStylesParts);
            }

            return result;
        }

        private static void parseCustomStyles(StringToIntDictionary result, Field field, String[] customStylesParts)
        {
            int implicitStyleLevel = LevelRange.MIN_LEVEL;
            StyleCollection styles = field.getDocument().getStyles();

            int i = 0;
            while (i < customStylesParts.length)
            {
                String styleName = msString.trim(customStylesParts[i]);
                // WORDSNET-10465 Use invariant built-in style name, if exist.
                String invariantStyleName = StyleNameTranslator.getInvariantStyleName(styleName);

                if (i == customStylesParts.length - 1)
                {
                    mapCustomStyleToLevel(result, styleName, invariantStyleName, 1);
                    return;
                }

                String styleLevelString = msString.trim(customStylesParts[i + 1]);
                int styleLevel = FormatterPal.tryParseIntPortion(styleLevelString);

                if (LevelRange.isLevelValid(styleLevel))
                {
                    i++;
                    implicitStyleLevel = LevelRange.MAX_LEVEL;
                }
                else if (hasStyle(styles, styleName, invariantStyleName))
                {
                    styleLevel = implicitStyleLevel;
                    if (implicitStyleLevel < LevelRange.MAX_LEVEL)
                        implicitStyleLevel++;
                }

                if (LevelRange.isLevelValid(styleLevel))
                    mapCustomStyleToLevel(result, styleName, invariantStyleName, styleLevel);

                i++;
            }
        }

        private static boolean hasStyle(StyleCollection styles, String styleName, String invariantStyleName)
        {
            return hasStyle(styles, styleName) || hasStyle(styles, invariantStyleName);
        }

        private static boolean hasStyle(StyleCollection styles, String name)
        {
            if (msString.isNullOrEmpty(name))
                return false;

            return styles.get(name) != null;
        }

        private static void mapCustomStyleToLevel(
            StringToIntDictionary result,
            String styleName,
            String invariantStyleName,
            int styleLevel)
        {
            // WORDSNET-13628 & WORDSNET-13235 The DOM may contain style names in non-invariant culture.
            mapCustomStyleToLevel(result, styleName, styleLevel);
            mapCustomStyleToLevel(result, invariantStyleName, styleLevel);
        }

        private static void mapCustomStyleToLevel(StringToIntDictionary result, String styleName, int styleLevel)
        {
            if (msString.isNullOrEmpty(styleName))
                return;

            if (!result.containsKey(styleName)) // WORDSNET-23853 Avoid adding the same style twice.
                result.add(styleName, styleLevel);
        }

        private /*final*/ FieldToc mField;
        private /*final*/ StringToIntDictionary mCustomStylesToLevelsMap;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\h",
		"\\u",
		"\\w",
		"\\x",
		"\\z",
		"\\a",
		"\\b",
		"\\c",
		"\\d",
		"\\f",
		"\\l",
		"\\n",
		"\\o",
		"\\p",
		"\\s",
		"\\t"
	);

}

