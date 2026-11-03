// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the TA field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Defines the text and page number for a table of authorities entry, which is used by a TOA field.
/// </remarks>
public class FieldTA extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets whether to apply bold formatting to the page number for the entry.
    /// </summary>
    public boolean isBold() { return getFieldCodeCache().hasSwitch(IS_BOLD_SWITCH); }
    public void isBold(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_BOLD_SWITCH, value); }

    /// <summary>
    /// Gets or sets the integral entry category, which is a number that corresponds to the order of
    /// categories.
    /// </summary>
    public String getEntryCategory() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_CATEGORY_SWITCH); }
    public void setEntryCategory(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(ENTRY_CATEGORY_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to apply italic formatting to the page number for the entry.
    /// </summary>
    public boolean isItalic() { return getFieldCodeCache().hasSwitch(IS_ITALIC_SWITCH); }
    public void isItalic(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_ITALIC_SWITCH, value); }

    /// <summary>
    /// Gets or sets the long citation for the entry.
    /// </summary>
    public String getLongCitation() { return getFieldCodeCache().getSwitchArgumentAsString(LONG_CITATION_SWITCH); }
    public void setLongCitation(String value) throws Exception { getFieldCodeCache().setSwitch(LONG_CITATION_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the bookmark that marks a range of pages that is inserted as the entry's page number.
    /// </summary>
    public String getPageRangeBookmarkName() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_RANGE_BOOKMARK_NAME_SWITCH); }
    public void setPageRangeBookmarkName(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_RANGE_BOOKMARK_NAME_SWITCH, value); }

    boolean hasPageRangeBookmarkNameSwitch() { return getFieldCodeCache().hasSwitch(PAGE_RANGE_BOOKMARK_NAME_SWITCH); }

    /// <summary>
    /// Gets or sets the short citation for the entry.
    /// </summary>
    public String getShortCitation() { return getFieldCodeCache().getSwitchArgumentAsString(SHORT_CITATION_SWITCH); }
    public void setShortCitation(String value) throws Exception { getFieldCodeCache().setSwitch(SHORT_CITATION_SWITCH, value); }

    NodeRange getLongCitationRange() { return getFieldCodeCache().getSwitchArgumentRange(LONG_CITATION_SWITCH); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_BOLD_SWITCH*/0:
            case /*IS_ITALIC_SWITCH*/1:
            {
                return FieldSwitchType.FLAG;
            }
            case /*ENTRY_CATEGORY_SWITCH*/2:
            case /*LONG_CITATION_SWITCH*/3:
            case /*PAGE_RANGE_BOOKMARK_NAME_SWITCH*/4:
            case /*SHORT_CITATION_SWITCH*/5:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final String IS_BOLD_SWITCH = "\\b";
    private static final String ENTRY_CATEGORY_SWITCH = "\\c";
    private static final String IS_ITALIC_SWITCH = "\\i";
    static final String LONG_CITATION_SWITCH = "\\l";
    private static final String PAGE_RANGE_BOOKMARK_NAME_SWITCH = "\\r";
    private static final String SHORT_CITATION_SWITCH = "\\s";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\b",
		"\\i",
		"\\c",
		"\\l",
		"\\r",
		"\\s"
	);

}

