// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts miscellaneous enumerated types between enum and WML string.
/// </summary>
class WmlEnum
{
    static /*FontFamily*/int wmlToFontFamily(String value)
    {
        return (/*FontFamily*/int)G_FONT_FAMILY_MAP.getValue(value, (int)FontFamily.AUTO);
    }

    static String fontFamilyToWml(/*FontFamily*/int value)
    {
        return G_FONT_FAMILY_MAP.getValue((int)value, "");
    }

    static /*NumberStyle*/int wmlToNumberStyle(String value)
    {
        return (/*NumberStyle*/int)G_NUMBER_STYLE_MAP.getValue(value, (int)NumberStyle.ARABIC);
    }

    static String numberStyleToWml(/*NumberStyle*/int value)
    {
        return G_NUMBER_STYLE_MAP.getValue((int)value, "");
    }

    static /*LineStyle*/int wmlToLineStyle(String value)
    {
        return (/*LineStyle*/int)G_LINE_STYLE_MAP.getValue(value, (int)LineStyle.NONE);
    }

    static String lineStyleToWml(/*LineStyle*/int value)
    {
        switch (value)
        {
                // MS Word converts this line style to 'none' in my test.
                // I still think we should convert it to 'single'.
            case LineStyle.HAIRLINE: return "single";
            default:
                return G_LINE_STYLE_MAP.getValue((int)value, "none");
        }
    }

    static /*StyleType*/int wmlToStyleType(String value)
    {
        return (/*StyleType*/int)G_STYLE_TYPE_MAP.getValue(value, (int)StyleType.PARAGRAPH);
    }

    static String styleTypeToWml(/*StyleType*/int value)
    {
        return G_STYLE_TYPE_MAP.getValue((int)value, "paragraph");
    }

    static /*FootnoteSeparatorType*/int wmlToFootnoteSeparatorType(String value, boolean isEndnote)
    {
        StringToIntBidirectionalMap map = isEndnote ? G_ENDNOTE_SEPARATOR_TYPE_MAP : G_FOOTNOTE_SEPARATOR_TYPE_MAP;
        return (/*FootnoteSeparatorType*/int)map.getValue(value, -1);
    }

    static String footnoteSeparatorTypeToWml(/*FootnoteSeparatorType*/int value, boolean isEndnote)
    {
        StringToIntBidirectionalMap map = isEndnote ? G_ENDNOTE_SEPARATOR_TYPE_MAP : G_FOOTNOTE_SEPARATOR_TYPE_MAP;
        return map.getValue((int)value, "");
    }

    private static /*final*/ StringToIntBidirectionalMap G_FONT_FAMILY_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_NUMBER_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LINE_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_STYLE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FOOTNOTE_SEPARATOR_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ENDNOTE_SEPARATOR_TYPE_MAP = new StringToIntBidirectionalMap();

    static/* WmlEnum()*/
    {
        // It seems that all border styles that are available via Borders and Shading menu in MS Word 2003 are here.
        G_LINE_STYLE_MAP.addEntry("none", (int)LineStyle.NONE);
        G_LINE_STYLE_MAP.addEntry("single", (int)LineStyle.SINGLE);
        G_LINE_STYLE_MAP.addEntry("thick", (int)LineStyle.THICK);
        G_LINE_STYLE_MAP.addEntry("double", (int)LineStyle.DOUBLE);
        G_LINE_STYLE_MAP.addEntry("dotted", (int)LineStyle.DOT);
        G_LINE_STYLE_MAP.addEntry("dashed", (int)LineStyle.DASH_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("dot-dash", (int)LineStyle.DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("dot-dot-dash", (int)LineStyle.DOT_DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("triple", (int)LineStyle.TRIPLE);
        G_LINE_STYLE_MAP.addEntry("thin-thick-small-gap", (int)LineStyle.THIN_THICK_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thick-thin-small-gap", (int)LineStyle.THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thin-thick-thin-small-gap", (int)LineStyle.THIN_THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thin-thick-medium-gap", (int)LineStyle.THIN_THICK_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thick-thin-medium-gap", (int)LineStyle.THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thin-thick-thin-medium-gap", (int)LineStyle.THIN_THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thin-thick-large-gap", (int)LineStyle.THIN_THICK_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thick-thin-large-gap", (int)LineStyle.THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thin-thick-thin-large-gap", (int)LineStyle.THIN_THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("wave", (int)LineStyle.WAVE);
        G_LINE_STYLE_MAP.addEntry("double-wave", (int)LineStyle.DOUBLE_WAVE);
        G_LINE_STYLE_MAP.addEntry("dash-small-gap", (int)LineStyle.DASH_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("dash-dot-stroked", (int)LineStyle.DASH_DOT_STROKER);
        G_LINE_STYLE_MAP.addEntry("three-d-emboss", (int)LineStyle.EMBOSS_3_D);
        G_LINE_STYLE_MAP.addEntry("three-d-engrave", (int)LineStyle.ENGRAVE_3_D);
        G_LINE_STYLE_MAP.addEntry("outset", (int)LineStyle.OUTSET);
        G_LINE_STYLE_MAP.addEntry("inset", (int)LineStyle.INSET);
        // page border art
        G_LINE_STYLE_MAP.addEntry("apples", (int)PageBorderArt.APPLES);
        G_LINE_STYLE_MAP.addEntry("arched-scallops", (int)PageBorderArt.ARCHED_SCALLOPS);
        G_LINE_STYLE_MAP.addEntry("baby-pacifier", (int)PageBorderArt.BABY_PACIFIER);
        G_LINE_STYLE_MAP.addEntry("baby-rattle", (int)PageBorderArt.BABY_RATTLE);
        G_LINE_STYLE_MAP.addEntry("balloons-3-colors", (int)PageBorderArt.BALLOONS_3_COLORS);
        G_LINE_STYLE_MAP.addEntry("balloons-hot-air", (int)PageBorderArt.BALLOONS_HOT_AIR);
        G_LINE_STYLE_MAP.addEntry("basic-black-dashes", (int)PageBorderArt.BASIC_BLACK_DASHES);
        G_LINE_STYLE_MAP.addEntry("basic-black-dots", (int)PageBorderArt.BASIC_BLACK_DOTS);
        G_LINE_STYLE_MAP.addEntry("basic-black-squares", (int)PageBorderArt.BASIC_BLACK_SQUARES);
        G_LINE_STYLE_MAP.addEntry("basic-thin-lines", (int)PageBorderArt.BASIC_THIN_LINES);
        G_LINE_STYLE_MAP.addEntry("basic-white-dashes", (int)PageBorderArt.BASIC_WHITE_DASHES);
        G_LINE_STYLE_MAP.addEntry("basic-white-dots", (int)PageBorderArt.BASIC_WHITE_DOTS);
        G_LINE_STYLE_MAP.addEntry("basic-white-squares", (int)PageBorderArt.BASIC_WHITE_SQUARES);
        G_LINE_STYLE_MAP.addEntry("basic-wide-inline", (int)PageBorderArt.BASIC_WIDE_INLINE);
        G_LINE_STYLE_MAP.addEntry("basic-wide-midline", (int)PageBorderArt.BASIC_WIDE_MIDLINE);
        G_LINE_STYLE_MAP.addEntry("basic-wide-outline", (int)PageBorderArt.BASIC_WIDE_OUTLINE);
        G_LINE_STYLE_MAP.addEntry("bats", (int)PageBorderArt.BATS);
        G_LINE_STYLE_MAP.addEntry("birds", (int)PageBorderArt.BIRDS);
        G_LINE_STYLE_MAP.addEntry("birds-flight", (int)PageBorderArt.BIRDS_FLIGHT);
        G_LINE_STYLE_MAP.addEntry("cabins", (int)PageBorderArt.CABINS);
        G_LINE_STYLE_MAP.addEntry("cake-slice", (int)PageBorderArt.CAKE_SLICE);
        G_LINE_STYLE_MAP.addEntry("candy-corn", (int)PageBorderArt.CANDY_CORN);
        G_LINE_STYLE_MAP.addEntry("celtic-knotwork", (int)PageBorderArt.CELTIC_KNOTWORK);
        G_LINE_STYLE_MAP.addEntry("certificate-banner", (int)PageBorderArt.CERTIFICATE_BANNER);
        G_LINE_STYLE_MAP.addEntry("chain-link", (int)PageBorderArt.CHAIN_LINK);
        G_LINE_STYLE_MAP.addEntry("champagne-bottle", (int)PageBorderArt.CHAMPAGNE_BOTTLE);
        G_LINE_STYLE_MAP.addEntry("checked-bar-black", (int)PageBorderArt.CHECKED_BAR_BLACK);
        G_LINE_STYLE_MAP.addEntry("checked-bar-color", (int)PageBorderArt.CHECKED_BAR_COLOR);
        G_LINE_STYLE_MAP.addEntry("checkered", (int)PageBorderArt.CHECKERED);
        G_LINE_STYLE_MAP.addEntry("christmas-tree", (int)PageBorderArt.CHRISTMAS_TREE);
        G_LINE_STYLE_MAP.addEntry("circles-lines", (int)PageBorderArt.CIRCLES_LINES);
        G_LINE_STYLE_MAP.addEntry("circles-rectangles", (int)PageBorderArt.CIRCLES_RECTANGLES);
        G_LINE_STYLE_MAP.addEntry("classical-wave", (int)PageBorderArt.CLASSICAL_WAVE);
        G_LINE_STYLE_MAP.addEntry("clocks", (int)PageBorderArt.CLOCKS);
        G_LINE_STYLE_MAP.addEntry("compass", (int)PageBorderArt.COMPASS);
        G_LINE_STYLE_MAP.addEntry("confetti", (int)PageBorderArt.CONFETTI);
        G_LINE_STYLE_MAP.addEntry("confetti-grays", (int)PageBorderArt.CONFETTI_GRAYS);
        G_LINE_STYLE_MAP.addEntry("confetti-outline", (int)PageBorderArt.CONFETTI_OUTLINE);
        G_LINE_STYLE_MAP.addEntry("confetti-streamers", (int)PageBorderArt.CONFETTI_STREAMERS);
        G_LINE_STYLE_MAP.addEntry("confetti-white", (int)PageBorderArt.CONFETTI_WHITE);
        G_LINE_STYLE_MAP.addEntry("corner-triangles", (int)PageBorderArt.CORNER_TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("coupon-cutout-dashes", (int)PageBorderArt.COUPON_CUTOUT_DASHES);
        G_LINE_STYLE_MAP.addEntry("coupon-cutout-dots", (int)PageBorderArt.COUPON_CUTOUT_DOTS);
        G_LINE_STYLE_MAP.addEntry("crazy-maze", (int)PageBorderArt.CRAZY_MAZE);
        G_LINE_STYLE_MAP.addEntry("creatures-butterfly", (int)PageBorderArt.CREATURES_BUTTERFLY);
        G_LINE_STYLE_MAP.addEntry("creatures-fish", (int)PageBorderArt.CREATURES_FISH);
        G_LINE_STYLE_MAP.addEntry("creatures-insects", (int)PageBorderArt.CREATURES_INSECTS);
        G_LINE_STYLE_MAP.addEntry("creatures-lady-bug", (int)PageBorderArt.CREATURES_LADY_BUG);
        G_LINE_STYLE_MAP.addEntry("cross-stitch", (int)PageBorderArt.CROSS_STITCH);
        G_LINE_STYLE_MAP.addEntry("cup", (int)PageBorderArt.CUP);
        G_LINE_STYLE_MAP.addEntry("deco-arch", (int)PageBorderArt.DECO_ARCH);
        G_LINE_STYLE_MAP.addEntry("deco-arch-color", (int)PageBorderArt.DECO_ARCH_COLOR);
        G_LINE_STYLE_MAP.addEntry("deco-blocks", (int)PageBorderArt.DECO_BLOCKS);
        G_LINE_STYLE_MAP.addEntry("diamonds-gray", (int)PageBorderArt.DIAMONDS_GRAY);
        G_LINE_STYLE_MAP.addEntry("double-d", (int)PageBorderArt.DOUBLE_D);
        G_LINE_STYLE_MAP.addEntry("double-diamonds", (int)PageBorderArt.DOUBLE_DIAMONDS);
        G_LINE_STYLE_MAP.addEntry("earth-1", (int)PageBorderArt.EARTH_1);
        G_LINE_STYLE_MAP.addEntry("earth-2", (int)PageBorderArt.EARTH_2);
        G_LINE_STYLE_MAP.addEntry("eclipsing-squares-1", (int)PageBorderArt.ECLIPSING_SQUARES_1);
        G_LINE_STYLE_MAP.addEntry("eclipsing-squares-2", (int)PageBorderArt.ECLIPSING_SQUARES_2);
        G_LINE_STYLE_MAP.addEntry("eggs-black", (int)PageBorderArt.EGGS_BLACK);
        G_LINE_STYLE_MAP.addEntry("fans", (int)PageBorderArt.FANS);
        G_LINE_STYLE_MAP.addEntry("film", (int)PageBorderArt.FILM);
        G_LINE_STYLE_MAP.addEntry("firecrackers", (int)PageBorderArt.FIRECRACKERS);
        G_LINE_STYLE_MAP.addEntry("flowers-block-print", (int)PageBorderArt.FLOWERS_BLOCK_PRINT);
        G_LINE_STYLE_MAP.addEntry("flowers-daisies", (int)PageBorderArt.FLOWERS_DAISIES);
        G_LINE_STYLE_MAP.addEntry("flowers-modern-1", (int)PageBorderArt.FLOWERS_MODERN_1);
        G_LINE_STYLE_MAP.addEntry("flowers-modern-2", (int)PageBorderArt.FLOWERS_MODERN_2);
        G_LINE_STYLE_MAP.addEntry("flowers-pansy", (int)PageBorderArt.FLOWERS_PANSY);
        G_LINE_STYLE_MAP.addEntry("flowers-red-rose", (int)PageBorderArt.FLOWERS_RED_ROSE);
        G_LINE_STYLE_MAP.addEntry("flowers-roses", (int)PageBorderArt.FLOWERS_ROSES);
        G_LINE_STYLE_MAP.addEntry("flowers-teacup", (int)PageBorderArt.FLOWERS_TEACUP);
        G_LINE_STYLE_MAP.addEntry("flowers-tiny", (int)PageBorderArt.FLOWERS_TINY);
        G_LINE_STYLE_MAP.addEntry("gems", (int)PageBorderArt.GEMS);
        G_LINE_STYLE_MAP.addEntry("gingerbread-man", (int)PageBorderArt.GINGERBREAD_MAN);
        G_LINE_STYLE_MAP.addEntry("gradient", (int)PageBorderArt.GRADIENT);
        G_LINE_STYLE_MAP.addEntry("handmade-1", (int)PageBorderArt.HANDMADE_1);
        G_LINE_STYLE_MAP.addEntry("handmade-2", (int)PageBorderArt.HANDMADE_2);
        G_LINE_STYLE_MAP.addEntry("heart-balloon", (int)PageBorderArt.HEART_BALLOON);
        G_LINE_STYLE_MAP.addEntry("heart-gray", (int)PageBorderArt.HEART_GRAY);
        G_LINE_STYLE_MAP.addEntry("hearts", (int)PageBorderArt.HEARTS);
        G_LINE_STYLE_MAP.addEntry("heebie-jeebies", (int)PageBorderArt.HEEBIE_JEEBIES);
        G_LINE_STYLE_MAP.addEntry("holly", (int)PageBorderArt.HOLLY);
        G_LINE_STYLE_MAP.addEntry("house-funky", (int)PageBorderArt.HOUSE_FUNKY);
        G_LINE_STYLE_MAP.addEntry("hypnotic", (int)PageBorderArt.HYPNOTIC);
        G_LINE_STYLE_MAP.addEntry("ice-cream-cones", (int)PageBorderArt.ICE_CREAM_CONES);
        G_LINE_STYLE_MAP.addEntry("light-bulb", (int)PageBorderArt.LIGHT_BULB);
        G_LINE_STYLE_MAP.addEntry("lightning-1", (int)PageBorderArt.LIGHTNING_1);
        G_LINE_STYLE_MAP.addEntry("lightning-2", (int)PageBorderArt.LIGHTNING_2);
        G_LINE_STYLE_MAP.addEntry("map-pins", (int)PageBorderArt.MAP_PINS);
        G_LINE_STYLE_MAP.addEntry("maple-leaf", (int)PageBorderArt.MAPLE_LEAF);
        G_LINE_STYLE_MAP.addEntry("maple-muffins", (int)PageBorderArt.MAPLE_MUFFINS);
        G_LINE_STYLE_MAP.addEntry("marquee", (int)PageBorderArt.MARQUEE);
        G_LINE_STYLE_MAP.addEntry("marquee-toothed", (int)PageBorderArt.MARQUEE_TOOTHED);
        G_LINE_STYLE_MAP.addEntry("moons", (int)PageBorderArt.MOONS);
        G_LINE_STYLE_MAP.addEntry("mosaic", (int)PageBorderArt.MOSAIC);
        G_LINE_STYLE_MAP.addEntry("music-notes", (int)PageBorderArt.MUSIC_NOTES);
        G_LINE_STYLE_MAP.addEntry("northwest", (int)PageBorderArt.NORTHWEST);
        G_LINE_STYLE_MAP.addEntry("ovals", (int)PageBorderArt.OVALS);
        G_LINE_STYLE_MAP.addEntry("packages", (int)PageBorderArt.PACKAGES);
        G_LINE_STYLE_MAP.addEntry("palms-black", (int)PageBorderArt.PALMS_BLACK);
        G_LINE_STYLE_MAP.addEntry("palms-color", (int)PageBorderArt.PALMS_COLOR);
        G_LINE_STYLE_MAP.addEntry("paper-clips", (int)PageBorderArt.PAPER_CLIPS);
        G_LINE_STYLE_MAP.addEntry("papyrus", (int)PageBorderArt.PAPYRUS);
        G_LINE_STYLE_MAP.addEntry("party-favor", (int)PageBorderArt.PARTY_FAVOR);
        G_LINE_STYLE_MAP.addEntry("party-glass", (int)PageBorderArt.PARTY_GLASS);
        G_LINE_STYLE_MAP.addEntry("pencils", (int)PageBorderArt.PENCILS);
        G_LINE_STYLE_MAP.addEntry("people", (int)PageBorderArt.PEOPLE);
        G_LINE_STYLE_MAP.addEntry("people-waving", (int)PageBorderArt.PEOPLE_WAVING);
        G_LINE_STYLE_MAP.addEntry("people-hats", (int)PageBorderArt.PEOPLE_HATS);
        G_LINE_STYLE_MAP.addEntry("poinsettias", (int)PageBorderArt.POINSETTIAS);
        G_LINE_STYLE_MAP.addEntry("postage-stamp", (int)PageBorderArt.POSTAGE_STAMP);
        G_LINE_STYLE_MAP.addEntry("pumpkin-1", (int)PageBorderArt.PUMPKIN_1);
        G_LINE_STYLE_MAP.addEntry("push-pin-note-1", (int)PageBorderArt.PUSH_PIN_NOTE_1);
        G_LINE_STYLE_MAP.addEntry("push-pin-note-2", (int)PageBorderArt.PUSH_PIN_NOTE_2);
        G_LINE_STYLE_MAP.addEntry("pyramids", (int)PageBorderArt.PYRAMIDS);
        G_LINE_STYLE_MAP.addEntry("pyramids-above", (int)PageBorderArt.PYRAMIDS_ABOVE);
        G_LINE_STYLE_MAP.addEntry("quadrants", (int)PageBorderArt.QUADRANTS);
        G_LINE_STYLE_MAP.addEntry("rings", (int)PageBorderArt.RINGS);
        G_LINE_STYLE_MAP.addEntry("safari", (int)PageBorderArt.SAFARI);
        G_LINE_STYLE_MAP.addEntry("sawtooth", (int)PageBorderArt.SAWTOOTH);
        G_LINE_STYLE_MAP.addEntry("sawtooth-gray", (int)PageBorderArt.SAWTOOTH_GRAY);
        G_LINE_STYLE_MAP.addEntry("scared-cat", (int)PageBorderArt.SCARED_CAT);
        G_LINE_STYLE_MAP.addEntry("seattle", (int)PageBorderArt.SEATTLE);
        G_LINE_STYLE_MAP.addEntry("shadowed-squares", (int)PageBorderArt.SHADOWED_SQUARES);
        G_LINE_STYLE_MAP.addEntry("sharks-teeth", (int)PageBorderArt.SHARKS_TEETH);
        G_LINE_STYLE_MAP.addEntry("shorebird-tracks", (int)PageBorderArt.SHOREBIRD_TRACKS);
        G_LINE_STYLE_MAP.addEntry("skyrocket", (int)PageBorderArt.SKYROCKET);
        G_LINE_STYLE_MAP.addEntry("snowflake-fancy", (int)PageBorderArt.SNOWFLAKE_FANCY);
        G_LINE_STYLE_MAP.addEntry("snowflakes", (int)PageBorderArt.SNOWFLAKES);
        G_LINE_STYLE_MAP.addEntry("sombrero", (int)PageBorderArt.SOMBRERO);
        G_LINE_STYLE_MAP.addEntry("southwest", (int)PageBorderArt.SOUTHWEST);
        G_LINE_STYLE_MAP.addEntry("stars", (int)PageBorderArt.STARS);
        G_LINE_STYLE_MAP.addEntry("stars-top", (int)PageBorderArt.STARS_TOP);
        G_LINE_STYLE_MAP.addEntry("stars-3d", (int)PageBorderArt.STARS_3_D);
        G_LINE_STYLE_MAP.addEntry("stars-black", (int)PageBorderArt.STARS_BLACK);
        G_LINE_STYLE_MAP.addEntry("stars-shadowed", (int)PageBorderArt.STARS_SHADOWED);
        G_LINE_STYLE_MAP.addEntry("sun", (int)PageBorderArt.SUN);
        G_LINE_STYLE_MAP.addEntry("swirligig", (int)PageBorderArt.SWIRLIGIG);
        G_LINE_STYLE_MAP.addEntry("torn-paper", (int)PageBorderArt.TORN_PAPER);
        G_LINE_STYLE_MAP.addEntry("torn-paper-black", (int)PageBorderArt.TORN_PAPER_BLACK);
        G_LINE_STYLE_MAP.addEntry("trees", (int)PageBorderArt.TREES);
        G_LINE_STYLE_MAP.addEntry("triangle-party", (int)PageBorderArt.TRIANGLE_PARTY);
        G_LINE_STYLE_MAP.addEntry("triangles", (int)PageBorderArt.TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("tribal-1", (int)PageBorderArt.TRIBAL_1);
        G_LINE_STYLE_MAP.addEntry("tribal-2", (int)PageBorderArt.TRIBAL_2);
        G_LINE_STYLE_MAP.addEntry("tribal-3", (int)PageBorderArt.TRIBAL_3);
        G_LINE_STYLE_MAP.addEntry("tribal-4", (int)PageBorderArt.TRIBAL_4);
        G_LINE_STYLE_MAP.addEntry("tribal-5", (int)PageBorderArt.TRIBAL_5);
        G_LINE_STYLE_MAP.addEntry("tribal-6", (int)PageBorderArt.TRIBAL_6);
        G_LINE_STYLE_MAP.addEntry("twisted-lines-1", (int)PageBorderArt.TWISTED_LINES_1);
        G_LINE_STYLE_MAP.addEntry("twisted-lines-2", (int)PageBorderArt.TWISTED_LINES_2);
        G_LINE_STYLE_MAP.addEntry("vine", (int)PageBorderArt.VINE);
        G_LINE_STYLE_MAP.addEntry("waveline", (int)PageBorderArt.WAVELINE);
        G_LINE_STYLE_MAP.addEntry("weaving-angles", (int)PageBorderArt.WEAVING_ANGLES);
        G_LINE_STYLE_MAP.addEntry("weaving-braid", (int)PageBorderArt.WEAVING_BRAID);
        G_LINE_STYLE_MAP.addEntry("weaving-ribbon", (int)PageBorderArt.WEAVING_RIBBON);
        G_LINE_STYLE_MAP.addEntry("weaving-strips", (int)PageBorderArt.WEAVING_STRIPS);
        G_LINE_STYLE_MAP.addEntry("white-flowers", (int)PageBorderArt.WHITE_FLOWERS);
        G_LINE_STYLE_MAP.addEntry("woodwork", (int)PageBorderArt.WOODWORK);
        G_LINE_STYLE_MAP.addEntry("x-illusions", (int)PageBorderArt.X_ILLUSIONS);
        G_LINE_STYLE_MAP.addEntry("zany-triangles", (int)PageBorderArt.ZANY_TRIANGLES);
        G_LINE_STYLE_MAP.addEntry("zig-zag", (int)PageBorderArt.ZIG_ZAG);
        G_LINE_STYLE_MAP.addEntry("zig-zag-stitch", (int)PageBorderArt.ZIG_ZAG_STITCH);

        G_NUMBER_STYLE_MAP.addEntry("decimal", (int)NumberStyle.ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("upper-roman", (int)NumberStyle.UPPERCASE_ROMAN);
        G_NUMBER_STYLE_MAP.addEntry("lower-roman", (int)NumberStyle.LOWERCASE_ROMAN);
        G_NUMBER_STYLE_MAP.addEntry("upper-letter", (int)NumberStyle.UPPERCASE_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("lower-letter", (int)NumberStyle.LOWERCASE_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("ordinal", (int)NumberStyle.ORDINAL);
        G_NUMBER_STYLE_MAP.addEntry("cardinal-text", (int)NumberStyle.NUMBER);
        G_NUMBER_STYLE_MAP.addEntry("ordinal-text", (int)NumberStyle.ORDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("hex", (int)NumberStyle.HEX);
        G_NUMBER_STYLE_MAP.addEntry("chicago", (int)NumberStyle.CHICAGO_MANUAL);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-digital", (int)NumberStyle.KANJI);
        G_NUMBER_STYLE_MAP.addEntry("japanese-counting", (int)NumberStyle.KANJI_DIGIT);
        G_NUMBER_STYLE_MAP.addEntry("aiueo", (int)NumberStyle.AIUEO_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("iroha", (int)NumberStyle.IROHA_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("decimal-full-width", (int)NumberStyle.ARABIC_FULL_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("decimal-half-width", (int)NumberStyle.ARABIC_HALF_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("japanese-legal", (int)NumberStyle.KANJI_TRADITIONAL);
        G_NUMBER_STYLE_MAP.addEntry("japanese-digital-ten-thousand", (int)NumberStyle.KANJI_TRADITIONAL_2);
        G_NUMBER_STYLE_MAP.addEntry("decimal-enclosed-circle", (int)NumberStyle.NUMBER_IN_CIRCLE);
        G_NUMBER_STYLE_MAP.addEntry("decimal-full-width2", (int)NumberStyle.DECIMAL_FULL_WIDTH);
        G_NUMBER_STYLE_MAP.addEntry("aiueo-full-width", (int)NumberStyle.AIUEO);
        G_NUMBER_STYLE_MAP.addEntry("iroha-full-width", (int)NumberStyle.IROHA);
        G_NUMBER_STYLE_MAP.addEntry("decimal-zero", (int)NumberStyle.LEADING_ZERO);
        G_NUMBER_STYLE_MAP.addEntry("bullet", (int)NumberStyle.BULLET);
        G_NUMBER_STYLE_MAP.addEntry("ganada", (int)NumberStyle.GANADA);
        G_NUMBER_STYLE_MAP.addEntry("chosung", (int)NumberStyle.CHOSUNG);
        G_NUMBER_STYLE_MAP.addEntry("decimal-enclosed-fullstop", (int)NumberStyle.GB_1);
        G_NUMBER_STYLE_MAP.addEntry("decimal-enclosed-paren", (int)NumberStyle.GB_2);
        G_NUMBER_STYLE_MAP.addEntry("decimal-enclosed-circle-chinese", (int)NumberStyle.GB_3);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-enclosed-circle", (int)NumberStyle.GB_4);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-traditional", (int)NumberStyle.ZODIAC_1);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-zodiac", (int)NumberStyle.ZODIAC_2);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-zodiac-traditional", (int)NumberStyle.ZODIAC_3);
        G_NUMBER_STYLE_MAP.addEntry("taiwanese-counting", (int)NumberStyle.TRAD_CHIN_NUM_1);
        G_NUMBER_STYLE_MAP.addEntry("ideograph-legal-traditional", (int)NumberStyle.TRAD_CHIN_NUM_2);
        G_NUMBER_STYLE_MAP.addEntry("taiwanese-counting-thousand", (int)NumberStyle.TRAD_CHIN_NUM_3);
        G_NUMBER_STYLE_MAP.addEntry("taiwanese-digital", (int)NumberStyle.TRAD_CHIN_NUM_4);
        G_NUMBER_STYLE_MAP.addEntry("chinese-counting", (int)NumberStyle.SIMP_CHIN_NUM_1);
        G_NUMBER_STYLE_MAP.addEntry("chinese-legal-simplified", (int)NumberStyle.SIMP_CHIN_NUM_2);
        G_NUMBER_STYLE_MAP.addEntry("chinese-counting-thousand", (int)NumberStyle.SIMP_CHIN_NUM_3);
        G_NUMBER_STYLE_MAP.addEntry("chinese-not-impl", (int)NumberStyle.SIMP_CHIN_NUM_4);
        G_NUMBER_STYLE_MAP.addEntry("korean-digital", (int)NumberStyle.HANJA_READ);
        G_NUMBER_STYLE_MAP.addEntry("korean-counting", (int)NumberStyle.HANJA_READ_DIGIT);
        G_NUMBER_STYLE_MAP.addEntry("korean-legal", (int)NumberStyle.HANGUL);
        G_NUMBER_STYLE_MAP.addEntry("korean-digital2", (int)NumberStyle.HANJA);
        G_NUMBER_STYLE_MAP.addEntry("vietnamese-counting", (int)NumberStyle.VIET_CARDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("russian-lower", (int)NumberStyle.LOWERCASE_RUSSIAN);
        G_NUMBER_STYLE_MAP.addEntry("russian-upper", (int)NumberStyle.UPPERCASE_RUSSIAN);
        G_NUMBER_STYLE_MAP.addEntry("none", (int)NumberStyle.NONE);
        G_NUMBER_STYLE_MAP.addEntry("number-in-dash", (int)NumberStyle.NUMBER_IN_DASH);
        G_NUMBER_STYLE_MAP.addEntry("hebrew-1", (int)NumberStyle.HEBREW_1);
        G_NUMBER_STYLE_MAP.addEntry("hebrew-2", (int)NumberStyle.HEBREW_2);
        G_NUMBER_STYLE_MAP.addEntry("arabic-alpha", (int)NumberStyle.ARABIC_1);
        G_NUMBER_STYLE_MAP.addEntry("arabic-abjad", (int)NumberStyle.ARABIC_2);
        G_NUMBER_STYLE_MAP.addEntry("hindi-vowels", (int)NumberStyle.HINDI_LETTER_1);
        G_NUMBER_STYLE_MAP.addEntry("hindi-consonants", (int)NumberStyle.HINDI_LETTER_2);
        G_NUMBER_STYLE_MAP.addEntry("hindi-numbers", (int)NumberStyle.HINDI_ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("hindi-counting", (int)NumberStyle.HINDI_CARDINAL_TEXT);
        G_NUMBER_STYLE_MAP.addEntry("thai-letters", (int)NumberStyle.THAI_LETTER);
        G_NUMBER_STYLE_MAP.addEntry("thai-numbers", (int)NumberStyle.THAI_ARABIC);
        G_NUMBER_STYLE_MAP.addEntry("thai-counting", (int)NumberStyle.THAI_CARDINAL_TEXT);

        G_STYLE_TYPE_MAP.addEntry("paragraph", (int)StyleType.PARAGRAPH);
        G_STYLE_TYPE_MAP.addEntry("character", (int)StyleType.CHARACTER);
        G_STYLE_TYPE_MAP.addEntry("table", (int)StyleType.TABLE);
        G_STYLE_TYPE_MAP.addEntry("list", (int)StyleType.LIST);

        G_FONT_FAMILY_MAP.addEntry("Auto", (int)FontFamily.AUTO);
        G_FONT_FAMILY_MAP.addEntry("Decorative", (int)FontFamily.DECORATIVE);
        G_FONT_FAMILY_MAP.addEntry("Modern", (int)FontFamily.MODERN);
        G_FONT_FAMILY_MAP.addEntry("Roman", (int)FontFamily.ROMAN);
        G_FONT_FAMILY_MAP.addEntry("Script", (int)FontFamily.SCRIPT);
        G_FONT_FAMILY_MAP.addEntry("Swiss", (int)FontFamily.SWISS);

        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("normal", -1);
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("separator", (int)FootnoteSeparatorType.FOOTNOTE_SEPARATOR);
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("continuation-separator", (int)FootnoteSeparatorType.FOOTNOTE_CONTINUATION_SEPARATOR);
        G_FOOTNOTE_SEPARATOR_TYPE_MAP.addEntry("continuation-notice", (int)FootnoteSeparatorType.FOOTNOTE_CONTINUATION_NOTICE);

        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("normal", -1);
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("separator", (int)FootnoteSeparatorType.ENDNOTE_SEPARATOR);
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("continuation-separator", (int)FootnoteSeparatorType.ENDNOTE_CONTINUATION_SEPARATOR);
        G_ENDNOTE_SEPARATOR_TYPE_MAP.addEntry("continuation-notice", (int)FootnoteSeparatorType.ENDNOTE_CONTINUATION_NOTICE);

    }
}

