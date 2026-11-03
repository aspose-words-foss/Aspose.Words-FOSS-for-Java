// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.Language;
import com.aspose.LocaleConverter;


/// <summary>
/// Provides methods for reading run properties from different document parts.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxRunPrReader extends NrxRunPrReaderBase
{
    DocxRunPrReader()
    {
    }

    DocxRunPrReader(DocxFldCharReader fldCharReader)
    {
        mFldCharReader = fldCharReader;
    }

    /// <summary>
    /// Reads 'w:rPr' element from the specified reader.
    /// Reader should be positioned to element start.
    /// </summary>
    /*override*/ void read(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            // WORDSNET-13301 Text that appears directly in an rPr element was not extracted.
            readTextElementsOnly(reader, runPr);
            return;
        }

        while (xmlReader.readChild("rPr"))
        {
            if ("w14".equals(xmlReader.getPrefix()))
            {
                reader.getComplianceInfo().isDocxExtensions(true);
                readW14Child((DocxDocumentReaderBase)reader, runPr);
            }

            if (xmlReader.isEndElement("rPr"))
                break;

            readChild(reader, runPr, false);
        }
    }

    /// <summary>
    /// Reads only text child elements of an w:rPr element. For read with formatting skipping.
    /// </summary>
    private void readTextElementsOnly(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("rPr"))
        {
            if (("t".equals(xmlReader.getLocalName())) || ("r".equals(xmlReader.getLocalName())))
                readChild(reader, runPr, false);
            else
                xmlReader.ignoreElementNoWarn();
        }
    }

    private static void readW14Child(DocxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"glow"*/0:
                runPr.setAttr(FontAttr.EFFECT_GLOW, DmlShapeEffectReader.read(reader));
                break;
            case /*"shadow"*/1:
                runPr.setAttr(FontAttr.EFFECT_SHADOW, DmlShapeEffectReader.read(reader));
                break;
            case /*"reflection"*/2:
                runPr.setAttr(FontAttr.EFFECT_REFLECTION, DmlShapeEffectReader.read(reader));
                break;
            case /*"textOutline"*/3:
                runPr.setAttr(FontAttr.EFFECT_OUTLINE, DmlOutlineReader.read(reader));
                break;
            case /*"textFill"*/4:
                runPr.setAttr(FontAttr.EFFECT_FILL, DmlFillReader.readTextFill(reader));
                break;
            case /*"scene3d"*/5:
                runPr.setAttr(
                    FontAttr.EFFECT_SCENE_3_D,
                    DmlScene3DReader.readScene3DProperties(reader, complianceInfo));
                break;
            case /*"props3d"*/6:
                runPr.setAttr(
                    FontAttr.EFFECT_PROPS_3_D,
                    DmlScene3DReader.readShape3DProperties(reader, complianceInfo));
                break;
            case /*"stylisticSets"*/7:
                runPr.setAttr(FontAttr.OPEN_TYPE_STYLISTIC_SETS, readStylisticSets(reader));
                break;
            case /*"ligatures"*/8:
                runPr.setAttr(FontAttr.OPEN_TYPE_LIGATURE, DocxEnum.xmlToLigature(xmlReader.readVal()));
                break;
            case /*"numForm"*/9:
                runPr.setAttr(FontAttr.OPEN_TYPE_NUM_FORM, DocxEnum.xmlToNumForm(xmlReader.readVal()));
                break;
            case /*"numSpacing"*/10:
                runPr.setAttr(FontAttr.OPEN_TYPE_NUM_SPACING, DocxEnum.xmlToNumSpacing(xmlReader.readVal()));
                break;
            case /*"cntxtAlts"*/11:
                runPr.setAttr(FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES, xmlReader.readBoolVal());
                break;
            default:
                xmlReader.ignoreElement();
                break;
        }
    }

    /// <summary>
    /// Reads an element that can occur as a child of an w:rPr element or m:rPr element in case of Office Math.
    /// </summary>
    /*override*/ void readChild(NrxDocumentReaderBase reader, RunPr runPr, boolean resiliency) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"rStyle"*/12:
            {
                int istd = reader.resolveStyleIdToIstd(xmlReader.readVal(), StyleIndex.DEFAULT_PARAGRAPH_FONT);
                runPr.setAttr(FontAttr.ISTD, istd);
                break;
            }
            case /*"rFonts"*/13:
                readFonts(xmlReader, runPr);
                break;
            case /*"font"*/14:
                if (!runPr.containsKey(FontAttr.NAME_ASCII))
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_ASCII, xmlReader.readVal(), false);
                break;
            case /*"b"*/15:
            {
                // andrnosk: RESILENCE WORDSNET-7513 Catching InvalidOperationException and write warning if there is incorrect on/off value.
                try
                {
                    AttrBoolEx exVal = xmlReader.readBoolExVal();
                    runPr.setAttr(FontAttr.BOLD, exVal);
                }
                catch (IllegalStateException ex)
                {
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, ex.getMessage());
                }

                break;
            }
            case /*"bCs"*/16:
                runPr.setAttr(FontAttr.BOLD_BI, xmlReader.readBoolExVal());
                break;
            case /*"i"*/17:
                runPr.setAttr(FontAttr.ITALIC, xmlReader.readBoolExVal());
                break;
            case /*"iCs"*/18:
                runPr.setAttr(FontAttr.ITALIC_BI, xmlReader.readBoolExVal());
                break;
            case /*"caps"*/19:
                runPr.setAttr(FontAttr.ALL_CAPS, xmlReader.readBoolExVal());
                break;
            case /*"smallCaps"*/20:
                runPr.setAttr(FontAttr.SMALL_CAPS, xmlReader.readBoolExVal());
                break;
            case /*"strike"*/21:
                runPr.setAttr(FontAttr.STRIKE_THROUGH, xmlReader.readBoolExVal());
                break;
            case /*"dstrike"*/22:
                runPr.setAttr(FontAttr.DOUBLE_STRIKE_THROUGH, xmlReader.readBoolExVal());
                break;
            case /*"outline"*/23:
                runPr.setAttr(FontAttr.OUTLINE, xmlReader.readBoolExVal());
                break;
            case /*"shadow"*/1:
                // WORDSNET-5431 Ignore w14 markup for shadow.
                if (!msString.equals(xmlReader.getNamespaceURI(), DocxNamespaces.getNamespace(
                        DocxNamespace.W1_4_MARKUP,
                        complianceInfo.getCompliance() == OoxmlComplianceCore.ISO_STRICT)))
                    runPr.setAttr(FontAttr.SHADOW, xmlReader.readBoolExVal());
                break;
            case /*"emboss"*/24:
                runPr.setAttr(FontAttr.EMBOSS, xmlReader.readBoolExVal());
                break;
            case /*"imprint"*/25:
                runPr.setAttr(FontAttr.ENGRAVE, xmlReader.readBoolExVal());
                break;
            case /*"noProof"*/26:
                runPr.setAttr(FontAttr.NO_PROOFING, xmlReader.readBoolExVal());
                break;
            case /*"vanish"*/27:
                runPr.setAttr(FontAttr.HIDDEN, xmlReader.readBoolExVal());
                break;
            case /*"snapToGrid"*/28:
                runPr.setAttr(FontAttr.SNAP_TO_GRID, xmlReader.readBoolExVal());
                break;
            case /*"webHidden"*/29:
                runPr.setAttr(FontAttr.WEB_HIDDEN, xmlReader.readBoolExVal());
                break;
            case /*"color"*/30:
                readRunPrColor(xmlReader, runPr);
                break;
            case /*"spacing"*/31:
                runPr.setAttr(FontAttr.SPACING, xmlReader.readValAsTwips(complianceInfo));
                break;
            case /*"w"*/32:
            {
                // WORDSNET-18712 Empty tag should be ignored, not thrown upon.
                String value = xmlReader.readVal();
                if (StringUtil.hasChars(value))
                    runPr.setAttr(FontAttr.SCALING, NrxXmlReader.xmlToPercent(value, complianceInfo));
                break;
            }
            case /*"kern"*/33:
                runPr.setAttr(FontAttr.KERNING, xmlReader.readValAsHalfPoints(complianceInfo));
                break;
            case /*"position"*/34:
                runPr.setAttr(FontAttr.POSITION, xmlReader.readValAsHalfPoints(complianceInfo));
                break;
            case /*"sz"*/35:
                if (!resiliency)
                    runPr.setAttr(FontAttr.SIZE, xmlReader.readValAsHalfPoints(complianceInfo));
                break;
            case /*"szCs"*/36:
                if (!resiliency)
                    runPr.setAttr(FontAttr.SIZE_BI, xmlReader.readValAsHalfPoints(complianceInfo));
                break;
            case /*"highlight"*/37:
                runPr.setAttr(FontAttr.HIGHLIGHT_COLOR, NrxRunEnum.xmlToHighlight(xmlReader.readVal()));
                break;
            case /*"u"*/38:
                readUnderline(xmlReader, runPr);
                break;
            case /*"effect"*/39:
                runPr.setAttr(FontAttr.TEXT_EFFECT, NrxRunEnum.xmlToTextEffect(xmlReader.readVal()));
                break;
            case /*"bdr"*/40:
                runPr.setAttr(FontAttr.BORDER, xmlReader.readBorder());
                break;
            case /*"shd"*/41:
                runPr.setAttr(FontAttr.SHADING, xmlReader.readShading());
                break;
            case /*"fitText"*/42:
            {
                int id = xmlReader.readIntAttribute("id", 0);
                int val = xmlReader.readValAsTwips(complianceInfo);
                runPr.setAttr(FontAttr.FIT_TEXT, new FitText(val, id));
                break;
            }
            case /*"vertAlign"*/43:
                runPr.setAttr(
                    FontAttr.VERTICAL_ALIGNMENT,
                    NrxRunEnum.xmlToRunVerticalAlignment(xmlReader.readVal()));
                break;
            case /*"rtl"*/44:
                runPr.setAttr(FontAttr.BIDI, xmlReader.readBoolExVal());
                break;
            case /*"cs"*/45:
                runPr.setAttr(FontAttr.COMPLEX_SCRIPT, xmlReader.readBoolExVal());
                break;
            case /*"em"*/46:
                runPr.setAttr(FontAttr.EMPHASIS_MARK, NrxRunEnum.xmlToEmphasisMark(xmlReader.readVal()));
                break;
            case /*"lang"*/47:
                readLang(xmlReader, runPr);
                break;
            case /*"specVanish"*/48:
                runPr.setAttr(FontAttr.SPECIAL_HIDDEN, xmlReader.readBoolExVal());
                break;
            case /*"rPrChange"*/49:
                Debug.assertTrue(mAnnotationReader != null);
                mAnnotationReader.readRunPrRevision(reader, runPr);
                break;
            case /*"ins"*/50:
                Debug.assertTrue(mAnnotationReader != null);
                DocxAnnotationReader.readRunPrInsertion(xmlReader, runPr);
                break;
            case /*"del"*/51:
                Debug.assertTrue(mAnnotationReader != null);
                DocxAnnotationReader.readRunPrDeletion(xmlReader, runPr);
                break;
            case /*"moveFrom"*/52:
                Debug.assertTrue(mAnnotationReader != null);
                DocxAnnotationReader.readRunPrMoveFrom(xmlReader, runPr);
                break;
            case /*"moveTo"*/53:
                Debug.assertTrue(mAnnotationReader != null);
                DocxAnnotationReader.readRunPrMoveTo(xmlReader, runPr);
                break;
            case /*"lit"*/54:
                runPr.setMathIsLiteral(xmlReader.readBoolVal());
                break;
            case /*"nor"*/55:
                runPr.setMathIsNormalText(xmlReader.readBoolVal());
                break;
            case /*"scr"*/56:
                runPr.setMathScript(DocxEnum.docxToMathScriptType(xmlReader.readVal()));
                break;
            case /*"sty"*/57:
                runPr.setMathStyle(DocxEnum.docxToMathStyleType(xmlReader.readVal()));
                break;
            case /*"brk"*/58:
                runPr.setMathLineBreak(NrxMathReaderUtil.readMathLineBreak(xmlReader));
                break;
            case /*"aln"*/59:
                runPr.setMathIsAlignmentPoint(xmlReader.readBoolVal());
                break;
            case /*"oMath"*/60:
                runPr.isOMath(xmlReader.readBoolVal());
                break;
            case /*"eastAsianLayout"*/61:
                readFarEastLayout(runPr, xmlReader);
                break;
            case /*"pStyle"*/62:
            case /*"textAlignment"*/63:
                xmlReader.ignoreElementUnexpected(true);
                break;
            case /*"instrText"*/64:
            case /*"t"*/65:
            {
                DocxReaderFactory.getRunReader().readText(reader, runPr);
                NrxRunReaderBase.flushRun(reader, runPr);
                break;
            }
            case /*"rPr"*/66:
                read(reader, runPr);
                break;
            case /*"r"*/67:
            {
                DocxReaderFactory.getRunReader().readTextLevelElement(reader, runPr);
                break;
            }
            case /*"fldChar"*/68:
                Debug.assertTrue(mFldCharReader != null);
                if (mFldCharReader != null)
                {
                    // RESILIENCY 25828  - a "fldChar" tag is found inside "rPr" element.
                    NrxRunReaderBase.flushRun(reader, runPr);
                    mFldCharReader.read(reader, runPr);
                }

                break;
            case /*"AlternateContent"*/69:
                // WORDSNET-27021 Handle AlternateContent for run properties.
                readAlternateContent(reader, runPr);
                break;

            default:
                xmlReader.ignoreElement();
                break;
        }
    }

    private void readAlternateContent(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("AlternateContent"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Fallback"*/70:
                {
                    while (xmlReader.readChild("Fallback"))
                    {
                        readChild(reader, runPr, false);
                    }

                    break;
                }

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /*override*/ void readFonts(NrxXmlReader xmlReader, RunPr runPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"ascii"*/71:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_ASCII, xmlReader.getValue(), false);
                    break;
                case /*"hAnsi"*/72:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_OTHER, xmlReader.getValue(), false);
                    break;
                case /*"eastAsia"*/73:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_FAR_EAST, xmlReader.getValue(), false);
                    break;
                case /*"cs"*/45:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_BI, xmlReader.getValue(), false);
                    break;
                case /*"hint"*/74:
                    runPr.setAttr(FontAttr.CHARACTER_CATEGORY_HINT, NrxRunEnum.xmlToHint(xmlReader.getValue()));
                    break;
                case /*"asciiTheme"*/75:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_ASCII, xmlReader.getValue(), true);
                    break;
                case /*"hAnsiTheme"*/76:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_OTHER, xmlReader.getValue(), true);
                    break;
                case /*"eastAsiaTheme"*/77:
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_FAR_EAST, xmlReader.getValue(), true);
                    break;
                case /*"cstheme"*/78: // This is the correct one.
                case /*"csTheme"*/79: // But let's read this too.
                    NrxXmlUtil.setFontNameIfHasChars(runPr, FontAttr.NAME_BI, xmlReader.getValue(), true);
                    break;
                default:
                    break;
            }
        }
    }

    /// <summary>
    /// Reads and sets eastAsianLayout element.
    /// </summary>
    /// <param name="runPr"></param>
    /// <param name="reader"></param>
    private static void readFarEastLayout(RunPr runPr, NrxXmlReader reader)
    {
        FarEastLayout feLayout = new FarEastLayout();
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/80:
                    feLayout.setFarEastLayoutId(reader.getValueAsInt());
                    break;
                case /*"vert"*/81:
                    feLayout.setVertical(reader.getValueAsBool());
                    break;
                case /*"vertCompress"*/82:
                    feLayout.setVerticalCompress(reader.getValueAsBool());
                    break;
                case /*"combine"*/83:
                    feLayout.setCombine(reader.getValueAsBool());
                    break;
                case /*"combineBrackets"*/84:
                    feLayout.setCombineBrackets(NrxRunEnum.xmlToCombineBrackets(reader.getValue()));
                    break;
                default:
                    break;
            }
        }

        runPr.setFarEastLayout(feLayout);
    }

    private static void readRunPrColor(NrxXmlReader xmlReader, RunPr runPr)
    {
        // WORDSNET-23566 Remove color-related attributes that may have already been read before to mimic Word.
        runPr.remove(FontAttr.COLOR);
        runPr.remove(FontAttr.THEME_COLOR);
        runPr.remove(FontAttr.THEME_SHADE);
        runPr.remove(FontAttr.THEME_TINT);

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/85:
                    runPr.setAttr(FontAttr.COLOR, NrxXmlUtil.xmlToColor(xmlReader.getValue()));
                    break;
                case /*"themeColor"*/86:
                    runPr.setAttr(FontAttr.THEME_COLOR, xmlReader.getValue());
                    break;
                case /*"themeShade"*/87:
                    runPr.setAttr(FontAttr.THEME_SHADE, xmlReader.getValue());
                    break;
                case /*"themeTint"*/88:
                    runPr.setAttr(FontAttr.THEME_TINT, xmlReader.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    private static void readUnderline(NrxXmlReader xmlReader, RunPr runPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/85:
                    runPr.setAttr(FontAttr.UNDERLINE, NrxRunEnum.xmlToUnderline(xmlReader.getValue()));
                    break;
                case /*"color"*/30:
                    runPr.setAttr(FontAttr.UNDERLINE_COLOR, NrxXmlUtil.xmlToColor(xmlReader.getValue()));
                    break;
                case /*"themeColor"*/86:
                    runPr.setAttr(FontAttr.UNDERLINE_THEME_COLOR, xmlReader.getValue());
                    break;
                case /*"themeShade"*/87:
                    runPr.setAttr(FontAttr.UNDERLINE_THEME_SHADE, xmlReader.getValue());
                    break;
                case /*"themeTint"*/88:
                    runPr.setAttr(FontAttr.UNDERLINE_THEME_TINT, xmlReader.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    private static void readLang(NrxXmlReader xmlReader, RunPr runPr)
    {
        boolean isLocaleIdSpecified = false;
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"val"*/85:
                    setLocale(runPr, FontAttr.LOCALE_ID, xmlReader.getValue());
                    isLocaleIdSpecified = true;
                    break;
                case /*"eastAsia"*/73:
                    setLocale(runPr, FontAttr.LOCALE_ID_FAR_EAST, xmlReader.getValue());
                    isLocaleIdSpecified = true;
                    break;
                case /*"bidi"*/89:
                    setLocale(runPr, FontAttr.LOCALE_ID_BI, xmlReader.getValue());
                    isLocaleIdSpecified = true;
                    break;
                default:
                    break;
            }
        }

        // Set LocaleId to some invalid value to ensure correctness of the roundtrip.
        if (!isLocaleIdSpecified)
            runPr.setAttr(FontAttr.LOCALE_ID, (int)Language.LANGUAGE_NOT_SET);
    }

    /// <summary>
    /// Sets locale considering LanguageNotSet.
    /// </summary>
    /// <remarks>
    /// See TestJira15005() for details.
    /// </remarks>
    private static void setLocale(RunPr runPr, int key, String value)
    {
        if ("x-none".equals(value))
            runPr.setAttr(key, Language.LANGUAGE_NOT_SET);
        else
            runPr.setAttr(key, LocaleConverter.docxTagToLocale(value));
    }

    void setAnnotationReader(DocxAnnotationReader annotationReader)
    {
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    /// <summary>
    /// Reads the 'w14:stylisticSets' element.
    /// </summary>
    private static /*StylisticSets*/int readStylisticSets(DocxDocumentReaderBase reader)
    {
        Debug.assertTrue(reader != null);

        /*StylisticSets*/int stylisticSets = StylisticSets.DEFAULT;
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            if (("styleSet".equals(xmlReader.getLocalName())) && xmlReader.readBoolVal())
                stylisticSets |= DocxEnum.xmlToStylisticSets(xmlReader.readId());
        }

        return stylisticSets;
    }

    private DocxAnnotationReader mAnnotationReader;
    private /*final*/ DocxFldCharReader mFldCharReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"glow",
		"shadow",
		"reflection",
		"textOutline",
		"textFill",
		"scene3d",
		"props3d",
		"stylisticSets",
		"ligatures",
		"numForm",
		"numSpacing",
		"cntxtAlts",
		"rStyle",
		"rFonts",
		"font",
		"b",
		"bCs",
		"i",
		"iCs",
		"caps",
		"smallCaps",
		"strike",
		"dstrike",
		"outline",
		"emboss",
		"imprint",
		"noProof",
		"vanish",
		"snapToGrid",
		"webHidden",
		"color",
		"spacing",
		"w",
		"kern",
		"position",
		"sz",
		"szCs",
		"highlight",
		"u",
		"effect",
		"bdr",
		"shd",
		"fitText",
		"vertAlign",
		"rtl",
		"cs",
		"em",
		"lang",
		"specVanish",
		"rPrChange",
		"ins",
		"del",
		"moveFrom",
		"moveTo",
		"lit",
		"nor",
		"scr",
		"sty",
		"brk",
		"aln",
		"oMath",
		"eastAsianLayout",
		"pStyle",
		"textAlignment",
		"instrText",
		"t",
		"rPr",
		"r",
		"fldChar",
		"AlternateContent",
		"Fallback",
		"ascii",
		"hAnsi",
		"eastAsia",
		"hint",
		"asciiTheme",
		"hAnsiTheme",
		"eastAsiaTheme",
		"cstheme",
		"csTheme",
		"id",
		"vert",
		"vertCompress",
		"combine",
		"combineBrackets",
		"val",
		"themeColor",
		"themeShade",
		"themeTint",
		"bidi"
	);

}

