// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.MathUtil;
import com.aspose.ms.System.msNumber;

/// <summary>
/// 20.1.8.60 tint (Tint Effect)
/// This element specifies a tint effect. Shifts effect color values 
/// towards/away from hue by the specified amount.
/// </summary>
class DmlTintEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.TINT; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlTintEffect value = (DmlTintEffect)obj;

        return msObject.equals(value.getHue(), getHue()) &&
               MathUtil.areEqual(value.getAmount(), getAmount());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        hash ^= msNumber.doubleHashCode(getAmount());
        hash ^= msNumber.doubleHashCode(getHue());
        return hash;
    }

    /// <summary>
    /// Specifies by how much the color value is shifted.
    /// </summary>
    double getAmount() { return mAmount; }
    void setAmount(double value) { mAmount = value; }

    /// <summary>
    /// Specifies the hue towards which to tint.
    /// </summary>
    double getHue() { return mHue; }
    void setHue(double value) { mHue = value; }

    private double mAmount;
    private double mHue;
}

