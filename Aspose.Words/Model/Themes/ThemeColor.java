// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the theme colors for document themes.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-styles-and-themes/">Working with Styles and Themes</a> documentation article.</para>
/// </summary>
/// <remarks>
/// The specified theme color is a reference to one of the predefined theme colors, located in the
/// document's Theme part, which allows color information to be set centrally in the document.
/// </remarks>
/// <dev>
/// The Color Scheme Color elements appear in a sequence.
/// See also https://docs.microsoft.com/en-us/office/vba/api/word.wdthemecolorindex
/// </dev>
public /*enum*/ final class ThemeColor
{
    private ThemeColor(){}
    
    /// <summary>
    /// No color.
    /// </summary>
    public static final int NONE = -1;

    /// <summary>
    /// Dark main color 1.
    /// </summary>
    public static final int DARK_1 = 0;

    /// <summary>
    /// Light main color 1.
    /// </summary>
    public static final int LIGHT_1 = 1;

    /// <summary>
    /// Dark main color 2.
    /// </summary>
    public static final int DARK_2 = 2;

    /// <summary>
    /// Light main color 2.
    /// </summary>
    public static final int LIGHT_2 = 3;

    /// <summary>
    /// Accent color 1.
    /// </summary>
    public static final int ACCENT_1 = 4;

    /// <summary>
    /// Accent color 2.
    /// </summary>
    public static final int ACCENT_2 = 5;

    /// <summary>
    /// Accent color 3.
    /// </summary>
    public static final int ACCENT_3 = 6;

    /// <summary>
    /// Accent color 4.
    /// </summary>
    public static final int ACCENT_4 = 7;

    /// <summary>
    /// Accent color 5.
    /// </summary>
    public static final int ACCENT_5 = 8;

    /// <summary>
    /// Accent color 6.
    /// </summary>
    public static final int ACCENT_6 = 9;

    /// <summary>
    /// Hyperlink color.
    /// </summary>
    public static final int HYPERLINK = 10;

    /// <summary>
    /// Followed hyperlink color.
    /// </summary>
    public static final int FOLLOWED_HYPERLINK = 11;

    /// <summary>
    /// Text color 1.
    /// </summary>
    public static final int TEXT_1 = 12;

    /// <summary>
    /// Text color 2.
    /// </summary>
    public static final int TEXT_2 = 13;

    /// <summary>
    /// Background color 1.
    /// </summary>
    public static final int BACKGROUND_1 = 14;

    /// <summary>
    /// Background color 2.
    /// </summary>
    public static final int BACKGROUND_2 = 15;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ThemeColor value.
    /// </summary>
    public static String getName(/*ThemeColor*/int themeColor)
    {
    	switch (themeColor)
    	{
    		case NONE: return "NONE";
    		case DARK_1: return "DARK_1";
    		case LIGHT_1: return "LIGHT_1";
    		case DARK_2: return "DARK_2";
    		case LIGHT_2: return "LIGHT_2";
    		case ACCENT_1: return "ACCENT_1";
    		case ACCENT_2: return "ACCENT_2";
    		case ACCENT_3: return "ACCENT_3";
    		case ACCENT_4: return "ACCENT_4";
    		case ACCENT_5: return "ACCENT_5";
    		case ACCENT_6: return "ACCENT_6";
    		case HYPERLINK: return "HYPERLINK";
    		case FOLLOWED_HYPERLINK: return "FOLLOWED_HYPERLINK";
    		case TEXT_1: return "TEXT_1";
    		case TEXT_2: return "TEXT_2";
    		case BACKGROUND_1: return "BACKGROUND_1";
    		case BACKGROUND_2: return "BACKGROUND_2";
    		default: return "Unknown ThemeColor value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ThemeColor value.
    /// </summary>
    public static String toString(/*ThemeColor*/int themeColor)
    {
    	switch (themeColor)
    	{
    		case NONE: return "None";
    		case DARK_1: return "Dark1";
    		case LIGHT_1: return "Light1";
    		case DARK_2: return "Dark2";
    		case LIGHT_2: return "Light2";
    		case ACCENT_1: return "Accent1";
    		case ACCENT_2: return "Accent2";
    		case ACCENT_3: return "Accent3";
    		case ACCENT_4: return "Accent4";
    		case ACCENT_5: return "Accent5";
    		case ACCENT_6: return "Accent6";
    		case HYPERLINK: return "Hyperlink";
    		case FOLLOWED_HYPERLINK: return "FollowedHyperlink";
    		case TEXT_1: return "Text1";
    		case TEXT_2: return "Text2";
    		case BACKGROUND_1: return "Background1";
    		case BACKGROUND_2: return "Background2";
    		default: return "Unknown ThemeColor value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ThemeColor name.
    /// </summary>
    public static /*ThemeColor*/int fromName(String themeColorName)
    {
    	if ("NONE".equals(themeColorName)) return NONE;
    	if ("DARK_1".equals(themeColorName)) return DARK_1;
    	if ("LIGHT_1".equals(themeColorName)) return LIGHT_1;
    	if ("DARK_2".equals(themeColorName)) return DARK_2;
    	if ("LIGHT_2".equals(themeColorName)) return LIGHT_2;
    	if ("ACCENT_1".equals(themeColorName)) return ACCENT_1;
    	if ("ACCENT_2".equals(themeColorName)) return ACCENT_2;
    	if ("ACCENT_3".equals(themeColorName)) return ACCENT_3;
    	if ("ACCENT_4".equals(themeColorName)) return ACCENT_4;
    	if ("ACCENT_5".equals(themeColorName)) return ACCENT_5;
    	if ("ACCENT_6".equals(themeColorName)) return ACCENT_6;
    	if ("HYPERLINK".equals(themeColorName)) return HYPERLINK;
    	if ("FOLLOWED_HYPERLINK".equals(themeColorName)) return FOLLOWED_HYPERLINK;
    	if ("TEXT_1".equals(themeColorName)) return TEXT_1;
    	if ("TEXT_2".equals(themeColorName)) return TEXT_2;
    	if ("BACKGROUND_1".equals(themeColorName)) return BACKGROUND_1;
    	if ("BACKGROUND_2".equals(themeColorName)) return BACKGROUND_2;
    	throw new IllegalArgumentException("Unknown ThemeColor name.");
    }
    
    /// <summary>
    /// Returns array of ThemeColor values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		DARK_1,
    		LIGHT_1,
    		DARK_2,
    		LIGHT_2,
    		ACCENT_1,
    		ACCENT_2,
    		ACCENT_3,
    		ACCENT_4,
    		ACCENT_5,
    		ACCENT_6,
    		HYPERLINK,
    		FOLLOWED_HYPERLINK,
    		TEXT_1,
    		TEXT_2,
    		BACKGROUND_1,
    		BACKGROUND_2,
    	};
    }

    public static final int length = 17;
}

