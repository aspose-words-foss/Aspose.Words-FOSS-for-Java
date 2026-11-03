// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringToIntBidirectionalMap;
import com.aspose.StringToObjBidirectionalMap;


/// <summary>
/// Performs conversions between enumerated types MS Word XML strings that are common to both DOCX and WordML.
/// </summary>
class StyleConvertUtil
{
    static /*FramesetBorderType*/int nrxToFramesetBorderType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"flatBorders"*/0: return FramesetBorderType.SIMPLE;
            case /*"noBorder"*/1: return FramesetBorderType.NONE;
            default: return FramesetBorderType.RAISED;
        }
    }

    static String framesetBorderTypeToNrx(/*FramesetBorderType*/int value)
    {
        switch (value)
        {
            case FramesetBorderType.SIMPLE: return "flatBorders";
            case FramesetBorderType.NONE: return "noBorder";
            default: return "";
        }
    }

    static /*FrameLayoutType*/int nrxToFrameLayoutType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"cols"*/2: return FrameLayoutType.HORIZONTAL;
            case /*"rows"*/3: return FrameLayoutType.VERTICAL;
            default: return FrameLayoutType.NONE;
        }
    }

    static String frameLayoutTypeToNrx(/*FrameLayoutType*/int value)
    {
        switch (value)
        {
            case FrameLayoutType.HORIZONTAL: return "cols";
            case FrameLayoutType.VERTICAL: return "rows";
            default: return "";
        }
    }

    static /*TableStyleOverrideType*/int xmlToTableStyleOverrideType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"band1Horz"*/4: return TableStyleOverrideType.ODD_ROW_BANDING;
            case /*"band1Vert"*/5: return TableStyleOverrideType.ODD_COLUMN_BANDING;
            case /*"band2Horz"*/6: return TableStyleOverrideType.EVEN_ROW_BANDING;
            case /*"band2Vert"*/7: return TableStyleOverrideType.EVEN_COLUMN_BANDING;
            case /*"firstCol"*/8: return TableStyleOverrideType.FIRST_COLUMN;
            case /*"firstRow"*/9: return TableStyleOverrideType.FIRST_ROW;
            case /*"lastCol"*/10: return TableStyleOverrideType.LAST_COLUMN;
            case /*"lastRow"*/11: return TableStyleOverrideType.LAST_ROW;
            case /*"neCell"*/12: return TableStyleOverrideType.TOP_RIGHT_CELL;
            case /*"nwCell"*/13: return TableStyleOverrideType.TOP_LEFT_CELL;
            case /*"seCell"*/14: return TableStyleOverrideType.BOTTOM_RIGHT_CELL;
            case /*"swCell"*/15: return TableStyleOverrideType.BOTTOM_LEFT_CELL;
            case /*"wholeTable"*/16: return TableStyleOverrideType.WHOLE_TABLE;
            default: return TableStyleOverrideType.NONE;
        }
    }

    static String tableStyleOverrideTypeToXml(/*TableStyleOverrideType*/int value)
    {
        switch (value)
        {
            case TableStyleOverrideType.ODD_ROW_BANDING: return "band1Horz";
            case TableStyleOverrideType.ODD_COLUMN_BANDING: return "band1Vert";
            case TableStyleOverrideType.EVEN_ROW_BANDING: return "band2Horz";
            case TableStyleOverrideType.EVEN_COLUMN_BANDING: return "band2Vert";
            case TableStyleOverrideType.FIRST_COLUMN: return "firstCol";
            case TableStyleOverrideType.FIRST_ROW: return "firstRow";
            case TableStyleOverrideType.LAST_COLUMN: return "lastCol";
            case TableStyleOverrideType.LAST_ROW: return "lastRow";
            case TableStyleOverrideType.TOP_RIGHT_CELL: return "neCell";
            case TableStyleOverrideType.TOP_LEFT_CELL: return "nwCell";
            case TableStyleOverrideType.BOTTOM_RIGHT_CELL: return "seCell";
            case TableStyleOverrideType.BOTTOM_LEFT_CELL: return "swCell";
            case TableStyleOverrideType.WHOLE_TABLE: return "wholeTable";
            default: return "wholeTable";
        }
    }

    static /*FontPitch*/int xmlToFontPitch(String value)
    {
        return (/*FontPitch*/int)G_FONT_PITCH_MAP.getValue(value, (int)FontPitch.DEFAULT);
    }

    static String fontPitchToXml(/*FontPitch*/int value)
    {
        return G_FONT_PITCH_MAP.getValue((int)value, "");
    }

    static /*RelativeVerticalSize*/int xmlToRelativeHeight(String value)
    {
        return (/*RelativeVerticalSize*/int)G_RELATIVE_VERTICAL_SIZE_MAP.getValue(value, (int)RelativeVerticalSize.DEFAULT);
    }

    static String relativeHeightToXml(/*RelativeVerticalSize*/int value)
    {
        return G_RELATIVE_VERTICAL_SIZE_MAP.getValue((int)value, "");
    }

    static /*RelativeHorizontalSize*/int xmlToRelativeWidth(String value)
    {
        return (/*RelativeHorizontalSize*/int)G_RELATIVE_HORIZONTAL_SIZE_MAP.getValue(value, (int)RelativeHorizontalSize.DEFAULT);
    }

    static String relativeWidthToXml(/*RelativeHorizontalSize*/int value)
    {
        return G_RELATIVE_HORIZONTAL_SIZE_MAP.getValue((int)value, "");
    }

    static /*RelativeVerticalPosition*/int xmlToRelativeVerticalPosition(String value)
    {
        return (/*RelativeVerticalPosition*/int)G_RELATIVE_VERTICAL_POSITION_MAP.getValue(value, (int)RelativeVerticalPosition.TABLE_DEFAULT);
    }

    static /*RelativeVerticalPosition*/int xmlToRelativeVerticalPosition(String value, /*RelativeVerticalPosition*/int defaultValue)
    {
        return (/*RelativeVerticalPosition*/int)G_RELATIVE_VERTICAL_POSITION_MAP.getValue(value, (int)defaultValue);
    }

    static String relativeVerticalPositionToXml(/*RelativeVerticalPosition*/int value)
    {
        return G_RELATIVE_VERTICAL_POSITION_MAP.getValue((int)value, "");
    }

    static /*RelativeHorizontalPosition*/int xmlToRelativeHorizontalPosition(String value)
    {
        return (/*RelativeHorizontalPosition*/int)G_RELATIVE_HORIZONTAL_POSITION_MAP.getValue(value, (int)RelativeHorizontalPosition.DEFAULT);
    }

    static /*RelativeHorizontalPosition*/int xmlToTableRelativeHorizontalPosition(String value)
    {
        // Mimics MS Word behavior which
        // sets relative horizontal position to "page" if "column" position is specified in XML.
        // 'column' cannot be added to the map as the same map is used for reading VML shape position
        // and there is no such substitution for VML.
        return msString.equals(value, "column", StringComparison.ORDINAL)
            ? RelativeHorizontalPosition.PAGE
            : xmlToRelativeHorizontalPosition(value);
    }

    static String relativeHorizontalPositionToXml(/*RelativeHorizontalPosition*/int value)
    {
        return G_RELATIVE_HORIZONTAL_POSITION_MAP.getValue((int)value, "");
    }

    static /*HorizontalAlignment*/int xmlToHorizontalAlignment(String value)
    {
        return (/*HorizontalAlignment*/int)G_HORIZONTAL_ALIGNMENT_MAP.getValue(value, (int)HorizontalAlignment.NONE);
    }

    static String horizontalAlignmentToXml(/*HorizontalAlignment*/int value)
    {
        return G_HORIZONTAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*VerticalAlignment*/int xmlToVerticalAlignment(String value)
    {
        return (/*VerticalAlignment*/int)G_VERTICAL_ALIGNMENT_MAP.getValue(value, (int)VerticalAlignment.NONE);
    }

    static String verticalAlignmentToXml(/*VerticalAlignment*/int value)
    {
        return G_VERTICAL_ALIGNMENT_MAP.getValue((int)value, "");
    }

    /// <summary>
    /// See below comment for StyleIdentifierToDocx.
    /// </summary>
    static /*StyleIdentifier*/int xmlToStyleIdentifier(String value)
    {
        return (/*StyleIdentifier*/int)G_STYLE_IDENTIFIER_MAP.getValue(value, (int)StyleIdentifier.USER);
    }

    /// <summary>
    /// Converts a built-in style identifier into a "standard" MS Word locale independent
    /// style name that is written to DOCX, WordML and probably RTF.
    ///
    /// Note that this name is suitable for writing into a file, but not suitable
    /// for using in the model because some names are different in the model.
    /// For example, this method will return "heading 2" because that's what MS Word
    /// writes into the above file formats. But in the model and in DOC it is "Heading 2".
    /// To convert between DOCX etc names and model/DOC names use DocxToStyleName.
    /// </summary>
    static String styleIdentifierToXml(/*StyleIdentifier*/int styleIdentifier, String defaultValue)
    {
        return G_STYLE_IDENTIFIER_MAP.getValue((int)styleIdentifier, defaultValue);
    }

    /// <summary>
    /// Converts a locale independent "standard" MS Word style name
    /// that was read from DOCX, WordML and probably RTF into a name that is suitable
    /// for use in the model/DOC.
    /// If cannot find a mapping, just returns the original name.
    /// See comments above.
    /// </summary>
    static String xmlToStyleName(String value)
    {
        return G_STYLE_NAME_MAP.getValue(value, value);
    }

    /// <summary>
    /// Converts a "model" style name into a "file" style name (standard locale independent).
    /// If cannot find a mapping, just returns the original name.
    /// See comments above.
    /// </summary>
    static String styleNameToXml(String value)
    {
        return G_STYLE_NAME_MAP.getKey(value, value);
    }

    static /*ScreenSize*/int xmlToScreenSize(String value)
    {
        return (/*ScreenSize*/int)G_SCREEN_SIZE_MAP.getValue(value, (int)ScreenSize.SIZE_800_X_600);
    }

    static String screenSizeToXml(/*ScreenSize*/int value)
    {
        return G_SCREEN_SIZE_MAP.getValue((int)value, "");
    }

    static /*PreferredWidthType*/int xmlToLengthType(String value)
    {
        // WORDSNET-26396 Ignore case for preferred width type value.
        switch (gStringSwitchMap.of(value.toLowerCase()))
        {
            case /*"pct"*/17: return PreferredWidthType.PERCENT;
            case /*"dxa"*/18: return PreferredWidthType.POINTS;
            case /*"auto"*/19: return PreferredWidthType.AUTO;
            case /*"nil"*/20: return (/*PreferredWidthType*/int)0;
            default: return PreferredWidthType.AUTO;
        }
    }

    static String lengthTypeToXml(/*PreferredWidthType*/int value)
    {
        switch (value)
        {
            case PreferredWidthType.PERCENT: return "pct";
            case PreferredWidthType.POINTS: return "dxa";
            case PreferredWidthType.AUTO: return "auto";
            default: return "";
        }
    }

    static /*TextureIndex*/int xmlToTextureIndex(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"clear"*/21: return TextureIndex.TEXTURE_NONE;
            case /*"nil"*/20: return TextureIndex.TEXTURE_NIL;
            case /*"solid"*/22: return TextureIndex.TEXTURE_SOLID;
            case /*"horzStripe"*/23:
            case /*"horz-stripe"*/24: return TextureIndex.TEXTURE_DARK_HORIZONTAL;
            case /*"vertStripe"*/25:
            case /*"vert-stripe"*/26: return TextureIndex.TEXTURE_DARK_VERTICAL;
            case /*"reverseDiagStripe"*/27:
            case /*"reverse-diag-stripe"*/28: return TextureIndex.TEXTURE_DARK_DIAGONAL_DOWN;
            case /*"diagStripe"*/29:
            case /*"diag-stripe"*/30: return TextureIndex.TEXTURE_DARK_DIAGONAL_UP;
            case /*"horzCross"*/31:
            case /*"horz-cross"*/32: return TextureIndex.TEXTURE_DARK_CROSS;
            case /*"diagCross"*/33:
            case /*"diag-cross"*/34: return TextureIndex.TEXTURE_DARK_DIAGONAL_CROSS;
            case /*"thinHorzStripe"*/35:
            case /*"thin-horz-stripe"*/36: return TextureIndex.TEXTURE_HORIZONTAL;
            case /*"thinVertStripe"*/37:
            case /*"thin-vert-stripe"*/38: return TextureIndex.TEXTURE_VERTICAL;
            case /*"thinReverseDiagStripe"*/39:
            case /*"thin-reverse-diag-stripe"*/40: return TextureIndex.TEXTURE_DIAGONAL_DOWN;
            case /*"thinDiagStripe"*/41:
            case /*"thin-diag-stripe"*/42: return TextureIndex.TEXTURE_DIAGONAL_UP;
            case /*"thinHorzCross"*/43:
            case /*"thin-horz-cross"*/44: return TextureIndex.TEXTURE_CROSS;
            case /*"thinDiagCross"*/45:
            case /*"thin-diag-cross"*/46: return TextureIndex.TEXTURE_DIAGONAL_CROSS;
            case /*"pct5"*/47:
            case /*"pct-5"*/48: return TextureIndex.TEXTURE_5_PERCENT;
            case /*"pct10"*/49:
            case /*"pct-10"*/50: return TextureIndex.TEXTURE_10_PERCENT;
            case /*"pct12"*/51:
            case /*"pct-12"*/52: return TextureIndex.TEXTURE_12_PT_5_PERCENT;
            case /*"pct15"*/53:
            case /*"pct-15"*/54: return TextureIndex.TEXTURE_15_PERCENT;
            case /*"pct20"*/55:
            case /*"pct-20"*/56: return TextureIndex.TEXTURE_20_PERCENT;
            case /*"pct25"*/57:
            case /*"pct-25"*/58: return TextureIndex.TEXTURE_25_PERCENT;
            case /*"pct30"*/59:
            case /*"pct-30"*/60: return TextureIndex.TEXTURE_30_PERCENT;
            case /*"pct35"*/61:
            case /*"pct-35"*/62: return TextureIndex.TEXTURE_35_PERCENT;
            case /*"pct37"*/63:
            case /*"pct-37"*/64: return TextureIndex.TEXTURE_37_PT_5_PERCENT;
            case /*"pct40"*/65:
            case /*"pct-40"*/66: return TextureIndex.TEXTURE_40_PERCENT;
            case /*"pct45"*/67:
            case /*"pct-45"*/68: return TextureIndex.TEXTURE_45_PERCENT;
            case /*"pct50"*/69:
            case /*"pct-50"*/70: return TextureIndex.TEXTURE_50_PERCENT;
            case /*"pct55"*/71:
            case /*"pct-55"*/72: return TextureIndex.TEXTURE_55_PERCENT;
            case /*"pct60"*/73:
            case /*"pct-60"*/74: return TextureIndex.TEXTURE_60_PERCENT;
            case /*"pct62"*/75:
            case /*"pct-62"*/76: return TextureIndex.TEXTURE_62_PT_5_PERCENT;
            case /*"pct65"*/77:
            case /*"pct-65"*/78: return TextureIndex.TEXTURE_65_PERCENT;
            case /*"pct70"*/79:
            case /*"pct-70"*/80: return TextureIndex.TEXTURE_70_PERCENT;
            case /*"pct75"*/81:
            case /*"pct-75"*/82: return TextureIndex.TEXTURE_75_PERCENT;
            case /*"pct80"*/83:
            case /*"pct-80"*/84: return TextureIndex.TEXTURE_80_PERCENT;
            case /*"pct85"*/85:
            case /*"pct-85"*/86: return TextureIndex.TEXTURE_85_PERCENT;
            case /*"pct87"*/87:
            case /*"pct-87"*/88: return TextureIndex.TEXTURE_87_PT_5_PERCENT;
            case /*"pct90"*/89:
            case /*"pct-90"*/90: return TextureIndex.TEXTURE_90_PERCENT;
            case /*"pct95"*/91:
            case /*"pct-95"*/92: return TextureIndex.TEXTURE_95_PERCENT;
            default: return TextureIndex.TEXTURE_NONE;
        }
    }

    static String textureIndexToXml(/*TextureIndex*/int value, boolean isDocx)
    {
        switch (value)
        {
            case TextureIndex.TEXTURE_NONE: return "clear";
            case TextureIndex.TEXTURE_NIL: return "nil";
            case TextureIndex.TEXTURE_SOLID: return "solid";
            case TextureIndex.TEXTURE_DARK_HORIZONTAL: return (isDocx) ? "horzStripe" : "horz-stripe";
            case TextureIndex.TEXTURE_DARK_VERTICAL: return (isDocx) ? "vertStripe" : "vert-stripe";
            case TextureIndex.TEXTURE_DARK_DIAGONAL_DOWN: return (isDocx) ? "reverseDiagStripe" : "reverse-diag-stripe";
            case TextureIndex.TEXTURE_DARK_DIAGONAL_UP: return (isDocx) ? "diagStripe" : "diag-stripe";
            case TextureIndex.TEXTURE_DARK_CROSS: return (isDocx) ? "horzCross" : "horz-cross";
            case TextureIndex.TEXTURE_DARK_DIAGONAL_CROSS: return (isDocx) ? "diagCross" : "diag-cross";
            case TextureIndex.TEXTURE_HORIZONTAL: return (isDocx) ? "thinHorzStripe" : "thin-horz-stripe";
            case TextureIndex.TEXTURE_VERTICAL: return (isDocx) ? "thinVertStripe" : "thin-vert-stripe";
            case TextureIndex.TEXTURE_DIAGONAL_DOWN: return (isDocx) ? "thinReverseDiagStripe" : "thin-reverse-diag-stripe";
            case TextureIndex.TEXTURE_DIAGONAL_UP: return (isDocx) ? "thinDiagStripe" : "thin-diag-stripe";
            case TextureIndex.TEXTURE_CROSS: return (isDocx) ? "thinHorzCross" : "thin-horz-cross";
            case TextureIndex.TEXTURE_DIAGONAL_CROSS: return (isDocx) ? "thinDiagCross" : "thin-diag-cross";
            case TextureIndex.TEXTURE_5_PERCENT: return (isDocx) ? "pct5" : "pct-5";
            case TextureIndex.TEXTURE_10_PERCENT: return (isDocx) ? "pct10" : "pct-10";
            case TextureIndex.TEXTURE_12_PT_5_PERCENT: return (isDocx) ? "pct12" : "pct-12";
            case TextureIndex.TEXTURE_15_PERCENT: return (isDocx) ? "pct15" : "pct-15";
            case TextureIndex.TEXTURE_20_PERCENT: return (isDocx) ? "pct20" : "pct-20";
            case TextureIndex.TEXTURE_25_PERCENT: return (isDocx) ? "pct25" : "pct-25";
            case TextureIndex.TEXTURE_30_PERCENT: return (isDocx) ? "pct30" : "pct-30";
            case TextureIndex.TEXTURE_35_PERCENT: return (isDocx) ? "pct35" : "pct-35";
            case TextureIndex.TEXTURE_37_PT_5_PERCENT: return (isDocx) ? "pct37" : "pct-37";
            case TextureIndex.TEXTURE_40_PERCENT: return (isDocx) ? "pct40" : "pct-40";
            case TextureIndex.TEXTURE_45_PERCENT: return (isDocx) ? "pct45" : "pct-45";
            case TextureIndex.TEXTURE_50_PERCENT: return (isDocx) ? "pct50" : "pct-50";
            case TextureIndex.TEXTURE_55_PERCENT: return (isDocx) ? "pct55" : "pct-55";
            case TextureIndex.TEXTURE_60_PERCENT: return (isDocx) ? "pct60" : "pct-60";
            case TextureIndex.TEXTURE_62_PT_5_PERCENT: return (isDocx) ? "pct62" : "pct-62";
            case TextureIndex.TEXTURE_65_PERCENT: return (isDocx) ? "pct65" : "pct-65";
            case TextureIndex.TEXTURE_70_PERCENT: return (isDocx) ? "pct70" : "pct-70";
            case TextureIndex.TEXTURE_75_PERCENT: return (isDocx) ? "pct75" : "pct-75";
            case TextureIndex.TEXTURE_80_PERCENT: return (isDocx) ? "pct80" : "pct-80";
            case TextureIndex.TEXTURE_85_PERCENT: return (isDocx) ? "pct85" : "pct-85";
            case TextureIndex.TEXTURE_87_PT_5_PERCENT: return (isDocx) ? "pct87" : "pct-87";
            case TextureIndex.TEXTURE_90_PERCENT: return (isDocx) ? "pct90" : "pct-90";
            case TextureIndex.TEXTURE_95_PERCENT: return (isDocx) ? "pct95" : "pct-95";

            // Some intermediate settings that exist in DOC do not exist in Office Open XML.
            // We round them to existing settings.
            case TextureIndex.TEXTURE_2_PT_5_PERCENT: return (isDocx) ? "pct5" : "pct-5";
            case TextureIndex.TEXTURE_7_PT_5_PERCENT: return (isDocx) ? "pct10" : "pct-10";
            case TextureIndex.TEXTURE_17_PT_5_PERCENT: return (isDocx) ? "pct20" : "pct-20";
            case TextureIndex.TEXTURE_22_PT_5_PERCENT: return (isDocx) ? "pct25" : "pct-25";
            case TextureIndex.TEXTURE_27_PT_5_PERCENT: return (isDocx) ? "pct30" : "pct-30";
            case TextureIndex.TEXTURE_32_PT_5_PERCENT: return (isDocx) ? "pct35" : "pct-35";
            case TextureIndex.TEXTURE_42_PT_5_PERCENT: return (isDocx) ? "pct40" : "pct-40";
            case TextureIndex.TEXTURE_47_PT_5_PERCENT: return (isDocx) ? "pct45" : "pct-45";
            case TextureIndex.TEXTURE_52_PT_5_PERCENT: return (isDocx) ? "pct50" : "pct-50";
            case TextureIndex.TEXTURE_57_PT_5_PERCENT: return (isDocx) ? "pct55" : "pct-55";
            case TextureIndex.TEXTURE_67_PT_5_PERCENT: return (isDocx) ? "pct65" : "pct-65";
            case TextureIndex.TEXTURE_72_PT_5_PERCENT: return (isDocx) ? "pct70" : "pct-70";
            case TextureIndex.TEXTURE_77_PT_5_PERCENT: return (isDocx) ? "pct80" : "pct-80";
            case TextureIndex.TEXTURE_82_PT_5_PERCENT: return (isDocx) ? "pct85" : "pct-85";
            case TextureIndex.TEXTURE_92_PT_5_PERCENT: return (isDocx) ? "pct90" : "pct-90";
            case TextureIndex.TEXTURE_97_PT_5_PERCENT: return (isDocx) ? "pct95" : "pct-95";

            default: return "nil";
        }
    }

    static /*TextOrientation*/int xmlToTextOrientation(String value, OoxmlComplianceInfo cInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"lr"*/93:   // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.UPWARD;
                }
            case /*"btLr"*/94:
            case /*"bt-lr"*/95:
                return TextOrientation.UPWARD;
            case /*"tb"*/96:  // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.HORIZONTAL;
                }
            case /*"lrTb"*/97:
            case /*"lr-tb"*/98:
                return TextOrientation.HORIZONTAL;
            case /*"tbV"*/99: // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.HORIZONTAL_ROTATED_FAR_EAST;
                }
            case /*"lrTbV"*/100:
            case /*"lr-tb-v"*/101:
                return TextOrientation.HORIZONTAL_ROTATED_FAR_EAST;
            case /*"rlV"*/102: // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.VERTICAL_FAR_EAST;
                }
            case /*"tbRlV"*/103:
            case /*"tb-rl-v"*/104:
                return TextOrientation.VERTICAL_FAR_EAST;
            case /*"rl"*/105:  // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.DOWNWARD;
                }
            case /*"tbRl"*/106:
            case /*"tb-rl"*/107:
                return TextOrientation.DOWNWARD;
            case /*"tb-lr-v"*/108:
            case /*"tbLrV"*/109:
                return TextOrientation.VERTICAL_ROTATED_FAR_EAST;
            case /*"lrV"*/110:  // iso29500
                {
                    cInfo.markAsIsoTransitional();
                    return TextOrientation.VERTICAL_ROTATED_FAR_EAST;
                }
            default:
                return TextOrientation.HORIZONTAL;
        }
    }

    static String textOrientationToXml(/*TextOrientation*/int value, boolean isDocx, /*OoxmlComplianceCore*/int compliance)
    {
        boolean isIsoStrict = isDocx && (compliance == OoxmlComplianceCore.ISO_STRICT);

        switch (value)
        {
            case TextOrientation.UPWARD:
                return (isDocx) ? ((isIsoStrict) ? "lr" : "btLr") : "bt-lr";
            case TextOrientation.HORIZONTAL:
                return (isDocx) ? ((isIsoStrict) ? "tb" : "lrTb") : "lr-tb";
            case TextOrientation.HORIZONTAL_ROTATED_FAR_EAST:
                return (isDocx) ? ((isIsoStrict) ? "tbV" : "lrTbV") : "lr-tb-v";
            case TextOrientation.VERTICAL_FAR_EAST:
                return (isDocx) ? ((isIsoStrict) ? "rlV" : "tbRlV") : "tb-rl-v";
            case TextOrientation.DOWNWARD:
                return (isDocx) ? ((isIsoStrict) ? "rl" : "tbRl") : "tb-rl";
            case TextOrientation.VERTICAL_ROTATED_FAR_EAST:
                return (isDocx) ? ((isIsoStrict) ? "lrV" : "tbLrV") : "tb-lr-v";
            default:
                return "";
        }
    }

    /// <summary>
    /// Converts int values into <see cref="StylePaneSortMethod"/>.
    /// Is used for docx as well as for rtf. See RK comments and constants in DocPr.
    /// </summary>
    static /*StylePaneSortMethod*/int intToStylePaneSortMethod(int value)
    {
        switch (value)
        {
            case DocPr.STYLE_PANE_SORT_METHOD_NAMES:
                return StylePaneSortMethod.NAME;
            case DocPr.STYLE_PANE_SORT_METHOD_DEFAULT:
                return StylePaneSortMethod.DEFAULT;
            case DocPr.STYLE_PANE_SORT_METHOD_FONT:
                return StylePaneSortMethod.FONT;
            case DocPr.STYLE_PANE_SORT_METHOD_STYLE:
                return StylePaneSortMethod.BASED_ON;
            case DocPr.STYLE_PANE_SORT_METHOD_TYPE:
                return StylePaneSortMethod.STYLE_TYPE;
            default:
                return StylePaneSortMethod.DEFAULT;
        }
    }

    /// <summary>
    /// Converts <see cref="StylePaneSortMethod"/> values in int.
    /// Is used for docx as well as for rtf. See RK comments and constants in DocPr.
    /// </summary>
    static int stylePaneSortMethodToInt(/*StylePaneSortMethod*/int value)
    {
        switch (value)
        {
            case StylePaneSortMethod.NAME:
                return DocPr.STYLE_PANE_SORT_METHOD_NAMES;
            case StylePaneSortMethod.FONT:
                return DocPr.STYLE_PANE_SORT_METHOD_FONT;
            case StylePaneSortMethod.BASED_ON:
                return DocPr.STYLE_PANE_SORT_METHOD_STYLE;
            case StylePaneSortMethod.STYLE_TYPE:
                return DocPr.STYLE_PANE_SORT_METHOD_TYPE;
            case StylePaneSortMethod.DEFAULT:
            case StylePaneSortMethod.PRIORITY:
            default:
                return DocPr.STYLE_PANE_SORT_METHOD_DEFAULT;
        }
    }

    private static /*final*/ StringToIntBidirectionalMap G_FONT_PITCH_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_VERTICAL_SIZE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_HORIZONTAL_SIZE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_VERTICAL_POSITION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_HORIZONTAL_POSITION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_HORIZONTAL_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_VERTICAL_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_STYLE_IDENTIFIER_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToObjBidirectionalMap<String> G_STYLE_NAME_MAP = new StringToObjBidirectionalMap<String>();
    private static /*final*/ StringToIntBidirectionalMap G_SCREEN_SIZE_MAP = new StringToIntBidirectionalMap();
    static/* StyleConvertUtil()*/
    {
        G_FONT_PITCH_MAP.addEntry("default", (int)FontPitch.DEFAULT);
        G_FONT_PITCH_MAP.addEntry("fixed", (int)FontPitch.FIXED);
        G_FONT_PITCH_MAP.addEntry("variable", (int)FontPitch.VARIABLE);

        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("margin", (int)RelativeVerticalSize.MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("page", (int)RelativeVerticalSize.PAGE);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("outer-margin-area", (int)RelativeVerticalSize.OUTER_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("inner-margin-area", (int)RelativeVerticalSize.INNER_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("top-margin-area", (int)RelativeVerticalSize.TOP_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("bottom-margin-area", (int)RelativeVerticalSize.BOTTOM_MARGIN);

        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("margin", (int)RelativeHorizontalSize.MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("page", (int)RelativeHorizontalSize.PAGE);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("outer-margin-area", (int)RelativeHorizontalSize.OUTER_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("inner-margin-area", (int)RelativeHorizontalSize.INNER_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("left-margin-area", (int)RelativeHorizontalSize.LEFT_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("right-margin-area", (int)RelativeHorizontalSize.RIGHT_MARGIN);

        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("margin", (int)RelativeVerticalPosition.MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("page", (int)RelativeVerticalPosition.PAGE);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("text", (int)RelativeVerticalPosition.PARAGRAPH);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("line", (int)RelativeVerticalPosition.LINE);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("outer-margin-area", (int)RelativeVerticalPosition.OUTSIDE_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("inner-margin-area", (int)RelativeVerticalPosition.INSIDE_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("top-margin-area", (int)RelativeVerticalPosition.TOP_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("bottom-margin-area", (int)RelativeVerticalPosition.BOTTOM_MARGIN);

        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("margin", (int)RelativeHorizontalPosition.MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("page", (int)RelativeHorizontalPosition.PAGE);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("text", (int)RelativeHorizontalPosition.COLUMN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("char", (int)RelativeHorizontalPosition.CHARACTER);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("outer-margin-area", (int)RelativeHorizontalPosition.OUTSIDE_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("inner-margin-area", (int)RelativeHorizontalPosition.INSIDE_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("left-margin-area", (int)RelativeHorizontalPosition.LEFT_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("right-margin-area", (int)RelativeHorizontalPosition.RIGHT_MARGIN);

        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("absolute", (int)HorizontalAlignment.NONE);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("left", (int)HorizontalAlignment.LEFT);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("center", (int)HorizontalAlignment.CENTER);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("right", (int)HorizontalAlignment.RIGHT);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("inside", (int)HorizontalAlignment.INSIDE);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("outside", (int)HorizontalAlignment.OUTSIDE);

        G_VERTICAL_ALIGNMENT_MAP.addEntry("absolute", (int)VerticalAlignment.NONE);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("top", (int)VerticalAlignment.TOP);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("center", (int)VerticalAlignment.CENTER);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("bottom", (int)VerticalAlignment.BOTTOM);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("inside", (int)VerticalAlignment.INSIDE);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("outside", (int)VerticalAlignment.OUTSIDE);

        // This defines all built-in styles as per MS Word 2007.
        // Style names here are as they appear in the file.

        // Character styles.
        G_STYLE_IDENTIFIER_MAP.addEntry("Book Title", (int)StyleIdentifier.BOOK_TITLE);
        G_STYLE_IDENTIFIER_MAP.addEntry("annotation reference", (int)StyleIdentifier.COMMENT_REFERENCE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Default Paragraph Font", (int)StyleIdentifier.DEFAULT_PARAGRAPH_FONT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Emphasis", (int)StyleIdentifier.EMPHASIS);
        G_STYLE_IDENTIFIER_MAP.addEntry("endnote reference", (int)StyleIdentifier.ENDNOTE_REFERENCE);
        G_STYLE_IDENTIFIER_MAP.addEntry("FollowedHyperlink", (int)StyleIdentifier.FOLLOWED_HYPERLINK);
        G_STYLE_IDENTIFIER_MAP.addEntry("footnote reference", (int)StyleIdentifier.FOOTNOTE_REFERENCE);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Acronym", (int)StyleIdentifier.HTML_ACRONYM);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Cite", (int)StyleIdentifier.HTML_CITE);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Code", (int)StyleIdentifier.HTML_CODE);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Definition", (int)StyleIdentifier.HTML_DEFINITION);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Keyboard", (int)StyleIdentifier.HTML_KEYBOARD);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Sample", (int)StyleIdentifier.HTML_SAMPLE);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Typewriter", (int)StyleIdentifier.HTML_TYPEWRITER);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Variable", (int)StyleIdentifier.HTML_VARIABLE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Hyperlink", (int)StyleIdentifier.HYPERLINK);
        G_STYLE_IDENTIFIER_MAP.addEntry("Intense Emphasis", (int)StyleIdentifier.INTENSE_EMPHASIS);
        G_STYLE_IDENTIFIER_MAP.addEntry("Intense Reference", (int)StyleIdentifier.INTENSE_REFERENCE);
        G_STYLE_IDENTIFIER_MAP.addEntry("line number", (int)StyleIdentifier.LINE_NUMBER);
        G_STYLE_IDENTIFIER_MAP.addEntry("page number", (int)StyleIdentifier.PAGE_NUMBER);
        G_STYLE_IDENTIFIER_MAP.addEntry("Placeholder Text", (int)StyleIdentifier.PLACEHOLDER_TEXT);
        // WORDSNET-21757 Added style used in MS Word 2019, Office 365.
        G_STYLE_IDENTIFIER_MAP.addEntry("Smart Link", (int)StyleIdentifier.SMART_LINK);
        G_STYLE_IDENTIFIER_MAP.addEntry("Strong", (int)StyleIdentifier.STRONG);
        G_STYLE_IDENTIFIER_MAP.addEntry("Subtle Emphasis", (int)StyleIdentifier.SUBTLE_EMPHASIS);
        G_STYLE_IDENTIFIER_MAP.addEntry("Subtle Reference", (int)StyleIdentifier.SUBTLE_REFERENCE);

        // Paragraph styles that have linked character styles.
        // RK I'm not really doing anything about linked styles yet.
        G_STYLE_IDENTIFIER_MAP.addEntry("Balloon Text", (int)StyleIdentifier.BALLOON_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text", (int)StyleIdentifier.BODY_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text 2", (int)StyleIdentifier.BODY_TEXT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text 3", (int)StyleIdentifier.BODY_TEXT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text First Indent", (int)StyleIdentifier.BODY_TEXT_1_I);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text First Indent 2", (int)StyleIdentifier.BODY_TEXT_1_I_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text Indent", (int)StyleIdentifier.BODY_TEXT_IND);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text Indent 2", (int)StyleIdentifier.BODY_TEXT_IND_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Body Text Indent 3", (int)StyleIdentifier.BODY_TEXT_IND_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Closing", (int)StyleIdentifier.CLOSING);
        G_STYLE_IDENTIFIER_MAP.addEntry("annotation subject", (int)StyleIdentifier.COMMENT_SUBJECT);
        G_STYLE_IDENTIFIER_MAP.addEntry("annotation text", (int)StyleIdentifier.COMMENT_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Date", (int)StyleIdentifier.DATE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Document Map", (int)StyleIdentifier.DOCUMENT_MAP);
        G_STYLE_IDENTIFIER_MAP.addEntry("E-mail Signature", (int)StyleIdentifier.EMAIL_SIGNATURE);
        G_STYLE_IDENTIFIER_MAP.addEntry("endnote text", (int)StyleIdentifier.ENDNOTE_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("footer", (int)StyleIdentifier.FOOTER);
        G_STYLE_IDENTIFIER_MAP.addEntry("footnote text", (int)StyleIdentifier.FOOTNOTE_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("header", (int)StyleIdentifier.HEADER);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 1", (int)StyleIdentifier.HEADING_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 2", (int)StyleIdentifier.HEADING_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 3", (int)StyleIdentifier.HEADING_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 4", (int)StyleIdentifier.HEADING_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 5", (int)StyleIdentifier.HEADING_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 6", (int)StyleIdentifier.HEADING_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 7", (int)StyleIdentifier.HEADING_7);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 8", (int)StyleIdentifier.HEADING_8);
        G_STYLE_IDENTIFIER_MAP.addEntry("heading 9", (int)StyleIdentifier.HEADING_9);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Address", (int)StyleIdentifier.HTML_ADDRESS);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Bottom of Form", (int)StyleIdentifier.HTML_BOTTOM_OF_FORM);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Preformatted", (int)StyleIdentifier.HTML_PREFORMATTED);
        G_STYLE_IDENTIFIER_MAP.addEntry("HTML Top of Form", (int)StyleIdentifier.HTML_TOP_OF_FORM);
        G_STYLE_IDENTIFIER_MAP.addEntry("Intense Quote", (int)StyleIdentifier.INTENSE_QUOTE);
        G_STYLE_IDENTIFIER_MAP.addEntry("macro", (int)StyleIdentifier.MACRO);
        G_STYLE_IDENTIFIER_MAP.addEntry("Message Header", (int)StyleIdentifier.MESSAGE_HEADER);
        G_STYLE_IDENTIFIER_MAP.addEntry("Note Heading", (int)StyleIdentifier.NOTE_HEADING);
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Text", (int)StyleIdentifier.PLAIN_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Quote", (int)StyleIdentifier.QUOTE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Salutation", (int)StyleIdentifier.SALUTATION);
        G_STYLE_IDENTIFIER_MAP.addEntry("Signature", (int)StyleIdentifier.SIGNATURE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Subtitle", (int)StyleIdentifier.SUBTITLE);
        G_STYLE_IDENTIFIER_MAP.addEntry("Title", (int)StyleIdentifier.TITLE);

        // Paragraph styles.
        G_STYLE_IDENTIFIER_MAP.addEntry("Bibliography", (int)StyleIdentifier.BIBLIOGRAPHY);
        G_STYLE_IDENTIFIER_MAP.addEntry("Block Text", (int)StyleIdentifier.BLOCK_TEXT);
        G_STYLE_IDENTIFIER_MAP.addEntry("caption", (int)StyleIdentifier.CAPTION);
        G_STYLE_IDENTIFIER_MAP.addEntry("envelope address", (int)StyleIdentifier.ENVELOPE_ADDRESS);
        G_STYLE_IDENTIFIER_MAP.addEntry("envelope return", (int)StyleIdentifier.ENVELOPE_RETURN);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 1", (int)StyleIdentifier.INDEX_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 2", (int)StyleIdentifier.INDEX_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 3", (int)StyleIdentifier.INDEX_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 4", (int)StyleIdentifier.INDEX_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 5", (int)StyleIdentifier.INDEX_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 6", (int)StyleIdentifier.INDEX_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 7", (int)StyleIdentifier.INDEX_7);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 8", (int)StyleIdentifier.INDEX_8);
        G_STYLE_IDENTIFIER_MAP.addEntry("index 9", (int)StyleIdentifier.INDEX_9);
        G_STYLE_IDENTIFIER_MAP.addEntry("index heading", (int)StyleIdentifier.INDEX_HEADING);
        G_STYLE_IDENTIFIER_MAP.addEntry("List", (int)StyleIdentifier.LIST);
        G_STYLE_IDENTIFIER_MAP.addEntry("List 2", (int)StyleIdentifier.LIST_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List 3", (int)StyleIdentifier.LIST_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List 4", (int)StyleIdentifier.LIST_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List 5", (int)StyleIdentifier.LIST_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Bullet", (int)StyleIdentifier.LIST_BULLET);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Bullet 2", (int)StyleIdentifier.LIST_BULLET_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Bullet 3", (int)StyleIdentifier.LIST_BULLET_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Bullet 4", (int)StyleIdentifier.LIST_BULLET_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Bullet 5", (int)StyleIdentifier.LIST_BULLET_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Continue", (int)StyleIdentifier.LIST_CONTINUE);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Continue 2", (int)StyleIdentifier.LIST_CONTINUE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Continue 3", (int)StyleIdentifier.LIST_CONTINUE_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Continue 4", (int)StyleIdentifier.LIST_CONTINUE_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Continue 5", (int)StyleIdentifier.LIST_CONTINUE_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Number", (int)StyleIdentifier.LIST_NUMBER);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Number 2", (int)StyleIdentifier.LIST_NUMBER_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Number 3", (int)StyleIdentifier.LIST_NUMBER_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Number 4", (int)StyleIdentifier.LIST_NUMBER_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Number 5", (int)StyleIdentifier.LIST_NUMBER_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Paragraph", (int)StyleIdentifier.LIST_PARAGRAPH);
        G_STYLE_IDENTIFIER_MAP.addEntry("No Spacing", (int)StyleIdentifier.NO_SPACING);
        G_STYLE_IDENTIFIER_MAP.addEntry("Normal", (int)StyleIdentifier.NORMAL);
        G_STYLE_IDENTIFIER_MAP.addEntry("Normal (Web)", (int)StyleIdentifier.NORMAL_WEB);
        G_STYLE_IDENTIFIER_MAP.addEntry("Normal Indent", (int)StyleIdentifier.NORMAL_INDENT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Revision", (int)StyleIdentifier.REVISION);
        G_STYLE_IDENTIFIER_MAP.addEntry("table of authorities", (int)StyleIdentifier.TABLE_OF_AUTHORITIES);
        G_STYLE_IDENTIFIER_MAP.addEntry("table of figures", (int)StyleIdentifier.TABLE_OF_FIGURES);
        G_STYLE_IDENTIFIER_MAP.addEntry("toa heading", (int)StyleIdentifier.TOA_HEADING);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 1", (int)StyleIdentifier.TOC_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 2", (int)StyleIdentifier.TOC_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 3", (int)StyleIdentifier.TOC_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 4", (int)StyleIdentifier.TOC_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 5", (int)StyleIdentifier.TOC_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 6", (int)StyleIdentifier.TOC_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 7", (int)StyleIdentifier.TOC_7);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 8", (int)StyleIdentifier.TOC_8);
        G_STYLE_IDENTIFIER_MAP.addEntry("toc 9", (int)StyleIdentifier.TOC_9);
        G_STYLE_IDENTIFIER_MAP.addEntry("TOC Heading", (int)StyleIdentifier.TOC_HEADING);

        // List styles.
        G_STYLE_IDENTIFIER_MAP.addEntry("Outline List 1", (int)StyleIdentifier.OUTLINE_LIST_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Outline List 2", (int)StyleIdentifier.OUTLINE_LIST_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Outline List 3", (int)StyleIdentifier.OUTLINE_LIST_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("No List", (int)StyleIdentifier.NO_LIST);

        // Table styles.
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid", (int)StyleIdentifier.COLORFUL_GRID);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 1", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 2", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 3", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 4", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 5", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Grid Accent 6", (int)StyleIdentifier.COLORFUL_GRID_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List", (int)StyleIdentifier.COLORFUL_LIST);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 1", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 2", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 3", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 4", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 5", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful List Accent 6", (int)StyleIdentifier.COLORFUL_LIST_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading", (int)StyleIdentifier.COLORFUL_SHADING);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 1", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 2", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 3", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 4", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 5", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Colorful Shading Accent 6", (int)StyleIdentifier.COLORFUL_SHADING_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List", (int)StyleIdentifier.DARK_LIST);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 1", (int)StyleIdentifier.DARK_LIST_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 2", (int)StyleIdentifier.DARK_LIST_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 3", (int)StyleIdentifier.DARK_LIST_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 4", (int)StyleIdentifier.DARK_LIST_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 5", (int)StyleIdentifier.DARK_LIST_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Dark List Accent 6", (int)StyleIdentifier.DARK_LIST_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid", (int)StyleIdentifier.LIGHT_GRID);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 1", (int)StyleIdentifier.LIGHT_GRID_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 2", (int)StyleIdentifier.LIGHT_GRID_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 3", (int)StyleIdentifier.LIGHT_GRID_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 4", (int)StyleIdentifier.LIGHT_GRID_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 5", (int)StyleIdentifier.LIGHT_GRID_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Grid Accent 6", (int)StyleIdentifier.LIGHT_GRID_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List", (int)StyleIdentifier.LIGHT_LIST);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 1", (int)StyleIdentifier.LIGHT_LIST_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 2", (int)StyleIdentifier.LIGHT_LIST_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 3", (int)StyleIdentifier.LIGHT_LIST_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 4", (int)StyleIdentifier.LIGHT_LIST_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 5", (int)StyleIdentifier.LIGHT_LIST_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light List Accent 6", (int)StyleIdentifier.LIGHT_LIST_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading", (int)StyleIdentifier.LIGHT_SHADING);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 1", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 2", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 3", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 4", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 5", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Light Shading Accent 6", (int)StyleIdentifier.LIGHT_SHADING_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1", (int)StyleIdentifier.MEDIUM_GRID_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 1", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 2", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 3", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 4", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 5", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 1 Accent 6", (int)StyleIdentifier.MEDIUM_GRID_1_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2", (int)StyleIdentifier.MEDIUM_GRID_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 1", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 2", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 3", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 4", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 5", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 2 Accent 6", (int)StyleIdentifier.MEDIUM_GRID_2_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3", (int)StyleIdentifier.MEDIUM_GRID_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 1", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 2", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 3", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 4", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 5", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Grid 3 Accent 6", (int)StyleIdentifier.MEDIUM_GRID_3_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1", (int)StyleIdentifier.MEDIUM_LIST_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 1", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 2", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 3", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 4", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 5", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 1 Accent 6", (int)StyleIdentifier.MEDIUM_LIST_1_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2", (int)StyleIdentifier.MEDIUM_LIST_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 1", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 2", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 3", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 4", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 5", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium List 2 Accent 6", (int)StyleIdentifier.MEDIUM_LIST_2_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1", (int)StyleIdentifier.MEDIUM_SHADING_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 1", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 2", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 3", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 4", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 5", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 1 Accent 6", (int)StyleIdentifier.MEDIUM_SHADING_1_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2", (int)StyleIdentifier.MEDIUM_SHADING_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 1", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 2", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 3", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 4", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 5", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Medium Shading 2 Accent 6", (int)StyleIdentifier.MEDIUM_SHADING_2_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table 3D effects 1", (int)StyleIdentifier.TABLE_3_D_EFFECTS_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table 3D effects 2", (int)StyleIdentifier.TABLE_3_D_EFFECTS_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table 3D effects 3", (int)StyleIdentifier.TABLE_3_D_EFFECTS_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Classic 1", (int)StyleIdentifier.TABLE_CLASSIC_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Classic 2", (int)StyleIdentifier.TABLE_CLASSIC_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Classic 3", (int)StyleIdentifier.TABLE_CLASSIC_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Classic 4", (int)StyleIdentifier.TABLE_CLASSIC_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Colorful 1", (int)StyleIdentifier.TABLE_COLORFUL_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Colorful 2", (int)StyleIdentifier.TABLE_COLORFUL_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Colorful 3", (int)StyleIdentifier.TABLE_COLORFUL_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Columns 1", (int)StyleIdentifier.TABLE_COLUMNS_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Columns 2", (int)StyleIdentifier.TABLE_COLUMNS_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Columns 3", (int)StyleIdentifier.TABLE_COLUMNS_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Columns 4", (int)StyleIdentifier.TABLE_COLUMNS_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Columns 5", (int)StyleIdentifier.TABLE_COLUMNS_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Contemporary", (int)StyleIdentifier.TABLE_CONTEMPORARY);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Elegant", (int)StyleIdentifier.TABLE_ELEGANT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid", (int)StyleIdentifier.TABLE_GRID);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 1", (int)StyleIdentifier.TABLE_GRID_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 2", (int)StyleIdentifier.TABLE_GRID_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 3", (int)StyleIdentifier.TABLE_GRID_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 4", (int)StyleIdentifier.TABLE_GRID_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 5", (int)StyleIdentifier.TABLE_GRID_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 6", (int)StyleIdentifier.TABLE_GRID_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 7", (int)StyleIdentifier.TABLE_GRID_7);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Grid 8", (int)StyleIdentifier.TABLE_GRID_8);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 1", (int)StyleIdentifier.TABLE_LIST_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 2", (int)StyleIdentifier.TABLE_LIST_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 3", (int)StyleIdentifier.TABLE_LIST_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 4", (int)StyleIdentifier.TABLE_LIST_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 5", (int)StyleIdentifier.TABLE_LIST_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 6", (int)StyleIdentifier.TABLE_LIST_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 7", (int)StyleIdentifier.TABLE_LIST_7);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table List 8", (int)StyleIdentifier.TABLE_LIST_8);
        G_STYLE_IDENTIFIER_MAP.addEntry("Normal Table", (int)StyleIdentifier.TABLE_NORMAL);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Professional", (int)StyleIdentifier.TABLE_PROFESSIONAL);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Simple 1", (int)StyleIdentifier.TABLE_SIMPLE_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Simple 2", (int)StyleIdentifier.TABLE_SIMPLE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Simple 3", (int)StyleIdentifier.TABLE_SIMPLE_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Subtle 1", (int)StyleIdentifier.TABLE_SUBTLE_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Subtle 2", (int)StyleIdentifier.TABLE_SUBTLE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Theme", (int)StyleIdentifier.TABLE_THEME);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Web 1", (int)StyleIdentifier.TABLE_WEB_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Web 2", (int)StyleIdentifier.TABLE_WEB_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Table Web 3", (int)StyleIdentifier.TABLE_WEB_3);

        // New table styles
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Table 1", (int)StyleIdentifier.PLAIN_TABLE_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Table 2", (int)StyleIdentifier.PLAIN_TABLE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Table 3", (int)StyleIdentifier.PLAIN_TABLE_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Table 4", (int)StyleIdentifier.PLAIN_TABLE_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Plain Table 5", (int)StyleIdentifier.PLAIN_TABLE_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table Light", (int)StyleIdentifier.TABLE_GRID_LIGHT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light", (int)StyleIdentifier.GRID_TABLE_1_LIGHT);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2", (int)StyleIdentifier.GRID_TABLE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3", (int)StyleIdentifier.GRID_TABLE_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4", (int)StyleIdentifier.GRID_TABLE_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark", (int)StyleIdentifier.GRID_TABLE_5_DARK);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 1", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 1", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 1", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 1", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 1", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 1", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 1", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 2", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 2", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 2", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 2", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 2", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 2", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 2", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 3", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 3", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 3", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 3", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 3", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 3", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 3", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 4", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 4", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 4", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 4", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 4", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 4", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 4", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 5", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 5", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 5", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 5", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 5", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 5", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 5", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 1 Light Accent 6", (int)StyleIdentifier.GRID_TABLE_1_LIGHT_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 2 Accent 6", (int)StyleIdentifier.GRID_TABLE_2_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 3 Accent 6", (int)StyleIdentifier.GRID_TABLE_3_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 4 Accent 6", (int)StyleIdentifier.GRID_TABLE_4_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 5 Dark Accent 6", (int)StyleIdentifier.GRID_TABLE_5_DARK_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 6 Colorful Accent 6", (int)StyleIdentifier.GRID_TABLE_6_COLORFUL_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Grid Table 7 Colorful Accent 6", (int)StyleIdentifier.GRID_TABLE_7_COLORFUL_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light", (int)StyleIdentifier.LIST_TABLE_1_LIGHT);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2", (int)StyleIdentifier.LIST_TABLE_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3", (int)StyleIdentifier.LIST_TABLE_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4", (int)StyleIdentifier.LIST_TABLE_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark", (int)StyleIdentifier.LIST_TABLE_5_DARK);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 1", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 1", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 1", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 1", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 1", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 1", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 1", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_1);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 2", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 2", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 2", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 2", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 2", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 2", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 2", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_2);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 3", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 3", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 3", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 3", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 3", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 3", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 3", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_3);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 4", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 4", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 4", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 4", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 4", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 4", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 4", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_4);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 5", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 5", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 5", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 5", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 5", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 5", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 5", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_5);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 1 Light Accent 6", (int)StyleIdentifier.LIST_TABLE_1_LIGHT_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 2 Accent 6", (int)StyleIdentifier.LIST_TABLE_2_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 3 Accent 6", (int)StyleIdentifier.LIST_TABLE_3_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 4 Accent 6", (int)StyleIdentifier.LIST_TABLE_4_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 5 Dark Accent 6", (int)StyleIdentifier.LIST_TABLE_5_DARK_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 6 Colorful Accent 6", (int)StyleIdentifier.LIST_TABLE_6_COLORFUL_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("List Table 7 Colorful Accent 6", (int)StyleIdentifier.LIST_TABLE_7_COLORFUL_ACCENT_6);
        G_STYLE_IDENTIFIER_MAP.addEntry("Mention", (int)StyleIdentifier.MENTION);
        G_STYLE_IDENTIFIER_MAP.addEntry("Smart Hyperlink", (int)StyleIdentifier.SMART_HYPERLINK);
        G_STYLE_IDENTIFIER_MAP.addEntry("Hashtag", (int)StyleIdentifier.HASHTAG);
        G_STYLE_IDENTIFIER_MAP.addEntry("Unresolved Mention", (int)StyleIdentifier.UNRESOLVED_MENTION);

        // This contains all style names that are different between DOCX/WordML and model/DOC/RTF.
        // First entry is name in DOCX/WordML, second entry is name in the Model/DOC/RTF.

        // Character styles.
        G_STYLE_NAME_MAP.addEntry("annotation reference", "Comment Reference");
        G_STYLE_NAME_MAP.addEntry("endnote reference", "Endnote Reference");
        G_STYLE_NAME_MAP.addEntry("footnote reference", "Footnote Reference");
        G_STYLE_NAME_MAP.addEntry("line number", "Line Number");
        G_STYLE_NAME_MAP.addEntry("page number", "Page Number");

        // Paragraph styles.
        G_STYLE_NAME_MAP.addEntry("annotation subject", "Comment Subject");
        G_STYLE_NAME_MAP.addEntry("annotation text", "Comment Text");
        G_STYLE_NAME_MAP.addEntry("caption", "Caption");
        G_STYLE_NAME_MAP.addEntry("endnote text", "Endnote Text");
        G_STYLE_NAME_MAP.addEntry("envelope address", "Envelope Address");
        G_STYLE_NAME_MAP.addEntry("envelope return", "Envelope Return");
        G_STYLE_NAME_MAP.addEntry("footer", "Footer");
        G_STYLE_NAME_MAP.addEntry("footnote text", "Footnote Text");
        G_STYLE_NAME_MAP.addEntry("header", "Header");
        G_STYLE_NAME_MAP.addEntry("heading 1", "Heading 1");
        G_STYLE_NAME_MAP.addEntry("heading 2", "Heading 2");
        G_STYLE_NAME_MAP.addEntry("heading 3", "Heading 3");
        G_STYLE_NAME_MAP.addEntry("heading 4", "Heading 4");
        G_STYLE_NAME_MAP.addEntry("heading 5", "Heading 5");
        G_STYLE_NAME_MAP.addEntry("heading 6", "Heading 6");
        G_STYLE_NAME_MAP.addEntry("heading 7", "Heading 7");
        G_STYLE_NAME_MAP.addEntry("heading 8", "Heading 8");
        G_STYLE_NAME_MAP.addEntry("heading 9", "Heading 9");
        G_STYLE_NAME_MAP.addEntry("index 1", "Index 1");
        G_STYLE_NAME_MAP.addEntry("index 2", "Index 2");
        G_STYLE_NAME_MAP.addEntry("index 3", "Index 3");
        G_STYLE_NAME_MAP.addEntry("index 4", "Index 4");
        G_STYLE_NAME_MAP.addEntry("index 5", "Index 5");
        G_STYLE_NAME_MAP.addEntry("index 6", "Index 6");
        G_STYLE_NAME_MAP.addEntry("index 7", "Index 7");
        G_STYLE_NAME_MAP.addEntry("index 8", "Index 8");
        G_STYLE_NAME_MAP.addEntry("index 9", "Index 9");
        G_STYLE_NAME_MAP.addEntry("index heading", "Index Heading");
        G_STYLE_NAME_MAP.addEntry("macro", "Macro Text");
        G_STYLE_NAME_MAP.addEntry("note heading", "Note Heading");
        G_STYLE_NAME_MAP.addEntry("table of authorities", "Table of Authorities");
        G_STYLE_NAME_MAP.addEntry("table of figures", "Table of Figures");
        G_STYLE_NAME_MAP.addEntry("toa heading", "TOA Heading");
        G_STYLE_NAME_MAP.addEntry("toc 1", "TOC 1");
        G_STYLE_NAME_MAP.addEntry("toc 2", "TOC 2");
        G_STYLE_NAME_MAP.addEntry("toc 3", "TOC 3");
        G_STYLE_NAME_MAP.addEntry("toc 4", "TOC 4");
        G_STYLE_NAME_MAP.addEntry("toc 5", "TOC 5");
        G_STYLE_NAME_MAP.addEntry("toc 6", "TOC 6");
        G_STYLE_NAME_MAP.addEntry("toc 7", "TOC 7");
        G_STYLE_NAME_MAP.addEntry("toc 8", "TOC 8");
        G_STYLE_NAME_MAP.addEntry("toc 9", "TOC 9");

        // List styles.
        G_STYLE_NAME_MAP.addEntry("Outline List 1", "1 / a / i");
        G_STYLE_NAME_MAP.addEntry("Outline List 2", "1 / 1.1 / 1.1.1");
        G_STYLE_NAME_MAP.addEntry("Outline List 3", "Article / Section");

        // Table styles.
        G_STYLE_NAME_MAP.addEntry("Normal Table", "Table Normal"); //TableNormal

        G_SCREEN_SIZE_MAP.addEntry("1024x768", (int)ScreenSize.SIZE_1024_X_768);
        G_SCREEN_SIZE_MAP.addEntry("1152x882", (int)ScreenSize.SIZE_1152_X_882);
        G_SCREEN_SIZE_MAP.addEntry("1152x900", (int)ScreenSize.SIZE_1152_X_900);
        G_SCREEN_SIZE_MAP.addEntry("1280x1024", (int)ScreenSize.SIZE_1280_X_1024);
        G_SCREEN_SIZE_MAP.addEntry("1600x1200", (int)ScreenSize.SIZE_1600_X_1200);
        G_SCREEN_SIZE_MAP.addEntry("1800x1440", (int)ScreenSize.SIZE_1800_X_1440);
        G_SCREEN_SIZE_MAP.addEntry("1920x1200", (int)ScreenSize.SIZE_1920_X_1200);
        G_SCREEN_SIZE_MAP.addEntry("544x376", (int)ScreenSize.SIZE_544_X_376);
        G_SCREEN_SIZE_MAP.addEntry("640x480", (int)ScreenSize.SIZE_640_X_480);
        G_SCREEN_SIZE_MAP.addEntry("720x512", (int)ScreenSize.SIZE_720_X_512);
        G_SCREEN_SIZE_MAP.addEntry("800x600", (int)ScreenSize.SIZE_800_X_600);

    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"flatBorders",
		"noBorder",
		"cols",
		"rows",
		"band1Horz",
		"band1Vert",
		"band2Horz",
		"band2Vert",
		"firstCol",
		"firstRow",
		"lastCol",
		"lastRow",
		"neCell",
		"nwCell",
		"seCell",
		"swCell",
		"wholeTable",
		"pct",
		"dxa",
		"auto",
		"nil",
		"clear",
		"solid",
		"horzStripe",
		"horz-stripe",
		"vertStripe",
		"vert-stripe",
		"reverseDiagStripe",
		"reverse-diag-stripe",
		"diagStripe",
		"diag-stripe",
		"horzCross",
		"horz-cross",
		"diagCross",
		"diag-cross",
		"thinHorzStripe",
		"thin-horz-stripe",
		"thinVertStripe",
		"thin-vert-stripe",
		"thinReverseDiagStripe",
		"thin-reverse-diag-stripe",
		"thinDiagStripe",
		"thin-diag-stripe",
		"thinHorzCross",
		"thin-horz-cross",
		"thinDiagCross",
		"thin-diag-cross",
		"pct5",
		"pct-5",
		"pct10",
		"pct-10",
		"pct12",
		"pct-12",
		"pct15",
		"pct-15",
		"pct20",
		"pct-20",
		"pct25",
		"pct-25",
		"pct30",
		"pct-30",
		"pct35",
		"pct-35",
		"pct37",
		"pct-37",
		"pct40",
		"pct-40",
		"pct45",
		"pct-45",
		"pct50",
		"pct-50",
		"pct55",
		"pct-55",
		"pct60",
		"pct-60",
		"pct62",
		"pct-62",
		"pct65",
		"pct-65",
		"pct70",
		"pct-70",
		"pct75",
		"pct-75",
		"pct80",
		"pct-80",
		"pct85",
		"pct-85",
		"pct87",
		"pct-87",
		"pct90",
		"pct-90",
		"pct95",
		"pct-95",
		"lr",
		"btLr",
		"bt-lr",
		"tb",
		"lrTb",
		"lr-tb",
		"tbV",
		"lrTbV",
		"lr-tb-v",
		"rlV",
		"tbRlV",
		"tb-rl-v",
		"rl",
		"tbRl",
		"tb-rl",
		"tb-lr-v",
		"tbLrV",
		"lrV"
	);

}

