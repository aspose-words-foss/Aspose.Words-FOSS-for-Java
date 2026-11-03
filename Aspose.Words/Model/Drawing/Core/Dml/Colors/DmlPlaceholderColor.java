// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// This color can appear in themes. It is 
/// represented by scheme color with value "phClr".
/// </summary>
class DmlPlaceholderColor extends DmlColor
{
    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        if (getStyleColor() != null)
            return getStyleColor().createDrColor(themeProvider, null);
        else
            return DrColor.EMPTY;
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlPlaceholderColor result = new DmlPlaceholderColor();
        if (getStyleColor() != null)
            result.setStyleColor(getStyleColor().deepClone());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.PLACEHOLDER_COLOR; }

    public /*override*/ void applyStyleColor(DmlColor styleColor)
    {
        setStyleColor(styleColor);
    }

    /// <summary>
    /// Color assigned from style to the placeholder.
    /// </summary>
    DmlColor getStyleColor() { return mStyleColor; }
    void setStyleColor(DmlColor value) { mStyleColor = value; }

    /// <summary>
    /// Returns true, if style color is null.
    /// </summary>
    /*override*/ boolean isEmpty() { return (mStyleColor == null); }

    private DmlColor mStyleColor;
}

