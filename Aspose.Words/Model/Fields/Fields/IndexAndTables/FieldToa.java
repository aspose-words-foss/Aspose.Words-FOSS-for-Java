// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements the TOA field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Builds a table of authorities (that is, a list of the references in a legal document, such as references
/// to cases, statutes, and rules, along with the numbers of the pages on which the references appear) using the
/// entries specified by TA fields.
/// </remarks>
public class FieldToa extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Bookmark bookmark = null;
        if (hasBookmarkNameSwitch())
        {
            String bookmarkName = getBookmarkName();
            if (bookmarkName == null)
                return new FieldUpdateActionInsertErrorMessage(this, NO_BOOKMARK_NAME_GIVEN_ERROR);

            bookmark = fetchBookmark(bookmarkName);
            if (bookmark == null)
                return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR);
        }

        String entryCategory = getEntryCategory();
        int categoryNumber = FormatterPal.tryParseInt(entryCategory);
        if (categoryNumber <= 0)
            return new FieldUpdateActionInsertErrorMessage(this, CATEGORY_NUMBER_NOT_FOUND_ERROR);

        String sequenceName = getSequenceName();
        FieldSeqDataProvider seqDataProvider = sequenceName != null
            ? getUpdater().getDataProviders().ensure(new FieldSeqDataProvider(getUpdater()))
            : null;

        ArrayList<ToaEntry> entries = ToaEntryExtractor.extractToaEntries(fetchDocument(), getEntryCategory(), bookmark, sequenceName, seqDataProvider);
        if (entries.size() == 0)
            return new FieldUpdateActionInsertErrorMessage(this, NO_TABLE_OF_AUTHORITIES_ENTRIES_FOUND_ERROR);

        IDisposable iDisposableResource = getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            DocumentBuilder builder = FieldIndexAndTablesUtil.getDocumentBuilder(this);
            FieldIndexAndTablesUtil.convertOuterInlineSdtToBlock(this);

            buildHeading(categoryNumber, builder);
            buildEntries(entries, builder);
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }

        return new FieldUpdateActionDoNothing(this);
    }

    private void buildHeading(int category, DocumentBuilder builder)
    {
        if (!getUseHeading())
            return;

        String heading = fetchDocument().getFieldOptions().getEffectiveToaCategories().get(category);

        FieldIndexAndTablesUtil.setUpEntryParagraph(this, builder,  StyleIdentifier.TOA_HEADING);
        FieldIndexAndTablesUtil.ensureEntryParagraphTabStop(builder, null);

        builder.write(heading);
    }

    private void buildEntries(Iterable<ToaEntry> entries, DocumentBuilder builder) throws Exception
    {
        boolean usePassim = getUsePassim();

        String entrySeparator = normalizeSeparator(getEntrySeparator(), ControlChar.TAB);
        String pageNumberListSeparator = normalizeSeparator(getPageNumberListSeparator(), ", ");
        String sequenceSeparator = normalizeSeparator(getSequenceSeparator(), "-");
        String pageRangeSeparator = normalizeSeparator(getPageRangeSeparator(), "–");

        ToaEntryAttributeModifier toaEntryAttributeModifier = new ToaEntryAttributeModifier(getDocument().getStyles(), getRemoveEntryFormatting());

        for (ToaEntry entry : entries)
        {
            buildEntry(
                builder,
                entry,
                toaEntryAttributeModifier,
                usePassim,
                entrySeparator,
                pageNumberListSeparator,
                sequenceSeparator,
                pageRangeSeparator);
        }
    }

    private void buildEntry(
        DocumentBuilder builder,
        ToaEntry entry,
        INodeModifier toaEntryAttributeModifier,
        boolean usePassim,
        String entrySeparator,
        String pageNumberListSeparator,
        String sequenceSeparator,
        String pageRangeSeparator) throws Exception
    {
        FieldIndexAndTablesUtil.setUpEntryParagraph(this, builder, StyleIdentifier.TABLE_OF_AUTHORITIES);
        FieldIndexAndTablesUtil.ensureEntryParagraphTabStop(builder, null);

        CompositeModifier modifier = new CompositeModifier();

        DocumentBase entryDocument = entry.getRange().getDocument();
        if (entryDocument != getDocument())
            modifier.addModifier(new ExternalDocumentModifier(entryDocument, getDocument(), ImportFormatMode.USE_DESTINATION_STYLES));

        modifier.addModifier(new FieldTokenDecoderNodeModifier(entry.getRange(), FieldTokenDecoderOptions.ALL));
        modifier.addModifier(toaEntryAttributeModifier);

        Node dummyRefNode = FieldIndexAndTablesUtil.createAndInsertDummyRefNode(builder);
        NodeCopier.copyWithoutFields(entry.getRange(), dummyRefNode, modifier, null, true);

        builder.write(entrySeparator);

        ArrayList<Run> range = entry.buildPagesRuns(builder.getDocument(), usePassim, pageNumberListSeparator, sequenceSeparator, pageRangeSeparator);
        for (Run run : range)
            builder.insertNode(run);

        dummyRefNode.remove();
    }

    private static String normalizeSeparator(String switchValue, String defaultValue)
    {
        final int MAX_SEPARATOR_LENGTH = 15;

        return switchValue == null
            ? defaultValue
            : StringUtil.truncate(switchValue, MAX_SEPARATOR_LENGTH);
    }

    private Bookmark fetchBookmark(String bookmarkName)
    {
        if (msString.equals(bookmarkName, ""))
            return null;

        Document document = fetchDocument();
        Bookmark bookmark = document.getRange().getBookmarks().get(bookmarkName);

        if (bookmark == null)
            return null;

        if (bookmark.getBookmarkStart().getAncestor(NodeType.BODY) == null)
            return null;

        return bookmark;
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*REMOVE_ENTRY_FORMATTING_SWITCH*/0:
            case /*USE_PASSIM_SWITCH*/1:
            case /*HEADING_SWITCH*/2:
                return FieldSwitchType.FLAG;
            case /*BOOKMARK_NAME_SWITCH*/3:
            case /*ENTRY_CATEGORY_SWITCH*/4:
            case /*SEQUENCE_SEPARATOR_SWITCH*/5:
            case /*ENTRY_SEPARATOR_SWITCH*/6:
            case /*PAGE_RANGE_SEPARATOR_SWITCH*/7:
            case /*PAGE_NUMBER_LIST_SEPARATOR_SWITCH*/8:
            case /*SEQUENCE_NAME_SWITCH*/9:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets or sets the name of the bookmark that marks the portion of the document used to build the table.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getSwitchArgumentAsString(BOOKMARK_NAME_SWITCH); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setSwitch(BOOKMARK_NAME_SWITCH, value); }

    boolean hasBookmarkNameSwitch() { return getFieldCodeCache().hasSwitch(BOOKMARK_NAME_SWITCH);}

    /// <summary>
    /// Gets or sets the integral category for entries included in the table.
    /// </summary>
    public String getEntryCategory() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_CATEGORY_SWITCH); }
    public void setEntryCategory(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(ENTRY_CATEGORY_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate sequence numbers and page numbers.
    /// </summary>
    public String getSequenceSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(SEQUENCE_SEPARATOR_SWITCH); }
    public void setSequenceSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(SEQUENCE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate a table of authorities entry and its page number.
    /// </summary>
    public String getEntrySeparator() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_SEPARATOR_SWITCH); }
    public void setEntrySeparator(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to remove the formatting of the entry text in the document from the
    /// entry in the table of authorities.
    /// </summary>
    public boolean getRemoveEntryFormatting() { return getFieldCodeCache().hasSwitch(REMOVE_ENTRY_FORMATTING_SWITCH); }
    public void setRemoveEntryFormatting(boolean value) throws Exception { getFieldCodeCache().setSwitch(REMOVE_ENTRY_FORMATTING_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate the start and end of a page range.
    /// </summary>
    public String getPageRangeSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_RANGE_SEPARATOR_SWITCH); }
    public void setPageRangeSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_RANGE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to include the category heading for the entries in a table of authorities.
    /// </summary>
    public boolean getUseHeading() { return getFieldCodeCache().hasSwitch(HEADING_SWITCH); }
    public void setUseHeading(boolean value) throws Exception { getFieldCodeCache().setSwitch(HEADING_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate two page numbers in a page number list.
    /// </summary>
    public String getPageNumberListSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_LIST_SEPARATOR_SWITCH); }
    public void setPageNumberListSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_LIST_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to replace five or more different page references to the same
    /// authority with "passim", which is used to indicate that a word or passage occurs frequently
    /// in the work cited.
    /// </summary>
    public boolean getUsePassim() { return getFieldCodeCache().hasSwitch(USE_PASSIM_SWITCH); }
    public void setUsePassim(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_PASSIM_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of a sequence whose number is included with the page number.
    /// </summary>
    public String getSequenceName() { return getFieldCodeCache().getSwitchArgumentAsString(SEQUENCE_NAME_SWITCH); }
    public void setSequenceName(String value) throws Exception { getFieldCodeCache().setSwitch(SEQUENCE_NAME_SWITCH, value); }

    private static final String BOOKMARK_NAME_SWITCH = "\\b";
    private static final String ENTRY_CATEGORY_SWITCH = "\\c";
    private static final String SEQUENCE_SEPARATOR_SWITCH = "\\d";
    private static final String ENTRY_SEPARATOR_SWITCH = "\\e";
    private static final String REMOVE_ENTRY_FORMATTING_SWITCH = "\\f";
    private static final String PAGE_RANGE_SEPARATOR_SWITCH = "\\g";
    private static final String HEADING_SWITCH = "\\h";
    private static final String PAGE_NUMBER_LIST_SEPARATOR_SWITCH = "\\l";
    private static final String USE_PASSIM_SWITCH = "\\p";
    private static final String SEQUENCE_NAME_SWITCH = "\\s";

    private static final String CATEGORY_NUMBER_NOT_FOUND_ERROR = "Error! Category number not found.";
    private static final String NO_TABLE_OF_AUTHORITIES_ENTRIES_FOUND_ERROR = "No table of authorities entries found.";
    private static final String NO_BOOKMARK_NAME_GIVEN_ERROR = "Error! No bookmark name given.";
    private static final String BOOKMARK_NOT_DEFINED_ERROR = "Error! Bookmark not defined.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\f",
		"\\p",
		"\\h",
		"\\b",
		"\\c",
		"\\d",
		"\\e",
		"\\g",
		"\\l",
		"\\s"
	);

}

