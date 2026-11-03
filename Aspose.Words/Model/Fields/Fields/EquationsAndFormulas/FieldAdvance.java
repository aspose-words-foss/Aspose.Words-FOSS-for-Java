// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the ADVANCE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Moves the starting point at which the text that lexically follows the field is displayed to the right or left,
/// up or down, or to a specific horizontal or vertical position.
/// </remarks>
public class FieldAdvance extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved down.
    /// </summary>
    public String getDownOffset() { return getFieldCodeCache().getSwitchArgumentAsString(DOWN_OFFSET_SWITCH); }
    public void setDownOffset(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(DOWN_OFFSET_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved left.
    /// </summary>
    public String getLeftOffset() { return getFieldCodeCache().getSwitchArgumentAsString(LEFT_OFFSET_SWITCH); }
    public void setLeftOffset(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(LEFT_OFFSET_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved right.
    /// </summary>
    public String getRightOffset() { return getFieldCodeCache().getSwitchArgumentAsString(RIGHT_OFFSET_SWITCH); }
    public void setRightOffset(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(RIGHT_OFFSET_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved up.
    /// </summary>
    public String getUpOffset() { return getFieldCodeCache().getSwitchArgumentAsString(UP_OFFSET_SWITCH); }
    public void setUpOffset(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(UP_OFFSET_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved horizontally
    /// from the left edge of the column, frame, or text box.
    /// </summary>
    public String getHorizontalPosition() { return getFieldCodeCache().getSwitchArgumentAsString(HORIZONTAL_POSITION_SWITCH); }
    public void setHorizontalPosition(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(HORIZONTAL_POSITION_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of points by which the text that follows the field should be moved vertically
    /// from the top edge of the page.
    /// </summary>
    public String getVerticalPosition() { return getFieldCodeCache().getSwitchArgumentAsString(VERTICAL_POSITION_SWITCH); }
    public void setVerticalPosition(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(VERTICAL_POSITION_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*DOWN_OFFSET_SWITCH*/0:
            case /*LEFT_OFFSET_SWITCH*/1:
            case /*RIGHT_OFFSET_SWITCH*/2:
            case /*UP_OFFSET_SWITCH*/3:
            case /*HORIZONTAL_POSITION_SWITCH*/4:
            case /*VERTICAL_POSITION_SWITCH*/5:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final String DOWN_OFFSET_SWITCH = "\\d";
    private static final String LEFT_OFFSET_SWITCH = "\\l";
    private static final String RIGHT_OFFSET_SWITCH = "\\r";
    private static final String UP_OFFSET_SWITCH = "\\u";
    private static final String HORIZONTAL_POSITION_SWITCH = "\\x";
    private static final String VERTICAL_POSITION_SWITCH = "\\y";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\d",
		"\\l",
		"\\r",
		"\\u",
		"\\x",
		"\\y"
	);

}

