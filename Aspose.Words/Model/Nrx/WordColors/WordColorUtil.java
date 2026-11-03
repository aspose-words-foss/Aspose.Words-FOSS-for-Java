// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.HSLColor;


/// <summary>
/// Helper methods to deal with colors in MS Word documents.
/// </summary>
/*static*/ class WordColorUtil
{
	/* Simulation of static class by using private constructor */
	private WordColorUtil()
	{}

    static /*Word97Color*/int colorToWord97Color(DrColor color)
    {
        return wordXPColorToWord97Color(colorToWordXPColor(color));
    }

    static DrColor word97ColorToColor(/*Word97Color*/int word97Color)
    {
        return wordXPColorToColor(word97ColorToWordXPColor(word97Color));
    }

    /// <summary>
    /// Converts WordXP 32bit color into Word97 color.
    /// Returns closest WordColor for the specified System.Drawing.Color.
    /// Not very correct, might need to replace later.
    /// </summary>
    static /*Word97Color*/int wordXPColorToWord97Color(int wordXPColor)
    {
        if (wordXPColor == WordXPColor.AUTO)
        {
            //Special case for the Auto color.
            return Word97Color.AUTO;
        }
        else
        {
            //Perform color matching.
            double bestDelta = Double.MAX_VALUE;
            /*Word97Color*/int bestWord97Color = Word97Color.BLACK;
            for (int i = 0; i < G_WORD_97_COLOR_MAP.length; i++)
            {
                double curDelta = HSLColor.colorDiff(G_WORD_97_COLOR_MAP[i], wordXPColor);
                if (curDelta <= bestDelta)
                {
                    bestWord97Color = (/*Word97Color*/int)i;
                    bestDelta = curDelta;
                }
            }
            return bestWord97Color;
        }
    }

    /// <summary>
    /// Converts Word97 color into WordXP 32bit color.
    /// </summary>
    static int word97ColorToWordXPColor(/*Word97Color*/int word97Color)
    {
        //Sometimes in documents I encounter colors which are out of Word97Color range,
        //I convert these to Automatic.
        if ((int)word97Color < G_WORD_97_COLOR_MAP.length)
            return G_WORD_97_COLOR_MAP[(int)word97Color];
        else
            return WordXPColor.AUTO;
    }

    /// <summary>
    /// Converts .NET Color into Word XP 32bit color (into Word raw color).
    /// </summary>
    static int colorToWordXPColor(DrColor color)
    {
        int result = 0;
        result |= color.getR();
        result |= (color.getG() << 8);
        result |= (color.getB() << 16);
        //Convert alpha into transparency.
        result |= (~color.getA() << 24);
        return result;
    }

    /// <summary>
    /// Converts .NET Color into COLORREF structure.
    /// </summary>
    /// <remarks>
    /// See [MS-DOC] 2.9.43 COLORREF.
    /// </remarks>
    static /*uint*/int colorToColorRef(DrColor color)
    {
        if (color.isEmpty())
            return (int) 0xff000000L;

        // WORDSNET-13087 fAuto MUST be 0 at least for shading. Otherwise Word shows error message and unable to load complete document.
        return (/*uint*/int)(color.getR() | (color.getG() << 8) | (color.getB() << 16));
    }

    /// <summary>
    /// Converts Word 32bit color (Word raw color) into .NET Color.
    /// I call Word 32bit color "raw color". Word32 bit color layout is different from .Net color.
    /// It has R, G, B and T. R is the low bits, T is the high bits. 
    /// T is transparency.
    /// </summary>
    static DrColor wordXPColorToColor(int officeXPColor)
    {
        int r = (officeXPColor & 0x000000FF);
        int g = ((officeXPColor >> 8) & 0x000000FF);
        int b = ((officeXPColor >> 16) & 0x000000FF);
        int t = ((officeXPColor >> 24) & 0x000000FF);
        //It looks like in Word file it is transparency, 
        //but in windows it is alpha, so I need to invert it.
        int a = ~t & 0x000000FF;
        return new DrColor(a, r, g, b);
    }

    /// <summary>
    /// Returns the extended color modifier from MSOTINTSHADE OfficeArt record.
    /// </summary>
    /// <dev>
    /// SPEC: [MS-ODRAW].
    /// 2.2.3 MSOSHADE record.
    /// (16 bits): 0x01F4 MSOSHADE flag.
    /// (8 bits):  0xXX shade value.
    /// (8 bits):  0x10 MSOTINTSHADE flag.
    /// 2.2.4 MSOTINT record.
    /// (16 bits): 0x02F4 MSOTINT flag.
    /// (8 bits):  0xXX tint value.
    /// (8 bits):  0x10 MSOTINTSHADE flag.
    /// </dev>
    static int msoRecordToExtColorModifier(int value)
    {
        // Checks MSOTINTSHADE flags.
        if (((value >> 24) == 0x10) && ((((byte)value) & 0xFF) == 0xf4))
            // Returns the modifier as a positive value if MSOTINT flag is set, and as a negative one otherwise.
            return (/*ushort*/short)value == 0x02f4
                ? (value >> 16 & 0xff)
                : -(value >> 16 & 0xff);

        return 0;
    }

    /// <summary>
    /// Convert extended color modifiers to MSOTINTSHADE OfficeArt record.
    /// </summary>
    /// <dev>
    /// SPEC: Similar with <see cref="MsoRecordToExtColorModifier"/>.
    /// </dev>
    static int extColorModifierToMsoRecord(int modifier)
    {
        // Checks whether the value is within the allowed tint range.
        if ((modifier >= 0) && (modifier < 256))
            // Returns the tint modifier value with the required MSOTINT and MSOTINTSHADE flags.
            return ((modifier | 0x1000) << 16) | 0x02f4;

        // Checks whether the value is within the allowed shade range.
        if ((modifier < 0) && (modifier > -256))
            // Returns the shade modifier value with the required MSOSHADE and MSOTINTSHADE flags.
            return ((-modifier | 0x1000) << 16) | 0x01f4;

        return 0;
    }

    private static /*final*/ int[] G_WORD_97_COLOR_MAP;

    static/* WordColorUtil()*/
    {
        G_WORD_97_COLOR_MAP = new int[] 
        {
            WordXPColor.AUTO,
            WordXPColor.BLACK,
            WordXPColor.BLUE,
            WordXPColor.CYAN,
            WordXPColor.GREEN,
            WordXPColor.MAGENTA,
            WordXPColor.RED,
            WordXPColor.YELLOW,
            WordXPColor.WHITE,
            WordXPColor.DARK_BLUE,
            WordXPColor.DARK_CYAN,
            WordXPColor.DARK_GREEN,
            WordXPColor.DARK_MAGENTA,
            WordXPColor.DARK_RED,
            WordXPColor.DARK_YELLOW,
            WordXPColor.DARK_GRAY,
            WordXPColor.LIGHT_GRAY
        };
    }
}

