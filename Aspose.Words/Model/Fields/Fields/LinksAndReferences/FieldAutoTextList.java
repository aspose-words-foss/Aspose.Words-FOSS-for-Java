// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the AUTOTEXTLIST field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Creates a shortcut menu based on AutoText entries in the active template.
/// </remarks>
public class FieldAutoTextList extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets the name of the AutoText entry.
    /// </summary>
    public String getEntryName() { return getFieldCodeCache().getArgumentAsString(ENTRY_NAME_ARGUMENT_INDEX); }
    public void setEntryName(String value) throws Exception { getFieldCodeCache().setArgument(ENTRY_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the name of the style on which the list to contain entries is based.
    /// </summary>
    public String getListStyle() { return getFieldCodeCache().getSwitchArgumentAsString(LIST_STYLE_SWITCH); }
    public void setListStyle(String value) throws Exception { getFieldCodeCache().setSwitch(LIST_STYLE_SWITCH, value); }

    /// <summary>
    /// Gets or sets the text of the ScreenTip to show.
    /// </summary>
    public String getScreenTip() { return getFieldCodeCache().getSwitchArgumentAsString(SCREEN_TIP_SWITCH); }
    public void setScreenTip(String value) throws Exception { getFieldCodeCache().setSwitch(SCREEN_TIP_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*LIST_STYLE_SWITCH*/0:
            case /*SCREEN_TIP_SWITCH*/1:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int ENTRY_NAME_ARGUMENT_INDEX = 0;

    private static final String LIST_STYLE_SWITCH = "\\s";
    private static final String SCREEN_TIP_SWITCH = "\\t";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\s",
		"\\t"
	);

}

