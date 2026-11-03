// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.Language;
import com.aspose.LocaleConverter;


abstract class NrxRunPrWriterBase
{
    /// <summary>
    /// Writes a run formatting properties into the writer in DOCX and WML formats.
    /// RK I don't like writePPrElementStart here.
    /// NOTE: this class should be immutable (state cannot be modified after it is created).
    /// </summary>
    /// <param name="runPr">Run properties to write.</param>
    /// <param name="attrSource">The attribute source that is used to resolve full values of BoolEx attributes.</param>
    /// <param name="writePPrElementStart">Write w:pPr element start before writing w:rPr element. Required to avoid writing empty w:pPr declarations (cosmetic).</param>
    /// <param name="writer">The writer to write to.</param>
    /// <returns>True if run properties were written. Required to avoid writing empty w:pPr declarations (cosmetic).</returns>
    boolean writeForNodes(RunPr runPr, 
        IRunAttrSource attrSource, 
        boolean writePPrElementStart,
        INrxWriterContext writer) throws Exception
    {
        NrxXmlBuilder builder = writer.getBuilder();

        if (runPr.isMathRunPr() && isWriteMathPr(writer))
            writeMathPr(writer, runPr);

        if (runPr.getFormatRevision() != null)
        {
            // Calculate and write the AfterChanges attribute collection.
            RunPr afterChanges = AttrCollectionExtension.deepClone(runPr);
            afterChanges.acceptFormatRevision();
            // Force writing w:rPr always here because we need it to contain w:rPrChange that we always write below.
            writeStart(afterChanges, attrSource, true, writePPrElementStart, true, writer, false);

            // Write the BeforeChanges attribute collection.
            builder.writeRevisionStart(runPr.getFormatRevision(), "w:rPrChange", writer.getNextAnnotationId());
            if (writeStart(runPr, attrSource, true, false, false, writer, true))
                builder.endElement("w:rPr"); 
            builder.writeRevisionEnd(); //w:rPrChange

            builder.endElement("w:rPr"); 
            return true;
        }
        else if (writeStart(runPr, attrSource, true, writePPrElementStart, false, writer, false))
        {
            builder.endElement("w:rPr"); 
            return true;
        }
        else
            return false;
    }

    /// <summary>
    /// Writes a complete run properties element.
    /// Writes without a reference to the character style and without pPr element start.
    /// </summary>
    /// <param name="runPr">The attributes to write?</param>
    /// <param name="attrSource">The attribute source that is used to resolve full values of BoolEx attributes.</param>
    /// <param name="writer">The writer to write to.</param>
    static void writeForStyles(RunPr runPr, IRunAttrSource attrSource, INrxWriterContext writer) throws Exception
    {
        if (runPr == null)
            return;

        if (runPr.getFormatRevision() != null)
        {
            // Calculate and write the AfterChanges attribute collection.
            RunPr afterChanges = AttrCollectionExtension.deepClone(runPr);
            afterChanges.acceptFormatRevision();
            // Force writing w:rPr always here because we need it to contain w:rPrChange that we always write below.
            writeStart(afterChanges, attrSource, false, false, true, writer, false);

            // Write the BeforeChanges attribute collection.
            // It seems that Word uses 0 as Id for style revisions.
            writer.getBuilder().writeRevisionStart(runPr.getFormatRevision(), "w:rPrChange", 0);
            if (writeStart(runPr, attrSource, false, false, false, writer, true))
                writer.getBuilder().endElement("w:rPr");
            writer.getBuilder().writeRevisionEnd(); //w:rPrChange

            writer.getBuilder().endElement("w:rPr");
        }
        else if (writeStart(runPr, attrSource, false, false, false, writer, false))
            writer.getBuilder().endElement("w:rPr");
    }

    protected abstract boolean isWriteMathPr(INrxWriterContext writer);

    /// <summary>
    /// Write a number of math-specific runPr values into m:rPr
    /// </summary>
    private void writeMathPr(INrxWriterContext writer, RunPr runPr)
    {
        NrxXmlBuilder builder = writer.getBuilder();
        if (runPr.getCount() > 0) // we have at least one non-default pr in m:rPr, so lets write it.
        {
            builder.startElement("m:rPr");
            builder.writeValIfTrue("m:lit", runPr.getMathIsLiteral());
            builder.writeValIfTrue("m:nor", runPr.getMathIsNormalText());

            /*MathScript*/int mathScript = runPr.getMathScript();
            if (mathScript != MathScript.DEFAULT)
                builder.writeVal("m:scr", DocxEnum.mathScriptTypeToDocx(mathScript));

            /*MathStyle*/int mathStyle = runPr.getMathStyle();
            if (mathStyle != MathStyle.DEFAULT)
                builder.writeVal("m:sty", DocxEnum.mathStyleTypeToDocx(mathStyle));

            if (runPr.getMathLineBreak() != null)
                writeMathLineBreak(runPr.getMathLineBreak(), builder);

            builder.writeValIfTrue("m:aln", runPr.getMathIsAlignmentPoint());

            builder.endElement("m:rPr");
        }
    }

    protected abstract void writeMathLineBreak(MathLineBreak mathLineBreak, NrxXmlBuilder builder);

    private static boolean writeStart(
        RunPr attrs, 
        IRunAttrSource attrSource, 
        boolean isWriteStyleRef,
        boolean isWritePPrElementStart,
        boolean isForceWriteRPrElementStart,
        INrxWriterContext writer,
        boolean isFormattingRevision) throws Exception
    {
        if (attrs == null)
            return false;

        boolean isDocx = writer.isDocx();
        boolean isIsoStrict = isDocx && (writer.getCompliance() == OoxmlComplianceCore.ISO_STRICT);

        // Counter is used to determine if there are any properties that need to be written in w:rPr declaration.
        // That is used to avoid writing empty w:rPr declarations.
        // Checking runPr.Count does not help here because there can be attributes that should not be written in WordML.
        int writableAttributesCounter = 0;

        String styleId = null;
        ComplexFontName fontAscii = null;
        ComplexFontName fontHAnsi = null;
        ComplexFontName fontFarEast = null;
        ComplexFontName fontCs = null;
        String hint = null;

        Object bold = null;
        Object boldCs = null;
        Object italic = null;
        Object italicCs = null;
        Object caps = null;
        Object smallCaps = null;
        Object strike = null;
        Object doubleStrike = null;
        Object outline = null;
        Object shadow = null;
        Object emboss = null;
        Object imprint = null;
        Object noProof = null;
        Object snapToGrid = null;
        Object vanish = null;
        Object webHidden = null;
        Object color = null;
        Object themeColor = null;
        Object themeShade = null;
        Object themeTint = null;

        Object spacing = null;
        Object w = null;
        Object kern = null;
        Object position = null;
        Object size = null;
        Object sizeCs = null;
        Object highlight = null;

        String underlineValue = null;
        String underlineColor = null;

        String effect = null;
        Border border = null;
        Shading shading = null;

        FitText fitText = null;

        String vertAlign = null;
        Object rtl = null;
        Object cs = null;
        String em = null;

        String hyphen = null;
        String hyphenRule = null;

        String lang = null;
        String langFarEast = null;
        String langBidi = null;

        FarEastLayout feLayout = null;

        Object specVanish = null;

        Object wxFont = null;
        Object wxSymFont = null;
        Object wxSymChar = null;

        boolean oMath = false;

        Theme theme = writer.getDocument().getThemeInternal();

        // Retrieves run attributes from the specified WordAttrCollection.

        // This is the main loop to collect the properties.
        for (int k = 0; k < attrs.getCount(); k++)
        {
            int key = attrs.getKey(k);
            Object value = attrs.getByIndex(k);

            // RK By default, lets increment the number of properties collected so we don't
            // have to do it in every case statement.
            writableAttributesCounter++;

            switch (key)
            {
                case FontAttr.ISTD:
                {
                    int istd = (/*int*/Integer)value;

                    if (isWriteStyleRef && WordUtil.isValidIstd(istd))
                    {
                        styleId = writer.getStyleId(istd);
                    }
                    else
                    {
                        // We need to set it explicitly here to merge revised RunProps correctly.
                        styleId = null;
                        writableAttributesCounter--;
                    }
                    break;
                }

                case FontAttr.NAME_ASCII:
                    fontAscii = (ComplexFontName)value;
                    if (!isDocx && fontAscii.isThemeFont())
                        writableAttributesCounter--;
                    break;
                case FontAttr.NAME_FAR_EAST:
                    fontFarEast = (ComplexFontName)value;
                    if (!isDocx && fontFarEast.isThemeFont())
                        writableAttributesCounter--;
                    break;
                case FontAttr.NAME_OTHER:
                    fontHAnsi = (ComplexFontName)value;
                    if (!isDocx && fontHAnsi.isThemeFont())
                        writableAttributesCounter--;
                    break;
                case FontAttr.NAME_BI:
                    fontCs = (ComplexFontName)value;
                    if (!isDocx && fontCs.isThemeFont())
                        writableAttributesCounter--;
                    break;
                case FontAttr.CHARACTER_CATEGORY_HINT:
                    hint = NrxRunEnum.hintToXml((/*CharacterCategory*/Integer)value, isDocx);
                    break;
                case FontAttr.UNDERLINE:
                    underlineValue = NrxRunEnum.underlineToXml((/*Underline*/Integer)value, isDocx);
                    break;
                case FontAttr.UNDERLINE_COLOR:
                    underlineColor = NrxXmlUtil.colorToXml((DrColor)value);
                    break;
                case FontAttr.UNDERLINE_THEME_COLOR:
                    if (!isDocx)
                        writableAttributesCounter--;
                    break;
                case FontAttr.UNDERLINE_THEME_SHADE:
                    if (!isDocx)
                        writableAttributesCounter--;
                    break;
                case FontAttr.UNDERLINE_THEME_TINT:
                    if (!isDocx)
                        writableAttributesCounter--;
                    break;
                case FontAttr.HYPHEN_CHAR:
                    // RK Do nothing. Seems not used in DOCX.
                    writableAttributesCounter--;
                    break;
                case FontAttr.HYPHEN_RULE:
                    if (isDocx)
                        writableAttributesCounter--;    // RK Do nothing. Seems not used in DOCX.
                    else
                        hyphenRule = NrxRunEnum.hyphenRuleToXml((/*HyphenRule*/Integer)value);
                    break;
                case FontAttr.LOCALE_ID:
                    lang = getLang((/*int*/Integer)value, isDocx);
                    if (!StringUtil.hasChars(lang) && ((/*int*/Integer)value != (int)Language.INVARIANT_CULTURE))
                    {
                        writableAttributesCounter--;
                        lang = null;
                    }
                    break;
                case FontAttr.LOCALE_ID_FAR_EAST:
                    langFarEast = getLang((/*int*/Integer)value, isDocx);
                    if (!StringUtil.hasChars(langFarEast) && ((/*int*/Integer)value != (int)Language.INVARIANT_CULTURE))
                    {
                        writableAttributesCounter--;
                        langFarEast = null;
                    }
                    break;
                case FontAttr.LOCALE_ID_BI:
                    langBidi = getLang((/*int*/Integer)value, isDocx);
                    if (!StringUtil.hasChars(langBidi) && ((/*int*/Integer)value != (int)Language.INVARIANT_CULTURE))
                    {
                        writableAttributesCounter--;
                        langBidi = null;
                    }
                    break;
                case FontAttr.BOLD:
                    bold = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.BOLD_BI:
                    boldCs = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.ITALIC:
                    italic = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.ITALIC_BI:
                    italicCs = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.ALL_CAPS:
                    caps = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.SMALL_CAPS:
                    smallCaps = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.STRIKE_THROUGH:
                    strike = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.DOUBLE_STRIKE_THROUGH:
                    doubleStrike = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.SHADOW:
                    shadow = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.EMBOSS:
                    emboss = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.ENGRAVE:
                    imprint = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.NO_PROOFING:
                    noProof = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.OUTLINE:
                    outline = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.BIDI:
                    rtl = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.COMPLEX_SCRIPT:
                    cs = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.HIDDEN:
                    vanish = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.SPECIAL_HIDDEN:
                    specVanish = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.SNAP_TO_GRID:
                    snapToGrid = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.WEB_HIDDEN:
                    // TODO 2 Find where in DOC.
                    webHidden = boolExToBool(value, attrSource, key);
                    break;
                case FontAttr.SIZE:
                    size = value;
                    break;
                case FontAttr.SIZE_BI:
                    sizeCs = value;
                    break;
                case FontAttr.KERNING:
                    kern = value;
                    break;
                case FontAttr.SPACING:
                    spacing = value;
                    break;
                case FontAttr.POSITION:
                    position = value;
                    break;
                case FontAttr.SCALING:
                    w = value;
                    break;
                case FontAttr.BORDER:
                    border = (Border)value;
                    if (border.isInherited())
                        writableAttributesCounter--;
                    break;
                case FontAttr.SHADING:
                    shading = (Shading)value;
                    if (shading.isInherited())
                        writableAttributesCounter--;
                    break;
                case FontAttr.COLOR:
                    color = value;
                    break;
                case FontAttr.THEME_COLOR:
                    if (isDocx)
                        themeColor = value;
                    else
                        writableAttributesCounter--;
                    break;
                case FontAttr.THEME_SHADE:
                    if (isDocx)
                        themeShade = value;
                    else
                        writableAttributesCounter--;
                    break;
                case FontAttr.THEME_TINT:
                    if (isDocx)
                        themeTint = value;
                    else
                        writableAttributesCounter--;
                    break;
                case FontAttr.HIGHLIGHT_COLOR:
                    highlight = NrxRunEnum.highlightToXml((DrColor)value, isDocx);
                    break;
                case FontAttr.TEXT_EFFECT:
                    effect = NrxRunEnum.textEffectToXml((/*TextEffect*/Integer)value, isDocx);
                    break;
                case FontAttr.VERTICAL_ALIGNMENT:
                    vertAlign = NrxRunEnum.runVerticalAlignmentToXml((/*RunVerticalAlignment*/Integer)value);
                    break;
                case RevisionAttr.INSERT_REVISION:
                case RevisionAttr.DELETE_REVISION:
                case RevisionAttr.MOVE_FROM_REVISION:
                case RevisionAttr.MOVE_TO_REVISION:
                {
                    // Do not write insert/delete/move revision inside a format revision.
                    if (!isFormattingRevision)
                    {
                        if (!(attrSource instanceof Paragraph))         // This code was not in WML, but okay for now.
                            writableAttributesCounter--;
                    }
                    break;
                }
                case RevisionAttr.FORMAT_REVISION:
                    break;
                case FontAttr.LINE_BREAK_CLEAR:
                    // not written in WordML
                    writableAttributesCounter--;
                    break;
                case FontAttr.MATH_IS_O_MATH:
                    oMath = true;
                    break;
                case FontAttr.EMPHASIS_MARK:
                    em = NrxRunEnum.emphasisMarkToXml((/*EmphasisMark*/Integer)value, isDocx);
                    break;
                case FontAttr.FAR_EAST_LAYOUT:
                    feLayout = (FarEastLayout)value;
                    break;
                case FontAttr.EFFECT_GLOW:
                case FontAttr.EFFECT_SHADOW:
                case FontAttr.EFFECT_REFLECTION:
                case FontAttr.EFFECT_OUTLINE:
                case FontAttr.EFFECT_FILL:
                case FontAttr.EFFECT_SCENE_3_D:
                case FontAttr.EFFECT_PROPS_3_D:
                case FontAttr.OPEN_TYPE_LIGATURE:
                case FontAttr.OPEN_TYPE_NUM_FORM:
                case FontAttr.OPEN_TYPE_NUM_SPACING:
                case FontAttr.OPEN_TYPE_STYLISTIC_SETS:
                case FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES:
                    // Writable, written in WriteW14Attributes().
                    break;
                case FontAttr.FIT_TEXT:
                    fitText = (FitText)value;
                    break;
                default:
                    writableAttributesCounter--;
                    break;
            }
        }

        // Write properties in proper order.
        // Although the order of elements is not important,
        // keeping the same order as in MS Word 2003 generated xml
        // makes comparison and analysis more convenient.

        // Attributes with null values are not written by DocxBuilder.WriteXXX methods.
        // Check is performed inside DocxBuilder methods.

        if ((writableAttributesCounter == 0) && !isForceWriteRPrElementStart)
            return false;

        NrxXmlBuilder builder = writer.getBuilder();

        if (isWritePPrElementStart)
            builder.startElement("w:pPr");

        builder.startElement("w:rPr");

        // Write insert and delete revisions only if not writing a format revision.
        if (!isFormattingRevision)
        {
            // This is nasty, is there a better way?
            if (attrSource instanceof Paragraph)
            {
                if (attrs.hasInsertRevision())
                    builder.writeRevision(attrs.getInsertRevision(), writer.getNextAnnotationId());
                if (attrs.hasDeleteRevision())
                    builder.writeRevision(attrs.getDeleteRevision(), writer.getNextAnnotationId());
                if (attrs.hasMoveFromRevision())
                    builder.writeRevision(attrs.getMoveFromRevision(), writer.getNextAnnotationId());
                if (attrs.hasMoveToRevision())
                    builder.writeRevision(attrs.getMoveToRevision(), writer.getNextAnnotationId());
            }
        }
        
        // TODO 6139. See "[MS-OI29500] 2.1.243 Part 1 Section 17.7.5.4, rPr (Run Properties)".
        // The standard states that the cs, highlight, oMath, rPrChange, rStyle, and rtl elements are valid children of the rPr element. 
        // Word does not allow these elements to be children of the rPr element.
        builder.writeVal("w:rStyle", styleId);
        if (isDocx)
        {
            builder.writeValIfTrue("w:oMath", oMath);
            
            builder.writeElementWithAttributes(
                "w:rFonts",
                "w:ascii", getFontNameOrNull(fontAscii),
                "w:eastAsia", getFontNameOrNull(fontFarEast),
                "w:hAnsi", getFontNameOrNull(fontHAnsi),
                "w:asciiTheme", getThemeNameOrNull(fontAscii), 
                "w:eastAsiaTheme", getThemeNameOrNull(fontFarEast),
                "w:hAnsiTheme", getThemeNameOrNull(fontHAnsi),
                "w:cs", getFontNameOrNull(fontCs),
                "w:cstheme", getThemeNameOrNull(fontCs),  // Yes, it is "cstheme", not "csTheme"!
                "w:hint", hint);
        }
        else
        {
            builder.writeElementWithAttributes(
                "w:rFonts",
                "w:ascii", ComplexFontName.resolve(fontAscii, theme),
                "w:fareast", ComplexFontName.resolve(fontFarEast, theme),
                "w:h-ansi", ComplexFontName.resolve(fontHAnsi, theme),
                "w:cs", ComplexFontName.resolve(fontCs, theme),
                "w:hint", hint);
        }

        builder.writeVal("w:b", bold);
        builder.writeVal(isDocx ? "w:bCs" : "w:b-cs", boldCs);
        builder.writeVal("w:i", italic);
        builder.writeVal(isDocx ? "w:iCs" : "w:i-cs", italicCs);
        builder.writeVal("w:caps", caps);
        builder.writeVal("w:smallCaps", smallCaps);
        builder.writeVal("w:strike", strike);
        builder.writeVal("w:dstrike", doubleStrike);
        builder.writeVal("w:outline", outline);
        builder.writeVal("w:shadow", shadow);
        builder.writeVal("w:emboss", emboss);
        builder.writeVal("w:imprint", imprint);
        builder.writeVal("w:noProof", noProof);
        builder.writeVal("w:snapToGrid", snapToGrid);
        builder.writeVal("w:vanish", vanish);
        builder.writeVal("w:webHidden", webHidden);
        
        if (isDocx)
        {
            builder.writeElementWithAttributes(
                "w:color",
                "w:val", color,
                "w:themeColor", themeColor,
                "w:themeShade", themeShade,
                "w:themeTint", themeTint);
        }
        else
        {
            builder.writeVal("w:color", color);
        }
        
        builder.writeVal("w:spacing", spacing);
        if ((w != null) && isIsoStrict)
            builder.writeVal("w:w", NrxXmlBuilder.percentToXml((/*int*/Integer)w));
        else
            builder.writeVal("w:w", w);
        builder.writeVal("w:kern", kern);
        builder.writeVal("w:position", position);
        builder.writeVal("w:sz", size);
        builder.writeVal(isDocx ? "w:szCs" : "w:sz-cs", sizeCs);
        builder.writeVal("w:highlight", highlight);

        builder.writeElementWithAttributes(
            "w:u",
            "w:val", underlineValue,
            "w:color", underlineColor);

        builder.writeVal("w:effect", effect);
        builder.writeBorder("w:bdr", border);
        builder.writeShd(shading);

        if (fitText != null)
        {
            builder.writeElementWithAttributes(
                "w:fitText",
                "w:val", fitText.getValue(),
                "w:id", fitText.getId());
        }

        builder.writeVal("w:vertAlign", vertAlign);
        builder.writeVal("w:rtl", rtl);
        builder.writeVal("w:cs", cs);
       
        builder.writeVal("w:em", em);

        if (!isDocx)
        {
            builder.writeElementWithAttributes(
                "w:hyphen",
                "w:val", hyphen,
                "w:rule", hyphenRule);
        }

        if ((lang != null) || (langFarEast != null) || (langBidi != null))
        {
            builder.startElement("w:lang");

            if(lang != null)
                builder.writeAttributeString("w:val", lang);
            if(langFarEast != null)
                builder.writeAttributeString(isDocx ? "w:eastAsia" : "w:fareast", langFarEast);
            if(langBidi != null)
                builder.writeAttributeString("w:bidi", langBidi);

            builder.endElement();
        }

        if (feLayout != null)
            writeFarEastLayout(builder, feLayout, isDocx);

        builder.writeVal("w:specVanish", specVanish);

        writer.writeW14Attributes(writer, attrs);

        if (!isDocx)
        {
            //TODO 3 Find data for wx.

            builder.writeVal("wx:font", wxFont);

            builder.writeElementWithAttributes(
                "wx:sym",
                "wx:font", wxSymFont,
                "wx:char", wxSymChar);
        }

        return true;
    }

    /// <summary>
    /// Returns lang value for given locale considering LanguageNotSet.
    /// </summary>
    /// <remarks>
    /// See TestJira15005() for details.
    /// </remarks>
    private static String getLang(int locale, boolean isDocx)
    {
        if (isDocx && (Language.LANGUAGE_NOT_SET == (/*Language*/int)locale))
        {
            return "x-none";
        }

        return isDocx ? LocaleConverter.localeToDocxTag(locale) : LocaleConverter.localeToWmlTag(locale);
    }

    /// <summary>
    /// Returns font name if font is not null and font is not theme font. Otherwise returns null.
    /// </summary>
    private static String getFontNameOrNull(ComplexFontName fontName)
    {
        return ((fontName != null) && !fontName.isThemeFont()) ? fontName.getName() : null;
    }

    /// <summary>
    /// Returns theme name if font is not null and font is theme font. Otherwise returns null.
    /// </summary>
    private static String getThemeNameOrNull(ComplexFontName fontName)
    {
        return ((fontName != null) && fontName.isThemeFont()) ? NrxRunEnum.themeFontToXml(fontName.getThemeFontCore()) : null;
    }

    private static void writeFarEastLayout(NrxXmlBuilder builder, FarEastLayout feLayout, boolean isDocx)
    {
        builder.writeElementWithAttributes(
            isDocx ? "w:eastAsianLayout" : "w:asianLayout", 
            "w:id", feLayout.getFarEastLayoutId(),
            "w:vert", feLayout.getVertical(),
            isDocx ? "w:vertCompress" : "w:vert-compress", feLayout.getVerticalCompress(),
            "w:combine", feLayout.getCombine(),
            isDocx ? "w:combineBrackets" : "w:combine-brackets", NrxRunEnum.combineBracketsToXml(feLayout.getCombineBrackets())
        );
    }

    /// <summary>
    /// TODO neremin this code never used.
    /// All "boolean" attributes in a run attributes collection are stored as BoolEx values.
    /// BoolEx has to be converted to bool before writing to WordML.
    /// </summary>
    /// <param name="value">The BoolEx value.</param>
    /// <param name="attrSource">The attribute source that is used to resolve full values of BoolEx attributes.</param>
    /// <param name="key">The key of the attribute.</param>
    /// <returns>True, false or null.</returns>
    private static Object boolExToBool(Object value, IRunAttrSource attrSource, int key)
    {
        return ((AttrBoolEx)value).resolveFetchInheritedRunAttrWithNull(attrSource, key);
    }
}

