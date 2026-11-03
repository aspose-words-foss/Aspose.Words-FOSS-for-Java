// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msObject;


/// <summary>
/// This element specifies a color bound to a user's theme.
/// As with all elements which define a color,
/// it is possible to apply a list of color transforms to the base color defined.
/// </summary>
class DmlSchemeColor extends DmlColor
{
    /// <summary>
    /// Default ctor.
    /// </summary>
    DmlSchemeColor()
    {
    }

    /// <summary>
    /// Ctor with specifying the desired theme color.
    /// </summary>
    DmlSchemeColor(/*ThemeColor*/int value)
    {
        setValue(value);
    }

    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        if(themeProvider == null)
            return DrColor.EMPTY;

        DmlColor themeColor = themeProvider.getThemeColor(getValue());
        return themeColor.createDrColor(themeProvider, null);
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlSchemeColor result = new DmlSchemeColor(getValue());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlSchemeColor value = (DmlSchemeColor)obj;

        return msObject.equals(value.getValue(), getValue());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= /*enum.hashCode()=(int)value*/getValue();
        return hash;
    }

    /// <summary>
    /// Resolves this scheme color to a concrete RGB color using a specified theme provider.
    /// </summary>
    DmlHexRgbColor resolve(IThemeProvider themeProvider)
    {
        DrColor drColor = createUnmodifiedColor(themeProvider);
        DmlHexRgbColor dmlHexRgbColor = DmlHexRgbColor.fromDrColor(drColor);
        copyColorModifiersTo(dmlHexRgbColor);

        return dmlHexRgbColor;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.SCHEME_COLOR; }

    /// <summary>
    /// Specifies the desired scheme.
    /// </summary>
    /*ThemeColor*/int getValue() { return mValue; }
    void setValue(/*ThemeColor*/int value) { mValue = value; }

    private /*ThemeColor*/int mValue;
}

