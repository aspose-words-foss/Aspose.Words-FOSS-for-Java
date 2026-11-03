// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts enumerated types related to document properties between enum and DOCX string.
/// </summary>
class DocxDopEnum
{
    static /*ProtectionType*/int docxToProtectionType(String value)
    {
        return (/*ProtectionType*/int)G_PROTECTION_TYPE_MAP.getValue(value, (int)ProtectionType.NO_PROTECTION);
    }

    static String protectionTypeToDocx(/*ProtectionType*/int value)
    {
        return G_PROTECTION_TYPE_MAP.getValue((int)value, "none");
    }

    static /*JustificationMode*/int docxToKinsokuJustification(String value)
    {
        return (/*JustificationMode*/int)G_KINSOKU_JUSTIFICATION_MAP.getValue(value, (int)JustificationMode.EXPAND);
    }

    static String kinsokuJustificationToDocx(/*JustificationMode*/int value)
    {
        return G_KINSOKU_JUSTIFICATION_MAP.getValue((int)value, "DontCompress");
    }

    static /*ProofState*/int docxToProofState(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"clean"*/0:
                return ProofState.CLEAN;
            default:
                return ProofState.NONE;
        }
    }

    static String proofStateToDocx(/*ProofState*/int value)
    {
        switch (value)
        {
            case ProofState.CLEAN:
                return "clean";
            default:
                return "";
        }
    }

    static /*ViewType*/int docxToViewType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"normal"*/1:
                return ViewType.NORMAL;
            case /*"print"*/2:
                return ViewType.PAGE_LAYOUT;
            case /*"outline"*/3:
                return ViewType.OUTLINE;
            case /*"masterPages"*/4:
                return ViewType.OUTLINE;
            case /*"web"*/5:
                return ViewType.WEB;
            default:
                return ViewType.PAGE_LAYOUT;
        }
    }

    static String viewTypeToDocx(/*ViewType*/int value)
    {
        // ViewType.Reading setting seems to be ignored by MS Word now.
        switch (value)
        {
            case ViewType.NORMAL:
                return "normal";
            case ViewType.OUTLINE:
                return "masterPages";
            case ViewType.WEB:
                return "web";
            default:
                return "";
        }
    }
    
    /// <summary>
    /// Can convert XML values to <see cref="StylePaneSortMethod"/> as defined in 
    /// ISO29500 part1, 17.18.82 ST_StyleSort (Style Sort Settings),
    /// and ECMA-376, part4, 2.15.1.87 stylePaneSortMethod
    /// </summary>
    static /*StylePaneSortMethod*/int docxToStylePaneSortMethod(String value, OoxmlComplianceInfo ci)
    {
        if (Character.isDigit(value.charAt(0))) // older pre ISO29500 way.
        {
            return StyleConvertUtil.intToStylePaneSortMethod(FormatterPal.xmlToInt(value));
        }
        else // new ISO29500 and up.
        {
            ci.markAsIsoTransitional();
            return (/*StylePaneSortMethod*/int)G_STYLE_PANE_SORT_METHOD_MAP.getValue(value, (int)StylePaneSortMethod.DEFAULT);
        }
    }
    
    static String stylePaneSortMethodToDocx(/*StylePaneSortMethod*/int value)
    {
        return G_STYLE_PANE_SORT_METHOD_MAP.getValue((int)value, "default");
    }
    
    static /*ZoomType*/int docxToZoomType(String value)
    {
        return (/*ZoomType*/int)G_ZOOM_TYPE_MAP.getValue(value, (int)ZoomType.NONE);
    }

    static String zoomTypeToDocx(/*ZoomType*/int value)
    {
        return G_ZOOM_TYPE_MAP.getValue((int)value, "none");
    }

    static /*AutoFormatDocumentType*/int docxToAutoFormatDocumentType(String value)
    {
        return (/*AutoFormatDocumentType*/int)G_AUTO_FORMAT_DOCUMENT_TYPE_MAP.getValue(value, (int)AutoFormatDocumentType.NORMAL);
    }

    static String autoFormatDocumentTypeToDocx(/*AutoFormatDocumentType*/int value)
    {
        return G_AUTO_FORMAT_DOCUMENT_TYPE_MAP.getValue((int)value, "");
    }
    
    static /*WebTarget*/int docxToWebTarget(String value)
    {
        return (/*WebTarget*/int)G_WEB_TARGET_MAP.getValue(value, (int)WebTarget.NONE);
    }
    
    static String webTargetToDocx(/*WebTarget*/int value)
    {
        return G_WEB_TARGET_MAP.getValue((int)value, "");
    }

    static /*MathBreakOnBinary*/int docxToMathBreakOnBinary(String value)
    {
        return (/*MathBreakOnBinary*/int)G_MATH_BREAK_ON_BINARY_MAP.getValue(value, (int)MathBreakOnBinary.DEFAULT);
    }
    
    static String mathBreakOnBynaryToDocx(/*MathBreakOnBinary*/int value)
    {
        return G_MATH_BREAK_ON_BINARY_MAP.getValue((int)value, "");
    }
    
    static /*MathBreakOnBinarySubtraction*/int docxToMathBreakOnBinarySubtraction(String value)
    {
        return (/*MathBreakOnBinarySubtraction*/int)G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP.getValue(value, (int)MathBreakOnBinarySubtraction.DEFAULT);
    }
    
    static String mathBreakOnBinarySubtractionToDocx(/*MathBreakOnBinarySubtraction*/int value)
    {
        return G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP.getValue((int)value, "");
    }

    static /*EditorType*/int docxToEditorType(String value)
    {
        // Fixed typo in default value: empty string -> EditorType.Unspecified.
        return (/*EditorType*/int)G_EDITOR_TYPE_MAP.getValue(value, (int)EditorType.UNSPECIFIED);
    }

    static String editorTypeToDocx(/*EditorType*/int value)
    {
        return G_EDITOR_TYPE_MAP.getValue((int)value, "");
    }
    
    static /*DisplacedByType*/int docxToDisplacedByType(String value)
    {
        return (/*DisplacedByType*/int)G_DISPLACED_BY_TYPE_MAP.getValue(value, (int)DisplacedByType.UNSPECIFIED);
    }

    static String displacedByTypeToDocx(/*DisplacedByType*/int value)
    {
        return G_DISPLACED_BY_TYPE_MAP.getValue((int)value, "");
    }
    
    //JAVA: declarations moved here to exclude java's illegal forward reference.

    private static /*final*/ StringToIntBidirectionalMap G_PROTECTION_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_KINSOKU_JUSTIFICATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ZOOM_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_AUTO_FORMAT_DOCUMENT_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WEB_TARGET_MAP = new StringToIntBidirectionalMap();        
    private static /*final*/ StringToIntBidirectionalMap G_STYLE_PANE_SORT_METHOD_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_BREAK_ON_BINARY_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_EDITOR_TYPE_MAP = new StringToIntBidirectionalMap(); 
    private static /*final*/ StringToIntBidirectionalMap G_DISPLACED_BY_TYPE_MAP = new StringToIntBidirectionalMap();          
    static/* DocxDopEnum()*/
    {
        G_ZOOM_TYPE_MAP.addEntry("none", (int)ZoomType.NONE);
        G_ZOOM_TYPE_MAP.addEntry("fullPage", (int)ZoomType.FULL_PAGE);
        G_ZOOM_TYPE_MAP.addEntry("bestFit", (int)ZoomType.PAGE_WIDTH);
        G_ZOOM_TYPE_MAP.addEntry("textFit", (int)ZoomType.TEXT_FIT);

        G_AUTO_FORMAT_DOCUMENT_TYPE_MAP.addEntry("letter", (int)AutoFormatDocumentType.LETTER);
        G_AUTO_FORMAT_DOCUMENT_TYPE_MAP.addEntry("eMail", (int)AutoFormatDocumentType.EMAIL);

        G_KINSOKU_JUSTIFICATION_MAP.addEntry("compressPunctuation", (int)JustificationMode.COMPRESS);
        G_KINSOKU_JUSTIFICATION_MAP.addEntry("compressPunctuationAndJapaneseKana", (int)JustificationMode.COMPRESS_KANA);
        G_KINSOKU_JUSTIFICATION_MAP.addEntry("doNotCompress", (int)JustificationMode.EXPAND);

        G_PROTECTION_TYPE_MAP.addEntry("trackedChanges", (int)ProtectionType.ALLOW_ONLY_REVISIONS);
        G_PROTECTION_TYPE_MAP.addEntry("comments", (int)ProtectionType.ALLOW_ONLY_COMMENTS);
        G_PROTECTION_TYPE_MAP.addEntry("forms", (int)ProtectionType.ALLOW_ONLY_FORM_FIELDS);
        G_PROTECTION_TYPE_MAP.addEntry("readOnly", (int)ProtectionType.READ_ONLY);
        G_PROTECTION_TYPE_MAP.addEntry("none", (int)ProtectionType.NO_PROTECTION);

        G_WEB_TARGET_MAP.addEntry("W3C XHTML+CSS1", (int)WebTarget.XHTML_PLUS_CSS_1);
        G_WEB_TARGET_MAP.addEntry("W3C HTML4+CSS1", (int)WebTarget.HTML_4_PLUS_CSS_1);
        G_WEB_TARGET_MAP.addEntry("W3C XHTML+CSS2", (int)WebTarget.XHTML_PLUS_CSS_2);
        G_WEB_TARGET_MAP.addEntry("W3C HTML4+CSS2", (int)WebTarget.HTML_4_PLUS_CSS_2);

        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("basedOn", (int)StylePaneSortMethod.BASED_ON);
        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("font", (int)StylePaneSortMethod.FONT);
        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("name", (int)StylePaneSortMethod.NAME);
        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("priority", (int)StylePaneSortMethod.PRIORITY);
        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("type", (int)StylePaneSortMethod.STYLE_TYPE);
        G_STYLE_PANE_SORT_METHOD_MAP.addEntry("default", (int)StylePaneSortMethod.DEFAULT);

        G_MATH_BREAK_ON_BINARY_MAP.addEntry("after", (int)MathBreakOnBinary.AFTER);
        G_MATH_BREAK_ON_BINARY_MAP.addEntry("before", (int)MathBreakOnBinary.BEFORE);
        G_MATH_BREAK_ON_BINARY_MAP.addEntry("repeat", (int)MathBreakOnBinary.REPEAT);

        G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP.addEntry("+-", (int)MathBreakOnBinarySubtraction.PLUS_MINUS);
        G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP.addEntry("-+", (int)MathBreakOnBinarySubtraction.MINUS_PLUS);
        G_MATH_BREAK_ON_BINARY_SUBTRACTION_MAP.addEntry("--", (int)MathBreakOnBinarySubtraction.MINUS_MINUS);

        G_EDITOR_TYPE_MAP.addEntry("", (int)EditorType.UNSPECIFIED);
        G_EDITOR_TYPE_MAP.addEntry("administrators", (int)EditorType.ADMINISTRATORS);
        G_EDITOR_TYPE_MAP.addEntry("contributors", (int)EditorType.CONTRIBUTORS);
        G_EDITOR_TYPE_MAP.addEntry("current", (int)EditorType.CURRENT);
        G_EDITOR_TYPE_MAP.addEntry("editors", (int)EditorType.EDITORS);
        G_EDITOR_TYPE_MAP.addEntry("everyone", (int)EditorType.EVERYONE);
        G_EDITOR_TYPE_MAP.addEntry("none", (int)EditorType.NONE);
        G_EDITOR_TYPE_MAP.addEntry("owners", (int)EditorType.OWNERS);

        G_DISPLACED_BY_TYPE_MAP.addEntry("next", (int)DisplacedByType.NEXT);
        G_DISPLACED_BY_TYPE_MAP.addEntry("prev", (int)DisplacedByType.PREV);
        G_DISPLACED_BY_TYPE_MAP.addEntry("", (int)DisplacedByType.UNSPECIFIED);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"clean",
		"normal",
		"print",
		"outline",
		"masterPages",
		"web"
	);

}

