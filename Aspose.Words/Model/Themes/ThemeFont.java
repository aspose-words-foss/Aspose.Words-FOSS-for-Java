// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the types of theme font names for document themes.
/// </summary>
/// <remarks>
/// Specifies a theme font type which can be referenced as a theme font within the parent object properties.
/// This theme font is a reference to one of the predefined theme fonts, located in the document's
/// Theme part, which allows for font information to be set centrally in the document.
/// </remarks>
/// <dev>
/// See https://docs.microsoft.com/en-us/office/vba/api/excel.font.themefont
/// and ISO29500 17.18.96 ST_Theme (Theme Font).
/// </dev>
public /*enum*/ final class ThemeFont
{
    private ThemeFont(){}
    
    /// <summary>
    /// No theme font.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Major theme font.
    /// </summary>
    public static final int MAJOR = 1;

    /// <summary>
    /// Minor theme font.
    /// </summary>
    public static final int MINOR = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ThemeFont value.
    /// </summary>
    public static String getName(/*ThemeFont*/int themeFont)
    {
    	switch (themeFont)
    	{
    		case NONE: return "NONE";
    		case MAJOR: return "MAJOR";
    		case MINOR: return "MINOR";
    		default: return "Unknown ThemeFont value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ThemeFont value.
    /// </summary>
    public static String toString(/*ThemeFont*/int themeFont)
    {
    	switch (themeFont)
    	{
    		case NONE: return "None";
    		case MAJOR: return "Major";
    		case MINOR: return "Minor";
    		default: return "Unknown ThemeFont value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ThemeFont name.
    /// </summary>
    public static /*ThemeFont*/int fromName(String themeFontName)
    {
    	if ("NONE".equals(themeFontName)) return NONE;
    	if ("MAJOR".equals(themeFontName)) return MAJOR;
    	if ("MINOR".equals(themeFontName)) return MINOR;
    	throw new IllegalArgumentException("Unknown ThemeFont name.");
    }
    
    /// <summary>
    /// Returns array of ThemeFont values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		MAJOR,
    		MINOR,
    	};
    }

    public static final int length = 3;
}

