// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the AUTONUM field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts an automatic number.
/// </remarks>
public class FieldAutoNum extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ NodeRange getFakeResult() throws Exception
    {
        return FieldNumUtil.getFakeResultNodeRange(this);
    }

    /// <summary>
    /// Gets or sets the separator character to be used.
    /// </summary>
    public String getSeparatorCharacter() { return getFieldCodeCache().getSwitchArgumentAsString(SEPARATOR_CHARACTER_SWITCH); }
    public void setSeparatorCharacter(String value) throws Exception { getFieldCodeCache().setSwitch(SEPARATOR_CHARACTER_SWITCH, value); }

    /// <summary>
    /// Gets a separator character by the separator string provided by the field's code.
    /// </summary>
    char getSeparatorCharacterCore() { return FieldNumUtil.getSeparatorCharacterCore(getSeparatorCharacter()); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*SEPARATOR_CHARACTER_SWITCH*/0:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final String SEPARATOR_CHARACTER_SWITCH = "\\s";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\s"
	);

}

