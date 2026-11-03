// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts list enumerated types between enum and DOCX string.
/// </summary>
class DocxNumberingEnum
{
    static /*ListLevelAlignment*/int docxToListLevelAlignment(String value, OoxmlComplianceInfo complianceInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"left"*/0:
                return ListLevelAlignment.LEFT;
            case /*"start"*/1:
                complianceInfo.markAsIsoTransitional();
                return ListLevelAlignment.LEFT;
            case /*"center"*/2:
                return ListLevelAlignment.CENTER;
            case /*"right"*/3:
                return ListLevelAlignment.RIGHT;
            case /*"end"*/4:
                complianceInfo.markAsIsoTransitional();
                return ListLevelAlignment.RIGHT;
            default:
                return ListLevelAlignment.LEFT;
        }
        // TODO 2 Implement remaining ListLevelAlignment members:
        //left
        //center
        //right
        //both
        //mediumKashida
        //distribute
        //listTab
        //highKashida
        //lowKashida
        //thaiDistribute
    }

    static String listLevelAlignmentToDocx(/*ListLevelAlignment*/int value, boolean isIsoStrict)
    {
        switch (value)
        {
            case ListLevelAlignment.LEFT:
                return (isIsoStrict) ? "start" : "left";
            case ListLevelAlignment.CENTER:
                return "center";
            case ListLevelAlignment.RIGHT:
                return (isIsoStrict) ? "end" : "right";
            default: 
                return "";
        }
    }

    static /*ListTrailingCharacter*/int docxToListTrailingCharacter(String value)
    {
        return (/*ListTrailingCharacter*/int)G_LIST_TRAILING_CHARACTER_MAP.getValue(value, (int)ListTrailingCharacter.TAB);
    }

    static String listTrailingCharacterToDocx(/*ListTrailingCharacter*/int value)
    {
        return G_LIST_TRAILING_CHARACTER_MAP.getValue((int)value, "");
    }

    static /*ListType*/int docxToListType(String value)
    {
        // WORDSNET-26739 An incorrect value in the WML file, we need to default to multi-level list to read such files reliably.
        return (/*ListType*/int)G_LIST_TYPE_MAP.getValue(value, (int)ListType.HYBRID_MULTI_LEVEL);
    }

    static String listTypeToDocx(/*ListType*/int value)
    {
        return G_LIST_TYPE_MAP.getValue((int)value, "");
    }

    //JAVA: declarations moved here to exclude java's illegal forward reference.

    private static /*final*/ StringToIntBidirectionalMap G_LIST_TRAILING_CHARACTER_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LIST_TYPE_MAP = new StringToIntBidirectionalMap();
    static/* DocxNumberingEnum()*/
    {

        G_LIST_TYPE_MAP.addEntry("singleLevel", (int)ListType.SINGLE_LEVEL);
        G_LIST_TYPE_MAP.addEntry("multilevel", (int)ListType.MULTI_LEVEL);
        G_LIST_TYPE_MAP.addEntry("hybridMultilevel", (int)ListType.HYBRID_MULTI_LEVEL);

        G_LIST_TRAILING_CHARACTER_MAP.addEntry("tab", (int)ListTrailingCharacter.TAB);
        G_LIST_TRAILING_CHARACTER_MAP.addEntry("space", (int)ListTrailingCharacter.SPACE);
        G_LIST_TRAILING_CHARACTER_MAP.addEntry("nothing", (int)ListTrailingCharacter.NOTHING);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"start",
		"center",
		"right",
		"end"
	);

}

