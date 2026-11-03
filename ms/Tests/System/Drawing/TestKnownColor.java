// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2007 by Konstantin Sidorenko
// 2016/01/27 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.KnownColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// System.Drawing.KnownColor names and values are hardcoded in java com.aspose.ms.System.Drawing.KnownColor.
/// When .Net original is changed - hardcoded tables in this test and java's KnownColor should be updated as well.
/// So, please call java team if some of tests fail.
/// </summary>
@Test (enabled = false, description = "Used for Java porting only.")
public class TestKnownColor
{
    /// <summary>
    /// Compares a number of colors in KnownColor Enum in .Net and Java.
    /// </summary>
    @Test (enabled = false)
    public void testSize()
    {
        // Preprocessor needed for the tests since java' KnownColor is enum 
        // (not final class with integer constans) so it non-autportable. 
        int length = KnownColor.values().length;
        msAssert.areEqual(length, G_KNOWN_COLOR_NAMES.length);
        msAssert.areEqual(length, G_KNOWN_COLOR_VALUES.length);
    }

    /// <summary>
    /// Compares current system .Net KnownColor names/values and hardcoded Java analogs.
    /// </summary>
    @Test (enabled = false)
    public void testValues()
    {
        for (int ordinal = 1; ordinal < G_KNOWN_COLOR_NAMES.length; ordinal++)
        {
            String colorName = KnownColor.get(G_KNOWN_COLOR_NAMES[ordinal]).name();
            int colorValue = KnownColor.get(colorName).value();
            try
            {
                msAssert.areEqual(colorName, G_KNOWN_COLOR_NAMES[ordinal]);
                msAssert.areEqual(colorValue, (int)G_KNOWN_COLOR_VALUES[ordinal]);
            }
            catch (AssertionError e)
            {
                // "Desktop" KnownColor value can vary depending on local system personalization settings.
                if ("Desktop".equals(colorName))
                    break;

                throw new AssertionError(msString.format(
                    "Please update gKnownColorNames/gKnownColorValues! The tables are used to update Java's hardcoded KnownColor class after autoporting.\r\n" +
                    "ordinal: {0}\r\n" +
                    "colorName/colorValue                       : {1}/{2}\r\n" +
                    "gKnownColorNames/gKnownColorValues[ordinal]: {3}/{4})",
                    ordinal, colorName, FormatterPal.intToStrX(colorValue),
                    G_KNOWN_COLOR_NAMES[ordinal], FormatterPal.intToStrX((int)G_KNOWN_COLOR_VALUES[ordinal])));
            }
        }
    }

    // Names from .Net KnownColor implementation.
    private static /*final*/ String[] G_KNOWN_COLOR_NAMES = new String[]
    {
        "None",
        // Special colors.
        "ActiveBorder",
        "ActiveCaption",
        "ActiveCaptionText",
        "AppWorkspace",
        "Control",
        "ControlDark",
        "ControlDarkDark",
        "ControlLight",
        "ControlLightLight",
        "ControlText",
        "Desktop",
        "GrayText",
        "Highlight",
        "HighlightText",
        "HotTrack",
        "InactiveBorder",
        "InactiveCaption",
        "InactiveCaptionText",
        "Info",
        "InfoText",
        "Menu",
        "MenuText",
        "ScrollBar",
        "Window",
        "WindowFrame",
        "WindowText",

        // Specific colors.
        "Transparent",
        "AliceBlue",
        "AntiqueWhite",
        "Aqua",
        "Aquamarine",
        "Azure",
        "Beige",
        "Bisque",
        "Black",
        "BlanchedAlmond",
        "Blue",
        "BlueViolet",
        "Brown",
        "BurlyWood",
        "CadetBlue",
        "Chartreuse",
        "Chocolate",
        "Coral",
        "CornflowerBlue",
        "Cornsilk",
        "Crimson",
        "Cyan",
        "DarkBlue",
        "DarkCyan",
        "DarkGoldenrod",
        "DarkGray",
        "DarkGreen",
        "DarkKhaki",
        "DarkMagenta",
        "DarkOliveGreen",
        "DarkOrange",
        "DarkOrchid",
        "DarkRed",
        "DarkSalmon",
        "DarkSeaGreen",
        "DarkSlateBlue",
        "DarkSlateGray",
        "DarkTurquoise",
        "DarkViolet",
        "DeepPink",
        "DeepSkyBlue",
        "DimGray",
        "DodgerBlue",
        "Firebrick",
        "FloralWhite",
        "ForestGreen",
        "Fuchsia",
        "Gainsboro",
        "GhostWhite",
        "Gold",
        "Goldenrod",
        "Gray",
        "Green",
        "GreenYellow",
        "Honeydew",
        "HotPink",
        "IndianRed",
        "Indigo",
        "Ivory",
        "Khaki",
        "Lavender",
        "LavenderBlush",
        "LawnGreen",
        "LemonChiffon",
        "LightBlue",
        "LightCoral",
        "LightCyan",
        "LightGoldenrodYellow",
        "LightGray",
        "LightGreen",
        "LightPink",
        "LightSalmon",
        "LightSeaGreen",
        "LightSkyBlue",
        "LightSlateGray",
        "LightSteelBlue",
        "LightYellow",
        "Lime",
        "LimeGreen",
        "Linen",
        "Magenta",
        "Maroon",
        "MediumAquamarine",
        "MediumBlue",
        "MediumOrchid",
        "MediumPurple",
        "MediumSeaGreen",
        "MediumSlateBlue",
        "MediumSpringGreen",
        "MediumTurquoise",
        "MediumVioletRed",
        "MidnightBlue",
        "MintCream",
        "MistyRose",
        "Moccasin",
        "NavajoWhite",
        "Navy",
        "OldLace",
        "Olive",
        "OliveDrab",
        "Orange",
        "OrangeRed",
        "Orchid",
        "PaleGoldenrod",
        "PaleGreen",
        "PaleTurquoise",
        "PaleVioletRed",
        "PapayaWhip",
        "PeachPuff",
        "Peru",
        "Pink",
        "Plum",
        "PowderBlue",
        "Purple",
        "Red",
        "RosyBrown",
        "RoyalBlue",
        "SaddleBrown",
        "Salmon",
        "SandyBrown",
        "SeaGreen",
        "SeaShell",
        "Sienna",
        "Silver",
        "SkyBlue",
        "SlateBlue",
        "SlateGray",
        "Snow",
        "SpringGreen",
        "SteelBlue",
        "Tan",
        "Teal",
        "Thistle",
        "Tomato",
        "Turquoise",
        "Violet",
        "Wheat",
        "White",
        "WhiteSmoke",
        "Yellow",
        "YellowGreen",
        "ButtonFace",
        "ButtonHighlight",
        "ButtonShadow",
        "GradientActiveCaption",
        "GradientInactiveCaption",
        "MenuBar",
        "MenuHighlight",

    };

    // Values from .Net KnownColor implementation.
    private static /*final*/ long[] G_KNOWN_COLOR_VALUES =
    {
        // Placeholder for KnownColor value of zero.
        0x00000000,//None

        // Special colors - may be overridden by profiles.
        0xFFB4B4B4L,//ActiveBorder
        0xFF99B4D1L,//ActiveCaption
        0xFF000000L,//ActiveCaptionText
        0xFFABABABL,//AppWorkspace
        0xFFF0F0F0L,//Control
        0xFFA0A0A0L,//ControlDark
        0xFF696969L,//ControlDarkDark
        0xFFE3E3E3L,//ControlLight
        0xFFFFFFFFL,//ControlLightLight
        0xFF000000L,//ControlText
        0xFF000000L,//Desktop
        0xFF6D6D6DL,//GrayText
        0xFF3399FFL,//Highlight
        0xFFFFFFFFL,//HighlightText
        0xFF0066CCL,//HotTrack
        0xFFF4F7FCL,//InactiveBorder
        0xFFBFCDDBL,//InactiveCaption
        0xFF000000L,//InactiveCaptionText
        0xFFFFFFE1L,//Info
        0xFF000000L,//InfoText
        0xFFF0F0F0L,//Menu
        0xFF000000L,//MenuText
        0xFFC8C8C8L,//ScrollBar
        0xFFFFFFFFL,//Window
        0xFF646464L,//WindowFrame
        0xFF000000L,//WindowText

        // Specific colors.
        0xFFFFFF,//Transparent
        0xFFF0F8FFL,//AliceBlue
        0xFFFAEBD7L,//AntiqueWhite
        0xFF00FFFFL,//Aqua
        0xFF7FFFD4L,//Aquamarine
        0xFFF0FFFFL,//Azure
        0xFFF5F5DCL,//Beige
        0xFFFFE4C4L,//Bisque
        0xFF000000L,//Black
        0xFFFFEBCDL,//BlanchedAlmond
        0xFF0000FFL,//Blue
        0xFF8A2BE2L,//BlueViolet
        0xFFA52A2AL,//Brown
        0xFFDEB887L,//BurlyWood
        0xFF5F9EA0L,//CadetBlue
        0xFF7FFF00L,//Chartreuse
        0xFFD2691EL,//Chocolate
        0xFFFF7F50L,//Coral
        0xFF6495EDL,//CornflowerBlue
        0xFFFFF8DCL,//Cornsilk
        0xFFDC143CL,//Crimson
        0xFF00FFFFL,//Cyan
        0xFF00008BL,//DarkBlue
        0xFF008B8BL,//DarkCyan
        0xFFB8860BL,//DarkGoldenrod
        0xFFA9A9A9L,//DarkGray
        0xFF006400L,//DarkGreen
        0xFFBDB76BL,//DarkKhaki
        0xFF8B008BL,//DarkMagenta
        0xFF556B2FL,//DarkOliveGreen
        0xFFFF8C00L,//DarkOrange
        0xFF9932CCL,//DarkOrchid
        0xFF8B0000L,//DarkRed
        0xFFE9967AL,//DarkSalmon
        0xFF8FBC8BL,//DarkSeaGreen
        0xFF483D8BL,//DarkSlateBlue
        0xFF2F4F4FL,//DarkSlateGray
        0xFF00CED1L,//DarkTurquoise
        0xFF9400D3L,//DarkViolet
        0xFFFF1493L,//DeepPink
        0xFF00BFFFL,//DeepSkyBlue
        0xFF696969L,//DimGray
        0xFF1E90FFL,//DodgerBlue
        0xFFB22222L,//Firebrick
        0xFFFFFAF0L,//FloralWhite
        0xFF228B22L,//ForestGreen
        0xFFFF00FFL,//Fuchsia
        0xFFDCDCDCL,//Gainsboro
        0xFFF8F8FFL,//GhostWhite
        0xFFFFD700L,//Gold
        0xFFDAA520L,//Goldenrod
        0xFF808080L,//Gray
        0xFF008000L,//Green
        0xFFADFF2FL,//GreenYellow
        0xFFF0FFF0L,//Honeydew
        0xFFFF69B4L,//HotPink
        0xFFCD5C5CL,//IndianRed
        0xFF4B0082L,//Indigo
        0xFFFFFFF0L,//Ivory
        0xFFF0E68CL,//Khaki
        0xFFE6E6FAL,//Lavender
        0xFFFFF0F5L,//LavenderBlush
        0xFF7CFC00L,//LawnGreen
        0xFFFFFACDL,//LemonChiffon
        0xFFADD8E6L,//LightBlue
        0xFFF08080L,//LightCoral
        0xFFE0FFFFL,//LightCyan
        0xFFFAFAD2L,//LightGoldenrodYellow
        0xFFD3D3D3L,//LightGray
        0xFF90EE90L,//LightGreen
        0xFFFFB6C1L,//LightPink
        0xFFFFA07AL,//LightSalmon
        0xFF20B2AAL,//LightSeaGreen
        0xFF87CEFAL,//LightSkyBlue
        0xFF778899L,//LightSlateGray
        0xFFB0C4DEL,//LightSteelBlue
        0xFFFFFFE0L,//LightYellow
        0xFF00FF00L,//Lime
        0xFF32CD32L,//LimeGreen
        0xFFFAF0E6L,//Linen
        0xFFFF00FFL,//Magenta
        0xFF800000L,//Maroon
        0xFF66CDAAL,//MediumAquamarine
        0xFF0000CDL,//MediumBlue
        0xFFBA55D3L,//MediumOrchid
        0xFF9370DBL,//MediumPurple
        0xFF3CB371L,//MediumSeaGreen
        0xFF7B68EEL,//MediumSlateBlue
        0xFF00FA9AL,//MediumSpringGreen
        0xFF48D1CCL,//MediumTurquoise
        0xFFC71585L,//MediumVioletRed
        0xFF191970L,//MidnightBlue
        0xFFF5FFFAL,//MintCream
        0xFFFFE4E1L,//MistyRose
        0xFFFFE4B5L,//Moccasin
        0xFFFFDEADL,//NavajoWhite
        0xFF000080L,//Navy
        0xFFFDF5E6L,//OldLace
        0xFF808000L,//Olive
        0xFF6B8E23L,//OliveDrab
        0xFFFFA500L,//Orange
        0xFFFF4500L,//OrangeRed
        0xFFDA70D6L,//Orchid
        0xFFEEE8AAL,//PaleGoldenrod
        0xFF98FB98L,//PaleGreen
        0xFFAFEEEEL,//PaleTurquoise
        0xFFDB7093L,//PaleVioletRed
        0xFFFFEFD5L,//PapayaWhip
        0xFFFFDAB9L,//PeachPuff
        0xFFCD853FL,//Peru
        0xFFFFC0CBL,//Pink
        0xFFDDA0DDL,//Plum
        0xFFB0E0E6L,//PowderBlue
        0xFF800080L,//Purple
        0xFFFF0000L,//Red
        0xFFBC8F8FL,//RosyBrown
        0xFF4169E1L,//RoyalBlue
        0xFF8B4513L,//SaddleBrown
        0xFFFA8072L,//Salmon
        0xFFF4A460L,//SandyBrown
        0xFF2E8B57L,//SeaGreen
        0xFFFFF5EEL,//SeaShell
        0xFFA0522DL,//Sienna
        0xFFC0C0C0L,//Silver
        0xFF87CEEBL,//SkyBlue
        0xFF6A5ACDL,//SlateBlue
        0xFF708090L,//SlateGray
        0xFFFFFAFAL,//Snow
        0xFF00FF7FL,//SpringGreen
        0xFF4682B4L,//SteelBlue
        0xFFD2B48CL,//Tan
        0xFF008080L,//Teal
        0xFFD8BFD8L,//Thistle
        0xFFFF6347L,//Tomato
        0xFF40E0D0L,//Turquoise
        0xFFEE82EEL,//Violet
        0xFFF5DEB3L,//Wheat
        0xFFFFFFFFL,//White
        0xFFF5F5F5L,//WhiteSmoke
        0xFFFFFF00L,//Yellow
        0xFF9ACD32L,//YellowGreen
        0xFFF0F0F0L,//ButtonFace
        0xFFFFFFFFL,//ButtonHighlight
        0xFFA0A0A0L,//ButtonShadow
        0xFFB9D1EAL,//GradientActiveCaption
        0xFFD7E4F2L,//GradientInactiveCaption
        0xFFF0F0F0L,//MenuBar
        0xFF3399FFL,//MenuHighlight
    };
}

