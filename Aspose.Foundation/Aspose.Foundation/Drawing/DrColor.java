// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2010 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;


/// <summary>
/// Use this class instead of GDI+ Color to make code autoportable to Java.
/// 
/// This is a by-reference object with by-value semantics. It is immutable and can be equality compared.
/// 
/// Why do we have this class instead of implementing ms.System.Drawing.Color?
/// 1. .NET Color is too convoluted and awkward to use. For example, it is error prone to compare (have to convert to argb first).
/// 2. Even if we decided to implement .NET Color in Java, it would be more convoluted than we need because it uses enum.ToString etc.
/// So it seems best to just implement our own simple color class that is automatically portable.
/// </summary>
public class DrColor
{
    /// <summary>
    /// Normally such method should not be on an abstraction class, but in this case it is autoportable.
    /// </summary>
    public Color toNativeColor()
    {
        return ColorPal.toNativeColor(this);
    }

    /// <summary>
    /// Converts color returned as attribute of document model into color which can be rendered correctly.
    /// </summary>
    public DrColor toColorFixAlpha()
    {
        return (isEmpty())
            ? this
            : new DrColor(getR(), getG(), getB());
    }

    /// <summary>
    /// See https://www.w3.org/TR/AERT/#color-contrast for more info.
    /// </summary>
    public float getLuminance()
    {
        return (float)((299 * getR()) + (587 * getG()) + (114 * getB())) / 255000f; // Casting for Java.
    }

    /// <summary>
    /// Normally such method should not be on an abstraction class, but in this case it is autoportable.
    /// </summary>
    public static DrColor fromNativeColor(Color color)
    {
        return ColorPal.fromNativeColor(color);
    }

    /// <summary>
    /// Same as calling a ctor. Kept to preserve color creation familiar to .NET developers.
    /// </summary>
    public static DrColor fromArgb(int r, int g, int b)
    {
        return new DrColor(r, g, b);
    }

    /// <summary>
    /// Same as calling a ctor. Kept to preserve color creation familiar to .NET developers.
    /// </summary>
    public static DrColor fromArgb(int a, int r, int g, int b)
    {
        return new DrColor(a, r, g, b);
    }

    /// <summary>
    /// Gets value of system color.
    /// </summary>
    /// <param name="systemColorName"></param>
    /// <returns>System color value if system colors are supported otherwise DrColor.Empty</returns>
    public static DrColor getSystemColor(String systemColorName)
    {
        return ColorPal.getSystemColor(systemColorName);
    }

    public /*override*/ String toString()
    {
        String name = FormatterPal.intToStrX8(mArgb);


        return msString.format("DrColor [{0}]", name);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    public DrColor(int argb)
    {
        mArgb = argb;
    }

    /// <summary>
    /// Creates a Color from the specified Color, but with the new specified alpha value.
    /// Although this method allows a 32-bit value to be passed for the alpha value, the value is limited to 8 bits.
    /// </summary>
    public DrColor(int a, int r, int g, int b)
    {
        a = ((correctColorComponent(a) & 0xff) << SHIFT_A);
        r = ((correctColorComponent(r) & 0xff) << SHIFT_R);
        g = ((correctColorComponent(g) & 0xff) << SHIFT_G);
        b = ((correctColorComponent(b) & 0xff) << SHIFT_B);
        mArgb = a | r | g | b;
    }

    /// <summary>
    /// Corrects color value in accordance with color range.
    /// </summary>
    private static int correctColorComponent(int color)
    {
        if (color > 0xff)
            color = 0xff;
        else if (color < 0x0)
            color = 0x0;

        return color;
    }

    /// <summary>
    /// Creates a color with the specified alpha.
    /// </summary>
    public DrColor(int newAlpha, DrColor color)
    {
    	this(newAlpha, color.getR(), color.getG(), color.getB());
	
    }

    /// <summary>
    /// Creates a color with 0xff alpha.
    /// </summary>
    public DrColor(int r, int g, int b)
    {
    	this(0xff, r, g, b);
	
    }

    public int getA() { return ((mArgb >> SHIFT_A) & 0xff); }

    public int getR() { return ((mArgb >> SHIFT_R) & 0xff); }

    public int getG() { return ((mArgb >> SHIFT_G) & 0xff); }

    public int getB() { return ((mArgb >> SHIFT_B) & 0xff); }

    public boolean isEmpty() { return mArgb == DrArgb.EMPTY; }

    public int toArgb()
    {
        return mArgb;
    }

    /// <summary>
    /// Returns true if color represents the level of gray i.e. R = G = B.
    /// </summary>
    public boolean isGray() { return ((getR() == getG()) && (getG() == getB())); }

    /// <summary>
    /// Overridden because equals is overridden.
    /// </summary>
    public /*override*/ int hashCode()
    {
        return mArgb;
    }

    public /*override*/ boolean equals(Object obj)
    {
        return (obj instanceof DrColor) && (equals(this, (DrColor)obj));
    }

    /// <summary>
    /// The actual implementation.
    /// </summary>
    public static boolean equals(DrColor a, DrColor b)
    {
        if (msObject.referenceEquals(a, b))
            return true;

        if (msObject.referenceEquals(null, a))
            return false;

        if (msObject.referenceEquals(null, b))
            return false;

        return (a.mArgb == b.mArgb);
    }

    public static boolean op_Equality(DrColor a, DrColor b)
    {
        return equals(a, b);
    }

    public static boolean op_Inequality(DrColor a, DrColor b)
    {
        return !equals(a, b);
    }

    /// <summary>
    /// Index of red component.
    /// </summary>
    public static final short R_INDEX = 2;

    /// <summary>
    /// Index of green component.
    /// </summary>
    public static final short G_INDEX = 1;

    /// <summary>
    /// Index of blue component.
    /// </summary>
    public static final short B_INDEX = 0;

    /// <summary>
    /// Index of alpha component for ARGB images.
    /// </summary>
    public static final short A_INDEX = 3;

    // 0
    public static /*final*/ DrColor ALICE_BLUE;
    public static /*final*/ DrColor ANTIQUE_WHITE;
    public static /*final*/ DrColor AQUA;
    public static /*final*/ DrColor AQUAMARINE;
    public static /*final*/ DrColor AZURE;
    public static /*final*/ DrColor BEIGE;
    public static /*final*/ DrColor BISQUE;
    public static /*final*/ DrColor BLACK;
    public static /*final*/ DrColor BLANCHED_ALMOND;
    public static /*final*/ DrColor BLUE;
    // 10
    public static /*final*/ DrColor BLUE_VIOLET;
    public static /*final*/ DrColor BROWN;
    public static /*final*/ DrColor BURLY_WOOD;
    public static /*final*/ DrColor CADET_BLUE;
    public static /*final*/ DrColor CHARTREUSE;
    public static /*final*/ DrColor CHOCOLATE;
    public static /*final*/ DrColor CORAL;
    public static /*final*/ DrColor CORNFLOWER_BLUE;
    public static /*final*/ DrColor CORNSILK;
    public static /*final*/ DrColor CRIMSON;
    // 20
    public static /*final*/ DrColor CYAN;
    public static /*final*/ DrColor DARK_BLUE;
    public static /*final*/ DrColor DARK_CYAN;
    public static /*final*/ DrColor DARK_GOLDENROD;
    public static /*final*/ DrColor DARK_GRAY;
    public static /*final*/ DrColor DARK_GREEN;
    public static /*final*/ DrColor DARK_KHAKI;
    public static /*final*/ DrColor DARK_MAGENTA;
    public static /*final*/ DrColor DARK_OLIVE_GREEN;
    public static /*final*/ DrColor DARK_ORANGE;
    // 30
    public static /*final*/ DrColor DARK_ORCHID;
    public static /*final*/ DrColor DARK_RED;
    public static /*final*/ DrColor DARK_SALMON;
    public static /*final*/ DrColor DARK_SEA_GREEN;
    public static /*final*/ DrColor DARK_SLATE_BLUE;
    public static /*final*/ DrColor DARK_SLATE_GRAY;
    public static /*final*/ DrColor DARK_TURQUOISE;
    public static /*final*/ DrColor DARK_VIOLET;
    public static /*final*/ DrColor DEEP_PINK;
    public static /*final*/ DrColor DEEP_SKY_BLUE;
    // 40
    public static /*final*/ DrColor DIM_GRAY;
    public static /*final*/ DrColor DODGER_BLUE;
    public static /*final*/ DrColor FIREBRICK;
    public static /*final*/ DrColor FLORAL_WHITE;
    public static /*final*/ DrColor FOREST_GREEN;
    public static /*final*/ DrColor FUCHSIA;
    public static /*final*/ DrColor GAINSBORO;
    public static /*final*/ DrColor GHOST_WHITE;
    public static /*final*/ DrColor GOLD;
    public static /*final*/ DrColor GRAY;
    // 50
    public static /*final*/ DrColor GREEN;
    public static /*final*/ DrColor GREEN_YELLOW;
    public static /*final*/ DrColor GOLDENROD;
    public static /*final*/ DrColor HONEYDEW;
    public static /*final*/ DrColor HOT_PINK;
    public static /*final*/ DrColor INDIAN_RED;
    public static /*final*/ DrColor INDIGO;
    public static /*final*/ DrColor IVORY;
    public static /*final*/ DrColor KHAKI;
    public static /*final*/ DrColor LAVENDER;
    // 60
    public static /*final*/ DrColor LAVENDER_BLUSH;
    public static /*final*/ DrColor LAWN_GREEN;
    public static /*final*/ DrColor LEMON_CHIFFON;
    public static /*final*/ DrColor LIGHT_BLUE;
    public static /*final*/ DrColor LIGHT_CORAL;
    public static /*final*/ DrColor LIGHT_CYAN;
    public static /*final*/ DrColor LIGHT_GOLDENROD_YELLOW;
    public static /*final*/ DrColor LIGHT_GRAY;
    public static /*final*/ DrColor LIGHT_GREEN;
    public static /*final*/ DrColor LIGHT_PINK;
    // 70
    public static /*final*/ DrColor LIGHT_SALMON;
    public static /*final*/ DrColor LIGHT_SEA_GREEN;
    public static /*final*/ DrColor LIGHT_SKY_BLUE;
    public static /*final*/ DrColor LIGHT_SLATE_GRAY;
    public static /*final*/ DrColor LIGHT_STEEL_BLUE;
    public static /*final*/ DrColor LIGHT_YELLOW;
    public static /*final*/ DrColor LIME;
    public static /*final*/ DrColor LIME_GREEN;
    public static /*final*/ DrColor LINEN;
    public static /*final*/ DrColor MAGENTA;
    // 80
    public static /*final*/ DrColor MAROON;
    public static /*final*/ DrColor MEDIUM_AQUAMARINE;
    public static /*final*/ DrColor MEDIUM_BLUE;
    public static /*final*/ DrColor MEDIUM_ORCHID;
    public static /*final*/ DrColor MEDIUM_PURPLE;
    public static /*final*/ DrColor MEDIUM_SEA_GREEN;
    public static /*final*/ DrColor MEDIUM_SLATE_BLUE;
    public static /*final*/ DrColor MEDIUM_SPRING_GREEN;
    public static /*final*/ DrColor MEDIUM_TURQUOISE;
    public static /*final*/ DrColor MEDIUM_VIOLET_RED;
    // 90
    public static /*final*/ DrColor MIDNIGHT_BLUE;
    public static /*final*/ DrColor MINT_CREAM;
    public static /*final*/ DrColor MISTY_ROSE;
    public static /*final*/ DrColor MOCCASIN;
    public static /*final*/ DrColor NAVAJO_WHITE;
    public static /*final*/ DrColor NAVY;
    public static /*final*/ DrColor OLD_LACE;
    public static /*final*/ DrColor OLIVE;
    public static /*final*/ DrColor OLIVE_DRAB;
    public static /*final*/ DrColor ORANGE;
    // 100
    public static /*final*/ DrColor ORANGE_RED;
    public static /*final*/ DrColor ORCHID;
    public static /*final*/ DrColor PALE_GOLDENROD;
    public static /*final*/ DrColor PALE_GREEN;
    public static /*final*/ DrColor PALE_TURQUOISE;
    public static /*final*/ DrColor PALE_VIOLET_RED;
    public static /*final*/ DrColor PAPAYA_WHIP;
    public static /*final*/ DrColor PEACH_PUFF;
    public static /*final*/ DrColor PERU;
    public static /*final*/ DrColor PINK;
    // 110
    public static /*final*/ DrColor PLUM;
    public static /*final*/ DrColor POWDER_BLUE;
    public static /*final*/ DrColor PURPLE;
    public static /*final*/ DrColor RED;
    public static /*final*/ DrColor ROSY_BROWN;
    public static /*final*/ DrColor ROYAL_BLUE;
    public static /*final*/ DrColor SADDLE_BROWN;
    public static /*final*/ DrColor SALMON;
    public static /*final*/ DrColor SANDY_BROWN;
    public static /*final*/ DrColor SEA_GREEN;
    // 120
    public static /*final*/ DrColor SEA_SHELL;
    public static /*final*/ DrColor SIENNA;
    public static /*final*/ DrColor SILVER;
    public static /*final*/ DrColor SKY_BLUE;
    public static /*final*/ DrColor SLATE_BLUE;
    public static /*final*/ DrColor SLATE_GRAY;
    public static /*final*/ DrColor SNOW;
    public static /*final*/ DrColor SPRING_GREEN;
    public static /*final*/ DrColor STEEL_BLUE;
    public static /*final*/ DrColor TAN;
    // 130
    public static /*final*/ DrColor TEAL;
    public static /*final*/ DrColor THISTLE;
    public static /*final*/ DrColor TOMATO;
    public static /*final*/ DrColor TURQUOISE;
    public static /*final*/ DrColor VIOLET;
    public static /*final*/ DrColor WHEAT;
    public static /*final*/ DrColor WHITE;
    public static /*final*/ DrColor WHITE_SMOKE;
    public static /*final*/ DrColor YELLOW;
    public static /*final*/ DrColor YELLOW_GREEN;
    // 140 colors

    // System window color code.
    public static /*final*/ DrColor WINDOW;

    /// <summary>
    /// Gets Transparent color. This is full transparency white color.
    ///</summary>
    public static /*final*/ DrColor TRANSPARENT;

    /// <summary>
    /// Gets Empty color. This is full transparency black color used in the AW model.
    ///</summary>
    public static /*final*/ DrColor EMPTY;

    private /*final*/ int mArgb;

    private static final int SHIFT_A = 24;
    private static final int SHIFT_R = 16;
    private static final int SHIFT_G = 8;
    private static final int SHIFT_B = 0;

    static/* DrColor()*/
    {
        // RK These are in a static ctor to make auto porting to Java possible.
        ALICE_BLUE = new DrColor(DrArgb.ALICE_BLUE);
        ANTIQUE_WHITE = new DrColor(DrArgb.ANTIQUE_WHITE);
        AQUA = new DrColor(DrArgb.AQUA);
        AQUAMARINE = new DrColor(DrArgb.AQUAMARINE);
        AZURE = new DrColor(DrArgb.AZURE);
        BEIGE = new DrColor(DrArgb.BEIGE);
        BISQUE = new DrColor(DrArgb.BISQUE);
        BLACK = new DrColor(DrArgb.BLACK);
        BLANCHED_ALMOND = new DrColor(DrArgb.BLANCHED_ALMOND);
        BLUE = new DrColor(DrArgb.BLUE);

        BLUE_VIOLET = new DrColor(DrArgb.BLUE_VIOLET);
        BROWN = new DrColor(DrArgb.BROWN);
        BURLY_WOOD = new DrColor(DrArgb.BURLY_WOOD);
        CADET_BLUE = new DrColor(DrArgb.CADET_BLUE);
        CHARTREUSE = new DrColor(DrArgb.CHARTREUSE);
        CHOCOLATE = new DrColor(DrArgb.CHOCOLATE);
        CORAL = new DrColor(DrArgb.CORAL);
        CORNFLOWER_BLUE = new DrColor(DrArgb.CORNFLOWER_BLUE);
        CORNSILK = new DrColor(DrArgb.CORNSILK);
        CRIMSON = new DrColor(DrArgb.CRIMSON);

        CYAN = new DrColor(DrArgb.CYAN);
        DARK_BLUE = new DrColor(DrArgb.DARK_BLUE);
        DARK_CYAN = new DrColor(DrArgb.DARK_CYAN);
        DARK_GOLDENROD = new DrColor(DrArgb.DARK_GOLDENROD);
        DARK_GRAY = new DrColor(DrArgb.DARK_GRAY);
        DARK_GREEN = new DrColor(DrArgb.DARK_GREEN);
        DARK_KHAKI = new DrColor(DrArgb.DARK_KHAKI);
        DARK_MAGENTA = new DrColor(DrArgb.DARK_MAGENTA);
        DARK_OLIVE_GREEN = new DrColor(DrArgb.DARK_OLIVE_GREEN);
        DARK_ORANGE = new DrColor(DrArgb.DARK_ORANGE);

        DARK_ORCHID = new DrColor(DrArgb.DARK_ORCHID);
        DARK_RED = new DrColor(DrArgb.DARK_RED);
        DARK_SALMON = new DrColor(DrArgb.DARK_SALMON);
        DARK_SEA_GREEN = new DrColor(DrArgb.DARK_SEA_GREEN);
        DARK_SLATE_BLUE = new DrColor(DrArgb.DARK_SLATE_BLUE);
        DARK_SLATE_GRAY = new DrColor(DrArgb.DARK_SLATE_GRAY);
        DARK_TURQUOISE = new DrColor(DrArgb.DARK_TURQUOISE);
        DARK_VIOLET = new DrColor(DrArgb.DARK_VIOLET);
        DEEP_PINK = new DrColor(DrArgb.DEEP_PINK);
        DEEP_SKY_BLUE = new DrColor(DrArgb.DEEP_SKY_BLUE);

        DIM_GRAY = new DrColor(DrArgb.DIM_GRAY);
        DODGER_BLUE = new DrColor(DrArgb.DODGER_BLUE);
        FIREBRICK = new DrColor(DrArgb.FIREBRICK);
        FLORAL_WHITE = new DrColor(DrArgb.FLORAL_WHITE);
        FOREST_GREEN = new DrColor(DrArgb.FOREST_GREEN);
        FUCHSIA = new DrColor(DrArgb.FUCHSIA);
        GAINSBORO = new DrColor(DrArgb.GAINSBORO);
        GHOST_WHITE = new DrColor(DrArgb.GHOST_WHITE);
        GOLD = new DrColor(DrArgb.GOLD);
        GRAY = new DrColor(DrArgb.GRAY);

        GREEN = new DrColor(DrArgb.GREEN);
        GREEN_YELLOW = new DrColor(DrArgb.GREEN_YELLOW);
        GOLDENROD = new DrColor(DrArgb.GOLDENROD);
        HONEYDEW = new DrColor(DrArgb.HONEYDEW);
        HOT_PINK = new DrColor(DrArgb.HOT_PINK);
        INDIAN_RED = new DrColor(DrArgb.INDIAN_RED);
        INDIGO = new DrColor(DrArgb.INDIGO);
        IVORY = new DrColor(DrArgb.IVORY);
        KHAKI = new DrColor(DrArgb.KHAKI);
        LAVENDER = new DrColor(DrArgb.LAVENDER);

        LAVENDER_BLUSH = new DrColor(DrArgb.LAVENDER_BLUSH);
        LAWN_GREEN = new DrColor(DrArgb.LAWN_GREEN);
        LEMON_CHIFFON = new DrColor(DrArgb.LEMON_CHIFFON);
        LIGHT_BLUE = new DrColor(DrArgb.LIGHT_BLUE);
        LIGHT_CORAL = new DrColor(DrArgb.LIGHT_CORAL);
        LIGHT_CYAN = new DrColor(DrArgb.LIGHT_CYAN);
        LIGHT_GOLDENROD_YELLOW = new DrColor(DrArgb.LIGHT_GOLDENROD_YELLOW);
        LIGHT_GRAY = new DrColor(DrArgb.LIGHT_GRAY);
        LIGHT_GREEN = new DrColor(DrArgb.LIGHT_GREEN);
        LIGHT_PINK = new DrColor(DrArgb.LIGHT_PINK);

        LIGHT_SALMON = new DrColor(DrArgb.LIGHT_SALMON);
        LIGHT_SEA_GREEN = new DrColor(DrArgb.LIGHT_SEA_GREEN);
        LIGHT_SKY_BLUE = new DrColor(DrArgb.LIGHT_SKY_BLUE);
        LIGHT_SLATE_GRAY = new DrColor(DrArgb.LIGHT_SLATE_GRAY);
        LIGHT_STEEL_BLUE = new DrColor(DrArgb.LIGHT_STEEL_BLUE);
        LIGHT_YELLOW = new DrColor(DrArgb.LIGHT_YELLOW);
        LIME = new DrColor(DrArgb.LIME);
        LIME_GREEN = new DrColor(DrArgb.LIME_GREEN);
        LINEN = new DrColor(DrArgb.LINEN);
        MAGENTA = new DrColor(DrArgb.MAGENTA);

        MAROON = new DrColor(DrArgb.MAROON);
        MEDIUM_AQUAMARINE = new DrColor(DrArgb.MEDIUM_AQUAMARINE);
        MEDIUM_BLUE = new DrColor(DrArgb.MEDIUM_BLUE);
        MEDIUM_ORCHID = new DrColor(DrArgb.MEDIUM_ORCHID);
        MEDIUM_PURPLE = new DrColor(DrArgb.MEDIUM_PURPLE);
        MEDIUM_SEA_GREEN = new DrColor(DrArgb.MEDIUM_SEA_GREEN);
        MEDIUM_SLATE_BLUE = new DrColor(DrArgb.MEDIUM_SLATE_BLUE);
        MEDIUM_SPRING_GREEN = new DrColor(DrArgb.MEDIUM_SPRING_GREEN);
        MEDIUM_TURQUOISE = new DrColor(DrArgb.MEDIUM_TURQUOISE);
        MEDIUM_VIOLET_RED = new DrColor(DrArgb.MEDIUM_VIOLET_RED);

        MIDNIGHT_BLUE = new DrColor(DrArgb.MIDNIGHT_BLUE);
        MINT_CREAM = new DrColor(DrArgb.MINT_CREAM);
        MISTY_ROSE = new DrColor(DrArgb.MISTY_ROSE);
        MOCCASIN = new DrColor(DrArgb.MOCCASIN);
        NAVAJO_WHITE = new DrColor(DrArgb.NAVAJO_WHITE);
        NAVY = new DrColor(DrArgb.NAVY);
        OLD_LACE = new DrColor(DrArgb.OLD_LACE);
        OLIVE = new DrColor(DrArgb.OLIVE);
        OLIVE_DRAB = new DrColor(DrArgb.OLIVE_DRAB);
        ORANGE = new DrColor(DrArgb.ORANGE);

        ORANGE_RED = new DrColor(DrArgb.ORANGE_RED);
        ORCHID = new DrColor(DrArgb.ORCHID);
        PALE_GOLDENROD = new DrColor(DrArgb.PALE_GOLDENROD);
        PALE_GREEN = new DrColor(DrArgb.PALE_GREEN);
        PALE_TURQUOISE = new DrColor(DrArgb.PALE_TURQUOISE);
        PALE_VIOLET_RED = new DrColor(DrArgb.PALE_VIOLET_RED);
        PAPAYA_WHIP = new DrColor(DrArgb.PAPAYA_WHIP);
        PEACH_PUFF = new DrColor(DrArgb.PEACH_PUFF);
        PERU = new DrColor(DrArgb.PERU);
        PINK = new DrColor(DrArgb.PINK);

        PLUM = new DrColor(DrArgb.PLUM);
        POWDER_BLUE = new DrColor(DrArgb.POWDER_BLUE);
        PURPLE = new DrColor(DrArgb.PURPLE);
        RED = new DrColor(DrArgb.RED);
        ROSY_BROWN = new DrColor(DrArgb.ROSY_BROWN);
        ROYAL_BLUE = new DrColor(DrArgb.ROYAL_BLUE);
        SADDLE_BROWN = new DrColor(DrArgb.SADDLE_BROWN);
        SALMON = new DrColor(DrArgb.SALMON);
        SANDY_BROWN = new DrColor(DrArgb.SANDY_BROWN);
        SEA_GREEN = new DrColor(DrArgb.SEA_GREEN);

        SEA_SHELL = new DrColor(DrArgb.SEA_SHELL);
        SIENNA = new DrColor(DrArgb.SIENNA);
        SILVER = new DrColor(DrArgb.SILVER);
        SKY_BLUE = new DrColor(DrArgb.SKY_BLUE);
        SLATE_BLUE = new DrColor(DrArgb.SLATE_BLUE);
        SLATE_GRAY = new DrColor(DrArgb.SLATE_GRAY);
        SNOW = new DrColor(DrArgb.SNOW);
        SPRING_GREEN = new DrColor(DrArgb.SPRING_GREEN);
        STEEL_BLUE = new DrColor(DrArgb.STEEL_BLUE);
        TAN = new DrColor(DrArgb.TAN);

        TEAL = new DrColor(DrArgb.TEAL);
        THISTLE = new DrColor(DrArgb.THISTLE);
        TOMATO = new DrColor(DrArgb.TOMATO);
        TURQUOISE = new DrColor(DrArgb.TURQUOISE);
        VIOLET = new DrColor(DrArgb.VIOLET);
        WHEAT = new DrColor(DrArgb.WHEAT);
        WHITE = new DrColor(DrArgb.WHITE);
        WHITE_SMOKE = new DrColor(DrArgb.WHITE_SMOKE);
        YELLOW = new DrColor(DrArgb.YELLOW);
        YELLOW_GREEN = new DrColor(DrArgb.YELLOW_GREEN);

        TRANSPARENT = new DrColor(DrArgb.TRANSPARENT);
        EMPTY = new DrColor(DrArgb.EMPTY);

        // System color "Window" that should be resolved elsewhere.
        WINDOW = new DrColor(0xef, 0x11, 0x00, 0x00);

    }

}

