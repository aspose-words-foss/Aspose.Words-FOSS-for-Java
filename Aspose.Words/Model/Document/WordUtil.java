// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.CharacterClassifier;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.DateTime;
import com.aspose.GeometryUtil;
import com.aspose.ms.System.Drawing.msSizeF;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.EnumUtilPal;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Various unsorted Word document related utility functions.
/// Try to keep this to minimum and move methods into more suitable classes.
/// </summary>
/*static*/ class WordUtil
{
	/* Simulation of static class by using private constructor */
	private WordUtil()
	{}

    /// <summary>
    /// Checks that style index is valid for Word document.
    /// </summary>
    static boolean isValidIstd(int istd)
    {
        return istd < STYLE_LIMIT;
    }

    /// <summary>
    /// Checks that style index is valid for Word document, otherwise logs warning.
    /// </summary>
    static boolean checkValidAndWarn(Style style, IWarningCallback warningCallback, /*WarningSource*/int warningSource)
    {
        if (isValidIstd(style.getIstd()))
            return true;

        if(warningCallback != null)
            warningCallback.warning(
                new WarningInfo(WarningType.DATA_LOSS, warningSource, msString.format(WarningStrings.INVALID_STYLE_ISTD, style.getName())));

        return false;
    }

    /// <summary>
    /// Converts style index read from Word document to Aspose style index.
    /// </summary>
    static int toAsposeIstd(int istd)
    {
        return (istd == STYLE_NIL)
            ? StyleIndex.NIL
            : istd;
    }

    /// <summary>
    /// Converts Aspose style index to style index can be saved to Word document.
    /// </summary>
    static int toWordIstd(int istd)
    {
        return (istd == StyleIndex.NIL)
            ? STYLE_NIL
            : istd;
    }

    /// <summary>
    /// If the node is empty or the last child is not a paragraph, adds one empty paragraph.
    /// Needed to make some stories valid from MS Word point of view (e.g. body, cell, footnote, comment).
    /// </summary>
    static void ensureNonEmptyStory(CompositeNode story)
    {
        if (isAddParagraphNeeded(story))
            story.appendChild(new Paragraph(story.getDocument()));
    }

    static boolean isAddParagraphNeeded(CompositeNode story)
    {
        Node lastChild = story.getLastNonAnnotationChild();

        if (lastChild == null)
            return true;

        switch (lastChild.getNodeType())
        {
            case NodeType.PARAGRAPH:
                // Last child is a paragraph already, don't need to add one.
                return false;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            {
                ((StructuredDocumentTag)lastChild).ensureCorrectLastChild();
                // I think recursion is well applicable here, just peer inside the sdt/customXml to find if it contains a paragraph or not.
                return isAddParagraphNeeded((CompositeNode)lastChild);
            }
            default:
                return true;
        }
    }

    static boolean paragraphCanBeRemoved(Paragraph paragraph)
    {
        CompositeNode previousNode = ms.as(paragraph.getPreviousNonAnnotationSibling(), CompositeNode.class);
        if (previousNode != null && (previousNode.getNodeType() == NodeType.PARAGRAPH || !isAddParagraphNeeded(previousNode)))
            return true;

        Node parent = paragraph;
        while (parent != null &&
               parent.getNodeType() != NodeType.BODY &&
               parent.getNodeType() != NodeType.CELL &&
               parent.getNodeType() != NodeType.COMMENT &&
               parent.getNodeType() != NodeType.FOOTNOTE)
            parent = (parent.getNextNonAnnotationSibling() == null) ? parent.getParentNode() : null;

        if (parent == null)
            return true;

        return false;
    }

    /// <summary>
    /// Gets the character category (that determines what font name to use) for a specified character.
    ///
    /// The ranges of Unicode characters here are taken from
    /// http://en.wikipedia.org/wiki/Mapping_of_Unicode_characters
    /// and might not be perfect.
    /// See this resource for Unicode ranges:
    /// http://www.alanwood.net/unicode/index.html
    /// </summary>
    static /*CharacterCategory*/int getCharacterCategory(int c)
    {
        if ((c >= 0) && (c <= 127))
            return CharacterCategory.ASCII;

        if (c <= 0xffff && CharacterClassifier.isCjk(c)) // CJK Unicode blocks.
            return CharacterCategory.FAR_EAST;

        if (c > 0xffff && CharacterClassifier.isSurrogateCjk(c)) // Extended CJK Unicode blocks (surrogate pairs).
            return CharacterCategory.FAR_EAST;

        if ((c >= 0x0590) && (c <= 0x0dff))            // Range from Hebrew to Sinhala.
            return CharacterCategory.COMPLEX_SCRIPT;

        if ((c >= 0xFB1D) && (c <= 0xFB4F))            // Hebrew Presentation Forms
            return CharacterCategory.COMPLEX_SCRIPT;

        if ((c >= 0xFB50 && c <= 0xFDFF) ||            // Arabic Presentation Forms
            (c >= 0xFE70 && c <= 0xFEFF))
            return CharacterCategory.COMPLEX_SCRIPT;

        // WORDSNET-17222 Add detection of the Thai Unicode range.
        if (CharacterClassifier.isThai((char)c))
            return CharacterCategory.COMPLEX_SCRIPT;

        return CharacterCategory.OTHER;
    }

    /// <summary>
    /// Returns category of character considering character category hint and complex script property.
    ///
    /// This method doesn't check whether Font.ComplexScript is true or not.
    /// This method is used in TextSplitter along with Font.ComplexScript value caching
    /// Because frequent calls of Font.ComplexScript is too expensive.
    /// </summary>
    static /*CharacterCategory*/int getCharacterCategoryIgnoreComplexScript(int c, Font font)
    {
        /*CharacterCategory*/int charCategory = WordUtil.getCharacterCategory(c);

        // AM. According to binary doc specification hint presence forces Word to use specified category when set but
        // according to ECMA-376 hint affects only ambiguous character. There are few files with ascii text has FarEast hint set and
        // Word really ignores the hint. So I couldn't understand what the ambiguity means exactly and
        // try to apply hint only for CharacterCategory.Other characters.
        // So far it seems to be OK and some golds accepted are better looking now.
        if (charCategory != CharacterCategory.OTHER)
            return charCategory;

        Object hintValue = font.getParent().getDirectRunAttr(FontAttr.CHARACTER_CATEGORY_HINT);

        if (hintValue == null)
            return charCategory;

        /*CharacterCategory*/int hint = (/*CharacterCategory*/Integer)hintValue;

        if (hint == CharacterCategory.FAR_EAST)
        {
            // Word ignores the FarEast hint for some Unicode ranges depending on ThemeFontFarEast or/and NameFarEast.
            // See WORDSNET-18373, WORDSNET-25643, WORDSNET-27859 for details.

            // Latin Extended-A (U+0100 - U+017F), Latin Extended-B (U+0180 - U+024F), IPA Extensions (U+0250-U+02AF).
            if (0x0100 <= c && c <= 0x02AF &&
                font.getThemeFontFarEast() == ThemeFont.NONE)
            {
                return CharacterCategory.OTHER;
            }

            // Spacing Modifier Letters (U+02B0 - U+02FF), Combining Diacritical Marks (U+0300 - U+036F),
            // Greek and Coptic (U+0370 - U+03FF), Cyrillic (U+0400 - U+04FF)
            if (0x02B0 <= c && c <= 0x4FF &&
                (TIMES_NEW_ROMAN.equals(font.getNameFarEast()) || TIMES_NEW_ROMAN_CYR.equals(font.getNameFarEast())) &&
                font.getThemeFontFarEast() == ThemeFont.NONE)
            {
                return CharacterCategory.OTHER;
            }

            // Cyrillic Supplementary (U+0500 - u+052F)
            // Armenian (U+0530 - U+058F)
            if (0x0500 <= c && c <= 0x058F)
            {
                return CharacterCategory.OTHER;
            }

            // When East Asian hint is specified for characters with CharacterCategory.Other
            // the ultimate CharacterCategory is resolved based on the East Asian width of characters.
            return EastAsianWidthResolver.isWideCharacter(c, font.getLocaleIdFarEast())
                ? CharacterCategory.FAR_EAST
                : CharacterCategory.OTHER;
        }

        // Ignore CharacterCategory.ComplexScript hint if ComplexScript is not set.
        if (hint == CharacterCategory.COMPLEX_SCRIPT)
            return CharacterCategory.OTHER;

        return hint;
    }

    /// <summary>
    /// Returns category of character considering character category hint and complex script property.
    /// </summary>
    static /*CharacterCategory*/int getCharacterCategory(int c, Font font)
    {
        // WORDSNET-8660 Character category should be ComplexScript when
        // Font.ComplexScript is true regardless of other properties. See ISO_IEC_29500-1, 17.3.2.7.
        if (font.getComplexScript())
            return CharacterCategory.COMPLEX_SCRIPT;

        return getCharacterCategoryIgnoreComplexScript(c, font);
    }

    /// <summary>
    /// Returns the complex font name for the specified character category.
    /// </summary>
    static ComplexFontName getComplexFontNameByCategory(/*CharacterCategory*/int characterCategory,
        ComplexFontName nameAscii, ComplexFontName nameBi, ComplexFontName nameFarEast, ComplexFontName nameOther)
    {
        switch (characterCategory)
        {
            case CharacterCategory.ASCII:
                return nameAscii;
            case CharacterCategory.COMPLEX_SCRIPT:
                return nameBi;
            case CharacterCategory.FAR_EAST:
                return nameFarEast;
            case CharacterCategory.OTHER:
            default:
                return nameOther;
        }
    }

    /// <summary>
    /// Returns the font name for the specified character category.
    /// </summary>
    static String getFontNameByCategory(/*CharacterCategory*/int characterCategory,
        String nameAscii, String nameBi, String nameFarEast, String nameOther)
    {
        switch (characterCategory)
        {
            case CharacterCategory.ASCII:
                return nameAscii;
            case CharacterCategory.COMPLEX_SCRIPT:
                return nameBi;
            case CharacterCategory.FAR_EAST:
                return nameFarEast;
            case CharacterCategory.OTHER:
            default:
                return nameOther;
        }
    }

    static String trimDoubleQuotes(String s)
    {
        return trimDoubleQuotes(s, true);
    }

    static String trimDoubleQuotes(String s, boolean isAllOccurrences)
    {
        if (isAllOccurrences)
            return msString.trim(s, '\"');

        if (msString.startsWith(s, "\"", StringComparison.ORDINAL))
            s = s.substring(1);
        if (msString.endsWith(s, "\"", StringComparison.ORDINAL))
            s = s.substring((0), (0) + (s.length() - 1));

        return s;
    }

    // Check all chars that is treated as quotation marks by Word.
    static boolean isInQuotationMarks(String s)
    {
        if (msString.isNullOrEmpty(s) || s.length() < 2)
            return false;

        return (msString.indexOfAny(s, G_QUOTATION_MARKS, 0, 1) == 0) && (msString.indexOfAny(s, G_QUOTATION_MARKS, s.length() - 1, 1) == s.length() - 1);
    }

    // Trim all chars that is treated as quotation marks by Word.
    static String trimQuotationMarks(String s)
    {
        s = msString.trimStart(s, G_QUOTATION_MARKS);
        s = msString.trimEnd(s, G_QUOTATION_MARKS);

        return s;
    }

    static String addDoubleQuotes(String s)
    {
        return msString.format("\"{0}\"", s);
    }

    /// <summary>
    /// Removes enclosing double quotes and replaces double backslashes with single ones.
    /// </summary>
    static String decodeMSWordUri(String wordUri)
    {
        Debug.assertTrue(wordUri != null);
        wordUri = trimDoubleQuotes(wordUri);
        wordUri = wordUri.replace("\\\\", "\\");
        return wordUri;
    }

    /// <summary>
    /// Replaces single backslashes with double backslashes and adds double quotes around the string.
    /// </summary>
    static String encodeMSWordUri(String uri)
    {
        Debug.assertTrue(uri != null);
        uri = uri.replace("\\", "\\\\");
        uri = addDoubleQuotes(uri);
        return uri;
    }

    /// <summary>
    /// Replaces CrLf combinations with Cr, replaces Lf characters with Cr.
    /// </summary>
    static String normalizeToWord(String text)
    {
        // Performance optimization.
        if (text.indexOf(ControlChar.LINE_FEED_CHAR) == -1)
            return text;

        StringBuilder builder = new StringBuilder(text);
        msStringBuilder.replace(builder, ControlChar.CR_LF, ControlChar.CR);
        msStringBuilder.replace(builder, ControlChar.LF, ControlChar.CR);
        return builder.toString();
    }

    /// <summary>
    /// Replaces CrLf or Cr with Lf.
    /// </summary>
    static String normalizeToWordArt(String text)
    {
        StringBuilder builder = new StringBuilder(text);
        msStringBuilder.replace(builder, ControlChar.CR_LF, ControlChar.LF);
        msStringBuilder.replace(builder, ControlChar.CR, ControlChar.LF);
        return builder.toString();
    }

    /// <summary>
    /// Formats a DateTime value using an MS Word date/time format string.
    /// </summary>
    static String formatDateTime(DateTime d, String format)
    {
        final int EAST_ASIAN_LANGUAGE_NOT_SET = 0;
        return formatDateTime(d, format, EAST_ASIAN_LANGUAGE_NOT_SET);
    }

    /// <summary>
    /// Formats a DateTime value using an MS Word date/time format string.
    /// </summary>
    static String formatDateTime(DateTime d, String format, int eastAsianLanguageId)
    {
        return formatDateTime(d, format, eastAsianLanguageId, CalendarType.GREGORIAN);
    }

    /// <summary>
    /// Formats a DateTime value using an MS Word date/time format string.
    /// </summary>
    static String formatDateTime(DateTime d, String format, int eastAsianLanguageId, /*CalendarType*/int calendarType)
    {
        return DateTimeFormat.formatDateTime(format, d, eastAsianLanguageId, calendarType);
    }

    static /*StoryType*/int headerFooterTypeToStoryType(/*HeaderFooterType*/int type)
    {
        switch (type)
        {
            case HeaderFooterType.FOOTER_EVEN:
                return StoryType.EVEN_PAGES_FOOTER;
            case HeaderFooterType.FOOTER_FIRST:
                return StoryType.FIRST_PAGE_FOOTER;
            case HeaderFooterType.FOOTER_PRIMARY:
                return StoryType.PRIMARY_FOOTER;
            case HeaderFooterType.HEADER_EVEN:
                return StoryType.EVEN_PAGES_HEADER;
            case HeaderFooterType.HEADER_FIRST:
                return StoryType.FIRST_PAGE_HEADER;
            case HeaderFooterType.HEADER_PRIMARY:
                return StoryType.PRIMARY_HEADER;
            default:
                throw new IllegalStateException("Unknown header or footer type.");
        }
    }

    static /*StoryType*/int footnoteSeparatorTypeToStoryType(/*FootnoteSeparatorType*/int type)
    {
        switch (type)
        {
            case FootnoteSeparatorType.FOOTNOTE_SEPARATOR:
                return StoryType.FOOTNOTE_SEPARATOR;
            case FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR:
                return StoryType.FOOTNOTE_CONTINUATION_SEPARATOR;
            case FootnoteSeparatorType.FOOTNOTE_CONTINUATION_NOTICE:
                return StoryType.FOOTNOTE_CONTINUATION_NOTICE;
            case FootnoteSeparatorType.ENDNOTE_SEPARATOR:
                return StoryType.ENDNOTE_SEPARATOR;
            case FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR:
                return StoryType.ENDNOTE_CONTINUATION_SEPARATOR;
            case FootnoteSeparatorType.ENDNOTE_CONTINUATION_NOTICE:
                return StoryType.ENDNOTE_CONTINUATION_NOTICE;
            default:
                throw new IllegalStateException("Unknown footnote or endnote separator type.");
        }
    }

    static /*HeaderFooterType*/int storyTypeToHeaderFooterType(/*StoryType*/int type)
    {
        switch (type)
        {
            case StoryType.EVEN_PAGES_FOOTER:
                return HeaderFooterType.FOOTER_EVEN;
            case StoryType.FIRST_PAGE_FOOTER:
                return HeaderFooterType.FOOTER_FIRST;
            case StoryType.PRIMARY_FOOTER:
                return HeaderFooterType.FOOTER_PRIMARY;
            case StoryType.EVEN_PAGES_HEADER:
                return HeaderFooterType.HEADER_EVEN;
            case StoryType.FIRST_PAGE_HEADER:
                return HeaderFooterType.HEADER_FIRST;
            case StoryType.PRIMARY_HEADER:
                return HeaderFooterType.HEADER_PRIMARY;
            default:
                throw new IllegalStateException("This story is not a header or footer story.");
        }
    }

    /// <summary>
    /// Returns paper size in twips.
    /// </summary>
    static /*Size*/long paperSizeToSize(/*PaperSize*/int paperSize)
    {
        Debug.assertTrue(paperSize != PaperSize.CUSTOM);
        return G_PAPER_SIZES[(int)paperSize];
    }

    /// <summary>
    /// Converts size in twips into paper size enumeration. Width and height must match the specified orientation, otherwise it will return custom paper size.
    /// </summary>
    static /*PaperSize*/int sizeToPaperSize(int width, int height, boolean isLandscape)
    {
        for (int i = 0; i < G_PAPER_SIZES.length; i++)
        {
            if (i == (int)PaperSize.CUSTOM)
                continue;

            /*Size*/long refSize = G_PAPER_SIZES[i];

            if (GeometryUtil.equalsPaperSize(msSizeF.ctor(msSize.getWidth(refSize), msSize.getHeight(refSize)), msSizeF.ctor(width, height), isLandscape, G_PAPER_SIZE_TOLERANCE))
                return (/*PaperSize*/int)i;
        }
        return PaperSize.CUSTOM;
    }

    /// <summary>
    /// Returns a floating value which represents the filling level of the specified
    /// texture type.
    /// </summary>
    /// <param name="index"></param>
    /// <returns>
    /// A value from 0.0 to 1.0 where 0 corresponds to 0% filling and 1.0 corresponds
    /// to 100% filling level.
    /// </returns>
    static double getFillingLevelByTextureIndex(/*TextureIndex*/int index)
    {
        switch (index)
        {
            case TextureIndex.TEXTURE_NONE:
            case TextureIndex.TEXTURE_NIL:
                return 0.0;
            case TextureIndex.TEXTURE_2_PT_5_PERCENT:
                return 0.025;
            case TextureIndex.TEXTURE_5_PERCENT:
                return 0.05;
            case TextureIndex.TEXTURE_7_PT_5_PERCENT:
                return 0.075;
            case TextureIndex.TEXTURE_10_PERCENT:
                return 0.1f;
            case TextureIndex.TEXTURE_12_PT_5_PERCENT:
                return 0.125;
            case TextureIndex.TEXTURE_15_PERCENT:
                return 0.15;
            case TextureIndex.TEXTURE_17_PT_5_PERCENT:
                return 0.175;
            case TextureIndex.TEXTURE_20_PERCENT:
                return 0.2;
            case TextureIndex.TEXTURE_22_PT_5_PERCENT:
                return 0.225;
            case TextureIndex.TEXTURE_25_PERCENT:
            case TextureIndex.TEXTURE_HORIZONTAL:
            case TextureIndex.TEXTURE_VERTICAL:
            case TextureIndex.TEXTURE_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DIAGONAL_UP:
                return 0.25;
            case TextureIndex.TEXTURE_27_PT_5_PERCENT:
                return 0.275;
            case TextureIndex.TEXTURE_30_PERCENT:
            case TextureIndex.TEXTURE_DIAGONAL_CROSS:
                return 0.3;
            case TextureIndex.TEXTURE_32_PT_5_PERCENT:
                return 0.325;
            case TextureIndex.TEXTURE_35_PERCENT:
            case TextureIndex.TEXTURE_CROSS:
                return 0.35;
            case TextureIndex.TEXTURE_37_PT_5_PERCENT:
                return 0.375;
            case TextureIndex.TEXTURE_40_PERCENT:
                return 0.4;
            case TextureIndex.TEXTURE_42_PT_5_PERCENT:
                return 0.425;
            case TextureIndex.TEXTURE_45_PERCENT:
                return 0.45;
            case TextureIndex.TEXTURE_47_PT_5_PERCENT:
                return 0.475;
            case TextureIndex.TEXTURE_50_PERCENT:
            case TextureIndex.TEXTURE_DARK_HORIZONTAL:
            case TextureIndex.TEXTURE_DARK_VERTICAL:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_UP:
            case TextureIndex.TEXTURE_DARK_CROSS:
                return 0.5;
            case TextureIndex.TEXTURE_52_PT_5_PERCENT:
                return 0.525;
            case TextureIndex.TEXTURE_55_PERCENT:
                return 0.55;
            case TextureIndex.TEXTURE_57_PT_5_PERCENT:
                return 0.575;
            case TextureIndex.TEXTURE_60_PERCENT:
                return 0.6;
            case TextureIndex.TEXTURE_62_PT_5_PERCENT:
                return 0.625;
            case TextureIndex.TEXTURE_65_PERCENT:
                return 0.65;
            case TextureIndex.TEXTURE_67_PT_5_PERCENT:
                return 0.675;
            case TextureIndex.TEXTURE_70_PERCENT:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_CROSS:
                return 0.7;
            case TextureIndex.TEXTURE_72_PT_5_PERCENT:
                return 0.725;
            case TextureIndex.TEXTURE_75_PERCENT:
                return 0.75;
            case TextureIndex.TEXTURE_77_PT_5_PERCENT:
                return 0.775;
            case TextureIndex.TEXTURE_80_PERCENT:
                return 0.8;
            case TextureIndex.TEXTURE_82_PT_5_PERCENT:
                return 0.825;
            case TextureIndex.TEXTURE_85_PERCENT:
                return 0.85;
            case TextureIndex.TEXTURE_87_PT_5_PERCENT:
                return 0.875;
            case TextureIndex.TEXTURE_90_PERCENT:
                return 0.9;
            case TextureIndex.TEXTURE_92_PT_5_PERCENT:
                return 0.925;
            case TextureIndex.TEXTURE_95_PERCENT:
                return 0.95;
            case TextureIndex.TEXTURE_97_PT_5_PERCENT:
                return 0.975;
            case TextureIndex.TEXTURE_SOLID:
                return 1.0;
            default:
                throw new IllegalStateException("Unknown texture.");
        }
    }

    /// <summary>
    /// Validates the specified texture index against the available patterns and converts
    /// the invalid indices into <see cref="TextureIndex.TextureNone"/> just like MS Word does.
    /// </summary>
    static /*TextureIndex*/int validateTextureIndex(/*TextureIndex*/int index)
    {
        switch (index)
        {
            case TextureIndex.TEXTURE_NONE:
            case TextureIndex.TEXTURE_NIL:
            case TextureIndex.TEXTURE_2_PT_5_PERCENT:
            case TextureIndex.TEXTURE_5_PERCENT:
            case TextureIndex.TEXTURE_7_PT_5_PERCENT:
            case TextureIndex.TEXTURE_10_PERCENT:
            case TextureIndex.TEXTURE_12_PT_5_PERCENT:
            case TextureIndex.TEXTURE_15_PERCENT:
            case TextureIndex.TEXTURE_17_PT_5_PERCENT:
            case TextureIndex.TEXTURE_20_PERCENT:
            case TextureIndex.TEXTURE_22_PT_5_PERCENT:
            case TextureIndex.TEXTURE_25_PERCENT:
            case TextureIndex.TEXTURE_HORIZONTAL:
            case TextureIndex.TEXTURE_VERTICAL:
            case TextureIndex.TEXTURE_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DIAGONAL_UP:
            case TextureIndex.TEXTURE_27_PT_5_PERCENT:
            case TextureIndex.TEXTURE_30_PERCENT:
            case TextureIndex.TEXTURE_DIAGONAL_CROSS:
            case TextureIndex.TEXTURE_32_PT_5_PERCENT:
            case TextureIndex.TEXTURE_35_PERCENT:
            case TextureIndex.TEXTURE_CROSS:
            case TextureIndex.TEXTURE_37_PT_5_PERCENT:
            case TextureIndex.TEXTURE_40_PERCENT:
            case TextureIndex.TEXTURE_42_PT_5_PERCENT:
            case TextureIndex.TEXTURE_45_PERCENT:
            case TextureIndex.TEXTURE_47_PT_5_PERCENT:
            case TextureIndex.TEXTURE_50_PERCENT:
            case TextureIndex.TEXTURE_DARK_HORIZONTAL:
            case TextureIndex.TEXTURE_DARK_VERTICAL:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_UP:
            case TextureIndex.TEXTURE_DARK_CROSS:
            case TextureIndex.TEXTURE_52_PT_5_PERCENT:
            case TextureIndex.TEXTURE_55_PERCENT:
            case TextureIndex.TEXTURE_57_PT_5_PERCENT:
            case TextureIndex.TEXTURE_60_PERCENT:
            case TextureIndex.TEXTURE_62_PT_5_PERCENT:
            case TextureIndex.TEXTURE_65_PERCENT:
            case TextureIndex.TEXTURE_67_PT_5_PERCENT:
            case TextureIndex.TEXTURE_70_PERCENT:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_CROSS:
            case TextureIndex.TEXTURE_72_PT_5_PERCENT:
            case TextureIndex.TEXTURE_75_PERCENT:
            case TextureIndex.TEXTURE_77_PT_5_PERCENT:
            case TextureIndex.TEXTURE_80_PERCENT:
            case TextureIndex.TEXTURE_82_PT_5_PERCENT:
            case TextureIndex.TEXTURE_85_PERCENT:
            case TextureIndex.TEXTURE_87_PT_5_PERCENT:
            case TextureIndex.TEXTURE_90_PERCENT:
            case TextureIndex.TEXTURE_92_PT_5_PERCENT:
            case TextureIndex.TEXTURE_95_PERCENT:
            case TextureIndex.TEXTURE_97_PT_5_PERCENT:
            case TextureIndex.TEXTURE_SOLID:
                return index;
            default:
                return TextureIndex.TEXTURE_NONE;
        }
    }

    static boolean isComplexTexture(/*TextureIndex*/int index)
    {
        switch (index)
        {
            case TextureIndex.TEXTURE_HORIZONTAL:
            case TextureIndex.TEXTURE_VERTICAL:
            case TextureIndex.TEXTURE_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DIAGONAL_UP:
            case TextureIndex.TEXTURE_DIAGONAL_CROSS:
            case TextureIndex.TEXTURE_CROSS:
            case TextureIndex.TEXTURE_DARK_HORIZONTAL:
            case TextureIndex.TEXTURE_DARK_VERTICAL:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_DOWN:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_UP:
            case TextureIndex.TEXTURE_DARK_CROSS:
            case TextureIndex.TEXTURE_DARK_DIAGONAL_CROSS:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns a TextureIndex enumeration member which corresponds to the specified filling level.
    /// </summary>
    /// <param name="value">
    /// A value from 0.0 to 1.0 where 0 corresponds to 0% filling and 1.0 corresponds
    /// to 100% filling level.
    /// </param>
    /// <returns></returns>
    static /*TextureIndex*/int getTextureIndexByFillingLevel(double value)
    {
        switch ((int)(40.0 * value))
        {
            case 0: return TextureIndex.TEXTURE_NONE;
            case 1: return TextureIndex.TEXTURE_2_PT_5_PERCENT;
            case 2: return TextureIndex.TEXTURE_5_PERCENT;
            case 3: return TextureIndex.TEXTURE_7_PT_5_PERCENT;
            case 4: return TextureIndex.TEXTURE_10_PERCENT;
            case 5: return TextureIndex.TEXTURE_12_PT_5_PERCENT;
            case 6: return TextureIndex.TEXTURE_15_PERCENT;
            case 7: return TextureIndex.TEXTURE_17_PT_5_PERCENT;
            case 8: return TextureIndex.TEXTURE_20_PERCENT;
            case 9: return TextureIndex.TEXTURE_22_PT_5_PERCENT;
            case 10: return TextureIndex.TEXTURE_25_PERCENT;
            case 11: return TextureIndex.TEXTURE_27_PT_5_PERCENT;
            case 12: return TextureIndex.TEXTURE_30_PERCENT;
            case 13: return TextureIndex.TEXTURE_32_PT_5_PERCENT;
            case 14: return TextureIndex.TEXTURE_35_PERCENT;
            case 15: return TextureIndex.TEXTURE_37_PT_5_PERCENT;
            case 16: return TextureIndex.TEXTURE_40_PERCENT;
            case 17: return TextureIndex.TEXTURE_42_PT_5_PERCENT;
            case 18: return TextureIndex.TEXTURE_45_PERCENT;
            case 19: return TextureIndex.TEXTURE_47_PT_5_PERCENT;
            case 20: return TextureIndex.TEXTURE_50_PERCENT;
            case 21: return TextureIndex.TEXTURE_52_PT_5_PERCENT;
            case 22: return TextureIndex.TEXTURE_55_PERCENT;
            case 23: return TextureIndex.TEXTURE_57_PT_5_PERCENT;
            case 24: return TextureIndex.TEXTURE_60_PERCENT;
            case 25: return TextureIndex.TEXTURE_62_PT_5_PERCENT;
            case 26: return TextureIndex.TEXTURE_65_PERCENT;
            case 27: return TextureIndex.TEXTURE_67_PT_5_PERCENT;
            case 28: return TextureIndex.TEXTURE_70_PERCENT;
            case 29: return TextureIndex.TEXTURE_72_PT_5_PERCENT;
            case 30: return TextureIndex.TEXTURE_75_PERCENT;
            case 31: return TextureIndex.TEXTURE_77_PT_5_PERCENT;
            case 32: return TextureIndex.TEXTURE_80_PERCENT;
            case 33: return TextureIndex.TEXTURE_82_PT_5_PERCENT;
            case 34: return TextureIndex.TEXTURE_85_PERCENT;
            case 35: return TextureIndex.TEXTURE_87_PT_5_PERCENT;
            case 36: return TextureIndex.TEXTURE_90_PERCENT;
            case 37: return TextureIndex.TEXTURE_92_PT_5_PERCENT;
            case 38: return TextureIndex.TEXTURE_95_PERCENT;
            case 39: return TextureIndex.TEXTURE_97_PT_5_PERCENT;
            default: return TextureIndex.TEXTURE_SOLID;
        }
    }

    private static /*final*/ /*Size*/long[] G_PAPER_SIZES;

    //Paper size tolerance is said to be 1.5mm for dimensions up to 150mm and 2mm above. This is 2mm tolerance.
    private static /*final*/ int G_PAPER_SIZE_TOLERANCE = ConvertUtilCore.mmToTwip(2.0);

    /// <summary>
    /// Maximum font size in points allowed by MS Word.
    /// </summary>
    public static final double MAX_FONT_SIZE = 1638.0;

    /// <summary>
    /// Maximal style index allowed by MS Word.
    /// </summary>
    /// <remarks>
    /// According to spec maximum style count written to DOC format MUST be less than this value.
    /// This gives us maximum style index to be this value - 1 (Istd is zero based).
    ///
    /// It is interesting that other than DOC Word document formats allow greater values for style index but
    /// it's better to have unified behavior here.
    /// </remarks>
    static final int STYLE_LIMIT = (0x0FFE - 1);

    /// <summary>
    /// Style index used as Nil by MS Word.
    /// </summary>
    static final int STYLE_NIL = 0x0FFF;

    /// <summary>
    /// HeadersFooters sort order.
    /// </summary>
    private static /*final*/ int[] G_HEADER_SORT_ORDER =
        new int[]
        {
            2, /* HeaderEven */
            1, /* HeaderPrimary */
            5, /* FooterEven */
            4, /* FooterPrimary */
            0, /* HeaderFirst */
            3, /* FooterFirst */
        };

    /// <summary>
    /// The hash of Aspose.Words.Resources.AsposeLogo.png, generated with SHA-256.
    /// </summary>
    private static /*final*/ byte[] G_ASPOSE_LOGO_HASH = new byte[]
    {
        (byte) 0xFF, (byte) 0x0B, (byte) 0xA8, (byte) 0xE6, (byte) 0xF5, (byte) 0x6B, (byte) 0x06, (byte) 0x1F, (byte) 0xAC, (byte) 0x6F, (byte) 0x11, (byte) 0x12, (byte) 0xC4, (byte) 0x3C, (byte) 0x2C, (byte) 0x14,
        (byte) 0xD9, (byte) 0xB5, (byte) 0x41, (byte) 0x53, (byte) 0x20, (byte) 0x4B, (byte) 0x14, (byte) 0x0A, (byte) 0xF9, (byte) 0xE1, (byte) 0xFA, (byte) 0x40, (byte) 0xC6, (byte) 0x10, (byte) 0x4B, (byte) 0x34
    };

    private static /*final*/ char[] G_QUOTATION_MARKS = new char[] { '\"', '«', '»', '“', '”', '„', '‟' };

    private static final String TIMES_NEW_ROMAN = "Times New Roman";
    private static final String TIMES_NEW_ROMAN_CYR = "Times New Roman CYR";

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* WordUtil()*/
    {
        //See http://www.cl.cam.ac.uk/~mgk25/iso-paper.html for more info on paper sizes.
        //Also http://en.wikipedia.org/wiki/Paper_size
        // WORDSNET-19250 Corrected values for Folio, Ledger and Quarto.
        int paperSizeCount = EnumUtilPal.getEffectiveArrayLength((20));
        G_PAPER_SIZES = new /*Size*/long[paperSizeCount];
        G_PAPER_SIZES[(int)PaperSize.A3] = ConvertUtilCore.mmToTwip(297.0, 420.0);
        G_PAPER_SIZES[(int)PaperSize.A4] = ConvertUtilCore.mmToTwip(210.0, 297.0);
        G_PAPER_SIZES[(int)PaperSize.A5] = ConvertUtilCore.mmToTwip(148.0, 210.0);
        G_PAPER_SIZES[(int)PaperSize.B4] = ConvertUtilCore.mmToTwip(250.0, 353.0);
        G_PAPER_SIZES[(int)PaperSize.B5] = ConvertUtilCore.mmToTwip(176.0, 250.0);
        G_PAPER_SIZES[(int)PaperSize.EXECUTIVE] = ConvertUtilCore.inchToTwip(7.25, 10.5);
        G_PAPER_SIZES[(int)PaperSize.FOLIO] = ConvertUtilCore.inchToTwip(8.5, 13.0);
        G_PAPER_SIZES[(int)PaperSize.LEDGER] = ConvertUtilCore.inchToTwip(17.0, 11.0);
        G_PAPER_SIZES[(int)PaperSize.LEGAL] = ConvertUtilCore.inchToTwip(8.5, 14.0);
        G_PAPER_SIZES[(int)PaperSize.LETTER] = ConvertUtilCore.inchToTwip(8.5, 11.0);
        G_PAPER_SIZES[(int)PaperSize.ENVELOPE_DL] = ConvertUtilCore.mmToTwip(110.0, 220.0);
        G_PAPER_SIZES[(int)PaperSize.QUARTO] = ConvertUtilCore.inchToTwip(8.47, 10.83);
        G_PAPER_SIZES[(int)PaperSize.STATEMENT] = ConvertUtilCore.inchToTwip(5.5, 8.5);
        G_PAPER_SIZES[(int)PaperSize.TABLOID] = ConvertUtilCore.inchToTwip(11.0, 17.0);
        G_PAPER_SIZES[(int)PaperSize.PAPER_10_X_14] = ConvertUtilCore.inchToTwip(10.0, 14.0);
        G_PAPER_SIZES[(int)PaperSize.PAPER_11_X_17] = ConvertUtilCore.inchToTwip(11.0, 17.0);
        G_PAPER_SIZES[(int)PaperSize.NUMBER_10_ENVELOPE] = ConvertUtilCore.inchToTwip(4.125, 9.5);
        G_PAPER_SIZES[(int)PaperSize.JIS_B_4] = ConvertUtilCore.mmToTwip(257.0, 364.0);
        G_PAPER_SIZES[(int)PaperSize.JIS_B_5] = ConvertUtilCore.mmToTwip(182.0, 257.0);
    }
}

