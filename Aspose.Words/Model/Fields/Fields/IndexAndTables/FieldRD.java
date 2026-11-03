// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the RD field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Identifies a file to include when you create a table of contents, a table of authorities, or an index
/// with the TOC, TOA, or INDEX field
/// </remarks>
public class FieldRD extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets the name of the file to include when generating a table of contents, table of authorities, or index.
    /// </summary>
    public String getFileName() { return getFieldCodeCache().getArgumentAsString(FILE_NAME_ARGUMENT_INDEX); }
    public void setFileName(String value) throws Exception { getFieldCodeCache().setArgument(FILE_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets whether the path is relative to the current document.
    /// </summary>
    public boolean isPathRelative() { return getFieldCodeCache().hasSwitch(IS_PATH_RELATIVE_SWITCH); }
    public void isPathRelative(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_PATH_RELATIVE_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_PATH_RELATIVE_SWITCH*/0:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int FILE_NAME_ARGUMENT_INDEX = 0;

    private static final String IS_PATH_RELATIVE_SWITCH = "\\f";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\f"
	);

}

