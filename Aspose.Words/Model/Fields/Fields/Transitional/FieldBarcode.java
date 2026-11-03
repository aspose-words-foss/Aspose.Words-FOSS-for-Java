// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;

/// <summary>
/// Implements the BARCODE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts a postal barcode in a machine-readable form of address used by the U.S. Postal Service.
/// </remarks>
public class FieldBarcode extends Field implements IFieldCodeTokenInfoProvider
{
    private BarcodeParameters getBarcodeParameters()
    {
        BarcodeParameters parameters = new BarcodeParameters();
        parameters.setPostalAddress(getPostalAddress());
        parameters.isBookmark(isBookmark());
        parameters.setFacingIdentificationMark(getFacingIdentificationMark());
        parameters.isUSPostalAddress(isUSPostalAddress());
        return parameters;
    }

    /*override*/ NodeRange getFakeResult() throws Exception
    {
        return FieldBarcodeUtil.getFakeResult(getType(), fetchDocument(), getBarcodeParameters());
    }

    /// <summary>
    /// Gets or sets the postal address used for generating a barcode or the name of the bookmark that refers to it.
    /// </summary>
    public String getPostalAddress() { return getFieldCodeCache().getArgumentAsString(POSTAL_ADDRESS_ARGUMENT_INDEX); }
    public void setPostalAddress(String value) throws Exception { getFieldCodeCache().setArgument(POSTAL_ADDRESS_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets whether <see cref="PostalAddress"/> is the name of a bookmark.
    /// </summary>
    public boolean isBookmark() { return getFieldCodeCache().hasSwitch(IS_BOOKMARK_SWITCH); }
    public void isBookmark(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_BOOKMARK_SWITCH, value); }

    /// <summary>
    /// Gets or sets the type of a Facing Identification Mark (FIM) to insert.
    /// </summary>
    public String getFacingIdentificationMark() { return getFieldCodeCache().getSwitchArgumentAsString(FACING_IDENTIFICATION_MARK_SWITCH); }
    public void setFacingIdentificationMark(String value) throws Exception { getFieldCodeCache().setSwitch(FACING_IDENTIFICATION_MARK_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether <see cref="PostalAddress"/> is a U.S. postal address.
    /// </summary>
    public boolean isUSPostalAddress() { return getFieldCodeCache().hasSwitch(IS_US_POSTAL_ADDRESS_SWITCH); }
    public void isUSPostalAddress(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_US_POSTAL_ADDRESS_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_BOOKMARK_SWITCH*/0:
            case /*IS_US_POSTAL_ADDRESS_SWITCH*/1:
                return FieldSwitchType.FLAG;
            case /*FACING_IDENTIFICATION_MARK_SWITCH*/2:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int POSTAL_ADDRESS_ARGUMENT_INDEX = 0;

    private static final String IS_BOOKMARK_SWITCH = "\\b";
    private static final String FACING_IDENTIFICATION_MARK_SWITCH = "\\f";
    private static final String IS_US_POSTAL_ADDRESS_SWITCH = "\\u";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\b",
		"\\u",
		"\\f"
	);

}

