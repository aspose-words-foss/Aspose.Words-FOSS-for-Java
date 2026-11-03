// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents the repository of text fonts.
/// </summary>
class DmlTextFontRepository
{
    /// <summary>
    /// WORDSNET-10615 In run properties font typeface is specified as "+mn-lt" (See TestJira10614).
    /// It seems it is abbreviation of MinorLatin theme font. So try to get actual font name from document theme.
    /// Actually, it would be more correct to get font name from ThemeOverride in case of Chart,
    /// but it is not very convenient with current structure, so use main document's theme.
    /// In most cases this gives correct result, will change this if customers complain.
    /// </summary>
    static String getTypeFace(String typeFace, Theme theme)
    {
        if (theme == null)
        {
            // WORDSNET-19779 If theme is not specified, and latin typeface is "+mn-lt", MS Word uses "Calibri"
            // as default typeface.
            typeFace = (TYPE_FACE_MINOR_ASCII.equals(typeFace)) ? DEFAULT_TYPE_FACE : typeFace;
            return typeFace;
        }

        IThemeProvider themeProvider = theme;

        switch (gStringSwitchMap.of(typeFace))
        {
            case /*TYPE_FACE_MINOR_ASCII*/0:
                return themeProvider.getFontName(ThemeFontCore.MINOR_ASCII);
            case /*TYPE_FACE_MINOR_EAST_ASIA*/1:
                return themeProvider.getFontName(ThemeFontCore.MINOR_EAST_ASIA);
            case /*TYPE_FACE_MINOR_BIDI*/2:
                return themeProvider.getFontName(ThemeFontCore.MINOR_BIDI);
            case /*TYPE_FACE_MAJOR_ASCII*/3:
                return themeProvider.getFontName(ThemeFontCore.MAJOR_ASCII);
            case /*TYPE_FACE_MAJOR_EAST_ASIA*/4:
                return themeProvider.getFontName(ThemeFontCore.MAJOR_EAST_ASIA);
            case /*TYPE_FACE_MAJOR_BIDI*/5:
                return themeProvider.getFontName(ThemeFontCore.MAJOR_BIDI);
            default:
                return typeFace;
        }
    }

    private static final String TYPE_FACE_MINOR_ASCII = "+mn-lt";
    private static final String TYPE_FACE_MINOR_EAST_ASIA = "+mn-ea";
    private static final String TYPE_FACE_MINOR_BIDI = "+mn-cs";
    private static final String TYPE_FACE_MAJOR_ASCII = "+mj-lt";
    private static final String TYPE_FACE_MAJOR_EAST_ASIA = "+mj-ea";
    private static final String TYPE_FACE_MAJOR_BIDI = "+mj-cs";
    private static final String DEFAULT_TYPE_FACE = "Calibri";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"+mn-lt",
		"+mn-ea",
		"+mn-cs",
		"+mj-lt",
		"+mj-ea",
		"+mj-cs"
	);

}

