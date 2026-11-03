// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the PRINT field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// An instruction to send the printer-specific control code characters to the selected printer
/// when the document is printed.
/// </remarks>
public class FieldPrint extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets the printer-specific control code characters or PostScript instructions.
    /// </summary>
    public String getPrinterInstructions() { return getFieldCodeCache().getArgumentAsString(PRINTER_INSTRUCTIONS_ARGUMENT_INDEX); }
    public void setPrinterInstructions(String value) throws Exception { getFieldCodeCache().setArgument(PRINTER_INSTRUCTIONS_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the drawing rectangle that the PostScript instructions operate on.
    /// </summary>
    public String getPostScriptGroup() { return getFieldCodeCache().getSwitchArgumentAsString(POST_SCRIPT_GROUP_SWITCH); }
    public void setPostScriptGroup(String value) throws Exception { getFieldCodeCache().setSwitch(POST_SCRIPT_GROUP_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*POST_SCRIPT_GROUP_SWITCH*/0:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int PRINTER_INSTRUCTIONS_ARGUMENT_INDEX = 0;

    private static final String POST_SCRIPT_GROUP_SWITCH = "\\p";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\p"
	);

}

