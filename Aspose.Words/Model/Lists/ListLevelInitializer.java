// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides utility methods to initialize <see cref="ListLevel"/> objects used by list factories.
/// </summary>
/// <dev>
/// This class is a product of <see cref="ListFactory"/> class refactoring. All of the source code 
/// was left untouched while moving.
/// </dev>
/*static*/ class ListLevelInitializer
{
	/* Simulation of static class by using private constructor */
	private ListLevelInitializer()
	{}

    /// <summary>
    /// Fills a list level of a bulleted list.
    /// </summary>
    static void initBulletListLevel(
        List list,
        int levelNumber,
        String fontName,
        String bullet,
        int tabPosition,
        int leftIndent,
        int firstLineIndent)
    {
        initBulletListLevel(list.getListLevels().get(levelNumber), levelNumber, fontName, bullet,
            tabPosition, leftIndent, firstLineIndent);
    }

    /// <summary>
    /// Fills a list level of a bulleted list.
    /// </summary>
    static void initBulletListLevel(
        ListLevel level,
        int levelNumber,
        String fontName,
        String bullet,
        int tabPosition,
        int leftIndent,
        int firstLineIndent)
    {
        //Set list level properties
        level.setStartAt(1);
        level.setNumberStyle(NumberStyle.BULLET);
        level.setNumberFormat(bullet);
        level.setAlignment(ListLevelAlignment.LEFT);
        level.setTrailingCharacter(ListTrailingCharacter.TAB);
        level.setLegacySpace(-firstLineIndent);
        level.setLegacyPrev(true);
        level.setParaStyleIstd(StyleIndex.NIL);
        level.setRestartAfterLevel(levelNumber - 1);

        //Set list level paragraph properties
        level.getParaPr().setLeftIndent(leftIndent);
        level.getParaPr().setFirstLineIndent(firstLineIndent);

        TabStopCollection tabStops = new TabStopCollection();
        tabStops.hasTolerances(true);    //This is what MS Word does.
        level.getParaPr().setTabStops(tabStops);

        TabStop tabStop = new TabStop(tabPosition, TabAlignment.LIST, TabLeader.NONE);
        tabStops.add(tabStop);

        //Set list level font properties
        level.getRunPr().setSymbolFonts(fontName);
    }

    /// <summary>
    /// Fills a list level of a numbered list.
    /// </summary>
    static void initNumberListLevel(
        List list,
        int levelNumber,
        /*NumberStyle*/int numberStyle,
        String numberFormat,
        /*ListLevelAlignment*/int alignment,
        int tabPosition,
        int leftIndent,
        int firstLineIndent)
    {
        initNumberListLevel(list, levelNumber, numberStyle, numberFormat,
            alignment, tabPosition, leftIndent, firstLineIndent, StyleIndex.NIL, ListTrailingCharacter.TAB);
    }

    /// <summary>
    /// Fills a list level of a numbered list.
    /// </summary>
    static void initNumberListLevel(
        List list,
        int levelNumber,
        /*NumberStyle*/int numberStyle,
        String numberFormat,
        /*ListLevelAlignment*/int alignment,
        int tabPosition,
        int leftIndent,
        int firstLineIndent,
        int linkedParagraphStyleIstd,
        /*ListTrailingCharacter*/int trailingCharacter)
    {
        initNumberListLevel(list.getListLevels().get(levelNumber), levelNumber, numberStyle, numberFormat,
            alignment, tabPosition, leftIndent, firstLineIndent, linkedParagraphStyleIstd, trailingCharacter);
    }

    /// <summary>
    /// Fills a list level of a numbered list.
    /// </summary>
    static void initNumberListLevel(
        ListLevel level,
        int levelNumber,
        /*NumberStyle*/int numberStyle,
        String numberFormat,
        /*ListLevelAlignment*/int alignment,
        int tabPosition,
        int leftIndent,
        int firstLineIndent,
        int linkedParagraphStyleIstd,
        /*ListTrailingCharacter*/int trailingCharacter)
    {
        initNumberListLevel(level, levelNumber, 1, numberStyle, numberFormat,
            alignment, tabPosition, leftIndent, firstLineIndent, linkedParagraphStyleIstd, trailingCharacter);
    }

    /// <summary>
    /// Fills a list level of a numbered list.
    /// </summary>
    static void initNumberListLevel(
        ListLevel level,
        int levelNumber,
        int startAt,
        /*NumberStyle*/int numberStyle,
        String numberFormat,
        /*ListLevelAlignment*/int alignment,
        int tabPosition,
        int leftIndent,
        int firstLineIndent,
        int linkedParagraphStyleIstd,
        /*ListTrailingCharacter*/int trailingCharacter)
    {
        level.setStartAt(startAt);
        level.setNumberStyle(numberStyle);
        level.setNumberFormat(numberFormat);
        level.setAlignment(alignment);
        level.setTrailingCharacter(trailingCharacter);
        level.setLegacySpace(-firstLineIndent);
        level.setLegacyPrev(true);
        level.setParaStyleIstd(linkedParagraphStyleIstd);
        level.setRestartAfterLevel(levelNumber - 1);

        //Set list level paragraph properties
        level.getParaPr().setLeftIndent(leftIndent);
        level.getParaPr().setFirstLineIndent(firstLineIndent);

        TabStopCollection tabStops = new TabStopCollection();
        tabStops.hasTolerances(true);    //This is what MS Word does.
        level.getParaPr().setTabStops(tabStops);

        if (trailingCharacter == ListTrailingCharacter.TAB)
        {
            TabStop tabStop = new TabStop(tabPosition, TabAlignment.LIST, TabLeader.NONE);
            tabStops.add(tabStop);
        }
    }

    /// <summary>
    /// Fills a list level with None number style.
    /// </summary>
    static void initNoneListLevel(ListLevel level, int levelNumber)
    {
        level.setStartAt(0);
        level.setNumberStyle(NumberStyle.NONE);
        level.setNumberFormat("");
        level.setAlignment(ListLevelAlignment.LEFT);
        level.setTrailingCharacter(ListTrailingCharacter.TAB);

        level.isLegal(false);
        level.setLegacySpace(0);
        level.setLegacyIndent(0);
        level.setLegacyPrev(false);
        level.IsTentative = false;
        level.setLegacyPrevSpace(false);
        level.setRestartAfterLevel(levelNumber - 1);

        level.getRunPr().clear();

        level.getParaPr().clear();

        TabStopCollection tabStops = new TabStopCollection();
        level.getParaPr().setTabStops(tabStops);

        TabStop tabStop = new TabStop(360, TabAlignment.LIST, TabLeader.NONE);
        tabStops.add(tabStop);
    }
}

