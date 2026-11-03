// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2006 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Builds lists according to list templates predefined in MS Word.
/// </summary>
/*static*/ class ListFactory
{
	/* Simulation of static class by using private constructor */
	private ListFactory()
	{}

    /// <summary>
    /// Creates and adds a list and a list definition to the lists collection.
    /// </summary>
    /// <param name="lists">Where to add the new list.</param>
    /// <param name="template">Specifies what kind of list to create.</param>
    static List createList(ListCollection lists, /*ListTemplate*/int template)
    {
        switch (template)
        {
            case ListTemplate.BULLET_DEFAULT:
                return createBulletDefault(lists);
            case ListTemplate.BULLET_CIRCLE:
                return createBulletDefault(lists, COURIER_CIRCLE, COURIER_FONT);
            case ListTemplate.BULLET_SQUARE:
                return createBulletDefault(lists, WINGDING_SQUARE, WINGDINGS_FONT);
            case ListTemplate.BULLET_DIAMONDS:
                return createBulletDefault(lists, WINGDING_DIAMONDS, WINGDINGS_FONT);
            case ListTemplate.BULLET_ARROW_HEAD:
                return createBulletDefault(lists, WINGDING_ARROW_HEAD, WINGDINGS_FONT);
            case ListTemplate.BULLET_TICK:
                return createBulletDefault(lists, WINGDING_TICK, WINGDINGS_FONT);

            case ListTemplate.NUMBER_DEFAULT:
                return createNumberDefault(lists);
            case ListTemplate.NUMBER_ARABIC_PARENTHESIS:
                return createNumberDefault(lists, NumberStyle.ARABIC, "\u0000)", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_UPPERCASE_ROMAN_DOT:
                return createNumberDefault(lists, NumberStyle.UPPERCASE_ROMAN, "\u0000.", ListLevelAlignment.RIGHT);
            case ListTemplate.NUMBER_UPPERCASE_LETTER_DOT:
                return createNumberDefault(lists, NumberStyle.UPPERCASE_LETTER, "\u0000.", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_LETTER_PARENTHESIS:
                return createNumberDefault(lists, NumberStyle.LOWERCASE_LETTER, "\u0000)", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_LETTER_DOT:
                return createNumberDefault(lists, NumberStyle.LOWERCASE_LETTER, "\u0000.", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_ROMAN_DOT:
                return createNumberDefault(lists, NumberStyle.LOWERCASE_ROMAN, "\u0000.", ListLevelAlignment.RIGHT);

            case ListTemplate.OUTLINE_NUMBERS:
                return createOutlineNumbers(lists);
            case ListTemplate.OUTLINE_LEGAL:
                return createOutlineLegal(lists);
            case ListTemplate.OUTLINE_BULLETS:
                return createOutlineBullets(lists);
            case ListTemplate.OUTLINE_HEADINGS_ARTICLE_SECTION:
                return createOutlineHeadingsArticleSection(lists);
            case ListTemplate.OUTLINE_HEADINGS_LEGAL:
                return createOutlineHeadingsLegal(lists);
            case ListTemplate.OUTLINE_HEADINGS_NUMBERS:
                return createOutlineHeadingsNumbers(lists);
            case ListTemplate.OUTLINE_HEADINGS_CHAPTER:
                return createOutlineHeadingsChapter(lists);
            default:
                throw new IllegalArgumentException("template");
        }
    }

    /// <summary>
    /// Creates and adds a single level list and the list definition to the list collection.
    /// </summary>
    static List createSingleLevelList(ListCollection lists, /*ListTemplate*/int template)
    {
        switch (template)
        {
            case ListTemplate.BULLET_DEFAULT:
                return createSingleLevelBulletList(lists, SYMBOL_DISC, SYMBOL_FONT);
            case ListTemplate.BULLET_CIRCLE:
                return createSingleLevelBulletList(lists, COURIER_CIRCLE, COURIER_FONT);
            case ListTemplate.BULLET_SQUARE:
                return createSingleLevelBulletList(lists, WINGDING_SQUARE, WINGDINGS_FONT);
            case ListTemplate.BULLET_DIAMONDS:
                return createSingleLevelBulletList(lists, WINGDING_DIAMONDS, WINGDINGS_FONT);
            case ListTemplate.BULLET_ARROW_HEAD:
                return createSingleLevelBulletList(lists, WINGDING_ARROW_HEAD, WINGDINGS_FONT);
            case ListTemplate.BULLET_TICK:
                return createSingleLevelBulletList(lists, WINGDING_TICK, WINGDINGS_FONT);

            case ListTemplate.NUMBER_DEFAULT:
                return createSingleLevelNumberList(lists, NumberStyle.ARABIC, "\u0000.", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_ARABIC_PARENTHESIS:
                return createSingleLevelNumberList(lists, NumberStyle.ARABIC, "\u0000)", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_UPPERCASE_ROMAN_DOT:
                return createSingleLevelNumberList(lists, NumberStyle.UPPERCASE_ROMAN, "\u0000.", ListLevelAlignment.RIGHT);
            case ListTemplate.NUMBER_UPPERCASE_LETTER_DOT:
                return createSingleLevelNumberList(lists, NumberStyle.UPPERCASE_LETTER, "\u0000.", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_LETTER_PARENTHESIS:
                return createSingleLevelNumberList(lists, NumberStyle.LOWERCASE_LETTER, "\u0000)", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_LETTER_DOT:
                return createSingleLevelNumberList(lists, NumberStyle.LOWERCASE_LETTER, "\u0000.", ListLevelAlignment.LEFT);
            case ListTemplate.NUMBER_LOWERCASE_ROMAN_DOT:
                return createSingleLevelNumberList(lists, NumberStyle.LOWERCASE_ROMAN, "\u0000.", ListLevelAlignment.RIGHT);
            default:
                throw new IllegalArgumentException("Unable to apply the outline template to a single level list." + "\r\nParameter name: " + "template");
        }
    }

    /// <summary>
    /// Checks whether list bullet is standard and gives appropriate font.
    /// Returns null for non-standard bullets.
    /// </summary>
    static String standardBulletToFont(char bullet)
    {
        switch (bullet)
        {
            case SYMBOL_DISC_CHAR:
            case SYMBOL_DIAMOND_CHAR:
                return SYMBOL_FONT;
            case COURIER_CIRCLE_CHAR:
                return COURIER_FONT;
            case WINGDING_SQUARE_CHAR:
            case WINGDING_DIAMONDS_CHAR:
            case WINGDING_ARROW_HEAD_CHAR:
            case WINGDING_TICK_CHAR:
                return WINGDINGS_FONT;
            default:
                return null;
        }
    }

    /// <summary>
    /// Creates a bulleted single level list and the list definition.
    /// </summary>
    private static List createSingleLevelBulletList(ListCollection lists, String numberFormat, String fontName)
    {
        List list = lists.addEmpty(ListType.SINGLE_LEVEL);

        ListLevelInitializer.initBulletListLevel(list, 0, fontName, numberFormat, 720, 720, -360);

        return list;
    }

    /// <summary>
    /// Creates a numbered single level list and the list definition.
    /// </summary>
    private static List createSingleLevelNumberList(ListCollection lists, /*NumberStyle*/int numberStyle, String numberFormat, /*ListLevelAlignment*/int alignment)
    {
        List list = lists.addEmpty(ListType.SINGLE_LEVEL);

        ListLevelInitializer.initNumberListLevel(list, 0, numberStyle, numberFormat, alignment, 720, 720, -360);

        return list;
    }

    /// <summary>
    /// Creates a list and a list definition of a default bulleted list.
    /// </summary>
    private static List createBulletDefault(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.HYBRID_MULTI_LEVEL);
        ListLevelInitializer.initBulletListLevel(list, 0, SYMBOL_FONT, SYMBOL_DISC, 720, 720, -360);
        ListLevelInitializer.initBulletListLevel(list, 1, COURIER_FONT, COURIER_CIRCLE, 1440, 1440, -360);
        ListLevelInitializer.initBulletListLevel(list, 2, WINGDINGS_FONT, WINGDING_SQUARE, 2160, 2160, -360);
        ListLevelInitializer.initBulletListLevel(list, 3, SYMBOL_FONT, SYMBOL_DISC, 2880, 2880, -360);
        ListLevelInitializer.initBulletListLevel(list, 4, COURIER_FONT, COURIER_CIRCLE, 3600, 3600, -360);
        ListLevelInitializer.initBulletListLevel(list, 5, WINGDINGS_FONT, WINGDING_SQUARE, 4320, 4320, -360);
        ListLevelInitializer.initBulletListLevel(list, 6, SYMBOL_FONT, SYMBOL_DISC, 5040, 5040, -360);
        ListLevelInitializer.initBulletListLevel(list, 7, COURIER_FONT, COURIER_CIRCLE, 5760, 5760, -360);
        ListLevelInitializer.initBulletListLevel(list, 8, WINGDINGS_FONT, WINGDING_SQUARE, 6480, 6480, -360);
        return list;
    }

    /// <summary>
    /// Creates a list and a list definition based on a default bulleted list,
    /// but with a customized first level. This is to match MS Word list templates.
    /// </summary>
    private static List createBulletDefault(ListCollection lists, String firstLevelNumberFormat, String firstLevelFontName)
    {
        List list = createBulletDefault(lists);
        list.getListLevels().get(0).setNumberFormat(firstLevelNumberFormat);
        list.getListLevels().get(0).getRunPr().setSymbolFonts(firstLevelFontName);
        return list;
    }

    /// <summary>
    /// Creates a list and a list definition of a default numbered list.
    /// </summary>
    private static List createNumberDefault(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.ARABIC, "\u0000.",
            ListLevelAlignment.LEFT, 720, 720, -360);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.LOWERCASE_LETTER, "\u0001.",
            ListLevelAlignment.LEFT, 1440, 1440, -360);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.LOWERCASE_ROMAN, "\u0002.",
            ListLevelAlignment.RIGHT, 2160, 2160, -180);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.ARABIC, "\u0003.",
            ListLevelAlignment.LEFT, 2880, 2880, -360);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.LOWERCASE_LETTER, "\u0004.",
            ListLevelAlignment.LEFT, 3600, 3600, -360);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.LOWERCASE_ROMAN, "\u0005.",
            ListLevelAlignment.RIGHT, 4320, 4320, -180);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.ARABIC, "\u0006.",
            ListLevelAlignment.LEFT, 5040, 5040, -360);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "\u0007.",
            ListLevelAlignment.LEFT, 5760, 5760, -360);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "\u0008.",
            ListLevelAlignment.RIGHT, 6480, 6480, -180);
        return list;
    }

    /// <summary>
    /// Creates a list and a list definition based on a default numbered list,
    /// but hwith a customized first level. This is to match MS Word list templates.
    /// </summary>
    private static List createNumberDefault(
        ListCollection lists,
        /*NumberStyle*/int firstLevelNumberStyle,
        String firstLevelNumberFormat,
        /*ListLevelAlignment*/int firstLevelAlignment)
    {
        List list = createNumberDefault(lists);
        list.getListLevels().get(0).setNumberStyle(firstLevelNumberStyle);
        list.getListLevels().get(0).setNumberFormat(firstLevelNumberFormat);
        list.getListLevels().get(0).setAlignment(firstLevelAlignment);
        return list;
    }

    /// <summary>
    /// Creates the defualt outline numbered list.
    /// </summary>
    private static List createOutlineNumbers(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.ARABIC, "\u0000)",
            ListLevelAlignment.LEFT, 360, 360, -360);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.LOWERCASE_LETTER, "\u0001)",
            ListLevelAlignment.LEFT, 720, 720, -360);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.LOWERCASE_ROMAN, "\u0002)",
            ListLevelAlignment.LEFT, 1080, 1080, -360);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.ARABIC, "(\u0003)",
            ListLevelAlignment.LEFT, 1440, 1440, -360);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.LOWERCASE_LETTER, "(\u0004)",
            ListLevelAlignment.LEFT, 1800, 1800, -360);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.LOWERCASE_ROMAN, "(\u0005)",
            ListLevelAlignment.LEFT, 2160, 2160, -360);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.ARABIC, "\u0006.",
            ListLevelAlignment.LEFT, 2520, 2520, -360);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "\u0007.",
            ListLevelAlignment.LEFT, 2880, 2880, -360);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "\u0008.",
            ListLevelAlignment.LEFT, 3240, 3240, -360);
        return list;
    }

    /// <summary>
    /// Creates a legal numbered outline list.
    /// </summary>
    private static List createOutlineLegal(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.ARABIC, 
            "\u0000.",
            ListLevelAlignment.LEFT, 360, 360, -360);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.ARABIC, 
            "\u0000.\u0001.",
            ListLevelAlignment.LEFT, 792, 792, -432);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.",
            ListLevelAlignment.LEFT, 1440, 1224, -504);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.",
            ListLevelAlignment.LEFT, 1800, 1728, -648);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.",
            ListLevelAlignment.LEFT, 2520, 2232, -792);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.",
            ListLevelAlignment.LEFT, 2880, 2736, -936);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.",
            ListLevelAlignment.LEFT, 3600, 3240, -1080);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007.",
            ListLevelAlignment.LEFT, 3960, 3744, -1224);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007.\u0008.",
            ListLevelAlignment.LEFT, 4680, 4320, -1440);
        return list;
    }

    /// <summary>
    /// Creates the outline numbered list with different bullets for the levels.
    /// </summary>
    private static List createOutlineBullets(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initBulletListLevel(list, 0, WINGDINGS_FONT, WINGDING_DIAMONDS, 360, 360, -360);
        ListLevelInitializer.initBulletListLevel(list, 1, WINGDINGS_FONT, WINGDING_ARROW_HEAD, 720, 720, -360);
        ListLevelInitializer.initBulletListLevel(list, 2, WINGDINGS_FONT, WINGDING_SQUARE, 1080, 1080, -360);
        ListLevelInitializer.initBulletListLevel(list, 3, SYMBOL_FONT, SYMBOL_DISC, 1440, 1440, -360);
        ListLevelInitializer.initBulletListLevel(list, 4, SYMBOL_FONT, SYMBOL_DIAMOND, 1800, 1800, -360);
        ListLevelInitializer.initBulletListLevel(list, 5, WINGDINGS_FONT, WINGDING_ARROW_HEAD, 2160, 2160, -360);
        ListLevelInitializer.initBulletListLevel(list, 6, WINGDINGS_FONT, WINGDING_SQUARE, 2520, 2520, -360);
        ListLevelInitializer.initBulletListLevel(list, 7, SYMBOL_FONT, SYMBOL_DISC, 2880, 2880, -360);
        ListLevelInitializer.initBulletListLevel(list, 8, SYMBOL_FONT, SYMBOL_DIAMOND, 3240, 3240, -360);
        return list;
    }

    /// <summary>
    /// Creates the Article \ Section outline headings list.
    /// </summary>
    private static List createOutlineHeadingsArticleSection(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.UPPERCASE_ROMAN, "Article \u0000.",
            ListLevelAlignment.LEFT, 1800, 0, -0, StyleIndex.HEADING_1, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.LEADING_ZERO, "Section \u0000.\u0001",
            ListLevelAlignment.LEFT, 1800, 0, -0, StyleIndex.HEADING_2, ListTrailingCharacter.TAB);
        // This is the only level that is set to legal numbering.
        list.getListLevels().get(1).isLegal(true);

        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.LOWERCASE_LETTER, "(\u0002)",
            ListLevelAlignment.LEFT, 720, 720, -432, StyleIndex.HEADING_3, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.LOWERCASE_ROMAN, "(\u0003)",
            ListLevelAlignment.RIGHT, 864, 864, -144, StyleIndex.HEADING_4, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.ARABIC, "\u0004)",
            ListLevelAlignment.LEFT, 1008, 1008, -432, StyleIndex.HEADING_5, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.LOWERCASE_LETTER, "\u0005)",
            ListLevelAlignment.LEFT, 1152, 1152, -432, StyleIndex.HEADING_6, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.LOWERCASE_ROMAN, "\u0006)",
            ListLevelAlignment.RIGHT, 1296, 1296, -288, StyleIndex.HEADING_7, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "\u0007.",
            ListLevelAlignment.LEFT, 1440, 1440, -432, StyleIndex.HEADING_8, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "\u0008.",
            ListLevelAlignment.RIGHT, 1584, 1584, -144, StyleIndex.HEADING_9, ListTrailingCharacter.TAB);
        return list;
    }

    private static List createOutlineHeadingsLegal(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.ARABIC, 
            "\u0000",
            ListLevelAlignment.LEFT, 432, 432, -432, StyleIndex.HEADING_1, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.ARABIC, 
            "\u0000.\u0001",
            ListLevelAlignment.LEFT, 576, 576, -576, StyleIndex.HEADING_2, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002",
            ListLevelAlignment.LEFT, 720, 720, -720, StyleIndex.HEADING_3, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003",
            ListLevelAlignment.LEFT, 864, 864, -864, StyleIndex.HEADING_4, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004",
            ListLevelAlignment.LEFT, 1008, 1008, -1008, StyleIndex.HEADING_5, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005",
            ListLevelAlignment.LEFT, 1152, 1152, -1152, StyleIndex.HEADING_6, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006",
            ListLevelAlignment.LEFT, 1296, 1296, -1296, StyleIndex.HEADING_7, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007",
            ListLevelAlignment.LEFT, 1440, 1440, -1440, StyleIndex.HEADING_8, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.ARABIC, 
            "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007.\u0008",
            ListLevelAlignment.LEFT, 1584, 1584, -1584, StyleIndex.HEADING_9, ListTrailingCharacter.TAB);
        return list;
    }

    private static List createOutlineHeadingsNumbers(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.UPPERCASE_ROMAN, "\u0000.",
            ListLevelAlignment.LEFT, 360, 0, -0, StyleIndex.HEADING_1, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.UPPERCASE_LETTER, "\u0001.",
            ListLevelAlignment.LEFT, 1080, 720, -0, StyleIndex.HEADING_2, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.ARABIC, "\u0002.",
            ListLevelAlignment.LEFT, 1800, 1440, -0, StyleIndex.HEADING_3, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.LOWERCASE_LETTER, "\u0003)",
            ListLevelAlignment.LEFT, 2520, 2160, -0, StyleIndex.HEADING_4, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.ARABIC, "(\u0004)",
            ListLevelAlignment.LEFT, 3240, 2880, -0, StyleIndex.HEADING_5, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.LOWERCASE_LETTER, "(\u0005)",
            ListLevelAlignment.LEFT, 3960, 3600, -0, StyleIndex.HEADING_6, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.LOWERCASE_ROMAN, "(\u0006)",
            ListLevelAlignment.LEFT, 4680, 4320, -0, StyleIndex.HEADING_7, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "(\u0007)",
            ListLevelAlignment.LEFT, 5400, 5040, -0, StyleIndex.HEADING_8, ListTrailingCharacter.TAB);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "(\u0008)",
            ListLevelAlignment.LEFT, 6120, 5760, -0, StyleIndex.HEADING_9, ListTrailingCharacter.TAB);
        return list;
    }

    private static List createOutlineHeadingsChapter(ListCollection lists)
    {
        List list = lists.addEmpty(ListType.MULTI_LEVEL);
        ListLevelInitializer.initNumberListLevel(list, 0, NumberStyle.ARABIC, "Chapter \u0000",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_1, ListTrailingCharacter.SPACE);
        ListLevelInitializer.initNumberListLevel(list, 1, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_2, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 2, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_3, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 3, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_4, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 4, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_5, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 5, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_6, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 6, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_7, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 7, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_8, ListTrailingCharacter.NOTHING);
        ListLevelInitializer.initNumberListLevel(list, 8, NumberStyle.NONE, "",
            ListLevelAlignment.LEFT, 0, 0, -0, StyleIndex.HEADING_9, ListTrailingCharacter.NOTHING);
        return list;
    }

    /// <summary>
    /// Constants for standard list bullets and their fonts (also standard).
    /// </summary>
    static final String SYMBOL_FONT = "Symbol";

    static final char SYMBOL_DISC_CHAR = '\uf0b7';
    static final String SYMBOL_DISC = "\uf0b7"; // == SymbolDiscChar.ToString()

    static final char SYMBOL_DIAMOND_CHAR = '\uf0a8';
    static final String SYMBOL_DIAMOND = "\uf0a8"; // == SymbolDiamondChar.ToString()

    static final String COURIER_FONT = "Courier New";

    static final char COURIER_CIRCLE_CHAR = 'o';
    static final String COURIER_CIRCLE = "o"; // == CourierCircleChar.ToString()

    static final String WINGDINGS_FONT = "Wingdings";

    static final char WINGDING_SQUARE_CHAR = '\uf0a7';
    static final String WINGDING_SQUARE = "\uf0a7"; // == WingdingSquareChar.ToString()

    static final char WINGDING_DIAMONDS_CHAR = '\uf076';
    static final String WINGDING_DIAMONDS = "\uf076"; // == WingdingDiamondsChar.ToString()

    static final char WINGDING_ARROW_HEAD_CHAR = '\uf0d8';
    static final String WINGDING_ARROW_HEAD = "\uf0d8"; // == WingdingArrowHeadChar.ToString()

    static final char WINGDING_TICK_CHAR = '\uf0fc';
    static final String WINGDING_TICK = "\uf0fc"; // == WingdingTickChar.ToString()
}

