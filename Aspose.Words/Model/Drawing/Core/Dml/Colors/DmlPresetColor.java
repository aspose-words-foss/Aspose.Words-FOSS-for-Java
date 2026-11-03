// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.DrKnownColors;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;


/// <summary>
/// This element specifies a color which is bound to one of a predefined collection of colors.
/// </summary>
class DmlPresetColor extends DmlColor
{
    DmlPresetColor()
    {
    }

    DmlPresetColor(String value)
    {
        mValue = value;
    }

    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        return DrKnownColors.fromName(getValue());
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlPresetColor result = new DmlPresetColor();
        result.setValue(getValue());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlPresetColor value = (DmlPresetColor)obj;

        return msObject.equals(value.getValue(), getValue());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getValue().hashCode();
        return hash;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.PRESET_COLOR; }

    /// <summary>
    /// Specifies the actual preset color value. 
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }
    /// <summary>
    /// Returns true, if this color value is empty or null.
    /// </summary>
    /*override*/ boolean isEmpty() { return !StringUtil.hasChars(mValue); }
    
    private String mValue = "";
}

