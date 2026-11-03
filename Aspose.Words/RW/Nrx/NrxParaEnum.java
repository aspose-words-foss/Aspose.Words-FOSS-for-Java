// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/*static*/ class NrxParaEnum
{
	/* Simulation of static class by using private constructor */
	private NrxParaEnum()
	{}

    static /*TextboxTightWrap*/int xmlToTextboxTightWrap(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"allLines"*/0: return TextboxTightWrap.ALL_LINES;
            case /*"firstAndLastLine"*/1: return TextboxTightWrap.FIRST_AND_LAST_LINE;
            case /*"firstLineOnly"*/2: return TextboxTightWrap.FIRST_LINE_ONLY;
            case /*"lastLineOnly"*/3: return TextboxTightWrap.LAST_LINE_ONLY;
            case /*"none"*/4: return TextboxTightWrap.NONE;
            default: return TextboxTightWrap.DEFAULT;
        }
    }

    static String textboxTightWrapToXml(/*TextboxTightWrap*/int value)
    {
        switch (value)
        {
            case TextboxTightWrap.ALL_LINES: return "allLines";
            case TextboxTightWrap.FIRST_AND_LAST_LINE : return "firstAndLastLine";
            case TextboxTightWrap.FIRST_LINE_ONLY: return "firstLineOnly";
            case TextboxTightWrap.LAST_LINE_ONLY: return "lastLineOnly";
            case TextboxTightWrap.NONE : return "none";
            default: return "";
        }
    }

    static /*BaselineAlignment*/int xmlToBaselineAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        { 
            case /*"top"*/5: return BaselineAlignment.TOP;
            case /*"center"*/6: return BaselineAlignment.CENTER;
            case /*"baseline"*/7: return BaselineAlignment.BASELINE;
            case /*"bottom"*/8: return BaselineAlignment.BOTTOM;
            case /*"auto"*/9: return BaselineAlignment.AUTO;
            default: return BaselineAlignment.AUTO;
        }
    }

    static String baselineAlignmentToXml(/*BaselineAlignment*/int value)
    {
        switch (value)
        {
            case BaselineAlignment.TOP: return "top";
            case BaselineAlignment.CENTER: return "center";
            case BaselineAlignment.BASELINE: return "baseline";
            case BaselineAlignment.BOTTOM: return "bottom";
            case BaselineAlignment.AUTO: return "auto";
            default: return "";
        }
    }

    static /*DropCapPosition*/int xmlToDropCapPosition(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"none"*/4: return DropCapPosition.NONE;
            case /*"drop"*/10: return DropCapPosition.NORMAL;
            case /*"margin"*/11: return DropCapPosition.MARGIN;
            default: return DropCapPosition.NONE;
        }
    }

    static String dropCapPositionToXml(/*DropCapPosition*/int value)
    {
        switch (value)
        {
            case DropCapPosition.NONE: return "none";
            case DropCapPosition.NORMAL: return "drop";
            case DropCapPosition.MARGIN: return "margin";
            default: return "";
        }
    }

    static /*VerticalAlignment*/int xmlToVerticalAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"inline"*/12: return VerticalAlignment.INLINE;
            case /*"top"*/5: return VerticalAlignment.TOP;
            case /*"center"*/6: return VerticalAlignment.CENTER;
            case /*"bottom"*/8: return VerticalAlignment.BOTTOM;
            case /*"inside"*/13: return VerticalAlignment.INSIDE;
            case /*"outside"*/14: return VerticalAlignment.OUTSIDE;
            default: return VerticalAlignment.NONE;
        }
    }

    static String verticalAlignmentToXml(/*VerticalAlignment*/int value)
    {
        switch (value)
        {
            case VerticalAlignment.INLINE: return "inline";
            case VerticalAlignment.TOP: return "top";
            case VerticalAlignment.CENTER: return "center";
            case VerticalAlignment.BOTTOM: return "bottom";
            case VerticalAlignment.INSIDE: return "inside";
            case VerticalAlignment.OUTSIDE: return "outside";
            default: return "";
        }
    }

    static /*HorizontalAlignment*/int xmlToHorizontalAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"left"*/15: return HorizontalAlignment.LEFT;
            case /*"center"*/6: return HorizontalAlignment.CENTER;
            case /*"right"*/16: return HorizontalAlignment.RIGHT;
            case /*"inside"*/13: return HorizontalAlignment.INSIDE;
            case /*"outside"*/14: return HorizontalAlignment.OUTSIDE;
            default: return HorizontalAlignment.NONE;
        }
    }

    static String horizontalAlignmentToXml(/*HorizontalAlignment*/int value)
    {
        switch (value)
        {
            case HorizontalAlignment.LEFT: return "left";
            case HorizontalAlignment.CENTER: return "center";
            case HorizontalAlignment.RIGHT: return "right";
            case HorizontalAlignment.INSIDE: return "inside";
            case HorizontalAlignment.OUTSIDE: return "outside";
            default: return "";
        }
    }

    static /*RelativeHorizontalPosition*/int xmlToRelativeHorizontalPosition(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"text"*/17: return RelativeHorizontalPosition.COLUMN;
            case /*"margin"*/11: return RelativeHorizontalPosition.MARGIN;
            case /*"page"*/18: return RelativeHorizontalPosition.PAGE;
            default: return RelativeHorizontalPosition.DEFAULT;
        }
    }

    static String relativeHorizontalPositionToXml(/*RelativeHorizontalPosition*/int value)
    {
        switch (value)
        {
            case RelativeHorizontalPosition.COLUMN: return "text";
            case RelativeHorizontalPosition.MARGIN: return "margin";
            case RelativeHorizontalPosition.PAGE: return "page";
            default: return "";
        }
    }

    static /*RelativeVerticalPosition*/int xmlToRelativeVerticalPosition(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"text"*/17: return RelativeVerticalPosition.PARAGRAPH;
            case /*"margin"*/11: return RelativeVerticalPosition.MARGIN;
            case /*"page"*/18: return RelativeVerticalPosition.PAGE;
            default: return RelativeVerticalPosition.TABLE_DEFAULT;
        }
    }

    static String relativeVerticalPositionToXml(/*RelativeVerticalPosition*/int value)
    {
        switch (value)
        {
            case RelativeVerticalPosition.PARAGRAPH: return "text";
            case RelativeVerticalPosition.MARGIN: return "margin";
            case RelativeVerticalPosition.PAGE: return "page";
            default: return "";
        }
    }

    static /*WrapType*/int xmlToTextFrameWrapType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"auto"*/9: return WrapType.INLINE;    // This is arguable. Some claim that this should mean "around".
            case /*"notBeside"*/19: return WrapType.TOP_BOTTOM;
            case /*"not-beside"*/20: return WrapType.TOP_BOTTOM;   // WML
            case /*"around"*/21: return WrapType.SQUARE;
            case /*"tight"*/22: return WrapType.TIGHT;
            case /*"through"*/23: return WrapType.THROUGH;
            case /*"none"*/4: return WrapType.NONE;
            default: return WrapType.NONE;
        }
    }

    static String textFrameWrapTypeToXml(/*WrapType*/int value, boolean isDocx)
    {
        switch (value)
        {
            case WrapType.INLINE: return "auto";
            case WrapType.TOP_BOTTOM: return (isDocx) ? "notBeside" : "not-beside";
            case WrapType.SQUARE: return "around";
            case WrapType.TIGHT: return "tight";
            case WrapType.THROUGH: return "through";
            case WrapType.NONE: return "none";
            default: return "";
        }
    }

    static /*HeightRule*/int xmlToHeightRule(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"auto"*/9: return HeightRule.AUTO;
            case /*"exact"*/24: return HeightRule.EXACTLY;
            case /*"atLeast"*/25: return HeightRule.AT_LEAST;
            case /*"at-least"*/26: return HeightRule.AT_LEAST; // WML
            default: return HeightRule.AUTO;
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

    static /*LineSpacingRule*/int xmlToLineSpacingRule(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"auto"*/9: return LineSpacingRule.MULTIPLE;
            case /*"exact"*/24: return LineSpacingRule.EXACTLY;
            case /*"atLeast"*/25: return LineSpacingRule.AT_LEAST;
            case /*"at-least"*/26: return LineSpacingRule.AT_LEAST;    // WML
            default: return LineSpacingRule.AT_LEAST;    
        }
    }

    static String lineSpacingRuleToXml(/*LineSpacingRule*/int value, boolean isDocx)
    {
        switch (value)
        {
            case LineSpacingRule.MULTIPLE: return "auto";
            case LineSpacingRule.EXACTLY: return "exact";
            case LineSpacingRule.AT_LEAST: return (isDocx) ? "atLeast" : "at-least";
            default: return "";
        }
    }

    static /*ParagraphAlignment*/int xmlToParagraphAlignment(String value, OoxmlComplianceInfo cInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"start"*/27: 
                cInfo.markAsIsoTransitional();
                return ParagraphAlignment.LEFT;
            case /*"left"*/15: 
                return ParagraphAlignment.LEFT;
            case /*"center"*/6: 
                return ParagraphAlignment.CENTER;
            case /*"right"*/16: 
                return ParagraphAlignment.RIGHT;
            case /*"end"*/28: 
                cInfo.markAsIsoTransitional();
                return ParagraphAlignment.RIGHT;
            case /*"both"*/29: 
                return ParagraphAlignment.JUSTIFY;
            case /*"distribute"*/30: 
                return ParagraphAlignment.DISTRIBUTED;
            // WORDSNET-4870 Arabic alignment roundtrip support. See TestJira4870 for details.
            case /*"lowKashida"*/31: 
            case /*"low-kashida"*/32:
                return ParagraphAlignment.ARABIC_LOW_KASHIDA;
            case /*"mediumKashida"*/33:
            case /*"medium-kashida"*/34:
                return ParagraphAlignment.ARABIC_MEDIUM_KASHIDA;
            case /*"highKashida"*/35:
            case /*"high-kashida"*/36:
                return ParagraphAlignment.ARABIC_HIGH_KASHIDA;
            case /*"thaiDistribute"*/37: 
            case /*"thai-distribute"*/38:
                return ParagraphAlignment.THAI_DISTRIBUTED;
            //"listTab", ParagraphAlignment.,
            //"list-tab", ParagraphAlignment.,  // WML
            default: return ParagraphAlignment.LEFT;
        }
    }

    static String paragraphAlignmentToXml(/*ParagraphAlignment*/int value, boolean isDocx, 
        /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = isDocx && (compliance == OoxmlComplianceCore.ISO_STRICT);

        switch (value)
        {
            case ParagraphAlignment.LEFT: return (isIsoStrict) ? "start" : "left";
            case ParagraphAlignment.CENTER: return "center";
            case ParagraphAlignment.RIGHT: return (isIsoStrict) ? "end" : "right";
            case ParagraphAlignment.JUSTIFY: return "both";
            case ParagraphAlignment.DISTRIBUTED: return "distribute";
            // WORDSNET-4870 Arabic alignment roundtrip support.
            case ParagraphAlignment.ARABIC_LOW_KASHIDA: return isDocx ? "lowKashida" : "low-kashida";
            case ParagraphAlignment.ARABIC_MEDIUM_KASHIDA: return isDocx ? "mediumKashida" : "medium-kashida";
            case ParagraphAlignment.ARABIC_HIGH_KASHIDA: return isDocx ? "highKashida" : "high-kashida";
            case ParagraphAlignment.THAI_DISTRIBUTED: return isDocx ? "thaiDistribute" : "thai-distribute";
            //"listTab", ParagraphAlignment.,
            //"list-tab", ParagraphAlignment.,  // WML
            default: return "";
        }
    }

    static /*TabLeader*/int xmlToTabLeader(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"none"*/4: return TabLeader.NONE;
            case /*"dot"*/39: return TabLeader.DOTS;
            case /*"hyphen"*/40: return TabLeader.DASHES;
            case /*"underscore"*/41: return TabLeader.LINE;
            case /*"heavy"*/42: return TabLeader.HEAVY;
            case /*"middleDot"*/43: return TabLeader.MIDDLE_DOT;
            case /*"middle-dot"*/44: return TabLeader.MIDDLE_DOT;   // WML
            default: return TabLeader.NONE;
        }
    }

    static String tabLeaderToXml(/*TabLeader*/int value, boolean isDocx)
    {
        switch (value)
        {
            case TabLeader.NONE: return "none";
            case TabLeader.DOTS: return "dot";
            case TabLeader.DASHES: return "hyphen";
            case TabLeader.LINE: return "underscore";
            case TabLeader.HEAVY: return "heavy";
            case TabLeader.MIDDLE_DOT: return (isDocx) ? "middleDot" : "middle-dot";
            default: return "";
        }
    }

    static /*TabAlignment*/int xmlToTabAlignment(String value, OoxmlComplianceInfo complianceInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"clear"*/45: 
                return TabAlignment.CLEAR;
            case /*"left"*/15:
            case /*"l"*/46: // DML
                return TabAlignment.LEFT;
            case /*"start"*/27: 
                if (complianceInfo != null) 
                    complianceInfo.markAsIsoTransitional();
                return TabAlignment.LEFT;
            case /*"center"*/6:
            case /*"ctr"*/47: // DML
                return TabAlignment.CENTER;
            case /*"right"*/16:
            case /*"r"*/48: // DML
                return TabAlignment.RIGHT;
            case /*"end"*/28:
                if (complianceInfo != null)
                    complianceInfo.markAsIsoTransitional();
                return TabAlignment.RIGHT;
            case /*"decimal"*/49:
            case /*"dec"*/50: // DML
                return TabAlignment.DECIMAL;
            case /*"bar"*/51: 
                return TabAlignment.BAR;
            case /*"num"*/52: 
                return TabAlignment.LIST;
            case /*"list"*/53: 
                return TabAlignment.LIST;   // WML
            default: return TabAlignment.CLEAR;
        }
    }

    static String tabAlignmentToXml(/*TabAlignment*/int value, boolean isDocx, boolean isIsoStrict)
    {
        switch (value)
        {
            case TabAlignment.CLEAR: return "clear";
            case TabAlignment.LEFT: return (isDocx && isIsoStrict) ? "start" : "left";
            case TabAlignment.CENTER: return "center";
            case TabAlignment.RIGHT: return (isDocx && isIsoStrict) ? "end" : "right";
            case TabAlignment.DECIMAL: return "decimal";
            case TabAlignment.BAR: return "bar";
            case TabAlignment.LIST: return (isDocx) ? "num" : "list";
            default: return "";
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"allLines",
		"firstAndLastLine",
		"firstLineOnly",
		"lastLineOnly",
		"none",
		"top",
		"center",
		"baseline",
		"bottom",
		"auto",
		"drop",
		"margin",
		"inline",
		"inside",
		"outside",
		"left",
		"right",
		"text",
		"page",
		"notBeside",
		"not-beside",
		"around",
		"tight",
		"through",
		"exact",
		"atLeast",
		"at-least",
		"start",
		"end",
		"both",
		"distribute",
		"lowKashida",
		"low-kashida",
		"mediumKashida",
		"medium-kashida",
		"highKashida",
		"high-kashida",
		"thaiDistribute",
		"thai-distribute",
		"dot",
		"hyphen",
		"underscore",
		"heavy",
		"middleDot",
		"middle-dot",
		"clear",
		"l",
		"ctr",
		"r",
		"decimal",
		"dec",
		"bar",
		"num",
		"list"
	);

}

