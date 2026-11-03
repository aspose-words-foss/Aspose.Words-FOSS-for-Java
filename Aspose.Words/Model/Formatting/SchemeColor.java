// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2008 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This is likely to become public in the future, when we rework colors.
/// </summary>
/*enum*/ final class SchemeColor
{
    private SchemeColor(){}
    
    /// <summary>
    /// Extra scheme color 1.
    /// </summary>
    public static final int ACCENT_1 = 0;
    /// <summary>
    /// Extra scheme color 2.
    /// </summary>
    public static final int ACCENT_2 = 1;
    /// <summary>
    /// Extra scheme color 3.
    /// </summary>
    public static final int ACCENT_3 = 2;
    /// <summary>
    /// Extra scheme color 4.
    /// </summary>
    public static final int ACCENT_4 = 3;
    /// <summary>
    /// Extra scheme color 5.
    /// </summary>
    public static final int ACCENT_5 = 4;
    /// <summary>
    /// Extra scheme color 6.
    /// </summary>
    public static final int ACCENT_6 = 5;
    /// <summary>
    /// Semantic background color.
    /// </summary>
    public static final int BACKGROUND_1 = 6;
    /// <summary>
    /// Semantic additional background color.
    /// </summary>
    public static final int BACKGROUND_2 = 7;
    /// <summary>
    /// Main dark color 1.
    /// </summary>
    public static final int DARK_1 = 8;
    /// <summary>
    /// Main dark color 2.
    /// </summary>
    public static final int DARK_2 = 9;
    /// <summary>
    /// Followed Hyperlink Color.
    /// </summary>
    public static final int FOLLOWED_HYPERLINK = 10;
    /// <summary>
    /// Regular Hyperlink Color.
    /// </summary>
    public static final int HYPERLINK = 11;
    /// <summary>
    /// Main Light Color 1.
    /// </summary>
    public static final int LIGHT_1 = 12;
    /// <summary>
    /// Main Light Color 2.
    /// </summary>
    public static final int LIGHT_2 = 13;
    /// <summary>
    /// A color used in theme definitions which means to use the color of the style.
    /// </summary>
    public static final int STYLE = 14;
    /// <summary>
    /// Semantic text color.
    /// </summary>
    public static final int TEXT_1 = 15;
    /// <summary>
    /// Semantic additional text color.
    /// </summary>
    public static final int TEXT_2 = 16;

    public static final int DEFAULT = TEXT_1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SchemeColor value.
    /// </summary>
    public static String getName(/*SchemeColor*/int schemeColor)
    {
    	switch (schemeColor)
    	{
    		case ACCENT_1: return "ACCENT_1";
    		case ACCENT_2: return "ACCENT_2";
    		case ACCENT_3: return "ACCENT_3";
    		case ACCENT_4: return "ACCENT_4";
    		case ACCENT_5: return "ACCENT_5";
    		case ACCENT_6: return "ACCENT_6";
    		case BACKGROUND_1: return "BACKGROUND_1";
    		case BACKGROUND_2: return "BACKGROUND_2";
    		case DARK_1: return "DARK_1";
    		case DARK_2: return "DARK_2";
    		case FOLLOWED_HYPERLINK: return "FOLLOWED_HYPERLINK";
    		case HYPERLINK: return "HYPERLINK";
    		case LIGHT_1: return "LIGHT_1";
    		case LIGHT_2: return "LIGHT_2";
    		case STYLE: return "STYLE";
    		case TEXT_1:
    		/*case DEFAULT:*/
    			return "TEXT_1" + " | " + "DEFAULT";
    		case TEXT_2: return "TEXT_2";
    		default: return "Unknown SchemeColor value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SchemeColor value.
    /// </summary>
    public static String toString(/*SchemeColor*/int schemeColor)
    {
    	switch (schemeColor)
    	{
    		case ACCENT_1: return "Accent1";
    		case ACCENT_2: return "Accent2";
    		case ACCENT_3: return "Accent3";
    		case ACCENT_4: return "Accent4";
    		case ACCENT_5: return "Accent5";
    		case ACCENT_6: return "Accent6";
    		case BACKGROUND_1: return "Background1";
    		case BACKGROUND_2: return "Background2";
    		case DARK_1: return "Dark1";
    		case DARK_2: return "Dark2";
    		case FOLLOWED_HYPERLINK: return "FollowedHyperlink";
    		case HYPERLINK: return "Hyperlink";
    		case LIGHT_1: return "Light1";
    		case LIGHT_2: return "Light2";
    		case STYLE: return "Style";
    		case TEXT_1:
    		/*case DEFAULT:*/
    			return "Text1" + " | " + "Default";
    		case TEXT_2: return "Text2";
    		default: return "Unknown SchemeColor value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SchemeColor name.
    /// </summary>
    public static /*SchemeColor*/int fromName(String schemeColorName)
    {
    	if ("ACCENT_1".equals(schemeColorName)) return ACCENT_1;
    	if ("ACCENT_2".equals(schemeColorName)) return ACCENT_2;
    	if ("ACCENT_3".equals(schemeColorName)) return ACCENT_3;
    	if ("ACCENT_4".equals(schemeColorName)) return ACCENT_4;
    	if ("ACCENT_5".equals(schemeColorName)) return ACCENT_5;
    	if ("ACCENT_6".equals(schemeColorName)) return ACCENT_6;
    	if ("BACKGROUND_1".equals(schemeColorName)) return BACKGROUND_1;
    	if ("BACKGROUND_2".equals(schemeColorName)) return BACKGROUND_2;
    	if ("DARK_1".equals(schemeColorName)) return DARK_1;
    	if ("DARK_2".equals(schemeColorName)) return DARK_2;
    	if ("FOLLOWED_HYPERLINK".equals(schemeColorName)) return FOLLOWED_HYPERLINK;
    	if ("HYPERLINK".equals(schemeColorName)) return HYPERLINK;
    	if ("LIGHT_1".equals(schemeColorName)) return LIGHT_1;
    	if ("LIGHT_2".equals(schemeColorName)) return LIGHT_2;
    	if ("STYLE".equals(schemeColorName)) return STYLE;
    	if ("TEXT_1".equals(schemeColorName)) return TEXT_1;
    	if ("TEXT_2".equals(schemeColorName)) return TEXT_2;
    	if ("DEFAULT".equals(schemeColorName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown SchemeColor name.");
    }
    
    /// <summary>
    /// Returns array of SchemeColor values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ACCENT_1,
    		ACCENT_2,
    		ACCENT_3,
    		ACCENT_4,
    		ACCENT_5,
    		ACCENT_6,
    		BACKGROUND_1,
    		BACKGROUND_2,
    		DARK_1,
    		DARK_2,
    		FOLLOWED_HYPERLINK,
    		HYPERLINK,
    		LIGHT_1,
    		LIGHT_2,
    		STYLE,
    		TEXT_1,
    		TEXT_2,
    		DEFAULT,
    	};
    }

    public static final int length = 18;
}

