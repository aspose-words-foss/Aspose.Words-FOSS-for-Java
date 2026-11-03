// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;


/// <summary>
/// Converts ThemeColor enumeration values to or from string.
/// </summary>
/*static*/ class ThemeColorConverter
{
	/* Simulation of static class by using private constructor */
	private ThemeColorConverter()
	{}

    static /*ThemeColor*/int fromString(String color)
    {
        if (!StringUtil.hasChars(color))
            return ThemeColor.NONE;
        
        switch (gStringSwitchMap.of(color))
        {
            case /*"accent1"*/0:
                return ThemeColor.ACCENT_1;
            case /*"accent2"*/1:
                return ThemeColor.ACCENT_2;
            case /*"accent3"*/2:
                return ThemeColor.ACCENT_3;
            case /*"accent4"*/3:
                return ThemeColor.ACCENT_4;
            case /*"accent5"*/4:
                return ThemeColor.ACCENT_5;
            case /*"accent6"*/5:
                return ThemeColor.ACCENT_6;
            case /*"dk1"*/6:
                return ThemeColor.DARK_1;
            case /*"dk2"*/7:
                return ThemeColor.DARK_2;
            case /*"folHlink"*/8:
            case /*"followedHyperlink"*/9:
                return ThemeColor.FOLLOWED_HYPERLINK;
            case /*"hlink"*/10:
            case /*"hyperlink"*/11:
                return ThemeColor.HYPERLINK;
            case /*"lt1"*/12:
            case /*"light1"*/13:
                return ThemeColor.LIGHT_1;
            case /*"lt2"*/14:
            case /*"light2"*/15:
                return ThemeColor.LIGHT_2;
            case /*"tx1"*/16:
            case /*"text1"*/17:
                return ThemeColor.TEXT_1;
            case /*"text2"*/18:
            case /*"tx2"*/19:
                return ThemeColor.TEXT_2;
            case /*"bg1"*/20:
            case /*"background1"*/21:
                return ThemeColor.BACKGROUND_1;
            case /*"bg2"*/22:
            case /*"background2"*/23:
                return ThemeColor.BACKGROUND_2;
            default:
                return ThemeColor.DARK_1;
        }
    }

    static String toString(/*ThemeColor*/int color)
    {
        switch (color)
        {
            case ThemeColor.ACCENT_1:
                return "accent1";
            case ThemeColor.ACCENT_2:
                return "accent2";
            case ThemeColor.ACCENT_3:
                return "accent3";
            case ThemeColor.ACCENT_4:
                return "accent4";
            case ThemeColor.ACCENT_5:
                return "accent5";
            case ThemeColor.ACCENT_6:
                return "accent6";
            case ThemeColor.DARK_1:
                return "dk1";
            case ThemeColor.DARK_2:
                return "dk2";
            case ThemeColor.FOLLOWED_HYPERLINK:
                return "folHlink";
            case ThemeColor.HYPERLINK:
                return "hlink";
            case ThemeColor.LIGHT_1:
                return "lt1";
            case ThemeColor.LIGHT_2:
                return "lt2";
            case ThemeColor.TEXT_1:
                return "tx1";
            case ThemeColor.TEXT_2:
                return "tx2";
            case ThemeColor.BACKGROUND_1:
                return "bg1";
            case ThemeColor.BACKGROUND_2:
                return "bg2";
            case ThemeColor.NONE:
                return "";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "color");
        }
    }

    /// <summary>
    /// Converts a specified theme color reference to a string.
    /// </summary>
    /// <remarks>
    /// The values are restricted by the list of 17.18.97 ST_ThemeColor (Theme Color) of ISO29500-1.  
    /// </remarks>
    static String themeReferenceToString(/*ThemeColor*/int color)
    {
        switch (color)
        {
            case ThemeColor.ACCENT_1:
                return "accent1";
            case ThemeColor.ACCENT_2:
                return "accent2";
            case ThemeColor.ACCENT_3:
                return "accent3";
            case ThemeColor.ACCENT_4:
                return "accent4";
            case ThemeColor.ACCENT_5:
                return "accent5";
            case ThemeColor.ACCENT_6:
                return "accent6";
            case ThemeColor.BACKGROUND_1:
                return "background1";
            case ThemeColor.BACKGROUND_2:
                return "background2";
            case ThemeColor.DARK_1:
                return "dark1";
            case ThemeColor.DARK_2:
                return "dark2";
            case ThemeColor.FOLLOWED_HYPERLINK:
                return "followedHyperlink";
            case ThemeColor.HYPERLINK:
                return "hyperlink";
            case ThemeColor.LIGHT_1:
                return "light1";
            case ThemeColor.LIGHT_2:
                return "light2";
            case ThemeColor.TEXT_1:
                return "text1";
            case ThemeColor.TEXT_2:
                return "text2";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "color");
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"accent1",
		"accent2",
		"accent3",
		"accent4",
		"accent5",
		"accent6",
		"dk1",
		"dk2",
		"folHlink",
		"followedHyperlink",
		"hlink",
		"hyperlink",
		"lt1",
		"light1",
		"lt2",
		"light2",
		"tx1",
		"text1",
		"text2",
		"tx2",
		"bg1",
		"background1",
		"bg2",
		"background2"
	);

}

