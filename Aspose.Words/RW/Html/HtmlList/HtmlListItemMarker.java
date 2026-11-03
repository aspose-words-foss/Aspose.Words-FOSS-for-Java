// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/10/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a list-item marker in a list. 
/// </summary>
class HtmlListItemMarker
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="listStyleType">The type of the list-item marker in a HTML list.</param>
    /// <param name="isBullet">Specifies whether the list-item marker is a bullet.</param>
    /// <param name="listTypeAttribute">HTML UL or OL type attribute.</param>
    /// <param name="listTemplate">Corresponding predefined list format available in Microsoft Word.</param>
    /// <param name="numberStyle">Corresponding number style for a list.</param>
    /// <param name="bulletFormat">Bullet format for the list level.</param>
    /// <param name="bulletFontName">Bullet font name for the list level.</param>
    private HtmlListItemMarker(
        String listStyleType,
        boolean isBullet,
        String listTypeAttribute,
        /*ListTemplate*/int listTemplate,
        /*NumberStyle*/int numberStyle,
        String bulletFormat,
        String bulletFontName)
    {
        mListStyleType = listStyleType;
        mIsBullet = isBullet;
        mListTypeAttribute = listTypeAttribute;
        mListTemplate = listTemplate;
        mNumberStyle = numberStyle;
        mBulletFormat = bulletFormat;
        mBulletFontName = bulletFontName;
    }

    /// <summary>
    /// Returns a list-item marker by a value of CSS list-style-type property.
    /// </summary>
    /// <param name="listStyleType">Value of CSS list-style-type property.</param>
    /// <returns>List-item marker, or null if a list style type is unknown.</returns>
    static HtmlListItemMarker getFromListStyleType(String listStyleType)
    {
        for (HtmlListItemMarker listTypeDef : G_REGISTERED_LIST_TYPE_DEFS)
        {
            if (StringUtil.equalsIgnoreCase(listTypeDef.getListStyleType(), listStyleType))
                return listTypeDef;
        }
        return null;
    }

    /// <summary>
    /// Returns a list-item marker for a list tag with no type specified.
    /// </summary>
    /// <param name="listTag">List tag (UL or OL).</param>
    /// <param name="listLevelNumber">List level number.</param>
    /// <returns>List-item marker, or null if a list tag is unknown.</returns>
    static HtmlListItemMarker getFromListTag(
        String listTag,
        int listLevelNumber)
    {
        switch (gStringSwitchMap.of(listTag))
        {
            case /*"ol"*/0:
                return G_DECIMAL;
            case /*"ul"*/1:
                return getBulletMarkerFromListLevel(listLevelNumber);
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    /// <summary>
    /// Returns a list-item marker for a specified list level.
    /// </summary>
    static HtmlListItemMarker getFromListLevel(ListLevel level)
    {
        HtmlListItemMarker listItemMarker = getBulletMarkerFromListLevel(level);
        if (listItemMarker != null)
            return listItemMarker;

        switch (level.getNumberStyle())
        {
            case NumberStyle.ARABIC:
            case NumberStyle.NUMBER:
            case NumberStyle.ORDINAL:
            case NumberStyle.ORDINAL_TEXT:
                return G_DECIMAL;
            case NumberStyle.LEADING_ZERO:
                return G_DECIMAL_LEADING_ZERO;
            case NumberStyle.LOWERCASE_LETTER:
                return G_LOWER_LATIN;
            case NumberStyle.LOWERCASE_ROMAN:
                return G_LOWER_ROMAN;
            case NumberStyle.UPPERCASE_LETTER:
                return G_UPPER_LATIN;
            case NumberStyle.UPPERCASE_ROMAN:
                return G_UPPER_ROMAN;
            default:
                return G_DECIMAL;
        }

        // These ones have no counterparts in the model:
        // lower-greek | armenian | georgian

        // These are the same as lower-latin and upper-latin
        // lower-alpha | upper-alpha
    }

    boolean equals(HtmlListItemMarker other)
    {
        if (msObject.referenceEquals(null, other))
            return false;

        if (msObject.referenceEquals(this, other))
            return true;

        return (isBullet() == other.isBullet()) &&
               (msString.equals(getListTypeAttribute(), other.getListTypeAttribute())) &&
               (getListTemplate() == other.getListTemplate()) &&
               (getNumberStyle() == other.getNumberStyle()) &&
               (msString.equals(getListStyleType(), other.getListStyleType())) &&
               StringUtil.equalsIgnoreCase(getBulletFormat(), other.getBulletFormat()) &&
               StringUtil.equalsIgnoreCase(getBulletFontName(), other.getBulletFontName());
    }

    /// <summary>
    /// Returns list-item bullet marker for specified list level number.
    /// </summary>
    private static HtmlListItemMarker getBulletMarkerFromListLevel(int listLevelNumber)
    {
        // Every three levels the bullets repeat so I normalize to [0..2] range.
        // In the future we'll hopefully provide support for all bullets
        // (either drawing them as images or anyhow else).
        switch (listLevelNumber % 3)
        {
            case 0:
                return G_DISK;
            case 1:
                return G_CIRCLE;
            case 2:
                return G_SQUARE;
            default:
                Debug.fail("Invalid value.");
                return null;
        }
    }

    /// <summary>
    /// Returns list-item bullet marker if a list level should be output with bullets, otherwise null.
    /// </summary>
    private static HtmlListItemMarker getBulletMarkerFromListLevel(ListLevel level)
    {
        // Check against standard HTML bullets first (#3325): disc, circle, square.
        // For some bullet formats we know how to output HTML.
        // We should have been already checked the possibility to represent it as HTML native list (font etc.).
        switch (gStringSwitchMap.of(level.getNumberFormat()))
        {
            case/*ListFactory.SYMBOL_DISC*/2:
                return G_DISK;
            case/*ListFactory.COURIER_CIRCLE*/3:
                return G_CIRCLE;
            case/*ListFactory.WINGDING_SQUARE*/4:
                return G_SQUARE;
            default:
                // There are no standard HTML counterparts for other number formats.
                break;
        }

        if ((level.getNumberStyle() == NumberStyle.BULLET) || (level.getNumberStyle() == NumberStyle.NONE))
        {
            // Standard HTML bullets are already processed.
            // This is applied to all other bullets.
            return getBulletMarkerFromListLevel(level.getLevelNumber());
        }

        return null;
    }

    /// <summary>
    /// Creates a list-item bullet marker.
    /// </summary>
    /// <param name="listStyleType">The type of the list-item marker in a HTML list.</param>
    /// <param name="listTypeAttribute">HTML UL or OL type attribute.</param>
    /// <param name="listTemplate">Corresponding predefined list format available in Microsoft Word.</param>
    /// <param name="bulletFormat">Bullet format for the list level.</param>
    /// <param name="bulletFontName">Bullet font name for the list level.</param>
    private static HtmlListItemMarker createBulletMarker(
        String listStyleType,
        String listTypeAttribute,
        /*ListTemplate*/int listTemplate,
        String bulletFormat,
        String bulletFontName)
    {
        HtmlListItemMarker listDef = new HtmlListItemMarker(
            listStyleType,
            true,
            listTypeAttribute,
            listTemplate,
            NumberStyle.BULLET,
            bulletFormat,
            bulletFontName);
        msArrayList.add(G_REGISTERED_LIST_TYPE_DEFS, listDef);
        return listDef;
    }

    /// <summary>
    /// Creates a list-item numbered marker.
    /// </summary>
    /// <param name="listStyleType">The type of the list-item marker in a HTML list.</param>
    /// <param name="listTypeAttribute">HTML UL or OL type attribute.</param>
    /// <param name="listTemplate">Corresponding predefined list format available in Microsoft Word.</param>
    /// <param name="numberStyle">Corresponding number style for a list.</param>
    private static HtmlListItemMarker createNumberedMarker(
        String listStyleType,
        String listTypeAttribute,
        /*ListTemplate*/int listTemplate,
        /*NumberStyle*/int numberStyle)
    {
        HtmlListItemMarker listDef = new HtmlListItemMarker(
            listStyleType,
            false,
            listTypeAttribute,
            listTemplate,
            numberStyle,
            "",
            "");
        msArrayList.add(G_REGISTERED_LIST_TYPE_DEFS, listDef);
        return listDef;
    }

    /// <summary>
    /// Specifies whether the list-item marker is a bullet.
    /// </summary>
    boolean isBullet() { return mIsBullet; };

    private boolean mIsBullet;

    /// <summary>
    /// Specifies the type of list-item marker in a HTML list.
    /// Corresponds to CSS 'list-style-type' property.
    /// </summary>
    String getListStyleType() { return mListStyleType; };

    private String mListStyleType;

    /// <summary>
    /// Specifies the kind of marker to use in the list.
    /// Corresponds to HTML UL or OL 'type' attribute.
    /// </summary>
    String getListTypeAttribute() { return mListTypeAttribute; };

    private String mListTypeAttribute;

    /// <summary>
    /// Predefined list format available in Microsoft Word.
    /// </summary>
    /*ListTemplate*/int getListTemplate() { return mListTemplate; };

    private /*ListTemplate*/int mListTemplate;

    /// <summary>
    /// Number style for the list.
    /// </summary>
    /*NumberStyle*/int getNumberStyle() { return mNumberStyle; };

    private /*NumberStyle*/int mNumberStyle;

    /// <summary>
    /// Bullet format for the list level.
    /// </summary>
    String getBulletFormat() { return mBulletFormat; };

    private String mBulletFormat;

    /// <summary>
    /// Bullet font name for the list level.
    /// </summary>
    String getBulletFontName() { return mBulletFontName; };

    private String mBulletFontName;

    /// <summary>
    /// Represents HTML list with circle marker.
    /// </summary>
    private static /*final*/ HtmlListItemMarker G_CIRCLE;
    /// <summary>
    /// Represents HTML list with filled circle marker.
    /// </summary>
    private static /*final*/ HtmlListItemMarker G_DISK;
    /// <summary>
    /// Represents HTML list with square marker.
    /// </summary>
    private static /*final*/ HtmlListItemMarker G_SQUARE;
    private static /*final*/ HtmlListItemMarker G_DECIMAL;
    private static /*final*/ HtmlListItemMarker G_DECIMAL_LEADING_ZERO;
    private static /*final*/ HtmlListItemMarker G_LOWER_LATIN;
    private static /*final*/ HtmlListItemMarker G_LOWER_ROMAN;
    private static /*final*/ HtmlListItemMarker G_UPPER_LATIN;
    private static /*final*/ HtmlListItemMarker G_UPPER_ROMAN;
    /// <summary>
    /// Array of list style types supported in HTML.
    /// </summary>
    private static /*final*/ ArrayList<HtmlListItemMarker> G_REGISTERED_LIST_TYPE_DEFS;

    static/* HtmlListItemMarker()*/
    {
        // Defines list style types supported in HTML.
        G_REGISTERED_LIST_TYPE_DEFS = new ArrayList<HtmlListItemMarker>();

        G_CIRCLE = createBulletMarker(
            "circle",
            "circle",
            ListTemplate.BULLET_CIRCLE,
            ListFactory.COURIER_CIRCLE,
            ListFactory.COURIER_FONT);
        G_DISK = createBulletMarker(
            "disc",
            "disc",
            ListTemplate.BULLET_DISK,
            ListFactory.SYMBOL_DISC,
            ListFactory.SYMBOL_FONT);
        G_SQUARE = createBulletMarker(
            "square",
            "square",
            ListTemplate.BULLET_SQUARE,
            ListFactory.WINGDING_SQUARE,
            ListFactory.WINGDINGS_FONT);

        G_DECIMAL = createNumberedMarker(
            "decimal",
            "1",
            ListTemplate.NUMBER_ARABIC_DOT,
            NumberStyle.ARABIC);
        G_DECIMAL_LEADING_ZERO = createNumberedMarker(
            "decimal-leading-zero",
            "1",
            ListTemplate.NUMBER_ARABIC_DOT,
            NumberStyle.LEADING_ZERO);

        G_LOWER_LATIN = createNumberedMarker(
            "lower-latin",
            "a",
            ListTemplate.NUMBER_LOWERCASE_LETTER_DOT,
            NumberStyle.LOWERCASE_LETTER);
        G_UPPER_LATIN = createNumberedMarker(
            "upper-latin",
            "A",
            ListTemplate.NUMBER_UPPERCASE_LETTER_DOT,
            NumberStyle.UPPERCASE_LETTER);

        G_LOWER_ROMAN = createNumberedMarker(
            "lower-roman",
            "i",
            ListTemplate.NUMBER_LOWERCASE_ROMAN_DOT,
            NumberStyle.LOWERCASE_ROMAN);
        G_UPPER_ROMAN = createNumberedMarker(
            "upper-roman",
            "I",
            ListTemplate.NUMBER_UPPERCASE_ROMAN_DOT,
            NumberStyle.UPPERCASE_ROMAN);

        createNumberedMarker(
            "lower-alpha",
            "a",
            ListTemplate.NUMBER_LOWERCASE_LETTER_DOT,
            NumberStyle.LOWERCASE_LETTER);
        createNumberedMarker(
            "upper-alpha",
            "A",
            ListTemplate.NUMBER_UPPERCASE_LETTER_DOT,
            NumberStyle.UPPERCASE_LETTER);

        // {lower|upper}-greek isn't supported in AW.
        createNumberedMarker(
            "lower-greek",
            "",
            ListTemplate.NUMBER_LOWERCASE_LETTER_DOT,
            NumberStyle.LOWERCASE_LETTER);
        createNumberedMarker(
            "upper-greek",
            "",
            ListTemplate.NUMBER_UPPERCASE_LETTER_DOT,
            NumberStyle.UPPERCASE_LETTER);

        // WORDSNET-26977 Add support for "arabic-indic" list style.
        createNumberedMarker(
            "arabic-indic",
            "",
            ListTemplate.NUMBER_ARABIC_DOT,
            NumberStyle.ARABIC);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ol",
		"ul",
		"\uf0b7",
		"o",
		"\uf0a7"
	);

}

