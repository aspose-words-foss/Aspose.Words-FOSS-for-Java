// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.words.ref.RefBoolean;

/// <summary>
/// Implements the REF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the text or graphics represented by the specified bookmark.
/// </remarks>
public class FieldRef extends Field implements IFieldCodeTokenInfoProvider, IMergeFieldSurrogate
{
    /*override*/ /*FieldUpdateStage*/int getUpdateStage() throws Exception
    {
        boolean isParagraphNumberInAnyContext = getInsertParagraphNumberInAnyContext();
        if (!isParagraphNumberInAnyContext && getInsertRelativePosition())
            return FieldUpdateStage.MAIN_LOOP;

        return FieldRefUtil.getFieldUpdateStage(this, getBookmarkName(), isParagraphNumberInAnyContext);
    }

    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        // WORDSNET-6330 Exception is thrown on bookmark seek if name of bookmark is not specified.
        if (!StringUtil.hasChars(getBookmarkName()))
            return new FieldUpdateActionInsertErrorMessage(this, NO_BOOKMARK_NAME_GIVEN_ERROR_MESSAGE);

        // SPEED Get a bookmark from a cache.
        Bookmark bookmark = FieldUtil.getCachedBookmark(this, getBookmarkName());
        if (bookmark == null)
            return new FieldUpdateActionInsertErrorMessage(this, REFERENCE_SOURCE_NOT_FOUND_ERROR_MESSAGE);

        // WORDSNET-22358 Get bookmark node range before old field result is removed because bookmark may be inside.
        NodeRange bookmarkRange = bookmark.getNodeRange();
        if (bookmarkRange.isVoid())
            return new FieldUpdateActionInsertErrorMessage(this, REFERENCE_SOURCE_NOT_FOUND_ERROR_MESSAGE);

        bookmarkRange = adjustBookmarkRangeIfNeeded(bookmarkRange);

        boolean hasBookmarkInResult = false;
        RefBoolean referenceToHasBookmarkInResult = new RefBoolean(hasBookmarkInResult);
        Paragraph bookmarkParagraph = FieldRefUtil.beginFieldUpdate(this, bookmark, getInsertParagraphNumberInAnyContext(), /*out*/ referenceToHasBookmarkInResult);
        hasBookmarkInResult = referenceToHasBookmarkInResult.get();

        String result = getParagraphNumberOrRelativePosition(bookmark, bookmarkParagraph);
        if (result != null)
            return new FieldUpdateActionApplyResult(this, result);

        Debug.assertTrue(bookmarkRange != null);

        return FieldRefUtil.endFieldUpdate(
            this,
            bookmark,
            bookmarkRange,
            bookmarkParagraph,
            hasBookmarkInResult,
            getIncludeNoteOrComment());
    }

    private String getParagraphNumberOrRelativePosition(Bookmark bookmark, Paragraph bookmarkParagraph) throws Exception
    {
        if (getInsertParagraphNumber())
        {
            // It seems like the trailing dot is the only thing that does not go to the REF field. That is, "11."
            // becomes "11", but "11.2" stays "11.2" and "11*" stays "11*". Maybe need to investigate more.
            return FieldRefUtil.getParagraphNumber(
                this,
                bookmark,
                bookmarkParagraph,
                getSuppressNonDelimiters(),
                getInsertRelativePosition());
        }

        if (getInsertParagraphNumberInRelativeContext())
        {
            // It seems like the trailing dot is the only thing that does not go to the REF field. That is, "11."
            // becomes "11", but "11.2" stays "11.2" and "11*" stays "11*". Maybe need to investigate more.
            return FieldRefUtil.getParagraphNumberInRelativeContext(
                this,
                bookmark,
                bookmarkParagraph,
                getStart().getParentParagraph(),
                getSuppressNonDelimiters(),
                getNumberSeparator(),
                getInsertRelativePosition());
        }

        if (getInsertParagraphNumberInFullContext())
        {
            // It seems like the trailing dot is the only thing that does not go to the REF field. That is, "11."
            // becomes "11", but "11.2" stays "11.2" and "11*" stays "11*". Maybe need to investigate more.
            return FieldRefUtil.getParagraphNumberInFullContext(
                this,
                bookmark,
                bookmarkParagraph,
                getSuppressNonDelimiters(),
                getNumberSeparator(),
                getInsertRelativePosition());
        }

        if (getInsertRelativePosition())
        {
            return FieldRefUtil.getRelativeBookmarkPosition(this, bookmark);
        }

        return null;
    }

    private NodeRange adjustBookmarkRangeIfNeeded(NodeRange bookmarkRange)
    {
        if (getInsertParagraphNumberOrRelativePosition())
            return bookmarkRange;

        // MS Word copies whole field result even if bookmark range does not include whole field.
        return includeIncompleteFields(bookmarkRange);
    }

    private static NodeRange includeIncompleteFields(NodeRange range)
    {
        int depth = 0;
        FieldStart fieldStart = null;
        FieldEnd fieldEnd = null;

        for (Node node : range)
        {
            switch (node.getNodeType())
            {
                case NodeType.FIELD_START:
                    if (depth == 0)
                        fieldStart = (FieldStart)node;
                    depth++;
                    break;
                case NodeType.FIELD_END:
                    if (depth != 0)
                        depth--;
                    else
                        fieldEnd = (FieldEnd)node;

                    if (depth == 0)
                        fieldStart = null;
                    break;
                default:
                    break;
            }
        }

        if ((fieldStart == null) && (fieldEnd == null))
            return range;

        Node rangeStart = fieldEnd != null
            ? fieldEnd.getField().getStart()
            : null;

        Node rangeEnd = fieldStart != null
            ? fieldStart.getField().getEnd()
            : null;

        return new NodeRange(
            (rangeStart != null ? rangeStart : range.getStart().getNode()),
            rangeStart != null || range.isStartIncluded(),
            (rangeEnd != null ? rangeEnd : range.getEnd().getNode()),
            rangeEnd != null || range.isEndIncluded());
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INCLUDE_NOTE_OR_COMMENT_SWITCH*/0:
            case /*INSERT_HYPERLINK_SWITCH*/1:
            case /*INSERT_PARAGRAPH_NUMBER_SWITCH*/2:
            case /*INSERT_RELATIVE_POSITION_SWITCH*/3:
            case /*INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH*/4:
            case /*SUPPRESS_NON_DELIMITERS_SWITCH*/5:
            case /*INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH*/6:
            {
                return FieldSwitchType.FLAG;
            }
            case /*NUMBER_SEPARATOR_SWITCH*/7:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    @ReservedForInternalUse @Deprecated
    public String /*IMergeFieldSurrogate.*/getMergeFieldName()
    {
        return getBookmarkName();
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/canWorkAsMergeField()
    {
        return true;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*IMergeFieldSurrogate.*/isMergeValueRequired()
    {
        return true;
    }

    /// <summary>
    /// Gets or sets the referenced bookmark's name.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_ARGUMENT_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate sequence numbers and page numbers.
    /// </summary>
    public String getNumberSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(NUMBER_SEPARATOR_SWITCH); }
    public void setNumberSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(NUMBER_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to increment footnote, endnote, and annotation numbers that are
    /// marked by the bookmark, and insert the corresponding footnote, endnote, and comment text.
    /// </summary>
    public boolean getIncludeNoteOrComment() { return getFieldCodeCache().hasSwitch(INCLUDE_NOTE_OR_COMMENT_SWITCH); }
    public void setIncludeNoteOrComment(boolean value) throws Exception { getFieldCodeCache().setSwitch(INCLUDE_NOTE_OR_COMMENT_SWITCH, value); }

    private boolean getInsertParagraphNumberInAnyContext() { return (getInsertParagraphNumber() || getInsertParagraphNumberInRelativeContext() || getInsertParagraphNumberInFullContext()); }

    private boolean getInsertParagraphNumberOrRelativePosition() { return getInsertParagraphNumberInAnyContext() || getInsertRelativePosition(); }

    /// <summary>
    /// Gets or sets whether to create a hyperlink to the bookmarked paragraph.
    /// </summary>
    public boolean getInsertHyperlink() { return getFieldCodeCache().hasSwitch(INSERT_HYPERLINK_SWITCH); }
    public void setInsertHyperlink(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_HYPERLINK_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph exactly as it appears in the document.
    /// </summary>
    public boolean getInsertParagraphNumber() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_SWITCH); }
    public void setInsertParagraphNumber(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the relative position of the referenced paragraph.
    /// </summary>
    public boolean getInsertRelativePosition() { return getFieldCodeCache().hasSwitch(INSERT_RELATIVE_POSITION_SWITCH); }
    public void setInsertRelativePosition(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_RELATIVE_POSITION_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph in relative context.
    /// </summary>
    public boolean getInsertParagraphNumberInRelativeContext() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH); }
    public void setInsertParagraphNumberInRelativeContext(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to suppress non-delimiter characters.
    /// </summary>
    public boolean getSuppressNonDelimiters() { return getFieldCodeCache().hasSwitch(SUPPRESS_NON_DELIMITERS_SWITCH); }
    public void setSuppressNonDelimiters(boolean value) throws Exception { getFieldCodeCache().setSwitch(SUPPRESS_NON_DELIMITERS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph in full context.
    /// </summary>
    public boolean getInsertParagraphNumberInFullContext() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH); }
    public void setInsertParagraphNumberInFullContext(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH, value); }

    private static final int BOOKMARK_NAME_ARGUMENT_INDEX = 0;

    private static final String NUMBER_SEPARATOR_SWITCH = "\\d";
    private static final String INCLUDE_NOTE_OR_COMMENT_SWITCH = "\\f";
    static final String INSERT_HYPERLINK_SWITCH = "\\h";
    private static final String INSERT_PARAGRAPH_NUMBER_SWITCH = "\\n";
    private static final String INSERT_RELATIVE_POSITION_SWITCH = "\\p";
    private static final String INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH = "\\r";
    private static final String SUPPRESS_NON_DELIMITERS_SWITCH = "\\t";
    private static final String INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH = "\\w";

    private static final String NO_BOOKMARK_NAME_GIVEN_ERROR_MESSAGE = "Error! No bookmark name given.";
    private static final String REFERENCE_SOURCE_NOT_FOUND_ERROR_MESSAGE = "Error! Reference source not found.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\f",
		"\\h",
		"\\n",
		"\\p",
		"\\r",
		"\\t",
		"\\w",
		"\\d"
	);

}

