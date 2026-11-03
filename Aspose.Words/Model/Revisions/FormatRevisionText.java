// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.FontUtil;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.DrColor;
import com.aspose.DictionaryExtension;
import com.aspose.MathUtil;
import com.aspose.ms.System.msMath;
import com.aspose.ConvertUtilCore;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Formats human readable text for <see cref="FormatRevision" /> object.
/// </summary>
/// <remarks>
/// AM. Output is not perfect for some complex cases yet (lists, tabs, etc) but all customer documents is OK and
/// I think we can postpone complex cases for a while.
/// </remarks>
/*static*/ class FormatRevisionText
{
	/* Simulation of static class by using private constructor */
	private FormatRevisionText()
	{}

    static String getText(FormatRevision revision, DocumentBase doc)
    {
        StringBuilder sb = new StringBuilder();
        WordAttrCollection pr = revision.getRevPr();

        if (pr.contains(ParaAttr.ISTD))
        {
            Style style = doc.getStyles().getByIstd((/*int*/Integer)pr.get(ParaAttr.ISTD), false);
            concat(sb, style.getName());
        }

        // Font.
        concat(sb, formatFont(pr, doc));

        // Paragraph formatting.
        concat(sb, formatParagraph(pr, doc));

        // Section formatting.
        SectPr revSectPr = ms.as(pr, SectPr.class);
        if(revSectPr != null)
            concat(sb, formatSection(revSectPr));

        return sb.toString();
    }

    /// <summary>
    /// Formats description for font format revision.
    /// </summary>
    private static String formatFont(AttrCollection pr, DocumentBase doc)
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbTemp = new StringBuilder();

        if (pr.contains(FontAttr.NAME_ASCII))
        {
            ComplexFontName officeFont = (ComplexFontName) pr.get(FontAttr.NAME_ASCII);

            Theme theme = doc.getThemeInternal();
            String fontName = officeFont.resolve(theme);
            if (officeFont.isThemeFont())
                fontName = msString.format((officeFont.getThemeFontCore() & ThemeFontCore.GROUP_MASK) == ThemeFontCore.MAJOR ? "+Headings ({0})" : "+Body ({0})", fontName);

            // See "Test24237GroupText" for details.
            String currentOtherFont = getOtherFontName(pr, theme);
            String defaultOtherFontName = getOtherFontName(doc.getStyles().getDefaultRunPr(), theme);

            if (pr.contains(FontAttr.NAME_BI) ||
                (msString.equals(currentOtherFont, defaultOtherFontName)) ||
                (msString.equals(FontUtil.getFontSubstitutesCache().get(fontName), defaultOtherFontName)))
            {
                fontName = msString.format("(Default) {0}", fontName);
            }

            concat(sbTemp, fontName);
        }

        if (pr.contains(FontAttr.SIZE))
            concat(sbTemp, msString.format("{0} pt", ((/*int*/Integer)pr.get(FontAttr.SIZE)) / 2));

        concatBoolEx(sbTemp, pr, FontAttr.BOLD, "Bold");
        concatBoolEx(sbTemp, pr, FontAttr.ITALIC, "Italic");

        if (sbTemp.length() > 0)
            concat(sb, msString.format("Font: {0}", sbTemp));

        if (pr.contains(FontAttr.UNDERLINE))
            concat(sb, formatUnderline((/*Underline*/Integer)pr.get(FontAttr.UNDERLINE)));

        if (pr.contains(FontAttr.UNDERLINE_COLOR))
            concat(sb, msString.format("Underline color: {0}", formatColor(pr.get(FontAttr.UNDERLINE_COLOR), pr.get(FontAttr.UNDERLINE_THEME_COLOR))));

        if (pr.contains(FontAttr.COLOR))
            concat(sb, msString.format("Font color: {0}", formatColor(pr.get(FontAttr.COLOR), pr.get(FontAttr.THEME_COLOR))));

        // Complex script.
        sbTemp.setLength(0);
        if (pr.contains(FontAttr.NAME_BI))
        {
            ComplexFontName officeFont = (ComplexFontName)pr.get(FontAttr.NAME_BI);
            String fontName = officeFont.resolve(doc.getThemeInternal());
            if (officeFont.isThemeFont())
                fontName = msString.format((officeFont.getThemeFontCore() & ThemeFontCore.GROUP_MASK) == ThemeFontCore.MAJOR ? "+Headings ({0})" : "+Body ({0})", fontName);

            concat(sbTemp, fontName);
        }

        if (pr.contains(FontAttr.SIZE_BI))
            concat(sbTemp, msString.format("{0} pt", ((/*int*/Integer)pr.get(FontAttr.SIZE_BI)) / 2));

        concatBoolEx(sbTemp, pr, FontAttr.BOLD_BI, "Bold");
        concatBoolEx(sbTemp, pr, FontAttr.ITALIC_BI, "Italic");

        if (sbTemp.length() > 0)
            concat(sb, msString.format("Complex Script Font: {0}", sbTemp));

        concatBoolEx(sb, pr, FontAttr.STRIKE_THROUGH, "Strikethrough");
        concatBoolEx(sb, pr, FontAttr.DOUBLE_STRIKE_THROUGH, "Double strikethrough");

        if (pr.contains(FontAttr.VERTICAL_ALIGNMENT))
            concat(sb, formatVerticalAlignment((/*RunVerticalAlignment*/Integer)pr.get(FontAttr.VERTICAL_ALIGNMENT)));

        concatBoolEx(sb, pr, FontAttr.SMALL_CAPS, "Small caps");

        if (pr.contains(FontAttr.SCALING))
            concat(sb, msString.format("Character scale: {0}%", (/*int*/Integer)pr.get(FontAttr.SCALING)));

        if (pr.contains(FontAttr.SPACING))
            concat(sb, msString.format("Expanded by {0} pt", ((/*int*/Integer)pr.get(FontAttr.SPACING)) / 20));

        if (pr.contains(FontAttr.POSITION))
            concat(sb, msString.format("Raised by {0} pt", ((/*int*/Integer)pr.get(FontAttr.POSITION)) / 2));

        if (pr.contains(FontAttr.KERNING))
            concat(sb, msString.format("Kern at {0} pt", ((/*int*/Integer)pr.get(FontAttr.KERNING)) / 2));

        if (pr.contains(FontAttr.BORDER))
            concat(sb, msString.format("Border: {0}", formatBorder("", (Border)pr.get(FontAttr.BORDER))));

        concatPropertyName(sb, pr, FontAttr.HIGHLIGHT_COLOR, "Highlight");

        // Text effects.
        StringBuilder sbEffects = new StringBuilder();
        concatPropertyName(sbEffects, pr, FontAttr.EFFECT_OUTLINE, "Outline");
        concatPropertyName(sbEffects, pr, FontAttr.EFFECT_GLOW, "Glow");
        concatPropertyName(sbEffects, pr, FontAttr.EFFECT_REFLECTION, "Reflection");
        concatPropertyName(sbEffects, pr, FontAttr.EFFECT_FILL, "Fill");
        concatPropertyName(sbEffects, pr, FontAttr.EFFECT_PROPS_3_D, "Bevel");
        if (sbEffects.length() > 0)
            concat(sb, msString.format("Text {0}", sbEffects));

        // Locale
        if (pr.contains(FontAttr.LOCALE_ID))
        {
            int localeId = (/*int*/Integer)pr.get(FontAttr.LOCALE_ID);

            msCultureInfo cultureInfo = getCultureInfoSafe(localeId);

            if(cultureInfo != null)
                concat(sb, cultureInfo.getDisplayName());
        }

        return sb.toString();
    }

    private static msCultureInfo getCultureInfoSafe(int localeId)
    {
        msCultureInfo cultureInfo = null;
        try
        {
            cultureInfo = new msCultureInfo(localeId, false);
        }
        catch(Exception e)
        {
        }

        return cultureInfo;
    }

    private static String getOtherFontName(AttrCollection attrs, Theme theme)
    {
        if (!attrs.contains(FontAttr.NAME_OTHER))
            return null;

        ComplexFontName fontNameComplex = (ComplexFontName)attrs.get(FontAttr.NAME_OTHER);
        String fontName = fontNameComplex.isThemeFont() ? fontNameComplex.resolve(theme) : fontNameComplex.getName();

        return fontName;
    }

    /// <summary>
    /// Formats description for paragraph format revision.
    /// </summary>
    private static String formatParagraph(AttrCollection pr, DocumentBase doc)
    {
        // FOSS: LayoutOptions/RevisionOptions removed; use the documented default (Centimeters)
        // that master's doc.FetchDocument().LayoutOptions.RevisionOptions.MeasurementUnit defaulted to.
        /*MeasurementUnits*/int unit = MeasurementUnits.CENTIMETERS;
        StringBuilder sb = new StringBuilder();

        // Paragraph formatting
        if (pr.contains(ParaAttr.ALIGNMENT))
            concat(sb, formatAlignment((/*ParagraphAlignment*/Integer)pr.get(ParaAttr.ALIGNMENT)));

        String indents = formatIndent(pr, unit);
        if (StringUtil.hasChars(indents))
            concat(sb, msString.format("Indent: {0}", indents));

        if (pr.contains(ParaAttr.LIST_ID))
            concat(sb, formatNumbering(pr, doc));

        StringBuilder sbTemp = new StringBuilder();
        if (pr.contains(ParaAttr.TAB_STOPS))
        {
            TabStopCollection tabStops = (TabStopCollection)pr.get(ParaAttr.TAB_STOPS);

            for (int i = 0; i < tabStops.getCount(); i++)
            {
                TabStop tab = tabStops.get(i);
                concat(sbTemp, formatTabStop(tab, unit));
            }
            if (sbTemp.length() > 0)
                concat(sb, msString.format("Tab stops: {0}", sbTemp));
        }

        sbTemp.setLength(0);
        if (pr.contains(ParaAttr.SPACE_BEFORE))
            concat(sbTemp, msString.format("Before: {0} pt", (/*int*/Integer)pr.get(ParaAttr.SPACE_BEFORE) / 20));

        if (pr.contains(ParaAttr.SPACE_AFTER))
            concat(sbTemp, msString.format("After: {0} pt", (/*int*/Integer)pr.get(ParaAttr.SPACE_AFTER) / 20));

        if (sbTemp.length() > 0)
            concat(sb, msString.format("Space {0}", sbTemp));

        if (pr.contains(ParaAttr.LINE_SPACING))
            concat(sb, msString.format("Line spacing: {0}", formatLineSpacing((LineSpacing)pr.get(ParaAttr.LINE_SPACING))));

        concatBool(sb, pr, ParaAttr.AUTO_ADJUST_RIGHT_INDENT, "Automatically adjust right indent when grid is defined", "Don't automatically adjust right indent when grid is defined");
        concatBool(sb, pr, ParaAttr.NO_SPACE_BETWEEN_SAME_STYLE, "Don't add space between paragraphs of the same style", "Add space between paragraphs of the same style");
        concatBool(sb, pr, ParaAttr.WIDOW_CONTROL, "Widow/orphan control", "No widow/orphan control");
        concatBool(sb, pr, ParaAttr.PAGE_BREAK_BEFORE, "Page break before", "No page break before");
        concatBool(sb, pr, ParaAttr.KEEP_WITH_NEXT, "Keep with next", "Don't keep with next");
        concatBool(sb, pr, ParaAttr.KEEP_TOGETHER, "Keep lines together", "Don't keep lines together");
        concatBool(sb, pr, ParaAttr.SUPPRESS_LINE_NUMBERS, "Suppress line numbers", "Don't suppress line numbers");
        concatBool(sb, pr, ParaAttr.SUPPRESS_AUTO_HYPHENS, "Don't hyphenate", "Hyphenate");
        concatBool(sb, pr, ParaAttr.FAR_EAST_LINE_BREAK_CONTROL, "Use Asian rules to control first and last character", "Don't use Asian rules to control first and last character");
        concatBool(sb, pr, ParaAttr.WORD_WRAP, "Don't allow text to wrap in the middle of a word", "Allow text to wrap in the middle of a word");
        concatBool(sb, pr, ParaAttr.HANGING_PUNCTUATION, "Allow hanging punctuation", "Don't allow hanging punctuation");
        concatBool(sb, pr, ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA, "Adjust space between Latin and Asian text", "Don't adjust space between Latin and Asian text");
        concatBool(sb, pr, ParaAttr.ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT, "Adjust space between Asian text and numbers", "Don't adjust space between Asian text and numbers");
        concatBool(sb, pr, ParaAttr.SNAP_TO_GRID, "Snap to grid", "Don't snap to grid");
        concatBool(sb, pr, ParaAttr.MIRROR_INDENTS, "Don't swap indents on facing pages", "Not Don't swap indents on facing pages");
        concatBool(sb, pr, ParaAttr.TOP_LINE_PUNCTUATION, "Compress initial punctuation", "Don't compress initial punctuation");

        sbTemp.setLength(0);
        if (pr.contains(ParaAttr.BORDER_LEFT) ||
            pr.contains(ParaAttr.BORDER_RIGHT) ||
            pr.contains(ParaAttr.BORDER_TOP) ||
            pr.contains(ParaAttr.BORDER_BAR) ||
            pr.contains(ParaAttr.BORDER_BETWEEN) ||
            pr.contains(ParaAttr.BORDER_BOTTOM))
        {
            concat(sbTemp, formatParaBorder(pr));
        }
        if (sbTemp.length() > 0)
            concat(sb, msString.format("Border: {0}", sbTemp));

        if (pr.contains(ParaAttr.SHADING))
            concat(sb, formatShading((Shading)pr.get(ParaAttr.SHADING)));


        return sb.toString();
    }

    /// <summary>
    /// Formats description for section format revision.
    /// </summary>
    private static String formatSection(SectPr revPr)
    {
        StringBuilder sb = new StringBuilder();

        if (revPr.contains(SectAttr.LEFT_MARGIN))
            concat(sb, msString.format("Left: {0} cm", twipToCm(revPr.getLeftMargin())));

        if (revPr.contains(SectAttr.RIGHT_MARGIN))
            concat(sb, msString.format("Right: {0} cm", twipToCm(revPr.getRightMargin())));

        if (revPr.contains(SectAttr.TOP_MARGIN))
            concat(sb, msString.format("Top: {0} cm", twipToCm(revPr.getTopMargin())));

        if (revPr.contains(SectAttr.BOTTOM_MARGIN))
            concat(sb, msString.format("Bottom: {0} cm", twipToCm(revPr.getBottomMargin())));

        if (revPr.contains(SectAttr.PAGE_WIDTH))
            concat(sb, msString.format("Width: {0} cm", twipToCm(revPr.getPageWidth())));

        if (revPr.contains(SectAttr.PAGE_HEIGHT))
            concat(sb, msString.format("Height: {0} cm", twipToCm(revPr.getPageHeight())));

        if (revPr.contains(SectAttr.COLUMNS_COUNT))
            concat(sb, msString.format("Number of columns: {0}", revPr.getColumnsCount()));

        if (revPr.contains(SectAttr.SECTION_START))
            concat(sb, msString.format("Section start: {0}", formatSectionStart(revPr.getSectionStart())));

        return sb.toString();
    }

    private static String formatSectionStart(/*SectionStart*/int sectionStart)
    {
        switch (sectionStart)
        {
            case SectionStart.NEW_PAGE: return "New page";
            case SectionStart.CONTINUOUS: return "Continuous";
            case SectionStart.EVEN_PAGE: return "Even page";
            case SectionStart.ODD_PAGE: return "Odd page";
            default: return "";
        }
    }

    private static String formatNumbering(AttrCollection pr, DocumentBase doc)
    {
        int listId = (/*int*/Integer)pr.get(ParaAttr.LIST_ID);
        int listLevel = (/*int*/Integer)pr.fetchAttr(ParaAttr.LIST_LEVEL);

        if (listId == 0)
        {
            return "No bullets or numbering";
        }

        List list = doc.getLists().getListByListId(listId);
        ListLevel level = list.getListLevels().get(listLevel);

        // FOSS: LayoutOptions/RevisionOptions removed; use the documented default (Centimeters).
        /*MeasurementUnits*/int unit = MeasurementUnits.CENTIMETERS;

        int alignAt = (/*int*/Integer)level.getParaPr().fetchAttr(ParaAttr.LEFT_INDENT) +
                        (/*int*/Integer)level.getParaPr().fetchAttr(ParaAttr.FIRST_LINE_INDENT);

        StringBuilder sb = new StringBuilder();
        msStringBuilder.append(sb, msString.format("Numbered + Level: {0} + ", listLevel + 1));
        msStringBuilder.append(sb, msString.format("Numbering Style: {0} + ", formatNumberStyle(level.getNumberStyle())));
        msStringBuilder.append(sb, msString.format("Aligned at: {0} + ", formatMeasurementValue(unit, alignAt)));
        if ((level.getParaPr().getTabStops()!=null) && (level.getParaPr().getTabStops().getCount() == 1))
            msStringBuilder.append(sb, msString.format("Tab after: {0} cm + ", twipToCm(level.getParaPr().getTabStops().get(0).getPositionTwips())));
        int leftIndent = (/*int*/Integer)level.getParaPr().fetchAttr(ParaAttr.LEFT_INDENT);
        msStringBuilder.append(sb, msString.format("Indent at: {0}", formatMeasurementValue(unit, leftIndent)));

        return sb.toString();
    }

    private static String formatNumberStyle(/*NumberStyle*/int numberStyle)
    {
        switch (numberStyle)
        {
            case NumberStyle.UPPERCASE_ROMAN: return "I, II, III, ...";
            case NumberStyle.ARABIC: return "1, 2, 3, ...";
            case NumberStyle.BULLET: return "Bullet";

            default:
                return "";
        }
    }

    private static void concatBoolEx(StringBuilder sb, AttrCollection pr, int key, String name)
    {
        Object val = pr.get(key);
        if (val != null)
            concat(sb, formatBoolEx((AttrBoolEx)val, name));
    }

    private static void concatPropertyName(StringBuilder sb, AttrCollection pr, int key, String name)
    {
        if (pr.contains(key))
            concat(sb, name);
    }

    private static void concatBool(StringBuilder sb, AttrCollection pr, int key, String name, String notName)
    {
        Object val = pr.get(key);
        if (val != null)
            concat(sb, formatBool((/*boolean*/Boolean)val, name, notName));
    }

    private static String formatIndent(AttrCollection pr, /*MeasurementUnits*/int unit)
    {
        StringBuilder sb = new StringBuilder();
        if (pr.contains(ParaAttr.LEFT_INDENT))
        {
            int left = (/*int*/Integer)pr.get(ParaAttr.LEFT_INDENT) + (/*int*/Integer)pr.fetchAttr(ParaAttr.FIRST_LINE_INDENT);
            concat(sb, msString.format("Left: {0}", formatMeasurementValue(unit, left)));
        }
        if (pr.contains(ParaAttr.RIGHT_INDENT))
            concat(sb, msString.format("Right: {0}", formatMeasurementValue(unit, (/*int*/Integer)pr.get(ParaAttr.RIGHT_INDENT))));
        if (pr.contains(ParaAttr.FIRST_LINE_INDENT))
        {
            int firstLine = (/*int*/Integer)pr.get(ParaAttr.FIRST_LINE_INDENT);
            if (firstLine != 0)
                concat(sb, msString.format("{0}: {1} cm", firstLine > 0 ? "First Line" : "Hanging", twipToCm(Math.abs(firstLine))));
        }

        return sb.toString();
    }

    private static String formatTabAlignment(/*TabAlignment*/int alignment)
    {
        switch (alignment)
        {
            case TabAlignment.RIGHT: return "Right";
            case TabAlignment.LIST: return "List";

            default:
                return "Left";
        }
    }

    private static String formatTabStop(TabStop tab, /*MeasurementUnits*/int unit)
    {
        return tab.isClear()
            ? msString.format("Not at {0}", formatMeasurementValue(unit, tab.getPositionTwips()))
            : msString.format("{0}, {1}", formatMeasurementValue(unit, tab.getPositionTwips()), formatTabAlignment(tab.getAlignment()));
    }

    private static String formatShading(Shading shading)
    {
        StringBuilder sb = new StringBuilder();

        if (shading.getTexture() == TextureIndex.TEXTURE_SOLID)
        {
            concat(sb, msString.format("Pattern: {0} ({1})",
                formatTextureIndex(shading.getTexture()),
                formatColor(shading.getForegroundPatternColorInternal(), shading.getThemeColor())
                ));

        }
        else if (shading.getTexture() == TextureIndex.TEXTURE_NONE)
        {
            concat(sb, msString.format("Pattern: {0} ({1})",
                formatTextureIndex(shading.getTexture()),
                formatColor(shading.getBackgroundPatternColorInternal(), shading.getThemeFill())
                ));

        }
        else
        {
            concat(sb, msString.format("Pattern: {0} ({1} Foreground, {2} Background)",
                formatTextureIndex(shading.getTexture()),
                formatColor(shading.getForegroundPatternColorInternal(), shading.getThemeColor()),
                formatColor(shading.getBackgroundPatternColorInternal(), shading.getThemeFill())
                ));
        }

        return sb.toString();
    }

    private static String formatAlignment(/*ParagraphAlignment*/int alignment)
    {
        switch (alignment)
        {
            case ParagraphAlignment.LEFT: return "Left";
            case ParagraphAlignment.CENTER: return "Centered";
            case ParagraphAlignment.RIGHT: return "Right";

            default:
                return "?";
        }
    }

    private static String formatLineSpacing(LineSpacing lineSpacing)
    {
        switch (lineSpacing.getRule())
        {
            case LineSpacingRule.MULTIPLE:
                if (lineSpacing.getValue() == 240)
                    return "Single";

                if (lineSpacing.getValue() == 480)
                    return "Double";

                return msString.format("{0} lines", lineSpacing.getValue() / 240.0);

            default:
                return msString.format("{0} {1} pt", (lineSpacing.getRule() == LineSpacingRule.EXACTLY ? "Exactly" : "At least"), lineSpacing.getValue() / 20.0);
        }
    }

    private static String formatVerticalAlignment(/*RunVerticalAlignment*/int vertAlignment)
    {
        switch (vertAlignment)
        {
            case RunVerticalAlignment.SUBSCRIPT: return "Subscript";
            case RunVerticalAlignment.SUPERSCRIPT: return "Superscript";
            case RunVerticalAlignment.BASELINE: return "Baseline";

            default:
                return "";
        }
    }

    private static String formatBoolEx(AttrBoolEx val, String name)
    {
        return val.toBool() ? name : msString.format("Not {0}", name);
    }

    private static String formatBool(boolean val, String name, String notName)
    {
        return val ? name : notName;
    }

    private static String formatTextureIndex(/*TextureIndex*/int textureIndex)
    {
        switch (textureIndex)
        {
            case TextureIndex.TEXTURE_SOLID: return "Solid (100%)";
            case TextureIndex.TEXTURE_NONE: return "Clear";
            case TextureIndex.TEXTURE_20_PERCENT: return "20%";
            case TextureIndex.TEXTURE_62_PT_5_PERCENT: return "62.5%";
            case TextureIndex.TEXTURE_12_PT_5_PERCENT: return "12.5%";
            case TextureIndex.TEXTURE_DARK_CROSS: return "Dk Grid";
            case TextureIndex.TEXTURE_CROSS: return "Lt Grid";
            case TextureIndex.TEXTURE_DARK_HORIZONTAL: return "Dk Horizontal";
            case TextureIndex.TEXTURE_DIAGONAL_CROSS: return "Lt Trellis";

            default:
                return "";
        }
    }

    private static String formatLineStyle(/*LineStyle*/int lineStyle)
    {
        switch (lineStyle)
        {
            case LineStyle.SINGLE: return "Single solid line";
            case LineStyle.DASH_LARGE_GAP: return "Dashed (large gap)";
            case LineStyle.DOT: return "Dotted";
            case LineStyle.DASH_SMALL_GAP: return "Dashed (small gap)";
            case LineStyle.TRIPLE:return "Triple solid lines";
            case LineStyle.DASH_DOT_STROKER: return "Dash dot (stroked)";
            case LineStyle.DOT_DOT_DASH: return "Dash dot dot";
            case LineStyle.DOUBLE_WAVE: return "Double wavy";

            default:
                return "";
        }
    }

    private static String formatParaBorder(AttrCollection pr)
    {
        StringBuilder sb = new StringBuilder();

        Border between = (Border)pr.get(ParaAttr.BORDER_BETWEEN);
        Border bar = (Border)pr.get(ParaAttr.BORDER_BAR);

        concat(sb, formatBorder("Between", between));
        concat(sb, formatBorder("Bar", bar));

        Border left = (Border)pr.get(ParaAttr.BORDER_LEFT);
        Border right = (Border)pr.get(ParaAttr.BORDER_RIGHT);
        Border top = (Border)pr.get(ParaAttr.BORDER_TOP);
        Border bottom = (Border)pr.get(ParaAttr.BORDER_BOTTOM);

        if ((left != null) && areEqual(left, right) && areEqual(left, top) && areEqual(left, bottom))
        {
            concat(sb, formatBorder("Box", left));
        }
        else
        {
            concat(sb, formatBorder("Top", top));
            concat(sb, formatBorder("Bottom", bottom));
            concat(sb, formatBorder("Left", left));
            concat(sb, formatBorder("Right", right));
        }

        return sb.toString();
    }

    private static String formatBorder(String name, Border border)
    {
        if (border == null)
            return "";

        return msString.format("{0}: ({1}{2}, {3}, {4} pt Line width)",
            name,
            border.getShadow() ? "Shadowed " : "",
            formatLineStyle(border.getLineStyle()),
            formatColor(border.getColorInternal(), border.getThemeColorInternal()),
            border.getLineWidth());
    }

    private static String formatColor(Object color, Object themeColor)
    {
        if (themeColor == null)
        {
            DrColor drColor = (DrColor) color;
            if (drColor.isEmpty())
                return "Auto";

            String knownColorText = DictionaryExtension.getValueOrNull(G_KNOWN_COLORS, (DrColor)color);

            return (StringUtil.hasChars(knownColorText))
                ? knownColorText
                : msString.format("Custom Color(RGB({0},{1},{2}))", drColor.getR(), drColor.getG(), drColor.getB());
        }

        // Color is theme color.
        switch (gStringSwitchMap.of((String) themeColor))
        {
            case /*"accent1"*/0: return "Accent 1";
            case /*"accent2"*/1: return "Accent 2";
            case /*"accent3"*/2: return "Accent 3";
            case /*"accent4"*/3: return "Accent 4";
            case /*"accent5"*/4: return "Accent 5";
            case /*"accent6"*/5: return "Accent 6";
            case /*"text1"*/6: return "Text 1";
            case /*"text2"*/7: return "Text 2";
            case /*"background1"*/8: return "Background 1";
            case /*"background2"*/9: return "Background 2";

            default:
                return "";
        }
    }

    private static String formatUnderline(/*Underline*/int underline)
    {
        switch (underline)
        {
            case Underline.SINGLE: return "Underline";
            case Underline.DOUBLE: return "Double underline";
            case Underline.DASH: return "Dashed underline";
            case Underline.THICK: return "Thick underline";
            case Underline.DOTTED: return "Dotted underline";
            case Underline.DOT_DASH: return "Dot-dash underline";
            case Underline.DOT_DOT_DASH: return "Dot-dot-dash underline";
            case Underline.WAVY: return "Wave underline";
            case Underline.WAVY_DOUBLE: return "Wave double underline";

            default:
                return "";
        }
    }

    /// <summary>
    /// Concatenates text to list using comma as separator.
    /// </summary>
    private static void concat(StringBuilder sb, String text)
    {
        if (!StringUtil.hasChars(text))
            return;

        if (sb.length() > 0)
            msStringBuilder.append(sb, ", ");

        msStringBuilder.append(sb, text);
    }

    /// <summary>
    /// Custom equality method for borders. Ignores distance from text values.
    /// </summary>
    private static boolean areEqual(Border border1, Border border2)
    {
        if (border1 == null || border2 == null)
            return false;

        return
            (border1.getLineStyle() == border2.getLineStyle()) &&
            MathUtil.areEqual(border1.getLineWidth(), border2.getLineWidth()) &&
            (border1.getColorInternal().equals(border2.getColorInternal())) &&
            (border1.getFrame() == border2.getFrame()) &&
            (border1.getShadow() == border2.getShadow()) &&
            (msString.equals(border1.getThemeColorInternal(), border2.getThemeColorInternal())) &&
            (msString.equals(border1.getThemeShade(), border2.getThemeShade())) &&
            (msString.equals(border1.getThemeTint(), border2.getThemeTint()));
    }

    private static double twipToCm(int value)
    {
        return msMath.round(ConvertUtilCore.twipToMm(value) / 10.0, 2);
    }

    private static double twipToMeasurementUnits(/*MeasurementUnits*/int measurementUnit, int value)
    {
        switch (measurementUnit)
        {
            case MeasurementUnits.CENTIMETERS:
                return ConvertUtilCore.twipToCm(value);
            case MeasurementUnits.INCHES:
                return ConvertUtilCore.twipToInch(value);
            case MeasurementUnits.MILLIMETERS:
                return ConvertUtilCore.twipToMm(value);
            case MeasurementUnits.PICAS:
                return ConvertUtilCore.twipToPicas(value);
            case MeasurementUnits.POINTS:
                return ConvertUtilCore.twipToPoint(value);

            default:
                Debug.assertTrue(false);
                return Double.NaN;
        }
    }

    private static String formatMeasurementValue(/*MeasurementUnits*/int measurementUnit, int value)
    {
        double convertedValue = twipToMeasurementUnits(measurementUnit, value);

        switch (measurementUnit)
        {
            case MeasurementUnits.CENTIMETERS:
                return msString.format("{0} cm", msMath.round(convertedValue, 2));
            case MeasurementUnits.INCHES:
                return msString.format("{0}\"", msMath.round(convertedValue, 2));
            case MeasurementUnits.MILLIMETERS:
                return msString.format("{0} mm", msMath.round(convertedValue, 1));
            case MeasurementUnits.PICAS:
                return msString.format("{0} pi", msMath.round(convertedValue, 1));
            case MeasurementUnits.POINTS:
                return msString.format("{0} pt", msMath.round(convertedValue, 0));

            default:
                Debug.assertTrue(false);
                return "";
        }
    }

    private static /*final*/ HashMap<DrColor, String> G_KNOWN_COLORS = new HashMap<DrColor, String>();

    static/* FormatRevisionText()*/
    {
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0xC0, 0x00, 0x00), "Dark Red");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0xFF, 0xFF, 0x00), "Yellow");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0xFF, 0x00, 0x00), "Red");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x00, 0xB0, 0x50), "Green");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x92, 0xD0, 0x50), "Light Green");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x70, 0x30, 0xA0), "Purple");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x00, 0x20, 0x60), "Dark Blue");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x00, 0x70, 0xC0), "Blue");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0xff, 0xc0, 0x00), "Orange");
        msDictionary.add(G_KNOWN_COLORS, DrColor.fromArgb(0x00, 0x00, 0x00), "Black");
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"accent1",
		"accent2",
		"accent3",
		"accent4",
		"accent5",
		"accent6",
		"text1",
		"text2",
		"background1",
		"background2"
	);

}

