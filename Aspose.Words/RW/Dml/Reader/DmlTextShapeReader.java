// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.Language;
import com.aspose.LocaleConverter;
import com.aspose.ms.System.Guid;


/// <summary>
/// 20.1.2.2.41 txSp (Text Shape)
/// Represent class reading a text shape within a parent shape. This text shape is specifically used for
/// displaying text as it has only text related child elements.
/// </summary>
class DmlTextShapeReader extends DmlReaderBase
{
    private DmlTextShapeReader()
    {
    }

    static DmlTextShape read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        if (!"txSp".equals(xmlReader.getLocalName()))
            return null;
        DmlTextShape result = new DmlTextShape();

        while (xmlReader.readChild("txSp"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"txBody"*/0:
                    readTextBody(reader, result.getTextBody(), "txBody");
                    break;
                case /*"useSpRect"*/1:
                    result.setUseShapeTextRectangle(true);
                    break;
                case /*"xfrm"*/2:
                    result.setTransform(DmlTransformReader.readTransform(xmlReader, complianceInfo));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return result;
    }

    static void readTextBody(DocxDocumentReaderBase reader, DmlTextBody textBody, String tagName) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"p"*/3:
                    textBody.addParagraph(readParagraph(reader));
                    break;
                case /*"lstStyle"*/4:
                    readListStyles(reader, textBody.getTextListStyles());
                    break;
                case /*"bodyPr"*/5:
                    readTextBodyProperties(reader, textBody.getProperties(), reader.getComplianceInfo());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_TextBodyProperties complex type that defines the body properties for the text body within a shape.
    /// 5.1.5.1.1 bodyPr (Body Properties)
    /// </summary>
    static void readTextBodyProperties(DocxDocumentReaderBase reader, DmlTextBodyProperties props,
        OoxmlComplianceInfo complianceInfo) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"anchor"*/6:
                    props.setAnchor(DmlEnum.dmlToTextAnchoringType(xmlReader.getValue()));
                    break;
                case /*"anchorCtr"*/7:
                    props.setAnchorCenter(xmlReader.getValueAsBool());
                    break;
                case /*"bIns"*/8:
                    props.setBottomInset(MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"lIns"*/9:
                    props.setLeftInset(MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"tIns"*/10:
                    props.setTopInset(MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"rIns"*/11:
                    props.setRightInset(MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"forceAA"*/12:
                    props.setForceAntiAlias(xmlReader.getValueAsBool());
                    break;
                case /*"fromWordArt"*/13:
                    props.setFromWordArt(xmlReader.getValueAsBool());
                    break;
                case /*"rot"*/14:
                    props.setRotation(new DmlAngle(xmlReader.getValueAsDouble()));
                    break;
                case /*"numCol"*/15:
                    props.setColumnNumber(xmlReader.getValueAsInt());
                    break;
                case /*"rtlCol"*/16:
                    props.setColumnOrder(xmlReader.getValueAsBool() ?
                        DmlTextColumnOrder.RIGHT_TO_LEFT : DmlTextColumnOrder.LEFT_TO_RIGHT);
                    break;
                case /*"spcCol"*/17:
                    props.setSpaceBetweenColumns(xmlReader.getValueAsInt());
                    break;
                case /*"compatLnSpc"*/18:
                    props.setUseCompatibleLineSpacing(xmlReader.getValueAsBool());
                    break;
                case /*"spcFirstLastPara"*/19:
                    props.setAreFirstAndLastParagraphsUseSpacing(xmlReader.getValueAsBool());
                    break;
                case /*"horzOverflow"*/20:
                    props.setTextHorizontalOverflow(DmlEnum.dmlToTextHorizontalOverflowType(xmlReader.getValue()));
                    break;
                case /*"upright"*/21:
                    props.isTextUpright(xmlReader.getValueAsBool());
                    break;
                case /*"vert"*/22:
                    props.setTextOrientation(DmlEnum.dmlToShapeTextOrientation(xmlReader.getValue()));
                    break;
                case /*"vertOverflow"*/23:
                    props.setTextVerticalOverflow(DmlEnum.dmlToTextVerticalOverflowType(xmlReader.getValue()));
                    break;
                case /*"wrap"*/24:
                    props.setTextWrappingType(DmlEnum.dmlToTextWrappingType(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        // Read children
        while (xmlReader.readChild("bodyPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"noAutofit"*/25:
                    props.setAutoFitMode(new DmlNoAutoFitMode());
                    break;
                case /*"normAutofit"*/26:
                {
                    DmlNormalAutoFitMode normalAutoFit = new DmlNormalAutoFitMode();
                    normalAutoFit.setFontScale(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("fontScale",
                            ""), 1.0, complianceInfo));
                    normalAutoFit.setLineSpaceReduction(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute("lnSpcReduction",
                            ""), complianceInfo));
                    props.setAutoFitMode(normalAutoFit);
                    break;
                }
                case /*"spAutoFit"*/27:
                    props.setAutoFitMode(new DmlShapeAutoFitMode());
                    break;
                case /*"prstTxWarp"*/28:
                    readPresetTextWrap(props, xmlReader, complianceInfo);
                    break;
                case /*"scene3d"*/29:
                    props.setScene3DProperties(DmlScene3DReader.readScene3DProperties(reader, complianceInfo));
                    break;
                case /*"sp3d"*/30:
                    props.setShape3DProperties(DmlScene3DReader.readShape3DProperties(reader, complianceInfo));
                    break;
                case /*"extLst"*/31:
                    props.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                case /*"flatTx"*/32:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    private static void readPresetTextWrap(DmlTextBodyProperties props, NrxXmlReader reader,
        OoxmlComplianceInfo complianceInfo)
    {
        String value = reader.readAttribute("prst", "");
        DmlPresetTextWarp presetTextWarp = new DmlPresetTextWarp();
        presetTextWarp.setTextShapeType(DmlEnum.dmlToTextShapeType(value));
        props.setPresetTextWrap(presetTextWarp);
        String presetXml = getPresetTextWarpXmlRepository().getPresetTextWarpXml(value);

        if (StringUtil.hasChars(presetXml))
        {
            NrxXmlReader guidesReader = new NrxXmlReader(presetXml, null);
            readPresetTextWrapGuides(value, true, props, guidesReader, complianceInfo);
        }

        // Read children
        while (reader.readChild("prstTxWarp"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"avLst"*/33:
                    DmlGeometryReader.readAdjustableValues(props.getPresetTextWrap().getGuides(), false, reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    private static void readPresetTextWrapGuides(String tag, boolean isPreset, DmlTextBodyProperties props,
        NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        while (reader.readChild(tag))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"pathLst"*/34:
                    readPathList(props, reader, complianceInfo);
                    break;
                case /*"avLst"*/33:
                    DmlGeometryReader.readAdjustableValues(props.getPresetTextWrap().getGuides(), isPreset, reader);
                    break;
                case /*"gdLst"*/35:
                    readGuides(isPreset, props, reader);
                    break;
                case /*"ahLst"*/36:
                    warnNotSupportedAndIgnoreElement(reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    private static void readPathList(DmlTextBodyProperties props, NrxXmlReader reader,
        OoxmlComplianceInfo complianceInfo)
    {
        while (reader.readChild("pathLst"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"path"*/37:
                    DmlPath path = DmlPathReader.read(reader, complianceInfo);
                    props.getPresetTextWrap().addPath(path);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    private static void readGuides(boolean isPreset, DmlTextBodyProperties props, NrxXmlReader reader)
    {
        while (reader.readChild("gdLst"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"gd"*/38:
                    DmlGeometryReader.readGuide(false, props.getPresetTextWrap().getGuides(), isPreset, reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads CT_TextListStyle complex type that specifies the list of styles associated with this body of text.
    /// 5.1.5.4.12 lstStyle (Text List Styles)
    /// </summary>
    static void readListStyles(DocxDocumentReaderBase reader, DmlTextListStyles styles) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("lstStyle"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"defPPr"*/39:
                    readParagraphProperties(styles.getDefaultParagraphProperties(), reader);
                    break;
                case /*"lvl1pPr"*/40:
                    readParagraphProperties(styles.getListLevel1Style(), reader);
                    break;
                case /*"lvl2pPr"*/41:
                    readParagraphProperties(styles.getListLevel2Style(), reader);
                    break;
                case /*"lvl3pPr"*/42:
                    readParagraphProperties(styles.getListLevel3Style(), reader);
                    break;
                case /*"lvl4pPr"*/43:
                    readParagraphProperties(styles.getListLevel4Style(), reader);
                    break;
                case /*"lvl5pPr"*/44:
                    readParagraphProperties(styles.getListLevel5Style(), reader);
                    break;
                case /*"lvl6pPr"*/45:
                    readParagraphProperties(styles.getListLevel6Style(), reader);
                    break;
                case /*"lvl7pPr"*/46:
                    readParagraphProperties(styles.getListLevel7Style(), reader);
                    break;
                case /*"lvl8pPr"*/47:
                    readParagraphProperties(styles.getListLevel8Style(), reader);
                    break;
                case /*"lvl9pPr"*/48:
                    readParagraphProperties(styles.getListLevel9Style(), reader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    static DmlParagraph readParagraph(DocxDocumentReaderBase reader) throws Exception
    {
        DmlParagraph paragraph = new DmlParagraph();
        readParagraph(paragraph, reader);
        return paragraph;
    }

    static void readParagraph(DmlParagraph paragraph, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("p"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"br"*/49:
                    paragraph.addElement(readInline(reader));
                    break;
                case /*"fld"*/50:
                    paragraph.addElement(readInline(reader));
                    break;
                case /*"r"*/51:
                    paragraph.addElement(readInline(reader));
                    break;
                case /*"m"*/52:
                    paragraph.addElement(readInline(reader));
                    break;
                case /*"pPr"*/53:
                    readParagraphProperties(paragraph.getProperties(), reader);
                    break;
                case /*"endParaRPr"*/54:
                    readRunProperties(paragraph.getEndParagraphRunProperties(), reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    static DmlParagraphTextElementBase readInline(DocxDocumentReaderBase reader) throws Exception
    {
        switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
        {
            case /*"br"*/49:
                return readBreakLine(reader);
            case /*"fld"*/50:
                return readTextField(reader);
            case /*"r"*/51:
                return readRun(reader);
            case /*"m"*/52:
                return DmlTextMathReader.read(reader);
            default:
                warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                return null;
        }
    }

    private static DmlRun readRun(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlRun run = new DmlRun();
        while (xmlReader.readChild("r"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"t"*/55:
                    run.setText(run.getText() + xmlReader.readString());
                    break;
                case /*"rPr"*/56:
                    readRunProperties(run.getRunProperties(), reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
        return run;
    }

    static void readRunProperties(DmlRunProperties properties, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String localName = xmlReader.getLocalName();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"altLang"*/57:
                    properties.setAlternativeLanguage((/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue()));
                    break;
                case /*"b"*/58:
                    properties.setBold(xmlReader.getValueAsBool());
                    break;
                case /*"baseline"*/59:
                    properties.setBaseline(DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.getValue(), complianceInfo));
                    break;
                case /*"bmk"*/60:
                    properties.setBookmarkLinkTarget(xmlReader.getValue());
                    break;
                case /*"cap"*/61:
                    properties.setCapitalization(DmlEnum.dmlToCapitalization(xmlReader.getValue()));
                    break;
                case /*"dirty"*/62:
                    properties.isDirty(xmlReader.getValueAsBool());
                    break;
                case /*"err"*/63:
                    properties.hasSpellingError(xmlReader.getValueAsBool());
                    break;
                case /*"i"*/64:
                    properties.setItalics(xmlReader.getValueAsBool());
                    break;
                case /*"kern"*/65:
                    properties.setKerning(new DmlTextPoints(xmlReader.getValueAsInt()));
                    break;
                case /*"kumimoji"*/66:
                    properties.setKumimoji(xmlReader.getValueAsBool());
                    break;
                case /*"lang"*/67:
                    properties.setLanguage((/*Language*/int)LocaleConverter.docxTagToLocale(xmlReader.getValue()));
                    break;
                case /*"noProof"*/68:
                    properties.setNoProofing(xmlReader.getValueAsBool());
                    break;
                case /*"normalizeH"*/69:
                    properties.setNormalizeHeights(xmlReader.getValueAsBool());
                    break;
                case /*"smtClean"*/70:
                    properties.setSmartTagsClean(xmlReader.getValueAsBool());
                    break;
                case /*"smtId"*/71:
                    properties.setSmartTagID(xmlReader.getValueAsUInt());
                    break;
                case /*"spc"*/72:
                    properties.setSpacing(new DmlTextPoints(xmlReader.getValueAs100thsOfPoint(complianceInfo)));
                    break;
                case /*"strike"*/73:
                    properties.setStrikethrough(DmlEnum.dmlToTextStrike(xmlReader.getValue()));
                    break;
                case /*"sz"*/74:
                    properties.setFontSize(new DmlTextPoints(xmlReader.getValueAsInt()));
                    break;
                case /*"u"*/75:
                    properties.setUnderline(DmlEnum.dmlToTextUnderlineType(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        while (xmlReader.readChild(localName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"blipFill"*/76:
                case /*"gradFill"*/77:
                case /*"grpFill"*/78:
                case /*"noFill"*/79:
                case /*"pattFill"*/80:
                case /*"solidFill"*/81:
                    properties.setFill(DmlFillReader.read(reader));
                    break;
                case /*"ln"*/82:
                    properties.setOutline(DmlOutlineReader.read(reader));
                    break;
                case /*"highlight"*/83:
                {
                    while (xmlReader.readChild("highlight"))
                    {
                        properties.setHighlightColor(DmlColorReader.read(xmlReader, complianceInfo));
                    }
                    break;
                }
                case /*"cs"*/84:
                    properties.setComplexScriptFont(readFont(xmlReader));
                    break;
                case /*"ea"*/85:
                    properties.setEastAsianFont(readFont(xmlReader));
                    break;
                case /*"sym"*/86:
                    properties.setSymbolFont(readFont(xmlReader));
                    break;
                case /*"latin"*/87:
                    properties.setLatinFont(readFont(xmlReader));
                    break;
                case /*"effectLst"*/88:
                    properties.setEffects(DmlShapeEffectReader.readEffects(reader, false, false));
                    break;
                case /*"effectDag"*/89:
                    properties.setEffects(DmlShapeEffectReader.readEffects(reader, false, true));
                    break;
                case /*"hlinkClick"*/90:
                    properties.setHlinkClick(DmlHlinkReader.read(reader));
                    break;
                case /*"hlinkMouseOver"*/91:
                    properties.setHlinkHover(DmlHlinkReader.read(reader));
                    break;
                case /*"uFill"*/92:
                    properties.setUnderlineFill(readUnderlineFill(reader));
                    break;
                case /*"uFillTx"*/93:
                    properties.setUnderlineFillTx(true);
                    break;
                case /*"uLn"*/94:
                    properties.setUnderlineStroke(DmlOutlineReader.read(reader));
                    break;
                case /*"uLnTx"*/95:
                    properties.setUnderlineStrokeTx(true);
                    break;
                case /*"rtl"*/96:
                    properties.setRightToLeftFlowDirection(true);
                    break;
                case /*"extLst"*/31:
                    properties.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    private static DmlFill readUnderlineFill(DocxDocumentReaderBase reader) throws Exception
    {
        DmlFill fill = null;
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.readChild("uFill"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"blipFill"*/76:
                case /*"gradFill"*/77:
                case /*"grpFill"*/78:
                case /*"pattFill"*/80:
                case /*"noFill"*/79:
                case /*"solidFill"*/81:
                    fill = DmlFillReader.read(reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
        return fill;
    }

    private static DmlFont readFont(NrxXmlReader reader)
    {
        DmlFont font = new DmlFont();

        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"charset"*/97:
                    font.setSimilarCharacterSet(reader.getValueAsInt());
                    break;
                case /*"panose"*/98:
                    font.setPanoseSetting(reader.getValue());
                    break;
                case /*"pitchFamily"*/99:
                    font.setSimilarFontFamily(reader.getValueAsInt());
                    break;
                case /*"typeface"*/100:
                    font.setTextTypeface(reader.getValue());
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }

        return font;
    }

    private static DmlTextField readTextField(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        DmlTextField field = new DmlTextField();
        field.setId(xmlReader.readGuidAttribute("id", Guid.Empty));
        field.setType(xmlReader.readAttribute("type", ""));
        while (xmlReader.readChild("fld"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPr"*/56:
                    readRunProperties(field.getRunProperties(), reader);
                    break;
                case /*"pPr"*/53:
                    readParagraphProperties(field.getParagraphProperties(), reader);
                    break;
                case /*"t"*/55:
                    field.setText(field.getText() + xmlReader.readString());
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
        return field;
    }

    private static void readParagraphProperties(DmlParagraphProperties properties, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String localName = xmlReader.getLocalName();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"algn"*/101:
                    properties.setAlignment(DmlEnum.dmlToTextAlignment(xmlReader.getValue()));
                    break;
                case /*"defTabSz"*/102:
                    properties.setDefaultTabSize(MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo)));
                    break;
                case /*"eaLnBrk"*/103:
                    properties.isEastAsianLineBreakAllowed(xmlReader.getValueAsBool());
                    break;
                case /*"hangingPunct"*/104:
                    properties.isHangingPunctuationAllowed(xmlReader.getValueAsBool());
                    break;
                case /*"fontAlgn"*/105:
                    properties.setFontAlignment(DmlEnum.dmlToFontAlignment(xmlReader.getValue()));
                    break;
                case /*"indent"*/106:
                    properties.setTextIdentation(xmlReader.getValueAsInt());
                    break;
                case /*"latinLnBrk"*/107:
                    properties.isLatinLineBreakAllowed(xmlReader.getValueAsBool());
                    break;
                case /*"lvl"*/108:
                    properties.setLevel(xmlReader.getValueAsInt());
                    break;
                case /*"marL"*/109:
                    properties.setLeftMargin(xmlReader.getValueAsInt());
                    break;
                case /*"marR"*/110:
                    properties.setRightMargin(xmlReader.getValueAsInt());
                    break;
                case /*"rtl"*/96:
                    properties.setRightToLeftFlowDirection(xmlReader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        while (xmlReader.readChild(localName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"defRPr"*/111:
                    properties.hasDefaultRunProperties(true);
                    readRunProperties(properties.getDefaultRunProperties(), reader);
                    break;
                case /*"lnSpc"*/112:
                    properties.setLineSpacing(readTextSpacing(xmlReader, complianceInfo));
                    break;
                case /*"spcAft"*/113:
                    properties.setSpaceAfter(readTextSpacing(xmlReader, complianceInfo));
                    break;
                case /*"spcBef"*/114:
                    properties.setSpaceBefore(readTextSpacing(xmlReader, complianceInfo));
                    break;
                    // Currently not implemented.
                case /*"buAutoNum"*/115:
                    properties.setBullet(readAutoNumBullet(xmlReader));
                    break;
                case /*"buBlip"*/116:
                    properties.setBullet(readBlipBullet(reader));
                    break;
                case /*"buChar"*/117:
                    properties.setBullet(readCharBullet(xmlReader));
                    break;
                case /*"buNone"*/118: // Sent empty bullet its type is none by default.
                    properties.setBullet(new DmlTextBullet());
                    break;
                case /*"buClr"*/119:
                case /*"buClrTx"*/120:
                    properties.setBulletColor(readBulletColor(xmlReader, complianceInfo));
                    break;
                case /*"buFont"*/121:
                case /*"buFontTx"*/122:
                    properties.setBulletFont(readBulletFont(xmlReader));
                    break;
                case /*"buSzPct"*/123:
                case /*"buSzPts"*/124:
                case /*"buSzTx"*/125:
                    properties.setBulletSize(readBulletSize(xmlReader, complianceInfo));
                    break;
                case /*"tabLst"*/126:
                    properties.setTabList(readTabList(xmlReader, complianceInfo));
                    break;
                case /*"extLst"*/31:
                    properties.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }
    }

    private static TabStopCollection readTabList(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        TabStopCollection tabStops = new TabStopCollection();

        while (xmlReader.readChild("tabLst"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tab"*/127:
                {
                    /*TabAlignment*/int alignment = TabAlignment.CENTER;
                    int pos = 0;

                    while (xmlReader.moveToNextAttribute())
                    {
                        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                        {
                            case /*"algn"*/101:
                                alignment = NrxParaEnum.xmlToTabAlignment(xmlReader.getValue(), complianceInfo);
                                break;
                            case /*"pos"*/128:
                                pos = MathUtil.doubleToInt(xmlReader.getValueAsEmus(complianceInfo));
                                break;
                            default:
                                warnUnexpected(xmlReader);
                                break;
                        }
                    }

                    tabStops.add(new TabStop(pos, alignment, TabLeader.NONE));
                    break;
                }
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        return tabStops;
    }

    private static DmlTextBullet readAutoNumBullet(NrxXmlReader xmlReader)
    {
        DmlTextBullet bullet = new DmlTextBullet();
        bullet.setStartAt(xmlReader.readIntAttribute("startAt", 1));
        bullet.setBulletType(DmlEnum.dmlToTextBulletType(xmlReader.readAttribute("type", "")));
        return bullet;
    }

    private static DmlTextBullet readBlipBullet(DocxDocumentReaderBase reader) throws Exception
    {
        DmlTextBullet bullet = new DmlTextBullet();

        while (reader.getXmlReader().readChild("buBlip"))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"blip"*/129:
                    bullet.setPictureBullet(DmlFillReader.readBlip(reader));
                    break;
                default:
                    warnUnexpected(reader.getXmlReader());
                    break;
            }
        }

        return bullet;
    }

    private static DmlTextBullet readCharBullet(NrxXmlReader xmlReader)
    {
        DmlTextBullet bullet = new DmlTextBullet();
        bullet.setBulletChar(xmlReader.readAttribute("char", ""));
        return bullet;
    }

    private static DmlTextBulletColor readBulletColor(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        DmlTextBulletColor bulletColor = new DmlTextBulletColor();

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"buClr"*/119:
                bulletColor.setColor(DmlColorReader.readColor(xmlReader, complianceInfo));
                break;
            case /*"buClrTx"*/120:
            default:
                break;
        }

        return bulletColor;
    }

    private static DmlTextBulletFont readBulletFont(NrxXmlReader xmlReader)
    {
        DmlTextBulletFont bulletFont = new DmlTextBulletFont();

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"buFont"*/121:
                bulletFont.setFont(readFont(xmlReader));
                break;
            case /*"buFontTx"*/122:
            default:
                break;
        }

        return bulletFont;
    }

    private static DmlTextBulletSize readBulletSize(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        double value = 0.0;
        /*DmlTextBulletSizeType*/int sizeType = DmlTextBulletSizeType.FOLOW_TEXT;

        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"buSzPct"*/123:
                double asFraction =
                    DmlPercentageUtil.fromPercentOrDmlPercent(xmlReader.readAttribute(""), 1.0, complianceInfo);
                value = DmlPercentageUtil.toDmlPercent(asFraction);
                sizeType = DmlTextBulletSizeType.PERCENTAGE;
                break;
            case /*"buSzPts"*/124:
                value = xmlReader.readDoubleAttribute(1.0);
                sizeType = DmlTextBulletSizeType.POINTS;
                break;
            case /*"buSzTx"*/125:
            default:
                break;
        }

        return new DmlTextBulletSize(value, sizeType);
    }

    private static DmlTextSpacing readTextSpacing(NrxXmlReader reader, OoxmlComplianceInfo complianceInfo)
    {
        DmlTextSpacing result = null;
        String parentElement = reader.getLocalName();
        while (reader.readChild(parentElement))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"spcPct"*/130:
                {
                    double value =  DmlPercentageUtil.fromPercentOrDmlPercent(reader.readAttribute(""), 1.0, complianceInfo);
                    DmlPercentageTextSpacing spacing = new DmlPercentageTextSpacing(value);
                    result = spacing;
                    break;
                }
                case /*"spcPts"*/131:
                {
                    int value = reader.readIntAttribute(-1);
                    DmlPointsTextSpacing spacing = new DmlPointsTextSpacing(new DmlTextPoints(value));
                    result = spacing;
                    break;
                }
                default:
                warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }
        return result;
    }

    private static DmlTextBreakLine readBreakLine(DocxDocumentReaderBase reader) throws Exception
    {
        DmlTextBreakLine breakLine = new DmlTextBreakLine();
        while (reader.getXmlReader().readChild("br"))
        {
            switch (gStringSwitchMap.of(reader.getXmlReader().getLocalName()))
            {
                case /*"rPr"*/56:
                    readRunProperties(breakLine.getRunProperties(), reader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader.getXmlReader());
                    break;
            }
        }
        return breakLine;
    }

    private static IPresetTextWarpXmlRepository getPresetTextWarpXmlRepository()
    {
        if (mPresetTextWarpXmlRepository == null)
            mPresetTextWarpXmlRepository = new PresetTextWarpXmlRepository();
        return mPresetTextWarpXmlRepository;
    }

    private static IPresetTextWarpXmlRepository mPresetTextWarpXmlRepository;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"txBody",
		"useSpRect",
		"xfrm",
		"p",
		"lstStyle",
		"bodyPr",
		"anchor",
		"anchorCtr",
		"bIns",
		"lIns",
		"tIns",
		"rIns",
		"forceAA",
		"fromWordArt",
		"rot",
		"numCol",
		"rtlCol",
		"spcCol",
		"compatLnSpc",
		"spcFirstLastPara",
		"horzOverflow",
		"upright",
		"vert",
		"vertOverflow",
		"wrap",
		"noAutofit",
		"normAutofit",
		"spAutoFit",
		"prstTxWarp",
		"scene3d",
		"sp3d",
		"extLst",
		"flatTx",
		"avLst",
		"pathLst",
		"gdLst",
		"ahLst",
		"path",
		"gd",
		"defPPr",
		"lvl1pPr",
		"lvl2pPr",
		"lvl3pPr",
		"lvl4pPr",
		"lvl5pPr",
		"lvl6pPr",
		"lvl7pPr",
		"lvl8pPr",
		"lvl9pPr",
		"br",
		"fld",
		"r",
		"m",
		"pPr",
		"endParaRPr",
		"t",
		"rPr",
		"altLang",
		"b",
		"baseline",
		"bmk",
		"cap",
		"dirty",
		"err",
		"i",
		"kern",
		"kumimoji",
		"lang",
		"noProof",
		"normalizeH",
		"smtClean",
		"smtId",
		"spc",
		"strike",
		"sz",
		"u",
		"blipFill",
		"gradFill",
		"grpFill",
		"noFill",
		"pattFill",
		"solidFill",
		"ln",
		"highlight",
		"cs",
		"ea",
		"sym",
		"latin",
		"effectLst",
		"effectDag",
		"hlinkClick",
		"hlinkMouseOver",
		"uFill",
		"uFillTx",
		"uLn",
		"uLnTx",
		"rtl",
		"charset",
		"panose",
		"pitchFamily",
		"typeface",
		"algn",
		"defTabSz",
		"eaLnBrk",
		"hangingPunct",
		"fontAlgn",
		"indent",
		"latinLnBrk",
		"lvl",
		"marL",
		"marR",
		"defRPr",
		"lnSpc",
		"spcAft",
		"spcBef",
		"buAutoNum",
		"buBlip",
		"buChar",
		"buNone",
		"buClr",
		"buClrTx",
		"buFont",
		"buFontTx",
		"buSzPct",
		"buSzPts",
		"buSzTx",
		"tabLst",
		"tab",
		"pos",
		"blip",
		"spcPct",
		"spcPts"
	);

}

