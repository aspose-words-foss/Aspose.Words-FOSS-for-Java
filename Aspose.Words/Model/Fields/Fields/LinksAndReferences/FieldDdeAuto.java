// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the DDEAUTO field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// For information copied from another application, this field links that information to its original source file using DDE
/// and is updated automatically.
/// </remarks>
public class FieldDdeAuto extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets the application type of the link information.
    /// </summary>
    public String getProgId() { return getFieldCodeCache().getArgumentAsString(PROG_ID_ARGUMENT_INDEX); }
    public void setProgId(String value) throws Exception { getFieldCodeCache().setArgument(PROG_ID_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the name and location of the source file.
    /// </summary>
    public String getSourceFullName() { return getFieldCodeCache().getArgumentAsString(SOURCE_FULL_NAME_ARGUMENT_INDEX); }
    public void setSourceFullName(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_FULL_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the portion of the source file that's being linked.
    /// </summary>
    public String getSourceItem() { return getFieldCodeCache().getArgumentAsString(SOURCE_ITEM_ARGUMENT_INDEX); }
    public void setSourceItem(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_ITEM_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object as a bitmap.
    /// </summary>
    public boolean getInsertAsBitmap() { return getFieldCodeCache().hasSwitch(INSERT_AS_BITMAP_SWITCH); }
    public void setInsertAsBitmap(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_BITMAP_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to reduce the file size by not storing graphics data with the document.
    /// </summary>
    public boolean isLinked() { return getFieldCodeCache().hasSwitch(IS_LINKED_SWITCH); }
    public void isLinked(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_LINKED_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object as HTML format text.
    /// </summary>
    public boolean getInsertAsHtml() { return getFieldCodeCache().hasSwitch(INSERT_AS_HTML_SWITCH); }
    public void setInsertAsHtml(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_HTML_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object as a picture.
    /// </summary>
    public boolean getInsertAsPicture() { return getFieldCodeCache().hasSwitch(INSERT_AS_PICTURE_SWITCH); }
    public void setInsertAsPicture(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_PICTURE_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object in rich-text format (RTF).
    /// </summary>
    public boolean getInsertAsRtf() { return getFieldCodeCache().hasSwitch(INSERT_AS_RTF_SWITCH); }
    public void setInsertAsRtf(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_RTF_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object in text-only format.
    /// </summary>
    public boolean getInsertAsText() { return getFieldCodeCache().hasSwitch(INSERT_AS_TEXT_SWITCH); }
    public void setInsertAsText(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_TEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the linked object as Unicode text.
    /// </summary>
    public boolean getInsertAsUnicode() { return getFieldCodeCache().hasSwitch(INSERT_AS_UNICODE_SWITCH); }
    public void setInsertAsUnicode(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_AS_UNICODE_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INSERT_AS_BITMAP_SWITCH*/0:
            case /*IS_LINKED_SWITCH*/1:
            case /*INSERT_AS_HTML_SWITCH*/2:
            case /*INSERT_AS_PICTURE_SWITCH*/3:
            case /*INSERT_AS_RTF_SWITCH*/4:
            case /*INSERT_AS_TEXT_SWITCH*/5:
            case /*INSERT_AS_UNICODE_SWITCH*/6:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int PROG_ID_ARGUMENT_INDEX = 0;
    private static final int SOURCE_FULL_NAME_ARGUMENT_INDEX = 1;
    private static final int SOURCE_ITEM_ARGUMENT_INDEX = 2;

    private static final String INSERT_AS_BITMAP_SWITCH = "\\b";
    private static final String IS_LINKED_SWITCH = "\\d";
    private static final String INSERT_AS_HTML_SWITCH = "\\h";
    private static final String INSERT_AS_PICTURE_SWITCH = "\\p";
    private static final String INSERT_AS_RTF_SWITCH = "\\r";
    private static final String INSERT_AS_TEXT_SWITCH = "\\t";
    private static final String INSERT_AS_UNICODE_SWITCH = "\\u";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\b",
		"\\d",
		"\\h",
		"\\p",
		"\\r",
		"\\t",
		"\\u"
	);

}

