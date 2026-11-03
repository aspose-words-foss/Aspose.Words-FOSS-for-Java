// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Interface providing theme objects.
/// </summary>
interface IThemeProvider
{
    public String getFontName(/*ThemeFontCore*/int themeFont);
    public DmlColor getThemeColor(/*ThemeColor*/int color);
    public DmlFill getBackgroundFillStyle(int index);
    public DmlFill getFillStyle(int index);
    public DmlOutline getLineStyle(int index);
    public EffectStyle getEffectStyle(int index);

    /// <summary>
    /// Handles theme changes.
    /// </summary>
    public void onChange();
}

