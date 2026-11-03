// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/17/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import com.aspose.DrColor;
import com.aspose.Language;
import com.aspose.LocaleClassifier;
import com.aspose.StringUtil;


/// <summary>
/// Represents methods for conversion between <see cref="DmlRunProperties"/> and <see cref="RunPr"/>.
/// </summary>
class RunPrConverter
{
    /// <summary>
    /// Converts <see cref="DmlRunProperties"/>'s properties to the corresponding attributes of <see cref="RunPr"/>.
    /// </summary>
    static RunPr convertToRunPr(DmlRunProperties dmlRunPr, Theme theme)
    {
        RunPr runPr = new RunPr();

        for (int fontAttribute : G_CONVERTING_FONT_ATTRIBUTES)
        {
            Object value = getProperty(dmlRunPr, fontAttribute, theme, false);
            if (value != null)
                runPr.setAttr(fontAttribute, value);
        }

        return runPr;
    }

    /// <summary>
    /// Converts <see cref="RunPr"/>'s attributes to the corresponding properties of <see cref="DmlRunProperties"/>.
    /// </summary>
    static DmlRunProperties convertToDmlRunProperties(RunPr runPr, Theme theme)
    {
        DmlRunProperties dmlRunPr = new DmlRunProperties();

        for (int fontAttribute : G_CONVERTING_FONT_ATTRIBUTES)
        {
            Object value = runPr.get(fontAttribute);
            if (value != null)
                setProperty(dmlRunPr, fontAttribute, value, theme);
        }

        return dmlRunPr;
    }

    /// <summary>
    /// Returns a flag indicating whether <paramref name="properties"/> contain the specified font property that is
    /// set directly.
    /// </summary>
    static boolean isPropertySpecified(DmlRunProperties properties, int fontAttr)
    {
        switch (fontAttr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
                return properties.isPropertySpecified(DmlRunPropertiesIds.FONT_SIZE);
            case FontAttr.KERNING:
                return properties.isPropertySpecified(DmlRunPropertiesIds.KERNING);
            case FontAttr.SPACING:
                return properties.isPropertySpecified(DmlRunPropertiesIds.SPACING);
            case FontAttr.NAME_ASCII:
                return properties.isPropertySpecified(DmlRunPropertiesIds.LATIN_FONT);
            case FontAttr.NAME_BI:
                return properties.isPropertySpecified(DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT);
            case FontAttr.NAME_FAR_EAST:
                return properties.isPropertySpecified(DmlRunPropertiesIds.EAST_ASIAN_FONT);
            case FontAttr.NAME_OTHER:
                return properties.isPropertySpecified(DmlRunPropertiesIds.SYMBOL_FONT);
            case FontAttr.BOLD:
            case FontAttr.BOLD_BI:
                return properties.isPropertySpecified(DmlRunPropertiesIds.BOLD);
            case FontAttr.ITALIC:
            case FontAttr.ITALIC_BI:
                return properties.isPropertySpecified(DmlRunPropertiesIds.ITALICS);
            case FontAttr.UNDERLINE:
                return properties.isPropertySpecified(DmlRunPropertiesIds.UNDERLINE);
            case FontAttr.DOUBLE_STRIKE_THROUGH:
            case FontAttr.STRIKE_THROUGH:
                return properties.isPropertySpecified(DmlRunPropertiesIds.STRIKETHROUGH);
            case FontAttr.ALL_CAPS:
            case FontAttr.SMALL_CAPS:
                return properties.isPropertySpecified(DmlRunPropertiesIds.CAPITALIZATION);
            case FontAttr.VERTICAL_ALIGNMENT:
                return properties.isPropertySpecified(DmlRunPropertiesIds.BASELINE);
            case FontAttr.COLOR:
                return properties.isPropertySpecified(DmlRunPropertiesIds.FILL);
            case FontAttr.EFFECT_FILL:
                return properties.isPropertySpecified(DmlRunPropertiesIds.FILL);
            case FontAttr.HIGHLIGHT_COLOR:
                return properties.isPropertySpecified(DmlRunPropertiesIds.HIGHLIGHT_COLOR);
            case FontAttr.EFFECT_OUTLINE:
                return properties.isPropertySpecified(DmlRunPropertiesIds.OUTLINE);
            case FontAttr.EFFECT_GLOW:
                return (properties.getEffects() != null) && (properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.GLOW) != null);
            case FontAttr.EFFECT_SHADOW:
                return (properties.getEffects() != null) && (properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.OUTER_SHADOW) != null);
            case FontAttr.EFFECT_REFLECTION:
                return (properties.getEffects() != null) && (properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.REFLECTION) != null);
            case FontAttr.LOCALE_ID:
            case FontAttr.LOCALE_ID_BI:
            case FontAttr.LOCALE_ID_FAR_EAST:
                return properties.isPropertySpecified(DmlRunPropertiesIds.LANGUAGE);
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns <b>true</b> if the specified font attribute is supported when converting between
    /// <see cref="DmlRunProperties"/> and <see cref="RunPr"/>
    /// </summary>
    static boolean isPropertySupported(int fontAttr)
    {
        switch (fontAttr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
            case FontAttr.KERNING:
            case FontAttr.SPACING:
            case FontAttr.NAME_ASCII:
            case FontAttr.NAME_BI:
            case FontAttr.NAME_FAR_EAST:
            case FontAttr.NAME_OTHER:
            case FontAttr.BOLD:
            case FontAttr.BOLD_BI:
            case FontAttr.ITALIC:
            case FontAttr.ITALIC_BI:
            case FontAttr.UNDERLINE:
            case FontAttr.DOUBLE_STRIKE_THROUGH:
            case FontAttr.STRIKE_THROUGH:
            case FontAttr.ALL_CAPS:
            case FontAttr.SMALL_CAPS:
            case FontAttr.VERTICAL_ALIGNMENT:
            case FontAttr.COLOR:
            case FontAttr.EFFECT_FILL:
            case FontAttr.HIGHLIGHT_COLOR:
            case FontAttr.EFFECT_OUTLINE:
            case FontAttr.EFFECT_GLOW:
            case FontAttr.EFFECT_SHADOW:
            case FontAttr.EFFECT_REFLECTION:
            case FontAttr.LOCALE_ID:
            case FontAttr.LOCALE_ID_BI:
            case FontAttr.LOCALE_ID_FAR_EAST:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Gets value of a DML run property specified by a font attribute.
    /// </summary>
    static Object getProperty(DmlRunProperties properties, int fontAttr, Theme theme, boolean isForApi)
    {
        switch (fontAttr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
                // Multiply by 2 because half-points units must be used.
                return (int)(properties.getFontSize().getValueInPoints() * 2.0);
            case FontAttr.KERNING:
                // Multiply by 2 because half-points units must be used.
                return (int)(properties.getKerning().getValueInPoints() * 2.0);
            case FontAttr.SPACING:
                // Get spacing between characters in twips.
                return ConvertUtilCore.pointToTwip(properties.getSpacing().getValueInPoints());
            case FontAttr.NAME_ASCII:
                return createComplexFontName(properties.getLatinFont(), ThemeFontCore.MINOR_H_ANSI, theme);
            case FontAttr.NAME_BI:
                return createComplexFontName(properties.getComplexScriptFont(), ThemeFontCore.MINOR_BIDI, theme);
            case FontAttr.NAME_FAR_EAST:
                return createComplexFontName(properties.getEastAsianFont(), ThemeFontCore.MINOR_EAST_ASIA, theme);
            case FontAttr.NAME_OTHER:
            {
                // If Symbol font is omitted, use Latin font instead.
                DmlFont symbolFont = (properties.getSymbolFont() != null) ? properties.getSymbolFont() : properties.getLatinFont();
                return createComplexFontName(symbolFont, ThemeFontCore.MINOR_H_ANSI, theme);
            }
            case FontAttr.BOLD:
            case FontAttr.BOLD_BI:
                return AttrBoolEx.fromBool(properties.getBold());
            case FontAttr.ITALIC:
            case FontAttr.ITALIC_BI:
                return AttrBoolEx.fromBool(properties.getItalics());
            case FontAttr.UNDERLINE:
                return properties.getUnderline();
            case FontAttr.DOUBLE_STRIKE_THROUGH:
                return AttrBoolEx.fromBool(properties.getStrikethrough() == DmlTextStrike.DOUBLE);
            case FontAttr.STRIKE_THROUGH:
                return AttrBoolEx.fromBool(properties.getStrikethrough() == DmlTextStrike.SINGLE);
            case FontAttr.ALL_CAPS:
                return AttrBoolEx.fromBool(properties.getCapitalization() == DmlCapitalization.ALL);
            case FontAttr.SMALL_CAPS:
                return AttrBoolEx.fromBool(properties.getCapitalization() == DmlCapitalization.SMALL);
            case FontAttr.VERTICAL_ALIGNMENT:
                return getVerticalAlignment(properties);
            case FontAttr.COLOR:
                return getSolidFillColor(properties, theme);
            case FontAttr.EFFECT_FILL:
                // Outline and Fill of text are set through effects.
                return (properties.getFill() != null)
                    ? (isForApi) ? properties.getFill() : null // FOSS: DmlTextEffectsHelper is gone
                    : null;
            case FontAttr.HIGHLIGHT_COLOR:
                return (properties.getHighlightColor() != null)
                    ? properties.getHighlightColor().createDrColor(theme, null)
                    : null;
            case FontAttr.EFFECT_OUTLINE:
                return getEffectOutline(properties);
            case FontAttr.EFFECT_GLOW:
                return getEffectGlow(properties);
            case FontAttr.EFFECT_SHADOW:
                return getEffectShadow(properties);
            case FontAttr.EFFECT_REFLECTION:
                return getEffectReflection(properties);
            case FontAttr.LOCALE_ID:
            case FontAttr.LOCALE_ID_BI:
            case FontAttr.LOCALE_ID_FAR_EAST:
            {
                if (isForApi || (getFontAttrLocaleId(properties.getLanguage()) == fontAttr))
                    return (int)properties.getLanguage();
                else
                    return null;
            }
            case FontAttr.TEXT_EFFECT:
            case FontAttr.OUTLINE:
            case FontAttr.SHADOW:
            case FontAttr.ENGRAVE:
            case FontAttr.SCALING:
            case FontAttr.EMBOSS:
            case FontAttr.HIDDEN:
                // These attributes cannot be applied to chart title.
                return null;
            case FontAttr.POSITION:
            case FontAttr.BIDI:
            case FontAttr.COMPLEX_SCRIPT:
            case FontAttr.NO_PROOFING:
                // Currently there is no way to set these attributes inside DmlRunProperties.
                return null;
            default:
                return null;
        }
    }

    private static Object getVerticalAlignment(DmlRunProperties properties)
    {
        if (MathUtil.areEqual(properties.getBaseline(), 0.0))
            return RunVerticalAlignment.BASELINE;

        // WORDSNET-16973 The text with the specified baseline should be rendered as "subscript" or
        // "superscript".
        return (properties.getBaseline() < 0) ? RunVerticalAlignment.SUBSCRIPT : RunVerticalAlignment.SUPERSCRIPT;
    }

    private static Object getSolidFillColor(DmlRunProperties properties, Theme theme)
    {
        if ((properties.getFill() == null) || (properties.getFill().getDmlFillType() != DmlFillType.SOLID_FILL))
            return null;

        // If fill is solid set text color, required for proper color rendering in Math,
        // because DmlEffects in math is not yet supported.
        DmlSolidFill solidFill = (DmlSolidFill)properties.getFill();

        DrColor color = solidFill.getColor().createDrColor(theme, null);
        if ((color.getA() == 0) && (solidFill.getColor().getAlpha() == null))
            color = new DrColor(0xff, color);

        return color;
    }

    private static Object getEffectOutline(DmlRunProperties properties)
    {
        throw new UnsupportedOperationException("FOSS");
    }

    private static Object getEffectGlow(DmlRunProperties properties)
    {
        if (properties.getEffects() == null)
            return null;

        DmlShapeEffect glowEffect = properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.GLOW);
        if (glowEffect == null)
            return null;

        return null;
        // FOSS
    }

    private static Object getEffectShadow(DmlRunProperties properties)
    {
        if (properties.getEffects() == null)
            return null;

        DmlShapeEffect shadowEffect = properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.OUTER_SHADOW);
        if (shadowEffect == null)
            return null;

        return null;
        // FOSS
    }

    private static DmlShapeEffect getEffectReflection(DmlRunProperties properties)
    {
        if (properties.getEffects() == null)
            return null;

        DmlShapeEffect reflectionEffect = properties.getEffects().getByDmlShapeEffectType(DmlShapeEffectType.REFLECTION);
        return (reflectionEffect != null) ? reflectionEffect.deepClone() : null;
    }

    /// <summary>
    /// Sets value of a DML run property specified by a font attribute.
    /// </summary>
    static void setProperty(DmlRunProperties properties, int fontAttr, Object value, Theme theme)
    {
        switch (fontAttr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
                properties.setFontSize(new DmlTextPoints(((/*int*/Integer)value / 2) * 100));
                break;
            case FontAttr.KERNING:
                properties.setKerning(new DmlTextPoints(((/*int*/Integer)value / 2) * 100));
                break;
            case FontAttr.SPACING:
                properties.setSpacing(new DmlTextPoints((int)(ConvertUtilCore.twipToPoint((/*int*/Integer)value) * 100.0)));
                break;
            case FontAttr.NAME_ASCII:
            {
                properties.setLatinFont(new DmlFont());
                properties.getLatinFont().setTextTypeface(ComplexFontName.resolve(value, theme));
                break;
            }
            case FontAttr.NAME_BI:
            {
                properties.setComplexScriptFont(new DmlFont());
                properties.getComplexScriptFont().setTextTypeface(ComplexFontName.resolve(value, theme));
                break;
            }
            case FontAttr.NAME_FAR_EAST:
            {
                properties.setEastAsianFont(new DmlFont());
                properties.getEastAsianFont().setTextTypeface(ComplexFontName.resolve(value, theme));
                break;
            }
            case FontAttr.NAME_OTHER:
            {
                properties.setSymbolFont(new DmlFont());
                properties.getSymbolFont().setTextTypeface(ComplexFontName.resolve(value, theme));
                break;
            }
            case FontAttr.BOLD:
            case FontAttr.BOLD_BI:
                properties.setBold(((AttrBoolEx)value).toBool());
                break;
            case FontAttr.ITALIC:
            case FontAttr.ITALIC_BI:
                properties.setItalics(((AttrBoolEx)value).toBool());
                break;
            case FontAttr.UNDERLINE:
                properties.setUnderline((/*Underline*/Integer)value);
                break;
            case FontAttr.MATH_STYLE:
            {
                switch ((/*MathStyle*/Integer)value)
                {
                    case MathStyle.PLAIN:
                    {
                        properties.setBold(false);
                        properties.setItalics(false);
                        break;
                    }
                    case MathStyle.BOLD:
                    {
                        properties.setBold(true);
                        properties.setItalics(false);
                        break;
                    }
                    case MathStyle.ITALIC:
                    {
                        properties.setBold(false);
                        properties.setItalics(true);
                        break;
                    }
                    case MathStyle.BOLD_ITALIC:
                    {
                        properties.setBold(true);
                        properties.setItalics(true);
                        break;
                    }
                }

                break;
            }
            case FontAttr.DOUBLE_STRIKE_THROUGH:
            {
                boolean isOn = ((AttrBoolEx)value).toBool();
                if (isOn != (properties.getStrikethrough() == DmlTextStrike.DOUBLE))
                    properties.setStrikethrough(isOn ? DmlTextStrike.DOUBLE : DmlTextStrike.NO);
                break;
            }
            case FontAttr.STRIKE_THROUGH:
            {
                boolean isOn = ((AttrBoolEx)value).toBool();
                if (isOn != (properties.getStrikethrough() == DmlTextStrike.SINGLE))
                    properties.setStrikethrough(isOn ? DmlTextStrike.SINGLE : DmlTextStrike.NO);
                break;
            }
            case FontAttr.ALL_CAPS:
            {
                boolean isOn = ((AttrBoolEx)value).toBool();
                if (isOn != (properties.getCapitalization() == DmlCapitalization.ALL))
                    properties.setCapitalization(isOn ? DmlCapitalization.ALL : DmlCapitalization.NONE);
                break;
            }
            case FontAttr.SMALL_CAPS:
            {
                boolean isOn = ((AttrBoolEx)value).toBool();
                if (isOn != (properties.getCapitalization() == DmlCapitalization.SMALL))
                    properties.setCapitalization(isOn ? DmlCapitalization.SMALL : DmlCapitalization.NONE);
                break;
            }
            case FontAttr.COLOR:
                properties.setFill(new DmlSolidFill(DmlHexRgbColor.fromDrColor((DrColor)value)));
                break;
            case FontAttr.LOCALE_ID:
            case FontAttr.LOCALE_ID_BI:
            case FontAttr.LOCALE_ID_FAR_EAST:
                properties.setLanguage((/*Language*/Integer)value);
                break;
            case FontAttr.VERTICAL_ALIGNMENT:
            {
                // MS Word uses these defaults in its Font dialog.
                final double SUPERSCRIPT_OFFSET = 0.3;
                final double SUBSCRIPT_OFFSET = -0.25;
                switch ((/*RunVerticalAlignment*/Integer)value)
                {
                    case RunVerticalAlignment.SUPERSCRIPT:
                        properties.setBaseline(SUPERSCRIPT_OFFSET);
                        break;
                    case RunVerticalAlignment.SUBSCRIPT:
                        properties.setBaseline(SUBSCRIPT_OFFSET);
                        break;
                    case RunVerticalAlignment.BASELINE:
                    default:
                        properties.setBaseline(0.0);
                        break;
                }
                break;
            }
            case FontAttr.HIGHLIGHT_COLOR:
                properties.setHighlightColor(DmlColor.createFromDrColor((DrColor)value));
                break;
            case FontAttr.EFFECT_FILL:
                properties.setFill((DmlFill)value);
                break;
            case FontAttr.TEXT_EFFECT:
            case FontAttr.OUTLINE:
            case FontAttr.SHADOW:
            case FontAttr.ENGRAVE:
            case FontAttr.SCALING:
            case FontAttr.EMBOSS:
            case FontAttr.HIDDEN:
                // These attributes cannot be applied to chart title.
                break;
            case FontAttr.POSITION:
            case FontAttr.BIDI:
            case FontAttr.COMPLEX_SCRIPT:
            case FontAttr.NO_PROOFING:
                // Currently there is no way to set these attributes inside DmlRunProperties.
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Clears a DML run property specified by a font attribute.
    /// </summary>
    static void clearProperty(DmlRunProperties properties, int fontAttr)
    {
        /*DmlRunPropertiesIds*/int propertyId;

        switch (fontAttr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
                propertyId = DmlRunPropertiesIds.FONT_SIZE;
                break;
            case FontAttr.KERNING:
                propertyId = DmlRunPropertiesIds.KERNING;
                break;
            case FontAttr.SPACING:
                propertyId = DmlRunPropertiesIds.SPACING;
                break;
            case FontAttr.NAME_ASCII:
                propertyId = DmlRunPropertiesIds.LATIN_FONT;
                break;
            case FontAttr.NAME_BI:
                propertyId = DmlRunPropertiesIds.COMPLEX_SCRIPT_FONT;
                break;
            case FontAttr.NAME_FAR_EAST:
                propertyId = DmlRunPropertiesIds.EAST_ASIAN_FONT;
                break;
            case FontAttr.NAME_OTHER:
                propertyId = DmlRunPropertiesIds.SYMBOL_FONT;
                break;
            case FontAttr.BOLD:
            case FontAttr.BOLD_BI:
                propertyId = DmlRunPropertiesIds.BOLD;
                break;
            case FontAttr.ITALIC:
            case FontAttr.ITALIC_BI:
                propertyId = DmlRunPropertiesIds.ITALICS;
                break;
            case FontAttr.UNDERLINE:
                propertyId = DmlRunPropertiesIds.UNDERLINE;
                break;
            case FontAttr.DOUBLE_STRIKE_THROUGH:
            {
                if (properties.getStrikethrough() != DmlTextStrike.DOUBLE)
                    return;

                propertyId = DmlRunPropertiesIds.STRIKETHROUGH;
                break;
            }
            case FontAttr.STRIKE_THROUGH:
            {
                if (properties.getStrikethrough() != DmlTextStrike.SINGLE)
                    return;

                propertyId = DmlRunPropertiesIds.STRIKETHROUGH;
                break;
            }
            case FontAttr.ALL_CAPS:
            {
                if (properties.getCapitalization() != DmlCapitalization.ALL)
                    return;

                propertyId = DmlRunPropertiesIds.CAPITALIZATION;
                break;
            }
            case FontAttr.SMALL_CAPS:
            {
                if (properties.getCapitalization() != DmlCapitalization.SMALL)
                    return;

                propertyId = DmlRunPropertiesIds.CAPITALIZATION;
                break;
            }
            case FontAttr.COLOR:
                propertyId = DmlRunPropertiesIds.FILL;
                break;
            case FontAttr.HIGHLIGHT_COLOR:
                propertyId = DmlRunPropertiesIds.HIGHLIGHT_COLOR;
                break;
            case FontAttr.LOCALE_ID:
            case FontAttr.LOCALE_ID_BI:
            case FontAttr.LOCALE_ID_FAR_EAST:
                propertyId = DmlRunPropertiesIds.LANGUAGE;
                break;
            case FontAttr.TEXT_EFFECT:
            case FontAttr.OUTLINE:
            case FontAttr.SHADOW:
            case FontAttr.ENGRAVE:
            case FontAttr.SCALING:
            case FontAttr.EMBOSS:
            case FontAttr.HIDDEN:
                // These attributes cannot be applied to chart title.
                return;
            case FontAttr.POSITION:
            case FontAttr.BIDI:
            case FontAttr.COMPLEX_SCRIPT:
            case FontAttr.NO_PROOFING:
                // Currently there is no way to set these attributes inside DmlRunProperties.
                return;
            default:
                return;
        }

        properties.remove(propertyId);
    }

    /// <summary>
    /// Returns font attribute id used for storing the specified language.
    /// </summary>
    private static int getFontAttrLocaleId(/*Language*/int language)
    {
        int locale = (int)language;

        if (LocaleClassifier.isArabic(locale))
            return FontAttr.LOCALE_ID_BI;
        if (LocaleClassifier.isChineseOrJapanese(locale))
            return FontAttr.LOCALE_ID_FAR_EAST;

        return FontAttr.LOCALE_ID;
    }

    /// <summary>
    /// Creates <see cref="ComplexFontName"/> for specified <see cref="DmlFont"/>.
    /// If font is null or typeface of the font is not set, default theme font is returned.
    /// </summary>
    private static ComplexFontName createComplexFontName(DmlFont font, /*ThemeFontCore*/int defaultFont, Theme theme)
    {
        String typeface = (font != null) ? DmlTextFontRepository.getTypeFace(font.getTextTypeface(), theme) : "";

        return StringUtil.hasChars(typeface)
                   ? ComplexFontName.fromName(typeface)
                   : ComplexFontName.fromTheme(defaultFont);
    }

    private static /*final*/ int[] G_CONVERTING_FONT_ATTRIBUTES = new int[]
    {
        FontAttr.SIZE, FontAttr.KERNING, FontAttr.SPACING, FontAttr.NAME_ASCII, FontAttr.NAME_BI,
        FontAttr.NAME_FAR_EAST, FontAttr.NAME_OTHER, FontAttr.BOLD, FontAttr.ITALIC, FontAttr.UNDERLINE,
        FontAttr.DOUBLE_STRIKE_THROUGH, FontAttr.STRIKE_THROUGH, FontAttr.ALL_CAPS, FontAttr.SMALL_CAPS,
        FontAttr.VERTICAL_ALIGNMENT, FontAttr.COLOR, FontAttr.EFFECT_FILL, FontAttr.HIGHLIGHT_COLOR,
        FontAttr.EFFECT_OUTLINE, FontAttr.EFFECT_GLOW, FontAttr.EFFECT_SHADOW, FontAttr.EFFECT_REFLECTION,
        FontAttr.LOCALE_ID_BI, FontAttr.LOCALE_ID_FAR_EAST, FontAttr.LOCALE_ID, FontAttr.MATH_STYLE
    };
}

