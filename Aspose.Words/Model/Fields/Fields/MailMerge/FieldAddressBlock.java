// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToObjDictionary;


/// <summary>
/// Implements the ADDRESSBLOCK field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Represents an address block. An <i>address block</i> is a block of text specifying information
/// appropriate for a postal mailing address, in the order required by the destination country.
/// </remarks>
public class FieldAddressBlock extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore()
    {
        return MergeFieldUtil.processUnusedField(this, "«AddressBlock»");
    }

    /// <summary>
    /// Gets or sets whether to format the address according to the country/region of the recipient
    /// as defined by POST*CODE (Universal Postal Union 2006).
    /// </summary>
    public boolean getFormatAddressOnCountryOrRegion() { return getFieldCodeCache().hasSwitch(FORMAT_ADDRESS_ON_COUNTRY_OR_REGION_SWITCH); }
    public void setFormatAddressOnCountryOrRegion(boolean value) throws Exception { getFieldCodeCache().setSwitch(FORMAT_ADDRESS_ON_COUNTRY_OR_REGION_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to include the name of the country/region.
    /// </summary>
    public String getIncludeCountryOrRegionName() { return getFieldCodeCache().getSwitchArgumentAsString(INCLUDE_COUNTRY_OR_REGION_NAME_SWITCH); }
    public void setIncludeCountryOrRegionName(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(INCLUDE_COUNTRY_OR_REGION_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the excluded country/region name.
    /// </summary>
    /// <dev>TODO DV May be more than one.</dev>
    public String getExcludedCountryOrRegionName() { return getFieldCodeCache().getSwitchArgumentAsString(EXCLUDED_COUNTRY_OR_REGION_NAME_SWITCH); }
    public void setExcludedCountryOrRegionName(String value) throws Exception { getFieldCodeCache().setSwitch(EXCLUDED_COUNTRY_OR_REGION_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name and address format.
    /// </summary>
    public String getNameAndAddressFormat() { return getFieldCodeCache().getSwitchArgumentAsString(NAME_AND_ADDRESS_FORMAT_SWITCH); }
    public void setNameAndAddressFormat(String value) throws Exception { getFieldCodeCache().setSwitch(NAME_AND_ADDRESS_FORMAT_SWITCH, value); }

    /// <summary>
    /// Gets or sets the language ID used to format the address.
    /// </summary>
    public String getLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(LANGUAGE_ID_SWITCH); }
    public void setLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(LANGUAGE_ID_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*FORMAT_ADDRESS_ON_COUNTRY_OR_REGION_SWITCH*/0:
            {
                return FieldSwitchType.FLAG;
            }
            case /*INCLUDE_COUNTRY_OR_REGION_NAME_SWITCH*/1:
            case /*EXCLUDED_COUNTRY_OR_REGION_NAME_SWITCH*/2:
            case /*NAME_AND_ADDRESS_FORMAT_SWITCH*/3:
            case /*LANGUAGE_ID_SWITCH*/4:
            {
                return FieldSwitchType.HAS_ARGUMENT;
            }
            default:
            {
                return FieldSwitchType.UNKNOWN;
            }
        }
    }

    private static final String FORMAT_ADDRESS_ON_COUNTRY_OR_REGION_SWITCH = "\\d";
    private static final String INCLUDE_COUNTRY_OR_REGION_NAME_SWITCH = "\\c";
    private static final String EXCLUDED_COUNTRY_OR_REGION_NAME_SWITCH = "\\e";
    private static final String NAME_AND_ADDRESS_FORMAT_SWITCH = "\\f";
    private static final String LANGUAGE_ID_SWITCH = "\\l";

    private static /*final*/ StringToObjDictionary<String> G_PLACEHOLDERS_TO_FIELDS_MAP = new
        StringToObjDictionary<String>(false);

    static/* FieldAddressBlock()*/
    {
        // Found at http://msdn.microsoft.com/en-us/library/ff531301(office.12).aspx.

        G_PLACEHOLDERS_TO_FIELDS_MAP.add("TITLE0", "Courtesy Title");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("NICK0", "Nickname");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("FIRST0", "First Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("MIDDLE0", "Middle Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("LAST0", "Last Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("SUFFIX0", "Suffix");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("TITLE1", "Spouse Courtesy Title");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("NICK1", "Spouse Nickname");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("FIRST1", "Spouse First Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("MIDDLE1", "Spouse Middle Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("LAST1", "Spouse Last Name");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("SUFFIX1", "Spouse Suffix");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("COMPANY", "Company");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("STREET1", "Address 1");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("STREET2", "Address 2");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("CITY", "City");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("STATE", "State");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("POSTAL", "Postal Code");
        G_PLACEHOLDERS_TO_FIELDS_MAP.add("COUNTRY", "Country or Region");
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\d",
		"\\c",
		"\\e",
		"\\f",
		"\\l"
	);

}

