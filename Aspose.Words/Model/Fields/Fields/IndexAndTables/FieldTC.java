// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.NullableInt32;


/// <summary>
/// Implements the TC field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Defines the text and page number for a table of contents (including a table of figures) entry, which
/// is used by a TOC field.
/// </remarks>
public final class FieldTC extends Field implements IFieldCodeTokenInfoProvider, ITocEntry
{
    /*override*/ void initialize(FieldStart start, FieldSeparator separator, FieldEnd end)
    {
        super.initialize(start, separator, end);
        parse();
    }

    private void parse()
    {
        FieldArgument text = getFieldCodeCache().getArgument(TEXT_ARGUMENT_INDEX);
        // Set a flag indicating if the entry shows in TOC
        mShowInToc = (text != null) && (text.getNormalizedText().length() > 0);

        // WORDSNET-22419 NullReferenceException occurs during UpdateFields.
        // Even if the entry does not show it TOC, the correct argument range is needed for document conversion.
        setTextRange(getFieldCodeCache().getArgumentRange(TEXT_ARGUMENT_INDEX));

        // The level should be an integer number from 1 to 9.
        int level = LevelRange.MIN_LEVEL;
        if (hasEntryLevelSwitch())
        {
            // TODO MS Word updates a TOC field with following error if a TC field level range switch is zero or negative or not a number:
            // Error! Not a valid heading level in TOC entry on page 1.

            level = getEntryLevelAsInt32().getValueOrDefault(level);
            level = LevelRange.isLevelValid(level)
                ? level
                : LevelRange.MIN_LEVEL;
        }

        mLevel = level;
    }

    /// <summary>
    /// Calls Node.InsertAfter() or Node.InsertBefore depending on insertAfter parameter.
    /// Node.Insert() could be used instead, but it's private.
    /// </summary>
    /// <param name="newChild"></param>
    /// <param name="refChild"></param>
    /// <param name="insertAfter"></param>
    private static void insertNode(Node newChild, Node refChild, boolean insertAfter)
    {
        if (insertAfter)
            refChild.insertNext(newChild);
        else
            refChild.insertPrevious(newChild);
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*OMIT_PAGE_NUMBER_SWITCH*/0:
                return FieldSwitchType.FLAG;
            case /*TYPE_IDENTIFIER_SWITCH*/1:
            case /*ENTRY_LEVEL_SWITCH*/2:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets the node range for this field's text.
    /// </summary>
    NodeRange getTextRange() { return mTextRange; }; private void setTextRange(NodeRange value) { mTextRange = value; };

    private NodeRange mTextRange;

    /// <summary>
    /// Gets or sets the text of the entry.
    /// </summary>
    public String getText() { return getFieldCodeCache().getArgumentAsString(TEXT_ARGUMENT_INDEX); }
    public void setText(String value) throws Exception { getFieldCodeCache().setArgument(TEXT_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets a type identifier for this field (which is typically a letter).
    /// </summary>
    NodeRange getRange() { return getTextRange(); }

    /// <summary>
    /// Gets or sets a type identifier for this field (which is typically a letter).
    /// </summary>
    public String getTypeIdentifier() { return getFieldCodeCache().getSwitchArgumentAsString(TYPE_IDENTIFIER_SWITCH); }
    public void setTypeIdentifier(String value) throws Exception { getFieldCodeCache().setSwitch(TYPE_IDENTIFIER_SWITCH, value); }

    /// <summary>
    /// Gets or sets the level of the entry.
    /// </summary>
    public String getEntryLevel() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_LEVEL_SWITCH); }
    public void setEntryLevel(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(ENTRY_LEVEL_SWITCH, value); }

    boolean hasEntryLevelSwitch() { return getFieldCodeCache().hasSwitch(ENTRY_LEVEL_SWITCH); }

    NullableInt32 getEntryLevelAsInt32() { return getFieldCodeCache().getSwitchArgumentAsInt32(ENTRY_LEVEL_SWITCH); }

    /// <summary>
    /// Gets or sets whether page number in TOC should be omitted for this field.
    /// </summary>
    public boolean getOmitPageNumber() { return getFieldCodeCache().hasSwitch(OMIT_PAGE_NUMBER_SWITCH); }
    public void setOmitPageNumber(boolean value) throws Exception { getFieldCodeCache().setSwitch(OMIT_PAGE_NUMBER_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public Paragraph /*ITocEntry.*/getParagraph()
    {
        NodeRange range = getEntryRange();
        if (range == null)
            return null;

        return (Paragraph)range.getStart().getNode().getAncestor(NodeType.PARAGRAPH);
    }

    @ReservedForInternalUse @Deprecated
    public NodeRange /*ITocEntry.*/insertBookmark(String bookmarkName)
    {
        NodeRange range = getEntryRange();
        if (range == null)
            return null;

        BookmarkStart bookmarkStart = new BookmarkStart(fetchDocument(), bookmarkName);
        Node startNode = range.getStart().getNode();
        insertNode(bookmarkStart, startNode, range.getStart().isEnd());

        BookmarkEnd bookmarkEnd = new BookmarkEnd(fetchDocument(), bookmarkName);
        Node endNode = range.getEnd().getNode();
        insertNode(bookmarkEnd, endNode, !range.getEnd().isStart());

        return Bookmark.getNodeRange(bookmarkStart, bookmarkEnd);
    }

    @ReservedForInternalUse @Deprecated
    public int /*ITocEntry.*/getLevel() { return mLevel; }

    @ReservedForInternalUse @Deprecated
    public String /*ITocEntry.*/getDocumentOutlineTitle()
    {
        throw new IllegalStateException();
    }

    @ReservedForInternalUse @Deprecated
    public NodeRange /*ITocEntry.*/getLabelRange()
    {
        return null;
    }

    @ReservedForInternalUse @Deprecated
    public boolean /*ITocEntry.*/isInFieldCode() { return false; }

    @ReservedForInternalUse @Deprecated
    public boolean /*ITocEntry.*/hasBookmark() { return true; }

    @ReservedForInternalUse @Deprecated
    public boolean /*ITocEntry.*/isLinkedStyleTocEntry() { return false; }

    @ReservedForInternalUse @Deprecated
    public int /*ITocEntry.*/getSequenceValue(String sequenceIdentifier)
    {
        throw new IllegalStateException();
    }

    @ReservedForInternalUse @Deprecated
    public int /*ITocEntry.*/getPageNumber()
    {
        throw new IllegalStateException();
    }

    private NodeRange getEntryRange()
    {
        if (!mShowInToc || getTextRange().isEmpty())
            return null;

        getTextRange().isolate();

        return getTextRange();
    }

    private int mLevel;
    /// <summary>
    /// Tells if the entry should be shown in table of contents.
    /// Entries with empty text should not be shown even if containing range is not empty ("", for example).
    /// </summary>
    private boolean mShowInToc;

    static final int TEXT_ARGUMENT_INDEX = 0;

    static final String TYPE_IDENTIFIER_SWITCH = "\\f";
    private static final String ENTRY_LEVEL_SWITCH = "\\l";
    private static final String OMIT_PAGE_NUMBER_SWITCH = "\\n";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\n",
		"\\f",
		"\\l"
	);

}

