// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts miscellaneous enumerated types between enum and DOCX string.
/// </summary>
class DocxEnum
{
    static String contentTypeToExtension(String contentType)
    {
        switch (gStringSwitchMap.of(contentType))
        {
            case /*DocxContentType.DOC*/0: return ".doc";
            case/*DocxContentType.XLS*/1: return ".xls";
            case/*DocxContentType.PPT*/2: return ".ppt";
            case/*DocxContentType.VSD*/3: return ".vsd";
            case/*DocxContentType.DOCX*/4: return ".docx";
            case /*DocxContentType.DOTX*/5: return ".dotx";
            case/*DocxContentType.DOCM*/6: return ".docm";
            case/*DocxContentType.DOTM*/7: return ".dotm";
            case/*DocxContentType.XLSX*/8: return ".xlsx";
            case/*DocxContentType.XLSM*/9: return ".xlsm";
            case/*DocxContentType.XLSB*/10: return ".xlsb";
            case/*DocxContentType.PPTX*/11: return ".pptx";
            case/*DocxContentType.PPTM*/12: return ".pptm";
            case /*DocxContentType.PPSX*/13: return ".ppsx";
            case/*DocxContentType.PPSM*/14: return ".ppsm";
            case/*DocxContentType.SLDX*/15: return ".sldx";
            case/*DocxContentType.SLDM*/16: return ".sldm";
            case/*DocxContentType.VSDX*/17: return ".vsdx";
            default: return ".bin";
        }
    }

    static /*Ligature*/int xmlToLigature(String value)
    {
        if ("none".equals(value))
            return Ligature.NONE;
        else if ("all".equals(value))
            return Ligature.STANDARD | Ligature.CONTEXTUAL | Ligature.HISTORICAL | Ligature.DISCRETIONAL;

        /*Ligature*/int ligature = Ligature.NONE;

        if (StringUtil.contains(value, "standard", true))
            ligature |= Ligature.STANDARD;

        if (StringUtil.contains(value, "contextual", true))
            ligature |= Ligature.CONTEXTUAL;

        if (StringUtil.contains(value, "historical", true))
            ligature |= Ligature.HISTORICAL;

        if (StringUtil.contains(value, "discretional", true))
            ligature |= Ligature.DISCRETIONAL;

        return ligature;
    }

    static String ligatureToXml(/*Ligature*/int ligature)
    {
        if (ligature == Ligature.NONE)
            return "none";
        else if (ligature == Ligature.ALL)
            return "all";

        StringBuilder sb = new StringBuilder();

        if ((ligature & Ligature.STANDARD) != 0)
            msStringBuilder.append(sb, "Standard");

        if ((ligature & Ligature.CONTEXTUAL) != 0)
            msStringBuilder.append(sb, "Contextual");

        if ((ligature & Ligature.HISTORICAL) != 0)
            msStringBuilder.append(sb, "Historical");

        if ((ligature & Ligature.DISCRETIONAL) != 0)
            msStringBuilder.append(sb, "Discretional");

        String value = sb.toString();

        // Make first letter lower.
        return msString.format("{0}{1}", value.substring((0), (0) + (1)).toLowerCase(), value.substring(1));
    }

    static /*NumForm*/int xmlToNumForm(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"default"*/18: return NumForm.DEFAULT;
            case /*"lining"*/19: return NumForm.LINING;
            case /*"oldStyle"*/20: return NumForm.OLD_STYLE;
            default: return NumForm.DEFAULT;
        }
    }

    static String numFormToXml(/*NumForm*/int numForm)
    {
        switch (numForm)
        {
            case NumForm.DEFAULT: return "default";
            case NumForm.LINING: return "lining";
            case NumForm.OLD_STYLE: return "oldStyle";
            default: return "";
        }
    }

    static /*NumSpacing*/int xmlToNumSpacing(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"default"*/18: return NumSpacing.DEFAULT;
            case /*"proportional"*/21: return NumSpacing.PROPORTIONAL;
            case /*"tabular"*/22: return NumSpacing.TABULAR;
            default: return NumSpacing.DEFAULT;
        }
    }

    static String numSpacingToXml(/*NumSpacing*/int numSpacing)
    {
        switch (numSpacing)
        {
            case NumSpacing.DEFAULT: return "default";
            case NumSpacing.PROPORTIONAL: return "proportional";
            case NumSpacing.TABULAR: return "tabular";
            default: return "";
        }
    }

    /// <summary>
    /// Converts Xml value to StylisticSets.
    /// </summary>
    static /*StylisticSets*/int xmlToStylisticSets(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"1"*/23: return StylisticSets.SET_01;
            case /*"2"*/24: return StylisticSets.SET_02;
            case /*"3"*/25: return StylisticSets.SET_03;
            case /*"4"*/26: return StylisticSets.SET_04;
            case /*"5"*/27: return StylisticSets.SET_05;
            case /*"6"*/28: return StylisticSets.SET_06;
            case /*"7"*/29: return StylisticSets.SET_07;
            case /*"8"*/30: return StylisticSets.SET_08;
            case /*"9"*/31: return StylisticSets.SET_09;
            case /*"10"*/32: return StylisticSets.SET_10;
            case /*"11"*/33: return StylisticSets.SET_11;
            case /*"12"*/34: return StylisticSets.SET_12;
            case /*"13"*/35: return StylisticSets.SET_13;
            case /*"14"*/36: return StylisticSets.SET_14;
            case /*"15"*/37: return StylisticSets.SET_15;
            case /*"16"*/38: return StylisticSets.SET_16;
            case /*"17"*/39: return StylisticSets.SET_17;
            case /*"18"*/40: return StylisticSets.SET_18;
            case /*"19"*/41: return StylisticSets.SET_19;
            case /*"20"*/42: return StylisticSets.SET_20;
            default: return StylisticSets.DEFAULT;
        }
    }

    /// <summary>
    /// Converts StylisticSets to Xml value.
    /// </summary>
    static String stylisticSetsToXml(/*StylisticSets*/int stylisticSets)
    {
        switch (stylisticSets)
        {
            case StylisticSets.SET_01: return "1";
            case StylisticSets.SET_02: return "2";
            case StylisticSets.SET_03: return "3";
            case StylisticSets.SET_04: return "4";
            case StylisticSets.SET_05: return "5";
            case StylisticSets.SET_06: return "6";
            case StylisticSets.SET_07: return "7";
            case StylisticSets.SET_08: return "8";
            case StylisticSets.SET_09: return "9";
            case StylisticSets.SET_10: return "10";
            case StylisticSets.SET_11: return "11";
            case StylisticSets.SET_12: return "12";
            case StylisticSets.SET_13: return "13";
            case StylisticSets.SET_14: return "14";
            case StylisticSets.SET_15: return "15";
            case StylisticSets.SET_16: return "16";
            case StylisticSets.SET_17: return "17";
            case StylisticSets.SET_18: return "18";
            case StylisticSets.SET_19: return "19";
            case StylisticSets.SET_20: return "20";
            default: return "0";
        }
    }

    static /*TextFormFieldType*/int docxToTextFormFieldType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"calculated"*/43: return TextFormFieldType.CALCULATED;
            case /*"currentDate"*/44: return TextFormFieldType.CURRENT_DATE;
            case /*"currentTime"*/45: return TextFormFieldType.CURRENT_TIME;
            case /*"date"*/46: return TextFormFieldType.DATE;
            case /*"number"*/47: return TextFormFieldType.NUMBER;
            case /*""*/48: return TextFormFieldType.REGULAR;
            case /*"regular"*/49: return TextFormFieldType.REGULAR;
            default: return TextFormFieldType.REGULAR;
        }
    }

    static String textFormFieldTypeToDocx(/*TextFormFieldType*/int value)
    {
        switch (value)
        {
            case TextFormFieldType.CALCULATED: return "calculated";
            case TextFormFieldType.CURRENT_DATE: return "currentDate";
            case TextFormFieldType.CURRENT_TIME: return "currentTime";
            case TextFormFieldType.DATE: return "date";
            case TextFormFieldType.NUMBER: return "number";
            case TextFormFieldType.REGULAR: return "";
            default: return "";
        }
    }

    static /*NumberStyle*/int docxToNumberStyle(String value)
    {
        return (/*NumberStyle*/int)G_NUMBER_STYLE_MAP.getValue(value, (int)NumberStyle.ARABIC);
    }

    static String numberStyleToDocx(/*NumberStyle*/int value)
    {
        return G_NUMBER_STYLE_MAP.getValue((int)value, "");
    }

    static /*FontFamily*/int docxToFontFamily(String value)
    {
        return (/*FontFamily*/int)G_FONT_FAMILY_MAP.getValue(value, (int)FontFamily.AUTO);
    }

    static String fontFamilyToDocx(/*FontFamily*/int value)
    {
        return G_FONT_FAMILY_MAP.getValue((int)value, "");
    }

    static /*LineStyle*/int docxToLineStyle(String value)
    {
        return (/*LineStyle*/int)G_LINE_STYLE_MAP.getValue(value, (int)LineStyle.NONE);
    }

    static String lineStyleToDocx(/*LineStyle*/int value)
    {
        switch (value)
        {
            // andrnosk: WORDSNET-7167 Hairline is not in spec and it seems Hairline made equivalent to Single
            // Made in the same way as WmlEnum.LineStyleToWml.
            case LineStyle.HAIRLINE: return "single";
            default:
                return G_LINE_STYLE_MAP.getValue((int)value, "none");
        }
    }

    static /*StyleType*/int docxToStyleType(String value)
    {
        return (/*StyleType*/int)G_STYLE_TYPE_MAP.getValue(value, (int)StyleType.PARAGRAPH);
    }

    static String styleTypeToDocx(/*StyleType*/int value)
    {
        return G_STYLE_TYPE_MAP.getValue((int)value, "paragraph");
    }

    static /*FootnoteSeparatorType*/int docxToFootnoteSeparatorType(String value, boolean isEndnote)
    {
        StringToIntBidirectionalMap map = isEndnote ? G_ENDNOTE_SEPARATOR_TYPE_MAP : G_FOOTNOTE_SEPARATOR_TYPE_MAP;
        return (/*FootnoteSeparatorType*/int)map.getValue(value, -1);
    }

    static String footnoteSeparatorTypeToDocx(/*FootnoteSeparatorType*/int value, boolean isEndnote)
    {
        StringToIntBidirectionalMap map = isEndnote ? G_ENDNOTE_SEPARATOR_TYPE_MAP : G_FOOTNOTE_SEPARATOR_TYPE_MAP;
        return map.getValue((int)value, "");
    }

    static String docPartBehaviorToDocx(/*BuildingBlockBehavior*/int value)
    {
        return G_DOC_PART_BEHAVIOR_MAP.getValue((int)value, "");
    }

    static /*BuildingBlockBehavior*/int docxToDocPartBehavior(String value)
    {
        return (/*BuildingBlockBehavior*/int)G_DOC_PART_BEHAVIOR_MAP.getValue(value, (int)BuildingBlockBehavior.DEFAULT);
    }

    static String docPartGalleryToDocx(/*BuildingBlockGallery*/int value)
    {
        return G_DOC_PART_GALLERY_MAP.getValue((int)value, "any");
    }

    static /*BuildingBlockGallery*/int docxToDocPartGallery(String value)
    {
        return (/*BuildingBlockGallery*/int)G_DOC_PART_GALLERY_MAP.getValue(value, (int)BuildingBlockGallery.ALL);
    }

    static String docPartTypeToDocx(/*BuildingBlockType*/int value)
    {
        return G_DOC_PART_TYPE_MAP.getValue((int)value, "");
    }

    static /*BuildingBlockType*/int docxToDocPartType(String value)
    {
        return (/*BuildingBlockType*/int)G_DOC_PART_TYPE_MAP.getValue(value, (int)BuildingBlockType.NONE);
    }

    static String calendarTypeToDocx(/*SdtCalendarType*/int value)
    {
        return G_CALENDAR_TYPE_MAP.getValue((int)value, "");
    }

    static /*SdtCalendarType*/int docxToCaledarType(String value)
    {
        return (/*SdtCalendarType*/int)G_CALENDAR_TYPE_MAP.getValue(value, (int)SdtCalendarType.NONE);
    }

    static String sdtLockTypeToDocx(/*SdtLockType*/int value)
    {
        return G_SDT_LOCK_TYPE_MAP.getValue((int)value, "");
    }

    static /*SdtLockType*/int docxToSdtLockType(String value)
    {
        return (/*SdtLockType*/int)G_SDT_LOCK_TYPE_MAP.getValue(value, (int)SdtLockType.UNLOCKED);
    }

    static /*SdtDateStorageFormat*/int docxToSdtDateStorageFormat(String value)
    {
        return (/*SdtDateStorageFormat*/int)G_SDT_DATE_STORAGE_FORMAT_MAP.getValue(value, (int)SdtDateStorageFormat.DEFAULT);
    }

    static String sdtDateStorageFormatToDocx(/*SdtDateStorageFormat*/int value)
    {
        return G_SDT_DATE_STORAGE_FORMAT_MAP.getValue((int)value, "dateTime");
    }

    static /*SdtAppearance*/int docxToSdtAppearance(String value)
    {
        return (/*SdtAppearance*/int)G_SDT_APPEARANCE_MAP.getValue(value, (int)SdtAppearance.DEFAULT);
    }

    static String sdtAppearanceToDocx(/*SdtAppearance*/int value)
    {
        return G_SDT_APPEARANCE_MAP.getValue((int)value, "boundingBox");
    }

    static String mathJustificationToDocx(/*OfficeMathJustification*/int value)
    {
        return G_MATH_JUSTIFICATION_MAP.getValue((int)value, "");
    }

    static /*OfficeMathJustification*/int docxToMathJustification(String value)
    {
        return (/*OfficeMathJustification*/int)G_MATH_JUSTIFICATION_MAP.getValue(value, (int)OfficeMathJustification.DEFAULT);
    }

    static String mathScriptTypeToDocx(/*MathScript*/int value)
    {
        return G_MATH_SCRIPT_MAP.getValue((int)value, "");
    }

    static /*MathScript*/int docxToMathScriptType(String value)
    {
        return (/*MathScript*/int)G_MATH_SCRIPT_MAP.getValue(value, (int)MathScript.DEFAULT);
    }

    static String mathStyleTypeToDocx(/*MathStyle*/int value)
    {
        return G_MATH_STYLE_MAP.getValue((int)value, "");
    }

    static /*MathStyle*/int docxToMathStyleType(String value)
    {
        return (/*MathStyle*/int)G_MATH_STYLE_MAP.getValue(value, (int)MathStyle.DEFAULT);
    }

    static String mathPositionTypeToDocx(/*MathPosition*/int value)
    {
        return G_MATH_POSITION_MAP.getValue((int)value, "");
    }

    static /*MathPosition*/int docxToMathPositionType(String value)
    {
        return (/*MathPosition*/int)G_MATH_POSITION_MAP.getValue(value, (int)MathPosition.DEFAULT);
    }

    static String mathDelimiterShapeToDocx(/*MathDelimiterShape*/int value)
    {
        return G_MATH_DELIMITER_SHAPE_MAP.getValue((int)value, "");
    }

    static /*MathDelimiterShape*/int docxToMathDelimiterShape(String value)
    {
        return (/*MathDelimiterShape*/int)G_MATH_DELIMITER_SHAPE_MAP.getValue(value, (int)MathDelimiterShape.DEFAULT);
    }

    static String mathBaseJustificationToDocx(/*MathBaseJustification*/int value, boolean isIso29500)
    {

        switch (value)
        {
            case MathBaseJustification.TOP:
                return "top";
            case MathBaseJustification.BOTTOM:
                return (isIso29500) ? "bottom" : "bot";
            default:
                return "center";
        }
    }

    static /*MathBaseJustification*/int docxToMathBaseJustification(String value,
        OoxmlComplianceInfo complianceInfo)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"top"*/50:
                return MathBaseJustification.TOP;
            case /*"bottom"*/51:
                if (complianceInfo != null)
                    complianceInfo.markAsIsoTransitional();
                return MathBaseJustification.BOTTOM;
            case /*"bot"*/52:
                return MathBaseJustification.BOTTOM;
            case /*"center"*/53:
                return MathBaseJustification.CENTER;
            default:
                return MathBaseJustification.DEFAULT;
        }
    }

    static String mathVerticalJustificationTypeToDocx(/*MathVerticalJustification*/int value)
    {
        return G_MATH_VERTICAL_JUSTIFICATION_TYPE_MAP.getValue((int)value, "");
    }

    static /*MathVerticalJustification*/int docxToMathVerticalJustificationType(String value)
    {
        return (/*MathVerticalJustification*/int)G_MATH_VERTICAL_JUSTIFICATION_TYPE_MAP.getValue(value, (int)MathVerticalJustification.BOTTOM);
    }

    static String mathFractionTypeToDocx(/*MathFractionType*/int value)
    {
        return G_MATH_FRACTION_TYPE_MAP.getValue((int)value, "");
    }

    static /*MathFractionType*/int docxToMathFractionType(String value)
    {
        return (/*MathFractionType*/int)G_MATH_FRACTION_TYPE_MAP.getValue(value, (int)MathFractionType.DEFAULT);
    }

    static String mathLimitLocationToDocx(/*MathLimitLocation*/int value)
    {
        return G_MATH_LIMIT_LOCATION_MAP.getValue((int)value, "");
    }

    static /*MathLimitLocation*/int docxToMathLimitLocation(String value)
    {
        return (/*MathLimitLocation*/int)G_MATH_LIMIT_LOCATION_MAP.getValue(value, (int)MathLimitLocation.UNDER_OVER);
    }

    static String absolutePositionTabAlignmentToDocx(/*AbsolutePositionTabAlignment*/int value)
    {
        return G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*AbsolutePositionTabAlignment*/int docxToAbsolutePositionTabAlignment(String value)
    {
        return (/*AbsolutePositionTabAlignment*/int)G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP.getValue(value, (int)AbsolutePositionTabAlignment.DEFAULT);
    }

    static String absolutePositionTabLeaderCharToDocx(/*AbsolutePositionTabLeaderChar*/int value)
    {
        return G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.getValue((int)value, "");
    }

    static /*AbsolutePositionTabLeaderChar*/int docxToAbsolutePositionTabLeaderChar(String value)
    {
        return (/*AbsolutePositionTabLeaderChar*/int)G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.getValue(value, (int)AbsolutePositionTabLeaderChar.DEFAULT);
    }

    static String absolutePositionTabPositioningBaseToDocx(/*AbsolutePositionTabPositioningBase*/int value)
    {
        return G_ABSOLUTE_POSITION_TAB_RELATIVE_TO_MAP.getValue((int)value, "");
    }

    static /*AbsolutePositionTabPositioningBase*/int docxToAbsolutePositionTabPositioningBase(String value)
    {
        return (/*AbsolutePositionTabPositioningBase*/int)G_ABSOLUTE_POSITION_TAB_RELATIVE_TO_MAP.getValue(value, (int)AbsolutePositionTabPositioningBase.DEFAULT);
    }

    static String contactIdentityProviderToDocx(/*ContactIdentityProvider*/int value)
    {
        return G_CONTACT_IDENTITY_PROVIDER_MAP.getValue((int)value, "None");
    }

    static /*ContactIdentityProvider*/int docxToContactIdentityProvider(String value)
    {
        return (/*ContactIdentityProvider*/int)G_CONTACT_IDENTITY_PROVIDER_MAP.getValue(value,
            (int)ContactIdentityProvider.NONE);
    }

    static /*TaskPaneDockState*/int docxToTaskPaneDockState(String value)
    {
        return (/*TaskPaneDockState*/int)G_TASK_PANE_DOCK_STATE_MAP.getValue(value, (int)TaskPaneDockState.RIGHT);
    }

    static String taskPaneDockStateToDocx(/*TaskPaneDockState*/int dockState)
    {
        return G_TASK_PANE_DOCK_STATE_MAP.getValue((int)dockState, "right");
    }

    static /*WebExtensionStoreType*/int docxToWebExtensionStoreType(String value)
    {
        return (/*WebExtensionStoreType*/int)G_WEB_EXTENSION_STORE_TYPE_MAP.getValue(value, (int)WebExtensionStoreType.DEFAULT);
    }

    static String webExtensionStoreTypeToDocx(/*WebExtensionStoreType*/int storeType)
    {
        return G_WEB_EXTENSION_STORE_TYPE_MAP.getValue((int)storeType, "spcatalog");
    }

    static /*WebExtensionBindingType*/int docxToWebExtensionBindingType(String value)
    {
        return (/*WebExtensionBindingType*/int)G_WEB_EXTENSION_BINDING_TYPE_MAP.getValue(value, (int)WebExtensionBindingType.DEFAULT);
    }

    static String webExtensionBindingTypeToDocx(/*WebExtensionBindingType*/int bindingType)
    {
        return G_WEB_EXTENSION_BINDING_TYPE_MAP.getValue((int)bindingType, "matrix");
    }

    //JAVA: declarations moved here to exclude java's illegal forward reference.

    private static /*final*/ StringToIntBidirectionalMap G_NUMBER_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FONT_FAMILY_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LINE_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_STYLE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FOOTNOTE_SEPARATOR_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ENDNOTE_SEPARATOR_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_DOC_PART_BEHAVIOR_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_DOC_PART_GALLERY_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_DOC_PART_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CALENDAR_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SDT_LOCK_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SDT_DATE_STORAGE_FORMAT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SDT_APPEARANCE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_JUSTIFICATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_SCRIPT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_POSITION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_DELIMITER_SHAPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_VERTICAL_JUSTIFICATION_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_FRACTION_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_LIMIT_LOCATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ABSOLUTE_POSITION_TAB_RELATIVE_TO_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CONTACT_IDENTITY_PROVIDER_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TASK_PANE_DOCK_STATE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WEB_EXTENSION_STORE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WEB_EXTENSION_BINDING_TYPE_MAP = new StringToIntBidirectionalMap();

    static/* DocxEnum()*/
    {
        // It seems that all border styles that are available via Borders and Shading menu in MS Word 2003 are here.
        G_LINE_STYLE_MAP.addEntry("none", (int)LineStyle.NONE);
        G_LINE_STYLE_MAP.addEntry("single", (int)LineStyle.SINGLE);
        G_LINE_STYLE_MAP.addEntry("thick", (int)LineStyle.THICK);
        G_LINE_STYLE_MAP.addEntry("double", (int)LineStyle.DOUBLE);
        G_LINE_STYLE_MAP.addEntry("dotted", (int)LineStyle.DOT);
        G_LINE_STYLE_MAP.addEntry("dashed", (int)LineStyle.DASH_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("dotDash", (int)LineStyle.DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("dotDotDash", (int)LineStyle.DOT_DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("triple", (int)LineStyle.TRIPLE);
        G_LINE_STYLE_MAP.addEntry("thinThickSmallGap", (int)LineStyle.THIN_THICK_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThinSmallGap", (int)LineStyle.THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickThinSmallGap", (int)LineStyle.THIN_THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickMediumGap", (int)LineStyle.THIN_THICK_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThinMediumGap", (int)LineStyle.THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickThinMediumGap", (int)LineStyle.THIN_THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickLargeGap", (int)LineStyle.THIN_THICK_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThinLargeGap", (int)LineStyle.THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickThinLargeGap", (int)LineStyle.THIN_THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("wave", (int)LineStyle.WAVE);
        G_LINE_STYLE_MAP.addEntry("doubleWave", (int)LineStyle.DOUBLE_WAVE);
        G_LINE_STYLE_MAP.addEntry("dashSmallGap", (int)LineStyle.DASH_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("dashDotStroked", (int)LineStyle.DASH_DOT_STROKER);
        G_LINE_STYLE_MAP.addEntry("threeDEmboss", (int)LineStyle.EMBOSS_3_D);
        G_LINE_STYLE_MAP.addEntry("threeDEngrave", (int)LineStyle.ENGRAVE_3_D);
        G_LINE_STYLE_MAP.addEntry("outset", (int)LineStyle.OUTSET);
        G_LINE_STYLE_MAP.addEntry("inset", (int)LineStyle.INSET);
        // page border art
        G_LINE_STYLE_MAP.addEntry("apples", (int)PageBorderArt.APPLES);
        G_LINE_STYLE_MAP.addEntry("archedScallops", (int)PageBorderArt.ARCHED_SCALLOPS);
        G_LINE_STYLE_MAP.addEntry("babyPacifier", (int)PageBorderArt.BABY_PACIFIER);
        G_LINE_STYLE_MAP.addEntry("babyRattle", (int)PageBorderArt.BABY_RATTLE);
        G_LINE_STYLE_MAP.addEntry("balloons3Colors", (int)PageBorderArt.BALLOONS_3_COLORS);
        G_LINE_STYLE_MAP.addEntry("balloonsHotAir", (int)PageBorderArt.BALLOONS_HOT_AIR);
        G_LINE_STYLE_MAP.addEntry("basicBlackDashes", (int)PageBorderArt.BASIC_BLACK_DASHES);
        G_LINE_STYLE_MAP.addEntry("basicBlackDots", (int)PageBorderArt.BASIC_BLACK_DOTS);
        G_LINE_STYLE_MAP.addEntry("basicBlackSquares", (int)PageBorderArt.BASIC_BLACK_SQUARES);
        G_LINE_STYLE_MAP.addEntry("basicThinLines", (int)PageBorderArt.BASIC_THIN_LINES);
        G_LINE_STYLE_MAP.addEntry("basicWhiteDashes", (int)PageBorderArt.BASIC_WHITE_DASHES);
        G_LINE_STYLE_MAP.addEntry("basicWhiteDots", (int)PageBorderArt.BASIC_WHITE_DOTS);
        G_LINE_STYLE_MAP.addEntry("basicWhiteSquares", (int)PageBorderArt.BASIC_WHITE_SQUARES);
        G_LINE_STYLE_MAP.addEntry("basicWideInline", (int)PageBorderArt.BASIC_WIDE_INLINE);
        G_LINE_STYLE_MAP.addEntry("basicWideMidline", (int)PageBorderArt.BASIC_WIDE_MIDLINE);
        G_LINE_STYLE_MAP.addEntry("basicWideOutline", (int)PageBorderArt.BASIC_WIDE_OUTLINE);
        G_LINE_STYLE_MAP.addEntry("bats", (int)PageBorderArt.BATS);
        G_LINE_STYLE_MAP.addEntry("birds", (int)PageBorderArt.BIRDS);
        G_LINE_STYLE_MAP.addEntry("birdsFlight", (int)PageBorderArt.BIRDS_FLIGHT);
        G_LINE_STYLE_MAP.addEntry("cabins", (int)PageBorderArt.CABINS);
        G_LINE_STYLE_MAP.addEntry("cakeSlice", (int)PageBorderArt.CAKE_SLICE);
        G_LINE_STYLE_MAP.addEntry("candyCorn", (int)PageBorderArt.CANDY_CORN);
        G_LINE_STYLE_MAP.addEntry("celticKnotwork", (int)PageBorderArt.CELTIC_KNOTWORK);
        G_LINE_STYLE_MAP.addEntry("certificateBanner", (int)PageBorderArt.CERTIFICATE_BANNER);
        G_LINE_STYLE_MAP.addEntry("chainLink", (int)PageBorderArt.CHAIN_LINK);
        G_LINE_STYLE_MAP.addEntry("champagneBottle", (int)PageBorderArt.CHAMPAGNE_BOTTLE);
        G_LINE_STYLE_MAP.addEntry("checkedBarBlack", (int)PageBorderArt.CHECKED_BAR_BLACK);
        G_LINE_STYLE_MAP.addEntry("checkedBarColor", (int)PageBorderArt.CHECKED_BAR_COLOR);
        G_LINE_STYLE_MAP.addEntry("checkered", (int)PageBorderArt.CHECKERED);
        G_LINE_STYLE_MAP.addEntry("christmasTree", (int)PageBorderArt.CHRISTMAS_TREE);
        G_LINE_STYLE_MAP.addEntry("circlesLines", (int)PageBorderArt.CIRCLES_LINES);
        G_LINE_STYLE_MAP.addEntry("circlesRectangles", (int)PageBorderArt.CIRCLES_RECTANGLES);
        G_LINE_STYLE_MAP.addEntry("classicalWave", (int)PageBorderArt.CLASSICAL_WAVE);
        G_LINE_STYLE_MAP.addEntry("clocks", (int)PageBorderArt.CLOCKS);
        G_LINE_STYLE_MAP.addEntry("compass", (int)PageBorderArt.COMPASS);
        G_LINE_STYLE_MAP.addEntry("confetti", (int)PageBorderArt.CONFETTI);
        G_LINE_STYLE_MAP.addEntry("confettiGrays", (int)PageBorderArt.CONFETTI_GRAYS);
        G_LINE_STYLE_MAP.addEntry("confettiOutline", (int)PageBorderArt.CONFETTI_OUTLINE);
        G_LINE_STYLE_MAP.addEntry("confettiStreamers", (int)PageBorderArt.CONFETTI_STREAMERS);
        G_LINE_STYLE_MAP.addEntry("confettiWhite", (int)PageBorderArt.CONFETTI_WHITE);
        G_LINE_STYLE_MAP.addEntry("cornerTriangles", (int)PageBorderArt.CORNER_TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("couponCutoutDashes", (int)PageBorderArt.COUPON_CUTOUT_DASHES);
        G_LINE_STYLE_MAP.addEntry("couponCutoutDots", (int)PageBorderArt.COUPON_CUTOUT_DOTS);
        G_LINE_STYLE_MAP.addEntry("crazyMaze", (int)PageBorderArt.CRAZY_MAZE);
        G_LINE_STYLE_MAP.addEntry("creaturesButterfly", (int)PageBorderArt.CREATURES_BUTTERFLY);
        G_LINE_STYLE_MAP.addEntry("creaturesFish", (int)PageBorderArt.CREATURES_FISH);
        G_LINE_STYLE_MAP.addEntry("creaturesInsects", (int)PageBorderArt.CREATURES_INSECTS);
        G_LINE_STYLE_MAP.addEntry("creaturesLadyBug", (int)PageBorderArt.CREATURES_LADY_BUG);
        G_LINE_STYLE_MAP.addEntry("crossStitch", (int)PageBorderArt.CROSS_STITCH);
        G_LINE_STYLE_MAP.addEntry("cup", (int)PageBorderArt.CUP);
        G_LINE_STYLE_MAP.addEntry("decoArch", (int)PageBorderArt.DECO_ARCH);
        G_LINE_STYLE_MAP.addEntry("decoArchColor", (int)PageBorderArt.DECO_ARCH_COLOR);
        G_LINE_STYLE_MAP.addEntry("decoBlocks", (int)PageBorderArt.DECO_BLOCKS);
        G_LINE_STYLE_MAP.addEntry("diamondsGray", (int)PageBorderArt.DIAMONDS_GRAY);
        G_LINE_STYLE_MAP.addEntry("doubleD", (int)PageBorderArt.DOUBLE_D);
        G_LINE_STYLE_MAP.addEntry("doubleDiamonds", (int)PageBorderArt.DOUBLE_DIAMONDS);
        G_LINE_STYLE_MAP.addEntry("earth1", (int)PageBorderArt.EARTH_1);
        G_LINE_STYLE_MAP.addEntry("earth2", (int)PageBorderArt.EARTH_2);
        G_LINE_STYLE_MAP.addEntry("eclipsingSquares1", (int)PageBorderArt.ECLIPSING_SQUARES_1);
        G_LINE_STYLE_MAP.addEntry("eclipsingSquares2", (int)PageBorderArt.ECLIPSING_SQUARES_2);
        G_LINE_STYLE_MAP.addEntry("eggsBlack", (int)PageBorderArt.EGGS_BLACK);
        G_LINE_STYLE_MAP.addEntry("fans", (int)PageBorderArt.FANS);
        G_LINE_STYLE_MAP.addEntry("film", (int)PageBorderArt.FILM);
        G_LINE_STYLE_MAP.addEntry("firecrackers", (int)PageBorderArt.FIRECRACKERS);
        G_LINE_STYLE_MAP.addEntry("flowersBlockPrint", (int)PageBorderArt.FLOWERS_BLOCK_PRINT);
        G_LINE_STYLE_MAP.addEntry("flowersDaisies", (int)PageBorderArt.FLOWERS_DAISIES);
        G_LINE_STYLE_MAP.addEntry("flowersModern1", (int)PageBorderArt.FLOWERS_MODERN_1);
        G_LINE_STYLE_MAP.addEntry("flowersModern2", (int)PageBorderArt.FLOWERS_MODERN_2);
        G_LINE_STYLE_MAP.addEntry("flowersPansy", (int)PageBorderArt.FLOWERS_PANSY);
        G_LINE_STYLE_MAP.addEntry("flowersRedRose", (int)PageBorderArt.FLOWERS_RED_ROSE);
        G_LINE_STYLE_MAP.addEntry("flowersRoses", (int)PageBorderArt.FLOWERS_ROSES);
        G_LINE_STYLE_MAP.addEntry("flowersTeacup", (int)PageBorderArt.FLOWERS_TEACUP);
        G_LINE_STYLE_MAP.addEntry("flowersTiny", (int)PageBorderArt.FLOWERS_TINY);
        G_LINE_STYLE_MAP.addEntry("gems", (int)PageBorderArt.GEMS);
        G_LINE_STYLE_MAP.addEntry("gingerbreadMan", (int)PageBorderArt.GINGERBREAD_MAN);
        G_LINE_STYLE_MAP.addEntry("gradient", (int)PageBorderArt.GRADIENT);
        G_LINE_STYLE_MAP.addEntry("handmade1", (int)PageBorderArt.HANDMADE_1);
        G_LINE_STYLE_MAP.addEntry("handmade2", (int)PageBorderArt.HANDMADE_2);
        G_LINE_STYLE_MAP.addEntry("heartBalloon", (int)PageBorderArt.HEART_BALLOON);
        G_LINE_STYLE_MAP.addEntry("heartGray", (int)PageBorderArt.HEART_GRAY);
        G_LINE_STYLE_MAP.addEntry("hearts", (int)PageBorderArt.HEARTS);
        G_LINE_STYLE_MAP.addEntry("heebieJeebies", (int)PageBorderArt.HEEBIE_JEEBIES);
        G_LINE_STYLE_MAP.addEntry("holly", (int)PageBorderArt.HOLLY);
        G_LINE_STYLE_MAP.addEntry("houseFunky", (int)PageBorderArt.HOUSE_FUNKY);
        G_LINE_STYLE_MAP.addEntry("hypnotic", (int)PageBorderArt.HYPNOTIC);
        G_LINE_STYLE_MAP.addEntry("iceCreamCones", (int)PageBorderArt.ICE_CREAM_CONES);
        G_LINE_STYLE_MAP.addEntry("lightBulb", (int)PageBorderArt.LIGHT_BULB);
        G_LINE_STYLE_MAP.addEntry("lightning1", (int)PageBorderArt.LIGHTNING_1);
        G_LINE_STYLE_MAP.addEntry("lightning2", (int)PageBorderArt.LIGHTNING_2);
        G_LINE_STYLE_MAP.addEntry("mapPins", (int)PageBorderArt.MAP_PINS);
        G_LINE_STYLE_MAP.addEntry("mapleLeaf", (int)PageBorderArt.MAPLE_LEAF);
        G_LINE_STYLE_MAP.addEntry("mapleMuffins", (int)PageBorderArt.MAPLE_MUFFINS);
        G_LINE_STYLE_MAP.addEntry("marquee", (int)PageBorderArt.MARQUEE);
        G_LINE_STYLE_MAP.addEntry("marqueeToothed", (int)PageBorderArt.MARQUEE_TOOTHED);
        G_LINE_STYLE_MAP.addEntry("moons", (int)PageBorderArt.MOONS);
        G_LINE_STYLE_MAP.addEntry("mosaic", (int)PageBorderArt.MOSAIC);
        G_LINE_STYLE_MAP.addEntry("musicNotes", (int)PageBorderArt.MUSIC_NOTES);
        G_LINE_STYLE_MAP.addEntry("northwest", (int)PageBorderArt.NORTHWEST);
        G_LINE_STYLE_MAP.addEntry("ovals", (int)PageBorderArt.OVALS);
        G_LINE_STYLE_MAP.addEntry("packages", (int)PageBorderArt.PACKAGES);
        G_LINE_STYLE_MAP.addEntry("palmsBlack", (int)PageBorderArt.PALMS_BLACK);
        G_LINE_STYLE_MAP.addEntry("palmsColor", (int)PageBorderArt.PALMS_COLOR);
        G_LINE_STYLE_MAP.addEntry("paperClips", (int)PageBorderArt.PAPER_CLIPS);
        G_LINE_STYLE_MAP.addEntry("papyrus", (int)PageBorderArt.PAPYRUS);
        G_LINE_STYLE_MAP.addEntry("partyFavor", (int)PageBorderArt.PARTY_FAVOR);
        G_LINE_STYLE_MAP.addEntry("partyGlass", (int)PageBorderArt.PARTY_GLASS);
        G_LINE_STYLE_MAP.addEntry("pencils", (int)PageBorderArt.PENCILS);
        G_LINE_STYLE_MAP.addEntry("people", (int)PageBorderArt.PEOPLE);
        G_LINE_STYLE_MAP.addEntry("peopleWaving", (int)PageBorderArt.PEOPLE_WAVING);
        G_LINE_STYLE_MAP.addEntry("peopleHats", (int)PageBorderArt.PEOPLE_HATS);
        G_LINE_STYLE_MAP.addEntry("poinsettias", (int)PageBorderArt.POINSETTIAS);
        G_LINE_STYLE_MAP.addEntry("postageStamp", (int)PageBorderArt.POSTAGE_STAMP);
        G_LINE_STYLE_MAP.addEntry("pumpkin1", (int)PageBorderArt.PUMPKIN_1);
        G_LINE_STYLE_MAP.addEntry("pushPinNote1", (int)PageBorderArt.PUSH_PIN_NOTE_1);
        G_LINE_STYLE_MAP.addEntry("pushPinNote2", (int)PageBorderArt.PUSH_PIN_NOTE_2);
        G_LINE_STYLE_MAP.addEntry("pyramids", (int)PageBorderArt.PYRAMIDS);
        G_LINE_STYLE_MAP.addEntry("pyramidsAbove", (int)PageBorderArt.PYRAMIDS_ABOVE);
        G_LINE_STYLE_MAP.addEntry("quadrants", (int)PageBorderArt.QUADRANTS);
        G_LINE_STYLE_MAP.addEntry("rings", (int)PageBorderArt.RINGS);
        G_LINE_STYLE_MAP.addEntry("safari", (int)PageBorderArt.SAFARI);
        G_LINE_STYLE_MAP.addEntry("sawtooth", (int)PageBorderArt.SAWTOOTH);
        G_LINE_STYLE_MAP.addEntry("sawtoothGray", (int)PageBorderArt.SAWTOOTH_GRAY);
        G_LINE_STYLE_MAP.addEntry("scaredCat", (int)PageBorderArt.SCARED_CAT);
        G_LINE_STYLE_MAP.addEntry("seattle", (int)PageBorderArt.SEATTLE);
        G_LINE_STYLE_MAP.addEntry("shadowedSquares", (int)PageBorderArt.SHADOWED_SQUARES);
        G_LINE_STYLE_MAP.addEntry("sharksTeeth", (int)PageBorderArt.SHARKS_TEETH);
        G_LINE_STYLE_MAP.addEntry("shorebirdTracks", (int)PageBorderArt.SHOREBIRD_TRACKS);
        G_LINE_STYLE_MAP.addEntry("skyrocket", (int)PageBorderArt.SKYROCKET);
        G_LINE_STYLE_MAP.addEntry("snowflakeFancy", (int)PageBorderArt.SNOWFLAKE_FANCY);
        G_LINE_STYLE_MAP.addEntry("snowflakes", (int)PageBorderArt.SNOWFLAKES);
        G_LINE_STYLE_MAP.addEntry("sombrero", (int)PageBorderArt.SOMBRERO);
        G_LINE_STYLE_MAP.addEntry("southwest", (int)PageBorderArt.SOUTHWEST);
        G_LINE_STYLE_MAP.addEntry("stars", (int)PageBorderArt.STARS);
        G_LINE_STYLE_MAP.addEntry("starsTop", (int)PageBorderArt.STARS_TOP);
        G_LINE_STYLE_MAP.addEntry("stars3d", (int)PageBorderArt.STARS_3_D);
        G_LINE_STYLE_MAP.addEntry("starsBlack", (int)PageBorderArt.STARS_BLACK);
        G_LINE_STYLE_MAP.addEntry("starsShadowed", (int)PageBorderArt.STARS_SHADOWED);
        G_LINE_STYLE_MAP.addEntry("sun", (int)PageBorderArt.SUN);
        G_LINE_STYLE_MAP.addEntry("swirligig", (int)PageBorderArt.SWIRLIGIG);
        G_LINE_STYLE_MAP.addEntry("tornPaper", (int)PageBorderArt.TORN_PAPER);
        G_LINE_STYLE_MAP.addEntry("tornPaperBlack", (int)PageBorderArt.TORN_PAPER_BLACK);
        G_LINE_STYLE_MAP.addEntry("trees", (int)PageBorderArt.TREES);
        G_LINE_STYLE_MAP.addEntry("triangleParty", (int)PageBorderArt.TRIANGLE_PARTY);
        G_LINE_STYLE_MAP.addEntry("triangles", (int)PageBorderArt.TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("tribal1", (int)PageBorderArt.TRIBAL_1);
        G_LINE_STYLE_MAP.addEntry("tribal2", (int)PageBorderArt.TRIBAL_2);
        G_LINE_STYLE_MAP.addEntry("tribal3", (int)PageBorderArt.TRIBAL_3);
        G_LINE_STYLE_MAP.addEntry("tribal4", (int)PageBorderArt.TRIBAL_4);
        G_LINE_STYLE_MAP.addEntry("tribal5", (int)PageBorderArt.TRIBAL_5);
        G_LINE_STYLE_MAP.addEntry("tribal6", (int)PageBorderArt.TRIBAL_6);
        G_LINE_STYLE_MAP.addEntry("twistedLines1", (int)PageBorderArt.TWISTED_LINES_1);
        G_LINE_STYLE_MAP.addEntry("twistedLines2", (int)PageBorderArt.TWISTED_LINES_2);
        G_LINE_STYLE_MAP.addEntry("vine", (int)PageBorderArt.VINE);
        G_LINE_STYLE_MAP.addEntry("waveline", (int)PageBorderArt.WAVELINE);
        G_LINE_STYLE_MAP.addEntry("weavingAngles", (int)PageBorderArt.WEAVING_ANGLES);
        G_LINE_STYLE_MAP.addEntry("weavingBraid", (int)PageBorderArt.WEAVING_BRAID);
        G_LINE_STYLE_MAP.addEntry("weavingRibbon", (int)PageBorderArt.WEAVING_RIBBON);
        G_LINE_STYLE_MAP.addEntry("weavingStrips", (int)PageBorderArt.WEAVING_STRIPS);
        G_LINE_STYLE_MAP.addEntry("whiteFlowers", (int)PageBorderArt.WHITE_FLOWERS);
        G_LINE_STYLE_MAP.addEntry("woodwork", (int)PageBorderArt.WOODWORK);
        G_LINE_STYLE_MAP.addEntry("xIllusions", (int)PageBorderArt.X_ILLUSIONS);
        G_LINE_STYLE_MAP.addEntry("zanyTriangles", (int)PageBorderArt.ZANY_TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("zigZag", (int)PageBorderArt.ZIG_ZAG);
        G_LINE_STYLE_MAP.addEntry("zigZagStitch", (int)PageBorderArt.ZIG_ZAG_STITCH);
        G_LINE_STYLE_MAP.addEntry("earth3", (int)PageBorderArt.EARTH_3);
        G_LINE_STYLE_MAP.addEntry("triangle1", (int)PageBorderArt.TRIANGLE_1);
        G_LINE_STYLE_MAP.addEntry("triangle2", (int)PageBorderArt.TRIANGLE_2);
        G_LINE_STYLE_MAP.addEntry("triangleCircle1", (int)PageBorderArt.TRIANGLE_CIRCLE_1);
        G_LINE_STYLE_MAP.addEntry("triangleCircle2", (int)PageBorderArt.TRIANGLE_CIRCLE_2);
        G_LINE_STYLE_MAP.addEntry("shapes1", (int)PageBorderArt.SHAPES_1);
        G_LINE_STYLE_MAP.addEntry("shapes2", (int)PageBorderArt.SHAPES_2);
        G_LINE_STYLE_MAP.addEntry("custom", (int)PageBorderArt. CUSTOM);

        G_NUMBER_STYLE_MAP.addEntry("decimal", (int)NumberStyle.ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("upperRoman", (int)NumberStyle.UPPERCASE_ROMAN);
        G_NUMBER_STYLE_MAP.addEntry("lowerRoman", (int)NumberStyle.LOWERCASE_ROMAN);
        G_NUMBER_STYLE_MAP.addEntry("upperLetter", (int)NumberStyle.UPPERCASE_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("lowerLetter", (int)NumberStyle.LOWERCASE_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("ordinal", (int)NumberStyle.ORDINAL);
        G_NUMBER_STYLE_MAP.addEntry("cardinalText", (int)NumberStyle.NUMBER);
        G_NUMBER_STYLE_MAP.addEntry("ordinalText", (int)NumberStyle.ORDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("hex", (int)NumberStyle.HEX);
        G_NUMBER_STYLE_MAP.addEntry("chicago", (int)NumberStyle.CHICAGO_MANUAL);
        G_NUMBER_STYLE_MAP.addEntry("ideographDigital", (int)NumberStyle.KANJI);
        G_NUMBER_STYLE_MAP.addEntry("japaneseCounting", (int)NumberStyle.KANJI_DIGIT);
        G_NUMBER_STYLE_MAP.addEntry("aiueo", (int)NumberStyle.AIUEO_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("iroha", (int)NumberStyle.IROHA_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("decimalFullWidth", (int)NumberStyle.ARABIC_FULL_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("decimalHalfWidth", (int)NumberStyle.ARABIC_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("japaneseLegal", (int)NumberStyle.KANJI_TRADITIONAL);
        G_NUMBER_STYLE_MAP.addEntry("japaneseDigitalTenThousand", (int)NumberStyle.KANJI_TRADITIONAL_2);
        G_NUMBER_STYLE_MAP.addEntry("decimalEnclosedCircle", (int)NumberStyle.NUMBER_IN_CIRCLE);
        G_NUMBER_STYLE_MAP.addEntry("decimalFullWidth2", (int)NumberStyle.DECIMAL_FULL_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("aiueoFullWidth", (int)NumberStyle.AIUEO);
        G_NUMBER_STYLE_MAP.addEntry("irohaFullWidth", (int)NumberStyle.IROHA);
        G_NUMBER_STYLE_MAP.addEntry("decimalZero", (int)NumberStyle.LEADING_ZERO);
        G_NUMBER_STYLE_MAP.addEntry("bullet", (int)NumberStyle.BULLET);
        G_NUMBER_STYLE_MAP.addEntry("ganada", (int)NumberStyle.GANADA);
        G_NUMBER_STYLE_MAP.addEntry("chosung", (int)NumberStyle.CHOSUNG);
        G_NUMBER_STYLE_MAP.addEntry("decimalEnclosedFullstop", (int)NumberStyle.GB_1);
        G_NUMBER_STYLE_MAP.addEntry("decimalEnclosedParen", (int)NumberStyle.GB_2);
        G_NUMBER_STYLE_MAP.addEntry("decimalEnclosedCircleChinese", (int)NumberStyle.GB_3);
        G_NUMBER_STYLE_MAP.addEntry("ideographEnclosedCircle", (int)NumberStyle.GB_4);
        G_NUMBER_STYLE_MAP.addEntry("ideographTraditional", (int)NumberStyle.ZODIAC_1);
        G_NUMBER_STYLE_MAP.addEntry("ideographZodiac", (int)NumberStyle.ZODIAC_2);
        G_NUMBER_STYLE_MAP.addEntry("ideographZodiacTraditional", (int)NumberStyle.ZODIAC_3);
        G_NUMBER_STYLE_MAP.addEntry("taiwaneseCounting", (int)NumberStyle.TRAD_CHIN_NUM_1);
        G_NUMBER_STYLE_MAP.addEntry("ideographLegalTraditional", (int)NumberStyle.TRAD_CHIN_NUM_2);
        G_NUMBER_STYLE_MAP.addEntry("taiwaneseCountingThousand", (int)NumberStyle.TRAD_CHIN_NUM_3);
        G_NUMBER_STYLE_MAP.addEntry("taiwaneseDigital", (int)NumberStyle.TRAD_CHIN_NUM_4);
        G_NUMBER_STYLE_MAP.addEntry("chineseCounting", (int)NumberStyle.SIMP_CHIN_NUM_1);
        G_NUMBER_STYLE_MAP.addEntry("chineseLegalSimplified", (int)NumberStyle.SIMP_CHIN_NUM_2);
        G_NUMBER_STYLE_MAP.addEntry("chineseCountingThousand", (int)NumberStyle.SIMP_CHIN_NUM_3);
        G_NUMBER_STYLE_MAP.addEntry("koreanDigital", (int)NumberStyle.HANJA_READ);
        G_NUMBER_STYLE_MAP.addEntry("koreanCounting", (int)NumberStyle.HANJA_READ_DIGIT);
        G_NUMBER_STYLE_MAP.addEntry("koreanLegal", (int)NumberStyle.HANGUL);
        G_NUMBER_STYLE_MAP.addEntry("koreanDigital2", (int)NumberStyle.HANJA);
        G_NUMBER_STYLE_MAP.addEntry("vietnameseCounting", (int)NumberStyle.VIET_CARDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("russianLower", (int)NumberStyle.LOWERCASE_RUSSIAN);
        G_NUMBER_STYLE_MAP.addEntry("russianUpper", (int)NumberStyle.UPPERCASE_RUSSIAN);
        G_NUMBER_STYLE_MAP.addEntry("none", (int)NumberStyle.NONE);
        G_NUMBER_STYLE_MAP.addEntry("numberInDash", (int)NumberStyle.NUMBER_IN_DASH);
        G_NUMBER_STYLE_MAP.addEntry("hebrew1", (int)NumberStyle.HEBREW_1);
        G_NUMBER_STYLE_MAP.addEntry("hebrew2", (int)NumberStyle.HEBREW_2);
        G_NUMBER_STYLE_MAP.addEntry("arabicAlpha", (int)NumberStyle.ARABIC_1);
        G_NUMBER_STYLE_MAP.addEntry("arabicAbjad", (int)NumberStyle.ARABIC_2);
        G_NUMBER_STYLE_MAP.addEntry("hindiVowels", (int)NumberStyle.HINDI_LETTER_1);
        G_NUMBER_STYLE_MAP.addEntry("hindiConsonants", (int)NumberStyle.HINDI_LETTER_2);
        G_NUMBER_STYLE_MAP.addEntry("hindiNumbers", (int)NumberStyle.HINDI_ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("hindiCounting", (int)NumberStyle.HINDI_CARDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("thaiLetters", (int)NumberStyle.THAI_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("thaiNumbers", (int)NumberStyle.THAI_ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("thaiCounting", (int)NumberStyle.THAI_CARDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("custom", (int)NumberStyle.CUSTOM);

        G_STYLE_TYPE_MAP.addEntry("paragraph", (int)StyleType.PARAGRAPH);
        G_STYLE_TYPE_MAP.addEntry("character", (int)StyleType.CHARACTER);
        G_STYLE_TYPE_MAP.addEntry("table", (int)StyleType.TABLE);
        G_STYLE_TYPE_MAP.addEntry("numbering", (int)StyleType.LIST);

        G_FONT_FAMILY_MAP.addEntry("auto", (int)FontFamily.AUTO);
        G_FONT_FAMILY_MAP.addEntry("decorative", (int)FontFamily.DECORATIVE);
        G_FONT_FAMILY_MAP.addEntry("modern", (int)FontFamily.MODERN);
        G_FONT_FAMILY_MAP.addEntry("roman", (int)FontFamily.ROMAN);
        G_FONT_FAMILY_MAP.addEntry("script", (int)FontFamily.SCRIPT);
        G_FONT_FAMILY_MAP.addEntry("swiss", (int)FontFamily.SWISS);

        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("normal", -1);   // FootnoteSeparatorType.Normal.
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("separator", (int)FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("continuationSeparator", (int)FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR);
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("continuationNotice", (int)FootnoteSeparatorType.FOOTNOTE_CONTINUATION_NOTICE);

        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("normal", -1);    // FootnoteSeparatorType.Normal.
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("separator", (int)FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("continuationSeparator", (int)FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR);
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("continuationNotice", (int)FootnoteSeparatorType.ENDNOTE_CONTINUATION_NOTICE);

        G_DOC_PART_BEHAVIOR_MAP.addEntry("content", (int)BuildingBlockBehavior.CONTENT);
        G_DOC_PART_BEHAVIOR_MAP.addEntry("p", (int)BuildingBlockBehavior.PARAGRAPH);
        G_DOC_PART_BEHAVIOR_MAP.addEntry("pg", (int)BuildingBlockBehavior.PAGE);

        G_DOC_PART_GALLERY_MAP.addEntry("any", (int)BuildingBlockGallery.ALL);
        G_DOC_PART_GALLERY_MAP.addEntry("autoTxt", (int)BuildingBlockGallery.AUTO_TEXT);
        G_DOC_PART_GALLERY_MAP.addEntry("bib", (int)BuildingBlockGallery.BIBLIOGRAPHY);
        G_DOC_PART_GALLERY_MAP.addEntry("coverPg", (int)BuildingBlockGallery.COVER_PAGE);
        G_DOC_PART_GALLERY_MAP.addEntry("custAutoTxt", (int)BuildingBlockGallery.CUSTOM_AUTO_TEXT);
        G_DOC_PART_GALLERY_MAP.addEntry("custBib", (int)BuildingBlockGallery.CUSTOM_BIBLIOGRAPHY);
        G_DOC_PART_GALLERY_MAP.addEntry("custCoverPg", (int)BuildingBlockGallery.CUSTOM_COVER_PAGE);
        G_DOC_PART_GALLERY_MAP.addEntry("custEq", (int)BuildingBlockGallery.CUSTOM_EQUATIONS);
        G_DOC_PART_GALLERY_MAP.addEntry("custFtrs", (int)BuildingBlockGallery.CUSTOM_FOOTERS);
        G_DOC_PART_GALLERY_MAP.addEntry("custHdrs", (int)BuildingBlockGallery.CUSTOM_HEADERS);
        G_DOC_PART_GALLERY_MAP.addEntry("custom1", (int)BuildingBlockGallery.CUSTOM_1);
        G_DOC_PART_GALLERY_MAP.addEntry("custom2", (int)BuildingBlockGallery.CUSTOM_2);
        G_DOC_PART_GALLERY_MAP.addEntry("custom3", (int)BuildingBlockGallery.CUSTOM_3);
        G_DOC_PART_GALLERY_MAP.addEntry("custom4", (int)BuildingBlockGallery.CUSTOM_4);
        G_DOC_PART_GALLERY_MAP.addEntry("custom5", (int)BuildingBlockGallery.CUSTOM_5);
        G_DOC_PART_GALLERY_MAP.addEntry("custPgNum", (int)BuildingBlockGallery.CUSTOM_PAGE_NUMBER);
        G_DOC_PART_GALLERY_MAP.addEntry("custPgNumB", (int)BuildingBlockGallery.CUSTOM_PAGE_NUMBER_AT_BOTTOM);
        G_DOC_PART_GALLERY_MAP.addEntry("custPgNumMargins", (int)BuildingBlockGallery.CUSTOM_PAGE_NUMBER_AT_MARGIN);
        G_DOC_PART_GALLERY_MAP.addEntry("custPgNumT", (int)BuildingBlockGallery.CUSTOM_PAGE_NUMBER_AT_TOP);
        G_DOC_PART_GALLERY_MAP.addEntry("custQuickParts", (int)BuildingBlockGallery.CUSTOM_QUICK_PARTS);
        G_DOC_PART_GALLERY_MAP.addEntry("custTblOfContents", (int)BuildingBlockGallery.CUSTOM_TABLE_OF_CONTENTS);
        G_DOC_PART_GALLERY_MAP.addEntry("custTbls", (int)BuildingBlockGallery.CUSTOM_TABLES);
        G_DOC_PART_GALLERY_MAP.addEntry("custTxtBox", (int)BuildingBlockGallery.CUSTOM_TEXT_BOX);
        G_DOC_PART_GALLERY_MAP.addEntry("custWatermarks", (int)BuildingBlockGallery.CUSTOM_WATERMARKS);
        G_DOC_PART_GALLERY_MAP.addEntry("default", (int)BuildingBlockGallery.NO_GALLERY);
        G_DOC_PART_GALLERY_MAP.addEntry("docParts", (int)BuildingBlockGallery.QUICK_PARTS);
        G_DOC_PART_GALLERY_MAP.addEntry("eq", (int)BuildingBlockGallery.EQUATIONS);
        G_DOC_PART_GALLERY_MAP.addEntry("ftrs", (int)BuildingBlockGallery.FOOTERS);
        G_DOC_PART_GALLERY_MAP.addEntry("hdrs", (int)BuildingBlockGallery.HEADERS);
        G_DOC_PART_GALLERY_MAP.addEntry("pgNum", (int)BuildingBlockGallery.PAGE_NUMBER);
        G_DOC_PART_GALLERY_MAP.addEntry("pgNumB", (int)BuildingBlockGallery.PAGE_NUMBER_AT_BOTTOM);
        G_DOC_PART_GALLERY_MAP.addEntry("pgNumMargins", (int)BuildingBlockGallery.PAGE_NUMBER_AT_MARGIN);
        G_DOC_PART_GALLERY_MAP.addEntry("pgNumT", (int)BuildingBlockGallery.PAGE_NUMBER_AT_TOP);
        G_DOC_PART_GALLERY_MAP.addEntry("placeholder", (int)BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        G_DOC_PART_GALLERY_MAP.addEntry("tblOfContents", (int)BuildingBlockGallery.TABLE_OF_CONTENTS);
        G_DOC_PART_GALLERY_MAP.addEntry("tbls", (int)BuildingBlockGallery.TABLES);
        G_DOC_PART_GALLERY_MAP.addEntry("txtBox", (int)BuildingBlockGallery.TEXT_BOX);
        G_DOC_PART_GALLERY_MAP.addEntry("watermarks", (int)BuildingBlockGallery.WATERMARKS);

        G_DOC_PART_TYPE_MAP.addEntry("autoExp", (int)BuildingBlockType.AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT);
        G_DOC_PART_TYPE_MAP.addEntry("bbPlcHdr", (int)BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        G_DOC_PART_TYPE_MAP.addEntry("formFld", (int)BuildingBlockType.FORM_FIELD_HELP_TEXT);
        G_DOC_PART_TYPE_MAP.addEntry("none", (int)BuildingBlockType.NONE);
        G_DOC_PART_TYPE_MAP.addEntry("normal", (int)BuildingBlockType.NORMAL);
        G_DOC_PART_TYPE_MAP.addEntry("speller", (int)BuildingBlockType.AUTO_CORRECT);
        G_DOC_PART_TYPE_MAP.addEntry("toolbar", (int)BuildingBlockType.AUTO_TEXT);

        G_CALENDAR_TYPE_MAP.addEntry("gregorian", (int)SdtCalendarType.GREGORIAN);
        G_CALENDAR_TYPE_MAP.addEntry("gregorianArabic", (int)SdtCalendarType.GREGORIAN_ARABIC);
        G_CALENDAR_TYPE_MAP.addEntry("gregorianMeFrench", (int)SdtCalendarType.GREGORIAN_ME_FRENCH);
        G_CALENDAR_TYPE_MAP.addEntry("gregorianUs", (int)SdtCalendarType.GREGORIAN_US);
        G_CALENDAR_TYPE_MAP.addEntry("gregorianXlitEnglish", (int)SdtCalendarType.GREGORIAN_XLIT_ENGLISH);
        G_CALENDAR_TYPE_MAP.addEntry("gregorianXlitFrench", (int)SdtCalendarType.GREGORIAN_XLIT_FRENCH);
        G_CALENDAR_TYPE_MAP.addEntry("hebrew", (int)SdtCalendarType.HEBREW);
        G_CALENDAR_TYPE_MAP.addEntry("hijri", (int)SdtCalendarType.HIJRI);
        G_CALENDAR_TYPE_MAP.addEntry("japan", (int)SdtCalendarType.JAPAN);
        G_CALENDAR_TYPE_MAP.addEntry("korea", (int)SdtCalendarType.KOREA);
        G_CALENDAR_TYPE_MAP.addEntry("none", (int)SdtCalendarType.NONE);
        G_CALENDAR_TYPE_MAP.addEntry("saka", (int)SdtCalendarType.SAKA);
        G_CALENDAR_TYPE_MAP.addEntry("taiwan", (int)SdtCalendarType.TAIWAN);
        G_CALENDAR_TYPE_MAP.addEntry("thai", (int)SdtCalendarType.THAI);

        G_SDT_LOCK_TYPE_MAP.addEntry("contentLocked", (int)SdtLockType.CONTENT_LOCKED);
        G_SDT_LOCK_TYPE_MAP.addEntry("sdtContentLocked", (int)SdtLockType.SDT_AND_CONTENT_LOCKED);
        G_SDT_LOCK_TYPE_MAP.addEntry("sdtLocked", (int)SdtLockType.SDT_LOCKED);
        G_SDT_LOCK_TYPE_MAP.addEntry("unlocked", (int)SdtLockType.UNLOCKED);

        G_SDT_DATE_STORAGE_FORMAT_MAP.addEntry("date", (int)SdtDateStorageFormat.DATE);
        G_SDT_DATE_STORAGE_FORMAT_MAP.addEntry("dateTime", (int)SdtDateStorageFormat.DATE_TIME);
        G_SDT_DATE_STORAGE_FORMAT_MAP.addEntry("text", (int)SdtDateStorageFormat.TEXT);

        G_SDT_APPEARANCE_MAP.addEntry("boundingBox", (int)SdtAppearance.BOUNDING_BOX);
        G_SDT_APPEARANCE_MAP.addEntry("tags", (int)SdtAppearance.TAGS);
        G_SDT_APPEARANCE_MAP.addEntry("hidden", (int)SdtAppearance.HIDDEN);

        G_MATH_JUSTIFICATION_MAP.addEntry("center", (int)OfficeMathJustification.CENTER);
        G_MATH_JUSTIFICATION_MAP.addEntry("centerGroup", (int)OfficeMathJustification.CENTER_GROUP);
        G_MATH_JUSTIFICATION_MAP.addEntry("left", (int)OfficeMathJustification.LEFT);
        G_MATH_JUSTIFICATION_MAP.addEntry("right", (int)OfficeMathJustification.RIGHT);

        G_MATH_SCRIPT_MAP.addEntry("double-struck", (int)MathScript.DOUBLE_STRUCK);
        G_MATH_SCRIPT_MAP.addEntry("fraktur", (int)MathScript.FRAKTUR);
        G_MATH_SCRIPT_MAP.addEntry("monospace", (int)MathScript.MONOSPACE);
        G_MATH_SCRIPT_MAP.addEntry("roman", (int)MathScript.ROMAN);
        G_MATH_SCRIPT_MAP.addEntry("sans-serif", (int)MathScript.SANS_SERIF);
        G_MATH_SCRIPT_MAP.addEntry("script", (int)MathScript.SCRIPT);

        G_MATH_STYLE_MAP.addEntry("p", (int)MathStyle.PLAIN);
        G_MATH_STYLE_MAP.addEntry("b", (int)MathStyle.BOLD);
        G_MATH_STYLE_MAP.addEntry("i", (int)MathStyle.ITALIC);
        G_MATH_STYLE_MAP.addEntry("bi", (int)MathStyle.BOLD_ITALIC);

        G_MATH_POSITION_MAP.addEntry("top", (int)MathPosition.TOP);
        G_MATH_POSITION_MAP.addEntry("bot", (int)MathPosition.BOTTOM);

        G_MATH_DELIMITER_SHAPE_MAP.addEntry("centered", (int)MathDelimiterShape.CENTERED);
        G_MATH_DELIMITER_SHAPE_MAP.addEntry("match", (int)MathDelimiterShape.MATCH);

        G_MATH_VERTICAL_JUSTIFICATION_TYPE_MAP.addEntry("top", (int)MathVerticalJustification.TOP);
        G_MATH_VERTICAL_JUSTIFICATION_TYPE_MAP.addEntry("bot", (int)MathVerticalJustification.BOTTOM);

        G_MATH_FRACTION_TYPE_MAP.addEntry("bar", (int)MathFractionType.BAR);
        G_MATH_FRACTION_TYPE_MAP.addEntry("lin", (int)MathFractionType.LINEAR);
        G_MATH_FRACTION_TYPE_MAP.addEntry("noBar", (int)MathFractionType.NO_BAR);
        G_MATH_FRACTION_TYPE_MAP.addEntry("skw", (int)MathFractionType.SKEWED);

        G_MATH_LIMIT_LOCATION_MAP.addEntry("subSup", (int)MathLimitLocation.SUBSCRIPT_SUPERSCRIPT);
        G_MATH_LIMIT_LOCATION_MAP.addEntry("undOvr", (int)MathLimitLocation.UNDER_OVER);

        G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP.addEntry("center", (int)AbsolutePositionTabAlignment.CENTER);
        G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP.addEntry("left", (int)AbsolutePositionTabAlignment.LEFT);
        G_ABSOLUTE_POSITION_TAB_ALIGNMENT_MAP.addEntry("right", (int)AbsolutePositionTabAlignment.RIGHT);

        G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.addEntry("dot", (int)AbsolutePositionTabLeaderChar.DOT);
        G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.addEntry("hyphen", (int)AbsolutePositionTabLeaderChar.HYPHEN);
        G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.addEntry("middleDot", (int)AbsolutePositionTabLeaderChar.MIDDLE_DOT);
        G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.addEntry("none", (int)AbsolutePositionTabLeaderChar.NONE);
        G_ABSOLUTE_POSITION_TAB_LEADER_CHAR_MAP.addEntry("underscore", (int)AbsolutePositionTabLeaderChar.UNDERSCORE);

        G_ABSOLUTE_POSITION_TAB_RELATIVE_TO_MAP.addEntry("indent", (int)AbsolutePositionTabPositioningBase.INDENT);
        G_ABSOLUTE_POSITION_TAB_RELATIVE_TO_MAP.addEntry("margin", (int)AbsolutePositionTabPositioningBase.MARGIN);

        G_CONTACT_IDENTITY_PROVIDER_MAP.addEntry("None", (int)ContactIdentityProvider.NONE);
        G_CONTACT_IDENTITY_PROVIDER_MAP.addEntry("AD", (int)ContactIdentityProvider.ACTIVE_DIRECTORY);
        G_CONTACT_IDENTITY_PROVIDER_MAP.addEntry("Windows Live", (int)ContactIdentityProvider.WINDOWS_LIVE_ID);

        G_TASK_PANE_DOCK_STATE_MAP.addEntry("right", (int)TaskPaneDockState.RIGHT);
        G_TASK_PANE_DOCK_STATE_MAP.addEntry("left", (int)TaskPaneDockState.LEFT);

        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("omex", (int)WebExtensionStoreType.OMEX);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("spapp", (int)WebExtensionStoreType.SP_APP);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("registry", (int)WebExtensionStoreType.REGISTRY);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("exchange", (int)WebExtensionStoreType.EXCHANGE);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("spcatalog", (int)WebExtensionStoreType.SP_CATALOG);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("excatalog", (int)WebExtensionStoreType.EX_CATALOG);
        G_WEB_EXTENSION_STORE_TYPE_MAP.addEntry("filesystem", (int)WebExtensionStoreType.FILE_SYSTEM);

        G_WEB_EXTENSION_BINDING_TYPE_MAP.addEntry("text", (int)WebExtensionBindingType.TEXT);
        G_WEB_EXTENSION_BINDING_TYPE_MAP.addEntry("table", (int)WebExtensionBindingType.TABLE);
        G_WEB_EXTENSION_BINDING_TYPE_MAP.addEntry("matrix", (int)WebExtensionBindingType.MATRIX);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"application/msword",
		"application/vnd.ms-excel",
		"application/vnd.ms-powerpoint",
		"application/vnd.visio",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.template",
		"application/vnd.ms-word.document.macroEnabled.12",
		"application/vnd.ms-word.template.macroEnabled.12",
		"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		"application/vnd.ms-excel.sheet.macroEnabled.12",
		"application/vnd.ms-excel.sheet.binary.macroEnabled.12",
		"application/vnd.openxmlformats-officedocument.presentationml.presentation",
		"application/vnd.ms-powerpoint.presentation.macroEnabled.12",
		"application/vnd.openxmlformats-officedocument.presentationml.slideshow",
		"application/vnd.ms-powerpoint.slideshow.macroEnabled.12",
		"application/vnd.openxmlformats-officedocument.presentationml.slide",
		"application/vnd.ms-powerpoint.slide.macroEnabled.12",
		"application/vnd.ms-visio.drawing",
		"default",
		"lining",
		"oldStyle",
		"proportional",
		"tabular",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"10",
		"11",
		"12",
		"13",
		"14",
		"15",
		"16",
		"17",
		"18",
		"19",
		"20",
		"calculated",
		"currentDate",
		"currentTime",
		"date",
		"number",
		"",
		"regular",
		"top",
		"bottom",
		"bot",
		"center"
	);

}

