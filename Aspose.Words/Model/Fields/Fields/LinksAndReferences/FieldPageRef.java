// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Implements the PAGEREF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the number of the page containing the specified bookmark for a cross-reference.
/// </remarks>
public class FieldPageRef extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        if (!getBookmarkNameHasChars())
            return new FieldUpdateActionInsertErrorMessage(this, "Error! No bookmark name given.");

        Constant value = getUpdater().getDataProviders().getValue(this);
        if (value == null)
        {
            return getBookmarkNode() == null
                ? new FieldUpdateActionInsertErrorMessage(this, "Error! Bookmark not defined.")
                : null;
        }

        if (getInsertRelativePosition())
        {
            // Specifying some number format makes the field ignore relative position.
            boolean isFormatSpecified = (getFormat().getGeneralFormats().getNumericFormat() != GeneralFormat.NONE) || getFieldCodeCache().hasSwitch("\\#");
            if (!isFormatSpecified)
                return new FieldUpdateActionApplyResult(this, getRelativePosition((Int32Constant)value));
        }

        return new FieldUpdateActionApplyResult(this, value);
    }

    private String getRelativePosition(Int32Constant bookmarkPage)
    {
        int bookmarkPageValue = (int)bookmarkPage.getValueDouble();

        // FOSS: the relative position ("above"/"below") requires the layout engine to know on which page
        // this field is rendered relative to the bookmark. Without layout, fall back to the absolute
        // "on page N" wording, consistent with the unavailable-relative-position path above.
        return getOnPageNString(bookmarkPageValue);
    }

    private static String getOnPageNString(int pageNumber)
    {
        return msString.format("on page {0}", pageNumber);
    }

    /*override*/ Section getPageNumberFormatSection() throws Exception
    {
        return (Section)((getBookmarkNode() != null) ? getBookmarkNode().getAncestor(NodeType.SECTION) : null);
    }

    Node getChapterTitleNode() throws Exception
    {
        Bookmark bookmark = FieldUtil.getCachedBookmark(this, getBookmarkName());

        BookmarkStart bookmarkStart = bookmark.getBookmarkStart();
        BookmarkEnd bookmarkEnd = bookmark.getBookmarkEnd();

        if (bookmarkStart.getParentNode() == bookmarkEnd.getParentNode())
            return bookmarkStart;

        for (Node node : bookmark.getNodeRange())
        {
            if (node instanceof Inline)
                return node;
        }

        return bookmarkStart;
    }

    private boolean getBookmarkNameHasChars() { return StringUtil.hasChars(getBookmarkName()); }

    Node getBookmarkNode() throws Exception
    {
        if ((mBookmarkNode == null) || (mBookmarkNode.getParentNode() == null))
        {
            if (!getBookmarkNameHasChars())
                return null;

            // SPEED Get a bookmark from a cache.
            Bookmark bookmark = FieldUtil.getCachedBookmark(this, getBookmarkName());
            if (bookmark == null)
                return null;

            mBookmarkNode = bookmark.getBookmarkStart();
        }

        return mBookmarkNode;
    }
    void setBookmarkNode(Node value) { mBookmarkNode = value; }

    /// <summary>
    /// Gets or sets the name of the bookmark.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_ARGUMENT_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets whether to insert a hyperlink to the bookmarked paragraph.
    /// </summary>
    public boolean getInsertHyperlink() { return getFieldCodeCache().hasSwitch(INSERT_HYPERLINK_SWITCH); }
    public void setInsertHyperlink(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_HYPERLINK_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert a relative position of the bookmarked paragraph.
    /// </summary>
    public boolean getInsertRelativePosition() { return getFieldCodeCache().hasSwitch(INSERT_RELATIVE_POSITION_SWITCH); }
    public void setInsertRelativePosition(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_RELATIVE_POSITION_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INSERT_HYPERLINK_SWITCH*/0:
            case /*INSERT_RELATIVE_POSITION_SWITCH*/1:
            {
                return FieldSwitchType.FLAG;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private Node mBookmarkNode;

    private static final int BOOKMARK_NAME_ARGUMENT_INDEX = 0;

    static final String INSERT_HYPERLINK_SWITCH = "\\h";
    private static final String INSERT_RELATIVE_POSITION_SWITCH = "\\p";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\h",
		"\\p"
	);

}

