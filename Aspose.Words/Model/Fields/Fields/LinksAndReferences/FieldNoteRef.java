// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2005 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import java.util.ArrayList;


/// <summary>
/// Implements the NOTEREF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts the mark of the footnote or endnote that is marked by the specified bookmark.
/// </remarks>
public class FieldNoteRef extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        if (!StringUtil.hasChars(getBookmarkName()))
            return new FieldUpdateActionInsertErrorMessage(this, NO_BOOKMARK_NAME_GIVEN_ERROR_MESSAGE);

        Bookmark bookmark = FieldUtil.getCachedBookmark(this, getBookmarkName());
        if (bookmark == null)
            return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR_MESSAGE);

        ArrayList<Node> footnotes = NodeFinder.findNodes(bookmark.getNodeRange(), NodeType.FOOTNOTE);
        if (footnotes.size() == 0)
            return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR_MESSAGE);

        Footnote footnote = (Footnote)footnotes.get(0);

        NodeRange nodeRange = getResultRange(bookmark, footnote);
        if (nodeRange == null)
            return new FieldUpdateActionInsertErrorMessage(this, BOOKMARK_NOT_DEFINED_ERROR_MESSAGE);

        return new FieldUpdateActionApplyResult(this, new NodeRangeFieldResult(nodeRange));
    }

    /// <summary>
    /// Gets or sets the name of the bookmark.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_ARGUMENT_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Inserts the reference mark with the same character formatting as the Footnote Reference
    /// or Endnote Reference style.
    /// </summary>
    public boolean getInsertReferenceMark() { return getFieldCodeCache().hasSwitch(INSERT_REFERENCE_MARK_SWITCH); }
    public void setInsertReferenceMark(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_REFERENCE_MARK_SWITCH, value); }

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
            case /*INSERT_REFERENCE_MARK_SWITCH*/0:
            case /*INSERT_HYPERLINK_SWITCH*/1:
            case /*INSERT_RELATIVE_POSITION_SWITCH*/2:
            {
                return FieldSwitchType.FLAG;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private NodeRange getResultRange(Bookmark bookmark, Footnote footnote)
    {
        Paragraph para = new Paragraph(getDocument());

        Run start = getReferenceMarkRun(footnote);
        if (start == null)
            return null;

        para.appendChild(start);

        Run end = getRelativePositionRun(bookmark);
        if (end != null)
            para.appendChild(end);
        else
            end = start;

        return new NodeRange(start, end);
    }

    private Run getReferenceMarkRun(Footnote footnote)
    {
        // FOSS
        return null;
    }

    private Run getRelativePositionRun(Bookmark bookmark)
    {
        if (getInsertRelativePosition() && !isInHeaderFooter())
        {
            String position = " " + FieldRefUtil.getRelativeBookmarkPosition(this, bookmark);
            return new Run(getDocument(), position);
        }

        return null;
    }

    private static final int BOOKMARK_NAME_ARGUMENT_INDEX = 0;

    private static final String INSERT_REFERENCE_MARK_SWITCH = "\\f";
    private static final String INSERT_HYPERLINK_SWITCH = "\\h";
    private static final String INSERT_RELATIVE_POSITION_SWITCH = "\\p";

    private static final String NO_BOOKMARK_NAME_GIVEN_ERROR_MESSAGE = "Error! No bookmark name given.";
    private static final String BOOKMARK_NOT_DEFINED_ERROR_MESSAGE = "Error! Bookmark not defined.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\f",
		"\\h",
		"\\p"
	);

}

