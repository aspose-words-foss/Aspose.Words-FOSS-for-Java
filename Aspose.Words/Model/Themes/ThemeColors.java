// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.msString;


/// <summary>
/// <p>Represents the color scheme of the document theme which contains twelve colors.</p>
/// <p><see cref="ThemeColors"/> object contains six accent colors, two dark colors, two light colors 
/// and a color for each of a hyperlink and followed hyperlink.</p>
/// </summary>
/// <dev>
/// 20.1.6.2 clrScheme (Color Scheme)
/// Defines a set of colors which are referred to as a color scheme. The Color Scheme Color elements appear in a sequence. 
/// </dev>
public class ThemeColors implements Cloneable
{
    ThemeColors(IThemeProvider theme)
    {
        mTheme = theme;
    }

    /// <summary>
    /// Specifies color Accent 1.
    /// </summary>
    public Color getAccent1() { return getNativeColor(ThemeColor.ACCENT_1); }
    public void setAccent1(Color value) { setNativeColor(ThemeColor.ACCENT_1, value); }

    /// <summary>
    /// Specifies color Accent 2.
    /// </summary>
    public Color getAccent2() { return getNativeColor(ThemeColor.ACCENT_2); }
    public void setAccent2(Color value) { setNativeColor(ThemeColor.ACCENT_2, value); }

    /// <summary>
    /// Specifies color Accent 3.
    /// </summary>
    public Color getAccent3() { return getNativeColor(ThemeColor.ACCENT_3); }
    public void setAccent3(Color value) { setNativeColor(ThemeColor.ACCENT_3, value); }

    /// <summary>
    /// Specifies color Accent 4.
    /// </summary>
    public Color getAccent4() { return getNativeColor(ThemeColor.ACCENT_4); }
    public void setAccent4(Color value) { setNativeColor(ThemeColor.ACCENT_4, value); }

    /// <summary>
    /// Specifies color Accent 5.
    /// </summary>
    public Color getAccent5() { return getNativeColor(ThemeColor.ACCENT_5); }
    public void setAccent5(Color value) { setNativeColor(ThemeColor.ACCENT_5, value); }

    /// <summary>
    /// Specifies color Accent 6.
    /// </summary>
    public Color getAccent6() { return getNativeColor(ThemeColor.ACCENT_6); }
    public void setAccent6(Color value) { setNativeColor(ThemeColor.ACCENT_6, value); }

    /// <summary>
    /// Specifies color Dark 1.
    /// </summary>
    public Color getDark1() { return getNativeColor(ThemeColor.DARK_1); }
    public void setDark1(Color value) { setNativeColor(ThemeColor.DARK_1, value); }

    /// <summary>
    /// Specifies color Dark 2.
    /// </summary>
    public Color getDark2() { return getNativeColor(ThemeColor.DARK_2); }
    public void setDark2(Color value) { setNativeColor(ThemeColor.DARK_2, value); }

    /// <summary>
    /// Specifies color for a clicked hyperlink.
    /// </summary>
    public Color getFollowedHyperlink() { return getNativeColor(ThemeColor.FOLLOWED_HYPERLINK); }
    public void setFollowedHyperlink(Color value) { setNativeColor(ThemeColor.FOLLOWED_HYPERLINK, value); }

    /// <summary>
    /// Specifies color for a hyperlink.
    /// </summary>
    public Color getHyperlink() { return getNativeColor(ThemeColor.HYPERLINK); }
    public void setHyperlink(Color value) { setNativeColor(ThemeColor.HYPERLINK, value); }

    /// <summary>
    /// Specifies color Light 1.
    /// </summary>
    public Color getLight1() { return getNativeColor(ThemeColor.LIGHT_1); }
    public void setLight1(Color value) { setNativeColor(ThemeColor.LIGHT_1, value); }

    /// <summary>
    /// Specifies color Light 2.
    /// </summary>
    public Color getLight2() { return getNativeColor(ThemeColor.LIGHT_2); }
    public void setLight2(Color value) { setNativeColor(ThemeColor.LIGHT_2, value); }

    /// <summary>
    /// Clones this instance of theme colors.
    /// </summary>
    ThemeColors deepClone()
    {
        ThemeColors lhs = (ThemeColors)memberwiseClone();

        lhs.mColors = new DmlColor[THEME_COLOR_COUNT];
        for (int i = 0; i < THEME_COLOR_COUNT; i++)
        {
            if (mColors[i] != null)
                lhs.mColors[i] = mColors[i].deepClone();
        }

        return lhs;
    }

    /// <summary>
    /// Sets parent theme of this theme colors.
    /// </summary>
    void setTheme(IThemeProvider theme)
    {
        mTheme = theme;
    }

    /// <summary>
    /// Adds a color. If a color with the specified name already exists then
    /// the existing color will be overwritten.
    /// </summary>
    void addColor(DmlColor color, /*ThemeColor*/int colorName)
    {
        mColors[(int)colorName] = color;
    }

    /// <summary>
    /// Gets a color by its name. Returns null if a color with the specified
    /// name doesn't exist.
    /// </summary>
    DmlColor getColor(/*ThemeColor*/int colorName)
    {
        colorName = mapColors(colorName);
        return mColors[(int)colorName];
    }

    /// <summary>
    /// Gets a color by its string representation in Xml (theme color name). Returns null if a color with the specified
    /// string representation doesn't exist.
    /// </summary>
    DmlColor getColor(String themeColorStr)
    {
        return getColor(ThemeColorConverter.fromString(themeColorStr));
    }

    /// <summary>
    /// The common name for this color scheme. 
    /// This name can show up in the user interface in a list of color schemes.
    /// </summary>
    String getName()
    {
        if (mName == null)
            mName = "";
        return mName;
    }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Maps semantic colors to actual theme colors.
    /// </summary>
    private static /*ThemeColor*/int mapColors(/*ThemeColor*/int color)
    {
        switch (color)
        {
            case ThemeColor.TEXT_1:
                return ThemeColor.DARK_1;
            case ThemeColor.TEXT_2:
                return ThemeColor.DARK_2;
            case ThemeColor.BACKGROUND_1:
                return ThemeColor.LIGHT_1;
            case ThemeColor.BACKGROUND_2:
                return ThemeColor.LIGHT_2;
            default:
                return color;
        }
    }

    private Color getNativeColor(/*ThemeColor*/int colorIndex)
    {
        DmlColor dmlColor = getColor(colorIndex);
        return (dmlColor == null) ? msColor.Empty : dmlColor.createDrColor(mTheme, null).toNativeColor();
    }

    private void setNativeColor(/*ThemeColor*/int colorIndex, Color color)
    {
        DmlHexRgbColor newDmlColor = new DmlHexRgbColor(msString.format("{0:x2}{1:x2}{2:x2}", (color.getRed() & 0xFF), (color.getGreen() & 0xFF), (color.getBlue() & 0xFF)));
        addColor(newDmlColor, colorIndex);
        mIsModified = true;

        // WORDSNET-15915 Update document immediately.
        mTheme.onChange();
    }

    /// <summary>
    /// Indicates that the object was modified by public accessors.
    /// </summary>
    boolean isModified() { return mIsModified; }

    /// <summary>
    /// Indicates whether all colors are set.
    /// </summary>
    boolean getAreAllColorsSet()
    {
        for (/*ThemeColor*/int themeColor : ALL_THEME_COLORS)
            if (getColor(themeColor) == null)
                return false;

        return true;
    }

    /// <summary>
    /// Possible theme colors in order of appearance.
    /// </summary>
    static /*final*/ /*ThemeColor*/int[] ALL_THEME_COLORS = new /*ThemeColor*/int[]
    {
        ThemeColor.DARK_1, ThemeColor.LIGHT_1, ThemeColor.DARK_2, ThemeColor.LIGHT_2,
        ThemeColor.ACCENT_1, ThemeColor.ACCENT_2, ThemeColor.ACCENT_3, ThemeColor.ACCENT_4, ThemeColor.ACCENT_5, ThemeColor.ACCENT_6,
        ThemeColor.BACKGROUND_1, ThemeColor.BACKGROUND_2,
        ThemeColor.HYPERLINK, ThemeColor.FOLLOWED_HYPERLINK,
        ThemeColor.TEXT_1, ThemeColor.TEXT_2
    };

    private boolean mIsModified;
    private DmlColor[] mColors = new DmlColor[THEME_COLOR_COUNT];
    private String mName;

    private IThemeProvider mTheme;
    private static final int THEME_COLOR_COUNT = 17;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

