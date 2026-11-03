// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/07/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


class NrxTableEnum
{
    static /*TableAlignment*/int xmlToTableAlignment(String value, OoxmlComplianceInfo cInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"left"*/0: return TableAlignment.LEFT;
            case /*"start"*/1:
                {
                    cInfo.markAsIsoTransitional();
                    return TableAlignment.LEFT; // iso29500
                }
            case /*"center"*/2: return TableAlignment.CENTER;
            case /*"right"*/3: return TableAlignment.RIGHT;
            case /*"end"*/4:
                {
                    cInfo.markAsIsoTransitional();
                    return TableAlignment.RIGHT; // iso29500
                }
            default: return TableAlignment.LEFT;
            // RK These are not supported (have not seen) for tables.
            //    both
            //    medium-kashida
            //    mediumKashida
            //    distribute
            //    list-tab
            //    listTab
            //    high-kashida
            //    highKashida
            //    low-kashida
            //    lowKashida
            //    thai-distribute
            //    thaiDistribute
        }
    }

    static String tableAlignmentToXml(/*TableAlignment*/int value, boolean isDocx, /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = isDocx && (compliance == OoxmlComplianceCore.ISO_STRICT);

        switch (value)
        {
            case TableAlignment.LEFT: return (isIsoStrict) ? "start" : "left";
            case TableAlignment.CENTER: return "center";
            case TableAlignment.RIGHT: return (isIsoStrict) ? "end" : "right";
            default: return "";
        }
    }

    static /*HeightRule*/int xmlToHeightRule(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"auto"*/5: return HeightRule.AUTO;
            case /*"exact"*/6: return HeightRule.EXACTLY;
            case /*"atLeast"*/7: 
            case /*"at-least"*/8:
                return HeightRule.AT_LEAST;
            default:
                return HeightRule.AUTO;
        }
    }

    static String heightRuleToXml(/*HeightRule*/int value, boolean isDocx)
    {
        switch (value)
        {
            case HeightRule.AUTO: return "auto";
            case HeightRule.EXACTLY: return "exact";
            case HeightRule.AT_LEAST: return (isDocx) ? "atLeast" : "at-least";
            default: return "";
        }
    }

    static /*CellMerge*/int xmlToCellMerge(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"continue"*/9: return CellMerge.PREVIOUS;
            case /*"restart"*/10: return CellMerge.FIRST;
            default: return CellMerge.NONE;
        }
    }

    static String cellMergeToXml(/*CellMerge*/int value)
    {
        switch (value)
        {
            case CellMerge.PREVIOUS: return "continue";
            case CellMerge.FIRST: return "restart";
            default: return "";
        }
    }

    static /*CellVerticalAlignment*/int xmlToCellVerticalAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"top"*/11: return CellVerticalAlignment.TOP;
            case /*"center"*/2: return CellVerticalAlignment.CENTER;
            case /*"bottom"*/12: return CellVerticalAlignment.BOTTOM;
            default: return CellVerticalAlignment.TOP;
        }
    }

    static String cellVerticalAlignmentToXml(/*CellVerticalAlignment*/int value)
    {
        switch (value)
        {
            case CellVerticalAlignment.TOP: return "top";
            case CellVerticalAlignment.CENTER: return "center";
            case CellVerticalAlignment.BOTTOM: return "bottom";
            default: return "";
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"start",
		"center",
		"right",
		"end",
		"auto",
		"exact",
		"atLeast",
		"at-least",
		"continue",
		"restart",
		"top",
		"bottom"
	);

}

