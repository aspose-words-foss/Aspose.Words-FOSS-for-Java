// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.4 alphaInv (Alpha Inverse Effect)
/// This element represents an alpha inverse effect.
/// Alpha (opacity) values are inverted by subtracting from 100%.
/// </summary>
class DmlAlphaInverseEffect extends DmlEffect implements Cloneable
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_INVERSE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlAlphaInverseEffect value = (DmlAlphaInverseEffect)obj;

        return msObject.equals(value.getColor(), getColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= getColor().hashCode();
        return hash;
    }

    /*override*/ DmlEffect deepClone()
    {
        DmlAlphaInverseEffect lhs = (DmlAlphaInverseEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        return lhs;
    }

    DmlColor getColor() { return mColor; }
    void setColor(DmlColor value) { mColor = value; }

    private DmlColor mColor;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

