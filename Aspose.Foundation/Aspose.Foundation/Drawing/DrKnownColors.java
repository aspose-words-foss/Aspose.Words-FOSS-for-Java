// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides methods for converting colors names to color codes.
/// Made into a separate class to avoid loading the hashtable unless really used.
/// </summary>
public /*static*/ class DrKnownColors
{
	/* Simulation of static class by using private constructor */
	private DrKnownColors()
	{}

    /// <summary>
    /// Converts a case-insensitive color name to a color value. Returns <see cref="DrColor.Empty"/> if cannot find a color.
    /// </summary>
    public static DrColor fromName(String value)
    {
        DrColor color = G_KNOWN_COLOR_IMPORT.get(value);
        return (color != null) ? color : DrColor.EMPTY;
    }

    private static /*final*/ StringToObjDictionary<DrColor> G_KNOWN_COLOR_IMPORT;

    static/* DrKnownColors()*/
    {
        G_KNOWN_COLOR_IMPORT = new StringToObjDictionary<DrColor>(false);

        G_KNOWN_COLOR_IMPORT.add("aliceBlue", DrColor.ALICE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("antiqueWhite", DrColor.ANTIQUE_WHITE);
        G_KNOWN_COLOR_IMPORT.add("aqua", DrColor.AQUA);
        G_KNOWN_COLOR_IMPORT.add("aquamarine", DrColor.AQUAMARINE);
        G_KNOWN_COLOR_IMPORT.add("azure", DrColor.AZURE);

        G_KNOWN_COLOR_IMPORT.add("beige", DrColor.BEIGE);
        G_KNOWN_COLOR_IMPORT.add("bisque", DrColor.BISQUE);
        G_KNOWN_COLOR_IMPORT.add("black", DrColor.BLACK);
        G_KNOWN_COLOR_IMPORT.add("blanchedAlmond", DrColor.BLANCHED_ALMOND);
        G_KNOWN_COLOR_IMPORT.add("blue", DrColor.BLUE);
        G_KNOWN_COLOR_IMPORT.add("blueViolet", DrColor.BLUE_VIOLET);
        G_KNOWN_COLOR_IMPORT.add("brown", DrColor.BROWN);
        G_KNOWN_COLOR_IMPORT.add("burlyWood", DrColor.BURLY_WOOD);

        G_KNOWN_COLOR_IMPORT.add("cadetBlue", DrColor.CADET_BLUE);
        G_KNOWN_COLOR_IMPORT.add("chartreuse", DrColor.CHARTREUSE);
        G_KNOWN_COLOR_IMPORT.add("chocolate", DrColor.CHOCOLATE);
        G_KNOWN_COLOR_IMPORT.add("coral", DrColor.CORAL);
        G_KNOWN_COLOR_IMPORT.add("cornflowerBlue", DrColor.CORNFLOWER_BLUE);
        G_KNOWN_COLOR_IMPORT.add("cornsilk", DrColor.CORNSILK);
        G_KNOWN_COLOR_IMPORT.add("crimson", DrColor.CRIMSON);
        G_KNOWN_COLOR_IMPORT.add("cyan", DrColor.CYAN);

        G_KNOWN_COLOR_IMPORT.add("darkBlue", DrColor.DARK_BLUE);
        G_KNOWN_COLOR_IMPORT.add("darkCyan", DrColor.DARK_CYAN);
        G_KNOWN_COLOR_IMPORT.add("darkGoldenrod", DrColor.DARK_GOLDENROD);
        G_KNOWN_COLOR_IMPORT.add("darkGray", DrColor.DARK_GRAY);
        G_KNOWN_COLOR_IMPORT.add("darkGreen", DrColor.DARK_GREEN);
        G_KNOWN_COLOR_IMPORT.add("darkGrey", DrColor.DARK_GRAY);
        G_KNOWN_COLOR_IMPORT.add("darkKhaki", DrColor.DARK_KHAKI);
        G_KNOWN_COLOR_IMPORT.add("darkMagenta", DrColor.DARK_MAGENTA);
        G_KNOWN_COLOR_IMPORT.add("darkOliveGreen", DrColor.DARK_OLIVE_GREEN);
        G_KNOWN_COLOR_IMPORT.add("darkOrange", DrColor.DARK_ORANGE);
        G_KNOWN_COLOR_IMPORT.add("darkOrchid", DrColor.DARK_ORCHID);
        G_KNOWN_COLOR_IMPORT.add("darkRed", DrColor.DARK_RED);
        G_KNOWN_COLOR_IMPORT.add("darkSalmon", DrColor.DARK_SALMON);
        G_KNOWN_COLOR_IMPORT.add("darkSeaGreen", DrColor.DARK_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("darkSlateBlue", DrColor.DARK_SLATE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("darkSlateGray", DrColor.DARK_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("darkSlateGrey", DrColor.DARK_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("darkTurquoise", DrColor.DARK_TURQUOISE);
        G_KNOWN_COLOR_IMPORT.add("darkViolet", DrColor.DARK_VIOLET);
        G_KNOWN_COLOR_IMPORT.add("deepPink", DrColor.DEEP_PINK);
        G_KNOWN_COLOR_IMPORT.add("deepSkyBlue", DrColor.DEEP_SKY_BLUE);
        G_KNOWN_COLOR_IMPORT.add("dimGray", DrColor.DIM_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dimGrey", DrColor.DIM_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dkBlue", DrColor.DARK_BLUE);
        G_KNOWN_COLOR_IMPORT.add("dkCyan", DrColor.DARK_CYAN);
        G_KNOWN_COLOR_IMPORT.add("dkGoldenrod", DrColor.DARK_GOLDENROD);
        G_KNOWN_COLOR_IMPORT.add("dkGray", DrColor.DARK_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dkGreen", DrColor.DARK_GREEN);
        G_KNOWN_COLOR_IMPORT.add("dkGrey", DrColor.DARK_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dkKhaki", DrColor.DARK_KHAKI);
        G_KNOWN_COLOR_IMPORT.add("dkMagenta", DrColor.DARK_MAGENTA);
        G_KNOWN_COLOR_IMPORT.add("dkOliveGreen", DrColor.DARK_OLIVE_GREEN);
        G_KNOWN_COLOR_IMPORT.add("dkOrange", DrColor.DARK_ORANGE);
        G_KNOWN_COLOR_IMPORT.add("dkOrchid", DrColor.DARK_ORCHID);
        G_KNOWN_COLOR_IMPORT.add("dkRed", DrColor.DARK_RED);
        G_KNOWN_COLOR_IMPORT.add("dkSalmon", DrColor.DARK_SALMON);
        G_KNOWN_COLOR_IMPORT.add("dkSeaGreen", DrColor.DARK_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("dkSlateBlue", DrColor.DARK_SLATE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("dkSlateGray", DrColor.DARK_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dkSlateGrey", DrColor.DARK_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("dkTurquoise", DrColor.DARK_TURQUOISE);
        G_KNOWN_COLOR_IMPORT.add("dkViolet", DrColor.DARK_VIOLET);
        G_KNOWN_COLOR_IMPORT.add("dodgerBlue", DrColor.DODGER_BLUE);

        G_KNOWN_COLOR_IMPORT.add("firebrick", DrColor.FIREBRICK);
        G_KNOWN_COLOR_IMPORT.add("floralWhite", DrColor.FLORAL_WHITE);
        G_KNOWN_COLOR_IMPORT.add("forestGreen", DrColor.FOREST_GREEN);
        G_KNOWN_COLOR_IMPORT.add("fuchsia", DrColor.FUCHSIA);

        G_KNOWN_COLOR_IMPORT.add("gainsboro", DrColor.GAINSBORO);
        G_KNOWN_COLOR_IMPORT.add("ghostWhite", DrColor.GHOST_WHITE);
        G_KNOWN_COLOR_IMPORT.add("gold", DrColor.GOLD);
        G_KNOWN_COLOR_IMPORT.add("goldenrod", DrColor.GOLDENROD);
        G_KNOWN_COLOR_IMPORT.add("gray", DrColor.GRAY);
        G_KNOWN_COLOR_IMPORT.add("green", DrColor.GREEN);
        G_KNOWN_COLOR_IMPORT.add("greenYellow", DrColor.GREEN_YELLOW);
        G_KNOWN_COLOR_IMPORT.add("grey", DrColor.GRAY);

        G_KNOWN_COLOR_IMPORT.add("honeydew", DrColor.HONEYDEW);
        G_KNOWN_COLOR_IMPORT.add("hotPink", DrColor.HOT_PINK);

        G_KNOWN_COLOR_IMPORT.add("indianRed", DrColor.INDIAN_RED);
        G_KNOWN_COLOR_IMPORT.add("indigo", DrColor.INDIGO);
        G_KNOWN_COLOR_IMPORT.add("ivory", DrColor.IVORY);

        G_KNOWN_COLOR_IMPORT.add("khaki", DrColor.KHAKI);

        G_KNOWN_COLOR_IMPORT.add("lavender", DrColor.LAVENDER);
        G_KNOWN_COLOR_IMPORT.add("lavenderBlush", DrColor.LAVENDER_BLUSH);
        G_KNOWN_COLOR_IMPORT.add("lawnGreen", DrColor.LAWN_GREEN);
        G_KNOWN_COLOR_IMPORT.add("lemonChiffon", DrColor.LEMON_CHIFFON);
        G_KNOWN_COLOR_IMPORT.add("lightBlue", DrColor.LIGHT_BLUE);
        G_KNOWN_COLOR_IMPORT.add("lightCoral", DrColor.LIGHT_CORAL);
        G_KNOWN_COLOR_IMPORT.add("lightCyan", DrColor.LIGHT_CYAN);
        G_KNOWN_COLOR_IMPORT.add("lightGoldenrodYellow", DrColor.LIGHT_GOLDENROD_YELLOW);
        G_KNOWN_COLOR_IMPORT.add("lightGray", DrColor.LIGHT_GRAY);
        G_KNOWN_COLOR_IMPORT.add("lightGreen", DrColor.LIGHT_GREEN);
        G_KNOWN_COLOR_IMPORT.add("lightGrey", DrColor.LIGHT_GRAY);
        G_KNOWN_COLOR_IMPORT.add("lightPink", DrColor.LIGHT_PINK);
        G_KNOWN_COLOR_IMPORT.add("lightSalmon", DrColor.LIGHT_SALMON);
        G_KNOWN_COLOR_IMPORT.add("lightSeaGreen", DrColor.LIGHT_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("lightSkyBlue", DrColor.LIGHT_SKY_BLUE);
        G_KNOWN_COLOR_IMPORT.add("lightSlateGray", DrColor.LIGHT_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("lightSlateGrey", DrColor.LIGHT_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("lightSteelBlue", DrColor.LIGHT_STEEL_BLUE);
        G_KNOWN_COLOR_IMPORT.add("lightYellow", DrColor.LIGHT_YELLOW);
        G_KNOWN_COLOR_IMPORT.add("lime", DrColor.LIME);
        G_KNOWN_COLOR_IMPORT.add("limeGreen", DrColor.LIME_GREEN);
        G_KNOWN_COLOR_IMPORT.add("linen", DrColor.LINEN);
        G_KNOWN_COLOR_IMPORT.add("ltBlue", DrColor.LIGHT_BLUE);
        G_KNOWN_COLOR_IMPORT.add("ltCoral", DrColor.LIGHT_CORAL);
        G_KNOWN_COLOR_IMPORT.add("ltCyan", DrColor.LIGHT_CYAN);
        G_KNOWN_COLOR_IMPORT.add("ltGoldenrodYellow", DrColor.LIGHT_GOLDENROD_YELLOW);
        G_KNOWN_COLOR_IMPORT.add("ltGray", DrColor.LIGHT_GRAY);
        G_KNOWN_COLOR_IMPORT.add("ltGreen", DrColor.LIGHT_GREEN);
        G_KNOWN_COLOR_IMPORT.add("ltGrey", DrColor.LIGHT_GRAY);
        G_KNOWN_COLOR_IMPORT.add("ltPink", DrColor.LIGHT_PINK);
        G_KNOWN_COLOR_IMPORT.add("ltSalmon", DrColor.LIGHT_SALMON);
        G_KNOWN_COLOR_IMPORT.add("ltSeaGreen", DrColor.LIGHT_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("ltSkyBlue", DrColor.LIGHT_SKY_BLUE);
        G_KNOWN_COLOR_IMPORT.add("ltSlateGray", DrColor.LIGHT_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("ltSlateGrey", DrColor.LIGHT_SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("ltSteelBlue", DrColor.LIGHT_STEEL_BLUE);
        G_KNOWN_COLOR_IMPORT.add("ltYellow", DrColor.LIGHT_YELLOW);

        G_KNOWN_COLOR_IMPORT.add("magenta", DrColor.MAGENTA);
        G_KNOWN_COLOR_IMPORT.add("maroon", DrColor.MAROON);
        G_KNOWN_COLOR_IMPORT.add("medAquamarine", DrColor.MEDIUM_AQUAMARINE);
        G_KNOWN_COLOR_IMPORT.add("medBlue", DrColor.MEDIUM_BLUE);
        G_KNOWN_COLOR_IMPORT.add("mediumAquamarine", DrColor.MEDIUM_AQUAMARINE);
        G_KNOWN_COLOR_IMPORT.add("mediumBlue", DrColor.MEDIUM_BLUE);
        G_KNOWN_COLOR_IMPORT.add("mediumOrchid", DrColor.MEDIUM_ORCHID);
        G_KNOWN_COLOR_IMPORT.add("mediumPurple", DrColor.MEDIUM_PURPLE);
        G_KNOWN_COLOR_IMPORT.add("mediumSeaGreen", DrColor.MEDIUM_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("mediumSlateBlue", DrColor.MEDIUM_SLATE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("mediumSpringGreen", DrColor.MEDIUM_SPRING_GREEN);
        G_KNOWN_COLOR_IMPORT.add("mediumTurquoise", DrColor.MEDIUM_TURQUOISE);
        G_KNOWN_COLOR_IMPORT.add("mediumVioletRed", DrColor.MEDIUM_VIOLET_RED);
        G_KNOWN_COLOR_IMPORT.add("medOrchid", DrColor.MEDIUM_ORCHID);
        G_KNOWN_COLOR_IMPORT.add("medPurple", DrColor.MEDIUM_PURPLE);
        G_KNOWN_COLOR_IMPORT.add("medSeaGreen", DrColor.MEDIUM_SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("medSlateBlue", DrColor.MEDIUM_SLATE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("medSpringGreen", DrColor.MEDIUM_SPRING_GREEN);
        G_KNOWN_COLOR_IMPORT.add("medTurquoise", DrColor.MEDIUM_TURQUOISE);
        G_KNOWN_COLOR_IMPORT.add("medVioletRed", DrColor.MEDIUM_VIOLET_RED);
        G_KNOWN_COLOR_IMPORT.add("midnightBlue", DrColor.MIDNIGHT_BLUE);
        G_KNOWN_COLOR_IMPORT.add("mintCream", DrColor.MINT_CREAM);
        G_KNOWN_COLOR_IMPORT.add("mistyRose", DrColor.MISTY_ROSE);
        G_KNOWN_COLOR_IMPORT.add("moccasin", DrColor.MOCCASIN);

        G_KNOWN_COLOR_IMPORT.add("navajoWhite", DrColor.NAVAJO_WHITE);
        G_KNOWN_COLOR_IMPORT.add("navy", DrColor.NAVY);

        G_KNOWN_COLOR_IMPORT.add("oldLace", DrColor.OLD_LACE);
        G_KNOWN_COLOR_IMPORT.add("olive", DrColor.OLIVE);
        G_KNOWN_COLOR_IMPORT.add("oliveDrab", DrColor.OLIVE_DRAB);
        G_KNOWN_COLOR_IMPORT.add("orange", DrColor.ORANGE);
        G_KNOWN_COLOR_IMPORT.add("orangeRed", DrColor.ORANGE_RED);
        G_KNOWN_COLOR_IMPORT.add("orchid", DrColor.ORCHID);

        G_KNOWN_COLOR_IMPORT.add("paleGoldenrod", DrColor.PALE_GOLDENROD);
        G_KNOWN_COLOR_IMPORT.add("paleGreen", DrColor.PALE_GREEN);
        G_KNOWN_COLOR_IMPORT.add("paleTurquoise", DrColor.PALE_TURQUOISE);
        G_KNOWN_COLOR_IMPORT.add("paleVioletRed", DrColor.PALE_VIOLET_RED);
        G_KNOWN_COLOR_IMPORT.add("papayaWhip", DrColor.PAPAYA_WHIP);
        G_KNOWN_COLOR_IMPORT.add("peachPuff", DrColor.PEACH_PUFF);
        G_KNOWN_COLOR_IMPORT.add("peru", DrColor.PERU);
        G_KNOWN_COLOR_IMPORT.add("pink", DrColor.PINK);
        G_KNOWN_COLOR_IMPORT.add("plum", DrColor.PLUM);
        G_KNOWN_COLOR_IMPORT.add("powderBlue", DrColor.POWDER_BLUE);
        G_KNOWN_COLOR_IMPORT.add("purple", DrColor.PURPLE);

        G_KNOWN_COLOR_IMPORT.add("red", DrColor.RED);
        G_KNOWN_COLOR_IMPORT.add("rosyBrown", DrColor.ROSY_BROWN);
        G_KNOWN_COLOR_IMPORT.add("royalBlue", DrColor.ROYAL_BLUE);

        G_KNOWN_COLOR_IMPORT.add("saddleBrown", DrColor.SADDLE_BROWN);
        G_KNOWN_COLOR_IMPORT.add("salmon", DrColor.SALMON);
        G_KNOWN_COLOR_IMPORT.add("sandyBrown", DrColor.SANDY_BROWN);
        G_KNOWN_COLOR_IMPORT.add("seaGreen", DrColor.SEA_GREEN);
        G_KNOWN_COLOR_IMPORT.add("seaShell", DrColor.SEA_SHELL);
        G_KNOWN_COLOR_IMPORT.add("sienna", DrColor.SIENNA);
        G_KNOWN_COLOR_IMPORT.add("silver", DrColor.SILVER);
        G_KNOWN_COLOR_IMPORT.add("skyBlue", DrColor.SKY_BLUE);
        G_KNOWN_COLOR_IMPORT.add("slateBlue", DrColor.SLATE_BLUE);
        G_KNOWN_COLOR_IMPORT.add("slateGray", DrColor.SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("slateGrey", DrColor.SLATE_GRAY);
        G_KNOWN_COLOR_IMPORT.add("snow", DrColor.SNOW);
        G_KNOWN_COLOR_IMPORT.add("springGreen", DrColor.SPRING_GREEN);
        G_KNOWN_COLOR_IMPORT.add("steelBlue", DrColor.STEEL_BLUE);

        G_KNOWN_COLOR_IMPORT.add("tan", DrColor.TAN);
        G_KNOWN_COLOR_IMPORT.add("teal", DrColor.TEAL);
        G_KNOWN_COLOR_IMPORT.add("thistle", DrColor.THISTLE);
        G_KNOWN_COLOR_IMPORT.add("tomato", DrColor.TOMATO);
        G_KNOWN_COLOR_IMPORT.add("turquoise", DrColor.TURQUOISE);

        G_KNOWN_COLOR_IMPORT.add("violet", DrColor.VIOLET);

        G_KNOWN_COLOR_IMPORT.add("wheat", DrColor.WHEAT);
        G_KNOWN_COLOR_IMPORT.add("white", DrColor.WHITE);
        G_KNOWN_COLOR_IMPORT.add("whiteSmoke", DrColor.WHITE_SMOKE);

        G_KNOWN_COLOR_IMPORT.add("yellow", DrColor.YELLOW);
        G_KNOWN_COLOR_IMPORT.add("yellowGreen", DrColor.YELLOW_GREEN);
    }
}

