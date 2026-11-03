// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


/// <summary>
/// 20.1.8.23 duotone (Duotone Effect)
/// This element specifies a duotone effect.
/// For each pixel, combines clr1 and clr2 through a linear 
/// interpolation to determine the new color for that pixel.
/// </summary>
class DmlDuotoneEffect extends DmlEffect implements Cloneable
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.DUOTONE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlDuotoneEffect value = (DmlDuotoneEffect)obj;

        return msObject.equals(value.getColor1(), getColor1()) && msObject.equals(value.getColor2(), getColor2());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor1() != null)
            hash ^= getColor1().hashCode();
        if (getColor2() != null)
            hash ^= getColor2().hashCode();
        return hash;
    }

    /*override*/ DmlEffect deepClone()
    {
        DmlDuotoneEffect lhs = (DmlDuotoneEffect)memberwiseClone();

        if (mColor1 != null)
            lhs.mColor1 = mColor1.deepClone();

        if (mColor2 != null)
            lhs.mColor2 = mColor2.deepClone();

        return lhs;
    }

    /// <summary>
    /// Gets or sets a value of color which is used as the beginning of linear interpolation of the duotone effect.
    /// </summary>
    DmlColor getColor1() { return mColor1; }
    void setColor1(DmlColor value) { mColor1 = value; }

    /// <summary>
    /// Gets or sets a value of color which is used as the end of linear interpolation of the duotone effect.
    /// </summary>
    DmlColor getColor2() { return mColor2; }
    void setColor2(DmlColor value) { mColor2 = value; }

    private DmlColor mColor1;
    private DmlColor mColor2;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

