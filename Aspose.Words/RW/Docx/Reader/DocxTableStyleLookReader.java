// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.BitUtil;


/// <summary>
/// Knows how to read/write new ISO29500 style <see cref="TableStyleOptions"/> element.
/// </summary>
class DocxTableStyleLookReader
{
    /// <summary>
    /// Reads w:tblLook elements and returns <see cref="TableStyleOptions"/>.
    /// </summary>
    static /*TableStyleOptions*/int readTableStyleLook(NrxXmlReader xmlReader, 
        OoxmlComplianceInfo complianceInfo)
    {
        /*TableStyleOptions*/int result = TableStyleOptions.DEFAULT;
        String initialVal = xmlReader.readVal();
        if (StringUtil.hasChars(initialVal))
            result = NrxTableUtil.nrxToTableStyleOptions(NrxXmlUtil.hexToInt(initialVal));

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"firstRow"*/0:
                    complianceInfo.markAsIsoTransitional();
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.FIRST_ROW, xmlReader.getValueAsBool());
                    break;
                case /*"firstColumn"*/1:
                    complianceInfo.markAsIsoTransitional();
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.FIRST_COLUMN, xmlReader.getValueAsBool());
                    break;
                case /*"lastColumn"*/2:
                    complianceInfo.markAsIsoTransitional();
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.LAST_COLUMN, xmlReader.getValueAsBool());
                    break;
                case /*"lastRow"*/3:
                    complianceInfo.markAsIsoTransitional();
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.LAST_ROW, xmlReader.getValueAsBool());
                    break;
                case /*"noHBand"*/4:
                    complianceInfo.markAsIsoTransitional();
                    // In the model this is inverted.
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.ROW_BANDS, !xmlReader.getValueAsBool());
                    break;
                case /*"noVBand"*/5:
                    complianceInfo.markAsIsoTransitional();
                    // In the model this is inverted.
                    result = (/*TableStyleOptions*/int)BitUtil.setBit((int)result, (int)TableStyleOptions.COLUMN_BANDS, !xmlReader.getValueAsBool());
                    break;
                default:
                    continue;
            }
        }
        
        return result;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"firstRow",
		"firstColumn",
		"lastColumn",
		"lastRow",
		"noHBand",
		"noVBand"
	);

}

