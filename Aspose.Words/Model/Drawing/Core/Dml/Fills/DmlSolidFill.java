// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.54 solidFill (Solid Fill)
/// This element specifies a solid color fill. The shape is filled entirely with the specified color.
/// </summary>
class DmlSolidFill extends DmlFill
{
    DmlSolidFill()
    {
    }

    DmlSolidFill(DmlColor color)
    {
        mColor = color;
    }

    /// <summary>
    /// Set style color to any color placeholders used in the fill.
    /// </summary>
    public /*override*/ void applyStyleColor(DmlColor styleColor)
    {
        getColor().applyStyleColor(styleColor);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if(!super.equals(obj))
            return false;

        DmlSolidFill value = (DmlSolidFill)obj;

        return msObject.equals(value.getColor(), getColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getColor().hashCode();
        return hash;
    }

    /*override*/ DmlFill deepClone()
    {
        DmlSolidFill result = new DmlSolidFill();
        result.setColor(getColor().deepClone());
        return result;
    }

    /*override*/ DmlColor getDmlColorInternal() { return getColor(); }
    /*override*/ void setDmlColorInternal(DmlColor value) { setColor(value); }

    /// <summary>
    /// Gets type of the Dml Fill.
    /// </summary>
    /*override*/ /*DmlFillType*/int getDmlFillType() { return DmlFillType.SOLID_FILL; }

    DmlColor getColor()
    {
        if (mColor == null)
            mColor = new DmlHexRgbColor();
        return mColor;
    }
    void setColor(DmlColor value) { mColor = value; }

    /// <summary>
    /// Returns true if fill color is empty.
    /// </summary>
    boolean isColorEmpty() { return getColor().isEmpty(); }

    private DmlColor mColor;
}

