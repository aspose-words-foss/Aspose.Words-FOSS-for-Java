// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Converts ThemeFont enumeration values to string.
/// </summary>
/*static*/ class ThemeFontConverter
{
	/* Simulation of static class by using private constructor */
	private ThemeFontConverter()
	{}

    static String toString(/*ThemeFontCore*/int enumValue)
    {
        switch (enumValue)
        {
            case ThemeFontCore.NONE:
                return "None";

            case ThemeFontCore.MAJOR_ASCII:
                return "MajorAscii";
            case ThemeFontCore.MAJOR_BIDI:
                return "MajorBidi";
            case ThemeFontCore.MAJOR_EAST_ASIA:
                return "MajorEastAsia";
            case ThemeFontCore.MAJOR_H_ANSI:
                return "MajorHAnsi";

            case ThemeFontCore.MINOR_ASCII:
                return "MinorAscii";
            case ThemeFontCore.MINOR_BIDI:
                return "MinorBidi";
            case ThemeFontCore.MINOR_EAST_ASIA:
                return "MinorEastAsia";
            case ThemeFontCore.MINOR_H_ANSI:
                return "MinorHAnsi";


            case ThemeFontCore.GROUP_MASK:
                return "GroupMask";
            case ThemeFontCore.MAJOR:
                return "Major";
            case ThemeFontCore.MINOR:
                return "Minor";

            case ThemeFontCore.REGION_MASK:
                return "RegionMask";
            case ThemeFontCore.ASCII:
                return "Ascii";
            case ThemeFontCore.BIDI:
                return "Bidi";
            case ThemeFontCore.EAST_ASIA:
                return "EastAsia";
            case ThemeFontCore.H_ANSI:
                return "HAnsi";

            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "Unknown ThemeFont value, please update ThemeFontHelper.ToString().");
        }
    }
}

