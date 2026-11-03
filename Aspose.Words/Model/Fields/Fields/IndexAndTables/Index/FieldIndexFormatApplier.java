// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Map;
import com.aspose.ms.System.msArray;
import java.util.HashMap;


/*static*/ class FieldIndexFormatApplier
{
	/* Simulation of static class by using private constructor */
	private FieldIndexFormatApplier()
	{}

    private static FieldIndexFormatDefinition defineClassicFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 18);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .bold()
            .size(26)
            .name(ThemeFontCore.MINOR_H_ANSI);

        headingStyle.getParaPr().setAlignment(ParagraphAlignment.CENTER);
        headingStyle.getParaPr().setSpaceBefore(240);
        headingStyle.getParaPr().setSpaceAfter(120);

        return format;
    }

    private static FieldIndexFormatDefinition defineFancyFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 18);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .bold()
            .name(ThemeFontCore.MAJOR_H_ANSI);

        headingStyle.getParaPr().setAlignment(ParagraphAlignment.CENTER);
        headingStyle.getParaPr().setSpaceBefore(240);
        headingStyle.getParaPr().setSpaceAfter(120);

        Border border = new Border();
        border.setLineStyle(LineStyle.DOUBLE);
        border.setLineWidth(0.75);
        border.setShadow(true);
        headingStyle.getParaPr().setBorderTop(border);
        headingStyle.getParaPr().setBorderRight(border);
        headingStyle.getParaPr().setBorderBottom(border);
        headingStyle.getParaPr().setBorderLeft(border);

        return format;
    }

    private static FieldIndexFormatDefinition defineModernFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 18);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .bold()
            .italic()
            .size(26)
            .name(ThemeFontCore.MINOR_H_ANSI);

        headingStyle.getParaPr().setSpaceBefore(360);
        headingStyle.getParaPr().setSpaceAfter(240);

        Border border = new Border();
        border.setLineStyle(LineStyle.SINGLE);
        border.setLineWidth(1.5);
        headingStyle.getParaPr().setBorderTop(border);

        return format;
    }

    private static FieldIndexFormatDefinition defineBulletedFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 18);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .bold()
            .size(28)
            .name(ThemeFontCore.MAJOR_H_ANSI);

        headingStyle.getParaPr().setLeftIndent(140);
        headingStyle.getParaPr().setSpaceBefore(240);
        headingStyle.getParaPr().setSpaceAfter(120);

        return format;
    }

    private static FieldIndexFormatDefinition defineFormalFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 20);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .bold()
            .italic()
            .size(20)
            .name(ThemeFontCore.MINOR_H_ANSI);

        headingStyle.getParaPr().setSpaceBefore(120);
        headingStyle.getParaPr().setSpaceAfter(120);

        return format;
    }

    private static FieldIndexFormatDefinition defineSimpleFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonAttrs(format, 20);

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING)
            .size(20)
            .name(ThemeFontCore.MINOR_H_ANSI);

        headingStyle.getParaPr().setSpaceAfter(0);

        return format;
    }

    private static FieldIndexFormatDefinition defineTemplateFormat()
    {
        FieldIndexFormatDefinition format = new FieldIndexFormatDefinition();

        defineCommonParaPrAttrs(format);

        format.setEntriesParaPr(ParaAttr.LINE_SPACING, new LineSpacing(240, LineSpacingRule.MULTIPLE));

        FieldIndexFormatStyleDefinition headingStyle = format.getStyles().get(StyleIdentifier.INDEX_HEADING).bold();
        headingStyle.getRunPr().setComplexNameAscii(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_H_ANSI));
        headingStyle.getRunPr().setComplexNameFarEast(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_EAST_ASIA));
        headingStyle.getRunPr().setComplexNameOther(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_H_ANSI));
        headingStyle.getRunPr().setComplexNameBi(ComplexFontName.fromTheme(ThemeFontCore.MAJOR_BIDI));

        return format;
    }

    private static void defineCommonAttrs(FieldIndexFormatDefinition format, int fontSize)
    {
        defineCommonRunPrAttrs(format, fontSize);
        defineCommonParaPrAttrs(format);
    }

    private static void defineCommonRunPrAttrs(FieldIndexFormatDefinition format, int fontSize)
    {
        format.setEntriesRunPr(FontAttr.SIZE, fontSize);
        format.setEntriesRunPr(FontAttr.SIZE_BI, fontSize);
        format.setEntriesRunPr(FontAttr.NAME_ASCII, ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
        format.setEntriesRunPr(FontAttr.NAME_OTHER, ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
        format.setEntriesRunPr(FontAttr.NAME_BI, ComplexFontName.fromTheme(ThemeFontCore.MINOR_H_ANSI));
    }

    private static void defineCommonParaPrAttrs(FieldIndexFormatDefinition format)
    {
        format.setEntriesParaPr(ParaAttr.FIRST_LINE_INDENT, -220);
        format.setEntriesParaPr(ParaAttr.SPACE_AFTER, 0);

        format.getStyles().get(StyleIdentifier.INDEX_1).getParaPr().setLeftIndent(220);
        format.getStyles().get(StyleIdentifier.INDEX_2).getParaPr().setLeftIndent(440);
        format.getStyles().get(StyleIdentifier.INDEX_3).getParaPr().setLeftIndent(660);
        format.getStyles().get(StyleIdentifier.INDEX_4).getParaPr().setLeftIndent(880);
        format.getStyles().get(StyleIdentifier.INDEX_5).getParaPr().setLeftIndent(1100);
        format.getStyles().get(StyleIdentifier.INDEX_6).getParaPr().setLeftIndent(1320);
        format.getStyles().get(StyleIdentifier.INDEX_7).getParaPr().setLeftIndent(1540);
        format.getStyles().get(StyleIdentifier.INDEX_8).getParaPr().setLeftIndent(1760);
        format.getStyles().get(StyleIdentifier.INDEX_9).getParaPr().setLeftIndent(1980);
    }

    static void apply(Document document, /*FieldIndexFormat*/int format)
    {
        FieldIndexFormatDefinition definition = G_DEFINITIONS.get(format);
        for (Map.Entry<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> pair : definition.getStyles().entrySet())
        {
            Style documentStyle = document.getStyles().getByStyleIdentifier(pair.getKey());
            documentStyle.setRunPr(AttrCollectionExtension.deepClone(pair.getValue().getRunPr()));
            documentStyle.setParaPr(AttrCollectionExtension.deepClone(pair.getValue().getParaPr()));
        }
    }

    static /*FieldIndexFormat*/int identify(Document document)
    {
        for (Map.Entry<Integer, FieldIndexFormatApplier.FieldIndexFormatDefinition> pair : G_DEFINITIONS.entrySet())
        {
            if (pair.getKey() == FieldIndexFormat.TEMPLATE)
                continue;

            if (isFormatApplied(document, pair.getValue()))
                return pair.getKey();
        }

        return FieldIndexFormat.TEMPLATE;
    }

    private static boolean isFormatApplied(DocumentBase document, FieldIndexFormatDefinition formatDefinition)
    {
        for (Map.Entry<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> pair : formatDefinition.getStyles().entrySet())
        {
            Style documentStyle = document.getStyles().getByStyleIdentifier(pair.getKey());

            if (!isFormatApplied(
                new StyleRunPrAttrSource(documentStyle),
                pair.getValue().getRunPr(),
                FontAttr.NAME_ASCII,
                FontAttr.NAME_OTHER,
                FontAttr.NAME_BI,
                FontAttr.SIZE_BI,
                FontAttr.BOLD_BI,
                FontAttr.ITALIC_BI))
            {
                return false;
            }

            if (!isFormatApplied(
                new StyleParaPrAttrSource(documentStyle),
                pair.getValue().getParaPr(),
                ParaAttr.LEFT_INDENT,
                ParaAttr.FIRST_LINE_INDENT))
            {
                return false;
            }
        }

        return true;
    }

    private static boolean isFormatApplied(IAttrSource style, AttrCollection format, /*params*/ int... ignoredKeys)
    {
        for (int i = 0; i < format.getCount(); i++)
        {
            int key = format.getKey(i);
            if (msArray.indexOf((ignoredKeys), (key), 0, (ignoredKeys).length) != -1)
                continue;

            Object expectedValue = format.get(key);
            Object actualValue = style.fetchAttr(key);
            if (!expectedValue.equals(actualValue))
                return false;
        }

        return true;
    }

    private static /*final*/ Map<Integer, FieldIndexFormatApplier.FieldIndexFormatDefinition> G_DEFINITIONS = G_DEFINITIONSInitializer();
    /*JAVA-added private static Initializer*/
    private static Map<Integer, FieldIndexFormatApplier.FieldIndexFormatDefinition> G_DEFINITIONSInitializer()
    {
        Map<Integer, FieldIndexFormatApplier.FieldIndexFormatDefinition> G_DEFINITIONS = new HashMap<Integer, FieldIndexFormatApplier.FieldIndexFormatDefinition>();
    	{
        	G_DEFINITIONS.put( FieldIndexFormat.CLASSIC, defineClassicFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.FANCY, defineFancyFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.MODERN, defineModernFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.BULLETED, defineBulletedFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.FORMAL, defineFormalFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.SIMPLE, defineSimpleFormat());
        	G_DEFINITIONS.put( FieldIndexFormat.TEMPLATE, defineTemplateFormat());
    	}
        return G_DEFINITIONS;
    }

    private static class FieldIndexFormatDefinition
    {
        FieldIndexFormatDefinition()
        {
            mStyles = new HashMap<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition>();
            getStyles().put(StyleIdentifier.INDEX_HEADING, new FieldIndexFormatStyleDefinition());
            for (int i = (int)StyleIdentifier.INDEX_1; i <= (int)StyleIdentifier.INDEX_9; i++)
                getStyles().put((/*StyleIdentifier*/int)i, new FieldIndexFormatStyleDefinition());
        }

        Map<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> getStyles() { return mStyles; };

        private Map<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> mStyles;

        void setEntriesRunPr(int key, Object value)
        {
            for (Map.Entry<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> pair : getStyles().entrySet())
            {
                if (pair.getKey() == StyleIdentifier.INDEX_HEADING)
                    continue;

                pair.getValue().getRunPr().set(key, value);
            }
        }

        void setEntriesParaPr(int key, Object value)
        {
            for (Map.Entry<Integer, FieldIndexFormatApplier.FieldIndexFormatStyleDefinition> pair : getStyles().entrySet())
            {
                if (pair.getKey() == StyleIdentifier.INDEX_HEADING)
                    continue;

                pair.getValue().getParaPr().set(key, value);
            }
        }
    }

    private static class FieldIndexFormatStyleDefinition
    {
        FieldIndexFormatStyleDefinition()
        {
            mRunPr = new RunPr();
            mParaPr = new ParaPr();
        }

        RunPr getRunPr() { return mRunPr; };

        private RunPr mRunPr;
        ParaPr getParaPr() { return mParaPr; };

        private ParaPr mParaPr;

        FieldIndexFormatStyleDefinition bold()
        {
            getRunPr().setBold(AttrBoolEx.TRUE);
            getRunPr().setBoldBi(AttrBoolEx.TRUE);
            return this;
        }

        FieldIndexFormatStyleDefinition italic()
        {
            getRunPr().setItalic(AttrBoolEx.TRUE);
            getRunPr().setItalicBi(AttrBoolEx.TRUE);
            return this;
        }

        FieldIndexFormatStyleDefinition size(int size)
        {
            getRunPr().setSize(size);
            getRunPr().setSizeBi(size);
            return this;
        }

        FieldIndexFormatStyleDefinition name(/*ThemeFontCore*/int font)
        {
            getRunPr().setComplexNameAscii(ComplexFontName.fromTheme(font));
            getRunPr().setComplexNameOther(ComplexFontName.fromTheme(font));
            getRunPr().setComplexNameBi(ComplexFontName.fromTheme(font));
            return this;
        }
    }

    private interface IAttrSource
    {
        public Object fetchAttr(int key);
    }

    private static class StyleRunPrAttrSource implements IAttrSource
    {
        public StyleRunPrAttrSource(Style style)
        {
            mStyle = style;
        }

        public Object fetchAttr(int key)
        {
            return mStyle.getFontAttr(key, true);
        }

        private /*final*/ Style mStyle;
    }

    private static class StyleParaPrAttrSource implements IAttrSource
    {
        public StyleParaPrAttrSource(Style style)
        {
            mStyle = style;
        }

        public Object fetchAttr(int key)
        {
            return (mStyle.getParaAttr(key, RevisionsView.FINAL) != null ? mStyle.getParaAttr(key, RevisionsView.FINAL) : mStyle.getStyles().getDefaultParaPr().fetchAttr(key));
        }

        private /*final*/ Style mStyle;
    }
}

