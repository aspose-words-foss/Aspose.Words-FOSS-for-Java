// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.NullableInt32;


/// <summary>
/// Implements the SEQ field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Sequentially numbers chapters, tables, figures, and other user-defined lists of items in a document.
/// </remarks>
public class FieldSeq extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        if (isOutOfMainTextAndNotValid())
            return new FieldUpdateActionInsertErrorMessage(this, "Error! Main Document Only.");

        if (isSequenceIdentifierMissing())
            return new FieldUpdateActionInsertErrorMessage(this, "Error! No sequence specified.");

        if (isBookmarkMissing(getUpdater().getBookmarkCache()))
            return new FieldUpdateActionInsertErrorMessage(this, Bookmark.ERROR_BOOKMARK_NOT_DEFINED);

        // All numeric formatting switches prevents field result from being hidden.
        if (getHideFieldResult() && getFormat().getGeneralFormats().getNumericFormat() == GeneralFormat.NONE)
            return new FieldUpdateActionApplyResult(this, "");

        getUpdater().getDataProviders().ensure(new FieldSeqDataProvider(getUpdater()));

        Constant value = getUpdater().getDataProviders().getValue(this);

        return new FieldUpdateActionApplyResult(this, value);
    }

    boolean isError(BookmarkCache bookmarkCache) throws Exception
    {
        return isOutOfMainTextAndNotValid() || isSequenceIdentifierMissing() || isBookmarkMissing(bookmarkCache);
    }

    private boolean isBookmarkMissing(BookmarkCache bookmarkCache) throws Exception
    {
        // SPEED Get a bookmark from a cache.
        return hasBookmarkName() && (bookmarkCache.get(getBookmarkName()) == null);
    }

    private boolean isOutOfMainTextAndNotValid()
    {
        // According to Word's behaviour and MS MVP's statements on the internet, a SEQ located outside
        // of the main document only works if the \c switch is present.
        return (getStart().getAncestor(NodeType.BODY) == null) && !getInsertClosestPrecedingNumber();
    }

    private boolean isSequenceIdentifierMissing() { return !StringUtil.hasChars(getSequenceIdentifier()); }

    /// <summary>
    /// Gets or sets the name assigned to the series of items that are to be numbered.
    /// </summary>
    public String getSequenceIdentifier() { return getFieldCodeCache().getArgumentAsString(SEQUENCE_IDENTIFIER_ARGUMENT_INDEX); }
    public void setSequenceIdentifier(String value) throws Exception { getFieldCodeCache().setArgument(SEQUENCE_IDENTIFIER_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets a bookmark name that refers to an item elsewhere in the document rather than in the current location.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_ARGUMENT_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_ARGUMENT_INDEX, value); }

    boolean hasBookmarkName() { return StringUtil.hasChars(getBookmarkName()); }

    /// <summary>
    /// Gets or sets whether to insert the closest preceding sequence number.
    /// </summary>
    /// <remarks>
    /// This is useful for inserting chapter numbers in headers or footers.
    /// </remarks>
    boolean getInsertClosestPrecedingNumber() { return getFieldCodeCache().hasSwitch(INSERT_CLOSEST_PRECEDING_NUMBER_SWITCH); }
    void setInsertClosestPrecedingNumber(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_CLOSEST_PRECEDING_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to hide field result unless a general formatting switch is also present.
    /// </summary>
    /// <remarks>
    /// This switch can be used to refer to a SEQ field in a crossreference without printing the number.
    /// </remarks>
    boolean getHideFieldResult() { return getFieldCodeCache().hasSwitch(HIDE_FIELD_RESULT_SWITCH); }
    void setHideFieldResult(boolean value) throws Exception { getFieldCodeCache().setSwitch(HIDE_FIELD_RESULT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the next sequence number for the specified item.
    /// </summary>
    public boolean getInsertNextNumber() { return getFieldCodeCache().hasSwitch(INSERT_NEXT_NUMBER_SWITCH); }
    public void setInsertNextNumber(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_NEXT_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets an integer number to reset the sequence number to. Returns -1 if the number is absent.
    /// </summary>
    public String getResetNumber() { return getFieldCodeCache().getSwitchArgumentAsString(RESET_NUMBER_SWITCH); }
    public void setResetNumber(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(RESET_NUMBER_SWITCH, value); }

    boolean hasResetNumberSwitch() { return getFieldCodeCache().hasSwitch(RESET_NUMBER_SWITCH); }

    NullableInt32 getResetNumberAsInt32() { return getFieldCodeCache().getSwitchArgumentAsInt32(RESET_NUMBER_SWITCH); }

    /// <summary>
    /// Gets or sets an integer number representing a heading level to reset the sequence number to.
    /// Returns -1 if the number is absent.
    /// </summary>
    public String getResetHeadingLevel() { return getFieldCodeCache().getSwitchArgumentAsString(RESET_HEADING_LEVEL_SWITCH); }
    public void setResetHeadingLevel(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(RESET_HEADING_LEVEL_SWITCH, value); }

    boolean hasResetHeadingLevelSwitch() { return getFieldCodeCache().hasSwitch(RESET_HEADING_LEVEL_SWITCH); }

    NullableInt32 getResetHeadingLevelAsInt32() { return getFieldCodeCache().getSwitchArgumentAsInt32(RESET_HEADING_LEVEL_SWITCH); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INSERT_CLOSEST_PRECEDING_NUMBER_SWITCH*/0:
            case /*HIDE_FIELD_RESULT_SWITCH*/1:
            case /*INSERT_NEXT_NUMBER_SWITCH*/2:
            {
                return FieldSwitchType.FLAG;
            }
            case /*RESET_NUMBER_SWITCH*/3:
            case /*RESET_HEADING_LEVEL_SWITCH*/4:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final int SEQUENCE_IDENTIFIER_ARGUMENT_INDEX = 0;
    private static final int BOOKMARK_NAME_ARGUMENT_INDEX = 1;

    private static final String INSERT_CLOSEST_PRECEDING_NUMBER_SWITCH = "\\c";
    private static final String HIDE_FIELD_RESULT_SWITCH = "\\h";
    private static final String INSERT_NEXT_NUMBER_SWITCH = "\\n";
    private static final String RESET_NUMBER_SWITCH = "\\r";
    private static final String RESET_HEADING_LEVEL_SWITCH = "\\s";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\c",
		"\\h",
		"\\n",
		"\\r",
		"\\s"
	);

}

