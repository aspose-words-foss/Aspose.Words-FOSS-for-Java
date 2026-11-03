// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.DrColor;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;


/// <summary>
/// This element specifies a color using the red, green, blue RGB color model.
/// Red, green, and blue is expressed as sequence of hex digits, RRGGBB.
/// A perceptual gamma of 2.2 is used.
/// </summary>
class DmlHexRgbColor extends DmlColor
{
    DmlHexRgbColor()
    {
    }

    DmlHexRgbColor(String value)
    {
        setValue(value);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlHexRgbColor value = (DmlHexRgbColor)obj;

        return msObject.equals(value.getValue(), getValue());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getValue().hashCode();
        return hash;
    }

    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        if (isEmpty())
            return DrColor.EMPTY;
        return readHexColor(getValue());
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlHexRgbColor result = new DmlHexRgbColor();
        result.setValue(getValue());
        result.mColorIndex = mColorIndex;
        copyColorModifiersTo(result);
        return result;
    }


    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.HEX_RGB_COLOR; }

    /// <summary>
    /// Initialize DrColor from following string RRGGBB
    /// </summary>
    static DrColor readHexColor(String value)
    {
        // We need to add 0xff alpha to the rgb color.
        int argb = FormatterPal.parseHex(value) | /*unchecked*/((int)(int) 0xff000000L);
        return new DrColor(argb);
    }

    static DmlHexRgbColor fromDrColor(DrColor drColor)
    {
        DmlHexRgbColor result = new DmlHexRgbColor();

        // We need to remove 0xff alpha from the drColor.
        result.setValue(FormatterPal.intToStrX8(drColor.toArgb()).substring(2));
        return result;
    }

    /// <summary>
    /// The actual color value. Expressed as a sequence of hex digits RRGGBB.
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    /// <summary>
    /// Index into a color table specified by the "indexedColors" element.
    /// </summary>
    /// <remarks>
    /// This attribute used for maintain compatibility with implementations
    /// of Office Open XML file formats. When present in the context of a spreadsheet application,
    /// this attribute overrides any other color information present under its parent.
    /// </remarks>
    String getColorIndex() { return mColorIndex;  }
    void setColorIndex(String value) { mColorIndex = value; }

    /// <summary>
    /// Returns true, if this color value is empty or null.
    /// </summary>
    /*override*/ boolean isEmpty() { return !StringUtil.hasChars(mValue); }

    private String mValue = "";
    private String mColorIndex;
}

