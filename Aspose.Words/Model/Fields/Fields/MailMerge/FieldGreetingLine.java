// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Implements the GREETINGLINE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts a mail merge greeting line.
/// </remarks>
public class FieldGreetingLine extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return MergeFieldUtil.processUnusedField(this, "«GreetingLine»");
    }

    /// <summary>
    /// Gets or sets the text to include in the field if the name is blank.
    /// </summary>
    public String getAlternateText() { return getFieldCodeCache().getSwitchArgumentAsString(ALTERNATE_TEXT_SWITCH); }
    public void setAlternateText(String value) throws Exception { getFieldCodeCache().setSwitch(ALTERNATE_TEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets the format of the name included in the field.
    /// </summary>
    public String getNameFormat() { return getFieldCodeCache().getSwitchArgumentAsString(NAME_FORMAT_SWITCH); }
    public void setNameFormat(String value) throws Exception { getFieldCodeCache().setSwitch(NAME_FORMAT_SWITCH, value); }

    /// <summary>
    /// Gets or sets the language id used to format the name.
    /// </summary>
    public String getLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(LANGUAGE_ID_SWITCH); }
    public void setLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(LANGUAGE_ID_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*ALTERNATE_TEXT_SWITCH*/0:
            case /*NAME_FORMAT_SWITCH*/1:
            case /*LANGUAGE_ID_SWITCH*/2:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static /*final*/ StringToObjDictionary<String> G_PLACEHOLDERS_TO_FIELDS_MAP =
        new StringToObjDictionary<String>(false);

    private static /*final*/ String[] G_VALID_FORMATS =
    {
        "<<_TITLE0_>><<_LAST0_>>",
        "<<_TITLE0_>><<and _TITLE1_>><<_LAST0_>>",
        "<<_TITLE0_>><<_FIRST0_>><<_LAST0_>><<_SUFFIX0_>>",
        "<<_TITLE0_>><<_NICK0_>><<_LAST0_>><<_SUFFIX0_>>",
        "<<_FIRST0_>><<_LAST0_>><<_SUFFIX0_>>",
        "<<_NICK0_>><<_LAST0_>><<_SUFFIX0_>>",
        "<<_FIRST0_>><<and _FIRST1_>><<_LAST0_>>",
        "<<_NICK0_>><<and _FIRST1_>><<_LAST0_>>",
        "<<_FIRST0_>>",
        "<<_NICK0_>>",
        "<<_FIRST0_>><<and _FIRST1_>>",
        "<<_NICK0_>><<and _FIRST1_>>",
        "<<Mr. and Mrs. _LAST0_>>"
    };

    private static final String ALTERNATE_TEXT_SWITCH = "\\e";
    private static final String NAME_FORMAT_SWITCH = "\\f";
    private static final String LANGUAGE_ID_SWITCH = "\\l";

    static/* FieldGreetingLine()*/
    {
        // Found at http://msdn.microsoft.com/en-us/library/ff532312(v=office.12).aspx.

        G_PLACEHOLDERS_TO_FIELDS_MAP.add("TITLE0", "Courtesy Title");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("NICK0", "Nickname");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("FIRST0", "First Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("LAST0", "Last Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("SUFFIX0", "Suffix");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("TITLE1", "Spouse Courtesy Title");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("NICK1", "Spouse Nickname");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("FIRST1", "Spouse First Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("LAST1", "Spouse Last Name");
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\e",
		"\\f",
		"\\l"
	);

}

