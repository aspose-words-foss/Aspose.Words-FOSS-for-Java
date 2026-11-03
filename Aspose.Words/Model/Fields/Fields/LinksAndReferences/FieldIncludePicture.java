// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2012 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements the INCLUDEPICTURE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves a picture and displays it as the field result.
/// </remarks>
public class FieldIncludePicture extends Field implements IFieldCodeTokenInfoProvider, IFieldIncludePictureCode
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return FieldIncludePictureUpdater.update(this);
    }

    /*override*/ void beforeUnlink() throws Exception
    {
        NodeRange range = getFieldResultRange();
        for (Node node : range)
        {
            Shape shape = ms.as(node, Shape.class);
            if (shape != null)
                shape.getImageData().setSourceFullName("");
        }
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*GRAPHIC_FILTER_SWITCH*/0:
                return FieldSwitchType.HAS_ARGUMENT;
            case /*IS_LINKED_SWITCH*/1:
            case /*RESIZE_HORIZONTALLY_SWITCH*/2:
            case /*RESIZE_VERTICALLY_SWITCH*/3:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets or sets the location of the picture using an IRI.
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

    /// <summary>
    /// Gets or sets whether to resize the picture horizontally from the source.
    /// </summary>
    public boolean getResizeHorizontally() { return getFieldCodeCache().hasSwitch(RESIZE_HORIZONTALLY_SWITCH); }
    public void setResizeHorizontally(boolean value) throws Exception { getFieldCodeCache().setSwitch(RESIZE_HORIZONTALLY_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to resize the picture vertically from the source.
    /// </summary>
    public boolean getResizeVertically() { return getFieldCodeCache().hasSwitch(RESIZE_VERTICALLY_SWITCH); }
    public void setResizeVertically(boolean value) throws Exception { getFieldCodeCache().setSwitch(RESIZE_VERTICALLY_SWITCH, value); }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludePictureCode.SourceFullName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //boolean IFieldIncludePictureCode.IsLinked

    private static final int SOURCE_FULL_NAME_ARGUMENT_INDEX = 0;

    private static final String GRAPHIC_FILTER_SWITCH = "\\c";
    private static final String IS_LINKED_SWITCH = "\\d";
    private static final String RESIZE_HORIZONTALLY_SWITCH = "\\x";
    private static final String RESIZE_VERTICALLY_SWITCH = "\\y";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\c",
		"\\d",
		"\\x",
		"\\y"
	);

}

