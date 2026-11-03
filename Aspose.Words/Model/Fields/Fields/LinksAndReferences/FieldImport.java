// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements the IMPORT field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the picture contained in the document.
/// </remarks>
public class FieldImport extends Field implements IFieldCodeTokenInfoProvider, IFieldIncludePictureCode
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return FieldIncludePictureUpdater.update(this);
    }

    /// <summary>
    /// Gets or sets the location of the picture.
    /// </summary>
    public String getSourceFullName() { return getFieldCodeCache().getArgumentAsString(SOURCE_FULL_NAME_ARGUMENT_INDEX); }
    public void setSourceFullName(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_FULL_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the name of the filter for the format of the graphic that is to be inserted.
    /// </summary>
    public String getGraphicFilter() { return getFieldCodeCache().getSwitchArgumentAsString(GRAPHIC_FILTER_SWITCH); }
    public void setGraphicFilter(String value) throws Exception { getFieldCodeCache().setSwitch(GRAPHIC_FILTER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to reduce the file size by not storing graphics data with the document.
    /// </summary>
    public boolean isLinked() { return getFieldCodeCache().hasSwitch(IS_LINKED_SWITCH); }
    public void isLinked(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_LINKED_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*GRAPHIC_FILTER_SWITCH*/0:
                return FieldSwitchType.HAS_ARGUMENT;
            case /*IS_LINKED_SWITCH*/1:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludePictureCode.SourceFullName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //boolean IFieldIncludePictureCode.IsLinked

    private static final int SOURCE_FULL_NAME_ARGUMENT_INDEX = 0;

    private static final String GRAPHIC_FILTER_SWITCH = "\\c";
    private static final String IS_LINKED_SWITCH = "\\d";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\c",
		"\\d"
	);

}

