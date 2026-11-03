// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/21/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.StringToObjDictionary;
import com.aspose.LocaleConverter;


/// <summary>
/// 20.1.2.2.41 txSp (Text Shape)
/// Represents class writing a text shape within a parent shape. This text shape is specifically used for
/// displaying text as it has only text related child elements.
/// </summary>
/*static*/ class DmlTextShapeWriter
{
	/* Simulation of static class by using private constructor */
	private DmlTextShapeWriter()
	{}

    static void write(DmlShape dmlShape, IDmlShapeWriterContext writer) throws Exception
    {
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;
        NrxXmlBuilder builder = writer.getBuilder();
        DmlTextShape txShape = dmlShape.getTextShape();

        if (txShape == null)
            return;

        switch (dmlShape.getDmlNodeType())
        {
            case DmlNodeType.SHAPE:
            {
                builder.startElement("a:txSp");
                writeDmlShapeTextBody("a:txBody", txShape.getTextBody(), writer);
                if (txShape.getUseShapeTextRectangle())
                    builder.writeEmptyElement("a:useSpRect");

                builder.endElement("a:txSp");
                break;
            }
            case DmlNodeType.WORDPROCESSING_SHAPE:
            {
                String tagName = isIsoStrict ? "wp:bodyPr" : "wps:bodyPr";
                writeDmlTextShapeBodyPr(tagName, txShape.getTextBody().getProperties(), writer);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected Dml node type.");
        }
    }

    static void writeDmlShapeTextBody(
        String rootTagName, 
        DmlTextBody textBody, 
        IDmlShapeWriterContext writer) throws Exception
    {
        if (textBody == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement(rootTagName);

        writeDmlTextShapeBodyPr("a:bodyPr", textBody.getProperties(), writer);
        writeDmlTextListStyles(textBody.getTextListStyles(), writer);

        for (DmlParagraph paragraph : textBody.getParagraphs())
            writeParagraph(paragraph, writer);

        builder.endElement(rootTagName);
    }

    static void writeParagraph(DmlParagraph paragraph, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement("a:p");

        writeDmlParagraphProperties("a:pPr", paragraph.getProperties(), writer);
        writeDmlParagraphTextElements("a", paragraph.getElements(), writer);
        if (!paragraph.getEndParagraphRunProperties().isEmpty())
            writeDmlRunProperties("a:endParaRPr", paragraph.getEndParagraphRunProperties(), writer);

        builder.endElement("a:p");
    }

    private static void writeDmlParagraphTextElements(String prefix, 
        ArrayList<DmlParagraphTextElementBase> textElements, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        for (DmlParagraphTextElementBase textElement : textElements)
        {
            DmlTextBreakLine textBreak = ms.as(textElement, DmlTextBreakLine.class);
            if (textBreak != null)
            {
                builder.startElement("a:br");
                writeDmlRunProperties("a:rPr", textBreak.getRunProperties(), writer);
                builder.endElement("a:br");
            }

            DmlTextField textField = ms.as(textElement, DmlTextField.class);
            if (textField != null)
            {
                builder.startElement("a:fld");
                builder.writeAttribute("id", textField.getId());
                if (StringUtil.hasChars(textField.getType()))
                    builder.writeAttribute("type", textField.getType());
                // rPr must come before pPr to take precedence over it.
                writeDmlRunProperties("a:rPr", textField.getRunProperties(), writer);
                writeDmlParagraphProperties("a:pPr", textField.getParagraphProperties(), writer);
                writeText("a", textField.getText(), builder);

                builder.endElement("a:fld");
            }

            DmlRun textRun = ms.as(textElement, DmlRun.class);

            if (textRun != null)
            {
                String tagName = msString.format("{0}:r", prefix);

                builder.startElement(tagName);

                writeDmlRunProperties("a:rPr", textRun.getRunProperties(), writer);
                writeText(prefix, textRun.getText(), builder);

                builder.endElement(tagName);
            }

            DmlTextMath textMath = ms.as(textElement, DmlTextMath.class);
            if (textMath != null)
            {
                if (textMath.isRootElement())
                {
                    builder.startElement("a14:m");
                    builder.writeAttribute("xmlns:a14", DmlExtensionsNamespace.DRAWING_MAIN);
                    DocxNamespaces namespaces = 
                        new DocxNamespaces(writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);
                    builder.writeAttribute("xmlns:m", namespaces.getMath());
                }

                DocxMathWriter.writeStart(textMath.getMathObject(), textMath.getRunProperties(), (IMathWriterContext)writer);

                writeDmlParagraphTextElements("m", textMath.getElements(), writer);

                DocxMathWriter.writeEnd(builder);

                if (textMath.isRootElement())
                    builder.endElement("a14:m");
            }
        }
    }

    private static void writeText(String prefix, String text, NrxXmlBuilder builder)
    {
        String tagName = msString.format("{0}:t", prefix);
        builder.startElement(tagName);
        builder.writeString(text);
        builder.endElement(tagName);
    }

    static void writeDmlTextListStyles(DmlTextListStyles textListStyles, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        builder.startElement("a:lstStyle");

        writeDmlParagraphProperties("a:defPPr", textListStyles.getDefaultParagraphProperties(), writer);
        writeDmlParagraphProperties("a:lvl1pPr", textListStyles.getListLevel1Style(), writer);
        writeDmlParagraphProperties("a:lvl2pPr", textListStyles.getListLevel2Style(), writer);
        writeDmlParagraphProperties("a:lvl3pPr", textListStyles.getListLevel3Style(), writer);
        writeDmlParagraphProperties("a:lvl4pPr", textListStyles.getListLevel4Style(), writer);
        writeDmlParagraphProperties("a:lvl5pPr", textListStyles.getListLevel5Style(), writer);
        writeDmlParagraphProperties("a:lvl6pPr", textListStyles.getListLevel6Style(), writer);
        writeDmlParagraphProperties("a:lvl7pPr", textListStyles.getListLevel7Style(), writer);
        writeDmlParagraphProperties("a:lvl8pPr", textListStyles.getListLevel8Style(), writer);
        writeDmlParagraphProperties("a:lvl9pPr", textListStyles.getListLevel9Style(), writer);

        //Currently we do not read these elements.
        //extLst (Extension List) §20.1.2.2.15

        builder.endElement("a:lstStyle");
    }

    private static void writeDmlParagraphProperties(
        String elementName, 
        DmlParagraphProperties props, 
        IDmlShapeWriterContext writer) throws Exception
    {
        if ((props == null) || props.isEmpty())
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(elementName);

        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.LEFT_MARGIN, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.RIGHT_MARGIN, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.LEVEL, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.TEXT_IDENTATION, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.ALIGNMENT, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.DEFAULT_TAB_SIZE, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.IS_EAST_ASIAN_LINE_BREAK_ALLOWED, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.FONT_ALIGNMENT, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.IS_LATIN_LINE_BREAK_ALLOWED, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.IS_HANGING_PUNCTUATION_ALLOWED, props, writer);

        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.LINE_SPACING, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.SPACE_BEFORE, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.SPACE_AFTER, props, writer);

        // Write bullet properties.
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.BULLET_COLOR, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.BULLET_SIZE, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.BULLET_FONT, props, writer);
        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.BULLET, props, writer);

        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.TAB_LIST, props, writer);

        if (props.hasDefaultRunProperties())
            writeDmlRunProperties("a:defRPr", props.getDefaultRunProperties(), writer);

        writeDmlParagraphPropertiesIfNotNull(DmlParagraphPropertiesIds.EXTENSIONS, props, writer);

        //Currently we do not read these elements.
        //buAutoNum (Auto-Numbered Bullet) §21.1.2.4.1
        //buBlip (Picture Bullet)
        //buChar (Character Bullet) §21.1.2.4.3
        //buClr (Color Specified) §21.1.2.4.4
        //buClrTx (Follow Text) §21.1.2.4.5
        //buFont (Specified) §21.1.2.4.6
        //buFontTx (Follow text) §21.1.2.4.7
        //buNone (No Bullet) §21.1.2.4.8
        //buSzPct (Bullet Size Percentage) §21.1.2.4.9
        //buSzPts (Bullet Size Points) §21.1.2.4.10
        //buSzTx (Bullet Size Follows Text) §21.1.2.4.11

        builder.endElement(elementName);
    }

    private static void writeDmlTextSpacing(String elementName, DmlTextSpacing spacing, IDmlShapeWriterContext writer)
    {
        if (spacing == null)
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

        builder.startElement(elementName);

        DmlPercentageTextSpacing percentageSpacing = ms.as(spacing, DmlPercentageTextSpacing.class);
        if (percentageSpacing != null)
            builder.writeElementWithAttributes("a:spcPct", "val", 
                DmlPercentageUtil.toPercentOrDmlPercent(percentageSpacing.getValue(), isIsoStrict));

        DmlPointsTextSpacing pointSpacing = ms.as(spacing, DmlPointsTextSpacing.class);
        if (pointSpacing != null)
            builder.writeElementWithAttributes("a:spcPts", "val", pointSpacing.getValue().getValue());

        builder.endElement(elementName);
    }

    static void writeDmlRunProperties(String elementName, DmlRunProperties props,
        IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(elementName);

        // Order of attributes is the same as in scheme.
        builder.writeAttribute("kumimoji", props.getDirectProperty(DmlRunPropertiesIds.KUMIMOJI));

        writeLanguageAttributeIfNotNull("lang", DmlRunPropertiesIds.LANGUAGE, props, builder);
        writeLanguageAttributeIfNotNull("altLang", DmlRunPropertiesIds.ALTERNATIVE_LANGUAGE, props, builder);

        if (props.isPropertySpecified(DmlRunPropertiesIds.FONT_SIZE))
            builder.writeAttribute("sz", props.getFontSize().getValue());
        builder.writeAttribute("b", props.getDirectProperty(DmlRunPropertiesIds.BOLD));
        builder.writeAttribute("i", props.getDirectProperty(DmlRunPropertiesIds.ITALICS));
        if (props.isPropertySpecified(DmlRunPropertiesIds.UNDERLINE))
            builder.writeAttribute("u", DmlEnum.textUnderlineTypeToDml(props.getUnderline()));
        if (props.isPropertySpecified(DmlRunPropertiesIds.STRIKETHROUGH))
            builder.writeAttribute("strike", DmlEnum.textStrikeToDml(props.getStrikethrough()));
        if (props.isPropertySpecified(DmlRunPropertiesIds.KERNING))
            builder.writeAttribute("kern", props.getKerning().getValue());
        if (props.isPropertySpecified(DmlRunPropertiesIds.CAPITALIZATION))
            builder.writeAttribute("cap", DmlEnum.capitalizationToDml(props.getCapitalization()));
        if (props.isPropertySpecified(DmlRunPropertiesIds.SPACING))
            builder.writeAttribute("spc", props.getSpacing().getValue());
        builder.writeAttribute("normalizeH", props.getDirectProperty(DmlRunPropertiesIds.NORMALIZE_HEIGHTS));
        if (props.isPropertySpecified(DmlRunPropertiesIds.BASELINE))
            builder.writeAttribute("baseline", DmlPercentageUtil.toPercentOrDmlPercent(props.getBaseline(), isIsoStrict));
        builder.writeAttribute("noProof", props.getDirectProperty(DmlRunPropertiesIds.NO_PROOFING));
        builder.writeAttribute("dirty", props.getDirectProperty(DmlRunPropertiesIds.IS_DIRTY));
        builder.writeAttribute("err", props.getDirectProperty(DmlRunPropertiesIds.HAS_SPELLING_ERROR));
        builder.writeAttribute("smtClean", props.getDirectProperty(DmlRunPropertiesIds.SMART_TAGS_CLEAN));
        builder.writeAttributeUInt("smtId", props.getDirectProperty(DmlRunPropertiesIds.SMART_TAG_ID));
        builder.writeAttribute("bmk", props.getDirectProperty(DmlRunPropertiesIds.BOOKMARK_LINK_TARGET));

        // The following objects are written as child elements.
        DmlOutlineWriter.write("a:ln", (DmlOutline)props.getDirectProperty(DmlRunPropertiesIds.OUTLINE), writer);
        DmlFillWriter.write((DmlFill)props.getDirectProperty(DmlRunPropertiesIds.FILL), writer, false);
        DmlShapeEffectsWriter.write(props.getEffects(), writer, false);
        writeHighlightColor(props, writer);
       
        if (props.isPropertySpecified(DmlRunPropertiesIds.UNDERLINE_STROKE_TX))
            builder.writeEmptyElement("a:uLnTx");
        else
            DmlOutlineWriter.write("a:uLn", (DmlOutline)props.getDirectProperty(DmlRunPropertiesIds.UNDERLINE_STROKE), writer);

        writeUnderlineFillTx(props, writer);
        writeDmlFont("a:latin", (DmlFont)props.getDirectProperty(DmlRunPropertiesIds.LATIN_FONT), builder);
        writeDmlFont("a:ea", (DmlFont)props.getDirectProperty(DmlRunPropertiesIds.EAST_ASIAN_FONT), builder);
        writeDmlFont("a:cs", (DmlFont)props.getDirectProperty(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT), builder);
        writeDmlFont("a:sym", (DmlFont)props.getDirectProperty(DmlRunPropertiesIds.SYMBOL_FONT), builder);

        DmlHlink hlinkClick = (DmlHlink)props.getDirectProperty(DmlRunPropertiesIds.HLINK_CLICK);
        if (hlinkClick != null)
            DmlHlinkWriter.writeHlink("a:hlinkClick", hlinkClick.getId(), hlinkClick.getTargetFrame(), hlinkClick.getTooltip(),
                hlinkClick.getExtensions(), writer);

        DmlHlink hlinkHover = (DmlHlink)props.getDirectProperty(DmlRunPropertiesIds.HLINK_HOVER);
        if (hlinkHover != null)
            DmlHlinkWriter.writeHlink("a:hlinkHover", hlinkHover.getId(), hlinkHover.getTargetFrame(), hlinkHover.getTooltip(),
                hlinkHover.getExtensions(), writer);

        if (props.isPropertySpecified(DmlRunPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION))
            builder.writeEmptyElement("a:rtl");

        StringToObjDictionary<DmlExtension> extLst = (StringToObjDictionary<DmlExtension>)props.getDirectProperty(DmlRunPropertiesIds.EXTENSIONS);
        if (extLst != null)
            DmlExtensionListWriter.write(extLst, writer);

        // Currently do not read these elements.
        // effectDag (Effect Container) §20.1.8.25

        builder.endElement(elementName);
    }

    /// <summary>
    /// Writes "UnderlineFillTx"
    /// </summary>
    private static void writeUnderlineFillTx(DmlRunProperties props, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        if (props.isPropertySpecified(DmlRunPropertiesIds.UNDERLINE_FILL_TX))
        {
            builder.writeEmptyElement("a:uFillTx");
        }
        else
        {
            DmlFill underlineFill = (DmlFill)props.getDirectProperty(DmlRunPropertiesIds.UNDERLINE_FILL);
            if (underlineFill != null)
            {
                builder.startElement("a:uFill");
                DmlFillWriter.write(underlineFill, writer, false);
                builder.endElement("a:uFill");
            }
        }
    }

    /// <summary>
    /// Writes the highlight color property if specified.
    /// </summary>
    /// <param name="props">The specified <see cref="DmlRunProperties"/></param>
    /// <param name="writer">The specified <see cref="IDmlShapeWriterContext"/></param>
    private static void writeHighlightColor(DmlRunProperties props, IDmlShapeWriterContext writer)
    {
        if (!props.isPropertySpecified(DmlRunPropertiesIds.HIGHLIGHT_COLOR))
            return;

        String highlightElementName = "a:highlight";
        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement(highlightElementName);
        DmlColorWriter.write((DmlColor)props.getDirectProperty(DmlRunPropertiesIds.HIGHLIGHT_COLOR), writer);
        builder.endElement(highlightElementName);
    }

    private static void writeDmlFont(String elementName, DmlFont font, NrxXmlBuilder builder)
    {
        if (font == null)
            return;

        builder.startElement(elementName);
        builder.writeAttributeString("typeface", font.getTextTypeface());
        builder.writeAttribute("panose", font.getPanoseSetting());
        builder.writeAttributeIfNotZero("pitchFamily", font.getSimilarFontFamily());
        if (font.getSimilarCharacterSet() != 1)
            builder.writeAttribute("charset", font.getSimilarCharacterSet());
        builder.endElement(elementName);
    }

    static void writeDmlTextShapeBodyPr(String tagName, DmlTextBodyProperties props,
        IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT;

        builder.startElement(tagName);

        // The following code writes bodyPr attributes in order specified in Dml scheme.
        // Ms Word uses the same order.
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.ROTATION, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.ARE_FIRST_AND_LAST_PARAGRAPHS_USE_SPACING, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.TEXT_VERTICAL_OVERFLOW, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.TEXT_HORIZONTAL_OVERFLOW, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.TEXT_ORIENTATION, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.TEXT_WRAPPING_TYPE, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.LEFT_INSET, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.TOP_INSET, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.COLUMN_NUMBER, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.SPACE_BETWEEN_COLUMNS, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.COLUMN_ORDER, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.FROM_WORD_ART, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.ANCHOR, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.ANCHOR_CENTER, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.FORCE_ANTI_ALIAS, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.IS_TEXT_UPRIGHT, props, builder);
        writeBodyPrAttributeIfNotNull(DmlTextBodyPropertiesDefaultsIds.USE_COMPATIBLE_LINE_SPACING, props, builder);

        if (props.getPresetTextWrap().getTextShapeType() != DmlTextShapeType.NONE)
        {
            builder.startElement("a:prstTxWarp");
            builder.writeAttribute("prst", DmlEnum.textShapeTypeToDml(props.getPresetTextWrap().getTextShapeType()));
            DmlGeomertyWriter.writeGuides(props.getPresetTextWrap().getGuides().getAdjustableValues(), builder, "a:avLst");
            builder.endElement("a:prstTxWarp");
        }

        // Also "a:noAutofit" might persist, but it is default value and usually is omitted.
        if (props.getAutoFitMode() instanceof DmlShapeAutoFitMode)
            builder.writeEmptyElement("a:spAutoFit");

        DmlNormalAutoFitMode normalAutoFit = ms.as(props.getAutoFitMode(), DmlNormalAutoFitMode.class);
        if (normalAutoFit != null)
        {
            builder.writeElementWithAttributes("a:normAutofit",
                "fontScale",
                DmlPercentageUtil.toPercentOrDmlPercent(normalAutoFit.getFontScale(), isIsoStrict),
                "lnSpcReduction",
                DmlPercentageUtil.toPercentOrDmlPercent(normalAutoFit.getLineSpaceReduction(), isIsoStrict));
        }

        Dml3DPropertiesWriter.writeScene3D(props.getScene3DProperties(), writer, false);
        Dml3DPropertiesWriter.writeShape3D(props.getShape3DProperties(), writer, false);

        // Write extLst (Extension List) §20.1.2.2.15
        DmlExtensionListWriter.write(props.getExtensions(), writer);

        // We do not read these child nodes.
        //flatTx (No text in 3D scene) §20.1.5.8
        //prstTxWarp (Preset Text Warp) §20.1.9.19

        builder.endElement(tagName);
    }

    private static void writeDmlParagraphPropertiesIfNotNull(
        /*DmlParagraphPropertiesIds*/int id,
        DmlParagraphProperties props,
        IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        Object value = props.getDirectProperty(id);
        if (value == null)
            return;

        switch (id)
        {
            case DmlParagraphPropertiesIds.ALIGNMENT:
            {
                if (DmlEnum.isNotSupportedValue(props.getAlignment()))
                {
                    //Explicit Enum.ToString() added for Java.
                    writer.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DRAWING_ML,
                        msString.format(WarningStrings.UNSUPPORTED_DML_TEXT_ALIGNMENT, ParagraphAlignment.toString(props.getAlignment()),
                            ParagraphAlignment.toString(DmlEnum.fixUnsupportedValue(props.getAlignment()))));
                }

                builder.writeAttribute("algn", DmlEnum.textAlignmentToDml(props.getAlignment()));
                break;
            }
            case DmlParagraphPropertiesIds.DEFAULT_TAB_SIZE:
                builder.writeAttribute("defTabSz", value);
                break;
            case DmlParagraphPropertiesIds.IS_EAST_ASIAN_LINE_BREAK_ALLOWED:
                builder.writeAttribute("eaLnBrk", value);
                break;
            case DmlParagraphPropertiesIds.FONT_ALIGNMENT:
                builder.writeAttribute("fontAlgn", DmlEnum.fontAlignmentToDml(props.getFontAlignment()));
                break;
            case DmlParagraphPropertiesIds.IS_HANGING_PUNCTUATION_ALLOWED:
                builder.writeAttribute("hangingPunct", value);
                break;
            case DmlParagraphPropertiesIds.TEXT_IDENTATION:
                builder.writeAttribute("indent", value);
                break;
            case DmlParagraphPropertiesIds.IS_LATIN_LINE_BREAK_ALLOWED:
                builder.writeAttribute("latinLnBrk", value);
                break;
            case DmlParagraphPropertiesIds.LEFT_MARGIN:
                builder.writeAttribute("marL", value);
                break;
            case DmlParagraphPropertiesIds.RIGHT_MARGIN:
                builder.writeAttribute("marR", value);
                break;
            case DmlParagraphPropertiesIds.RIGHT_TO_LEFT_FLOW_DIRECTION:
                builder.writeAttribute("rtl", value);
                break;
            case DmlParagraphPropertiesIds.LEVEL:
                builder.writeAttribute("lvl", value);
                break;
            case DmlParagraphPropertiesIds.LINE_SPACING:
                writeDmlTextSpacing("a:lnSpc", (DmlTextSpacing)value, writer);
                break;
            case DmlParagraphPropertiesIds.SPACE_AFTER:
                writeDmlTextSpacing("a:spcAft", (DmlTextSpacing)value, writer);
                break;
            case DmlParagraphPropertiesIds.SPACE_BEFORE:
                writeDmlTextSpacing("a:spcBef", (DmlTextSpacing)value, writer);
                break;
            case DmlParagraphPropertiesIds.BULLET_COLOR:
                writeBulletColor((DmlTextBulletColor)value, writer);
                break;
            case DmlParagraphPropertiesIds.BULLET_SIZE:
                writeBulletSize((DmlTextBulletSize)value, writer);
                break;
            case DmlParagraphPropertiesIds.BULLET_FONT:
                writeBulletFont((DmlTextBulletFont)value, builder);
                break;
            case DmlParagraphPropertiesIds.BULLET:
                writeBullet((DmlTextBullet)value, writer);
                break;
            case DmlParagraphPropertiesIds.TAB_LIST:
                writeTabList((TabStopCollection)value, writer);
                break;
            case DmlParagraphPropertiesIds.EXTENSIONS:
                DmlExtensionListWriter.write((StringToObjDictionary<DmlExtension>)value, writer);
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Writes a:tabLst element using specified TabStops.
    /// </summary>
    private static void writeTabList(TabStopCollection tabs, IDmlShapeWriterContext writer)
    {
        if ((tabs == null) || (tabs.getCount() == 0))
            return;

        NrxXmlBuilder builder = writer.getBuilder();
        builder.startElement("a:tabLst");

        for (int i = 0; i < tabs.getCount(); i++)
        {
            TabStop tabStop = tabs.get(i);
            String alignment = DmlEnum.tabAlignmentToDml(tabStop.getAlignment());
            builder.writeElementWithAttributes("a:tab", "pos", tabStop.getPositionTwips(), "algn", alignment);
        }

        builder.endElement(); //a:tabLst
    }

    private static void writeBullet(DmlTextBullet value, IDmlShapeWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (!value.hasBullet())
        {
            builder.writeEmptyElement("a:buNone");
        }
        else if (value.isCharBullet())
        {
            builder.writeElementWithAttributes("a:buChar", "char", value.getBulletChar());
        }
        else if (value.isAutoNumBullet())
        {
            builder.writeElementWithAttributesStart("a:buAutoNum", "type", DmlEnum.textBulletTypeToDml(value.getBulletType()));
            if (value.getStartAt() != 1)
                builder.writeAttribute("startAt", value.getStartAt());

            builder.endElement("a:buAutoNum");
        }
        else if(value.isPictureBullet())
        {
            builder.startElement("a:buBlip");
            DmlFillWriter.writeBlip(value.getPictureBullet(), writer, "a:blip");
            builder.endElement("a:buBlip");
        }
    }

    private static void writeBulletFont(DmlTextBulletFont value, NrxXmlBuilder builder)
    {
        if (value.getFollowText())
            builder.writeEmptyElement("a:buFontTx");
        else
            writeDmlFont("a:buFont", value.getFont(), builder);
    }

    private static void writeBulletSize(DmlTextBulletSize value, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        boolean isIsoStrict = (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

        switch (value.getSizeType())
        {
            case DmlTextBulletSizeType.FOLOW_TEXT:
                builder.writeEmptyElement("a:buSzTx");
                break;
            case DmlTextBulletSizeType.PERCENTAGE:
                double asFraction = DmlPercentageUtil.fromDmlPercent(value.getValue());
                builder.writeElementWithAttributes("a:buSzPct", "val", 
                    DmlPercentageUtil.toPercentOrDmlPercent(asFraction, isIsoStrict));
                break;
            case DmlTextBulletSizeType.POINTS:
                builder.writeElementWithAttributes("a:buSzPts", "val", value.getValue());
                break;
            default:
                break;
        }
    }

    private static void writeBulletColor(DmlTextBulletColor value, IDmlShapeWriterContext writer)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        if (value.getFollowText())
        {
            builder.writeEmptyElement("a:buClrTx");
        }
        else
        {
            builder.startElement("a:buClr");
            DmlColorWriter.write(value.getColor(), writer);
            builder.endElement("a:buClr");
        }
    }

    private static void writeBodyPrAttributeIfNotNull(
        /*DmlTextBodyPropertiesDefaultsIds*/int id,
        DmlTextBodyProperties props, 
        NrxXmlBuilder builder)
    {
        Object value = props.getDirectProperty(id);

        // MS Word writes the value -60000000 indicating that the default rotation (0) is used.
        // The value can only be used with unspecified or horizontal orientation.
        if ((value == null) && (id == DmlTextBodyPropertiesDefaultsIds.ROTATION) && props.hasDefaultRotation())
            value = new DmlAngle((props.getTextOrientation() == ShapeTextOrientation.HORIZONTAL) ? -60000000 : 0);

        if (value == null)
            return;

        switch (id)
        {
            case DmlTextBodyPropertiesDefaultsIds.ANCHOR:
                builder.writeAttribute("anchor", DmlEnum.textAnchoringTypeToDml(props.getAnchor()));
                break;
            case DmlTextBodyPropertiesDefaultsIds.ANCHOR_CENTER:
                builder.writeAttribute("anchorCtr", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.ARE_FIRST_AND_LAST_PARAGRAPHS_USE_SPACING:
                builder.writeAttribute("spcFirstLastPara", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.COLUMN_NUMBER:
                builder.writeAttribute("numCol", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.LEFT_INSET:
                builder.writeAttribute("lIns", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.TOP_INSET:
                builder.writeAttribute("tIns", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.RIGHT_INSET:
                builder.writeAttribute("rIns", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.BOTTOM_INSET:
                builder.writeAttribute("bIns", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.COLUMN_ORDER:
                builder.writeAttribute("rtlCol", (props.getColumnOrder() == DmlTextColumnOrder.RIGHT_TO_LEFT));
                break;
            case DmlTextBodyPropertiesDefaultsIds.FORCE_ANTI_ALIAS:
                builder.writeAttribute("forceAA", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.FROM_WORD_ART:
                builder.writeAttribute("fromWordArt", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.IS_TEXT_UPRIGHT:
                builder.writeAttributeIfTrue("upright", props.isTextUpright());
                break;
            case DmlTextBodyPropertiesDefaultsIds.ROTATION:
                builder.writeAttribute("rot", ((DmlAngle)value).getValue());
                break;
            case DmlTextBodyPropertiesDefaultsIds.SPACE_BETWEEN_COLUMNS:
                builder.writeAttribute("spcCol", value);
                break;
            case DmlTextBodyPropertiesDefaultsIds.TEXT_HORIZONTAL_OVERFLOW:
                builder.writeAttribute("horzOverflow", DmlEnum.textHorizontalOverflowTypeToDml(props.getTextHorizontalOverflow()));
                break;
            case DmlTextBodyPropertiesDefaultsIds.TEXT_VERTICAL_OVERFLOW:
                builder.writeAttribute("vertOverflow", DmlEnum.textVerticalOverflowTypeToDml(props.getTextVerticalOverflow()));
                break;
            case DmlTextBodyPropertiesDefaultsIds.TEXT_ORIENTATION:
                builder.writeAttribute("vert", DmlEnum.shapeTextOrientationToDml(props.getTextOrientation()));
                break;
            case DmlTextBodyPropertiesDefaultsIds.TEXT_WRAPPING_TYPE:
                builder.writeAttribute("wrap", DmlEnum.textWrappingTypeToDml(props.getTextWrappingType()));
                break;
            case DmlTextBodyPropertiesDefaultsIds.USE_COMPATIBLE_LINE_SPACING:
                builder.writeAttribute("compatLnSpc", value);
                break;
            default:
                break;
        }
    }

    private static void writeLanguageAttributeIfNotNull(String attr, /*DmlRunPropertiesIds*/int id,
        DmlRunProperties props,  NrxXmlBuilder builder)
    {
        Object value = props.getDirectProperty(id);
        if (value != null)
            builder.writeAttribute(attr, LocaleConverter.localeToDocxTag((/*int*/Integer)value));
    }
}

