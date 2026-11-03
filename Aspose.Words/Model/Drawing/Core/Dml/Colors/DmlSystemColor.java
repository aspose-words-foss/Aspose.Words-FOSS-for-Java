// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;


/// <summary>
/// This element specifies a color bound to predefined operating system elements.
/// </summary>
class DmlSystemColor extends DmlColor
{
    protected /*override*/ DrColor createUnmodifiedColor(IThemeProvider themeProvider)
    {
        // WORDSNET-6840 Word uses actual system color value if possible.
        DrColor resultColor = DrColor.getSystemColor(getValue());

        if (DrColor.equals(resultColor, DrColor.EMPTY))
        {
            // If color is not found or not supported use LastColor.
            resultColor = (getLastColor().length() > 0) ? DmlHexRgbColor.readHexColor(getLastColor()) : DrColor.EMPTY;
        }

        return resultColor;
    }

    public /*override*/ DmlColor deepClone()
    {
        DmlSystemColor result = new DmlSystemColor();
        result.setValue(getValue());
        result.setLastColor(getLastColor());
        copyColorModifiersTo(result);
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlSystemColor value = (DmlSystemColor)obj;

        return msObject.equals(value.getValue(), getValue());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getValue().hashCode();
        return hash;
    }

    public /*override*/ /*DmlColorType*/int getColorType() { return DmlColorType.SYSTEM_COLOR; }

    /// <summary>
    /// Specifies the system color value.
    /// </summary>
    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    /// <summary>
    /// Specifies the color value that was last computed by the generating application.
    /// </summary>
    String getLastColor()
    {
        if (mLastColor == null)
            mLastColor = "";
        return mLastColor;
    }
    void setLastColor(String value) { mLastColor = value; }

    /// <summary>
    /// Returns true, if this color value is empty or null.
    /// </summary>
    /*override*/ boolean isEmpty() { return !StringUtil.hasChars(mValue); }

    private String mLastColor;
    private String mValue = "";
}

